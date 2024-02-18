create database if not exists doctors;

use doctors;

create table docInfo(
Specialty varchar(255),
fullName varchar(255),
Phone varchar(255),
Cost varchar(255)
);

insert into docInfo(Specialty, fullName, Phone, Cost)
values
('PrimaryCare', 'John Doe', '1-800-DOE-JOHN', '$2'),
('PrimaryCare', 'John Green', '1-800-HEL-PMEE', '$3');
