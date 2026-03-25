import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class JanelaImagem extends JPanel {

    private BufferedImage imagem;

    public JanelaImagem(BufferedImage img) {
        this.imagem = img;
    }

    public void atualizarImagem(BufferedImage img) {
        //repaint só na EDT (thread correta do Swing)
        SwingUtilities.invokeLater(() -> {
            this.imagem = img;
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagem != null) {
            g.drawImage(imagem, 0, 0, null);
        }
    }

    public static JFrame criarJanela(BufferedImage img) {
        JFrame frame = new JFrame("Flood Fill");
        JanelaImagem painel = new JanelaImagem(img);

        frame.add(painel);
        frame.setSize(img.getWidth(), img.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }
}