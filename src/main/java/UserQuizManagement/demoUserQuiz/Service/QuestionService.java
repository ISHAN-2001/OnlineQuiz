package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.Entity.Questions;
import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Repository.QuestionRepository;
import UserQuizManagement.demoUserQuiz.Repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    Map<Long,Score> totalanswermap; //Replace with sessionId

    QuestionService(){
        totalanswermap= new HashMap<>();
    }


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




    public int checkAnswer(Long userId,Long questionId, int givenAnswer){

        List<Questions> quesstionInList = questionRepository.findByquestionId(questionId);

        Questions question = quesstionInList.get(0);

        int marks=0;

        if(question.getCorrectAnswer()==givenAnswer){
            marks=1;
            Score currentScore;
            if(totalanswermap.containsKey(userId)){
                currentScore = totalanswermap.get(userId);
                currentScore.setScore(currentScore.getScore()+10);
            }
            else{
                currentScore = new Score(userId, question.getSubjectId(), 10L);
            }
            totalanswermap.put(userId,currentScore);

            System.out.println("Current Score:"+currentScore.getScore());
        }else{
            System.out.println("Wrong Answer");
        }

        return marks;

    }


    public Questions generateQuestions(Long subjectId,int offset){

        Questions question = null;
        try {
            List<Questions> questionList = questionRepository.findBysubjectId(subjectId, PageRequest.of(offset, 1));
            question = questionList.get(0);
        }catch (Exception ex){
            ex.printStackTrace();
            System.out.println("Offset more than questions");
        }

        //System.out.println(question);

        return question;
    }


    public List<Questions> getAllQuestions() {
        return  questionRepository.findAll();
    }

    public void saveAllQuestions(List<Questions> example) {

        questionRepository.saveAll(example);

    }

    @Transactional
    public void endthisQuiz(Long userId) {
        Score finalScore = totalanswermap.get(userId);

        List<Score> scoreInList = scoreRepository.findByuserId(userId);

        if(scoreInList.size()==0){
            scoreRepository.save(finalScore);
        }
        else{
            Score previousScore = scoreInList.get(0);
            previousScore.setScore(finalScore.getScore());
        }

        totalanswermap.remove(userId);
    }

    // METHODS NOT USED

    /*  //ARRAY IMPLEMENTATION
    public int checkAnswerList(Long user_id, List<Responces> responces)  {
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

        /* NOT USED: ARRAY IMPLEMENTATION :-)

    public List<Questions> generateQestionsList(Long SubjectId){
        *//*
        Recieves: SubjectId
        Action: Generates (10/20) random questions
        Return: List<Questions>
         *//*

        return questionRepository.findBysubjectId(SubjectId);

    }*/

}
