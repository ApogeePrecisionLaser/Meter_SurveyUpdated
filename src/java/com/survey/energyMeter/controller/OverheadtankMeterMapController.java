/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.controller;

import com.survey.dbcon.DBConnection;
import com.survey.energyMeter.model.OverheadtankMeterMapModel;
import com.survey.energyMeter.tableClasses.OverheadtankMeterMapBean;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class OverheadtankMeterMapController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try{
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;
        int overhead_tankmeter_id=0;
        System.out.println("this is HealthStatusMap Controller....");
        ServletContext ctx = getServletContext();
        OverheadtankMeterMapModel healthStatusMapModel = new OverheadtankMeterMapModel();
        healthStatusMapModel.setDriverClass(ctx.getInitParameter("driverClass"));
        healthStatusMapModel.setConnectionString(ctx.getInitParameter("connectionString"));
        healthStatusMapModel.setDb_username(ctx.getInitParameter("db_username"));
        healthStatusMapModel.setDb_password(ctx.getInitParameter("db_password"));
    try{
        healthStatusMapModel.setConnection(DBConnection.getConnection(ctx));
       // healthStatusMapModel.connection=DBConnection.getConnection(ctx);
        }catch(Exception e){
         System.out.println("Error in OverheadtankMeterMapController "+e);
        }
//        Date now = new Date();
//        SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
//        String currentDate = df1.format(now);
        PrintWriter out = response.getWriter();
          OverheadtankMeterMapBean itemNameBean=new OverheadtankMeterMapBean();
        //OverheadtankMeterMapModel itemNameModel=new OverheadtankMeterMapModel();
        String task = request.getParameter("task");


        response.setContentType("text/html;charset=UTF-8");
        String overheadtank_name=request.getParameter("overheadtank_name");
        String meter_name=request.getParameter("meter_name");
          String remark=request.getParameter("remark");
          String searchOverheadtank_Name=request.getParameter("searchoverheadtank_name");  if(searchOverheadtank_Name==null)searchOverheadtank_Name="";
          String searchMeter_Name=request.getParameter("searchmeter_name");  if(searchMeter_Name==null)searchMeter_Name="";
          String overheadtankmetermap_id=request.getParameter("overheadtankmetermap_id");
          if(overheadtank_name==null)overheadtank_name="";
          if(meter_name==null)meter_name="";
          if(remark==null)remark="";
          if(overheadtankmetermap_id==null || overheadtankmetermap_id.isEmpty())overhead_tankmeter_id=0;
          else overhead_tankmeter_id=Integer.parseInt(overheadtankmetermap_id);
          itemNameBean.setOverheadtank_metermap_id(overheadtankmetermap_id);
          itemNameBean.setOverheadtank_name(overheadtank_name);
          itemNameBean.setMeter_name(meter_name);
          itemNameBean.setRemark(remark);
          //itemNameBean.setItem_name_id(itemNameId);

          String JQuery=request.getParameter("action1");
            String q=request.getParameter("q");
               if(JQuery!=null)
                {
                   List list=null;
                    if(JQuery.equals("getOverheadTankName"))
                    {
                        list=healthStatusMapModel.getOverheadTankName(q);
                    }
                   if(JQuery.equals("getMeterName"))
                    {
                        list=healthStatusMapModel.getMeterName(q);
                    }

                   if(JQuery.equals("getSearchOverheadTankName"))
                    {
                        list=healthStatusMapModel.getSearchOverheadTankName(q);
                    }
                   if(JQuery.equals("getSearchMeterName"))
                    {
                        list=healthStatusMapModel.getSearchMeterName(q);
                    }
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext())
                    {
                        String data = iter.next();
                        out.println(data);
                    }
                    return;
                }

         //int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 6, noOfRowsInTable;
         //String task=request.getParameter("task");
         if(task==null)
                task="";
         if(task.equals("Save"))
         {
            healthStatusMapModel.insertRecord(itemNameBean);
         }
         if(task.equals("Save AS New"))
           {
             if(healthStatusMapModel.insertRecord(itemNameBean)){
                 System.out.println("record saved successfully");
             }else{
               System.out.print("record not saved");
             }
           }
        if(task.equals("Delete"))
           {
               if(healthStatusMapModel.deleteRecord(itemNameBean)){
                   System.out.print("record deleted");
                    } else{
                    System.out.print("record not deleted");
                    }
           }
         if(task.equals("Update"))
           {
               if(healthStatusMapModel.UpdateRecord(itemNameBean)){
                   System.out.print("record deleted");
                    } else{
                    System.out.print("record not deleted");
                    }
           }
         if(task.equals("Show All Records"))
           {
            searchOverheadtank_Name="";
            searchMeter_Name="";
           }
            String buttonAction = request.getParameter("buttonAction");
         if(buttonAction == null)
                 buttonAction = "none";
              try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e)
            {
                lowerLimit = noOfRowsTraversed = 0;
            }
        noOfRowsInTable = healthStatusMapModel.getNoOfRows(searchOverheadtank_Name,searchMeter_Name);
        if (buttonAction.equals("Next"));
        else if (buttonAction.equals("Previous"))
            {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0)
                {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else
                {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First"))
            {
                lowerLimit = 0;
            }
            else if (buttonAction.equals("Last"))
            {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }
            List<OverheadtankMeterMapBean> list=healthStatusMapModel.ShowData(lowerLimit,noOfRowsToDisplay,searchOverheadtank_Name,searchMeter_Name);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable)
            {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
        request.setAttribute("list", list);
        request.setAttribute("message", healthStatusMapModel.getMessage());
            request.setAttribute("msgBgColor", healthStatusMapModel.getMsgBgColor());
        request.setAttribute("searchOverheadtank_name", searchOverheadtank_Name);
        request.setAttribute("searchMeter_name", searchMeter_Name);
         request.setAttribute("lowerLimit",lowerLimit);
        request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        // request.setAttribute("searchcity", searchcity);
            RequestDispatcher rd=request.getRequestDispatcher("ohtmeter_map");
            rd.forward(request, response);

        }catch(Exception e){
            System.out.println("Error in OverheadTankMeterMapController "+e);
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
