/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.client;

import caro.common.GPos;
import caro.common.CPiece;
import caro.common.KMessage;
import caro.common.Users;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/**
 *
 * @author Nguyen Cao Ky
 */
public class Main extends javax.swing.JFrame implements inReceiveMessage{

    ListenServer listenServer;
    //PanelBoard windowPanel;
    
    JGoban Goban;
    JScrollPane jScroll;

    int GameState = 0;
    static final int WAIT = 0;
    static final int MY_TURN = 1;
    static final int YOU_WIN = 2;
    static final int YOU_LOSE = 3;
    /**
     * Creates new form Main
     */
    public Main(ListenServer listenServer) {
        initComponents();

        
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 565);
        
        
        setLayout(new BorderLayout());
        //windowPanel = new PanelBoard(socket);
        //add("Center", windowPanel);
        
        InitGame();
        setLocationRelativeTo(null);
        
        
        this.listenServer = listenServer;
        this.listenServer.receive = this;
        setTitle("Game Caro - " + listenServer.user.getUsername());
        try {
            listenServer.SendMessage(28, null); //Lay thong tin 2 ng
            listenServer.SendMessage(30, null); //Lay ban co
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    void InitGame() {

        Goban = new JGoban();
        Goban.setPreferredSize(new Dimension(500,500));
        
        //jScroll = new JScrollPane(Goban);
        //Panel frame = new Panel();
        //frame.add(Goban);
        //panelCaro.add(Goban);
        panelCaro.setViewportView(Goban);

        Goban.Sakiyomi = true;
        Goban.Kinjite = true;

        Goban.init(500, 500);
        Goban.Initialize();
        Goban.Draw();
    }
    /*
    @Override
    public boolean mouseDown(Event evt,int x,int y) {
        if (GameState==MY_TURN)
        {
            GPos pos = new GPos();
            int offetX = Goban.getX() + panelCaro.getX()+this.getInsets().left ;
            int offetY = Goban.getY() + panelCaro.getY()+this.getInsets().top ;

            if( !Goban.GetPos(x-offetX,y-offetY,pos) )
                return true;

            if(Goban.Pieces[pos.x][pos.y].State == CPiece.EMPTY)
            {
                try {
                    GameState = WAIT;
                    putStatus("Doi...");
                    listenServer.SendMessage(30, pos);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                putStatus("Ban khong duoc danh vao vung nay!");
            }
        }
        return true;
    }

    @Override
    public boolean mouseUp(Event evt,int x,int y) {

        return true;
    }*/

    void putStatus(String strStt)
    {
        lblStatus.setText(strStt);
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g); //To change body of generated methods, choose Tools | Templates.        

    
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtChat = new javax.swing.JTextField();
        txtSendChat = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatHistory = new javax.swing.JTextArea();
        btnExitRoom = new javax.swing.JButton();
        panelCaro = new javax.swing.JScrollPane();
        lblName1 = new java.awt.Label();
        lblScore1 = new java.awt.Label();
        lblName2 = new java.awt.Label();
        lblScore2 = new java.awt.Label();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblStatus.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblStatus.setText("Doi nguoi choi thu 2");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("X");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("O");

        txtChat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtChatKeyPressed(evt);
            }
        });

        txtSendChat.setText("Send");
        txtSendChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSendChatMouseClicked(evt);
            }
        });

        ChatHistory.setEditable(false);
        ChatHistory.setColumns(20);
        ChatHistory.setRows(5);
        jScrollPane1.setViewportView(ChatHistory);

        btnExitRoom.setText("Exit Room");
        btnExitRoom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitRoomMouseClicked(evt);
            }
        });

        panelCaro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCaroMouseClicked(evt);
            }
        });

        lblName1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblName1.setText("...");

        lblScore1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblScore1.setText("...");

        lblName2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblName2.setText("...");

        lblScore2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblScore2.setText("...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelCaro, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtChat)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSendChat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblScore1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblName1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblScore2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblName2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addComponent(btnExitRoom, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(lblName1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblScore1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(lblName2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblScore2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSendChat, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(panelCaro, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExitRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                .addGap(44, 44, 44))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitRoomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitRoomMouseClicked
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
        
    }//GEN-LAST:event_btnExitRoomMouseClicked

    private void panelCaroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCaroMouseClicked
        // TODO add your handling code here:
        int x = evt.getX();
        int y = evt.getY();
        
        if (GameState==MY_TURN)
        {
            GPos pos = new GPos();
            int offetX = Goban.getX();
            int offetY = Goban.getY();

            if( !Goban.GetPos(x-offetX,y-offetY,pos) )
                return;

            if(Goban.Pieces[pos.x][pos.y].State == CPiece.EMPTY)
            {
                try {
                    
                    GameState = WAIT;
                    putStatus("Doi...");
                    listenServer.SendMessage(30, pos);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                putStatus("Ban khong duoc danh vao vung nay!");
            }
        }

    }//GEN-LAST:event_panelCaroMouseClicked

    private void txtSendChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSendChatMouseClicked
        // TODO add your handling code here:
        if (txtChat.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null, "Can't send message empty!", "Error", 1);
            return;
        }
        
        String strMess = listenServer.user.getUsername() + ": " + txtChat.getText();
        if (ChatHistory.getText().isEmpty())
            ChatHistory.setText(strMess);
        else
            ChatHistory.setText(ChatHistory.getText()+'\n'+strMess);
        txtChat.setText("");
        try {
            listenServer.SendMessage(40, strMess);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSendChatMouseClicked

    private void txtChatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtChatKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyChar()=='\n')
            txtSendChatMouseClicked(null);
    }//GEN-LAST:event_txtChatKeyPressed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                System.out.println("CLIENT");
//                new Main(null).setVisible(true);
//            }
//        });
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ChatHistory;
    private javax.swing.JButton btnExitRoom;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label lblName1;
    private java.awt.Label lblName2;
    private java.awt.Label lblScore1;
    private java.awt.Label lblScore2;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JScrollPane panelCaro;
    private javax.swing.JTextField txtChat;
    private javax.swing.JButton txtSendChat;
    // End of variables declaration//GEN-END:variables


    
    @Override
            public void ReceiveMessage(KMessage msg) throws IOException {
            switch (msg.getType()) {
                case 30: // get ban co
                {
                    Goban.Pieces = (CPiece[][])msg.getObject();
                    Goban.Draw();

                    break;
                }
                case 31:
                {
                    putStatus("Toi luot ban");
                    GameState = MY_TURN;
                    break;
                }
                case 34: // Thong tin 2 ng choi
                {
                    Users[] arrUser = (Users[])msg.getObject();
                    if (arrUser!=null && arrUser.length>=2)
                    {
                        lblName1.setText(arrUser[0].getUsername());
                        lblScore1.setText(""+arrUser[0].getScore());
                        lblName2.setText(arrUser[1].getUsername());
                        lblScore2.setText(""+arrUser[1].getScore());
                    }
                    
                    break;
                }
                case 35:
                {
                    if ("win".equalsIgnoreCase(msg.getObject().toString()))
                    {
                        GameState = YOU_WIN;
                        putStatus("Ban da thang");
                    }
                    else if ("lose".equalsIgnoreCase(msg.getObject().toString()))
                    {
                        GameState = YOU_LOSE;
                        putStatus("Ban da thua");
                    }
                    //System.out.println(msg.getObject());
                    break;
                }    
                case 36:
                {
                    putStatus("Doi...");

                    break;
                }   
                case 40: // Recieve Message
                {
                    String strMess = msg.getObject().toString();
                    if (ChatHistory.getText().isEmpty())
                        ChatHistory.setText(strMess);
                    else
                        ChatHistory.setText(ChatHistory.getText()+'\n'+strMess);
                    break;
                }   
                    
                case 75: // Thang cuoc competition
                {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        new WaitCompetition(listenServer).setVisible(true);
                    }
                    });
                    this.dispose();
                    break;
                }
            }
        }
}
