package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.dto.request.board.PostBoardRequestDTO;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "BOARD")
@Table(name="BOARD")
@DynamicInsert
@DynamicUpdate
@Builder
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_NUMBER")
    private Long boardNumber;

    @NonNull
    @Column(name="TITLE", nullable = false)
    private String title;
    @NonNull
    @Column(name="CONTENT",nullable = false)
    private String content;

    @NonNull
    @Column(name="WRITE_DATETIME",nullable = false)
    private String writeDatetime;

    @NonNull
    @Column(name="FAVORITE_COUNT")
    private Long favoriteCount;
    @NonNull
    @Column(name = "COMMENT_COUNT")
    private Long commentCount;
    @NonNull
    @Column(name="VIEW_COUNT")
    private Long viewCount;
    @NonNull
    @Column(name = "WRITER_ID")
    private String writerId;

    public BoardEntity(PostBoardRequestDTO pDTO, String userId){

        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDateTime = simpleDateFormat.format(now);

        this.title = pDTO.title();
        this.content = pDTO.content();
        this.writeDatetime = writeDateTime;
        this.favoriteCount = 0L;
        this.commentCount = 0L;
        this.viewCount = 0L;
        this.writerId = userId;
    }
}
