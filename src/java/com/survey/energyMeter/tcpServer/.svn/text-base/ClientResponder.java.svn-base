package com.survey.energyMeter.tcpServer;

import com.survey.energyMeter.general.controller.ModemResReadSaveController;
import com.survey.energyMeter.general.controller.EM_StatusShowerController;
import com.survey.energyMeter.general.controller.EM_StatusUpdaterController;
import com.survey.energyMeter.tableClasses.EnergyMeterStatus;
import com.survey.energyMeter.tableClasses.JunctionBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ClientResponder extends HttpServlet
        implements Runnable {

    private InputStream inputStream;
    private OutputStream outputStream;
    private Socket clientSocket;
    private BufferedReader reader;
    private OutputStreamWriter outputStreamWriter;
    private String driverClass;
    private PrintWriter out;
    private String connectionString;
    private String db_userName;
    private Map<String, ClientResponder> map;
    private String db_userPassword;
    private Map<Integer, JunctionBean> junctionList;
    private String ipAddress;
    private String ipPort;
    private String ipLoginTimstamp;
    private Boolean ipStatus;
    ClientResponderModel clientResponderModel = new ClientResponderModel();
    EM_StatusShowerController tsStatusShowerCont = new EM_StatusShowerController();
    private JunctionBean junction;
    private int totalNoOfSides;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext ctx;
    private ServerSocket serverSocket;
    private Async async;
    private Date date;
    private EM_StatusUpdaterController tsUpdaterCont;
    private Calendar cal;
    private EnergyMeterStatus energyMeterStatus = new EnergyMeterStatus();
    String currentTime;
    private String lastVisitedTime;
    DateFormat dateFormat;
    private boolean requestForClearance;
    private boolean requestForActivity;
    private boolean requestToUpdateProgramVersionNo;
    private boolean isJunctionLive;
    private int clearanceSide;
    private int activityNo;
    private int activitySide;
    private ModemResReadSaveController modemResReadSaveCont;
    private int noOfRequestReceived;
    private String receivedData = "";
    private String sentData = "";

    public ClientResponder(InputStream inputStream, OutputStream outputStream) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
        this.outputStreamWriter = new OutputStreamWriter(outputStream);
        this.out = new PrintWriter(outputStream, true);
        this.async = new Async();
        this.modemResReadSaveCont = new ModemResReadSaveController();
        this.tsUpdaterCont = new EM_StatusUpdaterController();
    }

    public ClientResponder() {
    }

    public void run() {
        this.clientResponderModel.setDriverClass(this.driverClass);
        this.clientResponderModel.setConnectionString(this.connectionString);
        this.clientResponderModel.setDb_userName(this.db_userName);
        this.clientResponderModel.setDb_userPasswrod(this.db_userPassword);
        this.clientResponderModel.setConnection();

        this.async.setClientSocket(this.clientSocket);
        this.async.setTcpServer(this.serverSocket);
        this.async.setDriverClass(this.driverClass);
        this.async.setConnectionString(this.connectionString);
        this.async.setDb_userName(this.db_userName);
        this.async.setDb_userPasswrod(this.db_userPassword);
        this.ctx.setAttribute("energyMeterStatus", this.energyMeterStatus);
        this.ctx.setAttribute("receivedData", this.receivedData);
        this.ctx.setAttribute("sentData", this.sentData);
        this.tsUpdaterCont.setCtx(this.ctx);

        System.out.println("Reading data from Client...");
        try {
            this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.cal = Calendar.getInstance();
            this.currentTime = this.dateFormat.format(this.cal.getTime());
            this.lastVisitedTime = this.currentTime;
            System.out.println("run:cal -lastVisitedTime--" + this.lastVisitedTime);
            this.junction.setLastVisitedTime(this.lastVisitedTime);
            this.junction.setClientResponder(this);
            this.async.setJunction(this.junction);
            this.ctx.setAttribute("energyMeterStatus", this.energyMeterStatus);
            this.ctx.setAttribute("receivedData", this.receivedData);
            this.ctx.setAttribute("sentData", this.sentData);
            this.tsUpdaterCont.setCtx(this.ctx);
            this.ctx.setAttribute("junction", this.junction);
            this.modemResReadSaveCont.setCtx(this.ctx);
            System.out.println("clientSocket closed Status #######" + this.clientSocket.isClosed());
            System.out.println("client responder obj=" + this.junction.getClientResponder());

            while (readClientResponse()) {
                try {
                    synchronized (this) {
                        wait(2000L);
                    }
                } catch (InterruptedException interruptedEx) {
                    System.out.println("ClientResponseReader run() Error: " + interruptedEx);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientResponder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean readClientResponse()
            throws IOException {
        boolean isConnectionClosed = false;
        String currentVisitedTime = null;
        try {
            byte[] bytes = new byte[1000];
            int read = this.inputStream.read(bytes);
            while (read > 1) {
                noOfRequestReceived++;
                System.out.println("number of bytes actualy read: " + read);
                System.out.println("received Request No: " + noOfRequestReceived);
                this.lastVisitedTime = this.currentTime;
                this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                this.cal = Calendar.getInstance();
                this.currentTime = this.dateFormat.format(this.cal.getTime());
                System.out.println("clientSocket closed Status #######" + this.clientSocket.isClosed());
                System.out.println("cal -currentTime--" + this.currentTime);
                this.junction.setLastVisitedTime(this.currentTime);
                this.async.setJunction(this.junction);
                int firstStartDataPosition = 0;
                int minimumDatabytes = 6;
                int initialflag1 = 0;
                int initialflag2 = 0;
                int endflag2 = 0;
                try {
                    for (int i = 0; i <= (bytes.length - minimumDatabytes) && (initialflag1 != 1 || initialflag2 != 1); i++) {
                        if (bytes[i] == 126 && (initialflag1 != 1 || initialflag2 != 1)) {
                            if (bytes[i + 1] == 126) {
                                firstStartDataPosition = i + 5;
                                initialflag2 = 1;
                            } else {
                                firstStartDataPosition = i + 4;
                                initialflag1 = 1;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ClientResponseReader readClientResponse initial delimiter Exception: " + e);
                }
                if (initialflag1 == 1 || initialflag2 == 1) {
                    byte dataSize[] = new byte[2];
                    dataSize[0] = bytes[firstStartDataPosition - 3];
                    dataSize[1] = bytes[firstStartDataPosition - 2];
                    int receivedDataSize = new BigInteger(dataSize).intValue();
                    int function_no = bytes[firstStartDataPosition + 3];

                    if (matchDataLength(receivedDataSize, function_no)) {
                        this.receivedData = "";
                        try {
                            for (int i = initialflag2 == 1 ? firstStartDataPosition - 5 : firstStartDataPosition - 4; i <= (firstStartDataPosition + receivedDataSize + 2); i++) {
                                this.receivedData = this.receivedData + bytes[i] + " ";
                            }
                        } catch (Exception e) {
                            System.out.println("this.receivedData for loop error: "+e);
                        }

                        if (bytes[firstStartDataPosition + receivedDataSize] == 125) {

                            if (validateDataByCrc(bytes, firstStartDataPosition, receivedDataSize)) {
                                boolean testRequestFromJunction = false;
                                int junctionID = (int) bytes[firstStartDataPosition];
                                int program_version_no = (int) bytes[firstStartDataPosition + 1];
                                int fileNo = (int) bytes[firstStartDataPosition + 2];
                                int functionNo = (int) bytes[firstStartDataPosition + 3];
                                currentVisitedTime = this.currentTime;
                                System.out.println("readClientResponse----currentVisitedTime--- " + currentVisitedTime + ", " + "lastVisitedTime--- " + this.lastVisitedTime);

                                int calculateLastTime = calculateTimeInSeconds(this.lastVisitedTime);
                                int calculateCurrentTime = calculateTimeInSeconds(currentVisitedTime);
                                int calculatedDifference = calculateCurrentTime - calculateLastTime;

                                this.junction.setLastVisitedTime(currentVisitedTime);
                                this.junction.setJunction_id(junctionID);
                                this.junction.setProgram_version_no(program_version_no);
                                this.junction.setPanel_file_no(fileNo + "".trim());
                                this.junction.setClientResponder(this);
                                this.async.setJunction(this.junction);
                                this.ctx.setAttribute("junction", this.junction);
                                this.modemResReadSaveCont.setCtx(this.ctx);
                                String res = null;

                                int error = functionNo != 1 ? clientResponderModel.checkJunctionId(junctionID, program_version_no, fileNo) : 0;

                                if (functionNo != 1 ? error == 12 ? true : functionNo == 4 ? true : false : true) {

                                    if (functionNo == 2 && !isJunctionLive) {

                                        if (checkIfTestRequest(bytes, firstStartDataPosition)) {
                                            setRequestForActivity(false);
                                            setRequestForClearance(false);
                                            testRequestFromJunction = true;
                                        }
//                                    if (clientResponderModel.checkJunctionLastSynchronisation(ipAddress, ipPort, junctionID, program_version_no, testRequestFromJunction)) {
                                        confirmRegistration(junctionID, program_version_no, testRequestFromJunction);
//                                    }
                                    }
                                    if (functionNo == 2 ? isJunctionLive : true) {
                                        if (functionNo == 1) {
                                            if (this.clientResponderModel.isValidJunction(junctionID, program_version_no)) {
                                                res = doRegistration(bytes, firstStartDataPosition);
                                            } else {
                                                res = checkRegistration(bytes, firstStartDataPosition);
                                            }
                                        } else if (functionNo == 2) {
                                            res = junctionRefreshFunction(bytes, firstStartDataPosition, testRequestFromJunction);
                                        } else if (functionNo == 4) {
                                            res = getJunctionTime(bytes, firstStartDataPosition);
                                        } else if (functionNo == 5) {
                                            res = SunriseSunsetDetails(bytes, firstStartDataPosition);
                                        } else if (functionNo == 6) {
                                            res = mobileDetails(bytes, firstStartDataPosition);
                                        } else if (functionNo == 7) {
                                            res = ipDetails(bytes, firstStartDataPosition);
                                        } else {
                                            sendErrorResponse(14);
                                        }
                                        if (res != null && !res.isEmpty()) {
                                            sendResponse(res);
                                        }
                                    } else {
                                        sendErrorResponse(13);
                                    }
                                } else {
                                    sendErrorResponse(error);
                                }
                            } else {
                                sendErrorResponse(4);
                            }
                        } else {
                            if (initialflag2 == 1) {
                                sendErrorResponse(3);
                            } else {
                                sendErrorResponse(18);
                                //System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Error: Delimiters not found  @@@@@@@@@@@@@@@@@@@@@@");
                            }
                        }
                    } else {
                        if (initialflag2 == 1) {
                            sendErrorResponse(2);
                        } else {
                            sendErrorResponse(19);
                            //System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Error: corrupted data according to data length and single starting byte  @@@@@@@@@@@@@@@@@@@@@@");
                        }
                    }
                } else {
                    try {
                        for (int i = minimumDatabytes; i < (bytes.length) - 1 && endflag2 != 1; i++) {
                            if (bytes[i] == 125) {
                                if (bytes[i + 1] == 125) {
                                    endflag2 = 1;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("ClientResponseReader readClientResponse last delimiter Exception: " + e);
                    }
                    if (endflag2 == 1) {
                        sendErrorResponse(1);
                    } else {
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@ Delimiters not found  @@@@@@@@@@@@@@@@@@@@@@");
                    }


                }
                this.receivedData = "";
                read = this.inputStream.read(bytes);
            }
        } catch (IOException ioEx) {
            this.clientSocket.close();
            //System.out.println("Socket got closed during readClientResponse() exception catch");
            isConnectionClosed = true;
            //System.out.println("ClientResponseReader readClientResponse() Error: " + ioEx);
        }
        isConnectionClosed = setConnectedIpList();
        return !isConnectionClosed;
    }

    public boolean validateDataByCrc(byte[] bytes, int firstStartDataPosition, int receivedDataSize) {

        int calculatedCrc = 0;
        int receivedCrc = 0;
        int dSum = 0;
        try {
            for (int i = (firstStartDataPosition - 1); i < ((firstStartDataPosition - 1) + receivedDataSize); i++) {
                int d = bytes[i];
                dSum += d;
            }
            calculatedCrc = (0xFF - (dSum & 0xFF)); // get lowest 8-bit and subtract lowest 8-bit from 255
            receivedCrc = bytes[(firstStartDataPosition - 1) + receivedDataSize] & 0xFF;
            System.out.println("Receivd crc: " + receivedCrc + "  and calculatedCrc: " + calculatedCrc);
        } catch (Exception e) {
            System.out.println("Exception in ClientResponder validateData: " + e);
        }
        return (calculatedCrc == receivedCrc);
    }

    public boolean matchDataLength(int dataLength, int functionNo) {
        if (functionNo == 1) {
            return (dataLength == 28 || dataLength == 30);
        } else if (functionNo == 2) {
            return (dataLength == 46);
        } else if (functionNo == 3) {
            return (dataLength == 19);
        } else if (functionNo == 4) {
            return (dataLength == 5 || dataLength == 20);
        } else if (functionNo == 5) {
            return (dataLength == 6);
        } else if (functionNo == 6) {
            return (dataLength == 35);
        } else if (functionNo == 7) {
            return (dataLength == 17);
        } else {
            return false;
        }
    }

    public boolean checkIfTestRequest(byte[] dataToProcess, int firstStartDataPosition) {
        boolean result = false;

        int test1byte = dataToProcess[firstStartDataPosition + 4];
        int test2byte = dataToProcess[firstStartDataPosition + 5];
        int test3byte = dataToProcess[firstStartDataPosition + 6];
        int test4byte = dataToProcess[firstStartDataPosition + 7];
        int test5byte = dataToProcess[firstStartDataPosition + 8];
        int test6byte = dataToProcess[firstStartDataPosition + 9];
        int test7byte = dataToProcess[firstStartDataPosition + 10];
        int test8byte = dataToProcess[firstStartDataPosition + 11];
        int test9byte = dataToProcess[firstStartDataPosition + 12];
        int test10byte = dataToProcess[firstStartDataPosition + 13];
        int test11byte = dataToProcess[firstStartDataPosition + 14];
        int test12byte = dataToProcess[firstStartDataPosition + 15];
        int test13byte = dataToProcess[firstStartDataPosition + 16];
        int test14byte = dataToProcess[firstStartDataPosition + 17];
        int test15byte = dataToProcess[firstStartDataPosition + 18];
        int test16byte = dataToProcess[firstStartDataPosition + 19];
        int test17byte = dataToProcess[firstStartDataPosition + 20];
        int test18byte = dataToProcess[firstStartDataPosition + 21];
        int test19byte = dataToProcess[firstStartDataPosition + 23];
        int test20byte = dataToProcess[firstStartDataPosition + 23];
        int test21byte = dataToProcess[firstStartDataPosition + 24];
        int test22byte = dataToProcess[firstStartDataPosition + 25];
        int test23byte = dataToProcess[firstStartDataPosition + 26];
        int test24byte = dataToProcess[firstStartDataPosition + 27];
        int test25byte = dataToProcess[firstStartDataPosition + 28];
        int test26byte = dataToProcess[firstStartDataPosition + 29];
        int test27byte = dataToProcess[firstStartDataPosition + 30];
        int test28byte = dataToProcess[firstStartDataPosition + 31];
        int test29byte = dataToProcess[firstStartDataPosition + 32];
        int test30byte = dataToProcess[firstStartDataPosition + 33];
        int test31byte = dataToProcess[firstStartDataPosition + 34];
        int test32byte = dataToProcess[firstStartDataPosition + 35];
        int test33byte = dataToProcess[firstStartDataPosition + 36];
        int test34byte = dataToProcess[firstStartDataPosition + 37];
        int test35byte = dataToProcess[firstStartDataPosition + 38];
        int test36byte = dataToProcess[firstStartDataPosition + 39];

        if (test1byte == 1 && test2byte == 1 && test3byte == 1
                && test4byte == 1 && test5byte == 1 && test6byte == 1
                && test7byte == 1 && test8byte == 1 && test9byte == 1
                && test10byte == 1 && test11byte == 1 && test12byte == 1
                && test13byte == 1 && test14byte == 1 && test15byte == 1
                && test16byte == 1 && test17byte == 1 && test18byte == 1
                && test19byte == 1 && test20byte == 1 && test21byte == 1
                && test22byte == 1 && test23byte == 1 && test24byte == 1
                && test25byte == 1 && test26byte == 1 && test27byte == 1
                && test28byte == 1 && test29byte == 1 && test30byte == 1
                && test31byte == 1 && test32byte == 1 && test33byte == 1
                && test34byte == 1 && test35byte == 1 && test36byte == 1) {
            result = true;
        }

        return result;
    }

    public String checkRegistration(byte[] dataToProcess, int firstStartDataPosition) {
        String imeiNo = "";
        String energyMeterNo = "";
        String response = "";
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int dataSize1 = dataToProcess[firstStartDataPosition - 3];
            int dataSize2 = dataToProcess[firstStartDataPosition - 2];
            int project_no = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];

            for (int i = (firstStartDataPosition + 4); i <= (firstStartDataPosition + 18); i++) {
                char ch = (char) dataToProcess[i];
                imeiNo = imeiNo + ch;
            }

            for (int i = (firstStartDataPosition + 19); i <= (firstStartDataPosition + 26); i++) {
                char ch = (char) dataToProcess[i];
                energyMeterNo = energyMeterNo + ch;
            }

            int crc = dataToProcess[firstStartDataPosition + 27] & 0xFF;
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 28];
            int secLastDelimiter = dataToProcess[firstStartDataPosition + 29];

            receivedData = "FirstStartDelimliter=" + firstStartDelimiter + " SecondStartDelimliter=" + secStartDelimiter + " dataSize1=" + dataSize1
                    + " dataSize2=" + dataSize2 + " project_no=" + project_no + " junctionID=" + junctionID + " program_version_no=" + program_version_no + " file_no=" + fileNo
                    + " functionNo=" + functionNo + " imeiNo= " + imeiNo + " energyMeterNo= " + energyMeterNo + " crc=" + crc + " firstLastDelimiter= " + firstLastDelimiter + " secLastDelimiter= " + secLastDelimiter;
            System.out.println("register modem request--- " + receivedData);

            int junctIDFromDB = this.clientResponderModel.getJunctionID(imeiNo, energyMeterNo);

            if (junctIDFromDB != 0) {
                int programVersionNoFromDB = junctIDFromDB == 0 ? 0 : this.clientResponderModel.getProgramVersionNo(junctIDFromDB);

                int registrationStatus = junctIDFromDB == 0 ? 0 : this.clientResponderModel.updateJunctionTransferredStatus(junctionID, program_version_no, "NO") ? 0 : 0;

                int noOfPhases = this.clientResponderModel.getNoOfPhases(junctIDFromDB, programVersionNoFromDB);
                int typeOfConnection = this.clientResponderModel.getTypeOfConnection(junctIDFromDB, programVersionNoFromDB);
                int fileNoFromDB = this.clientResponderModel.getFileNo(junctIDFromDB, programVersionNoFromDB);

                if (fileNo != fileNoFromDB) {
                    this.clientResponderModel.updateFileNo(junctIDFromDB, fileNo, programVersionNoFromDB);
                }
                fileNoFromDB = this.clientResponderModel.getFileNo(junctIDFromDB, programVersionNoFromDB);

                response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctIDFromDB + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + registrationStatus + " " + noOfPhases + " " + typeOfConnection + " " + firstLastDelimiter + " " + firstLastDelimiter;
            } else {
                sendErrorResponse(this.clientResponderModel.checkJunctionError(imeiNo, energyMeterNo));
            }
        } catch (Exception e) {
            System.out.println("ClientResponder checkRegistration exception: " + e);
        }
        return response;
    }

    public String doRegistration(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        String imeiNo = "";
        String energyMeterNo = "";
        try {
            int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
            int dataSize1 = dataToProcess[firstStartDataPosition - 3];
            int dataSize2 = dataToProcess[firstStartDataPosition - 2];
            int project_no = dataToProcess[firstStartDataPosition - 1];
            int junctionID = dataToProcess[firstStartDataPosition];
            int program_version_no = dataToProcess[firstStartDataPosition + 1];
            int fileNo = dataToProcess[firstStartDataPosition + 2];
            int functionNo = dataToProcess[firstStartDataPosition + 3];
            for (int i = (firstStartDataPosition + 4); i <= (firstStartDataPosition + 18); i++) {
                char ch = (char) dataToProcess[i];
                imeiNo = imeiNo + ch;
            }

            for (int i = (firstStartDataPosition + 19); i <= (firstStartDataPosition + 26); i++) {
                char ch = (char) dataToProcess[i];
                energyMeterNo = energyMeterNo + ch;
            }

            int noOfPhases = dataToProcess[firstStartDataPosition + 27];
            int typeOfConnection = dataToProcess[firstStartDataPosition + 28];
            int crc = dataToProcess[firstStartDataPosition + 29] & 0xFF;
            int firstLastDelimiter = dataToProcess[firstStartDataPosition + 30];
            int secLastDelimiter = dataToProcess[firstStartDataPosition + 31];

            receivedData = firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + project_no + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + imeiNo + " " + energyMeterNo + " " + noOfPhases + " " + typeOfConnection + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter;
            System.out.println("acknowledgement request--- " + receivedData);

            int junctIDFromDB = this.clientResponderModel.getJunctionID(imeiNo, energyMeterNo);

            if (junctIDFromDB != 0) {

                int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(imeiNo, energyMeterNo, junctionID);
                int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);
                int noOfPhasesFromDB = this.clientResponderModel.getNoOfPhases(junctionID, programVersionNoFromDB);
                int typeOfConnectionFromDB = this.clientResponderModel.getTypeOfConnection(junctionID, programVersionNoFromDB);
                if (fileNo != fileNoFromDB) {
                    this.clientResponderModel.updateFileNo(junctionID, fileNo, programVersionNoFromDB);
                }
                fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, programVersionNoFromDB);
                JunctionBean juncID = (JunctionBean) this.junctionList.get(Integer.valueOf(junctionID));
                int registrationStatus = 0;

                if (noOfPhases == noOfPhasesFromDB && typeOfConnection == typeOfConnectionFromDB) {
                    if (program_version_no != 127 && program_version_no != 0) {
                        if (program_version_no != programVersionNoFromDB) {
                            registrationStatus = 1;
                            sendErrorResponse(6);
                        } else {
                            if (!this.clientResponderModel.isJunctionLive(ipAddress, ipPort, junctionID, program_version_no)) {
                                confirmRegistration(junctionID, program_version_no, false);
                            }
                            registrationStatus = 2;
                            juncID.setRegistration_status(true);
                            response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + registrationStatus + " " + noOfPhasesFromDB + " " + typeOfConnectionFromDB + " " + firstLastDelimiter + " " + firstLastDelimiter;
                        }
                    } else {
                        sendErrorResponse(6);
                    }
                } else {
                    registrationStatus = 0;
                    sendErrorResponse(noOfPhases == noOfPhasesFromDB ? typeOfConnection == typeOfConnectionFromDB ? 12 : 9 : 8);
                }
            } else {
                sendErrorResponse(this.clientResponderModel.checkJunctionError(imeiNo, energyMeterNo));
            }

        } catch (Exception e) {
            System.out.println("ClientResponder doRegistration exception: " + e);
        }

        return response;
    }

    private boolean confirmRegistration(int junctionID, int program_version_no, boolean testRequestFromJunction) {
        boolean result = false;
        try {
            boolean updateJunctionTransferredStatus = testRequestFromJunction ? true : this.clientResponderModel.updateJunctionTransferredStatus(junctionID, program_version_no, "Y");
            if (updateJunctionTransferredStatus == true) {
                setIsJunctionLive(true);
                new Thread(this.async).start();
                System.out.println("*********************ASYNC STARTED******************");
                result = true;
            }
        } catch (Exception e) {
            result = false;
            setIsJunctionLive(false);
            System.out.println("Error - clientResponder - " + e);
        }
        return result;
    }

    public String getJunctionTime(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
        int project_no = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int juncHr = dataToProcess[firstStartDataPosition + 4];
        int juncMin = dataToProcess[firstStartDataPosition + 5];
        int juncDat = dataToProcess[firstStartDataPosition + 6];
        int juncMonth = dataToProcess[firstStartDataPosition + 7];
        int juncYear = dataToProcess[firstStartDataPosition + 8];
        int crc = dataToProcess[firstStartDataPosition + 9] & 0xFF;
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 10];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 11];
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

        /*---------------------------- code started to manage one minute difference in junction time and in application time  ------------------------------------------*/

        int monthNo = (juncMonth - appMonth) == Math.abs(1) ? juncMonth > appMonth ? appMonth : juncMonth : 0;

        if (((juncYear - appYear) == Math.abs(1)) && ((juncMonth - appMonth) == Math.abs(11)) && ((juncDat - appDat) == Math.abs(30)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of last month of a year
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
            juncMonth = appMonth;
            juncYear = appYear;
        } else if (((juncMonth - appMonth) == Math.abs(1)) && ((juncDat - appDat) == Math.abs(getNoOfDays(monthNo, appYear) - 1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of a month
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
            juncMonth = appMonth;
        } else if (((juncDat - appDat) == Math.abs(1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of a day
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
        } else if (((juncHr - appHr) == Math.abs(1)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of an hour
            juncHr = appHr;
            juncMin = appMin;
        } else if ((juncMin - appMin) == Math.abs(1)) { //a minute
            juncMin = appMin;
        }
        /*---------------------------- code endeded to manage one minute difference in junction time and in application time  ------------------------------------------*/

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB && fileNoFromDB != 0) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        this.clientResponderModel.synchronizeTimeData(junctionID, program_version_no, juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);
        String currentTimeSynchronizationStatus = this.clientResponderModel.getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);

        int currTimeSynchronizationStatus = currentTimeSynchronizationStatus.equals("Y") ? 1 : 0;

        receivedData = firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + project_no + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + juncHr + " " + juncMin + " " + juncDat + " " + juncMonth + " " + juncYear + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter;
        System.out.println("junction time info request--- " + receivedData);

        response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + appHr + " " + appMin + " " + appDat + " " + appMonth + " " + appYear + " " + currTimeSynchronizationStatus + " " + firstLastDelimiter + " " + firstLastDelimiter;

        //System.out.println("Response of junction info:" + response);
        return response;
    }

    public String SunriseSunsetDetails(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
        int project_no = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        int month = dataToProcess[firstStartDataPosition + 4];
        int crc = dataToProcess[firstStartDataPosition + 5] & 0xFF;
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 6];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 7];
        int cityID = this.clientResponderModel.getCityId(junctionID);

        receivedData = firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + project_no + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + month + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter;
        System.out.println("sunrise sunset  request--- " + receivedData);

        List riseSetTime = this.clientResponderModel.getCitySunriseSunset(month, cityID);

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        response = firstStartDelimiter + " " + secStartDelimiter + " " + firstStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + month;
        Iterator iter = riseSetTime.iterator();
        while (iter.hasNext()) {
            response = response + " " + (Integer) iter.next();
        }
        response = response + " " + firstLastDelimiter + " " + secLastDelimiter;
        //System.out.println("Response of sunrisr sunset :" + response);
        return response;
    }

    private String junctionRefreshFunction(byte[] dataToProcess, int firstStartDataPosition, boolean testRequestFromJunction) {
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
        int activity2 = 1;
        int activity3 = 1;
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

        int crc = dataToProcess[firstStartDataPosition + 45];
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 46];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 47];

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

        /*---------------------------- code started to manage one minute difference in junction time and in application time  ------------------------------------------*/

        int monthNo = (juncMonth - appMonth) == Math.abs(1) ? juncMonth > appMonth ? appMonth : juncMonth : 0;

        if (((juncYear - appYear) == Math.abs(1)) && ((juncMonth - appMonth) == Math.abs(11)) && ((juncDat - appDat) == Math.abs(30)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of last month of a year
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
            juncMonth = appMonth;
            juncYear = appYear;
        } else if (((juncMonth - appMonth) == Math.abs(1)) && ((juncDat - appDat) == Math.abs(getNoOfDays(monthNo, appYear) - 1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of last day of a month
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
            juncMonth = appMonth;
        } else if (((juncDat - appDat) == Math.abs(1)) && ((juncHr - appHr) == Math.abs(23)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of last hour of a day
            juncHr = appHr;
            juncMin = appMin;
            juncDat = appDat;
        } else if (((juncHr - appHr) == Math.abs(1)) && ((juncMin - appMin) == Math.abs(59))) { //last minute of an hour
            juncHr = appHr;
            juncMin = appMin;
        } else if ((juncMin - appMin) == Math.abs(1)) { //a minute
            juncMin = appMin;
        }
        /*---------------------------- code endeded to manage one minute difference in junction time and in application time  ------------------------------------------*/

        String currentTimeSynchronizationStatus = this.clientResponderModel.getCurrentTimeSynchronizationStatus(juncHr, juncMin, juncDat, juncMonth, juncYear, appHr, appMin, appDat, appMonth, appYear);

        int juncOnOffTimeSynchronizationStatus = this.clientResponderModel.getjuncOnOffTimeSynchronizationStatus(junctionID, program_version_no, juncOnTimeHr, juncOnTimeMin, juncOffTimeHr, juncOffTimeMin);

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);
        int noOfPhasesFromDB = this.clientResponderModel.getNoOfPhases(junctionID, programVersionNoFromDB);
        int typeOfConnectionFromDB = this.clientResponderModel.getTypeOfConnection(junctionID, programVersionNoFromDB);

        int status = 0;

        receivedData = firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + project_no + " "
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
        String overall_status = this.clientResponderModel.getOverAllStatus(noOfPhasesFromDB, phase1Status, phase2Status, phase3Status, phase1VCStatus, phase2VCStatus, phase3VCStatus);

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
                if (this.junction.getLastDataInstertedTime() == null || calculateTimeInSeconds(this.currentTime) - calculateTimeInSeconds(this.junction.getLastDataInstertedTime()) > this.clientResponderModel.getTimeInterval()) {
                    status = this.clientResponderModel.insertReadings(energyMeterData);
                    this.junction.setLastDataInstertedTime(this.currentTime);
                } else {
                    status = 1;
                }
                this.energyMeterStatus = energyMeterData;
            } catch (Exception e) {
                System.out.println("Error - clientResponder - " + e);
            }
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

        this.ctx.setAttribute("energyMeterStatus", this.energyMeterStatus);
        this.ctx.setAttribute("receivedData", this.receivedData);
        this.ctx.setAttribute("sentData", this.sentData);
        this.ctx.setAttribute("junction", this.junction);
        this.modemResReadSaveCont.setCtx(this.ctx);

        System.out.println("activity bytes: " + activity + " " + activity1 + " " + activity2 + " " + activity3);
        responseVal = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + activity + " " + activity1 + " " + activity2 + " " + activity3 + " " + firstLastDelimiter + " " + firstLastDelimiter;

        return responseVal;
    }

    public String mobileDetails(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        String master_mobile_no = "", sec_mobile_no1 = "", sec_mobile_no2 = "";
        String db_master_mobile_no = "", db_sec_mobile_no1 = "", db_sec_mobile_no2 = "";
        String sdb_master_mobile_no = "", sdb_sec_mobile_no1 = "", sdb_sec_mobile_no2 = "";
        int master_mob_status = 0, sec_mob1_status = 0, sec_mob2_status = 0;

        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
        int project_no = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];
        for (int i = (firstStartDataPosition + 4); i <= (firstStartDataPosition + 13); i++) {
            char ch = (char) dataToProcess[i];
            master_mobile_no = master_mobile_no + ch;
        }
        for (int i = (firstStartDataPosition + 14); i <= (firstStartDataPosition + 23); i++) {
            char ch = (char) dataToProcess[i];
            sec_mobile_no1 = sec_mobile_no1 + ch;
        }
        for (int i = (firstStartDataPosition + 24); i <= (firstStartDataPosition + 33); i++) {
            char ch = (char) dataToProcess[i];
            sec_mobile_no2 = sec_mobile_no2 + ch;
        }
        int crc = dataToProcess[firstStartDataPosition + 34] & 0xFF;
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 35];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 36];

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB && fileNoFromDB != 0) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        db_master_mobile_no = this.clientResponderModel.getMasterMobileNo(junctionID, programVersionNoFromDB);
        db_sec_mobile_no1 = this.clientResponderModel.getSecMobile1(junctionID, programVersionNoFromDB);
        db_sec_mobile_no2 = this.clientResponderModel.getSecMobile2(junctionID, programVersionNoFromDB);

        master_mob_status = (master_mobile_no != null && !master_mobile_no.isEmpty()) ? master_mobile_no.equals(db_master_mobile_no) ? 1 : 0 : 0;
        sec_mob1_status = (sec_mobile_no1 != null && !sec_mobile_no1.isEmpty()) ? sec_mobile_no1.equals(db_sec_mobile_no1) ? 1 : 0 : 0;
        sec_mob1_status = (sec_mobile_no2 != null && !sec_mobile_no2.isEmpty()) ? sec_mobile_no2.equals(db_sec_mobile_no2) ? 1 : 0 : 0;

        receivedData = firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + project_no + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + master_mobile_no + " " + sec_mobile_no1 + " " + sec_mobile_no1 + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter;
        System.out.println("mobile info request--- " + receivedData);

        for (int i = 0; i < 10; i++) {
            sdb_master_mobile_no = sdb_master_mobile_no + " " + db_master_mobile_no.charAt(i);
            sdb_sec_mobile_no1 = sdb_sec_mobile_no1 + " " + db_sec_mobile_no1.charAt(i);
            sdb_sec_mobile_no2 = sdb_sec_mobile_no2 + " " + db_sec_mobile_no2.charAt(i);
        }

        response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + master_mob_status + " " + sec_mob1_status + " " + sec_mob2_status + " " + sdb_master_mobile_no.trim() + " " + sdb_sec_mobile_no1.trim() + " " + sdb_sec_mobile_no2.trim() + " " + firstLastDelimiter + " " + firstLastDelimiter;

        return response;
    }

    public String ipDetails(byte[] dataToProcess, int firstStartDataPosition) {
        String response = null;
        String ip1 = "", ip2 = "";
        int port1 = 0, port2 = 0;
        String db_ip1 = "", db_ip2 = "";
        String sdb_ip1 = "", sdb_ip2 = "";
        int db_port1 = 0, db_port2 = 0;
        int ip1_status = 0, ip2_status = 0, port1_status = 0, port2_status = 0;

        byte twoByteData[] = new byte[2];

        int firstStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int secStartDelimiter = dataToProcess[firstStartDataPosition - 4];
        int dataSize1 = dataToProcess[firstStartDataPosition - 3];
        int dataSize2 = dataToProcess[firstStartDataPosition - 2];
        int project_no = dataToProcess[firstStartDataPosition - 1];
        int junctionID = dataToProcess[firstStartDataPosition];
        int program_version_no = dataToProcess[firstStartDataPosition + 1];
        int fileNo = dataToProcess[firstStartDataPosition + 2];
        int functionNo = dataToProcess[firstStartDataPosition + 3];

        int unsignedIp1Byte1 = dataToProcess[firstStartDataPosition + 4] & 0xFF;
        int unsignedIp1Byte2 = dataToProcess[firstStartDataPosition + 5] & 0xFF;
        int unsignedIp1Byte3 = dataToProcess[firstStartDataPosition + 6] & 0xFF;
        int unsignedIp1Byte4 = dataToProcess[firstStartDataPosition + 7] & 0xFF;

        int unsignedIp2Byte1 = dataToProcess[firstStartDataPosition + 8] & 0xFF;
        int unsignedIp2Byte2 = dataToProcess[firstStartDataPosition + 9] & 0xFF;
        int unsignedIp2Byte3 = dataToProcess[firstStartDataPosition + 10] & 0xFF;
        int unsignedIp2Byte4 = dataToProcess[firstStartDataPosition + 11] & 0xFF;

        ip1 = unsignedIp1Byte1 + "." + unsignedIp1Byte2 + "." + unsignedIp1Byte3 + "." + unsignedIp1Byte4;
        ip2 = unsignedIp2Byte1 + "." + unsignedIp2Byte2 + "." + unsignedIp2Byte3 + "." + unsignedIp2Byte4;

        twoByteData[0] = dataToProcess[firstStartDataPosition + 12];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 13];
        port1 = new BigInteger(twoByteData).intValue();

        twoByteData[0] = dataToProcess[firstStartDataPosition + 14];
        twoByteData[1] = dataToProcess[firstStartDataPosition + 15];
        port2 = new BigInteger(twoByteData).intValue();

        int crc = dataToProcess[firstStartDataPosition + 16] & 0xFF;
        int firstLastDelimiter = dataToProcess[firstStartDataPosition + 17];
        int secLastDelimiter = dataToProcess[firstStartDataPosition + 18];

        int fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        if (fileNo != fileNoFromDB && fileNoFromDB != 0) {
            this.clientResponderModel.updateFileNo(junctionID, fileNo, program_version_no);
        }
        fileNoFromDB = this.clientResponderModel.getFileNo(junctionID, program_version_no);
        int programVersionNoFromDB = this.clientResponderModel.getProgramVersionNo(junctionID);

        db_ip1 = this.clientResponderModel.getIp1(junctionID, programVersionNoFromDB);
        db_ip2 = this.clientResponderModel.getIp2(junctionID, programVersionNoFromDB);
        db_port1 = this.clientResponderModel.getPort1(junctionID, programVersionNoFromDB);
        db_port2 = this.clientResponderModel.getPort2(junctionID, programVersionNoFromDB);

        ip1_status = (db_ip1 != null && !db_ip1.isEmpty()) ? ip1.equals(db_ip1) ? 1 : 0 : 0;
        ip2_status = (db_ip1 != null && !db_ip1.isEmpty()) ? ip1.equals(db_ip1) ? 1 : 0 : 0;
        port1_status = db_port1 == port1 ? 1 : 0;
        port2_status = db_port2 == port2 ? 1 : 0;


        receivedData = firstStartDelimiter + " " + secStartDelimiter + " " + dataSize1 + " " + dataSize2 + " " + project_no + " " + junctionID + " " + program_version_no + " " + fileNo + " " + functionNo + " " + ip1 + " " + ip2 + " " + port1 + " " + port2 + " " + crc + " " + firstLastDelimiter + " " + secLastDelimiter;
        System.out.println("mobile info request--- " + receivedData);

        String[] ip = new String[4];

        ip = db_ip1.split(".");
        for (int i = 0; i < 4; i++) {
            sdb_ip1 = sdb_ip1 + " " + ip[i];
        }
        ip = db_ip2.split(".");
        for (int i = 0; i < 4; i++) {
            sdb_ip2 = sdb_ip2 + " " + ip[i];
        }

        response = secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + secStartDelimiter + " " + project_no + " " + junctionID + " " + programVersionNoFromDB + " " + fileNoFromDB + " " + functionNo + " " + ip1_status + " " + ip2_status + " " + port1_status + " " + port2_status + " " + sdb_ip1.trim() + " " + sdb_ip2.trim() + " " + db_port1 + " " + db_port2 + " " + firstLastDelimiter + " " + firstLastDelimiter;

        return response;
    }

    public boolean sendResponse(String response)
            throws IOException {
        boolean status = false;
        if (response != null && !response.isEmpty()) {
            sentData = "";
            String[] b1 = response.split(" ");
            // byte[] bytes = new byte[b1.length];
            byte[] finalBytes = new byte[b1.length + 3];
            int[] k = new int[b1.length];
            for (int j = 0; j < b1.length; j++) {
                k[j] = Integer.parseInt(b1[j]);
                //   bytes[j] = ((byte) k[j]);
            }
            finalBytes = getFinalBytes(k);
            this.outputStream.write(finalBytes);
            System.out.print("Server Response: ");
            for (int j = 0; j < finalBytes.length; j++) {
                System.out.print(finalBytes[j] + " ");
                sentData = sentData + " " + finalBytes[j];
            }
            System.out.println(" ");
        }
        status = true;
        return status;
    }

    public boolean sendErrorResponse(int error) throws IOException {

        String errorMessage = this.clientResponderModel.getSentErrorMessage(error);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@  Sent Error: " + errorMessage + "  @@@@@@@@@@@@@@@@@@@@@@");
        String errorResponse = "126 126 126 126 0 0 0 3 " + error + " 0 0 0 125 125";
        this.clientResponderModel.insertIntoErrorLog(this.junction.getJunction_id(), error, this.receivedData, errorResponse);
        return sendResponse(errorResponse);
    }

    public boolean sendErrorResponse(String response)
            throws IOException {
        boolean status = false;
        if (response != null) {
            byte[] bytes = response.getBytes();
            this.outputStream.write(bytes);
        }
        status = true;
        return status;
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
            System.out.println("Exception in ClientResponder getFinalBytes: " + e);
        }
        return finalBytes;
    }

    public int calculateTimeInSeconds(String Time) {
        String[] currTime = Time.split(" ");
        String strTime = currTime[1];

        String[] currStr = strTime.split(":");
        int Hr = Integer.parseInt(currStr[0]);
        int Min = Integer.parseInt(currStr[1]);
        int Sec = Integer.parseInt(currStr[2]);
        int calculatedTime = Hr * 60 * 60 + Min * 60 + Sec;

        return calculatedTime;
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
            System.out.println("ClientResponder getNoOfDays error: " + e);
        }
        return noOfDays;
    }

    private String getHealthStatus(String[] receivedPhaseStatus) {
        String status = "";
        int position = 8;
        while (position > 2 && status.isEmpty()) {
            status = receivedPhaseStatus[position].equals("0") ? manipulateErrorStatus(receivedPhaseStatus, position) == 1 ? "Emergency Error " + (position - 1)
                    : position == 8 ? "Fuse In Fault" : position == 7 ? "Fuse Out Fault" : position == 6 ? "Contactor Off"
                    : position == 5 ? "Coil Fault" : position == 4 ? "MCCB In Fault" : position == 3 ? "MCCB Out Fault" : "No Fault"
                    : "";
            position--;
        }

        return status.isEmpty() ? "No Fault" : status;
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

    private String callURL(String strURL) {
        String responseMsg;
        try {
            URL urlObj = new URL(strURL);
            HttpURLConnection httpReq = (HttpURLConnection) urlObj.openConnection();
            httpReq.setDoOutput(true);
            httpReq.setInstanceFollowRedirects(true);
            httpReq.setRequestMethod("GET");
            responseMsg = readWebServerResponse(httpReq.getInputStream());
        } catch (MalformedURLException mfUrlEx) {
            responseMsg = "Invalid URL or unknown protocol.";
        } catch (IOException ioEx) {
            responseMsg = "Unable to connect to the WebServer.";
        } catch (Exception ex) {
            responseMsg = ex.toString();
        }
        return responseMsg;
    }

    private String readWebServerResponse(InputStream inputStream) {
        BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
        String response = "";
        try {
            String nextLine = rd.readLine();
            while (nextLine != null) {
                response = response + nextLine;
                nextLine = rd.readLine();
            }
        } catch (IOException ioEx) {
            response = "Unable to read response from the WebServer.";
            System.out.println("ClientResponseReader readWebServerResponse() Error: " + ioEx);
        }
        return response;
    }

    public void setClientResponse(String message) {
        try {
            message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException uEx) {
            System.out.println("ClientResponseReader setClientResponse() Error: " + uEx);
        }

        String url = "http://localhost/ts/modemResReadSaveCont?task=setClientResponse&message=" + message;
        callURL(url);
    }

    public String updateModemTime(String[] inputs) {
        String response;
        try {
            String hours = inputs[1];
            String minutes = inputs[2];
            String seconds = inputs[3];

            String url = "http://localhost/ts/?req=modem&h=" + hours + "&m=" + minutes + "&s=" + seconds;
            response = callURL(url);
        } catch (Exception ex) {
            response = "Invalid update arguments.";
            System.out.println("ClientResponseReader updateModemTime() Error: " + ex);
        }
        return response;
    }

    public boolean setConnectedIpList() {
        boolean isConnectionClosed = false;
        List<ClientResponder> connectedIpList = (List<ClientResponder>) this.ctx.getAttribute("connectedIp");
        int time_delay = calculateTimeInSeconds(this.currentTime) - calculateTimeInSeconds(this.lastVisitedTime);
        if (this.clientSocket.isClosed() || time_delay > 120) {
            if (connectedIpList != null && connectedIpList.contains(this)) {
                try {
                    this.clientSocket.close();
                    System.out.println("Socket got closed in setConnectedIpList() because of " + time_delay + " time delay.");
                    isConnectionClosed = true;
                    connectedIpList.remove(this);
                    this.ctx.setAttribute("connectedIp", connectedIpList);
                } catch (IOException ex) {
                    Logger.getLogger(ClientResponder.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //isConnectionClosed = connectedIpList == null ? true : false;
            }
        }
        return isConnectionClosed;
    }

    public void setMap(Map<String, ClientResponder> map) {
        this.map = map;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return this.clientSocket;
    }

    public void setTcpServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setJunction(JunctionBean junction) {
        this.junction = junction;
    }

    public void setAsync(Async async) {
        this.async = async;
    }

    public void setModemResReadSaveCont(ModemResReadSaveController modemResReadSaveCont) {
        this.modemResReadSaveCont = modemResReadSaveCont;
    }

    public void setContext(ServletContext ctx) {
        this.ctx = ctx;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setIPAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setJunctionList(Map<Integer, JunctionBean> junctionList) {
        this.junctionList = junctionList;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public void setDb_userPasswrod(String db_userPasswrod) {
        this.db_userPassword = db_userPasswrod;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public void setEnergyMeterStatus(EnergyMeterStatus energyMeterStatus) {
        this.energyMeterStatus = energyMeterStatus;
    }

    public int getClearanceSide() {
        return clearanceSide;
    }

    public void setClearanceSide(int clearanceSide) {
        this.clearanceSide = clearanceSide;
    }

    public boolean isRequestToUpdateProgramVersionNo() {
        return requestToUpdateProgramVersionNo;
    }

    public void setRequestToUpdateProgramVersionNo(boolean requestToUpdateProgramVersionNo) {
        this.requestToUpdateProgramVersionNo = requestToUpdateProgramVersionNo;
    }

    public boolean isRequestForClearance() {
        return requestForClearance;
    }

    public void setRequestForClearance(boolean requestForClearance) {
        this.requestForClearance = requestForClearance;
    }

    public int getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(int activityNo) {
        this.activityNo = activityNo;
    }

    public int getActivitySide() {
        return activitySide;
    }

    public void setActivitySide(int activitySide) {
        this.activitySide = activitySide;
    }

    public boolean isRequestForActivity() {
        return requestForActivity;
    }

    public void setRequestForActivity(boolean requestForActivity) {
        this.requestForActivity = requestForActivity;
    }

    public boolean isIsJunctionLive() {
        return isJunctionLive;
    }

    public void setIsJunctionLive(boolean isJunctionLive) {
        this.isJunctionLive = isJunctionLive;
    }

    public String getIpLoginTimstamp() {
        return ipLoginTimstamp;
    }

    public void setIpLoginTimstamp(String ipLoginTimstamp) {
        this.ipLoginTimstamp = ipLoginTimstamp;
    }

    public Boolean getIpStatus() {
        return ipStatus;
    }

    public void setIpStatus(Boolean ipStatus) {
        this.ipStatus = ipStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getIpPort() {
        return ipPort;
    }

    public void setIpPort(String ipPort) {
        this.ipPort = ipPort;
    }

    public int getNoOfRequestReceived() {
        return noOfRequestReceived;
    }

    public void setNoOfRequestReceived(int noOfRequestReceived) {
        this.noOfRequestReceived = noOfRequestReceived;
    }
}
