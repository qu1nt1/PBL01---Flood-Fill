import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) throws Exception {

        BufferedImage img = ImageIO.read(new File("estrela.png"));

        JFrame frame = JanelaImagem.criarJanela(img);
        JanelaImagem painel = (JanelaImagem) frame.getContentPane().getComponent(0);
        new File("frames_fila").mkdirs();
        new File("frames_pilha").mkdirs();

//        FloodFill.preencherFila(img, 32, 203, Color.MAGENTA, painel);
        FloodFill.preencherPilha(img, 32, 203, Color.MAGENTA, painel);

        System.out.println("finalizado");
    }
}