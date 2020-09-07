/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.WardTypeModel;
import com.survey.tableClasses.WardTypeBean;
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
public class WardTypeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
      int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is WARD TYPE Controller....");
        ServletContext ctx = getServletContext();
        WardTypeModel wardTypeModel = new WardTypeModel();
        wardTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        wardTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        wardTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        wardTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        wardTypeModel.setConnection();
        String task = request.getParameter("task");
        String wardType="";

         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getCityType")) {
                    list = wardTypeModel.getCityType(q);
                }
                else if (JQstring.equals("getWardType")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        wardType = request.getParameter("action2");
                    }
                    list = wardTypeModel.getWardType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                wardTypeModel.closeConnection();
                return;
            }
            } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
      if (task == null) {
            task = "";
        }
      
        if (task.equals("Cancel")) {
            // wardTypeBean wd=new WardTypeBean();
             int ward_id = Integer.parseInt(request.getParameter("ward_id"));
             int ward_rev_no = Integer.parseInt(request.getParameter("ward_rev_no"));
            wardTypeModel.cancelRecord(ward_id, ward_rev_no);  // Pretty sure that organisation_type_id will be available.
        }else if (task.equals("Save") || task.equals("Save AS New")||task.equals("Revise")) {
            int ward_id;
            int ward_rev_no;
            try {
                ward_id = Integer.parseInt(request.getParameter("ward_id"));
            } catch (Exception e) {
                ward_id = 0;
            }
              try {
                ward_rev_no = Integer.parseInt(request.getParameter("ward_rev_no"));
            } catch (Exception e) {
                ward_rev_no = 0;
            }
            if (task.equals("Save AS New")) {
                ward_id = 0;
                ward_rev_no = 0;
            }

            WardTypeBean wardTypeBean = new WardTypeBean();
            wardTypeBean.setWard_id(ward_id);
            wardTypeBean.setWard_rev_no(ward_rev_no);
            wardTypeBean.setWard_name(request.getParameter("ward_name"));
            wardTypeBean.setWard_no(request.getParameter("ward_no"));
            wardTypeBean.setCity_name(request.getParameter("city_name"));
          
            wardTypeBean.setCity_id(wardTypeModel.getCityId(request.getParameter("city_name"))); 
          //  poleTypeBean.setCreated_by(request.getParameter("created_by"));
         //   wattageTypeBean.setCreated_date(request.getParameter("created_date"));
            wardTypeBean.setRemark(request.getParameter("remark"));
       //     wardTypeBean.setActive(request.getParameter("active"));
            if (ward_id== 0) {
                System.out.println("Inserting values by model......");

                wardTypeModel.insertRecord(wardTypeBean);
            } else {
                System.out.println("Revising values by model........");
                wardTypeModel.reviseRecord(wardTypeBean);
            }
        }
          // Start of Auto Completer code
        String searchCityType = "";
        String searchWardType = "";
        // End of Auto Completer code
        searchCityType = request.getParameter("searchCityType");
        searchWardType = request.getParameter("searchWardType");
        try {
            if (searchCityType == null) {
                searchCityType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
         try {
            if (searchWardType == null) {
                searchWardType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }

          if(task.equals("wardViewReport")){
               // String wardType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=wardTypeModel.showAllData(searchCityType,searchWardType);
                jrxmlFilePath = ctx.getRealPath("/report/newWardTypeReport.jrxml");
                byte[] reportInbytes = wardTypeModel.generateMapReport(jrxmlFilePath,listAll);
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
         //System.out.println("searching.......... " + searchSourceType);
         noOfRowsInTable = wardTypeModel.getNoOfRows(searchCityType, searchWardType);

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
          if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")||task.equals("Revise")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchCityType = "";
            searchWardType = "";
        }
             // Logic to show data in the table.
        List<WardTypeBean> wardTypeList = wardTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchCityType, searchWardType);
        lowerLimit = lowerLimit + wardTypeList.size();
        noOfRowsTraversed = wardTypeList.size();
          request.setAttribute("searchCityType", searchCityType);
          request.setAttribute("searchWardType", searchWardType);
          // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("wardTypeList", wardTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + wardTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchPoleType", searchCityType);
        request.setAttribute("searchMountingType", searchWardType);
        request.setAttribute("message", wardTypeModel.getMessage());
        request.setAttribute("msgBgColor", wardTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/ward_TypeView").forward(request, response);






    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        doGet(request, response);
    }
}
