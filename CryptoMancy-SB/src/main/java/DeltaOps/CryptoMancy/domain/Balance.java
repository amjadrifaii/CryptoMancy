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

public class Balance {
    private Long uid;
    private String symbol;
    private  BigDecimal amount;
}
