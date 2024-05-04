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
@Entity(name = "STUDENT_USER")
@Table(name = "STUDENT_USER")
@DynamicInsert
@DynamicUpdate
@Builder(toBuilder = true)
public class StudentUserEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;
    @NonNull
    @Column(name = "USER_NAME", length = 500, nullable = false)
    private String userName;
    @NonNull
    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;

    @NonNull
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NonNull
    @Column(name = "EMAIL_AUTH", nullable = false)
    private Boolean emailAuth;

    @NonNull
    @Column(name = "TEL_NUMBER", nullable = false)
    private String telNumber;

    @NonNull
    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @NonNull
    @Column(name = "ADDR", nullable = false)
    private String addr;

    @NonNull
    @Column(name="ADDR_DETAIL")
    private String addrDetail;

    @NonNull
    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;
    @Column(name = "FACE_ID")
    private String faceId;

    @Column(name = "ROLE")
    private String role;
}
