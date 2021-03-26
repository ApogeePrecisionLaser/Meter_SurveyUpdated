/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;


import com.survey.dataEntry.model.TubeWellSurveyModel;
import com.survey.tableClasses.TubeWellSurveyBean;
import com.survey.util.GetDate;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import org.apache.pdfbox.util.PDFMergerUtility;

/**
 *
 * @author JPSS
 */
public class TubeWellSurveyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is survey Controller....");
        ServletContext ctx = getServletContext();
        TubeWellSurveyModel tubeWellSurveyModel = new TubeWellSurveyModel();
        tubeWellSurveyModel.setDriverClass(ctx.getInitParameter("driverClass"));
        tubeWellSurveyModel.setConnectionString(ctx.getInitParameter("connectionString"));
        tubeWellSurveyModel.setDb_username(ctx.getInitParameter("db_username"));
        tubeWellSurveyModel.setDb_password(ctx.getInitParameter("db_password"));
        tubeWellSurveyModel.setConnection();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        String searchPoleNo = request.getParameter("searchPoleNo");
        String searchIvrsNo = request.getParameter("searchIvrsNo");
        String searchFileNo = request.getParameter("searchFileNo");
        String searchPageNo = request.getParameter("searchPageNo");
        String searchByDate = request.getParameter("searchByDate");
         String task2=request.getParameter("task2");
         if(task2==null)task2="";

        String searchFeeder = request.getParameter("searchFeeder");
        String searchTypeOfConnection = request.getParameter("searchTypeOfConnection");
        String searchToDate = request.getParameter("searchToDate");
        String searchMeterFunctional=request.getParameter("searchMeterFunctional");

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
                        list = tubeWellSurveyModel.getPoleNo(q);
                    } else {
                        list = tubeWellSurveyModel.getSwitchingPoleNo(q);
                    }
                } else if (JQstring.equals("getSwitchNo")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        mountingType = request.getParameter("action2");
                    }
                    list = tubeWellSurveyModel.getSwichingPointNo(q);
                } else if (JQstring.equals("getSwitchingPtName")) {
                    list = tubeWellSurveyModel.getSwitchingPtName(q);
                } else if (JQstring.equals("getSearchPole_No")) {
                    list = tubeWellSurveyModel.getSearchPole_No(q);
                } 
                
//                else if (JQstring.equals("getSearchPole_No")) {
//                    list = tubeWellSurveyModel.getSearchPole_No(q);
//                } 
                else if (JQstring.equals("getAutoSwitchType")) {
                    list = tubeWellSurveyModel.getAutoSwitchType(q);
                } else if (JQstring.equals("getMainSwitchType")) {
                    list = tubeWellSurveyModel.getMainSwitchType(q);
                } else if (JQstring.equals("getEnclosureType")) {
                    list = tubeWellSurveyModel.getEnclosureType(q);
                } else if (JQstring.equals("getFuseType")) {
                    list = tubeWellSurveyModel.getFuseType(q);
                } else if (JQstring.equals("getStarterType")) {
                    list = tubeWellSurveyModel.getStarterType(q);
                } else if (JQstring.equals("getMccbType")) {
                    list = tubeWellSurveyModel.getMccbType(q);
                } else if (JQstring.equals("getStarterMake")) {
                    list = tubeWellSurveyModel.getStarterMake(q);
                }else if (JQstring.equals("getSearchIvrsNo")) {
                    list = tubeWellSurveyModel.getIvrsNo(q);
                }
                else if (JQstring.equals("getreason_type")) {
                    list = tubeWellSurveyModel.getreason_type(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                tubeWellSurveyModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        String task1 = request.getParameter("task1");
        if (task1 == null) {
            task1 = "";
        }
         String searchMeterNo="";
//        if (task1.equals("generateMapReport")) {
//            String searchPole = request.getParameter("poll_no");
//            searchIvrsNo = request.getParameter("ivrs_no");
//             searchMeterNo=tubeWellSurveyModel.getMeterNumber(searchIvrsNo);
//            List listAll = null;
//            String jrxmlFilePath;
//            response.setContentType("application/pdf");
//            ServletOutputStream servletOutputStream = response.getOutputStream();
//            listAll = tubeWellSurveyModel.showData(-1, -1, searchPole, searchIvrsNo, "",searchFileNo,searchPageNo,searchByDate,"",searchMeterFunctional,searchFeeder,searchTypeOfConnection,searchToDate);
//            jrxmlFilePath = ctx.getRealPath("/report/tubeWellSurvey_2.jrxml");
//            byte[] reportInbytes = tubeWellSurveyModel.generateMapReport(jrxmlFilePath, listAll);
//            response.setContentLength(reportInbytes.length);
//            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//            servletOutputStream.flush();
//            servletOutputStream.close();
//            return;
//        }

         if (task1.equals("showAllTubeWellSurvey")) {
            List<TubeWellSurveyBean> List = tubeWellSurveyModel.showDataBean();
            request.setAttribute("CoordinatesList", List);
            request.getRequestDispatcher("/view/MapView/allMapWindow.jsp").forward(request, response);
            return;
        }


         if (task1.equals("generateMapReport"))
        {
            String report_type = request.getParameter("report_type");
            String Pole = request.getParameter("poll_no");
            String IvrsNo = request.getParameter("ivrs_no");
            String FileNo = request.getParameter("FileNo");
            String PageNo = request.getParameter("PageNo");
            String Date = request.getParameter("Date");
            String feeder=request.getParameter("feeder");          //searchFeeder,searchToDate,searchTypeOfConnection
            String typeOfConnection = request.getParameter("typeOfConnection");
            String dateTo = request.getParameter("dateTo");
            String meterFunctional=request.getParameter("meterFunctional");
            if(Pole==null)Pole="";
            if(IvrsNo==null)IvrsNo="";
            if(FileNo==null)FileNo="";
            if(PageNo==null)PageNo="";
            if(Date==null)Date="";
            if(feeder==null)feeder="";
            if(typeOfConnection==null)typeOfConnection="";
            if(dateTo==null)dateTo="";
            if(meterFunctional==null)meterFunctional="";
            //searchMeterNo=newSwitchingPointSurveyModel.getMeterNumber(searchIvrsNo);
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            if(report_type.equals("1"))
            {
                //listAll = newSwitchingPointSurveyModel.showData(-1, -1, searchPole, searchMeterNo);
                jrxmlFilePath = ctx.getRealPath("/report/tubeWellSurvey_2.jrxml");
            }else
            {
                //listAll = newSwitchingPointSurveyModel.showData(-1, -1, searchPole, searchMeterNo);
                jrxmlFilePath = ctx.getRealPath("/report/tubeWellSurveyShortReport.jrxml");
            }
            listAll = tubeWellSurveyModel.showData(-1, -1,Pole,IvrsNo,"",FileNo,PageNo,Date,"",meterFunctional,feeder,typeOfConnection,dateTo);
            byte[] reportInbytes = tubeWellSurveyModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
         
         if (task1.equals("generateXLSReport")) {
            String report_type = request.getParameter("report_type");
            String Pole = request.getParameter("poll_no");
            String IvrsNo = request.getParameter("ivrs_no");
            String FileNo = request.getParameter("FileNo");
            String PageNo = request.getParameter("PageNo");
            String Date = request.getParameter("Date");
            String feeder=request.getParameter("feeder");          //searchFeeder,searchToDate,searchTypeOfConnection
            String typeOfConnection = request.getParameter("typeOfConnection");
            String dateTo = request.getParameter("dateTo");
            String meterFunctional=request.getParameter("meterFunctional");
            if(Pole==null)Pole="";
            if(IvrsNo==null)IvrsNo="";
            if(FileNo==null)FileNo="";
            if(PageNo==null)PageNo="";
            if(Date==null)Date="";
            if(feeder==null)feeder="";
            if(typeOfConnection==null)typeOfConnection="";
            if(dateTo==null)dateTo="";
            if(meterFunctional==null)meterFunctional="";
            //searchMeterNo=newSwitchingPointSurveyModel.getMeterNumber(searchIvrsNo);
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/vnd.ms-excel");            
            ServletOutputStream servletOutputStream = response.getOutputStream();
            if(report_type.equals("1")){                
                jrxmlFilePath = ctx.getRealPath("/report/tubeWellSurvey_2.jrxml");
                response.addHeader("Content-Disposition", "attachment; filename=SurveyReport_1.xls");
            }else{
                response.addHeader("Content-Disposition", "attachment; filename=SurveyReport_2.xls");
                jrxmlFilePath = ctx.getRealPath("/report/tubeWellSurveyShortReport.jrxml");
            }
            listAll = tubeWellSurveyModel.showData(-1, -1,Pole,IvrsNo,"",FileNo,PageNo,Date,"",meterFunctional,feeder,typeOfConnection,dateTo);
            ByteArrayOutputStream reportInbytes = tubeWellSurveyModel.generateXLSReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.size());
            servletOutputStream.write(reportInbytes.toByteArray());
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
         if (task1.equals("GetCordinates1"))
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

        if (task1.equals("showMapWindow")) {
            String longi = request.getParameter("logitude");
            String latti = request.getParameter("lattitude");
            request.setAttribute("longi", longi);
            request.setAttribute("latti", latti);
            System.out.println(latti + "," + longi);
            request.getRequestDispatcher("openMapWindowView").forward(request, response);
            return;
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
                        destination_path = tubeWellSurveyModel.getRepositoryPath(image_uploaded_for);
                        File file = new File(destination_path);
                        if (!image_name.isEmpty()) {
                            file = new File(destination_path);
                            if (!file.exists()) {
                                isCreated = file.mkdirs();
                            }

                            item.write(new File(destination_path + "\\" + image_name));
                            list.add(new File(destination_path + "\\" + image_name));
                        }
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
        if (task.equalsIgnoreCase("Save") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Cancel")) {
            int survey_id;
            int survey_rev_no;
            int tube_well_survey_id;
            int tube_well_survey_rev_no;
            int switching_rev_no;
            int tube_well_detail_id;
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
                tube_well_survey_id = Integer.parseInt(map.get("tube_well_survey_id"));
            } catch (Exception e) {
                tube_well_survey_id = 0;
            }
            try {
                tube_well_survey_rev_no = Integer.parseInt(map.get("tube_well_survey_rev_no"));
            } catch (Exception e) {
                tube_well_survey_rev_no = 0;
            }
            if (task.equals("Save AS New")) {
                survey_id = 0;
                survey_rev_no = 0;
                tube_well_survey_id = 0;
                tube_well_survey_rev_no = 0;
            }
            TubeWellSurveyBean tubeWellSurveyBean = new TubeWellSurveyBean();
            tubeWellSurveyBean.setSurvey_id(survey_id);
            tubeWellSurveyBean.setSurvey_rev_no(survey_rev_no);
            tubeWellSurveyBean.setTube_well_survey_id(tube_well_survey_id);
            tubeWellSurveyBean.setTube_well_survey_rev_no(tube_well_survey_rev_no);
            tubeWellSurveyBean.setSurvey_type(map.get("survey_type"));
            tubeWellSurveyBean.setService_conn_no(map.get("ivrs_no"));
            tubeWellSurveyBean.setSurvey_by(map.get("survey_by"));
            tubeWellSurveyBean.setSurvey_date(map.get("survey_date"));
            //tubeWellSurveyBean.setStatus(map.get("status"));
            tubeWellSurveyBean.setSurvey_file_no(map.get("survey_file_no"));
            tubeWellSurveyBean.setSurvey_page_no(map.get("survey_page_no"));
            tubeWellSurveyBean.setB_phase(map.get("b_phase"));
            tubeWellSurveyBean.setStarter_functional(map.get("starter_functional"));
            //      tubeWellSurveyBean.setCreated_date(request.getParameter("created_date"));
            tubeWellSurveyBean.setFuse_functional(map.get("fuse_functional"));
            tubeWellSurveyBean.setMccb_functional(map.get("mccb_functional"));
            tubeWellSurveyBean.setMeter_functional(map.get("meter_functional"));
            // tubeWellSurveyBean.setTimer_functional(map.get("timer_functional"));
            tubeWellSurveyBean.setMeter_no(map.get("meter_no"));
            tubeWellSurveyBean.setPole_no(map.get("pole_no"));
            tubeWellSurveyBean.setPower(map.get("power"));
            tubeWellSurveyBean.setR_phase(map.get("r_phase"));
            tubeWellSurveyBean.setFuse1(map.get("fuse_1").trim());
            tubeWellSurveyBean.setFuse2(map.get("fuse_2").trim());
            tubeWellSurveyBean.setFuse3(map.get("fuse_3").trim());
            tubeWellSurveyBean.setStarter_id(tubeWellSurveyModel.getContracterId(map.get("starter_type")));
            tubeWellSurveyBean.setStarter_make_id(tubeWellSurveyModel.getStarterMakeId(map.get("starter_make")));
            tubeWellSurveyBean.setStarter_capacity(map.get("starter_capacity"));
            tubeWellSurveyBean.setMccb1(map.get("mccb_1").trim());
            tubeWellSurveyBean.setMccb2(map.get("mccb_2").trim());
            tubeWellSurveyBean.setMccb3(map.get("mccb_3").trim());
            tubeWellSurveyBean.setReason_id(tubeWellSurveyModel.getReasonId(map.get("reason_type")));
            tubeWellSurveyBean.setImage_name(map.get("image_name"));
            tubeWellSurveyBean.setFuse_id1(tubeWellSurveyModel.getFuseId(map.get("fuse_type1")));
            tubeWellSurveyBean.setFuse_id2(tubeWellSurveyModel.getFuseId(map.get("fuse_type2")));
            tubeWellSurveyBean.setFuse_id3(tubeWellSurveyModel.getFuseId(map.get("fuse_type3")));
            tubeWellSurveyBean.setMccb_id1(tubeWellSurveyModel.getMccbId(map.get("mccb_type1")));
            tubeWellSurveyBean.setMccb_id2(tubeWellSurveyModel.getMccbId(map.get("mccb_type2")));
            tubeWellSurveyBean.setMccb_id3(tubeWellSurveyModel.getMccbId(map.get("mccb_type3")));
            tubeWellSurveyBean.setNo_of_phase(Integer.parseInt(map.get("phase_no")));
            tubeWellSurveyBean.setMeter_phase(Integer.parseInt(map.get("meter_phase")));
            tubeWellSurveyBean.setMeter_reading(map.get("meter_reading"));
            tubeWellSurveyBean.setAuto_switch_type_id(tubeWellSurveyModel.getSwitchId(map.get("auto_switch_type")));
            tubeWellSurveyBean.setMain_switch_type_id(tubeWellSurveyModel.getSwitchId(map.get("main_switch_type")));
            tubeWellSurveyBean.setMain_switch_rating(map.get("main_switch_rating"));
            tubeWellSurveyBean.setEnclosure_type_id(tubeWellSurveyModel.getEnclosureTypeId(map.get("enclosure_type")));
            tubeWellSurveyBean.setMeter_status(map.get("meter_status"));
            tubeWellSurveyBean.setImage_name(map.get("image_name"));
            tubeWellSurveyBean.setSurvey_pole_no(map.get("survey_pole_no"));
            tubeWellSurveyBean.setLatitude(map.get("latitude"));
            tubeWellSurveyBean.setLongitude(map.get("longitude"));
            tubeWellSurveyBean.setHours(map.get("hours"));


            tubeWellSurveyBean.setY_phase(map.get("y_phase"));
            tubeWellSurveyBean.setRemark(map.get("remark2"));
            tubeWellSurveyBean.setPole_id(tubeWellSurveyModel.getPole_id(map.get("pole_no")));

            tubeWellSurveyBean.setMeter_address(map.get("meter_address"));
            //      tubeWellSurveyBean.setPole_id( tubeWellSurveyModel.getPoleId(request.getParameter("pole_no")));
            //      tubeWellSurveyBean.setSwitching_point_detail_id( tubeWellSurveyModel.getSwichingPointId(request.getParameter("pole_no_s")));
            if (survey_id == 0) {
                boolean flag = false;
                System.out.println("Inserting values by model......");
                if (tubeWellSurveyBean.getSurvey_type().equals("tubewell_type_survey")) {
                    String switching_id_rev = "";
                    if (!tubeWellSurveyModel.getSwitchingPoint_id(map.get("pole_no")).isEmpty()) {
                        switching_id_rev = tubeWellSurveyModel.getSwitchingPoint_id(map.get("pole_no"));
                        flag = true;
                        // surveyBean.setPole_id(Integer.parseInt(switching_id_rev));

                    }
                    if (switching_id_rev == null || switching_id_rev.isEmpty()) {
                        flag = true;
                    }
                    // String switching_id_rev = tubeWellSurveyModel.getSwitchingPoint_id(map.get("pole_no"));
                    // tubeWellSurveyBean.setTube_well_detail_id(Integer.parseInt(switching_id_rev.split("_")[0]));
                    //  tubeWellSurveyBean.setSwitching_rev_no(Integer.parseInt(switching_id_rev.split("_")[1]));
                    // flag = tubeWellSurveyModel.validationCheck(Integer.parseInt(switching_id_rev.split("_")[0]), Integer.parseInt(switching_id_rev.split("_")[1]), tubeWellSurveyBean.getSurvey_type());
                }
//                else if (tubeWellSurveyBean.getSurvey_type().equals("pole_type_survey")) {
//                    String pole_id_rev = tubeWellSurveyModel.getPoleId(map.get("pole_no"));
//                    tubeWellSurveyBean.setPole_id(Integer.parseInt(pole_id_rev.split("_")[0]));
//                    tubeWellSurveyBean.setPole_rev_no(Integer.parseInt(pole_id_rev.split("_")[1]));
//                    flag = tubeWellSurveyModel.validationCheck(Integer.parseInt(pole_id_rev.split("_")[0]), Integer.parseInt(pole_id_rev.split("_")[1]), tubeWellSurveyBean.getSurvey_type());
//                }
                if (flag) {
                    tubeWellSurveyModel.insertRecord(tubeWellSurveyBean, list);
                }
            } else {
                System.out.println("Revise values by model........");
                if (task.equals("Revise")) {
                    tubeWellSurveyModel.reviseRecord(tubeWellSurveyBean, list);
                } else {
                    tubeWellSurveyModel.cancelRecord(tubeWellSurveyBean);
                }
            }
        }
        // Start of Auto Completer code
//        String searchPoleNo = "";
//        String searchIvrsNo = "";
//        // End of Auto Completer code
//        searchPoleNo = request.getParameter("searchPoleNo");
//        searchIvrsNo = request.getParameter("searchIvrsNo");
       
        try {
            if (searchPoleNo == null) {
                searchPoleNo = "";
            }
            if (searchFileNo == null) {
                searchFileNo = "";
            }
            if (searchPageNo == null) {
                searchPageNo = "";
            }
            if (searchByDate == null) {
                searchByDate = "";
            }
            if(searchMeterFunctional==null)
            {
                searchMeterFunctional="";
            }
            if(searchFeeder==null)
            {
                searchFeeder="";
            }
            if(searchTypeOfConnection==null)
            {
                searchTypeOfConnection="";
            }
            if(searchToDate==null)
            {
                searchToDate="";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        try {
            if (searchIvrsNo == null) {
                searchIvrsNo = "";
            }else{
            searchMeterNo=tubeWellSurveyModel.getMeterNumber(searchIvrsNo);
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
 
        // also for testing
        if (task1.equals("viewImage")) {
            try {
                ArrayList list1=new ArrayList();
               // int general_image_details_id = Integer.parseInt(request.getParameter("general_image_details_id"));
              //  String image_name = request.getParameter("image_name");
             //   String[] imageName=image_name.split(",");
                int survey_id = Integer.parseInt(request.getParameter("survey_id"));
                int general_image_details_id =tubeWellSurveyModel.getGeneral_image_details_id(survey_id);
                String destination = tubeWellSurveyModel.getdestinationPathForMulImage(general_image_details_id);
              //  destination=destination+"//"+survey_id;
             
                if (destination.equals("") || destination.isEmpty())
                {
                    list1.add("C:\\ssadvt_repository\\no_image.png");
                } else {
                    //path = destination + "\\" + survey_id + "\\" + image_name;
   String image_name = tubeWellSurveyModel.getImageList(survey_id);
                String[] imageName=image_name.split(",");
                    for(int i=0;i<imageName.length;i++)
                   {
                       String imageDestination = "";
                        File f = new File(destination + "\\tube_well\\" +"survey_id_"+survey_id+"\\"+ imageName[i]);
                        if(f.exists())
                        {
                            imageDestination = destination + "\\tube_well\\" +"survey_id_"+survey_id+"\\"+ imageName[i];                            
                        }
                        else
                        {
                            imageDestination = destination + "\\" + "no_image.png";                            
                        }
                        imageDestination = imageDestination.replace("\\", "$");
                        list1.add(imageDestination);
                    }
                }
                request.setAttribute("imageList", list1);
                request.getRequestDispatcher("ImagesView").forward(request, response);
                return;
            } catch (Exception e) {
                System.out.println("Exception occur: " + e);
            }
        }

        String image_destination = "";
        image_destination = request.getParameter("img_destination");
        if(image_destination==null)image_destination="";

        if (task1.equals("getImage") || task1.equals("getImageThumb"))
        {
            String path="C:/ssadvt_repository/meter_survey/survey_image/switching_point";
            if (image_destination == null || image_destination.isEmpty()) {
                image_destination = path + "general/no_image.png";
                  String ext = image_destination.split("\\.")[1];
                    response.setContentType("image/" + ext);
            } else
			{
            File f = new File(image_destination);
                if (f.exists()) {
                    String ext = image_destination.split("\\.")[1];
                    response.setContentType("image/" + ext);
                } else {
                  image_destination = path + "general/no_image.png";
                }
            }
            ServletOutputStream os = response.getOutputStream();
            FileInputStream is = new FileInputStream(new File(image_destination));
            byte[] buf = new byte[1024 * 1024];
            int nRead = 0;
            while ((nRead = is.read(buf)) != -1) {
               os.write(buf, 0, nRead);
            }
            os.flush();
            os.close();
            is.close();
            return;
        }
        
        if(task1.equals("generatePDFReport"))
        {
            String report_type = request.getParameter("report_type");
            String survey_id = request.getParameter("survey_id");
            String ivrs_no = request.getParameter("ivrs_no");
            String meter_name_auto=request.getParameter("meter_name_auto");
            String pdfFileName=meter_name_auto+"_"+survey_id+".pdf";
            String ErrorPath="C:/ssadvt_repository/meter_survey/survey_image";
            String report="tubeWellSurvey.pdf";
            PDFMergerUtility ut = new PDFMergerUtility();
            try
            {
                File ff = new File("C:/ssadvt_repository/meter_survey/survey_Image_pdf/"+pdfFileName);
                if (ff.exists())
                {
                    System.out.print("file already Exit");
                }
                else
                {
                    int count=0;
                    if(survey_id==null)survey_id="";
                    List listAll = null;
                    String jrxmlFilePath;
                    response.setContentType("application/pdf");
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    if(ivrs_no==null)
                    {
                       // jrxmlFilePath = ctx.getRealPath("/report/tubeWellSurvey_1.jrxml");
                        jrxmlFilePath = ctx.getRealPath("/Report/testingPurpose.jrxml");
                        //listAll = tubeWellSurveyModel.showData(-1, -1,"","","","","","",countnum,survey_id,"","","","");
                        listAll = tubeWellSurveyModel.showData(-1, -1,"","",survey_id,"","","","","","","","");
                    }
                    else
                    {
                        listAll = tubeWellSurveyModel.showData(-1, -1, "", ivrs_no, "","","","","","","","","");
                        jrxmlFilePath = ctx.getRealPath("/Report/tubeWellSurvey_2.jrxml");                      
                    }
                  tubeWellSurveyModel.SavePdf(jrxmlFilePath, listAll, report);
                    Iterator itrr=listAll.iterator();
                    String image=null;
                    //String path="C:/ssadvt_repository/meter_survey/survey_image/tube_well";
                    String path="C:/ssadvt_repository/meter_survey/survey_image/tube_well/survey_id_"+survey_id;
                    String savedFilePath="C:/ssadvt_repository/meter_survey/temp_pdf";
                    String[] imageName=null;
                    List<String> list1 = new ArrayList<String>();
                    while(itrr.hasNext())
                    {
                       TubeWellSurveyBean tubeWellSurveyBean1 = new TubeWellSurveyBean();
                       tubeWellSurveyBean1=(TubeWellSurveyBean)itrr.next();
                       //image=tubeWellSurveyBean1.getImage_name();
                       //  for testing
                       int survey_id1=tubeWellSurveyBean1.getSurvey_id();
                       image = tubeWellSurveyModel.getImageList(survey_id1);
                       // end for testng
                       if(image != null && !image.isEmpty())
                       {
                           imageName=image.split(",");
                           for(int i=0;i<imageName.length;i++)
                           {
                               if(imageName[i].contains(".pdf")){

                                } else{
                               list1.add(path+"/"+imageName[i]);
                               }
                           }
                        }
                        else
                        {
                            list1.add(ErrorPath + "/no_image.png");
                        }
                    }
                    if (list1 == null || list1.isEmpty())
                    {
                        String path1=ErrorPath + "/no_image.png";
                        String ext = path1.split("\\.")[1];
                        response.setContentType("image/" + ext);
                    }
                    else
                    {
                        ut.addSource(new File(savedFilePath+"/"+report));
                        Calendar cal = Calendar.getInstance();
                        String monthString=GetDate.getCurrentMonth();
                        String year=(GetDate.getCurrentDate()).split("-")[2];
                        String date=monthString+"-"+year;
                        boolean status=true;
                        String name_path="";
                      try
                      {

        ////   for teting insert bill pdf in report

             int survey_id2=Integer.parseInt(survey_id);
                        String pdf_file = tubeWellSurveyModel.getImageList(survey_id2);
                        String pdf_file1[]=pdf_file.split(",");
                        String pdf_file2=null;
                           for(int i=0;i<pdf_file1.length;i++)
                           {
                               if(pdf_file1[i].contains(".pdf")){
                               pdf_file2=pdf_file1[i];
                           }
                           }
                        File file1 = new File(path+"\\"+pdf_file2);
                        if (file1.exists()){
                        ut.addSource(new File(path+"\\"+pdf_file2));
                        }
                        else{
                      String Bill_not_Found_path="C:/ssadvt_repository/JABALPUR/MPEZ/general";
                      String name1="bill_not_found.pdf";
                      ut.addSource(new File(Bill_not_Found_path+"/"+name1));
                        }
                  
      ///    end of testing

                      } catch(Exception e)
                     {
                          e.printStackTrace();
                     }

                        Iterator itrr1=list1.iterator();
                        while(itrr1.hasNext())
                        {
                            String temp_list=(String)itrr1.next();
                            File f = new File(temp_list);
                            if (f.exists())
                            {
                                String ext = temp_list.split("\\.")[1];
                                // response.setContentType("image/" + ext);
                            }
                            else
                            {
                                temp_list = ErrorPath + "/no_image.png";
                            }
                            count++;
                            String imageFilepath= tubeWellSurveyModel.imageToPdfConvert(temp_list,count);
                           /* FileInputStream is = new FileInputStream(new File(imageFilepath));
                            FileOutputStream fout=new FileOutputStream(path+"/"+report);
                            //ServletOutputStream os = response.getOutputStream();
                            // OutputStream oss=new OutputStream(os);
                            byte[] buf = new byte[1024 * 1024];
                            int nRead = 0;
                            while ((nRead = is.read(buf)) != -1)
                            {
                                fout.write(buf, 0, nRead);
                            }
                            fout.flush();
                            fout.close();
                            is.close();
                            return;*/
                            ut.addSource(new File(imageFilepath));
                            if(ivrs_no==null)
                                ut.setDestinationFileName("C:/ssadvt_repository/meter_survey/survey_Image_pdf/"+pdfFileName);
                             else
                                 ut.setDestinationFileName("C:/ssadvt_repository/meter_survey/survey_Ivrs_Image_Pdf"+"/"+report);
                         }
                        // String getBill_Image_Path=tubeWellSurveyModel.getBillImagePath("Bill Image");
                        ut.mergeDocuments();
                        if(ivrs_no==null)
                        tubeWellSurveyModel.insertGeneral_Image_Details(pdfFileName,"survey_Image_pdf");
                    }
                }
                response.setContentType("application/pdf");
                ServletOutputStream os = response.getOutputStream();
                FileInputStream is=null;
                if(ivrs_no==null)
                {
                    is = new FileInputStream(new File("C:/ssadvt_repository/meter_survey/survey_Image_pdf/"+pdfFileName));
                }
                else
                {
                    is = new FileInputStream(new File("C:/ssadvt_repository/meter_survey/survey_Ivrs_Image_Pdf"+"/"+report));
                }
                byte[] buf = new byte[1024 * 1024];
                int nRead = 0;
                while ((nRead = is.read(buf)) != -1)
                {
                    os.write(buf, 0, nRead);
                }
                os.flush();
                os.close();
                is.close();
               File file1 = new File("C:/ssadvt_repository/meter_survey/temp_pdf");
               File[]  myFiles = file1.listFiles();
               for (int i=0; i<myFiles.length; i++)
               {
                   myFiles[i].delete();
               }
               File file2 = new File("C:/ssadvt_repository/meter_survey/temp_img");
               File[]  myFiles1 = file2.listFiles();
               for (int j=0; j<myFiles1.length; j++)
               {
                    myFiles1[j].delete();
               }
            }
            catch(Exception e)
            {
               e.printStackTrace();
            }
            /*byte[] reportInbytes = tubeWellSurveyModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();*/

            //int a=tubeWellSurveyModel.insertGeneral_Image_Details(pdfFileName,"C:/ssadvt_repository/meter_survey/survey_Image_pdf/");                        
                        
             return;
        }


        // Start of Search Table
        try {
            if(map.isEmpty())
            {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            }else{
                lowerLimit = Integer.parseInt(map.get("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(map.get("noOfRowsTraversed"));
            }
        } catch (Exception e) {
            lowerLimit = noOfRowsTraversed = 0;
        }
        String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
        if (buttonAction == null) {
            buttonAction = "none";
        }
        //System.out.println("searching.......... " + searchSourceType);
        //noOfRowsInTable =  tubeWellSurveyModel.getNoOfRows(searchPoleNo, searchIvrsNo);
        noOfRowsInTable = tubeWellSurveyModel.getNoOfRows(searchPoleNo,searchIvrsNo, "",searchFileNo,searchPageNo,searchByDate,"",searchMeterFunctional,searchFeeder,searchTypeOfConnection,searchToDate);

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
        } else if (task2.equals("Show All Records")) {
            searchPoleNo = "";
            searchIvrsNo = "";
            searchFileNo="";
            searchPageNo="";
            searchByDate="";
            searchIvrsNo="";
            searchMeterFunctional="";
            searchToDate="";
            searchTypeOfConnection="";
        }
        // Logic to show data in the table.
        //List<SurveyBean> surveyTypeList =  tubeWellSurveyModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNo, searchIvrsNo);
        List<TubeWellSurveyBean> surveyTypeList = tubeWellSurveyModel.showData(lowerLimit, noOfRowsToDisplay, searchPoleNo,searchIvrsNo, "",searchFileNo,searchPageNo,searchByDate,"",searchMeterFunctional,searchFeeder,searchTypeOfConnection,searchToDate);
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
        System.out.println("color is :" + tubeWellSurveyModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchPoleNo", searchPoleNo);
        request.setAttribute("searchIvrsNo", searchIvrsNo);
        request.setAttribute("searchFileNo", searchFileNo);
        request.setAttribute("searchPageNo", searchPageNo);
        request.setAttribute("searchFeeder", searchFeeder);
        request.setAttribute("searchToDate", searchToDate);
        request.setAttribute("searchTypeOfConnection", searchTypeOfConnection);
        request.setAttribute("searchMeterFunctional", searchMeterFunctional);
        request.setAttribute("searchByDate", searchByDate);
        request.setAttribute("message", tubeWellSurveyModel.getMessage());
        request.setAttribute("msgBgColor", tubeWellSurveyModel.getMsgBgColor());
        request.getRequestDispatcher("/tubeWellSurveyView").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
