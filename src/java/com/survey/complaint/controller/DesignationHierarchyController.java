/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.complaint.controller;

import com.survey.complaint.model.DesignationHierarchyModel;
import com.survey.complaint.tableClasses.DesignationHierarchyBean;
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
 * @author jpss
 */
public class DesignationHierarchyController extends HttpServlet {
   
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
            String task = null;
            /*HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user_name") == null) {
                response.sendRedirect("beforelogin.jsp");
                return;
            }*/
            DesignationHierarchyModel designationHierarchyModel = new DesignationHierarchyModel();
            ServletContext ctx = getServletContext();
            designationHierarchyModel.setDriverClass(ctx.getInitParameter("driverClass"));
            designationHierarchyModel.setConnectionString(ctx.getInitParameter("connectionString"));
            designationHierarchyModel.setDb_userName(ctx.getInitParameter("db_username"));
            designationHierarchyModel.setDb_userPasswrod(ctx.getInitParameter("db_password"));
            designationHierarchyModel.setConnection();

            /*try {
            designationHierarchyModel.setConnection(DBConnection.getConnectionForUtf(ctx, session));
        } catch (Exception e) {
            System.out.println("error in ComplaintTypeController setConnection() calling try block"+e);
        }*/
        try {
        //    String task = null;
            int i = 0;
            String searchDesignation = "", searchHigherDesignation = "";
            PrintWriter out = response.getWriter();
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input

                if (JQstring != null) {
                    List<String> list = null;
                    if (JQstring.equals("getDesignationList")) {
                        list = designationHierarchyModel .getDesignationList(q);
                    }else if (JQstring.equals("getHigherDesignationList")) {
                        String designation = request.getParameter("action2");
                        list = designationHierarchyModel .getHigherDesignationList(q, designationHierarchyModel.getDesignationId(designation));
                    }else if (JQstring.equals("getSearchDesignationList")) {
                        list = designationHierarchyModel .getDesignationList(q);
                    }else if (JQstring.equals("getsearchHigherDesignationList")) {
                        list = designationHierarchyModel .getDesignationList(q);
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
                    designationHierarchyModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
            }

     searchDesignation = request.getParameter("searchDesignation");
     searchHigherDesignation = request.getParameter("searchHigherDesignation");
            try {
                if (searchDesignation == null) {
                    searchDesignation = "";
                }
                if (searchHigherDesignation == null) {
                    searchHigherDesignation = "";
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
                designationHierarchyModel.deleteRecord(Integer.parseInt(request.getParameter("designationHierarchyBean_id")));  // Pretty sure that meter_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Add All Records")||task.equals("Update")) {
                try {
                    DesignationHierarchyBean designationHierarchyBean = new DesignationHierarchyBean();
                    String designation_hierarchy_id[] = new String[1];
                    try {
                        designation_hierarchy_id[0] = request.getParameter("designation_hierarchy_id");            // meter_id may or may NOT be available i.e. it can be update or new record.
                    } catch (Exception e) {
                        designation_hierarchy_id[0] = "0";
                    }
                    if (task.equals("Save AS New")) {
                        designation_hierarchy_id[0] = "0";
                         String designation_hierarchy_id1[]={"1"};
                        designationHierarchyBean.setDesignationHierarchy_idM(designation_hierarchy_id1);
                    } else if (task.equals("Save")) {
                       designationHierarchyBean.setDesignationHierarchy_idM(designation_hierarchy_id);
                    }
                    designationHierarchyBean.setDesignationM(request.getParameterValues("designation"));
                    designationHierarchyBean.setHigherDesignationM(request.getParameterValues("higher_designation"));
                    designationHierarchyBean.setDescriptionM(request.getParameterValues("description"));
                    designationHierarchyBean.setRemarkM(request.getParameterValues("remark"));

                     if (task.equals("Add All Records")) {
                        designationHierarchyBean.setDesignationHierarchy_idM(request.getParameterValues("designation_hierarchy_id"));
                    }
                    if (designation_hierarchy_id[0].equals("0") || task.equals("Add All Records")) {
                        designationHierarchyModel.insertRecord(designationHierarchyBean);
                    } else {                        // update existing record.
                        designationHierarchyBean.setDesignationHierarchy_idM(request.getParameterValues("designation_hierarchy_id"));
                        designationHierarchyModel.updateRecord( designationHierarchyBean);
                    }
                } catch (Exception e) {
                    System.out.println("Error - " + e);
                }
            } else if (task.equals("Show All Records")) {
                searchDesignation = "";
                searchHigherDesignation = "";
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
             noOfRowsInTable = designationHierarchyModel.getNoOfRows(searchDesignation, searchHigherDesignation);

            if (task.equals("Search") || task.equals("clear")) {

                lowerLimit = 0;
            }

            //noOfRowsInTable = designationHierarchyModel.getNoOfRows();                  // get the number of records (rows) in the table.
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
            List<DesignationHierarchyBean> list1 = designationHierarchyModel.showData(lowerLimit, noOfRowsToDisplay, searchDesignation, searchHigherDesignation);
            lowerLimit = lowerLimit + list1.size();
            noOfRowsTraversed = list1.size();

            // Logic to show data in the table.
      //      List< DesignationHierarchyBean> ComplaintTypeList = designationHierarchyModel.showData(lowerLimit, noOfRowsToDisplay);
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
            request.setAttribute("designationHierarchyList", list1);
   //}
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", designationHierarchyModel.getMessage());
            request.setAttribute("searchDesignation", searchDesignation);
            request.setAttribute("searchHigherDesignation", searchHigherDesignation);
            request.setAttribute("msgBgColor", designationHierarchyModel.getMsgBgColor());
            // Following request scoped attributes NAME will change from module to module.
       //     request.setAttribute("ComplaintTypeList", list1);
            designationHierarchyModel.closeConnection();
            request.getRequestDispatcher("designationHierarchyView").forward(request, response);
            //}

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
