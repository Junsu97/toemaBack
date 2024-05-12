package junsu.personal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TEACHER_LIST_VIEW")
@Table(name = "TEACHER_LIST_VIEW")
@DynamicInsert
@DynamicUpdate
@Builder(toBuilder = true)
public class TeacherSubjectEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "KOREAN")
    private Boolean korean;

    @Column(name = "MATH")
    private Boolean math;

    @Column(name = "SCIENCE")
    private Boolean science;

    @Column(name = "SOCIAL")
    private Boolean social;

    @Column(name = "ENGLISH")
    private Boolean english;

    @Column(name = "DESCRIPTION")
    private String desc;

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;
    @Column(name = "SCHOOL")
    private String school;
}
