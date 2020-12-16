package playground;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
@Getter
public class OrderLine {

    @Id
    private UUID id;

    private final String name;

    private final int quantity;

    private final BigDecimal price;

}
