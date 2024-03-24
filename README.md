# COLOCAR AQUI INFORMAÇÕES SOBRE ARQUITETURA DE CÓDIGO

# Infraestrutura

### Infra criada no Openshift Container Platform


OpenShift é uma plataforma de contêineres baseada em Kubernetes, desenvolvida pela Red Hat. Ela simplifica o desenvolvimento, implantação e gerenciamento de aplicativos em contêineres, oferecendo recursos como escalabilidade automática, balanceamento de carga e provisionamento de serviços. O OpenShift fornece ferramentas para construção, implantação e operação de aplicativos em contêineres de forma rápida e consistente, facilitando a modernização de infraestruturas de TI e o desenvolvimento de aplicações em nuvem.

é possível criar e implantar instâncias do OpenShift em várias plataformas de nuvem, incluindo AWS (Amazon Web Services), Azure (Microsoft Azure), Google Cloud Platform (GCP), IBM Cloud e outras.

### Aplicações criadas

![pod](/images/pods-openshift.png)

### Infra escalável

![pod](/images/scale-pod.png)

### Evidência do banco criado

![db](/images/evidência_db.png)

### evidência de todos os recursos criados no Namespace do openshift:

![all](/images/all_resources.png)

## Evidência execução CI/CD via Github Action

https://github.com/wienerdev/fiap-phinder/actions/runs/8406386739/job/23020206825

![cicd](/images/action.png)

## Arquitetura Infra Openshift

Nossa infraestrutura no OpenShift consiste em DeploymentConfigs para configurar como as aplicações são implantadas, Services para expor e acessar essas aplicações, Routes para disponibilizar acesso externo via HTTP/HTTPS, e um banco de dados PostgreSQL em contêiner separado. 

Todos esses componentes são gerenciados pelo OpenShift, permitindo escalabilidade e automação de implantação.

![infra](/images/fiap-infra-phinder.drawio.png)
