/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.StarterMakerModel;
import com.survey.dataEntry.model.StarterTypeModel;
import com.survey.tableClasses.StarterMakerBean;
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

/**
 *
 * @author Navitus2
 */
public class StarterMakerController extends HttpServlet {

      @Override
    protected  void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{


      int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

          request.setCharacterEncoding("UTF-8");
         response.setContentType("text/html");

  try{

         System.out.println("this is Starter Maker Controller....");
        ServletContext ctx = getServletContext();
        StarterMakerModel starterMakerModel = new StarterMakerModel();
        starterMakerModel.setDriverClass(ctx.getInitParameter("driverClass"));
        starterMakerModel.setConnectionString(ctx.getInitParameter("connectionString"));
        starterMakerModel.setDb_username(ctx.getInitParameter("db_username"));
        starterMakerModel.setDb_password(ctx.getInitParameter("db_password"));
        starterMakerModel.setConnection();


         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {

                List<String> list = null;
                if (JQstring.equals("getStarterMake")) {

                    list = starterMakerModel.getStarterMake(q);
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
            System.out.println("\n Error --StarterMakerController get JQuery Parameters Part-" + e);
               return;
            }

        String task=request.getParameter("task");
        if(task==null){
            task="";
        }

        String searchstartermake=request.getParameter("searchstartermake");
        if(searchstartermake==null){
            searchstartermake="";
          }



        if(task.equals("Delete")){
             starterMakerModel.deleteRecord(Integer.parseInt(request.getParameter("starter_make_id")));
        }else if(task.equals("Save") || task.equals("Save AS New")){
             int starter_make_id;
            try {
                starter_make_id = Integer.parseInt(request.getParameter("starter_make_id"));
            } catch (Exception e) {
                starter_make_id = 0;
            }
            if (task.equals("Save AS New")) {
                starter_make_id = 0;
            }

             StarterMakerBean starter=new StarterMakerBean();
             starter.setStarter_make_id(starter_make_id);
             starter.setStarter_make(request.getParameter("starter_make"));
             starter.setRemark(request.getParameter("remark"));

              if (starter_make_id == 0) {
                System.out.println("Inserting values by StarterMakerModel......");
                starterMakerModel.insertRecord(starter);
            } else {
                System.out.println("Update values by StarterMakerModel........");
                starterMakerModel.updateRecord(starter);
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

        noOfRowsInTable = starterMakerModel.getNoOfRows(searchstartermake);
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
            searchstartermake = "";

        }

         List<StarterMakerBean> starterMakerList = starterMakerModel.showData(lowerLimit, noOfRowsToDisplay, searchstartermake);
        lowerLimit = lowerLimit + starterMakerList.size();
        noOfRowsTraversed = starterMakerList.size();

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("starterMakerList", starterMakerList);

          if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + starterMakerModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchstartermake", searchstartermake);
        request.setAttribute("message", starterMakerModel.getMessage());
        request.setAttribute("msgBgColor", starterMakerModel.getMsgBgColor());
        request.getRequestDispatcher("startermakerview").forward(request, response);

       }catch(Exception e){
           System.out.print("error in StarterMakerController"+e);

       }


}
      @Override
     protected  void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{

        this.doPost(request,response);
     }

}
