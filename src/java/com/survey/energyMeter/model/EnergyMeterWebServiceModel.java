/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.survey.energyMeter.model;
import java.sql.PreparedStatement;
import com.survey.energyMeter.tableClasses.EnergyMeterStatus;
import com.survey.energyMeter.tableClasses.PhaseBean;
import com.survey.util.GetDate;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author Pooja
 */
public class EnergyMeterWebServiceModel {

    private Connection connection;
    private HttpSession session;
    private ServletContext context;

    public void setConnection(Connection con) {

        connection = con;
    }

    public void setConnection() {
        try {
            System.out.println("hii");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/meter_survey", "jpss_2", "jpss_1277");
        } catch (Exception e) {
            System.out.println("ReadMailModel setConnection() Error: " + e);
        }
    }

    public static void main(String...args){
        //byte[] receivedBytes = {126,126,0,28,1,0,0,2,1,48,49,51,55,55,55,48,48,48,54,48,57,56,51,50,48,48,56,48,50,55,53,57,87,125,125};// function 1
        //byte[] receivedBytes = {126,126,0,9,1,16,0,2,9,0,0,0,0,0,-13,125,125};// function 9
        byte[] receivedBytes = {126,126,0,46,1,16,1,1,2,90,-49,90,-38,90,-55,0,0,0,23,0,0,0,0,0,1,0,1,0,3,0,3,0,0,7,-6,1,59,59,59,1,1,18,42,5,31,11,34,12,5,16,-111,125};// fun 2
        EnergyMeterWebServiceModel obj = new EnergyMeterWebServiceModel();
        obj.setConnection();
        String res = obj.junctionRefreshFunction(receivedBytes,5,false);
        try{
            obj.sendResponse(res);
        }catch(Exception ex){
            System.out.println("ERROR : in response " + ex);
        }
        System.out.println(res);
    }

    public int junctionRefreshFunction(String dataToProcess) {

        Map map = getMapDataFromString(dataToProcess);

        int status = 0;

        if (!map.isEmpty()) {

            int project_no, junctionID, fileNo, program_version_no;

            String phase1Status, phase2Status, phase3Status, statusByte;

            project_no = map.get("project_no") != null ? Integer.parseInt(map.get("project_no").toString()) : 0;
            junctionID = map.get("junction_id") != null ? Integer.parseInt(map.get("junction_id").toString()) : 0;
            fileNo = map.get("file_no") != null ? Integer.parseInt(map.get("file_no").toString()) : 0;
            program_version_no = map.get("program_version_no") != null ? Integer.parseInt(map.get("program_version_no").toString()) : 0;

            phase1Status = map.get("phase1") != null ? map.get("phase1").toString() : "00000000";
            phase2Status = map.get("phase2") != null ? map.get("phase2").toString() : "00000000";
            phase3Status = map.get("phase3") != null ? map.get("phase3").toString() : "00000000";
            statusByte = map.get("status") != null ? map.get("status").toString() : "00000000";

            System.out.println("Phase1 Status: " + phase1Status);
            System.out.println("Phase2 Status: " + phase2Status);
            System.out.println("Phase3 Status: " + phase3Status);
            System.out.println("Status Value: " + statusByte);

            switch (phase1Status.length()) {
                case 1:
                    phase1Status = "0000000" + phase1Status;
                    break;
                case 2:
                    phase1Status = "000000" + phase1Status;
                    break;
                case 3:
                    phase1Status = "00000" + phase1Status;
                    break;
                case 4:
                    phase1Status = "0000" + phase1Status;
                    break;
                case 5:
                    phase1Status = "000" + phase1Status;
                    break;
                case 6:
                    phase1Status = "00" + phase1Status;
                    break;
                case 7:
                    phase1Status = "0" + phase1Status;
                    break;
            }
            switch (phase2Status.length()) {
                case 1:
                    phase2Status = "0000000" + phase2Status;
                    break;
                case 2:
                    phase2Status = "000000" + phase2Status;
                    break;
                case 3:
                    phase2Status = "00000" + phase2Status;
                    break;
                case 4:
                    phase2Status = "0000" + phase2Status;
                    break;
                case 5:
                    phase2Status = "000" + phase2Status;
                    break;
                case 6:
                    phase2Status = "00" + phase2Status;
                    break;
                case 7:
                    phase2Status = "0" + phase2Status;
                    break;
            }
            switch (phase3Status.length()) {
                case 1:
                    phase3Status = "0000000" + phase3Status;
                    break;
                case 2:
                    phase3Status = "000000" + phase3Status;
                    break;
                case 3:
                    phase3Status = "00000" + phase3Status;
                    break;
                case 4:
                    phase3Status = "0000" + phase3Status;
                    break;
                case 5:
                    phase3Status = "000" + phase3Status;
                    break;
                case 6:
                    phase3Status = "00" + phase3Status;
                    break;
                case 7:
                    phase3Status = "0" + phase3Status;
                    break;
            }
            switch (statusByte.length()) {
                case 1:
                    statusByte = "0000000" + statusByte;
                    break;
                case 2:
                    statusByte = "000000" + statusByte;
                    break;
                case 3:
                    statusByte = "00000" + statusByte;
                    break;
                case 4:
                    statusByte = "0000" + statusByte;
                    break;
                case 5:
                    statusByte = "000" + statusByte;
                    break;
                case 6:
                    statusByte = "00" + statusByte;
                    break;
                case 7:
                    statusByte = "0" + statusByte;
                    break;
            }

            int programVersionNoFromDB = getProgramVersionNo(junctionID);
            int noOfPhasesFromDB = getNoOfPhases(junctionID, programVersionNoFromDB);
            int typeOfConnectionFromDB = getTypeOfConnection(junctionID, programVersionNoFromDB);


            String[] receivedPhase1status = phase1Status.split("");
            String[] receivedPhase2status = phase2Status.split("");
            String[] receivedPhase3status = phase3Status.split("");
            String[] receivedStatus = statusByte.split("");

            phase1Status = getHealthStatus(receivedPhase1status);
            phase2Status = noOfPhasesFromDB != 1 ? getHealthStatus(receivedPhase2status) : "No Phase";
            phase3Status = noOfPhasesFromDB == 3 ? getHealthStatus(receivedPhase3status) : "No Phase";

            String phase1VCStatus = getVCStatus(receivedStatus, receivedPhase1status[2], receivedPhase1status[1], 1);
            String phase2VCStatus = noOfPhasesFromDB != 1 ? getVCStatus(receivedStatus, receivedPhase2status[2], receivedPhase1status[1], 2) : "No Phase";
            String phase3VCStatus = noOfPhasesFromDB == 3 ? getVCStatus(receivedStatus, receivedPhase3status[2], receivedPhase1status[1], 3) : "No Phase";
            String overall_status = getOverAllStatus(noOfPhasesFromDB, phase1Status, phase2Status, phase3Status, phase1VCStatus, phase2VCStatus, phase3VCStatus);

            EnergyMeterStatus energyMeterData = new EnergyMeterStatus();
            energyMeterData.setJunction_id(junctionID);
            energyMeterData.setProgram_version_no(programVersionNoFromDB);
            energyMeterData.setVoltage1(map.get("voltage1") != null ? Integer.parseInt(map.get("voltage1").toString()) : 0);
            energyMeterData.setVoltage2(map.get("voltage2") != null ? Integer.parseInt(map.get("voltage2").toString()) : 0);
            energyMeterData.setVoltage3(map.get("voltage3") != null ? Integer.parseInt(map.get("voltage3").toString()) : 0);
            energyMeterData.setCurrent1(map.get("current1") != null ? Integer.parseInt(map.get("current1").toString()) : 0);
            energyMeterData.setCurrent2(map.get("current2") != null ? Integer.parseInt(map.get("current2").toString()) : 0);
            energyMeterData.setCurrent3(map.get("current3") != null ? Integer.parseInt(map.get("current3").toString()) : 0);
            energyMeterData.setActivePower1(map.get("active_power1") != null ? Integer.parseInt(map.get("active_power1").toString()) : 0);
            energyMeterData.setActivePower2(map.get("active_power2") != null ? Integer.parseInt(map.get("active_power2").toString()) : 0);
            energyMeterData.setActivePower3(map.get("active_power3") != null ? Integer.parseInt(map.get("active_power3").toString()) : 0);
            energyMeterData.setPower_factor(map.get("power_factor") != null ? Integer.parseInt(map.get("power_factor").toString()) : 0);
            energyMeterData.setConsumed_unit(map.get("consumed_unit") != null ? Integer.parseInt(map.get("consumed_unit").toString()) : 0);
            energyMeterData.setConnected_load(map.get("total_active_power") != null ? Integer.parseInt(map.get("total_active_power").toString()) : 0);
            energyMeterData.setNo_of_phases(noOfPhasesFromDB);
            energyMeterData.setType_of_premises_id(typeOfConnectionFromDB);
            energyMeterData.setContactorOnStatus(map.get("contacter_on_command") != null ? Integer.parseInt(map.get("contacter_on_command").toString()) : 0);
            energyMeterData.setJuncOffTimeHr(map.get("junc_on_time_hr") != null ? Integer.parseInt(map.get("junc_on_time_hr").toString()) : 0);
            energyMeterData.setJuncOffTimeMin(map.get("junc_on_time_min") != null ? Integer.parseInt(map.get("junc_on_time_min").toString()) : 0);
            energyMeterData.setJuncOnTimeHr(map.get("junc_off_time_hr") != null ? Integer.parseInt(map.get("junc_off_time_hr").toString()) : 0);
            energyMeterData.setJuncOnTimeMin(map.get("junc_off_time_min") != null ? Integer.parseInt(map.get("junc_off_time_min").toString()) : 0);
            energyMeterData.setPhase1_status(phase1Status);
            energyMeterData.setPhase2_status(phase2Status);
            energyMeterData.setPhase3_status(phase3Status);
            energyMeterData.setPhase1_vc_status(phase1VCStatus);
            energyMeterData.setPhase2_vc_status(phase2VCStatus);
            energyMeterData.setPhase3_vc_status(phase3VCStatus);
            energyMeterData.setOverall_status(overall_status);

            for (int i = 8; i > 0; i--) {
                if (i == 8) {
                    energyMeterData.setPhase1_fuseIn(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_fuseIn(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_fuseIn(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == 7) {
                    energyMeterData.setPhase1_fuseOut(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_fuseOut(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_fuseOut(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == 6) {
                    energyMeterData.setPhase1_contactor_switching(receivedPhase1status[i].equals("1") ? "On" : "Off");
                    energyMeterData.setPhase2_contactor_switching(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "On" : "Off" : "No Phase");
                    energyMeterData.setPhase3_contactor_switching(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "On" : "Off" : "No Phase");
                } else if (i == 5) {
                    energyMeterData.setPhase1_coil_status(receivedPhase1status[i].equals("1") ? "On" : "Off");
                    energyMeterData.setPhase2_coil_status(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "On" : "Off" : "No Phase");
                    energyMeterData.setPhase3_coil_status(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "On" : "Off" : "No Phase");
                } else if (i == 4) {
                    energyMeterData.setPhase1_mccbIn(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_mccbIn(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_mccbIn(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == 3) {
                    energyMeterData.setPhase1_mccbOut(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_mccbOut(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_mccbOut(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == 2) {
                    energyMeterData.setPhase1_current_status(receivedPhase1status[i].equals("0") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_current_status(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_current_status(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == 1) {
                    energyMeterData.setPhase1_voltage_status(receivedPhase1status[i].equals("0") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_voltage_status(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_voltage_status(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                }
            }

            energyMeterData.setJuncHr(map.get("junc_hr") != null ? Integer.parseInt(map.get("junc_hr").toString()) : 0);
            energyMeterData.setJuncMin(map.get("junc_min") != null ? Integer.parseInt(map.get("junc_min").toString()) : 0);
            energyMeterData.setJuncDate(map.get("junc_date") != null ? Integer.parseInt(map.get("junc_date").toString()) : 0);
            energyMeterData.setJuncMonth(map.get("junc_month") != null ? Integer.parseInt(map.get("junc_month").toString()) : 0);
            energyMeterData.setJuncYear(map.get("junc_year") != null ? Integer.parseInt(map.get("junc_year").toString()) : 0);

            status = insertReadings(energyMeterData);
        }
        return status;
    }

    public String junctionRefreshFunctionOne(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) {
        String responseVal = null;
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
        int project_no = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];

        String imeiNoStr = "";
        int imeiCount = firstStartDataPosition + 3;
        for(int i = 0; i < 15; i++){
            imeiCount = imeiCount + 1;
            int n = dataToProcess[imeiCount];            
            imeiNoStr = imeiNoStr + (char)n;
        }

        String meterNoinBytes = "";        
        for(int i = 0; i < 8; i++){
            imeiCount = imeiCount + 1;
            meterNoinBytes = meterNoinBytes + (char) dataToProcess[imeiCount];
        }        
        int firstLastDelimiter = dataToProcess[imeiCount + 2];
        int secLastDelimiter = dataToProcess[imeiCount + 3];

        junctionID = getJunctionId(imeiNoStr);
        program_version_no = getProgramVersionNo(junctionID);
        int fileNoDb = getFileNo(junctionID, program_version_no);
        if(fileNo != fileNoDb)
            updateFileNo(junctionID, fileNo, program_version_no);
        int registering_status =  updatePanelTransferredStatus(junctionID, program_version_no);
        int phase = getNoOfPhases(junctionID, program_version_no);
        int type_of_connection = getTypeOfConnection(junctionID, program_version_no);

        responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + registering_status + " " + phase + " " + type_of_connection + " " + firstLastDelimiter + " " + firstLastDelimiter;
        return responseVal;
    }

    public String junctionRefreshFunctionNine(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) {
        String responseVal = null;
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
        int project_no = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 10];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 11];
        
        int fileNoDb = getFileNo(junctionID, program_version_no);
        if(fileNo != fileNoDb)
            updateFileNo(junctionID, fileNo, program_version_no);
        Calendar cal = Calendar.getInstance();
        int juncHr = cal.get(Calendar.HOUR_OF_DAY);
        int juncMin = cal.get(Calendar.MINUTE);
        int juncDat = cal.get(Calendar.DATE);
        int juncMonth = cal.get(Calendar.MONTH) + 1;
        //int juncYear = cal.get(Calendar.YEAR);
        DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
        String formattedDate = df.format(Calendar.getInstance().getTime());
        int juncYear = Integer.parseInt(formattedDate);
        int appHr = cal.get(Calendar.HOUR_OF_DAY);
        int appMin = cal.get(Calendar.MINUTE);
        int appDat = cal.get(Calendar.DATE);
        int appMonth = cal.get(Calendar.MONTH) + 1;
        int appYear = juncYear;
        String currentTimeSynchronizationStatus = "Y";
        int timeSynchronizationStatus = insertSynchronizeTimeData(junctionID, program_version_no, juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear, currentTimeSynchronizationStatus);
        responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + juncHr + " " + juncMin + " " + juncDat + " " + juncMonth + " " + juncYear + " " + timeSynchronizationStatus + " " + firstLastDelimiter + " " + firstLastDelimiter;
        return responseVal;
    }

    public int getJunctionId(String imei_no){
        int junctionId = 0;
        String query = "SELECT junction_id FROM junction WHERE imei_no='"+imei_no+"'";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next())
                junctionId = rs.getInt("junction_id");
        }catch(Exception ex){
            System.out.println("ERROR: in getJunctionId() in EnergyMeterWebServiceModel : "+ ex);
        }
        return junctionId;
    }

    public String junctionRefreshFunction(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) {
        String responseVal = null;
        String[] receivedPhase1status = null;
        String[] receivedPhase2status = null;
        String[] receivedPhase3status = null;
        String[] receivedStatus = null;

        double divisor = 100;

        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
        int project_no = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];

        byte twoByteData[] = new byte[2];

        twoByteData[0] = dataToProcess[firstStartDataPosition + 4];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 5];
        long voltage1 = (new BigInteger(twoByteData).longValue() / 100);

        twoByteData[0] = dataToProcess[firstStartDataPosition + 6];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 7];
        long voltage2 = new BigInteger(twoByteData).longValue() / 100;

        twoByteData[0] = dataToProcess[firstStartDataPosition + 8];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 9];
        long voltage3 = new BigInteger(twoByteData).longValue() / 100;

        twoByteData[0] = dataToProcess[firstStartDataPosition + 10];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 11];
        long current1 = new BigInteger(twoByteData).longValue() / 100;

        twoByteData[0] = dataToProcess[firstStartDataPosition + 12];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 13];
        long current2 = new BigInteger(twoByteData).longValue();

        twoByteData[0] = dataToProcess[firstStartDataPosition + 14];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 15];
        long current3 = new BigInteger(twoByteData).longValue() / 100;

        twoByteData[0] = dataToProcess[firstStartDataPosition + 16];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 17];
        long activePower1 = new BigInteger(twoByteData).longValue() / 100;

        twoByteData[0] = dataToProcess[firstStartDataPosition + 18];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 19];
        long activePower2 = new BigInteger(twoByteData).longValue();

        twoByteData[0] = dataToProcess[firstStartDataPosition + 20];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 21];
        long activePower3 = new BigInteger(twoByteData).longValue() / 100;

        twoByteData[0] = dataToProcess[firstStartDataPosition + 22];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 23];
        long connectedLoad = new BigInteger(twoByteData).longValue() / 100; // totalActivePower

        twoByteData[0] = dataToProcess[firstStartDataPosition + 24];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 25];
        long powerFactor = new BigInteger(twoByteData).longValue() / 100;

        byte fourByteData[] = new byte[4];
        fourByteData[0] = dataToProcess[firstStartDataPosition + 26];
        fourByteData[1] = dataToProcess[firstStartDataPosition + 27];
        fourByteData[2] = dataToProcess[firstStartDataPosition + 28];
        fourByteData[3] = dataToProcess[firstStartDataPosition + 29];
        long consumedUnit = (long) (new BigInteger(fourByteData).longValue() / divisor);

        int activity = dataToProcess[firstStartDataPosition + 30];
        int activity1 = 1;
        int activity2 = 0;
        int activity3 = 0;
        int receivedActivity = activity;

        int unsignedPhase1 = dataToProcess[firstStartDataPosition + 31] & 0xFF;
        int unsignedPhase2 = dataToProcess[firstStartDataPosition + 32] & 0xFF;
        int unsignedPhase3 = dataToProcess[firstStartDataPosition + 33] & 0xFF;

        int unsignedStatusByte = dataToProcess[firstStartDataPosition + 34] & 0xFF;

        String phase1Status = Integer.toBinaryString(unsignedPhase1);
        String phase2Status = Integer.toBinaryString(unsignedPhase2);
        String phase3Status = Integer.toBinaryString(unsignedPhase3);

        String statusByte = Integer.toBinaryString(unsignedStatusByte);

        int contactorOnStatus = dataToProcess[firstStartDataPosition + 35];

        int juncOnTimeHr = dataToProcess[firstStartDataPosition + 36];
        int juncOnTimeMin = dataToProcess[firstStartDataPosition + 37];
        int juncOffTimeHr = dataToProcess[firstStartDataPosition + 38];
        int juncOffTimeMin = dataToProcess[firstStartDataPosition + 39];

        int juncHr = dataToProcess[firstStartDataPosition + 40];
        int juncMin = dataToProcess[firstStartDataPosition + 41];
        int juncDat = dataToProcess[firstStartDataPosition + 42];
        int juncMonth = dataToProcess[firstStartDataPosition + 43];
        int juncYear = dataToProcess[firstStartDataPosition + 44];
        System.out.println("juncHr="+juncHr+" juncMin="+juncMin+" juncDat="+juncDat+" juncMonth="+juncMonth+" juncYear"+juncYear);

        int crc = dataToProcess[firstStartDataPosition + 45];
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 46];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 46];

        int appHr = 0, appMin = 0, appDat = 0, appMonth = 0, appYear = 0;
        Calendar cald = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = sd.format(cald.getTime());

        String appDate = dateTime.split(" ")[0];
        String appTime = dateTime.split(" ")[1];

        String time[] = appTime.split(":");
        String dte[] = appDate.split("-");

        appHr = Integer.parseInt(time[0]);
        appMin = Integer.parseInt(time[1]);

        appDat = Integer.parseInt(dte[2]);
        appMonth = Integer.parseInt(dte[1]);
        appYear = Integer.parseInt(dte[0].substring(2));

//        /*---------------------------- code started to manage one minute difference in junction time and in application time  ------------------------------------------*/
//
//        int monthNo = (juncMonth - appMonth) == Math.abs(1) ? juncMonth > appMonth ? appMonth : juncMonth : 0;
//
//        if (((juncYear - appYear) == Math.abs(1)) && ((juncMonth - appMonth) == Math.abs(11)) && ((juncDat - appDat) == Math.abs(30)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of last month of a year
//            juncHr = appHr;
//            juncMin = appMin;
//            juncDat = appDat;
//            juncMonth = appMonth;
//            juncYear = appYear;
//        } else if (((juncMonth - appMonth) == Math.abs(1)) && ((juncDat - appDat) == Math.abs(getNoOfDays(monthNo, appYear) - 1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of a month
//            juncHr = appHr;
//            juncMin = appMin;
//            juncDat = appDat;
//            juncMonth = appMonth;
//        } else if (((juncDat - appDat) == Math.abs(1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of a day
//            juncHr = appHr;
//            juncMin = appMin;
//            juncDat = appDat;
//        } else if (((juncHr - appHr) == Math.abs(1)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of an hour
//            juncHr = appHr;
//            juncMin = appMin;
//        } else if ((juncMin - appMin) == Math.abs(1)) { //a minute
//            juncMin = appMin;
//        }
//        /*---------------------------- code endeded to manage one minute difference in junction time and in application time  ------------------------------------------*/

        String currentTimeSynchronizationStatus = getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);

        int juncOnOffTimeSynchronizationStatus = getjuncOnOffTimeSynchronizationStatus(junctionID, program_version_no, juncOnTimeHr, juncOnTimeMin, juncOffTimeHr, juncOffTimeMin);

        int fileNoFromDB = getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB =getProgramVersionNo(junctionID);
        int noOfPhasesFromDB = getNoOfPhases(junctionID, programVersionNoFromDB);
        int typeOfConnectionFromDB = getTypeOfConnection(junctionID, programVersionNoFromDB);

        int status = 0;

        String receivedData = firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + project_no + " "
                + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + voltage1 + " " + voltage2 + " " + voltage3 + " " + current1 + " "
                + current2 + " " + current3 + " " + activePower1 + " " + activePower2 + " " + activePower3 + " " + connectedLoad + " " + powerFactor + " "
                + consumedUnit + " " + activity + " " + unsignedPhase1 + " " + unsignedPhase2 + " " + unsignedPhase3 + " " + unsignedStatusByte + " " + contactorOnStatus + " "
                + juncOnTimeHr + " " + juncOnTimeMin + " " + juncOffTimeHr + " " + juncOffTimeMin + " "
                + juncHr + " " + juncMin + " " + juncDat + " " + juncMonth + " " + juncYear + " "
                + crc + " " + firstLastDelimiter + " " + secLastDelimiter;
        System.out.println("refresh  request from modem--- " + receivedData);

        System.out.println("Phase1 Status: " + phase1Status);
        System.out.println("Phase2 Status: " + phase2Status);
        System.out.println("Phase3 Status: " + phase3Status);
        System.out.println("Status Value: " + statusByte);

        try{

        switch (phase1Status.length()) {
            case 1:
                phase1Status = "0000000" + phase1Status;
                break;
            case 2:
                phase1Status = "000000" + phase1Status;
                break;
            case 3:
                phase1Status = "00000" + phase1Status;
                break;
            case 4:
                phase1Status = "0000" + phase1Status;
                break;
            case 5:
                phase1Status = "000" + phase1Status;
                break;
            case 6:
                phase1Status = "00" + phase1Status;
                break;
            case 7:
                phase1Status = "0" + phase1Status;
                break;
        }
        switch (phase2Status.length()) {
            case 1:
                phase2Status = "0000000" + phase2Status;
                break;
            case 2:
                phase2Status = "000000" + phase2Status;
                break;
            case 3:
                phase2Status = "00000" + phase2Status;
                break;
            case 4:
                phase2Status = "0000" + phase2Status;
                break;
            case 5:
                phase2Status = "000" + phase2Status;
                break;
            case 6:
                phase2Status = "00" + phase2Status;
                break;
            case 7:
                phase2Status = "0" + phase2Status;
                break;
        }
        switch (phase3Status.length()) {
            case 1:
                phase3Status = "0000000" + phase3Status;
                break;
            case 2:
                phase3Status = "000000" + phase3Status;
                break;
            case 3:
                phase3Status = "00000" + phase3Status;
                break;
            case 4:
                phase3Status = "0000" + phase3Status;
                break;
            case 5:
                phase3Status = "000" + phase3Status;
                break;
            case 6:
                phase3Status = "00" + phase3Status;
                break;
            case 7:
                phase3Status = "0" + phase3Status;
                break;
        }
        switch (statusByte.length()) {
            case 1:
                statusByte = "0000000" + statusByte;
                break;
            case 2:
                statusByte = "000000" + statusByte;
                break;
            case 3:
                statusByte = "00000" + statusByte;
                break;
            case 4:
                statusByte = "0000" + statusByte;
                break;
            case 5:
                statusByte = "000" + statusByte;
                break;
            case 6:
                statusByte = "00" + statusByte;
                break;
            case 7:
                statusByte = "0" + statusByte;
                break;
        }

        if(!checkJunctionPhaseHealth(junctionID, phase1Status, phase2Status, phase3Status, statusByte, contactorOnStatus))
            insertJunctionPhaseHealth(junctionID, phase1Status, phase2Status, phase3Status, statusByte, contactorOnStatus);

        receivedPhase1status = phase1Status.split("");
        receivedPhase2status = phase2Status.split("");
        receivedPhase3status = phase3Status.split("");
        receivedStatus = statusByte.split("");

        phase1Status = getHealthStatus(receivedPhase1status);
        phase2Status = noOfPhasesFromDB != 1 ? getHealthStatus(receivedPhase2status) : "No Phase";
        phase3Status = noOfPhasesFromDB == 3 ? getHealthStatus(receivedPhase3status) : "No Phase";

        String phase1VCStatus = getVCStatus(receivedStatus, receivedPhase1status[2], receivedPhase1status[1], 1);
        String phase2VCStatus = noOfPhasesFromDB != 1 ? getVCStatus(receivedStatus, receivedPhase2status[2], receivedPhase1status[1], 2) : "No Phase";
        String phase3VCStatus = noOfPhasesFromDB == 3 ? getVCStatus(receivedStatus, receivedPhase3status[2], receivedPhase1status[1], 3) : "No Phase";
        String overall_status = getOverAllStatus(noOfPhasesFromDB, phase1Status, phase2Status, phase3Status, phase1VCStatus, phase2VCStatus, phase3VCStatus);

        if (!testRequestFromJunction) {

            EnergyMeterStatus energyMeterData = new EnergyMeterStatus();
            energyMeterData.setJunction_id(junctionID);
            energyMeterData.setProgram_version_no(programVersionNoFromDB);
            energyMeterData.setVoltage1(voltage1);
            energyMeterData.setVoltage2(voltage2);
            energyMeterData.setVoltage3(voltage3);
            energyMeterData.setCurrent1(current1);
            energyMeterData.setCurrent2(current2);
            energyMeterData.setCurrent3(current3);
            energyMeterData.setActivePower1(activePower1);
            energyMeterData.setActivePower2(activePower2);
            energyMeterData.setActivePower3(activePower3);
            energyMeterData.setPower_factor(powerFactor);
            energyMeterData.setConsumed_unit(consumedUnit);
            energyMeterData.setConnected_load(connectedLoad);
            energyMeterData.setNo_of_phases(noOfPhasesFromDB);
            energyMeterData.setType_of_premises_id(typeOfConnectionFromDB);
            energyMeterData.setContactorOnStatus(contactorOnStatus);
            energyMeterData.setJuncOffTimeHr(juncOffTimeHr);
            energyMeterData.setJuncOffTimeMin(juncOffTimeMin);
            energyMeterData.setJuncOnTimeHr(juncOnTimeHr);
            energyMeterData.setJuncOnTimeMin(juncOnTimeMin);
            energyMeterData.setPhase1_status(phase1Status);
            energyMeterData.setPhase2_status(phase2Status);
            energyMeterData.setPhase3_status(phase3Status);
            energyMeterData.setPhase1_vc_status(phase1VCStatus);
            energyMeterData.setPhase2_vc_status(phase2VCStatus);
            energyMeterData.setPhase3_vc_status(phase3VCStatus);
            energyMeterData.setOverall_status(overall_status);
            energyMeterData.setPhase1_status_id(getHealthStatusID(phase1Status));
            energyMeterData.setPhase2_status_id(getHealthStatusID(phase2Status));
            energyMeterData.setPhase3_status_id(getHealthStatusID(phase3Status));
            energyMeterData.setPhase1_vc_status_id(getHealthStatusID(phase1VCStatus));
            energyMeterData.setPhase2_vc_status_id(getHealthStatusID(phase2VCStatus));
            energyMeterData.setPhase3_vc_status_id(getHealthStatusID(phase3VCStatus));
            energyMeterData.setOverall_status_id(getHealthStatusID(overall_status));

            int phaseStatusLength = receivedPhase1status.length;

            for (int i = phaseStatusLength; i > 0; i--) {
                if (i == (phaseStatusLength - 1)) {
                    energyMeterData.setPhase1_fuseIn(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_fuseIn(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_fuseIn(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == (phaseStatusLength - 2)) {
                    energyMeterData.setPhase1_fuseOut(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_fuseOut(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_fuseOut(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == (phaseStatusLength - 3)) {
                    energyMeterData.setPhase1_contactor_switching(receivedPhase1status[i].equals("1") ? "On" : "Off");
                    energyMeterData.setPhase2_contactor_switching(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "On" : "Off" : "No Phase");
                    energyMeterData.setPhase3_contactor_switching(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "On" : "Off" : "No Phase");
                } else if (i == (phaseStatusLength - 4)) {
                    energyMeterData.setPhase1_coil_status(receivedPhase1status[i].equals("1") ? "On" : "Off");
                    energyMeterData.setPhase2_coil_status(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "On" : "Off" : "No Phase");
                    energyMeterData.setPhase3_coil_status(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "On" : "Off" : "No Phase");
                } else if (i == (phaseStatusLength - 5)) {
                    energyMeterData.setPhase1_mccbIn(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_mccbIn(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_mccbIn(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == (phaseStatusLength - 6)) {
                    energyMeterData.setPhase1_mccbOut(receivedPhase1status[i].equals("1") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_mccbOut(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_mccbOut(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("1") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == (phaseStatusLength - 7)) {
                    energyMeterData.setPhase1_current_status(receivedPhase1status[i].equals("0") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_current_status(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_current_status(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                } else if (i == (phaseStatusLength - 8)) {
                    energyMeterData.setPhase1_voltage_status(receivedPhase1status[i].equals("0") ? "Ok" : "Not Ok");
                    energyMeterData.setPhase2_voltage_status(noOfPhasesFromDB != 1 ? receivedPhase2status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                    energyMeterData.setPhase3_voltage_status(noOfPhasesFromDB == 3 ? receivedPhase3status[i].equals("0") ? "Ok" : "Not Ok" : "No Phase");
                }
            }

            energyMeterData.setJuncHr(juncHr);
            energyMeterData.setJuncMin(juncMin);
            energyMeterData.setJuncDate(juncDat);
            energyMeterData.setJuncMonth(juncMonth);
            energyMeterData.setJuncYear(juncYear);

            try {
               
                    status = insertReadings(energyMeterData);
                
            } catch (Exception e) {
                System.out.println("Error insertReadings - clientResponder - " + e);
            }
        }

          } catch (Exception e) {
                System.out.println("Error - clientResponder in EnergyMeterWebServiceModel - " + e);
            }

        activity = program_version_no == programVersionNoFromDB
                ? fileNo == fileNoFromDB
                ? currentTimeSynchronizationStatus.equals("Y")
                ? testRequestFromJunction
                ? status : juncOnOffTimeSynchronizationStatus == 1 ? 1 : 6
                : 5
                : 3
                : 2;
        activity1 = 1;
        if(context != null){
        String command = context.getAttribute(junctionID+"") == null?"":context.getAttribute(junctionID+"").toString();
        if(command != null && !command.isEmpty()){
            activity2 = Integer.parseInt(command.split(" ")[0]);
            activity3 = Integer.parseInt(command.split(" ")[1]);
        }
        }



        System.out.println("activity bytes: " + activity + " " + activity1 + " " + activity2 + " " + activity3);
        responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + activity + " " + activity1 + " " + activity2 + " " + activity3 + " " + firstLastDelimiter + " " + firstLastDelimiter;

        return responseVal;
    }

    public int insertReadings(EnergyMeterStatus readings) {
        int rowAffected = 0;
        try {

            String reading_date="";
            String reading_time="";
            reading_date= readings.getJuncYear()+"-"+readings.getJuncMonth()+"-"+readings.getJuncDate();
            reading_time=readings.getJuncHr()+":"+readings.getJuncMin()+":"+"00";

//            String strdate2 = "02-04-2013 11:35:42";
            String strdate2 = reading_date+" "+reading_time;

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //Date date = new Date();
            Date date =  dateFormat.parse(strdate2);
            String currentDate = dateFormat.format(date);
            //Calendar cal = Calendar.getInstance();
            String currentTime = dateFormat.format(date);
            java.util.Date parsedUtilDate = null;
            java.util.Date parsedUtilTime = null;
            try {
                parsedUtilDate = dateFormat.parse(currentDate);
                System.out.println(parsedUtilDate);
                parsedUtilTime = dateFormat.parse(currentTime);
            } catch (ParseException ex) {
                System.out.println("EnergyMeateWebServiceModel insertRecord dateFormat ParseException: " + ex);
            }
            java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
            java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());
            java.sql.Time sqltTime1 = new java.sql.Time(readings.getJuncOnTimeHr());

            String query = "INSERT INTO meter_readings( junction_id, program_version_no, voltage1, voltage2, voltage3, current1, current2, current3, "
                    + " power_factor, voltage_unit_id, current_unit_id, reading_date, reading_time, consumed_unit, connected_load, on_time_hr, on_time_min, off_time_hr, off_time_min ) "
                    + " VALUES(?, ?, ?, ?, ?, ? ,? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, readings.getJunction_id());
            pstmt.setInt(2, readings.getProgram_version_no());
            pstmt.setDouble(3, readings.getVoltage1());
            pstmt.setDouble(4, readings.getVoltage2());
            pstmt.setDouble(5, readings.getVoltage3());
            pstmt.setDouble(6, readings.getCurrent1());
            pstmt.setDouble(7, readings.getCurrent2());
            pstmt.setDouble(8, readings.getCurrent3());
            pstmt.setDouble(9, readings.getPower_factor());
            pstmt.setInt(10, 1);
            pstmt.setInt(11, 1);
            pstmt.setDate(12, sqltDate);
            pstmt.setTime(13, sqltTime);
            pstmt.setDouble(14, readings.getConsumed_unit());
            pstmt.setDouble(15, readings.getConnected_load());
            pstmt.setInt(16, readings.getJuncOnTimeHr());
            pstmt.setInt(17, readings.getJuncOnTimeMin());
            pstmt.setInt(18, readings.getJuncOffTimeHr());
            pstmt.setInt(19, readings.getJuncOffTimeMin());
            rowAffected = pstmt.executeUpdate();            
            if (rowAffected > 0 && !getLastFinalHealthStatus(readings.getJunction_id(), readings.getProgram_version_no(), readings.getOverall_status_id(), readings.getPhase1_status_id(), readings.getPhase2_status_id(), readings.getPhase3_status_id(), readings.getPhase1_vc_status_id(), readings.getPhase2_vc_status_id(), readings.getPhase3_vc_status_id())) {
                rowAffected = 0;
                rowAffected = insertHealthStatus(readings.getJunction_id(), readings.getProgram_version_no(), readings.getOverall_status_id(), readings.getPhase1_status_id(), readings.getPhase2_status_id(), readings.getPhase3_status_id(), readings.getPhase1_vc_status_id(), readings.getPhase2_vc_status_id(), readings.getPhase3_vc_status_id());
            }

        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel Error: insertRecord--- " + e);
        }

        return rowAffected;
    }

    private int getHealthStatusID(String status){
        int status_id = 0;
        String query = "SELECT health_status_id FROM health_status WHERE health_status = '"+ status +"'";
        try{
            ResultSet rs  = connection.prepareStatement(query).executeQuery();
            if(rs.next())
                status_id = rs.getInt(1);
        }catch(Exception ex){
        }
        return status_id;
    }

    private boolean checkJunctionPhaseHealth(int junction_id, String phase1, String phase2, String phase3, String contactor_status, int command) {
        int result = 0;
        String query = " SELECT IF(junction_id = " + junction_id + " AND  phase1_bits = " + phase1 + " AND phase2_bits = " + phase2
                + " AND  phase3_bits = " + phase3 + " AND  contactor_status_bits = " + contactor_status + " AND  command = " + command
                + " , 1 , 0 ) AS matchedLastStatus "
                + " FROM junction_phase_health WHERE junction_id =" + junction_id + " ORDER BY junction_phase_health_id DESC LIMIT 1 ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                result = rset.getInt("matchedLastStatus");
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel: checkJunctionPhaseHealth() Error" + e);
        }
        return result == 1 ? true : false;
    }

    private int insertJunctionPhaseHealth(int junction_id, String phase1, String phase2, String phase3, String contactor_status, int command){
        int rowAffected = 0;
        String query = "INSERT INTO junction_phase_health (junction_id, phase1_bits, phase2_bits, phase3_bits, contactor_status_bits, command) VALUES(?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement pstmt  = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            pstmt.setString(2, phase1);
            pstmt.setString(3, phase2);
            pstmt.setString(4, phase3);
            pstmt.setString(5, contactor_status);
            pstmt.setInt(6, command);
            rowAffected = pstmt.executeUpdate();
        }catch(Exception ex){
            System.out.println("EnergyMeateWebServiceModel: insertJunctionPhaseHealth() Error" + ex);
        }
        return rowAffected;
    }

    private boolean getLastFinalHealthStatus(int junction_id, int program_version_no, int overAllStatusId, int phase1StatusId, int phase2StatusId, int phase3StatusId, int phase1VCStatusId, int phase2VCStatusId, int phase3VCStatusId) {
        int result = 0;
        String query = " SELECT IF(health_status_id = " + overAllStatusId + " AND  phase1_status_id = " + phase1StatusId + " AND phase2_status_id = " + phase2StatusId
                + " AND  phase3_status_id = " + phase3StatusId + " AND  phase1_vc_status_id = " + phase1VCStatusId + " AND  phase2_vc_status_id = " + phase2VCStatusId
                + " AND phase3_vc_status_id = " + phase3VCStatusId + " , 1 , 0 ) AS matchedLastStatus "
                + " FROM health_status_map WHERE junction_id =" + junction_id + " AND program_version_no= " + program_version_no + " ORDER BY health_status_map_id DESC LIMIT 1 ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                result = rset.getInt("matchedLastStatus");
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel: getLastFinalHealth() Error" + e);
        }
        return result == 1 ? true : false;
    }

    public int insertHealthStatus(int junction_id, int program_version_no, int overAllStatusId, int phase1StatusId, int phase2StatusId, int phase3StatusId, int phase1VCStatusId, int phase2VCStatusId, int phase3VCStatusId) {
        int rowsReturned = 0;

        String query = "INSERT INTO health_status_map(health_status_id, junction_id, program_version_no, reading_date, reading_time, phase1_status_id, phase2_status_id,"
                + " phase3_status_id, phase1_vc_status_id, phase2_vc_status_id, phase3_vc_status_id ) "
                + " VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ) ";

        PreparedStatement pstmt = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
//        System.out.println(junctionQuery);
        String currentDate = dateFormat.format(date);
        Calendar cal = Calendar.getInstance();
        String currentTime = dateFormat.format(cal.getTime());
        java.util.Date parsedUtilDate = null;
        java.util.Date parsedUtilTime = null;
        try {
            parsedUtilDate = dateFormat.parse(currentDate);
            System.out.println(parsedUtilDate);
            parsedUtilTime = dateFormat.parse(currentTime);
        } catch (ParseException ex) {
            System.out.println("EnergyMeateWebServiceModel date parsing error: " + ex);
        }
        java.sql.Date sqltDate = new java.sql.Date(parsedUtilDate.getTime());
        java.sql.Time sqltTime = new java.sql.Time(parsedUtilTime.getTime());

        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, overAllStatusId);
            pstmt.setInt(2, junction_id);
            pstmt.setInt(3, program_version_no);
            pstmt.setDate(4, sqltDate);
            pstmt.setTime(5, sqltTime);
            pstmt.setInt(6, phase1StatusId);
            pstmt.setInt(7, phase2StatusId);
            pstmt.setInt(8, phase3StatusId);
            pstmt.setInt(9, phase1VCStatusId);
            pstmt.setInt(10, phase2VCStatusId);
            pstmt.setInt(11, phase3VCStatusId);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel insertHealthStatus() Error: " + e);
        }
        return rowsReturned;
    }

    private Map getMapDataFromString(String dataToProcess) {
        Map<String, String> map = new HashMap<String, String>();
        if (dataToProcess != null && !dataToProcess.isEmpty()) {
            String[] json_data = dataToProcess.split(",");
            String[] json_result = new String[2];
            for (int i = 0; i < json_data.length; i++) {
                json_result = json_data[i].split("=");
                try {
                    if (json_result.length == 2) {
                        //  json_result[1] = null;
                        System.out.println("received Data :" + json_result[0] + " " + json_result[1]);
                        map.put(json_result[0].trim(), json_result[1].trim());
                    }
                } catch (Exception e) {
                    System.out.println("EnergyMeateWebServiceModel: getMapDataFromString Exception occur: " + e);
                }
            }
        }
        return map;
    }

    public int getProgramVersionNo(int junctionID) {
        int program_Version_no = 0;
        String queryJunctionID = " SELECT program_version_no FROM junction WHERE junction_id = ? AND active='Y' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(queryJunctionID);
            pstmt.setInt(1, junctionID);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                program_Version_no = rset.getInt("program_version_no");
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel: getProgramVersionNo() Error" + e);
        }
        return program_Version_no;
    }

    public int getNoOfPhases(int junction_id, int program_version_no) {
        int noOfPhases = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT ph FROM switching_point_detail AS spd, junction AS j  "
                    + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no "
                    + " AND spd.active = 'Y' AND junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfPhases = rset.getInt(1);
            System.out.println(noOfPhases);
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel getNoOfPhases() Error: " + e);
        }
        return noOfPhases;
    }

    public int getTypeOfConnection(int junction_id, int program_version_no) {
        int type_of_premises_id = 0;

        String query = "SELECT type_of_premises_id FROM switching_point_detail AS spd, junction AS j  "
                + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no "
                + " AND spd.active = 'Y' AND j.junction_id= ? AND j.program_version_no= ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                type_of_premises_id = rset.getInt("type_of_premises_id");
                System.out.println(type_of_premises_id);
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel getNoOfPlans() Error: " + e);
        }
        return type_of_premises_id;
    }

    public String getHealthStatus(String[] receivedPhaseStatus) {
        String status = "";
        int phaseStatusLength = receivedPhaseStatus.length;
        int position = phaseStatusLength - 1;
        while (position > (phaseStatusLength - 7) && status.isEmpty()) {
            status = receivedPhaseStatus[position].equals("0") ? manipulateErrorStatus(receivedPhaseStatus, position) == 1 ? "Emergency Error " + (phaseStatusLength - position)
                    : position == (phaseStatusLength - 1) ? "Fuse In Fault" : position == (phaseStatusLength - 2) ? "Fuse Out Fault" : position == (phaseStatusLength - 3) ? "Contactor Off"
                    : position == (phaseStatusLength - 4) ? "Coil Fault" : position == (phaseStatusLength - 5) ? "MCCB In Fault" : position == (phaseStatusLength - 6) ? "MCCB Out Fault" : "No Fault"
                    : "";
            position--;
        }
        //status = "";

        return status.isEmpty() ? "No Fault" : status;
    }

    public PhaseBean getHealthStatus_1(String[] receivedPhaseStatus, PhaseBean phaseBean) {
        String status = "";
        int phaseStatusLength = receivedPhaseStatus.length;
        int position = phaseStatusLength - 1;

        String fuse_in = receivedPhaseStatus[position];
        String fuse_out = receivedPhaseStatus[position - 1];
        String contactor_status = receivedPhaseStatus[position - 2];
        String contactor_command = receivedPhaseStatus[position - 3];
        String mccb_in = receivedPhaseStatus[position - 4];
        String mccb_out = receivedPhaseStatus[position - 5];

        if(fuse_in.equals("0"))
            phaseBean.setFuse_in("Fault");
        else
            phaseBean.setFuse_in("OK");
        if(fuse_out.equals("0"))
            phaseBean.setFuse_out("Fault");
        else
            phaseBean.setFuse_out("OK");

        if(contactor_status.equals("0"))
            phaseBean.setContactor_status("OFF");
        else
            phaseBean.setContactor_status("ON");
        if(contactor_command.equals("0"))
            phaseBean.setContactor_command("OFF");
        else
            phaseBean.setContactor_command("ON");

        if(mccb_in.equals("0"))
            phaseBean.setMccb_in("Fault");
        else
            phaseBean.setMccb_in("OK");

        if(mccb_out.equals("0"))
            phaseBean.setMccb_out("Fault");
        else
            phaseBean.setMccb_out("OK");

        if(((fuse_out.equals("0") && contactor_status.equals("0")) || (fuse_out.equals("0") && contactor_status.equals("1")) || (fuse_out.equals("1") && contactor_status.equals("0"))) && mccb_in.equals("0"))
            phaseBean.setMccb_in("OK");
        if(((fuse_out.equals("0") && contactor_status.equals("0")) || (fuse_out.equals("0") && contactor_status.equals("1")) || (fuse_out.equals("1") && contactor_status.equals("0"))) && mccb_out.equals("0"))
            phaseBean.setMccb_out("OK");

        if(((fuse_out.equals("0") && contactor_status.equals("0")) || (fuse_out.equals("0") && contactor_status.equals("1")) || (fuse_out.equals("1") && contactor_status.equals("0"))) && mccb_in.equals("1"))
            phaseBean.setMccb_in("Fault");
        if(((fuse_out.equals("0") && contactor_status.equals("0")) || (fuse_out.equals("0") && contactor_status.equals("1")) || (fuse_out.equals("1") && contactor_status.equals("0"))) && mccb_out.equals("1"))
            phaseBean.setMccb_out("Fault");

//        if(manipulateErrorStatus(receivedPhaseStatus, position) == 1)
//                    status = "Emergency Error " + (position - 1);
//                else if(position == (phaseStatusLength - 1))
//                    status = "Fuse In Fault";
//                else if(position == (phaseStatusLength - 2))
//                    status = "Fuse Out Fault";
//                else if(position == (phaseStatusLength - 3))
//                    status = "Contactor Off";
//                else if(position == (phaseStatusLength - 4))
//                    status = "Coil Fault";
//                else if(position == (phaseStatusLength - 5))
//                    status = "MCCB In Fault";
//                else if(position == (phaseStatusLength - 6))
//                    status = "MCCB Out Fault";
//                else
//                    status = "No Fault";
//            else
//                status = "";
        
        return phaseBean;
    }

    private int manipulateErrorStatus(String[] receivedPhaseStatus, int position) {
        int errorState = 0;
        for (int j = position - 1; j > 1 && errorState == 0; j--) {
            if (receivedPhaseStatus[j].equals("1")) {
                errorState = 1;
            }
        }
        return errorState;
    }

    public int getHealthStatusId(String status) {
        int healthStatusId = 0;
        String query = "SELECT health_status_id FROM health_status WHERE health_status = '" + status.trim() + "'";
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                healthStatusId = rset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel getHealthStatusId() Error: " + e);
        }
        return healthStatusId;
    }

    private String getVCStatus(String[] receivedStatus, String bit7, String bit8, int phaseNo) {
        String status = "";

        int start = phaseNo == 1 ? 7 : phaseNo == 2 ? 5 : 3;

        if (bit7.equals("1") && bit8.equals("1")) {
            if (receivedStatus[start].equals("0") && receivedStatus[start - 1].equals("0")) {
                status = "Under Current and Under Voltage";
            } else if (receivedStatus[start].equals("0") && receivedStatus[start - 1].equals("1")) {
                status = "Under Current and Over Voltage";
            } else if (receivedStatus[start].equals("1") && receivedStatus[start - 1].equals("0")) {
                status = "Over Current and Under Voltage";
            } else if (receivedStatus[start].equals("1") && receivedStatus[start - 1].equals("1")) {
                status = "Over Current and Over Voltage";
            }
        } else if (bit7.equals("1") && bit8.equals("0")) {
            if (receivedStatus[start].equals("0")) {
                status = "Under Current";
            } else {
                status = "Over Current";
            }
        } else if (bit7.equals("0") && bit8.equals("1")) {
            if (receivedStatus[start - 1].equals("0")) {
                status = "Under Voltage";
            } else {
                status = "Over Voltage";
            }
        } else {
            status = "Healthy";
        }
        return status;
    }

    public String getOverAllStatus(int noOfPhasesFromDB, String phase1Status, String phase2Status, String phase3Status, String phase1VCStatus, String phase2VCStatus, String phase3VCStatus) {
        String status = "Not Healthy";
        if (noOfPhasesFromDB == 3) {
            status = phase1Status.equals(phase2Status) && phase2Status.equals(phase3Status) && phase3Status.equals("No Fault")
                    && phase1VCStatus.equals(phase2VCStatus) && phase2VCStatus.equals(phase3VCStatus) && phase3VCStatus.equals("Healthy") ? "Healthy" : "Not Healthy";
        } else if (noOfPhasesFromDB == 2) {
            status = phase1Status.equals(phase2Status) && phase2Status.equals("No Fault")
                    && phase1VCStatus.equals(phase2VCStatus) && phase2VCStatus.equals("Healthy") ? "Healthy" : "Not Healthy";
        } else {
            status = phase1Status.equals("No Fault")
                    && phase1VCStatus.equals("Healthy") ? "Healthy" : "Not Healthy";
        }
        return status;
    }

    public byte[] sendResponse(String response)
            throws Exception {
      byte[] finalBytes = null;
        if (response != null && !response.isEmpty()) {
           
            String[] b1 = response.split(" ");
            // byte[] bytes = new byte[b1.length];
           finalBytes = new byte[b1.length + 3];
            int[] k = new int[b1.length];
            for (int j = 0; j < b1.length; j++) {
                k[j] = Integer.parseInt(b1[j]);
                //   bytes[j] = ((byte) k[j]);
            }
            finalBytes = getFinalBytes(k);
            
        }
        System.out.println("sent response: "+response);
        return finalBytes;
    }


    public byte[] getFinalBytes(int[] k) {
        int length = k.length;

        int dSize = 0;
        int dSum = 0;

        byte dataSize[] = new byte[2];
        byte[] finalBytes = new byte[length + 3];
        try {

            for (int i = 4; i < (length - 2); i++) {
                dSum += k[i];
                dSize++;
            }

            dataSize[0] = (byte) ((dSize >> 8) & 0xFF); //>> 8 discards the lowest 8 bits by moving all bits 8 places to the right.
            dataSize[1] = (byte) (dSize & 0xFF); //& 0xFF masks the lowest eight bits.

            byte lowByte = (byte) (dSum & 0xFF); //get lowest 8-bit
            byte crcByte = (byte) (0xFF - lowByte); // subtract lowest 8-bit from 255

            for (int i = 0, j = 0; j < finalBytes.length; j++) {
                if (j == 4) {
                    finalBytes[j] = dataSize[0];
                } else if (j == 5) {
                    finalBytes[j] = dataSize[1];
                } else if (j == finalBytes.length - 3) {
                    finalBytes[j] = crcByte;
                } else {
                    finalBytes[j] = ((byte) k[i]);
                    i++;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in EnergyMeateWebServiceModel getFinalBytes: " + e);
        }
        return finalBytes;
    }

    public int getNoOfDays(int month_no, int year) {
        int noOfDays = 0;
        try {
            if (month_no == 2) { // february
                if (year % 4 == 0) {
                    noOfDays = 29; //leap year
                } else {
                    noOfDays = 28;
                }
            } else if (month_no == 4 || month_no == 6 || month_no == 9 || month_no == 11) {
                noOfDays = 30;
            } else {
                noOfDays = 31;
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel getNoOfDays error: " + e);
        }
        return noOfDays;
    }


    public String getCurrentTimeSynchronizationStatus(int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        String currentTimeSynchronizationStatus = "N";
        if (juncHr == appHr && juncMin == appMin && juncDat == appDat && juncMonth == appMonth && juncYear == appYear) {
            currentTimeSynchronizationStatus = "Y";
        } else {
            currentTimeSynchronizationStatus = "N";
        }
        return currentTimeSynchronizationStatus;
    }

    public String getLastTimeSynchronizationStatus(int junctionID, int program_version_no) {
        String status = "No Record";
        int final_rev_no = getLastTimeSynchronizationId(junctionID, program_version_no);
        if (final_rev_no != -1) {
            String query = " SELECT synchronization_status FROM time_synchronization_detail "
                    + " WHERE time_synchronization_detail_id = ? "
                    + " AND junction_id = ? AND program_version_no = ? ";
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, final_rev_no);
                pstmt.setInt(2, junctionID);
                pstmt.setInt(3, program_version_no);
                ResultSet rset = pstmt.executeQuery();
                while (rset.next()) {
                    status = rset.getString("synchronization_status");
                }
//            connection.close();
            } catch (Exception e) {
                System.out.println("EnergyMeateWebServiceModel: getLastTimeSynchronizationStatus() Error" + e);
            }
        }
        return status;
    }

    public int updateSynchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear) {
        int rowsReturned = 0;
        String insertQuery = " UPDATE time_synchronization_detail SET  junction_hr = ?, application_hr = ?, junction_min = ?, application_min = ?, "
                + " junction_date = ?, application_date = ?, junction_month = ?, application_month = ?, junction_year = ?, application_year = ? WHERE "
                + " junction_id = ? AND time_synchronization_detail_id = ? AND program_version_no = ? ";
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, juncHr);
            pstmt.setInt(2, appHr);
            pstmt.setInt(3, juncMin);
            pstmt.setInt(4, appMin);
            pstmt.setInt(5, juncDat);
            pstmt.setInt(6, appDat);
            pstmt.setInt(7, juncMonth);
            pstmt.setInt(8, appMonth);
            pstmt.setInt(9, juncYear);
            pstmt.setInt(10, appYear);
            pstmt.setInt(11, junctionID);
            pstmt.setInt(12, getLastTimeSynchronizationId(junctionID, program_version_no));
            pstmt.setInt(13, program_version_no);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel updateSynchronizeTimeData() Error: " + e);
        }
        return rowsReturned;
    }

    public int insertSynchronizeTimeData(int junctionID, int program_version_no, int juncHr, int juncMin, int juncDat, int juncMonth, int juncYear, int appHr, int appMin, int appDat, int appMonth, int appYear, String currentTimeSynchronizationStatus) {
        int rowsReturned = 0;
        String insertQuery = " INSERT INTO time_synchronization_detail(junction_id, time_synchronization_detail_id, junction_hr, application_hr, junction_min, application_min, "
                + " junction_date, application_date, junction_month, application_month, junction_year, application_year, synchronization_status, remark, program_version_no) "
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";

        PreparedStatement pstmt;
        try {
            int final_rev_no = getLastTimeSynchronizationId(junctionID, program_version_no);
            pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, (final_rev_no + 1));
            pstmt.setInt(3, juncHr);
            pstmt.setInt(4, appHr);
            pstmt.setInt(5, juncMin);
            pstmt.setInt(6, appMin);
            pstmt.setInt(7, juncDat);
            pstmt.setInt(8, appDat);
            pstmt.setInt(9, juncMonth);
            pstmt.setInt(10, appMonth);
            pstmt.setInt(11, juncYear);
            pstmt.setInt(12, appYear);
            pstmt.setString(13, currentTimeSynchronizationStatus);
            pstmt.setString(14, "");
            pstmt.setInt(15, program_version_no);
            rowsReturned = pstmt.executeUpdate();
            if (rowsReturned > 0 && final_rev_no != -1) {
                rowsReturned = 0;
                rowsReturned = updateSynchronizeTimeFinalRevision(junctionID, program_version_no, final_rev_no);
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel insertSynchronizeTimeData() Error: " + e);
        }
        return rowsReturned;
    }

    public int updateSynchronizeTimeFinalRevision(int junctionID, int program_version_no, int time_synchronization_detail_id) {
        String updateQuery = "UPDATE time_synchronization_detail SET  final_revision = ? WHERE  junction_id = ? AND time_synchronization_detail_id = ? AND program_version_no = ? ";
        int rowsReturned = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, "EXPIRED");
            pstmt.setInt(2, junctionID);
            pstmt.setInt(3, time_synchronization_detail_id);
            pstmt.setInt(4, program_version_no);
            rowsReturned = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel updateSynchronizeTimeFinalRevision() Error: " + e);
        }
        return rowsReturned;
    }

    public int getLastTimeSynchronizationId(int junctionID, int program_version_no) {
        int id = 0;
        String query = " SELECT COUNT(*) AS c,time_synchronization_detail_id AS id FROM time_synchronization_detail WHERE final_revision='VALID' AND junction_id = ? AND program_version_no = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt("c") != 0 ? rset.getInt("id") : -1;
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel: getLastTimeSynchronizationId() Error" + e);
        }
        return id;
    }

    public int getjuncOnOffTimeSynchronizationStatus(int junctionID, int program_version_no, int juncOnTimeHr, int juncOnTimeMin, int juncOffTimeHr, int juncOffTimeMin) {
        int status = 0;
        String query = " SELECT IF(sunrise_hr = " + juncOnTimeHr + " AND  sunrise_min = " + juncOnTimeHr + " AND sunset_hr = " + juncOnTimeHr + " AND sunset_min = " + juncOnTimeHr + ", 1,0 ) AS status, "
                + " city_id,date,SUBSTRING(date, 6),SUBSTRING(CURDATE(),6) FROM jn_rise_set_time WHERE city_id = " + getCityId(junctionID)
                + " AND SUBSTRING(date, 6)=SUBSTRING(CURDATE(),6) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                status = rset.getInt("status");
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel: getjuncOnOffTimeSynchronizationStatus() Error" + e);
        }
        return status;
    }

    public long getTimeInterval() {
        long timeInterval = 0;
        String query = " SELECT time_interval AS time_interval_in_seconds FROM time_interval t WHERE time_interval_for='Energy Meter Data' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                timeInterval = rset.getLong("time_interval_in_seconds");
            }
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel: getTimeInterval() Error" + e);
        }
        return timeInterval;
    }

     public int getFileNo(int junction_id, int program_version_no) {
        int fileNo = 0;
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(" SELECT panel_file_no FROM junction WHERE junction_id= ? AND program_version_no = ? ");
            pstmt.setInt(1, junction_id);
            pstmt.setInt(2, program_version_no);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            fileNo = rset.getInt("panel_file_no");
            //System.out.println(fileNo);
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel getFIleNo() Error: " + e);
        }
        return fileNo;
    }

    public int updateFileNo(int junctionID, int file_no, int program_version_no) {
        String query = null;
        PreparedStatement pstmt = null;
        query = "UPDATE junction SET panel_file_no= ? WHERE junction_id = ? AND program_version_no = ? ";
        int rowsAffected = 0;
        try {
            pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, file_no);
            pstmt.setInt(2, junctionID);
            pstmt.setInt(3, program_version_no);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException sqlEx) {
            System.out.println("EnergyMeateWebServiceModel updateFileNo() Error: " + sqlEx.getMessage());
        }
        return rowsAffected;
    }

    public int updatePanelTransferredStatus(int junctionID, int program_version_no) {
        String query = null;
        PreparedStatement pstmt = null;
        query = "UPDATE junction SET panel_transferred_status='Y' WHERE junction_id = ? AND program_version_no = ? ";
        int rowsAffected = 0;
        try {
            pstmt = connection.prepareStatement(query);
            //pstmt.setInt(1, file_no);
            pstmt.setInt(1, junctionID);
            pstmt.setInt(2, program_version_no);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException sqlEx) {
            System.out.println("EnergyMeateWebServiceModel updateFileNo() Error: " + sqlEx.getMessage());
        }
        return rowsAffected;
    }

    public int getCityId(int junctionID) {
        int id = 0;
        String query = "SELECT IF(isOnPole = 'Y' ,c.city_id, sc.city_id) AS city_id FROM junction AS j,"
                + " switching_point_detail AS spd LEFT JOIN (pole AS p,city AS c, ward AS w, area AS a ,city AS sc, ward AS sw, area AS sa )"
                + " ON spd.switching_point_detail_id = p.switching_point_detail_id AND spd.switching_rev_no = p.switching_rev_no"
                + " AND spd.active = 'Y' AND p.active='Y' AND p.area_id = a.area_id"
                + " AND a.ward_id = w.ward_id AND a.ward_rev_no = w.ward_rev_no AND w.city_id=c.city_id AND w.active='Y'"
                + " AND spd.area_id = sa.area_id AND sa.ward_id = sw.ward_id AND sa.ward_rev_no = sw.ward_rev_no AND sw.city_id=sc.city_id AND sw.active='Y'"
                + " WHERE spd.switching_point_detail_id = j.switching_point_detail_id AND spd.switching_rev_no = j.switching_rev_no AND j.active = 'Y' AND spd.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                id = rset.getInt("city_id");
            }
//            connection.close();
        } catch (Exception e) {
            System.out.println("EnergyMeateWebServiceModel: getCityId() Error" + e);
        }
        return id;
    }
    
    
    public JSONArray getAllTubewellData() {
               
        //JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        //Map map = new HashMap();
//        String query = "select tube_well_detail_id,tube_well_name,lattitude,longitude\n" +
//                       "from tube_well_detail group by tube_well_detail_id";

         String query = "SELECT tube_well_survey_id,s.survey_id, s.lattitude, s.longitude "
                 +" ,DATE_FORMAT(survey_date, '%d-%m-%Y') AS survey_date,survey_type,pole_no,mobile_no "
                 +" FROM tube_well_survey as t "
                 +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
                 +" left join feeder as f ON f.feeder_id=m.feeder_id "
                 +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
                 +" AND top.type_of_premises_id=ptm.type_of_premises_id "
                 +" ,survey as s,starter as sr,starter_make as sm "
                 +" where t.tube_well_survey_id=s.survey_id  and sr.starter_id=t.starter_id and sm.starter_make_id=t.starter_make_id "
                 + " and t.active='Y' and s.status='Y'";
        try {
     
           ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {
                JSONObject jsonObj = new JSONObject();
                String tank_id=rset.getString("tube_well_survey_id");
                int tank_id1=Integer.parseInt(tank_id);
                jsonObj.put("common_id",tank_id1);
                String latitude = rset.getString("lattitude");
                if(latitude.length()<6 || latitude == null){
                
                }else{
                jsonObj.put("active","No");
               // jsonObj.put("common_name",rset.getString("tube_well_name"));
                jsonObj.put("latitude",rset.getString("lattitude"));
                jsonObj.put("longitude",rset.getString("longitude"));                             
                arrayObj.put(jsonObj);   
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayObj;
    }
    
    public JSONArray getAllTubewellDataInfo(String latitude , String longitude) {
                    
        JSONArray arrayObj = new JSONArray();      
//         String query = "select  s.survey_type,s.pole_no,s.mobile_no from survey as s" +
//                        " where  s.lattitude = "+latitude+" and s.longitude = "+longitude+" and s.status='Y'";
       

 String query = "SELECT tube_well_survey_id,s.survey_id, s.lattitude, s.longitude "
                 +" ,DATE_FORMAT(survey_date, '%d-%m-%Y') AS survey_date,survey_type,pole_no,mobile_no "
                 +" FROM tube_well_survey as t "
                 +" left join meters as m ON m.meter_name_auto=t.meter_name_auto AND m.final_revision='VALID' "
                 +" left join feeder as f ON f.feeder_id=m.feeder_id "
                 +" left join (premises_tariff_map as ptm, type_of_premises as top) ON m.premises_tariff_map_id=ptm.premises_tariff_map_id "
                 +" AND top.type_of_premises_id=ptm.type_of_premises_id "
                 +" ,survey as s,starter as sr,starter_make as sm "
                 +" where t.tube_well_survey_id=s.survey_id  and sr.starter_id=t.starter_id and sm.starter_make_id=t.starter_make_id "
                 + " and t.active='Y' and s.status='Y' and s.lattitude = "+latitude+" and s.longitude = "+longitude+" ";



try {
            
          
            ResultSet rset = connection.prepareStatement(query).executeQuery();

            while (rset.next()) {
                JSONObject jsonObj = new JSONObject();            
                jsonObj.put("first",rset.getString("survey_type"));
                jsonObj.put("second",rset.getString("pole_no"));                             
                jsonObj.put("third",rset.getString("mobile_no"));                             
               // jsonObj.put("fourth"," ");                             
                //jsonObj.put("fifth"," ");                             
                arrayObj.put(jsonObj);                
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return arrayObj;
    }


    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try{
            this.connection.close();
        }catch(Exception ex){
            System.out.println("ERROR : in closeConncetion() in EnergyMeterWebServiceModel : "+ ex);
        }
    }

    public void setSession(HttpSession session){
        this.session = session;
    }

    public void setContext(ServletContext ctx){
        this.context = ctx;
    }
}
