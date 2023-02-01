package UserQuizManagement.demoUserQuiz.Controller;


import UserQuizManagement.demoUserQuiz.DTO.QuestionDTO;
import UserQuizManagement.demoUserQuiz.Entity.Questions;
import UserQuizManagement.demoUserQuiz.Service.QuestionService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@RestController
@RequestMapping(path="questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @PostMapping
    public void addQuestion(@RequestBody Questions question){

        if(question.getCorrectAnswer()<1 ||question.getCorrectAnswer()>4){
            throw new IllegalStateException("Correct answer should be between 1-4");
        }
        questionService.addquestion(question);
        //System.out.println(question);
    }

    @GetMapping
    public List<Questions> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping(path="/checkanswers/{userId}/{questionId}/{givenAnswer}")
    public int checkAnswers(@PathVariable Long userId,@PathVariable Long questionId,@PathVariable int givenAnswer){

        int marks = questionService.checkAnswer(userId,questionId,givenAnswer);

        return marks;

    }

    @GetMapping(path="/getquestions/{subject_id}/{offset}")
    public QuestionDTO getQuestions(@PathVariable Long subject_id,@PathVariable int offset ){

        Questions question= questionService.generateQuestions(subject_id,offset);
        QuestionDTO questionDTO = new QuestionDTO(question);
        return questionDTO;
    }

    @PostMapping(path="/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException, ParseException {
        if(file.isEmpty()){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No file found");
        }

        /*System.out.println(file.getName());
        System.out.println(file.getSize());
        System.out.println(file.getContentType());*/

        if(!file.getContentType().equals("application/json")){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File must be json");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Questions> example = objectMapper.readValue(file.getInputStream(), new TypeReference<List<Questions>>() {
            @Override
            public Type getType() {
                return super.getType();
            }
        });

        questionService.saveAllQuestions(example);


        System.out.println(example);



        return ResponseEntity.status(HttpStatus.ACCEPTED).body("OK");
    }

    @GetMapping("/endquiz/{userId}")
    public void endQuiz(@PathVariable Long userId){
        questionService.endthisQuiz(userId);
    }

    /* NOT USED...
    @GetMapping(path="/checkanswers")
    public int checkAnswers(){

        Long user_id=null;
        List<Responces> res = new ArrayList<>();

        res.add(new Responces(8l,2));
        res.add(new Responces(9l,3));
        res.add(new Responces(10l,4));


        int marks =questionService.checkAnswer(user_id,res);
        return marks;
    }*/



}
