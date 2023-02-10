package com.home.textanalyzerappapi.entity;

import com.home.textanalyzerappapi.model.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TaskEntity {

    @Id
    private Long id;

    @NotNull
    private String filePath;

    @NotNull
    private TaskStatus status;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @NotNull
    private Instant createdDate;

    @LastModifiedDate
    @NotNull
    private Instant lastModifiedDate;
}
