<h1 align="center">
Fiap Phinder ⌚
</h1>

# 🖥️ Infraestrutura

<br>

### Infra criada no Openshift Container Platform

OpenShift é uma plataforma de contêineres baseada em Kubernetes, desenvolvida pela Red Hat. Ela simplifica o desenvolvimento, implantação e gerenciamento de aplicativos em contêineres, oferecendo recursos como escalabilidade automática, balanceamento de carga e provisionamento de serviços. O OpenShift fornece ferramentas para construção, implantação e operação de aplicativos em contêineres de forma rápida e consistente, facilitando a modernização de infraestruturas de TI e o desenvolvimento de aplicações em nuvem. É possível criar e implantar instâncias do OpenShift em várias plataformas de nuvem, incluindo AWS (Amazon Web Services), Azure (Microsoft Azure), Google Cloud Platform (GCP), IBM Cloud e outras.

<br>

### Aplicações criadas

![pod](/images/pods-openshift.png)

<br>

### Infra escalável

![pod](/images/scale-pod.png)

<br>

### Evidência do banco criado

![db](/images/evidência_db.png)

<br>

### Evidência de todos os recursos criados no Namespace do openshift:

![all](/images/all_resources.png)

<br>

### Endpoint da Aplicação

https://fiap-phinder-alissonskt-dev.apps.sandbox-m2.ll9k.p1.openshiftapps.com/

<br>
<br>

## 🔄️ CI/CD via Github Action

https://github.com/wienerdev/fiap-phinder/actions/runs/8406386739/job/23020206825

![cicd](/images/action.png)

<br>
<br>

## 🎈 Arquitetura Infra Openshift

Nossa infraestrutura no OpenShift consiste em DeploymentConfigs para configurar como as aplicações são implantadas, Services para expor e acessar essas aplicações, Routes para disponibilizar acesso externo via HTTP/HTTPS, e um banco de dados PostgreSQL em contêiner separado. 

Todos esses componentes são gerenciados pelo OpenShift, permitindo escalabilidade e automação de implantação.

![infra](/images/fiap-infra-phinder.drawio.png)

<br>
<br>

# 💡 Solução

<br>

## Desenho da Solução

### Desenho de solução MVP
![pod](/images/Mvp1.jpg)

### Desenho de solução evolutiva (fase 2)
![pod](/images/Mvp2.png)

<br>

## 🧮 Arquitetura da Aplicação

Utilizamos a <b>arquitetura em camadas</b>, que é um dos padrões de arquitetura de software mais tradicionais e amplamente adotados, especialmente em aplicações empresariais. A ideia é separar as responsabilidades do software em camadas distintas, cada uma com uma responsabilidade clara, promovendo assim a organização do código, a manutenção e a escalabilidade do sistema.

<br>

## ⚠ Pré-requisitos para execução do projeto

* Java 17 ou inferior
* Maven
* PostgreSQL

<br>

## 📌 Como utilizar?

Para utilizar o Fiap Phinder, é necessário ter uma instância de conexão do banco de dados ativa (no caso PostgreSQL), que por padrão fica na porta 5432, caso sua porta esteja diferente, especifique no application.yaml em:

```
spring.datasource.url=jdbc:mysql://localhost:<PORTA_BD>/fiap-pedidos?createDatabaseIfNotExist=true
spring.datasource.username=<USUARIO_BD>
spring.datasource.password=<SENHA_BD>
```

Com o banco de dados devidamente configurado, rode o [back-end da aplicação](https://github.com/wienerdev/sds) através do seguinte comando:

*Disponível em http://localhost:8080/

```
mvn spring-boot:run 
```

Com a aplicação rodando, acesse o localhost (porta 4200), e navegue pelo sistema!

<br>

## ⚠️ Links importantes

* [Referência para o padrão arquitetural REST](https://restfulapi.net/)
* [Palheta de atalhos de comandos do IntelliJ](https://resources.jetbrains.com/storage/products/intellij-idea/docs/IntelliJIDEA_ReferenceCard.pdf)
* [Site oficial do Spring](https://spring.io/)
* [Site oficial do Spring Initialzr para setup do projeto](https://start.spring.io/)
* [SDKMan! para gerenciamento e instalação do Java e Maven](https://sdkman.io/)


