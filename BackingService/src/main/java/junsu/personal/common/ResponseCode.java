package junsu.personal.common;

public interface ResponseCode {
    /** HTTP Status 200*/
    String SUCCESS = "SU";

    /** HTTP Status 400*/
    String VALIDATION_FAILED = "VF";
    String DUPLICATE_ID = "DI";
    String DUPLICATE_APPLY = "DA";
    String DUPLICATE_NICKNAME = "DN";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_TEL_NUMBER = "DT";
    String NOT_EXISTED_USER = "NU";
    String NOT_EXISTED_BOARD = "NB";
    String NOT_EXISTED_APPLY = "NA";
    String NOT_EXISTED_COMMENT = "NC";

    String NOT_EXISTED_HOMEWORK = "NH";
    String NOT_EXISTED_MATCH = "NM";
    String NOT_EXISTED_TUTORING = "NT";

    /** HTTP Status 401*/
    String SIGN_IN_FAIL = "SF";
    String AUTHORIZATION_FAIL = "AF";

    /** HTTP Status 403*/
    String NO_PERMISSION = "NP";

    /** HTTP Status 500*/
    String DATABASE_ERROR = "DBE";

}
