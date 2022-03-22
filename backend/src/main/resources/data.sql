UPDATE hibernate_sequence SET next_val=3 LIMIT 1;
INSERT INTO user(id,first_Name,last_Name) VALUES (1, 'Juan', 'Antonio');
INSERT INTO user(id,first_Name,last_Name) VALUES (2, 'Rosa', 'Margarita');
INSERT INTO event(id,description,price,title,user_id) VALUES (1,'Descripcion',10.0,'Evento',1);
INSERT INTO location(id, owner_id, location, price,name,description) VALUES (1,1,"Direccion",500.0,"Nombre","Descripcion");

