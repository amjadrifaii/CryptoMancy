DROP TABLE IF EXISTS "coins" CASCADE;
DROP TABLE IF EXISTS "users" CASCADE;
DROP TABLE IF EXISTS "trading_pairs" CASCADE;
DROP TABLE IF EXISTS "orders" CASCADE;
DROP TABLE IF EXISTS "trades" CASCADE;
DROP TABLE IF EXISTS "balances" CASCADE;

DROP SEQUENCE IF EXISTS user_id_seq;
DROP SEQUENCE IF EXISTS pair_id_seq;
DROP SEQUENCE IF EXISTS order_id_seq;
DROP SEQUENCE IF EXISTS trade_id_seq;

CREATE SEQUENCE user_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999 CACHE 1;
CREATE SEQUENCE pair_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999 CACHE 1;
CREATE SEQUENCE order_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999 CACHE 1;
CREATE SEQUENCE trade_id_seq INCREMENT 1 MINVALUE 1 MAXVALUE 999999999999 CACHE 1;

CREATE TABLE "users" (
    "uid" bigint DEFAULT nextval('user_id_seq') NOT NULL,
    "name" text NOT NULL,
    "email" text NOT NULL,
    "firebase_uid" text NOT NULL,
    "creation_date" TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (uid)
);

CREATE TABLE "coins"(
    "symbol" text NOT NULL,
    "name" text NOT NULL,
    "type" text,
    "contract_address" text,
    "network" text,
    "logo_url" text,
    "description" text,
    CONSTRAINT coins_pkey PRIMARY KEY (symbol)
);

CREATE TABLE "trading_pairs"(
    "pid" bigint DEFAULT nextval('pair_id_seq') NOT NULL,
    "base_symbol" text NOT NULL REFERENCES coins(symbol),
    "quote_symbol" text NOT NULL REFERENCES coins(symbol),
    UNIQUE(base_symbol,quote_symbol),
    CONSTRAINT trading_pairs_pkey PRIMARY KEY (pid)
);

CREATE TABLE "orders"(
    "oid" bigint DEFAULT nextval('order_id_seq') NOT NULL,
    "uid" bigint NOT NULL REFERENCES users(uid),
    "pid" bigint NOT NULL REFERENCES trading_pairs(pid),
    "side" text NOT NULL CHECK(side IN('BUY','SELL')),
    "price" NUMERIC NOT NULL,
    "amount" NUMERIC NOT NULL,
    "status" text NOT NULL CHECK(status IN ('OPEN','FILLED','CANCELLED')),
    "creation_date" TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT orders_pkey PRIMARY KEY (oid)
);

CREATE TABLE "trades"(
    "tid" bigint DEFAULT nextval('trade_id_seq') NOT NULL,
    "buy_order_id" bigint NOT NULL REFERENCES orders(oid),
    "sell_order_id" bigint NOT NULL REFERENCES orders(oid),
    "pid" bigint NOT NULL REFERENCES trading_pairs(pid),
    "price" NUMERIC NOT NULL,
    "amount" NUMERIC NOT NULL,
    "executed_at" TIMESTAMP DEFAULT now() NOT NULL,
    CONSTRAINT trades_pkey PRIMARY KEY (tid)
);

CREATE TABLE "balances"(
    "uid" bigint NOT NULL REFERENCES users(uid),
    "symbol" text NOT NULL REFERENCES coins(symbol),
    "amount" NUMERIC DEFAULT 0 NOT NULL,
    CONSTRAINT balances_pkey PRIMARY KEY (uid,symbol)
);