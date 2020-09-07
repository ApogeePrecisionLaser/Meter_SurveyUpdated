package com.survey.energyMeter.general.controller;

import com.survey.energyMeter.general.model.Signal;
import com.survey.energyMeter.tableClasses.JunctionBean;
import com.survey.energyMeter.tcpServer.ClientResponder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EM_StatusUpdaterController extends HttpServlet {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private JunctionBean junction;
    private ServletContext ctx;
    private boolean responseFromModemForClearance;
    private boolean backToNormalStatus;
    DateFormat dateFormat;
    private Date date;
    private Calendar cal;
    String currentTime;
    private String lastVisitedTime;

    public void init()
            throws ServletException {
        super.init();
        Signal signal = new Signal();
        getServletContext().setAttribute("signal", signal);
        System.out.println("\n\nSC_HC: " + getServletContext().hashCode() + " signal obj set.  ObjHC: " + signal.hashCode() + "   ObjAddr: " + signal);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();

        PrintWriter out = response.getWriter();
        ctx = getServletContext();
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.cal = Calendar.getInstance();
        this.currentTime = this.dateFormat.format(this.cal.getTime());
        this.lastVisitedTime = this.currentTime;
        ctx = getServletContext();
        JunctionBean jun = (JunctionBean) ctx.getAttribute("junction");
        ClientResponder clientResponder = jun.getClientResponder();

        response.setContentType("text/html");

        String task = request.getParameter("task");
        task = task != null ? task.trim() : "";
        String hours = request.getParameter("h");
        String minutes = request.getParameter("m");
        String seconds = request.getParameter("s");
        String currentTime = hours + ":" + minutes + ":" + seconds;

        Signal signal = (Signal) ctx.getAttribute("signal");
        synchronized (signal) {
            signal.setCurrentTime(currentTime);
            System.out.println("\n\nTime " + signal.getCurrentTime() + " updated successfully." + " ObjHC: " + signal.hashCode() + "   ObjAddr: " + signal + "ServletContext Hashcode: " + ctx.hashCode());
        }
        if (task.equals("Change To Green")) {
            String sideNo1 = null;
            String junctionName = request.getParameter("junctionName");
            int junctionID = Integer.parseInt(request.getParameter("junctionId"));
            String radioBtnValue = request.getParameter("radioBtnValue");
            if (radioBtnValue.equals("1")) {
                sideNo1 = radioBtnValue;
            } else if (radioBtnValue.equals("2")) {
                sideNo1 = radioBtnValue;
            } else if (radioBtnValue.equals("3")) {
                sideNo1 = radioBtnValue;
            } else if (radioBtnValue.equals("4")) {
                sideNo1 = radioBtnValue;
            }
            String res = "126 126 126 126 6 " + junctionID + " " + sideNo1 + " " + "125" + " " + "125";
            try {
                System.out.println("clearance instant res..." + res);
            } catch (Exception ex) {
                Logger.getLogger(ClientResponder.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (clientResponder.sendResponse(res)) {
                this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.cal = Calendar.getInstance();
                currentTime = this.dateFormat.format(this.cal.getTime());
                String currentVisitedTime = currentTime;
                int calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                int calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                int calculatedDifference = calculateCurrentTime - calculateLastTime;
                this.responseFromModemForClearance = jun.isResponseFromModemForClearance();
                if (!this.responseFromModemForClearance) {
                    while (calculatedDifference < 20) {
                        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        this.cal = Calendar.getInstance();
                        currentTime = this.dateFormat.format(this.cal.getTime());
                        currentVisitedTime = currentTime;
                        calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                        calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                        calculatedDifference = calculateCurrentTime - calculateLastTime;
                        this.responseFromModemForClearance = jun.isResponseFromModemForClearance();
                        if (this.responseFromModemForClearance == true) {
                            break;
                        }
                    }
                }

                if (this.responseFromModemForClearance == true) {
                    System.out.println("responseFromModemForClearance status is----true");

                    request.getRequestDispatcher("em_statusShowerCont").forward(request, response);
                    return;
                }

                String jSON_format = "Timeout... Modem didn't responds relative to changing <b>" + junctionName + "</b> signal status.";

                request.setAttribute("message", jSON_format);
                request.getRequestDispatcher("errorView").forward(request, response);
                return;
            }

            System.out.println("Unable to send the request for clearance function");

            String jSON_format = "Unable to send the request for clearance function corresponding to <b>" + junctionName;
            request.setAttribute("message", jSON_format);
            request.getRequestDispatcher("errorView").forward(request, response);

            return;
        }

        if (task.equals("Back To Normal")) {
            String sideNo1 = null;
            String junctionName = request.getParameter("junctionName");
            int junctionID = Integer.parseInt(request.getParameter("junctionId"));
            String res = "126 126 126 126 7 " + junctionID + " " + "125" + " " + "125";
            System.out.println("Back To Normal Request - - " + res);
            if (clientResponder.sendResponse(res)) {
                request.getRequestDispatcher("em_statusShowerCont").forward(request, response);
                return;
            }
            System.out.println("Unable to send the request for back to normal function");

            String jSON_format = "Unable to send the request for back to normal function corresponding to <b>" + junctionName;
            request.setAttribute("message", jSON_format);
            request.getRequestDispatcher("errorView").forward(request, response);

            return;
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    private boolean updateModemCurrentTime(String currentTime) {
        int rowsAffected = -1;
        String query = "UPDATE modem SET modem_current_time = ? where modem_id = 1 ";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            pstmt.setString(1, currentTime);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EM_StatusUpdaterController updateModemCurrentTime() Error: " + e);
        }
        return rowsAffected > 0;
    }

    private void setConnection() {
        try {
            Class.forName(this.driverClass);
            this.connection = DriverManager.getConnection(this.connectionString, this.db_userName, this.db_userPasswrod);
        } catch (Exception e) {
            System.out.println("EM_StatusUpdaterController setConnection() Error: " + e);
        }
    }

    public ServletContext getCtx() {
        return this.ctx;
    }

    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }
}
