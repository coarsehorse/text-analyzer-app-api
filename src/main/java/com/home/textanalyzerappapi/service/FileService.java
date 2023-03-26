package com.home.textanalyzerappapi.service;

import com.home.textanalyzerappapi.exception.BadRequestException;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.home.textanalyzerappapi.util.Utils.checkNotNull;
import static com.home.textanalyzerappapi.util.Utils.extractSafely;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@Service
@Slf4j
public class FileService {

    public static final String FILENAME_SPLITTER = "_";
    public static final List<String> SUPPORTED_EXTENSIONS = List.of("txt");

    /**
     * file.name_3.txt => (file.name) + (_3) + (.txt)
     * file.name.txt => (file.name) + (null) + (.txt)
     */
    public static final Pattern FILENAME_PATTERN = Pattern.compile("^(.*?)(_\\d+)?(\\.[\\w\\d]+)$");

    @Value("${app.storage.path}")
    private String STORAGE_PATH;

    private Boolean isSupportedFile(MultipartFile file) {
        checkNotNull(file, "file");
        String extension = getFileExtension(file);
        return SUPPORTED_EXTENSIONS.contains(extension);
    }

    protected String getFileExtension(MultipartFile file) {
        String fileName = extractSafely(file::getOriginalFilename);
        checkNotNull(fileName, "fileName");

        String[] tokens = fileName.split("\\.");
        return tokens[tokens.length - 1];
    }

    protected String normalizeFilename(String filename) {
        checkNotNull(filename, "filename");
        return filename.strip()
                .replaceAll("[\\s-_]+", FILENAME_SPLITTER)
                .replaceAll("[^\\w\\d.]", "")
                .toLowerCase();
    }

    protected String generateUniqueFilename(String filename) {
        if (!fileExists(filename)) {
            return filename;
        }
        do {
            var filenameParts = extractFilenameCounterExtension(filename);
            String clearName = filenameParts._1;
            Integer fileCounter = filenameParts._2;
            String extension = filenameParts._3;

            Integer newCounter = Option.of(fileCounter)
                    .map(x -> x + 1)
                    .getOrElse(0);

            filename = clearName + "_" + newCounter + extension;
        } while (fileExists(filename));

        return filename;
    }

    private Boolean fileExists(String filename) {
        File file = Path.of(STORAGE_PATH, filename).toFile();
        return file.isFile() || file.isDirectory();
    }

    private Tuple3<String, Integer, String> extractFilenameCounterExtension(String filename) {
        Matcher nameMatcher = FILENAME_PATTERN.matcher(filename);
        if (!nameMatcher.matches()) {
            throw new RuntimeException(
                    "Name \"%s\" doesn't match the pattern".formatted(filename));
        }
        String clearName = nameMatcher.group(1);
        Integer fileCounter = Option.of(nameMatcher.group(2))
                .map(counterStr -> counterStr.replaceAll("_", ""))
                .map(Integer::valueOf)
                .getOrNull();
        String extension = nameMatcher.group(3);

        return Tuple.of(clearName, fileCounter, extension);
    }

    @SneakyThrows
    private void createStorageDirectoryIfNotExists() {
        Path path = Path.of(STORAGE_PATH);

        if (path.toFile().isDirectory()) {
            return;
        }

        Files.createDirectories(path);
    }

    /**
     * Saves file to the storage if is supported.
     * @param file object containing file to save.
     * @return path of saved file.
     */
    @SneakyThrows
    public Path saveInStorage(MultipartFile file) {
        if (!isSupportedFile(file)) {
            throw new BadRequestException(
                    "File extension \"%s\" is not supported".formatted(file.getContentType()));
        }
        createStorageDirectoryIfNotExists();

        String normalizedFilename = normalizeFilename(file.getOriginalFilename());
        String uniqueFilename = generateUniqueFilename(normalizedFilename);
        Path filePath = Path.of(STORAGE_PATH, uniqueFilename);

        return Files.write(filePath, file.getBytes(), CREATE, WRITE);
    }
}
