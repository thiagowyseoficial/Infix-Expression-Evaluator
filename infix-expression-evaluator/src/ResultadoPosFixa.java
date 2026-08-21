public class ResultadoPosFixa {
    private final Fila<String> filaPosFixa;
    private final String expressaoPosFixa;
    private final char[] variaveis;

    public ResultadoPosFixa(Fila<String> filaPosFixa, String expressaoPosFixa, char[] variaveis) {
        this.filaPosFixa = filaPosFixa;
        this.expressaoPosFixa = expressaoPosFixa;
        this.variaveis = variaveis;
    }

    public Fila<String> getFilaPosFixa() {
        return filaPosFixa;
    }

    public String getExpressaoPosFixa() {
        return expressaoPosFixa;
    }

    public char[] getVariaveis() {
        return variaveis;
    }
}
