package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.TestDataUtil;
import DeltaOps.CryptoMancy.dao.impl.BalanceDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.UserDaoImpl;
import DeltaOps.CryptoMancy.domain.Balance;
import DeltaOps.CryptoMancy.domain.Coin;
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
    private CoinDaoImpl coinDaoImpl;
    @Autowired
    public BalanceDaoImplIntegrationTest(BalanceDaoImpl underTest, UserDaoImpl userDaoImpl, CoinDaoImpl coinDaoImpl) {
        this.underTest = underTest;
        this.userDaoImpl = userDaoImpl;
        this.coinDaoImpl = coinDaoImpl;
    }
    @Test
    public void TestCreateBalanceAndFetch()
    {
        User user = TestDataUtil.CreateUser();
        userDaoImpl.create(user);
        Coin coin = TestDataUtil.CreateCoin();
        coinDaoImpl.create(coin);
        Balance balance = TestDataUtil.CreateBalance(user,coin);
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
        Coin coin = TestDataUtil.CreateCoin();
        coinDaoImpl.create(coin);
        Coin secondCoin = TestDataUtil.CreateSecondCoin();
        coinDaoImpl.create(secondCoin);
        Balance firstBalance = TestDataUtil.CreateBalance(user, coin);
        Balance secondBalance = TestDataUtil.CreateBalance(user,secondCoin);
        underTest.create(firstBalance);
        underTest.create(secondBalance);
        List<Balance> balances = underTest.findMany();
        List<Balance> balancesUnderTest = new ArrayList<>();
        balancesUnderTest.add(firstBalance);
        balancesUnderTest.add(secondBalance);
        for(int i=0;i<balances.size();i++)
            System.out.println(balances.get(i).getSymbol());
    }

    @Test
    public void TestForUpdatingBalance()
    {
        User user = TestDataUtil.CreateUser();
        Coin coin = TestDataUtil.CreateCoin();
        Balance balance = TestDataUtil.CreateBalance(user, coin);
        underTest.create(balance);
    }
}
