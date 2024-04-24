package junsu.personal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "image")
@Table(name="IMAGE")
@DynamicInsert
@DynamicUpdate
@Builder
public class ImageEntity {
    @Id
    @Column(name = "SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(name="IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name="BOARD_NUMBER", nullable = false)
    private Long boardNumber;

    public ImageEntity(Long boardNumber, String imageUrl){
        this.boardNumber = boardNumber;
        this.imageUrl = imageUrl;
    }
}
