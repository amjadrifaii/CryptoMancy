package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.Balance;

import java.util.Optional;

public interface BalanceDao {
    void create(Balance balance);

    Optional<Balance> findOne(long l, String s);
}
