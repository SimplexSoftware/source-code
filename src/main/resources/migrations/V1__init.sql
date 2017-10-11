CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE meeting (
    id bigint NOT NULL,
    date timestamp without time zone
);
CREATE TABLE meeting_report (
    meeting_id bigint NOT NULL,
    reports_id bigint NOT NULL
);

CREATE TABLE report (
    id bigint NOT NULL,
    title character varying(255),
    author_socialid character varying(255),
    meeting_id bigint,
    likecounter integer
);

CREATE TABLE report_wholikedit (
    report_id bigint NOT NULL,
    wholikedit boolean,
    wholikedit_key character varying(255) NOT NULL
);
CREATE TABLE speaker (
    socialid character varying(255) NOT NULL,
    fio character varying(255),
    raiting double precision NOT NULL
);
CREATE TABLE tag (
    id bigint NOT NULL
);

ALTER TABLE ONLY meeting
    ADD CONSTRAINT meeting_pkey PRIMARY KEY (id);

ALTER TABLE ONLY report
    ADD CONSTRAINT report_pkey PRIMARY KEY (id);

ALTER TABLE ONLY report_wholikedit
    ADD CONSTRAINT report_wholikedit_pkey PRIMARY KEY (report_id, wholikedit_key);

ALTER TABLE ONLY speaker
    ADD CONSTRAINT speaker_pkey PRIMARY KEY (socialid);

ALTER TABLE ONLY tag
    ADD CONSTRAINT tag_pkey PRIMARY KEY (id);

ALTER TABLE ONLY meeting_report
    ADD CONSTRAINT uk_hmhdixx1bnojh5fm8lb0d20t7 UNIQUE (reports_id);

ALTER TABLE ONLY report_wholikedit
    ADD CONSTRAINT fk1aumy91k1gmstt5ud3d1pxxp3 FOREIGN KEY (wholikedit_key) REFERENCES speaker(socialid);

ALTER TABLE ONLY report
    ADD CONSTRAINT fk2bu96g2xylqbuh6xlxfhnpb26 FOREIGN KEY (meeting_id) REFERENCES meeting(id);

ALTER TABLE ONLY meeting_report
    ADD CONSTRAINT fk3q20ttcwtms2esyn6bxwxbm66 FOREIGN KEY (meeting_id) REFERENCES meeting(id);

ALTER TABLE ONLY report_wholikedit
    ADD CONSTRAINT fkdl8yt3or5j63smeasjwsro3dw FOREIGN KEY (report_id) REFERENCES report(id);

ALTER TABLE ONLY report
    ADD CONSTRAINT fkhj3a73jm038m2ao1k98c2utq8 FOREIGN KEY (author_socialid) REFERENCES speaker(socialid);


ALTER TABLE ONLY meeting_report
    ADD CONSTRAINT fkn2toim5vcewaaaf6fuhm0n3qi FOREIGN KEY (reports_id) REFERENCES report(id);
