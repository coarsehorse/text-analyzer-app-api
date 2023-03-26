package com.home.textanalyzerappapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TextAnalyzerAppApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextAnalyzerAppApiApplication.class, args);
    }

}
