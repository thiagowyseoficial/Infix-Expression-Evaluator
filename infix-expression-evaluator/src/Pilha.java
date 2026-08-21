public interface Pilha<T> {
    void inserir(T valor);
    T retirar();
    T topo();
    boolean estaVazia();
    boolean estaCheia();
    void liberar();
    int tamanho();
}
