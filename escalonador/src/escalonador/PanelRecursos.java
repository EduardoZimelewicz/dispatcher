package escalonador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanelRecursos extends JPanel {
    
    private BufferedImage drive_img;
    private BufferedImage impressora_img;
    private BufferedImage modem_img;
    private BufferedImage scanner_img;
    
    private String drive_path = "src/escalonador/imagens/drive.png";
    private String impressora_path = "src/escalonador/imagens/impressora.png";
    private String modem_path = "src/escalonador/imagens/modem.png";
    private String scanner_path = "src/escalonador/imagens/scanner.png";
    
    public PanelRecursos() {
        try {
            drive_img = ImageIO.read(new File(drive_path));
            impressora_img = ImageIO.read(new File(impressora_path));
            modem_img = ImageIO.read(new File(modem_path));
            scanner_img = ImageIO.read(new File(scanner_path));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //total é o numero total de recursos taquele tipo
    //usado é o numero de recursos usados taquele tipo 
    private void paintRecurso(Graphics g, BufferedImage img,
            int total, int usado,
            int x, int y) {
        g.setFont(new Font("TimesRoman", Font.PLAIN, 21)); 
        g.drawImage(img, x, y, 120 , 120, null);
        g.setColor(Color.BLACK);
        g.drawString(usado + " / " + total, x + 150, y + 60);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintRecurso(g, drive_img, Escalonador.CD, Escalonador.cd, 0, 0);
        paintRecurso(g, impressora_img, Escalonador.IMPRESSORA, Escalonador.impressora, 0, 120);
        paintRecurso(g, modem_img, Escalonador.MODEM, Escalonador.modem, 0, 240);
        paintRecurso(g, scanner_img, Escalonador.SCANNER, Escalonador.scanner, 0, 380);
        updateUI();
    }
    
    
}
