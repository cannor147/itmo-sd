package com.github.cannor147.itmo.sd.lab04.controller;

import com.github.cannor147.itmo.sd.lab04.dao.TaskDao;
import com.github.cannor147.itmo.sd.lab04.dao.TaskListDao;
import com.github.cannor147.itmo.sd.lab04.dto.BoardDto;
import com.github.cannor147.itmo.sd.lab04.dto.TaskDto;
import com.github.cannor147.itmo.sd.lab04.util.ParseUtils;
import com.github.cannor147.itmo.sd.lab04.model.Task;
import com.github.cannor147.itmo.sd.lab04.model.TaskList;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Optional;

import static com.github.cannor147.itmo.sd.lab04.model.Status.TO_DO;
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
        final long taskListId = ParseUtils.parseLong(id);
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

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> create(@ModelAttribute TaskList formData) {
        final String name = formData.getName();
        if (Strings.isBlank(name)) {
            return ResponseEntity.badRequest().body("Task name required");
        }
        final TaskList taskList = new TaskList(-1, name, new Date());
        taskListDao.create(taskList);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/");
        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        final long taskListId = ParseUtils.parseLong(id);
        taskListDao.deleteById(taskListId);
        return ResponseEntity.ok("Task list deleted");
    }

    @PostMapping(value = "/{id}/add/{taskId}")
    public ResponseEntity<String> addTask(@PathVariable("id") String id, @PathVariable("taskId") String addingTaskId) {
        final long taskListId = ParseUtils.parseLong(id);
        final Optional<TaskList> taskList = taskListDao.findById(taskListId);
        if (taskList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final long taskId = ParseUtils.parseLong(addingTaskId);
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
    public ResponseEntity<String> createTask(@PathVariable("id") String id, @ModelAttribute Task formData) {
        final long taskListId = ParseUtils.parseLong(id);
        final Optional<TaskList> taskList = taskListDao.findById(taskListId);
        if (taskList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (Strings.isBlank(formData.getName())) {
            return ResponseEntity.badRequest().body("Task name required");
        } else if (Strings.isBlank(formData.getDescription())) {
            return ResponseEntity.badRequest().body("Task description required");
        }
        final Task task = new Task(-1, formData.getName(), formData.getDescription(), TO_DO, taskListId, new Date());
        taskDao.create(task);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/task-list/" + taskListId);
        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
    }
}
