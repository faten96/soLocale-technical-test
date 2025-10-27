CREATE TABLE campaign (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    target_impression BIGINT NOT NULL,
    target_budget DOUBLE PRECISION NOT NULL,
    cpm DOUBLE PRECISION NOT NULL,
    current_impression BIGINT DEFAULT 0,
    current_cost DOUBLE PRECISION DEFAULT 0
);

INSERT INTO campaign (name, target_impression, target_budget, cpm, current_impression, current_cost)
VALUES
  ('Summer Sale', 10000, 5000, 2.5, 500, 1.25),
  ('Winter Promo', 20000, 10000, 2.0, 1000, 2.0),
  ('Back to School', 15000, 8000, 3.0, 700, 2.1);
