INSERT INTO gift_certificates(name, description, price, duration, create_date, last_update_date)
VALUES ('goldie''s gym', '5 free visits', 9.99, 7, '2020-08-29T06:12:15.156', '2020-08-29T06:12:15.156'),
       ('Kfc birthday', '50% off', 5.55, 16, '2019-08-29T06:12:15.156', '2019-08-29T06:12:15.156'),
       ('Silver screen', 'one film', 4.99, 9, '2018-08-29T06:12:15.156', '2018-08-29T06:12:15.156');

INSERT INTO tags(tag_name)
VALUES ('gym'),
       ('cheap'),
       ('rest');

INSERT INTO gift_certificates_tags(gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (3, 3),
       (3, 2);

