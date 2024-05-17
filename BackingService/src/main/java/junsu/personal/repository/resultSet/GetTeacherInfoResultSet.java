package junsu.personal.repository.resultSet;

public interface GetTeacherInfoResultSet {
    String getUserId();
    String getNickname();
    String getSchool();
    String getProfileImage();
    Boolean getKorean();
    Boolean getMath();
    Boolean getScience();
    Boolean getSocial();
    Boolean getEnglish();
    String getDescription();
}
