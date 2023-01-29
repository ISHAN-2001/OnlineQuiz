package UserQuizManagement.demoUserQuiz.Repository;

import UserQuizManagement.demoUserQuiz.Entity.Questions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Questions,Long> {

    public List<Questions> findBysubjectId(Long subjectId);


    @Query("SELECT correctAnswer FROM Questions q where q.questionId=?1")
    public List<Integer> findByquestionId(Long questionId);


    public List<Questions> findBysubjectId(Long subjectId, PageRequest pg);

}
