import javax.swing.SwingWorker;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;

public class FloodFillWorker extends SwingWorker<Void, Void> {
    private final BufferedImage original;
    private final int x;
    private final int y;
    private final Color novaCor;
    private final boolean usarPilha;
    private final JanelaImagem tela;

    public FloodFillWorker(BufferedImage original, int x, int y, Color novaCor, boolean usarPilha, JanelaImagem tela) {
        this.original = original;
        this.x = x;
        this.y = y;
        // Evita que o alpha escolhido pelo UI deixe o preenchimento “apagado/escuro”.
        this.novaCor = new Color(novaCor.getRed(), novaCor.getGreen(), novaCor.getBlue(), 255);
        this.usarPilha = usarPilha;
        this.tela = tela;
    }

    @Override
    protected Void doInBackground() {
        BufferedImage img = copiarImagem(original);

        try {
            if (usarPilha) {
                FloodFill.preencherPilha(img, x, y, novaCor, tela);
            } else {
                FloodFill.preencherFila(img, x, y, novaCor, tela);
            }
        } catch (Exception e) {
            // Mantém a UI responsiva; em caso de erro, registra o stacktrace.
            e.printStackTrace();
        }

        return null;
    }

    private static BufferedImage copiarImagem(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        // Garante um modelo de cor “universal” para que setRGB não converta a cor para tons de cinza.
        BufferedImage copia = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = copia.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return copia;
    }
}

