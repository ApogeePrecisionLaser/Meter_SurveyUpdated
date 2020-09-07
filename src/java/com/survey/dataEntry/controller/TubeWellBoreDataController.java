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
import com.survey.dataEntry.model.TubeWellBoreDataModel;
import com.survey.tableClasses.TubeWellBoreTypeBean;
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
public class TubeWellBoreDataController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        TubeWellBoreDataModel tubeWellBoreDataModel = new TubeWellBoreDataModel();
        tubeWellBoreDataModel.setDriverClass(ctx.getInitParameter("driverClass"));
        tubeWellBoreDataModel.setConnectionString(ctx.getInitParameter("connectionString"));
        tubeWellBoreDataModel.setDb_username(ctx.getInitParameter("db_username"));
        tubeWellBoreDataModel.setDb_password(ctx.getInitParameter("db_password"));
        tubeWellBoreDataModel.setConnection();

        String task = request.getParameter("task");
       
        String searchTubeWellName = request.getParameter("searchTubeWellName");
        String searchIvrsNo = request.getParameter("searchIvrsNo");
        try {
            if (searchTubeWellName == null || searchIvrsNo == null ) {
                searchTubeWellName = "";
                searchIvrsNo="";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTubeWellNameType")) {
                    list = tubeWellBoreDataModel.getTUbeWellName(q);
                }
                if (JQstring.equals("getIVRSNumber")) {
                    list = tubeWellBoreDataModel.getIVRS(q);
                }
                if (JQstring.equals("getUsedForName")) {
                    list = tubeWellBoreDataModel.getUsed_for_name(q);
                }
                if (JQstring.equals("getIvrsNo")) {
                    list = tubeWellBoreDataModel.getIvrsNo(q);
                }
                if (JQstring.equals("getWardName")) {
                    list = tubeWellBoreDataModel.getWardName(q);
                }
                if (JQstring.equals("getBoreCasingTypeName")) {
                    list = tubeWellBoreDataModel.getBoreCasingTypeName(q);
                }
                if (JQstring.equals("getMotorTypeName")) {
                    list = tubeWellBoreDataModel.getMotorTypeName(q);
                }
                if (JQstring.equals("getTubeWellName1")) {
                    String ivrs_number=request.getParameter("temp");
                    list = tubeWellBoreDataModel.getTUbeWellNameByIvrsNo(q,ivrs_number);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                tubeWellBoreDataModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
        if (task.equals("generateMapReport")) {
            
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = tubeWellBoreDataModel.showData(-1,-1, searchTubeWellName,searchIvrsNo);
            jrxmlFilePath = ctx.getRealPath("/report/tubeWellBoreDataDetail.jrxml");
            byte[] reportInbytes = tubeWellBoreDataModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        if (task.equals("Delete")) {
            tubeWellBoreDataModel.deleteRecord(Integer.parseInt(request.getParameter("tubewell_bore_data_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int tubewell_bore_data_id;
            try {
                tubewell_bore_data_id = Integer.parseInt(request.getParameter("tubewell_bore_data_id"));
            } catch (Exception e) {
                tubewell_bore_data_id = 0;
            }
            if (task.equals("Save AS New")) {
                tubewell_bore_data_id = 0;
            }
            TubeWellBoreTypeBean tubeWellBoreTypeBean = new TubeWellBoreTypeBean();
            //tubeWellBoreTypeBean.setIvrs_no(request.getParameter("ivrs_no"));
            tubeWellBoreTypeBean.setTubewell_bore_data_id(tubewell_bore_data_id);
            tubeWellBoreTypeBean.setTube_well_detail_id(tubeWellBoreDataModel.getTubeWellDetailId(request.getParameter("ivrs_no")));
            tubeWellBoreTypeBean.setWard_id(tubeWellBoreDataModel.getWardId(request.getParameter("ward_no")));
            //tubeWellBoreTypeBean.setTube_well_rev_no(tubeWellBoreDataModel.getTubeWellRevNo(request.getParameter("tubewell_name")));
            tubeWellBoreTypeBean.setDepth(request.getParameter("bore_depth"));
            tubeWellBoreTypeBean.setBore_diameter(request.getParameter("bore_diameter"));
            tubeWellBoreTypeBean.setBore_casing_type(request.getParameter("bore_casing_type"));
            tubeWellBoreTypeBean.setMotore_capacity(request.getParameter("motor_capacity"));
            tubeWellBoreTypeBean.setSuction_diameter(request.getParameter("suction_diameter"));
            tubeWellBoreTypeBean.setDelivery_diameter(request.getParameter("delivery_diameter"));
            tubeWellBoreTypeBean.setDischarge_capacity(request.getParameter("discharge_capacity"));
            tubeWellBoreTypeBean.setMotor_type(request.getParameter("motor_type"));
            tubeWellBoreTypeBean.setDate_of_installation(request.getParameter("date_of_installation"));

            tubeWellBoreTypeBean.setContact_person_name(request.getParameter("near_by_contact_person_name"));
            tubeWellBoreTypeBean.setContact_person_address(request.getParameter("near_by_contact_person_address"));
            tubeWellBoreTypeBean.setContact_person_mobile_no(request.getParameter("near_by_contact_person_mobile_no"));
            tubeWellBoreTypeBean.setOperated_by(request.getParameter("operated_by"));
            tubeWellBoreTypeBean.setUsed_for(request.getParameter("used_for"));
            tubeWellBoreTypeBean.setOperator_name(request.getParameter("operator_name"));
            tubeWellBoreTypeBean.setOperator_mobile_no(request.getParameter("operator_mobile_no"));
            tubeWellBoreTypeBean.setRemark(request.getParameter("remark"));


            if (tubewell_bore_data_id == 0) {
                System.out.println("Inserting values by model......");
                tubeWellBoreDataModel.insertRecord(tubeWellBoreTypeBean);
            } else {
                System.out.println("Update values by model........");
                tubeWellBoreDataModel.updateRecord(tubeWellBoreTypeBean);
            }
        }
        // Start of Auto Completer code
        
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
        System.out.println("searching.......... " + searchTubeWellName);
        noOfRowsInTable = tubeWellBoreDataModel.getNoOfRows(searchTubeWellName,searchIvrsNo);

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
            searchTubeWellName = "";
            searchIvrsNo="";
        }
        // Logic to show data in the table.
        List<TubeWellBoreTypeBean> tubeWellBoreDataList = tubeWellBoreDataModel.showData(lowerLimit, noOfRowsToDisplay, searchTubeWellName,searchIvrsNo);
        lowerLimit = lowerLimit + tubeWellBoreDataList.size();
        noOfRowsTraversed = tubeWellBoreDataList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("tubeWellBoreDataList", tubeWellBoreDataList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        // End of Search Table
        System.out.println("color is :" + tubeWellBoreDataModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchTubeWellName", searchTubeWellName);
        request.setAttribute("searchIvrsNo", searchIvrsNo);
        request.setAttribute("message", tubeWellBoreDataModel.getMessage());
        request.setAttribute("msgBgColor", tubeWellBoreDataModel.getMsgBgColor());
        request.getRequestDispatcher("/TubeWellBoreDataView").forward(request, response);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
