package com.survey.energyMeter.tcpServer;

import com.survey.energyMeter.general.tableClasses.History;
import com.survey.energyMeter.tableClasses.JunctionBean;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Async extends HttpServlet
        implements Runnable {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Map<String, Boolean> statusMap;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String connectionString;
    private String driverClass;
    private String db_userName;
    private String db_userPassword;
    private String currentTime;
    private JunctionBean junction;
    private String clientStatus = "N";
    private Calendar cal;
    private ServletContext ctx;
    private DateFormat dateFormat;
    private String lastVisitedTime;
    private boolean responseFromModemForRefresh;
    ClientResponderModel clientResponderModel = new ClientResponderModel();

    public void run() {
        this.clientResponderModel.setDriverClass(this.driverClass);
        this.clientResponderModel.setConnectionString(this.connectionString);
        this.clientResponderModel.setDb_userName(this.db_userName);
        this.clientResponderModel.setDb_userPasswrod(this.db_userPassword);
        this.clientResponderModel.setConnection();
        try {
            int j = 0;
            while (true) {
                synchronized (this) {
                    this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    this.cal = Calendar.getInstance();

                    ClientResponder clientResponder = this.junction.getClientResponder();

                    String res = "";
                    String currentTime = this.dateFormat.format(this.cal.getTime());
                    this.lastVisitedTime = currentTime;
                    int calculateCurrentTimeInSeconds = calculateTimeInSeconds(currentTime);
                    int calculateLastVisitedTimeInSeconds = calculateTimeInSeconds(this.junction.getLastVisitedTime());

                    String lastVisitedTime1 = this.junction.getLastVisitedTime();
                    int junctionID = this.junction.getJunction_id();
                    int program_version_no = this.junction.getProgram_version_no();

                    //System.out.println("Async :clientSocket closed Status ####### " + this.clientSocket.isClosed());
                    if (!this.clientSocket.isClosed()) ;
                    History historyList = new History();
                    String ipAddress = this.clientSocket.getInetAddress().toString();
                    int port = this.clientSocket.getPort();
                    String str = ipAddress.toString();
                    String ipaddr = str.substring(1, str.length());
                    String ipaddress = ipaddr + ":" + port;
                    boolean closedStatus = this.clientSocket.isClosed();
                    this.statusMap = new HashMap();
                    this.statusMap.put(ipAddress, Boolean.valueOf(closedStatus));
                    historyList.setJunction_id(junctionID);
                    historyList.setProgram_version_no(program_version_no);
                    historyList.setIp_address(ipaddr);
                    historyList.setPort(port);
                    historyList.setStatus(closedStatus);
                    try {
                        if (j == 0) {
                            System.out.println("Async :serverSocket.isClosed()------" + closedStatus);
                            this.clientResponderModel.insertRecord(historyList);
                            j = 1;
                        }
                        else if((this.clientSocket.isClosed()) && (j == 1)) {
                            this.clientResponderModel.updateRecord(historyList);
                            this.clientResponderModel.closeConnection();
                            clientResponder.setRequestForActivity(false);
                            clientResponder.setIsJunctionLive(false);
                            clientResponder.setNoOfRequestReceived(0);
                            j = 3;
                        }
                        else if((calculateCurrentTimeInSeconds - calculateLastVisitedTimeInSeconds > 1000) && (this.clientStatus.equals("N"))) {
                            //System.out.println("CLOSE after 1000 sec.----- currentTime--- " + currentTime + ", " + "lastVisitedTime--- " + this.junction.getLastVisitedTime());
                            this.clientSocket.close();
                            List<ClientResponder> connectedIpList = (List<ClientResponder>) this.ctx.getAttribute("connectedIp");
                            if (connectedIpList != null && connectedIpList.contains(clientResponder)) {
                                connectedIpList.remove(clientResponder);
                                this.ctx.setAttribute("connectedIp", connectedIpList);
                            }
                            setClientStatus("Y");
                        }
                    } catch (Exception e) {
                    }

                }
            }
        } catch (Exception interruptedEx) {
            System.out.println("ASYNC run() Error: " + interruptedEx);
        }
    }

    public int calculateTimeInSeconds(String Time) {
        String[] currTime = Time.split(" ");
        String strTime = currTime[1];

        String[] currStr = strTime.split(":");
        int Hr = Integer.parseInt(currStr[0]);
        int Min = Integer.parseInt(currStr[1]);
        int Sec = Integer.parseInt(currStr[2]);
        int calculatedTime = Hr * 60 * 60 + Min * 60 + Sec;

        return calculatedTime;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPassword = db_userPasswrod;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public void setTcpServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void setJunction(JunctionBean junction) {
        this.junction = junction;
    }

    public String getClientStatus() {
        return this.clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }
}

