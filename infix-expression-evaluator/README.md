# Infix Expression Evaluator

Aplicação Java de terminal que converte expressões matemáticas da notação infixa para a notação pós-fixa — também chamada de notação polonesa reversa — e calcula o resultado. O projeto permite executar o mesmo algoritmo com implementações estáticas ou dinâmicas de pilhas e filas.

> Trabalho da disciplina de Estruturas de Dados, desenvolvido durante a graduação em Ciência da Computação por Thiago Wyse dos Santos e Lucas Fernandes dos Santos.

## Visão geral

Expressões escritas na forma habitual dependem de precedência de operadores e delimitadores. O projeto transforma essa entrada em uma sequência pós-fixa, que pode ser avaliada de maneira direta com uma pilha.

A solução implementa seus próprios tipos abstratos de dados, em vez de utilizar as coleções prontas da biblioteca padrão. Assim, o mesmo fluxo de conversão e cálculo pode ser comparado usando armazenamento em arrays ou nós encadeados.

## Objetivo

Aplicar pilhas e filas à resolução de um problema de análise de expressões, contemplando:

- precedência e associatividade de operadores;
- validação de parênteses, colchetes e chaves;
- identificação e leitura de variáveis;
- conversão para notação pós-fixa;
- avaliação numérica da expressão convertida.

## Principais funcionalidades

- Escolha entre estruturas estáticas e dinâmicas no início da execução.
- Pilha estática baseada em array e pilha dinâmica encadeada.
- Fila circular estática baseada em array e fila dinâmica encadeada.
- Suporte aos operadores `+`, `-`, `*`, `/` e `^`.
- Suporte a operandos de um dígito e variáveis representadas por uma letra.
- Validação de símbolos, operadores, operandos e delimitadores.
- Detecção de divisão por zero.
- Leitura de valores reais para as variáveis encontradas.
- Testes para conversão, cálculo, validação sintática e ambas as estratégias de armazenamento.

## Tecnologias utilizadas

- Java
- API padrão do Java
- Aplicação de linha de comando

O projeto não utiliza dependências externas nem ferramenta de build.

## Conceitos acadêmicos aplicados

- Tipos abstratos de dados (TADs).
- Pilhas e filas.
- Fila circular.
- Listas simplesmente encadeadas por meio de nós.
- Generics e interfaces.
- Notação infixa e pós-fixa.
- Precedência e associatividade de operadores.
- Tratamento de exceções.
- Programação orientada a objetos.

## Estrutura do projeto

```text
infix-expression-evaluator/
├── src/
│   ├── Main.java                  # Interface de terminal
│   ├── ConversorExpressao.java    # Validação e conversão para pós-fixa
│   ├── CalculadoraPosFixa.java    # Avaliação da expressão convertida
│   ├── FabricaEstruturas.java     # Seleção das implementações
│   ├── Pilha*.java                # Interface e pilhas estática/dinâmica
│   ├── Fila*.java                 # Interface e filas estática/dinâmica
│   ├── TabelaVariaveis.java       # Associação entre variáveis e valores
│   └── Testes.java                # Casos de teste executáveis
└── README.md
```

## Como executar

### Pré-requisitos

- JDK instalado.
- `javac` e `java` disponíveis no terminal.

Os comandos abaixo devem ser executados dentro da pasta `infix-expression-evaluator`.

### Compilar

```bash
javac -encoding UTF-8 -d build src/*.java
```

### Iniciar a aplicação

```bash
java -cp build Main
```

O programa solicitará a estratégia de armazenamento, a expressão e os valores das variáveis utilizadas.

### Executar os testes

```bash
java -cp build Testes
```

Os testes cobrem as versões estática e dinâmica das estruturas.

## Exemplo

Entrada:

```text
3+{[5*a]-[b/(3+c)]}
```

Valores informados:

```text
a = 5
b = 8
c = 1
```

Expressão pós-fixa produzida:

```text
3 5 a * b 3 c + / - +
```

Resultado:

```text
26.0
```

## Aprendizados e desafios

O trabalho exercita a escolha da estrutura de dados adequada para cada etapa do processamento. A pilha mantém operadores e respeita suas prioridades durante a conversão; a fila conserva a ordem da expressão pós-fixa; e uma segunda pilha realiza a avaliação.

Um dos principais desafios é garantir que as duas estratégias de armazenamento apresentem o mesmo comportamento. Interfaces e uma fábrica de estruturas isolam essa escolha do algoritmo principal. A validação de delimitadores, operandos e operadores também evidencia a importância de tratar entradas inválidas antes do cálculo.

## Limitações atuais

- Números presentes diretamente na expressão são limitados a um dígito.
- Variáveis são representadas por uma única letra.
- Operadores unários, como o sinal negativo, não são interpretados.
- Os testes são executados por uma classe Java própria, sem framework externo.

## Demonstração

O repositório não contém capturas de tela ou gravações. A interação e os resultados podem ser observados diretamente no terminal seguindo as instruções de execução.

