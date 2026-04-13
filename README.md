# Sistema de Gestao de Tarefas

Aplicacao Java Web desenvolvida como atividade pratica do processo seletivo da ESIG Group para a vaga de Estagio Pessoa Desenvolvedora Java.

Consiste em um gerenciador de tarefas com as funcionalidades de criar, atualizar, remover, listar e concluir tarefas. Cada tarefa possui titulo, descricao, responsavel, prioridade (alta, media, baixa), deadline e situacao (em andamento ou concluida). A listagem permite filtrar tarefas por numero, titulo/descricao, responsavel e situacao.

---

## Itens Implementados

a) Aplicacao Java Web utilizando JavaServer Faces (JSF) com Mojarra 2.3.18 e componentes PrimeFaces 12.0.0 para a interface. Gerenciamento de beans via CDI com Weld 3.1.9.

b) Persistencia em banco de dados PostgreSQL 16.13 utilizando driver JDBC 42.7.3.

c) JPA com Hibernate 5.6.15 como provider. As entidades sao mapeadas com annotations e as tabelas sao criadas automaticamente via hibernate.hbm2ddl.auto=update.

d) Classe Main.java para teste manual da entidade Tarefa e do acesso ao banco de dados via EntityManager, validando o ciclo completo de CRUD antes da integracao com o frontend.

---

## Video de Apresentacao

[Link para o video](https://youtu.be/OgT6ngyJTcI)

---

## Como Compilar e Executar

### Pre-requisitos

- Java JDK 17.0.8 [link Download](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- Apache Maven 3.8+ ou superiores [link Download](https://maven.apache.org/docs/3.8.8/release-notes.html)
- PostgreSQL 16.13 [link Download](https://www.postgresql.org/download/)
- Apache Tomcat 9.0.x (core version) [link Download](https://tomcat.apache.org/download-90.cgi)


- O uso do Maven é fortemente recomendado.


### 0: Criar o repositorio com Maven (Etapa Opcional)

```bash
mvn archetype:generate \
  -DgroupId=com.esig.estagio \
  -DartifactId=estagio_esig_projeto \
  -DarchetypeArtifactId=maven-archetype-webapp \
  -DarchetypeGroupId=org.apache.maven.archetypes \
  -DinteractiveMode=false
```

Em seguida, criar os pacotes dentro de `src/main/java/com/esig/estagio/`:

```bash
mkdir -p src/main/java/com/esig/estagio/model
mkdir -p src/main/java/com/esig/estagio/dao
mkdir -p src/main/java/com/esig/estagio/controller
mkdir -p src/main/resources/META-INF
```
### 1: Gitclone esse repositório

```bash
git clone https://github.com/raulramalho01/Atividade-est-gio-esig-CRUD.git
cd Atividade-est-gio-esig-CRUD
```

### 2: Buildar o projeto

```bash
cd estagio_esig_projeto
mvn clean compile
```

Para gerar o WAR de deploy:

```bash
mvn clean package
```

O arquivo `estagio_esig_projeto.war` sera gerado em `target/`.

### 3: Criar o banco de dados

```bash
sudo -u postgres psql
```

```sql
CREATE USER admin WITH PASSWORD 'admin123';
CREATE DATABASE estagio_esig OWNER admin;
GRANT ALL PRIVILEGES ON DATABASE estagio_esig TO admin;
\q
```

### 4: Configurar a conexao

O arquivo `src/main/resources/META-INF/persistence.xml` ja vem configurado com:

- URL: jdbc:postgresql://localhost:5432/estagio_esig
- Usuario: admin
- Senha: admin123

Altere estagio_esig,admin e admin123 se necessario para o seu ambiente.

### 5: Fazer deploy no Tomcat 9

```bash
cp target/estagio_esig_projeto.war /caminho/do/tomcat9/webapps/
```

Inicie o Tomcat:

```bash
/caminho/do/tomcat9/bin/startup.sh
```

### 6: Acessar a aplicacao

```
http://localhost:8080/estagio_esig_projeto/index.xhtml
```

---

## Tecnologias e Versoes

- Java 17.0.8 
- Maven 3.8+
- JSF (Mojarra) 2.3.18
- PrimeFaces 12.0.0
- CDI (Weld) 3.1.9
- JPA (Hibernate) 5.6.15
- PostgreSQL 16.13 (Driver 42.7.3)
- Apache Tomcat 9.0.x (core version)

---

## Estrutura do Projeto

```
estagio_esig_projeto/
├── pom.xml
└── src/main/
    ├── java/com/esig/estagio/
    │   ├── model/          Tarefa, Prioridade, Situacao
    │   ├── dao/            TarefaDAO
    │   ├── controller/     TarefaController
    │   └── Main.java       Teste manual
    ├── resources/META-INF/
    │   └── persistence.xml
    └── webapp/
        ├── WEB-INF/
        │   ├── web.xml
        │   └── beans.xml
        ├── index.xhtml
        ├── criarTarefa.xhtml
        ├── editarTarefa.xhtml
        └── listarTarefas.xhtml
```
