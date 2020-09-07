/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.ControlMechanismTypeModel;
import com.survey.tableClasses.ControlMechanismTypeBean;
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
public class ControlMechanismTypeController extends HttpServlet {
 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
          int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is WATTAGE Controller....");
        ServletContext ctx = getServletContext();
        ControlMechanismTypeModel mechanismTypeModel = new ControlMechanismTypeModel();
        mechanismTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        mechanismTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        mechanismTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        mechanismTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        mechanismTypeModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getMechanismType")) {
                    list = mechanismTypeModel.getMechanismType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                mechanismTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
        /*  if(task.equals("generateMapReport")){
                String searchWattageType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=mechanismTypeModel.showAllData(searchWattageType);
                jrxmlFilePath = ctx.getRealPath("/report/newWattageReport.jrxml");
                byte[] reportInbytes = mechanismTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }*/
         if (task.equals("Delete")) {
            mechanismTypeModel.deleteRecord(Integer.parseInt(request.getParameter("mechanism_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int mechanism_type_id;
            try {
                mechanism_type_id = Integer.parseInt(request.getParameter("mechanism_type_id"));
            } catch (Exception e) {
                mechanism_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                mechanism_type_id = 0;
            }
            ControlMechanismTypeBean mechanismTypeBean = new ControlMechanismTypeBean();
            mechanismTypeBean.setControl_mechanism_id(mechanism_type_id);
            mechanismTypeBean.setControl_mechanism_type(request.getParameter("mechanism_type"));
            mechanismTypeBean.setRemark(request.getParameter("remark"));
            if (mechanism_type_id == 0) {
                System.out.println("Inserting values by model......");
                mechanismTypeModel.insertRecord(mechanismTypeBean);
            } else {
                System.out.println("Update values by model........");
                mechanismTypeModel.updateRecord(mechanismTypeBean);
            }
        }
         // Start of Auto Completer code
        String searchMechanismType = "";

        // End of Auto Completer code
        searchMechanismType = request.getParameter("searchMechanismType");
         try {
            if (searchMechanismType == null) {
                searchMechanismType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
         if(task.equals("controlMechanismReport")){
               // String wardType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=mechanismTypeModel.showAllData(searchMechanismType);
                jrxmlFilePath = ctx.getRealPath("/report/newControlMechanismReport.jrxml");
                byte[] reportInbytes =mechanismTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
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
         System.out.println("searching.......... " + searchMechanismType);
         noOfRowsInTable = mechanismTypeModel.getNoOfRows(searchMechanismType);

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
            searchMechanismType = "";
        }
           // Logic to show data in the table.
        List<ControlMechanismTypeBean> mechanismTypeList = mechanismTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchMechanismType);
        lowerLimit = lowerLimit + mechanismTypeList.size();
        noOfRowsTraversed = mechanismTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("mechanismTypeList", mechanismTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + mechanismTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchMechanismType", searchMechanismType);
        request.setAttribute("message", mechanismTypeModel.getMessage());
        request.setAttribute("msgBgColor", mechanismTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/control_MechanismView").forward(request, response);
 }
 @Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            doGet(request, response);
    }

}
