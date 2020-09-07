/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.PoleTypeModel;
import com.survey.tableClasses.PoleTypeBean;
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
public class PoleTypeController extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Pole Controller....");
        ServletContext ctx = getServletContext();
        PoleTypeModel poleTypeModel = new PoleTypeModel();
        poleTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        poleTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        poleTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        poleTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        poleTypeModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getPoleType")) {
                    list = poleTypeModel.getPoleType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                poleTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchPoleType="";
                searchPoleType=request.getParameter("searchPoleType");
                if(searchPoleType==null)searchPoleType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=poleTypeModel.showAllData(searchPoleType);
                jrxmlFilePath = ctx.getRealPath("/report/newPoleTypeReport.jrxml");
                byte[] reportInbytes = poleTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Delete")) {
            poleTypeModel.deleteRecord(Integer.parseInt(request.getParameter("pole_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int pole_type_id;
            try {
                pole_type_id = Integer.parseInt(request.getParameter("pole_type_id"));
            } catch (Exception e) {
                pole_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                pole_type_id = 0;
            }
            PoleTypeBean poleTypeBean = new PoleTypeBean();
            poleTypeBean.setPole_type_id(pole_type_id);
            poleTypeBean.setPole_type(request.getParameter("pole_type"));
            poleTypeBean.setActive(request.getParameter("active"));
          //  poleTypeBean.setCreated_by(request.getParameter("created_by"));
         //   wattageTypeBean.setCreated_date(request.getParameter("created_date"));
            poleTypeBean.setRemark(request.getParameter("remark"));
            if (pole_type_id == 0) {
                System.out.println("Inserting values by model......");
                poleTypeModel.insertRecord(poleTypeBean);
            } else {
                System.out.println("Update values by model........");
                poleTypeModel.updateRecord(poleTypeBean);
            }
        }
         String searchPoleType = "";

        // End of Auto Completer code
        searchPoleType = request.getParameter("searchPoleType");
         try {
            if (searchPoleType == null) {
                searchPoleType = "";
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
          if (task.equals("Show All Records")) {
            searchPoleType = "";
        }
         System.out.println("searching.......... " + searchPoleType);
         noOfRowsInTable = poleTypeModel.getNoOfRows(searchPoleType);

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
        }
       // Logic to show data in the table.
        List<PoleTypeBean> poleTypeList = poleTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleType);
        lowerLimit = lowerLimit + poleTypeList.size();
        noOfRowsTraversed = poleTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("poleTypeList", poleTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + poleTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchPoleType", searchPoleType);
        request.setAttribute("message", poleTypeModel.getMessage());
        request.setAttribute("msgBgColor", poleTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/pole_TypeView").forward(request, response);

    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request,response);
    }
}
