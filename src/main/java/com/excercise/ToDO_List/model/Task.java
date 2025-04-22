package com.excercise.ToDO_List.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Task {

    // ID der Aufgabe
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //Name der beschäftigung
    private String title;
    //Beschreibung der Aufgabe
    private String description;
    //Bis wann die Aufgabe fertig sein soll
    private LocalDateTime dueDate;
    //Die dafür zuständige Person
    private String person;
    //Der Status der Aufgabe
    private boolean isDone;

    public Task(long id, String title, String description, LocalDateTime dateTime, String person, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dateTime;
        this.person = person;
        this.isDone = status;
    }

    public Task() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(boolean done) {
        this.isDone = done;
    }


}


