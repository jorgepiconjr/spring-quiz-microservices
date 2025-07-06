package com.jorgepiconjr.quizservice.dao;

import com.jorgepiconjr.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizDao extends JpaRepository<Quiz, Integer> {
    @Query(value = "SELECT MAX(q.id) FROM quiz q" , nativeQuery = true)
    Integer findMaxId();
}
