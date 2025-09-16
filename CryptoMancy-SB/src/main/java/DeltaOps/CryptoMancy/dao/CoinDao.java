package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.Coin;

import java.util.Optional;

public interface CoinDao {
    void create(Coin coin);

    Optional<Coin> findOne(String s);
}
