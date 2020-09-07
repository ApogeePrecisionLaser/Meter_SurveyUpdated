/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.RoadTypeModel;
import com.survey.tableClasses.RoadTypeBean;
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
public class RoadTypeController extends HttpServlet{
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
  int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is ROAD TYPE Controller....");
        ServletContext ctx = getServletContext();
        RoadTypeModel roadTypeModel = new RoadTypeModel();
        roadTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        roadTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        roadTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        roadTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        roadTypeModel.setConnection();
        String task = request.getParameter("task");
        String roadUseType = "";
        String roadCatType = "";
          try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getRoadType")) {
                    list = roadTypeModel.getRoadNameType(q);
                }
                else if (JQstring.equals("getRoadUseType")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        roadUseType = request.getParameter("action2");
                    }
                    list = roadTypeModel.getRoadUseType(q);
                }  else if (JQstring.equals("getRoadCatType")) {
                    if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty()) {
                        roadCatType = request.getParameter("action2");
                    }
                    list = roadTypeModel.getRoadCategoryType(q);
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
                String searchRoadNameType="";
                String searchRoadUseType="";
                String searchRoadCatType="";
          
        // End of Auto Completer code
            
            searchRoadNameType = request.getParameter("searchRoadNameType");
            searchRoadUseType = request.getParameter("searchRoadUseType");
            searchRoadCatType = request.getParameter("searchRoadCatType");
                 if (searchRoadNameType == null) {
                    searchRoadNameType = "";
                }
                if (searchRoadUseType == null) {
                    searchRoadUseType = "";
                }
                if (searchRoadCatType == null) {
                    searchRoadCatType = "";
                }


            List listAll = null;

                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=roadTypeModel.showAllData(searchRoadNameType, searchRoadUseType, searchRoadCatType);
                jrxmlFilePath = ctx.getRealPath("/report/newRoadTypeReport.jrxml");
                byte[] reportInbytes = roadTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Cancel")) {
             int road_id = Integer.parseInt(request.getParameter("road_id"));
             int road_rev_no = Integer.parseInt(request.getParameter("road_rev_no"));
            roadTypeModel.cancelRecord(road_id, road_rev_no);  // Pretty sure that organisation_type_id will be available.
        }else if (task.equals("Save") || task.equals("Save AS New")|| task.equals("Revise")||task.equals("Update")) {
            int road_id;
            int road_rev_no;
            try {
                road_id = Integer.parseInt(request.getParameter("road_id"));
            } catch (Exception e) {
                road_id = 0;
            }
            try {
                road_rev_no = Integer.parseInt(request.getParameter("road_rev_no"));
            } catch (Exception e) {
                road_rev_no = 0;
            }
            if (task.equals("Save AS New")) {
                road_id = 0;
                road_rev_no = 0;
            }
            RoadTypeBean roadTypeBean = new RoadTypeBean();
            roadTypeBean.setRoad_id(road_id);
            roadTypeBean.setRoad_rev_no(road_rev_no);
            roadTypeBean.setRoad_name(request.getParameter("road_name"));
            roadTypeBean.setStart_landmark(request.getParameter("start_landmark"));
            roadTypeBean.setEnd_landmark(request.getParameter("end_landmark"));
            roadTypeBean.setRoad_use_id(roadTypeModel.getRoadUseId(request.getParameter("road_use")));
            roadTypeBean.setCategory_id(roadTypeModel.getRoadCategoryId(request.getParameter("category_name")));
          //  roadTypeBean.setCreated_by(request.getParameter("created_by"));
            roadTypeBean.setCentral_light(request.getParameter("central_light"));
         //   wattageTypeBean.setCreated_date(request.getParameter("created_date"));
            roadTypeBean.setRemark(request.getParameter("remark"));
            roadTypeBean.setApprox_length(Double.parseDouble(request.getParameter("approx_length")));
           // roadTypeBean.setActive(request.getParameter("active"));
            if (road_id == 0) {
                System.out.println("Inserting values by model......");
                roadTypeModel.insertRecord(roadTypeBean);
            } else if (road_id != 0 && task.equals("Update")) {
                System.out.println("Update values by model........");
                roadTypeModel.updateRecord(roadTypeBean);
            }
             else{
                System.out.println("Revise values by model........");
                roadTypeModel.reviseRecord(roadTypeBean);
             }
        }
           // Start of Auto Completer code
        String searchRoadNameType = "";
        String searchRoadUseType = "";
        String searchRoadCatType = "";
        // End of Auto Completer code
        searchRoadNameType = request.getParameter("searchRoadNameType");
        searchRoadUseType = request.getParameter("searchRoadUseType");
        searchRoadCatType = request.getParameter("searchRoadCatType");
        try {
            if (searchRoadNameType == null) {
                searchRoadNameType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
         try {
            if (searchRoadUseType == null) {
                searchRoadUseType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchRoadCatType == null) {
                searchRoadCatType = "";
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
         //System.out.println("searching.......... " + searchSourceType);
         noOfRowsInTable = roadTypeModel.getNoOfRows(searchRoadNameType, searchRoadUseType, searchRoadCatType);

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
          if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Revise") ||task.equals("Update")|| task.equals("Cancel")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchRoadNameType = "";
            searchRoadUseType = "";
            searchRoadCatType = "";
        }
             // Logic to show data in the table.
        List<RoadTypeBean> roadTypeList = roadTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchRoadNameType, searchRoadUseType, searchRoadCatType);
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
        request.setAttribute("searchRoadNameType", searchRoadNameType);
        request.setAttribute("searchRoadUseType", searchRoadUseType);
        request.setAttribute("searchRoadCatType", searchRoadCatType);
        request.setAttribute("message", roadTypeModel.getMessage());
        request.setAttribute("msgBgColor", roadTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/road_TypeView").forward(request, response);

}
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
                doGet(request, response);
    }
}
