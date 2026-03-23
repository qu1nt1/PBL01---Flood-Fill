public class Pilha {
    private No topo;

    public void push(Pixel p) {
        No novo = new No(p);
        novo.proximo = topo;
        topo = novo;
    }

    public Pixel pop() {
        if (topo == null) return null;

        Pixel valor = topo.valor;
        topo = topo.proximo;
        return valor;
    }

    public boolean isEmpty() {
        return topo == null;
    }
}