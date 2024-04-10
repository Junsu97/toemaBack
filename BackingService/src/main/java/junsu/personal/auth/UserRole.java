package junsu.personal.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {
    USER ("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;
}
