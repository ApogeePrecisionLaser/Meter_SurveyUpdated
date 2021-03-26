/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.PoleSurveyDataModel;
import com.survey.tableClasses.CircuitSurveyBean;
import com.survey.tableClasses.PoleDetailTypeBean;
import com.survey.tableClasses.TubeWellSurveyBean;
import com.survey.util.GetDate;
import com.survey.util.UniqueIDGenerator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.util.PDFMergerUtility;

/**
 *
 * @author Administrator
 */
public class PoleSurveyDataController1 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
     
  int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Area TYPE Controller....");
        ServletContext ctx = getServletContext();
        PoleSurveyDataModel areaTypeModel = new PoleSurveyDataModel();
        areaTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        areaTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        areaTypeModel.setDb_username(ctx.getInitParameter("db_username"));
        areaTypeModel.setDb_password(ctx.getInitParameter("db_password"));
        areaTypeModel.setConnection();
        String task = request.getParameter("task");
        
        String task1=request.getParameter("task1");
        if(task1==null){
            task1="";
        }
        String meter_name_search = "",irvs_search = "",circuit_search="";
        try {

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getmeter_name_search")) {
                    list = areaTypeModel.getSearchMeterName(q);
                }else if (JQstring.equals("getirvs_search")) {
                    list = areaTypeModel.getSearchIrvsNo(q);
                } else if (JQstring.equals("getcircuit_search")) {
                    list = areaTypeModel.getCircuitNo(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                areaTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("Error in Jquery function()::" + e);
        }
   meter_name_search = request.getParameter("survey_type_search1");
        if (meter_name_search == null) {// survey_type_search,meter_no_search,meter_reading_search
            meter_name_search = "";
        }
        irvs_search = request.getParameter("meter_no_search1");
        if (irvs_search == null) {
            irvs_search = "";
        }
         circuit_search = request.getParameter("meter_reading_search1");
         if (circuit_search == null) {
            circuit_search = "";
        }
       String  pole_search = request.getParameter("meter_name_search1");
         if (pole_search == null) {
            pole_search = "";
        }
        
        if (task == null) {
            task = "";
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
        
          if(task.equals("Update")){
          String ids=request.getParameter("updateid");
        //  String ids=request.getParameter("id");
            List<PoleDetailTypeBean> dataList = areaTypeModel.showDataById(ids);
            String imgpath=areaTypeModel.getImagePath(ids);
            for(int i=0;i<dataList.size();i++){
                     request.setAttribute("imgpath", imgpath);
                     request.setAttribute("poleid", dataList.get(i).getPole_id());
                  request.setAttribute("polespan", dataList.get(i).getPole_span());
                  request.setAttribute("poleheight", dataList.get(i).getPole_height());
                  request.setAttribute("mountingheight", dataList.get(i).getMounting_height());
                  request.setAttribute("roadname", dataList.get(i).getRoad_name());
                  request.setAttribute("areaname", dataList.get(i).getArea_name());
                  request.setAttribute("poletype", dataList.get(i).getPole_type());
                  request.setAttribute("mountingtype", dataList.get(i).getMounting_type());
                  request.setAttribute("trfictype", dataList.get(i).getTraffic_type());
                  request.setAttribute("lightname", dataList.get(i).getLightname());
                  request.setAttribute("remark", dataList.get(i).getRemark());
                  request.setAttribute("poleno", dataList.get(i).getPole_no());
                  request.setAttribute("max_lux_level", dataList.get(i).getMax_avg_lux_level());
                  request.setAttribute("min_lux_level", dataList.get(i).getMin_avg_lux_level());
                  request.setAttribute("avg_lux_level", dataList.get(i).getAvg_lux_level());
                  request.setAttribute("standard_lux_level", dataList.get(i).getStandard_lux_level());
                  request.setAttribute("lattitude", dataList.get(i).getLatitude());
                  request.setAttribute("longitude", dataList.get(i).getLongitude());
                  request.setAttribute("areaid", dataList.get(i).getArea_id());
                  request.setAttribute("roadid", dataList.get(i).getRoad_id());
                  request.setAttribute("traffictypeid", dataList.get(i).getTraffic_type_id());
                  request.setAttribute("polelighttypeid", dataList.get(i).getPole_light_type_id());
                
            }
           
          }
          
          
          
        /// view image part 
         if (task.equals("viewFirstPoleImage")) {
             try{
                    int imageid1=0;
                    imageid1 = Integer.parseInt(request.getParameter("circiut_id"));
 

                   

                    List imageList = new ArrayList();
                    String destinationPath = "";
                      
                    String destination = areaTypeModel.getImageDestinationPath(imageid1);
//  if (destination.equals("") || destination.isEmpty()) {
//                    destinationPath = destination + "//" + "no_image.png";
//                     imageList.add(destinationPath);
//                } else {
                
                   File f = new File(destination);
                    File folder = new File(destination);
                    File[] listOfFiles = folder.listFiles();
                       
            
 
                            

                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
//                      
                            String name1 = listOfFiles[i].getName();
                            /////////////////////////////////////filterImageName//////////////////////////////////////////////                                                       
                            CircuitSurveyBean fd = new CircuitSurveyBean();
                            fd.setFirst_pole_image(name1);
//                                   fd.setTask(task2);
                            imageList.add(fd);

                        }
                    } 
                    int image_list_length = imageList.size();
                    request.setAttribute("imageList", imageList);
                  
                    request.setAttribute("imageid1", imageid1);
                    request.setAttribute("image_list_lenght", image_list_length);
                    request.getRequestDispatcher("MultipleImagePolesurvey").forward(request, response);
                    return;
                
             
            } catch(Exception e){
                System.out.println("Exception:"+e);
            
            }
         }else if (task1.equals("getImage") || task1.equals("getImageThumb")) {
             try{
            int imageid1=0;
            
            
            imageid1 = Integer.parseInt(request.getParameter("imageid1").trim());
            String image_name1 = request.getParameter("image_name").trim();
            String destinationPath = areaTypeModel.getImageDestinationPath(imageid1);
            destinationPath = destinationPath + "\\" + image_name1;
            File f = new File(destinationPath);
            FileInputStream fis = null;
            if (!f.exists()) {
                destinationPath = "C:\\ssadvt_repository\\prepaid\\general\\no_image.png";
                f = new File(destinationPath);
            }
            fis = new FileInputStream(f);
            if (destinationPath.contains("pdf")) {
                response.setContentType("pdf");
            } else {
                response.setContentType("image/jpeg");
            }
            
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            // BufferedImage bi=ImageIO.read(f);
            response.setContentLength(fis.available());
            ServletOutputStream os = response.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(os);
            int ch = 0;
            while ((ch = bis.read()) != -1) {
                out.write(ch);
            }
            
            bis.close();
            fis.close();
            out.close();
            os.close();
            response.flushBuffer();
            
            return;
        } 

               
          catch (Exception e) {
            System.out.println("exception in image view part" + e);
        }
         }
        
        // view image part end 
        
        
        
        //view image
              /// view image part 
         if (task.equals("viewlastPoleImage")) {
             try{
                    int imageid1=0;
                    imageid1 = Integer.parseInt(request.getParameter("circiut_id"));
 

                   

                    List imageList = new ArrayList();
                    String destinationPath = "";
                      
                    String destination = areaTypeModel.getImageDestinationPath1(imageid1);


                    File f = new File(destination);
                    File folder = new File(destination);
                    File[] listOfFiles = folder.listFiles();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
//                      
                            String name1 = listOfFiles[i].getName();
                            /////////////////////////////////////filterImageName//////////////////////////////////////////////                                                       
                            CircuitSurveyBean fd = new CircuitSurveyBean();
                            fd.setLast_pole_image(name1);
//                                   fd.setTask(task2);
                            imageList.add(fd);

                        }
                    }
                    int image_list_length = imageList.size();
                    request.setAttribute("imageList", imageList);
                  
                    request.setAttribute("imageid1", imageid1);
                    request.setAttribute("image_list_lenght", image_list_length);
                    request.getRequestDispatcher("MultipleImagesurvey_last").forward(request, response);
                    return;
                
             
            } catch(Exception e){
                System.out.println("Exception:"+e);
            
            }
         }else if (task1.equals("getImage") || task1.equals("getImageThumb")) {
             try{
            int imageid1=0;
            
            
            imageid1 = Integer.parseInt(request.getParameter("imageid1").trim());
            String image_name1 = request.getParameter("image_name").trim();
            String destinationPath = areaTypeModel.getImageDestinationPath1(imageid1);
            destinationPath = destinationPath + "\\" + image_name1;
            File f = new File(destinationPath);
            FileInputStream fis = null;
            if (!f.exists()) {
                destinationPath = "C:\\ssadvt_repository\\prepaid\\general\\no_image.png";
                f = new File(destinationPath);
            }
            fis = new FileInputStream(f);
            if (destinationPath.contains("pdf")) {
                response.setContentType("pdf");
            } else {
                response.setContentType("image/jpeg");
            }
            
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
            // BufferedImage bi=ImageIO.read(f);
            response.setContentLength(fis.available());
            ServletOutputStream os = response.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(os);
            int ch = 0;
            while ((ch = bis.read()) != -1) {
                out.write(ch);
            }
            
            bis.close();
            fis.close();
            out.close();
            os.close();
            response.flushBuffer();
            
            return;
        } 

               
          catch (Exception e) {
            System.out.println("exception in image view part" + e);
        }
         }
         
         
         
         
          if (task.equals("Save")){
         String pole_id=request.getParameter("pole_id");
         String pole_type=request.getParameter("pole_type");
         String lightname=request.getParameter("lightname");
         String poleno=request.getParameter("poleno");
         String pole_span=request.getParameter("pole_span");
         String pole_height=request.getParameter("pole_height");
         String mounting_type=request.getParameter("mounting_type");
         String mounting_height=request.getParameter("mounting_height");
         String gps_code=request.getParameter("gps_code");
         String max_avg_lux_level=request.getParameter("max_avg_lux_level");
         String standard_lux_level=request.getParameter("standard_lux_level");
         String min_avg_lux_level=request.getParameter("min_avg_lux_level");
         String avg_lux_level=request.getParameter("avg_lux_level");
         String is_working=request.getParameter("is_working");
         String road_name=request.getParameter("road_name");
         String traffic_type=request.getParameter("traffic_type");
         String area_name=request.getParameter("area_name");
         String longitude=request.getParameter("longitude");
         String latitude=request.getParameter("lattitude");
         String imgpath=request.getParameter("imgpath");
//         
//         String pole_type_id=String.valueOf(areaTypeModel.getPoleTypeId(pole_type));
//         String area_id=String.valueOf(areaTypeModel.getAreaTypeId(area_name));
//         String road_id=String.valueOf(areaTypeModel.getroadTypeId(road_name));
//         String pole_light_type_id=String.valueOf(areaTypeModel.getPoleLightTypeId(lightname));
//         String traffic_type_id=String.valueOf(areaTypeModel.getTrafficTypeId(traffic_type));
//         String mounting_type_id=String.valueOf(areaTypeModel.getMountingTypeId(mounting_type));
          PoleDetailTypeBean ptb=new PoleDetailTypeBean();
            ptb.setPole_id(Integer.parseInt(pole_id));
               
             ptb.setPole_type(pole_type);
            
           
            ptb.setPole_span(pole_span);
            ptb.setPole_height(pole_height);
            ptb.setMounting_height(mounting_height);
            ptb.setRemark("remark");
              
            ptb.setMounting_type(mounting_type);
           
            ptb.setActive("Y");
            ptb.setPole_no("T_1");
            ptb.setGps_code(gps_code);
            ptb.setMin_avg_lux_level(min_avg_lux_level);
            ptb.setMax_avg_lux_level(max_avg_lux_level);
            ptb.setStandard_lux_level(standard_lux_level);
            ptb.setAvg_lux_level(avg_lux_level);
            ptb.setIs_working(is_working);
            String pole_rev_no="";
            
              ptb.setPole_rev_no(0);
         
          
        
          
              ptb.setLongitude(Double.parseDouble(longitude));
           
               ptb.setLatitude(Double.parseDouble(latitude));
          
           
            ptb.setArea_name(area_name);
            
            ptb.setRoad_name(road_name);
          
            ptb.setTraffic_type(traffic_type);
             
                String isSwitchingPoint="Yes";
            
            ptb.setIs_switch_point(isSwitchingPoint);
              
            ptb.setRoad_rev_no(0);
            
            ptb.setTubewell_revno(0);
            
            ptb.setTubewell_id(1);
             
            ptb.setSwitch_point_detail_id(1);
            
            ptb.setSwitching_rev_no(0);
             
            ptb.setLightname(lightname);
            
            
            
       int survey_idinserted=areaTypeModel.insertPoleSurveyRecordInMain(ptb);
         if(survey_idinserted>0){
        int ins=areaTypeModel.insertImagePath(pole_id, imgpath);
         }
              System.out.println("ok");
         }
          
          
//            if (task1.equals("generateReport")) {
//                String jrxmlFilePath;
//                List list = null;
//                try {
//                    userEntryByImageBean userEntryByImageBean1 = new userEntryByImageBean();
//                    String traffic_police_id = request.getParameter("traffic_police_id");
//                    ////////////////////////////////////////////////////////////////////////
//                    List<String> imagePathList = tubeWellSurveyModel.getImagePath(traffic_police_id);
//
//                    int listSize = imagePathList.size();
//                    for(int i=0;i<listSize;i++){
//                        if(i ==0){
//                            userEntryByImageBean1.setImgPath(imagePathList.get(i));
//                        }
//                        if(i ==1){
//                            userEntryByImageBean1.setImgPath1(imagePathList.get(i));
//                        }
//                    }
//   
//            response.setContentType("application/pdf");
//                    ServletOutputStream servletOutputStream = response.getOutputStream();
//                    
//                    //jrxmlFilePath = ctx.getRealPath("/report/shift/onlineChallanReport3.jrxml");
//                    jrxmlFilePath = ctx.getRealPath("/report/shift/TestReport.jrxml");
//                    
//                    
//                    list = tubeWellSurveyModel.showMISData1(traffic_police_id,userEntryByImageBean1);
//                    //list = tubeWellSurveyModel.showMISData2(userEntryByImageBean1,traffic_police_id);
//                         // list = tubeWellSurveyModel.showAllData(searchOfficerName,searchMobile,searchPlaceOf,searchAmount ,searchcaseDate,searchBookNo, searchOffenceType, searchActType, searchVehicleType, searchVehicleNo, searchFromDate, searchToDate, searchJarayamNo, searchOffenceCode, searchOffenderLicenseNo, searchReceiptBookNo,searchChallanNo);               // list.add(userEntryByImageBean1);
//                    byte[] reportInbytes = tubeWellSurveyModel.generateRecordList(jrxmlFilePath, list);
//                    response.setContentLength(reportInbytes.length);
//                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                    servletOutputStream.flush();
//                    servletOutputStream.close();
//                    //tubeWellSurveyModel.closeConnection();
//                    return;
//            
//               } catch (Exception e) {
//                    System.out.println("Exception is:" + e);
//                }
//            }
            if(task1.equals("generatePDFReport"))
        {
        
            String survey_id = request.getParameter("survey_id");
            PoleDetailTypeBean p=new PoleDetailTypeBean();
               
                  List imageList = new ArrayList();
                String jrxmlFilePath;
                 List<String> imagePathList = areaTypeModel.getImagePathpdf(Integer.parseInt(survey_id));

                    int listSize = imagePathList.size();
                    for(int i=0;i<listSize;i++){
                          File folder = new File(imagePathList.get(i));
                          File[] listOfFiles = folder.listFiles();

                    for (int i1 = 0; i1 < listOfFiles.length; i1++) {
                        if (listOfFiles[i1].isFile()) {
//                      
                            String name1 = listOfFiles[i1].getName();
                            /////////////////////////////////////filterImageName//////////////////////////////////////////////                                                       
                          //  CircuitSurveyBean fd = new CircuitSurveyBean();
                          String fullPath = imagePathList.get(i)+"\\"+name1;
                            p.setImgpath(fullPath);
//                                   fd.setTask(task2);
                           // imageList.add(p);

                        }
                    }
//                        if(i ==0){
//                            p.setImgpath(imagePathList.get(i));
//                        }
//                        if(i ==1){
//                            p.setImgpath(imagePathList.get(i));
//                        }
                    }
   
             
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
               List  listAll=areaTypeModel.showDataById11(survey_id,p);
          
                jrxmlFilePath = ctx.getRealPath("/Report/pole_survey.jrxml");
                byte[] reportInbytes = areaTypeModel.generateSiteList(jrxmlFilePath,listAll);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                areaTypeModel.closeConnection();
                return;
            } 
         
       
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
        
        
        
        
        noOfRowsInTable = areaTypeModel.getNoOfRows(meter_name_search,irvs_search,circuit_search);

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
        }else if (task.equals("Show All Records")) {
            meter_name_search = "";

            irvs_search = "";
           circuit_search="";
        }
        List<PoleDetailTypeBean> dataTypeList = areaTypeModel.showData(lowerLimit, noOfRowsToDisplay, pole_search);
        lowerLimit = lowerLimit + dataTypeList.size();
        noOfRowsTraversed = dataTypeList.size();

        // forwarding parameters to jsp
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("dataTypeList", dataTypeList);
        request.setAttribute("ids", request.getParameter("updateid"));
        System.out.println("iiiiiiiiidddddddddd"+request.getParameter("updateid"));
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        //end  of search table

        System.out.println("color is :" + areaTypeModel.getMsgBgColor());   
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
          request.setAttribute("dataTypeList", dataTypeList);    
        request.setAttribute("meter_name_search", meter_name_search);
        request.setAttribute("irvs_search", irvs_search);
        request.setAttribute("circuit_search", circuit_search);
        request.setAttribute("message", areaTypeModel.getMessage());
        request.setAttribute("msgBgColor", areaTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/poledatasurveyview").forward(request, response);
   }
@Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
    }
}
