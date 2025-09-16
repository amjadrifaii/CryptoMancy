
package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.UserDaoImpl;
import DeltaOps.CryptoMancy.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDaoImpl underTest;

    @Test
    public void testCreateUser()
    {
        User user = TestDataUtil.CreateUser();
        underTest.create(user);

        verify(jdbcTemplate).update(
                eq("INSERT INTO users (uid, name, email, firebase_uid, creation_date) VALUES (?, ?, ?, ?, ?)"),
                eq(1L), eq("Test Name"), eq("testmail@mock.com"), eq("mock fire base uid"),
                eq(java.time.LocalDateTime.of(2005,4,23,11,12))
        );
    }



    @Test
    public void TestSelectOneUser()
    {
        underTest.findOne(1L);
        verify(jdbcTemplate).query(eq("SELECT * FROM users WHERE uid = ? LIMIT 1"), ArgumentMatchers.<UserDaoImpl.UserRowMapper>any(),eq(1L));
    }
    @Test
    public void TestSelectManyUsers()
    {
        underTest.findMany();
        verify(jdbcTemplate).query(eq("SELECT * FROM users"),ArgumentMatchers.<UserDaoImpl.UserRowMapper>any());
    }

}
