package com.github.cannor147.itmo.software.lab04.dto;

import com.github.cannor147.itmo.software.lab04.model.Task;
import com.github.cannor147.itmo.software.lab04.model.TaskList;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class TasksDto {
    private final List<Task> tasks;
    private final List<TaskList> taskLists;
}
