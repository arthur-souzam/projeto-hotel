# 🏨 Hotel IFSC
Sistema de gestão hoteleira em Java, desenvolvido durante o curso de Análise e Desenvolvimento de Sistemas do IFSC (4º semestre). O projeto utiliza a arquitetura MVC para fornecer uma interface desktop para a gestão completa de entidades hoteleiras.

---

## 📋 Visão Geral
O Hotel IFSC provê uma interface desktop para cadastro, listagem, edição e exclusão de entidades como hóspedes, funcionários, quartos e veículos. O foco do projeto é a implementação de um sistema robusto com o ciclo CRUD completo (View, Controller, Service, DAO) e conexão funcional com banco de dados.

---

## ⚙️ Funcionalidades
Telas de Cadastro, Listagem, Edição e Exclusão (CRUD) para as principais entidades.

Interface gráfica intuitiva para gerenciamento de dados.

Validações de campos e uso de máscaras para formatação.

Herança de modelos para reutilização de código (ex.: Hospede herda de Pessoa).

Layout padronizado para as telas de cadastro e busca.

Conexão com banco de dados relacional para persistência de dados.

---

## 💻 Tecnologias
Linguagem: Java 8+

GUI: Swing

Banco de Dados: MySQL

IDE: Netbeans (recomendado) ou outra compatível

Arquitetura: Model-View-Controller (MVC) com camadas de Serviço (Service) e Acesso a Dados (DAO)

---

## 🏁 Instalação e Execução

1. **Clone o repositório**  
   ```bash
   git clone https://github.com/cauamv/projeto-hotel-ifsc.git
   cd projeto-hotel-ifsc
   ```
2. **Importe no Netbeans**  
   - Selecione a pasta do projeto como um projeto Java.
3. **Execute a classe principal**  
   - Navegue até `src/main/java/main/TelaMenuPrincipal.java`.
   - Execute o método para abrir a tela principal.

---

## 📐 Diagrama de Classes
Abaixo está o diagrama de classes atual do sistema Hotel IFSC:

![Diagrama de Classes](https://i.postimg.cc/26q12Hvs/Diagram-de-Classes-Hotel.jpg)

## 📌 Observações
Dados são persistidos em um banco de dados MySQL.

O projeto está em desenvolvimento contínuo; novas entidades e funcionalidades serão adicionadas.

## ✍️ Créditos
Projeto sendo desenvolvido por [**Cauã de Moraes Vieira**](https://www.linkedin.com/in/cauamv/) e [**Arthur Souza Mendes**](https://www.linkedin.com/in/arthur-souza-mendes-078653252/).  
