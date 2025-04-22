package com.excercise.ToDO_List.repo;

import com.excercise.ToDO_List.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    Task getTaskById(long id);
}
