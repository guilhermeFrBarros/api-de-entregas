create table ocorrencia
(
	id bigint not null auto_increment,
    entrega_id bigint not null,
    descricao text not null,
    data_registro datetime not null,
    
    primary key(id)
);

alter table ocorrencia ADD constraint fk_ocorrencia_entrega
foreign key (entrega_id) references entrega (id);
