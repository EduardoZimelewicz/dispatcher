package escalonador;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import javax.swing.JPanel;

public class PanelMemoria extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Vector<Processo> quadros = Escalonador.memoria.quadros;
        int x = 20;
        int y = 10;
        int altura = 35;
        int tamQuadro = (Escalonador.JANELA_LARGURA - 2 * x) / quadros.size(); //tamanho de um quadro na tela
        g.setColor(Color.WHITE);
        g.fillRect(x, y, tamQuadro * quadros.size(), altura); //desenha fundo
        for (int i = 0; i < quadros.size(); i++) {
            if (quadros.get(i) != null) {
                g.setColor(quadros.get(i).cor);
                g.fillRect(tamQuadro * i + x, y, tamQuadro, altura);
            }
        }
        g.setColor(Color.BLACK);
        g.drawRect(x, y, tamQuadro * quadros.size(), altura);
    }
    
    
    
}
