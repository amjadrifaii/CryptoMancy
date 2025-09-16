package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.UserDaoImpl;
import DeltaOps.CryptoMancy.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserDaoImplIntegrationTest {

    private UserDaoImpl underTest;

    @Autowired
    public UserDaoImplIntegrationTest(UserDaoImpl underTest)
    {
        this.underTest = underTest;
    }

    @Test
    public void TestUserCreationAndFetch()
    {
        User user = TestDataUtil.CreateUser();
        underTest.create(user);
        Optional<User> users = underTest.findOne(user.getUid());
        assertThat(users).isPresent();
        assertThat(users.get()).isEqualTo(user);
    }

    @Test
    public void TestMultipleUsersCreationAndFetch()
    {
        User firstUser = TestDataUtil.CreateUser();
        User secondUser = TestDataUtil.CreateSecondUser();
        underTest.create(firstUser);
        underTest.create(secondUser);
        List<User> usersTest = new ArrayList<>();
        usersTest.add(firstUser);
        usersTest.add(secondUser);
        List<User> users = underTest.findMany();
        assertThat(users.containsAll(usersTest));
    }

}
