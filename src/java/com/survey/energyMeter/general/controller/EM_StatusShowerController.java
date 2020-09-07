package com.survey.energyMeter.general.controller;

import com.survey.energyMeter.general.model.Signal;
import com.survey.energyMeter.general.tableClasses.PlanInfo;
import com.survey.energyMeter.tcpServer.ClientResponder;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EM_StatusShowerController extends HttpServlet {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPasswrod;
    private PlanInfo planInfoList;
    private ClientResponder clientResponder;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ServletContext ctx = getServletContext();

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("jQueryRequest")) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                String time = null;
                Signal signal = (Signal) ctx.getAttribute("signal");
                if (signal != null) {
                    synchronized (signal) {
                        time = signal.getCurrentTime();
                    }
                } else {
                    System.out.println("signal object was not found");
                }

                out.print(time);
                out.flush();
                return;
            }
            if (task.equals("getLatestStatus")) {
                PrintWriter out = response.getWriter();
                this.planInfoList = ((PlanInfo) ctx.getAttribute("planInfolist"));
                if (this.planInfoList != null) {
                    boolean responseFromModemForRefresh = this.planInfoList.isResponseFromModemForRefresh();
                    boolean responseFromModemForClearnace = this.planInfoList.isResponseFromModemForClearance();

                    if ((responseFromModemForRefresh == true) || (responseFromModemForClearnace == true)) {
                        int functionNo = this.planInfoList.getFunction_no();
                        int junction_id = this.planInfoList.getJunction_id();
                        int side_no = this.planInfoList.getSide_no();
                        String junctionName = this.planInfoList.getJunction_name();
                        String sideName = this.planInfoList.getSideName();

                        int onTimeHr = this.planInfoList.getOn_time_hour();
                        int onTimeMin = this.planInfoList.getOn_time_min();
                        int onTimeSec = this.planInfoList.getOn_time_sec();
                        int OffTimeHr = this.planInfoList.getOff_time_hour();
                        int OffTimeMin = this.planInfoList.getOff_time_min();
                        int offTimeSec = this.planInfoList.getOff_time_sec();
                        String side1Name = this.planInfoList.getSide1Name();
                        String side2Name = this.planInfoList.getSide2Name();
                        String side3Name = this.planInfoList.getSide3Name();
                        String side4Name = this.planInfoList.getSide4Name();
                        String side5Name = this.planInfoList.getSide5Name();

                        int side1Time = this.planInfoList.getSide1_time();
                        int side2Time = this.planInfoList.getSide2_time();
                        int side3Time = this.planInfoList.getSide3_time();
                        int side4Time = this.planInfoList.getSide4_time();
                        int side1LeftStatus = this.planInfoList.getSide1_left_status();
                        int side1RightStatus = this.planInfoList.getSide1_right_status();
                        int side1UpStatus = this.planInfoList.getSide1_up_status();
                        int side1DownStatus = this.planInfoList.getSide1_down_status();
                        int side2LeftStatus = this.planInfoList.getSide2_left_status();
                        int side2RightStatus = this.planInfoList.getSide2_right_status();
                        int side2UpStatus = this.planInfoList.getSide2_up_status();
                        int side2DownStatus = this.planInfoList.getSide2_down_status();
                        int side3LeftStatus = this.planInfoList.getSide3_left_status();
                        int side3RightStatus = this.planInfoList.getSide3_right_status();
                        int side3UpStatus = this.planInfoList.getSide3_up_status();
                        int side3DownStatus = this.planInfoList.getSide3_down_status();
                        int side4LeftStatus = this.planInfoList.getSide4_left_status();
                        int side4RightStatus = this.planInfoList.getSide4_right_status();
                        int side4UpStatus = this.planInfoList.getSide4_up_status();
                        int side4DownStatus = this.planInfoList.getSide4_down_status();
                        String response_data = "functionNo=" + functionNo + "#$" + "junction_id=" + junction_id + "#$" + "side_no=" + side_no + "#$" + "junctionName=" + junctionName + "#$" + "sideName=" + sideName + "#$" + "onTimeHr=" + onTimeHr + "onTimeMin=" + onTimeMin + "#$" + "onTimeSec=" + onTimeSec + "#$" + "OffTimeHr=" + OffTimeHr + "#$" + "OffTimeMin=" + OffTimeMin + "#$" + "offTimeSec=" + offTimeSec + "side1Name=" + side1Name + "#$" + "side2Name=" + side2Name + "#$" + "side3Name=" + side3Name + "#$" + "side4Name=" + side4Name + "#$" + "side5Name=" + side5Name + "#$" + "side1Time=" + side1Time + "#$" + "side2Time=" + side2Time + "#$" + "side3Time=" + side3Time + "#$" + "side4Time=" + side4Time + "#$" + "side1LeftStatus=" + side1LeftStatus + "#$" + "side1RightStatus=" + side1RightStatus + "#$" + "side1UpStatus=" + side1UpStatus + "#$" + "side1DownStatus=" + side1DownStatus + "#$" + "side2LeftStatus=" + side2LeftStatus + "#$" + "side2RightStatus=" + side2RightStatus + "#$" + "side2UpStatus=" + side2UpStatus + "#$" + "side2DownStatus=" + side2DownStatus + "#$" + "side3LeftStatus=" + side3LeftStatus + "#$" + "side3RightStatus=" + side3RightStatus + "#$" + "side3UpStatus=" + side3UpStatus + "#$" + "side3DownStatus=" + side3DownStatus + "#$" + "side4LeftStatus=" + side4LeftStatus + "#$" + "side4RightStatus=" + side4RightStatus + "#$" + "side4UpStatus=" + side4UpStatus + "#$" + "side4DownStatus=" + side4DownStatus;

                        out.println(response_data);
                        out.flush();
                    }
                } else {
                    out.println("");
                }

            } else {
                this.planInfoList = ((PlanInfo) ctx.getAttribute("planInfolist"));
                int functionNo = this.planInfoList.getFunction_no();
                int junction_id = this.planInfoList.getJunction_id();
                int side_no = this.planInfoList.getSide_no();
                String junctionName = this.planInfoList.getJunction_name();
                String sideName = this.planInfoList.getSideName();

                int onTimeHr = this.planInfoList.getOn_time_hour();
                int onTimeMin = this.planInfoList.getOn_time_min();
                int onTimeSec = this.planInfoList.getOn_time_sec();
                int OffTimeHr = this.planInfoList.getOff_time_hour();
                int OffTimeMin = this.planInfoList.getOff_time_min();
                int offTimeSec = this.planInfoList.getOff_time_sec();
                String side1Name = this.planInfoList.getSide1Name();
                String side2Name = this.planInfoList.getSide2Name();
                String side3Name = this.planInfoList.getSide3Name();
                String side4Name = this.planInfoList.getSide4Name();
                String side5Name = this.planInfoList.getSide5Name();

                int side1Time = this.planInfoList.getSide1_time();
                int side2Time = this.planInfoList.getSide2_time();
                int side3Time = this.planInfoList.getSide3_time();
                int side4Time = this.planInfoList.getSide4_time();
                int side1LeftStatus = this.planInfoList.getSide1_left_status();
                int side1RightStatus = this.planInfoList.getSide1_right_status();
                int side1UpStatus = this.planInfoList.getSide1_up_status();
                int side1DownStatus = this.planInfoList.getSide1_down_status();
                int side2LeftStatus = this.planInfoList.getSide2_left_status();
                int side2RightStatus = this.planInfoList.getSide2_right_status();
                int side2UpStatus = this.planInfoList.getSide2_up_status();
                int side2DownStatus = this.planInfoList.getSide2_down_status();
                int side3LeftStatus = this.planInfoList.getSide3_left_status();
                int side3RightStatus = this.planInfoList.getSide3_right_status();
                int side3UpStatus = this.planInfoList.getSide3_up_status();
                int side3DownStatus = this.planInfoList.getSide3_down_status();
                int side4LeftStatus = this.planInfoList.getSide4_left_status();
                int side4RightStatus = this.planInfoList.getSide4_right_status();
                int side4UpStatus = this.planInfoList.getSide4_up_status();
                int side4DownStatus = this.planInfoList.getSide4_down_status();
                request.setAttribute("functionNo", Integer.valueOf(functionNo));
                request.setAttribute("junctionId", Integer.valueOf(junction_id));
                request.setAttribute("junctionName", junctionName);
                request.setAttribute("sideNo", Integer.valueOf(side_no));
                request.setAttribute("sideName", sideName);
                request.setAttribute("side1Name", side1Name);
                request.setAttribute("side2Name", side2Name);
                request.setAttribute("side3Name", side3Name);
                request.setAttribute("side4Name", side4Name);
                request.setAttribute("side5Name", side5Name);

                request.setAttribute("onTimeHr", Integer.valueOf(onTimeHr));
                request.setAttribute("onTimeMin", Integer.valueOf(onTimeMin));
                request.setAttribute("onTimeSec", Integer.valueOf(onTimeSec));
                request.setAttribute("OffTimeHr", Integer.valueOf(OffTimeHr));
                request.setAttribute("OffTimeMin", Integer.valueOf(OffTimeMin));
                request.setAttribute("offTimeSec", Integer.valueOf(offTimeSec));

                request.setAttribute("side1Time", Integer.valueOf(side1Time));
                request.setAttribute("side2Time", Integer.valueOf(side2Time));
                request.setAttribute("side3Time", Integer.valueOf(side3Time));
                request.setAttribute("side4Time", Integer.valueOf(side4Time));
                request.setAttribute("side1LeftStatus", Integer.valueOf(side1LeftStatus));
                request.setAttribute("side1RightStatus", Integer.valueOf(side1RightStatus));
                request.setAttribute("side1UpStatus", Integer.valueOf(side1UpStatus));
                request.setAttribute("side1DownStatus", Integer.valueOf(side1DownStatus));
                request.setAttribute("side2LeftStatus", Integer.valueOf(side2LeftStatus));
                request.setAttribute("side2RightStatus", Integer.valueOf(side2RightStatus));
                request.setAttribute("side2UpStatus", Integer.valueOf(side2UpStatus));
                request.setAttribute("side2DownStatus", Integer.valueOf(side2DownStatus));
                request.setAttribute("side3LeftStatus", Integer.valueOf(side3LeftStatus));
                request.setAttribute("side3RightStatus", Integer.valueOf(side3RightStatus));
                request.setAttribute("side3UpStatus", Integer.valueOf(side3UpStatus));
                request.setAttribute("side3DownStatus", Integer.valueOf(side3DownStatus));
                request.setAttribute("side4LeftStatus", Integer.valueOf(side4LeftStatus));
                request.setAttribute("side4RightStatus", Integer.valueOf(side4RightStatus));
                request.setAttribute("side4UpStatus", Integer.valueOf(side4UpStatus));
                request.setAttribute("side4DownStatus", Integer.valueOf(side4DownStatus));
                request.getRequestDispatcher("em_statusShower_view").forward(request, response);
                return;
            }
        } catch (Exception e) {
            System.out.println("EM_StatusShowerController- doPostError :" + e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    public String getCurrentTime() {
        DateFormat df = DateFormat.getTimeInstance(2);
        return df.format(new Date());
    }

    private String getModemCurrentTime() {
        String currentTime = null;
        String query = "SELECT modem_current_time FROM modem WHERE modem_id = 1 ";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                currentTime = rset.getString("modem_current_time");
            }
        } catch (Exception e) {
            System.out.println("EM_StatusShowerController getModemCurrentTime() Error: " + e);
        }
        return currentTime;
    }

    private void setConnection() {
        try {
            Class.forName(this.driverClass);
            this.connection = DriverManager.getConnection(this.connectionString, this.db_userName, this.db_userPasswrod);
        } catch (Exception e) {
            System.out.println("EM_StatusShowerController setConnection() Error: " + e);
        }
    }

    public ClientResponder getClientResponder() {
        return this.clientResponder;
    }

    public void setClientResponder(ClientResponder clientResponder) {
        this.clientResponder = clientResponder;
    }
}
