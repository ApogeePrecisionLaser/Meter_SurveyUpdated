/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.dataEntry.controller;

import com.survey.dataEntry.model.SwitchingPointReportModel;
import com.survey.tableClasses.SwitchingPointReportBean;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class SwitchingPointReport extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Switching Point Controller....");
        ServletContext ctx = getServletContext();
        SwitchingPointReportModel model = new SwitchingPointReportModel();
        model.setDriverClass(ctx.getInitParameter("driverClass"));
        model.setConnectionString(ctx.getInitParameter("connectionString"));
        model.setDb_username(ctx.getInitParameter("db_username"));
        model.setDb_password(ctx.getInitParameter("db_password"));
        model.setConnection();
        String view = "switchingPointReport";
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }

        if (task.equals("ShowZoneHtmlViewOfSwitching")) {
            String zone = request.getParameter("zone");
            request.setAttribute("zone", zone);
            request.setAttribute("switchinglist", model.showZoneData(zone));
            view = "zoneSwitchingDetailView";
        }
        if (task.equals("ShowSwitchingPointsOfPolesInAZone")) {
            String zone = request.getParameter("zone");
            request.setAttribute("zone", zone);
            request.setAttribute("polelist", model.showSwitchingPointsOfPoles(zone));
            view = "zonePoleDetailSwitchingView";
        }
        if (task.equals("showPoleDetailofSwitchingPoint")) {
            String zone = request.getParameter("zone");
            String switching_pt_name = request.getParameter("switching_pt_name");
            request.setAttribute("switching_pt_name", switching_pt_name);
            request.setAttribute("zone", zone);
            request.setAttribute("polelist", model.showPoleDetailofSwitchingPoint(zone,switching_pt_name));
            view = "zonePoleDetailView";
        }


        List<SwitchingPointReportBean> zone_list = model.getZoneReport();
        int sum_switching_point_nos = model.getSumTotalSwitchingNos();
        int sum_pole_nos = model.getSumTotalPoleNos();
        int sumTotalPoleNosFromPole = model.getSumTotalPoleNosFromPole();

        request.setAttribute("zone_list", zone_list);
        request.setAttribute("sum_switching_point_nos", sum_switching_point_nos);
        request.setAttribute("sum_pole_nos", sum_pole_nos);
        request.setAttribute("sumTotalPoleNosFromPole", sumTotalPoleNosFromPole);


        request.getRequestDispatcher(view).forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
