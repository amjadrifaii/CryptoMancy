package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.OrderDao;
import DeltaOps.CryptoMancy.domain.Order;
import org.springframework.jdbc.core.JdbcTemplate;

import static javax.management.Query.eq;

public class OrderDaoImpl implements OrderDao {
    private final JdbcTemplate jdbcTemplate;

    public OrderDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Order order) {
        jdbcTemplate.update("INSERT INTO orders(oid, uid, pid, side, price, amount, status, creation_date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                order.getOid(),order.getUid(),order.getPid(),order.getSide(),order.getPrice(),order.getAmount(),order.getStatus(),order.getCreation_date());
    }
}
