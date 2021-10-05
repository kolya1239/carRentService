package com.example.carrentservice.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "complaints_suggestions")
public class ComplaintsSuggestions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotBlank(message = "Введите имя")
    @Size(min = 10, max = 45)
    private String name;
    @Column(name = "description")
    @NotBlank(message = "Введите описание.")
    @Size(min = 10, max = 3000)
    private String description;
    @Column(name = "complaint_or_suggestion")
    private int complaint_or_suggestion;

    public ComplaintsSuggestions() {
    }

    public ComplaintsSuggestions(String name, String description, int complaintOrSuggestion) {
        this.name = name;
        this.description = description;
        this.complaint_or_suggestion = complaintOrSuggestion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getComplaint_or_suggestion() {
        return complaint_or_suggestion;
    }

    public void setComplaint_or_suggestion(int complaintOrSuggestion) {
        this.complaint_or_suggestion = complaintOrSuggestion;
    }
}
