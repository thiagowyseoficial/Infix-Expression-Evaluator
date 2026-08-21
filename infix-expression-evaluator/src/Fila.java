public interface Fila<T> {
    void inserir(T valor);
    T retirar();
    T frente();
    boolean estaVazia();
    boolean estaCheia();
    void liberar();
    int tamanho();
}
