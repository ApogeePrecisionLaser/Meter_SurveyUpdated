/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suver.webservices;

import com.survey.dataEntry.controller.SurveyController;
import com.survey.dataEntry.model.SurveyModel;
import com.survey.tableClasses.SurveyBean;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 *
 * @author Administrator
 */
@Path("/service")
public class MeterSuverWebServices extends SurveyController  {
 SurveyModel surveyModel = null;
 ServletContext ctx=null;
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws IOException, ServletException {
        //int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
     //   System.out.println("this is survey Controller....");
       // ServletContext ctx = getServletContext();
       
      
   // }
MeterSuverWebServices ws=(MeterSuverWebServices) new SurveyController();

    public SurveyModel getSurveyModel() {
        return surveyModel;
    }


    @Override
    public ServletConfig getServletConfig() {
        return super.getServletConfig();
    }


    @Override
    public ServletContext getServletContext() {
        return super.getServletContext();
    }

//    @POST
//    @Path("/hello")
//    @Produces(MediaType.APPLICATION_JSON)//http://192.168.1.15:8084/meter_survey/api/service/hello
//    @Consumes(MediaType.APPLICATION_JSON)
//    public JSONObject sayPlainTextHello(JSONObject inputJsonObj) throws Exception {
//        int survey_id=0;
//        System.out.println("Hello meter_survey");
//        SurveyBean surveyBean = new SurveyBean();
//        surveyModel=ws.getSurveyModel();
////          surveyModel.setDriverClass(ctx.getInitParameter("driverClass"));
////        surveyModel.setConnectionString(ctx.getInitParameter("connectionString"));
////        surveyModel.setDb_username(ctx.getInitParameter("db_username"));
////        surveyModel.setDb_password(ctx.getInitParameter("db_password"));
////        surveyModel.setConnection();System.out.println("heloo conn: "+ctx.getInitParameter("connectionString"));
//        surveyBean.setSurvey_type(inputJsonObj.get("survey_type").toString());
//        System.out.println("survey type :"+inputJsonObj.get("survey_type").toString());
//        surveyBean.setSurvey_by(inputJsonObj.get("survey_by").toString());
//        surveyBean.setSurvey_date(inputJsonObj.get("survey_date").toString());
//        surveyBean.setStatus(inputJsonObj.get("status").toString());
//        surveyBean.setSurvey_file_no(inputJsonObj.get("survey_file_no").toString());
//        surveyBean.setSurvey_page_no(inputJsonObj.get("survey_page_no").toString());
//        surveyBean.setB_phase(inputJsonObj.get("b_phase").toString());
//        surveyBean.setContacter_funactional(inputJsonObj.get("contacter_functional").toString());
//        //      surveyBean.setCreated_date(request.getParameter("created_date"));
//        surveyBean.setFuse_functional(inputJsonObj.get("fuse_functional").toString());
//        surveyBean.setMccb_functional(inputJsonObj.get("mccb_functional").toString());
//        surveyBean.setMeter_functional(inputJsonObj.get("meter_functional").toString());
//        surveyBean.setTimer_functional(inputJsonObj.get("timer_functional").toString());
//        surveyBean.setMeter_no(inputJsonObj.get("meter_no").toString());
//        surveyBean.setPole_no(inputJsonObj.get("pole_no").toString());
//        surveyBean.setPower(inputJsonObj.get("power").toString());
//        surveyBean.setR_phase(inputJsonObj.get("r_phase").toString());
//        if (surveyBean.getSurvey_type().equals(inputJsonObj.get("pole_type_survey").toString())) {
//            surveyBean.setRemark(inputJsonObj.get("remark1").toString());
//            try {
//                surveyBean.setPole_id(Integer.parseInt(inputJsonObj.get("pole_id").toString()));
//            } catch (Exception e) {
//                surveyBean.setPole_id(0);
//            }
//            try {
//                surveyBean.setPole_rev_no(Integer.parseInt(inputJsonObj.get("pole_rev_no").toString()));
//            } catch (Exception e) {
//                surveyBean.setPole_rev_no(0);
//            }
//        } else {
//            surveyBean.setRemark(inputJsonObj.get("remark2").toString());
//            try {
//                surveyBean.setSwitching_point_detail_id(Integer.parseInt(inputJsonObj.get("switching_point_detail_id").toString()));
//            } catch (Exception e) {
//                surveyBean.setSwitching_point_detail_id(0);
//            }
//            try {
//                surveyBean.setSwitching_rev_no(Integer.parseInt(inputJsonObj.get("switching_rev_no").toString()));
//            } catch (Exception e) {
//                surveyBean.setSwitching_rev_no(0);
//            }
//        }
//        surveyBean.setY_phase(inputJsonObj.get("y_phase").toString());
//        String input = (String) inputJsonObj.get("survey_by");
//        String output = "The input you sent is :" + input;
//        String age = (String) inputJsonObj.get("pole_no");
//        System.out.println(age);
//        JSONObject outputJsonObj = new JSONObject();
//        outputJsonObj.put("output", output);
//        if (survey_id == 0) {
//            boolean flag = false;
//            System.out.println("Inserting values by model......");
//            if (surveyBean.getSurvey_type().equals("switching_type_survey")) {
//                String switching_id_rev = surveyModel.getSwitchingPoint_id(inputJsonObj.get("pole_no").toString());
//                surveyBean.setSwitching_point_detail_id(Integer.parseInt(switching_id_rev.split("_")[0]));
//                surveyBean.setSwitching_rev_no(Integer.parseInt(switching_id_rev.split("_")[1]));
//                flag = surveyModel.validationCheck(Integer.parseInt(switching_id_rev.split("_")[0]), Integer.parseInt(switching_id_rev.split("_")[1]), surveyBean.getSurvey_type());
//            } else if (surveyBean.getSurvey_type().equals("pole_type_survey")) {
//                String pole_id_rev = surveyModel.getPoleId(inputJsonObj.get("pole_no").toString());
//                surveyBean.setPole_id(Integer.parseInt(pole_id_rev.split("_")[0]));
//                surveyBean.setPole_rev_no(Integer.parseInt(pole_id_rev.split("_")[1]));
//                flag = surveyModel.validationCheck(Integer.parseInt(pole_id_rev.split("_")[0]), Integer.parseInt(pole_id_rev.split("_")[1]), surveyBean.getSurvey_type());
//            }
//            if (flag) {
//                surveyModel.insertRecord(surveyBean);
//            }
//        }
//        return outputJsonObj;
//    }
}
