/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.PoleDetailTypeModel;
import com.survey.tableClasses.PoleDetailTypeBean;
import com.survey.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
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
public class PoleDetailTypeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        String view = "poleDetail_View";
        System.out.println("this is POLE TYPE Controller....");
        ServletContext ctx = getServletContext();
        PoleDetailTypeModel poleTypeModel = new PoleDetailTypeModel();
        PoleDetailTypeBean bean = new PoleDetailTypeBean();
        poleTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        poleTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        poleTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        poleTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        poleTypeModel.setConnection();
        String task = request.getParameter("task");
        String mountingType = "", feeder_name = "";

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getPoleType")) {
                    list = poleTypeModel.getPoleType(q);
                }  else if (JQstring.equals("getSwitchingPointName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        feeder_name = request.getParameter("action2");
                    }
                    list = poleTypeModel.getSwitchingPointName(q, feeder_name);
                } else if (JQstring.equals("getMountingType")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        mountingType = request.getParameter("action2");
                    }
                    list = poleTypeModel.getMountingType(q);
                } else if (JQstring.equals("getSwitchingFeederName")) {

                    list = poleTypeModel.getSwitchingFeederName(q);
                } else if (JQstring.equals("getSearchSwitchingPointName")) {
                    list = poleTypeModel.getSearchSwitchingPointName(q);
                } else if (JQstring.equals("getSourceWattageType")) {
                    list = poleTypeModel.getSourceWattageType(q);
                } else if (JQstring.equals("getAreaName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        list = poleTypeModel.getAreaName(q, request.getParameter("action2"));
                    }
                } else if (JQstring.equals("getPoleNo")) {
                    // if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                    list = poleTypeModel.getSearchPoleNo(q, request.getParameter("action2"));
                    //  }
                } else if (JQstring.equals("getWard_No")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        list = poleTypeModel.getWard_No(q, request.getParameter("action2").trim());
                    }
                } else if (JQstring.equals("getRoadUse")) {
                    list = poleTypeModel.getRoadUse(q);
                } else if (JQstring.equals("getRoad_Category")) {
                    list = poleTypeModel.getRoad_Category(q);
                } else if (JQstring.equals("getRoadName")) {
                    if ((request.getParameter("action2") != null || request.getParameter("action3") != null) && (!request.getParameter("action2").isEmpty() || !request.getParameter("action3").isEmpty())) {
                        list = poleTypeModel.getRoadName(q, request.getParameter("action2").trim(), request.getParameter("action3").trim());
                    }
                } else if (JQstring.equals("getCity")) {
                    list = poleTypeModel.getCity(q);
                } else if (JQstring.equals("getTrafficType")) {
                    list = poleTypeModel.getTrafficType(q);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                poleTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
        if (task.equals("Update Pole No's")) {
            System.out.println("Update Pole No's");
            bean.setPole_id_showData(request.getParameterValues("pole_id_showData"));  //pole id
            bean.setClient_pole_no_id(request.getParameterValues("client_pole_no_id"));  // check uncheck no
            bean.setClient_pole_no_edit(request.getParameterValues("client_pole_no_edit"));  // pole no
            poleTypeModel.updatePoloNoClient(bean);
        }
        String searchPoleType = "";
        String searchMountingType = "";
        String searchPoleNo = "";
        String searchSwitchingPoint = "";
        // End of Auto Completer code
        searchSwitchingPoint = request.getParameter("searchSwitchingPoint");
        try {
            if (searchSwitchingPoint == null) {
                searchSwitchingPoint = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        searchPoleNo = request.getParameter("searchPoleNo");
        try {
            if (searchPoleNo == null) {
                searchPoleNo = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }


        if (request.getParameter("searchPoleType") != null && !request.getParameter("searchPoleType").isEmpty()) {

            searchPoleType = request.getParameter("searchPoleType");
            request.setAttribute("searchPoleType", searchPoleType);
        }
        searchMountingType = request.getParameter("searchMountingType");
        try {
            if (searchPoleType == null) {
                searchPoleType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchMountingType == null) {
                searchMountingType = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        if (task.equals("generatePoleExcelReport")) {
            String jrxmlFilePath;
            jrxmlFilePath = ctx.getRealPath("/report/newPoleDetailReport_excel.jrxml");
            HashMap mymap = new HashMap();
            //   mymap.put("zone_name", "Ranjhi");
            //    String excelFileName = "Ranjhi_SPD.xls";
            List siteList = poleTypeModel.showAllData(searchPoleType, searchMountingType, searchSwitchingPoint,searchPoleNo);
            boolean b = poleTypeModel.generateExcelReport(jrxmlFilePath, siteList, mymap, searchSwitchingPoint, response);
            return;

//             ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        JRXlsExporter exporter = new JRXlsExporter();
//               //         exporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, s);
//               //         exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, japserList);
//                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
//                        exporter.exportReport();
//
//                        ServletOutputStream servletOutputStream = response.getOutputStream();
//                        response.setContentType("application/vnd.ms-excel");
//                        response.setHeader("Content-Disposition", "attachment;filename=bill_parameter_details_excel.xls");
//                        response.setContentLength(baos.size());
//                        servletOutputStream.write(baos.toByteArray());
//                        servletOutputStream.flush();
//                        servletOutputStream.close();




//            List listAll = null;
//            String jrxmlFilePath;
//            response.setContentType("application/pdf");
//            ServletOutputStream servletOutputStream = response.getOutputStream();
            //     listAll = poleTypeModel.showAllData(searchPoleType, searchMountingType, searchSwitchingPoint);
//            jrxmlFilePath = ctx.getRealPath("/report/newPoleDetailReport_excel.jrxml");
//            byte[] reportInbytes = poleTypeModel.generateMapReport(jrxmlFilePath, listAll);
//            response.setContentLength(reportInbytes.length);
//            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//            servletOutputStream.flush();
//            servletOutputStream.close();
            //         return;
        }
        if (task.equals("generateMapReport")) {

            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = poleTypeModel.showAllData(searchPoleType, searchMountingType, searchSwitchingPoint,searchPoleNo);
            jrxmlFilePath = ctx.getRealPath("/report/newPoleDetailReport.jrxml");
            byte[] reportInbytes = poleTypeModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        } else if (task.equals("showMapWindow")) {
            String longi = request.getParameter("logitude");
            String latti = request.getParameter("lattitude");
            request.setAttribute("longi", longi);
            request.setAttribute("latti", latti);
            System.out.println(longi + "," + longi);
            view = "openMapWindowView";
        }
        if (task.equals("Cancel")) {
            int pole_id = Integer.parseInt(request.getParameter("pole_id"));
            int pole_rev_no = Integer.parseInt(request.getParameter("pole_rev_no"));
            poleTypeModel.cancelRecord(pole_id, pole_rev_no);  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Update")) {
            PoleDetailTypeBean poleTypeBean = new PoleDetailTypeBean();
            int pole_id;
            int pole_rev_no;
            try {
                pole_id = Integer.parseInt(request.getParameter("pole_id"));
            } catch (Exception e) {
                pole_id = 0;
            }
            try {
                pole_rev_no = Integer.parseInt(request.getParameter("pole_rev_no"));
            } catch (Exception e) {
                pole_rev_no = 0;
            }
            if (task.equals("Save AS New")) {
                pole_id = 0;
                pole_rev_no = 0;
                poleTypeBean.setIs_switch_point("No");
            } else {
                poleTypeBean.setIs_switch_point(request.getParameter("pole_is_switch_point"));
            }
            String road_category = request.getParameter("road_category").trim();
            String road_use = request.getParameter("road_use").trim();

            poleTypeBean.setPole_id(pole_id);
            poleTypeBean.setPole_rev_no(pole_rev_no);
            poleTypeBean.setPole_span(request.getParameter("pole_span"));
            poleTypeBean.setPole_height(request.getParameter("pole_height"));
            poleTypeBean.setMounting_height(request.getParameter("mounting_height"));
            poleTypeBean.setPole_type_id(poleTypeModel.getPoleId(request.getParameter("pole_type")));
            poleTypeBean.setMounting_type_id(poleTypeModel.getMountingId(request.getParameter("mounting_type")));

            poleTypeBean.setTraffic_type_id(poleTypeModel.getTrafficId(request.getParameter("traffic_type").trim()));
            poleTypeBean.setArea_id(poleTypeModel.getAreaId(request.getParameter("city").trim(), request.getParameter("ward_no").trim(), request.getParameter("area_name").trim()));

            String road_id_rev_no = poleTypeModel.getRoadId(request.getParameter("road_name").trim(), road_category, road_use);

            if (!road_id_rev_no.isEmpty() || road_id_rev_no != null) {
                poleTypeBean.setRoad_id(Integer.parseInt(road_id_rev_no.split("_")[0]));
                poleTypeBean.setRoad_rev_no(Integer.parseInt(road_id_rev_no.split("_")[1]));
            }

            //     poleTypeBean.setLight_type_id(poleTypeModel.getSourceWattageId(request.getParameter("source_wattage")));
            int feeder_id = poleTypeModel.getFeederId(request.getParameter("feeder_name").trim());
            String switching_id_rev_no = poleTypeModel.getSwitchingPointDetailId(request.getParameter("switching_point_name").trim(), feeder_id);
            poleTypeBean.setSwitch_point_detail_id(Integer.parseInt(switching_id_rev_no.split("_")[0]));
            poleTypeBean.setSwitching_rev_no(Integer.parseInt(switching_id_rev_no.split("_")[1]));

            poleTypeBean.setPole_no(request.getParameter("pole_no"));
            poleTypeBean.setPole_no_client(request.getParameter("pole_no_client"));
            poleTypeBean.setGps_code(request.getParameter("gps_code"));
            poleTypeBean.setMax_avg_lux_level(request.getParameter("max_avg_lux_level"));
            poleTypeBean.setMin_avg_lux_level(request.getParameter("min_avg_lux_level"));
            poleTypeBean.setAvg_lux_level(request.getParameter("avg_lux_level"));
            poleTypeBean.setStandard_lux_level(request.getParameter("standard_lux_level"));
            poleTypeBean.setIs_working(request.getParameter("is_working"));
            poleTypeBean.setRemark(request.getParameter("remark"));
            // poleTypeBean.setActive(request.getParameter("active"));
            poleTypeBean.setSources_wattage_M(request.getParameterValues("source_wattage"));
            poleTypeBean.setQuantity(request.getParameterValues("quantity"));

            poleTypeBean.setMapp_id(request.getParameterValues("mapp_id"));
            poleTypeBean.setMap_light_type_id(request.getParameterValues("light_type_id"));


            if (pole_id == 0) {
                poleTypeBean.setAddRowCount(Integer.parseInt(request.getParameter("addRowCount").trim()));
                // poleTypeBean.setNewRowCount(Integer.parseInt(request.getParameter("newRowCount").trim()));
                System.out.println("Inserting values by model......");
                if (poleTypeModel.validationCheck(request.getParameter("pole_no").trim(), request.getParameter("pole_no_client").trim())) {
                    poleTypeModel.insertRecord(poleTypeBean);
                }
            } else if (pole_id != 0 && task.equals("Update")) {
                poleTypeBean.setAddRowCount(Integer.parseInt(request.getParameter("addRowCount").trim()));
                poleTypeBean.setNewRowCount(Integer.parseInt(request.getParameter("newRowCount").trim()));
                System.out.println("Update values by model........");
                if (poleTypeModel.validationCheckforRevise(request.getParameter("pole_no").trim(), request.getParameter("pole_no_client").trim(), pole_id)) {
                    poleTypeModel.updateRecord(poleTypeBean);
                }
            } else {
                poleTypeBean.setAddRowCount(Integer.parseInt(request.getParameter("addRowCount").trim()));
                poleTypeBean.setNewRowCount(Integer.parseInt(request.getParameter("newRowCount").trim()));
                System.out.println("Revise values by model........");
                if (poleTypeModel.validationCheckforRevise(request.getParameter("pole_no").trim(), request.getParameter("pole_no_client").trim(), pole_id)) {
                    poleTypeModel.reviseRecord(poleTypeBean);
                }
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
        //System.out.println("searching.......... " + searchSourceType);
        noOfRowsInTable = poleTypeModel.getNoOfRows(searchPoleType, searchMountingType, searchSwitchingPoint, searchPoleNo);

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
        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Update") || task.equals("Update Pole No's")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchPoleType = "";
            searchMountingType = "";
            searchPoleNo = "";
            searchSwitchingPoint = "";
        }
        // Logic to show data in the table.
        List<PoleDetailTypeBean> poleTypeList = poleTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleType, searchMountingType, searchSwitchingPoint, searchPoleNo);
        lowerLimit = lowerLimit + poleTypeList.size();
        noOfRowsTraversed = poleTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("poleTypeList", poleTypeList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        // End of Search Table
        System.out.println("color is :" + poleTypeModel.getMsgBgColor());
        request.setAttribute("messagee", poleTypeModel.getMessagee());
        request.setAttribute("msgBgColorr", poleTypeModel.getMsgBgColorr());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchPoleType", searchPoleType);
        request.setAttribute("searchPoleNo", searchPoleNo);
        request.setAttribute("searchSwitchingPoint", searchSwitchingPoint);
        request.setAttribute("searchMountingType", searchMountingType);
        request.setAttribute("message", poleTypeModel.getMessage());
        request.setAttribute("msgBgColor", poleTypeModel.getMsgBgColor());
        request.getRequestDispatcher(view).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
