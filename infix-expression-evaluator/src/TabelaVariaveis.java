public class TabelaVariaveis {
    private final char[] nomes;
    private final double[] valores;
    private int quantidade;

    public TabelaVariaveis(int capacidade) {
        this.nomes = new char[capacidade];
        this.valores = new double[capacidade];
        this.quantidade = 0;
    }

    public void guardar(char variavel, double valor) {
        int indice = procurarIndice(variavel);
        if (indice >= 0) {
            valores[indice] = valor;
            return;
        }
        nomes[quantidade] = variavel;
        valores[quantidade] = valor;
        quantidade++;
    }

    public double obterValor(char variavel) throws ErroSintaxe {
        int indice = procurarIndice(variavel);
        if (indice < 0) {
            throw new ErroSintaxe("Variavel sem valor informado: '" + variavel + "'.");
        }
        return valores[indice];
    }

    private int procurarIndice(char variavel) {
        for (int i = 0; i < quantidade; i++) {
            if (nomes[i] == variavel) {
                return i;
            }
        }
        return -1;
    }
}
