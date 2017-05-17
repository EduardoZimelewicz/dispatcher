package escalonador;

import java.util.Vector;

public class Memoria {
    public int tamanho = 1024;
    public int tamanhoOcupado;
    public Vector<Processo> quadros = new Vector<Processo>(this.tamanho/64);
    
    public Memoria(){
        
    }
    
    public boolean freeToProcess(Processo p){
        if (p.tam <= this.tamanhoOcupado)
            return true;
        return false;
    }
    
    public void alocarP(Processo p){
        
    }
    
}
