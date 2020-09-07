/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.complaint.controller;

import com.survey.complaint.model.ComplaintHistoryReportModel;
import com.survey.complaint.model.ComplaintMISModel;
import com.survey.complaint.model.ComplaintRegisterModel;
import com.survey.complaint.tableClasses.ComplaintHistoryReport;
import com.survey.complaint.tableClasses.ComplaintRegister;
import com.survey.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
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

/**
 *
 * @author jpss
 */
public class ComplaintMISController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
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
            ComplaintMISModel misModel = new ComplaintMISModel();
            ComplaintRegisterModel complaintRegisterModel = new ComplaintRegisterModel();
            ServletContext ctx = getServletContext();
            misModel.setDriverClass(ctx.getInitParameter("driverClass"));
            misModel.setConnectionString(ctx.getInitParameter("connectionString"));
            misModel.setDb_userName(ctx.getInitParameter("db_username"));
            misModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            misModel.setConnection();
            complaintRegisterModel.setDriverClass(ctx.getInitParameter("driverClass"));
            complaintRegisterModel.setConnectionString(ctx.getInitParameter("connectionString"));
            complaintRegisterModel.setDb_userName(ctx.getInitParameter("db_username"));
            complaintRegisterModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            complaintRegisterModel.setConnection();
            /*try {
                misModel.setConnection(DBConnection.getConnectionForUtf(ctx, session));
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
                    list = misModel.getsiteList(ad_sub_type2);
                    i = 1;
                  }
                  else if (jqstring.equals("PostedBy")) {
                  list = misModel.getPostedBy();
               }
                 else if (jqstring.equals("AssingedFor")) {
                  list = misModel.getAssingedFor();
               }
                else if (jqstring.equals("ComplaintNo")) {
                  list = misModel.getComplaintNo(input);
               }
                misModel.closeConnection();
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
                    Map map = misModel.getComplintTypelist();
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
                        response_site_name=misModel.getSiteName(site_name);
                       out.println(response_site_name );



                    return ;
                }
                else if (task.equals("getAddSubSiteName")) {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();
                  out = response.getWriter();
                    response_ad_sub_site_name=request.getParameter(" sub_site_name");
                   response_ad_sub_site_name=misModel.getAdSubSiteName(response_ad_sub_site_name);
                   out.println(response_ad_sub_site_name);
                  return ;
              }

              else if (task.equals("getAddSubSite")) {
                  response.setContentType("text/html");
                  PrintWriter out = response.getWriter();
                  out = response.getWriter();
                    site_name=request.getParameter("site_name");
                   response_ad_sub_site_name=misModel.getAdSubSite(site_name);
                   out.println(response_ad_sub_site_name);
                  return ;
              }

              else if (task.equals("getComplaintStatus")) {
                    response.setContentType("text/html");
                    PrintWriter out = response.getWriter();
                    out = response.getWriter();
                    Map map = misModel.getComplaintStatusList();
                    String response_data = map.toString();
                    out.println(response_data);
                    return;
              }
               else if (task.equals("Delete")) {
                    misModel.deleteRecord(Integer.parseInt(request.getParameter("complaint_reg_no")));  // Pretty sure that meter_id will be available.

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
            String s_posted_by = "", s_assigned_for = "", pole_no = "";
             int s_complaint_no=0, pole_id=0, sp_id = 0;
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
              if(request.getParameter("pole_no")!=null && !request.getParameter("pole_no").isEmpty()){
              pole_no=request.getParameter("pole_no");
              pole_id = complaintRegisterModel.getPoleId(pole_no);
              if(pole_id!=0){
              request.setAttribute("pole_no", pole_no);
                  }
            }
             if(request.getParameter("sp_id")!=null && !request.getParameter("sp_id").isEmpty()){
              sp_id=Integer.parseInt(request.getParameter("sp_id"));
              //pole_id = complaintRegisterModel.getPoleId(pole_no);
              if(pole_id!=0){
              request.setAttribute("pole_no", pole_no);
                  }
            }
            
              if (task.equals("PRINTComplaintReport")) {
                String jrxmlFilePath;
                response.setHeader("Content-Type", "text/plain; charset=UTF-8");
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintMISReport.jrxml");
                String delayed_work = request.getParameter("delayed_work");
                byte[] reportInbytes = misModel.generateComplaintReportPdf(jrxmlFilePath, s_posted_by, s_assigned_for, s_status,delayed_work,s_complaint_no,pole_id, sp_id);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                misModel.closeConnection();
                return;

            }

              if (task.equals("PRINTComplaintExcelReport")) {
                String jrxmlFilePath;
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintMISReport.jrxml");
                        String delayed_work = request.getParameter("delayed_work");
                        ByteArrayOutputStream reportInbytes = misModel.generateComplaintReportExcel(jrxmlFilePath, s_posted_by, s_assigned_for, s_status,delayed_work,s_complaint_no, pole_id, sp_id);
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment;filename=ComplaintHistory.xls");
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        misModel.closeConnection();
                        return;
            }

             if (task.equals("HTMLView")) {
                String jrxmlFilePath;
                //response.setHeader("Content-Type", "text/plain; charset=UTF-8");
                //response.setContentType("application/pdf");
                //ServletOutputStream servletOutputStream = response.getOutputStream();
                //jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintMISReport.jrxml");
                String delayed_work = request.getParameter("delayed_work");
                List<ComplaintRegister> htmlList = misModel.showData(s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no, delayed_work, pole_id, sp_id);
                //response.setContentLength(reportInbytes.length); s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no, delayed_work
                //servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
               // servletOutputStream.flush();
                //servletOutputStream.close();
                misModel.closeConnection();
                request.setAttribute("ComplaintRegisterList", htmlList);
                request.getRequestDispatcher("misHTMLView").forward(request, response);
                return;

            }
             noOfRowsInTable = misModel.getNoOfRows(s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no);                  // get the number of records (rows) in the table.
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
            List<ComplaintHistoryReport>ComplaintHistoryReportList = misModel.showData(lowerLimit, noOfRowsToDisplay, s_status, s_posted_by, s_assigned_for, complaintDateFrom, complaintDateTo,s_complaint_no);
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
            request.setAttribute("complaintTypeList", complaintRegisterModel.complaintTypeList());
            request.setAttribute("divisionList", complaintRegisterModel.divisionList());
            request.setAttribute("zoneList", complaintRegisterModel.zoneList());
            request.setAttribute("feederList", complaintRegisterModel.feederList());
            request.setAttribute("switchingPointList", complaintRegisterModel.switchingPointList());

        //    request.setAttribute("lowerLimit", lowerLimit);
         //   request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());

            // request.setAttribute("searchPrintMedia", searchPrintMedia);

            request.setAttribute("message", misModel.getMessage());
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
            request.setAttribute("msgBgColor", misModel.getMsgBgColor());
            // Following request scoped attributes NAME will change from module to module.
         //   request.setAttribute("ComplaintHistoryReportList",ComplaintHistoryReportList);

            request.setAttribute("statuslist", misModel.getStatuslist());
            // request.setAttribute("complaintTypelist", complregisterModel.getComplintTypelist());
            misModel.closeConnection();
            request.getRequestDispatcher("complaint_mis_view").forward(request, response);

        } catch (Exception e) {
            System.out.println("Error int ComplaintReisterController" + e);
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
