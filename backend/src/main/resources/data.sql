UPDATE hibernate_sequence SET next_val=99 LIMIT 1;

INSERT INTO user(id,first_Name,last_Name) VALUES (1, 'Juan', 'Antonio');
INSERT INTO user(id,first_Name,last_Name) VALUES (2, 'Rosa', 'Margarita');
INSERT INTO user(id,first_Name,last_Name) VALUES (3, 'Antonio', 'Pedro');
INSERT INTO user(id,first_Name,last_Name) VALUES (4, 'Maria', 'Ana');
INSERT INTO user(id,first_Name,last_Name) VALUES (5, 'Juan', 'Carlos');
INSERT INTO user(id,first_Name,last_Name) VALUES (6, 'Juan', 'Jose');
INSERT INTO user(id,first_Name,last_Name) VALUES (7, 'Juan', 'Luis');
INSERT INTO user(id,first_Name,last_Name) VALUES (8, 'Juan', 'Manuel');
INSERT INTO user(id,first_Name,last_Name) VALUES (9, 'Juan', 'Pablo');
INSERT INTO user(id,first_Name,last_Name) VALUES (10, 'Juan', 'Ricardo');

INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (1,'Descripccion',10.0,'Evento',1, "2022-04-23T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (2,'Descripccion',10.0,'Evento',2, "2022-04-21T20:26:41.036121","2022-05-25T20:39:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (3,'Descripccion',10.0,'Evento',3, "2022-04-22T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (4,'Descripccion',10.0,'Evento',4, "2022-04-23T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (5,'Descripccion',10.0,'Evento',5, "2022-04-24T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (6,'Descripccion',10.0,'Evento',6, "2022-04-25T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (7,'Descripccion',10.0,'Evento',7, "2022-04-26T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (8,'Descripccion',10.0,'Evento',8, "2022-04-27T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (9,'Descripccion',10.0,'Evento',9, "2022-04-28T20:26:41.036121","2022-05-23T20:26:41.036121");
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (10,'Descripccion',10.0,'Evento',10, "2022-04-29T20:26:41.036121","2022-05-23T20:26:41.036121");

INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (1, "2022-04-23T20:26:41.036121", 10.0, 1, 1, 2);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (2, "2022-04-23T20:26:41.036121", 10.0, 1, 1, 3);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (3, "2022-04-23T20:26:41.036121", 10.0, 1, 1, 4);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (4, "2022-04-23T20:26:41.036121", 10.0, 1, 1, 5);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (5, "2022-04-23T20:26:41.036121", 10.0, 1, 1, 6);

INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (6, "2022-04-23T20:26:41.036121", 10.0, 1, 2, 7);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (7, "2022-04-23T20:26:41.036121", 10.0, 1, 2, 8);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (8, "2022-04-23T20:26:41.036121", 10.0, 1, 2, 9);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (9, "2022-04-23T20:26:41.036121", 10.0, 1, 2, 10);

INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (10, "2022-04-23T20:26:41.036121", 10.0, 1, 3, 7);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (11, "2022-04-23T20:26:41.036121", 10.0, 1, 3, 8);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (12, "2022-04-23T20:26:41.036121", 10.0, 1, 3, 9);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (13, "2022-04-23T20:26:41.036121", 10.0, 1, 3, 10);

INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (15, "2022-04-23T20:26:41.036121", 10.0, 1, 4, 1);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (16, "2022-04-23T20:26:41.036121", 10.0, 1, 4, 2);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (17, "2022-04-23T20:26:41.036121", 10.0, 1, 4, 3);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (18, "2022-04-23T20:26:41.036121", 10.0, 1, 4, 4);
INSERT INTO participation(id, buy_date, price, ticket, event_id, user_id) VALUES (19, "2022-04-23T20:26:41.036121", 10.0, 1, 4, 5);

INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (1, true, 'Sponsor', 1, 1, 2);
INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (2, true, 'Sponsor', 1, 1, 3);
INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (3, true, 'Sponsor', 1, 1, 4);

INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (4, true, 'Sponsor', 1, 2, 5);
INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (5, true, 'Sponsor', 1, 2, 6);
INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (6, true, 'Sponsor', 1, 2, 7);

INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (7, true, 'Sponsor', 1, 3, 5);
INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (8, true, 'Sponsor', 1, 3, 6);
INSERT INTO sponsorship(id, is_accepted, name, quantity, event_id, user_id) VALUES (9, true, 'Sponsor', 1, 3, 7);

INSERT INTO location(id, owner_id, name,price, latitude, longitude, description) VALUES (1,1,"Nombre 1",500.0,0,1,"Descripcion");
INSERT INTO location(id, owner_id,name,price, latitude, longitude, description) VALUES (2,2,"Nombre 2",412.0,1,0,"Descripcion 2 y algun texto de prueba");
INSERT INTO location(id, owner_id,name,price, latitude, longitude, description) VALUES (3,3,"Nombre 3",412.0,1,1,"Descripcion 3");
INSERT INTO location(id, owner_id,name,price, latitude, longitude, description) VALUES (4,4,"Nombre 4",412.0,0,0,"Descripcion 4");
