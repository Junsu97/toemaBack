package junsu.personal.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    STUDENT ("STUDENT"),
    TEACHER("TEACHER");

    private final String value;
}
