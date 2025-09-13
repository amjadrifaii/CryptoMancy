package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.TradingPairDao;
import DeltaOps.CryptoMancy.domain.Trade;
import DeltaOps.CryptoMancy.domain.TradingPair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

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
    @Override

    public Optional<TradingPair> findOne(long tradingPairId)
    {
        List<TradingPair> tradingPairs = jdbcTemplate.query("SELECT * FROM trading_pairs WHERE pid = ? LIMIT = 1",new TradingPairsRowMapper(), tradingPairId);
                return tradingPairs.stream().findFirst();
    }

    public static class TradingPairsRowMapper implements RowMapper<TradingPair>
    {
        @Override
        public TradingPair mapRow(ResultSet rs,int rowNum) throws SQLException
        {
            return TradingPair.builder()
                    .pid(rs.getLong("pid"))
                    .base_symbol(rs.getString("base_symbol"))
                    .quote_symbol(rs.getString("quote_symbol"))
                    .build();
        }
    }


}
