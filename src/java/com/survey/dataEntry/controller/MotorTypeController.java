/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.MotorTypeModel;
import com.survey.tableClasses.MotorTypeBean;
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
 * @author Shobha
 */
public class MotorTypeController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        MotorTypeModel motorTypeModel = new MotorTypeModel();
        motorTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        motorTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        motorTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        motorTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        motorTypeModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getMotorTypeName")) {
                    list = motorTypeModel.getMotorTypeName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                motorTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchTypeOfUse="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=motorTypeModel.showAllData(searchTypeOfUse);
                jrxmlFilePath = ctx.getRealPath("/report/newFuseReport.jrxml");
                byte[] reportInbytes = motorTypeModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Delete")) {
            motorTypeModel.deleteRecord(Integer.parseInt(request.getParameter("motor_type_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int motor_type_id;
            try {
                motor_type_id = Integer.parseInt(request.getParameter("motor_type_id"));
            } catch (Exception e) {
                motor_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                motor_type_id = 0;
            }
            MotorTypeBean motorTypeBean = new MotorTypeBean();
            motorTypeBean.setMotorTypeId(motor_type_id);
            motorTypeBean.setMotorTypeName(request.getParameter("motor_type_name"));
            motorTypeBean.setRemark(request.getParameter("remark"));
            if (motor_type_id == 0) {
                System.out.println("Inserting values by model......");
                motorTypeModel.insertRecord(motorTypeBean);
            } else {
                System.out.println("Update values by model........");
                motorTypeModel.updateRecord(motorTypeBean);
            }
        }
         // Start of Auto Completer code
        String searchMotorTypeName = "";

        // End of Auto Completer code
        searchMotorTypeName = request.getParameter("searchMotorTypeName");
         try {
            if (searchMotorTypeName == null) {
                searchMotorTypeName = "";
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
         System.out.println("searching.......... " + searchMotorTypeName);
         noOfRowsInTable = motorTypeModel.getNoOfRows(searchMotorTypeName);

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
            searchMotorTypeName = "";
        }
           // Logic to show data in the table.
        List<MotorTypeBean> MotorTypeList = motorTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchMotorTypeName);
        lowerLimit = lowerLimit + MotorTypeList.size();
        noOfRowsTraversed = MotorTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("MotorTypeList", MotorTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + motorTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchMotorTypeName", searchMotorTypeName);
        request.setAttribute("message", motorTypeModel.getMessage());
        request.setAttribute("msgBgColor", motorTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/motortype").forward(request, response);

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
