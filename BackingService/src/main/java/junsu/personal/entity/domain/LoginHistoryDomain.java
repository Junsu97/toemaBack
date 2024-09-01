package junsu.personal.entity.domain;

import junsu.personal.repository.mongo.object.LoginHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "loginHistory")
@Getter
@Setter
@NoArgsConstructor
public class LoginHistoryDomain {
    private String userId;
    private List<LoginHistory> loginHistoryList = new ArrayList<>();
}
