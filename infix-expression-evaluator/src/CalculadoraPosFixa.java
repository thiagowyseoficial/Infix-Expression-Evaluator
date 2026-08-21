public class CalculadoraPosFixa {
    private final FabricaEstruturas fabrica;

    public CalculadoraPosFixa(FabricaEstruturas fabrica) {
        this.fabrica = fabrica;
    }

    public double calcular(Fila<String> filaPosFixa, TabelaVariaveis tabelaVariaveis) throws ErroSintaxe {
        // Copiamos a fila para avaliar sem alterar a expressao original.
        Fila<String> filaAuxiliar = UtilFila.copiarFila(filaPosFixa, fabrica);
        Pilha<Double> valores = fabrica.criarPilha();

        while (!filaAuxiliar.estaVazia()) {
            String item = filaAuxiliar.retirar();
            char simbolo = item.charAt(0);

            if (item.length() == 1 && Character.isDigit(simbolo)) {
                valores.inserir((double) (simbolo - '0'));
            } else if (item.length() == 1 && Character.isLetter(simbolo)) {
                valores.inserir(tabelaVariaveis.obterValor(simbolo));
            } else if (item.length() == 1 && ehOperador(simbolo)) {
                if (valores.tamanho() < 2) {
                    throw new ErroSintaxe("Nao ha operandos suficientes para o operador '" + simbolo + "'.");
                }
                // Em pos-fixa, o operador usa sempre os dois ultimos valores empilhados.
                double direita = valores.retirar();
                double esquerda = valores.retirar();
                valores.inserir(aplicarOperador(esquerda, direita, simbolo));
            } else {
                throw new ErroSintaxe("Token invalido na avaliacao: " + item);
            }
        }

        // Uma expressao valida termina com exatamente um resultado na pilha.
        if (valores.tamanho() != 1) {
            throw new ErroSintaxe("A expressao pos-fixa gerou uma pilha final invalida.");
        }

        return valores.retirar();
    }

    private boolean ehOperador(char valor) {
        return valor == '+' || valor == '-' || valor == '*' || valor == '/' || valor == '^';
    }

    private double aplicarOperador(double esquerda, double direita, char operador) throws ErroSintaxe {
        switch (operador) {
            case '+':
                return esquerda + direita;
            case '-':
                return esquerda - direita;
            case '*':
                return esquerda * direita;
            case '/':
                if (direita == 0) {
                    throw new ErroSintaxe("Divisao por zero.");
                }
                return esquerda / direita;
            case '^':
                return Math.pow(esquerda, direita);
            default:
                throw new ErroSintaxe("Operador invalido: " + operador);
        }
    }
}
