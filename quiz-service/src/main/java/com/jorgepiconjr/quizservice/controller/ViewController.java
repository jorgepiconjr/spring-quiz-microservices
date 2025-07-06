package com.jorgepiconjr.quizservice.controller;

import com.jorgepiconjr.quizservice.model.QuestionWrapper;
import com.jorgepiconjr.quizservice.model.Quiz;
import com.jorgepiconjr.quizservice.model.Response;
import com.jorgepiconjr.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class ViewController {

    @Autowired
    QuizService quizService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("getQuizResponse")
    public String showQuiz(Model model){

        Random random = new Random();
        int quizId = 1 + random.nextInt(quizService.getNumberOfQuizesInDB());

        Quiz quiz = quizService.getQuiz(quizId);
        List<QuestionWrapper> quizQuestions = quizService.getQuizQuestions(quizId).getBody();

        model.addAttribute("quiz",quiz);
        model.addAttribute("quizQuestions",quizQuestions);

        return "add-response";
    }

    @PostMapping("getQuizResponse/{quizId}/submit")
    public String submitQuiz(
            @PathVariable Integer quizId,
            @RequestParam Map<String, String> allParams,
            Model model){

        Quiz quiz = quizService.getQuiz(quizId);
        model.addAttribute("quiz",quiz);

        List<Response> responses = new ArrayList<>();

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            try {
                Integer questionId = Integer.parseInt(entry.getKey());
                String answer = entry.getValue();

                responses.add(new Response(questionId, answer));
            } catch (NumberFormatException e) {
                // skip non-question inputs if needed
            }
        }

        int correctAnswers = quizService.calculateResult(quizId,responses).getBody();
        model.addAttribute("correctAnswers" , correctAnswers);

        return "add-response";

    }

    @GetMapping("create")
    public String showAddQuiz(Model model) {
        model.addAttribute("buttonEnable" , true);
        return "add-quiz";
    }

    @PostMapping("createQuiz")
    public String createQuiz(
            @RequestParam String category,
            @RequestParam int numQ,
            @RequestParam String title,
            Model model) {

        model.addAttribute("message_title" , title);
        model.addAttribute("message_numQ" , numQ);
        model.addAttribute("message_category" , category);
        model.addAttribute("buttonEnable" , false);

        quizService.createQuiz(category,numQ,title);

        return "add-quiz";
    }

    @GetMapping("getQuizzes")
    public String getQuizzes(Model model){
        List<Quiz> quizzes = quizService.getQuizzes();
        Map<Integer , List<QuestionWrapper>> quizQuestions = new HashMap<>();

        for (Quiz q : quizzes) {
            quizQuestions.put(q.getId() , quizService.getQuizQuestions(q.getId()).getBody());
        }

        model.addAttribute("quizzes" ,quizzes);
        model.addAttribute("quizQuestions", quizQuestions);

        return "get-quizzes";
    }

}
