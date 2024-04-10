package junsu.personal.entity.primaryKey;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MatchAndHomeworkPk implements Serializable {
    @Column(name = "userId")
    private String studentId;

    @Column(name = "userId")
    private String teacherId;
}
