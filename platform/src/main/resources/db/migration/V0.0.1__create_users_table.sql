create TABLE "users"
(
    "id"         SERIAL              NOT NULL,
    "email"      VARCHAR(320) UNIQUE NOT NULL,
    "sub"        VARCHAR(255)        NOT NULL,
    "provider"   VARCHAR(32)         NOT NULL,
    "created_at" timestamptz         NOT NULL,
    "deleted_at" timestamptz,
    PRIMARY KEY ("id")
);

CREATE UNIQUE INDEX email_index ON users (email);
