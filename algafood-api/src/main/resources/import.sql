insert into cozinha (nome) values ('Tailandesa');
insert into cozinha (nome) values ('Indiana');


insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tuc Tuc', 6.9, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Morte lenta', 0.9, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Podrão', 5, 2);


insert into forma_pagamento (descricao) values ('Dinheiro');
insert into forma_pagamento (descricao) values ('Crédito');
insert into forma_pagamento (descricao) values ('Débito');


insert into permissao (nome, descricao) values ('INSERT_RESTAURANTE', 'Permite inserir restaurantes');
insert into permissao (nome, descricao) values ('REMOVE_RESTAURANTE', 'Permite remover restaurantes');
insert into permissao (nome, descricao) values ('INSERT_COZINHA', 'Permite inserir cozinhas');
insert into permissao (nome, descricao) values ('REMOVE_COZINHA', 'Permite remover cozinhas');


insert into estado (nome) values ('São Paulo');
insert into estado (nome) values ('Minas Gerais');
insert into estado (nome) values ('Bahia');

insert into cidade (nome, estado_id) values ('Ourinhos', 1);
insert into cidade (nome, estado_id) values ('Barueri', 1);
insert into cidade (nome, estado_id) values ('Belo Horizonte', 2);
insert into cidade (nome, estado_id) values ('Salvador', 3);