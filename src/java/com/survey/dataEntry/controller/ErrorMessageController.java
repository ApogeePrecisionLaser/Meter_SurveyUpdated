/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.ErrorMessageModel;
import com.survey.tableClasses.ErrorMessageBean;
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
public class ErrorMessageController extends HttpServlet {
   
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
       
        ErrorMessageModel errorMessageModel = new ErrorMessageModel();
        ErrorMessageBean errorMessageBean  = new ErrorMessageBean();
        try {
          int lowerLimit=0, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is CONTACTER Controller....");
        ServletContext ctx = getServletContext();
        errorMessageModel.setDriverClass(ctx.getInitParameter("driverClass"));
        errorMessageModel.setConnectionString(ctx.getInitParameter("connectionString"));
        errorMessageModel.setDb_username(ctx.getInitParameter("db_username"));
        errorMessageModel.setDb_password(ctx.getInitParameter("db_password"));
        errorMessageModel.setConnection();
        String jQuery=request.getParameter("action1");
        String q=request.getParameter("q");
              try
              {
               if(jQuery!=null)
               {
                    PrintWriter out = response.getWriter();
                    List<String> list=null;
                    if(jQuery.equals("searchMessage"))
                    {
                        list=errorMessageModel.getAutoSearch(q);
                    }
                    Iterator<String> i=list.iterator();
                    while(i.hasNext())
                    {
                        out.println(i.next());
                    }
                 return;
               }
            }catch(Exception e)
              {
                  System.out.println("Error : -" +e);
              }
            String buttonAction=request.getParameter("buttonAction");
            String searchMessage=request.getParameter("searchMessage");
            
            String task=request.getParameter("task");

            if(task==null)
            {
                task="";
            }
            if(searchMessage==null)
            {
                searchMessage="";
            }
             if(buttonAction==null)
             {
                 buttonAction="noaction";
             }

            if(task.equals("Save") || task.equals("Delete") || task.equals("Save As New"))
            {
                String message_id=request.getParameter("message_id");
                int messageid=0;
                if(message_id!=null)
                {
                    messageid=Integer.parseInt(message_id);
                }else{message_id="";}
            String message=request.getParameter("message");
            String remark=request.getParameter("remark");
            String createdBy=request.getParameter("created");
            String active=request.getParameter("active");
            if(message==null){message="";}
            if(remark==null){remark="";}
            if(createdBy==null){createdBy="";}
            if(active==null){active="";}
            errorMessageBean.setActive(active);
            errorMessageBean.setCreatedBy(createdBy);
            errorMessageBean.setMessage(message);
            errorMessageBean.setRemark(remark);
            errorMessageBean.setError_message_id(messageid);
            if(task.equals("Delete"))
            {
              errorMessageModel.getDelete(messageid);
            }
             else{
            if(task.equals("Save As New"))
            {
              errorMessageModel.getNewInsert(errorMessageBean);
            }else
            {
                if(task.equals("Save"))
                {
                errorMessageModel.getUpdated(errorMessageBean);
                }
            }
             }

            }
                if(task.equals("Add All Records"))
                   {
                    errorMessageBean.setMessage_id(request.getParameterValues("message_id"));
                    errorMessageBean.setActive1(request.getParameterValues("active"));
                  errorMessageBean.setCretedBy1(request.getParameterValues("created"));
                 errorMessageBean.setMessage1(request.getParameterValues("message"));
                 errorMessageBean.setRemark1(request.getParameterValues("remark"));
                errorMessageModel.getInsert(errorMessageBean);
                  }                     
            
         try {
            lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
              }       catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
                                       }
            if(task.equals("Show All Data"))
            {
                lowerLimit=0;
            }
         noOfRowsInTable=errorMessageModel.getNumberOfRow(searchMessage);
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

        List list=errorMessageModel.showAllData(lowerLimit,noOfRowsToDisplay,searchMessage);
        lowerLimit = lowerLimit + list.size();
        noOfRowsTraversed = list.size();
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("message", errorMessageModel.getMessage());
        request.setAttribute("serachMessage", searchMessage);
        request.setAttribute("messageList", list);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
       request.getRequestDispatcher("errorMessage").forward(request, response);
         } finally {
           
        }
    }     // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
