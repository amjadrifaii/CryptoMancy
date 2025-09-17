package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.TradeDao;
import DeltaOps.CryptoMancy.domain.Order;
import DeltaOps.CryptoMancy.domain.Trade;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
@Component

public class TradeDaoImpl implements TradeDao {
    private final JdbcTemplate jdbcTemplate;

    public TradeDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Trade trade) {
        jdbcTemplate.update("INSERT INTO trades(tid, buy_order_id, sell_order_id, pid, price, amount, executed_at) VALUES (?, ?, ?, ?, ?, ?, ?)",
        trade.getTid(),trade.getBuy_order_id(),trade.getSell_order_id(),trade.getPid(),trade.getPrice(),trade.getAmount(),trade.getExecuted_at()
        );
    }

    @Override
    public Optional<Trade> findOne(long tradeId)
    {
        List<Trade> trades = jdbcTemplate.query("SELECT * FROM trades WHERE tid = ? LIMIT 1",new TradeRowMapper(), tradeId);
        return trades.stream().findFirst();
    }

    @Override
    public List<Trade> findMany()
    {
        return jdbcTemplate.query("SELECT * FROM trades",new TradeRowMapper());
    }
    public static class TradeRowMapper implements RowMapper<Trade>
    {
        @Override
        public Trade mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return Trade.builder()
                    .tid(rs.getLong("tid"))
                    .buy_order_id(rs.getLong("buy_order_id"))
                    .sell_order_id(rs.getLong("sell_order_id"))
                    .pid(rs.getLong("pid"))
                    .price(rs.getBigDecimal("price"))
                    .amount(rs.getBigDecimal("amount"))
                    .executed_at(rs.getTimestamp("executed_at").toLocalDateTime().toLocalDate())
                    .build();
        }

    }
}

