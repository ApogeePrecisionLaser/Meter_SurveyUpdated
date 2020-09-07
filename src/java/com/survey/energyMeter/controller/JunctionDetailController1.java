/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.controller;

import com.survey.util.UniqueIDGenerator;
import com.survey.energyMeter.model.HealthStatusMapModel;
import com.survey.energyMeter.model.JunctionDetailModel;
import com.survey.energyMeter.model.JunctionDetailModel1;
import com.survey.energyMeter.model.MeterReadingModel;
import com.survey.energyMeter.tableClasses.HealthStatusMapBean;
import com.survey.energyMeter.tableClasses.PhaseBean;
import com.survey.energyMeter.tableClasses.Readings;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.simple.JSONArray;


/**
 *
 * @author jpss
 */
public class JunctionDetailController1 extends HttpServlet {

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
        System.out.println("this is JunctionDetail Controller....");
        ServletContext ctx = getServletContext();
        HttpSession session = request.getSession();
        HealthStatusMapModel healthStatusMapModel = new HealthStatusMapModel();
        healthStatusMapModel .setDriverClass(ctx.getInitParameter("driverClass"));
        healthStatusMapModel.setDb_username(ctx.getInitParameter("db_username"));
        healthStatusMapModel.setDb_password(ctx.getInitParameter("db_password"));
        healthStatusMapModel.setConnectionString(ctx.getInitParameter("connectionString"));
        healthStatusMapModel.setConnection();
        Connection con = healthStatusMapModel.getConnection();
        MeterReadingModel readingModel = new MeterReadingModel();
        readingModel.setConnection(con);
        JunctionDetailModel1 junctionDetailModel = new JunctionDetailModel1();
        junctionDetailModel.setConnection(con);

        Date now = new Date();
        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
        String currentDate = df1.format(now);

        String task = request.getParameter("task");
        String searchSwitchingPointName= request.getParameter("searchJunction") == null ? "" : request.getParameter("searchJunction");

        String searchIvrs_no= request.getParameter("searchJunction") == null ? "" : request.getParameter("searchJunction");

        String searchHealthStatus= request.getParameter("searchHealthStatus")== null ? "" : request.getParameter("searchHealthStatus");
        String searchP1HealthStatus= request.getParameter("searchP1HealthStatus")== null ? "" : request.getParameter("searchP1HealthStatus");
        String searchP2HealthStatus= request.getParameter("searchP2HealthStatus")== null ? "" : request.getParameter("searchP2HealthStatus");
        String searchP3HealthStatus= request.getParameter("searchP3HealthStatus")== null ? "" : request.getParameter("searchP3HealthStatus");
        String searchP1vcHealthStatus= request.getParameter("searchP1vcHealthStatus")== null ? "" : request.getParameter("searchP1vcHealthStatus");
        String searchP2vcHealthStatus= request.getParameter("searchP2vcHealthStatus")== null ? "" : request.getParameter("searchP2vcHealthStatus");
        String searchP3vcHealthStatus= request.getParameter("searchP3vcHealthStatus")== null ? "" : request.getParameter("searchP3vcHealthStatus");
        String searchReadingDateFrom = request.getParameter("searchReadingDateFrom") == null ? "" : request.getParameter("searchReadingDateFrom");
        String searchReadingDateTo = request.getParameter("searchReadingDateTo") == null ? currentDate : request.getParameter("searchReadingDateTo");
        String reading_time_from_hour = request.getParameter("reading_time_from_hour") == null? "00" : request.getParameter("reading_time_from_hour");
        String reading_time_from_min = request.getParameter("reading_time_from_min") == null? "00" : request.getParameter("reading_time_from_min");
        String time_from = request.getParameter("time_from") == null? "" : request.getParameter("time_from");
        String reading_time_to_hour = request.getParameter("reading_time_to_hour") == null? "00" : request.getParameter("reading_time_to_hour");
        String reading_time_to_min = request.getParameter("reading_time_to_min") == null? "00" : request.getParameter("reading_time_to_min");
        String time_to = request.getParameter("time_to") == null? "" : request.getParameter("time_to");
        String searchReadingTimeFrom = "";
        String searchReadingTimeTo = "";
        if(!reading_time_from_hour.equals("00") || !reading_time_from_min.equals("00")){
            if(!time_from.isEmpty() && time_from.equals("PM")){
                String hourFrom = "";
                if(reading_time_from_hour.equals("12")){
                    hourFrom = "00";
                }else hourFrom = reading_time_from_hour;
                searchReadingTimeFrom = (12 + Integer.parseInt(hourFrom))+":"+reading_time_from_min;
            }else{
                searchReadingTimeFrom = reading_time_from_hour+":"+reading_time_from_min;
            }
        }else searchReadingTimeFrom = "";

        if(!reading_time_to_hour.equals("00") || !reading_time_to_min.equals("00")){
            if(!time_to.isEmpty() && time_to.equals("PM")){
                String hourTo = "";
                if(reading_time_from_hour.equals("12")){
                    hourTo = "00";
                }else hourTo = reading_time_to_hour;
                searchReadingTimeTo = (12 + Integer.parseInt(hourTo))+":"+reading_time_to_min;
            }else searchReadingTimeTo = reading_time_to_hour+":"+reading_time_to_min;
        }else searchReadingTimeTo = "";

        response.setContentType("text/html;charset=UTF-8");
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("setCommand")){
                    String junction_id = request.getParameter("junction_id");
                    String change_command = request.getParameter("change_command");
                    String change_command_value = request.getParameter("change_command_value");
                    //session.setAttribute(junction_id, change_command + " " + change_command_value);
                    ctx.setAttribute(junction_id, change_command + " " + change_command_value);
                    out.print("1");
                    return;
                }
                if (JQstring.equals("getJunctionData")) {
                    List<Readings> reading = readingModel.showData(searchSwitchingPointName,searchIvrs_no, "", "", 0, 1, "", "", "", "", "", "", "", "", "");
                    Iterator itr = reading.iterator();
                    Readings readBean = new Readings();
                    int junction_id = 0;
                    if(itr.hasNext()){
                        readBean = (Readings) itr.next();
                        junction_id = readBean.getJunction_id();
                        if(searchSwitchingPointName.isEmpty())
                            searchSwitchingPointName = readBean.getJunction_name();
                    }
                    List<HealthStatusMapBean> healthStatusList = healthStatusMapModel.showData(0, 1, "", "", "", "", searchSwitchingPointName, "", "", "", "", "", "", "");
                    itr = healthStatusList.iterator();
                    HealthStatusMapBean healthStatusBean = new HealthStatusMapBean();
                    if(itr.hasNext()){
                        healthStatusBean = (HealthStatusMapBean) itr.next();
                    }
                    junctionDetailModel.getJunctionHealthBitData(healthStatusBean, junction_id);
                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put("junction_id", readBean.getJunction_id());
                    jsonObj.put("switching_point_name", readBean.getJunction_name());
                    jsonObj.put("voltage1", readBean.getVoltage1());
                    jsonObj.put("voltage2", readBean.getVoltage2());
                    jsonObj.put("voltage3", readBean.getVoltage3());
                    jsonObj.put("current1", readBean.getCurrent1());
                    jsonObj.put("current2", readBean.getCurrent2());
                    jsonObj.put("current3", readBean.getCurrent3());
                    jsonObj.put("connected_load", readBean.getConnected_load());
                    jsonObj.put("power_factor", readBean.getPower_factor());
                    jsonObj.put("consumed_unit", readBean.getConsumed_unit());
                    jsonObj.put("reading_date", readBean.getReading_date());
                    jsonObj.put("reading_time", readBean.getReading_time());
                    jsonObj.put("on_time_hr", readBean.getOn_time_hr());
                    jsonObj.put("on_time_min", readBean.getOn_time_min());
                    jsonObj.put("off_time_hr", readBean.getOff_time_hr());
                    jsonObj.put("off_time_min", readBean.getOff_time_min());

                    jsonObj.put("phase1_status", healthStatusBean.getPhase1_status());
                    jsonObj.put("phase2_status", healthStatusBean.getPhase2_status());
                    jsonObj.put("phase3_status", healthStatusBean.getPhase3_status());

                    if(healthStatusBean.getPhase1Bean() == null)
                        healthStatusBean.setPhase1Bean(new PhaseBean());
                    if(healthStatusBean.getPhase2Bean() == null)
                        healthStatusBean.setPhase2Bean(new PhaseBean());
                    if(healthStatusBean.getPhase3Bean() == null)
                        healthStatusBean.setPhase3Bean(new PhaseBean());
                    jsonObj.put("fuse1_in", healthStatusBean.getPhase1Bean().getFuse_in());
                    jsonObj.put("fuse1_out", healthStatusBean.getPhase1Bean().getFuse_out());
                    jsonObj.put("contactor1_status", healthStatusBean.getPhase1Bean().getContactor_status());
                    jsonObj.put("contactor1_command", healthStatusBean.getPhase1Bean().getContactor_command());
                    jsonObj.put("mccb1_in", healthStatusBean.getPhase1Bean().getMccb_in());
                    jsonObj.put("mccb1_out", healthStatusBean.getPhase1Bean().getMccb_out());

                    jsonObj.put("fuse2_in", healthStatusBean.getPhase2Bean().getFuse_in());
                    jsonObj.put("fuse2_out", healthStatusBean.getPhase2Bean().getFuse_out());
                    jsonObj.put("contactor2_status", healthStatusBean.getPhase2Bean().getContactor_status());
                    jsonObj.put("contactor2_command", healthStatusBean.getPhase2Bean().getContactor_command());
                    jsonObj.put("mccb2_in", healthStatusBean.getPhase2Bean().getMccb_in());
                    jsonObj.put("mccb2_out", healthStatusBean.getPhase2Bean().getMccb_out());

                    jsonObj.put("fuse3_in", healthStatusBean.getPhase3Bean().getFuse_in());
                    jsonObj.put("fuse3_out", healthStatusBean.getPhase3Bean().getFuse_out());
                    jsonObj.put("contactor3_status", healthStatusBean.getPhase3Bean().getContactor_status());
                    jsonObj.put("contactor3_command", healthStatusBean.getPhase3Bean().getContactor_command());
                    jsonObj.put("mccb3_in", healthStatusBean.getPhase3Bean().getMccb_in());
                    jsonObj.put("mccb3_out", healthStatusBean.getPhase3Bean().getMccb_out());

                    jsonObj.put("contactor_status", healthStatusBean.getContactor_status());
                    jsonObj.put("contactor_command", healthStatusBean.getContactor_command());

                    JSONArray arrayObj = new JSONArray();
                    arrayObj= junctionDetailModel.getAllRecords();
                    jsonObj.put("arrayObj", arrayObj);
                    out.print(jsonObj);
                    readingModel.closeConnection();
                    healthStatusMapModel.closeConnection();
                    junctionDetailModel.closeConnection();
                    return;
                }
                if (JQstring.equals("getSwitchingPointName")) {
                    list = healthStatusMapModel.getSwitchingPointName(q);
                }else if (JQstring.equals("getHealthStatus")) {
                    list = healthStatusMapModel.getHealthStatus(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                healthStatusMapModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --HealthStatusMapController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }
        if (task.equals("generateHSReport")) {
            String readingTimeFrom = "";
            String readingTimeTo = "";
            if(!reading_time_from_hour.equals("00") || !reading_time_from_min.equals("00")){
                readingTimeFrom = searchReadingTimeFrom+" "+time_from;
                readingTimeTo = reading_time_to_hour+":"+reading_time_to_min+" "+time_to;
            }
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/HealthStatusMapReport.jrxml");
                        list=healthStatusMapModel.showData(lowerLimit, noOfRowsToDisplay, searchReadingDateFrom, searchReadingDateTo, searchReadingTimeFrom, searchReadingTimeTo, searchSwitchingPointName,
                                searchHealthStatus, searchP1HealthStatus, searchP2HealthStatus, searchP3HealthStatus, searchP1vcHealthStatus, searchP2vcHealthStatus, searchP3vcHealthStatus);
                        byte[] reportInbytes =healthStatusMapModel.generateRecordList(jrxmlFilePath, list, searchSwitchingPointName, searchHealthStatus, searchReadingDateFrom, searchReadingDateTo, readingTimeFrom
                                , readingTimeTo, searchP1HealthStatus, searchP2HealthStatus, searchP3HealthStatus, searchP1vcHealthStatus, searchP2vcHealthStatus, searchP3vcHealthStatus);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        healthStatusMapModel.closeConnection();
                        return;
            }

        try {
            if ( searchHealthStatus == null || searchSwitchingPointName == null) {
                searchHealthStatus = "";
                searchSwitchingPointName = "";
            }
        } catch (Exception e) {
        }
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
             searchHealthStatus = "";
             searchP1HealthStatus = "";
             searchP2HealthStatus = "";
             searchP3HealthStatus = "";
             searchP1vcHealthStatus = "";
             searchP2vcHealthStatus = "";
             searchP3vcHealthStatus = "";
             searchSwitchingPointName = "";
             searchReadingDateFrom = "";
             searchReadingDateTo = currentDate;

             searchIvrs_no="";

        }
        System.out.println("searching.......... " +  searchHealthStatus);
        noOfRowsInTable = healthStatusMapModel.getNoOfRows(searchReadingDateFrom, searchReadingDateTo, searchReadingTimeFrom, searchReadingTimeTo, searchSwitchingPointName,
                searchHealthStatus, searchP1HealthStatus, searchP2HealthStatus, searchP3HealthStatus, searchP1vcHealthStatus, searchP2vcHealthStatus, searchP3vcHealthStatus);                  // get the number of records (rows) in the table.

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

        List<HealthStatusMapBean> healthStatusMapList = healthStatusMapModel.showData(lowerLimit, noOfRowsToDisplay, searchReadingDateFrom, searchReadingDateTo, searchReadingTimeFrom, searchReadingTimeTo, searchSwitchingPointName,
                searchHealthStatus, searchP1HealthStatus, searchP2HealthStatus, searchP3HealthStatus, searchP1vcHealthStatus, searchP2vcHealthStatus, searchP3vcHealthStatus);
        lowerLimit = lowerLimit + healthStatusMapList.size();
        noOfRowsTraversed = healthStatusMapList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
        List<Readings> reading = readingModel.showData(searchSwitchingPointName,searchIvrs_no, "", "", 0, 1, "", "", "", "", "", "", "", "", "");
        Iterator itr = reading.iterator();
        Readings readBean = new Readings();
        int junction_id = 0;
        if(itr.hasNext()){
            readBean = (Readings) itr.next();
            junction_id = readBean.getJunction_id();
            if(searchSwitchingPointName.isEmpty())
                searchSwitchingPointName = readBean.getJunction_name();
        }
        List<HealthStatusMapBean> healthStatusList = healthStatusMapModel.showData(0, 1, "", "", "", "", searchSwitchingPointName, "", "", "", "", "", "", "");
        itr = healthStatusList.iterator();
        HealthStatusMapBean healthStatusBean = new HealthStatusMapBean();
        if(itr.hasNext()){
            healthStatusBean = (HealthStatusMapBean) itr.next();
        }
        junctionDetailModel.getJunctionHealthBitData(healthStatusBean, junction_id);

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("readingData", readBean);
        request.setAttribute("healthStatusBean", healthStatusBean);
        //request.setAttribute("healthStatusMapBean", healthStatusMapBean);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("healthStatusMapList", healthStatusMapList);
        request.setAttribute("searchHealthStatus", searchHealthStatus);
        request.setAttribute("searchP1HealthStatus", searchP1HealthStatus);
        request.setAttribute("searchP2HealthStatus", searchP2HealthStatus);
        request.setAttribute("searchP3HealthStatus", searchP3HealthStatus);
        request.setAttribute("searchP3vcHealthStatus", searchP3vcHealthStatus);
        request.setAttribute("searchP2vcHealthStatus", searchP2vcHealthStatus);
        request.setAttribute("searchP1vcHealthStatus", searchP1vcHealthStatus);
        request.setAttribute("searchJunction", searchSwitchingPointName);
        request.setAttribute("searchReadingDateTo", searchReadingDateTo);
        request.setAttribute("searchReadingDateFrom", searchReadingDateFrom);
        request.setAttribute("reading_time_from_hour", reading_time_from_hour);
        request.setAttribute("reading_time_from_min", reading_time_from_min);
        request.setAttribute("time_from", time_from);
        request.setAttribute("reading_time_to_hour", reading_time_to_hour);
        request.setAttribute("reading_time_to_min", reading_time_to_min);
        request.setAttribute("time_to", time_to);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", healthStatusMapModel.getMessage());
        request.setAttribute("msgBgColor", healthStatusMapModel.getMsgBgColor());
        readingModel.closeConnection();
        healthStatusMapModel.closeConnection();
        junctionDetailModel.closeConnection();
        request.getRequestDispatcher("junctionFullInfoView1").forward(request, response);
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
