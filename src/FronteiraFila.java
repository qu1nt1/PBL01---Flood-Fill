public class FronteiraFila implements FronteiraFloodFill {
    private final Fila fila = new Fila();

    @Override
    public void adicionar(Pixel pixel) {
        fila.enqueue(pixel);
    }

    @Override
    public Pixel remover() {
        return fila.dequeue();
    }

    @Override
    public boolean estaVazia() {
        return fila.isEmpty();
    }
}
