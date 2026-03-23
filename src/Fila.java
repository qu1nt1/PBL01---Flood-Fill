public class Fila {
    private No inicio;
    private No fim;

    public void enqueue(Pixel p) {
        No novo = new No(p);

        if (fim != null) {
            fim.proximo = novo;
        }

        fim = novo;

        if (inicio == null) {
            inicio = novo;
        }
    }

    public Pixel dequeue() {
        if (inicio == null) return null;

        Pixel valor = inicio.valor;
        inicio = inicio.proximo;

        if (inicio == null) {
            fim = null;
        }

        return valor;
    }

    public boolean isEmpty() {
        return inicio == null;
    }
}