package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.entity.primaryKey.MatchAndHomeworkPk;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CONNECTION")
@Table(name="CONNECTION")
@DynamicInsert
@DynamicUpdate
@Builder(toBuilder = true)
@IdClass(MatchAndHomeworkPk.class)
public class MatchEntity {
    @Id
    @Column(name = "STUDENT_ID")
    private String studentId;
    @Id
    @Column(name = "TEACHER_ID")
    private String teacherId;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "WRITE_DATETIME")
    private String writeDatetime;
}
