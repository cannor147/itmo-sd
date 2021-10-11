package com.github.cannor147.itmo.software.lab04.controller;

import com.github.cannor147.itmo.software.lab04.dao.TaskDao;
import com.github.cannor147.itmo.software.lab04.dao.TaskListDao;
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

@Controller
@RequestMapping(value = "/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskDao taskDao;
    private final TaskListDao taskListDao;

    @GetMapping(value = "/{id}")
    public ModelAndView view(@PathVariable("id") String id) {
        long taskId;
        try {
            taskId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            taskId = -1;
        }

        final ModelAndView modelAndView = new ModelAndView("Task");
        taskDao.findById(taskId).ifPresent(task -> {
            final Long taskListId = task.getTaskListId();
            final TaskList taskList = Optional.ofNullable(taskListId).flatMap(taskListDao::findById).orElse(null);
            modelAndView.addObject("task", new TaskDto(task, taskList));
        });
        return modelAndView;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> create(@RequestParam("name") String name,
                                         @RequestParam("description") String description) {
        if (Strings.isBlank(name)) {
            return ResponseEntity.badRequest().body("Task name required");
        } else if (Strings.isBlank(description)) {
            return ResponseEntity.badRequest().body("Task description required");
        }
        final Task task = new Task(-1, name, description, Status.TO_DO, null, new Date());
        taskDao.create(task);
        return ResponseEntity.ok("Task created");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        long taskId;
        try {
            taskId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            taskId = -1;
        }

        taskDao.deleteById(taskId);
        return ResponseEntity.ok("Task deleted");
    }

    @PostMapping(value = "/{id}/mark")
    public ResponseEntity<String> mark(@PathVariable("id") String id,
                                       @RequestParam("status") String status) {
        long taskId;
        try {
            taskId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            taskId = -1;
        }

        final Optional<Task> task = taskDao.findById(taskId);
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Status taskStatus;
        try {
            taskStatus = Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            taskStatus = null;
        }

        if (taskStatus == null) {
            return ResponseEntity.badRequest().body("Unknown status");
        }

        final Task updatedTask = task.get();
        if (updatedTask.getStatus() != taskStatus) {
            updatedTask.setStatus(taskStatus);
            taskDao.save(updatedTask);
        }

        return ResponseEntity.ok("Set task status to " + taskStatus);
    }
}
