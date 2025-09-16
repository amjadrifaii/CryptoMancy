package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.OrderDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.TradingPairDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.UserDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import DeltaOps.CryptoMancy.domain.Order;
import DeltaOps.CryptoMancy.domain.TradingPair;
import DeltaOps.CryptoMancy.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class OrderDaoImplIntegrationTest {
    private final OrderDaoImpl underTest;
    private final UserDaoImpl userDaoImpl;
    private final TradingPairDaoImpl tradingPairDao;
    private final CoinDaoImpl coinDaoImpl;
    @Autowired
    public OrderDaoImplIntegrationTest(OrderDaoImpl underTest, UserDaoImpl userDaoImpl, TradingPairDaoImpl tradingPairDao, CoinDaoImpl coinDaoImpl)
    {
        this.underTest = underTest;
        this.userDaoImpl = userDaoImpl;
        this.tradingPairDao = tradingPairDao;
        this.coinDaoImpl = coinDaoImpl;
    }

    @Test
    public void TestCreateOrderAndFetch()
    {
        User user = TestDataUtil.CreateUser();
        userDaoImpl.create(user);
        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);
        TradingPair pair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        tradingPairDao.create(pair);
        Order order = TestDataUtil.CreateOrder(user,pair);
        underTest.create(order);
        Optional<Order> orders = underTest.findOne(order.getOid());
        assertThat(orders).isPresent();
        assertThat(orders.get()).isEqualTo(order);
    }
}
