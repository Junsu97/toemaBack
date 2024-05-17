package junsu.personal.repository;

import jakarta.transaction.Transactional;
import junsu.personal.entity.CommentEntity;
import junsu.personal.entity.primaryKey.CommentPK;
import junsu.personal.repository.resultSet.GetCommentListResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, CommentPK> {
    @Query(value =
            "SELECT " +
                    " COALESCE(SU.NICKNAME, TU.NICKNAME) AS nickname, " +
                    " COALESCE(SU.PROFILE_IMAGE, TU.PROFILE_IMAGE) AS profileImage, " +
                    " C.WRITE_DATETIME AS writeDatetime, " +
                    " C.CONTENT AS content " +
                    "FROM " +
                    " COMMENT AS C " +
                    " LEFT JOIN STUDENT_USER AS SU ON C.USER_ID = SU.USER_ID " +
                    " LEFT JOIN TEACHER_USER AS TU ON C.USER_ID = TU.USER_ID " +
                    "WHERE " +
                    " C.BOARD_NUMBER = ?1 " +
                    "ORDER BY " +
                    " writeDatetime DESC ",
            nativeQuery = true
    )
    List<GetCommentListResultSet> getCommentList(Long boardNumber);

    Long countByBoardNumber(Long boardNumber);

    @Transactional
    void deleteByBoardNumber(Long boardNumber);
}
