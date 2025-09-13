package DeltaOps.CryptoMancy.dao.impl;

import DeltaOps.CryptoMancy.dao.CoinDao;
import DeltaOps.CryptoMancy.domain.Coin;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

public class CoinDaoImpl implements CoinDao {
    private final JdbcTemplate jdbcTemplate;

    public CoinDaoImpl(final JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Coin coin) {
        jdbcTemplate.update("INSERT INTO coins(symbol, name, type, contractAddress, network,logo_url, description) VALUES(?, ?, ?, ?, ?, ?, ?)",
                coin.getSymbol(), coin.getName(), coin.getType(), coin.getContractAddress(), coin.getNetwork(), coin.getLogo_url(), coin.getDescription());
    }
    public Optional<Coin> findOne(long coinSymbol)
    {
        List<Coin> coins = jdbcTemplate.query("SELECT symbol, name, type, contract_address, network, logo_url, description FROM coins WHERE symbol = ? LIMIT 1",new CoinRowMapper(),coinSymbol);
        return coins.stream().findFirst();
    }

    public static class CoinRowMapper implements RowMapper<Coin>
    {
        @Override
        public Coin mapRow(ResultSet rs, int rowNum) throws SQLException
        {
            return Coin.builder()
                    .symbol(rs.getString("symbol"))
                    .name(rs.getString("name"))
                    .type(rs.getString("type"))
                    .contractAddress(rs.getString("contract_address"))
                    .network(rs.getString("network"))
                    .logo_url(rs.getString("logo_url"))
                    .description(rs.getString("description"))
                    .build();
        }
    }
}
