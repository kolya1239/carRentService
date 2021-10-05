package com.example.carrentservice.services;

import com.example.carrentservice.entities.ComplaintsSuggestions;

import java.util.List;

public interface ComplaintsSuggestionServiceInterface {
    public List<ComplaintsSuggestions> getComplaintsSuggestionList();
    public ComplaintsSuggestions getComplaintSuggestion(int id);
    public void saveOrUpdateComplaintSuggestion(ComplaintsSuggestions complaintsSuggestions);
}
