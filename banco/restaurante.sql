CREATE DATABASE restaurante;

USE restaurante;

CREATE TABLE Cliente (
    id INT AUTO_INCREMENT,
    nome VARCHAR(225) NOT NULL,
    telefone CHAR(13) NOT NULL,
    email VARCHAR(225) NOT NULL,
    clienteVip BOOLEAN NOT NULL,
    dataCadastro DATE NOT NULL,
    dataUltimaVisita DATE NOT NULL, 
    PRIMARY KEY(id)
);

CREATE TABLE Prato (
    id INT AUTO_INCREMENT,
    nomePrato VARCHAR(225) NOT NULL,
    descricao VARCHAR(250) NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    qtdPessoasServe INT NOT NULL DEFAULT(1),
    categoria VARCHAR(225) NOT NULL,
    disponivel BOOLEAN NOT NULL,
    tempoPreparo INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE Pedido (
    id INT AUTO_INCREMENT,
    idCliente INT NOT NULL,
    mesa INT NOT NULL,
    horaPedido TIME NOT NULL,
    valorTotal DECIMAL(10, 2) NOT NULL,
    status VARCHAR(150) NOT NULL DEFAULT('Pendente'),
    PRIMARY KEY(id),
    FOREIGN KEY(idCliente) REFERENCES Cliente(id)
);

CREATE TABLE ItemPedido (
    id INT AUTO_INCREMENT,
    idPedido INT NOT NULL,
    idPrato INT NOT NULL,
    qtdPedido INT NOT NULL,
    valorTotal DECIMAL(10, 2) NOT NULL,
    observacoesCliente VARCHAR(225),
    status VARCHAR(150) NOT NULL DEFAULT('Pendente'),
    PRIMARY KEY(id),
    FOREIGN KEY(idPedido) REFERENCES Pedido(id),
    FOREIGN KEY(idPrato) REFERENCES Prato(id),
)