ALTER TABLE document_blocks DROP CONSTRAINT document_blocks_type_check;

ALTER TABLE document_blocks ADD CONSTRAINT document_blocks_type_check
    CHECK (type IN ('h1', 'h2', 'h3', 'paragraph', 'bullet', 'link', 'image', 'file', 'hr', 'code', 'quote'));

ALTER TABLE document_blocks ADD COLUMN language VARCHAR(30);
