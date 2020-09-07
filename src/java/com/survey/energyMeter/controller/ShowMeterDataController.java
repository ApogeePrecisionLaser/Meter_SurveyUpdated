/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.controller;

import com.survey.energyMeter.model.ShowMeterDataModel;
import com.survey.energyMeter.tableClasses.ShowMeterDataBean;
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
 * @author Shobha
 */
public class ShowMeterDataController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        //PrintWriter out = response.getWriter();
        try {
            ServletContext ctx = getServletContext();
           ShowMeterDataModel showMeterDataModel = new ShowMeterDataModel();
           
        showMeterDataModel.setDriverClass(ctx.getInitParameter("driverClass"));
        showMeterDataModel.setConnectionString(ctx.getInitParameter("connectionString"));
        showMeterDataModel.setDb_username(ctx.getInitParameter("db_username"));
        showMeterDataModel.setDb_password(ctx.getInitParameter("db_password"));
        showMeterDataModel.setConnection();


        String task = request.getParameter("task");


        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getZoneType")) {
                   // list = zoneModel.getZoneType(q);
                }
                else if(JQstring.equals("getDivisionName")) {
                   // list = zoneModel.getDivisionType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
               // zoneModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }

        String allMeter_id=request.getParameter("meter_id");
       // String meter_id=request.getParameter("meter_id");
        System.out.println(allMeter_id+"-----");


//        if(task.equals("generateMapReport")){
//                String searchZoneType="";
//                List listAll = null;
//                String jrxmlFilePath;
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//               // listAll=showMeterDataModel.showAllData(searchZoneType);
//                jrxmlFilePath = ctx.getRealPath("/report/zone_m.jrxml");
//                byte[] reportInbytes = showMeterDataModel.generateMapReport(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                return;
//            }

//        if(task.equals("Save_in_tube_well_detail")){
//            try{
//                if(allMeter_id.length() > 0){
//                String meter_id[]=allMeter_id.split(",");
//                showMeterDataModel.insertRecord(meter_id);
//                }else{
//                    ///
//                }
//            }catch(Exception e){
//                System.out.println(e);
//            }
//
//        }
 if(task.equals("Save_in_switching_point_detail")){
            try{

               // String meter_id[]=allMeter_id.split(",");
                showMeterDataModel.insertRecordswitching_point_detail();

            }catch(Exception e){
                System.out.println(e);
            }

        }
        if(task.equals("Save_in_tube_well_detail")){
            try{

               // String meter_id[]=allMeter_id.split(",");
                showMeterDataModel.insertRecord1();

            }catch(Exception e){
                System.out.println(e);
            }

        }
        if(task.equals("insertintometers")){
            try{
                showMeterDataModel.insertInToMeters();

            }catch(Exception e){
                System.out.println(e);
            }

        }
if(task.equals("insertData")){
            try{
                showMeterDataModel.insertRecord2();

            }catch(Exception e){
                System.out.println(e);
            }

        }

//        if (task.equals("Delete")) {
//            //showMeterDataModel.deleteRecord(Integer.parseInt(request.getParameter("zone_id_m")));  // Pretty sure that organisation_type_id will be available.
//        } else if (task.equals("Save") || task.equals("Save AS New")) {
//            int zone_id_m;
//            try {
//                zone_id_m = Integer.parseInt(request.getParameter("zone_id_m"));
//            } catch (Exception e) {
//                zone_id_m = 0;
//            }
//            if (task.equals("Save AS New")) {
//                zone_id_m = 0;
//            }
//            ShowMeterDataBean showMeterDataBean = new ShowMeterDataBean();
//
//            if (zone_id_m == 0) {
//                System.out.println("Inserting values by model......");
//                showMeterDataModel.insertRecord(showMeterDataBean);
//            } else {
//                System.out.println("Update values by model........");
//               // showMeterDataModel.updateRecord(showMeterDataBean);
//            }
//        }

        String searchZoneType = "";

        // End of Auto Completer code
        searchZoneType = request.getParameter("searchZoneType");
         try {
            if (searchZoneType == null) {
                searchZoneType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
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

         System.out.println("searching.......... " + searchZoneType);
         noOfRowsInTable = showMeterDataModel.getNoOfRows();

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

         if (task.equals("Save_in_tube_well_detail") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchZoneType = "";
        }
           // Logic to show data in the table.
        List<ShowMeterDataBean> meterTypeList = showMeterDataModel.showData(lowerLimit, noOfRowsToDisplay);
        lowerLimit = lowerLimit + meterTypeList.size();
        noOfRowsTraversed = meterTypeList.size();
        
        
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        System.out.println("color is :" + showMeterDataModel.getMsgBgColor());
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("meterTypeList", meterTypeList);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchZoneType", searchZoneType);
        request.setAttribute("message", showMeterDataModel.getMessage());
        request.setAttribute("msgBgColor", showMeterDataModel.getMsgBgColor());

            request.getRequestDispatcher("/meterData").forward(request, response);
        } finally { 
           // out.close();
        }
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
