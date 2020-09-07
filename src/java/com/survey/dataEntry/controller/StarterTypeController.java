/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.StarterTypeModel;
import com.survey.tableClasses.StarterTypeBean;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Navitus2
 */
public class StarterTypeController extends HttpServlet {

      @Override
    protected  void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{


      int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

          request.setCharacterEncoding("UTF-8");
         response.setContentType("text/html");


  try{
         System.out.println("this is Starter TYPE Controller....");
        ServletContext ctx = getServletContext();
        StarterTypeModel starterTypeModel = new StarterTypeModel();
        starterTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        starterTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        starterTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        starterTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        starterTypeModel.setConnection();


         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {

                List<String> list = null;
                if (JQstring.equals("getStarterType")) {

                    list = starterTypeModel.getStarterType(q);
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
            System.out.println("\n Error --StarterTypeController get JQuery Parameters Part-" + e);
               return;
            }

        String task=request.getParameter("task");
        if(task==null){
            task="";
        }

        String searchstartertype=request.getParameter("searchstartertype");
        if(searchstartertype==null){
            searchstartertype="";
          }



        if(task.equals("Delete")){
             starterTypeModel.deleteRecord(Integer.parseInt(request.getParameter("starter_id")));
        }else if(task.equals("Save") || task.equals("Save AS New")){
             int starter_type_id;
            try {
                starter_type_id = Integer.parseInt(request.getParameter("starter_id"));
            } catch (Exception e) {
                starter_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                starter_type_id = 0;
            }

             StarterTypeBean starter=new StarterTypeBean();
             starter.setStarter_id(starter_type_id);
             starter.setStarter_type(request.getParameter("starter_type"));
             starter.setRemark(request.getParameter("remark"));

              if (starter_type_id == 0) {
                System.out.println("Inserting values by StarterTypeModel......");
                starterTypeModel.insertRecord(starter);
            } else {
                System.out.println("Update values by StarterTypeModel........");
                starterTypeModel.updateRecord(starter);
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

        noOfRowsInTable = starterTypeModel.getNoOfRows(searchstartertype);
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
            searchstartertype = "";

        }

         List<StarterTypeBean> starterTypeList = starterTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchstartertype);
        lowerLimit = lowerLimit + starterTypeList.size();
        noOfRowsTraversed = starterTypeList.size();

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("starterTypeList", starterTypeList);

          if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + starterTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchstartertype", searchstartertype);
        request.setAttribute("message", starterTypeModel.getMessage());
        request.setAttribute("msgBgColor", starterTypeModel.getMsgBgColor());
        request.getRequestDispatcher("startertypeview").forward(request, response);
          }catch(Exception e){
              System.out.print("error in StarterTypeController"+e);
          }


}
      @Override
     protected  void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{

        this.doPost(request,response);
     }

}
