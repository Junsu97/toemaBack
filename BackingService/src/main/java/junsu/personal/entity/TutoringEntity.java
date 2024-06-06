package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.dto.request.tutoring.PatchTutoringRequestDTO;
import junsu.personal.dto.request.tutoring.PostTutoringRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TUTORING")
@Table(name="TUTORING")
@DynamicInsert
@DynamicUpdate
@Builder(toBuilder = true)
public class TutoringEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "STUDENT_ID", nullable = false)
    private String studentId;

    @Column(name ="TEACHER_ID", nullable = false)
    private String teacherId;

    @Column(name = "TUTORING_DATE")
    private String tutoringDate;

    @Column(name = "TUTORING_START_TIME")
    private String tutoringStartTime;
    @Column(name = "TUTORING_END_TIME")
    private String tutoringEndTime;

    @Column(name = "TUTORING_SUBJECT")
    private String tutoringSubject;

    public TutoringEntity(PostTutoringRequestDTO pDTO){
        this.studentId = pDTO.studentId();
        this.teacherId = pDTO.teacherId();
        this.tutoringDate = pDTO.tutoringDate();
        this.tutoringStartTime = pDTO.tutoringStartTime();
        this.tutoringEndTime = pDTO.tutoringEndTime();
        this.tutoringSubject = pDTO.tutoringSubject();
    }

    public void patchTutoring(PatchTutoringRequestDTO pDTO){
        this.tutoringDate = pDTO.tutoringDate();
        this.tutoringStartTime = pDTO.tutoringStartTime();
        this.tutoringEndTime = pDTO.tutoringEndTime();
        this.tutoringSubject = pDTO.tutoringSubject();
    }
}
