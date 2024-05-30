package junsu.personal.repository.resultSet;

public interface GetCommentListResultSet {
    Integer getCommentNumber();
    Integer getBoardNumber();
    String getNickname();
    String getProfileImage();
    String getWriteDatetime();
    String getContent();
}
