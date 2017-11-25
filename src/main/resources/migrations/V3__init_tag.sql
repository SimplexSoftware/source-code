ALTER TABLE tag ADD COLUMN rating int;

CREATE TABLE tag_wholikedit (
    tag_id bigint NOT NULL,
    wholikedit boolean,
    wholikedit_key character varying(255) NOT NULL
);

ALTER TABLE ONLY tag_wholikedit
    ADD CONSTRAINT fk7ysoa6lvstpm7phdxydqt9tlu FOREIGN KEY (wholikedit_key) REFERENCES speaker(socialid);

ALTER TABLE ONLY tag_wholikedit
    ADD CONSTRAINT fkq07o6n482nohvrq39uisk98kk FOREIGN KEY (tag_id) REFERENCES tag(id);

ALTER TABLE ONLY tag_wholikedit
    ADD CONSTRAINT tag_wholikedit_pkey PRIMARY KEY (tag_id, wholikedit_key);