package com.home.textanalyzerappapi.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileServiceTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            the-filename  1 ,the_filename_1
            the_filename%%2@,the_filename2
            THE_filename_3,the_filename_3
    """)
    void testNormalizeFilename(String filename, String expectedFilename) {
        String normalizedName = FileService.normalizeFilename(filename);
        assertEquals(normalizedName, expectedFilename);
    }

    @Test
    void testNormalizeFilenameThrows() {
        assertThrows(NullPointerException.class, () -> FileService.normalizeFilename(null));
    }
}
