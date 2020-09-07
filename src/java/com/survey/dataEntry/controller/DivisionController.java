/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

/**
 *
 * @author Administrator
 */


import com.survey.dataEntry.model.DivisionModel;
import com.survey.tableClasses.DivisionTypeBean;
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
public class  DivisionController  extends HttpServlet{
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
     int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        DivisionModel divisionModel = new DivisionModel();
        divisionModel.setDriverClass(ctx.getInitParameter("driverClass"));
        divisionModel.setConnectionString(ctx.getInitParameter("connectionString"));
        divisionModel.setDb_username(ctx.getInitParameter("db_username"));
        divisionModel.setDb_password(ctx.getInitParameter("db_password"));
        divisionModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDivisionName")) {
                    list = divisionModel.getDivisionType(q);
                }
                else if(JQstring.equals("getCircleName")) {
                    list = divisionModel.getCircleType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                divisionModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchDivisionType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=divisionModel.showAllData(searchDivisionType);
                jrxmlFilePath = ctx.getRealPath("/report/division_m.jrxml");
                byte[] reportInbytes = divisionModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Cancel")) {
            divisionModel.deleteRecord(Integer.parseInt(request.getParameter("division_id_m")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int division_id_m;
            try {
                division_id_m = Integer.parseInt(request.getParameter("division_id_m"));
            } catch (Exception e) {
                division_id_m = 0;
            }
            if (task.equals("Save AS New")) {
                division_id_m = 0;
            }
            DivisionTypeBean divisionTypeBean = new DivisionTypeBean();
            divisionTypeBean.setDivision_id_m(division_id_m);
             divisionTypeBean.setCircle_id(divisionModel.getCircleId(request.getParameter("circle_name")));
            divisionTypeBean.setDivision_name_m(request.getParameter("division_name_m"));
            divisionTypeBean.setDescription(request.getParameter("description"));
            if (division_id_m == 0) {
                System.out.println("Inserting values by model......");
                divisionModel.insertRecord(divisionTypeBean);
            } else {
                System.out.println("Update values by model........");
                divisionModel.updateRecord(divisionTypeBean);
            }
        }
         // Start of Auto Completer code
        String searchDivisionType = "";

        // End of Auto Completer code
        searchDivisionType = request.getParameter("searchDivisionType");
         try {
            if (searchDivisionType == null) {
                searchDivisionType = "";
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
         System.out.println("searching.......... " + searchDivisionType);
         noOfRowsInTable = divisionModel.getNoOfRows(searchDivisionType);

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
            searchDivisionType = "";
        }
           // Logic to show data in the table.
        List<DivisionTypeBean> divisionTypeList = divisionModel.showData(lowerLimit, noOfRowsToDisplay, searchDivisionType);
        lowerLimit = lowerLimit + divisionTypeList.size();
        noOfRowsTraversed = divisionTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("divisionTypeList", divisionTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + divisionModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchDivisionType", searchDivisionType);
        request.setAttribute("message", divisionModel.getMessage());
        request.setAttribute("msgBgColor", divisionModel.getMsgBgColor());
        request.getRequestDispatcher("/divisionView").forward(request, response);



    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            doGet(request, response);
    }

}

