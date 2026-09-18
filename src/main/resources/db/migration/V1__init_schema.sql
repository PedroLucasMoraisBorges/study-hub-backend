-- ============================================================
-- study-hub — schema inicial
-- created_at/updated_at são gerenciados pelo Hibernate (@CreationTimestamp/@UpdateTimestamp),
-- por isso não há default nem trigger de banco para essas colunas.
-- ============================================================

CREATE TABLE colors (
  id          BIGSERIAL PRIMARY KEY,
  slug        VARCHAR(20) NOT NULL UNIQUE,
  hexadecimal VARCHAR(7)  NOT NULL
);

INSERT INTO colors (slug, hexadecimal) VALUES
  ('accent', '#2f6f4f'),
  ('amber',  '#c9822a'),
  ('blue',   '#3a5aab'),
  ('rose',   '#a13d63'),
  ('violet', '#6a4ba1'),
  ('teal',   '#2a8a82'),
  ('coral',  '#c15b3f'),
  ('slate',  '#556273');

CREATE TABLE topics (
  id       BIGSERIAL PRIMARY KEY,
  name     VARCHAR(100) NOT NULL,
  fk_color BIGINT NOT NULL REFERENCES colors(id),
  icon     VARCHAR(20) NOT NULL
    CHECK (icon IN ('code', 'book', 'star', 'flag', 'target', 'bulb'))
);

CREATE INDEX idx_topics_fk_color ON topics(fk_color);

CREATE TABLE files (
  id          BIGSERIAL PRIMARY KEY,
  fk_topic    BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
  name        VARCHAR(200) NOT NULL,
  description TEXT NOT NULL DEFAULT '',
  type        VARCHAR(10) NOT NULL
    CHECK (type IN ('doc', 'cards', 'mindmap', 'slides')),
  metadata    JSONB NOT NULL DEFAULT '{}',
  created_at  TIMESTAMPTZ NOT NULL,
  updated_at  TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_files_fk_topic ON files(fk_topic);
CREATE INDEX idx_files_type ON files(type);

-- ---------- type = 'doc' ----------
CREATE TABLE document_blocks (
  id                  BIGSERIAL PRIMARY KEY,
  fk_file             BIGINT NOT NULL REFERENCES files(id) ON DELETE CASCADE,
  "order"             INTEGER NOT NULL,
  type                VARCHAR(10) NOT NULL
    CHECK (type IN ('h1', 'h2', 'h3', 'paragraph', 'bullet', 'link', 'image', 'file', 'hr')),
  content_text        TEXT,
  link_url            TEXT,
  image_file          TEXT,
  document_file       TEXT,
  document_file_name  VARCHAR(255)
);

CREATE INDEX idx_document_blocks_fk_file_order ON document_blocks(fk_file, "order");

-- ---------- type = 'cards' ----------
CREATE TABLE flashcards (
  id      BIGSERIAL PRIMARY KEY,
  fk_file BIGINT NOT NULL REFERENCES files(id) ON DELETE CASCADE,
  "order" INTEGER NOT NULL,
  front   TEXT NOT NULL,
  back    TEXT NOT NULL
);

CREATE INDEX idx_flashcards_fk_file_order ON flashcards(fk_file, "order");

-- ---------- type = 'slides' ----------
CREATE TABLE slides (
  id            BIGSERIAL PRIMARY KEY,
  fk_file       BIGINT NOT NULL REFERENCES files(id) ON DELETE CASCADE,
  "order"       INTEGER NOT NULL,
  title         TEXT NOT NULL DEFAULT '',
  layout        VARCHAR(10) NOT NULL DEFAULT 'single'
    CHECK (layout IN ('single', 'two', 'three')),
  title_align_h VARCHAR(10) NOT NULL DEFAULT 'center'
    CHECK (title_align_h IN ('left', 'center', 'right')),
  title_align_v VARCHAR(10) NOT NULL DEFAULT 'center'
    CHECK (title_align_v IN ('top', 'center', 'bottom')),
  bg_image      TEXT
);

CREATE INDEX idx_slides_fk_file_order ON slides(fk_file, "order");

CREATE TABLE slide_columns (
  id       BIGSERIAL PRIMARY KEY,
  fk_slide BIGINT NOT NULL REFERENCES slides(id) ON DELETE CASCADE,
  "order"  INTEGER NOT NULL,
  text     TEXT NOT NULL DEFAULT '',
  align    VARCHAR(10) NOT NULL DEFAULT 'left'
    CHECK (align IN ('left', 'center', 'right')),
  image    TEXT
);

CREATE INDEX idx_slide_columns_fk_slide_order ON slide_columns(fk_slide, "order");

-- ---------- type = 'mindmap' ----------
CREATE TABLE mindmap_nodes (
  id              BIGSERIAL PRIMARY KEY,
  fk_file         BIGINT NOT NULL REFERENCES files(id) ON DELETE CASCADE,
  fk_parent       BIGINT REFERENCES mindmap_nodes(id) ON DELETE CASCADE,
  label           TEXT NOT NULL DEFAULT '',
  description     TEXT NOT NULL DEFAULT '',
  pos_x           DOUBLE PRECISION NOT NULL,
  pos_y           DOUBLE PRECISION NOT NULL,
  width           DOUBLE PRECISION NOT NULL,
  height          DOUBLE PRECISION NOT NULL,
  shape           VARCHAR(10) NOT NULL DEFAULT 'rectangle'
    CHECK (shape IN ('rectangle', 'circle', 'square', 'diamond')),
  fk_color        BIGINT REFERENCES colors(id),
  border_style    VARCHAR(10) NOT NULL DEFAULT 'none'
    CHECK (border_style IN ('none', 'solid', 'dashed', 'dotted')),
  fk_border_color BIGINT REFERENCES colors(id),
  font_size       VARCHAR(10) NOT NULL DEFAULT 'medium'
    CHECK (font_size IN ('small', 'medium', 'large'))
);

CREATE INDEX idx_mindmap_nodes_fk_file ON mindmap_nodes(fk_file);
CREATE INDEX idx_mindmap_nodes_fk_parent ON mindmap_nodes(fk_parent);
