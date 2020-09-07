/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.MISModel;
import com.survey.tableClasses.MISTypeBean;
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
public class MISController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Mccb Controller....");
        ServletContext ctx = getServletContext();
        MISModel misModel = new MISModel();
        misModel.setDriverClass(ctx.getInitParameter("driverClass"));
        misModel.setConnectionString(ctx.getInitParameter("connectionString"));
        misModel.setDb_username(ctx.getInitParameter("db_username"));
        misModel.setDb_password(ctx.getInitParameter("db_password"));
        misModel.setConnection();

        String task = request.getParameter("task");
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getMccbType")) {
                    list = misModel.getMccbType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                misModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
        if (task.equals("generateReport")) {
            String searchFuseType = "";
            List listAll = null;
            String jrxmlFilePath="";
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            if (request.getParameter("report_name").equals("phaseReport")) {
                listAll = misModel.showData();
                jrxmlFilePath = ctx.getRealPath("/report/phaseImbalanceFault.jrxml");
            } else if (request.getParameter("report_name").equals("consumption_faults")) {
                listAll = misModel.showProjectedConsumptionData();
                jrxmlFilePath = ctx.getRealPath("/report/consumptionFault.jrxml");
            } else if (request.getParameter("report_name").equals("final_faults")) {
                listAll = misModel.showSurveyConsumptionData();
                jrxmlFilePath = ctx.getRealPath("/report/consumptionFault.jrxml");
            }

            byte[] reportInbytes = misModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
//        if (task.equals("generateReport")) {
//            String searchFuseType = "";
//            List listAll = null;
//            String jrxmlFilePath;
//            response.setContentType("application/pdf");
//            ServletOutputStream servletOutputStream = response.getOutputStream();
//            if (request.getParameter("report_name").equals("")) {
//            }
//            listAll = misModel.showData();
//            jrxmlFilePath = ctx.getRealPath("/report/phaseImbalanceFault.jrxml");
//            byte[] reportInbytes = misModel.generateMapReport(jrxmlFilePath, listAll);
//            response.setContentLength(reportInbytes.length);
//            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//            servletOutputStream.flush();
//            servletOutputStream.close();
//            return;
//        }
        if (task.equals("Delete")) {
            misModel.deleteRecord(Integer.parseInt(request.getParameter("mccb_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int mccb_id;
            try {
                mccb_id = Integer.parseInt(request.getParameter("mccb_id"));
            } catch (Exception e) {
                mccb_id = 0;
            }
            if (task.equals("Save AS New")) {
                mccb_id = 0;
            }
            MISTypeBean misTypeBean = new MISTypeBean();
//            misTypeBean.setMccb_id(mccb_id);
//            misTypeBean.setMccb_type(request.getParameter("mccb_type"));
//            misTypeBean.setRemark(request.getParameter("remark"));
            if (mccb_id == 0) {
                System.out.println("Inserting values by model......");
                //   misModel.insertRecord(misTypeBean);
            } else {
                System.out.println("Update values by model........");
                // misModel.updateRecord(misTypeBean);
            }
        }
        // Start of Auto Completer code
        String searchMccbType = "";

        // End of Auto Completer code
        searchMccbType = request.getParameter("searchMccbType");
        try {
            if (searchMccbType == null) {
                searchMccbType = "";
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
        System.out.println("searching.......... " + searchMccbType);
        noOfRowsInTable = misModel.getNoOfRows(searchMccbType);

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
            searchMccbType = "";
        }
        // Logic to show data in the table.

        List<MISTypeBean> misTypeList = misModel.showData();
        //  misTypeList = misModel.showData(lowerLimit, noOfRowsToDisplay, task);
        List<MISTypeBean> consumptionTypeList = null;
        List<MISTypeBean> surveyConsumptionTypeList = null;
        if (task.equals(" ") || task.equals("Consumption Faults")) {
            consumptionTypeList = misModel.showProjectedConsumptionData();
            request.setAttribute("consumptionTypeList", consumptionTypeList);
            request.getRequestDispatcher("/consumptionDetailView").forward(request, response);
            misModel.closeConnection();
            return;

        }
        if (task.equals(" ") || task.equals("Faults")) {
            surveyConsumptionTypeList = misModel.showSurveyConsumptionData();
            request.setAttribute("surveyConsumptionTypeList", surveyConsumptionTypeList);
            request.getRequestDispatcher("/faultsDetailView").forward(request, response);
            misModel.closeConnection();
            return;
        }

        lowerLimit = lowerLimit + misTypeList.size();
        noOfRowsTraversed = misTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
//        request.setAttribute("misTypeList", misTypeList);
//         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
//            request.setAttribute("showFirst", "false");
//            request.setAttribute("showPrevious", "false");
//        }
//        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
//            request.setAttribute("showNext", "false");
//            request.setAttribute("showLast", "false");
//        }
        // End of Search Table
        // System.out.println("color is :" + misModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("misTypeList", misTypeList);


        request.setAttribute("searchMccbType", searchMccbType);
        request.setAttribute("yearlist", misModel.getyearLst());
        request.setAttribute("message", misModel.getMessage());
        request.setAttribute("msgBgColor", misModel.getMsgBgColor());
        request.getRequestDispatcher("/misView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
