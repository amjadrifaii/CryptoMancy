package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

        public void create(User user);

        Optional<User> findOne(long l);

    List<User> findMany();
}
