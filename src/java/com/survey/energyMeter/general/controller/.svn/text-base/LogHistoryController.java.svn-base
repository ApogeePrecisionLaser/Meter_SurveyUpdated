/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.general.controller;

import com.survey.energyMeter.general.model.LogHistoryModel;
import com.survey.energyMeter.general.tableClasses.History;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jpss
 */
public class LogHistoryController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is LogHistory Controller....");
        ServletContext ctx = getServletContext();
        LogHistoryModel logHistoryModel = new LogHistoryModel();
        logHistoryModel .setDriverClass(ctx.getInitParameter("driverClass"));
        logHistoryModel.setDb_username(ctx.getInitParameter("db_username"));
        logHistoryModel.setDb_password(ctx.getInitParameter("db_password"));
        logHistoryModel.setConnectionString(ctx.getInitParameter("connectionString"));
        logHistoryModel.setConnection();

        //Date now = new Date();
        //SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = "";

        String task = request.getParameter("task");
        String searchError_state= request.getParameter("searchError_state");
        String searchLoginDateFrom = request.getParameter("searchLoginDateFrom") == null  ? currentDate : request.getParameter("searchLoginDateFrom");
        String searchLoginDateTo = request.getParameter("searchLoginDateTo") == null ? currentDate : request.getParameter("searchLoginDateTo");
        String searchLogoutDateFrom = request.getParameter("searchLogoutDateFrom") == null ? currentDate : request.getParameter("searchLogoutDateFrom");
        String searchLogoutDateTo = request.getParameter("searchLogoutDateTo") == null ? currentDate : request.getParameter("searchLogoutDateTo");
        String login_time_from_hour = request.getParameter("login_time_from_hour") == null || request.getParameter("login_time_from_hour").equals("")? "00" : request.getParameter("login_time_from_hour");
        String login_time_from_min = request.getParameter("login_time_from_min") == null || request.getParameter("login_time_from_min").equals("")? "00" : request.getParameter("login_time_from_min");
        String login_time_from = request.getParameter("login_time_from") == null || request.getParameter("login_time_from").equals("")? "" : request.getParameter("login_time_from");
        String login_time_to_hour = request.getParameter("login_time_to_hour") == null || request.getParameter("login_time_to_hour").equals("")? "00" : request.getParameter("login_time_to_hour");
        String login_time_to_min = request.getParameter("login_time_to_min") == null || request.getParameter("login_time_to_min").equals("")? "00" : request.getParameter("login_time_to_min");
        String login_time_to = request.getParameter("login_time_to") == null? "" : request.getParameter("login_time_to");
        String searchLoginTimeFrom = "";
        String searchLoginTimeTo = "";
        String logout_time_from_hour = request.getParameter("logout_time_from_hour") == null || request.getParameter("logout_time_from_hour").equals("")? "00" : request.getParameter("logout_time_from_hour");
        String logout_time_from_min = request.getParameter("logout_time_from_min") == null || request.getParameter("logout_time_from_min").equals("")? "00" : request.getParameter("logout_time_from_min");
        String logout_time_from = request.getParameter("logout_time_from") == null || request.getParameter("logout_time_from").equals("")? "" : request.getParameter("logout_time_from");
        String logout_time_to_hour = request.getParameter("logout_time_to_hour") == null || request.getParameter("logout_time_to_hour").equals("")? "00" : request.getParameter("logout_time_to_hour");
        String logout_time_to_min = request.getParameter("logout_time_to_min") == null || request.getParameter("logout_time_to_min").equals("")? "00" : request.getParameter("logout_time_to_min");
        String logout_time_to = request.getParameter("logout_time_to") == null? "" : request.getParameter("logout_time_to");
        String searchLogoutTimeFrom = "";
        String searchLogoutTimeTo = "";

        //for login time
        if(!login_time_from_hour.equals("00") || !login_time_from_min.equals("00")){
            if(!login_time_from.isEmpty() && login_time_from.equals("PM")){
                String hourFrom = "";
                if(login_time_from_hour.equals("12")){
                    hourFrom = "00";
                }else hourFrom = login_time_from_hour;
                searchLoginTimeFrom = (12 + Integer.parseInt(hourFrom))+":"+login_time_from_min;
            }else{
                searchLoginTimeFrom = login_time_from_hour+":"+login_time_from_min;
            }
        }else searchLoginTimeFrom = "";

        if(!login_time_to_hour.equals("00") || !login_time_to_min.equals("00")){
            if(!login_time_to.isEmpty() && login_time_to.equals("PM")){
                String hourTo = "";
                if(login_time_from_hour.equals("12")){
                    hourTo = "00";
                }else hourTo = login_time_to_hour;
                searchLoginTimeTo = (12 + Integer.parseInt(hourTo))+":"+login_time_to_min;
            }else searchLoginTimeTo = login_time_to_hour+":"+login_time_to_min;
        }else searchLoginTimeTo = "";

        //for logout time
        if(!logout_time_from_hour.equals("00") || !logout_time_from_min.equals("00")){
            if(!logout_time_from.isEmpty() && logout_time_from.equals("PM")){
                String hourFrom = "";
                if(logout_time_from_hour.equals("12")){
                    hourFrom = "00";
                }else hourFrom = logout_time_from_hour;
                searchLogoutTimeFrom = (12 + Integer.parseInt(hourFrom))+":"+logout_time_from_min;
            }else{
                searchLogoutTimeFrom = logout_time_from_hour+":"+logout_time_from_min;
            }
        }else searchLogoutTimeFrom = "";

        if(!logout_time_to_hour.equals("00") || !logout_time_to_min.equals("00")){
            if(!logout_time_to.isEmpty() && logout_time_to.equals("PM")){
                String hourTo = "";
                if(logout_time_from_hour.equals("12")){
                    hourTo = "00";
                }else hourTo = logout_time_to_hour;
                searchLogoutTimeTo = (12 + Integer.parseInt(hourTo))+":"+logout_time_to_min;
            }else searchLogoutTimeTo = logout_time_to_hour+":"+logout_time_to_min;
        }else searchLogoutTimeTo = "";

        response.setContentType("text/html;charset=UTF-8");
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getSwitchingPointName")) {
                    //list = logHistoryModel.getSwitchingPointName(q);
                }else if (JQstring.equals("getHealthStatus")) {
                    //list = logHistoryModel.getHealthStatus(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                logHistoryModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --LogHistoryController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }

        try {
            if ( searchError_state == null) {
                searchError_state = "";
            }
        } catch (Exception e) {
        }

        /*if (task.equals("generateHSReport")) {
            String readingTimeFrom = "";
            String readingTimeTo = "";
            if(!reading_time_from_hour.equals("00") || !reading_time_from_min.equals("00")){
                readingTimeFrom = searchLoginTime+" "+time_from;
                readingTimeTo = reading_time_to_hour+":"+reading_time_to_min+" "+time_to;
            }
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/HealthStatusMapReport.jrxml");
                        list=logHistoryModel.showData(lowerLimit, noOfRowsToDisplay, searchLoginDateFrom, searchLogoutDateFrom, searchLoginTime, searchLogoutTime, searchError_state);
                        byte[] reportInbytes =logHistoryModel.generateRecordList(jrxmlFilePath, list, searchError_state, searchLoginDateFrom, searchLogoutDateFrom, readingTimeFrom, readingTimeTo);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        logHistoryModel.closeConnection();
                        return;
            }        */
        // Start of Search Table
        try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        if (task.equals("Show All Records")) {
             searchError_state = "";
             searchLoginDateFrom = currentDate;
             searchLoginDateTo = "";
             searchLogoutDateFrom = currentDate;
             searchLogoutDateTo = "";
             searchLoginTimeFrom = "";
             searchLoginTimeTo = "";
             searchLogoutTimeFrom = "";
             searchLogoutTimeTo = "";
        }
        System.out.println("searching.......... " +  searchError_state);
        noOfRowsInTable = logHistoryModel.getNoOfRows(searchLoginDateFrom, searchLoginDateTo, searchLogoutDateFrom, searchLogoutDateTo, searchLoginTimeFrom, searchLoginTimeTo, searchLogoutTimeFrom, searchLogoutTimeTo, searchError_state);                  // get the number of records (rows) in the table.

        if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0) {
                noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                lowerLimit = 0;
            } else {
                lowerLimit = temp;
            }
        } else if (buttonAction.equals("First")) {
            lowerLimit = 0;
        } else if (buttonAction.equals("Last")) {
            lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
            if (lowerLimit < 0) {
                lowerLimit = 0;
            }
        }

        List<History> historyList = logHistoryModel.showData(lowerLimit, noOfRowsToDisplay, searchLoginDateFrom, searchLoginDateTo, searchLogoutDateFrom, searchLogoutDateTo, searchLoginTimeFrom, searchLoginTimeTo, searchLogoutTimeFrom, searchLogoutTimeTo, searchError_state);
        lowerLimit = lowerLimit + historyList.size();
        noOfRowsTraversed = historyList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("historyList", historyList);
        request.setAttribute("searchError_state", searchError_state);
        request.setAttribute("searchLogoutDateFrom", searchLogoutDateFrom);
        request.setAttribute("searchLoginDateFrom", searchLoginDateFrom);
        request.setAttribute("searchLogoutDateTo", searchLogoutDateTo);
        request.setAttribute("searchLoginDateTo", searchLoginDateTo);
        request.setAttribute("login_time_from_hour", login_time_from_hour);
        request.setAttribute("login_time_from_min", login_time_from_min);
        request.setAttribute("login_time_from", login_time_from);
        request.setAttribute("login_time_to_hour", login_time_to_hour);
        request.setAttribute("login_time_to_min", login_time_to_min);
        request.setAttribute("login_time_to", login_time_to);
        request.setAttribute("logout_time_from_hour", logout_time_from_hour);
        request.setAttribute("logout_time_from_min", logout_time_from_min);
        request.setAttribute("logout_time_from", logout_time_from);
        request.setAttribute("logout_time_to_hour", logout_time_to_hour);
        request.setAttribute("logout_time_to_min", logout_time_to_min);
        request.setAttribute("logout_time_to", logout_time_to);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", logHistoryModel.getMessage());
        request.setAttribute("msgBgColor", logHistoryModel.getMsgBgColor());
        request.getRequestDispatcher("logHistoryView").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
