/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.tcpServer;

import com.survey.energyMeter.general.controller.ModemResReadSaveController;
import com.survey.energyMeter.tableClasses.JunctionBean;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pooja
 */
public class TcpServer extends HttpServlet {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ClientResponder clientResponder;
    private String driverClass;
    private String connectionString;
    private Map<String, ClientResponder> map;
    private Map<Integer, JunctionBean> junctionList;
    private String db_userName;
    private String db_userPassword;
    private JunctionBean junction;
    private HttpServletRequest request;
    private ServletContext ctx;
    private ModemResReadSaveController modemResReadSaveCont;
    private List<ClientResponder> ipList = new ArrayList<ClientResponder>();

    public TcpServer(int serverPort) throws IOException {
        super();
        try {
            serverSocket = new ServerSocket(serverPort);
            if (serverSocket.getLocalPort() == -1) {
                throw new Exception(serverPort + " port not available for this server");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void interceptRequests() {
        try {
            while (true) {
                System.out.println("Waiting for Client to connect..."); //  clientSocket is a Server's Socket Object which is conncted to the Socket Object of the client
                clientSocket = serverSocket.accept();  // New Socket object created on the server which is connected to the Socket object of the client ( so, communication is possible now)
                InetAddress ipAddress = clientSocket.getInetAddress(); // returns the address of the client's computer to which this socket(server socket) is connected to.
                int port = clientSocket.getPort();  // returns the port of the remote(ie client's) socket
                String str = ipAddress.toString();
                String ipaddr = str.substring(1, str.length());
                String ipaddress = ipaddr + "" + port;
                System.out.println("Client connected. clientSocket.isConnected();-: " + clientSocket.isConnected());
                System.out.println("ipaddress" + ipaddress);
                InputStream inputStream = clientSocket.getInputStream(); // returns the inputStream object of the server which is connected to the outputStream of the client
                OutputStream outputStream = clientSocket.getOutputStream(); // returns the outputStream object of the server which is connected to the input stream of the client

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Calendar cal = Calendar.getInstance();

                clientResponder = new ClientResponder(inputStream, outputStream);
                modemResReadSaveCont = new ModemResReadSaveController();

                clientResponder.setDriverClass(driverClass);
                clientResponder.setConnectionString(connectionString);
                clientResponder.setDb_userName(db_userName);
                clientResponder.setDb_userPasswrod(db_userPassword);
                clientResponder.setJunctionList(junctionList);
                clientResponder.setRequest(request);
                clientResponder.setTcpServer(serverSocket);
                clientResponder.setClientSocket(clientSocket);

                clientResponder.setIPAddress(ipaddr);
                clientResponder.setIpPort(port + "");
                clientResponder.setIpStatus(clientSocket.isClosed());
                clientResponder.setIpLoginTimstamp(dateFormat.format(cal.getTime()));

                junction.setClientResponder(clientResponder);
                clientResponder.setJunction(junction);

//-----------------------------------old code start  -----------------------------------------------------------------------------------------
//                for(ClientResponder cr: ipList){
//                    if(cr.getIpAddress().equals(this.clientResponder.getIpAddress())
//                    /*--&& cr.getIpPort().equals(this.clientResponder.getIpPort())*/) {
//                            ipList.remove(cr);
//                        }
//                    }
//-----------------------------------old code end  --------------------------------------------------------------------------------------------

//                *******  new code implemented to avoid concurrent modification thread exception over collection.  **********
//-----------------------------------new code start  ------------------------------------------------------------------------------------------
                // Get an iterator.
	          Iterator<ClientResponder> ite = ipList.iterator();
	          /* Remove the second value of the list, while iterating over its elements,
	           * using the iterator's remove method. */
	          while(ite.hasNext()) {
	               ClientResponder cr = ite.next();
	               if(cr.getIpAddress().equals(this.clientResponder.getIpAddress())
                    /*--&& cr.getIpPort().equals(this.clientResponder.getIpPort())*/) {
	                    ite.remove();
	               }
                }
//-----------------------------------new code end   ------------------------------------------------------------------------------------------
                

                ipList.add(clientResponder);
                ctx.setAttribute("connectedIp", ipList);
                ctx.setAttribute("junction", junction);

                clientResponder.setContext(ctx);
                modemResReadSaveCont.setCtx(ctx);

                map.put(ipaddr, clientResponder);
                System.out.println("map-data: " + map.get(ipaddr) + "," + map.values());
                clientResponder.setMap(map);
                map.containsKey(ipaddr);
                (new Thread(clientResponder)).start();

                // Start a new thread to READ/WRITE data from/to client.


            }
        } catch (IOException ioEx) {
            System.out.println("EnergyMeter TCPServer main() Error: " + ioEx);
        }
    }

    public ClientResponder getClientResponder() {
        return clientResponder;
    }

    public void setJunction(JunctionBean junction) {
        this.junction = junction;
    }

    public void setContext(ServletContext ctx) {
        this.ctx = ctx;
    }

    public void setMap(Map<String, ClientResponder> map) {
        this.map = map;
    }

    public void setJunctionList(Map<Integer, JunctionBean> junctionList) {
        this.junctionList = junctionList;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPassword = db_userPasswrod;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("TcpServer: finalize , " + "Hello Client");
        closeClientSocket();
        closeServerSocket();
    }

    public void closeServerSocket() {
        try {
//            clientResModel.updateRecord(null);
//            async.destroy();
            serverSocket.close();
            System.out.println("TcpServer: closeServerSocket , " + "Hello Client");
        } catch (Exception ioEx) {
            System.out.println("TCPServer closeSocket() Error: " + ioEx);
        }
    }

    public void closeClientSocket() {
        try {
//            clientResponder.destroy();
            clientSocket.close();
            System.out.println("TcpServer: closeClientSocket , " + "Hello Client");
        } catch (Exception ioEx) {
            System.out.println("TCPServer closeClientSocket() Error: " + ioEx);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
