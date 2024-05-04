package junsu.personal.repository;

import junsu.personal.entity.BoardEntity;
import junsu.personal.repository.resultSet.GetBoardResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    boolean existsByBoardNumber(Long boardNumber);
    BoardEntity findByBoardNumber(Long boardNumber);

    List<BoardEntity> findAllByWriterId(String userId);

    @Query(
            value =
                    "SELECT " +
                    "B.BOARD_NUMBER AS boardNumber," +
                    "B.TITLE AS title," +
                    "B.CONTENT AS content," +
                    "B.WRITE_DATETIME AS writeDatetime," +
                    "B.WRITER_ID AS writerId," +
                    "U.NICKNAME AS nickname," +
                    "U.PROFILE_IMAGE AS writerProfileImage " +
                    "FROM BOARD AS B " +
                    "INNER JOIN STUDENT_USER AS U " +
                    "ON B.WRITER_ID = U.USER_ID " +
                    "WHERE BOARD_NUMBER = ?1 ",
                    nativeQuery = true
    )
    GetBoardResultSet getBoard(Long boardNumber);
}
