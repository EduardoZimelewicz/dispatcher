package escalonador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.Queue;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PanelFila extends JPanel {

    private Queue<Integer> fila;
    public int tam = 1;
    
    public PanelFila(Queue<Integer> fila) {
        this.fila = fila;
        
    }
    
    public void redimencionar(int largura, int altura) {
        Dimension d = this.getPreferredSize();
        setPreferredSize(new Dimension( d.width + largura, d.height + altura));
        updateUI();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for(int i = 0; i < tam; i++) {
            g.setColor(Color.BLUE);
            g.fillRect( i * 50, 0, 50, 50);
        }
        g.dispose();
    }
}
