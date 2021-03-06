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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

public class Escalonador extends JFrame implements Runnable, ActionListener{
    
//<editor-fold defaultstate="collapsed" desc="variaveis do escalonador">
    //estatos do escalonador
    public static int PAUSADO = 0;
    public static int EXECUTANDO = 1;
    public static int estado;
    
    //recursos
    public static final int IMPRESSORA = 2;
    public static final int SCANNER = 1;
    public static final int CD = 2;
    public static final int MODEM = 1;
    public static int impressora = IMPRESSORA;
    public static int scanner = SCANNER;
    public static int cd = CD;
    public static int modem = MODEM;
    
    public static int cpu = 4;
    public static int clock = 0;
    public static int control = 0; //controle de filas
    public static boolean trocaDeFila = false; //controle de troca
    public static Vector <Cpu> uDeProcss = new Vector<Cpu>();
    
    //estruturas de dados dos processos
    public static Processo[] pros;
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
    public static String path;
    public Thread t;
    
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
    private PanelRecursos pRecursos;
    private JPanel pBotoes;
    private JButton btAbrir;
    private JButton btSimular;
    private JButton btPausar;
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
    
    public static void buscaCpuOciosa(Processo p){
        for(int i = 0; i < uDeProcss.size(); i++){
            if(uDeProcss.elementAt(i) != null){
                if(uDeProcss.elementAt(i).estado == 0){
                    uDeProcss.elementAt(i).excProcss(p);
                    break;
                }
            }
        }
    }
    
    public static boolean temCpuOciosa(){
        for(int i = 0; i < uDeProcss.size(); i++){
            if(uDeProcss.elementAt(i) != null){
                if(uDeProcss.elementAt(i).estado == 0){
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
        if(Escalonador.cpu != 0 && temCpuOciosa()){
            Escalonador.cpu--;
            p.utCpu = true;
            if(p.pri == 0){
                buscaCpuOciosa(p);
                return true;
            }
            
            if((p.nImpr <= Escalonador.impressora && Escalonador.impressora != 0) || p.nImpr == 0){
                if((p.nScnr <= Escalonador.scanner && Escalonador.scanner != 0) || p.nScnr == 0){
                    if((p.nCds <= Escalonador.cd && Escalonador.cd != 0) || p.nCds == 0){
                        if((p.nMdm <= Escalonador.modem && Escalonador.modem != 0) || p.nMdm == 0){
                            entregaRecrs(p);
                            buscaCpuOciosa(p);
                            return true;
                        }
                    }
                }
            }
        }
        Escalonador.cpu++;
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
            
            else if(m.memoriaCheia()){
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
                if(m.prcssEstaNaMe(f1.element()) && Escalonador.cpu > 0 && f1.element().estado != Estados.BLOQUEADO){
                    m.colocaNaCPU(f1.element());
                    Escalonador.cpu--;
                    f1.remove();
                }
                
                else if(m2.prcssEstaNaMe(f1.element()))
                    f1.remove();
                
                else if(f1.element().estado == Estados.BLOQUEADO)
                    f1.remove();
                
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
                if(m.prcssEstaNaMe(f2.element()) && Escalonador.cpu > 0 && f2.element().estado != Estados.BLOQUEADO){
                    m.colocaNaCPU(f2.element());
                    Escalonador.cpu--;
                    f2.remove();
                }
                
                else if(m2.prcssEstaNaMe(f2.element()))
                    f2.remove();
                
                else if(f2.element().estado == Estados.BLOQUEADO)
                    f2.remove();
                
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
                if(m.prcssEstaNaMe(f3.element()) && Escalonador.cpu > 0 && f3.element().estado != Estados.BLOQUEADO){
                    m.colocaNaCPU(f3.element());
                    Escalonador.cpu--;
                    f3.remove();
                }
                
                else if(m2.prcssEstaNaMe(f3.element()))
                    f3.remove();
                
                else if(f3.element().estado == Estados.BLOQUEADO)
                    f3.remove();
                
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
    
    //reseta o escalonador 
    public static void resetarEscalonador() {
        impressora = IMPRESSORA;
        modem = MODEM;
        cd = CD;
        scanner = SCANNER;
        cpu = 4;
        clock = 0;
        control = 0; //controle de filas
        trocaDeFila = false; //controle de troca
        
        inicializaVectorCpu();
        fe = new LinkedList<Processo>();
        ftr = new LinkedList<Processo>();
        fu = new LinkedList<Processo>();
        f1 = new LinkedList<Processo>();
        f2 = new LinkedList<Processo>();
        f3 = new LinkedList<Processo>();
        memoria = new Memoria();
        memSec = new Memoria();
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
        
        estado = PAUSADO;
        t = new Thread(this);
        t.start();
        
    }
    
    private void initComponentes() {
        
        Border borda = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        Border borda2 = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
        
        
        //tabela de processos
        tProcesso  = new JTable();
        tProcesso.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Object[] columns = {"Processo", "Chegada", "Estado", "Prioridade", "Duração", "Turnaround", "Normalizado"};
        DefaultTableModel model = new DefaultTableModel(rows, columns);
        //model.setColumnIdentifiers(columns);
        tProcesso.setModel(model);
        
        spProcesso = new JScrollPane(tProcesso);
        spProcesso.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_ALWAYS);
        spProcesso.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_AS_NEEDED);
        spProcesso.setPreferredSize(new Dimension(200, 100));
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 300;
        constraints.weightx = 1;
        addComponent(spProcesso, 0, 0, 1, 1);
        
        //botoes
        pBotoes = new JPanel();
        btAbrir = new JButton("Abrir");
        btSimular = new JButton("Simular");
        btPausar = new JButton("Pausar");
        btSimular.setEnabled(false);
        btPausar.setEnabled(false);
        btAbrir.addActionListener(this);
        btSimular.addActionListener(this);
        btPausar.addActionListener(this);
        pBotoes.add(btAbrir);
        pBotoes.add(btSimular);
        pBotoes.add(btPausar);
        pBotoes.setPreferredSize(new Dimension(100, 100));
        addComponent(pBotoes, 1, 0, 1, 1);
        
        //filas de pronto
        panelF1 = new PanelFila();
        spFilas = new JScrollPane(panelF1);
        spFilas.setHorizontalScrollBarPolicy(ScrollPaneLayout.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        spFilas.setVerticalScrollBarPolicy(ScrollPaneLayout.VERTICAL_SCROLLBAR_NEVER);
        spFilas.setBorder(borda2);
        spFilas.setPreferredSize(new Dimension(300, 100));
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 300;
        constraints.weightx = 1;
        addComponent(spFilas, 0, 1, 2, 2);
        
        //Cpus
        pCpus = new PanelCpus();
        pCpus.setBackground(Color.lightGray);
        pCpus.setBorder(borda);
        constraints.weightx = 1;
        addComponent(pCpus, 0, 3, 1, 2);
        
        //Recursos
        pRecursos = new PanelRecursos();
        pRecursos.setBackground(new Color(144,238,144));
        pRecursos.setBorder(borda);
        constraints.weightx = 1;
        addComponent(pRecursos, 0, 4, 1, 2);
        
        
        //Memoria
        pMemoria = new PanelMemoria();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 60;
        constraints.weightx = 1;
        addComponent(pMemoria, 2, 0, 5, 1);
        
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
        addComponent(spTimeline, 3, 0, 5, 1);
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
    
    private void carregarArquivo(String path) {
        try {
            File file = new File(path);
            Scanner scanner = new Scanner(file);     
            resetarEscalonador();
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
            pros = fe.toArray(new Processo[fe.size()]);
            criarLinhas();

        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    //eventos dos botoes
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btAbrir) {
            FileDialog dialogAbrir = new FileDialog(new Dialog(this), "Abrir arquivo de processos", FileDialog.LOAD);
            dialogAbrir.setVisible(true);
            path = dialogAbrir.getDirectory() + dialogAbrir.getFile();
            btSimular.setEnabled(true);
        }
        else if (e.getSource() == btSimular) {
            carregarArquivo(path);
            estado = EXECUTANDO;
            btPausar.setEnabled(true);
            btSimular.setEnabled(false);
        }
        else if (e.getSource() == btPausar) {
            if (estado == EXECUTANDO) {
                estado = PAUSADO;
                btPausar.setText("Retomar");
            }
            else {
                estado = EXECUTANDO;
                btPausar.setText("Pausar");
            }
        }
    }
    
    //criar as linhas para a tabela
    public static void criarLinhas() {
       rows = new Object[pros.length][7];
       for (int i = 0; i < pros.length; i++){
           rows[i][0] = pros[i].nome;
           rows[i][1] = pros[i].tempoC;
           rows[i][2] = pros[i].estado;
           rows[i][3] = pros[i].pri;
           rows[i][4] = pros[i].tProc;
           rows[i][5] = pros[i].turnAround;
           rows[i][6] = pros[i].tDeFilaNorm;
       }
    }
//</editor-fold>
    
//<editor-fold defaultstate="collapsed" desc="Metodo main">
    public static void main(String[] args) {
        uDeProcss.setSize(4);
        inicializaVectorCpu();
        
        new Escalonador().setVisible(true);
        
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Metodo run da thread">
    @Override
    public void run() {
        Random r = new Random();
        int prio = 0;
        
        while (true) {
        
        while (estado == EXECUTANDO && (!fe.isEmpty() || !fu.isEmpty() || !ftr.isEmpty() || memoria.temPrcssMemoria())){
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
                
                if(clock%3 == 0){
                     prio = r.nextInt(3) + 1;
                     memoria.bloqueiaProcss(prio);
                     memSec.bloqueiaProcss(prio);
                 }

                 if(clock%4 == 0){
                     prio = r.nextInt(3) + 1;
                     memoria.desbloqProcss(prio);
                     memSec.desbloqProcss(prio);
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
                
                
                clock++;
                
                //interface
                criarLinhas();
                Object[] columns = {"Processo", "Chegada", "Estado", "Prioridade", "Duração", "Turnaround", "Normalizado"};
                DefaultTableModel model = new DefaultTableModel(rows, columns);
                //model.setColumnIdentifiers(columns);
                tProcesso.setModel(model);
                panelF1.repaint();
                pCpus.repaint();
                pRecursos.repaint();
                pMemoria.repaint();
                timeline.repaint();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        if (estado == EXECUTANDO) {
            estado = PAUSADO;
            btPausar.setEnabled(false);
            btSimular.setEnabled(true);
            JOptionPane.showMessageDialog(this, "Simulação concluida com sucesso!");
            
        }
        this.repaint();
        }
    }
//</editor-fold>
    
}
