package junsu.personal.entity.domain;


import junsu.personal.dto.request.auth.faceId.object.LandMark;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "STUDENT")
@Getter
@NoArgsConstructor
public class StudentFaceIdDomain {
    @Id
    private String id;

    @Field(name = "userId")
    private String userId;

    @Field(name = "accuracy")
    private double accuracy;

    @Field(name = "landMarks")
    private LandMark landMarks;
}
