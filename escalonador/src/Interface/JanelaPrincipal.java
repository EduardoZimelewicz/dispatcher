/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

/**
 *
 * @author fabri
 */
public class JanelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form JanelaPrincipal
     */
    public JanelaPrincipal() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SPTimeline = new javax.swing.JScrollPane();
        SPTabela = new javax.swing.JScrollPane();
        TProcesso = new javax.swing.JTable();
        PCpus = new javax.swing.JPanel();
        PRecursos = new javax.swing.JPanel();
        PMenoria = new javax.swing.JPanel();
        PBotoes = new javax.swing.JPanel();
        BtAbrir = new javax.swing.JButton();
        BtSimular = new javax.swing.JButton();
        SPFilas = new javax.swing.JScrollPane();
        LFIlasDeProntos = new javax.swing.JLabel();
        LProcessos = new javax.swing.JLabel();
        LCpus = new javax.swing.JLabel();
        LRecursos = new javax.swing.JLabel();
        LMemoria = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("FJanelaPrincipal"); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 600));

        SPTimeline.setBackground(new java.awt.Color(204, 102, 255));

        SPTabela.setBackground(new java.awt.Color(204, 255, 204));

        TProcesso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        SPTabela.setViewportView(TProcesso);

        PCpus.setBackground(new java.awt.Color(204, 204, 255));

        javax.swing.GroupLayout PCpusLayout = new javax.swing.GroupLayout(PCpus);
        PCpus.setLayout(PCpusLayout);
        PCpusLayout.setHorizontalGroup(
            PCpusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        PCpusLayout.setVerticalGroup(
            PCpusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PRecursos.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout PRecursosLayout = new javax.swing.GroupLayout(PRecursos);
        PRecursos.setLayout(PRecursosLayout);
        PRecursosLayout.setHorizontalGroup(
            PRecursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        PRecursosLayout.setVerticalGroup(
            PRecursosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        PMenoria.setBackground(new java.awt.Color(255, 102, 204));
        PMenoria.setPreferredSize(new java.awt.Dimension(780, 50));

        javax.swing.GroupLayout PMenoriaLayout = new javax.swing.GroupLayout(PMenoria);
        PMenoria.setLayout(PMenoriaLayout);
        PMenoriaLayout.setHorizontalGroup(
            PMenoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 780, Short.MAX_VALUE)
        );
        PMenoriaLayout.setVerticalGroup(
            PMenoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        PBotoes.setBackground(new java.awt.Color(153, 204, 255));

        BtAbrir.setText("Abrir");

        BtSimular.setText("Simular");
        BtSimular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSimularActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PBotoesLayout = new javax.swing.GroupLayout(PBotoes);
        PBotoes.setLayout(PBotoesLayout);
        PBotoesLayout.setHorizontalGroup(
            PBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PBotoesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtAbrir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtSimular)
                .addContainerGap())
        );
        PBotoesLayout.setVerticalGroup(
            PBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PBotoesLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(PBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(BtAbrir)
                    .addComponent(BtSimular))
                .addContainerGap())
        );

        LFIlasDeProntos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LFIlasDeProntos.setText("Fila de Prontos");

        LProcessos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LProcessos.setText("Processos");

        LCpus.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LCpus.setText("Cpus");

        LRecursos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LRecursos.setText("Recursos");

        LMemoria.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        LMemoria.setText("Memória");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SPTimeline, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PMenoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(SPTabela, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(PBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(LProcessos))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SPFilas)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(LFIlasDeProntos)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(PCpus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LCpus))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LRecursos)
                            .addComponent(PRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LMemoria, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LFIlasDeProntos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LProcessos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LCpus, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LRecursos, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SPTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PCpus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PRecursos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SPFilas, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LMemoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PMenoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SPTimeline, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtSimularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSimularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtSimularActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JanelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JanelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtAbrir;
    private javax.swing.JButton BtSimular;
    private javax.swing.JLabel LCpus;
    private javax.swing.JLabel LFIlasDeProntos;
    private javax.swing.JLabel LMemoria;
    private javax.swing.JLabel LProcessos;
    private javax.swing.JLabel LRecursos;
    private javax.swing.JPanel PBotoes;
    private javax.swing.JPanel PCpus;
    private javax.swing.JPanel PMenoria;
    private javax.swing.JPanel PRecursos;
    private javax.swing.JScrollPane SPFilas;
    private javax.swing.JScrollPane SPTabela;
    private javax.swing.JScrollPane SPTimeline;
    private javax.swing.JTable TProcesso;
    // End of variables declaration//GEN-END:variables
}
