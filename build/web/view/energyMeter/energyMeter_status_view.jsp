<%-- 
    Document   : energyMeter_status_view
    Created on : Mar 27, 2015, 4:03:23 PM
    Author     : Pooja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">

            function loadContents() {
                $.ajax({url: "energyMeterStatusCont.do", async: true, data: "task=getLatestStatus", success: function(response_data) {
                        //alert(response_data);
                        if(response_data == ''){}else{
                            var response_data_split = response_data.split("#$", response_data.length);
                            //alert(response_data_split);
                            for(var j=0; j <= response_data_split.length; j++){
                                //alert(response_data_split[j]);
                                var response1 = response_data_split[j].split("=", response_data_split[j].length);
                                if(response1[0] == 'junction_id'){
                                    $("#junction_id").val(response1[1]);
                                }else if(response1[0] == 'program_version_no'){
                                    $("#program_version_no").val(response1[1]);
                                }else if(response1[0] == 'searchJunction'){
                                    $("#searchJunction").val(response1[1]);
                                }else if(response1[0] == 'junctionName'){
                                    $("#junctionName").val(response1[1]);
                                }else if(response1[0] == 'searchReadingDateFrom'){
                                    $("#searchReadingDateFrom").val(response1[1]);
                                }else if(response1[0] == 'searchReadingDateTo'){
                                    $("#searchReadingDateTo").val(response1[1]);
                                }else if(response1[0] == 'voltage1'){
                                    $("#voltage1").val(response1[1]);
                                }else if(response1[0] == 'voltage2'){
                                    $("#voltage2").val(response1[1]);
                                }else if(response1[0] == 'voltage3'){
                                    $("#voltage3").val(response1[1]);
                                }else if(response1[0] == 'current1'){
                                    $("#current1").val(response1[1]);
                                }else if(response1[0] == 'current2'){
                                    $("#current2").val(response1[1]);
                                }else if(response1[0] == 'current3'){
                                    $("#current3").val(response1[1]);
                                }else if(response1[0] == 'activePower1'){
                                    $("#activePower1").val(response1[1]);
                                }else if(response1[0] == 'activePower2'){
                                    $("#activePower2").val(response1[1]);
                                }else if(response1[0] == 'activePower3'){
                                    $("#activePower3").val(response1[1]);
                                }else if(response1[0] == 'power_factor'){
                                    $("#power_factor").val(response1[1]);
                                }else if(response1[0] == 'consumed_unit'){
                                    $("#consumed_unit").val(response1[1]);
                                }else if(response1[0] == 'voltage_unit_id'){
                                    $("#voltage_unit_id").val(response1[1]);
                                }else if(response1[0] == 'no_of_phases'){
                                    $("#no_of_phases").val(response1[1]);
                                }else if(response1[0] == 'no_of_poles'){
                                    $("#no_of_poles").val(response1[1]);
                                }else if(response1[0] == 'current_unit_id'){
                                    $("#current_unit_id").val(response1[1]);
                                }else if(response1[0] == 'type_of_premises_id'){
                                    $("#type_of_premises_id").val(response1[1]);
                                }else if(response1[0] == 'type_of_premises'){
                                    $("#type_of_premises").val(response1[1]);
                                }else if(response1[0] == 'voltage_unit'){
                                    $("#voltage_unit").val(response1[1]);
                                }else if(response1[0] == 'current_unit'){
                                    $("#current_unit").val(response1[1]);
                                }else if(response1[0] == 'reading_date'){
                                    $("#reading_date").val(response1[1]);
                                }else if(response1[0] == 'reading_time'){
                                    $("#reading_time").val(response1[1]);
                                }else if(response1[0] == 'phase1_status'){
                                    $("#phase1_status").val(response1[1]);
                                }else if(response1[0] == 'phase2_status'){
                                    $("#phase2_status").val(response1[1]);
                                }else if(response1[0] == 'phase3_status'){
                                    $("#phase3_status").val(response1[1]);
                                }else if(response1[0] == 'phase1_vc_status'){
                                    $("#phase1_vc_status").val(response1[1]);
                                }else if(response1[0] == 'phase2_vc_status'){
                                    $("#phase2_vc_status").val(response1[1]);
                                }else if(response1[0] == 'phase3_vc_status'){
                                    $("#phase3_vc_status").val(response1[1]);
                                }else if(response1[0] == 'overall_status'){
                                    $("#overall_status").val(response1[1]);
                                }else if(response1[0] == 'health_status_date'){
                                    $("#health_status_date").val(response1[1]);
                                }else if(response1[0] == 'health_status_time'){
                                    $("#health_status_time").val(response1[1]);
                                }else if(response1[0] == 'ivrs_no'){
                                    $("#ivrs_no").val(response1[1]);
                                }else if(response1[0] == 'connected_load'){
                                    $("#connected_load").val(response1[1]);
                                }else if(response1[0] == 'sanctioned_load'){
                                    $("#sanctioned_load").val(response1[1]);
                                }else if(response1[0] == 'phase1_healthy_voltage'){
                                    $("#phase1_healthy_voltage").val(response1[1]);
                                }else if(response1[0] == 'phase2_healthy_voltage'){
                                    $("#phase2_healthy_voltage").val(response1[1]);
                                }else if(response1[0] == 'phase3_healthy_voltage'){
                                    $("#phase3_healthy_voltage").val(response1[1]);
                                }else if(response1[0] == 'phase1_healthy_current'){
                                    $("#phase1_healthy_current").val(response1[1]);
                                }else if(response1[0] == 'phase2_healthy_current'){
                                    $("#phase2_healthy_current").val(response1[1]);
                                }else if(response1[0] == 'phase3_healthy_current'){
                                    $("#phase3_healthy_current").val(response1[1]);
                                }else if(response1[0] == 'contactorOnStatus'){
                                    $("#contactorOnStatus").val(response1[1]);
                                }else if(response1[0] == 'juncOnTimeHr'){
                                    $("#juncOnTimeHr").val(response1[1]);
                                }else if(response1[0] == 'juncOnTimeMin'){
                                    $("#juncOnTimeMin").val(response1[1]);
                                }else if(response1[0] == 'juncOffTimeHr'){
                                    $("#juncOffTimeHr").val(response1[1]);
                                }else if(response1[0] == 'juncOffTimeMin'){
                                    $("#juncOffTimeMin").val(response1[1]);
                                }else if(response1[0] == 'phase1_fuseIn'){
                                    $("#phase1_fuseIn").val(response1[1]);
                                }else if(response1[0] == 'phase1_fuseOut'){
                                    $("#phase1_fuseOut").val(response1[1]);
                                }else if(response1[0] == 'phase1_contactor_switching'){
                                    $("#phase1_contactor_switching").val(response1[1]);
                                }else if(response1[0] == 'phase1_coil_status'){
                                    $("#phase1_coil_status").val(response1[1]);
                                }else if(response1[0] == 'phase1_mccbIn'){
                                    $("#phase1_mccbIn").val(response1[1]);
                                }else if(response1[0] == 'phase1_mccbOut'){
                                    $("#phase1_mccbOut").val(response1[1]);
                                }else if(response1[0] == 'phase2_fuseIn'){
                                    $("#phase2_fuseIn").val(response1[1]);
                                }else if(response1[0] == 'phase2_fuseOut'){
                                    $("#phase2_fuseOut").val(response1[1]);
                                }else if(response1[0] == 'phase2_contactor_switching'){
                                    $("#phase2_contactor_switching").val(response1[1]);
                                }else if(response1[0] == 'phase2_coil_status'){
                                    $("#phase2_coil_status").val(response1[1]);
                                }else if(response1[0] == 'phase2_mccbIn'){
                                    $("#phase2_mccbIn").val(response1[1]);
                                }else if(response1[0] == 'phase2_mccbOut'){
                                    $("#phase2_mccbOut").val(response1[1]);
                                }else if(response1[0] == 'phase3_fuseIn'){
                                    $("#phase3_fuseIn").val(response1[1]);
                                }else if(response1[0] == 'phase3_fuseOut'){
                                    $("#phase3_fuseOut").val(response1[1]);
                                }else if(response1[0] == 'phase3_contactor_switching'){
                                    $("#phase3_contactor_switching").val(response1[1]);
                                }else if(response1[0] == 'phase3_coil_status'){
                                    $("#phase3_coil_status").val(response1[1]);
                                }else if(response1[0] == 'phase3_mccbIn'){
                                    $("#phase3_mccbIn").val(response1[1]);
                                }else if(response1[0] == 'phase3_mccbOut'){
                                    $("#phase3_mccbOut").val(response1[1]);
                                }else if(response1[0] == 'phase1_voltage_status'){
                                    $("#phase1_voltage_status").val(response1[1]);
                                }else if(response1[0] == 'phase2_voltage_status'){
                                    $("#phase2_voltage_status").val(response1[1]);
                                }else if(response1[0] == 'phase3_voltage_status'){
                                    $("#phase3_voltage_status").val(response1[1]);
                                }else if(response1[0] == 'phase1_current_status'){
                                    $("#phase1_current_status").val(response1[1]);
                                }else if(response1[0] == 'phase2_current_status'){
                                    $("#phase2_current_status").val(response1[1]);
                                }else if(response1[0] == 'phase3_current_status'){
                                    $("#phase3_current_status").val(response1[1]);
                                }else if(response1[0] == 'juncHr'){
                                    $("#juncHr").val(response1[1]);
                                }else if(response1[0] == 'juncMin'){
                                    $("#juncMin").val(response1[1]);
                                }else if(response1[0] == 'juncDate'){
                                    $("#juncDate").val(response1[1]);
                                }else if(response1[0] == 'juncMonth'){
                                    $("#juncMonth").val(response1[1]);
                                }else if(response1[0] == 'juncYear'){
                                    $("#juncYear").val(response1[1]);
                                }else if(response1[0] == 'receivedData'){
                                    $("#receivedData").val(response1[1]);
                                }else if(response1[0] == 'sentData'){
                                    $("#sentData").val(response1[1]);
                                }
                            }
                        }
                    }
                });
                ChangeStatus();
                setTimeout("loadContents()", 1 * 1000);
            }

            function ChangeStatus() {
                var contactorOnStatus = document.getElementById("contactorOnStatus").value;
                if(contactorOnStatus==1){
                      $("#contactorStatusTd").html(document.getElementById("div_redSignal").innerHTML);
                 }else{
                    $("#contactorStatusTd").html(document.getElementById("div_graySignal").innerHTML);
                 }
                 try{
                    var dbConnectedLoad = parseInt($("#dbConnectedLoadtd").html());
                    var connectedLoad = parseInt($("#cltd").html());
                    var  context = $("#cltd").html();
                    if(dbConnectedLoad - connectedLoad > 5 || connectedLoad - dbConnectedLoad > 5) {
                           $("#cltd").html('<span style="color:red;">'+context+'</span>');
                    }else{
                         $("#cltd").html(context);
                    }
                 }catch(e){
                     alert(e);
                 }
//                $("#p1Hvtd").html(document.getElementById("phase1_healthy_voltage").value);
//                $("#p2Hvtd").html(document.getElementById("phase2_healthy_voltage").value);
//                $("#p3Hvtd").html(document.getElementById("phase3_healthy_voltage").value);
//                $("#p1Hctd").html(document.getElementById("phase1_healthy_current").value);
//                $("#p2Hctd").html(document.getElementById("phase2_healthy_current").innerHTML);
//                $("#p3Hctd").html(document.getElementById("phase3_healthy_current").innerHTML);
                $("#overAllStatustd").html(document.getElementById("overall_status").value);
                $("#p1Statustd").html(document.getElementById("phase1_status").value);
                $("#p2Statustd").html(document.getElementById("phase2_status").value);
                $("#p3Statustd").html(document.getElementById("phase3_status").value);
                $("#p1vcStatustd").html(document.getElementById("phase1_vc_status").value);
                $("#p2vcStatustd").html(document.getElementById("phase2_vc_status").value);
                $("#p3vcStatustd").html(document.getElementById("phase3_vc_status").value);
                $("#v1td").html(document.getElementById("voltage1").value);
                $("#v2td").html(document.getElementById("voltage2").value);
                $("#v3td").html(document.getElementById("voltage3").value);
                $("#c1td").html(document.getElementById("current1").value);
                $("#c2td").html(document.getElementById("current2").value);
                $("#c3td").html(document.getElementById("current3").value);
                $("#pftd").html(document.getElementById("power_factor").value);
                $("#cutd").html(document.getElementById("consumed_unit").value);
                $("#juncOnTimetd").html(document.getElementById("juncOnTimeHr").value+":"+document.getElementById("juncOnTimeMin").value);
                $("#juncOffTimetd").html(document.getElementById("juncOffTimeHr").value+":"+document.getElementById("juncOffTimeMin").value);

                $("#p1fuseIntd").html(document.getElementById("phase1_fuseIn").value);
                $("#p1fuseOuttd").html(document.getElementById("phase1_fuseOut").value);
                $("#p1contactorSwitchingtd").html(document.getElementById("phase1_contactor_switching").value);
                $("#p1coilStatustd").html(document.getElementById("phase1_coil_status").value);
                $("#p1mccbIntd").html(document.getElementById("phase1_mccbIn").value);
                $("#p1mccbOuttd").html(document.getElementById("phase1_mccbOut").value);
                $("#p1currentStatustd").html(document.getElementById("phase1_current_status").value);
                $("#p1voltageStatustd").html(document.getElementById("phase1_voltage_status").value);
                $("#p1activePowertd").html(document.getElementById("activePower1").value);
                $("#p2fuseIntd").html(document.getElementById("phase2_fuseIn").value);
                $("#p2fuseOuttd").html(document.getElementById("phase2_fuseOut").value);
                $("#p2contactorSwitchingtd").html(document.getElementById("phase2_contactor_switching").value);
                $("#p2coilStatustd").html(document.getElementById("phase2_coil_status").value);
                $("#p2mccbIntd").html(document.getElementById("phase2_mccbIn").value);
                $("#p2mccbOuttd").html(document.getElementById("phase2_mccbOut").value);
                $("#p2currentStatustd").html(document.getElementById("phase2_current_status").value);
                $("#p2voltageStatustd").html(document.getElementById("phase2_voltage_status").value);
                $("#p2activePowertd").html(document.getElementById("activePower2").value);
                $("#p3fuseIntd").html(document.getElementById("phase3_fuseIn").value);
                $("#p3fuseOuttd").html(document.getElementById("phase3_fuseOut").value);
                $("#p3contactorSwitchingtd").html(document.getElementById("phase3_contactor_switching").value);
                $("#p3coilStatustd").html(document.getElementById("phase3_coil_status").value);
                $("#p3mccbIntd").html(document.getElementById("phase3_mccbIn").value);
                $("#p3mccbOuttd").html(document.getElementById("phase3_mccbOut").value);
                $("#p3currentStatustd").html(document.getElementById("phase3_current_status").value);
                $("#p3voltageStatustd").html(document.getElementById("phase3_voltage_status").value);
                $("#p3activePowertd").html(document.getElementById("activePower3").value);

                $("#juncHr_td1").html(document.getElementById("juncHr").value);
                $("#juncHr_td2").html(document.getElementById("juncHr").value);
                $("#juncHr_td3").html(document.getElementById("juncHr").value);
                $("#juncMin_td1").html(document.getElementById("juncMin").value);
                $("#juncMin_td2").html(document.getElementById("juncMin").value);
                $("#juncMin_td3").html(document.getElementById("juncMin").value);
                $("#juncDate_td1").html(document.getElementById("juncDate").value);
                $("#juncDate_td2").html(document.getElementById("juncDate").value);
                $("#juncDate_td3").html(document.getElementById("juncDate").value);
                $("#juncMonth_td1").html(document.getElementById("juncMonth").value);
                $("#juncMonth_td2").html(document.getElementById("juncMonth").value);
                $("#juncMonth_td3").html(document.getElementById("juncMonth").value);
                $("#juncYear_td1").html(document.getElementById("juncYear").value);
                $("#juncYear_td2").html(document.getElementById("juncYear").value);
                $("#juncYear_td3").html(document.getElementById("juncYear").value);

                $("#receivedData_text").html(document.getElementById("receivedData").value);
                $("#sentData_text").html(document.getElementById("sentData").value);

            }

        </script>
        <title>Energy Meter Status</title>
    </head>
    <body onload="loadContents();changeStatus();">
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
            <tr>
                <td>
                    <div class="maindiv" id="body">
                        <table width="100%">
                            <tr>
                                <td  colspan="2" align="center">
                                    <div id="wrapper" align="center" >
                                        <div class="header_table" style="width: 980px">
                                            <div><h2>${junction_name} Meter Status</h2></div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr style="display: none">
                                <td align="center">Last Received bytes : </td>
                                <td id="receivedData_text">${receivedData}</td>
                            </tr>
                            <tr style="display: none">
                                <td align="center">Last Sent bytes : </td>
                                <td id="sentData_text">${sentData}></td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center" id="contactorStatusTd"></td>
                                <td align="center" id="date_time_td"></td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <div id="wrapper" align="center" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <div style="width: 980px">
                                            <table border="1"  id="table3" align="center" width="910" class="content" >
                                                <tr bgcolor="#92B8ED"><td colspan="8s" align="center"><b>${junction_name} Standard Detail</b></td></tr>
                                                <tr>
                                                    <th class="heading">Sanctioned Load</th>
                                                    <th class="heading">Connected Load</th>
                                                    <th class="heading">Phase1 Healthy Voltage</th>
                                                    <th class="heading">Phase2 Healthy Voltage</th>
                                                    <th class="heading">Phase3 Healthy Voltage</th>
                                                    <th class="heading">Phase1 Healthy Current</th>
                                                    <th class="heading">Phase2 Healthy Current</th>
                                                    <th class="heading">Phase3 Healthy Current</th>
                                                </tr>
                                                <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                    <td id="sancLoadtd" align="center">${sanctioned_load}</td>
                                                    <td id="dbConnectedLoadtd" align="center">${db_connected_load}</td>
                                                    <td id="p1Hvtd" align="center">${phase1_healthy_voltage}</td>
                                                    <td id="p2Hvtd" align="center">${phase2_healthy_voltage}</td>
                                                    <td id="p3Hvtd" align="center">${phase3_healthy_voltage}</td>
                                                    <td id="p1Hctd" align="center">${phase1_healthy_current}</td>
                                                    <td id="p2Hctd" align="center">${phase2_healthy_current}</td>
                                                    <td id="p3Hctd" align="center">${phase3_healthy_current}</td>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <div STYLE="overflow: auto; width: 990px;margin: 20px 0px;">
                                        <form action="" id="form1" name="form1" method="">
                                            <table border="1"  id="table1" align="center" width="910" class="content" >
                                                <tr bgcolor="#92B8ED"><td colspan="7" align="center"><b>Health Status At :- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span id="juncDate_td1">${juncDate}</span>- <span id="juncMonth_td1">${juncMonth}</span>- <span id="juncYear_td1">${juncYear}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span id="juncHr_td1">${juncHr}</span>: <span id="juncMin_td1">${juncMin}</span></b></td></tr>
                                                <tr>
                                                    <th class="heading">Over All Status</th>
                                                    <th class="heading">Phase 1 Status</th>
                                                    <th class="heading">Phase 1 VC Status</th>
                                                    <th class="heading">Phase 2 Status</th>
                                                    <th class="heading">Phase 2 VC Status</th>
                                                    <th class="heading">Phase 3 Status</th>
                                                    <th class="heading">Phase 3 VC Status</th>
                                                </tr>
                                                <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                    <td id="overAllStatustd" align="center">${overall_status}</td>
                                                    <td id="p1Statustd" align="center">${phase1_status}</td>
                                                    <td id="p1vcStatustd" align="center">${phase1_vc_status}</td>
                                                    <td id="p2Statustd" align="center">${phase2_status}</td>
                                                    <td id="p2vcStatustd" align="center">${phase2_vc_status}</td>
                                                    <td id="p3Statustd" align="center">${phase3_status}</td>
                                                    <td id="p3vcStatustd" align="center">${phase3_vc_status}</td>
                                                </tr>
                                            </table>
                                            <input type="hidden" name="searchJunction" value="${searchJunction}">
                                            <input type="hidden" name="searchReadingDateFrom" value="${searchReadingDateFrom}">
                                            <input type="hidden" name="searchReadingDateTo" value="${searchReadingDateTo}">
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <div STYLE="overflow: auto; width: 990px;margin: 20px 0px;">
                                        <form action="" id="form1" name="form1" method="">
                                            <table border="1"  id="table1" align="center" width="910" class="content" >
                                                <tr bgcolor="#92B8ED"><td colspan="10" align="center"><b>Health Status At :-  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span id="juncDate_td2">${juncDate}</span>- <span id="juncMonth_td2">${juncMonth}</span>- <span id="juncYear_td2">${juncYear}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span id="juncHr_td2">${juncHr}</span>: <span id="juncMin_td2">${juncMin}</span></b></td></tr>
                                                <tr>
                                                    <th class="heading">Phase</th>
                                                    <th class="heading">Fuse In</th>
                                                    <th class="heading">Fuse Out</th>
                                                    <th class="heading" style="white-space: normal">Contactor Switching</th>
                                                    <th class="heading">Coil Status</th>
                                                    <th class="heading">MCCB In</th>
                                                    <th class="heading">MCCB Out</th>
                                                    <th class="heading">Current Status</th>
                                                    <th class="heading">Voltage Status</th>
                                                    <th class="heading">Connected Load</th>
                                                </tr>
                                                <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                    <td >Phase1</td>
                                                    <td id="p1fuseIntd" align="center">${phase1_fuseIn}</td>
                                                    <td id="p1fuseOuttd" align="center">${phase1_fuseOut}</td>
                                                    <td id="p1contactorSwitchingtd" align="center">${phase1_contactor_switching}</td>
                                                    <td id="p1coilStatustd" align="center">${phase1_coil_status}</td>
                                                    <td id="p1mccbIntd" align="center">${phase1_mccbIn}</td>
                                                    <td id="p1mccbOuttd" align="center">${phase1_mccbOut}</td>
                                                    <td id="p1currentStatustd" align="center">${phase1_current_status}</td>
                                                    <td id="p1voltageStatustd" align="center">${phase1_voltage_status}</td>
                                                    <td id="p1activePowertd" align="center">${activePower1}</td>
                                                </tr>
                                                <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                    <td >Phase2</td>
                                                    <td id="p2fuseIntd" align="center">${phase2_fuseIn}</td>
                                                    <td id="p2fuseOuttd" align="center">${phase2_fuseOut}</td>
                                                    <td id="p2contactorSwitchingtd" align="center">${phase2_contactor_switching}</td>
                                                    <td id="p2coilStatustd" align="center">${phase2_coil_status}</td>
                                                    <td id="p2mccbIntd" align="center">${phase2_mccbIn}</td>
                                                    <td id="p2mccbOuttd" align="center">${phase2_mccbOut}</td>
                                                    <td id="p2currentStatustd" align="center">${phase2_current_status}</td>
                                                    <td id="p2voltageStatustd" align="center">${phase2_voltage_status}</td>
                                                    <td id="p2activePowertd" align="center">${activePower2}</td>
                                                </tr>
                                                <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                    <td >Phase3</td>
                                                    <td id="p3fuseIntd" align="center">${phase3_fuseIn}</td>
                                                    <td id="p3fuseOuttd" align="center">${phase3_fuseOut}</td>
                                                    <td id="p3contactorSwitchingtd" align="center">${phase3_contactor_switching}</td>
                                                    <td id="p3coilStatustd" align="center">${phase3_coil_status}</td>
                                                    <td id="p3mccbIntd" align="center">${phase3_mccbIn}</td>
                                                    <td id="p3mccbOuttd" align="center">${phase3_mccbOut}</td>
                                                    <td id="p3currentStatustd" align="center">${phase3_current_status}</td>
                                                    <td id="p3voltageStatustd" align="center">${phase3_voltage_status}</td>
                                                    <td id="p3activePowertd" align="center">${activePower3}</td>
                                                </tr>
                                            </table>
                                            <input type="hidden" name="searchJunction" value="${searchJunction}">
                                            <input type="hidden" name="searchReadingDateFrom" value="${searchReadingDateFrom}">
                                            <input type="hidden" name="searchReadingDateTo" value="${searchReadingDateTo}">
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" align="center">
                                    <div STYLE="overflow: auto; width: 990px;margin: 20px 0px;">
                                        <form action="" id="form2" name="form2" method="">
                                            <table border="1"  id="table2" align="center" width="910" class="content" >
                                                <tr bgcolor="#92B8ED"><td colspan="11" align="center"><b>Meter Readings  AT :-  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span id="juncDate_td3">${juncDate}</span>- <span id="juncMonth_td3">${juncMonth}</span>- <span id="juncYear_td3">${juncYear}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                            <span id="juncHr_td3">${juncHr}</span>: <span id="juncMin_td3">${juncMin}</span></b></td></tr>
                                                <tr>
                                                    <th class="heading">Voltage1</th>
                                                    <th class="heading">Voltage2</th>
                                                    <th class="heading">Voltage3</th>
                                                    <th class="heading">Current1</th>
                                                    <th class="heading">Current2</th>
                                                    <th class="heading">Current3</th>
                                                    <th class="heading">Power Factor</th>
                                                    <th class="heading">Consumed Unit</th>
                                                    <th class="heading">Connected Load</th>
                                                    <th class="heading">Junction On TIme</th>
                                                    <th class="heading">Junction Off TIme</th>
                                                </tr>
                                                <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                    <td id="v1td" align="center">${voltage1}</td>
                                                    <td id="v2td" align="center">${voltage2}</td>
                                                    <td id="v3td" align="center">${voltage3}</td>
                                                    <td id="c1td" align="center">${current1}</td>
                                                    <td id="c2td" align="center">${current2}</td>
                                                    <td id="c3td" align="center">${current3}</td>
                                                    <td id="pftd" align="center">${power_factor}</td>
                                                    <td id="cutd" align="center">${consumed_unit}</td>
                                                    <td id="cltd" align="center">${connected_load}</td>
                                                    <td id="juncOnTimetd" align="center">${juncOnTimeHr} : ${juncOnTimeMin}</td>
                                                    <td id="juncOffTimetd" align="center">${juncOffTimeHr} : ${juncOffTimeMin}</td>
                                                </tr>
                                            </table>
                                            <input type="hidden" name="searchJunction" value="${searchJunction}">
                                            <input type="hidden" name="searchReadingDateFrom" value="${searchReadingDateFrom}">
                                            <input type="hidden" name="searchReadingDateTo" value="${searchReadingDateTo}">
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <input type="hidden" id="phase1_fuseIn" name="phase1_fuseIn" value="${phase1_fuseIn}">
                            <input type="hidden" id="phase1_fuseOut" name="phase1_fuseOut" value="${phase1_fuseOut}">
                            <input type="hidden" id="phase1_contactor_switching" name="phase1_contactor_switching" value="${phase1_contactor_switching}">
                            <input type="hidden" id="phase1_coil_status" name="phase1_coil_status" value="${phase1_coil_status}">
                            <input type="hidden" id="phase1_mccbIn" name="phase1_mccbIn" value="${phase1_mccbIn}">
                            <input type="hidden" id="phase1_mccbOut" name="phase1_mccbOut" value="${phase1_mccbOut}">
                            <input type="hidden" id="phase1_voltage_status" name="phase1_voltage_status" value="${phase1_voltage_status}">
                            <input type="hidden" id="phase1_current_status" name="phase1_current_status" value="${phase1_current_status}">
                            <input type="hidden" id="phase2_fuseIn" name="phase2_fuseIn" value="${phase2_fuseIn}">
                            <input type="hidden" id="phase2_fuseOut" name="phase2_fuseOut" value="${phase2_fuseOut}">
                            <input type="hidden" id="phase2_contactor_switching" name="phase2_contactor_switching" value="${phase2_contactor_switching}">
                            <input type="hidden" id="phase2_coil_status" name="phase2_coil_status" value="${phase2_coil_status}">
                            <input type="hidden" id="phase2_mccbIn" name="phase2_mccbIn" value="${phase2_mccbIn}">
                            <input type="hidden" id="phase2_mccbOut" name="phase2_mccbOut" value="${phase2_mccbOut}">
                            <input type="hidden" id="phase2_voltage_status" name="phase2_voltage_status" value="${phase2_voltage_status}">
                            <input type="hidden" id="phase2_current_status" name="phase2_current_status" value="${phase2_current_status}">
                            <input type="hidden" id="phase3_fuseIn" name="phase3_fuseIn" value="${phase3_fuseIn}">
                            <input type="hidden" id="phase3_fuseOut" name="phase3_fuseOut" value="${phase3_fuseOut}">
                            <input type="hidden" id="phase3_contactor_switching" name="phase3_contactor_switching" value="${phase3_contactor_switching}">
                            <input type="hidden" id="phase3_coil_status" name="phase3_coil_status" value="${phase3_coil_status}">
                            <input type="hidden" id="phase3_mccbIn" name="phase3_mccbIn" value="${phase3_mccbIn}">
                            <input type="hidden" id="phase3_mccbOut" name="phase3_mccbOut" value="${phase3_mccbOut}">
                            <input type="hidden" id="phase3_voltage_status" name="phase3_voltage_status" value="${phase3_voltage_status}">
                            <input type="hidden" id="phase3_current_status" name="phase3_current_status" value="${phase3_current_status}">
                            <input type="hidden" id="juncHr" name="juncHr" value="${juncHr}">
                            <input type="hidden" id="juncMin" name="juncMin" value="${juncMin}">
                            <input type="hidden" id="juncDate" name="juncDat" value="${juncDat}">
                            <input type="hidden" id="juncMonth" name="juncMonth" value="${juncMonth}">
                            <input type="hidden" id="juncYear" name="juncYear" value="${juncYear}">
                            <input type="hidden" id="juncOnTimeHr" name="juncOnTimeHr" value="${juncOnTimeHr}">
                            <input type="hidden" id="juncOnTimeMin" name="juncOnTimeMin" value="${juncOnTimeMin}">
                            <input type="hidden" id="juncOffTimeHr" name="juncOffTimeHr" value="${juncOffTimeHr}">
                            <input type="hidden" id="juncOffTimeMin" name="juncOffTimeMin" value="${juncOffTimeMin}">
                            <input type="hidden" id="contactorOnStatus" name="contactorOnStatus" value="${contactorOnStatus}">
                            <input type="hidden" id="health_status_time" name="health_status_time" value="${health_status_time}">
                            <input type="hidden" id="health_status_date" name="health_status_date" value="${health_status_date}">
                            <input type="hidden" id="phase3_vc_status" name="phase3_vc_status" value="${phase3_vc_status}">
                            <input type="hidden" id="phase2_vc_status" name="phase2_vc_status" value="${phase2_vc_status}">
                            <input type="hidden" id="phase1_vc_status" name="phase1_vc_status" value="${phase1_vc_status}">
                            <input type="hidden" id="overall_status" name="overall_status" value="${overall_status}">
                            <input type="hidden" id="phase1_status" name="phase1_status" value="${phase1_status}">
                            <input type="hidden" id="phase2_status" name="phase2_status" value="${phase2_status}">
                            <input type="hidden" id="phase3_status" name="phase3_status" value="${phase3_status}">
                            <input type="hidden" id="reading_time" name="reading_time" value="${reading_time}">
                            <input type="hidden" id="reading_date" name="reading_date" value="${reading_date}">
                            <input type="hidden" id="current_unit" name="current_unit" value="${current_unit}">
                            <input type="hidden" id="ivrs" name="ivrs" value="${ivrs}">
                            <input type="hidden" id="voltage_unit" name="voltage_unit" value="${voltage_unit}">
                            <input type="hidden" id="type_of_premises" name="type_of_premises" value="${type_of_premises}">
                            <input type="hidden" id="junction_name" name="junction_name" value="${junction_name}">
                            <input type="hidden" id="no_of_poles" name="no_of_poles" value="${no_of_poles}">
                            <input type="hidden" id="no_of_phases" name="no_of_phases" value="${no_of_phases}">
                            <input type="hidden" id="phase3_healthy_current" name="phase3_healthy_current" value="${phase3_healthy_current}">
                            <input type="hidden" id="phase2_healthy_current" name="phase2_healthy_current" value="${phase2_healthy_current}">
                            <input type="hidden" id="phase1_healthy_current" name="phase1_healthy_current" value="${phase1_healthy_current}">
                            <input type="hidden" id="phase3_healthy_voltage" name="phase3_healthy_voltage" value="${phase3_healthy_voltage}">
                            <input type="hidden" id="phase2_healthy_voltage" name="phase2_healthy_voltage" value="${phase2_healthy_voltage}">
                            <input type="hidden" id="phase1_healthy_voltage" name="phase1_healthy_voltage" value="${phase1_healthy_voltage}">
                            <input type="hidden" id="sanctioned_load" name="sanctioned_load" value="${sanctioned_load}">
                            <input type="hidden" id="connected_load" name="connected_load" value="${connected_load}">
                            <input type="hidden" id="consumed_unit" name="consumed_unit" value="${consumed_unit}">
                            <input type="hidden" id="power_factor" name="power_factor" value="${power_factor}">
                            <input type="hidden" id="activePower3" name="activePower3" value="${activePower3}">
                            <input type="hidden" id="activePower2" name="activePower2" value="${activePower2}">
                            <input type="hidden" id="activePower1" name="activePower1" value="${activePower1}">
                            <input type="hidden" id="current3" name="current3" value="${current3}">
                            <input type="hidden" id="current2" name="current2" value="${current2}">
                            <input type="hidden" id="current1" name="current1" value="${current1}">
                            <input type="hidden" id="voltage3" name="voltage3" value="${voltage3}">
                            <input type="hidden" id="voltage2" name="voltage2" value="${voltage2}">
                            <input type="hidden" id="voltage1" name="voltage1" value="${voltage1}">
                            <input type="hidden" id="program_version_no" name="program_version_no" value="${program_version_no}">
                            <input type="hidden" id="junction_id" name="junction_id" value="${junction_id}">
                            <input type="hidden" id="switching_rev_no" name="switching_rev_no" value="${switching_rev_no}">
                            <input type="hidden" id="switching_point_detail_id" name="switching_point_detail_id" value="${switching_point_detail_id}">
                            <input type="hidden" id="meter_readings_id" name="meter_readings_id" value="${meter_readings_id}">

                            <input type="hidden" id="receivedData" value="${receivedData}">
                            <input type="hidden" id="sentData" value="${sentData}">

                            <div id="div_signals" style="visibility: hidden;border: 1px solid red">
                                <div id="div_redSignal" style="position: absolute;"> <img src="./images/red_light.png" width="30" height="30"></div>
                                <div id="div_graySignal" style="position: absolute;"><img src="./images/grey.png" width="30" height="25"></div>
                                <div id="div_animationLoading" style="position: absolute;"><img src="./images/animated.gif" width="30" height="30"></div>
                            </div>
                        </table>
                    </div>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
