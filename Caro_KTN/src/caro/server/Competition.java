/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package caro.server;

import caro.common.Users;
import caro.database.DataFunc;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nguyen Cao Ky
 */
public class Competition {
    public ArrayList<ClientHandler> lstClient = null;
    public int sumClient = 0;
    public String strNameCompetition;
    public int stateCompetition = 0; // Bat dau giai, dag tien hanh giai, da xog giai
    
    public Users userWinner = null;
    
    int roomid=100;
    
    public ArrayList<Room> lstRoom = null;
    
    public Competition(String strNameCompetition, int sumClient)
    {
        this.sumClient = sumClient;
        this.strNameCompetition = strNameCompetition;
        lstClient = new ArrayList<ClientHandler>();
        lstRoom = new ArrayList<Room>();
    }
    
    public void TaoRoom() throws IOException
    {
        for (int i=0; i<lstClient.size(); i+=2)
        {
            //lstClient.get(i).SendMessage(20, null);
            //lstClient.get(i+1).SendMessage(20, null);
            Room temp = new Room(++roomid);
            temp.competition = this;
            lstClient.get(i).room = temp;
            lstClient.get(i+1).room = temp;
            temp.add(lstClient.get(i));
            lstClient.get(i).SendMessage(20, null);
            temp.add(lstClient.get(i+1));
            lstClient.get(i+1).SendMessage(20, null);
            lstRoom.add(temp);
        }
    }
    
    public void startCompetition() throws IOException
    {
        stateCompetition = 1;
        TaoRoom();
    }
    
   
    public Boolean add(ClientHandler cli) throws IOException
    {
        for (ClientHandler clientHandler : lstClient) {
            if (cli==clientHandler)
                return false;
        }
        lstClient.add(cli);
        if (lstClient.size() == sumClient)
            startCompetition();
        return true;
    }
    
    public Boolean removeClient(ClientHandler cli)
    {
        cli.room=null;
        lstClient.remove(cli);
        return true;
    }
    
    public Boolean removeRoom(Room r) throws IOException
    {
        lstRoom.remove(r);
        if (r.client1!=null && r.client1.room!=null)
            r.client1.room=null;
        if (r.client2!=null && r.client2.room!=null)
            r.client2.room=null;
        if (lstRoom.size()==0)
        {
            if (lstClient.size()<=1)
            {
                stateCompetition = 2;//ket thuc giai
                if (lstClient.size()==1)
                {
                    userWinner = lstClient.get(0).user;
                    lstClient.get(0).SendMessage(76, "Ban la nguoi chien thang competition " + strNameCompetition);
                    
                    // Luu vao database
                    lstClient.get(0).user.setScore(lstClient.get(0).user.getScore()+1000);
                    try {
                        DataFunc dataFunc = new DataFunc();
                        dataFunc.updateUser(lstClient.get(0).user);
                    } catch (SQLException ex) {
                        Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else
            {
                TaoRoom();
            }
            
        }
        return true;
    }
}
