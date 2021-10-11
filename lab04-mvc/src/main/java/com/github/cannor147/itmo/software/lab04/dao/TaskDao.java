package com.github.cannor147.itmo.software.lab04.dao;

import com.github.cannor147.itmo.software.lab04.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDao {
    Optional<Task> findById(long id);
    List<Task> findAll();
    List<Task> findAllByTaskListId(long taskListId);
    void create(Task task);
    void save(Task task);
    void deleteById(long id);
}
