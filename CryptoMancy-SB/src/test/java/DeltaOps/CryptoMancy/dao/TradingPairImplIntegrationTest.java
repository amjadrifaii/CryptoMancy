package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.TradingPairDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import DeltaOps.CryptoMancy.domain.TradingPair;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

        Optional<TradingPair> tradingPairs = underTest.findOne(tradingPair.getPid());
        assertThat(underTest.findOne(tradingPair.getPid())).isPresent();
        assertThat(tradingPairs.get()).isEqualTo(tradingPair);
    }

    @Test
    public void TestMultipleTradingPairsCreationAndFetch()
    {
        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        Coin thirdCoin = TestDataUtil.CreateThirdCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);
        coinDaoImpl.create(thirdCoin);
        TradingPair tradingPair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        TradingPair secondTradingPair = TestDataUtil.CreateSecondTradingPair(firstCoin,thirdCoin);
        TradingPair thirdTradingPair = TestDataUtil.CreateThirdTradingPair(secondCoin,thirdCoin);
        underTest.create(tradingPair);
        underTest.create(secondTradingPair);
        underTest.create(thirdTradingPair);

        List<TradingPair> tradingPairs = underTest.findMany();
        List<TradingPair> tradingPairsUnderTest = new ArrayList<>();
        tradingPairsUnderTest.add(tradingPair);
        tradingPairs.add(secondTradingPair);
        tradingPairs.add(thirdTradingPair);
        assertThat(tradingPairs.containsAll(tradingPairsUnderTest));
        for(int i=0;i<tradingPairs.size();i++)
            System.out.println(tradingPairs.get(i).getBase_symbol()+" : "+tradingPairs.get(i).getQuote_symbol());


    }

    @Test
    public void TestUpdatingTradingPair()
    {
        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        Coin thirdCoin = TestDataUtil.CreateThirdCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);
        coinDaoImpl.create(thirdCoin);


        TradingPair oldPair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        TradingPair newPair = TestDataUtil.CreateSecondTradingPair(firstCoin,thirdCoin);
        underTest.create(oldPair);

        underTest.update(oldPair,newPair);
        assertThat(underTest.findOne(newPair.getPid())).isPresent();
        System.out.println(underTest.findOne(newPair.getPid()));
    }
}
