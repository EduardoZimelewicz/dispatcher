package escalonador;

import java.util.LinkedList;
import java.util.Queue;
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
    
    public boolean temPrcssFTRMemoria(){
        for(int i = 0; i < quadros.size(); i++)
            if(quadros.elementAt(i) != null)
                if(quadros.elementAt(i).pri == 0)
                return true;
        return false;
    }
    
    public boolean temPrcssFUMemoria(){
        for(int i = 0; i < quadros.size(); i++)
            if(quadros.elementAt(i) != null)
                if(quadros.elementAt(i).pri != 0)
                return true;
        return false;
    }
    
    public boolean prcssEstaNaMe(Processo p){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).nome.equals(p.nome))
                    return true;
            }
        }
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
    
    public Queue retiraDaCPU(){
        Queue <Processo> f = new LinkedList<Processo>();
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).pri != 0){
                    if(!f.contains(quadros.elementAt(i))){
                        f.add(quadros.elementAt(i));
                    }
                    
                    quadros.elementAt(i).utCpu = false;
                }
            }
        }
        return f;
    }
        
    
    public void swapOut(Processo p, Memoria m){
        Processo temp_p = new Processo();
        for(int i = 0; i < m.quadros.size(); i++){
            if(m.quadros.elementAt(i) != null){
                if(m.quadros.elementAt(i).pri != 0){
                    if(m.quadros.elementAt(i).tam >= p.tam){
                        temp_p = m.quadros.elementAt(i);
                        alocarP(temp_p);
                        
                        if(temp_p.estado == Estados.BLOQUEADO){
                            temp_p.estado = Estados.BLOQUEADOSUSPENSO;
                        }
                        
                        else {
                            temp_p.estado = Estados.PRONTOSUSPENSO;
                        }
                        
                        for(int k = i; k < quadros.size(); k++){
                            if(m.quadros.elementAt(k).equals(temp_p)){
                                m.quadros.set(i, null);
                            }
                        }
                    }
                }
            }
            break;
        }
    }
    
    public boolean temEstBloqueado(){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).estado == Estados.BLOQUEADO)
                    return true;
            }
        }
        return false;
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
        int tam = 0;
        Vector <Processo> termtd = new Vector <Processo>();
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).tProc == quadros.elementAt(i).serTotal){
                    quadros.elementAt(i).estado = Estados.FINALIZADO;
                    quadros.elementAt(i).utCpu = false;
                    
                    if(!termtd.contains(quadros.elementAt(i))){
                        System.out.println(quadros.elementAt(i).nome + " " + "finalizado no tempo " + Escalonador.clock);
                        this.tamanhoOcupado = this.tamanhoOcupado - quadros.elementAt(i).tam;
                        termtd.add(quadros.elementAt(i));
                        Escalonador.atualizaRecrs(quadros.elementAt(i));
                    }
                    
                    quadros.set(i, null);
                }
            }
        }
    }
    
    public void setTempoSer(){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).serTotal < quadros.elementAt(i).tProc){
                    if(quadros.elementAt(i).utCpu)
                        quadros.elementAt(i).serTotal++;
                }
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
