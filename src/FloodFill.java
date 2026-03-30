import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class FloodFill {

    //BFS: vizinhos 4 sentidos; fila = FIFO (largura)
    public static void preencherFila(BufferedImage img, int x, int y, Color novaCor, JanelaImagem tela) throws Exception {
        preencher(
                img,
                x,
                y,
                novaCor,
                tela,
                new FronteiraFila(),
                false,
                "frames_fila",
                "resultado_fila.png",
                false
        );
    }

    //DFS: empilha vizinhos; visitado[][] evita reprocessar o mesmo (x,y)
    public static void preencherPilha(BufferedImage img, int x, int y, Color novaCor, JanelaImagem tela) {
        try {
            preencher(
                    img,
                    x,
                    y,
                    novaCor,
                    tela,
                    new FronteiraPilha(),
                    true,
                    "frames_pilha",
                    "resultado_pilha.png",
                    true
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void preencher(
            BufferedImage img,
            int x,
            int y,
            Color novaCor,
            JanelaImagem tela,
            FronteiraFloodFill fronteira,
            boolean usarVisitado,
            String pastaFrames,
            String arquivoResultado,
            boolean criarPastaFrames
    ) throws Exception {
        int largura = img.getWidth();
        int altura = img.getHeight();
        int corOriginal = img.getRGB(x, y);
        int novaCorRGB = novaCor.getRGB();

        boolean[][] visitado = usarVisitado ? new boolean[altura][largura] : null;
        fronteira.adicionar(new Pixel(x, y));

        int frame = 0;
        while (!fronteira.estaVazia()) {
            Pixel p = fronteira.remover();
            if (p == null) {
                continue;
            }

            int px = p.x;
            int py = p.y;
            if (foraDaImagem(px, py, largura, altura)) {
                continue;
            }

            if (usarVisitado && jaVisitado(visitado, px, py)) {
                continue;
            }

            if (img.getRGB(px, py) != corOriginal) {
                continue;
            }

            img.setRGB(px, py, novaCorRGB);
            if (tela != null) {
                tela.atualizarImagem(img);
            }

            salvarFrameSeNecessario(img, frame, pastaFrames, criarPastaFrames);
            frame++;

            //ms por pixel(0 é o mais rápido)
            Thread.sleep(1);
            adicionarVizinhos(px, py, fronteira);
        }

        salvarResultado(img, arquivoResultado);
    }

    private static boolean foraDaImagem(int x, int y, int largura, int altura) {
        return x < 0 || y < 0 || x >= largura || y >= altura;
    }

    private static boolean jaVisitado(boolean[][] visitado, int x, int y) {
        if (visitado[y][x]) {
            return true;
        }
        visitado[y][x] = true;
        return false;
    }

    private static void adicionarVizinhos(int x, int y, FronteiraFloodFill fronteira) {
        fronteira.adicionar(new Pixel(x + 1, y));
        fronteira.adicionar(new Pixel(x - 1, y));
        fronteira.adicionar(new Pixel(x, y + 1));
        fronteira.adicionar(new Pixel(x, y - 1));
    }

    private static void salvarFrameSeNecessario(
            BufferedImage img,
            int frame,
            String pastaFrames,
            boolean criarPastaFrames
    ) throws Exception {
        if (frame % 200 != 0) {
            return;
        }

        if (criarPastaFrames) {
            File pasta = new File(pastaFrames);
            pasta.mkdirs();
            File arquivo = new File(pasta, "frame_" + frame + ".png");
            ImageIO.write(img, "png", arquivo);
            return;
        }

        ImageIO.write(img, "png", new File(pastaFrames + "/frame_" + frame + ".png"));
    }

    private static void salvarResultado(BufferedImage img, String arquivoResultado) throws Exception {
        ImageIO.write(img, "png", new File(arquivoResultado));
    }
}