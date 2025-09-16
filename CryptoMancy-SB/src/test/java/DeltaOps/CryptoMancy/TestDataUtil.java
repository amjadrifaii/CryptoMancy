package DeltaOps.CryptoMancy;

import DeltaOps.CryptoMancy.dao.TradingPairDao;
import DeltaOps.CryptoMancy.domain.*;

import java.math.BigDecimal;

public final class TestDataUtil {
    private TestDataUtil()
    {

    }

    public static User CreateUser() {
        return User.builder()
                .uid(4L)
                .name("Test Name")
                .email("testmail@mock.com")
                .firebase_uid("mock fire base uid")
                .creation_date(java.time.LocalDateTime.of(2005, 4, 23, 11, 12))
                .build();
    }

    public static User CreateSecondUser() {
        return User.builder()
                .uid(7L)
                .name("Test Name2")
                .email("testmail2@mock.com")
                .firebase_uid("mock fire base uid2")
                .creation_date(java.time.LocalDateTime.of(2006, 5, 24, 12, 13))
                .build();
    }

    public static Balance CreateBalance(Long userId)
    {
        return Balance.builder()
                .uid(userId)
                .amount(new BigDecimal(0))
                .symbol("USDT")
                .build();
    }

    public static Coin CreateCoin()
    {
        return Coin.builder()
                .symbol("SOL")
                .name("Solana")
                .type("Token")
                .contractAddress("ERC-20")
                .network("BTC")
                .logo_url("some url")
                .description("a nice coin")
                .build();
    }

    public static Coin CreateSecondCoin()
    {
        return Coin.builder()
                .symbol("TEST")
                .name("TEST")
                .type("TEST")
                .contractAddress("TEST")
                .network("TEST")
                .logo_url("some url")
                .description("highest priced coin")
                .build();
    }

    public static Order CreateOrder(User user,TradingPair pair)
    {
        return Order.builder()
                .oid(5L)
                .uid(user.getUid())
                .pid(pair.getPid())
                .side("BUY")
                .price(new BigDecimal(255.5))
                .amount(new BigDecimal(55))
                .status("OPEN")
                .creation_date(java.time.LocalDate.of(2005, 2, 12))
                .build();
    }

    public static Order CreateSecondOrder(User user, TradingPair pair)
    {
        return Order.builder()
                .oid(10L)
                .uid(user.getUid())
                .pid(pair.getPid())
                .side("SELL")
                .price(new BigDecimal(255.5))
                .amount(new BigDecimal(55))
                .status("OPEN")
                .creation_date(java.time.LocalDate.of(2004, 1, 1))
                .build();
    }

    public static TradingPair CreateTradingPair(Coin firstCoin, Coin secondCoin)
    {
        return TradingPair.builder()
                .pid(16L)
                .base_symbol(firstCoin.getSymbol())
                .quote_symbol(secondCoin.getSymbol())
                .build();
    }

    public static Trade CreateTrade(Order buyOrder, Order sellOrder)
    {
        return Trade.builder()
                .tid(66L)
                .buy_order_id(buyOrder.getOid())
                .sell_order_id(sellOrder.getOid())
                .pid(buyOrder.getPid())
                .price(new BigDecimal(2156.33))
                .amount(new BigDecimal(223.112))
                .executed_at(java.time.LocalDate.of(2002,2,2))
                .build();
    }
}
