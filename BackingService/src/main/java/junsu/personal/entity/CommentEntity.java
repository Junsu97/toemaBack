package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.dto.request.board.PostCommentRequestDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

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

    public CommentEntity(PostCommentRequestDTO pDTO, Long boardNumber, String userId){
        java.util.Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);
        this.content = pDTO.content();
        this.writeDatetime = writeDatetime;
        this.userId = userId;
        this.boardNumber = boardNumber;
    }
}
