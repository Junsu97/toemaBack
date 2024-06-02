package junsu.personal.common;

public interface ResponseMessage {
    /** HTTP Status 200*/
    String SUCCESS = "Success.";

    /** HTTP Status 400*/
    String VALIDATION_FAILED = "Validation failed.";
    String DUPLICATE_ID = "Duplication ID.";
    String DUPLICATE_APPLY = "Duplication Apply";
    String DUPLICATE_NICKNAME = "Duplication Nickname.";
    String DUPLICATE_EMAIL = "Duplication Email.";
    String DUPLICATE_TEL_NUMBER = "Duplication Tel Number.";

    String NOT_EXISTED_USER = "This user does not exist.";
    String NOT_EXISTED_BOARD = "This board does not exist.";
    String NOT_EXISTED_COMMENT = "This Comment does not exist.";
    String NOT_EXISTED_APPLY = "This apply does not exist.";

    String NOT_EXISTED_HOMEWORK = "This homework does not exist";
    String NOT_EXISTED_MATCH = "Does Not Matched";
    String NOT_EXISTED_TUTORING = "This tutoring does not exist";

    /** HTTP Status 401*/
    String SIGN_IN_FAIL = "Login information mismatch.";
    String AUTHORIZATION_FAIL = "Authorization Failed.";


    /** HTTP Status 403*/
    String NO_PERMISSION = "Do not have permission.";

    /** HTTP Status 500*/
    String DATABASE_ERROR = "Database Error.";
}
