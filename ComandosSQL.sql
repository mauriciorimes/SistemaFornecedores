CREATE TABLE Contato (

	id INT(6) NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(255),
	email VARCHAR(255),
	telefone VARVHAR(255),
	foto VARCHAR(255)

)

INSERT INTO CONTATO(nome, email, telefone, foto) VALUES(‘Mauricio’, ‘mauriciorimes@gmail.com’, ‘999415722’, ‘../\\fotos\\linda.jpg’);

INSERT INTO CONTATO(nome, email, telefone, foto) VALUES(‘Kamilla’,
‘kamillaxavier@gmail.com’, ‘999784563’, ‘../\\fotos\\bonita.jpg’);
