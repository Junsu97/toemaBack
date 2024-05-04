package junsu.personal.repository;

import jakarta.transaction.Transactional;
import junsu.personal.entity.FavoriteEntity;
import junsu.personal.entity.primaryKey.FavoritePk;
import junsu.personal.repository.resultSet.GetFavoriteListResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk> {
    FavoriteEntity findByBoardNumberAndUserId(Long boardNumber, String userId);

    @Query(
            value =
                    "SELECT "+
                            "CASE "+
                            "WHEN SU.USER_ID IS NOT NULL THEN SU.USER_ID "+
                            "WHEN TU.USER_ID IS NOT NULL THEN TU.USER_ID "+
                            "END AS userId, "+
                            "CASE "+
                            "WHEN SU.NICKNAME IS NOT NULL THEN SU.NICKNAME "+
                            "WHEN TU.NICKNAME IS NOT NULL THEN TU.NICKNAME "+
                            "END AS nickname, " +
                            "CASE "+
                            "WHEN SU.PROFILE_IMAGE IS NOT NULL THEN SU.PROFILE_IMAGE "+
                            "WHEN TU.PROFILE_IMAGE IS NOT NULL THEN TU.PROFILE_IMAGE "+
                            "END AS profileImage " +
                            "FROM FAVORITE AS F "+
                            "LEFT JOIN STUDENT_USER AS SU " +
                            "ON F.USER_ID = SU.USER_ID "+
                            "LEFT JOIN TEACHER_USER AS TU " +
                            "ON F.USER_ID = TU.USER_ID "+
                            "WHERE F.BOARD_NUMBER = ?1 ",
            nativeQuery = true
    )
    List<GetFavoriteListResultSet> getFavoriteList(Long boardNumber);

    @Transactional
    void deleteByBoardNumber(Long boardNumber);
    List<FavoriteEntity> findAllByUserId(String userId);
}
