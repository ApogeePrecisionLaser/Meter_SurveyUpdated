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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Administrator
 */
@MultipartConfig
public class CircuitSurveyDataController extends HttpServlet {
private File tmpDir;
    private static final long serialVersionUID = 1L;
    private static final String Save_dir = "images";

    private String extractfileName(Part part) {
        String content = part.getHeader("content-disposition");
        String[] items = content.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {

                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }

        }

        return null;
    }
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
                }else if (JQstring.equals("getirvs_no")) {
                    list = areaTypeModel.getSearchIrvsNo(q);
                } else if (JQstring.equals("getCircuitName")) {
                    list = areaTypeModel.getCurcuitName(q);
                 
                } else if (JQstring.equals("getPole")) {
                    list = areaTypeModel.getPoleNo(q);
                }  
                 else if (JQstring.equals("getCircuitIndex")) {
                      String name = request.getParameter("action2");
                    list = areaTypeModel.getCurcuitIndex(name);
                } 
                else if (JQstring.equals("getcircuit_search")) {
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
       if(task.equalsIgnoreCase("GetCordinates")){
        String latti = request.getParameter("lattitudefirstpole1");
                String longi = request.getParameter("longitudefirstpole1");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("/view/MapView/getCordinateMapWindow1_1.jsp").forward(request, response);
                return;
       }
       if(task.equalsIgnoreCase("GetCordinates1")){
        String latti = request.getParameter("lattitudelastpole1");
                String longi = request.getParameter("longitudelastpole1");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("/view/MapView/getCordinateMapWindow1_2.jsp").forward(request, response);
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
         
           Date dt = new Date();
    
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df1.format(dt);
    cut_dt=cut_dt.replace("-", "");
    cut_dt=cut_dt.replace(":", "");
    cut_dt=cut_dt.replace(" ", ""); 
    
    
    
    
         if(task.equalsIgnoreCase("Save")){
             CircuitSurveyBean c=new CircuitSurveyBean();
            String ivrsno1=request.getParameter("ivrsno1"); 
            String circuitno1=request.getParameter("circuitno1");
            String circuitname=request.getParameter("circuitname");
            String firstpole=request.getParameter("firstpole");
            String lastpole=request.getParameter("lastpole");
                       
            String syncstatus1=request.getParameter("syncstatus1");
            String accuracylastpole1=request.getParameter("acuracyfirstpole1");
            String acuracyfirstpole1=request.getParameter("accuracylastpole1");
            String  lattitudefirstpole1=request.getParameter("lattitudefirstpole1");
      String lattitudelastpole1=request.getParameter("lattitudelastpole1");   
      String longitudefirstpole1=request.getParameter("longitudefirstpole1");     
      String longitudelastpole1=request.getParameter("longitudelastpole1");  
      String altitudefirstpole=request.getParameter("altitudefirstpole");
      String altitudelastpole=request.getParameter("altitudelastpole");
    int firstpoleid=0;  
    int lastpoleid=0;  
    int cableid=0;  
     String firstpoleimage="";
     String lastpoleimage=""; 
     
      String savepath = "C:\\ssadvt_repository\\meter_survey" + File.separator;
            File file = new File(savepath);
            Part part = request.getPart("img");
            String filename = extractfileName(part);
            filename="Pole".concat(cut_dt)+".jpg";
            part.write(savepath + File.separator + filename);
              c.setFirst_pole_image(savepath + File.separator + filename);
            File file1 = new File(savepath);
            Part part1 = request.getPart("img1");
            String filename1 = extractfileName(part1);
            filename1="Pole".concat(cut_dt)+".jpg";
            part1.write(savepath + File.separator + filename1);
              c.setLast_pole_image(savepath + File.separator + filename);
              String firstpole_id="1";  
               String time1="2021-03-15";        String ischild1="";        String parent1="0";  String cabletypeid="1"; String switching_id="1";
              String lastpole_id="2";
            int i=areaTypeModel.insertRecordPrimary(circuitname,ivrsno1,circuitno1,switching_id,firstpole_id,lastpole_id,cabletypeid,parent1,time1,ischild1,c.getFirst_pole_image(),c.getLast_pole_image(),lattitudefirstpole1,longitudefirstpole1,altitudefirstpole,acuracyfirstpole1,lattitudelastpole1,longitudelastpole1,altitudelastpole ,accuracylastpole1);
            
        
         }
        List<CircuitSurveyBean> dataTypeList = areaTypeModel.showData1(lowerLimit, noOfRowsToDisplay, meter_name_search,irvs_search,circuit_search);
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
        request.getRequestDispatcher("/circuit_surveydata").forward(request, response);
   }
@Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
    }
}
