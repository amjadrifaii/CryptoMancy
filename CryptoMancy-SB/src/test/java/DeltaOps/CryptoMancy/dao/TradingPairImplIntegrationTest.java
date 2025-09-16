package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.TradingPairDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import DeltaOps.CryptoMancy.domain.TradingPair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TradingPairImplIntegrationTest {
    private TradingPairDaoImpl underTest;
    private CoinDaoImpl coinDaoImpl;

    @Autowired
    public TradingPairImplIntegrationTest(TradingPairDaoImpl underTest, CoinDaoImpl coinDaoImpl)
    {
        this.underTest = underTest;
        this.coinDaoImpl = coinDaoImpl;
    }

    @Test
    public void TestCreateTradingPairAndFetch()
    {
        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);

        TradingPair tradingPair = TestDataUtil.CreateTradingPair(firstCoin, secondCoin);
        underTest.create(tradingPair);

        Optional<TradingPair> tradingPairs = underTest.findOne(16L);
        assertThat(tradingPairs.get()).isEqualTo(tradingPair);
    }
}
