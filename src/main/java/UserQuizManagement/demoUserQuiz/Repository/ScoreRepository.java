package UserQuizManagement.demoUserQuiz.Repository;



import UserQuizManagement.demoUserQuiz.Entity.Score;
import UserQuizManagement.demoUserQuiz.Utils.ScoreId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, ScoreId> {

    @Query("SELECT s from Score s where s.userId=?1 AND s.subjectId=?2")
    public List<Score> findScore(Long userId, Long subjectId);
}
