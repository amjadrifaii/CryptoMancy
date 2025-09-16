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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode =  DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    public void TestMultipleBalancesCreationAndFetch()
    {
        User user = TestDataUtil.CreateUser();
        userDaoImpl.create(user);
        Balance usdtBalance = TestDataUtil.CreateBalance(user.getUid());
        Balance ethBalance = TestDataUtil.CreateEthBalance(user.getUid());
        underTest.create(usdtBalance);
        underTest.create(ethBalance);
        List<Balance> balances = underTest.findMany();
        List<Balance> balancesUnderTest = new ArrayList<>();
        balancesUnderTest.add(usdtBalance);
        balancesUnderTest.add(ethBalance);
        assertThat(balances.containsAll(balancesUnderTest));
        for(int i=0;i<balances.size();i++)
            System.out.println(balances.get(i).getSymbol());

    }
}
