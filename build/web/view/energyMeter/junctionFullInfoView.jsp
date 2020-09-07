<%-- 
    Document   : healthStatusMapView
    Created on : Apr 6, 2015, 2:43:33 PM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" language="javascript">
    var popupwin;
    jQuery(function(){
        $("#searchJunction").autocomplete("meterReadingCont.do", {
                    extraParams: {
                        action1: function() { return "getJunctionName"}
                    }
        });
        $("#searchSwitchingPointName").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getSwitchingPointName"}
            }
        });

        $("#searchHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP1HealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP2HealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP3HealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP1vcHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP2vcHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });
        $("#searchP3vcHealthStatus").autocomplete("healthStatusMapCont.do", {
            extraParams: {
                action1: function() { return "getHealthStatus"}
            }
        });

        $("#searchReadingDateFrom").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });
        $("#searchReadingDateTo").datepicker({
            minDate: -100,
            showOn: "both",
            buttonImage: "images/calender.png",
            dateFormat: 'dd-mm-yy',
            buttonImageOnly: true,
            changeMonth: true,
            changeYear: true
        });             
    });
    $(document).ready(function() {
    // run the first time; all subsequent calls will take care of themselves
        setTimeout(worker, 15000);
    });
    function worker() {
        $.ajax({url: "junctionDetailCont.do?searchJunction="+ $("#searchJunction").val(), data: "action1=getJunctionData&q=", dataType: 'json', success: function(response_data) {
                    $("junction_id").val(response_data.junction_id);
                    $("#switching_point_name").val(response_data.switching_point_name);
                    $("#voltage1").val(response_data.voltage1);
                    $("#voltage2").val(response_data.voltage2);
                    $("#voltage3").val(response_data.voltage3);
                    $("#current1").val(response_data.current1);
                    $("#current2").val(response_data.current2);
                    $("#current3").val(response_data.current3);
                    $("#connected_load").val(response_data.connected_load);
                    $("#power_factor").val(response_data.power_factor);
                    $("#consumed_unit").val(response_data.consumed_unit);
                    $("#reading_date").val(response_data.reading_date);
                    $("#reading_time").val(response_data.reading_time);
                    $("#on_time_hr").val(response_data.on_time_hr);
                    $("#on_time_min").val(response_data.on_time_min);
                    $("#off_time_hr").val(response_data.off_time_hr);
                    $("#off_time_min").val(response_data.off_time_min);

                    $("#phase1_status").val(response_data.phase1_status);
                    $("#phase2_status").val(response_data.phase2_status);
                    $("#phase3_status").val(response_data.phase3_status);

                    $("#fuse1_in").val(response_data.fuse1_in);
                    if(response_data.fuse1_in == 'Fault')
                        $("#fuse1_in").css('border-color', 'red');
                    else
                        $("#fuse1_in").css('border-color', '');

                    $("#fuse1_out").val(response_data.fuse1_out);
                    if(response_data.fuse1_out == 'Fault')
                        $("#fuse1_out").css('border-color', 'red');
                    else
                        $("#fuse1_out").css('border-color', '');

                    $("#contactor1_status").val(response_data.contactor1_status);
                    if(response_data.contactor1_status == 'ON')
                        $("#contactor1_light").attr('src', './images/red_light.png')
                    else
                        $("#contactor1_light").attr('src', './images/grey.png');
                    $("#contactor1_command").val(response_data.contactor1_command);
                    $("#mccb1_in").val(response_data.mccb1_in);
                    if(response_data.mccb1_in == 'Fault')
                        $("#mccb1_in").css('border-color', 'red');
                    else
                        $("#mccb1_in").css('border-color', '');

                    $("#mccb1_out").val(response_data.mccb1_out);
                    if(response_data.mccb1_out == 'Fault')
                        $("#mccb1_out").css('border-color', 'red');
                    else
                        $("#mccb1_out").css('border-color', '');

                    $("#fuse2_in").val(response_data.fuse2_in);
                    if(response_data.fuse2_in == 'Fault')
                        $("#fuse2_in").css('border-color', 'red');
                    else
                        $("#fuse2_in").css('border-color', '');

                    $("#fuse2_out").val(response_data.fuse2_out);
                    if(response_data.fuse2_out == 'Fault')
                        $("#fuse2_out").css('border-color', 'red');
                    else
                        $("#fuse2_out").css('border-color', '');

                    $("#contactor2_status").val(response_data.contactor2_status);
                    if(response_data.contactor2_status == 'ON')
                        $("#contactor2_light").attr('src', './images/red_light.png')
                    else
                        $("#contactor2_light").attr('src', './images/grey.png');
                    $("#contactor2_command").val(response_data.contactor2_command);
                    $("#mccb2_in").val(response_data.mccb2_in);
                    if(response_data.mccb2_in == 'Fault')
                        $("#mccb2_in").css('border-color', 'red');
                    else
                        $("#mccb2_in").css('border-color', '');

                    $("#mccb2_out").val(response_data.mccb2_out);
                    if(response_data.mccb2_out == 'Fault')
                        $("#mccb2_out").css('border-color', 'red');
                    else
                        $("#mccb2_out").css('border-color', '');

                    $("#fuse3_in").val(response_data.fuse3_in);
                    if(response_data.fuse3_in == 'Fault')
                        $("#fuse3_in").css('border-color', 'red');
                    else
                        $("#fuse3_in").css('border-color', '');

                    $("#fuse3_out").val(response_data.fuse3_out);
                    if(response_data.fuse3_out == 'Fault')
                        $("#fuse3_out").css('border-color', 'red');
                    else
                        $("#fuse3_out").css('border-color', '');

                    $("#contactor3_status").val(response_data.contactor3_status);
                    if(response_data.contactor3_status == 'ON')
                        $("#contactor3_light").attr('src', './images/red_light.png')
                    else
                        $("#contactor3_light").attr('src', './images/grey.png');
                    $("#contactor3_command").val(response_data.contactor3_command);
                    $("#mccb3_in").val(response_data.mccb3_in);
                    if(response_data.mccb3_in == 'Fault')
                        $("#mccb3_in").css('border-color', 'red');
                    else
                        $("#mccb3_in").css('border-color', '');

                    $("#mccb3_out").val(response_data.mccb3_out);
                    if(response_data.mccb3_out == 'Fault')
                        $("#mccb3_out").css('border-color', 'red');
                    else
                        $("#mccb3_out").css('border-color', '');

                    $("#contactor_status").val(response_data.contactor_status);
                    if(response_data.contactor_status == 'ON')                       
                        $("#contactor_light").attr('src', './images/red_light.png')                    
                    else
                        $("#contactor_light").attr('src', './images/grey.png');
                    $("#command").val(response_data.contactor_command);
                    }
                        // Schedule the next request when the current one's complete
               });
               setTimeout(worker, 15000);
        }

    function IsNumeric(id) {
        var strString=    document.getElementById(id).value;
        var strValidChars = "0123456789";
        var strChar;
        var blnResult = true;
        if (strString.length == 0) return false;
        for (i = 0; i < strString.length && blnResult == true; i++)
        {
            strChar = strString.charAt(i);
            if (strValidChars.indexOf(strChar) == -1)
            {
                document.getElementById(id).value="";
                alert("Time should be Numeric");
                blnResult = false;
            }else{
                var time = document.getElementById(id).value;
                var timePeriodFrom = document.getElementById("time_from").value;
                var timePeriodTo = document.getElementById("time_to").value;
                if(id=="reading_time_from_hour"){
                    if(timePeriodFrom == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(timePeriodFrom == "PM"){
                        if(time>12){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 12");
                            return;
                        }
                    }
                }else if(id=='reading_time_to_hour'){
                    if(timePeriodTo == "AM"){
                        if(time>11){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 11");
                            return;
                        }
                    }else if(timePeriodTo == "PM"){
                        if(time>12){
                            document.getElementById(id).value="";
                            alert("Hour should Be less than or equals 12");
                            return;
                        }
                    }
                }else{
                    if(time>59){
                        document.getElementById(id).value="";
                        alert("Min should Be less than 60");
                        return;
                    }
                    $("#message").html("");
                }

            }
        }
        return blnResult;
    }

    function verify(){
        var search = document.getElementById("searchIn").value;
        if(search == "Search"){
            var searchReadingDateFrom = document.getElementById("searchReadingDateFrom").value;
            var searchReadingDateTo = document.getElementById("searchReadingDateTo").value;
            var pattern = /(\d{2})-(\d{2})-(\d{4})/;//alert(pattern);
            var dateFrom = new Date(searchReadingDateFrom.replace(pattern, '$3-$2-$1'));//alert(dateFrom);
            var dateTo = new Date(searchReadingDateTo.replace(pattern, '$3-$2-$1'));//alert(dateTo);
            //var dateFrom = new Date(searchReadingDateFrom, "d"));alert(dateFrom);
            //var dateTo = new Date(searchReadingDateTo, ""));alert(dateTo);
            if(searchReadingDateFrom != "" && searchReadingDateTo != ""){
                if(dateFrom > dateTo){
                    document.getElementById("searchReadingDateFrom").value="";
                    document.getElementById("searchReadingDateTo").value="";
                    alert("Reading Date From should Be less than Reading Date To");
                    return false;
                }
            }
            var timePeriodFrom = document.getElementById("time_from").value;
            var timePeriodTo = document.getElementById("time_to").value;
            var hourFrom = document.getElementById("reading_time_from_hour").value;
            var hourTo = document.getElementById("reading_time_to_hour").value;
            var minFrom = document.getElementById("reading_time_from_min").value;
            var minTo = document.getElementById("reading_time_to_min").value;
            if((timePeriodFrom == "AM" && timePeriodTo == "AM") || (timePeriodFrom == "PM" && timePeriodTo == "PM")){
                if((hourFrom != "00" && hourFrom != "") && (hourTo != "00" && hourTo != "")){
                    if(hourFrom > hourTo){
                        document.getElementById("reading_time_from_hour").value="";
                        document.getElementById("reading_time_to_hour").value="";
                        alert("Time From should Be less than Time To");
                        return false;
                    }
                }else if((minFrom != "" && minFrom != "00") && (minTo != "" && minTo != "00")){
                    if(minFrom > minTo){
                        document.getElementById("reading_time_from_min").value="";
                        document.getElementById("reading_time_to_min").value="";
                        alert("Time From should Be less than Time To");
                        return false;
                    }
                }
            }else if(timePeriodFrom == "PM" && timePeriodTo == "AM"){
                document.getElementById("reading_time_from_hour").value="";
                document.getElementById("reading_time_to_hour").value="";
                document.getElementById("reading_time_from_min").value="";
                document.getElementById("reading_time_to_min").value="";
                alert("Time From should Be less than Time To");
                return false;
            }
        }
        return true;
    }

    function getHealthStatuslist(){
        var searchSwitchingPointName = document.getElementById("searchSwitchingPointName").value;
        var searchHealthStatus = document.getElementById("searchHealthStatus").value;
        var searchP1HealthStatus = document.getElementById("searchP1HealthStatus").value;
        var searchP2HealthStatus = document.getElementById("searchP2HealthStatus").value;
        var searchP3HealthStatus = document.getElementById("searchP3HealthStatus").value;
        var searchP1vcHealthStatus = document.getElementById("searchP1vcHealthStatus").value;
        var searchP2vcHealthStatus = document.getElementById("searchP2vcHealthStatus").value;
        var searchP3vcHealthStatus = document.getElementById("searchP3vcHealthStatus").value;
        var searchReadingDateFrom = document.getElementById("searchReadingDateFrom").value;
        var searchReadingDateTo = document.getElementById("searchReadingDateTo").value;
        //reading_time_from_hour=11&reading_time_from_min=00&time_from=AM&reading_time_to_hour=1&reading_time_to_min=00&time_to=PM
        var reading_time_from_hour = document.getElementById("reading_time_from_hour").value;
        var reading_time_from_min = document.getElementById("reading_time_from_min").value;
        var time_from = document.getElementById("time_from").value;
        var reading_time_to_hour = document.getElementById("reading_time_to_hour").value;
        var reading_time_to_min = document.getElementById("reading_time_to_min").value;
        var time_to = document.getElementById("time_to").value;
        var queryString = "task=generateHSReport&searchSwitchingPointName="+searchSwitchingPointName+"&searchHealthStatus="
            +searchHealthStatus+"&searchReadingDateFrom="+searchReadingDateFrom+"&searchReadingDateTo="+searchReadingDateTo+"&reading_time_from_hour="
            +reading_time_from_hour+"&reading_time_from_min="+reading_time_from_min+"&time_from="+time_from+"&reading_time_to_hour="+reading_time_to_hour+"&reading_time_to_min="
            +reading_time_to_min+"&time_to="+time_to+"&searchP1HealthStatus="+searchP1HealthStatus+"&searchP2HealthStatus="+searchP2HealthStatus+"&searchP3HealthStatus="+searchP3HealthStatus
            +"&searchP1vcHealthStatus="+searchP1vcHealthStatus+"&searchP2vcHealthStatus="+searchP2vcHealthStatus+"&searchP3vcHealthStatus="+searchP3vcHealthStatus;
        var url = "healthStatusMapCont.do?" + queryString;
        popupwin = openPopUp(url, "Health Status Map List", 600, 900);
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

    function setCommand(){debugger;
        var junction_id = $("#junction_id").val();
        var switching_point_name = $("#switching_point_name").val();
        var change_command = $("#change_command").val();
        var change_command_value = $("#change_command_value").val();       
        $.ajax({
            url:"junctionDetailCont.do",
            data:"action1=setCommand&junction_id=" + junction_id + "&change_command=" + change_command + "&change_command_value=" + change_command_value,
            success:function(response){
                if(response == 1)
                    $("#message").html("<td colspan='5' bgcolor='yellow'><b>Command is Set...</b></td>");
                else
                    $("#message").html("<td colspan='5' bgcolor='coral'><b>Command is not Set Some Error!</b></td>");
            }
        });
    }

</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Health Status Map</title>
    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >            <!--DWLayoutDefaultTable-->
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <table width="100%">
                            <tr><tr><td>
                                    <table align="center">
                                        <tr>
                                            <td align="center" class="header_table" width="980"><b>Junction Detail</b></td>
                                            <td><%--<%@include file="/layout/org_menu.jsp" %>--%></td>
                                            <%-- <td>
                                                 <%@include file="/view/layout/org_menu.jsp" %>
                                             </td> --%>
                                        </tr>
                                    </table>
                                </td></tr>
                            <tr>
                                <td align="center"> <div id="search_div">
                                        <form name="form0" method="get" action="junctionDetailCont.do" onsubmit="return verify()">
                                            <table align="center" width="995">
                                                <%--<tr align="center">
                                                    <th align="center">
                                                        Switching Point Name &nbsp;&nbsp;<input class="input" type="text" id="searchSwitchingPointName" name="searchSwitchingPointName" value="${searchSwitchingPointName}" >
                                                    </th>
                                                    <th align="center" >
                                                        Date From &nbsp;&nbsp;<input class="input" type="text" name="searchReadingDateFrom" id="searchReadingDateFrom" value="${searchReadingDateFrom == '' ? '' : searchReadingDateFrom}">
                                                    </th>
                                                    <th align="center" >
                                                        Date To &nbsp;&nbsp;<input class="input" type="text" name="searchReadingDateTo" id="searchReadingDateTo" value="${searchReadingDateTo == '' ? '' : searchReadingDateTo}">
                                                    </th>
                                                    <th align="center">
                                                        OverAll Health&nbsp;&nbsp;<input class="input" type="text" id="searchHealthStatus" name="searchHealthStatus" value="${searchHealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase1 Health&nbsp;&nbsp;<input class="input" type="text" id="searchP1HealthStatus" name="searchP1HealthStatus" value="${searchP1HealthStatus}" >
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th align="center">
                                                        Phase2 Health&nbsp;&nbsp;<input class="input" type="text" id="searchP2HealthStatus" name="searchP2HealthStatus" value="${searchP2HealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase3 Health&nbsp;&nbsp;<input class="input" type="text" id="searchP3HealthStatus" name="searchP3HealthStatus" value="${searchP3HealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase1 VC Health&nbsp;&nbsp;<input class="input" type="text" id="searchP1vcHealthStatus" name="searchP1vcHealthStatus" value="${searchP1vcHealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase2 VC Health&nbsp;&nbsp;<input class="input" type="text" id="searchP2vcHealthStatus" name="searchP2vcHealthStatus" value="${searchP2vcHealthStatus}" >
                                                    </th>
                                                    <th align="center">
                                                        Phase3 VC Health&nbsp;&nbsp;<input class="input" type="text" id="searchP3vcHealthStatus" name="searchP3vcHealthStatus" value="${searchP3vcHealthStatus}" >
                                                    </th>
                                                </tr>--%>
                                                <tr align="center">
                                                    <th align="center">
                                                        Junction &nbsp;&nbsp;&nbsp;<input class="input1" type="text" name="searchJunction" id="searchJunction" value="${searchJunction == '' ? '' : searchJunction}">
                                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                    </th>
                                                    <%--<th align="center" >
                                                        Time From &nbsp;&nbsp;&nbsp;<input  type="text" id="reading_time_from_hour" name="reading_time_from_hour" Placeholder ="HH" value="${reading_time_from_hour}"
                                                                                            size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="reading_time_from_min" name="reading_time_from_min"  value="${reading_time_from_min}" Placeholder ="MM"
                                                                size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <select id="time_from" name="time_from" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${time_from eq 'PM'}">
                                                                    <option>AM</option>
                                                                    <option selected>PM</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option selected>AM</option>
                                                                    <option>PM</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </th>
                                                    <th align="center" >
                                                        Time To &nbsp;&nbsp;&nbsp;<input  type="text" id="reading_time_to_hour" name="reading_time_to_hour" value="${reading_time_to_hour}"
                                                                                          size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <b>:</b>
                                                        <input  type="text" id="reading_time_to_min" name="reading_time_to_min"  value="${reading_time_to_min}"
                                                                size="2" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)">
                                                        <select id="time_to" name="time_to" class="dropdown" style="width: auto" >
                                                            <c:choose>
                                                                <c:when test="${time_to eq 'PM'}">
                                                                    <option>AM</option>
                                                                    <option selected>PM</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option selected>AM</option>
                                                                    <option>PM</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </select>
                                                    </th>--%>
<!--                                                    <td  colspan="" align="center">
                                                        <input class="button" type="submit" name="task" id="searchIn" value="Search">
                                                        <input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records">
                                                        <input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="getHealthStatuslist()">
                                                    </td>-->
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                            </tr>
                            <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
                            <%--<tr>
                                <td align="center">

                                    <form name="form1" method="POST" action="healthStatusMapCont.do">
                                        <DIV class="content_div">
                                            <table id="table1" align="center" border="1" class="content" width="650">
                                                <tr>
                                                    <th class="heading" style="width: 20px">S.No.</th>
                                                    <th class="heading">Switching Point Name</th>
                                                    <th class="heading">Reading Date</th>
                                                    <th class="heading">Reading Time</th>
                                                    <th class="heading">Overall Health</th>
                                                    <th class="heading">Phase</th>
                                                    <th class="heading">Phase 1</th>
                                                    <th class="heading">Phase 2</th>
                                                    <th class="heading">Phase 3</th>
                                                    <th class="heading">Phase1 VC Status</th>
                                                    <th class="heading">Phase2 VC Status</th>
                                                    <th class="heading">Phase3 VC Status</th>
                                                </tr>
                                                <c:forEach var="response_data" items="${requestScope['healthStatusMapList']}" varStatus="loopCounter">
                                                    <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusBean.health_status_map_id}">
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusBean.health_status_id}">
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusBean.switching_point_detail_id}">
                                                            <input type="hidden" id="health_status_id_gen${loopCounter.count}" value="${healthStatusBean.switching_rev_no}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.switching_point_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.reading_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.reading_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.health_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.phase1_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.phase2_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.phase3_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.phase1_vc_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.phase2_vc_status}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${healthStatusBean.phase3_vc_status}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align="center" colspan="12">
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
                                                    </td>  </tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <%--<input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            </table></DIV>
                                    </form>
                                </td>
                            </tr>--%>
                            <%--</c:if>--%>
                            <tr>
                                <td align="center">
                                    <DIV id="autoCreateTableDiv" STYLE="visibility: hidden;height: 0px ;margin-bottom: 10px">
                                        <form name="form3"  action="healthStatusMapCont.do" method="POST" >
                                            <table id="parentTable" class="content" border="1"  align="center" width="500">
                                                <tr>
                                                    <td>
                                                        <table id="insertTable" class="content" border="1" align="center" width="100%">
                                                            <tr>
                                                                <th class="heading" style="width: 50px">S.No.</th>
                                                                <th class="heading">Health Status</th>
                                                                <th class="heading">Health Status Value</th>
                                                                <th class="heading">Remark</th>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td align="center">
                                                        <input type="button"  class="button"  name="checkUncheckAll" id="checkUncheckAll" value="Uncheck All" onclick="checkUncheck(value)"/>
                                                        <input type="submit" class="button" id="addAllRecords" name="task" value="Add All Records">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="button"  class="button"  name="Cancel" value="Cancel" onclick="deleteRow();">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </DIV>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <div >
                                        <form name="form2" id="form2" method="POST" action="healthStatusMapCont.do" onsubmit="return verify()">
                                            <table border="0" id="table2" align="center" class="reference" width="650">
                                                <tr><td colspan="6"></td> </tr>                                                                                                
                                                <tr>
                                                    <th class="heading1">Switching Point Name</th>
                                                    <td align="center">                                                        
                                                        <input class="input" type="text" id="switching_point_name" name="switching_point_name" size="20" value="${readingData.junction_name}">
                                                        <input type="hidden" value="${readingData.junction_id}" id="junction_id">
                                                    </td>
                                                    <th class="heading1">Reading Date</th>
                                                    <td>
                                                        <input class="input" type="text" id="reading_date" name="reading_date" size="20" value="${readingData.reading_date}">
                                                    </td>
                                                    <th class="heading1">Reading Time</th>
                                                    <td>
                                                        <input class="input" type="text" id="reading_time" name="reading_time" size="20" value="${readingData.reading_time}">
                                                    </td>
                                                <tr>
                                                    <th colspan="6">
                                                        Voltage
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Voltage 1</th>
                                                    <td>
                                                        <input class="input" type="text" id="voltage1" name="voltage1" size="20" value="${readingData.voltage1}">
                                                    </td>
                                                    <th class="heading1">Voltage 2</th>
                                                    <td>
                                                        <input class="input" type="text" id="voltage2" name="voltage2" size="20" value="${readingData.voltage2}">
                                                    </td>
                                                    <th class="heading1">Voltage 3</th>
                                                    <td>
                                                        <input class="input" type="text" id="voltage3" name="voltage3" size="20" value="${readingData.voltage3}">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th colspan="6">
                                                        Current
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Current 1</th>
                                                    <td>
                                                        <input class="input" type="text" id="current1" name="current1" size="20" value="${readingData.current1}">
                                                    </td>
                                                    <th class="heading1">Current 2</th>
                                                    <td>
                                                        <input class="input" type="text" id="current2" name="current2" size="20" value="${readingData.current2}">
                                                    </td>
                                                    <th class="heading1">Current 3</th>
                                                    <td>
                                                        <input class="input" type="text" id="current3" name="current3" size="20" value="${readingData.current3}">
                                                    </td>
                                                </tr>                                                
                                                <tr>
                                                    <th class="heading1">Power Factor</th>
                                                    <td>
                                                        <input class="input" type="text" id="power_factor" name="power_factor" size="20" value="${readingData.power_factor}">
                                                    </td>
                                                    <th class="heading1">Consumed Unit</th>
                                                    <td>
                                                        <input class="input" type="text" id="consume_unit" name="consume_unit" size="20" value="${readingData.consumed_unit}">
                                                    </td>
                                                    <th class="heading1">Connected Load</th>
                                                    <td>
                                                        <input class="input" type="text" id="connected_load" name="connected_load" size="20" value="${readingData.connected_load}">
                                                    </td>
                                                </tr>                                                
                                                <tr>
                                                    <th>Phase 1</th>                                                    
                                                    <td><input class="input" type="text" id="phase1_status" name="phase1_status" size="20" value="${healthStatusBean.phase1_status}"></td>
                                                    <td><img id="contactor1_light" src="${healthStatusBean.phase1Bean.contactor_status eq 'ON'?'./images/red_light.png':'./images/grey.png'}" style="width: 30px;"></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Fuse IN</th>
                                                    <td>
                                                        <input class="input" type="text" id="fuse1_in" name="fuse1_in" size="20" value="${healthStatusBean.phase1Bean.fuse_in}" style="border-color: ${healthStatusBean.phase1Bean.fuse_in eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                    <th class="heading1">Contactor Status</th>
                                                    <td>
                                                        <input class="input" type="text" id="contactor1_status" name="contactor1_status" size="20" value="${healthStatusBean.phase1Bean.contactor_status}">
                                                    </td>
                                                    <th class="heading1">MCCB IN</th>
                                                    <td>
                                                        <input class="input" type="text" id="mccb1_in" name="mccb1_in" size="20" value="${healthStatusBean.phase1Bean.mccb_in}" style="border-color: ${healthStatusBean.phase1Bean.mccb_in eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Fuse OUT</th>
                                                    <td>
                                                        <input class="input" type="text" id="fuse1_out" name="fuse1_out" size="20" value="${healthStatusBean.phase1Bean.fuse_out}" style="border-color: ${healthStatusBean.phase1Bean.fuse_out eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                    <th class="heading1">Contactor Command</th>
                                                    <td>
                                                        <input class="input" type="text" id="contactor1_command" name="contactor1_command" size="20" value="${healthStatusBean.phase1Bean.contactor_command}">
                                                    </td>
                                                    <th class="heading1">MCCB OUT</th>
                                                    <td>
                                                        <input class="input" type="text" id="mccb1_out" name="mccb1_out" size="20" value="${healthStatusBean.phase1Bean.mccb_out}" style="border-color: ${healthStatusBean.phase1Bean.mccb_out eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Phase 2</th>                                                    
                                                    <td><input class="input" type="text" id="phase2_status" name="phase2_status" size="20" value="${healthStatusBean.phase3_status}"></td>
                                                    <td><img id="contactor2_light" src="${healthStatusBean.phase2Bean.contactor_status eq 'ON'?'./images/red_light.png':'./images/grey.png'}" style="width: 30px;"></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Fuse IN</th>
                                                    <td>
                                                        <input class="input" type="text" id="fuse2_in" name="fuse2_in" size="20" value="${healthStatusBean.phase2Bean.fuse_in}" style="border-color: ${healthStatusBean.phase2Bean.fuse_in eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                    <th class="heading1">Contactor Status</th>
                                                    <td>
                                                        <input class="input" type="text" id="contactor2_status" name="contactor2_status" size="20" value="${healthStatusBean.phase2Bean.contactor_status}">
                                                    </td>
                                                    <th class="heading1">MCCB IN</th>
                                                    <td>
                                                        <input class="input" type="text" id="mccb2_in" name="mccb2_in" size="20" value="${healthStatusBean.phase2Bean.mccb_in}" style="border-color: ${healthStatusBean.phase2Bean.mccb_in eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Fuse OUT</th>
                                                    <td>
                                                        <input class="input" type="text" id="fuse2_out" name="fuse2_out" size="20" value="${healthStatusBean.phase2Bean.fuse_out}" style="border-color: ${healthStatusBean.phase2Bean.fuse_out eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                    <th class="heading1">Contactor Command</th>
                                                    <td>
                                                        <input class="input" type="text" id="contactor2_command" name="contactor2_command" size="20" value="${healthStatusBean.phase2Bean.contactor_command}">
                                                    </td>
                                                    <th class="heading1">MCCB OUT</th>
                                                    <td>
                                                        <input class="input" type="text" id="mccb2_out" name="mccb2_out" size="20" value="${healthStatusBean.phase2Bean.mccb_out}" style="border-color: ${healthStatusBean.phase2Bean.mccb_out eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>Phase 3</th>                                                    
                                                    <td><input class="input" type="text" id="phase3_status" name="phase3_status" size="20" value="${healthStatusBean.phase3_status}"></td>
                                                    <td><img id="contactor3_light" src="${healthStatusBean.phase3Bean.contactor_status eq 'ON'?'./images/red_light.png':'./images/grey.png'}" style="width: 30px;"></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Fuse IN</th>
                                                    <td>
                                                        <input class="input" type="text" id="fuse3_in" name="fuse3_in" size="20" value="${healthStatusBean.phase3Bean.fuse_in}" style="border-color: ${healthStatusBean.phase3Bean.fuse_in eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                    <th class="heading1">Contactor Status</th>
                                                    <td>
                                                        <input class="input" type="text" id="contactor3_status" name="contactor3_status" size="20" value="${healthStatusBean.phase3Bean.contactor_status}">
                                                    </td>
                                                    <th class="heading1">MCCB IN</th>
                                                    <td>
                                                        <input class="input" type="text" id="mccb3_in" name="mccb3_in" size="20" value="${healthStatusBean.phase3Bean.mccb_in}" style="border-color: ${healthStatusBean.phase3Bean.mccb_in eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Fuse OUT</th>
                                                    <td>
                                                        <input class="input" type="text" id="fuse3_out" name="fuse3_out" size="20" value="${healthStatusBean.phase3Bean.fuse_out}" style="border-color: ${healthStatusBean.phase3Bean.fuse_out eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                    <th class="heading1">Contactor Command</th>
                                                    <td>
                                                        <input class="input" type="text" id="contactor3_command" name="contactor3_command" size="20" value="${healthStatusBean.phase3Bean.contactor_command}">
                                                    </td>
                                                    <th class="heading1">MCCB OUT</th>
                                                    <td>
                                                        <input class="input" type="text" id="mccb3_out" name="mccb3_out" size="20" value="${healthStatusBean.phase3Bean.mccb_out}" style="border-color: ${healthStatusBean.phase3Bean.mccb_out eq 'Fault'?'red':''};border-style: solid;">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th colspan="">
                                                        Contactor
                                                    </th>
                                                    <td><img alt="" id="contactor_light"  src="${healthStatusBean.contactor_status eq 'ON'?'./images/red_light.png':'./images/grey.png'}" style="width: 30px;"></td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Status</th>
                                                    <td>
                                                        <input class="input" type="text" id="contactor_status" name="contactor_status" size="20" value="${healthStatusBean.contactor_status}">
                                                    </td>
                                                    <th class="heading1">Command</th>
                                                    <td>
                                                        <input class="input" type="text" id="command" name="command" size="20" value="${healthStatusBean.contactor_command}">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th class="heading1">Junction On Time : </th>
                                                    <td>                                                        
                                                        <b class="heading1">Hours</b><input class="input" type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="junction_in_time_hour" name="junction_time_hour" value="${readingData.on_time_hr}" maxlength="2" size="3">
                                                        <b class="heading1">Min.</b><input class="input" type="numeric" pattern="[0-5]{1}[0-9]{1}" id="junction_time_min" name="junction_time_min" value="${readingData.on_time_min}" maxlength="2" size="3">
                                                    </td>
                                                    <th class="heading1">Junction Off Time : </th>
                                                    <td>                                                        
                                                        <b class="heading1">Hours</b><input class="input" type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="junction_off_time_hour" name="junction_off_time_hour" value="${readingData.off_time_hr}" maxlength="2" size="3">
                                                        <b class="heading1">Min.</b><input class="input" type="numeric" pattern="[0-5]{1}[0-9]{1}" id="junction_off_time_min" name="junction_off_time_min" value="${readingData.off_time_min}" maxlength="2" size="3">
                                                    </td>
                                                </tr>                                                
                                                <!--<tr>
                                                    <td align='center' colspan="6">
                                                        <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                        <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)" >
                                                        <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                    </td>
                                                </tr>-->                                                
                                                <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input type="hidden" id="clickedButton" value="">
                                            </table>
                                            <table border="0" id="table3" align="center" class="reference" width="650">
                                                <tr id="message">
                                                    <c:if test="${not empty message}">
                                                        <td colspan="5" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                    </c:if>
                                                </tr>
                                                <tr>
                                                    <th>Change Command</th>
                                                    <td>
                                                        <select id="change_command" name="change_command">
                                                            <option value="1">Yes</option>
                                                            <option value="0">No</option>
                                                        </select>
                                                    </td>
                                                    <th>Command</th>
                                                    <td>
                                                        <select id="change_command_value" name="change_command_value">
                                                            <option value="1">ON</option>
                                                            <option value="0">OFF</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <input class="button" type="button" name="task" id="save" value="SET" onclick="setCommand()">
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div></td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
