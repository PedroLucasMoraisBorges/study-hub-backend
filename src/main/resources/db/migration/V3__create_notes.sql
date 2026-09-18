-- ============================================================
-- Notas (post-its)
-- fk_file NULL = nota avulsa, presa apenas ao tópico.
-- Apagar o tópico ou o documento apaga as notas (ON DELETE CASCADE).
-- created_at é gerenciado pelo Hibernate (@CreationTimestamp), sem default de banco.
-- ============================================================

CREATE TABLE notes (
  id         BIGSERIAL PRIMARY KEY,
  fk_topic   BIGINT NOT NULL REFERENCES topics(id) ON DELETE CASCADE,
  fk_file    BIGINT REFERENCES files(id) ON DELETE CASCADE,
  text       TEXT,
  color      VARCHAR(10) NOT NULL DEFAULT 'butter'
    CHECK (color IN ('butter', 'sage', 'peach', 'lavender', 'mist')),
  created_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_notes_fk_topic ON notes(fk_topic);
CREATE INDEX idx_notes_fk_file ON notes(fk_file);
