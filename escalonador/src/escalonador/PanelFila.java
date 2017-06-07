package escalonador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PanelFila extends JPanel {
    
    private void paintFila(Graphics g, String nome, Processo[] pros, int x, int y) {
        int posX = 0;
        int posY = y;
        //desenha o label
        char label[] = nome.toCharArray();
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 21)); 
        g.drawChars(label, 0, label.length, x, y + 25);
        //desenha os processos
        g.setFont(new Font("TimesRoman", Font.PLAIN, 21)); 
        for (int i = 0; i < pros.length; i++) {
            posX = x + 75 + (i * 50);
            //posY = y + 25;
            char prosNome[] = pros[i].nome.toCharArray();
            g.setColor(pros[i].cor);
            g.fillRect(posX, posY, 50, 50);
            g.setColor(Color.BLACK);
            g.drawRect(posX, posY, 50, 50);
            g.setColor(Color.WHITE);
            g.drawChars(prosNome, 0, prosNome.length, posX + 5, posY + 25);
        }
        if (posX + 50 > getSize().width) {
            Dimension d = this.getPreferredSize();
            setPreferredSize(new Dimension(posX + 50, d.height));
            updateUI();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //desenhar fila f1
        Processo processos[] = Escalonador.ftr.toArray(
                new Processo[Escalonador.ftr.size()]);
        paintFila(g, "Treal", processos, 0, 0);
        
        //desenhar fila f1
        processos = Escalonador.f1.toArray(
                new Processo[Escalonador.f1.size()]);
        paintFila(g, "Fila 1", processos, 0, 100);
        
        //desenhar fila f2
        processos = Escalonador.f2.toArray(
                new Processo[Escalonador.f2.size()]);
        paintFila(g, "Fila 2", processos, 0, 200);
        
        //desenhar fila f3
        processos = Escalonador.f3.toArray(
                new Processo[Escalonador.f3.size()]);
        paintFila(g, "Fila 3", processos, 0, 300);
        
        g.dispose();
    }
}
