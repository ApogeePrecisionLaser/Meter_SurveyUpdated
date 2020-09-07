/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.controller;

import com.survey.energyMeter.model.EnergyMeterMISModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pooja
 */
public class EnergyMeterMISController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        EnergyMeterMISModel energyMeterMISModel = new EnergyMeterMISModel();
        energyMeterMISModel .setDriverClass(ctx.getInitParameter("driverClass"));
        energyMeterMISModel.setDb_username(ctx.getInitParameter("db_username"));
        energyMeterMISModel.setDb_password(ctx.getInitParameter("db_password"));
        energyMeterMISModel.setConnectionString(ctx.getInitParameter("connectionString"));
        energyMeterMISModel.setConnection();
         String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        if (task.equals("generateFaultyHealthStatusReport")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/faultyHealthStatusReport.jrxml");
            list = energyMeterMISModel.generateFaultyHealthStatusList();
            byte[] reportInbytes = energyMeterMISModel.generateFaultyHealthStatusReport(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            energyMeterMISModel.closeConnection();
            return;
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
