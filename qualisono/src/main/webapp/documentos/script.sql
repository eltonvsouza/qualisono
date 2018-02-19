-- Cria o Banco de Dados
CREATE DATABASE IF NOT EXISTS qualisono;

Use qualisono;

DROP TABLE IF EXISTS compra;
DROP TABLE IF EXISTS venda;
DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS banco;
DROP TABLE IF EXISTS tipofuncionario;
DROP TABLE IF EXISTS transportadora;
DROP TABLE IF EXISTS fornecedor;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS geral;
DROP TABLE IF EXISTS configuracao;
DROP TABLE IF EXISTS cliente;
DROP TABLE IF EXISTS funcionario;
DROP TABLE IF EXISTS nfitem;
DROP TABLE IF EXISTS contasapagar;
DROP TABLE IF EXISTS parcelasapagar;
DROP TABLE IF EXISTS agenda;

-- Cria a tabela UNIDADE
CREATE TABLE IF NOT EXISTS unidade(
	codigo INT NOT NULL AUTO_INCREMENT,
	descricao VARCHAR (100) NOT NULL,
	validade DATE NOT NULL,
	CONSTRAINT pk_unidade PRIMARY KEY (codigo)
);
INSERT INTO unidade (descricao, validade) VALUES
('Qualisono', '2011-11-01'),
('eltonvs', '2012-12-31');

-- Cria a tabela USUARIO do sistema
CREATE TABLE IF NOT EXISTS usuario(
	codigo INT NOT NULL AUTO_INCREMENT,
	unidade INT NOT NULL,
	usuario VARCHAR (10) NOT NULL,
	senha VARCHAR (100) NOT NULL,
	nome VARCHAR (100) NOT NULL,
	email VARCHAR (100),
  grupo VARCHAR(50),
	CONSTRAINT pk_usuario PRIMARY KEY (codigo),
	CONSTRAINT uk_usuario UNIQUE (usuario),
  CONSTRAINT fk_usuario_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Insere dados na tabela USUARIO do sistema
INSERT INTO usuario (unidade, usuario, senha, nome, email, grupo) VALUES
-- qualisono/12345
(1, 'qualisono', '5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5', 'Administrador do Sistema', '', 'ROLE_ADMIN'),
(1, 'elton', '5c91eed7c7d69a7c89468828646ebfe5d917d6d51c8e58f8f7d54ab5ed8f46f4', 'Elton Veiga de Souza', '', 'ROLE_ADMIN');

-- Cria tabela GERAL

CREATE TABLE IF NOT EXISTS geral(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  grupo VARCHAR (20) NOT NULL,
  chave VARCHAR (20) NOT NULL,
  descricao VARCHAR (100),
  valor VARCHAR (200) NOT NULL,
  CONSTRAINT pk_geral PRIMARY KEY (codigo),
  CONSTRAINT uk_geral UNIQUE (unidade, grupo, chave),
  CONSTRAINT fk_geral_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
INSERT INTO geral (unidade, grupo, chave, descricao, valor) VALUES
(1, 'tipocliente','a', '','Atacado'),
(1, 'tipocliente','c', '','Consumidor'),
(1, 'cptipodoc','r', 'Contas a Pagar - Tipo de Documento', 'Recibo'),
(1, 'cptipodoc','n', 'Contas a Pagar - Tipo de Documento', 'Nota Fiscal'),
(1, 'cptipodoc','b', 'Contas a Pagar - Tipo de Documento', 'Boleto'),
(1, 'cptipodoc','c', 'Contas a Pagar - Tipo de Documento', 'Cheque'),
(1, 'cptipodoc','d', 'Contas a Pagar - Tipo de Documento', 'Duplicata'),
(1, 'cpcategoria','combustivel', 'Contas a Pagar - Categoria', 'Combustível'),
(1, 'cpcategoria','comissoes', 'Contas a Pagar - Categoria', 'Comissoes'),
(1, 'cpcategoria','fixas', 'Contas a Pagar - Categoria', 'Fixas'),
(1, 'cpcategoria','fornecedor', 'Contas a Pagar - Categoria', 'Fornecedor'),
(1, 'cpcategoria','frete', 'Contas a Pagar - Categoria', 'Frete'),
(1, 'cpcategoria','impostos', 'Contas a Pagar - Categoria', 'Impostos'),
(1, 'cpcategoria','investimentos', 'Contas a Pagar - Categoria', 'Investimentos'),
(1, 'cpcategoria','matlimpeza', 'Contas a Pagar - Categoria', 'Material de Limpeza'),
(1, 'cpcategoria','matexpediente', 'Contas a Pagar - Categoria', 'Material de Expediente'),
(1, 'cpcategoria','pessoal', 'Contas a Pagar - Categoria', 'Pessoal'),
(1, 'cpcategoria','publicidade', 'Contas a Pagar - Categoria', 'Publicidade'),
(1, 'cpcategoria','refeicoes', 'Contas a Pagar - Categoria', 'Refeicoes'),
(1, 'cpcategoria','diversos', 'Contas a Pagar - Categoria', 'Diversos'),
(1, 'cpcategoria','imobilizado', 'Contas a Pagar - Categoria', 'Imobilizado'),
(1, 'cpcategoria','aluguel', 'Contas a Pagar - Categoria', 'Aluguel'),
(1, 'cpcategoria','agua', 'Contas a Pagar - Categoria', 'Água'),
(1, 'cpcategoria','contatelefone', 'Contas a Pagar - Categoria', 'Conta de Telefone'),
(1, 'cpcategoria','pis', 'Contas a Pagar - Categoria', 'Pis'),
(1, 'cpcategoria','confins', 'Contas a Pagar - Categoria', 'Confins'),
(1, 'cpcategoria','energia', 'Contas a Pagar - Categoria', 'Energia'),
(1, 'vendafpag','cartao', 'Venda - Forma de Pagamento', 'Cartão'),
(1, 'vendafpag','cheqvista', 'Venda - Forma de Pagamento', 'Cheque à Vista'),
(1, 'vendafpag','cheqdatado', 'Venda - Forma de Pagamento', 'Cheque Pré-Datado'),
(1, 'vendafpag','credito', 'Venda - Forma de Pagamento', 'Crédito'),
(1, 'vendafpag','deposito', 'Venda - Forma de Pagamento', 'Depósito'),
(1, 'vendafpag','duplibanco', 'Venda - Forma de Pagamento', 'Duplicata em Banco'),
(1, 'vendafpag','duplicart', 'Venda - Forma de Pagamento', 'Duplicata em carteira'),
(1, 'vendafpag','duplineg', 'Venda - Forma de Pagamento', 'Duplicata negociada'),
(1, 'vendafpag','especie', 'Venda - Forma de Pagamento', 'Espécie'),
(1, 'vendafpag','financeira', 'Venda - Forma de Pagamento', 'Financeira'),
(1, 'agenda','visita', 'Agenda - Evento', 'Visita'),
(1, 'agenda','evento', 'Agenda - Evento', 'Evento'),
(1, 'agenda','reuniao', 'Agenda - Evento', 'Reunião'),
(1, 'agenda','workshop', 'Agenda - Evento', 'Workshop'),
(1, 'agenda','gabinete', 'Agenda - Evento', 'Gabinete'),
(1, 'agenda','curso', 'Agenda - Evento', 'Curso'),
(1, 'agenda','treinamento', 'Agenda - Evento', 'Treinamento'),
(1, 'sexo','M', 'Sexo Masculino','Masculino'),
(1, 'sexo','F', 'Sexo Feminino','Feminino'),
(1, 'estadocivil','S', 'Solteiro','Solteiro'),
(1, 'estadocivil','C', 'Casado','Casado'),
(1, 'estadocivil','V', 'Viúvo','Viúvo'),
(1, 'estadocivil','SJ', 'Separado Judicialmente','Separado judicialmente'),
(1, 'estadocivil','D', 'Divorciado','Divorciado'),
(1, 'assuntoemail','V', 'Visita','Visita'),
(1, 'assuntoemail','E', 'Evento','Evento'),
(1, 'assuntoemail','R', 'Reunião','Reunião'),
(1, 'tipodesconto','D', 'Dinheiro','Dinheiro'),
(1, 'tipodesconto','P', 'Porcentagem','Porcentagem'),
(1, 'grupousuario','ROLE_ADMIN', 'Adminsitrador do Sistema','Adminsitrador'),
(1, 'grupousuario','ROLE_USER', 'Usuário do Sistema','Usuário'),
(1, 'categoriacontas', 'f', 'Fornecedor', 'Fornecedor'),
(1, 'categoriacontas', 'c', 'Cliente', 'Cliente'),
(1, 'categoriacontas', 't', 'Transportadora', 'Transportadora');

-- Cria tabela CONFIGURACAO do sistema
CREATE TABLE IF NOT EXISTS configuracao(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  grupo VARCHAR (20) NOT NULL,
  tipo VARCHAR (20) NOT NULL,
  descricao VARCHAR (100),
  valor VARCHAR (200) NOT NULL,
  CONSTRAINT pk_configuracao PRIMARY KEY (codigo),
  CONSTRAINT uk_configuracao UNIQUE (unidade, grupo, tipo),
  CONSTRAINT fk_configuracao_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
INSERT INTO configuracao (unidade, grupo, tipo, descricao, valor) VALUES
(1, 'sistema','backup', 'MySQL Backup','C:/Program Files (x86)/MySQL/MySQL Server 5.5/bin/mysqldump.exe'),
(1, 'sistema','restore', 'MySQL Restore','C:/Program Files (x86)/MySQL/MySQL Server 5.5/bin/mysql.exe'),
(1, 'sistema','email', 'E-Mail para o sistema enviar automaticamente','qualisono@gmail'),
(1, 'bancodados','nome', 'MySQL Nome do Banco','qualisono'),
(1, 'bancodados','usuario', 'MySQL Usuário','root'),
(1, 'bancodados','senha', 'MySQL Senha','root'),
(1, 'bancodados','tabelaBackup', 'Tabelas do banco que será feito backup','agenda, cliente, configuracao, contasapagar, fornecedor, funcionario, geral, nfitem, notafiscal, parcelasapagar, produto, tansportadora, usuario, venda, vendaitem'),
-- (1, 'comissao','pd', 'Comissão do Pré-Distribuidor','25'),
-- (1, 'comissao','m', 'Comissão do Monitor','10'),
-- (1, 'comissao','d', 'Comissão do Distribuidor','50'),
-- (1, 'pontuacao','m', 'Pontuação do Monitor','10'),
-- (1, 'pontuacao','pd', 'Pontuação do Pré-Distribuidor','30'),
(1, 'valorpto','pto', 'Valor 1 ponto','1931');

-- Cria tabela UF
DROP TABLE IF EXISTS `uf`;
CREATE TABLE `uf` (
  `uf_codigo` int(4) NOT NULL default '0',
  `uf_sigla` char(2) default NULL,
  `uf_descricao` varchar(72) default NULL,
  PRIMARY KEY  (`uf_codigo`)
);
INSERT INTO `uf` VALUES (1,'AC','ACRE'),(2,'AL','ALAGOAS'),(3,'AP','AMAPÁ'),(4,'AM','AMAZONAS'),(5,'BA','BAHIA'),(6,'CE','CEARÁ'),(7,'DF','DISTRITO FEDERAL'),(8,'ES','ESPÍRITO SANTO'),(9,'RR','RORAIMA'),(10,'GO','GOIÁS'),(11,'MA','MARANHÃO'),(12,'MT','MATO GROSSO'),(13,'MS','MATO GROSSO DO SUL'),(14,'MG','MINAS GERAIS'),(15,'PA','PARÁ'),(16,'PB','PARAÍBA'),(17,'PR','PARANÁ'),(18,'PE','PERNAMBUCO'),(19,'PI','PIAUÍ'),(20,'RJ','RIO DE JANEIRO'),(21,'RN','RIO GRANDE DO NORTE'),(22,'RS','RIO GRANDE DO SUL'),(23,'RO','RONDÔNIA'),(24,'TO','TOCANTINS'),(25,'SC','SANTA CATARINA'),(26,'SP','SÃO PAULO'),(27,'SE','SERGIPE');

-- Cria tabela CLIENTE
CREATE TABLE IF NOT EXISTS cliente(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  -- pessoais
  nome VARCHAR (200) NOT NULL,
  cpf VARCHAR (14) NOT NULL,
  datanascimento DATE,
  datacadastro DATE,
  sexo VARCHAR (20) NOT NULL,
  cnh INT (11),
  rg VARCHAR (10),
  orgaoemissor VARCHAR (5),
  dataexpedrg DATE,
  nomemae VARCHAR (200) NOT NULL,
  naturalidade VARCHAR (50),
  ecivil VARCHAR (20),
  dependentes INT (2),
  telefone VARCHAR (13),
  telefone2 VARCHAR (13),
  contato VARCHAR (50),
  email VARCHAR(20),
  -- endereco
  cep VARCHAR(11),
  uf INT (4),
  cidade VARCHAR (100),
  bairro VARCHAR (100),
  logradouro VARCHAR (100),
  pais VARCHAR (50),
  numero INT (5),
  complemento VARCHAR(100),
  tiporesid VARCHAR (20),
  valorresid DOUBLE (9,2),
  temporesid INT (2),
  -- profissionais
  empresa VARCHAR (100),
  cargo VARCHAR (100),
  profissao VARCHAR (100),
  cnpj VARCHAR (18),
  natocupacao VARCHAR (20),
  tipocliente VARCHAR (20) NOT NULL,	-- tabela geral
  ativo BOOLEAN NOT NULL,
  banco VARCHAR (20) NOT NULL,
  agencia VARCHAR (15) NOT NULL,
  nconta VARCHAR (15) NOT NULL,
  cespecial BOOLEAN,
  dtabertura DATE,
  ccredito BOOLEAN,
  afinidade VARCHAR (20),
  CONSTRAINT pk_cliente PRIMARY KEY (codigo),
  CONSTRAINT fk_cliente_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- cria tabela TIPO FUNCIONARIO
CREATE TABLE IF NOT EXISTS tipofuncionario(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  descricao VARCHAR (100),
  comissao DOUBLE (5,2) NOT NULL,
  pontuacao INT (2) NOT NULL,
  CONSTRAINT pk_tipofuncionario PRIMARY KEY (codigo),
  CONSTRAINT fk_tipofuncionario_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);
INSERT INTO tipofuncionario (unidade, descricao, comissao, pontuacao) VALUES
(1, 'Monitor',10, 10),
(1, 'Pré-Distribuidor',25, 30),
(1, 'Distribuidor',50, 0);

-- Cria tabela FUNCIONARIO
CREATE TABLE IF NOT EXISTS funcionario(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  tipo VARCHAR (4) NOT NULL,
  cpfcnpj VARCHAR (18) NOT NULL,
  inscestadual VARCHAR (18),
  rg VARCHAR (10),
  dataexpedRg DATE,
  nome VARCHAR (200) NOT NULL,
  datacadastro DATE,
  razaosocial VARCHAR (200),
  cep VARCHAR(11),
  uf INT (4),
  cidade VARCHAR (100),
  bairro VARCHAR (100),
  logradouro VARCHAR (100),
  pais VARCHAR (50),
  numero INT (5),
  complemento VARCHAR(100),
  pontoref VARCHAR (200),
  telefone VARCHAR (13),
  telefone2 VARCHAR (13),
  contato VARCHAR (50),
  datanascimento DATE,
  email VARCHAR(20),
  tipofuncionario INT NOT NULL,
  ativo BOOLEAN NOT NULL,
  CONSTRAINT pk_funcionario PRIMARY KEY (codigo),
  CONSTRAINT fk_funcionario_tipofuncionario FOREIGN KEY (tipofuncionario) REFERENCES tipofuncionario (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_funcionario_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela FORNECEDOR
CREATE TABLE IF NOT EXISTS fornecedor(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  cnpj VARCHAR (18) NOT NULL,
  inscestadual VARCHAR (18) NOT NULL,
  nome VARCHAR (200) NOT NULL,
  cep VARCHAR(11),
  uf INT (4),
  cidade VARCHAR (100),
  bairro VARCHAR (100),
  logradouro VARCHAR (100),
  pais VARCHAR (50),
  numero INT (5),
  complemento VARCHAR(100),
  telefone VARCHAR (13),
  fax VARCHAR (13),
  contato VARCHAR (50),
  email VARCHAR(20),
  representante VARCHAR (100),
  banco VARCHAR (20) NOT NULL,
  agencia VARCHAR (15) NOT NULL,
  nconta VARCHAR (15) NOT NULL,
  cespecial BOOLEAN,
  dtabertura DATE,
  ccredito BOOLEAN,
  afinidade VARCHAR (20),
  CONSTRAINT pk_fornecedor PRIMARY KEY (codigo),
  CONSTRAINT fk_fornecedor_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela PRODUTO
CREATE TABLE IF NOT EXISTS produto(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  codprod INT NOT NULL,
  descricao VARCHAR (60) NOT NULL,
  medida1 VARCHAR (20) NOT NULL,
  medida2 VARCHAR (20) NOT NULL,
  fornecedor INT NOT NULL,
  vcompra DOUBLE (9,2) NOT NULL,
  vvenda DOUBLE (9,2) NOT NULL ,
  icmscompra DOUBLE (5,2) NOT NULL,
  icmssubst DOUBLE (5,2) NOT NULL,
  CONSTRAINT pk_produto PRIMARY KEY (codigo),
  CONSTRAINT uk_produto UNIQUE (unidade, codprod),
  CONSTRAINT fk_produto_fornecedor FOREIGN KEY (fornecedor) REFERENCES fornecedor (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_produto_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela TRANSPORTADORA
CREATE TABLE IF NOT EXISTS transportadora(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  cnpj VARCHAR (18) NOT NULL,
  inscestadual VARCHAR (18) NOT NULL,
  nome VARCHAR (200) NOT NULL,
  cep VARCHAR(11),
  uf INT (4),
  cidade VARCHAR (100),
  bairro VARCHAR (100),
  logradouro VARCHAR (100),
  pais VARCHAR (50),
  numero INT (5),
  complemento VARCHAR(100),
  telefone VARCHAR (13),
  fax VARCHAR (13),
  contato VARCHAR (50),
  email VARCHAR(20),
  banco VARCHAR (20) NOT NULL,
  agencia VARCHAR (15) NOT NULL,
  nconta VARCHAR (15) NOT NULL,
  cespecial BOOLEAN,
  dtabertura DATE,
  ccredito BOOLEAN,
  afinidade VARCHAR (20),
  CONSTRAINT pk_transportadora PRIMARY KEY (codigo),
  CONSTRAINT fk_transportadora_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela COMPRA (NOTA FISCAL)
CREATE TABLE IF NOT EXISTS compra(
  unidade INT NOT NULL,
  nNF INT (9) NOT NULL,
  serie INT (3) NOT NULL,
  tpNF INT (1) NOT NULL,
  natOp VARCHAR (60) NOT NULL,
  fornecedor INT NOT NULL,
  transportadora INT,
  infCpl VARCHAR (5000),
  valor DOUBLE NOT NULL,
  data DATE NOT NULL,
  CONSTRAINT pk_compra PRIMARY KEY (nNF),
  CONSTRAINT uk_compra UNIQUE (unidade, nNF),
  CONSTRAINT fk_compra_fornecedor FOREIGN KEY (fornecedor) REFERENCES fornecedor (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_compra_transportadora FOREIGN KEY (transportadora) REFERENCES transportadora (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_compra_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela ITEM COMPRA (NOTA FISCAL)
CREATE TABLE IF NOT EXISTS compraitem(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  cProd INT NOT NULL,
  compra INT NOT NULL,
  quantidade INT NOT NULL,
  vtotal DOUBLE (15,2) NOT NULL,
  bcicms DOUBLE (15,2) NOT NULL,
  vicms DOUBLE (15,2) NOT NULL,
  vipi DOUBLE (15,2) NOT NULL,
  aicms DOUBLE (15,2) NOT NULL,
  aipi DOUBLE (15,2) NOT NULL,
  CONSTRAINT pk_compraitem PRIMARY KEY (codigo),
  CONSTRAINT fk_compraitem_compra FOREIGN KEY (compra) REFERENCES compra (nNF)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_compraitem_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_compraitem_produto FOREIGN KEY (cProd) REFERENCES produto (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela VENDA
CREATE TABLE IF NOT EXISTS venda(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  cliente INT NOT NULL,
  pdistribuidor INT NOT NULL,
  monitor INT NOT NULL,
  data DATE NOT NULL,
  fpagamento VARCHAR (20) NOT NULL,	-- tabela geral
  valor DOUBLE NOT NULL,
  CONSTRAINT pk_venda PRIMARY KEY (codigo),
  CONSTRAINT fk_venda_cliente FOREIGN KEY (cliente) REFERENCES cliente (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_venda_pdistribuidor FOREIGN KEY (pdistribuidor) REFERENCES funcionario (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_venda_monitor FOREIGN KEY (monitor) REFERENCES funcionario (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_venda_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela ITEM venda
CREATE TABLE IF NOT EXISTS vendaitem(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  venda INT NOT NULL,
  produto INT NOT NULL,
  quantidade INT (6) NOT NULL,
  pontuacao INT NOT NULL,
  valor DOUBLE (15,2) NOT NULL,
  comissaopd DOUBLE (5,2) NOT NULL,
  comissaom DOUBLE (5,2) NOT NULL,
  tipodesconto VARCHAR(200),
  desconto DOUBLE (9,2),
  CONSTRAINT pk_vendaitem PRIMARY KEY (codigo),
  CONSTRAINT fk_vendaitem_venda FOREIGN KEY (venda) REFERENCES venda (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_vendaitem_produto FOREIGN KEY (produto) REFERENCES produto (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_vendaitem_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela CONTAS A PAGAR
CREATE TABLE IF NOT EXISTS contasapagar(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  categoria VARCHAR (20) NOT NULL,	-- tabela geral
  opcaocategoria INT NOT NULL,
  -- fornecedor INT,
  mesreferencia DATE,
  tipodoc VARCHAR (20),	-- tabela geral
  compra INT (9),
  valor DOUBLE (13,2) NOT NULL,
  datavencimento DATE NOT NULL,
  parcela INT NOT NULL,
  dia INT (2) NOT NULL,
  CONSTRAINT pk_contasapagar PRIMARY KEY (codigo),
  -- CONSTRAINT fk_contasapagar_fornecedor FOREIGN KEY (fornecedor) REFERENCES fornecedor (codigo)
    -- ON DELETE RESTRICT
    -- ON UPDATE CASCADE,
  CONSTRAINT fk_contasapagar_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_contasapagar_compra FOREIGN KEY (compra) REFERENCES compra (nNF)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela PARCELAS A PAGAR
CREATE TABLE IF NOT EXISTS parcelasapagar(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  contaapagar INT NOT NULL,
  datavencimento DATE NOT NULL,
  valorparcela DOUBLE (13,2) NOT NULL,
  formapagamento VARCHAR (20),
  datapagamento DATE,
  valorpago DOUBLE (13,2),
  lote BOOLEAN,
  CONSTRAINT pk_parcelasapagar PRIMARY KEY (codigo),
  CONSTRAINT fk_parcelasapagar_contasapagar FOREIGN KEY (contaapagar) REFERENCES contasapagar (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT fk_parcelasapagar_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela AGENDA
CREATE TABLE IF NOT EXISTS agenda(
  codigo INT NOT NULL AUTO_INCREMENT,
  unidade INT NOT NULL,
  assunto VARCHAR (20) NOT NULL,
  mensagem VARCHAR (200),
  datahora DATETIME NOT NULL,
  alarme TIME,
  email VARCHAR (200) NOT NULL,
  CONSTRAINT pk_agenda PRIMARY KEY (codigo),
  CONSTRAINT fk_agenda_unidade FOREIGN KEY (unidade) REFERENCES unidade (codigo)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
);

-- Cria tabela ORGANOGRAMA