package com.home.textanalyzerappapi.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileServiceTest {

    private final FileService fileService = new FileService();

    @ParameterizedTest
    @CsvSource(textBlock = """
            the-filename  1 ,the_filename_1
            the_filename%%2@,the_filename2
            THE_filename_3,the_filename_3
            the_filename.txt,the_filename.txt
    """)
    void testNormalizeFilename(String filename, String expectedFilename) {
        String normalizedName = fileService.normalizeFilename(filename);
        assertEquals(normalizedName, expectedFilename);
    }

    @ParameterizedTest
    @CsvSource(textBlock = """
            the-filename.txt,txt
            the.file.name.txt,txt
            THE.33filename-_txt.mp3,mp3
    """)
    void testGetFileExtension(String filename, String expectedExtension) {
        var multipartFile = new MockMultipartFile("file",
                filename, "text/plain", "Hello".getBytes());
        String extension = fileService.getFileExtension(multipartFile);
        assertEquals(extension, expectedExtension);
    }
}
