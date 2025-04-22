package com.excercise.ToDO_List.service;

import com.excercise.ToDO_List.model.Task;
import com.excercise.ToDO_List.repo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;



@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    private final List<Task> tasks = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public List<Task> getAllTasks(){
       return taskRepository.findAll();
    }

    public Task getTaskById(long id){

       Task t = taskRepository.getTaskById(id);

        return t;
    }

    public Task updateTask(long id, Task updatedTask){
        Task existingTask = taskRepository.getTaskById(id);

        if(existingTask != null){
            existingTask.setTitle(updatedTask.getTitle());
            existingTask.setDescription(updatedTask.getDescription());
            existingTask.setPerson(updatedTask.getPerson());
            existingTask.setDueDate(updatedTask.getDueDate());
            existingTask.setIsDone(updatedTask.getIsDone());
            taskRepository.save(existingTask);
        }



        return updatedTask;

    }

    public boolean deleteTask(long id){
        taskRepository.delete(taskRepository.getTaskById(id));

        if(!(taskRepository.existsById(id))){
            return true;
        }

        return false;

    }

    public List<Task> getTasksByPerson(String person){

        List<Task> perPerson = new ArrayList<>();

        for(Task t : taskRepository.findAll()){

            if(t.getPerson().equals(person)){
                perPerson.add(t);
            }

        }

        return perPerson;
    }

    public Task addTask(Task task) {

        taskRepository.save(task);


        return task;
    }
}
