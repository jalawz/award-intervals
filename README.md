# Award Intervals API

API RESTful para consultar os produtores com menor e maior intervalo entre dois premios consecutivos da categoria Pior Filme do Golden Raspberry Awards.

## Requisitos

- Java 21
- Maven Wrapper incluido no projeto

## Como rodar os testes

```bash
MAVEN_USER_HOME=.m2 ./mvnw test
```

## Como rodar a aplicacao

```bash
MAVEN_USER_HOME=.m2 ./mvnw spring-boot:run
```

A aplicacao sobe em:

```text
http://localhost:8080
```

## Endpoint

```http
GET /api/awards/intervals
```

Exemplo de resposta:

```json
{
  "min": [
    {
      "producer": "Joel Silver",
      "interval": 1,
      "previousWin": 1990,
      "followingWin": 1991
    }
  ],
  "max": [
    {
      "producer": "Matthew Vaughn",
      "interval": 13,
      "previousWin": 2002,
      "followingWin": 2015
    }
  ]
}
```

## Banco de dados

O projeto usa H2 em memoria. O arquivo `Movielist.csv` fica em `src/main/resources` e e importado automaticamente quando a aplicacao inicia.

Console H2:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:awardintervals
```
