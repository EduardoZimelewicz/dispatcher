package escalonador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelCpus extends JPanel {
    
    private BufferedImage img;
    private String path = "src/escalonador/imagens/cpu.png";
    
    public PanelCpus() {
        try {
            img = ImageIO.read(new File(path));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int posX = 10;
        int posY = 10;
        Cpu cpus[] = Escalonador.uDeProcss.toArray(
                new Cpu[Escalonador.uDeProcss.size()]);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 21)); 
        for (int i = 0; i < cpus.length; i++) {
            //desenha a cpu
            g.drawImage(img, posX, posY, 100 , 100, null);
            g.setColor(Color.BLACK);
            g.drawChars(Integer.toString(cpus[i].id).toCharArray(),
                    0, 1, posX + 45, posY + 75);
            //desenha os processo
            if (cpus[i].getProcssCur() != null) {
                char prosNome[] = cpus[i].getProcssCur().nome.toCharArray();
                g.setColor(Color.BLUE);
                g.fillRect(posX + 100, posY + 25, 50, 50);
                g.setColor(Color.BLACK);
                g.drawRect(posX + 100, posY + 25, 50, 50);
                g.setColor(Color.WHITE);
                g.drawChars(prosNome, 0, prosNome.length, posX + 105, posY + 50);
            }
            posY += 120;
        }
    }
    
    
    
}
