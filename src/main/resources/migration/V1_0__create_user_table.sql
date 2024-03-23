CREATE TABLE IF NOT EXISTS `user` (
    id                    CHAR(36) PRIMARY KEY NOT NULL,
    name                  VARCHAR(255) NOT NULL,
    username              VARCHAR(20) NOT NULL,
    email                 VARCHAR(255) NOT NULL,
    role                  VARCHAR(50) NOT NULL,
    creationDate          TIMESTAMP,
    CONSTRAINT unique_username_email UNIQUE (username, email)
) ENGINE=InnoDB;