package playground;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Address {

    private final String country;

    private final String postalCode;

}
