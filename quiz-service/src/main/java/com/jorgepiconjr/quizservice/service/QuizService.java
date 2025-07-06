package com.jorgepiconjr.quizservice.service;
import com.jorgepiconjr.quizservice.feign.QuizQuestionInterface;
import com.jorgepiconjr.quizservice.model.QuestionWrapper;
import com.jorgepiconjr.quizservice.model.Quiz;
import com.jorgepiconjr.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.jorgepiconjr.quizservice.dao.QuizDao;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuizQuestionInterface quizQuestionInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Integer> questions = quizQuestionInterface.getQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questionIds = quiz.getQuestionIds();
        ResponseEntity<List<QuestionWrapper>> questions = quizQuestionInterface.getQuestionsFromId(questionIds);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses){
        ResponseEntity<Integer> score = quizQuestionInterface.getScore(responses);
        return score;
    }

    public Quiz getQuiz(Integer id){
        return quizDao.findById(id).get();
    }

    public Integer getNumberOfQuizesInDB(){
        Integer maxId = quizDao.findMaxId();
        if (maxId == null) {
            maxId = 0;
        }
        return maxId;
    }

    public List<Quiz> getQuizzes(){
        return quizDao.findAll();
    }

}
