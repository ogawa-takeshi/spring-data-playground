package playground;

import lombok.Value;

@Value
public class CustomerStatisticsByCountry {

    private final String country;

    private final long count;

}
