package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.TradingPair;

import java.util.List;
import java.util.Optional;

public interface TradingPairDao {
    void create(TradingPair tradingPair);

    Optional<TradingPair> findOne(long l);

    List<TradingPair> findMany();
}
