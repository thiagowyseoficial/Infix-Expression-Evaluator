public class PilhaEstatica<T> implements Pilha<T> {
    private final Object[] dados;
    private int topo;

    public PilhaEstatica(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade invalida.");
        }
        this.dados = new Object[capacidade];
        this.topo = -1;
    }

    @Override
    public void inserir(T valor) {
        if (estaCheia()) {
            throw new IllegalStateException("Pilha estatica cheia.");
        }
        dados[++topo] = valor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T retirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Pilha vazia.");
        }
        T valor = (T) dados[topo];
        dados[topo] = null;
        topo--;
        return valor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T topo() {
        if (estaVazia()) {
            throw new IllegalStateException("Pilha vazia.");
        }
        return (T) dados[topo];
    }

    @Override
    public boolean estaVazia() {
        return topo == -1;
    }

    @Override
    public boolean estaCheia() {
        return topo == dados.length - 1;
    }

    @Override
    public void liberar() {
        while (topo >= 0) {
            dados[topo] = null;
            topo--;
        }
    }

    @Override
    public int tamanho() {
        return topo + 1;
    }
}
