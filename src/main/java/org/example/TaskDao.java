package org.example;

import java.util.List;

public interface TaskDao {
    void addTask(Task task);
    void updateTask(int id, String status);
    void deleteTask(int id);
    List<Task> getTasks();
}
