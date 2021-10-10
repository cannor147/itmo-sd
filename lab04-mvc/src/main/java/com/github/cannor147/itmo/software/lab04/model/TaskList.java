package com.github.cannor147.itmo.software.lab04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskList {
    private long id;
    private String name;
    private List<Task> tasks;
    private Date creationDate;
}
