insert into user_detail (email, first_name, last_name, password, username, formatted_address, lat, lng, is_disabled)
select 'admin@admin.hr',
       'Admin',
       'Adminović',
       '$2a$10$BQS5/qhYmKFtJtaGqUjUG.lqjPUXtoZ9IQJj1J5KKRK4/.zY4wfbC',
       'admin',
       'Zagreb, Hrvatska',
       45.8150108,
       15.9819189,
       false
where not exists(select * from user_detail where username = 'admin');


insert into user_roles (user_id, roles)
select 1, 'ADMIN'
where not exists(select * from user_roles where user_id = 1 and roles = 'ADMIN');
insert into user_roles (user_id, roles)
select 1, 'USER'
where not exists(select * from user_roles where user_id = 1 and roles = 'USER');


