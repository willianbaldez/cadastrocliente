# Projeto Cadastro de Cliente
Processo seletivo Desenvolvedor Java

## Projeto desenvolvido com [Spring boot](https://spring.io/projects/spring-boot)

### IDE
A IDE utilizada foi o [Intellij](https://www.jetbrains.com/)

### Java
A versão Java utilizada para criar o projeto foi a 11.

### Testes Unitários

Para a implementação dos testes unitários foi utilizado o framework [JUnit5](https://junit.org/junit5/) e banco de dados [H2](https://www.h2database.com/html/main.html).

### Docker
Foi utilizado o [Docker](https://www.docker.com/) como facilitador para a utilização do banco de dados mysql, o que também vai possibilitar utilizar o projeto standalone.

### AWS
O projeto foi submetido para o servidor na plataforma em nuvem [AWS](https://aws.amazon.com). A branch master do github foi escolhida para realização do provisionamento no servidor.
O Banco de dados criado para a aplicação foi o [MySQL](https://www.mysql.com/).

### Maven
Projeto foi criado usando o controle de dependências [Apache Maven](https://maven.apache.org/), para construir o projeto
é necessário executar no terminal o seguinte comando:
> mvn install

Após a instalação do projeto será criado o artefato * __cadastrocliente-api-1.0.0.jar__,  na pasta __target__, onde  é o artefato principal criado pelo projeto,
que será implantado na [AWS](https://aws.amazon.com)

### Executar projeto Standalone
Para executar o projeto em local, é necessário construir o projeto com o maven, que foi descrito anteriormente, depois
executar o seguindo comando no terminal:
> java -jar cadastrocliente-api-1.0.0.jar

Foi adicionada o arquivo __docker-compose.yaml__ na raiz do projeto criando
a instância do banco de dados necessários para utilizar a aplicação standalone.

### Deployment Aplicação
O deploy da aplicação foi realizado na [AWS](https://aws.amazon.com) e pode ser acessada a partir do [link](https://18.229.157.11:8080/).
As transações podem ser realizadas pelo [swagger-ui](https://18.229.157.11:8080/swagger-ui.html).

### Postman
Para interação da aplicação pode ser usado a ferramenta [Postman](https://www.postman.com/). Pode ser importado na ferramento no arquivo JSON localizado na raiz do projeto na pasta chamada postman
