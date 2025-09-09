DROP TABLE IF EXISTS "coins";

CREATE TABLE "coins"(
    "symbol" text NOT NULL,
    "name" text NOT NULL,
    "type" text,
    "contract_address" text,
    "network" text,
    "logo_url" text,
    "description" text,

    CONSTRAINT "coin_pkey" PRIMARY KEY ("symbol")
);