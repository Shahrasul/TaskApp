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

    @Query("SELECT * FROM task ORDER BY title ASC")
    List<Task> getAllSorted();

    @Query("SELECT * FROM task ORDER BY createdAt ASC")
    List<Task> getAllDateSorted();

    @Delete
    void delete(Task task);

    @Query("DELETE FROM task WHERE id = :id")
    int deleteByID(final long id);


}
