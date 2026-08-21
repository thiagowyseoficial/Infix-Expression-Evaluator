public class PilhaDinamica<T> implements Pilha<T> {
    private No<T> topo;
    private int tamanho;

    @Override
    public void inserir(T valor) {
        No<T> novoNo = new No<>(valor);
        novoNo.proximo = topo;
        topo = novoNo;
        tamanho++;
    }

    @Override
    public T retirar() {
        if (estaVazia()) {
            throw new IllegalStateException("Pilha vazia.");
        }
        T valor = topo.valor;
        topo = topo.proximo;
        tamanho--;
        return valor;
    }

    @Override
    public T topo() {
        if (estaVazia()) {
            throw new IllegalStateException("Pilha vazia.");
        }
        return topo.valor;
    }

    @Override
    public boolean estaVazia() {
        return topo == null;
    }

    @Override
    public boolean estaCheia() {
        return false;
    }

    @Override
    public void liberar() {
        topo = null;
        tamanho = 0;
    }

    @Override
    public int tamanho() {
        return tamanho;
    }
}
