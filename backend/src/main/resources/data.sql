UPDATE hibernate_sequence SET next_val=3 LIMIT 1;
INSERT INTO user(id,first_Name,last_Name) VALUES (1, 'Juan', 'Antonio');
INSERT INTO user(id,first_Name,last_Name) VALUES (2, 'Rosa', 'Margarita');
INSERT INTO event(id,description,price,title,user_id,start_Date,end_Date) VALUES (1,'Descripccion',10.0,'Evento',1, "2022-04-23T20:26:41.036121","2022-05-23T20:26:41.036121");
