public class ConversorExpressao {
    private final FabricaEstruturas fabrica;

    public ConversorExpressao(FabricaEstruturas fabrica) {
        this.fabrica = fabrica;
    }

    public ResultadoPosFixa converterParaPosFixa(String expressao) throws ErroSintaxe {
        if (expressao == null || expressao.trim().isEmpty()) {
            throw new ErroSintaxe("A expressao nao pode estar vazia.");
        }

        // A pilha guarda operadores e delimitadores; a fila monta a saida em pos-fixa.
        Pilha<Character> operadores = fabrica.criarPilha();
        Fila<String> saida = fabrica.criarFila();
        boolean[] variaveisUsadas = new boolean[256];
        char[] variaveis = new char[256];
        int quantidadeVariaveis = 0;
        // Controla se o proximo simbolo esperado e um operando ou um operador.
        boolean esperaOperando = true;

        for (int i = 0; i < expressao.length(); i++) {
            char simbolo = expressao.charAt(i);

            if (Character.isWhitespace(simbolo)) {
                continue;
            }

            if (Character.isDigit(simbolo)) {
                if (!esperaOperando) {
                    throw new ErroSintaxe("Falta operador antes de '" + simbolo + "'.");
                }
                saida.inserir(String.valueOf(simbolo));
                esperaOperando = false;
            } else if (Character.isLetter(simbolo)) {
                if (!esperaOperando) {
                    throw new ErroSintaxe("Falta operador antes de '" + simbolo + "'.");
                }
                saida.inserir(String.valueOf(simbolo));
                if (!variaveisUsadas[simbolo]) {
                    variaveisUsadas[simbolo] = true;
                    variaveis[quantidadeVariaveis++] = simbolo;
                }
                esperaOperando = false;
            } else if (ehAbertura(simbolo)) {
                if (!esperaOperando) {
                    throw new ErroSintaxe("Falta operador antes de '" + simbolo + "'.");
                }
                operadores.inserir(simbolo);
            } else if (ehFechamento(simbolo)) {
                if (esperaOperando) {
                    throw new ErroSintaxe("Fechamento inesperado: '" + simbolo + "'.");
                }
                // Ao fechar um bloco, desempilha tudo ate encontrar a abertura correspondente.
                processarFechamento(operadores, saida, simbolo);
                esperaOperando = false;
            } else if (ehOperador(simbolo)) {
                if (esperaOperando) {
                    throw new ErroSintaxe("Operador sem operando: '" + simbolo + "'.");
                }
                // Desempilha operadores mais fortes (ou de mesma prioridade, exceto potencia).
                while (!operadores.estaVazia() && ehOperador(operadores.topo())) {
                    char topo = operadores.topo();
                    if (temMaiorPrioridade(topo, simbolo)) {
                        saida.inserir(String.valueOf(operadores.retirar()));
                    } else {
                        break;
                    }
                }
                operadores.inserir(simbolo);
                esperaOperando = true;
            } else {
                throw new ErroSintaxe("Simbolo invalido encontrado: '" + simbolo + "'.");
            }
        }

        if (esperaOperando) {
            throw new ErroSintaxe("A expressao terminou de forma incompleta.");
        }

        // No final, descarrega o que sobrou na pilha para concluir a pos-fixa.
        while (!operadores.estaVazia()) {
            char topo = operadores.retirar();
            if (ehAbertura(topo)) {
                throw new ErroSintaxe("Delimitador de abertura sem fechamento: '" + topo + "'.");
            }
            saida.inserir(String.valueOf(topo));
        }

        char[] resultadoVariaveis = new char[quantidadeVariaveis];
        for (int i = 0; i < quantidadeVariaveis; i++) {
            resultadoVariaveis[i] = variaveis[i];
        }

        String posFixa = UtilFila.filaParaTexto(saida, fabrica);
        return new ResultadoPosFixa(saida, posFixa, resultadoVariaveis);
    }

    private void processarFechamento(Pilha<Character> operadores, Fila<String> saida, char fechamento) throws ErroSintaxe {
        boolean encontrouAbertura = false;

        while (!operadores.estaVazia()) {
            char topo = operadores.retirar();
            if (ehAbertura(topo)) {
                if (!combina(topo, fechamento)) {
                    throw new ErroSintaxe(
                        "Delimitadores em ordem incorreta: '" + topo + "' com '" + fechamento + "'."
                    );
                }
                encontrouAbertura = true;
                break;
            }
            saida.inserir(String.valueOf(topo));
        }

        if (!encontrouAbertura) {
            throw new ErroSintaxe("Delimitador de fechamento sem abertura: '" + fechamento + "'.");
        }
    }

    private boolean temMaiorPrioridade(char operadorPilha, char operadorAtual) {
        int prioridadePilha = prioridade(operadorPilha);
        int prioridadeAtual = prioridade(operadorAtual);

        if (prioridadePilha > prioridadeAtual) {
            return true;
        }

        return prioridadePilha == prioridadeAtual && operadorAtual != '^';
    }

    private int prioridade(char operador) {
        switch (operador) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return 0;
        }
    }

    private boolean ehOperador(char valor) {
        return valor == '+' || valor == '-' || valor == '*' || valor == '/' || valor == '^';
    }

    private boolean ehAbertura(char valor) {
        return valor == '(' || valor == '[' || valor == '{';
    }

    private boolean ehFechamento(char valor) {
        return valor == ')' || valor == ']' || valor == '}';
    }

    private boolean combina(char abertura, char fechamento) {
        return (abertura == '(' && fechamento == ')')
            || (abertura == '[' && fechamento == ']')
            || (abertura == '{' && fechamento == '}');
    }
}
