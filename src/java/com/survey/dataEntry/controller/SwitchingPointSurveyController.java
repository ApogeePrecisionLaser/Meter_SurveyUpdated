/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.MeterModelABC1;
import com.survey.dataEntry.model.NewSwitchingPointSurveyModel;
import com.survey.dataEntry.model.SwitchingPointSurveyModel;
import com.survey.tableClasses.SwitchingPointSurveyBean;
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
import org.apache.jasper.tagplugins.jstl.core.ForEach;

public class SwitchingPointSurveyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        int countnum=0;
        System.out.println("this is Switching Point Controller....");
        ServletContext ctx = getServletContext();
        SwitchingPointSurveyModel surveyTypeModel = new SwitchingPointSurveyModel();
        SwitchingPointSurveyBean surveyTypeBean = new SwitchingPointSurveyBean();
        NewSwitchingPointSurveyModel newSwitchingPointSurveyModel = new NewSwitchingPointSurveyModel();

        surveyTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        surveyTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        surveyTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        surveyTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        surveyTypeModel.setConnection();

        MeterModelABC1 meter=new MeterModelABC1();
        meter.setDriverClass(ctx.getInitParameter("driverClass"));
        meter.setConnectionString(ctx.getInitParameter("connectionString"));
        meter.setDb_username(ctx.getInitParameter("db_username"));
        meter.setDb_password(ctx.getInitParameter("db_password"));
        meter.setConnection();

        newSwitchingPointSurveyModel.setDriverClass(ctx.getInitParameter("driverClass"));
        newSwitchingPointSurveyModel.setConnectionString(ctx.getInitParameter("connectionString"));
        newSwitchingPointSurveyModel.setDb_username(ctx.getInitParameter("db_username"));
        newSwitchingPointSurveyModel.setDb_password(ctx.getInitParameter("db_password"));
        newSwitchingPointSurveyModel.setConnection();
        String task = request.getParameter("task");
        String poleNo = "";
        String view = "switching_PointDetailView";

        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getZoneSearch")) {
                    list = surveyTypeModel.getZoneSearch(q);
                }
                if (JQstring.equals("getPoleType")) {
                    list = surveyTypeModel.getPoleType(q);
                } else if (JQstring.equals("getPoleNo")) {
                    list = surveyTypeModel.getPoleNo(q);
                } else if (JQstring.equals("getSwitchingPtName")) {
                    list = surveyTypeModel.getSwitchingPtName(q);
                } else if (JQstring.equals("getAreaName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        list = surveyTypeModel.getAreaName(q, request.getParameter("action2"));
                    }

                } else if (JQstring.equals("getDivision")) {
                    list = surveyTypeModel.getDivision(q);
                }else if (JQstring.equals("searchPoleNo")) {
                    list = surveyTypeModel.searchPoleNo(q);
                }
                else if (JQstring.equals("getZone")) {
                    //if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                    list = surveyTypeModel.getZone(q, request.getParameter("action2").trim());
                    // }
                } else if (JQstring.equals("getFeeder")) {
                    // if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                    list = surveyTypeModel.getFeeder(q, request.getParameter("action2").trim());
                    // }
                } else if (JQstring.equals("getWard_No")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        list = surveyTypeModel.getWard_No(q, request.getParameter("action2").trim());
                    }
                } else if (JQstring.equals("getRoadUse")) {
                    list = surveyTypeModel.getRoadUse(q);
                } else if (JQstring.equals("getRoad_Category")) {
                    list = surveyTypeModel.getRoad_Category(q);
                } else if (JQstring.equals("getRoadName")) {
                    if ((request.getParameter("action2") != null || request.getParameter("action3") != null) && (!request.getParameter("action2").isEmpty() || !request.getParameter("action3").isEmpty())) {
                        list = surveyTypeModel.getRoadName(q, request.getParameter("action2").trim(), request.getParameter("action3").trim());
                    }
                } else if (JQstring.equals("getCity")) {
                    list = surveyTypeModel.getCity(q);
                } else if (JQstring.equals("service_conn_no_Search")) {
                    list = surveyTypeModel.getservice_conn_no_Search(q);
                } else if (JQstring.equals("getPoleNoSwitch")) {
                    list = surveyTypeModel.getPoleNoSwitch(q);
                } else if (JQstring.equals("getTrafficType")) {
                    list = surveyTypeModel.getTrafficType(q);
                } else if (JQstring.equals("getLightType")) {
                    list = surveyTypeModel.getLightType(q);
                } else if (JQstring.equals("getControlType")) {
                    list = surveyTypeModel.getControlType(q);
                } else if (JQstring.equals("getSwitchType")) {
                    list = surveyTypeModel.getSwitchingName(q);
                } else if (JQstring.equals("getFuseType")) {
                    list = surveyTypeModel.getFuseType(q);
                } else if (JQstring.equals("getContacterType")) {
                    list = surveyTypeModel.getContacterType(q);
                } else if (JQstring.equals("getMccbType")) {
                    list = surveyTypeModel.getMccbType(q);
                } else if (JQstring.equals("getAutoSwitchType")) {
                    list = surveyTypeModel.getAutoSwitchType(q);
                } else if (JQstring.equals("getMainSwitchType")) {
                    list = surveyTypeModel.getMainSwitchType(q);
                }else if (JQstring.equals("getEnclosureType")) {
                    list = surveyTypeModel.getEnclosureType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                surveyTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        // Start of Auto Completer code
        String searchSwitchingPtName = "";
        String searchPoleType = "";
        String searchPoleNo = "";
        String searchRoadName = "";
        String searchAreaName = "";
        String searchPoleNoSwitch = "";
        String service_conn_no_Search = "";
        String zone_search = "";
        // End of Auto Completer code
        searchSwitchingPtName = request.getParameter("searchSwitchingPtName");
        searchPoleNoSwitch = request.getParameter("searchPoleNoSwitch");
        service_conn_no_Search = request.getParameter("service_conn_no_Search");
        searchPoleType = request.getParameter("searchPoleType");
        searchPoleNo = request.getParameter("searchPoleNo");
        searchRoadName = request.getParameter("searchRoadName");
        searchAreaName = request.getParameter("searchAreaName");
        zone_search = request.getParameter("zone_search");
        try {
            if (searchSwitchingPtName == null) {
                searchSwitchingPtName = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchPoleNoSwitch == null) {
                searchPoleNoSwitch = "";
            }
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
            if (searchPoleType == null) {
                searchPoleType = "";
            }
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
            if (searchRoadName == null) {
                searchRoadName = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchAreaName == null) {
                searchAreaName = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        if (task == null) {
            task = "";
        }
        if (task.equals("updateIVRS")) {
            System.out.println("update");
            surveyTypeBean.setSwitching_point_id_for_update(request.getParameterValues("switching_point_detail_id"));
            surveyTypeBean.setHidden_field(request.getParameterValues("ivrs_no_id"));
            surveyTypeBean.setIvrs_no_edit(request.getParameterValues("ivrs_no_edit"));
            surveyTypeModel.updateIVRSRecord(surveyTypeBean);
        }
        if (task.equals("generateMapReport")) {

            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = surveyTypeModel.showAllData(searchPoleNoSwitch, searchPoleType, searchPoleNo, searchAreaName, searchRoadName, service_conn_no_Search, searchSwitchingPtName, zone_search);
            jrxmlFilePath = ctx.getRealPath("/report/newSwitchingPointDetailReport.jrxml");
            byte[] reportInbytes = surveyTypeModel.generateMapReport(jrxmlFilePath, listAll);
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

            int switching_point_detail_id = Integer.parseInt(request.getParameter("switching_point_detail_id"));
            int switching_rev_no = Integer.parseInt(request.getParameter("switching_rev_no"));
            surveyTypeModel.cancelRecord(switching_point_detail_id, switching_rev_no);  // Pretty sure that organisation_type_id will be available.

        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Update")) {
            int switching_point_detail_id = 0;
            int switching_rev_no;
            try {
                switching_point_detail_id = Integer.parseInt(request.getParameter("switching_point_detail_id"));
            } catch (Exception e) {
            }
            try {
                switching_rev_no = Integer.parseInt(request.getParameter("switching_rev_no"));
            } catch (Exception e) {
                switching_rev_no = 0;
            }
            if (task.equals("Save AS New")) {
                switching_point_detail_id = 0;
                switching_rev_no = 0;
            }

            //       String city = request.getParameter("zone");
            String road_category="";
            // road_category = request.getParameter("road_category").trim();
             String road_use="";
//             road_use = request.getParameter("road_use");
//             if(road_use==null || road_use.equals("")){
//                  road_use="";
//             }
            String fuseY = request.getParameter("fuseY");
            String fuseN = request.getParameter("fuseN");
            String contacterY = request.getParameter("contacterY");
            String contacterN = request.getParameter("contacterN");
            String mccbY = request.getParameter("mccbY");
            String mccbN = request.getParameter("mccbN");
            String timerY = request.getParameter("timerY");
            String timerN = request.getParameter("timerN");
            if (fuseY == null && fuseN != null) {
                surveyTypeBean.setFuse("N");
            } else {
                surveyTypeBean.setFuse("Y");
            }
            if (contacterY == null && contacterN != null) {
                surveyTypeBean.setContacter("N");
            } else {
                surveyTypeBean.setContacter("Y");
            }
            if (mccbY == null && mccbN != null) {
                surveyTypeBean.setMccb("N");
            } else {
                surveyTypeBean.setMccb("Y");
            }
            if (timerY == null && timerN != null) {
                surveyTypeBean.setTimer("N");
            } else {
                surveyTypeBean.setTimer("Y");
            }
            String feder="";
            String zone_check="";
            String division_check ="";
            feder=request.getParameter("feeder");
            zone_check=request.getParameter("zone");
            division_check= request.getParameter("division");
            surveyTypeBean.setIs_on_pole(request.getParameter("is_on_pole"));
            surveyTypeBean.setFeeder_id(surveyTypeModel.getFeeder_id(feder,zone_check,division_check));
            surveyTypeBean.setArea_id(surveyTypeModel.getAreaId(request.getParameter("city"), request.getParameter("ward_no"), request.getParameter("area_name")));
            surveyTypeBean.setSwitching_point_name(request.getParameter("switching_point_name"));
            surveyTypeBean.setGps_code_s(request.getParameter("gps_code_s"));
            surveyTypeBean.setControl_mechanism_id(surveyTypeModel.getControlId(request.getParameter("control_mechanism_type")));
            surveyTypeBean.setTraffic_type_id(surveyTypeModel.getTrafficId(request.getParameter("traffic_type")));

            String road_id_rev_no = surveyTypeModel.getRoadId_Rev(request.getParameter("road_name"), road_category, road_use);

            if (road_id_rev_no != null && !road_id_rev_no.isEmpty()) {
                surveyTypeBean.setRoad_id(Integer.parseInt(road_id_rev_no.split("_")[0]));
                surveyTypeBean.setRoad_rev_no(Integer.parseInt(road_id_rev_no.split("_")[1]));
            }
            //        surveyTypeBean.setRoad_id(surveyTypeModel.getRoadId(request.getParameter("road_name").trim(), road_category, road_use));
           String phase_check=request.getParameter("phase");
           if(phase_check==null || phase_check.equals("")){
              surveyTypeBean.setPhase(0);
           }else{
            surveyTypeBean.setPhase(Integer.parseInt(phase_check));
           }
            surveyTypeBean.setIs_working(request.getParameter("is_working"));
            surveyTypeBean.setRemark(request.getParameter("remark"));
            // surveyTypeBean.setFuse_id(surveyTypeModel.getFuseId(request.getParameter("fuse_type").trim()));
            surveyTypeBean.setFuse_quantity(request.getParameter("fuse_quantity"));
            // surveyTypeBean.setContacter();
            // surveyTypeBean.setContacter_id(surveyTypeModel.getContacterId(request.getParameter("contacter_type").trim()));
            //   surveyTypeBean.setContacter_quantity(request.getParameter("contacter_quantity").trim());
            //  surveyTypeBean.setMccb();
            //  surveyTypeBean.setMccb_id(surveyTypeModel.getMccbId(request.getParameter("mccb_type").trim()));
            surveyTypeBean.setMccb_quantity(request.getParameter("mccb_quantity"));
            // surveyTypeBean.setTimer();
            // surveyTypeBean.setTimer_id(surveyTypeModel.getTimerId(request.getParameter("timer_type").trim()));
            // surveyTypeBean.setTimer_quantity(request.getParameter("timer_quantity").trim());
            if (request.getParameter("logitude") != null && !request.getParameter("logitude").trim().isEmpty()) {
                surveyTypeBean.setLongitude(Double.parseDouble(request.getParameter("logitude").trim()));
            }
            if (request.getParameter("lattitude")!= null && !request.getParameter("lattitude").trim().isEmpty()) {
                surveyTypeBean.setLattitude(Double.parseDouble(request.getParameter("lattitude").trim()));
            }


            surveyTypeBean.setIvrs_no(request.getParameter("ivrs_no"));
            surveyTypeBean.setMeter_no_s(request.getParameter("meter_no_s"));
            surveyTypeBean.setService_conn_no(request.getParameter("service_conn_no"));
            surveyTypeBean.setIsCheckedTrue(request.getParameter("isCheckedTrue"));
            surveyTypeBean.setFuse1(request.getParameter("fuse_1"));
            surveyTypeBean.setFuse2(request.getParameter("fuse_2"));
            surveyTypeBean.setFuse3(request.getParameter("fuse_3"));
            surveyTypeBean.setMccb1(request.getParameter("mccb_1"));
            surveyTypeBean.setMccb2(request.getParameter("mccb_2"));
            surveyTypeBean.setMccb3(request.getParameter("mccb_3"));
            //surveyTypeBean.setContacter_type(request.getParameter("contacter_type").trim());
            surveyTypeBean.setContacter_capacity(request.getParameter("contacter_capacity"));
            surveyTypeBean.setContacter_make(request.getParameter("contacter_make"));
            surveyTypeBean.setFuse_id1(surveyTypeModel.getFuseId(request.getParameter("fuse_type1")));
            surveyTypeBean.setFuse_id2(surveyTypeModel.getFuseId(request.getParameter("fuse_type2")));
            surveyTypeBean.setFuse_id3(surveyTypeModel.getFuseId(request.getParameter("fuse_type3")));
            surveyTypeBean.setMccb_id1(surveyTypeModel.getMccbId(request.getParameter("mccb_type3")));
            surveyTypeBean.setMccb_id2(surveyTypeModel.getMccbId(request.getParameter("mccb_type2")));
            surveyTypeBean.setMccb_id3(surveyTypeModel.getMccbId(request.getParameter("mccb_type3")));
            surveyTypeBean.setContacter_id(surveyTypeModel.getContacterId(request.getParameter("contacter_type")));
            surveyTypeBean.setAuto_switch_type_id(surveyTypeModel.getSwitchId(request.getParameter("auto_switch_type")));
            surveyTypeBean.setMain_switch_type_id(surveyTypeModel.getSwitchId(request.getParameter("main_switch_type")));
            surveyTypeBean.setEnclosure_type_id(surveyTypeModel.getEnclosureTypeId(request.getParameter("enclosure_type")));
            surveyTypeBean.setMain_switch_reading(request.getParameter("main_switch_reading"));
            String[] source_wattage = request.getParameterValues("source_wattage");
            String[] splited_values = null;
            String bulb_type = null;
            String bulb_wattage = null;
            int source_wattage_lenght=source_wattage.length;
            int[] light_typeIDs = new int[source_wattage.length];
            
            for (int i = 0; i < source_wattage_lenght; i++) {
                
                String single_source = source_wattage[i].trim();
                if(!single_source.equals("")){
                if (!single_source.isEmpty()) {
                    splited_values = single_source.split("-");
                    bulb_type = splited_values[0];
                    bulb_wattage = splited_values[1];
                    light_typeIDs[i] = surveyTypeModel.getlightTypeID(bulb_type, bulb_wattage);
                }
                surveyTypeBean.setLight_type_id_M(light_typeIDs);
         
                }else {
                    source_wattage_lenght=0;
                }
            }
               surveyTypeBean.setQuantity(request.getParameterValues("quantity"));
            surveyTypeBean.setWorking(request.getParameterValues("working"));
            surveyTypeBean.setN_working(request.getParameterValues("n_working"));
            surveyTypeBean.setMapp_ids(request.getParameterValues("mapp_id"));
            surveyTypeBean.setLight_type_ids(request.getParameterValues("light_type_id"));
            
            

            if (request.getParameter("no_of_poles") != null && !request.getParameter("no_of_poles").isEmpty()) {
                surveyTypeBean.setNo_of_poles(Integer.parseInt(request.getParameter("no_of_poles").trim()));
            } else {
                surveyTypeBean.setNo_of_poles(0);
            }
            if (request.getParameter("is_on_pole") != null) {
                // surveyTypeBean.setPole_type_id(surveyTypeModel.getPoleId(request.getParameter("pole_no").trim()));
                surveyTypeBean.setPole_no(request.getParameter("pole_no").trim());
            } else if (request.getParameter("is_on_pole") == null)
            {
                surveyTypeBean.setPole_no("NoPoleEntry");
            }
            //    surveyTypeBean.setPole_type_id(surveyTypeModel.getPoleId(request.getParameter("pole_no").trim()));
            surveyTypeBean.setPole_no_s(request.getParameter("pole_no_s"));  // Switching point no
            if(request.getParameter("m_load") != null && !request.getParameter("m_load").trim().isEmpty())
                surveyTypeBean.setMeasured_load(Double.parseDouble(request.getParameter("m_load").trim()));
            else
                surveyTypeBean.setMeasured_load(0);

            // surveyTypeBean.setSurvey_id(survey_id);
            // surveyTypeBean.setSurvey_rev_no(survey_rev_no);
            // surveyTypeBean.setGps_code_s(request.getParameter("gps_code_s"));

            //      surveyTypeBean.setGps_code_s(request.getParameter("gps_code_s"));



            //  surveyTypeBean.setSurvey_date(surveyTypeModel.convertToSqlDate(request.getParameter("survey_date")));

            //     surveyTypeBean.setMeter_no_s(request.getParameter("meter_no_s"));
            //     surveyTypeBean.setService_conn_no(request.getParameter("service_conn_no"));
            //     surveyTypeBean.setIvrs_no(request.getParameter("ivrs_no"));
            //   surveyTypeBean.setCity(city);

            //      surveyTypeBean.setSwitching_point_id(surveyTypeModel.getSwitchingPointId(request.getParameter("source_wattage")));

            /*surveyTypeBean.setController_model(request.getParameter("controller_model").trim());
            surveyTypeBean.setMobile_no(request.getParameter("mobile_no").trim());
            surveyTypeBean.setSim_no(request.getParameter("sim_no").trim());
            surveyTypeBean.setImei_no(request.getParameter("imei_no").trim());
            surveyTypeBean.setPanel_file_no(request.getParameter("panel_file_no").trim());
             */

            if (switching_point_detail_id == 0) {
                System.out.println("Inserting values by model......");
                if (surveyTypeModel.validationCheck(request.getParameter("service_conn_no"), request.getParameter("ivrs_no"), surveyTypeBean.getPole_no(), switching_point_detail_id, switching_rev_no, surveyTypeBean.getIsCheckedTrue())) {
                    surveyTypeModel.insertRecord(surveyTypeBean);
                }
            } else {
                System.out.println("Update values by model........");
                String isOnPole = request.getParameter("isCheckedTrue");

                //       surveyTypeBean.setPole_id(Integer.parseInt(request.getParameter("pole_id").trim()));
                //       surveyTypeBean.setPole_rev_no(Integer.parseInt(request.getParameter("pole_rev_no").trim()));

                surveyTypeBean.setSwitching_point_detail_id(switching_point_detail_id);
                surveyTypeBean.setSwitching_rev_no(switching_rev_no);
                if (task.equals("Revise")) {
                    if (surveyTypeModel.validationCheckforRevise(request.getParameter("service_conn_no"), request.getParameter("ivrs_no"), surveyTypeBean.getPole_no(), switching_point_detail_id, switching_rev_no, surveyTypeBean.getIsCheckedTrue())) {
                        surveyTypeModel.reviseRecord(surveyTypeBean);
                    }
                } else if (task.equals("Update")) {
                    if (surveyTypeModel.validationCheckforRevise(request.getParameter("service_conn_no"), request.getParameter("ivrs_no"), surveyTypeBean.getPole_no(), switching_point_detail_id, switching_rev_no, surveyTypeBean.getIsCheckedTrue())) {
                        surveyTypeModel.updateRecord(surveyTypeBean);
                    }
                }

            }

        }
        String service_conn_no = request.getParameter("service_conn_no");
        String meter_name_auto = request.getParameter("meter_name_auto");
        if(service_conn_no==null || service_conn_no.isEmpty())
            service_conn_no="";
        if(meter_name_auto==null || meter_name_auto.isEmpty())
            meter_name_auto="";

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
        noOfRowsInTable = surveyTypeModel.getNoOfRows(searchPoleNoSwitch, searchPoleType, searchPoleNo, searchAreaName, searchRoadName, service_conn_no_Search, searchSwitchingPtName, zone_search,service_conn_no,meter_name_auto);
        if(noOfRowsInTable==0)
        {
            noOfRowsInTable=newSwitchingPointSurveyModel.getNoOfRows("",service_conn_no,"","","",meter_name_auto,"","","","","");
        }
        if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
        else if (buttonAction.equals("Previous")) {
            int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
            if (temp < 0)
            {
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
        } else if (task.equals("Show All Records"))
        {
            searchPoleType = "";
            searchPoleNo = "";
            searchAreaName = "";
            searchRoadName = "";
            searchSwitchingPtName = "";
        }
        // Logic to show data in the table.
        List<SwitchingPointSurveyBean> SurveyTypeList = surveyTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNoSwitch, searchPoleType, searchPoleNo, searchAreaName, searchRoadName, service_conn_no_Search, searchSwitchingPtName, zone_search, service_conn_no, meter_name_auto);
        
        if(task.equals("showSurveyData")){
            if(SurveyTypeList.size()==0){
                countnum=1;
                String surveyId = request.getParameter("surveyId");
                //NewSwitchingPointSurveyModel newSwitchingPointSurveyModel = new NewSwitchingPointSurveyModel();
                SurveyTypeList = newSwitchingPointSurveyModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNo,service_conn_no,"","","",meter_name_auto,countnum,surveyId,"","","","");

               ////SurveyTypeList = meter.showData(lowerLimit, noOfRowsToDisplay, "", 0, 0, 0, "Y", "", "", "", "", "", "", "", "ALL", "", "Y", 0, "",SurveyTypeList,countnum,meter_name_auto,service_conn_no);
//                Iterator itr=SurveyTypeList.iterator();
//                SwitchingPointSurveyBean surveyTypeBean1 = new SwitchingPointSurveyBean();
//                while(itr.hasNext()){
//                    surveyTypeBean1=(SwitchingPointSurveyBean)itr.next();
//                }
//                request.setAttribute("surveyTypeBean", surveyTypeBean1);
            }else{
                request.setAttribute("surveyTypeList", SurveyTypeList);
            }
            Iterator itr=SurveyTypeList.iterator();
            SwitchingPointSurveyBean surveyTypeBean1 = new SwitchingPointSurveyBean();
            while(itr.hasNext()){
                surveyTypeBean1=(SwitchingPointSurveyBean)itr.next();
            }
            request.setAttribute("surveyTypeBean", surveyTypeBean1);
        }else{
            request.setAttribute("surveyTypeList", SurveyTypeList);
        }
        lowerLimit = lowerLimit + SurveyTypeList.size();
        noOfRowsTraversed = SurveyTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute(
                "noOfRowsTraversed", noOfRowsTraversed);
        
        if ((lowerLimit - noOfRowsTraversed) == 0)
        {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable)
        {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        // End of Search Table
 
        System.out.println("color is :"
                + surveyTypeModel.getMsgBgColor());
        request.setAttribute(
                "IDGenerator", new UniqueIDGenerator());
        request.setAttribute(
                "searchPoleType", searchPoleType);
            request.setAttribute(
                "searchPoleNo", searchPoleNo);
        request.setAttribute(
                "service_conn_no", service_conn_no);
        request.setAttribute(
                "searchMeterNameAuto", meter_name_auto);
        request.setAttribute(
                "searchSwitchingPtName", searchSwitchingPtName);
        request.setAttribute(
                "searchRoadName", searchRoadName);
        request.setAttribute(
                "searchAreaName", searchAreaName);
        if(!service_conn_no_Search.isEmpty() || service_conn_no_Search!=null)
            request.setAttribute("service_conn_no_Search", service_conn_no_Search);
        else
        {
            request.setAttribute("service_conn_no_Search",service_conn_no);
        }
        request.setAttribute("zone_search", zone_search);
        request.setAttribute(
                "searchPoleNoSwitch", searchPoleNoSwitch);
        request.setAttribute(
                "message", surveyTypeModel.getMessage());
        request.setAttribute(
                "messagee", surveyTypeModel.getMessagee());
        request.setAttribute(
                "msgBgColorr", surveyTypeModel.getMsgBgColorr());
        request.setAttribute(
                "msgBgColor", surveyTypeModel.getMsgBgColor());
        request.getRequestDispatcher(view).forward(request, response);
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);

    }
}
