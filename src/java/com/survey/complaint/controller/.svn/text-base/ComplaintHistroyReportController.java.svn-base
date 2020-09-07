/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.controller;

import com.survey.complaint.model.ComplaintHistoryReportModel;
import com.survey.complaint.tableClasses.ComplaintHistoryReport;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.survey.dbcon.DBConnection;
import java.io.ByteArrayOutputStream;

/**
 *
 * @author Shruti
 */
public class ComplaintHistroyReportController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        try {
            String task = null;
            List list = null;
            String input = null;
            String param = null;
            int i = 0;
            /*HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user_name") == null) {
                response.sendRedirect("beforelogin.jsp");
                return;
            }*/
            ComplaintHistoryReportModel historyReportModel = new ComplaintHistoryReportModel();
            ServletContext ctx = getServletContext();
            historyReportModel.setDriverClass(ctx.getInitParameter("driverClass"));
            historyReportModel.setConnectionString(ctx.getInitParameter("connectionString"));
            historyReportModel.setDb_userName(ctx.getInitParameter("db_username"));
            historyReportModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            historyReportModel.setConnection();
            /*try {
                historyReportModel.setConnection(DBConnection.getConnectionForUtf(ctx, session));
            } catch (Exception e) {
                System.out.println("error in ComplaintHistoryReportController setConnection() calling try block" + e);
            }*/
            String jqstring = request.getParameter("action");
            String q = request.getParameter("q");
            String response_ad_sub_site_name;
            String response_ad_sub_site_data;
            String response_site_name;
            String site_name;
            String complaintDateFrom = null, complaintDateTo = null;
            response.setContentType("text/html");
            if (jqstring != null) {
                PrintWriter out = response.getWriter();             
                input = request.getParameter("q").trim();
               // if (jqstring != null) {
                //    param = request.getParameter("Param");
                if (jqstring.equals("siteList")) {
                    String ad_sub_type2 = request.getParameter("add_sub_type2");
                    list = historyReportModel.getsiteList(ad_sub_type2);
                    i = 1;
                  }
                  else if (jqstring.equals("PostedBy")) {
                  list = historyReportModel.getPostedBy();
               }
                 else if (jqstring.equals("AssingedFor")) {
                  list = historyReportModel.getAssingedFor();
               }
                else if (jqstring.equals("ComplaintNo")) {
                  list = historyReportModel.getComplaintNo(input);
               }
                historyReportModel.closeConnection();
                Iterator itr = list.iterator();
                int count = 0;
                while (itr.hasNext()) {
               String   data = (String) itr.next();
                    if (data.toUpperCase().startsWith(input.toUpperCase())) {
                        out.println(data);
                        count++;
                    }
                }

                if (count == 0 && i == 1) {
                    out.print("No Such Site Exists.");

                }

                return;
            } else {
                task = request.getParameter("task");
                if (task == null) {
                    task = "";
                }
                if (request.getParameter("search_compalint_date_from") != null && !request.getParameter("search_compalint_date_from").isEmpty()) {
                    complaintDateFrom = request.getParameter("search_compalint_date_from");
                    System.out.println("cotrller page search_compalint_date_from " + complaintDateFrom);
                }
                if (request.getParameter("search_compalint_date_to") != null && !request.getParameter("search_compalint_date_to").isEmpty()) {
                    complaintDateTo = request.getParameter("search_compalint_date_to");
                    //    System.out.println("cotrller page search_compalint_date_to "+complaintDateTo);
                }
                if (task.equals("compliantTypeList")) {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out = response.getWriter();
                    Map map = historyReportModel.getComplintTypelist();
                    String response_data = map.toString();
                    out.println(response_data);
                    return;


                }
              //
              else  if (task.equals("getSiteName")) {
                       response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out = response.getWriter(); 
                  site_name =request.getParameter("site_name");
                       System.out.println(site_name);
                        response_site_name=historyReportModel.getSiteName(site_name);                  
                       out.println(response_site_name );
                   
                
                    
                    return ;
                }
                else if (task.equals("getAddSubSiteName")) {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();
                  out = response.getWriter();               
                    response_ad_sub_site_name=request.getParameter(" sub_site_name");
                   response_ad_sub_site_name=historyReportModel.getAdSubSiteName(response_ad_sub_site_name);               
                   out.println(response_ad_sub_site_name);
                  return ;          
              }   

              else if (task.equals("getAddSubSite")) {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();
                  out = response.getWriter();     
                    site_name=request.getParameter("site_name");
                   response_ad_sub_site_name=historyReportModel.getAdSubSite(site_name);             
                   out.println(response_ad_sub_site_name);
                  return ;             
              }

              else if (task.equals("getComplaintStatus")) {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out = response.getWriter();
                    Map map = historyReportModel.getComplaintStatusList();
                    String response_data = map.toString();
                    out.println(response_data);
                    return;
              }
               else if (task.equals("Delete")) {
                    historyReportModel.deleteRecord(Integer.parseInt(request.getParameter("complaint_reg_no")));  // Pretty sure that meter_id will be available.
                
                } else if (task.equals("Show All Records")) {
                    // searchPrintMedia = "";
                }
            }

         //if (request.getAttribute("isSelectPriv").equals("Y")) {
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
            if (task.equals("Search") || task.equals("clear")) {

                lowerLimit = 0;
            }
           // int complaint_register_id=0;
            int s_status = 0;
            String s_posted_by = "", s_assigned_for = "";
             int s_complaint_no=0;
            if (request.getParameter("s_posted_by") != null && !request.getParameter("s_posted_by").isEmpty()) {
                s_posted_by = request.getParameter("s_posted_by");
                request.setAttribute("s_posted_by", s_posted_by);
            }
            if (request.getParameter("s_status") != null && !request.getParameter("s_status").isEmpty()) {
                s_status = Integer.parseInt(request.getParameter("s_status"));
                request.setAttribute("s_status", s_status);
            }
            if (request.getParameter("s_assigned_for") != null && !request.getParameter("s_assigned_for").isEmpty()) {
                s_assigned_for = request.getParameter("s_assigned_for");
                request.setAttribute("s_assigned_for", s_assigned_for);
            }
            if(request.getParameter("s_complaint_no")!=null && !request.getParameter("s_complaint_no").isEmpty()){
              s_complaint_no=Integer.parseInt(request.getParameter("s_complaint_no"));
              if(s_complaint_no!=0){
              request.setAttribute("s_complaint_no", s_complaint_no);
            }
            }
              if (task.equals("PRINTComplaintReport")) {
                String jrxmlFilePath;
                response.setHeader("Content-Type", "text/plain; charset=UTF-8");
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintHistoryPdf.jrxml");
                String delayed_work = request.getParameter("delayed_work");
                byte[] reportInbytes = historyReportModel.generateComplaintReportPdf(jrxmlFilePath, s_posted_by, s_assigned_for, s_status,delayed_work,s_complaint_no);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                historyReportModel.closeConnection();
                return;

            }

              if (task.equals("PRINTComplaintExcelReport")) {
                String jrxmlFilePath;
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintHistory.jrxml");
                        String delayed_work = request.getParameter("delayed_work");
                        ByteArrayOutputStream reportInbytes = historyReportModel.generateComplaintReportExcel(jrxmlFilePath, s_posted_by, s_assigned_for, s_status,delayed_work,s_complaint_no);
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment;filename=ComplaintHistory.xls");
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        historyReportModel.closeConnection();
                        return;
            }
             noOfRowsInTable = historyReportModel.getNoOfRows(s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no);                  // get the number of records (rows) in the table.
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

       //     if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records") || task.equals("Send")) {
          //      lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
         //   }
            // Logic to show data in the table.
            List<ComplaintHistoryReport>ComplaintHistoryReportList = historyReportModel.showData(lowerLimit, noOfRowsToDisplay, s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no);
            lowerLimit = lowerLimit + ComplaintHistoryReportList.size();
            noOfRowsTraversed = ComplaintHistoryReportList.size();


            // Now set request scoped attributes, and then forward the request to view.
            // Following request scoped attributes NAME will remain constant from module to module.
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
             request.setAttribute("lowerLimit", lowerLimit);
             request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
             request.setAttribute("ComplaintHistoryReportList",ComplaintHistoryReportList);
         //}//prevele
            request.setAttribute("search_compalint_date_from", complaintDateFrom);
            request.setAttribute("search_compalint_date_to", complaintDateTo);
        //    request.setAttribute("lowerLimit", lowerLimit);
         //   request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
            
            request.setAttribute("message", historyReportModel.getMessage());
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
            request.setAttribute("msgBgColor", historyReportModel.getMsgBgColor());
            // Following request scoped attributes NAME will change from module to module.
         //   request.setAttribute("ComplaintHistoryReportList",ComplaintHistoryReportList);

            request.setAttribute("statuslist", historyReportModel.getStatuslist());
            // request.setAttribute("complaintTypelist", complregisterModel.getComplintTypelist());
            historyReportModel.closeConnection();
            request.getRequestDispatcher("complaint_history_report_view").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error int ComplaintReisterController" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}
