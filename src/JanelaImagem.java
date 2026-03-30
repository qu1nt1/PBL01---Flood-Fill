import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class JanelaImagem extends JPanel {

    private BufferedImage imagem;
    private int escala = 1;

    public JanelaImagem(BufferedImage img) {
        this.imagem = img;
    }

    public void setEscala(int escala) {
        if (escala < 1) {
            escala = 1;
        }
        this.escala = escala;
        revalidate();
        repaint();
    }

    public int getEscala() {
        return escala;
    }

    public Point viewParaImagem(Point p) {
        int x = p.x / escala;
        int y = p.y / escala;
        return new Point(x, y);
    }

    public void atualizarImagem(BufferedImage img) {
        //repaint só na EDT (thread correta do Swing)
        SwingUtilities.invokeLater(() -> {
            this.imagem = img;
            repaint();
        });
    }

    @Override
    public Dimension getPreferredSize() {
        if (imagem == null) {
            return super.getPreferredSize();
        }
        return new Dimension(imagem.getWidth() * escala, imagem.getHeight() * escala);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagem != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2.drawImage(imagem, 0, 0, imagem.getWidth() * escala, imagem.getHeight() * escala, null);
            g2.dispose();
        }
    }

    public static JFrame criarJanela(BufferedImage img) {
        JFrame frame = new JFrame("Flood Fill");
        JanelaImagem painel = new JanelaImagem(img);

        frame.add(painel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        return frame;
    }
}