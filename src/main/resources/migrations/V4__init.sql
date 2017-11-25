ALTER TABLE speaker ADD COLUMN email character varying(255);

ALTER TABLE speaker ADD COLUMN subscriber boolean;
ALTER TABLE speaker ALTER COLUMN subscriber SET NOT NULL;

