package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.dao.impl.TradeDaoImpl;
import DeltaOps.CryptoMancy.domain.Trade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.mockito.ArgumentMatchers.eq;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TradeDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private TradeDaoImpl underTest;

    @Test
    public void TestCreationTrade()
    {
        Trade trade = Trade.builder()
                .tid(1L)
                .buy_order_id(3L)
                .sell_order_id(15L)
                .pid(2L)
                .price(BigDecimal.valueOf(233.22154))
                .amount(BigDecimal.valueOf(222.32))
                .executed_at(java.time.LocalDate.from(LocalDate.of(2005, 4, 23)))
                .build();

        underTest.create(trade);

        verify(jdbcTemplate).update(eq("INSERT INTO trades(tid, buy_order_id, sell_order_id, pid, price, amount, executed_at) VALUES (?, ?, ?, ?, ?, ?, ?)")
        ,eq(1L),eq(3L),eq(15L),eq(2L),eq(BigDecimal.valueOf(233.22154)),eq(BigDecimal.valueOf(222.32)),eq(LocalDate.of(2005, 4, 23)));
    }

    @Test
    public void TestSelectOneTrade()
    {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(eq("SELECT * FROM trades WHERE tid = ? LIMIT 1"), ArgumentMatchers.<TradeDaoImpl.TradeRowMapper>any(),eq(1L));
    }

}
