# Finance App 

> Um aplicativo desktop de gestão financeira pessoal focado em organização de despesas, categorização e geração de relatórios.

## Contexto Acadêmico: Aplicação do 2º Semestre

Este projeto foi desenvolvido como trabalho prático do **segundo semestre** do curso de Análise e Desenvolvimento de Sistemas. O objetivo principal foi consolidar os fundamentos teóricos de programação e modelagem, transformando-os em uma aplicação funcional.

Durante a construção deste sistema, os seguintes conhecimentos foram aplicados na prática:

* **Programação Orientada a Objetos (POO):** Estruturação do código utilizando classes, objetos e encapsulamento em Java.
* **Padrão de Arquitetura DAO:** Implementação do padrão *Data Access Object* (DAO) para separar claramente a lógica de interface (`src/ui`) da lógica de acesso ao banco de dados (`src/dao`), resultando em um código mais limpo e manutenível.
* **Integração com Banco de Dados (JDBC):** Conexão da aplicação Java com um banco de dados relacional (SQLite) para garantir a persistência de usuários, categorias e transações.
* **Interfaces Gráficas (GUI):** Desenvolvimento de janelas e formulários interativos utilizando Java Swing.
* **Consumo de Bibliotecas Externas:** Integração de bibliotecas de terceiros (.jar) para resolver problemas específicos, como o `JCalendar` para seleção de datas e o `iTextPDF` para a geração de relatórios exportáveis.

## Funcionalidades

* **Autenticação de Usuários:** Sistema de login e criação de novas contas (`LoginUserDAO`, `CriarUserDAO`).
* **Gestão de Despesas:** Registro e listagem de compras e gastos (`CompraDAO`).
* **Categorização:** Criação e gerenciamento de categorias para classificar as despesas (`CategoriaDAO`).
* **Relatórios:** Geração de relatórios em PDF para acompanhamento financeiro.
* **Interface Intuitiva:** Painéis organizados (Cards) e filtros de busca por data.

## Tecnologias Utilizadas

* **Linguagem:** Java
* **Interface:** Java Swing (AWT)
* **Banco de Dados:** SQLite (via `sqlite-jdbc`)
* **Bibliotecas Adicionais:**
  * `JCalendar` (Componentes de calendário para a interface)
  * `iTextPDF` (Geração de documentos PDF)

## Como Executar o Projeto

### Pré-requisitos
* Java Development Kit (JDK) instalado (versão 8 ou superior).
* Uma IDE de sua preferência (IntelliJ IDEA, Eclipse, NetBeans) ou configurar o classpath manualmente.

### Passos

1. Clone este repositório:
   ```bash
   git clone [https://github.com/JoaoZanelato/finance-app.git](https://github.com/JoaoZanelato/finance-app.git)
