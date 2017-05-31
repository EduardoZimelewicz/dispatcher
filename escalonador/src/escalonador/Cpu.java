package escalonador;

public class Cpu {
    //estados em que a cpu pode se encontrar
    public static final int OCIOSO = 0;
    public static final int EXECUTANDO = 1;
    
    private int id;
    private Processo processo;
    private int estado;
    
    public Cpu (int id) {
        this.id = id;
        this.estado = Cpu.OCIOSO;
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