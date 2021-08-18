CREATE DATABASE bd_app_person;

CREATE TABLE public.person (
                               id int8 NOT NULL,
                               birth date NOT NULL,
                               email varchar(255) NOT NULL,
                               full_name varchar(255) NOT NULL,
                               gender varchar(255) NULL,
                               id_identificacion varchar(255) NOT NULL,
                               CONSTRAINT person_pkey PRIMARY KEY (id),
                               CONSTRAINT uk_1ordiocbb25y2vvqj5lj9nxiu UNIQUE (id_identificacion),
                               CONSTRAINT uk_fwmwi44u55bo4rvwsv0cln012 UNIQUE (email)
);

CREATE TABLE public.children_by_parents (
                                            id int8 NOT NULL,
                                            father int4 NULL,
                                            mother int4 NULL,
                                            child int8 NULL,
                                            CONSTRAINT children_by_parents_pkey PRIMARY KEY (id),
                                            CONSTRAINT fkf9roppyx8vj2u0drrhfiihnft FOREIGN KEY (child) REFERENCES public.person(id)
);

INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(33,'2021-08-16','juan@gmail','Juan Pablo Florez','masculino','1088330322');

INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(34,'2021-08-21','betruji@gma','Beatriz Elena Florez','femenino','41325689');

INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(35,'2021-08-17','santi@gmail','Santiago Gaviria','masculino','1089256789');

INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(36,'2021-08-16','felipe@gavi','Felipe Gaviria','masculino','1004602753');

INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(37,'2021-08-19','aleja@gmail','Alejandra Zapata','femenino','1098621123');


INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(38,'1995-10-12','karla@softl','Carla Montoya','femenino','1089365478');


INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(39,'2002-10-01','vargas@gmai','Camila Vargas','femenino','125896325');

INSERT INTO public.person
(id, birth, email, full_name, gender, id_identificacion)
VALUES(40,'2021-08-16','canda@audif','Jessica Candamil','femenino','14785236');


INSERT INTO public.children_by_parents
(id, father, mother, child)
VALUES(41,33,null,36);

INSERT INTO public.children_by_parents
(id, father, mother, child)
VALUES(42,33,null,37);

INSERT INTO public.children_by_parents
(id, father, mother, child)
VALUES(43,null,34,33);