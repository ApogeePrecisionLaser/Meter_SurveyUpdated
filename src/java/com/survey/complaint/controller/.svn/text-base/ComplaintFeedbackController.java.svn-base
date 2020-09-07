/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.controller;

import com.survey.complaint.model.ComplaintFeedbackModel;
//import com.ssadvt.tableClasses.ComplaintFeedback;
import com.survey.complaint.tableClasses.ComplaintFeedback;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.survey.dbcon.DBConnection;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author Shruti
 */
public class ComplaintFeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");


        //      try {
        String task = null;
        /*HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }*/
        ComplaintFeedbackModel compFeedbackModel = new ComplaintFeedbackModel();
        ServletContext ctx = getServletContext();
        compFeedbackModel.setDriverClass(ctx.getInitParameter("driverClass"));
        compFeedbackModel.setConnectionString(ctx.getInitParameter("connectionString"));
        compFeedbackModel.setDb_userName(ctx.getInitParameter("db_username"));
        compFeedbackModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
        compFeedbackModel.setConnection();
        /*try {
            compFeedbackModel.setConnection(DBConnection.getConnectionForUtf(ctx, session));
        } catch (Exception e) {
            System.out.println("error in ComplaintFeedbackController setConnection() calling try block" + e);
        }*/

        //
/*
        try {
        //    String task = null;
        int i = 0;
        String feedback_type = "";
        PrintWriter out = response.getWriter();
        try {
        String JQstring = request.getParameter("action1");
        String q = request.getParameter("q");   // field own input

        if (JQstring != null) {
        List<String> list = null;
        if (JQstring.equals("getFeedbackTypeList")) {
        list = compFeedbackModel.getComplaintFeedbackTypeList(q);
        }
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
        String data = iter.next();
        if (data == null) {
        out.print("");
        //                        if(data.equals("general_image_details_id")){
        //                            System.
        //                        }
        } else {
        out.println(data);
        }
        }
        compFeedbackModel.closeConnection();
        return;
        }
        } catch (Exception e) {
        }

        feedback_type = request.getParameter("feedback_type");
        try {
        if (feedback_type == null) {
        feedback_type= "";
        }
        } catch (Exception e) {
        }
         */
        //
        try {
            //    String task = null;
            int i = 0;
            int search_typeo_of_complaint_no = 0;
            String search_assigned_for = "";
            String search_feedback_date = null;
            int complaint_no;
            String response_data;
            String JQstring;
       
            try {
                JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input

                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getComplaintNoList")) {

                        list = compFeedbackModel.getComplaintNoList(q);
                        Iterator<String> iter = list.iterator();
                        while (iter.hasNext()) {
                            String data = iter.next();
                            if (data == null) {
                                out.print("");
                            } else {
                                out.println(data);
                            }
                        }
                    }
                    if (JQstring.equals("getAssignedForlist")) {

                        list = compFeedbackModel.getAssignedForSearchlist(q);
                        Iterator<String> iter = list.iterator();
                        while (iter.hasNext()) {
                            String data = iter.next();
                            if (data == null) {
                                out.print("");
                            } else {
                                out.println(data);
                            }
                        }
                    } else if (JQstring.equals("getComplaintDetail")) {
                        complaint_no = Integer.parseInt(request.getParameter("complaint_no"));

                        response_data = compFeedbackModel.getComplaintNoDetails(complaint_no);
                        //   response_data= "hello world";
                        // response_data = compFeedbackModel.getComplaintNoDetails(com);

                        out.println(response_data);

                    } else if (JQstring.equals("getAssignedComplaintDetail")) {
                        String assigned_complaint = request.getParameter("assigned_complaint").trim();
                        response_data = compFeedbackModel.getAssignedComplaintNolist(assigned_complaint);
                        out.println(response_data);
                        // List<String> responseData=new ArrayList();
                        // responseData=compFeedbackModel.getAssignedComplaintNolist( assigned_complaint);
                        // out.println(responseData);
                    }
                    compFeedbackModel.closeConnection();
                    return;
                }

            } catch (Exception e) {
            }

            if (request.getParameter("searchAssignedFor") != null && !request.getParameter("searchAssignedFor").isEmpty()) {
                search_assigned_for = request.getParameter("searchAssignedFor");
            }
            search_feedback_date = request.getParameter("searchFeedbackDate");

            //  search_assigned_for = request.getParameter("searchAssignedFor");
            // System.out.println("search_feedback_date in if ="+search_feedback_date);

            try {
                if (request.getParameter("searchTypeOfComplaintNo") != null || !request.getParameter("searchTypeOfComplaintNo").isEmpty()) {
                    search_typeo_of_complaint_no = Integer.parseInt(request.getParameter("searchTypeOfComplaintNo"));
                    search_feedback_date = null;
                    search_assigned_for = "";
                }

            } catch (Exception e) {
                search_typeo_of_complaint_no = 0;
                // search_feedback_date = 0;
            }



            //  String advertising_type = request.getParameter("complaint_no");


            //   String q = request.getParameter("q");   // "q" holds value entered in the "Advertising Sub Type1" field i.e. its own field.
            //    if (q != null) {    // "q" may be null. E.g. in case of getJSON(), window.open().
            //      q = q.trim();
            //  }



            task = request.getParameter("task");
           String other_feedback_type=request.getParameter("feedback_type");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                compFeedbackModel.deleteRecord(Integer.parseInt(request.getParameter("feedback_id")));  // Pretty sure that meter_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Add All Records")||task.equals("Update")) {
                try {
                    ComplaintFeedback complaint_feedback = new ComplaintFeedback();
                    String complaint_feedback_no[] = new String[1];
                    try {
                        complaint_feedback_no[0] = request.getParameter("feedback_id");            // meter_id may or may NOT be available i.e. it can be update or new record.
                    } catch (Exception e) {
                        complaint_feedback_no[0] = "0";
                    }
                    if (task.equals("Save AS New")) {
                        complaint_feedback_no[0] = "0";
                        String complaint_feedback_no1[] = {"1"};
                        complaint_feedback.setFeedback_idM(complaint_feedback_no1);
                    } else if (task.equals("Save")) {
                        complaint_feedback.setFeedback_idM(complaint_feedback_no);
                    }


                    complaint_feedback.setComplaint_noM(request.getParameterValues("complaint_no"));
                    if(other_feedback_type.equals("other")){
                        complaint_feedback.setFeedback_typeM(request.getParameterValues("other_feedback_type"));
                    }
                  else {
                    complaint_feedback.setFeedback_typeM(request.getParameterValues("feedback_type"));
                    }
                    // complaint_feedback.setFeedback_typeM(request.getParameterValues("complaint_type"));
                    complaint_feedback.setFeedback_dateM(request.getParameterValues("feedback_date"));
                    complaint_feedback.setComplaint_statusM(request.getParameterValues("complaint_status"));
                    complaint_feedback.setSubmission_time_hourM(request.getParameterValues("submission_time_hour"));
                    complaint_feedback.setSubmission_time_minM(request.getParameterValues("submission_time_min"));
                    complaint_feedback.setSubmission_timeM(request.getParameterValues("submission_time"));
                    //complaint_feedback.setComplaint_revision_noM(request.getParameterValues("complaint_revision_no"));
                    complaint_feedback.setAssigned_forM((request.getParameterValues("assigned_for")));
                    complaint_feedback.setRemarkM(request.getParameterValues("remark"));
                    if (task.equals("Add All Records")) {
                        complaint_feedback.setFeedback_idM(request.getParameterValues("feedback_id"));
                    }
                    if (complaint_feedback_no[0].equals("0") || task.equals("Add All Records")) {
                        compFeedbackModel.insertRecord(complaint_feedback);
                    } else {                        // update existing record.
                        complaint_feedback.setFeedback_idM(request.getParameterValues("feedback_id"));
                        compFeedbackModel.updateRecord(complaint_feedback);
                    }
                } catch (Exception e) {
                    System.out.println("Error - " + e);
                }
            } else if (task.equals("Show All Records")) {
                search_typeo_of_complaint_no = 0;
                search_assigned_for = "";
                search_feedback_date = null;
            }
            //}
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

          if (task.equals("PRINTComplaintFeedbackReport")) {
                String jrxmlFilePath;
                response.setHeader("Content-Type", "text/plain; charset=UTF-8");
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintFeedback.jrxml");
             //   String delayed_work = request.getParameter("delayed_work");
                byte[] reportInbytes =compFeedbackModel.generateComplaintFeedbackReportPdf(jrxmlFilePath,search_assigned_for,search_typeo_of_complaint_no);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                compFeedbackModel.closeConnection();
                return;

            }
           //  if(){
                 
         //    }
             if (task.equals("PRINTComplaintFeedExcelReport")) {
                String jrxmlFilePath;
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/complaint/complaintFeedback.jrxml");
                       // String delayed_work = request.getParameter("delayed_work");
                        ByteArrayOutputStream reportInbytes =compFeedbackModel.generateComplaintFeedbackReportExcel(jrxmlFilePath,search_assigned_for,search_typeo_of_complaint_no);
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment;filename=ComplaintFeedbackReport.xls");
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        compFeedbackModel.closeConnection();
                        return;
            }
            noOfRowsInTable = compFeedbackModel.getNoOfRows(search_typeo_of_complaint_no, search_feedback_date, search_assigned_for);                  // get the number of records (rows) in the table.
            //   noOfRowsInTable = compFeedbackModel.getNoOfRows();
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

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records")||task.equals("Update")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            List<Integer> complaint_no_list = compFeedbackModel.getComplaintNolist();
            // Logic to show data in the table.
            //  List<ComplaintFeedback>ComplaintFeedbackList = compFeedbackModel.showData(lowerLimit, noOfRowsToDisplay);
            List<ComplaintFeedback> ComplaintFeedbackList = compFeedbackModel.showData(lowerLimit, noOfRowsToDisplay, search_typeo_of_complaint_no, search_assigned_for, search_feedback_date);
            lowerLimit = lowerLimit + ComplaintFeedbackList.size();
            noOfRowsTraversed = ComplaintFeedbackList.size();

//System.out.println("search_feedback_date="+search_feedback_date);
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

            if (search_typeo_of_complaint_no == 0) {
                request.setAttribute("searchTypeOfComplaintNo", "");
            } else {
                request.setAttribute("searchTypeOfComplaintNo", search_typeo_of_complaint_no);
            }
           request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("complaint_no_list", complaint_no_list);
            request.setAttribute("ComplaintFeedbackList", ComplaintFeedbackList);

//}//prev
            request.setAttribute("searchAssignedFor", search_assigned_for);
            request.setAttribute("searchFeedbackDate", search_feedback_date);
         //  request.setAttribute("lowerLimit", lowerLimit);
         //   request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", compFeedbackModel.getMessage());
            request.setAttribute("feedback_type_list", compFeedbackModel.getFeedbackTypelist());
            request.setAttribute("assigned_for_list", compFeedbackModel.getAssignedForlist());
            // request.setAttribute("feedback_type", feedback_type);            
            request.setAttribute("msgBgColor", compFeedbackModel.getMsgBgColor());
            // Following request scoped attributes NAME will change from module to module.
          //  request.setAttribute("complaint_no_list", complaint_no_list);
          //  request.setAttribute("ComplaintFeedbackList", ComplaintFeedbackList);
            compFeedbackModel.closeConnection();
            request.getRequestDispatcher("complaintFeedback_view").forward(request, response);
            //}

        } catch (Exception e) {
            System.out.println("Error int ComplaintFeedbackController" + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
