/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.general.controller;

import com.survey.util.UniqueIDGenerator;
import com.survey.energyMeter.general.model.HealthStatusModel;
import com.survey.energyMeter.general.tableClasses.HealthStatusBean;
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
 * @author jpss
 */
public class HealthStatusController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is HealthStatus Controller....");
        ServletContext ctx = getServletContext();
        HealthStatusModel healthStatusModel = new HealthStatusModel();
        healthStatusModel .setDriverClass(ctx.getInitParameter("driverClass"));
        healthStatusModel.setDb_username(ctx.getInitParameter("db_username"));
        healthStatusModel.setDb_password(ctx.getInitParameter("db_password"));
        healthStatusModel.setConnectionString(ctx.getInitParameter("connectionString"));
        healthStatusModel.setConnection();

        String task = request.getParameter("task");
        String searchHealthStatus= request.getParameter("searchHealthStatus");


        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getHealthStatus")) {
                    list = healthStatusModel.getHealthStatus(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                healthStatusModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }
        if (task.equals("generateHSReport")) {
                        if ( searchHealthStatus == null) {
                            searchHealthStatus = "";
                        }
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/HealthStatusReport.jrxml");
                        if(searchHealthStatus.equals("")){
                            list=healthStatusModel.showAllData();
                        }else
                        list=healthStatusModel.showData(lowerLimit,noOfRowsToDisplay,searchHealthStatus);
                        byte[] reportInbytes =healthStatusModel.generateRecordList(jrxmlFilePath, list, searchHealthStatus);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        healthStatusModel.closeConnection();
                        return;
            }
        if (task.equals("Delete")) {
           healthStatusModel.deleteRecord(Integer.parseInt(request.getParameter("health_status_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Add All Records") || task.equals("Update")) {
            int health_status_id;
            try {
                health_status_id = Integer.parseInt(request.getParameter("health_status_id"));
            } catch (Exception e) {
                health_status_id = 0;
            }
            HealthStatusBean healthStatuseBean= new HealthStatusBean();
            if (task.equals("Save AS New")) {
                            health_status_id = 0;
                            String [] id = new String[1];
                            String hs_id = "1";
                            id[0] = hs_id;
                            healthStatuseBean.setHealth_status_idList(id);
                        }else{healthStatuseBean.setHealth_status_idList(request.getParameterValues("health_status_id"));}

                        healthStatuseBean.setHealth_statusList(request.getParameterValues("health_status"));
                        healthStatuseBean.setHealth_status_valueList(request.getParameterValues("health_status_value"));
                        healthStatuseBean.setRemarkList(request.getParameterValues("remark"));
                        if (task.equals("Add All Records")) {
                          healthStatuseBean.setHealth_status_idList(request.getParameterValues("health_status_id"));
                        }
                        if (health_status_id == 0 || task.equals("Add All Records")) {
                            // if health_status_id was not provided, that means insert new record.
                            healthStatusModel.insertMultipleRecord(healthStatuseBean);
                        } else if(task.equals("Update")){
                            // update existing record.
                            healthStatuseBean.setHealth_status_id(health_status_id);
                            healthStatusModel.updateRecord(healthStatuseBean);
                        }

        }

        try {
            if ( searchHealthStatus == null) {
                searchHealthStatus = "";
            }
        } catch (Exception e) {
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
        System.out.println("searching.......... " +  searchHealthStatus);
        noOfRowsInTable = healthStatusModel.getNoOfRows( searchHealthStatus);                  // get the number of records (rows) in the table.

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

        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Update")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
             searchHealthStatus = "";
        }

        List<HealthStatusBean> healthStatusList = healthStatusModel.showData(lowerLimit,noOfRowsToDisplay,searchHealthStatus);
        lowerLimit = lowerLimit + healthStatusList.size();
        noOfRowsTraversed = healthStatusList.size();

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
        request.setAttribute("healthStatusList", healthStatusList);
        request.setAttribute("searchHealthStatus", searchHealthStatus);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", healthStatusModel.getMessage());
        request.setAttribute("msgBgColor", healthStatusModel.getMsgBgColor());
        request.getRequestDispatcher("healthStatusView").forward(request, response);
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
