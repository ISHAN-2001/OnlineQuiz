package UserQuizManagement.demoUserQuiz.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class QuestionHelper {

    private int totalQuestions;
    private int pageSize;
    private int totalPages;
    private int questionIndex;
    private int pageNo;

    private int score;

    private List<Integer> offset;

    public QuestionHelper(int totalQuestions) {
        this.totalQuestions = totalQuestions;
        this.pageSize = 10;
        this.totalPages = totalQuestions/10;
        this.questionIndex = 0;
        this.pageNo = (int)(Math.random()*totalPages);
        this.offset = new ArrayList<>();
        this.score =0;

        for(int i=0;i<10;i++){
            offset.add(i);
        }

        Collections.shuffle(offset);
    }
}
