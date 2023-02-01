package UserQuizManagement.demoUserQuiz.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class UserSubjectHelper {

    private Long userId;
    private Long subjectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSubjectHelper that = (UserSubjectHelper) o;
        return Objects.equals(userId, that.userId) && Objects.equals(subjectId, that.subjectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, subjectId);
    }

    public UserSubjectHelper(Long userId, Long subjectId) {
        this.userId = userId;
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "UserSubjectHelper{" +
                "userId=" + userId +
                ", subjectId=" + subjectId +
                '}';
    }
}
