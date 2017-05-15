package escalonador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Escalonador {
    
    public boolean impressora1;
    public boolean impressora2;
    public boolean scanner;
    public boolean cd1;
    public boolean cd2;
    
    public static void fcFS(Queue <Processo> f, Memoria m){
        while(!f.isEmpty()){
            if(m.freeToProcess(f.element())){
                m.alocateP(f.remove());
            }
        }
    }
    
    public static void feeedBack(Queue <Processo> f){
        
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
           id++;
       }
       
       Queue <Processo> ftr = new LinkedList<Processo>();
       Queue <Processo> fu = new LinkedList<Processo>();
       
       int clock = 0;
       Memoria memoria = new Memoria();
       Processo temp_p; 
       
       while (!fe.isEmpty() || !ftr.isEmpty() || !fu.isEmpty()){
           
       }
       
    }
    
}
