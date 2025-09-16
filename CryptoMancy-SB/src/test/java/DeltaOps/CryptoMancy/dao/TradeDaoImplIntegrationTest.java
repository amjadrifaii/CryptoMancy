package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.*;
import DeltaOps.CryptoMancy.domain.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TradeDaoImplIntegrationTest {
    private TradeDaoImpl underTest;
    private OrderDaoImpl orderDaoImpl;
    private TradingPairDaoImpl tradingPairDao;
    private UserDaoImpl userDao;
    private CoinDaoImpl coinDaoImpl;

    @Autowired
    public TradeDaoImplIntegrationTest(TradeDaoImpl underTest, OrderDaoImpl orderDaoImpl, TradingPairDaoImpl tradingPairDao, UserDaoImpl userDao,
                                        CoinDaoImpl coinDaoImpl)
    {
        this.underTest = underTest;
        this.orderDaoImpl=orderDaoImpl;
        this.tradingPairDao=tradingPairDao;
        this.userDao = userDao;
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
        tradingPairDao.create(pair);

        userDao.create(firstUser);
        userDao.create(secondUser);

        Order firstOrder = TestDataUtil.CreateOrder(firstUser,pair);
        Order secondOrder = TestDataUtil.CreateSecondOrder(secondUser,pair);

        orderDaoImpl.create(firstOrder);
        orderDaoImpl.create(secondOrder);

        Trade trade = TestDataUtil.CreateTrade(firstOrder, secondOrder);
        underTest.create(trade);
        Optional<Trade> trades = underTest.findOne(66L);
        assertThat(trades.get()).isEqualTo(trade);
    }
}
