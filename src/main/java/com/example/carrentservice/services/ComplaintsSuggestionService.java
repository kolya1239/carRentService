package com.example.carrentservice.services;

import com.example.carrentservice.dao.ComplaintsSuggestionRepository;
import com.example.carrentservice.entities.ComplaintsSuggestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintsSuggestionService implements ComplaintsSuggestionServiceInterface{

    private ComplaintsSuggestionRepository complaintsSuggestionRepository;

    @Override
    public List<ComplaintsSuggestions> getComplaintsSuggestionList() {
        return complaintsSuggestionRepository.findAll();
    }

    @Override
    public ComplaintsSuggestions getComplaintSuggestion(int id) {
        return complaintsSuggestionRepository.getById(id);
    }

    @Override
    public void saveOrUpdateComplaintSuggestion(ComplaintsSuggestions complaintsSuggestions) {
        complaintsSuggestionRepository.save(complaintsSuggestions);
    }

    public ComplaintsSuggestionRepository getComplaintsSuggestionRepository() {
        return complaintsSuggestionRepository;
    }

    @Autowired
    public void setComplaintsSuggestionRepository(ComplaintsSuggestionRepository complaintsSuggestionRepository) {
        this.complaintsSuggestionRepository = complaintsSuggestionRepository;
    }
}
