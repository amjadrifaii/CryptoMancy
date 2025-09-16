package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.OrderDao;
import DeltaOps.CryptoMancy.domain.Order;
import org.mockito.ArgumentMatchers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
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

    @Override
    public Optional<Order> findOne(long orderId)
    {
        List<Order> orders = jdbcTemplate.query("SELECT oid, uid, pid, side, price, amount, status, creation_date FROM orders WHERE oid = ? LIMIT 1", new OrderRowMapper(),orderId);
        return orders.stream().findFirst();
    }

    public static class OrderRowMapper implements RowMapper<Order>
    {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return Order.builder()
                    .oid(rs.getLong("oid"))
                    .uid(rs.getLong("uid"))
                    .pid(rs.getLong("pid"))
                    .side(rs.getString("side"))
                    .price(rs.getBigDecimal("price"))
                    .amount(rs.getBigDecimal("amount"))
                    .status(rs.getString("status"))
                    .creation_date(rs.getTimestamp("creation_date").toLocalDateTime().toLocalDate())
                    .build();
        }
    }
}
