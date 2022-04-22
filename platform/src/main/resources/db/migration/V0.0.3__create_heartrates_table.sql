create TABLE "heartrates"
(
    "id"         SERIAL      NOT NULL,
    "user_id"    INT8      NOT NULL,
    "time"       timestamptz NOT NULL,
    "bpm"        INT8     NOT NULL,
    PRIMARY KEY ("id")
);
