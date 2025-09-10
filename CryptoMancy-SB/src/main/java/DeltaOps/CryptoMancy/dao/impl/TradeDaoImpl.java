package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.TradeDao;
import DeltaOps.CryptoMancy.domain.Trade;
import org.springframework.jdbc.core.JdbcTemplate;

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
}
