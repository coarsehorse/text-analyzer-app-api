CREATE TABLE tasks
(
    id                  INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    file_path           VARCHAR(300) NOT NULL,
    status              VARCHAR(20) NOT NULL,
    created_date        TIMESTAMP WITH TIME ZONE NOT NULL,
    last_modified_date  TIMESTAMP WITH TIME ZONE NOT NULL
);
