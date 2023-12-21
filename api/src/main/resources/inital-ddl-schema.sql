
CREATE TABLE public.tags
(
    crated_date timestamp(6) without time zone,
    id bigint NOT NULL DEFAULT nextval('tags_id_seq'::regclass),
    updated_date timestamp(6) without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT tags_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.tags
    OWNER to postgres;

CREATE TABLE public.gift_certificates
(
    duration integer,
    price numeric(38,2),
    crated_date timestamp(6) without time zone,
    id bigint NOT NULL DEFAULT nextval('gift_certificates_id_seq'::regclass),
    updated_date timestamp(6) without time zone,
    description character varying(255) COLLATE pg_catalog."default",
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT gift_certificates_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.gift_certificates
    OWNER to postgres;


CREATE TABLE public.gift_certificates_tags
(
    gift_certificate_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    CONSTRAINT gift_certificates_tags_pkey PRIMARY KEY (gift_certificate_id, tag_id),
    CONSTRAINT fk18qpjtn8x8ia2vtfxakaldbto FOREIGN KEY (gift_certificate_id)
        REFERENCES public.gift_certificates (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fkmtf8kx7hwi6d3gmh4vqrv8in FOREIGN KEY (tag_id)
        REFERENCES public.tags (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.gift_certificates_tags
    OWNER to postgres;


CREATE TABLE public.orders
(
    purchasecost numeric(38,2),
    crated_date timestamp(6) without time zone,
    giftcertificate_id bigint,
    id bigint NOT NULL DEFAULT nextval('orders_id_seq'::regclass),
    updated_date timestamp(6) without time zone,
    user_id bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id)
        REFERENCES public.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk936rfelcudmnw7qqbfgnrwfjk FOREIGN KEY (giftcertificate_id)
        REFERENCES public.gift_certificates (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

    TABLESPACE pg_default;

ALTER TABLE public.orders
    OWNER to postgres;

CREATE TABLE public.users
(
    crated_date timestamp(6) without time zone,
    id bigint NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    updated_date timestamp(6) without time zone,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE public.users
    OWNER to postgres;