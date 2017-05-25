package escalonador;

import java.util.Vector;

public class Memoria {
    public int tamanho = 1024;
    public int tamanhoOcupado = 0;
    public Vector<Processo> quadros = new Vector<Processo>();
    
    public Memoria(){
        quadros.setSize(this.tamanho/64);
    }
    
    public boolean temPrcssMemoria(){
        for(int i = 0; i < quadros.size(); i++)
            if(quadros.elementAt(i) != null)
                return true;
        return false;
    }
    
    public boolean freeToProcess(Processo p){
        if (!memoriaCheia() && (p.tam <= this.tamanho - this.tamanhoOcupado))
            return true;
        return false;
    }
    
    public boolean memoriaCheia(){
        if(this.tamanhoOcupado == this.tamanho)
            return true;
        return false;
    }
    
    public void swapOut(){
        
    }
    
    public boolean temEstFinalizado(){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).tProc == quadros.elementAt(i).serTotal)
                    return true;
            }
        }
        return false;
    }
    
    public void rmvEstadoFinalizado(){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).tProc == quadros.elementAt(i).serTotal){
                    quadros.elementAt(i).estado = Estados.FINALIZADO;
                    System.out.println(quadros.elementAt(i).nome + " " + "finalizado no tempo " + Escalonador.clock);
                    quadros.remove(i);
                    }
            }
        }
    }
    
    public void setTempoSer(){
        //Processo temp_p;
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).serTotal < quadros.elementAt(i).tProc)
                quadros.elementAt(i).serTotal++;
            }
       }
    }
    
    public void alocarP(Processo p){
        p.estado = Estados.EXECUTANDO;
        int nBlocos = p.tam / 64;
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) == null){
            if(nBlocos > 0){
               quadros.set(i, p);
               nBlocos--;
            }
            }
        }
        this.tamanhoOcupado = this.tamanhoOcupado + p.tam;
    }
    
}
