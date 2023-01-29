package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.CustomException;
import UserQuizManagement.demoUserQuiz.Entity.Questions;
import UserQuizManagement.demoUserQuiz.Repository.QuestionRepository;
import UserQuizManagement.demoUserQuiz.Utils.Responces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public void addquestion(Questions question){
        /*
         Recieves: question Object
         Action: Adds question to database
         */
        if(question.getCorrectAnswer()<1 || question.getCorrectAnswer()>4 ){
            throw new IllegalStateException("Answer should be between 1-4");
        }

        System.out.println(question);
        questionRepository.save(question);

    }

    /*public int checkAnswerList(Long user_id, List<Responces> responces)  {
        *//*
         Recieves: user_id , responce object array.{question_id,given_ans }
         Action: checks answer and RETURNS marks.
         Return marks(int);
         *//*
        int marks=0;

        for (Responces responce1 : responces) {
            //System.out.println(responce1.getQuestion_id()+" "+responce1.getGiven_answer());

            List<Integer> q1 = questionRepository.findByquestionId(responce1.getQuestion_id());

            Integer answer= q1.get(0);

            if(answer==responce1.getGiven_answer()){
                marks++;
            }

        }

        System.out.println();
        return marks;
    }*/

    public int checkAnswer(Long userId,Long questionId, int givenAnswer){

        List<Integer> correctAnswerList = questionRepository.findByquestionId(questionId);

        Integer correctAnswer= correctAnswerList.get(0);

        int marks=0;

        if(correctAnswer==givenAnswer){
            marks=1;
        }

        return marks;

    }




    /*public List<Questions> generateQestionsList(Long SubjectId){
        *//*
        Recieves: SubjectId
        Action: Generates (10/20) random questions
        Return: List<Questions>
         *//*

        return questionRepository.findBysubjectId(SubjectId);

    }*/

    public Questions generateQuestions(Long subjectId,int offset){


        //Page<Questions> question=  questionRepository.findAll(PageRequest.of(offset,1));

        List<Questions> questionList = questionRepository.findBysubjectId(subjectId,PageRequest.of(offset,1));

        Questions question = questionList.get(0);


        System.out.println(question);

        return question;
    }


    public List<Questions> getAllQuestions() {
        return  questionRepository.findAll();
    }


}
