package com.devtiro.tasks.domain.dto;

import com.devtiro.tasks.domain.entities.TaskPriority;
import com.devtiro.tasks.domain.entities.TaskStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
  UUID id,
  String title,
  String description,
  LocalDateTime dueDate,
  TaskPriority taskPriority,
  TaskStatusEnum taskStatus
) {
}
