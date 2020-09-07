/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.TubeWellUserModel;
import com.survey.tableClasses.TubeWellUserTypeBean;
import com.survey.util.UniqueIDGenerator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
/**
 *
 * @author JPSS
 */
public class TubeWellUserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        TubeWellUserModel tubeWellUserModel = new TubeWellUserModel();
        tubeWellUserModel.setDriverClass(ctx.getInitParameter("driverClass"));
        tubeWellUserModel.setConnectionString(ctx.getInitParameter("connectionString"));
        tubeWellUserModel.setDb_username(ctx.getInitParameter("db_username"));
        tubeWellUserModel.setDb_password(ctx.getInitParameter("db_password"));
        tubeWellUserModel.setConnection();
            Map<String, String> map = new HashMap<String, String>();
     
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTubeWellUserType")) {
                    list = tubeWellUserModel.getTubeWellUserType(q);
                } else if (JQstring.equals("getWardNo")) {
                    list = tubeWellUserModel.getWardNo(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                tubeWellUserModel.closeConnection();
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
                    //System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                    map.put(item.getFieldName(), item.getString("UTF-8"));
                } else {
                    //System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
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
                        destination_path = tubeWellUserModel.getRepositoryPath(image_uploaded_for);
                        File file = new File(destination_path);
                     //   if(image_name.startsWith("IMG")){
                            file=new  File(destination_path+"//"+"image");
                        if (!file.exists()) {
                            isCreated = file.mkdirs();
                        }

                        item.write(new File(destination_path + "\\" + image_name));
                        list.add(new File(destination_path + "\\" + image_name));
                     //    }
//                        if (ext.equals(".json")) {
//
//                            JSONParser parser = new JSONParser();
//                            Map<String, String> json_map = new HashMap<String, String>();
//
//                            try {
//                                System.out.println("Reading JSON file from Java program");
//                                FileReader fileReader = new FileReader(file + "\\" + image_name);
//                                JSONObject json = (JSONObject) parser.parse(fileReader);
//
//                                json.get("survey_by");
//                                String result = json.get("survey_data").toString();
//                                result = result.replaceAll("\\{", "").replaceAll("\\}", "");
//                                String[] json_data = result.split(",");
//                                for (int i = 1; i <= json_data.length; i++) {
//                                    String[] json_result = json_data[i].split("=");
//                                    System.out.println("Json Data :" + json_result[0] + " " + json_result[1]);
//                                    json_map.put(json_result[0], json_result[1]);
//                                }
//                                System.out.println(" " + json.get("meter_status"));
//
//                            } catch (Exception ex) {
//                                ex.printStackTrace();
//                            }
//                        }
//                        if(image_name.startsWith("VID")){
//                         file=new  File(destination_path+"//"+"video");
//                        if (!file.exists()) {
//                            isCreated = file.mkdirs();
//                        }
//
//                        item.write(new File(destination_path + "\\" + image_name));
//                        list.add(new File(destination_path + "\\" + image_name));
//                        }
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
              String task1=request.getParameter("task1");
        if(task1==null){
        task1="";}
        if (task1.equals("generateMapReport")) {
            String searchTubeWellUser = "";
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = tubeWellUserModel.showAllData(searchTubeWellUser);
            jrxmlFilePath = ctx.getRealPath("/report/tubeWellUSer.jrxml");
            byte[] reportInbytes = tubeWellUserModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        if (task.equals("Delete")) {
            tubeWellUserModel.deleteRecord(Integer.parseInt(map.get("tubewell_user_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int tubewell_user_id;
            try {
                tubewell_user_id = Integer.parseInt(map.get("tubewell_user_id"));
            } catch (Exception e) {
                tubewell_user_id = 0;
            }
            if (task.equals("Save AS New")) {
                tubewell_user_id = 0;
            }
            TubeWellUserTypeBean tubeWellUserTypeBean = new TubeWellUserTypeBean();
            tubeWellUserTypeBean.setTubewell_user_id(tubewell_user_id);
         //   tubeWellUserTypeBean.setTubewell_user_id(Integer.parseInt(map.get("tubewell_user_id")));
            tubeWellUserTypeBean.setWard_id_m(tubeWellUserModel.getWardId(map.get("ward_no_m")));
            tubeWellUserTypeBean.setType_of_premises_id(tubeWellUserModel.getPremisesId(map.get("type_of_premises")));
            tubeWellUserTypeBean.setSerial_no(map.get("serial_no"));
            tubeWellUserTypeBean.setDept_consumer_code(map.get("dept_consumer_code"));
            tubeWellUserTypeBean.setRefrence_number(map.get("refrence_number"));
            tubeWellUserTypeBean.setUser_name(map.get("user_name"));
            tubeWellUserTypeBean.setAddress(map.get("address"));
            tubeWellUserTypeBean.setDate_of_installation(map.get("date_of_installation"));
            tubeWellUserTypeBean.setSize(map.get("size"));
            tubeWellUserTypeBean.setMeter_no(map.get("meter_no"));
            tubeWellUserTypeBean.setContact_person(map.get("contact_person"));
            tubeWellUserTypeBean.setContact_no(map.get("contact_no"));
            tubeWellUserTypeBean.setWard_no_m(map.get("ward_no_m"));
            tubeWellUserTypeBean.setType_of_premises(map.get("type_of_premises"));
            tubeWellUserTypeBean.setImage_name(map.get("image_name"));
            if (tubewell_user_id == 0) {
                System.out.println("Inserting values by model......");
                tubeWellUserModel.insertRecord(tubeWellUserTypeBean,list);
            } else {
                System.out.println("Update values by model........");
                tubeWellUserModel.updateRecord(tubeWellUserTypeBean);
            }
        }
        // Start of Auto Completer code
        String searchTubeWellUser = "";

        // End of Auto Completer code
        searchTubeWellUser = map.get("searchTubeWellUser");
        try {
            if (searchTubeWellUser == null) {
                searchTubeWellUser = "";
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
        System.out.println("searching.......... " + searchTubeWellUser);
        noOfRowsInTable = tubeWellUserModel.getNoOfRows(searchTubeWellUser);

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
        } else if (task.equals("Show All Records")) {
            searchTubeWellUser = "";
        }
     
         if (task1.equals("viewImage")) {
            try {
                String path="";
                int general_image_details_id=Integer.parseInt(request.getParameter("general_image_details_id"));
                String image_name=request.getParameter("image_name");
                
                String destination = tubeWellUserModel.getdestinationPath(general_image_details_id, image_name);
                if (destination.equals("") || destination.isEmpty()) {
                    path = destination+"//" + "Sun.bmp";
                } else {
                    path = destination + "\\" + image_name;
                }
                File f = new File(path);

                FileInputStream fis = new FileInputStream(f);
                String ext = path.split("\\.")[1];
                if (ext.equals("xlsx") || ext.equals("xls")) {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename=sheet.xls");
                } else {
                    if (ext.equals("pdf")) {
                        response.setContentType("application/" + ext);
                    } else {
                        response.setContentType("image/" + ext);
                    }
                }
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                response.setContentLength(fis.available());
                ServletOutputStream os1 = response.getOutputStream();
                BufferedOutputStream out1 = new BufferedOutputStream(os1);
                int ch = 0;
                while ((ch = bis.read()) != -1) {
                    out1.write(ch);
                }

                bis.close();
                fis.close();
                out1.close();
                os1.close();
                response.flushBuffer();

               tubeWellUserModel.closeConnection();
                return;
            } catch (Exception e) {
                System.out.println("Exception occur: " + e);
            }
        }

        // Logic to show data in the table.
        List<TubeWellUserTypeBean> tubeWellUserTypeList = tubeWellUserModel.showData(lowerLimit, noOfRowsToDisplay, searchTubeWellUser);
        lowerLimit = lowerLimit + tubeWellUserTypeList.size();
        noOfRowsTraversed = tubeWellUserTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("tubeWellUserTypeList", tubeWellUserTypeList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        // End of Search Table
        System.out.println("color is :" + tubeWellUserModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchTubeWellUser", searchTubeWellUser);
        request.setAttribute("message", tubeWellUserModel.getMessage());
        request.setAttribute("msgBgColor", tubeWellUserModel.getMsgBgColor());
        request.getRequestDispatcher("/tubeWellUserView").forward(request, response);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
