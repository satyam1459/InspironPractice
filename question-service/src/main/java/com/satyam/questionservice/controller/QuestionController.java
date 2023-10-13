package com.satyam.questionservice.controller;

import com.satyam.questionservice.model.Question;
import com.satyam.questionservice.model.Response;
import com.satyam.questionservice.payloads.QuestionWrapper;
import com.satyam.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.NoSuchObjectException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    @Autowired
    private Environment environment;

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return new ResponseEntity<>(questionService.getAllQuestions(), HttpStatus.OK);
    }

    @PostMapping("/questions")
    public List<Question> addQuestions(@RequestBody List<Question> questions){
        return questionService.addQuestions(questions);
    }

    @PutMapping("/question/{id}")
    public Question updateQuestion(@PathVariable Integer id,@RequestBody Question question) throws NoSuchObjectException {
        return questionService.updateQuestion(id,question);
    }
    @DeleteMapping("/question/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) throws NoSuchObjectException {
        return questionService.deleteQuestion(id);
    }

    //get questions based on category
    @GetMapping("/questions/category/{category}")
    public List<Question> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @GetMapping("generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName , @RequestParam Integer numOfQuestions ){
        return questionService.getQuestionsForQuiz(categoryName,numOfQuestions);
    }

    @PostMapping("getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
        System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
        return questionService.getScore(responses);
    }
    //generate
    //getquestions (questionId)
    //getScore
}
