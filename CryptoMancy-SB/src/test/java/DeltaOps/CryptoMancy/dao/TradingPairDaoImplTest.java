package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.TradingPairDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
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

    @Test
    public void TestUpdateTradingPair()
    {
        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        Coin thirdCoin = TestDataUtil.CreateThirdCoin();
        TradingPair oldPair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        TradingPair newPair = TestDataUtil.CreateTradingPair(firstCoin,thirdCoin);
        underTest.update(oldPair,newPair);
        verify(jdbcTemplate).update(eq("UPDATE trading_pairs SET pid = ?, base_symbol = ?, quote_symbol = ? WHERE pid = ?"),
                eq(newPair.getPid()), eq(newPair.getBase_symbol()), eq(newPair.getQuote_symbol()), eq(oldPair.getPid()));

    }
}
