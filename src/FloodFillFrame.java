import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FloodFillFrame extends JFrame {
    private final BufferedImage originalImage;
    private final JanelaImagem painelImagem;

    private final JRadioButton rbPilha;
    private final JRadioButton rbFila;
    private final JLabel labelX;
    private final JLabel labelY;
    private final JLabel instrucao;
    private final JButton btnExecutar;
    private final JPanel previewCor;
    private final JButton btnEscolherCor;

    private Integer selectedX;
    private Integer selectedY;
    private Color corSelecionada = Color.MAGENTA;

    public FloodFillFrame(BufferedImage originalImage) {
        super("Flood Fill");
        this.originalImage = originalImage;

        this.painelImagem = new JanelaImagem(originalImage);
        this.painelImagem.setEscala(calcularEscalaAutomatica(originalImage));
        this.rbPilha = new JRadioButton("Pilha (DFS)", true);
        this.rbFila = new JRadioButton("Fila (BFS)");
        this.labelX = new JLabel("x: não definido");
        this.labelY = new JLabel("y: não definido");
        this.instrucao = new JLabel("<html>Clique para escolher<br> a posição inicial</html>");
        this.btnExecutar = new JButton("Executar");
        this.previewCor = new JPanel();
        this.btnEscolherCor = new JButton("Escolher cor");

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

        previewCor.setBackground(corSelecionada);
        previewCor.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        previewCor.setMaximumSize(new Dimension(40, 24));
        previewCor.setPreferredSize(new Dimension(40, 24));
        previewCor.setMinimumSize(new Dimension(40, 24));

        btnEscolherCor.addActionListener(e -> escolherCor());

        JPanel linhaCor = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        linhaCor.setAlignmentX(Component.LEFT_ALIGNMENT);
        linhaCor.add(previewCor);
        linhaCor.add(btnEscolherCor);
        controles.add(linhaCor);

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
        Dimension pref = painelImagem.getPreferredSize();
        setMinimumSize(new Dimension(pref.width + 260, Math.max(pref.height, 240)));
    }

    private static int calcularEscalaAutomatica(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int maior = Math.max(w, h);

        if (maior >= 450) {
            return 1;
        }

        int alvo = 650;
        int escala = alvo / Math.max(1, maior);
        if (escala < 1) escala = 1;
        if (escala > 20) escala = 20;
        return escala;
    }

    private void escolherCor() {
        Color nova = JColorChooser.showDialog(this, "Escolher cor do preenchimento", corSelecionada);
        if (nova != null) {
            corSelecionada = nova;
            previewCor.setBackground(corSelecionada);
        }
    }

    private void registrarCliqueNaImagem() {
        painelImagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point p = painelImagem.viewParaImagem(e.getPoint());
                int x = p.x;
                int y = p.y;

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

        Color novaCor = corSelecionada;
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

