/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.TrafficTypeModel;
import com.survey.tableClasses.TrafficTypeBean;
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
 * @author JPSS
 */
public class TrafficTypeController extends HttpServlet {
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is traffic Controller....");
        ServletContext ctx = getServletContext();
        TrafficTypeModel trafficTypeModel = new TrafficTypeModel();
        trafficTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        trafficTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        trafficTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        trafficTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        trafficTypeModel.setConnection();
        String task = request.getParameter("task");
          try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTrafficType")) {
                    list = trafficTypeModel.getTrafficType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                trafficTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchTrafficType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=trafficTypeModel.showAllData(searchTrafficType);
                jrxmlFilePath = ctx.getRealPath("/report/newTrafficReport.jrxml");
                byte[] reportInbytes = trafficTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Delete")) {
            trafficTypeModel.deleteRecord(Integer.parseInt(request.getParameter("traffic_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int traffic_type_id;
            try {
                traffic_type_id = Integer.parseInt(request.getParameter("traffic_type_id"));
            } catch (Exception e) {
                traffic_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                traffic_type_id = 0;
            }
            TrafficTypeBean trafficTypeBean = new TrafficTypeBean();
            trafficTypeBean.setTraffic_type_id(traffic_type_id);
            trafficTypeBean.setTraffic_type(request.getParameter("traffic_type"));
        //    trafficTypeBean.setCreated_by(request.getParameter("created_by"));
         //   trafficTypeBean.setCreated_date(request.getParameter("created_date"));
            trafficTypeBean.setRemark(request.getParameter("remark"));
            if (traffic_type_id == 0) {
                System.out.println("Inserting values by model......");
                trafficTypeModel.insertRecord(trafficTypeBean);
            } else {
                System.out.println("Update values by model........");
                trafficTypeModel.updateRecord(trafficTypeBean);
            }
        }
         // Start of Auto Completer code
        String searchTrafficType = "";

        // End of Auto Completer code
        searchTrafficType = request.getParameter("searchTrafficType");
         try {
            if (searchTrafficType == null) {
                searchTrafficType = "";
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
         System.out.println("searching.......... " + searchTrafficType);
         noOfRowsInTable = trafficTypeModel.getNoOfRows(searchTrafficType);

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
        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchTrafficType = "";
        }
         // Logic to show data in the table.
        List<TrafficTypeBean> trafficTypeList = trafficTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchTrafficType);
        lowerLimit = lowerLimit + trafficTypeList.size();
        noOfRowsTraversed = trafficTypeList.size();

         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("trafficTypeList", trafficTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + trafficTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchTrafficType", searchTrafficType);
        request.setAttribute("message", trafficTypeModel.getMessage());
        request.setAttribute("msgBgColor", trafficTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/traffic_TypeView").forward(request, response);
 }
 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
