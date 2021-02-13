/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.util.UniqueIDGenerator;
import com.survey.dataEntry.model.MeterModelABC1;
import com.survey.tableClasses.Meters;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class MeterController1 extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        MeterModelABC1 metersModel = new MeterModelABC1();
        metersModel.setDriverClass(ctx.getInitParameter("driverClass"));
        metersModel.setConnectionString(ctx.getInitParameter("connectionString"));
        metersModel.setDb_username(ctx.getInitParameter("db_username"));
        metersModel.setDb_password(ctx.getInitParameter("db_password"));
        metersModel.setConnection();
        metersModel.setConnection();
        try
        {
            int countnum=0;
            String input = null;
            String task = null;
            List list = null;
            String data = null;
            int i = 0;
            if (request.getParameter("q") != null) {
                PrintWriter out = response.getWriter();
                input = request.getParameter("q").trim();
                String JQstr = request.getParameter("Param");
                try{
                if (JQstr != null) {
                    if (JQstr.equals("Location")) {
                        list = metersModel.getLocationNameList();
                        i = 1;
                    } else if (JQstr.equals("City")) {
                        list = metersModel.getCityList();
                        i = 2;
                    } else if (JQstr.equals("Organisation")) {
                        list = metersModel.getOrganisationList();
                        i = 4;
                    } else if (JQstr.equals("Feeder")) {
                        list = metersModel.getfeederList(Integer.parseInt(request.getParameter("Param1")), Integer.parseInt(request.getParameter("Param2")));
                        i = 5;

                    } else if (JQstr.equals("Street Light")) {
                        list = metersModel.getSwitchingPoint(Integer.parseInt(request.getParameter("Param1")), Integer.parseInt(request.getParameter("Param2")));
                        i = 6;
                    } else if (JQstr.equals("connection")) {
                        String JQstr1 = request.getParameter("Param1");
                        String JQstr3 = request.getParameter("Param3");
                        String JQstr2 = request.getParameter("Param2");
                        String jQstr6 = request.getParameter("Param6");

                        if (JQstr3.equals("Y")) {
                            list = metersModel.getSwitchingPointList(Integer.parseInt(request.getParameter("Param4")), request.getParameter("Param5").trim(), request.getParameter("Param2").trim(), request.getParameter("Param6").trim());
                            i = 13;
                        } else {
                            list = metersModel.officelist(JQstr1, JQstr2, jQstr6);
                            i = 7;
                        }

                    } else if (JQstr.equals("Zone")) {
                        list = metersModel.getZoneList();
                        i = 8;
                    } else if (JQstr.equals("feeder")) {
                        if (request.getParameter("Param1") != null) {
                            if (request.getParameter("Param1").trim().equals("")) {
                                //  list = metersModel.getfeederList();
                                i = 5;
                            } else {
                                list = metersModel.getfeederList(request.getParameter("Param1"));
                                i = 9;
                            }
                        }
                    } else if (JQstr.equals("Circle")) {
                        if (request.getParameter("Param1") != null) {
                            if (request.getParameter("Param1").trim().equals("0")) {
                                list = metersModel.getSearchCircleList();
                                i = 12;
                            } else {
                                int search_company_id = Integer.parseInt(request.getParameter("Param1"));
                                list = metersModel.getSearchCircleList(search_company_id);
                                i = 11;
                            }
                        }
                    } else if (JQstr.equals("Meter")) {
                        if (request.getParameter("Param1") != null)
                        {
                            if (request.getParameter("Param1").trim().equals("")) {
                                list = metersModel.getMeterList();
                                i = 10;
                            } else {
                                list = metersModel.getMeterList(request.getParameter("Param1"));
                                i = 10;
                            }
                        }
                    }
                    else if(JQstr.equals("division")) {
                       if (request.getParameter("Param1")!= null) {
                            if (request.getParameter("Param1").trim().equals("")) {
                                list = metersModel.getZoneList(input);
                               list = metersModel.getDivisionList(input);
                               i=13;
                            }else
                            {
                                   list =metersModel.getDivisionList(input, request.getParameter("Param1").trim());
                                   i=13;
                            }
                       } else {

                           list = metersModel.getDivisionList(input);
                           i=13;
                       }
                    }
                    else if (JQstr.equals("file_no")) {
                        String msn = request.getParameter("Param1");
                        String premisesType = request.getParameter("Param2");
                        if (msn == null) {
                            msn = " ";

                        }
                        if (premisesType == null || premisesType.equals("ALL")) {
                            premisesType = " ";
                        }
                        list = metersModel.getFileNoList(msn, premisesType);
                        i = 14;
                    }
                Iterator itr = list.iterator();
                int count = 0;
                while (itr.hasNext())
                {
                    data = (String) itr.next();
                    if (data.toUpperCase().startsWith(input.toUpperCase())) {
                        out.println(data);
                        count++;
                    }
                }
                if (count == 0 && i == 1) {
                    out.print("No Such Location Exists.");
                    return;
                }
                if (count == 0 && i == 2) {
                    out.print("No Such City Exists.");
                    return;
                }
                if (count == 0 && i == 3) {
                    out.print("No Such Junior Engineer Office Exists.");
                    return;
                }
                if (count == 0 && i == 4) {
                    out.print("No Such Organisation Exists.");
                    return;
                }
                if (count == 0 && i == 6) {
                    out.print("No Such Site Exists.");
                    return;
                }
                if (count == 0 && i == 7) {
                    out.print("No Such Building Exists.");
                    return;
                }
                if (count == 0 && i == 13) {
                    out.print("No Such Swich Point Exists.");
                    return;
                }
                if (count == 0 && i == 8) {
                    out.print("No Such Zone Exists.");
                    return;
                }
                if (count == 0 && i == 9) {
                    out.print("No Such Feeder Exists.");
                    return;
                }
                if (count == 0 && i == 10) {
                    out.print("No Such Meter Service No Exists.");
                    return;
                }
                if (count == 0 && i == 11) {
                    out.print("No Such Circle  Name   Exists.");
                    return;
                }

                if (count == 0 && i == 12) {
                    out.print("No Such Circle  Name   Exists.");
                    return;
                }
                if (count == 0 && i == 13) {
                    out.print("No Such Division   Exists.");
                    return;
                }
                    if(count != 0)
                    return;
                }
                }catch(Exception e)
                { 
                    e.printStackTrace();
                }
            }

            try {
                 task = request.getParameter("task");
                 if(task == null)
                     task = "";
                        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
                    } catch (Exception e) {
                        lowerLimit = noOfRowsTraversed = 0;
                    }
                    String switch_point = null, organisation = null, org_office, zone_filter = "", date_filter = "", mete_ser_num_filter = "",
                            feeder_filter = "", division_filter = "", meter_no = "", file_no = "", premises_type = "", switching_point_sub_type = "", premises_type_details = "", circle_name = "";
                    int organisation_id = 0, switching_point_id = 0, org_office_id = 0;
                    int company_id = 0;
                    String con = request.getParameter("conn");
                    organisation = request.getParameter("organ");

                    if (request.getParameter("search_company_name") != null && !request.getParameter("search_company_name").isEmpty()) {

                        company_id = Integer.parseInt(request.getParameter("search_company_name").trim());
                        
                        request.setAttribute("search_company_id", company_id);
                    }
                    if (request.getParameter("search_circle_name") != null && !request.getParameter("search_circle_name").isEmpty()) {
                        circle_name = request.getParameter("search_circle_name").trim();
                        request.setAttribute("s_circle_name", circle_name);
                    }
                    if (request.getParameter("zone_filter") != null && !request.getParameter("zone_filter").isEmpty()) {
                        zone_filter = request.getParameter("zone_filter").trim();
                        request.setAttribute("zone_filter", zone_filter);
                    }
                    if (request.getParameter("division_filter") != null && !request.getParameter("division_filter").isEmpty()) {
                        division_filter = request.getParameter("division_filter").trim();
                        request.setAttribute("division_filter", division_filter);
                    }
                    if (request.getParameter("file_no") != null && !request.getParameter("file_no").isEmpty()) {
                        file_no = request.getParameter("file_no").trim();
                        request.setAttribute("file_no", file_no);
                    }
                    if (request.getParameter("date_filter") != null && !request.getParameter("date_filter").isEmpty()) {
                        date_filter = request.getParameter("date_filter").trim();
                        request.setAttribute("date_filter", date_filter);
                    }
                    if (request.getParameter("mete_ser_num_filter") != null && !request.getParameter("mete_ser_num_filter").isEmpty()) {
                        mete_ser_num_filter = request.getParameter("mete_ser_num_filter").trim();
                        request.setAttribute("mete_ser_num_filter", mete_ser_num_filter);
                    }
                    if (request.getParameter("feeder_filter") != null && !request.getParameter("feeder_filter").isEmpty()) {
                        feeder_filter = request.getParameter("feeder_filter").trim();
                        request.setAttribute("feeder_filter", feeder_filter);
                    }
                    if (organisation != null && !organisation.isEmpty()) {
                        organisation_id = metersModel.getOgranisation_id(request.getParameter("organ"));
                        request.setAttribute("organ", organisation);
                    } else {
                       // request.setAttribute("organ", (String) session.getAttribute("homeOrgName"));
                    }
                    if (request.getParameter("meter_no") != null && !request.getParameter("meter_no").isEmpty()) {
                        meter_no = request.getParameter("meter_no").trim();
                        request.setAttribute("meter_no", meter_no);
                    }
                    if (request.getParameter("premises_type_search") != null && !request.getParameter("premises_type_search").isEmpty()) {
                        premises_type = request.getParameter("premises_type_search").trim();
                        request.setAttribute("premises_type1", premises_type);
                    } else {
                        premises_type = "ALL";
                        request.setAttribute("premises_type", premises_type);
                    }
                    if (request.getParameter("switching_point_sub_type") != null && !request.getParameter("switching_point_sub_type").isEmpty()) {
                        switching_point_sub_type = request.getParameter("switching_point_sub_type").trim();
                        request.setAttribute("switching_point_sub_type1", switching_point_sub_type);
                    }
                    if (request.getParameter("premises_type") != null && !request.getParameter("premises_type").isEmpty()) {
                        premises_type_details = request.getParameter("premises_type").trim();
                    }
                    if (premises_type != null) {
                        if (premises_type.equals("ALL")) {
                            premises_type = "ALL";
                            premises_type_details = "ALL";
                        } else if (premises_type_details.equals("Y")) {
                            switch_point = request.getParameter("con_type");
                            if (switch_point != null && !switch_point.isEmpty()) {
                                int[] site = metersModel.getSite_id(switch_point);
                                switching_point_id = site[0];
                                request.setAttribute("con_type", switch_point);
                            }
                        } else {
                            org_office = request.getParameter("con_type");

                            if (org_office != null && !org_office.isEmpty()) {
                                org_office_id = metersModel.getOrgOffice_id(organisation_id, org_office);
                                request.setAttribute("con_type", org_office);
                            }
                        }
                        // request.setAttribute("premises_type", premises_type);
                    }
                    String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
                    if (buttonAction == null) {
                        buttonAction = "none";
                    }
                    String search = request.getParameter("search");
                    if (search != null) {
                        lowerLimit = 0;
                    }
                    String active_meter = request.getParameter("act_meter");
                    if (active_meter == null) {
                        active_meter = "Y";
                        request.setAttribute("active_meter", "Active");
                    } else if (active_meter.equals("All")) {
                        active_meter = "All";
                        request.setAttribute("active_meter", "All");
                    } else if (active_meter.equals("Active")) {
                        active_meter = "Y";
                        request.setAttribute("active_meter", "Active");
                    } else {
                        active_meter = "N";
                        request.setAttribute("active_meter", "InActive");
                    }
                    noOfRowsInTable = metersModel.getNoOfRows(con, switching_point_id, organisation_id, org_office_id, active_meter, zone_filter, date_filter, feeder_filter, mete_ser_num_filter, division_filter, meter_no, file_no, premises_type, switching_point_sub_type, premises_type_details, company_id, circle_name);
                    //}// get the number of records (rows) in the table.
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
                    if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Revise") || task.equals("Update")) {
                        lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
                    }
                    // Logic to show data in the table.
                    List meterList = null;
                    meterList = metersModel.showData(lowerLimit, noOfRowsToDisplay, con, switching_point_id, organisation_id, org_office_id, active_meter, zone_filter, date_filter, feeder_filter, mete_ser_num_filter, division_filter, meter_no, file_no, premises_type, switching_point_sub_type, premises_type_details, company_id, circle_name,null,countnum,"","");
                    lowerLimit = lowerLimit + meterList.size();
                    noOfRowsTraversed = meterList.size();
                    // Now set request scoped attributes, and then forward the request to view.
                    // Following request scoped attributes NAME will remain constant from module to module.
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
                    request.setAttribute("meterList", meterList);
                
                request.setAttribute("company_name", metersModel.getCompanyNameMap());
                request.setAttribute("circle_name", metersModel.getcircleName());
                request.setAttribute("tarrif_details", metersModel.getTarrifDetails());
                
                request.setAttribute("switching_pt_sub_type_map", metersModel.getSwitchingPointSubType());
                request.setAttribute("IDGenerator", new UniqueIDGenerator());
                request.setAttribute("message", metersModel.getMessage());
                request.setAttribute("officetypeList", metersModel.getofficetypeList());
                request.setAttribute("msgBgColor", metersModel.getMsgBgColor());
                //     request.setAttribute("city_name", city_name);
                // Following request scoped attributes NAME will change from module to module.
                //       request.setAttribute("meterList", meterList);
                request.setAttribute("sanctloadList", metersModel.getSanctLoadUnitList());
                metersModel.closeConnection();
                request.getRequestDispatcher("meters_view1").forward(request, response);

    }catch (Exception e)
         {
            System.out.println("Error in MeterController " + e);
        }
       /* finally{
        request.getRequestDispatcher("meters_view1").forward(request, response);
        }*/
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
