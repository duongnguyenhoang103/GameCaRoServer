/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.server;

import static caro.client.LoginForm.socket;
import static caro.client.LoginForm.user;
import caro.common.GPos;
import caro.common.KMessage;
import caro.common.Users;
import caro.database.DataFunc;
import static caro.server.Main.lstCompetition;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nguyen Cao Ky
 */
public class ClientHandler extends Thread {
        public Room room = null;
    
        private Socket socket;
        ObjectInputStream inputStream;
        ObjectOutputStream outputStream;

        public Users user;
        
        Boolean execute = true;
        
        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            
            execute = true;
        }

        void Send(String strSend) throws IOException
        {
            //Users temp = new Users(1, strSend, "123456", 100);
            //outputStream.writeObject(temp);
        }
        
        void Guilai() throws IOException, InterruptedException {
            //Users temp = new Users(1, "Server gui lai", "123456", 100);
            //outputStream.writeObject(temp);
            //Thread.sleep(1000);
        }

        void ReceiveMessage(KMessage msg) throws IOException {
            
            switch (msg.getType()) {
                case 0: {
                    Users temp = (Users)msg.getObject();
                    DataFunc df = new DataFunc();
                    user = df.checkLogin(temp.getUsername(), temp.getPassword());
                    if(user != null)
                    {
                        Boolean flag = true;
                        // Kiem tra coi da co ai dang nhap hay chua
                        for (ClientHandler cli : Main.lstClient) {
                            if (cli!=this && cli.user!=null && cli.user.getUsername().equalsIgnoreCase(user.getUsername()))
                            {
                                user = null;
                                break;
                            }
                        }
                        if (user!=null)
                            System.out.println("Server: Xin chao " + user.getUsername());
                    }
                    SendMessage(0, user);
                    break;
                }
                case 1: {
                    Users temp = (Users)msg.getObject();
                    DataFunc df = new DataFunc();
                    boolean succ;
                    succ = df.checkAva(df.getId(temp.getUsername()));
                    if (succ == true) {
                        SendMessage(1, temp.getUsername() + " is Available");
                        return;
                    }

                    succ = df.register(temp.getUsername(), temp.getPassword());
                    if (succ == true) {
                        SendMessage(1, "Register succesfull");
                    }
                    
                    break;
                }
                case 10: //chuyen de chat chit
                {
                    System.out.println(msg.getObject().toString());
                    break;
                }
                //Room
                case 20: // Join room
                {
                    room = Main.lstRoom.get(Integer.parseInt(msg.getObject().toString()));
                    if (room.add(this)==false) //full
                    {
                        int[] arrRoom = new int[Main.lstRoom.size()];
                        for (int i=0; i<Main.lstRoom.size(); i++)
                        {
                            arrRoom[i] = Main.lstRoom.get(i).countAvailable();
                        }
                        SendMessage(22, arrRoom);
                    }
                    else
                        SendMessage(20, null);
                    
                    break;
                }
                case 21: //Get all room
                {
                    int[] arrRoom = new int[Main.lstRoom.size()];
                    for (int i=0; i<Main.lstRoom.size(); i++)
                    {
                        arrRoom[i] = Main.lstRoom.get(i).countAvailable();
                    }
                    SendMessage(21, arrRoom);
                    break;
                }
                case 28:
                {
                    if (room.client1!=null && room.client2!=null)
                    {
                        Users[] arrUser = new Users[2];
                        arrUser[0] = room.client1.user;
                        arrUser[1] = room.client2.user;
                        room.client1.SendMessage(34, arrUser);
                        room.client2.SendMessage(34, arrUser);
                        
                        for (ClientHandler cli : room.lstClientView) {
                            cli.SendMessage(34, arrUser);
                        }
                        
                        room.client2.SendMessage(36, null);
                    }
                    break;
                }
                case 30: // Lay ban co
                {
                    GPos gPos = (GPos)msg.getObject();
                    if (gPos!=null)
                        room.put(this, gPos);
                    
                    if (room != null) {
                        for (ClientHandler cli : room.lstClientView) {
                            cli.SendMessage(30, room.pieceses);
                        }
                    }
        
                    break;
                }
                case 39: //Exit room
                {
                    if (room!=null)
                    {
                        room.clientExit(this);
                    }
                    break;
                }
                case 40: //Chat
                {
                    if (room!=null)
                    {
                        // Gui cho 2 client
                        if (room.client1!=this)
                            room.client1.SendMessage(msg);
                        if (room.client2!=this)
                            room.client2.SendMessage(msg);

                        for (ClientHandler cli : room.lstClientView) {
                            if (cli!=this)
                            {
                                cli.SendMessage(msg);
                            }
                        }
                    }
                    break;
                }
                case 41: //View
                {
                    room = Main.lstRoom.get(Integer.parseInt(msg.getObject().toString()));
                    room.lstClientView.add(this);
                    SendMessage(20, null);
                    break;
                }
                    
                    
                    //// competition
                case 70:
                {
                    ArrayList<String> lstStrCompetition = new ArrayList<String>();
                    for (int i=0; i<Main.lstCompetition.size(); i++)
                    {
                        String temp;
                        if (Main.lstCompetition.get(i).stateCompetition==1)
                        {
                            temp = " processing...";
                        }
                        else if (Main.lstCompetition.get(i).stateCompetition==2)
                        {
                            temp = " end.";
                            if (Main.lstCompetition.get(i).userWinner!=null)
                                temp += " Winner is " + Main.lstCompetition.get(i).userWinner.getUsername();
                        }
                        else
                        {
                            temp = (Main.lstCompetition.get(i).sumClient - Main.lstCompetition.get(i).lstClient.size()) + " available";
                        }
                        lstStrCompetition.add(Main.lstCompetition.get(i).strNameCompetition + ": " + temp);
                    }
                    SendMessage(70, lstStrCompetition);
                    break;
                }
                case 71:
                {
                    int temp = Integer.parseInt(msg.getObject().toString());
                    if (Main.lstCompetition.get(temp).stateCompetition==0) //san sang thi duoc tham gia
                    {
                        Main.lstCompetition.get(temp).add(this);
                        SendMessage(72, null);
                    }
                    else
                        SendMessage(71, Main.lstCompetition.get(temp).strNameCompetition);
                    break;
                }
                case 80:
                {
                    final ArrayList<String> lstTemp = new ArrayList<String>();
                    for (ClientHandler cli : Main.lstClient) {
                        Users users = cli.user;
                        if (users!=null)
                        {
                            lstTemp.add(users.getUsername());
                        }
                    }
                    SendMessage(80, lstTemp);
                    break;
                }
                case 81: //Play giua 2 ng choi
                {
                    String strOtherUser = msg.getObject().toString();
                    ClientHandler tempClient = null;
                    for (ClientHandler cli : Main.lstClient) {
                        Users users = cli.user;
                        if (users.getUsername().equalsIgnoreCase(strOtherUser))
                        {
                            tempClient = cli;
                            break;
                        }
                    }
                    if (tempClient!=this && tempClient.room==null)
                    {
                        Room temp = new Room(99);
                        room = temp;
                        tempClient.room = temp;
                        temp.add(this);
                        SendMessage(20, null);
                        temp.add(tempClient);
                        tempClient.SendMessage(20, null);
                        
                    }
                    break;
                }
            }
        }

        public void SendMessage(int ty, Object obj) throws IOException {
            KMessage temp = new KMessage(ty, obj);
            SendMessage(temp);
        }
                
        public void SendMessage(KMessage msg) throws IOException {
            outputStream.reset();
            outputStream.writeObject(msg);
        }
        
        public Boolean closeClient() throws Throwable
        {
            
            
            if (room!=null) // Thong bao thoat room
            {
                room.clientExit(this);
            }
            
            Main.lstClient.remove(this);
            try {
                this.socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Client Exit");
            execute = false;
            
            
            return true;
        }
        
        @Override
        public void run() {
            
            while (execute) {
                
                try {
                    Object o = inputStream.readObject();
                    if (o != null) {
                        ReceiveMessage((KMessage)o);
                    }
                    //Guilai();
                } catch (IOException e) {
                    try {
                        closeClient();
                    } catch (Throwable ex) {
                        Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }


    }