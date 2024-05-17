package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.entity.primaryKey.MatchAndHomeworkPk;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MATCH")
@Table(name="MATCH")
@DynamicInsert
@DynamicUpdate
@Builder
@IdClass(MatchAndHomeworkPk.class)
public class MatchEntity {
    @Id
    @Column(name = "STUDENT_ID")
    String studentId;
    @Id
    @Column(name = "TEACHER_ID")
    String teacherId;
    @Column(name = "STATUS")
    String status;
    @Column(name = "CONTENT")
    String content;
    @Column(name = "WRITE_DATETIME")
    private String writeDatetime;
}
