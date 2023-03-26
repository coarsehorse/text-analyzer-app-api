package com.home.textanalyzerappapi.service;

import com.home.textanalyzerappapi.entity.TaskEntity;
import com.home.textanalyzerappapi.model.TaskStatus;
import com.home.textanalyzerappapi.repositiry.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskEntity create(String filePath) {
        var task = new TaskEntity();
        task.setFilePath(filePath);
        task.setStatus(TaskStatus.IN_QUEUE);

        return taskRepository.save(task);
    }
}
