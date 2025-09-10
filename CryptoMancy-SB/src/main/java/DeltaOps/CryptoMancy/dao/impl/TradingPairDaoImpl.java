package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.TradingPairDao;
import DeltaOps.CryptoMancy.domain.TradingPair;
import org.springframework.jdbc.core.JdbcTemplate;

public class TradingPairDaoImpl implements TradingPairDao {
    private final JdbcTemplate jdbcTemplate;

    public TradingPairDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(TradingPair tradingPair) {
        jdbcTemplate.update("INSERT INTO trading_pairs(pid, base_symbol, quote_symbol) VALUES(?, ?, ?)",
                tradingPair.getPid(),tradingPair.getBase_symbol(),tradingPair.getQuote_symbol()

        );
    }
}
