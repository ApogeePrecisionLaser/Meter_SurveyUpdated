/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.controller;

import com.survey.energyMeter.model.EnergyMeterStatusModel;
import com.survey.energyMeter.tableClasses.EnergyMeterStatus;
import com.survey.util.GetDate;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pooja
 */
public class EnergyMeterStatusController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ServletContext ctx = getServletContext();
            String task = request.getParameter("task");
            task = task != null ? task.trim() : "";

            String searchJunction = request.getParameter("junctionName") == null ? "" : request.getParameter("junctionName");
            String searchReadingDateFrom = request.getParameter("searchReadingDateFrom") == null ? GetDate.getCurrentDate() : request.getParameter("searchReadingDateFrom");
            String searchReadingDateTo = request.getParameter("searchReadingDateTo") == null ? searchReadingDateFrom : request.getParameter("searchReadingDateTo");

            //EnergyMeterStatus energyMeterStatus =  energyMeterStatusModel.showData(searchJunction, searchReadingDateFrom, searchReadingDateTo);

            EnergyMeterStatus energyMeterStatus = ((EnergyMeterStatus) ctx.getAttribute("energyMeterStatus"));
            String receivedData = (String)ctx.getAttribute("receivedData");
            String sentData = (String)ctx.getAttribute("sentData");
            
            if (energyMeterStatus != null) {
                int junction_id = energyMeterStatus.getJunction_id();
                int program_version_no = energyMeterStatus.getProgram_version_no();
//                    int switching_point_detail_id = energyMeterStatus.getSwitching_point_detail_id();
//                    int switching_rev_no = energyMeterStatus.getSwitching_rev_no();
//                    long phase1_healthy_voltage = energyMeterStatus.getPhase1_healthy_voltage();
//                    long phase2_healthy_voltage = energyMeterStatus.getPhase2_healthy_voltage();
//                    long phase3_healthy_voltage = energyMeterStatus.getPhase3_healthy_voltage();
//                    long phase1_healthy_current = energyMeterStatus.getPhase1_healthy_current();
//                    long phase2_healthy_current = energyMeterStatus.getPhase2_healthy_current();
//                    long phase3_healthy_current = energyMeterStatus.getPhase3_healthy_current();
//                    long sanctioned_load = energyMeterStatus.getSanctioned_load();
//                    int no_of_phases = energyMeterStatus.getNo_of_phases();
//                    int no_of_poles = energyMeterStatus.getNo_of_poles();
//                    int current_unit_id = energyMeterStatus.getCurrent_unit_id();
//                    int type_of_premises_id = energyMeterStatus.getType_of_premises_id();
//                    String ivrs_no = energyMeterStatus.getIvrs();
//                    String junction_name = energyMeterStatus.getJunction_name();
//                    String type_of_premises = energyMeterStatus.getType_of_premises();
//                    String voltage_unit = energyMeterStatus.getVoltage_unit();
//                    String current_unit = energyMeterStatus.getCurrent_unit();
//                    String health_status_date = energyMeterStatus.getHealth_status_date();
//                    String health_status_time = energyMeterStatus.getHealth_status_time();
                long voltage1 = energyMeterStatus.getVoltage1();
                long voltage2 = energyMeterStatus.getVoltage2();
                long voltage3 = energyMeterStatus.getVoltage3();
                long current1 = energyMeterStatus.getCurrent1();
                long current2 = energyMeterStatus.getCurrent2();
                long current3 = energyMeterStatus.getCurrent3();
                long activePower1 = energyMeterStatus.getActivePower1();
                long activePower2 = energyMeterStatus.getActivePower2();
                long activePower3 = energyMeterStatus.getActivePower3();
                long power_factor = energyMeterStatus.getPower_factor();
                long consumed_unit = energyMeterStatus.getConsumed_unit();
                long connected_load = energyMeterStatus.getConnected_load();
                int voltage_unit_id = energyMeterStatus.getVoltage_unit_id();
                String reading_date = energyMeterStatus.getReading_date();
                String reading_time = energyMeterStatus.getReading_time();
                String phase1_status = energyMeterStatus.getPhase1_status();
                String phase2_status = energyMeterStatus.getPhase2_status();
                String phase3_status = energyMeterStatus.getPhase3_status();
                String overall_status = energyMeterStatus.getOverall_status();
                String phase1_vc_status = energyMeterStatus.getPhase1_vc_status();
                String phase2_vc_status = energyMeterStatus.getPhase2_vc_status();
                String phase3_vc_status = energyMeterStatus.getPhase3_vc_status();
                int contactorOnStatus = energyMeterStatus.getContactorOnStatus();
                int juncOnTimeHr = energyMeterStatus.getJuncOnTimeHr();
                int juncOnTimeMin = energyMeterStatus.getJuncOnTimeMin();
                int juncOffTimeHr = energyMeterStatus.getJuncOffTimeHr();
                int juncOffTimeMin = energyMeterStatus.getJuncOffTimeMin();
                int juncHr = energyMeterStatus.getJuncHr();
                int juncMin = energyMeterStatus.getJuncMin();
                int juncDate = energyMeterStatus.getJuncDate();
                int juncMonth = energyMeterStatus.getJuncMonth();
                int juncYear = energyMeterStatus.getJuncYear();

                String phase1_fuseIn = energyMeterStatus.getPhase1_fuseIn();
                String phase1_fuseOut = energyMeterStatus.getPhase1_fuseOut();
                String phase1_contactor_switching = energyMeterStatus.getPhase1_contactor_switching();
                String phase1_coil_status = energyMeterStatus.getPhase1_coil_status();
                String phase1_mccbIn = energyMeterStatus.getPhase1_mccbIn();
                String phase1_mccbOut = energyMeterStatus.getPhase1_mccbOut();
                String phase1_voltage_status = energyMeterStatus.getPhase1_voltage_status();
                String phase1_current_status = energyMeterStatus.getPhase1_current_status();
                String phase2_fuseIn = energyMeterStatus.getPhase2_fuseIn();
                String phase2_fuseOut = energyMeterStatus.getPhase2_fuseOut();
                String phase2_contactor_switching = energyMeterStatus.getPhase2_contactor_switching();
                String phase2_coil_status = energyMeterStatus.getPhase2_coil_status();
                String phase2_mccbIn = energyMeterStatus.getPhase2_mccbIn();
                String phase2_mccbOut = energyMeterStatus.getPhase2_mccbOut();
                String phase2_voltage_status = energyMeterStatus.getPhase2_voltage_status();
                String phase2_current_status = energyMeterStatus.getPhase2_current_status();
                String phase3_fuseIn = energyMeterStatus.getPhase3_fuseIn();
                String phase3_fuseOut = energyMeterStatus.getPhase3_fuseOut();
                String phase3_contactor_switching = energyMeterStatus.getPhase3_contactor_switching();
                String phase3_coil_status = energyMeterStatus.getPhase3_coil_status();
                String phase3_mccbIn = energyMeterStatus.getPhase3_mccbIn();
                String phase3_mccbOut = energyMeterStatus.getPhase3_mccbOut();
                String phase3_voltage_status = energyMeterStatus.getPhase3_voltage_status();
                String phase3_current_status = energyMeterStatus.getPhase3_current_status();


                if (task.equals("viewJunctionCurrentStatus")) {
                    EnergyMeterStatusModel energyMeterStatusModel = new EnergyMeterStatusModel();

                    energyMeterStatusModel.setDriverClass(ctx.getInitParameter("driverClass"));
                    energyMeterStatusModel.setConnectionString(ctx.getInitParameter("connectionString"));
                    energyMeterStatusModel.setDb_userName(ctx.getInitParameter("db_username"));
                    energyMeterStatusModel.setDb_userPassword(ctx.getInitParameter("db_password"));
                    energyMeterStatusModel.setConnection();

                    EnergyMeterStatus energyMeterStatusFromDb = energyMeterStatusModel.showData(searchJunction, searchReadingDateFrom, searchReadingDateTo);
                    int switching_point_detail_id = energyMeterStatusFromDb.getSwitching_point_detail_id();
                    int switching_rev_no = energyMeterStatusFromDb.getSwitching_rev_no();
                    long phase1_healthy_voltage = energyMeterStatusFromDb.getPhase1_healthy_voltage();
                    long phase2_healthy_voltage = energyMeterStatusFromDb.getPhase2_healthy_voltage();
                    long phase3_healthy_voltage = energyMeterStatusFromDb.getPhase3_healthy_voltage();
                    long phase1_healthy_current = energyMeterStatusFromDb.getPhase1_healthy_current();
                    long phase2_healthy_current = energyMeterStatusFromDb.getPhase2_healthy_current();
                    long phase3_healthy_current = energyMeterStatusFromDb.getPhase3_healthy_current();
                    long sanctioned_load = energyMeterStatusFromDb.getSanctioned_load();
                    long db_connected_load = energyMeterStatusFromDb.getConnected_load();
                    int no_of_phases = energyMeterStatusFromDb.getNo_of_phases();
                    int no_of_poles = energyMeterStatusFromDb.getNo_of_poles();
                    int current_unit_id = energyMeterStatusFromDb.getCurrent_unit_id();
                    int type_of_premises_id = energyMeterStatusFromDb.getType_of_premises_id();
                    String ivrs_no = energyMeterStatusFromDb.getIvrs();
                    String junction_name = energyMeterStatusFromDb.getJunction_name();
                    String type_of_premises = energyMeterStatusFromDb.getType_of_premises();
                    String voltage_unit = energyMeterStatusFromDb.getVoltage_unit();
                    String current_unit = energyMeterStatusFromDb.getCurrent_unit();
                    String health_status_date = energyMeterStatusFromDb.getHealth_status_date();
                    String health_status_time = energyMeterStatusFromDb.getHealth_status_time();

                    request.setAttribute("junction_id", junction_id);
                    request.setAttribute("program_version_no", program_version_no);
                    request.setAttribute("voltage1", voltage1);
                    request.setAttribute("voltage2", voltage2);
                    request.setAttribute("voltage3", voltage3);
                    request.setAttribute("current1", current1);
                    request.setAttribute("current2", current2);
                    request.setAttribute("current3", current3);
                    request.setAttribute("activePower1", activePower1);
                    request.setAttribute("activePower2", activePower2);
                    request.setAttribute("activePower3", activePower3);
                    request.setAttribute("power_factor", power_factor);
                    request.setAttribute("consumed_unit", consumed_unit);
                    request.setAttribute("voltage_unit_id", voltage_unit_id);
                    request.setAttribute("no_of_phases", no_of_phases);
                    request.setAttribute("no_of_poles", no_of_poles);
                    request.setAttribute("current_unit_id", current_unit_id);
                    request.setAttribute("type_of_premises_id", type_of_premises_id);
                    request.setAttribute("junction_name", junction_name);
                    request.setAttribute("type_of_premises", type_of_premises);
                    request.setAttribute("voltage_unit", voltage_unit);
                    request.setAttribute("current_unit", current_unit);
                    request.setAttribute("reading_date", reading_date);
                    request.setAttribute("reading_time", reading_time);
                    request.setAttribute("phase1_status", phase1_status);
                    request.setAttribute("phase2_status", phase2_status);
                    request.setAttribute("phase3_status", phase3_status);
                    request.setAttribute("overall_status", overall_status);
                    request.setAttribute("health_status_time", health_status_time);
                    request.setAttribute("health_status_date", health_status_date);
                    request.setAttribute("searchReadingDateTo", searchReadingDateTo);
                    request.setAttribute("searchReadingDateFrom", searchReadingDateFrom);
                    request.setAttribute("searchJunction", searchJunction);
                    request.setAttribute("switching_point_detail_id", switching_point_detail_id);
                    request.setAttribute("switching_rev_no", switching_rev_no);
                    request.setAttribute("sanctioned_load", sanctioned_load);
                    request.setAttribute("db_connected_load", db_connected_load);
                    request.setAttribute("phase1_healthy_voltage", phase1_healthy_voltage);
                    request.setAttribute("phase2_healthy_voltage", phase2_healthy_voltage);
                    request.setAttribute("phase3_healthy_voltage", phase3_healthy_voltage);
                    request.setAttribute("phase1_healthy_current", phase1_healthy_current);
                    request.setAttribute("phase2_healthy_current", phase2_healthy_current);
                    request.setAttribute("phase3_healthy_current", phase3_healthy_current);
                    request.setAttribute("phase1_vc_status", phase1_vc_status);
                    request.setAttribute("phase2_vc_status", phase2_vc_status);
                    request.setAttribute("phase3_vc_status", phase3_vc_status);
                    request.setAttribute("connected_load", connected_load);
                    request.setAttribute("ivrs_no", ivrs_no);
                    request.setAttribute("contactorOnStatus", contactorOnStatus);
                    request.setAttribute("juncOnTimeHr", juncOnTimeHr);
                    request.setAttribute("juncOnTimeMin", juncOnTimeMin);
                    request.setAttribute("juncOffTimeHr", juncOffTimeHr);
                    request.setAttribute("juncOffTimeMin", juncOffTimeMin);
                    request.setAttribute("juncHr", juncHr);
                    request.setAttribute("juncMin", juncMin);
                    request.setAttribute("juncDate", juncDate);
                    request.setAttribute("juncMonth", juncMonth);
                    request.setAttribute("juncYear", juncYear);
                    request.setAttribute("phase1_fuseIn", phase1_fuseIn);
                    request.setAttribute("phase1_fuseOut", phase1_fuseOut);
                    request.setAttribute("phase1_contactor_switching", phase1_contactor_switching);
                    request.setAttribute("phase1_coil_status", phase1_coil_status);
                    request.setAttribute("phase1_mccbIn", phase1_mccbIn);
                    request.setAttribute("phase1_mccbOut", phase1_mccbOut);
                    request.setAttribute("phase1_current_status", phase1_current_status);
                    request.setAttribute("phase1_voltage_status", phase1_voltage_status);
                    request.setAttribute("phase2_fuseIn", phase2_fuseIn);
                    request.setAttribute("phase2_fuseOut", phase2_fuseOut);
                    request.setAttribute("phase2_contactor_switching", phase2_contactor_switching);
                    request.setAttribute("phase2_coil_status", phase2_coil_status);
                    request.setAttribute("phase2_mccbIn", phase2_mccbIn);
                    request.setAttribute("phase2_mccbOut", phase2_mccbOut);
                    request.setAttribute("phase2_current_status", phase2_current_status);
                    request.setAttribute("phase2_voltage_status", phase2_voltage_status);
                    request.setAttribute("phase3_fuseIn", phase3_fuseIn);
                    request.setAttribute("phase3_fuseOut", phase3_fuseOut);
                    request.setAttribute("phase3_contactor_switching", phase3_contactor_switching);
                    request.setAttribute("phase3_coil_status", phase3_coil_status);
                    request.setAttribute("phase3_mccbIn", phase3_mccbIn);
                    request.setAttribute("phase3_mccbOut", phase3_mccbOut);
                    request.setAttribute("phase3_current_status", phase3_current_status);
                    request.setAttribute("phase3_voltage_status", phase3_voltage_status);

                    request.setAttribute("receivedData",receivedData);
                    request.setAttribute("sentData",sentData);

                    request.getRequestDispatcher("energyMeter_status_view").forward(request, response);
                    energyMeterStatusModel.closeConnection();
                }

                if (task.equals("getLatestStatus")) {
                    PrintWriter out = response.getWriter();
                    if (energyMeterStatus != null) {
                        String response_data = "junction_id=" + junction_id + "#$" + "program_version_no=" + program_version_no
                                + "#$" + "searchJunction=" + searchJunction + "#$" + "searchReadingDateFrom=" + searchReadingDateFrom + "#$" + "searchReadingDateTo=" + searchReadingDateTo
                                + "#$" + "voltage1=" + voltage1 + "#$" + "voltage2=" + voltage2 + "#$" + "voltage3=" + voltage3
                                + "#$" + "current1=" + current1 + "#$" + "current2=" + current2 + "#$" + "current3=" + current3
                                + "#$" + "activePower1=" + activePower1 + "#$" + "activePower2=" + activePower2 + "#$" + "activePower3=" + activePower3
                                + "#$" + "power_factor=" + power_factor + "#$" + "consumed_unit=" + consumed_unit + "#$" + "voltage_unit_id=" + voltage_unit_id
                                + "#$" + "reading_date=" + reading_date + "#$" + "reading_time=" + reading_time + "#$" + "phase1_status=" + phase1_status
                                + "#$" + "phase2_status=" + phase2_status + "#$" + "phase3_status=" + phase3_status + "#$" + "overall_status=" + overall_status
                                + "#$" + "connected_load=" + connected_load
                                + "#$" + "phase1_vc_status=" + phase1_vc_status + "#$" + "phase2_vc_status=" + phase2_vc_status + "#$" + "phase3_vc_status=" + phase3_vc_status + "#$" + "contactorOnStatus=" + contactorOnStatus
                                + "#$" + "juncOnTimeHr=" + juncOnTimeHr + "#$" + "juncOnTimeMin=" + juncOnTimeMin + "#$" + "juncOffTimeHr=" + juncOffTimeHr + "#$" + "juncOffTimeMin=" + juncOffTimeMin
                                + "#$" + "juncHr=" + juncHr + "#$" + "juncMin=" + juncMin + "#$" + "juncDate=" + juncDate + "#$" + "juncMonth=" + juncMonth + "#$" + "juncYear=" + juncYear
                                + "#$" + "phase1_fuseIn=" + phase1_fuseIn + "#$" + "phase1_fuseOut=" + phase1_fuseOut + "#$" + "phase1_contactor_switching=" + phase1_contactor_switching
                                + "#$" + "phase1_coil_status=" + phase1_coil_status + "#$" + "phase1_mccbIn=" + phase1_mccbIn + "#$" + "phase1_mccbOut=" + phase1_mccbOut
                                + "#$" + "phase2_fuseIn=" + phase2_fuseIn + "#$" + "phase2_fuseOut=" + phase2_fuseOut + "#$" + "phase2_contactor_switching=" + phase2_contactor_switching
                                + "#$" + "phase2_coil_status=" + phase2_coil_status + "#$" + "phase2_mccbIn=" + phase2_mccbIn + "#$" + "phase2_mccbOut=" + phase2_mccbOut
                                + "#$" + "phase3_fuseIn=" + phase3_fuseIn + "#$" + "phase3_fuseOut=" + phase3_fuseOut + "#$" + "phase3_contactor_switching=" + phase3_contactor_switching
                                + "#$" + "phase3_coil_status=" + phase3_coil_status + "#$" + "phase3_mccbIn=" + phase3_mccbIn + "#$" + "phase3_mccbOut=" + phase3_mccbOut
                                + "#$" + "phase1_current_status=" + phase1_current_status + "#$" + "phase2_current_status=" + phase2_current_status + "#$" + "phase3_current_status=" + phase3_current_status
                                + "#$" + "phase1_voltage_status=" + phase1_voltage_status + "#$" + "phase2_voltage_status=" + phase2_voltage_status + "#$" + "phase3_voltage_status=" + phase3_voltage_status
                                + "#$" + "receivedData=" + receivedData + "#$" + "sentData=" + sentData ;

//                                + "#$" + "no_of_phases=" + no_of_phases + "#$" + "no_of_poles=" + no_of_poles + "#$" + "current_unit_id=" + current_unit_id + "#$" + "type_of_premises_id=" + type_of_premises_id
//                                + "#$" + "junction_name=" + junction_name + "#$" + "type_of_premises=" + type_of_premises + "#$" + "voltage_unit=" + voltage_unit + "#$" + "current_unit=" + current_unit
//                                + "#$" + "health_status_date=" + health_status_date + "#$" + "health_status_time=" + health_status_time + "#$" + "ivrs_no=" + ivrs_no + "#$" + "sanctioned_load=" + sanctioned_load
//                                + "#$" + "phase1_healthy_voltage=" + phase1_healthy_voltage + "#$" + "phase2_healthy_voltage=" + phase2_healthy_voltage + "#$" + "phase3_healthy_voltage=" + phase3_healthy_voltage
//                                + "#$" + "phase1_healthy_current=" + phase1_healthy_current + "#$" + "phase2_healthy_current=" + phase2_healthy_current + "#$" + "phase3_healthy_current=" + phase3_healthy_current

                        out.println(response_data);
                        out.flush();

                    }
                }
            } else {
                    String jSON_format = "Oops... junction is not responding.";

                    request.setAttribute("message", jSON_format);
                    request.getRequestDispatcher("errorView").forward(request, response);
                    return;
                }
        } catch (Exception e) {
            System.out.println("EnergyMeterStatusController Exception : " + e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
