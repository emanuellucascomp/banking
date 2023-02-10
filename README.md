# Banking project

Desenvolva um projeto que exponha APIs no padrão REST (JSON) e atenda as
seguintes funcionalidades:
1. Endpoint para cadastrar um cliente, com as seguintes informações: id (único),
nome, número da conta (único) e saldo em conta;
2. Endpoint para listar todos os clientes cadastrados;
3. Endpoint para buscar um cliente pelo número da conta;
4. Endpoint para realizar transferência entre 2 contas. A conta origem precisa ter
saldo o suficiente para a realização da transferência e a transferência deve ser
de no máximo R$ 1000,00 reais;
5. Endpoint para buscar as transferências relacionadas à uma conta, por ordem
de data decrescente. Lembre-se que transferências sem sucesso também
devem armazenadas.

Para compilar o projeto, é interessante que você tenha o Maven instalado.

Com o Maven, na raíz do projeto você pode rodar o comando

```
mvn compile
```

E depois

```
mvn package
```

Com isso vai gerar um .jar na pasta target, que você pode rodar com 

```
java -jar <nome_do_jar>.jar
```

A documentação do projeto pode ser encontrada em

```
http://localhost:<porta>/swagger-ui.html
```
