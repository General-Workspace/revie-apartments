-- Add column role to user table
ALTER TABLE users
ADD COLUMN user_role VARCHAR(255) NOT NULL DEFAULT 'user';

-- Update existing users to have default role
UPDATE users SET user_role = LOWER(user_role) WHERE user_role IS NULL;

-- Add user_id foreign key to apartment table
ALTER TABLE apartments
ADD COLUMN user_id VARCHAR(255) NOT NULL;

-- Add foreign key constraint
ALTER TABLE apartments
ADD CONSTRAINT fk_apartment_user
FOREIGN KEY (user_id) REFERENCES users(id);