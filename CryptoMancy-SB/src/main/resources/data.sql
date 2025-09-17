-- USERS


INSERT INTO users (name, email, firebase_uid) VALUES
('Alice','alice@example.com','firebase_alice'),
('Bob','bob@example.com','firebase_bob');

-- COINS
INSERT INTO coins (symbol,name,type,network,logo_url,description) VALUES
('BTC','Bitcoin','Native','Bitcoin','https://en.wikipedia.org/wiki/Bitcoin','The first cryptocurrency'),
('ETH','Ethereum','Native','Ethereum','https://en.wikipedia.org/wiki/Ethereum','Smart contract platform'),
('USDT','Tether','Stablecoin','Ethereum','https://en.wikipedia.org/wiki/Tether_(cryptocurrency)','Stablecoin pegged to USD');

-- TRADING PAIRS
INSERT INTO trading_pairs (base_symbol, quote_symbol) VALUES
('BTC','USDT'),
('ETH','USDT'),
('ETH','BTC');

-- ORDERS
-- Assume user IDs 1 and 2, pair IDs 1 and 2
INSERT INTO orders (uid, pid, side, price, amount, status) VALUES
(1,1,'BUY',27000,0.1,'OPEN'),
(2,1,'SELL',27500,0.2,'OPEN'),
(1,2,'BUY',1800,2,'FILLED'),
(2,2,'SELL',1850,1.5,'FILLED');

-- TRADES
-- Assume trades match the orders above
INSERT INTO trades (buy_order_id, sell_order_id, pid, price, amount) VALUES
(3,4,2,1825,1.5),
(1,2,1,27250,0.1);

-- BALANCES
INSERT INTO balances (uid,symbol,amount) VALUES
(1,'BTC',0.15),
(1,'ETH',3),
(1,'USDT',1000),
(2,'BTC',0.25),
(2,'ETH',1.5),
(2,'USDT',2000);
