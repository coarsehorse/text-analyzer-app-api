package com.home.textanalyzerappapi.repositiry;

import com.home.textanalyzerappapi.entity.TaskEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<TaskEntity, Integer> {
}
