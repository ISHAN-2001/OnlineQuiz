package UserQuizManagement.demoUserQuiz.DTO;

import UserQuizManagement.demoUserQuiz.Entity.Questions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
    private Long questionId;
    private Long subjectId;
    private String description;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionDTO(Questions question){
        questionId= question.getQuestionId();
        subjectId = question.getSubjectId();
        description = question.getDescription();
        option1 = question.getOption1();
        option2= question.getOption2();
        option3= question.getOption3();
        option4= question.getOption4();
    }
}
