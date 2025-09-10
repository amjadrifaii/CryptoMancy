package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.CoinDao;
import DeltaOps.CryptoMancy.domain.Coin;
import org.springframework.jdbc.core.JdbcTemplate;

public class CoinDaoImpl implements CoinDao {
    private final JdbcTemplate jdbcTemplate;

    public CoinDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Coin coin) {
        jdbcTemplate.update("INSERT INTO coins (symbol, name, type, contract_address, network, logo_url, description) VALUES (?, ?, ?, ?, ?, ?, ?)",
                coin.getSymbol(),coin.getName(),coin.getType(),coin.getContractAddress(),coin.getNetwork(),coin.getLogo_url(),coin.getDescription());
    }
}
