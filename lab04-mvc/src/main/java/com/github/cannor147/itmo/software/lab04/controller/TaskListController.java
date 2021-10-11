package com.github.cannor147.itmo.software.lab04.controller;

import com.github.cannor147.itmo.software.lab04.dao.TaskDao;
import com.github.cannor147.itmo.software.lab04.dao.TaskListDao;
import com.github.cannor147.itmo.software.lab04.dto.BoardDto;
import com.github.cannor147.itmo.software.lab04.dto.TaskDto;
import com.github.cannor147.itmo.software.lab04.model.Status;
import com.github.cannor147.itmo.software.lab04.model.Task;
import com.github.cannor147.itmo.software.lab04.model.TaskList;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping(value = "/task-list")
@RequiredArgsConstructor
public class TaskListController {
    private final TaskDao taskDao;
    private final TaskListDao taskListDao;

    @GetMapping(value = "/{id}")
    public ModelAndView view(@PathVariable("id") String id) {
        long taskListId;
        try {
            taskListId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            taskListId = -1;
        }

        final ModelAndView modelAndView = new ModelAndView("TaskList");
        taskListDao.findById(taskListId).ifPresent(taskList -> {
            modelAndView.addObject("taskList", taskList);
            final BoardDto board = taskDao.findAllByTaskListId(taskList.getId()).stream()
                    .map(task -> new TaskDto(task, taskList))
                    .collect(collectingAndThen(toList(), BoardDto::new));
            modelAndView.addObject("board", board);
        });
        return modelAndView;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> create(@RequestParam("name") String name) {
        if (Strings.isBlank(name)) {
            return ResponseEntity.badRequest().body("Task name required");
        }
        final TaskList taskList = new TaskList(-1, name, new Date());
        taskListDao.create(taskList);
        return ResponseEntity.ok("Task list created");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        long taskListId;
        try {
            taskListId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            taskListId = -1;
        }

        taskListDao.deleteById(taskListId);
        return ResponseEntity.ok("Task list deleted");
    }

    @PostMapping(value = "/{id}/add/{taskId}")
    public ResponseEntity<String> addTask(@PathVariable("id") String id, @PathVariable("taskId") String addingTaskId) {
        long taskListId;
        try {
            taskListId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            taskListId = -1;
        }

        final Optional<TaskList> taskList = taskListDao.findById(taskListId);
        if (taskList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        long taskId;
        try {
            taskId = Long.parseLong(addingTaskId);
        } catch (NumberFormatException e) {
            taskId = -1;
        }

        final Optional<Task> task = taskDao.findById(taskId);
        if (task.isEmpty()) {
            return ResponseEntity.badRequest().body("No such task");
        }

        final Task updatedTask = task.get();
        updatedTask.setTaskListId(taskListId);
        taskDao.save(updatedTask);
        return ResponseEntity.ok("Task added to list");
    }

    @PostMapping(value = "/{id}/task")
    public ResponseEntity<String> createTask(@PathVariable("id") String id,
                                         @RequestParam("name") String name,
                                         @RequestParam("description") String description) {
        long taskListId;
        try {
            taskListId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            taskListId = -1;
        }

        final Optional<TaskList> taskList = taskListDao.findById(taskListId);
        if (taskList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (Strings.isBlank(name)) {
            return ResponseEntity.badRequest().body("Task name required");
        } else if (Strings.isBlank(description)) {
            return ResponseEntity.badRequest().body("Task description required");
        }
        final Task task = new Task(-1, name, description, Status.TO_DO, taskListId, new Date());
        taskDao.create(task);
        return ResponseEntity.ok("Task created");
    }
}
