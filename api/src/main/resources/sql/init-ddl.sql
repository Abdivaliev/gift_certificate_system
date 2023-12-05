DROP TABLE IF EXISTS gift_certificates_tags;
DROP TABLE IF EXISTS tags;
DROP TABLE IF EXISTS gift_certificates;
create table gift_certificates
(
    id               serial         not null primary key,
    name             varchar(64)    not null,
    description      varchar(512),
    price            numeric(16, 2) not null,
    duration         integer        not null,
    create_date      timestamp(64)  not null default now(),
    last_update_date timestamp(64)  not null default now(),
    constraint unique_certificate_name unique (name),
    constraint positive_price check (price > (0)::numeric)
);

create table tags
(
    id       serial      not null primary key,
    tag_name varchar(64) not null,
    constraint unique_tag_name unique (tag_name)
);

create table gift_certificates_tags
(
    gift_certificate_id integer not null references gift_certificates on delete cascade,
    tag_id              integer not null references tags on delete cascade,
    primary key (gift_certificate_id, tag_id)
);