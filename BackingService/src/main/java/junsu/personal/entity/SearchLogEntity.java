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
@Entity(name = "search_log")
@Table(name="SEARCH_LOG")
@DynamicInsert
@DynamicUpdate
@Builder
public class SearchLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEQ")
    private Long seq;

    @Column(name="SEARCH_WORD")
    private String searchWord;

    @Column(name="RELATION_WORD")
    private String relationWord;
    @Column(name = "RELATION")
    private boolean relation;


}
