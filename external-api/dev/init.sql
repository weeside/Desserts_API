create table question (
    id bigint auto_increment,
    created_at datetime,
    updated_at datetime,
    contents clob,
    no integer,
    yes integer,
    primary key (id)
)

create table result (
    id bigint auto_increment,
    point integer,
    primary key (id)
)