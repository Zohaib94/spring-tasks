package com.devtiro.tasks.controllers;

import com.devtiro.tasks.domain.dto.TaskListDto;
import com.devtiro.tasks.domain.entities.TaskList;
import com.devtiro.tasks.mappers.TaskListMapper;
import com.devtiro.tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {
  private final TaskListService taskListService;
  private final TaskListMapper taskListMapper;

  public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
    this.taskListService = taskListService;
    this.taskListMapper = taskListMapper;
  }

  @GetMapping
  public List<TaskListDto> listTaskLists() {
    return taskListService.listTaskLists().stream().map(taskListMapper::toDto).toList();
  }

  @GetMapping(path = "/{id}")
  public TaskListDto getTaskList(@PathVariable UUID id) {
    return taskListService.getTaskList(id).map(taskListMapper::toDto).orElse(null);
  }

  @PostMapping
  public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
    TaskList taskList = taskListMapper.fromDto(taskListDto);
    return taskListMapper.toDto(taskListService.createTaskList(taskList));
  }

  @PutMapping(path = "/{id}/update")
  public TaskListDto updateTaskList(@PathVariable UUID id, @RequestBody TaskListDto taskListDto) {
    TaskList taskList = taskListMapper.fromDto(taskListDto);
    return taskListMapper.toDto(taskListService.updateTaskList(id, taskList));
  }

  @DeleteMapping(path = "/{id}")
  public void deleteTaskList(@PathVariable UUID id) {
    taskListService.deleteTaskList(id);
  }
}
