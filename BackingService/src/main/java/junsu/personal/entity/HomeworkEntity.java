package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.dto.request.homework.PatchHomeworkRequestDTO;
import junsu.personal.dto.request.homework.PostHomeworkRequestDTO;
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
@Builder
public class HomeworkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name = "STUDENT_ID")
    private String studentId;


    @Column(name = "TEACHER_ID")
    private String teacherId;

    @NonNull
    @Column(name = "START_DATE")
    private String startDate;

    @NonNull
    @Column(name = "END_DATE")
    private String endDate;

    @NonNull
    @Column(name = "CONTENT")
    private String content;

    @NonNull
    @Column(name = "SUBMIT")
    private Boolean submit;

    public HomeworkEntity(PostHomeworkRequestDTO pDTO){
        this.studentId = pDTO.studentId();
        this.teacherId = pDTO.teacherId();;
        this.startDate = pDTO.startDate();
        this.endDate = pDTO.endDate();
        this.content = pDTO.content();
        this.submit = false;
    }

    public void patchHomework(PatchHomeworkRequestDTO pDTO){
        this.startDate = pDTO.startDate();
        this.endDate = pDTO.endDate();
        this.content = pDTO.content();
        this.submit = false;
    }
}
