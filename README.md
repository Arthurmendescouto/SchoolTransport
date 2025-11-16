# 🚌 Sistema de Transporte Escolar

Sistema de gerenciamento de transporte escolar desenvolvido em JavaFX para facilitar o controle de rotas, paradas, alunos, responsáveis e veículos.

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Como Executar](#-como-executar)
- [Documentação JavaDoc](#-documentação-javadoc)
- [Validações e Tratamento de Erros](#-validações-e-tratamento-de-erros)
- [Sistema de Registro de Presença e Faltas](#-sistema-de-registro-de-presença-e-faltas)
- [Estrutura de Classes](#-estrutura-de-classes)

## 🎯 Sobre o Projeto

O Sistema de Transporte Escolar é uma aplicação desktop desenvolvida em JavaFX que permite:

- Gerenciar paradas de ônibus escolares
- Cadastrar alunos, responsáveis e motoristas
- Controlar veículos e rotas
- Acompanhar o progresso das rotas em tempo real
- Visualizar paradas pendentes e concluídas
- Registrar presença/ausência de alunos durante o transporte
- Consultar faltas dos alunos vinculados ao responsável

## 🛠 Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **JavaFX 21** - Framework para interface gráfica
- **Maven** - Gerenciamento de dependências e build
- **FXML** - Definição de interfaces gráficas
- **CSS** - Estilização da interface

## ✨ Funcionalidades

### 👤 Autenticação
- Login para diferentes tipos de usuários (Administrador, Responsável, Aluno, Motorista)
- Redirecionamento automático baseado no tipo de usuário

### 🏢 Administrador
- Cadastro de paradas com validação completa
- Cadastro de alunos
- Cadastro de responsáveis
- Cadastro de veículos
- Cadastro de motoristas
- Cadastro de rotas
- Visualização de listas

### 🚗 Motorista
- Visualização da próxima parada
- Marcação de paradas como entregues
- Acompanhamento do progresso da rota
- Barra de progresso visual
- Contador de paradas entregues/pendentes
- **Registro de presença/ausência dos alunos**
- Lista de alunos com controle de presença em tempo real

### 👨‍👩‍👧‍👦 Responsável
- **Consulta de faltas dos alunos vinculados**
- Visualização de faltas agrupadas por aluno
- Exibição de datas das faltas
- Contador total de faltas
- Interface intuitiva com cards por aluno

### 📍 Paradas
- Cadastro com validação de campos
- Listagem de todas as paradas
- Remoção de paradas
- Status de entrega (pendente/concluída)

## 📁 Estrutura do Projeto

```
SchoolTransport/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/schooltransport/
│   │   │       ├── HelloApplication.java          # Classe principal
│   │   │       ├── Cadastro.java                  # Gerenciamento de paradas (Singleton)
│   │   │       ├── Parada.java                    # Modelo de parada
│   │   │       ├── controllers/                   # Controllers das telas
│   │   │       │   ├── LoginController.java
│   │   │       │   ├── PainelAdministradorController.java
│   │   │       │   ├── CadastrarParadaController.java
│   │   │       │   ├── CadastrarAlunoController.java
│   │   │       │   ├── CadastrarResponsavelController.java
│   │   │       │   ├── CadastrarVeiculoController.java
│   │   │       │   ├── CadastrarMotoristaController.java
│   │   │       │   ├── CadastrarRotaController.java
│   │   │       │   ├── ListaParadaController.java
│   │   │       │   ├── TelaMotoristaController.java
│   │   │       │   ├── ConsultarRotaAlunoController.java
│   │   │       │   ├── ConsultarRotaMotoristaController.java
│   │   │       │   └── ConsultarFaltasController.java    # Consulta de faltas
│   │   │       ├── model/                         # Modelos de dados
│   │   │       │   ├── Pessoa.java
│   │   │       │   ├── Aluno.java
│   │   │       │   ├── Responsavel.java
│   │   │       │   ├── Veiculo.java
│   │   │       │   ├── Rota.java
│   │   │       │   └── RegistroPresenca.java      # Registro de presença/falta
│   │   │       └── data/
│   │   │           └── Repositorio.java           # Repositório de dados
│   │   └── resources/
│   │       └── org/example/schooltransport/
│   │           ├── *.fxml                         # Arquivos de interface
│   │           ├── consultarFaltas.fxml            # Tela de consulta de faltas
│   │           └── styles.css                      # Estilos CSS
├── pom.xml                                        # Configuração Maven
├── mvnw                                           # Maven Wrapper (Linux/Mac)
├── mvnw.cmd                                       # Maven Wrapper (Windows)
└── README.md                                      # Este arquivo
```

## 📦 Pré-requisitos

- **Java JDK 21** ou superior
- **Maven** (opcional, o projeto inclui Maven Wrapper)

## 🚀 Como Executar

### Windows (PowerShell)

```powershell
# 1. Navegue até a pasta do projeto
cd C:\caminho\para\SchoolTransport

# 2. Execute a aplicação
.\mvnw.cmd clean javafx:run
```

### Linux/Mac

```bash
# 1. Navegue até a pasta do projeto
cd /caminho/para/SchoolTransport

# 2. Execute a aplicação
./mvnw clean javafx:run
```

### Se tiver Maven instalado

```bash
mvn clean javafx:run
```

## 📚 Documentação JavaDoc

O projeto possui documentação JavaDoc completa em todas as classes e métodos.

### Visualizar no IDE

**IntelliJ IDEA / Cursor:**
- Passe o mouse sobre qualquer classe ou método
- Ou pressione `Ctrl + Q` (Windows/Linux) ou `Cmd + J` (Mac)

**Visual Studio Code:**
- Passe o mouse sobre classes/métodos
- Ou use `Ctrl + Shift + O`

### Gerar Documentação HTML

**⚠️ IMPORTANTE:** Certifique-se de estar na **raiz do projeto** (pasta `SchoolTransport`), não dentro de `src`!

**Windows (PowerShell):**
```powershell
.\mvnw.cmd javadoc:javadoc
```

**Linux/Mac:**
```bash
./mvnw javadoc:javadoc
```

**Se tiver Maven instalado:**
```bash
mvn javadoc:javadoc
```

Após executar, a documentação será gerada em:
```
target/site/apidocs/index.html
```

Abra esse arquivo no seu navegador para ver toda a documentação formatada.

## ✅ Validações e Tratamento de Erros

### Cadastro de Paradas

O sistema possui validação completa de campos com mensagens de erro específicas:

#### Campos Obrigatórios
- **Nome da Parada**: Não pode estar vazio e não pode conter apenas números
- **CEP**: Obrigatório, deve conter exatamente 8 dígitos numéricos (aceita com ou sem hífen)
- **Logradouro**: Obrigatório
- **Número**: Obrigatório, deve conter apenas números (ex: 123 ou 123A)
- **Bairro**: Obrigatório
- **Cidade**: Obrigatória, não pode conter apenas números
- **Estado**: Obrigatório, deve ser sigla de 2 letras (ex: SP, RJ, MG)

#### Exemplos de Validações

✅ **CEP Válido:**
- `12345678`
- `12345-678`

❌ **CEP Inválido:**
- `12345` (menos de 8 dígitos)
- `123456789` (mais de 8 dígitos)
- `abc12345` (contém letras)

✅ **Número Válido:**
- `123`
- `123A`
- `456B`

❌ **Número Inválido:**
- `abc` (contém apenas letras)
- `12-34` (contém caracteres especiais)

✅ **Estado Válido:**
- `SP`
- `RJ`
- `MG`

❌ **Estado Inválido:**
- `S` (apenas 1 letra)
- `SPP` (mais de 2 letras)
- `12` (números)

### Mensagens de Erro

O sistema exibe mensagens de erro claras e específicas abaixo do botão "Concluir", indicando exatamente quais campos precisam ser corrigidos.

## 📊 Sistema de Registro de Presença e Faltas

O sistema possui um módulo completo para registro de presença dos alunos durante o transporte escolar.

### Como Funciona

1. **Registro pelo Motorista:**
   - O motorista visualiza a lista de alunos na tela de trabalho
   - Marca cada aluno como "Presente" ou "Ausente" usando o controle visual
   - Os registros são salvos automaticamente com data e hora

2. **Consulta pelo Responsável:**
   - O responsável faz login e é redirecionado para a tela de consulta de faltas
   - Visualiza todas as faltas dos alunos vinculados a ele
   - As faltas são agrupadas por aluno para melhor visualização
   - Cada card mostra o nome do aluno, quantidade de faltas e datas específicas

### Características

- **Registro Automático:** Cada marcação de presença/ausência é salva imediatamente
- **Data e Hora:** Cada registro inclui a data do dia em que foi feito
- **Filtragem por Responsável:** Cada responsável vê apenas as faltas dos seus alunos
- **Interface Intuitiva:** Cards visuais organizados por aluno
- **Tratamento de Erros:** Sistema robusto com verificações de null e tratamento de exceções

### Vínculo Aluno-Responsável

- Ao cadastrar um aluno, é necessário informar o nome do responsável
- O sistema vincula automaticamente o aluno ao responsável correspondente
- Responsáveis só visualizam faltas dos alunos vinculados a eles

## 🏗 Estrutura de Classes

### Classes Principais

- **`HelloApplication`**: Classe principal que inicia a aplicação JavaFX
- **`Cadastro`**: Singleton responsável pelo gerenciamento de paradas
- **`Parada`**: Modelo que representa uma parada de ônibus

### Modelos (model/)

- **`Pessoa`**: Classe base para pessoas (alunos, responsáveis, motoristas)
- **`Aluno`**: Representa um aluno do sistema
- **`Responsavel`**: Representa um responsável (pai/mãe/tutor)
- **`Veiculo`**: Representa um veículo (ônibus)
- **`Rota`**: Representa uma rota de transporte
- **`RegistroPresenca`**: Representa um registro de presença/falta de um aluno com data e status

### Controllers (controllers/)

- **`LoginController`**: Gerencia autenticação de usuários
- **`PainelAdministradorController`**: Painel principal do administrador
- **`CadastrarParadaController`**: Cadastro e validação de paradas
- **`CadastrarAlunoController`**: Cadastro de alunos
- **`CadastrarResponsavelController`**: Cadastro de responsáveis
- **`CadastrarVeiculoController`**: Cadastro de veículos
- **`CadastrarMotoristaController`**: Cadastro de motoristas
- **`CadastrarRotaController`**: Cadastro de rotas
- **`ListaParadaController`**: Listagem de paradas
- **`TelaMotoristaController`**: Interface do motorista com progresso da rota e registro de presença
- **`ConsultarRotaAlunoController`**: Consulta de rotas para alunos
- **`ConsultarRotaMotoristaController`**: Consulta de rotas para motoristas
- **`ConsultarFaltasController`**: Consulta de faltas dos alunos para responsáveis

### Dados (data/)

- **`Repositorio`**: Repositório centralizado para armazenamento de dados (alunos, responsáveis, veículos, rotas, registros de presença)
  - Métodos para gerenciar registros de presença
  - Busca de faltas por aluno
  - Busca de faltas por responsável
  - Busca de alunos por responsável

## 🎨 Interface Gráfica

A interface foi desenvolvida com JavaFX e FXML, utilizando CSS para estilização. O design é moderno e intuitivo, com:

- Cores temáticas (laranja/vermelho)
- Formulários com validação visual
- Barras de progresso para acompanhamento de rotas
- Mensagens de erro destacadas
- Navegação fluida entre telas
- Cards visuais para exibição de faltas
- Controles de presença com interface moderna (toggle switch)

## 📝 Notas Importantes

- A documentação JavaDoc está completa em todas as classes e métodos públicos
- Use `Ctrl + Q` no IDE para visualizar rapidamente a documentação
- O projeto utiliza Maven Wrapper, não sendo necessário ter Maven instalado
- Todas as validações são feitas no lado do cliente antes de processar os dados
- O sistema utiliza padrão Singleton para gerenciamento de paradas
- Os registros de presença são armazenados em memória (não persistem após fechar a aplicação)
- Para vincular um aluno a um responsável, informe o nome exato do responsável no cadastro do aluno

## 👥 Autores

- Sistema de Transporte Escolar
- Desenvolvido para disciplina de Estruturas de Dados

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos.

---

**Versão:** 1.0-SNAPSHOT  
**Última atualização:** 2024

