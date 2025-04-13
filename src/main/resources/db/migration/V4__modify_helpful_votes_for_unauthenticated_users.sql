-- V4__modify_helpful_votes_for_unauthenticated_users.sql
DO $$
BEGIN
    -- Check if constraint exists before trying to drop it
    IF EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'fk_helpful_vote_user'
        AND table_name = 'helpful_votes'
    ) THEN
ALTER TABLE helpful_votes DROP CONSTRAINT fk_helpful_vote_user;
END IF;

    -- Check if column exists before trying to drop it
    IF EXISTS (
        SELECT 1
        FROM information_schema.columns
        WHERE table_name = 'helpful_votes'
        AND column_name = 'user_id'
    ) THEN
ALTER TABLE helpful_votes DROP COLUMN user_id;
END IF;
END $$;

-- Add new column and index
ALTER TABLE helpful_votes ADD COLUMN voter_session_id VARCHAR(255) NOT NULL DEFAULT 'temp';
UPDATE helpful_votes SET voter_session_id = gen_random_uuid()::text WHERE voter_session_id = 'temp';
ALTER TABLE helpful_votes ALTER COLUMN voter_session_id DROP DEFAULT;

-- Drop old index if it exists
DROP INDEX IF EXISTS helpful_votes_review_id_user_id_idx;

-- Create new index
CREATE UNIQUE INDEX IF NOT EXISTS helpful_votes_review_id_session_id_idx ON helpful_votes (review_id, voter_session_id);