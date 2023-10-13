package com.satyam.questionservice.service;



import com.satyam.questionservice.dao.QuestionDao;
import com.satyam.questionservice.model.Question;
import com.satyam.questionservice.model.Response;
import com.satyam.questionservice.payloads.QuestionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    public List<Question> getAllQuestions(){
        return questionDao.findAll();    }

    public  List<Question> addQuestions(List<Question> questions){
        return questionDao.saveAll(questions);
    }

    public Question updateQuestion(Integer id,Question question) throws NoSuchObjectException {
        Optional<Question> question1 = questionDao.findById(id);
        if(!question1.isPresent()){
            throw new NoSuchObjectException("No question with this id");
        }

        if(question.getQuestionTitle()!=null){
            question1.get().setQuestionTitle(question.getQuestionTitle());
        }
        if(question.getOption1()!=null){
            question1.get().setOption1(question.getOption1());
        }
        if(question.getOption2()!=null){
            question1.get().setOption2(question.getOption2());
        }
        if(question.getOption3()!=null){
            question1.get().setOption3(question.getOption3());
        }
        if(question.getOption4()!=null){
            question1.get().setOption4(question.getOption4());
        }
        if(question.getCorrectAnswer()!=null){
            question1.get().setCorrectAnswer(question.getCorrectAnswer());
        }
        if(question.getDifficultyLevel()!=null){
            question1.get().setDifficultyLevel(question.getDifficultyLevel());
        }
        if(question.getCategory()!=null){
            question1.get().setCategory(question.getCategory());
        }

        return questionDao.save(question1.get());
    }

    public ResponseEntity<String> deleteQuestion(Integer id) throws NoSuchObjectException {
        Optional<Question> question1 = questionDao.findById(id);
        if(!question1.isPresent()){
            throw new NoSuchObjectException("No question with this id");
        }
        questionDao.deleteById(id);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.NO_CONTENT);
    }

    public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategory(category);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer  numOfQuestions) {
        List<Integer> questions =questionDao.findRandomQuestionByCategory(categoryName,numOfQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
        List<QuestionWrapper> questionWrappers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer id :questionIds){
            questions.add(questionDao.findById(id).get());
        }
        for(Question question : questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            questionWrappers.add(wrapper);
        }
        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        int right = 0;
        for(Response response :responses){
            Question question = questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(question.getCorrectAnswer()))
                right++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
