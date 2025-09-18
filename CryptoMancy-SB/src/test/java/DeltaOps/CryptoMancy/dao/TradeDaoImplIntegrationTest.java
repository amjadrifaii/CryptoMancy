package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.*;
import DeltaOps.CryptoMancy.domain.*;
import jakarta.transaction.Transactional;
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

public class TradeDaoImplIntegrationTest {
    private TradeDaoImpl underTest;
    private OrderDaoImpl orderDaoImpl;
    private TradingPairDaoImpl tradingPairDaoImpl;
    private UserDaoImpl userDaoImpl;
    private CoinDaoImpl coinDaoImpl;

    @Autowired
    public TradeDaoImplIntegrationTest(TradeDaoImpl underTest, OrderDaoImpl orderDaoImpl, TradingPairDaoImpl tradingPairDaoImpl, UserDaoImpl userDaoImpl,
                                        CoinDaoImpl coinDaoImpl)
    {
        this.underTest = underTest;
        this.orderDaoImpl=orderDaoImpl;
        this.tradingPairDaoImpl=tradingPairDaoImpl;
        this.userDaoImpl = userDaoImpl;
        this.coinDaoImpl = coinDaoImpl;
    }

    @Test
    public void TestCreateTradeAndFetch()
    {
        User firstUser = TestDataUtil.CreateUser();
        User secondUser = TestDataUtil.CreateSecondUser();

        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();

        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);

        TradingPair pair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        tradingPairDaoImpl.create(pair);

        userDaoImpl.create(firstUser);
        userDaoImpl.create(secondUser);

        Order firstOrder = TestDataUtil.CreateOrder(firstUser,pair);
        Order secondOrder = TestDataUtil.CreateSecondOrder(secondUser,pair);

        orderDaoImpl.create(firstOrder);
        orderDaoImpl.create(secondOrder);

        Trade trade = TestDataUtil.CreateTrade(firstOrder, secondOrder);
        underTest.create(trade);
        Optional<Trade> trades = underTest.findOne(66L);
        assertThat(trades.get()).isEqualTo(trade);
    }

    @Test
    public void TestMultipleTradesCreationAndFetch()
    {
        User firstUser = TestDataUtil.CreateUser();
        User secondUser = TestDataUtil.CreateSecondUser();

        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        Coin thirdCoin = TestDataUtil.CreateThirdCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);
        coinDaoImpl.create(thirdCoin);

        TradingPair firstPair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        TradingPair secondPair = TestDataUtil.CreateSecondTradingPair(secondCoin,thirdCoin);
        TradingPair thirdPair = TestDataUtil.CreateThirdTradingPair(firstCoin,thirdCoin);
        tradingPairDaoImpl.create(firstPair);
        tradingPairDaoImpl.create(secondPair);
        tradingPairDaoImpl.create(thirdPair);

        userDaoImpl.create(firstUser);
        userDaoImpl.create(secondUser);

        Order firstOrder = TestDataUtil.CreateOrder(firstUser,firstPair);
        Order secondOrder = TestDataUtil.CreateSecondOrder(secondUser,secondPair);
        Order thirdOrder = TestDataUtil.CreateThirdOrder(firstUser,thirdPair);

        orderDaoImpl.create(firstOrder);
        orderDaoImpl.create(secondOrder);
        orderDaoImpl.create(thirdOrder);

        Trade firstTrade = TestDataUtil.CreateTrade(firstOrder, secondOrder);
        Trade secondTrade = TestDataUtil.CreateSecondTrade(secondOrder, firstOrder);
        underTest.create(firstTrade);
        underTest.create(secondTrade);

        List<Trade> trades = underTest.findMany();
        assertThat(trades).contains(firstTrade,secondTrade);
        for(int i=0;i<trades.size();i++)
            System.out.println(trades.get(i).getTid());
    }

    @Test
    public void TestForUpdatingTrades()
    {
        User firstUser = TestDataUtil.CreateUser();
        User secondUser = TestDataUtil.CreateSecondUser();
        userDaoImpl.create(firstUser);
        userDaoImpl.create(secondUser);

        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        Coin thirdCoin = TestDataUtil.CreateThirdCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);
        coinDaoImpl.create(thirdCoin);

        TradingPair firstPair = TestDataUtil.CreateTradingPair(firstCoin, secondCoin);
        TradingPair secondPair = TestDataUtil.CreateSecondTradingPair(secondCoin, thirdCoin);
        tradingPairDaoImpl.create(firstPair);
        tradingPairDaoImpl.create(secondPair);

        Order firstOrder = TestDataUtil.CreateOrder(firstUser, firstPair);
        Order secondOrder = TestDataUtil.CreateSecondOrder(secondUser, firstPair);
        Order thirdOrder = TestDataUtil.CreateThirdOrder(secondUser, secondPair);
        orderDaoImpl.create(firstOrder);
        orderDaoImpl.create(secondOrder);
        orderDaoImpl.create(thirdOrder);

        Trade oldTrade = TestDataUtil.CreateTrade(firstOrder,secondOrder);
        Trade newTrade = TestDataUtil.CreateSecondTrade(firstOrder,thirdOrder);
        underTest.create(oldTrade);
        underTest.update(oldTrade,newTrade);
        assertThat(underTest.findOne(newTrade.getTid())).isPresent();
        System.out.println(underTest.findOne(newTrade.getTid()));
    }

}
