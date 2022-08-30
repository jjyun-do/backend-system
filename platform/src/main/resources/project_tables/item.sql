create TABLE "items"
(
    "revision_id"   INT             NOT NULL,
    "task_id"       VARCHAR(320)    NOT NULL,
    "id"            VARCHAR(320)    NOT NULL,
    "contents"      JSONB           NOT NULL,
    "type"          VARCHAR(20)       NOT NULL,
    "order"         INT             NOT NULL,
    PRIMARY KEY ("revision_id", "task_id", "id")
);

CREATE INDEX items_revision_id_index ON items (revision_id);
CREATE INDEX items_task_id_index ON items (task_id);
