package DeltaOps.CryptoMancy.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    private Long uid;
    private String name;
    private String email;
    private String firebase_uid;
    private java.time.LocalDateTime creation_date;
}


