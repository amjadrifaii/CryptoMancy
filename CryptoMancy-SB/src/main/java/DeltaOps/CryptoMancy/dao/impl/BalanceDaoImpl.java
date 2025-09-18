package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.BalanceDao;
import DeltaOps.CryptoMancy.domain.Balance;
import DeltaOps.CryptoMancy.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component

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
    public Optional<Balance> findOne(long userId, String symbol)
    {
        List<Balance> balances =
        jdbcTemplate.query("SELECT uid, symbol, amount FROM balances WHERE uid = ? AND symbol = ? LIMIT 1",new BalanceRowMapper(), userId, symbol);
        return balances.stream().findFirst();
    }

    @Override
    public List<Balance> findMany()
    {
        return jdbcTemplate.query("SELECT * FROM balances",new BalanceRowMapper());
    }

    @Override
    public void update(Balance oldBalance,Balance newBalance)
    {
        jdbcTemplate.update("UPDATE balances SET uid = ?,symbol = ?, amount = ? WHERE uid = ?", newBalance.getUid(), newBalance.getSymbol(), newBalance.getAmount(), oldBalance.getUid());
    }
    public static class BalanceRowMapper implements RowMapper<Balance>
    {
        @Override
        public Balance mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return Balance.builder()
                    .uid(rs.getLong("uid"))
                    .symbol(rs.getString("symbol"))
                    .amount(rs.getBigDecimal("amount"))
                    .build();
        }
    }
}

