CREATE TABLE IF NOT EXISTS dna
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    dna  VARCHAR(400) NOT NULL,
    type VARCHAR(6),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS statistic
(
    id               BIGINT NOT NULL,
    count_mutant_dna BIGINT NOT NULL,
    count_human_dna  BIGINT NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO statistic VALUES (1, 0, 0);