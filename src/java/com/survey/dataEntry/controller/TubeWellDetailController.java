/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.TubeWellDetailModel;
import com.survey.tableClasses.TubeWellDetailBean;
import com.survey.tableClasses.TubeWellSurveyBean;
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
public class TubeWellDetailController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is TubeWell Controller....");
        ServletContext ctx = getServletContext();
        TubeWellDetailModel tubeWellSurveyTypeModel = new TubeWellDetailModel();
        TubeWellDetailBean tubeWellTypeBean = new TubeWellDetailBean();
        tubeWellSurveyTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        tubeWellSurveyTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        tubeWellSurveyTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        tubeWellSurveyTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        tubeWellSurveyTypeModel.setConnection();
        String task = request.getParameter("task");
        String poleNo = "";
        String view = "tubeWellDetailView";

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getZoneSearch")) {
                    list = tubeWellSurveyTypeModel.getZoneSearch(q);
                }
                if (JQstring.equals("getPoleType")) {
                    list = tubeWellSurveyTypeModel.getPoleType(q);
                } else if (JQstring.equals("getPoleNo")) {
                    list = tubeWellSurveyTypeModel.getPoleNo(q);
                } else if (JQstring.equals("getSwitchingPtName")) {
                    list = tubeWellSurveyTypeModel.getSwitchingPtName(q);
                } else if (JQstring.equals("getAreaName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        list = tubeWellSurveyTypeModel.getAreaName(q, request.getParameter("action2"));
                    }
                } else if (JQstring.equals("getDivision")) {
                    list = tubeWellSurveyTypeModel.getDivision(q);
                } else if (JQstring.equals("getZoneName")) {
                    //if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                    //list = tubeWellSurveyTypeModel.getZone(q, request.getParameter("action2").trim());
                    list = tubeWellSurveyTypeModel.getZone(q);
                    // }
                } else if (JQstring.equals("getFeeder")) {
                    // if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                    list = tubeWellSurveyTypeModel.getFeeder(q, request.getParameter("action2").trim());
                    // }
                }else if (JQstring.equals("getFeederZoneName")) {
                    // if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                    list = tubeWellSurveyTypeModel.getFeederZoneName(q);
                    // }
                }
                else if (JQstring.equals("getWard_No")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        list = tubeWellSurveyTypeModel.getWard_No(q, request.getParameter("action2").trim());
                    }
                } else if (JQstring.equals("getRoadUse")) {
                    if((request.getParameter("action2") != null) && (!request.getParameter("action2").isEmpty())){
                    list = tubeWellSurveyTypeModel.getRoadUse(q,request.getParameter("action2"));
                    }
                } else if (JQstring.equals("getRoad_Category")) {
                    list = tubeWellSurveyTypeModel.getRoad_Category(q);
                } else if (JQstring.equals("getRoadName")) {
                    if ((request.getParameter("action2") != null || request.getParameter("action3") != null) && (!request.getParameter("action2").isEmpty() || !request.getParameter("action3").isEmpty())) {
                        list = tubeWellSurveyTypeModel.getRoadName(q, request.getParameter("action2").trim(), request.getParameter("action3").trim());
                    }
                } else if (JQstring.equals("getCity")) {
                    list = tubeWellSurveyTypeModel.getCity(q);
                } else if (JQstring.equals("getIvrsNoSearch")) {
                    list = tubeWellSurveyTypeModel.getIvrsNoSearch(q);
                } else if (JQstring.equals("getPoleNoSwitch")) {
                    list = tubeWellSurveyTypeModel.getPoleNoSwitch(q);
                } else if (JQstring.equals("getTrafficType")) {
                    list = tubeWellSurveyTypeModel.getTrafficType(q);
                } else if (JQstring.equals("getLightType")) {
                    list = tubeWellSurveyTypeModel.getLightType(q);
                } else if (JQstring.equals("getControlType")) {
                    list = tubeWellSurveyTypeModel.getControlType(q);
                } else if (JQstring.equals("getSwitchType")) {
                    list = tubeWellSurveyTypeModel.getSwitchingName(q);
                } else if (JQstring.equals("getFuseType")) {
                    list = tubeWellSurveyTypeModel.getFuseType(q);
                } else if (JQstring.equals("getStarterType")) {
                    list = tubeWellSurveyTypeModel.getStarterType(q);
                } else if (JQstring.equals("getMccbType")) {
                    list = tubeWellSurveyTypeModel.getMccbType1(q);
                } else if (JQstring.equals("getAutoSwitchType")) {
                    list = tubeWellSurveyTypeModel.getAutoSwitchType(q);
                } else if (JQstring.equals("getMainSwitchType")) {
                    list = tubeWellSurveyTypeModel.getMainSwitchType(q);
                } else if (JQstring.equals("getEnclosureType")) {
                    list = tubeWellSurveyTypeModel.getEnclosureType1(q);
                } else if (JQstring.equals("getIVRSNo")) {
                    list = tubeWellSurveyTypeModel.getIVRSNo(q, request.getParameter("action2"));
                } else if (JQstring.equals("getPoleNoS")) {
                    list = tubeWellSurveyTypeModel.getPole_No(q);
                } else if (JQstring.equals("getMeterNo")) {
                    list = tubeWellSurveyTypeModel.getMeterNo(q, request.getParameter("action2"));
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                tubeWellSurveyTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        // Start of Auto Completer code
        String searchSwitchingPtName = "";
        //String searchPoleType = "";
        String searchPoleNo = "";
        //String searchRoadName = "";
        //String searchAreaName = "";
       // String searchPoleNoSwitch = "";
        String service_conn_no_Search = "";
        String zone_search = "";
        // End of Auto Completer code
        searchSwitchingPtName = request.getParameter("searchSwitchingPtName");//
        //searchPoleNoSwitch = request.getParameter("searchPoleNoSwitch");
        service_conn_no_Search = request.getParameter("service_conn_no_Search");//
        //searchPoleType = request.getParameter("searchPoleType");
        searchPoleNo = request.getParameter("searchPoleNo");//
        //searchRoadName = request.getParameter("searchRoadName");
       // searchAreaName = request.getParameter("searchAreaName");
        zone_search = request.getParameter("zone_search");//
        try {
            if (searchSwitchingPtName == null) {
                searchSwitchingPtName = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
//            if (searchPoleNoSwitch == null) {
//                searchPoleNoSwitch = "";
//            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (zone_search == null) {
                zone_search = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (service_conn_no_Search == null) {
                service_conn_no_Search = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
//            if (searchPoleType == null) {
//                searchPoleType = "";
//            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchPoleNo == null) {
                searchPoleNo = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
//            if (searchRoadName == null) {
//                searchRoadName = "";
//            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
//            if (searchAreaName == null) {
//                searchAreaName = "";
//            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        if (task == null) {
            task = "";
        }
        if (task.equals("updateIVRS")) {
            System.out.println("update");
            tubeWellTypeBean.setSwitching_point_id_for_update(request.getParameterValues("tube_well_detail_id"));
            tubeWellTypeBean.setHidden_field(request.getParameterValues("ivrs_no_id"));
            tubeWellTypeBean.setIvrs_no_edit(request.getParameterValues("ivrs_no_edit"));
            tubeWellSurveyTypeModel.updateIVRSRecord(tubeWellTypeBean);
        }

        if (task.equals("GetCordinates1"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("getCordinate1").forward(request, response);
                return;
            }

        if (task.equals("generateMapReport")) {

            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            //listAll = tubeWellSurveyTypeModel.showAllData(searchPoleNoSwitch, searchPoleType, searchPoleNo, searchAreaName, searchRoadName, service_conn_no_Search, searchSwitchingPtName, zone_search);
            jrxmlFilePath = ctx.getRealPath("/report/newSwitchingPointDetailReport.jrxml");
            byte[] reportInbytes = tubeWellSurveyTypeModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        } else if (task.equals("showMapWindow")) {
            String longi = request.getParameter("longitude");
            String latti = request.getParameter("lattitude");
            request.setAttribute("longi", longi);
            request.setAttribute("latti", latti);
            System.out.println(longi + "," + longi);
            view = "openMapWindowView";
        }

        if (task.equals("Cancel")) {

            int tube_well_detail_id = Integer.parseInt(request.getParameter("tube_well_detail_id"));
            int switching_rev_no = Integer.parseInt(request.getParameter("switching_rev_no"));
            tubeWellSurveyTypeModel.cancelRecord(tube_well_detail_id, switching_rev_no);  // Pretty sure that organisation_type_id will be available.

        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Update")) {
            int tube_well_detail_id = 0;
            int tube_well_rev=0;
            int switching_rev_no;
            try {
                tube_well_detail_id = Integer.parseInt(request.getParameter("tube_well_detail_id"));
                tube_well_rev=Integer.parseInt(request.getParameter("tube_well_rev_no"));
                tubeWellTypeBean.setTube_well_detail_id(tube_well_detail_id);
                tubeWellTypeBean.setTube_well_rev_no(tube_well_rev);

            } catch (Exception e) {
            }
            try {
                switching_rev_no = Integer.parseInt(request.getParameter("switching_rev_no"));
            } catch (Exception e) {
                switching_rev_no = 0;
            }
            if (task.equals("Save AS New")) {
                tube_well_detail_id = 0;
                switching_rev_no = 0;
            }

            //       String city = request.getParameter("zone");
            String road_category = request.getParameter("road_category").trim();
            String road_use = request.getParameter("road_use").trim();
            String fuseY = request.getParameter("fuseY");
            String fuseN = request.getParameter("fuseN");
            String starterY = request.getParameter("starterY");
            String starterN = request.getParameter("starterN");
            String mccbY = request.getParameter("mccbY");
            String mccbN = request.getParameter("mccbN");
            //    String timerY = request.getParameter("timerY");
            //  String timerN = request.getParameter("timerN");
            if (fuseY == null && fuseN != null) {
                tubeWellTypeBean.setFuse("N");
            } else {
                tubeWellTypeBean.setFuse("Y");
            }
            if (starterY == null && starterN != null) {
                tubeWellTypeBean.setStarter("N");
            } else {
                tubeWellTypeBean.setStarter("Y");
            }
            if (mccbY == null && mccbN != null) {
                tubeWellTypeBean.setMccb("N");
            } else {
                tubeWellTypeBean.setMccb("Y");
            }

            tubeWellTypeBean.setIs_on_pole(request.getParameter("is_on_pole"));
            tubeWellTypeBean.setFeeder_id(tubeWellSurveyTypeModel.getFeeder_id(request.getParameter("feeder").trim(), request.getParameter("zone").trim(), request.getParameter("division").trim()));
            tubeWellTypeBean.setArea_id(tubeWellSurveyTypeModel.getAreaId(request.getParameter("zone").trim(), request.getParameter("ward_no").trim(), request.getParameter("area_name").trim()));
            tubeWellTypeBean.setTube_well_name(request.getParameter("tube_well_name").trim());
            tubeWellTypeBean.setGps_code_s(request.getParameter("gps_code_s").trim());
            tubeWellTypeBean.setControl_mechanism_id(tubeWellSurveyTypeModel.getControlId(request.getParameter("control_mechanism_type").trim()));
            tubeWellTypeBean.setTraffic_type_id(tubeWellSurveyTypeModel.getTrafficId(request.getParameter("traffic_type").trim()));

            String road_id_rev_no = tubeWellSurveyTypeModel.getRoadId_Rev(request.getParameter("road_name").trim(), road_category, road_use);

            if (!road_id_rev_no.isEmpty() || road_id_rev_no != null) {
                tubeWellTypeBean.setRoad_id(Integer.parseInt(road_id_rev_no.split("_")[0]));
                tubeWellTypeBean.setRoad_rev_no(Integer.parseInt(road_id_rev_no.split("_")[1]));
            }
            //        tubeWellTypeBean.setRoad_id(tubeWellSurveyTypeModel.getRoadId(request.getParameter("road_name").trim(), road_category, road_use));
            tubeWellTypeBean.setPhase(Integer.parseInt(request.getParameter("phase").trim()));
            tubeWellTypeBean.setIs_working(request.getParameter("is_working").trim());
            tubeWellTypeBean.setRemark(request.getParameter("remark").trim());
            // tubeWellTypeBean.setFuse_id(tubeWellSurveyTypeModel.getFuseId(request.getParameter("fuse_type").trim()));
            tubeWellTypeBean.setFuse_quantity(request.getParameter("fuse_quantity").trim());
            // tubeWellTypeBean.setStarter_type();
            // tubeWellTypeBean.setContacter_id(tubeWellSurveyTypeModel.getContacterId(request.getParameter("starter_type").trim()));
            //   tubeWellTypeBean.setContacter_quantity(request.getParameter("contacter_quantity").trim());
            //  tubeWellTypeBean.setMccb();
            //  tubeWellTypeBean.setMccb_id(tubeWellSurveyTypeModel.getMccbId(request.getParameter("mccb_type").trim()));
            tubeWellTypeBean.setMccb_quantity(request.getParameter("mccb_quantity").trim());
            // tubeWellTypeBean.setTimer();
            // tubeWellTypeBean.setTimer_id(tubeWellSurveyTypeModel.getTimerId(request.getParameter("timer_type").trim()));
            // tubeWellTypeBean.setTimer_quantity(request.getParameter("timer_quantity").trim());

            tubeWellTypeBean.setLongitude(request.getParameter("longitude").trim());
            tubeWellTypeBean.setLattitude(request.getParameter("latitude").trim());
            tubeWellTypeBean.setIvrs_no(request.getParameter("ivrs_no").trim());
            tubeWellTypeBean.setMeter_no_s(request.getParameter("meter_no_s").trim());
            tubeWellTypeBean.setService_conn_no(request.getParameter("service_conn_no").trim());
            tubeWellTypeBean.setIsCheckedTrue(request.getParameter("isCheckedTrue").trim());
            tubeWellTypeBean.setFuse1(request.getParameter("fuse_1").trim());
            tubeWellTypeBean.setFuse2(request.getParameter("fuse_2").trim());
            tubeWellTypeBean.setFuse3(request.getParameter("fuse_3").trim());
            tubeWellTypeBean.setMccb1(request.getParameter("mccb_1").trim());
            tubeWellTypeBean.setMccb2(request.getParameter("mccb_2").trim());
            tubeWellTypeBean.setMccb3(request.getParameter("mccb_3").trim());
            tubeWellTypeBean.setStarter_id(tubeWellSurveyTypeModel.getStarterId(request.getParameter("starter_type").trim()));
            tubeWellTypeBean.setStarter_capacity(request.getParameter("starter_capacity").trim());
            tubeWellTypeBean.setStarter_make_id(tubeWellSurveyTypeModel.getStarterMakeId(request.getParameter("starter_make").trim()));
            tubeWellTypeBean.setFuse_id1(tubeWellSurveyTypeModel.getFuseId(request.getParameter("fuse_type1").trim()));
            tubeWellTypeBean.setFuse_id2(tubeWellSurveyTypeModel.getFuseId(request.getParameter("fuse_type2").trim()));
            tubeWellTypeBean.setFuse_id3(tubeWellSurveyTypeModel.getFuseId(request.getParameter("fuse_type3").trim()));
            tubeWellTypeBean.setMccb_id1(tubeWellSurveyTypeModel.getMccbId(request.getParameter("mccb_type1").trim()));
            tubeWellTypeBean.setMccb_id2(tubeWellSurveyTypeModel.getMccbId(request.getParameter("mccb_type2").trim()));
            tubeWellTypeBean.setMccb_id3(tubeWellSurveyTypeModel.getMccbId(request.getParameter("mccb_type3").trim()));
            tubeWellTypeBean.setAuto_switch_type_id(tubeWellSurveyTypeModel.getSwitchId(request.getParameter("auto_switch_type").trim()));
            tubeWellTypeBean.setMain_switch_type_id(tubeWellSurveyTypeModel.getSwitchId(request.getParameter("main_switch_type").trim()));
            tubeWellTypeBean.setEnclosure_type_id(tubeWellSurveyTypeModel.getEnclosureTypeId(request.getParameter("enclosure_type").trim()));
            //   if (request.getParameter("main_switch_rating") == null) {
            tubeWellTypeBean.setMain_switch_reading(request.getParameter("main_switch_rating"));
            tubeWellTypeBean.setR_phase(request.getParameter("r_phase"));
            tubeWellTypeBean.setB_phase(request.getParameter("b_phase"));
            tubeWellTypeBean.setY_phase(request.getParameter("y_phase"));
            tubeWellTypeBean.setMeter_status(request.getParameter("meter_status"));

            tubeWellTypeBean.setMeter_address(request.getParameter("meter_address"));
//            } else {
//                tubeWellTypeBean.setMain_switch_reading("");
//            }
            tubeWellTypeBean.setMeter_id(tubeWellSurveyTypeModel.getMeterId(request.getParameter("ivrs_no").trim()));

            if (request.getParameter("no_of_poles") != null && !request.getParameter("no_of_poles").isEmpty()) {
                tubeWellTypeBean.setNo_of_poles(Integer.parseInt(request.getParameter("no_of_poles").trim()));
            } else {
                tubeWellTypeBean.setNo_of_poles(0);
            }
            if (request.getParameter("is_on_pole") != null) {
                // tubeWellTypeBean.setPole_type_id(tubeWellSurveyTypeModel.getPoleId(request.getParameter("pole_no").trim()));
                tubeWellTypeBean.setPole_no(request.getParameter("pole_no").trim());
            } else if (request.getParameter("is_on_pole") == null) {
                tubeWellTypeBean.setPole_no("NoPoleEntry");
            }
            //    tubeWellTypeBean.setPole_type_id(tubeWellSurveyTypeModel.getPoleId(request.getParameter("pole_no").trim()));
            tubeWellTypeBean.setPole_no_s(request.getParameter("pole_no_s").trim());  // Switching point no
            tubeWellTypeBean.setMeasured_load(request.getParameter("main_switch_rating").trim());
            tubeWellTypeBean.setPole_id(tubeWellSurveyTypeModel.getPoleId(request.getParameter("pole_no_s")));
            if (tube_well_detail_id == 0) {
                System.out.println("Inserting values by model......");

                //if (tubeWellSurveyTypeModel.validationCheck(request.getParameter("service_conn_no").trim(), request.getParameter("ivrs_no").trim(), tubeWellTypeBean.getPole_no(), tube_well_detail_id, switching_rev_no, tubeWellTypeBean.getIsCheckedTrue())) {
                tubeWellSurveyTypeModel.insertRecord(tubeWellTypeBean);
                //  }
            } else {

                System.out.println("Update values by model........");
                String isOnPole = request.getParameter("isCheckedTrue").trim();

                //       tubeWellTypeBean.setPole_id(Integer.parseInt(request.getParameter("pole_id").trim()));
                //       tubeWellTypeBean.setPole_rev_no(Integer.parseInt(request.getParameter("pole_rev_no").trim()));
                tubeWellTypeBean.setTube_well_detail_id(tube_well_detail_id);
                //tubeWellTypeBean.setTube_well_rev_no(switching_rev_no);
                if (task.equals("Revise")) {
                    // if (tubeWellSurveyTypeModel.validationCheckforRevise(request.getParameter("service_conn_no").trim(), request.getParameter("ivrs_no").trim(), tubeWellTypeBean.getPole_no(), tube_well_detail_id, switching_rev_no, tubeWellTypeBean.getIsCheckedTrue())) {
                    tubeWellSurveyTypeModel.reviseRecord(tubeWellTypeBean);
                    //  }
                } else if (task.equals("Update")) {
                    if (tubeWellSurveyTypeModel.validationCheckforRevise(request.getParameter("service_conn_no").trim(), request.getParameter("ivrs_no").trim(), tubeWellTypeBean.getPole_no(), tube_well_detail_id, switching_rev_no, tubeWellTypeBean.getIsCheckedTrue())) {
                        tubeWellSurveyTypeModel.updateRecord(tubeWellTypeBean);
                    }
                }

            }

        }
        if (task.equals("showSurveyData")) {
            TubeWellSurveyBean tubeWellSurveyBean  = new TubeWellSurveyBean();
            String pole_no = request.getParameter("poll_no");
            String meter_no = request.getParameter("meter_no");
            String service_conn_no = request.getParameter("service_conn_no");
            tubeWellTypeBean = tubeWellSurveyTypeModel.showDetailData(service_conn_no);
            tubeWellSurveyBean=tubeWellSurveyTypeModel.getTubeWellSurveyData(service_conn_no);
              request.setAttribute("tubeWellSurveyData", tubeWellSurveyBean);

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
        //System.out.println("searching.......... " + searchSourceType);
//        noOfRowsInTable = tubeWellSurveyTypeModel.getNoOfRows(searchPoleNoSwitch, searchPoleType, searchPoleNo, searchAreaName, searchRoadName, service_conn_no_Search, searchSwitchingPtName, zone_search);
        noOfRowsInTable = tubeWellSurveyTypeModel.getNoOfRows( searchPoleNo, service_conn_no_Search, searchSwitchingPtName, zone_search);
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
        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Update") || task.equals("Revise") || task.equals("updateIVRS")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals(
                "Show All Records")) {
            //searchPoleType = "";
            searchPoleNo = "";
            //searchAreaName = "";
            //searchRoadName = "";
            searchSwitchingPtName = "";
            service_conn_no_Search = "";
            zone_search = "";

        }
        // Logic to show data in the table.
//        List<TubeWellDetailBean> tubeWellSurveyTypeList = tubeWellSurveyTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNoSwitch, searchPoleType, searchPoleNo, searchAreaName, searchRoadName, service_conn_no_Search, searchSwitchingPtName, zone_search);
        List<TubeWellDetailBean> tubeWellSurveyTypeList = tubeWellSurveyTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNo, service_conn_no_Search, searchSwitchingPtName, zone_search);
        lowerLimit = lowerLimit + tubeWellSurveyTypeList.size();
        noOfRowsTraversed = tubeWellSurveyTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("surveyTypeList", tubeWellSurveyTypeList);





        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        // End of Search Table

        System.out.println("color is :" + tubeWellSurveyTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        //request.setAttribute("searchPoleType", searchPoleType);
        request.setAttribute("searchPoleNo", searchPoleNo);
        request.setAttribute("searchSwitchingPtName", searchSwitchingPtName);
       // request.setAttribute("searchRoadName", searchRoadName);
        //request.setAttribute("searchAreaName", searchAreaName);
        request.setAttribute("service_conn_no_Search", service_conn_no_Search);
        request.setAttribute("zone_search", zone_search);
        //request.setAttribute("searchPoleNoSwitch", searchPoleNoSwitch);
        request.setAttribute("message", tubeWellSurveyTypeModel.getMessage());
        request.setAttribute("messagee", tubeWellSurveyTypeModel.getMessagee());
        request.setAttribute("msgBgColorr", tubeWellSurveyTypeModel.getMsgBgColorr());
        request.setAttribute("msgBgColor", tubeWellSurveyTypeModel.getMsgBgColor());
        request.setAttribute("tubeWellTypeBean", tubeWellTypeBean);
        request.getRequestDispatcher(view).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);

    }
}
