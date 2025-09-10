package DeltaOps.CryptoMancy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TradingPair {
    private Long pid;
    private String base_symbol;
    private String quote_symbol;
}


