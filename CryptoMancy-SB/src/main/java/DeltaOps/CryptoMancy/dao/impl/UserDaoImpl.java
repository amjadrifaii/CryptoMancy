package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.UserDao;
import DeltaOps.CryptoMancy.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user)
    {
        jdbcTemplate.update("INSERT INTO users(uid, name, email, firebase_uid, creation_date) VALUES (?, ?, ?, ?, ?)",user.getUid(),user.getName(),user.getEmail(),user.getFirebase_uid(),user.getCreation_date());
    }

    @Override
    public Optional<User> findOne(long userId)
    {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE uid = ? LIMIT = 1",new UserRowMapper(),userId);
        return users.stream().findFirst();
    }

    public static class UserRowMapper implements RowMapper<User>
    {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return User.builder()
                    .uid(rs.getLong("uid"))
                    .name(rs.getString("name"))
                    .email(rs.getString("email"))
                    .firebase_uid(rs.getString("firebase_uid"))
                    .creation_date(rs.getTimestamp("creation_date").toLocalDateTime())
                    .build();
        }
    }

}
