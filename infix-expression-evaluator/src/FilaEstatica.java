public class FilaEstatica<T> implements Fila<T> {
    private final Object[] dados;
    private int inicio;
    private int fim;
    private int tamanho;

    public FilaEstatica(int capacidade) {
        if (capacidade <= 0) {
            throw new IllegalArgumentException("Capacidade invalida.");
        }
        this.dados = new Object[capacidade];
        this.inicio = 0;
        this.fim = 0;
        this.tamanho = 0;
    }

    @Override
    public void inserir(T valor) {
        if (estaCheia()) {
            throw new IllegalStateException("Fila estatica cheia.");
        }
        dados[fim] = valor;
        fim = (fim + 1) % dados.length;
        tamanho++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T retirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia.");
        }
        T valor = (T) dados[inicio];
        dados[inicio] = null;
        inicio = (inicio + 1) % dados.length;
        tamanho--;
        return valor;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T frente() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia.");
        }
        return (T) dados[inicio];
    }

    @Override
    public boolean estaVazia() {
        return tamanho == 0;
    }

    @Override
    public boolean estaCheia() {
        return tamanho == dados.length;
    }

    @Override
    public void liberar() {
        while (!estaVazia()) {
            retirar();
        }
    }

    @Override
    public int tamanho() {
        return tamanho;
    }
}
