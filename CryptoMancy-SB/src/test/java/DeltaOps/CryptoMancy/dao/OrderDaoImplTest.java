package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.OrderDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import DeltaOps.CryptoMancy.domain.Order;
import DeltaOps.CryptoMancy.domain.TradingPair;
import DeltaOps.CryptoMancy.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private OrderDaoImpl underTest;

    @Test
    public void TestCreationOrder()
    {
        Order order = Order.builder()
                .oid(2L)
                .uid(1L)
                .pid(4L)
                .side("BUY")
                .price(BigDecimal.valueOf(2533.22))
                .amount(BigDecimal.valueOf(3))
                .status("OPEN")
                .creation_date(LocalDate.from(LocalDate.of(2005, 4, 23)))
                .build();
        underTest.create(order);
        verify(jdbcTemplate).update(eq("INSERT INTO orders(oid, uid, pid, side, price, amount, status, creation_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)")
        ,eq(2L),eq(1L),eq(4L),eq("BUY"),eq(BigDecimal.valueOf(2533.22)),eq(BigDecimal.valueOf(3)),eq("OPEN"),eq(java.time.LocalDate.from(LocalDate.of(2005, 4, 23)))
        );
    }

    @Test
    public void TestSelectOneOrder()
    {
        underTest.findOne(1);
        verify(jdbcTemplate).query(eq("SELECT oid, uid, pid, side, price, amount, status, creation_date FROM orders WHERE oid = ? LIMIT 1"), ArgumentMatchers.<OrderDaoImpl.OrderRowMapper>any(),eq(1L));
    }

    @Test
    public void TestUpdateOrder()
    {
        User user = TestDataUtil.CreateUser();
        Coin firstCoin = TestDataUtil.CreateCoin();
        Coin secondCoin = TestDataUtil.CreateSecondCoin();

        TradingPair pair = TestDataUtil.CreateSecondTradingPair(firstCoin,secondCoin);
        Order oldOrder = TestDataUtil.CreateOrder(user,pair);
        Order newOrder = TestDataUtil.CreateSecondOrder(user,pair);
        underTest.create(oldOrder);
        underTest.create(newOrder);
        underTest.update(oldOrder,newOrder);
        verify(jdbcTemplate).update(eq("UPDATE orders SET oid = ?, uid = ?, pid = ?, side = ?, price = ?, amount = ?, status = ?, creation_date = ? WHERE oid = ?"),
                eq(newOrder.getOid()), eq(newOrder.getUid()), eq(newOrder.getPid()), eq(newOrder.getSide()), eq(newOrder.getPrice()), eq(newOrder.getAmount()), eq(newOrder.getStatus()),eq(newOrder.getCreation_date()),eq(oldOrder.getOid()));

    }
}
