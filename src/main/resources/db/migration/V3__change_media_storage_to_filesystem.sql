ALTER TABLE review_media DROP COLUMN media_data;
ALTER TABLE review_media ADD COLUMN file_path VARCHAR(255) NOT NULL;
ALTER TABLE review_media ADD COLUMN file_name VARCHAR(255) NOT NULL;