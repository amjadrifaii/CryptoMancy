package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.Coin;

import java.util.List;
import java.util.Optional;

public interface CoinDao {
    void create(Coin coin);

    Optional<Coin> findOne(String s);

    List<Coin> findMany();

    void update(Coin oldcoin, Coin newCoin);
}
