/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.survey.energyMeter.controller;

import com.survey.util.UniqueIDGenerator;
import com.survey.energyMeter.model.JunctionModel;
import com.survey.energyMeter.tableClasses.JunctionBean;
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
 * @author jpss
 */
public class JunctionController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Junction Controller....");
        ServletContext ctx = getServletContext();
        JunctionModel junctionModel = new JunctionModel();
        junctionModel .setDriverClass(ctx.getInitParameter("driverClass"));
        junctionModel.setDb_username(ctx.getInitParameter("db_username"));
        junctionModel.setDb_password(ctx.getInitParameter("db_password"));
        junctionModel.setConnectionString(ctx.getInitParameter("connectionString"));
        junctionModel.setConnection();

        String task = request.getParameter("task");
        String searchJunctionName = request.getParameter("searchJunctionName");
        String searchIvrsNo = request.getParameter("searchIvrsNo");

        response.setContentType("text/html;charset=UTF-8");
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getIvrs_no")) {
                    list = junctionModel.getIvrs_no(q);                    
                }else if (JQstring.equals("getJunctionName")) {
                    list = junctionModel.getJunctionName(q);
                }else if (JQstring.equals("getIvrsNo")) {
                    list = junctionModel.getIvrsNo(q);
                }else if (JQstring.equals("getJunctionName1")) {
                    list = junctionModel.getJunctionName1(q);
                }
                else if(JQstring.equals("getPhase")){
                    String ivrs_no = request.getParameter("ivrs_no");
                    int phase =  junctionModel.getPhase(ivrs_no);
                    out.println(phase);
                    return;
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                junctionModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --PatternDetailController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }
        if (task.equals("generateJunctionReport")) {
            if (searchJunctionName == null && searchIvrsNo == null) {
                searchJunctionName = "";
                searchIvrsNo = "";
            }
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/JunctionReport.jrxml");
                        if(searchJunctionName.equals("") && searchIvrsNo.equals("")){
                            list=junctionModel.showAllData();
                        }else
                        list=junctionModel.showData(lowerLimit,noOfRowsToDisplay,searchJunctionName,searchIvrsNo);
                        byte[] reportInbytes =junctionModel.generateRecordList(jrxmlFilePath,list, searchJunctionName,searchIvrsNo);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        junctionModel.closeConnection();
                        return;
            }
        if (task.equals("Cancel")) {
           junctionModel.cancelRecord(Integer.parseInt(request.getParameter("junction_id")), Integer.parseInt(request.getParameter("program_version_no")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Revise")) {
            int junction_id, program_version_no;
            try {
                junction_id = Integer.parseInt(request.getParameter("junction_id"));
                program_version_no = Integer.parseInt(request.getParameter("program_version_no"));
            } catch (Exception e) {
                System.out.println("value of s_no:"+e);
                junction_id = 0;
                program_version_no = 0;
            }
            if (task.equals("Save") || task.equals("Save AS New")) {
                junction_id = 0;
            }
           JunctionBean junctionBean= new JunctionBean();

           junctionBean.setJunction_id(junction_id);
           junctionBean.setProgram_version_no(program_version_no);
           junctionBean.setIvrs_no(request.getParameter("ivrs_no"));
           junctionBean.setController_model(request.getParameter("controller_model"));
           junctionBean.setMobile_no(request.getParameter("mobile_no"));
           junctionBean.setSim_no(request.getParameter("sim_no"));
           junctionBean.setImei_no(request.getParameter("imei_no"));
           junctionBean.setPanel_file_no(request.getParameter("panel_file_no"));
           String energyMeterNo = request.getParameter("energy_meter_no");
           switch (energyMeterNo.length()) {
            case 1:
                energyMeterNo = "0000000" + energyMeterNo;
                break;
            case 2:
                energyMeterNo = "000000" + energyMeterNo;
                break;
            case 3:
                energyMeterNo = "00000" + energyMeterNo;
                break;
            case 4:
                energyMeterNo = "0000" + energyMeterNo;
                break;
            case 5:
                energyMeterNo = "000" + energyMeterNo;
                break;
            case 6:
                energyMeterNo = "00" + energyMeterNo;
                break;
            case 7:
                energyMeterNo = "0" + energyMeterNo;
                break;
            }
           junctionBean.setEnergy_meter_no(energyMeterNo);
           junctionBean.setSanctioned_load(Double.parseDouble(request.getParameter("sanctioned_load")));
           junctionBean.setConnected_load(Double.parseDouble(request.getParameter("connected_load")));
           junctionBean.setPhase1_healthy_voltage(Double.parseDouble(request.getParameter("phase1_healthy_voltage")));
           junctionBean.setPhase1_healthy_current(Double.parseDouble(request.getParameter("phase1_healthy_current")));
           double phase2_healthy_voltage, phase3_healthy_voltage, phase2_healthy_current, phase3_healthy_current;
           try{
               phase2_healthy_voltage = Double.parseDouble(request.getParameter("phase2_healthy_voltage"));
               phase2_healthy_current = Double.parseDouble(request.getParameter("phase2_healthy_current"));
           }catch(Exception e){
               System.out.println("ERROR: in phase2 parseDouble "+e);
               phase2_healthy_voltage = 0;
               phase2_healthy_current = 0;
            }
           try {
               phase3_healthy_voltage = Double.parseDouble(request.getParameter("phase3_healthy_voltage"));
               phase3_healthy_current = Double.parseDouble(request.getParameter("phase3_healthy_current")); 
            }catch(Exception e) {
               System.out.println("ERROR: in phase3 parseDouble "+e);
               phase3_healthy_voltage = 0;
               phase3_healthy_current = 0;
           }
           junctionBean.setPhase2_healthy_voltage(phase2_healthy_voltage);
           junctionBean.setPhase3_healthy_voltage(phase3_healthy_voltage);
           junctionBean.setPhase2_healthy_current(phase2_healthy_current);
           junctionBean.setPhase3_healthy_current(phase3_healthy_current);
           junctionBean.setRemark(request.getParameter("remark"));
           junctionBean.setJunction_name(request.getParameter("junction_name"));
           junctionBean.setActive(request.getParameter("active"));
            if (junction_id == 0) {
                System.out.println("Inserting values by model......");
                junctionModel.insertRecord(junctionBean);
            } else{// if(task.equals("Save")){
                System.out.println("Revise values by model........");
                junctionModel.reviseRecord(junctionBean);
            }
        }


        try {
            if (searchJunctionName == null && searchIvrsNo == null) {
                searchJunctionName = "";
                searchIvrsNo = "";
            }
        } catch (Exception e) {
        }
        if (task.equals("Show All Records")) {
             searchJunctionName = "";
             searchIvrsNo = "";
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
        System.out.println("searching.......... " +  searchIvrsNo +" or " + searchJunctionName);
        noOfRowsInTable = junctionModel.getNoOfRows(searchJunctionName, searchIvrsNo);                  // get the number of records (rows) in the table.

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
        }
         List<JunctionBean> junctionList = junctionModel.showData(lowerLimit,noOfRowsToDisplay, searchJunctionName, searchIvrsNo);
        lowerLimit = lowerLimit + junctionList.size();
        noOfRowsTraversed = junctionList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("junctionList", junctionList);
        request.setAttribute("searchIvrsNo", searchIvrsNo);
        request.setAttribute("searchJunctionName", searchJunctionName);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", junctionModel.getMessage());
        request.setAttribute("msgBgColor", junctionModel.getMsgBgColor());
        request.getRequestDispatcher("junctionView").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
