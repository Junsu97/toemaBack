package junsu.personal.entity.primaryKey;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPK implements Serializable {
    @Column(name="COMMENT_NUMBER")
    private Long commentNumber;
    @Column(name="BOARD_NUMBER")
    private Long boardNumber;
}
