
<h1 align="center" style="font-weight: bold;">Cardápio Digital 🍔📱</h1>

<p align="center">
  <a href="#tech">Technologies</a> • 
  <a href="#started">Let's Start</a> • 
  <a href="#features">Features</a> • 
  <a href="#colab">Collaborators</a> • 
  <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>API e sistema para gerenciamento de cardápio digital em restaurantes, feito com Spring Boot e Angular.</b>
</p>

<h2 id="tech">💻 Technologies</h2>
<ul>
  <li>Java 17</li>
  <li>Spring Boot</li>
  <li>Spring Data JPA</li>
  <li>MySQL</li>
  <li>Hibernate</li>
  <li>Angular 17 (frontend)</li>
  <li>Swagger OpenAPI 3</li>
</ul>

<p align="center">
  <a href="https://skillicons.dev/icons?i=java,spring,mysql,angular">
    <img src="https://skillicons.dev/icons?i=java,spring,mysql,angular" alt="Technologies">
  </a>
</p>

<h2 id="started">🚀 Let's Start</h2>
<p>Passo a passo para rodar o projeto localmente.</p>

<h3>Pré-requisitos</h3>
<ul>
  <li>Java 17</li>
  <li>MySQL 8+</li>
  <li>Node.js e Angular CLI (para o front)</li>
</ul>

<h3>Clonando o Repositório</h3>
<pre><code>git clone https://github.com/CeloHelp/cardapio-digital.git</code></pre>

<h3>Configurando Banco de Dados</h3>
<p>Configure seu <code>application.properties</code>:</p>
<pre><code>
spring.datasource.url=jdbc:mysql://localhost:3306/cardapio_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
</code></pre>

<h3>Rodando o Projeto</h3>
<ol>
  <li>Compile com <code>./gradlew build</code> (ou via sua IDE)</li>
  <li>Rode com <code>./gradlew bootRun</code></li>
  <li>Abra <code>http://localhost:8080/swagger-ui.html</code> para visualizar a documentação.</li>
  <li>Rode o frontend com <code>ng serve</code> na pasta <code>cardapio-digital-front</code></li>
</ol>

<h2 id="features">📍 Features</h2>
<ul>
  <li>Cadastro, edição e inativação de produtos, categorias, pedidos e clientes.</li>
  <li>Listagem de pedidos por cliente.</li>
  <li>Atualização de status dos pedidos e controle de fluxo de pedidos.</li>
  <li>Swagger UI para testes de requisições.</li>
  <li>Boas práticas de organização de pacotes, SOLID e versionamento com Git.</li>
</ul>

<h2 id="contribute">📫 Contribute</h2>
<ol>
  <li><code>git clone https://github.com/CeloHelp/cardapio-digital.git</code></li>
  <li><code>git checkout -b feature/NOME_DA_FEATURE</code></li>
  <li>Comite seguindo boas práticas</li>
  <li>Abra um Pull Request explicando sua contribuição!</li>
</ol>

<h3>Links úteis</h3>
<ul>
  <li><a href="https://spring.io/guides">📖 Spring Boot Docs</a></li>
  <li><a href="https://angular.io/docs">📘 Angular Docs</a></li>
  <li><a href="https://swagger.io/specification/">📚 Swagger OpenAPI</a></li>
</ul>
