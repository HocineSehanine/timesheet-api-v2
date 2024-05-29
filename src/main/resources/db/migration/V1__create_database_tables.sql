create table if not exists db_timesheet.grupo
(
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nome varchar(255)
);

create table if not exists db_timesheet.recursos
(
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    nome varchar(255)
);

create table if not exists db_timesheet.recursos_grupo
(
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_recurso CHAR(36) NOT NULL,
    id_grupo CHAR(36) NOT NULL
);

CREATE TABLE IF NOT EXISTS db_timesheet.usuarios (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    grupo_id CHAR(36) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    time VARCHAR(255) NOT NULL,
    valor_hora DECIMAL(10, 2) NOT NULL,
    banco_horas BOOLEAN NOT NULL,
    contrato_mensal INT NOT NULL,
    data_inicio DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS db_timesheet.recuperar_senha (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_usuario CHAR(36) NOT NULL,
    code VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS db_timesheet.lancamentos (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_usuario CHAR(36) NOT NULL,
    data DATE NOT NULL,
    feriado BOOLEAN NOT NULL,
    hora_inicio VARCHAR(255) NOT NULL,
    hora_fim VARCHAR(255) NOT NULL,
    total VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    ano INT NOT NULL,
    mes INT NOT NULL
);

CREATE TABLE IF NOT EXISTS db_timesheet.banco_horas (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_usuario CHAR(36) NOT NULL,
    data DATE NOT NULL,
    hora VARCHAR(255) NOT NULL,
    descricao VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS db_timesheet.pagamentos (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    id_usuario CHAR(36) NOT NULL,
    mes INT NOT NULL,
    ano INT NOT NULL,
    total_horas DOUBLE NOT NULL,
    data_pagamento DATE NOT NULL,
    valor_hora DOUBLE NOT NULL,
    valor_total DOUBLE NOT NULL,
    banco_horas_atual DOUBLE NOT NULL
);


CREATE TABLE IF NOT EXISTS db_timesheet.feriados (
    id CHAR(36) PRIMARY KEY DEFAULT (UUID()),
    ano INT NOT NULL,
    data DATE NOT NULL,
    mes INT NOT NULL
);

