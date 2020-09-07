/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;
import com.survey.dataEntry.model.TimerTypeModel;
import com.survey.tableClasses.TimerTypeBean;
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
public class TimerTypeController extends HttpServlet{
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
     int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Timer Controller....");
        ServletContext ctx = getServletContext();
        TimerTypeModel timerTypeModel = new TimerTypeModel();
        timerTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        timerTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        timerTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        timerTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        timerTypeModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTimerType")) {
                    list = timerTypeModel.getTimerType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                timerTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchTimerType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=timerTypeModel.showAllData(searchTimerType);
                jrxmlFilePath = ctx.getRealPath("/report/newTimerReport.jrxml");
                byte[] reportInbytes = timerTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Delete")) {
            timerTypeModel.deleteRecord(Integer.parseInt(request.getParameter("timer_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int timer_id;
            try {
                timer_id = Integer.parseInt(request.getParameter("timer_id"));
            } catch (Exception e) {
                timer_id = 0;
            }
            if (task.equals("Save AS New")) {
                timer_id = 0;
            }
            TimerTypeBean timerTypeBean = new TimerTypeBean();
            timerTypeBean.setTimer_id(timer_id);
            timerTypeBean.setTimer_type(request.getParameter("timer_type"));
            timerTypeBean.setRemark(request.getParameter("remark"));
            if (timer_id == 0) {
                System.out.println("Inserting values by model......");
                timerTypeModel.insertRecord(timerTypeBean);
            } else {
                System.out.println("Update values by model........");
                timerTypeModel.updateRecord(timerTypeBean);
            }
        }
         // Start of Auto Completer code
        String searchTimerType = "";

        // End of Auto Completer code
        searchTimerType = request.getParameter("searchTimerType");
         try {
            if (searchTimerType == null) {
                searchTimerType = "";
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
         System.out.println("searching.......... " + searchTimerType);
         noOfRowsInTable = timerTypeModel.getNoOfRows(searchTimerType);

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
            searchTimerType = "";
        }
           // Logic to show data in the table.
        List<TimerTypeBean> timerTypeList = timerTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchTimerType);
        lowerLimit = lowerLimit + timerTypeList.size();
        noOfRowsTraversed = timerTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("timerTypeList", timerTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + timerTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchTimerType", searchTimerType);
        request.setAttribute("message", timerTypeModel.getMessage());
        request.setAttribute("msgBgColor", timerTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/timerView").forward(request, response);
}
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            doGet(request, response);
    }

}
