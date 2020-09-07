<%-- 
    Document   : junctionView
    Created on : Apr 9, 2015, 12:59:44 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<script type="text/javascript" language="javascript">
    jQuery(function(){
        $("#ivrs_no").autocomplete("junctionCont.do", {
            extraParams: {
                action1: function() { return "getIvrs_no"}
            }
        });

        $("#searchJunctionName").autocomplete("junctionCont.do", {
            extraParams: {
                action1: function() { return "getJunctionName"}
            }
        });

        $("#searchIvrsNo").autocomplete("junctionCont.do", {
            extraParams: {
                action1: function() { return "getIvrsNo"}
            }
        });
        $("#junction_name").autocomplete("junctionCont.do", {
            extraParams: {
                action1: function() { return "getJunctionName1"}
            }
        });
               
    });
    

    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise') {

            var ivrs_no = document.getElementById("ivrs_no").value;
            var mobile_no = document.getElementById("mobile_no").value;
            var sim_no = document.getElementById("sim_no").value;
            var imei_no = document.getElementById("imei_no").value;
            var sanctioned_load = document.getElementById("sanctioned_load").value;
            var connected_load = document.getElementById("connected_load").value;
            var phase1_healthy_voltage = document.getElementById("phase1_healthy_voltage").value;
            var phase2_healthy_voltage = document.getElementById("phase2_healthy_voltage").value;
            var phase3_healthy_voltage = document.getElementById("phase3_healthy_voltage").value;
            var phase1_healthy_current = document.getElementById("phase1_healthy_current").value;
            var phase2_healthy_current = document.getElementById("phase2_healthy_current").value;
            var phase3_healthy_current = document.getElementById("phase3_healthy_current").value;
            
            if(ivrs_no == '' || ivrs_no == '0') {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>IVRS No is require...</b></td>");
                document.getElementById("ivrs_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(mobile_no == '' || mobile_no == '0') {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Mobile No is require...</b></td>");
                document.getElementById("mobile_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(sim_no == '' || sim_no == '0') {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>SIM No is require...</b></td>");
                document.getElementById("sim_no").focus();
                return false; // code to stop from submitting the form2.
            }
            if(imei_no == '' || imei_no == '0') {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>IMEI No is require...</b></td>");
                document.getElementById("imei_no").focus();
                return false; // code to stop from submitting the form2.
            }

            var regex = /^[0-9]\d*(\.\d*)?$/;
            if(!sanctioned_load.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Sanctioned Load must be numeric...</b></td>");
                document.getElementById("sanctioned_load").focus();
                return false; // code to stop from submitting the form2.
            }
            if(!connected_load.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Connected Load must be numeric...</b></td>");
                document.getElementById("connected_load").focus();
                return false; // code to stop from submitting the form2.
            }
            if(!phase1_healthy_voltage.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Phase1 Healthy Voltage must be numeric...</b></td>");
                document.getElementById("phase1_healthy_voltage").focus();
                return false; // code to stop from submitting the form2.
            }
            if(!phase2_healthy_voltage.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Phase2 Healthy Voltage must be numeric...</b></td>");
                document.getElementById("phase2_healthy_voltage").focus();
                return false; // code to stop from submitting the form2.
            }
            if(!phase3_healthy_voltage.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Phase3 Healthy Voltage be numeric...</b></td>");
                document.getElementById("phase3_healthy_voltage").focus();
                return false; // code to stop from submitting the form2.
            }
            if(!phase1_healthy_current.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Phase1 Healthy Current must be numeric...</b></td>");
                document.getElementById("phase1_healthy_current").focus();
                return false; // code to stop from submitting the form2.
            }
            if(!phase2_healthy_current.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Phase2 Healthy Current must be numeric...</b></td>");
                document.getElementById("phase2_healthy_current").focus();
                return false; // code to stop from submitting the form2.
            }
            if(!phase3_healthy_current.match(regex)) {
                $("#message").html("<td colspan='8' bgcolor='coral'><b>Phase3 Healthy Current must be numeric...</b></td>");
                document.getElementById("phase3_healthy_current").focus();
                return false; // code to stop from submitting the form2.
            }

            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }
            if(document.getElementById("clickedButton").value == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?");
                return result;
            }else if(document.getElementById("clickedButton").value == 'Revise'){
                result = confirm("Are you sure you want to Revise this record?");
                return result;
            }
        } else result = confirm("Are you sure you want to Cancel this record?");
        return result;
    }


     function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 26;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit;
        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("junction_id").value= document.getElementById(t1id + (lowerLimit + 1)).innerHTML;
        document.getElementById("program_version_no").value= document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
        document.getElementById("junction_name").value= document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("ivrs_no").value= document.getElementById(t1id + (lowerLimit + 7)).innerHTML;
        document.getElementById("phase").value= document.getElementById(t1id + (lowerLimit + 8)).innerHTML;
        document.getElementById("controller_model").value= document.getElementById(t1id + (lowerLimit + 9)).innerHTML;
        document.getElementById("mobile_no").value= document.getElementById(t1id + (lowerLimit + 10)).innerHTML;
        document.getElementById("sim_no").value= document.getElementById(t1id + (lowerLimit + 11)).innerHTML;
        document.getElementById("imei_no").value= document.getElementById(t1id + (lowerLimit + 12)).innerHTML;
        document.getElementById("panel_file_no").value= document.getElementById(t1id + (lowerLimit + 13)).innerHTML;
        document.getElementById("energy_meter_no").value= document.getElementById(t1id + (lowerLimit + 15)).innerHTML;
        document.getElementById("sanctioned_load").value= document.getElementById(t1id + (lowerLimit + 16)).innerHTML;
        document.getElementById("connected_load").value= document.getElementById(t1id + (lowerLimit + 17)).innerHTML;
        document.getElementById("phase1_healthy_voltage").value= document.getElementById(t1id + (lowerLimit + 18)).innerHTML;
        document.getElementById("phase2_healthy_voltage").value= document.getElementById(t1id + (lowerLimit + 19)).innerHTML;
        document.getElementById("phase3_healthy_voltage").value= document.getElementById(t1id + (lowerLimit + 20)).innerHTML;
        document.getElementById("phase1_healthy_current").value= document.getElementById(t1id + (lowerLimit + 21)).innerHTML;
        document.getElementById("phase2_healthy_current").value= document.getElementById(t1id + (lowerLimit + 22)).innerHTML;
        document.getElementById("phase3_healthy_current").value= document.getElementById(t1id + (lowerLimit + 23)).innerHTML;
        document.getElementById("remark").value= document.getElementById(t1id + (lowerLimit + 24)).innerHTML;
        var active = document.getElementById(t1id + (lowerLimit + 25)).innerHTML;
        document.getElementById("active").value = active;

        var phase = document.getElementById("phase").value;
        if(phase >1 && phase <=3 ){
                              if(phase == 2){
                                  document.getElementById("phase2_healthy_voltage").disabled = false;
                                  document.getElementById("phase2_healthy_current").disabled = false;
                              }else{
                                  document.getElementById("phase2_healthy_voltage").disabled = false;
                                  document.getElementById("phase3_healthy_voltage").disabled = false;
                                  document.getElementById("phase2_healthy_current").disabled = false;
                                  document.getElementById("phase3_healthy_current").disabled = false;
                              }
                          }
        
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        makeEditable('');
        document.getElementById("revise").disabled = false;
            document.getElementById("save_As").disabled = false;
            document.getElementById("cancel").disabled = false;
            document.getElementById("save").disabled = true;
        $("#message").html("");
        if(document.getElementById("active").value == 'No'){
            document.getElementById("save_As").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("revise").disabled = true;
        }
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }

     function makeEditable(id) {
        document.getElementById("ivrs_no").disabled = false;
        document.getElementById("junction_name").disabled = false;
        document.getElementById("controller_model").disabled = false;
        document.getElementById("mobile_no").disabled = false;
        document.getElementById("sim_no").disabled = false;
        document.getElementById("imei_no").disabled = false;
        document.getElementById("panel_file_no").disabled = false;
        document.getElementById("energy_meter_no").disabled = false;
        document.getElementById("sanctioned_load").disabled = false;
        document.getElementById("connected_load").disabled = false;
        document.getElementById("phase1_healthy_voltage").disabled = false;
        //document.getElementById("phase2_healthy_voltage").disabled = false;
        //document.getElementById("phase3_healthy_voltage").disabled = false;
        document.getElementById("phase1_healthy_current").disabled = false;
        //document.getElementById("phase2_healthy_current").disabled = false;
        //document.getElementById("phase3_healthy_current").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;

        if(id == 'new') {            
            $("#message").html("");
            document.getElementById("revise").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("ivrs_no").focus();
            //document.getElementById("revised").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 26);
            //document.getElementById("state").focus();

        }
        if(id == 'revise'){
            document.getElementById("save").disabled = true;
            document.getElementById("save_As").disabled = false;
            document.getElementById("cancel").disabled = false;            
        }

    }

    function setStatus(id) {

      if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else if(id == 'revise'){
            document.getElementById("clickedButton").value = "Revise";
        }else document.getElementById("clickedButton").value = "Cancel";
    }

    function myLeftTrim(str) {
        var beginIndex = 0;
        for(var i = 0; i < str.length; i++) {
            if(str.charAt(i) == ' ')
                beginIndex++;
            else break;
        }
        return str.substring(beginIndex, str.length);
    }





    function getPhase(){
        var ivrs_no = document.getElementById("ivrs_no").value;
        //alert(ivrs_no);

        $.ajax({url: "junctionCont.do", data: "action1=getPhase&ivrs_no="
                           +ivrs_no, success: function(response_data) {
                          // alert(response_data);
                          document.getElementById("phase").value=response_data;
                          if(response_data >1 && response_data <=3 ){
                              if(response_data == 2){
                                  document.getElementById("phase2_healthy_voltage").disabled = false;
                                  document.getElementById("phase2_healthy_current").disabled = false;
                              }else{
                                  document.getElementById("phase2_healthy_voltage").disabled = false;
                                  document.getElementById("phase3_healthy_voltage").disabled = false;
                                  document.getElementById("phase2_healthy_current").disabled = false;
                                  document.getElementById("phase3_healthy_current").disabled = false;
                              }
                          }
                       }
                   });

    }


    function displayJunctionList(){
                var searchJunctionName = document.getElementById("searchJunctionName").value;
                var searchIvrsNo = document.getElementById("searchIvrsNo").value;
                var queryString = "task=generateJunctionReport&searchJunctionName="+searchJunctionName+"&searchIvrsNo="+searchIvrsNo;
                var url = "junctionCont.do?" + queryString;
                popupwin = openPopUp(url, "Junction List", 600, 900);
            }


            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Junction Detail</title>
    </head>
    <body>
          <table align="center" cellpadding="0" cellspacing="0" class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
            <%-- <tr>
                    <td width="50" height="600" valign="top"><%@include file="/view/layout/Leftmenu.jsp" %></td></tr> --%>
            <td>
                <DIV id="body" style="width:95%" class="maindiv" align="center" >
                    <table width="95%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%"><b>Junction Detail</b></td>
                                      <td><%@include file="/layout/org_menu.jsp" %></td>
                                        <%-- <td>
                                             <%@include file="/view/layout/org_menu.jsp" %>
                                         </td> --%>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="get" action="junctionCont.do">
                                        <table align="center" class="heading1" width="">
                                           <tr>
                                                <th class="heading1">Junction Name</th>
                                                <td>
                                                    <input class="input" type="hidden" id="s_n" name="s_n" value="" >
                                                    <input class="input" type="text" id="searchJunctionName" name="searchJunctionName" value="${searchJunctionName}" >
                                                </td>
                                                <th class="heading1">IVRS NO</th>
                                                <td>
                                                    <input class="input" type="text" id="searchIvrsNo" name="searchIvrsNo" value="${searchIvrsNo}" >
                                                </td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayJunctionList()">
                                           </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="get" action="junctionCont.do">
                                    <DIV id="content_div" class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">Junction ID</th>
                                                <th class="heading">Program Version no.</th>
                                                <th class="heading">Junction Name</th>
                                                <th class="heading">Address 1</th>
                                                <th class="heading">Address 2</th>
                                                <th class="heading">City</th>
                                                <th class="heading">IVRS No</th>
                                                <th class="heading">phase</th>
                                                <th class="heading">Controller Model</th>
                                                <th class="heading">Mobile No</th>
                                                <th class="heading">SIM No</th>
                                                <th class="heading">IMEI no</th>
                                                <th class="heading">Panel File No</th>
                                                <th class="heading">Panel Transferred Status</th>
                                                <th class="heading">Energy Meter No</th>
                                                <th class="heading">Sanctioned Load</th>
                                                <th class="heading">Connected Load</th>
                                                <th class="heading">Phase1 Healthy Voltage</th>
                                                <th class="heading">Phase2 HealthY Voltage</th>
                                                <th class="heading">phase3 Healthy Voltage</th>
                                                <th class="heading">phase1 Healthy Current</th>
                                                <th class="heading">phase2 Healthy Current</th>
                                                <th class="heading">phase3 Healthy Current</th>
                                                <th class="heading">Remark</th>
                                                <th class="heading">Active</th>
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from taxTypeList of TaxController     --->

                                                 <c:forEach var="junctionBean" items="${requestScope['junctionList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="" onclick="fillColumns(id)">${junctionBean.junction_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" style="" onclick="fillColumns(id)">${junctionBean.program_version_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.switching_point_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.road}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.area} ${junctionBean.ward}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.city}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.ivrs_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.controller_model}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.mobile_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.sim_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.imei_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.panel_file_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.panel_transferred_status}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.energy_meter_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.sanctioned_load}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.connected_load}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.phase1_healthy_voltage}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.phase2_healthy_voltage}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.phase3_healthy_voltage}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.phase1_healthy_current}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.phase2_healthy_current}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.phase3_healthy_current}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${junctionBean.active}</td>

                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='left' colspan="23">
                                                    <c:choose>
                                                        <c:when test="${showFirst eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='First'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showPrevious == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Previous' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Previous'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showNext eq 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Next' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Next'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <c:choose>
                                                        <c:when test="${showLast == 'false'}">
                                                            <input class="button" type='submit' name='buttonAction' value='Last' disabled>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <input class="button" type='submit' name='buttonAction' value='Last'>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </tr>
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <%--<input  type="hidden" id="searchDownloaded_from" name="searchIs_bill_old" value="${searchDownloaded_from}" >
                                            <input  type="hidden" id="searchOperating_system" name="searchIs_bill_old" value="${searchOperating_system}" >
                                            <input  type="hidden" id="searchIs_bill_old" name="searchIs_bill_old" value="${searchIs_bill_old}" >--%>
                                        </table></DIV>
                                        <div id="map_container" title="Location Map">
                                        <div id="map_canvas" style="width:100%;height:100%;"></div>
                                    </div>
                                </form>
                            </td>
                        </tr>
                         <tr id="message">
                                <c:if test="${not empty message}">
                                    <td  bgcolor="${msgBgColor}"><b></b></td>
                                </c:if>
                            </tr>
                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="get" action="junctionCont.do" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">IVRS no</th>
                                                <td>
                                                    <input class="input" type="hidden" id="junction_id" name="junction_id" value="" >
                                                    <input class="input" type="hidden" id="program_version_no" name="program_version_no" value="" >
                                                    <input class="input" id="ivrs_no" name="ivrs_no" value="" size="20" disabled="" type="text" onchange="getPhase()" onblur="getPhase()">
                                                </td>
                                                <th class="heading1">Phase</th>
                                                <td>
                                                   <input class="input" id="phase" name="phase" value="" size="20" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Controller Model</th>
                                                <td>
                                                   <input class="input" id="controller_model" name="controller_model" value="" size="20" disabled="" type="text">
                                                </td>
                                                <!--  <input class="input" type="text" id="fuse" name="fuse" value="" size="10" disabled></td>-->
                                                                                              
                                            </tr>
                                            <tr>
                                                 <th class="heading1">Mobile No</th>
                                                <td>
                                                    <input class="input" id="mobile_no" name="mobile_no" value="" size="20" maxlength="10" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Sim No</th>
                                                <td>
                                                    <input class="input" id="sim_no" name="sim_no" value="0" size="20" maxlength="25" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Imei No</th>
                                                <td>
                                                    <input class="input" id="imei_no" name="imei_no" value="" size="20" maxlength="15" disabled="" type="text">
                                                </td>                                                                                                
                                            </tr>
                                            <tr>
                                                <th class="heading1">Panel File No</th>
                                                <td colspan="">
                                                    <input class="input" id="panel_file_no" name="panel_file_no" value="" size="20" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Energy Meter No</th>
                                                <td>
                                                    <input class="input" id="energy_meter_no" name="energy_meter_no" value="" size="20" maxlength="8" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Sanctioned Load</th>
                                                <td>
                                                    <input class="input" id="sanctioned_load" name="sanctioned_load" value="0" size="20" maxlength="15" disabled="" type="text">
                                                </td>                                                                                               
                                            </tr>
                                            <tr>
                                                <th class="heading1">Connected Load</th>
                                                <td colspan="">
                                                    <input class="input" id="connected_load" name="connected_load" value="0" size="20" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Phase1 Healthy Voltage</th>
                                                <td>
                                                    <input class="input" id="phase1_healthy_voltage" name="phase1_healthy_voltage" value="0" size="20" maxlength="25" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Phase2 Healthy Voltage</th>
                                                <td>
                                                    <input class="input" id="phase2_healthy_voltage" name="phase2_healthy_voltage" value="0" size="20" maxlength="15" disabled="" type="text">
                                                </td>                                                
                                            </tr>
                                            <tr>
                                                <th class="heading1">Phase3 Healthy Voltage</th>
                                                <td colspan="">
                                                    <input class="input" id="phase3_healthy_voltage" name="phase3_healthy_voltage" value="0" size="20" disabled="" type="text">
                                                </td>

                                                 <th class="heading1">Phase1 Healthy Current</th>
                                                <td>
                                                    <input class="input" id="phase1_healthy_current" name="phase1_healthy_current" value="0" size="20" maxlength="25" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Phase2 Healthy Current</th>
                                                <td>
                                                    <input class="input" id="phase2_healthy_current" name="phase2_healthy_current" value="0" size="20" maxlength="15" disabled="" type="text">
                                                </td>                                                
                                            </tr>
                                            <tr>
                                                <th class="heading1">Phase3 Healthy Current</th>
                                                <td colspan="">
                                                    <input class="input" id="phase3_healthy_current" name="phase3_healthy_current" value="0" size="20" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Remark</th>
                                                <td>
                                                    <input class="input" id="remark" name="remark" value="" size="20" maxlength="25" disabled="" type="text">
                                                </td>
                                                <th class="heading1">Junction Name</th>
                                                <td colspan="">
                                                    <input class="input" id="junction_name" name="junction_name" value="0" size="20" disabled="" type="text">
                                                </td>

                                            </tr>
                                            <tr>
                                                <td align='center' colspan="8">
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchStatusType" value="${search}" >
                                            <input type="hidden" id="active" name="active" value="" >
                                        </table>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </table>

                </DIV>
            </td>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>
