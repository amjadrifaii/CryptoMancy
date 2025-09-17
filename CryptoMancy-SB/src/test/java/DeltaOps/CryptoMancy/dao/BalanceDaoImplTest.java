package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.dao.impl.BalanceDaoImpl;
import DeltaOps.CryptoMancy.domain.Balance;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BalanceDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private BalanceDaoImpl underTest;

    @Test
    public void testCreationBalance()
    {
        Balance balance = Balance.builder()
                .uid(5L)
                .symbol("ETH")
                .amount(BigDecimal.valueOf(55.11233))
                .build();
        underTest.create(balance);
        verify(jdbcTemplate).update(eq("INSERT INTO balances(uid, symbol, amount) VALUES(?, ?, ?)")
        ,eq(5L),eq("ETH"),eq(BigDecimal.valueOf(55.11233)));
    }

    @Test
    public void TestThatFindOneGeneratesTheCorrectSql()
    {
        underTest.findOne(1, "ETH");
        verify(jdbcTemplate).query(
                eq("SELECT uid, symbol, amount FROM balances WHERE uid = ? AND symbol = ? LIMIT 1"), ArgumentMatchers.<BalanceDaoImpl.BalanceRowMapper>any(), eq(1L), eq("ETH")
        );
    }
}
