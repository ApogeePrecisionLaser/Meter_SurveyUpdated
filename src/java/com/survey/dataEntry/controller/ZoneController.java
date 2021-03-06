/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.dataEntry.controller;

/**
 *
 * @author Administrator
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



import com.survey.dataEntry.model.ZoneModel;
import com.survey.tableClasses.ZoneTypeBean;
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
 * @author JPSS
 */
public class   ZoneController extends HttpServlet{
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
     int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        ZoneModel zoneModel = new ZoneModel();
        zoneModel.setDriverClass(ctx.getInitParameter("driverClass"));
        zoneModel.setConnectionString(ctx.getInitParameter("connectionString"));
        zoneModel.setDb_username(ctx.getInitParameter("db_username"));
        zoneModel.setDb_password(ctx.getInitParameter("db_password"));
        zoneModel.setConnection();

        String task = request.getParameter("task");
         try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getZoneType")) {
                    list = zoneModel.getZoneType(q);
                }
                else if(JQstring.equals("getDivisionName")) {
                    list = zoneModel.getDivisionType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                zoneModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
         if (task == null) {
            task = "";
        }
          if(task.equals("generateMapReport")){
                String searchZoneType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=zoneModel.showAllData(searchZoneType);
                jrxmlFilePath = ctx.getRealPath("/report/zone_m.jrxml");
                byte[] reportInbytes = zoneModel.generateMapReport(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
         if (task.equals("Delete")) {
            zoneModel.deleteRecord(Integer.parseInt(request.getParameter("zone_id_m")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int zone_id_m;
            try {
                zone_id_m = Integer.parseInt(request.getParameter("zone_id_m"));
            } catch (Exception e) {
                zone_id_m = 0;
            }
            if (task.equals("Save AS New")) {
                zone_id_m = 0;
            }
            ZoneTypeBean zoneTypeBean = new ZoneTypeBean();
            zoneTypeBean.setZone_id_m(zone_id_m);
            //zoneTypeBean.setDivision_id_m(zoneModel.getDivisonId(request.getParameter("division_name_m")));
            zoneTypeBean.setZone_m(request.getParameter("zone_m"));
            zoneTypeBean.setDescription(request.getParameter("description"));
            if (zone_id_m == 0) {
                System.out.println("Inserting values by model......");
                zoneModel.insertRecord(zoneTypeBean);
            } else {
                System.out.println("Update values by model........");
                zoneModel.updateRecord(zoneTypeBean);
            }
        }
         // Start of Auto Completer code
        String searchZoneType = "";

        // End of Auto Completer code
        searchZoneType = request.getParameter("searchZoneType");
         try {
            if (searchZoneType == null) {
                searchZoneType = "";
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
         System.out.println("searching.......... " + searchZoneType);
         noOfRowsInTable = zoneModel.getNoOfRows(searchZoneType);

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
            searchZoneType = "";
        }
           // Logic to show data in the table.
        List<ZoneTypeBean> zoneTypeList = zoneModel.showData(lowerLimit, noOfRowsToDisplay, searchZoneType);
        lowerLimit = lowerLimit + zoneTypeList.size();
        noOfRowsTraversed = zoneTypeList.size();
         // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("zoneTypeList", zoneTypeList);
         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
         // End of Search Table
        System.out.println("color is :" + zoneModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchZoneType", searchZoneType);
        request.setAttribute("message", zoneModel.getMessage());
        request.setAttribute("msgBgColor", zoneModel.getMsgBgColor());
        request.getRequestDispatcher("/zoneView").forward(request, response);



    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException{
            doGet(request, response);
    }

}



