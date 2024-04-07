package junsu.personal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board")
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
}
