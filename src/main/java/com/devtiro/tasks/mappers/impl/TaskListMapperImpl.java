package com.devtiro.tasks.mappers.impl;

import com.devtiro.tasks.domain.dto.TaskListDto;
import com.devtiro.tasks.domain.entities.Task;
import com.devtiro.tasks.domain.entities.TaskList;
import com.devtiro.tasks.domain.entities.TaskStatusEnum;
import com.devtiro.tasks.mappers.TaskListMapper;
import com.devtiro.tasks.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListMapperImpl implements TaskListMapper {
  private final TaskMapper taskMapper;

  public TaskListMapperImpl(TaskMapper taskMapper) {
    this.taskMapper = taskMapper;
  }

  @Override
  public TaskList fromDto(TaskListDto taskListDto) {
    return new TaskList(
        taskListDto.id(),
        taskListDto.title(),
        taskListDto.description(),
        null,
        null,
        Optional.ofNullable(taskListDto.tasks())
            .map(taskDtos -> taskDtos.stream().map(taskMapper::fromDto).toList())
            .orElse(null)
    );
  }

  @Override
  public TaskListDto toDto(TaskList task) {
    return new TaskListDto(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        Optional.ofNullable(task.getTasks()).map(List::size).orElse(0),
        calculateTaskListProgress(task.getTasks()),
        Optional.ofNullable(task.getTasks())
            .map(taskDtos -> taskDtos.stream().map(taskMapper::toDto).toList())
            .orElse(null));
  }

  private Double calculateTaskListProgress(List<Task> tasks) {
    if (tasks == null ) {
      return null;
    }

    long closedTaskCount = tasks.stream().filter(task -> task.getTaskStatus() == TaskStatusEnum.CLOSED).count();

    return (double) closedTaskCount / tasks.size();
  }
}
