package DeltaOps.CryptoMancy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Order {
    private  Long oid;
    private Long uid;
    private Long pid;
    private String side;
    private BigDecimal price;
    private BigDecimal amount;
    private String status;
    private java.time.LocalDate creation_date;
}
