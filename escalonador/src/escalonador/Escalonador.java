package escalonador;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Random;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class Escalonador extends JFrame implements Runnable{
    
//<editor-fold defaultstate="collapsed" desc="variaveis do escalonador">
    //estatos do escalonador
    public static int PAUSADO = 0;
    public static int EXECUTANDO = 1;
    
    public static int impressora = 2;
    public static int scanner = 1;
    public static int cd = 2;
    public static int modem = 1;
    public static int cpu = 4;
    public static int clock = 0;
    public static int control = 0; //controle de filas
    public static boolean trocaDeFila = false; //controle de troca
    public static Vector <Cpu> uDeProcss = new Vector<Cpu>();
    
    public static Queue <Processo> fe = new LinkedList<Processo>();
    public static Queue <Processo> ftr = new LinkedList<Processo>();
    public static Queue <Processo> fu = new LinkedList<Processo>();
    public static Queue <Processo> f1 = new LinkedList<Processo>();
    public static Queue <Processo> f2 = new LinkedList<Processo>();
    public static Queue <Processo> f3 = new LinkedList<Processo>();
    
    public static Memoria memoria = new Memoria();
    public static Memoria memSec = new Memoria();
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="variaveis da interface">
    public static final int JANELA_LARGURA = 1200;
    public static final int JANELA_ALTURA = 720;
    public static int DELAY = 2000; //valor em milesegundos
    private static  Object[][] rows;
    
    //layout
    private GridBagLayout layout;
    private GridBagConstraints constraints;
    
    //componentes
    private PanelFila panelF1;
    private JScrollPane spFilas;
    private PanelCpus pCpus;
    private JScrollPane spTimeline;
    private Timeline timeline;
    private JTable tProcesso;
    private JScrollPane spProcesso;
    private PanelMemoria pMemoria;
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Escalonador">
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
                if(uDeProcss.elementAt(i).estado == 0){
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
                    uDeProcss.elementAt(i).estado = 0;
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
            
            if((p.nImpr <= Escalonador.impressora && Escalonador.impressora != 0) || p.nImpr == 0){
                if((p.nScnr <= Escalonador.scanner && Escalonador.scanner != 0) || p.nScnr == 0){
                    if((p.nCds <= Escalonador.cd && Escalonador.cd != 0) || p.nCds == 0){
                        if((p.nMdm <= Escalonador.modem && Escalonador.modem != 0) || p.nMdm == 0){
                            entregaRecrs(p);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public static void entregaRecrs(Processo p){
        Escalonador.impressora = Escalonador.impressora - p.nImpr;
        Escalonador.scanner = Escalonador.scanner - p.nScnr;
        Escalonador.cd = Escalonador.cd - p.nCds;
        Escalonador.modem = Escalonador.modem - p.nMdm;
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
    
    public static void feedBack(Queue <Processo> f, Queue <Processo> f1, Queue <Processo> f2, Queue <Processo> f3, Memoria m, Memoria m2){
        if(!f.isEmpty()){
            if(m.freeToProcess(f.element()) && !m.prcssEstaNaMe(f.element())){
                System.out.println(f.element().nome + " " + "carregado para memória");
                m.alocarP(f.remove());
            }
            
        }
        
        if(clock%2 == 0 && clock >= 2 && control == 0 && trocaDeFila == false){
                control = 1;
                f1.addAll(m.retiraDaCPU());
                trocaDeFila = true;
            }
        
        if(f.isEmpty() && !f1.isEmpty() && trocaDeFila == false){
            
            while(!f1.isEmpty()){
                if(m.prcssEstaNaMe(f1.element()) && Escalonador.cpu > 0){
                    m.colocaNaCPU(f1.element());
                    Escalonador.cpu--;
                    f1.remove();
                }
                if(Escalonador.cpu == 0)
                    break;
            }
            
            if(!f1.isEmpty() && f1.element() != null){
                if(f1.element().estado == Estados.FINALIZADO)
                    f1.remove();
                else if(f2.contains(f1.element()))
                    f2.remove(f1.element());
                else if(f3.contains(f1.element()))
                    f3.remove(f1.element());
            }
            trocaDeFila = true;
        }
        
        if(clock%2 == 0 && control == 1 && trocaDeFila == false){
            f2.addAll(m.retiraDaCPU());
            trocaDeFila = true;
            control = 2;
        }
                    
        else if(f1.isEmpty() && !f2.isEmpty() && trocaDeFila == false){
            while(!f2.isEmpty()){
                if(m.prcssEstaNaMe(f2.element()) && Escalonador.cpu > 0){
                    m.colocaNaCPU(f2.element());
                    Escalonador.cpu--;
                    f2.remove();
                }
                if(Escalonador.cpu == 0)
                    break;
            }
            
            if(!f2.isEmpty() && f2.element() != null) {
                if(f2.element().estado == Estados.FINALIZADO)
                    f2.remove();
                else if(f1.contains(f2.element()))
                    f1.remove(f2.element());
                else if(f3.contains(f2.element()))
                    f3.remove(f2.element());
            }
            trocaDeFila = true;
        }
        
        if(clock%2 == 0 && control == 2 && trocaDeFila == false){
            f3.addAll(m.retiraDaCPU());
            trocaDeFila = true;
            control = 3;
        }
            
        else if(f1.isEmpty() && f2.isEmpty() && !f3.isEmpty() && trocaDeFila == false){
            
            while(!f3.isEmpty()){
                if(m.prcssEstaNaMe(f3.element()) && Escalonador.cpu > 0){
                    m.colocaNaCPU(f3.element());
                    Escalonador.cpu--;
                    f3.remove();
                }
                if(Escalonador.cpu == 0)
                    break;
            }
            
            
            if(!f3.isEmpty() && f3.element() != null){
                if(f3.element().estado == Estados.FINALIZADO)
                    f3.remove();
                else if(f2.contains(f3.element()))
                    f2.remove(f3.element());
                else if(f1.contains(f3.element()))
                    f1.remove(f3.element());
            }
            trocaDeFila = true;
        }
        
        if(clock%2 == 0 && control == 3 && trocaDeFila == false){
            f1.addAll(m.retiraDaCPU());
            control = 1;
            trocaDeFila = true;
        }
        
        m2.swapIn(m);
        trocaDeFila = false;
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
        Random r = new Random();
        p.cor = new Color(r.nextInt(5) * 45, 
                r.nextInt(5) * 45,
                r.nextInt(5) * 45);
        return p;
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Metodos da interface">
    
    public Escalonador() {
        
        setSize(JANELA_LARGURA, JANELA_ALTURA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        
        layout = new GridBagLayout();
        constraints = new GridBagConstraints();
        setLayout(layout);
        
        initComponentes();
        
        Thread t = new Thread(this);
        t.start();
        
    }
    
    private void initComponentes() {
        
        Border borda = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        Border borda2 = BorderFactory.createBevelBorder(BevelBorder.RAISED);
        
        
        //tabela de processos
        tProcesso  = new JTable();
        tProcesso.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Object[] columns = {"Processo", "Chegada", "Estado", "Prioridade", "Duração"};
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        //model.setColumnIdentifiers(columns);
        tProcesso.setModel(model);
        
        spProcesso = new JScrollPane(tProcesso);
        spProcesso.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_ALWAYS);
        spProcesso.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);
        spProcesso.setPreferredSize(new Dimension(100, 100));
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 500;
        constraints.weightx = 1;
        addComponent(spProcesso, 0, 0, 1, 1);
        
        //botoes
        
        
        //filas de pronto
        panelF1 = new PanelFila();
        spFilas = new JScrollPane(panelF1);
        spFilas.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spFilas.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_NEVER);
        spFilas.setBorder(borda);
        spFilas.setPreferredSize(new Dimension(350, 100));
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 500;
        constraints.weightx = 1;
        addComponent(spFilas, 0, 1, 2, 1);
        
        //Cpus
        pCpus = new PanelCpus();
        pCpus.setBackground(Color.lightGray);
        pCpus.setBorder(borda);
        constraints.weightx = 1;
        addComponent(pCpus, 0, 3, 1, 1);
        
        
        //Memoria
        pMemoria = new PanelMemoria();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 60;
        constraints.weightx = 1;
        addComponent(pMemoria, 1, 0, 4, 1);
        
        //Timeline
        timeline = new Timeline();
        timeline.setPreferredSize(new Dimension(100, 400));
        spTimeline = new JScrollPane(timeline);
        spTimeline.setBorder(borda2);
        spTimeline.setPreferredSize(new Dimension(100, 150));
        spTimeline.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_ALWAYS);
        spTimeline.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_ALWAYS);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weighty = 0;
        addComponent(spTimeline, 2, 0, 4, 1);
    }
    
    //controlar restrincoes
    private void addComponent(Component component,
            int row, int column, int width, int height) {
        constraints.gridx = column;
        constraints.gridy = row;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        layout.setConstraints(component, constraints);
        add(component);
    }
//</editor-fold>
    
    public static void main(String[] args) throws FileNotFoundException{
       File file = new File("src/escalonador/processos3.txt");
       Scanner scanner = new Scanner(file);
       
       int id = 1;
       while(scanner.hasNextLine()){
           Processo p = new Processo();
           p.nome = "P" + id;
           String line = scanner.nextLine();
           String[] lineArray = line.split(", ");
           p = createProcess(lineArray, p);
           fe.add(p);
           p.printProcesso();
           id++;
       }
       //criar as linhas para a tabela
       rows = new Object[fe.size()][5];
       Processo[] pros = fe.toArray(new Processo[fe.size()]);
       for (int i = 0; i < fe.size(); i++){
           rows[i][0] = pros[i].nome;
           rows[i][1] = pros[i].tempoC;
           rows[i][2] = pros[i].estado;
           rows[i][3] = pros[i].pri;
           rows[i][4] = pros[i].tProc;
       }
       
       uDeProcss.setSize(4);
       inicializaVectorCpu();
       
       new Escalonador().setVisible(true);
       
   }

    @Override
    public void run() {
              
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
                 
                 if(memSec.temEstFinalizado()){
                     memSec.rmvEstadoFinalizado();
                     Escalonador.cpu++;
                 }

                if(!ftr.isEmpty() || memoria.temPrcssFTRMemoria()){
                  fcFS(ftr,memoria, memSec);
                }

                else if((ftr.isEmpty() && !fu.isEmpty()) || memoria.temPrcssFUMemoria()){
                    feedBack(fu, f1, f2, f3, memoria, memSec);
                }

                if(memoria.tamanhoOcupado > 0){
                    memoria.setTempoSer();
                }
                
                if(memSec.tamanhoOcupado > 0){
                    memSec.setTempoSer();
                }
                
                clock++;
                
                //interface
                //System.out.println(panelF1.getSize());
               panelF1.repaint();
               pCpus.repaint();
               pMemoria.repaint();
               timeline.repaint();
           }
           catch (Exception e) {
               System.out.println(e.getMessage());
           }
       }
        
    }
    
}
