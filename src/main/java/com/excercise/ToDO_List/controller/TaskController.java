package com.excercise.ToDO_List.controller;

import com.excercise.ToDO_List.model.Task;
import com.excercise.ToDO_List.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import biweekly.Biweekly;
import biweekly.ICalendar;
import biweekly.component.VEvent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

@RestController
//Damit ich mit 100%iger Sicherheit die Daten im Front-End bekommen kann
@CrossOrigin(origins = "*")
@RequestMapping("/tasks")
public class TaskController {

    TaskService taskService;

    TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Task addTask(@RequestBody Task task) {
        return taskService.addTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(id, updatedTask);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/ical")
    public ResponseEntity<byte[]> exportTaskAsICal(@PathVariable Long id) throws IOException {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        // iCalendar-Erstellung
        ICalendar ical = new ICalendar();
        VEvent event = new VEvent();
        event.setSummary(task.getTitle());
        event.setDescription(task.getDescription());
        if (task.getDueDate() != null) {
            event.setDateStart(java.util.Date.from(task.getDueDate()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
        }
        ical.addEvent(event);

        // iCalendar in einen Byte-Array konvertieren
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Biweekly.write(ical).go(out);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"task-" + id + ".ics\"")
                .body(out.toByteArray());
    }

    @GetMapping(value = "/tasks-by-person/{person}.ical")
    public ResponseEntity<byte[]> exportTaskAsICalperPerson(@PathVariable String person) throws IOException {

        List<Task> task = taskService.getTasksByPerson(person);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        // iCalendar-Erstellung
        ICalendar ical = new ICalendar();
        VEvent event = new VEvent();

        for (int i = 0; i < task.size(); i++) {

            event.setSummary(task.get(i).getTitle());
            event.setDescription(task.get(i).getDescription());
            if (task.get(i).getDueDate() != null) {
                event.setDateStart(java.util.Date.from(task.get(i).getDueDate()
                        .atZone(ZoneId.systemDefault())
                        .toInstant()));
            }
            ical.addEvent(event);

        }


        // iCalendar in einen Byte-Array konvertieren
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Biweekly.write(ical).go(out);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=\"task-" + person + ".ics\"")
                .body(out.toByteArray());
    }






}
