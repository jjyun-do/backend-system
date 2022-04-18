create TABLE "heartrates"
(
    "id"         SERIAL      NOT NULL,
    "user_id"    SERIAL      NOT NULL,
    "time"       timestamptz NOT NULL,
    "bpm"        INT8     NOT NULL,
    PRIMARY KEY ("id")
);
