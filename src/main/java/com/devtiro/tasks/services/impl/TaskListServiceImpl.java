package com.devtiro.tasks.services.impl;

import com.devtiro.tasks.domain.entities.TaskList;
import com.devtiro.tasks.repositories.TaskListRepository;
import com.devtiro.tasks.services.TaskListService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskListServiceImpl implements TaskListService {
  private final TaskListRepository taskListRepository;

  public TaskListServiceImpl(TaskListRepository taskListRepository) {
    this.taskListRepository = taskListRepository;
  }

  @Override
  public List<TaskList> listTaskLists() {
    return taskListRepository.findAll();
  }

  @Override
  public TaskList createTaskList(TaskList taskList) {
    if (taskList == null) {
      throw new IllegalArgumentException("Task list is missing");
    }

    if (taskList.getId() != null) {
      throw new IllegalArgumentException("Task list should not have ID");
    }

    if (taskList.getTitle().isBlank()) {
      throw new IllegalArgumentException("Title of task list is missing");
    }

    LocalDateTime now = LocalDateTime.now();

    return taskListRepository.save(new TaskList(null, taskList.getTitle(), taskList.getDescription(), now, now, null));
  }

  @Override
  public Optional<TaskList> getTaskList(UUID id) {
    return taskListRepository.findById(id);
  }

  @Override
  public TaskList updateTaskList(UUID id, TaskList taskList) {
    TaskList updationList = taskListRepository.findById(id).orElse(null);

    if (updationList == null) {
      throw new IllegalArgumentException("Incorrect ID");
    }

    updationList.setTitle(taskList.getTitle());
    updationList.setDescription(taskList.getDescription());
    updationList.setUpdatedAt(LocalDateTime.now());

    return taskListRepository.save(updationList);
  }

  @Override
  public void deleteTaskList(UUID id) {
    TaskList deletionList = taskListRepository.findById(id).orElse(null);

    if (deletionList == null) {
      throw new IllegalArgumentException("Incorrect ID");
    }

    taskListRepository.deleteById(id);
  }
}
