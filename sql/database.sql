create table question
(
    id serial not null
        constraint question_pkey
            primary key,
    correct_answer varchar(128) not null,
    level integer,
    question varchar(256) not null,
    wrong_answer1 varchar(128) not null,
    wrong_answer2 varchar(128) not null,
    wrong_answer3 varchar(128) not null
);

alter table question owner to postgres;

