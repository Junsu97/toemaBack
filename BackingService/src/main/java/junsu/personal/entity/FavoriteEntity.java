package junsu.personal.entity;

import jakarta.persistence.*;
import junsu.personal.entity.primaryKey.FavoritePk;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorite")
@Table(name="FAVORITE")
@DynamicInsert
@DynamicUpdate
@IdClass(FavoritePk.class)
@Builder
public class FavoriteEntity {
    @Id
    @Column(name="USER_ID")
    private String userId;

    @Id
    @Column(name="BOARD_NUMBER", nullable = false)
    private Long boardNumber;
}
