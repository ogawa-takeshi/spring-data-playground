package playground;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "customers")
@AllArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PACKAGE)
@Getter
@Setter
public class Customer {

    @Id
    private ObjectId id;

    @Field
    private String name;

    private Gender gender;

    private LocalDate birthday;

    private String email;

    private Address address;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updatedDate;

    @Version
    private long version;

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

}
