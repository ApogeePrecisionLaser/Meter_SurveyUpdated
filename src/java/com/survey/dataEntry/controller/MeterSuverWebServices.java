/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.MeterSurveyWebServicesModel;
import com.survey.dataEntry.model.NewSwitchingPointSurveyModel;
import com.survey.dataEntry.model.TubeWellSurveyModel;
import com.survey.dbcon.DBConnection;
import com.survey.energyMeter.model.EnergyMeterWebServiceModel;
import com.survey.tableClasses.SurveyBean;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

//import org.json.JSONObject;
import javax.ws.rs.core.Response;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

import org.codehaus.jettison.json.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Administrator
 */
@Path("/")
public class MeterSuverWebServices {

    @Context
    ServletContext servletContext;

    FuseTypeController cont = new FuseTypeController();
    MeterSurveyWebServicesModel wsSurveyModel = null;
    TubeWellSurveyModel tubeWellSurveyModel = new TubeWellSurveyModel();

    @Context
// private WebServiceContext context;
    //ServletContext servletContext;// =    (ServletContext) context.getMessageContext().get(MessageContext.SERVLET_CONTEXT);

    @POST
    @Path("/hello")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public String sayPlainTextHello(JSONObject inputJsonObj) throws Exception {
        int survey_id = 0;
        String response = null;
        System.out.println("Hello meter_survey");
        List<File> list = new ArrayList<File>();
        SurveyBean surveyBean = new SurveyBean();
        wsSurveyModel = new MeterSurveyWebServicesModel();
        wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
        wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
        wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
        wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
        wsSurveyModel.setConnection();
//        tubeWellSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
//        tubeWellSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
//        tubeWellSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
//        tubeWellSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
//        tubeWellSurveyModel.setConnection();
        //     JSONArray json1 = wsSurveyModel.showData();
        //   System.out.println("View is" + json1.toString());
        String result = inputJsonObj.get("survey_data").toString();
        result = result.replaceAll("\\{", "").replaceAll("\\}", "");

//        try {
//
//            ObjectMapper mapper = new ObjectMapper();
//            String json = "{\"name\":\"mkyong\", \"age\":29}";
//
//            Map<String, Object> map = new HashMap<String, Object>();
//
//            // convert JSON string to Map
//            map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
//            });
//
//            System.out.println(map);
//
//        } catch (JsonGenerationException e) {
//            e.printStackTrace();
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        String[] json_data = result.split(",");
        for (int i = 0; i <= json_data.length - 1; i++) {
            String[] json_result = new String[2];
            json_result = json_data[i].split("=");
            try {
                if (json_result.length >= 2) {
                    //  json_result[1] = null;
                    System.out.println("Json Data :" + json_result[0] + " " + json_result[1]);
                    inputJsonObj.put(json_result[0].trim(), json_result[1].trim());
                } else {
                    json_result = new String[2];
                    json_result[1] = null;
                    inputJsonObj.put(json_result[0].trim(), json_result[1].trim());
                }

            } catch (Exception e) {

                //   inputJsonObj.put(json_result[0].trim(),JSONObject.class.);
                System.out.println("Exception occur: " + e);
            }

        }
        System.out.println(" " + inputJsonObj.get("meter_status"));

        try {
            surveyBean.setSurvey_type(inputJsonObj.get("survey_type").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_type(null);
        }
        try {
            surveyBean.setSurvey_by(inputJsonObj.get("survey_by").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_by(null);
        }
        try {
            surveyBean.setSurvey_with_contact(inputJsonObj.get("survey_with").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_with_contact(null);
        }
        try {
            surveyBean.setSurvey_with_name(inputJsonObj.get("survey_with_name").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_with_name(null);
        }
        try {
            surveyBean.setMeter_status(inputJsonObj.get("meter_status").toString());
        } catch (Exception e) {
            surveyBean.setMeter_status(null);
        }
        try {
            surveyBean.setSurvey_file_no(inputJsonObj.get("file_no").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_file_no(null);
        }
        try {
            surveyBean.setSurvey_page_no(inputJsonObj.get("page_no").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_page_no(null);
        }
        try {
            surveyBean.setB_phase(inputJsonObj.get("b_phase").toString());
        } catch (Exception e) {
            surveyBean.setB_phase(null);
        }
        try {
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                surveyBean.setContacter_functional(inputJsonObj.get("contacter_status").toString());
            } else {
                surveyBean.setStarter_functional(inputJsonObj.get("contacter_status").toString());
            }

        } catch (Exception e) {
            surveyBean.setContacter_functional(null);
        }
        try {
            surveyBean.setFuse_functional(inputJsonObj.get("fuse_status").toString());
        } catch (Exception e) {
            surveyBean.setFuse_functional(null);
        }
        try {
            surveyBean.setMccb_functional(inputJsonObj.get("mccb_status").toString());
        } catch (Exception e) {
            surveyBean.setMccb_functional(null);
        }
        try {
            surveyBean.setMeter_functional(inputJsonObj.get("meter_functional").toString());
        } catch (Exception e) {
            surveyBean.setMeter_functional(null);
        }
        try {
            surveyBean.setMeter_status(inputJsonObj.get("meter_status").toString());
        } catch (Exception e) {
            surveyBean.setMeter_status(null);
        }
        try {
            surveyBean.setMeter_no(inputJsonObj.get("meter_no").toString());
        } catch (Exception e) {
            surveyBean.setMeter_no(null);
        }
        try {
            surveyBean.setPole_no(inputJsonObj.get("db_pole_no").toString());
        } catch (Exception e) {
            surveyBean.setPole_no(null);
        }
        try {
            surveyBean.setPower(inputJsonObj.get("power").toString());
        } catch (Exception e) {
            surveyBean.setPower(null);
        }
        try {
            surveyBean.setR_phase(inputJsonObj.get("r_phase").toString());
        } catch (Exception e) {
            surveyBean.setR_phase(null);
        }
        try {
            surveyBean.setFuse1(inputJsonObj.get("fuse1").toString());
        } catch (Exception e) {
            surveyBean.setFuse1(null);
        }
        try {
            surveyBean.setFuse2(inputJsonObj.get("fuse2").toString());
        } catch (Exception e) {
            surveyBean.setFuse2(null);
        }
        try {
            surveyBean.setFuse3(inputJsonObj.get("fuse3").toString());
        } catch (Exception e) {
            surveyBean.setFuse3(null);
        }
        try {
            surveyBean.setMccb1(inputJsonObj.get("mccb1").toString());
        } catch (Exception e) {
            surveyBean.setMccb1(null);
        }
        try {
            surveyBean.setMccb2(inputJsonObj.get("mccb2").toString());
        } catch (Exception e) {
            surveyBean.setMccb2(null);
        }
        try {
            surveyBean.setMccb3(inputJsonObj.get("mccb3").toString());
        } catch (Exception e) {
            surveyBean.setMccb3(null);
        }
        try {
            surveyBean.setSurvey_date(inputJsonObj.get("survey_date").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_date(null);
        }
        try {
            surveyBean.setMeter_name_auto(inputJsonObj.get("meter_name_auto").toString());
        } catch (Exception e) {
            surveyBean.setMeter_name_auto(null);
        }
        try {
            surveyBean.setService_conn_no(inputJsonObj.get("service_no").toString());
        } catch (Exception e) {
            surveyBean.setService_conn_no(null);
        }
        try {
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                if (!wsSurveyModel.getContracterId(inputJsonObj.get("contacter_type").toString()).isEmpty()) {
                    surveyBean.setContacter_id(wsSurveyModel.getContracterId(inputJsonObj.get("contacter_type").toString()));
                } else {
                    surveyBean.setContacter_type(inputJsonObj.get("contacter_type").toString());

                    if (wsSurveyModel.insertContacterRecord(surveyBean) > 0) {
                        surveyBean.setContacter_id(wsSurveyModel.getContracterId(inputJsonObj.get("contacter_type").toString()));
                    }
                }
            } else {

                if (wsSurveyModel.getStarterId(inputJsonObj.get("contacter_type").toString()) != 0) {
                    surveyBean.setStarter_id(wsSurveyModel.getStarterId(inputJsonObj.get("contacter_type").toString()));
                } else {
                    surveyBean.setStarter_type(inputJsonObj.get("contacter_type").toString());

                    if (wsSurveyModel.insertContacterRecord(surveyBean) > 0) {
                        surveyBean.setStarter_id(wsSurveyModel.getStarterId(inputJsonObj.get("contacter_type").toString()));
                    }
                }
            }

        } catch (Exception e) {
            surveyBean.setContacter_id(null);
        }
        try {
            if (!wsSurveyModel.getFuseId(inputJsonObj.get("fuse1_type").toString()).isEmpty()) {
                surveyBean.setFuse_id1(wsSurveyModel.getFuseId(inputJsonObj.get("fuse1_type").toString()));
            } else {
                surveyBean.setFuse_type1(inputJsonObj.get("fuse1_type").toString());
                if (wsSurveyModel.insertFuseRecord(inputJsonObj.get("fuse1_type").toString()) > 0) {
                    surveyBean.setFuse_id1(wsSurveyModel.getFuseId(inputJsonObj.get("fuse1_type").toString()));

                } else {
                    surveyBean.setFuse_id1(null);
                }
            }

        } catch (Exception e) {
            surveyBean.setFuse_id1(null);
        }
        try {
            if (!wsSurveyModel.getFuseId(inputJsonObj.get("fuse2_type").toString()).isEmpty()) {
                surveyBean.setFuse_id2(wsSurveyModel.getFuseId(inputJsonObj.get("fuse2_type").toString()));
            } else {
                surveyBean.setFuse_type2(inputJsonObj.get("fuse2_type").toString());
                if (wsSurveyModel.insertFuseRecord(inputJsonObj.get("fuse2_type").toString()) > 0) {
                    surveyBean.setFuse_id2(wsSurveyModel.getFuseId(inputJsonObj.get("fuse2_type").toString()));
                } else {
                    surveyBean.setFuse_id2(null);
                }
            }
        } catch (Exception e) {
            surveyBean.setFuse_id2(null);
        }
        try {
            if (!wsSurveyModel.getFuseId(inputJsonObj.get("fuse3_type").toString()).isEmpty()) {
                surveyBean.setFuse_id3(wsSurveyModel.getFuseId(inputJsonObj.get("fuse3_type").toString()));
            } else {
                surveyBean.setFuse_type3(inputJsonObj.get("fuse3_type").toString());
                if (wsSurveyModel.insertFuseRecord(inputJsonObj.get("fuse3_type").toString()) > 0) {
                    surveyBean.setFuse_id3(wsSurveyModel.getFuseId(inputJsonObj.get("fuse3_type").toString()));
                } else {
                    surveyBean.setFuse_id3(null);
                }
            }
        } catch (Exception e) {
            surveyBean.setFuse_id3(null);
        }
        try {
            if (!wsSurveyModel.getMccbId(inputJsonObj.get("mccb1_type").toString()).isEmpty()) {
                surveyBean.setMccb_id1(wsSurveyModel.getMccbId(inputJsonObj.get("mccb1_type").toString()));
            } else {
                surveyBean.setMccb_type1(inputJsonObj.get("mccb1_type").toString());
                if (wsSurveyModel.insertMccbRecord(inputJsonObj.get("mccb1_type").toString()) > 0) {
                    surveyBean.setMccb_id1(wsSurveyModel.getMccbId(inputJsonObj.get("mccb1_type").toString()));
                }
            }

        } catch (Exception e) {
            surveyBean.setMccb_id1(null);
        }
        try {
            if (!wsSurveyModel.getMccbId(inputJsonObj.get("mccb2_type").toString()).isEmpty()) {
                surveyBean.setMccb_id2(wsSurveyModel.getMccbId(inputJsonObj.get("mccb2_type").toString()));
            } else {
                surveyBean.setMccb_type2(inputJsonObj.get("mccb2_type").toString());
                if (wsSurveyModel.insertMccbRecord(inputJsonObj.get("mccb2_type").toString()) > 0) {
                    surveyBean.setMccb_id2(wsSurveyModel.getMccbId(inputJsonObj.get("mccb2_type").toString()));
                }
            }

        } catch (Exception e) {

            surveyBean.setMccb_id2(null);
        }
        try {
            if (!wsSurveyModel.getMccbId(inputJsonObj.get("mccb3_type").toString()).isEmpty()) {
                surveyBean.setMccb_id3(wsSurveyModel.getMccbId(inputJsonObj.get("mccb3_type").toString()));
            } else {
                surveyBean.setMccb_type3(inputJsonObj.get("mccb3_type").toString());
                if (wsSurveyModel.insertMccbRecord(inputJsonObj.get("mccb3_type").toString()) > 0) {
                    surveyBean.setMccb_id3(wsSurveyModel.getMccbId(inputJsonObj.get("mccb3_type").toString()));
                }
            }

        } catch (Exception e) {

            surveyBean.setMccb_id3(null);
        }
        try {
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                if (!wsSurveyModel.getContacterMakeId(inputJsonObj.get("contacter_make").toString()).isEmpty()) {
                    surveyBean.setContacter_make_id(wsSurveyModel.getContacterMakeId(inputJsonObj.get("contacter_make").toString()));
                } else {
                    surveyBean.setContacter_make(inputJsonObj.get("contacter_make").toString());
                    if (wsSurveyModel.insertContacterMakeRecord(surveyBean) > 0) {
                        surveyBean.setContacter_make_id(wsSurveyModel.getContacterMakeId(inputJsonObj.get("contacter_make").toString()));
                    }
                }
            } else {
                if (wsSurveyModel.getStarterMakeId(inputJsonObj.get("contacter_make").toString()) != 0) {
                    surveyBean.setStarter_make_id(wsSurveyModel.getStarterMakeId(inputJsonObj.get("contacter_make").toString()));
                } else {
                    surveyBean.setStarter_make(inputJsonObj.get("contacter_make").toString());
                    if (wsSurveyModel.insertContacterMakeRecord(surveyBean) > 0) {
                        surveyBean.setStarter_make_id(wsSurveyModel.getStarterMakeId(inputJsonObj.get("contacter_make").toString()));
                    }
                }
            }

        } catch (Exception e) {

            surveyBean.setContacter_make_id(null);
        }
        try {
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                surveyBean.setContacter_capacity(inputJsonObj.get("contacter_capacity").toString());
            } else {
                surveyBean.setStarter_capacity(inputJsonObj.get("contacter_capacity").toString());
            }

        } catch (Exception e) {
            surveyBean.setContacter_capacity(null);
        }
        try {
            if (!wsSurveyModel.getSwitchId(inputJsonObj.get("auto_switch").toString()).isEmpty()) {
                surveyBean.setAuto_switch_type_id(wsSurveyModel.getSwitchId(inputJsonObj.get("auto_switch").toString()));
            } else {
                surveyBean.setAuto_switch_type(inputJsonObj.get("auto_switch").toString());
                if (wsSurveyModel.insertAutoSwitchRecord(surveyBean) > 0) {
                    surveyBean.setAuto_switch_type_id(wsSurveyModel.getSwitchId(inputJsonObj.get("auto_switch").toString()));
                }
            }
        } catch (Exception e) {
            surveyBean.setAuto_switch_type_id(null);
        }
        try {
            if (!wsSurveyModel.getSwitchId(inputJsonObj.get("main_switch").toString()).isEmpty()) {
                surveyBean.setMain_switch_type_id(wsSurveyModel.getSwitchId(inputJsonObj.get("main_switch").toString()));
            } else {
                surveyBean.setMain_switch_type(inputJsonObj.get("main_switch").toString());
                if (wsSurveyModel.insertMainSwitchRecord(surveyBean) > 0) {
                    surveyBean.setMain_switch_type_id(wsSurveyModel.getSwitchId(inputJsonObj.get("main_switch").toString()));
                }
            }

        } catch (Exception e) {
            surveyBean.setMain_switch_type_id(null);
        }
        try {
            surveyBean.setMain_switch_rating(inputJsonObj.get("main_switch_rating").toString());
        } catch (Exception e) {
            surveyBean.setMain_switch_rating(null);
        }
        try {
            surveyBean.setNo_of_phase(Integer.parseInt(inputJsonObj.get("phase").toString().replace("]", "")));
        } catch (Exception e) {
            surveyBean.setNo_of_phase(0);
        }
        try {
            surveyBean.setMeter_phase(Integer.parseInt(inputJsonObj.get("meter_phase").toString()));
        } catch (Exception e) {
            surveyBean.setMeter_phase(0);
        }
        try {
            surveyBean.setMeter_reading(inputJsonObj.get("meter_reading").toString());
        } catch (Exception e) {
            surveyBean.setMeter_reading(null);
        }
        try {
            surveyBean.setLatitude(inputJsonObj.get("latitude").toString());
        } catch (Exception e) {
            surveyBean.setLatitude(null);
        }
        try {
        } catch (Exception e) {
        }
        try {
            surveyBean.setLongitude(inputJsonObj.get("longitude").toString());
        } catch (Exception e) {
            surveyBean.setLongitude(null);
        }
//        try {
//            surveyBean.setImage_name(inputJsonObj.get("img_name").toString());
//        } catch (Exception e) {
//            surveyBean.setImage_name(null);
//        }

//        try {
//         org.json.JSONObject jsn = new org.json.JSONObject(inputJsonObj.toString());
//        org.json.JSONArray jsonArraay = jsn.getJSONArray("image");
//        int size = jsonArraay.length();
//        for (int i = 0; i < size; i++) {
//            org.json.JSONObject jsonObject = jsonArraay.getJSONObject(i);
//            byte[] fileAsBytes = new BASE64Decoder().decodeBuffer(jsonObject.getString("byte_arr"));
//            String fileName = jsonObject.getString("imgname");
//            surveyBean.setImage_name(fileName);
//            String path = "C:/ssadvt_repository/meter_survey/survey_image/tube_well";
//            //makeDirectory(path);
//            String file = path + "/" + fileName;
//            FileOutputStream outputStream = new FileOutputStream(file);
//            outputStream.write(fileAsBytes);
//            outputStream.close();
//         // int  affect = webServiceModel.insertImageRecord(fileName, id, 3);
//           // drive d=new drive();
//          //  d.drive(fileName,file,affect,affected+"");
//        }
//        } catch (Exception e) {
//            System.out.println("Image exception::"+e);
//        }
        try {
            surveyBean.setSurvey_pole_no(inputJsonObj.get("pole_no").toString());
        } catch (Exception e) {
            surveyBean.setSurvey_pole_no(null);
        }
        try {
            surveyBean.setPole_no(inputJsonObj.get("db_pole_no").toString());
        } catch (Exception e) {
            surveyBean.setPole_no(null);
        }
        try {
            if (!wsSurveyModel.getEnclosureTypeId(inputJsonObj.get("enclosure").toString()).isEmpty()) {
                surveyBean.setEnclosure_type_id(wsSurveyModel.getEnclosureTypeId(inputJsonObj.get("enclosure").toString()));
            } else {
                surveyBean.setEnclosure_type(inputJsonObj.get("enclosure").toString());
                if (wsSurveyModel.insertEnclosureRecord(surveyBean) > 0) {
                    surveyBean.setEnclosure_type_id(wsSurveyModel.getEnclosureTypeId(inputJsonObj.get("enclosure").toString()));
                }
            }
        } catch (Exception e) {
            surveyBean.setEnclosure_type_id(null);
        }
        try {
            surveyBean.setReason_id(wsSurveyModel.getReasonId(inputJsonObj.get("meter_remark").toString()));
        } catch (Exception e) {
            surveyBean.setReason_id(null);
        }

        try {
            if (surveyBean.getSurvey_type().equals("Pole")) {
                // surveyBean.setRemark(inputJsonObj.get("remark1").toString());
                try {
                    surveyBean.setPole_id(Integer.parseInt(inputJsonObj.get("pole_id").toString()));
                } catch (Exception e) {
                    surveyBean.setPole_id(0);
                }
//            try {
//                surveyBean.setPole_rev_no(Integer.parseInt(inputJsonObj.get("pole_rev_no").toString()));
//            } catch (Exception e) {
//                surveyBean.setPole_rev_no(0);
//            }
            } else if (surveyBean.getSurvey_type().equals("Switching Point")) {
                surveyBean.setRemark(inputJsonObj.get("survey_remark").toString());
//            try {
//                //surveyBean.setSwitching_point_detail_id(Integer.parseInt(inputJsonObj.get("switching_point_detail_id").toString()));
//                surveyBean.setSwitching_point_detail_id(0);
//            } catch (Exception e) {
//                surveyBean.setSwitching_point_detail_id(0);
//            }
//            try {
//                surveyBean.setSwitching_rev_no(Integer.parseInt(inputJsonObj.get("switching_rev_no").toString()));
//            } catch (Exception e) {
//                surveyBean.setSwitching_rev_no(0);
//            }
            }
        } catch (Exception e) {
            System.out.println("Exception occurs: " + e);
        }
        surveyBean.setY_phase(inputJsonObj.get("y_phase").toString());
        String input = (String) inputJsonObj.get("survey_by");
        String output = "The input you sent is :" + input;
        String age = (String) inputJsonObj.get("pole_no");
        System.out.println(age);
        JSONObject outputJsonObj = new JSONObject();
        outputJsonObj.put("output", output);
        if (survey_id == 0) {
            boolean flag = false;
            System.out.println("Inserting values by model......");
            if (surveyBean.getSurvey_type().equals("Switching Point")) {
                if (wsSurveyModel.getSwitchingPoint_id(inputJsonObj.get("db_pole_no").toString()).isEmpty()) {
                    String switching_id_rev = wsSurveyModel.getSwitchingPoint_id(inputJsonObj.get("db_pole_no").toString());
                    surveyBean.setPole_id(Integer.parseInt(switching_id_rev));
                    if (switching_id_rev != null || !switching_id_rev.isEmpty()) {
                        flag = true;
                    }
                    //surveyBean.setSwitching_point_detail_id(Integer.parseInt(switching_id_rev.split("_")[0]));
                    // surveyBean.setSwitching_rev_no(Integer.parseInt(switching_id_rev.split("_")[1]));
                    // flag = wsSurveyModel.validationCheck(Integer.parseInt(switching_id_rev.split("_")[0]), Integer.parseInt(switching_id_rev.split("_")[1]), surveyBean.getSurvey_type());
                } else {
                    surveyBean.setPole_id(0);
                    flag = true;
                }
            }
            if (surveyBean.getSurvey_type().equals("Tube Well")) {
                surveyBean.setSurvey_type("tubewell_type_survey");
                if (!wsSurveyModel.getSwitchingPoint_id(inputJsonObj.get("db_pole_no").toString()).isEmpty()) {
                    String switching_id_rev = wsSurveyModel.getSwitchingPoint_id(inputJsonObj.get("db_pole_no").toString());
                    surveyBean.setPole_id(Integer.parseInt(switching_id_rev));
                    if (switching_id_rev != null || !switching_id_rev.isEmpty()) {
                        flag = true;
                    }
                    //surveyBean.setSwitching_point_detail_id(Integer.parseInt(switching_id_rev.split("_")[0]));
                    // surveyBean.setSwitching_rev_no(Integer.parseInt(switching_id_rev.split("_")[1]));
                    // flag = wsSurveyModel.validationCheck(Integer.parseInt(switching_id_rev.split("_")[0]), Integer.parseInt(switching_id_rev.split("_")[1]), surveyBean.getSurvey_type());
                } else {
                    surveyBean.setPole_id(0);
                    flag = true;
                }
            } else if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
                String pole_id_rev = wsSurveyModel.getPoleId(inputJsonObj.get("db_pole_no").toString());

                surveyBean.setPole_id(Integer.parseInt(pole_id_rev.split("_")[0]));
                surveyBean.setPole_rev_no(Integer.parseInt(pole_id_rev.split("_")[1]));
                flag = wsSurveyModel.validationCheck(Integer.parseInt(pole_id_rev.split("_")[0]), Integer.parseInt(pole_id_rev.split("_")[1]), surveyBean.getSurvey_type());
            }
            String insertFlag = "";
            try {
                insertFlag = inputJsonObj.get("flag").toString();
            } catch (Exception ex) {
                insertFlag = "";
            }
            if (flag && !insertFlag.equals("1")) {

                // test for store multiple images
                int survey_id1 = wsSurveyModel.insertRecord(surveyBean, list);
//                System.out.println("survey id is:::" + survey_id1);
//                org.json.JSONObject jsn = new org.json.JSONObject(inputJsonObj.toString());
//                org.json.JSONArray jsonArraay = jsn.getJSONArray("image");
//                int size = jsonArraay.length();
//                int survey_gen_image_map_id=0;
//                for (int i = 0; i < size; i++) {
//                org.json.JSONObject jsonObject = jsonArraay.getJSONObject(i);
//                byte[] fileAsBytes = new BASE64Decoder().decodeBuffer(jsonObject.getString("byte_arr"));
//                String fileName = jsonObject.getString("imgname");
//                surveyBean.setImage_name(fileName);
//                String path = "C:/ssadvt_repository/meter_survey/survey_image/tube_well/survey_id_"+survey_id1;
//                makeDirectory(path);
//                String file = path + "/" + fileName;
//                FileOutputStream outputStream = new FileOutputStream(file);
//                outputStream.write(fileAsBytes);
//                outputStream.close();
//                System.out.println("image name is ::" +fileName);
//                int  gen_image_detail_id = wsSurveyModel.insertImageRecord(fileName);
//                System.out.println("gen_image_detail_id is.. if image is exist::" + gen_image_detail_id);
//                survey_gen_image_map_id=wsSurveyModel.insertSurveyImageMapRecord(survey_id1,gen_image_detail_id);
//                // drive d=new drive();
//                //  d.drive(fileName,file,affect,affected+"");
//             }
                // end of testing
                if (survey_id1 > 0) {
//                     if (survey_gen_image_map_id > 0) {
//                    response = Response.ok(inputJsonObj, MediaType.APPLICATION_JSON).build();
//                }else {
//                    //    response=Response.
//                }
                    response = "success";
                } else {
                    //    response=Response.
                }

            }
        }
        wsSurveyModel.closeConnection();
        return response;
    }

    @POST
    @Path("/selectedSurveyRecords")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject surveyLast5Records(String dataString) {
        JSONObject obj = new JSONObject();
        try {
            String sArray[] = dataString.split(",");
            int survey_id = Integer.parseInt(sArray[0]);
            String ivrs_no = sArray[1];
            String survey_date = sArray[2];
            wsSurveyModel = new MeterSurveyWebServicesModel();
            wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
            wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
            wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
            wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
            wsSurveyModel.setConnection();
            //JSONObject obj = new JSONObject();
            JSONArray json = null;

            json = wsSurveyModel.surveyRecordOfSelectedDateTime(survey_id, ivrs_no, survey_date);
            obj.put("selectedSurveyRecords", json);
        } catch (Exception e) {
            System.out.println("Error in MeterSurveyWebServices 'requestData' url calling getWardData()..." + e);
        }
        wsSurveyModel.closeConnection();

        return obj;
    }

    @POST
    @Path("/last5SurveyDateTime")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject last5SurveyDateTime(String ivrs_no) {
        wsSurveyModel = new MeterSurveyWebServicesModel();
        wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
        wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
        wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
        wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
        wsSurveyModel.setConnection();
        JSONObject obj = new JSONObject();
        JSONArray json = null;
        try {
            json = wsSurveyModel.last5SurveyDateTime(ivrs_no);
            obj.put("last5SurveyDateTime", json);
        } catch (Exception e) {
            System.out.println("Error in MeterSurveyWebServices 'requestData' url calling getWardData()..." + e);
        }
        wsSurveyModel.closeConnection();

        return obj;
    }

    @POST
    @Path("/getImageTypeAndCount")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject getImageCount(String survey_id) {
        JSONObject obj = new JSONObject();
        try {

            wsSurveyModel = new MeterSurveyWebServicesModel();
            wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
            wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
            wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
            wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
            wsSurveyModel.setConnection();
            //JSONObject obj = new JSONObject();
            JSONArray json = null;
            json = wsSurveyModel.imageCount(survey_id);
            obj.put("selectedSurveyRecords", json);
        } catch (Exception e) {
            System.out.println("Error in MeterSurveyWebServices 'getImageTypeAndCount' url calling imageCount()..." + e);
        }
        wsSurveyModel.closeConnection();

        return obj;
    }

    @POST
    @Path("/sendAttachment")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendMsgAttachment(String json) {
        //messageViewModel msgModel = new messageViewModel();
        wsSurveyModel = new MeterSurveyWebServicesModel();
        wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
        wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
        wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
        wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
        wsSurveyModel.setConnection();
        Response.ResponseBuilder responseBuilder = null;
        try {

            String s[] = json.split(",");
            String image_name = s[0];
            String survey_id = s[1];
            String image_type_id = s[2];
            String survey_type = wsSurveyModel.getSurveyType(survey_id);

            if (survey_type.equals("tubewell_type_survey")) {
                survey_type = "tube_well";
            } else {
                survey_type = "switching_point";
            }

            String image_path = wsSurveyModel.getAttachmentPath(image_name, survey_id, image_type_id, survey_type);
            File file = new File(image_path);
            responseBuilder = Response.ok((Object) file);
            responseBuilder.header("Content-Disposition", "attachment; filename=\" " + image_name + "\"");

        } catch (Exception ex) {
            System.out.println("ERROR : in sendMsgAttachment() in webServiceData : " + ex);
        }

        wsSurveyModel.closeConnection();
        return responseBuilder.build();
    }

    // for insert image record
    @POST
    @Path("/helloImage")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)

    public String sayPlainTextHelloForImage(JSONObject inputJsonObj) throws Exception {

        wsSurveyModel = new MeterSurveyWebServicesModel();
        wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
        wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
        wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
        wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
        wsSurveyModel.setConnection();
        int survey_id1 = 0;
        String response = null;
        String service_no = inputJsonObj.get("service_no").toString();
        String survey_type = inputJsonObj.get("survey_type").toString();
        String survey_date = inputJsonObj.get("survey_date").toString();
        survey_id1 = wsSurveyModel.getSurveyIdForImage(service_no, survey_type, survey_date);
        System.out.println("survey:" + survey_id1);
        org.json.JSONObject jsn = new org.json.JSONObject(inputJsonObj.toString());
        org.json.JSONArray jsonArraay = jsn.getJSONArray("image");
        try {
            int size = jsonArraay.length();
            int survey_gen_image_map_id = 0;
            for (int i = 0; i < size; i++) {
                org.json.JSONObject jsonObject = jsonArraay.getJSONObject(i);
                byte[] fileAsBytes = new BASE64Decoder().decodeBuffer(jsonObject.getString("byte_arr"));
                String fileName = jsonObject.getString("imgname");
                String image_type = jsonObject.getString("type");
                int image_type_id = wsSurveyModel.getImage_type_id(image_type);
                try {
                    String path = "C:/ssadvt_repository/meter_survey/survey_image/tube_well/survey_id_" + survey_id1;

                    wsSurveyModel.makeDirectory(path);

                    String file = path + "/" + fileName;
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(fileAsBytes);
                    outputStream.close();
                    int gen_image_detail_id = wsSurveyModel.insertImageRecord(fileName, image_type_id);
                    survey_gen_image_map_id = wsSurveyModel.insertSurveyImageMapRecord(survey_id1, gen_image_detail_id);
                    // drive d=new drive();
                    //  d.drive(fileName,file,affect,affected+"");
                } catch (Exception ex) {
                    System.out.println("Exception in file image insert:" + ex);
                }

                if (survey_gen_image_map_id > 0) {
                    //response = Response.ok(inputJsonObj, MediaType.APPLICATION_JSON).build();
                    response = "success";
                } else {
                    // response="image";
                }
            }
        } catch (Exception e) {
            System.out.println("Exception in image insertion web service:" + e);
        }
        wsSurveyModel.closeConnection();

        return response;
    }
    // for insert pdf record

    @POST
    @Path("/helloPDF")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)

    public String sayPlainTextHelloForPDF(JSONObject inputJsonObj) throws Exception {

        wsSurveyModel = new MeterSurveyWebServicesModel();
        wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
        wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
        wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
        wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
        wsSurveyModel.setConnection();
        String response = null;
        try {
            int survey_id1 = 0;
            String service_no = inputJsonObj.get("service_no").toString();
            String survey_type = inputJsonObj.get("survey_type").toString();
            String survey_date = inputJsonObj.get("survey_date").toString();
            survey_id1 = wsSurveyModel.getSurveyIdForImage(service_no, survey_type, survey_date);
            org.json.JSONObject jsn = new org.json.JSONObject(inputJsonObj.toString());
            org.json.JSONArray jsonArraay = jsn.getJSONArray("image");
            int size = jsonArraay.length();
            int survey_gen_image_map_id = 0;
            for (int i = 0; i < size; i++) {
                org.json.JSONObject jsonObject = jsonArraay.getJSONObject(i);
                byte[] fileAsBytes = new BASE64Decoder().decodeBuffer(jsonObject.getString("byte_arr"));
                String fileName = jsonObject.getString("imgname");
                String path = "C:/ssadvt_repository/meter_survey/survey_image/tube_well/survey_id_" + survey_id1;
                makeDirectory(path);
                String file = path + "/" + fileName;
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(fileAsBytes);
                outputStream.close();
                int gen_image_detail_id = wsSurveyModel.insertPDFRecord(fileName);
                survey_gen_image_map_id = wsSurveyModel.insertSurveyImageMapRecord(survey_id1, gen_image_detail_id);
                // drive d=new drive();
                //  d.drive(fileName,file,affect,affected+"");
                if (survey_gen_image_map_id > 0) {
                    //response = Response.ok(inputJsonObj, MediaType.APPLICATION_JSON).build();
                    response = "success";
                } else {
                    // response="image";
                }

            }
        } catch (Exception e) {

        }
        wsSurveyModel.closeConnection();
        return response;
    }

    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            result = directory.mkdir();
        }
        return result;
    }

    @POST
    @Path("/requestData")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject showData(String imei) {
        JSONObject obj = new JSONObject();
        try {
            System.out.println("imei number -" + imei);
            System.out.println("Access showData in MeterSurveyWebServices " + new Date());

            wsSurveyModel = new MeterSurveyWebServicesModel();
            //wsSurveyModel.setConnection();
            wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
            wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
            wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
            wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
            wsSurveyModel.setConnection();
            //String imei = "";
            JSONArray json = null;
            try {
                if (imei != null && !imei.isEmpty()) {
                    json = wsSurveyModel.showData(imei);/// meters table data
                } else {
                    json = wsSurveyModel.showData("");
                }
                obj.put("Data", json);
            } catch (Exception e) {
                System.out.println("requestData showData().." + e);
            }
            try {
                //json=null;
                json = wsSurveyModel.getTubeWellBoreData();
                obj.put("tubewell_bore_data", json);
            } catch (Exception e) {
                System.out.println(" requestData getTubeWellBoreData().." + e);
            }
            try {
                json = wsSurveyModel.getTypeOfUseData();
                obj.put("type_of_use", json);
            } catch (Exception e) {
                System.out.println("requestData getTypeOfUseData().." + e);
            }
            try {
                json = wsSurveyModel.getBoreCasingTypeData();
                obj.put("bore_casing_type", json);
            } catch (Exception e) {
                System.out.println("requestData getBoreCasingTypeData()..." + e);
            }
            try {
                json = wsSurveyModel.getMotorTypeData();
                obj.put("motor_type", json);
            } catch (Exception e) {
                System.out.println("requestData getBoreCasingTypeData()..." + e);
            }

//         json = wsSurveyModel.getWardData();
//         obj.put("ward_data", json);
            try {
                json = wsSurveyModel.getImageType();
                obj.put("img_type", json);
            } catch (Exception e) {
                System.out.println("requestData getImageType()..." + e);
            }

            try {
                json = wsSurveyModel.getZone_m_data();
                obj.put("zone_m", json);
            } catch (Exception e) {
                System.out.println("Error in MeterSurveyWebServices 'requestData' url calling getImageType()..." + e);
            }

            try {
                json = wsSurveyModel.getWard_m_data();
                obj.put("ward_m", json);
            } catch (Exception e) {
                System.out.println("Error in MeterSurveyWebServices 'requestData' url calling getImageType()..." + e);
            }

            try {
                json = wsSurveyModel.getArea_data();
                obj.put("area", json);
            } catch (Exception e) {
                System.out.println("Error in MeterSurveyWebServices 'requestData' url calling getImageType()..." + e);
            }

            NewSwitchingPointSurveyModel nsp = new NewSwitchingPointSurveyModel();
            nsp.setConnection(wsSurveyModel.getConnection());
            json = nsp.showSwitchingJsonData();
            obj.put("SwitchingPointData", json);

            json = nsp.showJsonData();
//showSwitchingJsonData
            obj.put("SurveyData", json);

            wsSurveyModel.closeConnection();
            nsp.closeConnection();

            System.out.println("Data Retrived : " + new Date() + " " + json);

        } catch (Exception ex) {
            Logger.getLogger(MeterSuverWebServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }

    @POST
    @Path("/surveyCordinates")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public String surveyCordinates(JSONObject inputJsonObj) {
        try {
            System.out.println("Access surveyCordinates in MeterSurveyWebServices ");
            String latitude = inputJsonObj.get("latitude").toString();
            String longitude = inputJsonObj.get("longitude").toString();
            String imei = inputJsonObj.get("deviceid").toString();
            String type = inputJsonObj.get("type").toString();
            String mobile_no = inputJsonObj.get("phoneno").toString();
            JSONObject obj = new JSONObject();
            wsSurveyModel = new MeterSurveyWebServicesModel();
            wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
            wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
            wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
            wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
            wsSurveyModel.setConnection();
            int status = wsSurveyModel.insertSurveyCordinates(latitude, longitude, imei, type, mobile_no);
            obj.put("Data", status);
            System.out.println("Data Retrived : " + inputJsonObj + " " + status);
            wsSurveyModel.closeConnection();

        } catch (Exception ex) {
            Logger.getLogger(MeterSuverWebServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "OK";
    }

    @POST
    @Path("/energyMeterData")
    @Produces(MediaType.MULTIPART_FORM_DATA)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public byte[] insertEnergyMeterData(@Context HttpServletRequest requestContext, byte[] receivedBytes) {
        HttpSession session = requestContext.getSession();
        byte[] response = null;
        String result = "Sorry!! something went wrong. ";
        System.out.println("data at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + ": " + requestContext.getRemoteAddr());
        for (int i = 0; i < receivedBytes.length; i++) {
            System.out.print(" " + receivedBytes[i]);
        }
        String changeCommand = session.getAttribute("sp14") == null ? "" : session.getAttribute("sp14").toString();
        servletContext.getInitParameter("driverClass");
        System.out.println("");
        EnergyMeterWebServiceModel wsEnergyMeterModel = new EnergyMeterWebServiceModel();
        if (receivedBytes != null && receivedBytes.length != 0) {
            try {
                wsEnergyMeterModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
                wsEnergyMeterModel.setContext(servletContext);
                wsEnergyMeterModel.setSession(session);
                String responseVal = null;
                if (receivedBytes.length == 35) {
                    responseVal = wsEnergyMeterModel.junctionRefreshFunctionOne(receivedBytes, 5, false);
                } else if (receivedBytes.length == 17) {
                    responseVal = wsEnergyMeterModel.junctionRefreshFunctionNine(receivedBytes, 5, false);
                } else {
                    responseVal = wsEnergyMeterModel.junctionRefreshFunction(receivedBytes, 5, false);
                }
                if (responseVal != null && !responseVal.isEmpty()) {
                    response = wsEnergyMeterModel.sendResponse(responseVal);
                    if (response != null && response.length > 0) {
                        result = "Successful!!";
                    }
                }
            } catch (Exception ex) {
                System.out.println("Exception in insertEnergyMeterData" + ex);
            } finally {
                wsEnergyMeterModel.closeConnection();
            }
        }

        //////////////////////////////////////////////////////////////////
        try {
            for (int i = 0; i < response.length; i++) {
                if (response[i] == 0) {
                    response[i] = (byte) 255;
                }
            }
        } catch (Exception e) {
            System.out.println("Error while replacing 0 with 255  " + e);
        }
        ///////////////////////////////////////////////////////////////////////

        System.out.println("result : " + result);
        return response;
    }

    @POST
    @Path("/get_all_tubewellData")
    @Produces(MediaType.APPLICATION_JSON)//http://45.114.142.35:8080/Smart_Meter_survey/resources/SmartMeterSurvey/test
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject tubewellRecords(String test) {
        JSONObject obj1 = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        EnergyMeterWebServiceModel wsEnergyMeterModel = new EnergyMeterWebServiceModel();
        try {
            wsEnergyMeterModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
            wsEnergyMeterModel.setContext(servletContext);
            //wsEnergyMeterModel.setSession(session);       
            arrayObj = wsEnergyMeterModel.getAllTubewellData();
            obj1.put("Data", arrayObj);
        } catch (Exception e) {
        }
        //System.out.println("request come from jabalpur"+ test);
        return obj1;
    }

    @POST
    @Path("/get_tubewellDataInformation")
    @Produces(MediaType.APPLICATION_JSON)//http://45.114.142.35:8080/Smart_Meter_survey/resources/SmartMeterSurvey/test
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject tubewellRecordsInfo(String test) {
        String latlon[] = test.split(",");
        String lat = latlon[0];
        String lon = latlon[1];
        JSONObject obj1 = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        EnergyMeterWebServiceModel wsEnergyMeterModel = new EnergyMeterWebServiceModel();
        try {
            wsEnergyMeterModel.setConnection(DBConnection.getConnectionForUtf(servletContext));
            wsEnergyMeterModel.setContext(servletContext);
            //wsEnergyMeterModel.setSession(session);       
            arrayObj = wsEnergyMeterModel.getAllTubewellDataInfo(lat, lon);
            obj1.put("Data", arrayObj);
        } catch (Exception e) {
        }
        //System.out.println("request come from jabalpur"+ test);
        return obj1;
    }

    // primary meter survey data get
    @POST
    @Path("/primary_survey_data")
    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
    @Consumes(MediaType.APPLICATION_JSON)
    public String primarySurveyData(JSONObject json) throws Exception {
        String responseCheck = "";
          String destination_path = "";
        try {
            wsSurveyModel = new MeterSurveyWebServicesModel();
            wsSurveyModel.setDriverClass(servletContext.getInitParameter("driverClass"));
            wsSurveyModel.setConnectionString(servletContext.getInitParameter("connectionString"));
            wsSurveyModel.setDb_username(servletContext.getInitParameter("db_username"));
            wsSurveyModel.setDb_password(servletContext.getInitParameter("db_password"));
            wsSurveyModel.setConnection();

            // JSONObject json = new JSONObject(inputJsonObj);
            JSONObject json1 = new JSONObject();
            JSONObject json2 = new JSONObject();
//        JSONObject json3 = new JSONObject();
//        JSONObject json4 = new JSONObject();
//        JSONObject json5 = new JSONObject();

//image part start
            int no_of_rows = 0;
            int dataBase = 0;
            String Year = "";
            String Month = "";
            String day = "";

            String imagePathMeterNo = "";
            String image_of_meter_reading = "";
            json1 = json.getJSONObject("text");
            json2 = json.getJSONObject("image");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDateTime now = LocalDateTime.now();
            System.out.println(dtf.format(now));

            String date_check = dtf.format(now);

            String date[] = date_check.split("/");
            day = date[2];
            Month = date[1];
            Year = date[0];
            OutputStream out = null;
            no_of_rows = wsSurveyModel.getNoOfRows();
            no_of_rows = no_of_rows + 1;
            List<File> fileList = new ArrayList<File>();

            try {

                //int size = Integer.parseInt(json2.get("imgnamemeternumber").toString());
                int size = 1;
              
                String fileNameArray[] = new String[size];

                String fileName = "";
                for (int i = 1; i <= size; i++) {

//                    String getBackEncodedString = json2.get("byte_arrmeternumberimage" + i).toString();
                    String getBackEncodedString = json2.get("byte_arrmeternumberimage").toString();
                    byte[] imageAsBytes = new BASE64Decoder().decodeBuffer(getBackEncodedString);
//                    fileName = (json2.get("imgnamemeternumber" + i).toString());
                    fileName = (json2.get("imgnamemeternumber").toString());
                    fileNameArray[i - 1] = fileName;
                    if (fileName.isEmpty()) {
                        fileName = "out.jpg";
                    }

                    destination_path = wsSurveyModel.getDestinationPath("DetailList");
                    if(destination_path==null || destination_path=="" ){
                                 destination_path="C:\\ssadvt_repository\\meter_survey\\survey_image";
                    }
                    fileNameArray[i - 1] = fileName;
                     destination_path         =  "C:\\ssadvt_repository\\meter_survey\\survey_image";
                    imagePathMeterNo = destination_path + "/primary_survey_images/imagePathMeterNo/" + Year + "/" + Month + "/" + day + "/" + no_of_rows;

                    wsSurveyModel.makeDirectory(imagePathMeterNo);
                    String file = imagePathMeterNo + "/" + fileName;
                    fileList.add(new File(file));
                    out = new FileOutputStream(file);
                    out.write(imageAsBytes);
                    out.close();

//                dataSendModel.insertImageRecord(fileName);
                    System.out.println("image insert ");

                }

            } catch (Exception e) {
                System.out.println("Exception" + e);
            }
            try {
                wsSurveyModel.setConnection();
            } catch (Exception ex) {
                System.out.println("ERROR: in trafficSignalData : " + ex);
            }

////image
            OutputStream out1 = null;
            no_of_rows = wsSurveyModel.getNoOfRows();
            no_of_rows = no_of_rows + 1;
            List<File> fileList1 = new ArrayList<File>();

            try {

                // int size = Integer.parseInt(json2.get("imgnamemeterreading").toString());
                int size = 1;
              
                String fileNameArray[] = new String[size];

                String fileName = "";
                for (int i = 1; i <= size; i++) {

//                    String getBackEncodedString = json2.get("byte_arrmeterreadingimage" + i).toString();
                    String getBackEncodedString = json2.get("byte_arrmeterreadingimage").toString();
                    byte[] imageAsBytes = new BASE64Decoder().decodeBuffer(getBackEncodedString);
//                    fileName = (json2.get("imgnamemeterreading" + i).toString());
                    fileName = (json2.get("imgnamemeterreading").toString());
                    fileNameArray[i - 1] = fileName;
                    if (fileName.isEmpty()) {
                        fileName = "out.jpg";
                    }

//                    destination_path = wsSurveyModel.getDestinationPath("DetailList");
                    fileNameArray[i - 1] = fileName;
                                   destination_path         =  "C:\\ssadvt_repository\\meter_survey\\survey_image";
                    image_of_meter_reading = destination_path + "/primary_survey_images/image_of_meter_reading/" + Year + "/" + Month + "/" + day + "/" + no_of_rows;

                    wsSurveyModel.makeDirectory(image_of_meter_reading);
                    String file = image_of_meter_reading + "/" + fileName;
                    fileList.add(new File(file));
                    out = new FileOutputStream(file);
                    out.write(imageAsBytes);
                    out.close();

//                dataSendModel.insertImageRecord(fileName);
                    System.out.println("image insert ");

                }

            } catch (Exception e) {
                System.out.println("Exception" + e);
            }
            try {
                wsSurveyModel.setConnection();
            } catch (Exception ex) {
                System.out.println("ERROR: in trafficSignalData : " + ex);
            }

///image end
            dataBase = wsSurveyModel.insertRecordPrimary(json1, imagePathMeterNo, image_of_meter_reading);

            if (dataBase > 0) {
                responseCheck = "success";

            } else {
                responseCheck = "Data success fail";
            }

        } catch (Exception ex) {
            System.out.println("Exception in web service Controller: " + ex);
        }
        return responseCheck;
    }

    // primary meter survey data get end
    @POST
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)//http://45.114.142.35:8080/Smart_Meter_survey/resources/SmartMeterSurvey/test
    @Consumes(MediaType.TEXT_PLAIN)
    public String d(String test) {

        System.out.println("request come from jabalpur" + test);

        return "success";
    }

}
