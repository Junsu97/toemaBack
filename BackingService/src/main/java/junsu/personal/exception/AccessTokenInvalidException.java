package junsu.personal.exception;

public class AccessTokenInvalidException extends RuntimeException{
    private int statusCode;

    public AccessTokenInvalidException(int statusCode, String message){
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode(){
        return statusCode;
    }
}
