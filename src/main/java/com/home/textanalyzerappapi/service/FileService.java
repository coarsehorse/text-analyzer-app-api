package com.home.textanalyzerappapi.service;

import com.home.textanalyzerappapi.exception.BadRequestException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static com.home.textanalyzerappapi.util.Utils.checkNotNull;

@Service
@Slf4j
public class FileService {

    public static final String STORAGE_PATH = "storage/";
    public static final String FILENAME_SPLITTER = "_";
    public static final List<String> SUPPORTED_EXTENSIONS = List.of("txt");



    private Boolean isFileSupported(MultipartFile file) {
        checkNotNull(file, "file");
        return SUPPORTED_EXTENSIONS.contains(file.getContentType());
    }

    protected static String normalizeFilename(String filename) {
        checkNotNull(filename, "filename");
        return filename.strip()
                .replaceAll("[\\s-_]+", FILENAME_SPLITTER)
                .replaceAll("[^\\w\\d]", "")
                .toLowerCase();
    }

    @SneakyThrows
    public void saveInStorage(MultipartFile file) {
        if (!isFileSupported(file)) {
            throw new BadRequestException("File extension " + file.getContentType() + " is not supported");
        }

        log.info("Filename : %s".formatted(file.getOriginalFilename()));

        String filename = normalizeFilename(file.getOriginalFilename());



        Files.createDirectories(Path.of(STORAGE_PATH));

//        Files.write(Paths.get(STORAGE_PATH, ))
    }
}
