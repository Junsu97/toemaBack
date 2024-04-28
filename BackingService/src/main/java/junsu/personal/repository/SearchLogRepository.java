package junsu.personal.repository;

import junsu.personal.entity.SearchLogEntity;
import junsu.personal.repository.resultSet.GetPopularListResultSet;
import junsu.personal.repository.resultSet.GetRelationListResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchLogRepository extends JpaRepository<SearchLogEntity, Long> {
    @Query(
            value =
                    "SELECT SEARCH_WORD as searchWord, COUNT(SEARCH_WORD) AS count " +
                            "FROM SEARCH_LOG " +
                            "WHERE RELATION IS FALSE " +
                            "GROUP BY SEARCH_WORD " +
                            "ORDER BY COUNT DESC " +
                            "LIMIT 15",
            nativeQuery = true
    )
    List<GetPopularListResultSet> getPopularList();

    @Query(
            value =
                    "SELECT RELATION_WORD as searchWord, COUNT(RELATION_WORD) AS count " +
                            "FROM SEARCH_LOG " +
                            "WHERE SEARCH_WORD = ?1 " +
                            "AND RELATION_WORD IS NOT NULL " +
                            "GROUP BY RELATION_WORD " +
                            "ORDER BY count DESC " +
                            "LIMIT 15",
            nativeQuery = true
    )
    List<GetRelationListResultSet> getRelationList(String searchWord);
}
