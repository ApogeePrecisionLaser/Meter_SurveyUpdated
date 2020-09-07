/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.complaint.controller;

import com.survey.complaint.model.ComplaintTypeModels;
import com.survey.complaint.tableClasses.ComplaintTypes;
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
import java.io.PrintWriter;
import java.util.*;
import org.apache.jasper.tagplugins.jstl.core.Catch;
/**
 *
 * @author Shruti
 */
public class ComplaintTypeControllers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
         request.setCharacterEncoding("UTF-8");
         response.setHeader("Content-Type", "text/plain; charset=UTF-8");
            String task = null;
            /*HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user_name") == null) {
                response.sendRedirect("beforelogin.jsp");
                return;
            }*/
            ComplaintTypeModels compComplaintTypeModel = new ComplaintTypeModels();
            ServletContext ctx = getServletContext();
            compComplaintTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
            compComplaintTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
            compComplaintTypeModel.setDb_userName(ctx.getInitParameter("db_username"));
            compComplaintTypeModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            compComplaintTypeModel.setConnection();

            /*try {
            compComplaintTypeModel.setConnection(DBConnection.getConnectionForUtf(ctx, session));
        } catch (Exception e) {
            System.out.println("error in ComplaintTypeController setConnection() calling try block"+e);
        }*/
        try {
        //    String task = null;
            int i = 0;
            String searchTypeOfComplaint = "";
            PrintWriter out = response.getWriter();
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input

                if (JQstring != null) {
                    List<String> list = null;
                    if (JQstring.equals("getComplaintTypeList")) {
                        list = compComplaintTypeModel .getComplaintTypeList(q);
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
                    compComplaintTypeModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
            }

     searchTypeOfComplaint = request.getParameter("searchTypeOfComplaint");
            try {
                if (searchTypeOfComplaint == null) {
                    searchTypeOfComplaint = "";
                }
            } catch (Exception e) {
            }
          //  task = request.getParameter("task");
         //   if (task == null) {
         //       task = "";
         //   }
 //            String jqstring = request.getParameter("action");
//            response.setContentType("text/html");
//            PrintWriter out = response.getWriter();
//            if (request.getParameter("q") != null) {
//                List list = null;
//                String data = null;
//                String input = request.getParameter("q").trim();
//                int i = 0;
//                if (jqstring.equals("siteList")) {
//                    String ad_sub_type2 = request.getParameter("add_sub_type2");
//                    list = compStatusModel.getsiteList(ad_sub_type2);
//                    i = 1;
//                }
//
//                compStatusModel.closeConnection();
//                Iterator itr = list.iterator();
//                int count = 0;
//                while (itr.hasNext()) {
//                    data = (String) itr.next();
//                    if (data.toUpperCase().startsWith(input.toUpperCase())) {
//                        out.println(data);
//                        count++;
//                    }
//                }
//
//                if (count == 0 && i == 1) {
//                    out.print("No Such Site Exists.");
//
//                }
//                return;
//            } else {
            task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            if (task.equals("Delete")) {
                compComplaintTypeModel.deleteRecord(Integer.parseInt(request.getParameter("complaint_type_id")));  // Pretty sure that meter_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Add All Records")||task.equals("Update")) {
                try {
                    ComplaintTypes complaint_type = new ComplaintTypes();
                    String complaint_reg_no[] = new String[1];
                    try {
                        complaint_reg_no[0] = request.getParameter("complaint_type_id");            // meter_id may or may NOT be available i.e. it can be update or new record.
                    } catch (Exception e) {
                        complaint_reg_no[0] = "0";
                    }
                    if (task.equals("Save AS New")) {
                        complaint_reg_no[0] = "0";
                         String complaint_reg_no1[]={"1"};
                        complaint_type.setComplaint_type_idM(complaint_reg_no1);
                    } else if (task.equals("Save")) {
                       complaint_type.setComplaint_type_idM(complaint_reg_no);
                    }
                    complaint_type.setComplaint_typeM(request.getParameterValues("complaint_type"));
                    //complaint_type.setComplaint_type_engM(request.getParameterValues("complaint_type_eng"));
                    complaint_type.setDescriptionM(request.getParameterValues("description"));

                     if (task.equals("Add All Records")) {
                        complaint_type.setComplaint_type_idM(request.getParameterValues("complaint_type_id"));
                    }
                    if (complaint_reg_no[0].equals("0") || task.equals("Add All Records")) {
                        compComplaintTypeModel.insertRecord(complaint_type);
                    } else {                        // update existing record.
                        complaint_type.setComplaint_type_idM(request.getParameterValues("complaint_type_id"));
                        compComplaintTypeModel.updateRecord( complaint_type);
                    }
                } catch (Exception e) {
                    System.out.println("Error - " + e);
                }
            } else if (task.equals("Show All Records")) {
                searchTypeOfComplaint = "";
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
             noOfRowsInTable = compComplaintTypeModel.getNoOfRows( searchTypeOfComplaint);

            if (task.equals("Search") || task.equals("clear")) {

                lowerLimit = 0;
            }
            
            //noOfRowsInTable = compComplaintTypeModel.getNoOfRows();                  // get the number of records (rows) in the table.
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
            List<ComplaintTypes>list1 = compComplaintTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchTypeOfComplaint);
            lowerLimit = lowerLimit + list1.size();
            noOfRowsTraversed = list1.size();

            // Logic to show data in the table.
      //      List< ComplaintTypes> ComplaintTypeList = compComplaintTypeModel.showData(lowerLimit, noOfRowsToDisplay);
        //    lowerLimit = lowerLimit + ComplaintTypeList.size();
    //        noOfRowsTraversed = ComplaintTypeList.size();


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
            request.setAttribute("ComplaintTypeList", list1);
   //}
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", compComplaintTypeModel.getMessage());
            request.setAttribute("searchTypeOfComplaint", searchTypeOfComplaint);
            // request.setAttribute("searchPrintMedia", searchPrintMedia);
            request.setAttribute("msgBgColor", compComplaintTypeModel.getMsgBgColor());
            // Following request scoped attributes NAME will change from module to module.
       //     request.setAttribute("ComplaintTypeList", list1);
            compComplaintTypeModel.closeConnection();
            request.getRequestDispatcher("complaintTypes_view").forward(request, response);
            //}

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
