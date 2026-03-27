public class FronteiraPilha implements FronteiraFloodFill {
    private final Pilha pilha = new Pilha();

    @Override
    public void adicionar(Pixel pixel) {
        pilha.push(pixel);
    }

    @Override
    public Pixel remover() {
        return pilha.pop();
    }

    @Override
    public boolean estaVazia() {
        return pilha.isEmpty();
    }
}
