insert into usuarios(email, data_criacao, senha) values('admin@email.com', '2021-02-24', '$2a$10$raRlPrNAGnvXHXo.71uOJe1rAT7t.A94dVYr9jEyANrSqDLX5SJlq');

insert into categorias(nome) values('Notebooks');

insert into produtos(nome, preco, quantidade, descricao, categoria_id, dono_id)
values('Notebook Dell Latitude 7400', 12000, 20, 'É um notebook', 1, 1);

insert into caracteristicas(nome, valor, produto_id) values('Processador', 'i7 vPro', 1);
insert into caracteristicas(nome, valor, produto_id) values('Memória Ram', '16gb', 1);
insert into caracteristicas(nome, valor, produto_id) values('Armazenamento', '512gb', 1);