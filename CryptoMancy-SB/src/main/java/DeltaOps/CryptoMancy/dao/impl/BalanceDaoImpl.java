package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.BalanceDao;
import DeltaOps.CryptoMancy.domain.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BalanceDaoImpl implements BalanceDao {
    private final JdbcTemplate jdbcTemplate;

    public BalanceDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Balance balance) {
        jdbcTemplate.update("INSERT INTO balances(uid, symbol, amount) VALUES(?, ?, ?)",
                balance.getUid(), balance.getSymbol(), balance.getAmount());
    }

    @Override
    public Optional<Balance> findOne(long balanceId)
    {
        List<Balance> balances =
        jdbcTemplate.query("SELECT uid, name, email FROM users WHERE uid = ? LIMIT 1",new BalanceRowMapper(),balanceId);
        return balances.stream().findFirst();
    }

    public static class BalanceRowMapper implements RowMapper<Balance>
    {
        @Override
        public Balance mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return Balance.builder()
                    .uid(rs.getLong("id"))
                    .symbol(rs.getString("symbol"))
                    .amount(rs.getBigDecimal("amount"))
                    .build();
        }
    }
}

