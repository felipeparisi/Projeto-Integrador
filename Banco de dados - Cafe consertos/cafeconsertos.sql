/**
* Sistema OS - Café Consertos
* @authors Giovanna Gil, Felipe Parisi e Higor Ribeiro
*/
create database dbcafeconsertos;
use dbcafeconsertos;

drop database dbcafeconsertos;

create table tbusuarios(
	iduser int primary key auto_increment,
	nomeuser varchar (50) not null,
	fone varchar (15),
	login varchar (15) not null unique,
	senha varchar (250) not null,
	perfil varchar (20) not null 
);

-- login (usuário: admin | senha: admin)
insert into tbusuarios(nomeuser, fone, login,senha,perfil)
values('Administrador','(00) 00000-0000','admin',md5('admin'),'admin');

 delete from tbusuarios where iduser = 1;
create table tbclientes(
	idcli int primary key auto_increment,
	nomecliente varchar(50) not null,
	cep varchar (8)not null,
	enderecocliente varchar (50),
	numero varchar (5)not null,
	complemento varchar (25),
	bairro varchar (25),
	cidade varchar (25),
	uf varchar (2),
	emailcliente varchar (30) unique,
	fonecliente varchar (15) not null unique
);

create table tbos(
	protocolo int primary key auto_increment,
	dataabe timestamp default current_timestamp,
	tipoequipa varchar (249) not null,
	tipoabertura varchar (100) not null,
	descricao varchar (249) not null,
	laudo varchar (249) not null,
	statusos varchar (100) not null,
	nomeuser varchar(50) not null,
	nomecliente varchar (50) not null,
	valor varchar (25)not null,
	statuspag varchar (100) not null,
	enderecocliente varchar (50),
	cep varchar(8),
	numero varchar (5),
	complemento varchar (25),
	bairro varchar (25),
	iduser int,
	idcli int not null,
	foreign key(idcli) references tbclientes(idcli),
	foreign key(iduser) references tbusuarios(iduser)
);

select tbos.protocolo, tbos.tipoequipa, tbos.tipoequipa, tbos.descricao, tbclientes.nomecliente from tbos inner join tbclientes on tbos.idcli = tbclientes.idcli where tipoabertura = 'Orçamento';

select tbos.protocolo, tbos.statusos, tbos.tipoequipa, date_format(tbos.dataabe,'%d/%m/%Y - %H:%i') as data_protocolo, tbclientes.nomecliente, tbclientes.fonecliente from tbos inner join tbclientes on tbos.idcli = tbclientes.idcli where tbos.statusos = 'Aguardando peça' or tbos.statusos = 'Aguardando disponibilidade técnica'or tbos.statusos = 'Aguardando aprovação do cliente';

update tbos set descricao = ?, statusos = ?,laudo = ?, valor = ?, statuspag = ?, tipoabertura = ? where protocolo = ?;

delete from tbos where protocolo = 8;
				