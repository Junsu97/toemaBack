package junsu.personal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
