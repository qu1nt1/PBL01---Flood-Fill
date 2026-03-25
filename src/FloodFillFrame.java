import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FloodFillFrame extends JFrame {
    private final BufferedImage originalImage;
    private final JanelaImagem painelImagem;

    private final JColorChooser chooser;
    private final JRadioButton rbPilha;
    private final JRadioButton rbFila;
    private final JLabel labelX;
    private final JLabel labelY;
    private final JLabel instrucao;
    private final JButton btnExecutar;

    private Integer selectedX;
    private Integer selectedY;

    public FloodFillFrame(BufferedImage originalImage) {
        super("Flood Fill");
        this.originalImage = originalImage;

        this.painelImagem = new JanelaImagem(originalImage);
        this.chooser = new JColorChooser(Color.MAGENTA);
        this.rbPilha = new JRadioButton("Pilha (DFS)", true);
        this.rbFila = new JRadioButton("Fila (BFS)");
        this.labelX = new JLabel("x: não definido");
        this.labelY = new JLabel("y: não definido");
        this.instrucao = new JLabel("<html>Clique para escolher<br> a posição inicial</html>");
        this.btnExecutar = new JButton("Executar");

        configurarUI();
        registrarCliqueNaImagem();
    }

    private void configurarUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(painelImagem, BorderLayout.CENTER);

        JPanel controles = new JPanel();
        controles.setLayout(new BoxLayout(controles, BoxLayout.Y_AXIS));
        controles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        controles.add(instrucao);

        controles.add(Box.createVerticalStrut(10));
        controles.add(new JLabel("Cor nova:"));

        // Deixa o chooser rolável quando a janela é redimensionada.
        JScrollPane chooserScroll = new JScrollPane(chooser);
        chooserScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        controles.add(chooserScroll);

        ButtonGroup metodoGroup = new ButtonGroup();
        metodoGroup.add(rbPilha);
        metodoGroup.add(rbFila);

        JPanel metodoPanel = new JPanel();
        metodoPanel.setLayout(new BoxLayout(metodoPanel, BoxLayout.Y_AXIS));
        metodoPanel.add(rbPilha);
        metodoPanel.add(rbFila);

        controles.add(Box.createVerticalStrut(10));
        controles.add(new JLabel("Método:"));
        controles.add(metodoPanel);

        controles.add(Box.createVerticalStrut(10));
        controles.add(labelX);
        controles.add(labelY);

        controles.add(Box.createVerticalStrut(10));
        btnExecutar.addActionListener(e -> executar());
        controles.add(btnExecutar);

        add(controles, BorderLayout.EAST);

        panelSizeHints();

        pack();
        setLocationRelativeTo(null);
    }

    private void panelSizeHints() {
        painelImagem.setPreferredSize(new Dimension(originalImage.getWidth(), originalImage.getHeight()));
        chooser.setPreferredSize(new Dimension(220, 180));
        setMinimumSize(new Dimension(originalImage.getWidth() + 260, originalImage.getHeight()));
    }

    private void registrarCliqueNaImagem() {
        painelImagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if (x < 0 || y < 0 || x >= originalImage.getWidth() || y >= originalImage.getHeight()) {
                    return;
                }

                selectedX = x;
                selectedY = y;
                labelX.setText("x: " + x);
                labelY.setText("y: " + y);
            }
        });
    }

    private void executar() {
        if (selectedX == null || selectedY == null) {
            JOptionPane.showMessageDialog(this, "Clique na imagem para selecionar o pixel inicial (x,y).");
            return;
        }

        Color novaCor = chooser.getColor();
        boolean usarPilha = rbPilha.isSelected();

        btnExecutar.setEnabled(false);

        FloodFillWorker worker = new FloodFillWorker(originalImage, selectedX, selectedY, novaCor, usarPilha, painelImagem) {
            @Override
            protected void done() {
                btnExecutar.setEnabled(true);
            }
        };
        worker.execute();
    }
}

