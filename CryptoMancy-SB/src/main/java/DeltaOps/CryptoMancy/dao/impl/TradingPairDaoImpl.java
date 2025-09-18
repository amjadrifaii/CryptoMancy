package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.TradingPairDao;
import DeltaOps.CryptoMancy.domain.Trade;
import DeltaOps.CryptoMancy.domain.TradingPair;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
@Component

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
    public void update(TradingPair oldPair, TradingPair newPair)
    {
        jdbcTemplate.update("UPDATE trading_pairs SET pid = ?, base_symbol = ?, quote_symbol = ? WHERE pid = ?",
                newPair.getPid(),newPair.getBase_symbol(),newPair.getQuote_symbol(),oldPair.getPid());
    }
    @Override
    public Optional<TradingPair> findOne(long tradingPairId)
    {
        List<TradingPair> tradingPairs = jdbcTemplate.query("SELECT * FROM trading_pairs WHERE pid = ? LIMIT 1",new TradingPairsRowMapper(), tradingPairId);
                return tradingPairs.stream().findFirst();
    }

    @Override
    public List<TradingPair> findMany()
    {
        return jdbcTemplate.query("SELECT * FROM trading_pairs",new TradingPairsRowMapper());
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
