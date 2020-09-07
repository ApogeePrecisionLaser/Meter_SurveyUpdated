/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.SwitchingTypeModel;
import com.survey.tableClasses.SwitchingTypeBean;
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
 * @author Navitus2
 */
public class SwitchingTypeController extends HttpServlet{
 @Override
    protected  void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{


      int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

          request.setCharacterEncoding("UTF-8");
         response.setContentType("text/html");

  try{

         System.out.println("this is Switching Type Controller....");
        ServletContext ctx = getServletContext();
        SwitchingTypeModel switchingTypeModel = new SwitchingTypeModel();
        switchingTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        switchingTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        switchingTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        switchingTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        switchingTypeModel.setConnection();


         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {

                List<String> list = null;
                if (JQstring.equals("getSwitchType")) {

                    list = switchingTypeModel.getSwitchType(q);
                }
                Iterator<String> iter = list.iterator();
                 PrintWriter out = response.getWriter();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                return;
            }
             } catch (Exception e) {
            System.out.println("\n Error --SwitchingTypeController get JQuery Parameters Part-" + e);
               return;
            }

        String task=request.getParameter("task");
        if(task==null){
            task="";
        }

        String searchswitchtype=request.getParameter("searchswitchtype");
        if(searchswitchtype==null){
            searchswitchtype="";
          }



        if(task.equals("Delete")){
             switchingTypeModel.deleteRecord(Integer.parseInt(request.getParameter("switch_type_id")));
        }else if(task.equals("Save") || task.equals("Save AS New")){
             int switch_type_id;
            try {
                switch_type_id = Integer.parseInt(request.getParameter("switch_type_id"));
            } catch (Exception e) {
                switch_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                switch_type_id = 0;
            }

             SwitchingTypeBean switching=new SwitchingTypeBean();
             switching.setSwitch_type_id(switch_type_id);
             switching.setSwitch_type(request.getParameter("switch_type"));
             switching.setRemark(request.getParameter("remark"));

              if (switch_type_id == 0) {
                System.out.println("Inserting values by AreaModel......");
                switchingTypeModel.insertRecord(switching);
            } else {
                System.out.println("Update values by AreaModel........");
                switchingTypeModel.updateRecord(switching);
            }
        }
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

        noOfRowsInTable = switchingTypeModel.getNoOfRows(searchswitchtype);
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
        }else if (task.equals("Show All Records")) {
            searchswitchtype = "";

        }

         List<SwitchingTypeBean> switchTypeList = switchingTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchswitchtype);
        lowerLimit = lowerLimit + switchTypeList.size();
        noOfRowsTraversed = switchTypeList.size();

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("switchTypeList", switchTypeList);

          if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + switchingTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchswitchtype", searchswitchtype);
        request.setAttribute("message", switchingTypeModel.getMessage());
        request.setAttribute("msgBgColor", switchingTypeModel.getMsgBgColor());
        request.getRequestDispatcher("switchingtypeview").forward(request, response);

     }catch(Exception e){
         System.out.print("error in SwitchingTypeController"+e);
     }

}
      @Override
     protected  void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{

        this.doPost(request,response);
     }

}

