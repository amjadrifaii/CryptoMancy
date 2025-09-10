package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.UserDao;
import DeltaOps.CryptoMancy.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user)
    {
        jdbcTemplate.update("INSERT INTO users (uid, name, email, firebase_uid, creation_date) VALUES (?, ?, ?, ?, ?)",
                user.getUid(),user.getName(),user.getEmail(),user.getFirebase_uid(),user.getCreation_date());
    }

}
