CREATE TABLE dep_data (
    id SERIAL PRIMARY KEY,
    dep_code varchar(20) NOT NULL,
    dep_job varchar(100) NOT NULL,
    description varchar(255),
    UNIQUE(dep_code, dep_job)
);

INSERT INTO dep_data (dep_code, dep_job, description) VALUES
    ('1', '2', 'description1'),
    ('1', '3', 'description2'),
    ('2', '1', 'description3'),
    ('2', '3', 'description4'),
    ('3', '2', 'description5');