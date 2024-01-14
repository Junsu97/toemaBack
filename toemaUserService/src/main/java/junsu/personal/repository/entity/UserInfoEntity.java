package junsu.personal.repository.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_INFO")
@DynamicUpdate
@DynamicInsert
@Builder
@Cacheable
@Entity
public class UserInfoEntity {
    @Id
    @Column(name = "USER_ID")
    private String userId;
    @NonNull
    @Column(name="USER_NAME", length = 500, nullable = false)
    private String userName;

    @NonNull
    @Column(name = "PASSWORD", length = 1, nullable = false)
    private String password;

    @NonNull
    @Column(name="EMAIL", nullable = false)
    private String email;

    @NonNull
    @Column(name = "ADDR1", nullable = false)
    private String addr1;

    @Column(name = "ADDR2", nullable = false)
    private String addr2;

    @NonNull
    @Column(name="ROLES", nullable = false)
    private String roles;

}
