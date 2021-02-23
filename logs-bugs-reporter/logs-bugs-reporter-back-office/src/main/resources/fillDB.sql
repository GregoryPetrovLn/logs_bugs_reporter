DELETE bugs;
DELETE programmers;

TRUNCATE TABLE bugs RESTART IDENTITY;

insert into programmers (id,name,email) VALUES
(1, 'Sara','sara@gmail.com'),
(2, 'Moshe','moshe@gmail.com');

insert into bugs (
          id,
          description,
          date_open,
          date_close,
          status,
          seriousness,
          opening_method,
          programmer_id
        )
VALUES
(1,'BLOCKING bug description','2020-01-01',null,'ASSIGNED','BLOCKING','MANUAL',1),
(2,'CRITICAL bug description','2021-02-02',null,'OPENED','CRITICAL','AUTOMATIC',1),
(3,'MINOR bug description','2021-03-03','2021-03-10','CLOSED','MINOR','AUTOMATIC',1),
(4,'CRITICAL bug description','2019-04-04',null,'OPENED','CRITICAL','AUTOMATIC',null),
(5,'COSMETIC bug description','2020-05-05','2020-05-12', 'CLOSED', 'COSMETIC','MANUAL',2);