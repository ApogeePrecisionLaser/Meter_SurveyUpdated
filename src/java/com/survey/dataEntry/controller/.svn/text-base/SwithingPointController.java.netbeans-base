/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.SwithingPointModel;

import com.survey.tableClasses.SwitchingPoint;
import com.survey.util.UniqueIDGenerator;
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
import javax.servlet.http.HttpSession;
/**
 *
 * @author JPSS
 */
public class SwithingPointController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
        SwithingPointModel sw_pointmodel = new SwithingPointModel();
        SwitchingPoint switchingpoint = new SwitchingPoint();
         HttpSession session = request.getSession(false);
       if (session == null || (String) session.getAttribute("user_name")==null ) {
            // response.sendRedirect("beforeLoginPage");             return;
        }
        try {
        sw_pointmodel.setDriverClass(ctx.getInitParameter("driverClass"));
        sw_pointmodel.setConnectionString(ctx.getInitParameter("connectionString"));
        sw_pointmodel.setDb_username(ctx.getInitParameter("db_username"));
        sw_pointmodel.setDb_password(ctx.getInitParameter("db_password"));
        sw_pointmodel.setConnection();
        } catch (Exception e)
        {
            System.out.println("error in SwithingPointController setConnection() calling try block" + e);
        }
        String input;
        List list = null;
        String data;
        int i = 0;
        String location = "", switching_point_filter = "", feeder_filter = "", zone_filter = "", switching_pt_no_filter,
                switching_point_type_filter, division_filter;
        try {
            zone_filter = request.getParameter("zone_filter");
            if (zone_filter == null) {
                zone_filter = "";
            }
            location = request.getParameter("location_filter");
            if (location == null) {
                location = "";
            }
            switching_point_filter = request.getParameter("switching_point_filter");
            if (switching_point_filter == null) {
                switching_point_filter = "";
            }
            feeder_filter = request.getParameter("feeder_filter");
            if (feeder_filter == null) {
                feeder_filter = "";
            }

            switching_pt_no_filter = request.getParameter("switching_pt_no_filter");
            if (switching_pt_no_filter == null) {
                switching_pt_no_filter = "";
            }

            switching_point_type_filter = request.getParameter("switching_point_type_filter");
            if (switching_point_type_filter == null) {
                switching_point_type_filter = "";
            }

            division_filter = request.getParameter("division_filter");
            if (division_filter == null) {
                division_filter = "";
            }

            if (request.getParameter("q") != null) {
                PrintWriter out = response.getWriter();
                input = request.getParameter("q").trim();
                String jQstr = request.getParameter("action1");
                if (jQstr.equals("feeder")) {
                    list = sw_pointmodel.getfeederList();
                    i = 1;

                } else if (jQstr.equals("getSwitchingPoint")) {
                    if (request.getParameter("action2") != null) {
                        if (request.getParameter("action2").trim().equals("")) {
                            list = sw_pointmodel.getSwitchingPoint(input);
                        } else {
                            list = sw_pointmodel.getSwitchingPoint(input, request.getParameter("action2").trim());
                        }
                    }
                } else if (jQstr.equals("getLocation")) {
                    list = sw_pointmodel.getLocation(input);
                }
                if (jQstr.equals("meter")) {
                    list = sw_pointmodel.getmeter_srv_numList();
                    i = 1;

                }
                Iterator itr = list.iterator();
                int count = 0;
                while (itr.hasNext()) {
                    data = (String) itr.next();
                    if (data.toUpperCase().startsWith(input.toUpperCase())) {
                        out.println(data);
                        count++;
                    }
                }
                if (count == 0 && i == 1) {
                    out.print("No Such feeder Exists.");
                    return;
                }
                if (count == 0 && i == 2) {
                    out.print("No Such service Number Exists.");
                    return;
                }
            } else if (request.getParameter("action1") != null) {
                if (request.getParameter("action1").equals("getSwitchingPointNo")) {
                    String feederName = request.getParameter("action2");
                    PrintWriter out = response.getWriter();
                    String switchingPointNo = sw_pointmodel.getSwitchingPointNo(feederName);
                    sw_pointmodel.closeConnection();
                    out.println(switchingPointNo);
                    return;
                }
            } else {
                try {
                    String requesterprint = request.getParameter("requestprinrt");
                    if (requesterprint != null) {

                        if (requesterprint.equals("PRINT")) {
                            String jrxmlFilePath;
                            response.setContentType("application/pdf");
                            ServletOutputStream servletOutputStream = response.getOutputStream();
                            jrxmlFilePath = ctx.getRealPath("/report/switching_point.jrxml");
                            String swtch_point = request.getParameter("switching_point_filter");
                            String loc = request.getParameter("location_filter");
                            String feeder = request.getParameter("feeder_filter");
                            String zone = request.getParameter("zone_filter");
                            String switching_pt_no = request.getParameter("switching_pt_no_filter");
                            String switching_point_type = request.getParameter("switching_point_type_filter");
                            String division = request.getParameter("division_filter");
                            if (swtch_point == null) {
                                swtch_point = "";
                            }
                            if (loc == null) {
                                loc = "";
                            }
                            if (feeder == null) {
                                feeder = "";
                            }
                            if (zone == null) {
                                zone = "";
                            }
                            if (switching_pt_no == null) {
                                switching_pt_no = "";
                            }
                            if (switching_point_type == null) {
                                switching_point_type = "";
                            }
                            if (division == null) {
                                division = "";
                            }
                            byte[] reportInbytes = sw_pointmodel.generateSWPointList(jrxmlFilePath, swtch_point, loc, feeder, zone, switching_pt_no, switching_point_type, division);
                            response.setContentLength(reportInbytes.length);
                            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                            servletOutputStream.flush();
                            servletOutputStream.close();
                            sw_pointmodel.closeConnection();
                            return;
                        }
                    }

                    String task = request.getParameter("task");
                    if (task != null) {
                        int switching_point_id = 0;
                        int meter_id = 0;
                        if (task.equals("Delete")) {
                            switching_point_id = Integer.parseInt(request.getParameter("switching_point_id"));
                            sw_pointmodel.deleteRecord(switching_point_id);
                        } else if (task.equals("Save") || task.equals("Save AS New")) {
                            try {
                                switching_point_id = Integer.parseInt(request.getParameter("switching_point_id"));            // city_id may or may NOT be available i.e. it can be update or new record.
                            } catch (Exception e) {
                                switching_point_id = 0;
                            }
                            if (task.equals("Save AS New")) {
                                switching_point_id = 0;
                            }
                            
                            switchingpoint.setSwitching_point_id(switching_point_id);
                            String switching_point_name = request.getParameter("switching_point_name").trim();
                            String auto_switching_point_name = request.getParameter("auto_switching_point_name");
                            String address1 = request.getParameter("address_line1").trim();
                            String feeder = request.getParameter("feeder_name").trim();
                            switchingpoint.setAddress1(address1);
                            switchingpoint.setSwitching_point(switching_point_name);
                            switchingpoint.setAuto_switching_point(auto_switching_point_name);
                            switchingpoint.setAddress2(request.getParameter("address_line2").trim());
                            if (request.getParameter("feeder_name") != null && !request.getParameter("feeder_name").isEmpty()) {
                                int feeder_id = sw_pointmodel.getfeeder_id(request.getParameter("feeder_name").trim());
                                switchingpoint.setFeeder_id(feeder_id);
                            }
                            if (request.getParameter("address_line3") != null && !request.getParameter("address_line3").isEmpty()) {
                                switchingpoint.setAddress3(request.getParameter("address_line3").trim());

                            }
                            if (request.getParameter("description") != null && !request.getParameter("description").isEmpty()) {
                                switchingpoint.setDescription(request.getParameter("description").trim());
                            }
                            if (request.getParameter("switching_point_type") != null && !request.getParameter("switching_point_type").isEmpty()) {
                                switchingpoint.setSwitching_point_type(request.getParameter("switching_point_type").trim());
                            }
                            if (request.getParameter("switching_point_type_old") != null && !request.getParameter("switching_point_type_old").isEmpty()) {
                                switchingpoint.setSwitching_point_type_old(request.getParameter("switching_point_type_old").trim());
                            }
                            if (request.getParameter("switching_pt_code") != null && !request.getParameter("switching_pt_code").isEmpty()) {
                                switchingpoint.setSwitching_point_code(request.getParameter("switching_pt_code").trim());
                            }

                            String active;
                            if (request.getParameter("active").equals("yes")) {
                                active = "Y";
                            } else {
                                active = "N";
                            }
                            switchingpoint.setActive(active);
                            if (switching_point_id == 0) {

                                sw_pointmodel.InsertSwitchpoint(switchingpoint);


                            } else {
                                sw_pointmodel.updateSwitchpoint(switchingpoint);
                            }
                        }
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
                    String search = request.getParameter("search_details");
                    if (search != null) {
                        lowerLimit = 0;
                    }

                    noOfRowsInTable = sw_pointmodel.getNoOfRows(switching_point_filter, location, feeder_filter, zone_filter, switching_pt_no_filter, switching_point_type_filter, division_filter);

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
                    if (task != null) {
                        if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
                            lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
                        }
                    }
                    // Logic to show data in the table.
                    List<SwitchingPoint> sw_pointList = sw_pointmodel.showData(lowerLimit, noOfRowsToDisplay, switching_point_filter, location, feeder_filter, zone_filter, switching_pt_no_filter, switching_point_type_filter, division_filter);
                    lowerLimit = lowerLimit + sw_pointList.size();
                    noOfRowsTraversed = sw_pointList.size();

                    // Now set request scoped attributes, and then forward the request to view.
                    request.setAttribute("division_filter", division_filter);
                    request.setAttribute("zone_filter", zone_filter);
                    request.setAttribute("location", location);
                    request.setAttribute("switching_point_filter", switching_point_filter);
                    request.setAttribute("feeder_filter", feeder_filter);
                    request.setAttribute("switching_pt_no_filter", switching_pt_no_filter);
                    request.setAttribute("switching_point_type_filter", switching_point_type_filter);
                    request.setAttribute("lowerLimit", lowerLimit);
                    request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
                    request.setAttribute("sw_pointList", sw_pointList);

                    if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                        request.setAttribute("showFirst", "false");
                        request.setAttribute("showPrevious", "false");
                    }
                    if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                        request.setAttribute("showNext", "false");
                        request.setAttribute("showLast", "false");
                    }
                    request.setAttribute("switching_pt_type_map", sw_pointmodel.getSwitchingPointType());
                    request.setAttribute("IDGenerator", new UniqueIDGenerator());
                    request.setAttribute("message", sw_pointmodel.getMessage());
                    request.setAttribute("msgBgColor", sw_pointmodel.getMsgBgColor());
                    request.setAttribute("switching_point_id",switchingpoint.getSwitching_point_id());
                    sw_pointmodel.closeConnection();
                    request.getRequestDispatcher("switchpoint_view").forward(request, response);
                } catch (Exception e) {
                    System.out.println("Error in SwithingPointController:" + e);
                }
            }
        } catch (Exception e) {
            System.out.println("SwitchingPointController main thread " + e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);

    }
}
