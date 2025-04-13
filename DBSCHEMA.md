# Possible DB schema for the project

## Users table
```sql
CREATE TABLE users (
    id VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

## Apartments table
```sql
CREATE TABLE apartments (
    id VARCHAR(50) PRIMARY KEY,
    address TEXT NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100),
    country VARCHAR(100) NOT NULL,
    zip_code VARCHAR(20),
    user_id VARCHAR(50) NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```

## Reviews table
```sql
CREATE TABLE reviews (
    id VARCHAR(50) PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL REFERENCES users(id),
    apartment_id VARCHAR(50) NOT NULL REFERENCES apartments(id),
    landlord_rating INTEGER NOT NULL CHECK (landlord_rating BETWEEN 1 AND 5),
    environment_rating INTEGER NOT NULL CHECK (environment_rating BETWEEN 1 AND 5),
    amenities_rating INTEGER NOT NULL CHECK (amenities_rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```

## Review Media table
```sql
CREATE TABLE review_media (
    id VARCHAR(50) PRIMARY KEY,
    review_id VARCHAR(50) NOT NULL REFERENCES reviews(id),
    media_type VARCHAR(20) NOT NULL CHECK (media_type IN ('IMAGE', 'VIDEO')),
    media_data BYTEA NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
```

## Helpful Votes table
```sql
CREATE TABLE helpful_votes (
    id VARCHAR(50) PRIMARY KEY,
    review_id VARCHAR(50) NOT NULL REFERENCES reviews(id),
    user_id VARCHAR(50) NOT NULL REFERENCES users(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(review_id, user_id)
);
```