/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.RoadUseTypeModel;
import com.survey.tableClasses.RoadUseTypeBean;
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
public class RoadUseTypeController extends HttpServlet {
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Road Use Controller....");
        ServletContext ctx = getServletContext();
        RoadUseTypeModel roadTypeModel = new RoadUseTypeModel();
        roadTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        roadTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        roadTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        roadTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        roadTypeModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getRoadType")) {
                    list = roadTypeModel.getRoadType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                roadTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchRoadType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=roadTypeModel.showAllData(searchRoadType);
                jrxmlFilePath = ctx.getRealPath("/report/newRoadUseReport.jrxml");
                byte[] reportInbytes = roadTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Delete")) {
            roadTypeModel.deleteRecord(Integer.parseInt(request.getParameter("road_use_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int road_use_id;
            try {
                road_use_id = Integer.parseInt(request.getParameter("road_use_id"));
            } catch (Exception e) {
                road_use_id = 0;
            }
            if (task.equals("Save AS New")) {
                road_use_id = 0;
            }
            RoadUseTypeBean roadTypeBean = new RoadUseTypeBean();
            roadTypeBean.setRoad_use_id(road_use_id);
            roadTypeBean.setRoad_use(request.getParameter("road_use"));
            roadTypeBean.setActive(request.getParameter("active"));
        //    roadTypeBean.setCreated_by(request.getParameter("created_by"));
         //   roadTypeBean.setCreated_date(request.getParameter("created_date"));
            roadTypeBean.setRemark(request.getParameter("remark"));
            if (road_use_id == 0) {
                System.out.println("Inserting values by model......");
                roadTypeModel.insertRecord(roadTypeBean);
            } else {
                System.out.println("Update values by model........");
                roadTypeModel.updateRecord(roadTypeBean);
            }
        }
         // Start of Auto Completer code
        String searchRoadType = "";

        // End of Auto Completer code
        searchRoadType = request.getParameter("searchRoadType");
         try {
            if (searchRoadType == null) {
                searchRoadType = "";
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
         System.out.println("searching.......... " + searchRoadType);
         noOfRowsInTable = roadTypeModel.getNoOfRows(searchRoadType);

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
            searchRoadType = "";
        }
           // Logic to show data in the table.
        List<RoadUseTypeBean> roadTypeList = roadTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchRoadType);
        lowerLimit = lowerLimit + roadTypeList.size();
        noOfRowsTraversed = roadTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("roadTypeList", roadTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + roadTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchRoadType", searchRoadType);
        request.setAttribute("message", roadTypeModel.getMessage());
        request.setAttribute("msgBgColor", roadTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/roadUse_TypeView").forward(request, response);
}
   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            doGet(request,response);
    }
}
