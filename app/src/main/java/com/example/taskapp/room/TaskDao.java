package com.example.taskapp.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.taskapp.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);
    @Query("SELECT * FROM task")
    List<Task> getAll();

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task WHERE id = :id")
    int deleteByID(final long id);

}
