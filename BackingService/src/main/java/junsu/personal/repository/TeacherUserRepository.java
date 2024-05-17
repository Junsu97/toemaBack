package junsu.personal.repository;

import junsu.personal.entity.TeacherUserEntity;
import junsu.personal.repository.resultSet.GetTeacherInfoResultSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherUserRepository extends JpaRepository<TeacherUserEntity, String> {
    boolean existsByUserId(String userId);

    boolean existsByNickname(String nickname);

    boolean existsByTelNumber(String telNumber);

    boolean existsByEmail(String email);

    TeacherUserEntity findByUserId(String userId);

    TeacherUserEntity findByEmail(String email);

    TeacherUserEntity findByEmailAndUserName(String email, String userName);

    TeacherUserEntity findByUserIdAndEmailAndUserName(String userId, String email, String userName);

    @Query(
            value =
                    "SELECT " +
                            "T.USER_ID AS userId, " +
                            "T.NICKNAME AS nickname, " +
                            "T.SCHOOL AS school, " +
                            "T.PROFILE_IMAGE AS profileImage, " +
                            "TS.KOREAN AS korean, " +
                            "TS.MATH AS math, " +
                            "TS.SCIENCE AS science, " +
                            "TS.SOCIAL AS social, " +
                            "TS.ENGLISH AS english, " +
                            "TS.DESCRIPTION AS description " +
                            "FROM TEACHER_USER AS T " +
                            "INNER JOIN TEACHER_SUBJECT AS TS " +
                            "ON T.USER_ID = TS.USER_ID " +
                            "WHERE TS.USER_ID = ?1",
            nativeQuery = true
    )
    GetTeacherInfoResultSet getTeacher(String userId);
}
