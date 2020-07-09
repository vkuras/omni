CREATE SCHEMA IF NOT EXISTS test_data_provider;
CREATE TABLE if not exists test_data_provider.omni (
                                                       id UUID NOT NULL,
                                                       data_type TEXT NOT NULL,
                                                       omni TEXT NOT NULL,
                                                       created_on timestamp with time zone NOT NULL
);

CREATE TABLE if not exists test_data_provider.category (
                                                           id UUID NOT NULL,
                                                           name TEXT NOT NULL,
                                                           data_types TEXT,
                                                           limit_red integer,
                                                           limit_yellow integer,
                                                           limit_green integer,
                                                           oldest_omni timestamp with time zone,
                                                           created_on timestamp with time zone NOT NULL
);

