CREATE TABLE parser (
    id SERIAL PRIMARY KEY,
    depCode varchar(20) NOT NULL,
    depJob varchar(100) NOT NULL,
    description varchar(255),
    UNIQUE(depCode, depJob)
);