package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CoinDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private CoinDaoImpl underTest;

    @Test
    public void testCreateCoin()
    {
        Coin coin = Coin.builder()
                .symbol("BTC")
                .name("Bitcoin")
                .type("coin")
                .contractAddress("NULL")
                .network("Mainnet")
                .logo_url("https://en.wikipedia.org/wiki/Bitcoin")
                .description("Pioneer Coin")
                .build();
        underTest.create(coin);

        verify(jdbcTemplate).update(eq("INSERT INTO coins (symbol, name, type, contract_address, network, logo_url, description) VALUES (?, ?, ?, ?, ?, ?, ?)")
        ,eq("BTC"), eq("Bitcoin"), eq("coin"), eq("NULL"), eq("Mainnet"),eq("https://en.wikipedia.org/wiki/Bitcoin"),eq("Pioneer Coin")
        );
    }

}
