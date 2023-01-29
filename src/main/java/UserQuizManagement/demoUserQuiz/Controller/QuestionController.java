package UserQuizManagement.demoUserQuiz.Controller;


import UserQuizManagement.demoUserQuiz.Entity.Questions;
import UserQuizManagement.demoUserQuiz.Service.QuestionService;
import UserQuizManagement.demoUserQuiz.Utils.Responces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path="questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public void addQuestion(@RequestBody Questions question){
        questionService.addquestion(question);
        //System.out.println(question);
    }

    @GetMapping
    public List<Questions> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    /*@GetMapping(path="/checkanswers")
    public int checkAnswers(){

        Long user_id=null;
        List<Responces> res = new ArrayList<>();

        res.add(new Responces(8l,2));
        res.add(new Responces(9l,3));
        res.add(new Responces(10l,4));


        int marks =questionService.checkAnswer(user_id,res);
        return marks;
    }*/

    @GetMapping(path="/checkanswers")
    public int checkAnswers(){

        Long userId=1L;
        Long questionId=8L;
        int givenAnswer=2;

        int marks = questionService.checkAnswer(userId,questionId,givenAnswer);

        return marks;

    }

    @GetMapping(path="/getquestions/{subject_id}/{offset}")
    public Questions getQuestions(@PathVariable Long subject_id,@PathVariable int offset ){

        return questionService.generateQuestions(subject_id,offset);

    }



}
