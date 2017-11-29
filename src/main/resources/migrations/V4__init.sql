ALTER TABLE speaker ADD COLUMN email character varying(255);

ALTER TABLE speaker ADD COLUMN subscriber boolean;
UPDATE speaker set subscriber = false;
ALTER TABLE speaker ALTER COLUMN subscriber SET NOT NULL;

