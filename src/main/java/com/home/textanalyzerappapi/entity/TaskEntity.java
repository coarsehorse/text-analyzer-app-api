package com.home.textanalyzerappapi.entity;

import com.home.textanalyzerappapi.model.TaskStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Entity
@Table(name = "tasks")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String filePath;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    @NotNull
    private Instant createdDate;

    @LastModifiedDate
    @NotNull
    private Instant lastModifiedDate;
}
