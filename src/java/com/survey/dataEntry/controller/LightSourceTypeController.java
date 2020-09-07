/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.LightSourceTypeModel;
import com.survey.tableClasses.LightSourceTypeBean;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
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
public class LightSourceTypeController extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is LIGHT SOURCE Controller....");
        ServletContext ctx = getServletContext();
        LightSourceTypeModel sourceTypeModel = new LightSourceTypeModel();
        sourceTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        sourceTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        sourceTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        sourceTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        sourceTypeModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getSourceType")) {
                    list = sourceTypeModel.getSourceType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                sourceTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchSourceType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=sourceTypeModel.showAllData(searchSourceType);
                jrxmlFilePath = ctx.getRealPath("/report/newLightSourceReport.jrxml");
                byte[] reportInbytes = sourceTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Delete")) {
            sourceTypeModel.deleteRecord(Integer.parseInt(request.getParameter("source_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int source_type_id;
            try {
                source_type_id = Integer.parseInt(request.getParameter("source_type_id"));
            } catch (Exception e) {
                source_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                source_type_id = 0;
            }
            LightSourceTypeBean sourceTypeBean = new LightSourceTypeBean();
            sourceTypeBean.setSource_type_id(source_type_id);
            sourceTypeBean.setSource_type(request.getParameter("source_type"));
            sourceTypeBean.setActive(request.getParameter("active"));
        //    sourceTypeBean.setCreated_by(request.getParameter("created_by"));
         //   wattageTypeBean.setCreated_date(request.getParameter("created_date"));
            sourceTypeBean.setRemark(request.getParameter("remark"));
            if (source_type_id == 0) {
                System.out.println("Inserting values by model......");
                sourceTypeModel.insertRecord(sourceTypeBean);
            } else {
                System.out.println("Update values by model........");
                sourceTypeModel.updateRecord(sourceTypeBean);
            }
        }
          // Start of Auto Completer code
        String searchSourceType = "";

        // End of Auto Completer code
        searchSourceType = request.getParameter("searchSourceType");
         try {
            if (searchSourceType == null) {
                searchSourceType = "";
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
         System.out.println("searching.......... " + searchSourceType);
         noOfRowsInTable = sourceTypeModel.getNoOfRows(searchSourceType);

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
            searchSourceType = "";
        }
       // Logic to show data in the table.
        List<LightSourceTypeBean> sourceTypeList = sourceTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchSourceType);
        lowerLimit = lowerLimit + sourceTypeList.size();
        noOfRowsTraversed = sourceTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("sourceTypeList", sourceTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + sourceTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchSourceType", searchSourceType);
        request.setAttribute("message", sourceTypeModel.getMessage());
        request.setAttribute("msgBgColor", sourceTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/lightSource_TypeView").forward(request, response);
     }
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }
}
