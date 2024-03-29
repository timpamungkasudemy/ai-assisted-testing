CREATE TABLE IF NOT EXISTS products (
  product_uuid UUID PRIMARY KEY,
  name TEXT NOT NULL,
  manufacturer TEXT,
  price DOUBLE PRECISION NOT NULL,
  description TEXT,
  stock_keeping_unit TEXT NOT NULL UNIQUE,
  active BOOLEAN NOT NULL DEFAULT FALSE,
  created_by TEXT NOT NULL,
  updated_by TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
