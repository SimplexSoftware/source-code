ALTER TABLE meeting ADD COLUMN wasNotification boolean;
ALTER TABLE meeting ALTER COLUMN wasNotification SET NOT NULL;
