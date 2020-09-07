/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.controller;

import com.survey.complaint.model.ComplaintAssingedForModel;
import com.survey.complaint.tableClasses.ComplaintRegister;
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

/**
 *
 * @author Shruti
 */
public class ComplaintAssingedForController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
      //  try {
            String task = null;
            /*HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user_name") == null) {
                response.sendRedirect("beforelogin.jsp");
                return;
            }*/
            ComplaintAssingedForModel complaint_assinged_model = new ComplaintAssingedForModel();
            ServletContext ctx = getServletContext();
            complaint_assinged_model.setDriverClass(ctx.getInitParameter("driverClass"));
            complaint_assinged_model.setConnectionString(ctx.getInitParameter("connectionString"));
            complaint_assinged_model.setDb_userName(ctx.getInitParameter("db_username"));
            complaint_assinged_model.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            complaint_assinged_model.setConnection();
            /*try {
                complaint_assinged_model.setConnection(DBConnection.getConnectionForUtf(ctx, session));
            } catch (Exception e) {
                System.out.println("error in ComplaintRegisterController setConnection() calling try block" + e);
            }*/
          try {
            //    String task = null;
            int i = 0;
            int search_type_of_complaint_no=0;
            String search_assigned_for="";
        
            int complaint_no;
           String response_data;
            String JQstring;
            PrintWriter out = response.getWriter();
            try {
                 JQstring = request.getParameter("action1");
                 String q = request.getParameter("q");   // field own input

                if (JQstring != null) {
                     List<String> list = null;
                    if (JQstring.equals("getComplaintNoList")) {

                        list = complaint_assinged_model.getComplaintNoList(q);
                        Iterator<String> iter = list.iterator();
                        while (iter.hasNext()) {
                            String data = iter.next();
                            if (data == null) {
                                out.print("");
                            } else if (data.toUpperCase().startsWith(q.toUpperCase())){
                                out.println(data);
                            }
                        }
                    }
                     if (JQstring.equals("getAssignedForlist")) {

                        list = complaint_assinged_model.getAssignedForSearchlist(q);
                        Iterator<String> iter = list.iterator();
                        while (iter.hasNext()) {
                            String data = iter.next();
                            if (data == null) {
                                out.print("");
                            } else if (data.toUpperCase().startsWith(q.toUpperCase())){
                                out.println(data);
                            }
                        }
                    }
             /*      if(JQstring.equals("getAssigneForNamelist"))
                  {
                        list = complaint_assinged_model.getAssignedForlist(q);
                        Iterator<String> iter = list.iterator();
                        while (iter.hasNext()) {
                            String data = iter.next();
                            if (data == null) {
                                out.print("");
                            } else {
                                out.println(data);
                            }
                        }
                   
                    }  */
                   complaint_assinged_model.closeConnection();
                    return ;
                }

            } catch (Exception e) {
            }
        
        if (request.getParameter("searchAssignedFor") != null && !request.getParameter("searchAssignedFor").isEmpty()) {
                search_assigned_for = request.getParameter("searchAssignedFor");
             request.setAttribute("searchAssignedFor",search_assigned_for);
        }

            try {
                if (request.getParameter("searchTypeOfComplaintNo") != null || !request.getParameter("searchTypeOfComplaintNo").isEmpty()) {
                    search_type_of_complaint_no = Integer.parseInt(request.getParameter("searchTypeOfComplaintNo"));

                    search_assigned_for = "";
                }

            } catch (Exception e) {
                search_type_of_complaint_no =0;
                // search_feedback_date = 0;
            }

            task = request.getParameter("task");
            if (task == null) {
                task = "";



                }

                 else if (task.equals("Save")) {
                String[] compalint_rev_no= request.getParameterValues("compalint_rev_no");
                String[] com_reg_id = request.getParameterValues("com_reg_id");
                String[] check_print = request.getParameterValues("is_check");
                String[]    assinged_for = request.getParameterValues("assigned_for");
                   complaint_assinged_model.complaintAssingedFor(com_reg_id,compalint_rev_no,assinged_for,check_print);
                } 
               else if (task.equals("Show All Records")) {
                search_type_of_complaint_no = 0;
                search_assigned_for = "";

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
            
           
              
            noOfRowsInTable = complaint_assinged_model.getNoOfRows(search_assigned_for,search_type_of_complaint_no);                  // get the number of records (rows) in the table.
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

            if (task.equals("Save")|| task.equals("Add All Records")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
 
 // Logic to show data in the table.
            List<ComplaintRegister> ComplaintRegisterList = complaint_assinged_model.showData(lowerLimit, noOfRowsToDisplay,search_assigned_for,search_type_of_complaint_no);
            lowerLimit = lowerLimit + ComplaintRegisterList.size();
            noOfRowsTraversed = ComplaintRegisterList.size();


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
          //  request.setAttribute("searchAssignedFor",search_assigned_for);
           if(search_type_of_complaint_no==0){
              request.setAttribute("searchTypeOfComplaintNo", "");
           }
       else{
              request.setAttribute("searchTypeOfComplaintNo",  search_type_of_complaint_no);
              }

            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("ComplaintRegisterList", ComplaintRegisterList);    
       //}//prevledge
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
               request.setAttribute("message",complaint_assinged_model.getMessage());
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
            request.setAttribute("msgBgColor",complaint_assinged_model.getMsgBgColor());
            //   request.setAttribute("sms_mail_message", complaint_assinged_model.getSmsMailMessage());
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
         //   request.setAttribute("sms_mail_msgBgColor", complaint_assinged_model.getSmsMailMsgBgColor());
        //    request.setAttribute("message", complaint_assinged_model.getMessage());
           //  request.setAttribute("searchPrintMedia", searchPrintMedia);
         //  request.setAttribute("msgBgColor", complaint_assinged_model.getMsgBgColor());
            // Following request scoped attributes NAME will change from module to module.
         //   request.setAttribute("ComplaintRegisterList", ComplaintRegisterList);

         //   request.setAttribute("statuslist",complaint_assinged_model.getStatuslist());
            // request.setAttribute("complaintTypelist", complregisterModel.getComplintTypelist());
            complaint_assinged_model.closeConnection();
            request.getRequestDispatcher("complaint_aasinged_for_view").forward(request, response);

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
