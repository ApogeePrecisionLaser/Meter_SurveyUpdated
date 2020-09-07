/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.general.controller;

//import com.survey.dataEntry.controller.*;
import com.survey.general.model.TimeIntervalModel;
import com.survey.tableClasses.TimeIintervalBean;
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
public class TimeIntervalController extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{

    int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
      //  System.out.println("this is LIGHT TYPE Controller....");
        ServletContext ctx = getServletContext();
        TimeIntervalModel timeIntervalModel = new TimeIntervalModel();
        timeIntervalModel.setDriverClass(ctx.getInitParameter("driverClass"));
        timeIntervalModel.setConnectionString(ctx.getInitParameter("connectionString"));
        timeIntervalModel.setDb_username(ctx.getInitParameter("db_username"));
        timeIntervalModel.setDb_password(ctx.getInitParameter("db_password"));
        timeIntervalModel.setConnection();
        String task = request.getParameter("task");
        String wattageType="";

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTimeIntervalFor")) {
                    list = timeIntervalModel.getTimeIntervalFor(q);
                }
              /*  else if (JQstring.equals("getWattageType")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        wattageType = request.getParameter("action2");
                    }
                    list = timeIntervalModel.getWattageType(q);
                } */
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                timeIntervalModel.closeConnection();
                return;
            }
            } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        } 
        if (task == null) {
            task = "";
        }
         if(task.equals("generateMapReport")){
                String searchTimeIntervalFor="";
                searchTimeIntervalFor = request.getParameter("search_time_interval_for");
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=timeIntervalModel.showAllData(searchTimeIntervalFor);
                jrxmlFilePath = ctx.getRealPath("/report/timeIntervalReport.jrxml");
                byte[] reportInbytes = timeIntervalModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }  
         if (task.equals("Delete")) {
            timeIntervalModel.deleteRecord(Integer.parseInt(request.getParameter("time_interval_id")));  // Pretty sure that organisation_type_id will be available.
        }else if (task.equals("Save") || task.equals("Save AS New")  || task.equals("Update")) {
            int time_interval_id;
            try {
                time_interval_id = Integer.parseInt(request.getParameter("time_interval_id"));
            } catch (Exception e) {
                time_interval_id = 0;
            }
            if (task.equals("Save AS New")) {
                time_interval_id = 0;
            }
             
            TimeIintervalBean timeIintervalBean = new TimeIintervalBean();
            timeIintervalBean.setTime_interval_id(time_interval_id);
          
            if(request.getParameter("interval_type").trim().equals("Hours"))
            {
            timeIintervalBean.setInterval_type("H");
            int time_interval= Integer.parseInt(request.getParameter("time_interval"));
            time_interval=time_interval*3600;
            String time_interval1= String.valueOf(time_interval);
            timeIintervalBean.setTime_interval(time_interval1);
             }
            else if(request.getParameter("interval_type").trim().equals("Minutes")){
            timeIintervalBean.setInterval_type("M");
            int time_interval= Integer.parseInt(request.getParameter("time_interval").trim());
            time_interval=time_interval*60;
            String time_interval1= String.valueOf(time_interval);
            timeIintervalBean.setTime_interval(time_interval1);
            }else
            {
             timeIintervalBean.setInterval_type("S");
           // int time_interval= Integer.parseInt(request.getParameter("time_interval"));
            timeIintervalBean.setTime_interval(request.getParameter("time_interval").trim());
            }

            timeIintervalBean.setTime_interval_for(request.getParameter("time_interval_for"));
            if (time_interval_id == 0) {
                System.out.println("Inserting values by model......");
                timeIntervalModel.insertRecord(timeIintervalBean);
            } else {
                System.out.println("Update values by model........");
                timeIntervalModel.updateRecord(timeIintervalBean);
            }
        }
         // Start of Auto Completer code
        String searchTimeIntervalFor ="";
    //    String searchWattageType = "";
        // End of Auto Completer code
        searchTimeIntervalFor = request.getParameter("search_time_interval_for");
        
         try {
            if (searchTimeIntervalFor== null) {
                searchTimeIntervalFor= "";
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
         noOfRowsInTable = timeIntervalModel.getNoOfRows(searchTimeIntervalFor);

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
          if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")|| task.equals("Update")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchTimeIntervalFor = "";
            searchTimeIntervalFor = "";
        }
           // Logic to show data in the table.
        List<TimeIintervalBean> timeIntervalLIst = timeIntervalModel.showData(lowerLimit, noOfRowsToDisplay,searchTimeIntervalFor);
        lowerLimit = lowerLimit + timeIntervalLIst.size();
        noOfRowsTraversed = timeIntervalLIst.size();
          // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("timeIntervalLIst", timeIntervalLIst);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + timeIntervalModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchTimeIntervalFor", searchTimeIntervalFor);
      //  request.setAttribute("searchWattageType", searchWattageType);
        request.setAttribute("message", timeIntervalModel.getMessage());
        request.setAttribute("msgBgColor", timeIntervalModel.getMsgBgColor());
        request.getRequestDispatcher("/time_interval_view").forward(request, response);

}
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException{
            doGet(request, response);
    }
}
