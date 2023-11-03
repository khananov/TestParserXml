CREATE DATABASE parser;

CREATE TABLE dep_data (
    id SERIAL PRIMARY KEY,
    dep_code varchar(20) NOT NULL,
    dep_job varchar(100) NOT NULL,
    description varchar(255),
    UNIQUE(dep_code, dep_job)
);

INSERT INTO dep_data (dep_code, dep_job, description) VALUES ('1', '1', 'description1');
INSERT INTO dep_data (dep_code, dep_job, description) VALUES ('1', '2', 'description2');
INSERT INTO dep_data (dep_code, dep_job, description) VALUES ('1', '3', 'description3');
INSERT INTO dep_data (dep_code, dep_job, description) VALUES ('2', '1', 'description4');
INSERT INTO dep_data (dep_code, dep_job, description) VALUES ('2', '2', 'description5');
INSERT INTO dep_data (dep_code, dep_job, description) VALUES ('2', '3', 'description6');
