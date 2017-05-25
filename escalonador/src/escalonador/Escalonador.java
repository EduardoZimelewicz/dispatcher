package escalonador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Escalonador{
    
    public static int impressora = 2;
    public static int scanner = 1;
    public static int cd = 2;
    public static int modem = 1;
    public static int cpu = 4;
    public static int clock = 0;
    
    public static boolean recursosDisp(Processo p){
        if(Escalonador.cpu != 0){
            Escalonador.cpu--;
            if(p.pri == 0){
                return true;
            }
            if(p.nImpr <= Escalonador.impressora && Escalonador.impressora != 0 && p.nImpr != 0){
                Escalonador.impressora--;
                if(p.nScnr <= Escalonador.scanner && Escalonador.scanner != 0 && p.nScnr != 0){
                    Escalonador.scanner--;
                    if(p.nCds <= Escalonador.cd && Escalonador.cd != 0 && p.nCds != 0){
                        Escalonador.cd--;
                        if(p.nMdm <= Escalonador.modem && Escalonador.modem != 0 && p.nMdm != 0){
                            Escalonador.modem--;
                            return true;
                        }
                    }
                }
            }
        }        
        Escalonador.cpu++;
        Escalonador.impressora++;
        Escalonador.scanner++;
        Escalonador.cd++;
        Escalonador.modem++;
        return false;
    }
    
    public static void fcFS(Queue <Processo> f, Memoria m){     
        if(!f.isEmpty()){
                    if(m.freeToProcess(f.element())){
                        System.out.println(f.element().nome + " " + "carregado para memória");
                        m.alocarP(f.remove());
                    }
                    if(m.memoriaCheia()){
                        m.swapOut();
                    }
                }

        if(m.temEstFinalizado()){
            m.rmvEstadoFinalizado();
        }
    }
    
    public static void feedBack(Queue <Processo> f, Queue <Processo> f1, Queue <Processo> f2, Queue <Processo> f3){
        if(!f.isEmpty()){
            
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
       File file = new File("/home/eduardo/NetBeansProjects/escalonador/src/escalonador/processos.txt");
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
       
       //Thread feedback = new Thread(() ->feedBack(fu, f1, f2, f3));
       //Thread fcfs = new Thread(() ->fcFS(ftr, memoria));
              
       while (!fe.isEmpty() || !fu.isEmpty() || !ftr.isEmpty() || memoria.temPrcssMemoria()){
            
            if(!fe.isEmpty()){
              if((fe.element().tempoC == clock) && recursosDisp(fe.element())){
                  if(fe.element().pri != 0) //prioridade não é 0
                      fu.add(fe.remove());
                  else
                      ftr.add(fe.remove());
              }
            }
           
           
           if(!ftr.isEmpty() || memoria.temPrcssMemoria()){
             //feedback.start();
             fcFS(ftr,memoria);
           }
           
           else if(ftr.isEmpty() && !fu.isEmpty()){
               //feedback.start();
           }
          
           
           if(memoria.tamanhoOcupado > 0){
               memoria.setTempoSer();
           }
           clock++;
       }
       
   }
    
}
