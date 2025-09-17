package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    public void TestMultipleCoinsCreationAndFetch()
    {
        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        underTest.create(firstCoin);
        underTest.create(secondCoin);
        List<Coin> coinList = underTest.findMany();
        List<Coin> coinsUnderTest = new ArrayList<>();
        coinsUnderTest.add(firstCoin);
        coinsUnderTest.add(secondCoin);
        assertThat(coinList.containsAll(coinsUnderTest));
        for(int i=0;i<coinList.size();i++)
            System.out.println(coinList.get(i).getSymbol());
    }
}
