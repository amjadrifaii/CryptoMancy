package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.Order;

import java.util.Optional;

public interface OrderDao {
     void create(Order order);

    Optional<Order> findOne(long l);
}
