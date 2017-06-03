package escalonador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Timeline extends JPanel {
    
    
    private void paintTimeline(Graphics g, String nome, Cpu cpu, int x, int y) {
        int posX = 0;
        int posY = y;
        //desenha o label
        char label[] = nome.toCharArray();
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 21)); 
        g.drawChars(label, 0, label.length, x, y + 25);
        //desenha os processos
        for (int i = 0; i < cpu.historico.size(); i++) {
            g.setFont(new Font("TimesRoman", Font.PLAIN, 21)); 
            posX = x + 75 + (i * 50);
            g.setColor(cpu.historico.get(i).cor);
            g.fillRect(posX, posY, 50, 50);
            g.setColor(Color.BLACK);
            g.drawRect(posX, posY, 50, 50);
            g.setColor(Color.WHITE);
            g.drawString(cpu.historico.get(i).nome, posX + 5, posY + 25);
            //desenha o tempo
            g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
            g.drawString(cpu.histTime.get(i).toString(), posX + 5, posY + 45);
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
        
        paintTimeline(g, "Cpu 0", Escalonador.uDeProcss.get(0), 0, 20);
        paintTimeline(g, "Cpu 1", Escalonador.uDeProcss.get(1), 0, 100);
        paintTimeline(g, "Cpu 2", Escalonador.uDeProcss.get(2), 0, 180);
        paintTimeline(g, "Cpu 3", Escalonador.uDeProcss.get(3), 0, 260);
        
    }
    
    
    
}
