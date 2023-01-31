package UserQuizManagement.demoUserQuiz.Service;

import UserQuizManagement.demoUserQuiz.Entity.Questions;
import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Repository.QuestionRepository;
import UserQuizManagement.demoUserQuiz.Repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ScoreRepository scoreRepository;

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



    @Transactional
    public int checkAnswer(Long userId,Long questionId, int givenAnswer){
        /*
        WARNING: same userId,questionId,givenAnswer database score will update by 1...
        questionId column not present in Scores Table... Needs care from frontend or caller function :)
         */

        List<Questions> quesstionInList = questionRepository.findByquestionId(questionId);

        Questions question = quesstionInList.get(0);

        int marks=0;

        if(question.getCorrectAnswer()==givenAnswer){
            marks=1;
            List<Score> scores = scoreRepository.findScore(userId, question.getSubjectId());
            if(scores.size()==0){
                scoreRepository.save(new Score(userId,question.getSubjectId(),10L));
            }
            else {
                Score score = scores.get(0);
                score.setScore(score.getScore() + 10L);
            }
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
