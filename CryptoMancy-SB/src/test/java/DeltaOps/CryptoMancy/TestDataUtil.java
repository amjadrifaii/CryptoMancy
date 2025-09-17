package DeltaOps.CryptoMancy;

import DeltaOps.CryptoMancy.dao.TradingPairDao;
import DeltaOps.CryptoMancy.domain.*;
import org.checkerframework.checker.units.qual.C;

import java.math.BigDecimal;
import java.util.List;

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

    public static Balance CreateBalance(User user, Coin coin)
    {
        return Balance.builder()
                .uid(user.getUid())
                .amount(new BigDecimal(0))
                .symbol(coin.getSymbol())
                .build();
    }

    public static Coin CreateCoin()
    {
        return Coin.builder()
                .symbol("Test Coin 1")
                .name("Test Coin 1 Name")
                .type("Test Coin 1 Type")
                .contractAddress("Test Coin 1 Contract Address")
                .network("Test Coin 1 Network")
                .logo_url("Test Coin 1 Logo Url")
                .description("Test Coin 1 Description")
                .build();
    }

    public static Coin CreateSecondCoin()
    {
        return Coin.builder()
                .symbol("Test Coin 2")
                .name("Test Coin 2 Name")
                .type("Test Coin 2 Type")
                .contractAddress("Test Coin 2 Contract Address")
                .network("Test Coin 2 Network")
                .logo_url("Test Coin 2 Logo Url")
                .description("Test Coin 2 Description")
                .build();
    }

    public static Coin CreateThirdCoin() {

        return Coin.builder()
                .symbol("Test Coin 3")
                .name("Test Coin 3 Name")
                .type("Test Coin 3 Type")
                .contractAddress("Test Coin 3 Contract Address")
                .network("Test Coin 3 Network")
                .logo_url("Test Coin 3 Logo Url")
                .description("Test Coin 3 Description")
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

    public static Order CreateThirdOrder(User user, TradingPair pair)
    {
        return Order.builder()
                .oid(111L)
                .uid(user.getUid())
                .pid(pair.getPid())
                .side("SELL")
                .price(new BigDecimal(255.5))
                .amount(new BigDecimal(55))
                .status("OPEN")
                .creation_date(java.time.LocalDate.of(2004, 1, 1))
                .build();
    }

    public static Order CreateFourthOrder(User user, TradingPair pair)
    {
        return Order.builder()
                .oid(222L)
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
                .pid(111L)
                .base_symbol(firstCoin.getSymbol())
                .quote_symbol(secondCoin.getSymbol())
                .build();
    }
    public static TradingPair CreateSecondTradingPair(Coin firstCoin, Coin secondCoin)
    {
        return TradingPair.builder()
                .pid(222L)
                .base_symbol(firstCoin.getSymbol())
                .quote_symbol(secondCoin.getSymbol())
                .build();
    }

    public static TradingPair CreateThirdTradingPair(Coin firstCoin, Coin secondCoin)
    {
        return TradingPair.builder()
                .pid(1234L)
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

    public static Trade CreateSecondTrade(Order buyOrder, Order sellOrder) {

        return Trade.builder()
                .tid(100L)
                .buy_order_id(buyOrder.getOid())
                .sell_order_id(sellOrder.getOid())
                .pid(buyOrder.getPid())
                .price(new BigDecimal(2156.33))
                .amount(new BigDecimal(223.112))
                .executed_at(java.time.LocalDate.of(2002,2,2))
                .build();
    }
}

