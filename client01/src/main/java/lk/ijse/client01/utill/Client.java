package lk.ijse.client01.utill;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread{
    private ArrayList<Client> clients;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public Client(Socket socket, ArrayList<Client> clients){
        try {
            this.socket = socket;
            this.clients = clients;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run(){
        try {
            String msg;
            while ((msg=bufferedReader.readLine())!=null){
                if (msg.equalsIgnoreCase("exit")){
                    break;
                }
                for (Client cl:clients){
                    cl.printWriter.println(msg);
                }
            }
        }catch (Exception e){

        }

        finally {
            try {
                bufferedReader.close();
                printWriter.close();
                socket.close();
            }catch (IOException e){

            }
        }
    }
}
