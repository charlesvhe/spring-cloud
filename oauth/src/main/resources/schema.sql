DROP TABLE IF EXISTS user_details;
DROP TABLE IF EXISTS client_details;


CREATE TABLE user_details (
    id INT NOT NULL AUTO_INCREMENT,
    username VARCHAR(64) NOT NULL,
    password VARCHAR(64) NOT NULL,
    authority VARCHAR(256) NOT NULL,
    status INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_details (username)
);

CREATE TABLE client_details (
    id INT NOT NULL AUTO_INCREMENT,
    client_id VARCHAR(64) NOT NULL,
    client_secret VARCHAR(64) NOT NULL,
    scope VARCHAR(256) NOT NULL,
    grant_types VARCHAR(256) NOT NULL,
    redirect_uri VARCHAR(512) NOT NULL,
    status INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_client_details (client_id)
);