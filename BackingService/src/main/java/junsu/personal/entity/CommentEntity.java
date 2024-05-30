package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.dto.request.board.PatchCommentRequestDTO;
import junsu.personal.dto.request.board.PostCommentRequestDTO;
import junsu.personal.entity.primaryKey.CommentPK;
import junsu.personal.entity.primaryKey.FavoritePk;
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
@IdClass(CommentPK.class)
public class CommentEntity {
    @Id
    @Column(name = "COMMENT_NUMBER")
    private Long commentNumber;

    @NonNull
    @Column(name = "CONTENT", nullable = false)
    private String content;

    @NonNull
    @Column(name="WRITE_DATETIME",nullable = false)
    private String writeDatetime;

    @NonNull
    @Column(name="USER_ID", nullable = false)
    private String userId;

    @Id
    @NonNull
    @Column(name="BOARD_NUMBER", nullable = false)
    private Long boardNumber;

    public CommentEntity(long seq,PostCommentRequestDTO pDTO, Long boardNumber, String userId){
        java.util.Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);
        this.commentNumber = seq;
        this.content = pDTO.content();
        this.writeDatetime = writeDatetime;
        this.userId = userId;
        this.boardNumber = boardNumber;
    }

    public void patchComment(PatchCommentRequestDTO requestBody){
        this.content = requestBody.content();
    }
}
