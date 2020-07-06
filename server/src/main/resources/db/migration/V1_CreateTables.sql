CREATE SCHEMA if not exists test-data-provider;
CREATE TABLE if not exists test-data-povide.omni (
 id UUID NOT NULL,
 data_type TEXT NOT NULL,
 omni TEXT NOT NULL,
 createdOn timestamp without time zone NOT NULL
)
CREATE TABLE if not exists test-data-povide.category (
 id UUID NOT NULL,
 name TEXT NOT NULL,
 data_types TEXT,
 limit_red integer,
 limit_yellow integer,
 limit_green integer,
 oldest_omni timestamp without time zone
 createdOn timestamp without time zone NOT NULL
)