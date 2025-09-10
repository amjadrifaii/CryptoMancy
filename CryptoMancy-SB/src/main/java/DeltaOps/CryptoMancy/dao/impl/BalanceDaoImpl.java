package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.BalanceDao;
import DeltaOps.CryptoMancy.domain.Balance;
import org.springframework.jdbc.core.JdbcTemplate;

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
}

