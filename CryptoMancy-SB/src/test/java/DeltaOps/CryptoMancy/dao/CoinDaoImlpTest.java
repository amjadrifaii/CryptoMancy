package DeltaOps.CryptoMancy.dao;

import DeltaOps.CryptoMancy.dao.impl.BalanceDaoImpl;
import DeltaOps.CryptoMancy.dao.impl.CoinDaoImpl;
import DeltaOps.CryptoMancy.domain.Coin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CoinDaoImlpTest{

    @Mock
    private JdbcTemplate jdbcTemplate;
    @InjectMocks
    private CoinDaoImpl underTest;

    @Test
    public void TestCreationCoin()
    {
        Coin coin = Coin.builder()
                .symbol("ETH")
                .name("Ethereum")
                .type("Token")
                .contractAddress("ERC-20")
                .network("ETH")
                .logo_url("link")
                .description("newly added")
                .build();
        underTest.create(coin);
        verify(jdbcTemplate).update(eq("INSERT INTO coins(symbol, name, type, contractAddress, network,logo_url, description) VALUES(?, ?, ?, ?, ?, ?, ?)")
        ,eq("ETH"), eq("Ethereum"), eq("Token"), eq("ERC-20"), eq("ETH"), eq("link"),eq("newly added"));
    }
    @Test
    public void TestSelectOneCoin()
    {
        underTest.findOne(1);
        verify(jdbcTemplate).query(eq("SELECT symbol, name, type, contract_address, network, logo_url, description FROM coins WHERE symbol = ? LIMIT 1"), ArgumentMatchers.<CoinDaoImpl.CoinRowMapper>any(), eq(1L));
    }
}
