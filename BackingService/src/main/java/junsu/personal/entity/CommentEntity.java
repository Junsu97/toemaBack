package junsu.personal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name="COMMENT")
@DynamicInsert
@DynamicUpdate
@Builder
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_NUMBER")
    private Long comment_Number;

    @NonNull
    @Column(name = "CONTENT", nullable = false)
    private String content;

    @NonNull
    @Column(name="WRITE_DATETIME",nullable = false)
    private String writeDatetime;

    @NonNull
    @Column(name="USER_ID", nullable = false)
    private String userId;

    @NonNull
    @Column(name="BOARD_NUMBER", nullable = false)
    private Long boardNumber;
}
