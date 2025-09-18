package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {
     void create(Order order);

    Optional<Order> findOne(long l);

    List<Order> findMany();

    void update(Order oldOrder,Order newOrder);
}
