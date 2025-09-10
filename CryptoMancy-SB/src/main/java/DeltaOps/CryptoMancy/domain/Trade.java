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

public class Trade {
    private Long tid;
    private Long buy_order_id;
    private Long sell_order_id;
    private Long pid;
    private BigDecimal price;
    private BigDecimal amount;
    private java.time.LocalDate executed_at;
}
