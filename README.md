# CardNest API — Spring Boot

API REST em **Java 17 e Spring Boot 3** com autenticação JWT e controle de acesso por perfil. Foi construída durante a disciplina de desenvolvimento de back-end do curso de Tecnologia em Sistemas para Internet (IFSul) e reproduz o domínio do [CardNest](https://card-nest.vercel.app): usuários, partidas, fórum, patch notes e denúncias.

> Projeto acadêmico, criado para aprender Spring Boot. A API em produção do CardNest é a versão em Laravel: [BlackJack2_Back](https://github.com/derickbit/BlackJack2_Back).

## O que tem

- **Autenticação JWT:** login em `POST /api/v1/login` devolve um token com validade de 2 horas.
- **Perfis de acesso:** `ROLE_ADMIN` e `ROLE_USER`, com `@Secured` nas operações sensíveis.
- **CRUD de usuários** com senha criptografada (BCrypt) e respostas por DTO, sem expor a senha.
- **Spring Data REST** para as demais entidades (partidas, fórum, patch notes, denúncias, mensagens), todas exigindo autenticação.
- **Documentação interativa** com Swagger/OpenAPI.
- **Testes de integração** com banco H2 em memória.
- **Docker Compose** com MariaDB para desenvolvimento.

## Stack

| | |
|---|---|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.2 (Web, Data JPA, Data REST, Validation, Security) |
| Autenticação | JWT (`java-jwt`) e Spring Security |
| Banco de dados | MariaDB (desenvolvimento), H2 (testes) |
| Documentação | springdoc-openapi (Swagger UI) |
| Build | Gradle |
| Outros | Lombok, JUnit 5 |

## Estrutura

```
src/main/java/.../tads_springboot/
├── autenticacao/   # Login: controller, serviço e DTOs
├── infra/security/ # Configuração do Spring Security, filtro e serviço JWT
├── user/           # Usuário, perfis, controller e DTOs
├── partida/ forumTopic/ forumComment/ patchNote/ report/ message/   # Entidades e repositórios
src/test/           # Testes de integração (H2)
api_tests.http      # Roteiro de requisições para o cliente HTTP do IDE
```

## Como rodar

Pré-requisitos: JDK 17 e Docker.

```bash
git clone https://github.com/derickbit/tads-springboot-api.git
cd tads-springboot-api
docker compose up -d                       # MariaDB em localhost:3306
export JWT_SECRET="gere-um-texto-aleatorio-com-32-caracteres-ou-mais"
./gradlew bootRun
```

No Windows (PowerShell): `$env:JWT_SECRET="..."` antes do `.\gradlew bootRun`.

A aplicação não tem segredo padrão: sem `JWT_SECRET` ela não inicia, de propósito.

Variáveis opcionais: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` (os padrões servem para o `docker-compose.yml`).

### Primeiro usuário

Como não existe cadastro público, crie os perfis e um administrador direto no banco (a senha do exemplo é `admin`):

```sql
INSERT INTO perfis (nome) VALUES ('ROLE_ADMIN'), ('ROLE_USER');
INSERT INTO users (name, email, password_hash)
  VALUES ('Admin', 'admin@email.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00dmxs.AQiy3.a');
INSERT INTO users_perfis (user_id, perfil_id)
  SELECT u.id, p.id FROM users u, perfis p WHERE u.email = 'admin@email.com' AND p.nome = 'ROLE_ADMIN';
```

Troque a senha em qualquer ambiente que não seja o seu computador.

### Usando a API

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- Faça login em `POST /api/v1/login` com `{"email": "...", "senha": "..."}` e envie o token em `Authorization: Bearer <token>`.
- O arquivo [`api_tests.http`](api_tests.http) traz um roteiro pronto: login, listagem, criação, edição e exclusão.

| Método | Rota | Acesso |
|---|---|---|
| POST | `/api/v1/login` | Público |
| GET | `/api/v1/users`, `/api/v1/users/{id}` | Autenticado |
| POST | `/api/v1/users` | Administrador |
| PUT | `/api/v1/users/{id}` | Administrador |
| DELETE | `/api/v1/users/{id}` | Administrador |

## Testes

```bash
./gradlew test
```

Os testes usam o perfil `test` (H2 em memória e um segredo próprio), então não precisam do Docker.

## Autor

**Dérick Bitencourte da Silva** · [GitHub](https://github.com/derickbit) · [LinkedIn](https://www.linkedin.com/in/derick-bitencourte-da-silva/)
