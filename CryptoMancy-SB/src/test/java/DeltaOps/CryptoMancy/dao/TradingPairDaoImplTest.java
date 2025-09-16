package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.dao.impl.TradingPairDaoImpl;
import DeltaOps.CryptoMancy.domain.TradingPair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TradingPairDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private TradingPairDaoImpl underTest;

    @Test
    public void TestCreationTradingPair()
    {
        TradingPair tradingPair = TradingPair.builder()
                .pid(2L)
                .base_symbol("ETH")
                .quote_symbol("USDT")
                .build();
        underTest.create(tradingPair);
        verify(jdbcTemplate).update(eq("INSERT INTO trading_pairs(pid, base_symbol, quote_symbol) VALUES(?, ?, ?)")
        ,eq(2L),eq("ETH"),eq("USDT")
        );
    }
    @Test
    public void TestSelectOneTradingPair()
    {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(eq("SELECT * FROM trading_pairs WHERE pid = ? LIMIT 1"), ArgumentMatchers.<TradingPairDaoImpl.TradingPairsRowMapper>any(),eq(1L));
    }
}
