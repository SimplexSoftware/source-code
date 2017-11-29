ALTER TABLE meeting ADD COLUMN wasNotification boolean;
UPDATE meeting set wasNotification = false;
ALTER TABLE meeting ALTER COLUMN wasNotification SET NOT NULL;
