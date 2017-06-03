package escalonador;

import java.util.ArrayList;

public class Cpu {
    //estados em que a cpu pode se encontrar
    public static final int OCIOSO = 0;
    public static final int EXECUTANDO = 1;
    
    public int id;
    public Processo processo;
    public int estado;
    public ArrayList<Processo> historico;
    public ArrayList<Integer> histTime;
    
    public Cpu (int id) {
        this.id = id;
        this.estado = Cpu.OCIOSO;
        historico = new ArrayList<>();
        histTime = new ArrayList<>();
    }
    
    //retorna o número da cpu
    public int getId() {
        return this.id;
    }
    
    //retorna o estado da cpu
    public int getEstado() {
        return this.estado;
    }
    
    //retorna o processo que está execultado se a cpu estiver ociosa retorna nulo
    public Processo getProcssCur() {
        return this.processo;
    }
    
    //reseta a cpu
    public void setProcssCur(){
        if (this.processo != null) {
            this.historico.add(this.processo);
            this.histTime.add(new Integer(Escalonador.clock));
            this.processo = null;
            this.estado = OCIOSO;
        }
    }
    
    //Coloca um processo para execultar se a cpu estiver ociosa
    public void excProcss(Processo procss) {
        if (this.estado == Cpu.OCIOSO) {
            this.processo = procss;
            this.estado = Cpu.EXECUTANDO;
        }
        else
          System.out.printf("Cpu %d está executando outro processo\n", this.id);
    }
}