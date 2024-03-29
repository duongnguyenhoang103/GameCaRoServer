/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.client;

import caro.common.KMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Cao Ky
 */
public class WaitCompetition extends javax.swing.JFrame implements inReceiveMessage{
ListenServer listenServer;
    /**
     * Creates new form WaitCompetition
     */
    public WaitCompetition(ListenServer listenServer) {
        initComponents();
        
        setLocationRelativeTo(null);
        this.listenServer = listenServer;
        this.listenServer.receive = this;
        
        setTitle("Game Caro");
        if (listenServer.user!=null)
        {
            setTitle("Game Caro - " + listenServer.user.getUsername());
        }
        try {
            listenServer.SendMessage(21, null);
            listenServer.SendMessage(70, null);
        } catch (IOException ex) {
            Logger.getLogger(RoomFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnBack.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblStatus = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblStatus.setText("Wait....");

        btnBack.setText("Back to Room");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack)
                    .addComponent(lblStatus))
                .addContainerGap(273, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(lblStatus)
                .addGap(18, 18, 18)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(150, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        // TODO add your handling code here:
        try {
            listenServer.SendMessage(39, null);   
            java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                    new RoomFrame(listenServer).setVisible(true);
                }
            });
            this.dispose();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnBackMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(WaitCompetition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(WaitCompetition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(WaitCompetition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(WaitCompetition.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new WaitCompetition().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables

    @Override
    public void ReceiveMessage(KMessage msg) throws IOException {
        switch (msg.getType()) {
            case 20: // get ban co
            {
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new Main(listenServer).setVisible(true);
                    }
                });
                this.dispose();
                break;
            }
            case 76: // chien thang
            {
                lblStatus.setText(msg.getObject().toString());
                btnBack.setVisible(true);
                break;
            }
        }
    }
}
