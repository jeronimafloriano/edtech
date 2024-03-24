CREATE TABLE IF NOT EXISTS `course` (
    id                  CHAR(36) PRIMARY KEY NOT NULL,
    name                VARCHAR(255) NOT NULL,
    code                VARCHAR(10) NOT NULL,
    instructor_id       CHAR(36) NOT NULL,
    description         TEXT NOT NULL,
    status              VARCHAR(50) NOT NULL,
    creation_date       TIMESTAMP,
    inactivation_date   TIMESTAMP,
    FOREIGN KEY (instructor_id) REFERENCES `user`(id)
) ENGINE=InnoDB;