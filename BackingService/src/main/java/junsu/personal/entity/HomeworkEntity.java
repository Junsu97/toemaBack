package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.entity.primaryKey.MatchAndHomeworkPk;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "HOMEWORK")
@Table(name="HOMEWORK")
@DynamicInsert
@DynamicUpdate
@IdClass(MatchAndHomeworkPk.class)
@Builder
public class HomeworkEntity {
    @Id
    @Column(name = "STUDENT_ID")
    String studentId;

    @Id
    @Column(name = "TEACHER_ID")
    String teacherId;

    @NonNull
    @Column(name = "START_DATE")
    String startDate;

    @NonNull
    @Column(name = "END_DATE")
    String endDate;

    @NonNull
    @Column(name = "CONTENT")
    String content;

    @NonNull
    @Column(name = "SUBMIT")
    String submit;

}
