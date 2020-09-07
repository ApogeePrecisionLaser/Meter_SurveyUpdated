/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.EnclosureModel;
import com.survey.tableClasses.EnclosureTypeBean;
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
public class EnclosureTypeController extends HttpServlet{
 @Override
    protected  void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{


      int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 5, noOfRowsInTable;

          request.setCharacterEncoding("UTF-8");
         response.setContentType("text/html");

  try{

         System.out.println("this is Enclosure TYPE Controller....");
        ServletContext ctx = getServletContext();
        EnclosureModel enclosureTypeModel = new EnclosureModel();
        enclosureTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        enclosureTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        enclosureTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        enclosureTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        enclosureTypeModel.setConnection();


         try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {

                List<String> list = null;
                if (JQstring.equals("getEnclosureType")) {

                    list = enclosureTypeModel.getEnclosureType(q);
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
            System.out.println("\n Error --EnclosureTypeController get JQuery Parameters Part-" + e);
               return;
            }

        String task=request.getParameter("task");
        if(task==null){
            task="";
        }

        String searchenclosure=request.getParameter("searchenclosure");
        if(searchenclosure==null){
            searchenclosure="";
          }



        if(task.equals("Delete")){
             enclosureTypeModel.deleteRecord(Integer.parseInt(request.getParameter("enclosure_type_id")));
        }else if(task.equals("Save") || task.equals("Save AS New")){
             int enclosure_type_id;
            try {
                enclosure_type_id = Integer.parseInt(request.getParameter("enclosure_type_id"));
            } catch (Exception e) {
                enclosure_type_id = 0;
            }
            if (task.equals("Save AS New")) {
                enclosure_type_id = 0;
            }

             EnclosureTypeBean enclosure=new EnclosureTypeBean();
             enclosure.setEnclosure_type_id(enclosure_type_id);
             enclosure.setEnclosure_type(request.getParameter("enclosure_type"));
             enclosure.setRemark(request.getParameter("remark"));

              if (enclosure_type_id == 0) {
                System.out.println("Inserting values by EnclosureTypeModel......");
                enclosureTypeModel.insertRecord(enclosure);
            } else {
                System.out.println("Update values by EnclosureTypeModel........");
                enclosureTypeModel.updateRecord(enclosure);
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

        noOfRowsInTable = enclosureTypeModel.getNoOfRows(searchenclosure);
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
            searchenclosure = "";

        }

         List<EnclosureTypeBean> enclosureTypeList = enclosureTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchenclosure);
        lowerLimit = lowerLimit + enclosureTypeList.size();
        noOfRowsTraversed = enclosureTypeList.size();

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("enclosureTypeList", enclosureTypeList);

          if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }

        System.out.println("color is :" + enclosureTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchenclosure", searchenclosure);
        request.setAttribute("message", enclosureTypeModel.getMessage());
        request.setAttribute("msgBgColor", enclosureTypeModel.getMsgBgColor());
        request.getRequestDispatcher("enclosuretypeview").forward(request, response);
  } catch (Exception ex) {
       System.out.print("error in EnclosureTypeController"+ex);
   }


}
      @Override
     protected  void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException{

        this.doPost(request,response);
     }

}

