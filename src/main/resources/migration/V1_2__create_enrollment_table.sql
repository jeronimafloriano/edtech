CREATE TABLE IF NOT EXISTS `enrollment` (
    id                  CHAR(36) PRIMARY KEY NOT NULL,
    user_id             CHAR(36) NOT NULL,
    course_id           CHAR(36) NOT NULL,
    enrollment_date     TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES `user`(id)
    FOREIGN KEY (course_id) REFERENCES `course`(id)
) ENGINE=InnoDB;