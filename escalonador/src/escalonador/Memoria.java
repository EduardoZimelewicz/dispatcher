package escalonador;

import static escalonador.Escalonador.buscaCpuComPrcss;
import static escalonador.Escalonador.buscaCpuOciosa;
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
    
    public boolean prcssTemIO (Processo p){
        if(p.nCds >= 1 || p.nImpr >=1 || p.nMdm >= 1 || p.nScnr >= 1)
            return true;
        return false;
    }
    
    public Processo rtrnPrcssPronto(){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).estado == Estados.PRONTO){
                    return quadros.elementAt(i);
                }
            }
        }
        return null;
    }
    
    public void bloqueiaProcss(int pri){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).pri == pri && prcssTemIO(quadros.elementAt(i))){
                    if(quadros.elementAt(i).estado != Estados.BLOQUEADO){
                        System.out.println(quadros.elementAt(i).nome + " Bloqueado");
                        buscaCpuComPrcss(quadros.elementAt(i));
                        for(int k = i; k < quadros.size(); k++){
                            if(quadros.elementAt(k) != null){
                                if(quadros.elementAt(k).nome.equals(quadros.elementAt(i).nome)){
                                    quadros.elementAt(k).utCpu = false;
                                    quadros.elementAt(k).estado = Estados.BLOQUEADO;
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    
    public void desbloqProcss(int pri){
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null && quadros.elementAt(i).pri == pri){
                if(quadros.elementAt(i).estado == Estados.BLOQUEADO){
                    System.out.println(quadros.elementAt(i).nome + " Desbloqueado");
                    for(int k = i; k < quadros.size(); k++){
                        if(quadros.elementAt(k) != null) 
                            if(quadros.elementAt(k).nome.equals(quadros.elementAt(i).nome)){
                                quadros.elementAt(k).estado = Estados.PRONTO;
                        }
                    }
                    break;
                }
            }
        }
    }
    
    public Queue retiraDaCPU(){
        Queue <Processo> f = new LinkedList<Processo>();
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(quadros.elementAt(i).pri != 0 && quadros.elementAt(i).estado != Estados.BLOQUEADO){
                    if(!f.contains(quadros.elementAt(i))){
                        f.add(quadros.elementAt(i));
                        buscaCpuComPrcss(quadros.elementAt(i));
                    }
                    
                    quadros.elementAt(i).utCpu = false;
                    quadros.elementAt(i).estado = Estados.PRONTO;
                }
            }
        }
        Escalonador.cpu = Escalonador.cpu + f.size();
        return f;
    }
        
    public void colocaNaCPU(Processo p){
        Queue <Processo> f = new LinkedList<Processo>();
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null && quadros.elementAt(i).estado != Estados.BLOQUEADO){
                if(quadros.elementAt(i).nome.equals(p.nome)){
                    if(!f.contains(quadros.elementAt(i))){
                        f.add(quadros.elementAt(i));
                        buscaCpuOciosa(quadros.elementAt(i));
                    }
                    quadros.elementAt(i).utCpu = true;
                    quadros.elementAt(i).estado = Estados.EXECUTANDO;
                }
            }
        }
    }
    
    //Tira o processo da memória definida como argumento e coloca na memória que chama a função
    public void swapOut(Processo p, Memoria m){
        Processo temp_p = new Processo();
        for(int i = 0; i < m.quadros.size(); i++){
            if(m.quadros.elementAt(i) != null){
                if(m.quadros.elementAt(i).pri != 0){
                    if(m.quadros.elementAt(i).tam >= p.tam){
                        temp_p = m.quadros.elementAt(i);
                        System.out.println(temp_p.nome + " retirado da memória");
                        if(temp_p.estado == Estados.BLOQUEADO){
                            temp_p.estado = Estados.BLOQUEADOSUSPENSO;
                        }
                        
                        else {
                            temp_p.estado = Estados.PRONTOSUSPENSO;
                            Escalonador.buscaCpuComPrcss(temp_p);
                            temp_p.utCpu = false;
                        }
                        
                        alocarPB(temp_p);
                        m.tamanhoOcupado = m.tamanhoOcupado - temp_p.tam;
                        
                        for(int k = i; k < quadros.size(); k++){
                            if(m.quadros.elementAt(i) != null){
                                if(m.quadros.elementAt(k).equals(temp_p)){
                                    m.quadros.set(i, null);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    
    //Devolve processo para a memória especificada como argumento
    public void swapIn(Memoria m){
        Processo temp_p = new Processo();
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null){
                if(freeToProcess(quadros.elementAt(i)) && Escalonador.temCpuOciosa()){
                   temp_p = quadros.elementAt(i);
                   System.out.println(temp_p.nome + " colocado de volta na memória");
                   if(temp_p.estado == Estados.BLOQUEADOSUSPENSO){
                        temp_p.estado = Estados.BLOQUEADO;
                        m.alocarPB(temp_p);
                   }
                        
                    else{
                        temp_p.estado = Estados.PRONTO;
                        m.alocarP(temp_p);
                        Escalonador.buscaCpuOciosa(temp_p);
                        temp_p.utCpu = true;
                   }
                   
                }
                
                for(int k = i; k < quadros.size(); k++){
                    if(quadros.elementAt(i) != null){
                        if(quadros.elementAt(i).nome.equals(temp_p.nome)){
                            quadros.set(i, null);
                        }
                    }
                }
                break;
            }
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
                        buscaCpuComPrcss(quadros.elementAt(i));
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
        Processo temp_p = new Processo(); 
        for(int i = 0; i < quadros.size(); i++){
            if(quadros.elementAt(i) != null && !quadros.elementAt(i).nome.equals(temp_p.nome) && quadros.elementAt(i).estado != Estados.BLOQUEADO){
                if(quadros.elementAt(i).serTotal < quadros.elementAt(i).tProc){
                    if(quadros.elementAt(i).utCpu)
                        quadros.elementAt(i).serTotal++;
                }
                temp_p = quadros.elementAt(i);
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
    
    public void alocarPB(Processo p){
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
