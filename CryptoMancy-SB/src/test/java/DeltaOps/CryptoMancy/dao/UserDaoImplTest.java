package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.dao.impl.UserDaoImpl;
import DeltaOps.CryptoMancy.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

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
        User user = User.builder()
                .uid(1L)
                .name("Test Name")
                .email("testmail@mock.com")
                .firebase_uid("mock fire base uid")
                .creation_date(java.time.LocalDateTime.of(2005,4,23,11,12))
                .build();
        underTest.create(user);

        verify(jdbcTemplate).update(
                eq("INSERT INTO users (uid, name, email, firebase_uid, creation_date) VALUES (?, ?, ?, ?, ?)"),
                eq(1L), eq("Test Name"), eq("testmail@mock.com"), eq("mock fire base uid"),
                eq(java.time.LocalDateTime.of(2005,4,23,11,12))
        );
    }
}
