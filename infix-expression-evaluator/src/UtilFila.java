public class UtilFila {
    private UtilFila() {
    }

    public static <T> Fila<T> copiarFila(Fila<T> original, FabricaEstruturas fabrica) {
        Fila<T> copia = fabrica.criarFila();
        int tamanho = original.tamanho();

        for (int i = 0; i < tamanho; i++) {
            T item = original.retirar();
            original.inserir(item);
            copia.inserir(item);
        }

        return copia;
    }

    public static String filaParaTexto(Fila<String> fila, FabricaEstruturas fabrica) {
        Fila<String> copia = copiarFila(fila, fabrica);
        StringBuilder texto = new StringBuilder();

        while (!copia.estaVazia()) {
            if (texto.length() > 0) {
                texto.append(' ');
            }
            texto.append(copia.retirar());
        }

        return texto.toString();
    }
}
