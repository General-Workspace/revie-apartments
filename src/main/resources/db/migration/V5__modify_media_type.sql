ALTER TABLE review_media
DROP CONSTRAINT review_media_media_type_check;

ALTER TABLE review_media
    ADD CONSTRAINT review_media_media_type_check
        CHECK (media_type IN ('IMAGE', 'VIDEO', 'image', 'video'));