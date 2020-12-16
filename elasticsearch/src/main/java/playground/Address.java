package playground;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Address {

    @Field(type = FieldType.Keyword)
    private final String country;

    private final String postalCode;

}
