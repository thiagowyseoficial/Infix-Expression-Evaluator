public class FabricaEstruturas {
    private final boolean usarEstatica;
    private final int capacidade;

    public FabricaEstruturas(boolean usarEstatica, int capacidade) {
        this.usarEstatica = usarEstatica;
        this.capacidade = capacidade;
    }

    public <T> Pilha<T> criarPilha() {
        if (usarEstatica) {
            return new PilhaEstatica<>(capacidade);
        }
        return new PilhaDinamica<>();
    }

    public <T> Fila<T> criarFila() {
        if (usarEstatica) {
            return new FilaEstatica<>(capacidade);
        }
        return new FilaDinamica<>();
    }
}
