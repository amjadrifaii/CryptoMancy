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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    public void TestMultipleOrdersCreationAndFetch()
    {
        User user = TestDataUtil.CreateUser();
        User secondUser = TestDataUtil.CreateSecondUser();
        userDaoImpl.create(user);
        userDaoImpl.create(secondUser);

        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        Coin thirdCoin = TestDataUtil.CreateThirdCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);
        coinDaoImpl.create(thirdCoin);

        TradingPair firstPair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        TradingPair secondPair = TestDataUtil.CreateSecondTradingPair(secondCoin,thirdCoin);
        TradingPair thirdPair = TestDataUtil.CreateThirdTradingPair(firstCoin,thirdCoin);
        tradingPairDao.create(firstPair);
        tradingPairDao.create(secondPair);
        tradingPairDao.create(thirdPair);


        Order firstOrder = TestDataUtil.CreateOrder(user,firstPair);
        Order secondOrder = TestDataUtil.CreateSecondOrder(user,secondPair);
        Order thirdOrder = TestDataUtil.CreateThirdOrder(secondUser,thirdPair);
        Order fourthOrder = TestDataUtil.CreateFourthOrder(secondUser,firstPair);
        underTest.create(firstOrder);
        underTest.create(secondOrder);
        underTest.create(thirdOrder);
        underTest.create(fourthOrder);

        List<Order> orders = underTest.findMany();
        List<Order> ordersUnderTest = new ArrayList<>();
        ordersUnderTest.add(firstOrder);
        ordersUnderTest.add(secondOrder);
        ordersUnderTest.add(thirdOrder);
        ordersUnderTest.add(fourthOrder);
        assertThat(orders.containsAll(ordersUnderTest));
        for(int i=0;i<orders.size();i++)
            System.out.println(orders.get(i).getUid());
    }

    @Test
    public void TestForUpdatingOrder()
    {
        User user = TestDataUtil.CreateUser();
        User secondUser = TestDataUtil.CreateSecondUser();
        userDaoImpl.create(user);
        userDaoImpl.create(secondUser);

        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        Coin thirdCoin = TestDataUtil.CreateThirdCoin();
        coinDaoImpl.create(firstCoin);
        coinDaoImpl.create(secondCoin);
        coinDaoImpl.create(thirdCoin);

        TradingPair firstPair = TestDataUtil.CreateTradingPair(firstCoin,secondCoin);
        TradingPair secondPair = TestDataUtil.CreateSecondTradingPair(secondCoin,thirdCoin);
        tradingPairDao.create(firstPair);
        tradingPairDao.create(secondPair);

        Order oldOrder = TestDataUtil.CreateOrder(user,firstPair);
        Order newOrder = TestDataUtil.CreateSecondOrder(user,secondPair);
        underTest.create(oldOrder);
        underTest.update(oldOrder, newOrder);
        System.out.println(underTest.findOne(newOrder.getOid()));


    }
}
