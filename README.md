# contact-managment-api

Esta é uma API RESTful para gerenciamento de contatos, desenvolvida com as seguintes tecnologias:

- **Swagger**: Utilizamos o Swagger para documentar e testar facilmente os endpoints da API. Você pode acessar a documentação [aqui](http://ec2-3-22-81-23.us-east-2.compute.amazonaws.com/swagger-doc/webjars/swagger-ui/index.html).

- **Spring Webflux**: O Spring Webflux é um framework do ecossistema Spring que oferece suporte à programação reativa. Isso significa que a API pode lidar com um grande número de solicitações concorrentes de forma eficiente, tornando-a escalável.

- **PostgreSQL com Docker**: Utilizamos o PostgreSQL como banco de dados para armazenar informações de contato. O uso do Docker facilita a configuração e a implantação do banco de dados em qualquer ambiente.

- **Clean Architecture**: Adotamos o Clean Architecture como padrão de projeto. Ele promove a separação clara de responsabilidades em camadas (Controladores, Casos de Uso, Entidades, etc.), tornando o código mais organizado, testável e de fácil manutenção.

## Resumo das Tecnologias

- **Swagger**: Uma ferramenta de código aberto que facilita a documentação, teste e consumo de APIs REST. A documentação gerada é interativa e amigável para os desenvolvedores.

- **Spring Webflux**: Uma extensão do Spring Framework que oferece suporte à programação reativa, permitindo que a API lide eficazmente com eventos concorrentes, tornando-a altamente escalável.

- **PostgreSQL com Docker**: O PostgreSQL é um sistema de gerenciamento de banco de dados relacional de código aberto. O uso do Docker simplifica a configuração e implantação do banco de dados em contêineres isolados.

- **Clean Architecture**: Uma abordagem de design de software que enfatiza a separação de responsabilidades em camadas bem definidas, tornando o código modular e mais fácil de manter e testar.

- **SOLID**: Além disso, seguimos os princípios SOLID de design de software para garantir que nossa aplicação seja escalável, de fácil manutenção e extensível.

## Instalação e Uso

Para implantar esta API localmente ou em outro ambiente, siga as instruções abaixo:

1. Clone este repositório.

2. Certifique-se de que o Docker esteja instalado em sua máquina.

3. Execute o PostgreSQL em um contêiner Docker:

   ```shell
   docker run -d -p 5432:5432 --name postgres-container -e POSTGRES_PASSWORD=sua_senha -e POSTGRES_DB=contatosdb postgres:latest
   ```

4. Configure as informações de conexão com o PostgreSQL no arquivo de propriedades da aplicação.

5. Compile e execute a aplicação Spring Boot.

6. Acesse a documentação da API através do Swagger UI [aqui](http://ec2-3-22-81-23.us-east-2.compute.amazonaws.com/swagger-doc/webjars/swagger-ui/index.html).

7. Comece a usar a API para gerenciar contatos.

## Contribuições

Fique à vontade para contribuir com este projeto. Siga as diretrizes de contribuição do projeto e envie pull requests com melhorias ou correções.
