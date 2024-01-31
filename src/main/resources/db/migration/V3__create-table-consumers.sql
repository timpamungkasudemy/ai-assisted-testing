CREATE TABLE IF NOT EXISTS consumers (
  customer_uuid UUID PRIMARY KEY,
  full_name TEXT NOT NULL,
  email TEXT NOT NULL UNIQUE,
  birth_date DATE NOT NULL,
  phone_number TEXT NOT NULL UNIQUE,
  member_number TEXT NOT NULL UNIQUE,
  created_by UUID NOT NULL,
  updated_by UUID NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);