package com.example.carrentservice.dao;

import com.example.carrentservice.entities.ComplaintsSuggestions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintsSuggestionRepository extends JpaRepository<ComplaintsSuggestions, Integer> {
}
