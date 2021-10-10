package com.github.cannor147.itmo.software.lab04.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private long id;
    private String name;
    private String description;
    private Status status;
    private TaskList taskList;
    private Date creationDate;
}
