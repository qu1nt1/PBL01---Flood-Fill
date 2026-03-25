import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) throws Exception {
        //arquivo PNG na raiz do projeto (troque o nome se precisar)
        BufferedImage img = ImageIO.read(new File("estrela.png"));

        SwingUtilities.invokeLater(() -> {
            FloodFillFrame frame = new FloodFillFrame(img);
            frame.setVisible(true);
        });
    }
}