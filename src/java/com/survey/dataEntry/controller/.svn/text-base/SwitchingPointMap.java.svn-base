/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.SwitchingPointMapModel;
import com.survey.tableClasses.SwitchingPointMapBean;
import com.survey.tableClasses.SwitchingPointSurveyBean;
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
 * @author Administrator
 */
public class SwitchingPointMap extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Switching Point Map Controller....");
        ServletContext ctx = getServletContext();
        SwitchingPointMapModel model = new SwitchingPointMapModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        model.setDb_username(ctx.getInitParameter("db_username"));
        model.setDb_password(ctx.getInitParameter("db_password"));
        model.setConnection();
        String task = request.getParameter("task");
        String poleNo="";

         try {         
            String JQstring = request.getParameter("action1");
            String action2 = request.getParameter("action2");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getsearchIVRSNo")) {
                    list = model.getsearchIVRSNo(q);
                }else if (JQstring.equals("getIVRSNo")) {
                    list = model.getIVRSNo(q);
                }
                else if (JQstring.equals("getsearchService_Conn_No")) {
                   list = model.getsearchService_Conn_No(q);
                } else if (JQstring.equals("getivrs_meter")) {
                   list = model.getivrs_meter(q);
                } else if (JQstring.equals("getsearchZone")) {
                   list = model.getsearchZone(q);
                } else if (JQstring.equals("getMeter_detail")) {
                    if(request.getParameter("action2") != null || !request.getParameter("action2").isEmpty()){
                    list = model.getMeter_detail(q, action2);
                    }
                }
                else if (JQstring.equals("getMeter_meter")){
                    if(request.getParameter("action2") != null || !request.getParameter("action2").isEmpty()){
                    list = model.getMeter_meter(q, action2);
                    }
                }else if (JQstring.equals("getSwitching_pt_detail")){
                    if(action2 != null || !action2.isEmpty()){
                    list = model.getSwitching_pt_detail(q, action2);
                    }
                }else if (JQstring.equals("getSwitching_pt_meter")){
                   if(action2 != null || !action2.isEmpty()){
                       list = model.getSwitching_pt_meter(q, action2);
                   }
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                model.closeConnection();
                return;
            }
            } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
        if (task.equals("generateMapReport")) {
            String searchIVRSNo = request.getParameter("searchIVRSNo");
            String searchZone = request.getParameter("searchZone");
            List<SwitchingPointMapBean> listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = model.showAllData(searchIVRSNo, searchZone);
            jrxmlFilePath = ctx.getRealPath("/report/newSurvey_MeterReport.jrxml");
            byte[] reportInbytes = model.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
//          if(task.equals("generateMapReport")){
//                String searchTrafficType="";
//                List listAll = null;
//                String jrxmlFilePath;
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                listAll=model.showAllData(searchTrafficType);
//                jrxmlFilePath = ctx.getRealPath("/report/newSwitchingPointDetailReport.jrxml");
//                byte[] reportInbytes = model.generateMapReport(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                return;
//            }
         if (task.equals("Delete")) {
             int id = Integer.parseInt(request.getParameter("switching_point_map_id"));
              model.deleteRecord(id);  // Pretty sure that organisation_type_id will be available.
        }else if (task.equals("Save") || task.equals("Update")) {
            int switching_point_map_id;
            String ivrs_no_survey =  request.getParameter("ivrs_no_survey");
            String ivrs_no_meter =  request.getParameter("ivrs_no_meter");
             
            try {
                switching_point_map_id = Integer.parseInt(request.getParameter("switching_point_map_id"));
            } catch (Exception e) {
                switching_point_map_id = 0;
            }
           
            SwitchingPointMapBean bean = new SwitchingPointMapBean();
          
           if (switching_point_map_id == 0) {
                System.out.println("Inserting values by model......");
                model.insertRecord(ivrs_no_survey, ivrs_no_meter);
            } else if(switching_point_map_id != 0) {
                model.updateRecord(switching_point_map_id, ivrs_no_survey, ivrs_no_meter);
                System.out.println("Update values by model........");
            
            } 
        }
          // Start of Auto Completer code
        String searchIVRSNo = "";
        String searchService_Conn_No = "";
        String searchZone = "";
       
        // End of Auto Completer code
        searchIVRSNo = request.getParameter("searchIVRSNo");
        searchService_Conn_No = request.getParameter("searchService_Conn_No");
        searchZone = request.getParameter("searchZone");
        
        try {
            if (searchIVRSNo == null) {
                searchIVRSNo = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchService_Conn_No == null) {
                searchService_Conn_No = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
         try {
            if (searchZone == null) {
                searchZone = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
          if (task.equals("Show All Records")) {
            searchZone = "";
            searchIVRSNo = "";
            searchService_Conn_No = "";
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
         //System.out.println("searching.......... " + searchSourceType);
         noOfRowsInTable = model.getNoOfRows(searchIVRSNo,searchService_Conn_No,searchZone);

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
          if (task.equals("Save") || task.equals("Delete") || task.equals("Update")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {

            searchZone = "";
            searchIVRSNo = "";
            searchService_Conn_No = "";
        }
             // Logic to show data in the table.
        List<SwitchingPointMapBean> sw_pointList = model.showData(lowerLimit,noOfRowsToDisplay, searchIVRSNo,searchService_Conn_No,searchZone);
        lowerLimit = lowerLimit + sw_pointList.size();
        noOfRowsTraversed = sw_pointList.size();
          // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("sw_pointList", sw_pointList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + model.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchIVRSNo", searchIVRSNo);
        request.setAttribute("searchService_Conn_No", searchService_Conn_No);
        request.setAttribute("searchZone", searchZone);
       
        request.setAttribute("message", model.getMessage());
        request.setAttribute("msgBgColor", model.getMsgBgColor());
        request.getRequestDispatcher("/switchingMapView").forward(request, response);
  }
  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException
    {
       doGet(request, response);
    }

}
