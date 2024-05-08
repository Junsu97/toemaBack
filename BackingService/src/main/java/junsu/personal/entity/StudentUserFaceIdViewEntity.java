package junsu.personal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "STUDENT_FACE_ID_VIEW")
@Table(name = "STUDENT_FACE_ID_VIEW")
public class StudentUserFaceIdViewEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PASSWORD")
    private String password;

}
