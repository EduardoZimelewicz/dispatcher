package escalonador;
import escalonador.Estados;

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
    Estados estado;
    
    
    public Processo(){
        this.estado = Estados.NOVO;
    }
    
    public void printProcesso(){
        System.out.print(this.nome + " ");
        System.out.println(this.estado + " ");
    }
    
}
