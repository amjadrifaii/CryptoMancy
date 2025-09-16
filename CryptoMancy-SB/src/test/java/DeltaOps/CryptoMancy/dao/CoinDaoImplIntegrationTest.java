package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CoinDaoImplIntegrationTest {
    private CoinDaoImpl underTest;

    @Autowired
    public CoinDaoImplIntegrationTest(CoinDaoImpl underTest)
    {
        this.underTest=underTest;
    }

    @Test
    public void TestCreateCoinAndFetch() {
        Coin coin = TestDataUtil.CreateCoin();
        underTest.create(coin);
        Optional<Coin> coins = underTest.findOne(coin.getSymbol());
        assertThat(coins).isPresent();
        System.out.println((coins.get().getSymbol()));

    }
}
