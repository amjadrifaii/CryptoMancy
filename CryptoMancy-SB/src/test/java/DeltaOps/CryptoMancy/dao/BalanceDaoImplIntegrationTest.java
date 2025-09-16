package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.BalanceDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.UserDaoImpl;
import DeltaOps.CryptoMancy.domain.Balance;
import DeltaOps.CryptoMancy.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BalanceDaoImplIntegrationTest {

    private BalanceDaoImpl underTest;
    private UserDaoImpl userDaoImpl;
    @Autowired
    public BalanceDaoImplIntegrationTest(BalanceDaoImpl underTest, UserDaoImpl userDaoImpl) {
        this.underTest = underTest;
        this.userDaoImpl = userDaoImpl;
    }
    @Test
    public void TestCreateBalanceAndFetch()
    {
        User user = TestDataUtil.CreateUser();
        userDaoImpl.create(user);
        Balance balance = TestDataUtil.CreateBalance(user.getUid());
        underTest.create(balance);
        Optional<Balance> balances = underTest.findOne(balance.getUid(),balance.getSymbol());
        assertThat(balances).isPresent();
        assertThat(balances.get()).isEqualTo(balance);
    }

}
