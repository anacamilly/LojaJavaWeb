-- Database: loja

-- DROP DATABASE IF EXISTS loja;

CREATE DATABASE loja
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
CREATE TABLE tb_clientes(
	id serial,
	nome varchar(266),
	email varchar(266) primary key,
	senha varchar(70)
);

CREATE TABLE tb_produtos(
	codigo integer primary key,
	nome varchar(266),
	descricao varchar(266),
	preco float,
	quantidade integer
);

CREATE TABLE tb_lojistas(
	id serial,
	nome varchar(266),
	email varchar(266) primary key,
	senha varchar(70)
);

SELECT * FROM tb_clientes;

insert into tb_lojistas(nome, email, senha) 
VALUES ('ana', 'ana@hotmail.com', 'ana123');