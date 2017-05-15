package escalonador;

import java.util.Vector;

public class Memoria {
    public int tamanho;
    public int tamanhoOcupado;
    public Vector<Processo> pagina = new Vector<Processo>(16);
    
    public Memoria(){
        this.tamanho = 1024;
        for(int i = 0; i < 16; i++){
            this.pagina.set(i, null);
        }
    }
    
    public boolean freeToProcess(Processo p){
        if (p.tam <= this.tamanhoOcupado)
            return true;
        return false;
    }
    
    public void alocateP(Processo p){
        
    }
}
