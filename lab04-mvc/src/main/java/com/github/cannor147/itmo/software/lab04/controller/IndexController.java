package com.github.cannor147.itmo.software.lab04.controller;

import com.github.cannor147.itmo.software.lab04.dao.TaskDao;
import com.github.cannor147.itmo.software.lab04.dao.TaskListDao;
import com.github.cannor147.itmo.software.lab04.dto.TaskDto;
import com.github.cannor147.itmo.software.lab04.model.Task;
import com.github.cannor147.itmo.software.lab04.model.TaskList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.function.Function.*;
import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class IndexController {
    private final TaskDao taskDao;
    private final TaskListDao taskListDao;

    @GetMapping(value = "/")
    public ModelAndView view() {
        final ModelAndView modelAndView = new ModelAndView("Index");

        final List<TaskList> taskLists = taskListDao.findAll();
        final Map<Long, TaskList> idToTaskListMap = taskLists.stream().collect(toMap(TaskList::getId, identity()));
        modelAndView.addObject("taskLists", taskLists);

        final List<TaskDto> tasks = taskDao.findAll().stream()
                .map(task -> new TaskDto(task, idToTaskListMap.get(task.getTaskListId())))
                .collect(toList());
        final List<TaskDto> todoTasks = tasks.stream().filter(TaskDto::isTodo).collect(toList());
        final List<TaskDto> inProgressTasks = tasks.stream().filter(TaskDto::isInProgress).collect(toList());
        final List<TaskDto> completedTasks = tasks.stream().filter(TaskDto::isCompleted).collect(toList());
        modelAndView.addObject("todoTasks", todoTasks);
        modelAndView.addObject("inProgressTasks", inProgressTasks);
        modelAndView.addObject("completedTasks", completedTasks);

        return modelAndView;
    }
}
