package junsu.personal.repository;

import junsu.personal.entity.SearchLogEntity;
import junsu.personal.repository.resultSet.GetPopularListResultSet;
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
}
