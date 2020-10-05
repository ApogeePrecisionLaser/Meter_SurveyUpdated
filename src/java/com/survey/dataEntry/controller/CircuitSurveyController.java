/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.CircuitSurveyModel;
import com.survey.dataEntry.model.PrimarySurveyModel;
import com.survey.tableClasses.CircuitSurveyBean;
import com.survey.tableClasses.PrimarySurveyBean;
import com.survey.util.UniqueIDGenerator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class CircuitSurveyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
     
  int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Area TYPE Controller....");
        ServletContext ctx = getServletContext();
      CircuitSurveyModel areaTypeModel = new CircuitSurveyModel();
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
        
        if (task == null) {
            task = "";
        }
        
        
        /// view image part 
         if (task.equals("viewFirstPoleImage")) {
             try{
                    int imageid1=0;
                    imageid1 = Integer.parseInt(request.getParameter("circiut_id"));
 

                   

                    List imageList = new ArrayList();
                    String destinationPath = "";
                      
                    String destination = areaTypeModel.getImageDestinationPath(imageid1);


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
                    request.getRequestDispatcher("MultipleImagesurveycircuit").forward(request, response);
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
         //view image
//       if(task.equals("areaDetailReport")){
//               // String wardType="";
//                List listAll = null;
//                String jrxmlFilePath;
//                response.setContentType("application/pdf");
//                ServletOutputStream servletOutputStream = response.getOutputStream();
//                listAll=areaTypeModel.showAllData(searchArea, searchWard);
//                jrxmlFilePath = ctx.getRealPath("/report/newAreaDetailReport.jrxml");
//                byte[] reportInbytes = areaTypeModel.generateMapReport(jrxmlFilePath,listAll);
//                response.setContentLength(reportInbytes.length);
//                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//                servletOutputStream.flush();
//                servletOutputStream.close();
//                return;
//            }
//        if (task.equals("Delete")) {
//
//            areaTypeModel.deleteRecord(Integer.parseInt(request.getParameter("area_id")));  // Pretty sure that city_id will be available.
//        } else if (task.equals("Save") || task.equals("Save AS New")) {
//            int area_id;
//            try {
//                area_id = Integer.parseInt(request.getParameter("area_id"));
//            } catch (Exception e) {
//                area_id = 0;
//            }
//            if (task.equals("Save AS New")) {
//                area_id = 0;
//            }
//            PrimarySurveyBean areaTypeBean = new PrimarySurveyBean();
//            areaTypeBean.setArea_id(area_id);
//            areaTypeBean.setWard_no(request.getParameter("ward_no"));
//            //areaTypeBean.setWard_name(request.getParameter("ward_name"));
//            areaTypeBean.setArea_name(request.getParameter("area_name"));
//            areaTypeBean.setRemark(request.getParameter("remark"));
//            //areaTypeBean.setActive(request.getParameter("active"));
//            areaTypeBean.setWard_id(areaTypeModel.getWardId(request.getParameter("ward_no")));
//            //areaTypeBean.setWard_rev_no(areaTypeModel.getWard_rev_no(request.getParameter("ward_no")));
//
//            if (area_id == 0) {
//                System.out.println("Inserting values by AreaModel......");
//                areaTypeModel.insertRecord(areaTypeBean);
//            } else {
//                System.out.println("Update values by AreaModel........");
//                areaTypeModel.updateRecord(areaTypeBean);
//            }
//        }
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
        List<CircuitSurveyBean> dataTypeList = areaTypeModel.showData(lowerLimit, noOfRowsToDisplay, meter_name_search,irvs_search,circuit_search);
        lowerLimit = lowerLimit + dataTypeList.size();
        noOfRowsTraversed = dataTypeList.size();

        // forwarding parameters to jsp
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("dataTypeList", dataTypeList);

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
        request.getRequestDispatcher("/circuit_survey").forward(request, response);
   }
@Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
    }
}
