/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.SurveyModel;
import com.survey.tableClasses.SurveyBean;
import com.survey.util.UniqueIDGenerator;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author JPSS
 */
public class SurveyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        System.out.println("this is survey Controller....");
        ServletContext ctx = getServletContext();
        SurveyModel surveyModel = new SurveyModel();
        surveyModel.setDriverClass(ctx.getInitParameter("driverClass"));
        surveyModel.setConnectionString(ctx.getInitParameter("connectionString"));
        surveyModel.setDb_username(ctx.getInitParameter("db_username"));
        surveyModel.setDb_password(ctx.getInitParameter("db_password"));
        surveyModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String mountingType = "";
        Map<String, String> map = new HashMap<String, String>();
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String survey_type = request.getParameter("action2");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getPole_No")) {
                    if (survey_type.equals("pole_type_survey")) {
                        list = surveyModel.getPoleNo(q);
                    } else {
                        list = surveyModel.getSwitchingPoleNo(q);
                    }
                } else if (JQstring.equals("getSwitchNo")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        mountingType = request.getParameter("action2");
                    }
                    list = surveyModel.getSwichingPointNo(q);
                } else if (JQstring.equals("getSwitchingPtName")) {
                    list = surveyModel.getSwitchingPtName(q);
                } else if (JQstring.equals("getSearchPole_No")) {
                    list = surveyModel.getSearchPole_No(q);
                } else if (JQstring.equals("getAutoSwitchType")) {
                    list = surveyModel.getAutoSwitchType(q);
                } else if (JQstring.equals("getMainSwitchType")) {
                    list = surveyModel.getMainSwitchType(q);
                }else if (JQstring.equals("getEnclosureType")) {
                    list = surveyModel.getEnclosureType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                surveyModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        String image_uploaded_for = "General";
        String destination_path = "";
        String string = "";
        boolean isCreated = true;
        List items = null;
        Iterator itr = null;
        List<File> list = new ArrayList<File>();
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
        fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
        fileItemFactory.setRepository(new File(""));
        ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
        try {
            items = uploadHandler.parseRequest(request);
            itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                if (item.isFormField()) {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                    map.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                    if (item.getName() == null || item.getName().isEmpty()) {
                        map.put(item.getFieldName(), "");
                    } else {
                        String image_name = item.getName();
                        image_name = image_name.substring(0, image_name.length());
                        int index = image_name.indexOf('.');
                        System.out.println(index);
                        String ext = image_name.substring(index, image_name.length());
                        System.out.println(image_name);
                        map.put(item.getFieldName(), item.getName());
                        destination_path = surveyModel.getRepositoryPath(image_uploaded_for);
                        File file = new File(destination_path);
                        if (image_name.startsWith("IMG")) {
                            file = new File(destination_path + "//" + "image");
                            if (!file.exists()) {
                                isCreated = file.mkdirs();
                            }

                            item.write(new File(destination_path + "\\" + image_name));
                            list.add(new File(destination_path + "\\" + image_name));
                        }
                        if (ext.equals(".json")) {

                            JSONParser parser = new JSONParser();
                            Map<String, String> json_map = new HashMap<String, String>();

                            try {
                                System.out.println("Reading JSON file from Java program");
                                FileReader fileReader = new FileReader(file + "\\" + image_name);
                                JSONObject json = (JSONObject) parser.parse(fileReader);

                                json.get("survey_by");
                                String result = json.get("survey_data").toString();
                                result = result.replaceAll("\\{", "").replaceAll("\\}", "");
                                String[] json_data = result.split(",");
                                for (int i = 1; i <= json_data.length; i++) {
                                    String[] json_result = json_data[i].split("=");
                                    System.out.println("Json Data :" + json_result[0] + " " + json_result[1]);
                                    json_map.put(json_result[0], json_result[1]);
                                }
                                System.out.println(" " + json.get("meter_status"));

                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        if (image_name.startsWith("VID")) {
                            file = new File(destination_path + "//" + "video");
                            if (!file.exists()) {
                                isCreated = file.mkdirs();
                            }

                            item.write(new File(destination_path + "\\" + image_name));
                            list.add(new File(destination_path + "\\" + image_name));
                        }
                    }
                }
            }
            itr = null;
            itr = items.iterator();
        } catch (Exception e) {
            System.out.println("Error is :" + e);
        }
        String task = map.get("task");
        if (task == null) {
            task = "";
        }
        if (task.equals("Save") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Cancel")) {
            int survey_id;
            int survey_rev_no;
            int switching_point_survey_id;
            int switching_point_survey_rev_no;
            int switching_rev_no;
            int switching_point_detail_id;
            int pole_id;
            int pole_rev_no;
            try {
                survey_id = Integer.parseInt(map.get("survey_id"));
            } catch (Exception e) {
                survey_id = 0;
            }
            try {
                survey_rev_no = Integer.parseInt(map.get("survey_rev_no"));
            } catch (Exception e) {
                survey_rev_no = 0;
            }
            try {
                switching_point_survey_id = Integer.parseInt(map.get("switching_point_survey_id"));
            } catch (Exception e) {
                switching_point_survey_id = 0;
            }
            try {
                switching_point_survey_rev_no = Integer.parseInt(map.get("switching_point_survey_rev_no"));
            } catch (Exception e) {
                switching_point_survey_rev_no = 0;
            }
            if (task.equals("Save AS New")) {
                survey_id = 0;
                survey_rev_no = 0;
                switching_point_survey_id = 0;
                switching_point_survey_rev_no = 0;
            }
            SurveyBean surveyBean = new SurveyBean();
            surveyBean.setSurvey_id(survey_id);
            surveyBean.setSurvey_rev_no(survey_rev_no);
            surveyBean.setSwitching_point_survey_id(switching_point_survey_id);
            surveyBean.setSwitching_point_survey_rev_no(switching_point_survey_rev_no);
            surveyBean.setSurvey_type(map.get("survey_type"));
            surveyBean.setSurvey_by(map.get("survey_by"));
            surveyBean.setSurvey_date(map.get("survey_date"));
            surveyBean.setStatus(map.get("status"));
            surveyBean.setSurvey_file_no(map.get("survey_file_no"));
            surveyBean.setSurvey_page_no(map.get("survey_page_no"));
            surveyBean.setB_phase(map.get("b_phase"));
            surveyBean.setContacter_functional(map.get("contacter_functional"));
            //      surveyBean.setCreated_date(request.getParameter("created_date"));
            surveyBean.setFuse_functional(map.get("fuse_functional"));
            surveyBean.setMccb_functional(map.get("mccb_functional"));
            surveyBean.setMeter_functional(map.get("meter_functional"));
            // surveyBean.setTimer_functional(map.get("timer_functional"));
            surveyBean.setMeter_no(map.get("meter_no"));
            surveyBean.setPole_no(map.get("pole_no"));
            surveyBean.setPower(map.get("power"));
            surveyBean.setR_phase(map.get("r_phase"));
            surveyBean.setFuse1(map.get("fuse_1"));
            surveyBean.setFuse2(map.get("fuse_2"));
            surveyBean.setFuse3(map.get("fuse_3"));
            surveyBean.setContacter_id(surveyModel.getContracterId(map.get("contacter_type")));
            surveyBean.setContacter_make_id(surveyModel.getContacterMakeId(map.get("contacter_make")));
            surveyBean.setContacter_capacity(map.get("contracter_capacity"));
            surveyBean.setMccb1(map.get("mccb_1"));
            surveyBean.setMccb2(map.get("mccb_2"));
            surveyBean.setMccb3(map.get("mccb_3"));
            surveyBean.setReason_id(surveyModel.getReasonId(map.get("reason_type")));
            surveyBean.setImage_name(map.get("image_name"));
            surveyBean.setFuse_id1(surveyModel.getFuseId(map.get("fuse_type1")));
            surveyBean.setFuse_id2(surveyModel.getFuseId(map.get("fuse_type2")));
            surveyBean.setFuse_id3(surveyModel.getFuseId(map.get("fuse_type3")));
            surveyBean.setMccb_id1(surveyModel.getMccbId(map.get("mccb_type1")));
            surveyBean.setMccb_id2(surveyModel.getMccbId(map.get("mccb_type2")));
            surveyBean.setMccb_id3(surveyModel.getMccbId(map.get("mccb_type3")));
            surveyBean.setNo_of_phase(Integer.parseInt(map.get("no_of_phase")));
            surveyBean.setMeter_phase(Integer.parseInt(map.get("meter_phase")));
            surveyBean.setMeter_reading(map.get("meter_reading"));
            surveyBean.setAuto_switch_type_id(surveyModel.getSwitchId(map.get("auto_switch_type")));
            surveyBean.setMain_switch_type_id(surveyModel.getSwitchId(map.get("main_switch_type")));
            surveyBean.setMain_switch_rating(map.get("main_switch_rating"));
            surveyBean.setEnclosure_type_id(surveyModel.getEnclosureTypeId(map.get("enclosure_type")));
            surveyBean.setMeter_status(map.get("meter_status"));
            if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                surveyBean.setRemark(map.get("remark1"));
                try {
                    surveyBean.setPole_id(Integer.parseInt(map.get("pole_id")));
                } catch (Exception e) {
                    surveyBean.setPole_id(0);
                }
                try {
                    surveyBean.setPole_rev_no(Integer.parseInt(map.get("pole_rev_no")));
                } catch (Exception e) {
                    surveyBean.setPole_rev_no(0);
                }
            } else {
                surveyBean.setRemark(map.get("remark2"));
                try {
                    surveyBean.setSwitching_point_detail_id(Integer.parseInt(map.get("switching_point_detail_id")));
                } catch (Exception e) {
                    surveyBean.setSwitching_point_detail_id(0);
                }
                try {
                    surveyBean.setSwitching_rev_no(Integer.parseInt(map.get("switching_rev_no")));
                } catch (Exception e) {
                    surveyBean.setSwitching_rev_no(0);
                }
            }
            surveyBean.setY_phase(map.get("y_phase"));
            //      surveyBean.setPole_id(surveyModel.getPoleId(request.getParameter("pole_no")));
            //      surveyBean.setSwitching_point_detail_id(surveyModel.getSwichingPointId(request.getParameter("pole_no_s")));
            if (survey_id == 0) {
                boolean flag = false;
                System.out.println("Inserting values by model......");
                if (surveyBean.getSurvey_type().equals("switching_type_survey")) {
                    String switching_id_rev = surveyModel.getSwitchingPoint_id(map.get("pole_no"));
                    surveyBean.setSwitching_point_detail_id(Integer.parseInt(switching_id_rev.split("_")[0]));
                    surveyBean.setSwitching_rev_no(Integer.parseInt(switching_id_rev.split("_")[1]));
                    flag = surveyModel.validationCheck(Integer.parseInt(switching_id_rev.split("_")[0]), Integer.parseInt(switching_id_rev.split("_")[1]), surveyBean.getSurvey_type());
                } else if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                    String pole_id_rev = surveyModel.getPoleId(map.get("pole_no"));
                    surveyBean.setPole_id(Integer.parseInt(pole_id_rev.split("_")[0]));
                    surveyBean.setPole_rev_no(Integer.parseInt(pole_id_rev.split("_")[1]));
                    flag = surveyModel.validationCheck(Integer.parseInt(pole_id_rev.split("_")[0]), Integer.parseInt(pole_id_rev.split("_")[1]), surveyBean.getSurvey_type());
                }
                if (flag) {
                    surveyModel.insertRecord(surveyBean, list);
                }
            } else {
                System.out.println("Revise values by model........");
                if (task.equals("Revise")) {
                    surveyModel.reviseRecord(surveyBean);
                } else {
                    surveyModel.cancelRecord(surveyBean);
                }
            }
        }
        // Start of Auto Completer code
        String searchPoleNo = "";
        String searchSwitchNo = "";
        // End of Auto Completer code
        searchPoleNo = map.get("searchPoleNo");
        searchSwitchNo = map.get("searchSwitchNo");
        try {
            if (searchPoleNo == null) {
                searchPoleNo = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchSwitchNo == null) {
                searchSwitchNo = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }

        // Start of Search Table
        try {
            lowerLimit = Integer.parseInt(map.get("lowerLimit"));
            noOfRowsTraversed = Integer.parseInt(map.get("noOfRowsTraversed"));
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        String buttonAction = map.get("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        //System.out.println("searching.......... " + searchSourceType);
        //noOfRowsInTable = surveyModel.getNoOfRows(searchPoleNo, searchSwitchNo);
        noOfRowsInTable = surveyModel.getNoOfRows(searchPoleNo);

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
        if (task.equals("Save") || task.equals("Cancel") || task.equals("Save AS New") || task.equals("Revise")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
        } else if (task.equals("Show All Records")) {
            searchPoleNo = "";
            searchSwitchNo = "";
        }
        // Logic to show data in the table.
        //List<SurveyBean> surveyTypeList = surveyModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNo, searchSwitchNo);
        List<SurveyBean> surveyTypeList = surveyModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNo);
        lowerLimit = lowerLimit + surveyTypeList.size();
        noOfRowsTraversed = surveyTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("surveyTypeList", surveyTypeList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        // End of Search Table
        System.out.println("color is :" + surveyModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchPoleNo", searchPoleNo);
        request.setAttribute("searchSwitchNo", searchSwitchNo);
        request.setAttribute("message", surveyModel.getMessage());
        request.setAttribute("msgBgColor", surveyModel.getMsgBgColor());
        request.getRequestDispatcher("/surveyView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
