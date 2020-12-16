package playground;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@RequiredArgsConstructor(staticName = "of")
@Getter
public class Address {

    private final String country;

    private final String postalCode;

    protected Address() {
        this.country = null;
        this.postalCode = null;
    }

}
