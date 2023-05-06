insert into tb_role_group (role_id, create_at, create_id, role_code, role_dc, role_nm, update_at, update_id)
values ('1', now(), '1', 'ADMIN', 'ADMIN', '관리자', now(), '1');

insert into tb_user (user_id, create_at, create_id, password, update_at, update_id, use_yn, username)
values
    ('1', now(), '1', '$2a$10$mZ2HvRc/KkQwKXidN6KzCuBHuoAPMpSj/Pq7.YKK9k7I/h8xulHay', now(), '1', 'Y', 'admin');

insert into tb_role_mapp (role_group_id, user_id, create_at, create_id, update_at, update_id)
values ('1', '1', now(), '1', now(), '1');