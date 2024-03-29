package com.home.textanalyzerappapi.api.v1;

import com.home.textanalyzerappapi.dto.AnalyzeResponse;
import com.home.textanalyzerappapi.entity.TaskEntity;
import com.home.textanalyzerappapi.service.FileService;
import com.home.textanalyzerappapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@RestController
@RequestMapping("api/v1/tasks")
@Validated
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final FileService fileService;

    @PostMapping("analyse")
    public AnalyzeResponse analyse(@RequestParam MultipartFile file) {
        Path savedPath = fileService.saveInStorage(file);
        TaskEntity task = taskService.create(savedPath.toString());
        return AnalyzeResponse.from(task);
    }
}
