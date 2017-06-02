package escalonador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

public class Escalonador{
    
    public static int DELAY = 500; //valor em milesegundos
    public static int impressora = 2;
    public static int scanner = 1;
    public static int cd = 2;
    public static int modem = 1;
    public static int cpu = 4;
    public static int clock = 0;
    public static Vector <Cpu> uDeProcss = new Vector<Cpu>();
    
    public static void inicializaVectorCpu(){
        for(int i = 0; i < uDeProcss.size(); i++){
            Cpu cpu = new Cpu(i);
            uDeProcss.set(i, cpu);
        }
    }
    
    public static void imprimeCpu(){
        for(int i = 0; i < uDeProcss.size(); i++){
            if(uDeProcss.elementAt(i) != null)
                System.out.println("Cpu " + uDeProcss.elementAt(i).getId() + "com " + uDeProcss.elementAt(i).getProcssCur().nome);
        }
    }
    
    public static boolean buscaCpuOciosa(Processo p){
        for(int i = 0; i < uDeProcss.size(); i++){
            if(uDeProcss.elementAt(i) != null){
                if(uDeProcss.elementAt(i).getEstado() == Cpu.OCIOSO){
                    uDeProcss.elementAt(i).excProcss(p);
                    return true;
                }
            }
        }
        return false;
    }
    
    public static void buscaCpuComPrcss(Processo p){
        for(int i = 0; i < uDeProcss.size(); i++){
            if(uDeProcss.elementAt(i) != null && uDeProcss.elementAt(i).getProcssCur() != null){
                if(uDeProcss.elementAt(i).getProcssCur().nome.equals(p.nome)){
                    uDeProcss.elementAt(i).setProcssCur();
                    break;
                }
            }
        }
    }
    
    public static boolean recursosDisp(Processo p){
        if(Escalonador.cpu != 0 && buscaCpuOciosa(p)){
            Escalonador.cpu--;
            p.utCpu = true;
            if(p.pri == 0){
                return true;
            }
            if(p.nImpr <= Escalonador.impressora && Escalonador.impressora != 0){
                Escalonador.impressora = Escalonador.impressora - p.nImpr;
                if(p.nScnr <= Escalonador.scanner && Escalonador.scanner != 0){
                    Escalonador.scanner = Escalonador.scanner - p.nScnr;
                    if(p.nCds <= Escalonador.cd && Escalonador.cd != 0){
                        Escalonador.cd = Escalonador.cd - p.nCds;
                        if(p.nMdm <= Escalonador.modem && Escalonador.modem != 0){
                            Escalonador.modem = Escalonador.modem - p.nMdm;
                            return true;
                        }
                    }
                }
            }
        }        
        Escalonador.cpu++;
        buscaCpuComPrcss(p);
        Escalonador.impressora = Escalonador.impressora + p.nImpr;
        Escalonador.scanner = Escalonador.scanner + p.nScnr;
        Escalonador.cd = Escalonador.cd + p.nCds;
        Escalonador.modem = Escalonador.modem + p.nMdm;
        return false;
    }
    
    public static void atualizaRecrs(Processo p){
        Escalonador.impressora = Escalonador.impressora + p.nImpr;
        Escalonador.scanner = Escalonador.scanner + p.nScnr;
        Escalonador.cd = Escalonador.cd + p.nCds;
        Escalonador.modem = Escalonador.modem + p.nMdm;
    }
    
    public static void fcFS(Queue <Processo> f, Memoria m, Memoria m2){     
        if(!f.isEmpty()){
            if(m.freeToProcess(f.element())){
                System.out.println(f.element().nome + " " + "carregado para memória");
                m.alocarP(f.remove());
            }
            
            if(m.memoriaCheia()){
                m2.swapOut(f.element(), m);
                if(m.freeToProcess(f.element())){
                    System.out.println(f.element().nome + " " + "carregado para memória");
                    m.alocarP(f.remove());
                }
            }
        }
    }
    
    public static void feedBack(Queue <Processo> f, Queue <Processo> f1, Queue <Processo> f2, Queue <Processo> f3, Memoria m){
        if(!f.isEmpty()){
            if(m.freeToProcess(f.element()) && !m.prcssEstaNaMe(f.element())){
                System.out.println(f.element().nome + " " + "carregado para memória");
                m.alocarP(f.remove());
            }
            
            if(clock%2 == 0){
                f1.addAll(m.retiraDaCPU());
                Escalonador.cpu++;
            }
        }
        
        if(f.isEmpty() && !f1.isEmpty()){
            if(m.prcssEstaNaMe(f1.element())){
                m.colocaNaCPU(f1.element());
                f1.remove();
            }
            
            if(clock%2 == 0){
                f2.addAll(m.retiraDaCPU());
                Escalonador.cpu++;
            }
        }
        
        else if(f1.isEmpty() && !f2.isEmpty()){
            if(m.prcssEstaNaMe(f2.element())){
                m.colocaNaCPU(f2.element());
                f2.remove();
            }
            
            if(clock%2 == 0){
                f3.addAll(m.retiraDaCPU());
                Escalonador.cpu++;
            }
        }
        
        else if(f1.isEmpty() && f2.isEmpty()){
            if(!f3.isEmpty()){
                if(m.prcssEstaNaMe(f3.element())){
                    m.colocaNaCPU(f3.element());
                    f3.remove();
                }
            }
            
            if(clock%2 == 0){
                    f1.addAll(m.retiraDaCPU());
                    Escalonador.cpu++;
            }
        }
    }
    
    public static Processo createProcess(String [] lineArray, Processo p){
        p.tempoC = Integer.parseInt(lineArray[0]);
        p.pri = Integer.parseInt(lineArray[1]);
        p.tProc = Integer.parseInt(lineArray[2]);
        p.tam = Integer.parseInt(lineArray[3]);
        p.nImpr = Integer.parseInt(lineArray[4]);
        p.nScnr = Integer.parseInt(lineArray[5]);
        p.nMdm = Integer.parseInt(lineArray[6]);
        p.nCds = Integer.parseInt(lineArray[7]);
        return p;
    }

    public static void main(String[] args) throws FileNotFoundException{
       File file = new File("src/escalonador/processos2.txt");
       Scanner scanner = new Scanner(file);
       Queue <Processo> fe = new LinkedList<Processo>();
       int id = 1;
       while(scanner.hasNextLine()){
           Processo p = new Processo();
           p.nome = "processo" + " " + id;
           String line = scanner.nextLine();
           String[] lineArray = line.split(", ");
           p = createProcess(lineArray, p);
           fe.add(p);
           p.printProcesso();
           id++;
       }
       
       Queue <Processo> ftr = new LinkedList<Processo>();
       Queue <Processo> fu = new LinkedList<Processo>();
       Queue <Processo> f1 = new LinkedList<Processo>();
       Queue <Processo> f2 = new LinkedList<Processo>();
       Queue <Processo> f3 = new LinkedList<Processo>();
       
       Memoria memoria = new Memoria();
       Memoria memSec = new Memoria();
       
       uDeProcss.setSize(4);
       inicializaVectorCpu();
       
       //Thread feedback = new Thread(() ->feedBack(fu, f1, f2, f3));
       //Thread fcfs = new Thread(() ->fcFS(ftr, memoria));
              
       while (!fe.isEmpty() || !fu.isEmpty() || !ftr.isEmpty() || memoria.temPrcssMemoria()){
           try {
                Thread.sleep(DELAY);

                 if(!fe.isEmpty()){
                   if((fe.element().tempoC <= clock) && recursosDisp(fe.element())){
                       if(fe.element().pri != 0) 
                           fu.add(fe.remove());
                       else
                           ftr.add(fe.remove());
                   }
                   
                 }

                 if(memoria.temEstFinalizado()){
                     memoria.rmvEstadoFinalizado();
                     Escalonador.cpu++;
                 }

                if(!ftr.isEmpty() || memoria.temPrcssFTRMemoria()){
                  fcFS(ftr,memoria, memSec);
                }

                else if((ftr.isEmpty() && !fu.isEmpty()) || memoria.temPrcssFUMemoria()){
                    feedBack(fu, f1, f2, f3, memoria);
                }

                if(memoria.tamanhoOcupado > 0){
                    memoria.setTempoSer();
                }
                
                clock++;
           }
           catch (Exception e) {
               System.out.println(e.getMessage());
           }
       }
       
   }
    
}
