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

/**
 * Create View 해줘야함 ㅇㅇ
 * 강의 6강 18분 ~ 참고
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board_list_view")
@Table(name="BOARD_LIST_VIEW")
@DynamicInsert
@DynamicUpdate
@Builder
public class BoardListViewEntity {

    @Id
    @Column(name="BOARD_NUMBER")
    private Long boardNumber;

    @Column(name="TITLE")
    private String title;

    @Column(name="CONTENT")
    private String content;
    @Column(name="TITLE_IMAGE")
    private String titleImage;

    @Column(name = "VIEW_COUNT")
    private Long viewCount;

    @Column(name="FAVORITE_COUNT")
    private Long favoriteCount;

    @Column(name="COMMENT_COUNT")
    private Long commentCount;

    @Column(name="WRITE_DATETIME")
    private String writeDatetime;
    @Column(name="WRITER_NICKNAME")
    private String writerNickname;
    @Column(name="WRITER_PROFILE_IMAGE")
    private String writerProfileImage;
}
