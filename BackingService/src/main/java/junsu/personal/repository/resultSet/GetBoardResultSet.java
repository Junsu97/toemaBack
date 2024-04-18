package junsu.personal.repository.resultSet;

public interface GetBoardResultSet {
    Integer getBoardNumber();
    String getTitle();
    String getContent();
    String getWriteDatetime();
    String getWriterId();
    String getWriterNickname();
    String getWriterProfileImage();
}
