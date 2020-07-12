CREATE SCHEMA IF NOT EXISTS test_data_provider;
CREATE TABLE if not exists test_data_provider.omni (
                                                       id UUID NOT NULL,
                                                       data_type TEXT NOT NULL,
                                                       omni TEXT NOT NULL,
                                                       created_on timestamp with time zone NOT NULL
);

CREATE TABLE if not exists test_data_provider.data_type (
                                                           id UUID NOT NULL,
                                                           name TEXT NOT NULL,
                                                           category TEXT,
                                                           minimum_red integer,
                                                           minimum_yellow integer,
                                                           minimum_green integer,
                                                           created_on timestamp with time zone NOT NULL
);

