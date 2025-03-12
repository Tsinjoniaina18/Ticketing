create database ticketing;

\c ticketing;

create sequence seq_utilisateur start with 1 increment by 1;
create sequence seq_type_siege start with 1 increment by 1;
create sequence seq_config_reservation start with 1 increment by 1;
create sequence seq_modele start with 1 increment by 1;
create sequence seq_ville start with 1 increment by 1;
create sequence seq_avion start with 1 increment by 1;
create sequence seq_vol start with 1 increment by 1;
create sequence seq_config_promotion start with 1 increment by 1;
create sequence seq_reservation start with 1 increment by 1;
create sequence seq_config_enfant start with 1 increment by 1;

create table utilisateur (
    id varchar(255) primary key ,
    nom varchar(100),
    email varchar(100),
    password varchar(50),
    role int
);

create table type_siege (
    id varchar(255) primary key ,
    nom varchar(100)
);

create table config_reservation (
    id varchar(255) primary key ,
    heure_reservation int,
    heure_annulation int
);

create table modele (
    id varchar(255) primary key ,
    nom varchar(100)
);

create table ville (
    id varchar(255) primary key ,
    nom varchar(100)
);

create table avion (
    id varchar(255) primary key ,
    nom varchar(100),
    modele varchar(255),
    business int,
    eco int,
    fabrication date,
    foreign key (modele) references modele(id)
);

create table vol (
    id varchar(255) primary key ,
    id_avion varchar(255),
    decollage timestamp,
    depart varchar(255),
    destination varchar(255),
    business decimal(15,2),
    eco decimal(15,2),
    etat int,
    foreign key (id_avion) references avion(id),
    foreign key (depart) references ville(id),
    foreign key (destination) references ville(id)
);

create table config_promotion (
    id varchar(255) primary key ,
    id_vol varchar(255),
    siege int,
    type_siege varchar(255),
    remise decimal(5,2),
    foreign key (id_vol) references vol(id),
    foreign key (type_siege) references type_siege(id)
);

create table reservation (
    id varchar(255) primary key ,
    id_utilisateur varchar(255),
    passager varchar(255),
    age int,
    id_vol varchar(255),
    date_reservation timestamp,
    siege int,
    type_siege varchar(255),
    promotion decimal(15,2),
    prix decimal(15,2),
    passeport varchar(255),
    passeport_bytes BYTEA,
    foreign key (id_vol) references vol(id),
    foreign key (id_utilisateur) references utilisateur(id),
    foreign key (type_siege) references type_siege(id)
);

create table config_enfant(
    id varchar(255) primary key ,
    age int,
    pourcentage_prix decimal(4, 2)
);

insert into utilisateur values ('UTR' || LPAD(nextval('seq_utilisateur')::text, 5, '0'), 'admin', 'admin@gmail.com', '1234');
insert into utilisateur values ('UTR' || LPAD(nextval('seq_utilisateur')::text, 5, '0'), 'Jean', 'jean@gmail.com', '1234');


insert into type_siege values ('TSG' || LPAD(nextval('seq_type_siege')::text, 5, '0'), 'Business');
insert into type_siege values ('TSG' || LPAD(nextval('seq_type_siege')::text, 5, '0'), 'Eco');


insert into config_reservation values ('CFGR' || LPAD(nextval('seq_config_reservation')::text, 5, '0'), 3, 6);

insert into config_enfant values ('CFGE' || LPAD(nextval('seq_config_enfant')::text, 5, '0'), 12, 80);


INSERT INTO modele (id, nom) VALUES ('MOD' || LPAD(nextval('seq_modele')::text, 5, '0'), 'Boeing 737');
INSERT INTO modele (id, nom) VALUES ('MOD' || LPAD(nextval('seq_modele')::text, 5, '0'), 'Airbus A320');
INSERT INTO modele (id, nom) VALUES ('MOD' || LPAD(nextval('seq_modele')::text, 5, '0'), 'Embraer E190');


INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Paris');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Marseille');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Lyon');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Toulouse');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Nice');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Nantes');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Strasbourg');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Montpellier');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Bordeaux');
INSERT INTO ville (id, nom) VALUES ('VILLE' || LPAD(nextval('seq_ville')::text, 5, '0'), 'Lille');


INSERT INTO avion (id, nom, modele, business, eco, fabrication) VALUES ('AVN' || LPAD(nextval('seq_avion')::text, 5, '0'), 'Boeing 737 Max', 'MOD00002', 16, 144, '2018-03-15');
INSERT INTO avion (id, nom, modele, business, eco, fabrication) VALUES ('AVN' || LPAD(nextval('seq_avion')::text, 5, '0'), 'Airbus A320neo', 'MOD00003', 12, 138, '2019-07-20');
INSERT INTO avion (id, nom, modele, business, eco, fabrication) VALUES ('AVN' || LPAD(nextval('seq_avion')::text, 5, '0'), 'Embraer E190-E2', 'MOD00004', 8, 100, '2020-05-10');
INSERT INTO avion (id, nom, modele, business, eco, fabrication) VALUES ('AVN' || LPAD(nextval('seq_avion')::text, 5, '0'), 'Boeing 737 MAX 8', 'MOD00002', 14, 150, '2017-11-05');
INSERT INTO avion (id, nom, modele, business, eco, fabrication) VALUES ('AVN' || LPAD(nextval('seq_avion')::text, 5, '0'), 'Airbus A320neo Plus', 'MOD00003', 12, 142, '2021-01-15');






