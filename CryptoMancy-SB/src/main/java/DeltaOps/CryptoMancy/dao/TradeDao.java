package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.Trade;

import java.util.Optional;

public interface TradeDao {
    void create(Trade trade);

    Optional<Trade> findOne(long l);
}
