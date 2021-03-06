/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.SurveyCordinatesModel;
import com.survey.tableClasses.SurveyCoordinatesBean;
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
public class SurveyCordinatesController  extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is SurveyCordinatesController....");
        ServletContext ctx = getServletContext();
        SurveyCordinatesModel surveyCordinatesModel = new SurveyCordinatesModel();
        surveyCordinatesModel.setDriverClass(ctx.getInitParameter("driverClass"));
        surveyCordinatesModel.setConnectionString(ctx.getInitParameter("connectionString"));
        surveyCordinatesModel.setDb_username(ctx.getInitParameter("db_username"));
        surveyCordinatesModel.setDb_password(ctx.getInitParameter("db_password"));
        surveyCordinatesModel.setConnection();
        String task = request.getParameter("task");
        String searchArea = "", searchCity = "", searchWard = "";
        try {

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("searchCity")) {
                    list = surveyCordinatesModel.getCityType(q);
                }else if (JQstring.equals("searchWard")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        searchCity = request.getParameter("action2");
                    }
                    list = surveyCordinatesModel.getWardNo(q, searchCity);
                } else if (JQstring.equals("searchArea")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        searchCity = request.getParameter("action2");
                    }
                    if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty()) {
                        searchWard = request.getParameter("action3");
                    }

                    list = surveyCordinatesModel.getAreaName(q, searchWard, searchCity );
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                surveyCordinatesModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("Error in Jquery function()::" + e);
        }
   searchArea = request.getParameter("searchArea");
        if (searchArea == null) {
            searchArea = "";
        }
        searchWard = request.getParameter("searchWard");
        if (searchWard == null) {
            searchWard = "";
        }
        searchCity = request.getParameter("searchCity");
        if (searchCity == null) {
            searchCity = "";
        }
        if (task == null) {
            task = "";
        }
        if (task.equals("showMapWindow")) {
            String longi = request.getParameter("logitude");
            String latti = request.getParameter("lattitude");
            List<SurveyCoordinatesBean> areaTypeList = surveyCordinatesModel.showDataBean(1, 1, searchArea, searchWard, searchCity);
            //request.setAttribute("longi", areaTypeList.getArea_name());
            //request.setAttribute("latti", areaTypeList.getWard_no());
            request.setAttribute("surveyCoordinatesList", areaTypeList);
            System.out.println(latti + "," + longi);
            surveyCordinatesModel.closeConnection();
            request.getRequestDispatcher("autoMapWindowView").forward(request, response);
            return;
        }
       if(task.equals("areaDetailReport")){
               // String wardType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=surveyCordinatesModel.showAllData(searchArea, searchWard, searchCity);
                jrxmlFilePath = ctx.getRealPath("/report/newAreaDetailReport.jrxml");
                byte[] reportInbytes = surveyCordinatesModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
        if (task.equals("Delete")) {

            surveyCordinatesModel.deleteRecord(Integer.parseInt(request.getParameter("area_id")));  // Pretty sure that city_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int area_id;
            try {
                area_id = Integer.parseInt(request.getParameter("area_id"));
            } catch (Exception e) {
                area_id = 0;
            }
            if (task.equals("Save AS New")) {
                area_id = 0;
            }
            SurveyCoordinatesBean areaTypeBean = new SurveyCoordinatesBean();
            areaTypeBean.setArea_id(area_id);
            areaTypeBean.setWard_no(request.getParameter("ward_no"));
         //   areaTypeBean.setWard_name(request.getParameter("ward_name"));
            areaTypeBean.setArea_name(request.getParameter("area_name"));
            areaTypeBean.setRemark(request.getParameter("remark"));
            areaTypeBean.setActive(request.getParameter("active"));
            areaTypeBean.setWard_id(surveyCordinatesModel.getWardId(request.getParameter("ward_no")));
            areaTypeBean.setWard_rev_no(surveyCordinatesModel.getWard_rev_no(request.getParameter("ward_no")));

            if (area_id == 0) {
                System.out.println("Inserting values by AreaModel......");
                surveyCordinatesModel.insertRecord(areaTypeBean);
            } else {
                System.out.println("Update values by AreaModel........");
                surveyCordinatesModel.updateRecord(areaTypeBean);
            }
        }
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
        noOfRowsInTable = surveyCordinatesModel.getNoOfRows(searchArea, searchWard, searchCity);

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
        }else if (task.equals("Show All Records")) {
            searchArea = "";
            searchCity = "";
            searchWard = "";
        }
        List<SurveyCoordinatesBean> areaTypeList = surveyCordinatesModel.showData(lowerLimit, noOfRowsToDisplay, searchArea, searchWard, searchCity);
        lowerLimit = lowerLimit + areaTypeList.size();
        noOfRowsTraversed = areaTypeList.size();

        // forwarding parameters to jsp
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("areaTypeList", areaTypeList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        //end  of search table

        System.out.println("color is :" + surveyCordinatesModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("city_name", searchCity);
        request.setAttribute("ward_no", searchWard);
        request.setAttribute("area_name", searchArea);
        request.setAttribute("message", surveyCordinatesModel.getMessage());
        request.setAttribute("msgBgColor", surveyCordinatesModel.getMsgBgColor());
        request.getRequestDispatcher("/view/dataEntry/surveyCordinatesView.jsp").forward(request, response);
   }
@Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
    }
}
