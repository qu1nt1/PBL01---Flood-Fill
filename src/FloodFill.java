import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Color;

public class FloodFill {

    //BFS: vizinhos 4 sentidos; fila = FIFO (largura)
    public static void preencherFila(BufferedImage img, int x, int y, Color novaCor, JanelaImagem tela) throws Exception {

        int largura = img.getWidth();
        int altura = img.getHeight();

        int corOriginal = img.getRGB(x, y);
        int novaCorRGB = novaCor.getRGB();

        Fila fila = new Fila();
        fila.enqueue(new Pixel(x, y));

        int frame = 0;

        while (!fila.isEmpty()) {
            Pixel p = fila.dequeue();
            if (p == null) continue;

            int px = p.x;
            int py = p.y;

            if (px < 0 || py < 0 || px >= largura || py >= altura)
                continue;

            if (img.getRGB(px, py) != corOriginal)
                continue;

            img.setRGB(px, py, novaCorRGB);
            tela.atualizarImagem(img);
            //ms por pixel(0 é o mais rápido)
            Thread.sleep(1);

            //print a cada 200 frames
            if (frame % 200 == 0) {
                ImageIO.write(img, "png", new File("frames_fila/frame_" + frame + ".png"));
            }
            frame++;

            fila.enqueue(new Pixel(px + 1, py));
            fila.enqueue(new Pixel(px - 1, py));
            fila.enqueue(new Pixel(px, py + 1));
            fila.enqueue(new Pixel(px, py - 1));
        }

        ImageIO.write(img, "png", new File("resultado_fila.png"));
    }

    //DFS: empilha vizinhos; visitado[][] evita reprocessar o mesmo (x,y)
    public static void preencherPilha(BufferedImage img, int x, int y, Color novaCor, JanelaImagem tela) {

        int largura = img.getWidth();
        int altura = img.getHeight();

        int corOriginal = img.getRGB(x, y);
        int novaCorRGB = novaCor.getRGB();

        boolean[][] visitado = new boolean[altura][largura];

        Pilha pilha = new Pilha();
        pilha.push(new Pixel(x, y));

        int frame = 0;

        while (!pilha.isEmpty()) {
            Pixel p = pilha.pop();
            if (p == null) continue;

            int px = p.x;
            int py = p.y;

            if (px < 0 || py < 0 || px >= largura || py >= altura)
                continue;

            if (visitado[py][px])
                continue;

            visitado[py][px] = true;

            if (img.getRGB(px, py) != corOriginal)
                continue;

            img.setRGB(px, py, novaCorRGB);

            if (tela != null) {
                tela.atualizarImagem(img);
            }

            try {
                //print a cada 200 frames
                if (frame % 200 == 0) {
                    File pasta = new File("frames_pilha");
                    pasta.mkdirs();

                    File arquivo = new File(pasta, "frame_" + frame + ".png");
                    ImageIO.write(img, "png", arquivo);
                }

                frame++;

                //ms por pixel (alinhe com preencherFila para comparar velocidade visual)
                Thread.sleep(1);

            } catch (Exception e) {
                e.printStackTrace();
            }

            pilha.push(new Pixel(px + 1, py));
            pilha.push(new Pixel(px - 1, py));
            pilha.push(new Pixel(px, py + 1));
            pilha.push(new Pixel(px, py - 1));
        }

        try {
            ImageIO.write(img, "png", new File("resultado_pilha.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}