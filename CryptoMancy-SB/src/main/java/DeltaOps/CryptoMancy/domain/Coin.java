package DeltaOps.CryptoMancy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //setters and getters
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Coin {
    private String symbol;
    private String name;
    private String type;
    private String contractAddress;
    private String network;
    private String logo_url;
    private String description;
}

