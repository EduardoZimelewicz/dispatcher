package escalonador;
import escalonador.Estados;
import java.awt.Color;

public class Processo {
    public String nome;
    public int tempoC;
    public int pri;
    public int tProc;
    public int tam;
    public int nImpr;
    public int nScnr;
    public int nMdm;
    public int nCds;
    public int turnAround;
    public float tDeFilaNorm;
    Estados estado;
    public int serTotal;
    public boolean utCpu;
    public Color cor;
    
    
    public Processo(){
        this.estado = Estados.NOVO;
        this.serTotal = 0;
        this.utCpu = false;
    }
    
    public void printProcesso(){
        System.out.print(this.nome + " ");
        System.out.println(this.estado + " ");
    }
    
}
