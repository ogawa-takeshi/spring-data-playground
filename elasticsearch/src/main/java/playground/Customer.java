package playground;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.index.VersionType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(indexName = "customers", versionType = VersionType.INTERNAL)
@NoArgsConstructor
@Getter
@Setter
public class Customer implements Persistable<String> {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;

    private Gender gender;

    @Field(type = FieldType.Date, format = DateFormat.basic_date)
    private LocalDate birthday;

    private String email;

    @Field(type = FieldType.Object)
    private Address address;

    @CreatedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Field(type = FieldType.Date, format = DateFormat.basic_date_time)
    private LocalDateTime updatedDate;

    private SeqNoPrimaryTerm version;

    public Customer(String name) {
        this(name, null, null, null, null);
    }

    public Customer(String name, Gender gender, LocalDate birthday, String email, Address address) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.address = address;
    }

    @Override
    public boolean isNew() {
        return this.id == null;
    }

}
