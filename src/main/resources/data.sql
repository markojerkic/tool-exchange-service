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


CREATE OR REPLACE FUNCTION calculate_distance(lat1 double precision, lon1 double precision, lat2 double precision, lon2 double precision)
    RETURNS double precision AS '
DECLARE
    dist float = 0;
    radlat1 float;
    radlat2 float;
    theta float;
    radtheta float;
BEGIN
    IF lat1 = lat2 OR lon1 = lon2
    THEN RETURN dist;
    ELSE
        radlat1 = pi() * lat1 / 180;
        radlat2 = pi() * lat2 / 180;
        theta = lon1 - lon2;
        radtheta = pi() * theta / 180;
        dist = sin(radlat1) * sin(radlat2) + cos(radlat1) * cos(radlat2) * cos(radtheta);

        IF dist > 1 THEN dist = 1; END IF;

        dist = acos(dist);
        dist = dist * 180 / pi();
        dist = dist * 60 * 1.1515;

        dist = dist * 1.609344;

        RETURN dist;
    END IF;
END;
' LANGUAGE plpgsql;;