/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.ContacterTypeModel;
import com.survey.dataEntry.model.LogErrorModel;
import com.survey.tableClasses.LogErrorBean;
import com.survey.util.GetDate;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Navitus1
 */
public class LogErrorController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        LogErrorModel logTypeModel=new LogErrorModel();
        LogErrorBean logTypeBean=new LogErrorBean();
        try {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 4, noOfRowsInTable=0;
        System.out.println("this is CONTACTER Controller....");
        ServletContext ctx = getServletContext();

        logTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        logTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        logTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        logTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        logTypeModel.setConnection();
        String task=request.getParameter("task");
        if(task==null)
        {
            task="";
        }
        try
        {
        String jQueryType=request.getParameter("action1");
        String q=request.getParameter("q");
        if(jQueryType!=null)
        {   PrintWriter out = response.getWriter();
            List<String> list=null;
            if(jQueryType.equals("searchJunction"))
            {
                list=logTypeModel.getDataJuction(q);
            }
             else if(jQueryType.equals("searchErrorMessage"))
             {
                list=logTypeModel.getDataErrorMsg(q);
             }
            Iterator<String> i=list.iterator();
            while(i.hasNext()){
                String data=i.next();
                out.println(data);
            }
            return;
        }
        }catch(Exception e)
        {
            System.out.println("Error :- "+e);
        }
        String searchJuction=request.getParameter("searchJunctionName");
        String searchErrorMessage=request.getParameter("searchErrorMsg");                     
        if(searchJuction==null)
        {
            searchJuction="";
        }
        if(searchErrorMessage==null)
        {
            searchErrorMessage="";
        }
         String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the five buttons: First, Previous, Next, Delete,Update.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        if(buttonAction.equals("Update"))
        {
            logTypeBean.setJunction_id1(request.getParameterValues("jId"));
            logTypeBean.setEmno(request.getParameterValues("emno"));
            logTypeBean.setImeino(request.getParameterValues("imeino"));
            logTypeModel.revisedRecord(logTypeBean);
        }

         String  searchReadingDateFrom, searchReadingDateTo;
         searchReadingDateFrom = request.getParameter("searchReadingDateFrom") == null ? "" : request.getParameter("searchReadingDateFrom");
         searchReadingDateTo = request.getParameter("searchReadingDateTo") == null ? GetDate.getCurrentDate() : request.getParameter("searchReadingDateTo");

        String searchDate = request.getParameter("searchDate") == null ? "" : request.getParameter("searchDate");
        String reading_time_from_hour = request.getParameter("reading_time_from_hour") == null? "00" : request.getParameter("reading_time_from_hour");
        String reading_time_from_min = request.getParameter("reading_time_from_min") == null? "00" : request.getParameter("reading_time_from_min");
        String time_from = request.getParameter("time_from") == null? "" : request.getParameter("time_from");
        String reading_time_to_hour = request.getParameter("reading_time_to_hour") == null? "00" : request.getParameter("reading_time_to_hour");
        String reading_time_to_min = request.getParameter("reading_time_to_min") == null? "00" : request.getParameter("reading_time_to_min");
        String time_to = request.getParameter("time_to") == null? "" : request.getParameter("time_to");
        String searchReadingTimeFrom = "";
        String searchReadingTimeTo = "";
        if(!reading_time_from_hour.equals("00") || !reading_time_from_min.equals("00")){
            if(!time_from.isEmpty() && time_from.equals("PM")){
                String hourFrom = "";
                if(reading_time_from_hour.equals("12")){
                    hourFrom = "00";
                }else hourFrom = reading_time_from_hour;
                searchReadingTimeFrom = (12 + Integer.parseInt(hourFrom))+":"+reading_time_from_min;
            }else{
                searchReadingTimeFrom = reading_time_from_hour+":"+reading_time_from_min;
            }
        }else searchReadingTimeFrom = "";

        if(!reading_time_to_hour.equals("00") || !reading_time_to_min.equals("00")){
            if(!time_to.isEmpty() && time_to.equals("PM")){
                String hourTo = "";
                if(reading_time_from_hour.equals("12")){
                    hourTo = "00";
                }else hourTo = reading_time_to_hour;
                searchReadingTimeTo = (12 + Integer.parseInt(hourTo))+":"+reading_time_to_min;
            }else searchReadingTimeTo = reading_time_to_hour+":"+reading_time_to_min;
        }else searchReadingTimeTo = "";

          try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
       
        if(buttonAction.equals("Update"))
        {
            lowerLimit=0;
        }
      //   System.out.println("searching.......... " + searchContacterType);
         noOfRowsInTable=  logTypeModel.getNoOfRows(searchReadingDateFrom,searchReadingDateTo,searchJuction,searchErrorMessage,searchReadingTimeFrom, searchReadingTimeTo,searchDate);
         //noOfRowsInTable=list.size();
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
         if(task.equals("Show All Records"))
         {
             searchJuction="";
             searchErrorMessage="";
             searchReadingDateFrom = "";
             searchReadingDateTo = GetDate.getCurrentDate();
             searchDate="";            
         }

        List list=logTypeModel.showAllData(lowerLimit,noOfRowsToDisplay,searchJuction,searchErrorMessage,searchReadingDateFrom,searchReadingDateTo,searchReadingTimeFrom, searchReadingTimeTo,searchDate);
        lowerLimit = lowerLimit + list.size();
        noOfRowsTraversed = list.size();

         // Now set request scoped attributes, and then forward the request to view.
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("message", logTypeModel.getMessage());
        request.setAttribute("status",LogErrorBean.getStatusofMessage());
        request.setAttribute("searchReadingDateTo", searchReadingDateTo);
        request.setAttribute("searchReadingDateFrom", searchReadingDateFrom);
        request.setAttribute("searchDate", searchDate);
        request.setAttribute("searchErrorMessage", searchErrorMessage);
        request.setAttribute("searchJunction",searchJuction);
        request.setAttribute("reading_time_from_hour", reading_time_from_hour);
        request.setAttribute("reading_time_from_min", reading_time_from_min);
        request.setAttribute("time_from", time_from);
        request.setAttribute("reading_time_to_hour", reading_time_to_hour);
        request.setAttribute("reading_time_to_min", reading_time_to_min);
        request.setAttribute("time_to", time_to);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("logList", list);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.getRequestDispatcher("logErrorView").forward(request, response);
       } catch(Exception e){
          System.out.print("error in logErrorController="+e);
        }finally {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
