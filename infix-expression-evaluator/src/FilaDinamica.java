public class FilaDinamica<T> implements Fila<T> {
    private No<T> inicio;
    private No<T> fim;
    private int tamanho;

    @Override
    public void inserir(T valor) {
        No<T> novoNo = new No<>(valor);
        if (estaVazia()) {
            inicio = novoNo;
            fim = novoNo;
        } else {
            fim.proximo = novoNo;
            fim = novoNo;
        }
        tamanho++;
    }

    @Override
    public T retirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia.");
        }
        T valor = inicio.valor;
        inicio = inicio.proximo;
        if (inicio == null) {
            fim = null;
        }
        tamanho--;
        return valor;
    }

    @Override
    public T frente() {
        if (estaVazia()) {
            throw new IllegalStateException("Fila vazia.");
        }
        return inicio.valor;
    }

    @Override
    public boolean estaVazia() {
        return inicio == null;
    }

    @Override
    public boolean estaCheia() {
        return false;
    }

    @Override
    public void liberar() {
        inicio = null;
        fim = null;
        tamanho = 0;
    }

    @Override
    public int tamanho() {
        return tamanho;
    }
}
