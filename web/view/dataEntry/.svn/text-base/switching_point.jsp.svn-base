<%--
    Document   : switching_point
    Created on : Oct 29, 2012, 12:10:20 PM
    Author     : JPSS
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">



<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
<link rel="stylesheet" type="text/css" href="css/calendar.css" >
<link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
<link rel="stylesheet" href="datePicker/jquery.ui.all.css">
<script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>



<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Switching Point Table</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
            jQuery(function(){
                $("#feeder_name").autocomplete("switchingpontCont.do", {
                    extraParams: {
                        action1: function() { return "feeder"}
                    }
                });
                $("#location_filter").autocomplete("switchingpontCont.do", {
                    extraParams: {
                        action1: function() { return "getLocation"}
                    }
                });
                $("#division_filter").autocomplete("autoCompSearchCont.do", {
                    extraParams: {
                        Param: function() { return "division"} }
                });
                $("#zone_filter").autocomplete("autoCompSearchCont.do", {
                    extraParams: {
                        Param: function() { return "Zone"},
                        Param1: function() { return document.getElementById("division_filter").value}
                    }
                });
                $("#feeder_filter").autocomplete("autoCompSearchCont.do", {
                    extraParams: {
                        Param: function() { return "feeder"},
                        Param1: function() { return document.getElementById("zone_filter").value;}
                    }
                });
                $("#switching_point_filter").autocomplete("switchingpontCont.do", {
                    extraParams: {
                        action1: function() { return "getSwitchingPoint";},
                        action2: function() { return document.getElementById("feeder_filter").value;}
                    }
                });
                $("#division_filter").change(function(){
                    $("#zone_filter").val('');
                    $("#zone_filter").flushCache();
                    $("#feeder_filter").val('');
                    $("#feeder_filter").flushCache();
                    $("#switching_point_filter").val('');
                    $("#switching_point_filter").flushCache();
                });
                $("#zone_filter").change(function(){
                    $("#feeder_filter").val('');
                    $("#feeder_filter").flushCache();
                    $("#switching_point_filter").val('');
                    $("#switching_point_filter").flushCache();
                });
                $("#feeder_filter").change(function(){
                    $("#switching_point_filter").val('');
                    $("#switching_point_filter").flushCache();
                });

                $("#feeder_name").result(function(){
                    var feeder = document.getElementById("feeder_name").value;
                    $.get("switchingpontCont.do", { action1:"getSwitchingPointNo" , action2: feeder}, function(data) {
                        //                        alert(data);
                        $("#auto_switching_point_name").val(feeder+"_"+data);
                    });

                });
            });

            function makeEditable(id) {

                document.getElementById("feeder_name").disabled = false;
                document.getElementById("address_line1").disabled = false;
                document.getElementById("address_line2").disabled = false;
                document.getElementById("address_line3").disabled = false;
                document.getElementById("description").disabled = false;
                document.getElementById("switching_point_name").disabled = false;
                if(id == 'new') {
                    //  document.getElementById("message").innerHTML = "";      // Remove message
                    $("#message").html("");
                    document.getElementById("edit").disabled = true;
                    document.getElementById("delete").disabled = true;
                    document.getElementById("save_As").disabled = true;
                    setDefaultColordOrgn(document.getElementById("noOfRowsTraversed").value, 16);
                    document.getElementById("feeder_name").focus();
                }
                if(id == 'edit'){
                    document.getElementById("save_As").disabled = false;
                    document.getElementById("delete").disabled = false;
                }
                document.getElementById("save").disabled = false;
            }
            function setDefaultColordOrgn(noOfRowsTraversed, noOfColumns) {
                //alert(noOfColumns);
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    for(var j = 1; j <= noOfColumns; j++) {
                        document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
                    }
                }
            }


            function clearText(){
                document.getElementById("zone_filter").value = "";
                document.getElementById("switching_point_filter").value="";
                document.getElementById("location_filter").value="";
                document.getElementById("feeder_filter").value="";
            }


            function fillColumns(id) {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var noOfColumns = 16;
                var columnId = id;
                columnId = columnId.substring(3, id.length);
                var lowerLimit, higherLimit;
                var noOfRows;
                for(var i = 0; i < noOfRowsTraversed; i++) {
                    noOfRows = i;
                    lowerLimit = i * noOfColumns + 1;

                    higherLimit = (i + 1) * noOfColumns;

                    if((columnId >= lowerLimit) && (columnId <= higherLimit))
                        break;
                }
                var lower= lowerLimit;
                var upper= higherLimit;
                setDefaultColordOrgn(noOfRowsTraversed, noOfColumns);
                var t1id = "t1c";
                $("#switching_pt_code").val(document.getElementById(t1id +(lower+1)).innerHTML);
                document.getElementById("switching_point_name").value = document.getElementById(t1id +(lower+2)).innerHTML;
                document.getElementById("auto_switching_point_name").value = document.getElementById(t1id +(lower+3)).innerHTML;
                //                alert(document.getElementById(t1id +(lower+6)).innerHTML);
                var switching_point_type = document.getElementById(t1id +(lower+9)).innerHTML;
                $("#switching_point_type_old").val(switching_point_type);
                $("#switching_point_type option").each(function()
                {
                    if($(this).text() == switching_point_type){
                        $(this).attr('selected', true);
                    }
                });
                document.getElementById("feeder_name").value = document.getElementById(t1id +(lower+8)).innerHTML;
                document.getElementById("address_line1").value = document.getElementById(t1id +(lower+10)).innerHTML;
                document.getElementById("address_line2").value = document.getElementById(t1id +(lower+11)).innerHTML;
                document.getElementById("address_line3").value= document.getElementById(t1id +(lower+12)).innerHTML;
                document.getElementById("description").value = document.getElementById(t1id +(lower+13)).innerHTML;
                var active= document.getElementById(t1id +(lower+14)).innerHTML;
                if(active=='Y'){
                    document.getElementById("yes").checked =true;
                }else{
                    document.getElementById("no").checked =true;
                }
                document.getElementById("switching_point_id").value=   document.getElementById(t1id +(lower+15)).innerHTML;
                for(var i = 0; i <=14; i++) {
                    document.getElementById(t1id + (lower + i)).bgColor = "#d0dafd";
                }
                document.getElementById("edit").disabled = false;
                if(!document.getElementById("save").disabled)

                {
                    document.getElementById("save_As").disabled = true;
                    document.getElementById("delete").disabled = false;

                }
                //  document.getElementById("message").innerHTML = "";
                $("#message").html("");
            }


            function setStatus(id) {
                if(id == 'save'){
                    document.getElementById("clickedButton").value = "Save";
                }
                else if(id == 'save_As'){
                    document.getElementById("clickedButton").value = "Save AS New";
                }
                else document.getElementById("clickedButton").value = "Delete";
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
            function verify() {
                var result;
                if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
                    var feeder_name = document.getElementById("feeder_name").value;
                    var address1 = document.getElementById("address_line1").value;
                    var address2 = document.getElementById("address_line2").value;
                    var switching_point_name = document.getElementById("switching_point_name").value;
                    if(myLeftTrim(feeder_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Feeder Name is required...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Feeder Name is required...</b></td>");
                        document.getElementById("feeder_name").focus();
                        return false;
                    }
                    if(myLeftTrim(switching_point_name).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Feeder Name is required...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Switching Point Name is required...</b></td>");
                        document.getElementById("switching_point_name").focus();
                        return false;
                    }
                    if(myLeftTrim(document.getElementById("switching_point_type").value) == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Address1 is required...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Select Switching Point Type..</b></td>");
                        document.getElementById("switching_point_type").focus();
                        return false;
                    }
                    if(myLeftTrim(address1).length == 0) {
                        //  document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Address1 is required...</b></td>";
                        $("#message").html("<td colspan='6' bgcolor='coral'><b>Address1 is required...</b></td>");
                        document.getElementById("address_line1").focus();
                        return false;
                    }
                    if(myLeftTrim(address2).length == 0) {
                        // document.getElementById("message").innerHTML = "<td colspan='6' bgcolor='coral'><b>Address2 is required...</b></td>";
                        $("#message").html( "<td colspan='6' bgcolor='coral'><b>Address2 is required...</b></td>");
                        document.getElementById("address_line2").focus();
                        return false;
                    }
                    if(result == false)
                    {// if result has value false do nothing, so result will remain contain value false.
                    }
                    else{ result = true;
                    }
                    if(document.getElementById("clickedButton").value == 'Save AS New'){
                        result = confirm("Are you sure you want to save it as New record?")
                        return result;
                    }
                } else result = confirm("Are you sure you want to delete this record?")
                return result;
            }

            function viewmeterlist(){
                var switching_pt_no_filter = document.getElementById("switching_pt_no_filter").value;
                var switching_point_type_filter = document.getElementById("switching_point_type_filter").value;
                var switching_point_filter = document.getElementById("switching_point_filter").value;
                var location_filter = document.getElementById("location_filter").value;
                var feeder_filter = document.getElementById("feeder_filter").value;
                var zone_filter = document.getElementById("zone_filter").value;
                var division_filter = document.getElementById("division_filter").value;
                var queryString = "requestprinrt=PRINT&switching_point_filter="+switching_point_filter +"&location_filter=" +location_filter + "&feeder_filter="
                    +feeder_filter +"&zone_filter=" +zone_filter+"&switching_point_type_filter="+switching_point_type_filter+"&switching_pt_no_filter="+switching_pt_no_filter+"&division_filter="+division_filter;
                var url = "switchingpontCont.do?" + queryString;
                popupwin = openPopUp(url, "Switching Point List", 600, 900);
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
    </head>
    <body>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" >            <!--DWLayoutDefaultTable-->
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                 <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <td>
                    <div class="maindiv" id="body" >
                        <table width="100%">
                            <tr>
                                <td>
                                    <form name="form1" method="POST" action="switchingpontCont.do" onsubmit="return verifySearch();">
                                        <table width="100%">
                                            <tr><td>
                                                    <div id="wrapper" align="center" style="margin-bottom: 0px;padding-bottom: 0px">
                                                        <div class="block1" style="width: 240px">
                                                            <div style="float: left"><h1> Switching Point</h1></div>
                                                            <div style="float: right"><input type="button" class="pdf_button" id="view_pdf" name="view_pdf" value="" onclick="viewmeterlist()"> </div>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>
                                                    <table width="100%" align="center" >
                                                        <tr>
                                                            <th class="heading1">Switching point No</th>
                                                            <td>
                                                                <input  class="input1" type="text" name="switching_pt_no_filter" id="switching_pt_no_filter" value="${switching_pt_no_filter eq '' ? '' : switching_pt_no_filter}" >
                                                            </td>
                                                            <th class="heading1">Switching Point Type</th>
                                                            <td>
                                                                <select class="dropdown" id="switching_point_type_filter" name="switching_point_type_filter">
                                                                    <option value="Switching Point Type">Switching Point Type</option>
                                                                    <c:forEach items="${switching_pt_type_map}" var="map">
                                                                        <option value="${map.value}" ${switching_point_type_filter == map.value ? 'selected' : ''}>${map.value}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </td>
                                                            <th class="heading1">Division</th>
                                                            <td>
                                                                <input class="input1" type="text" id="division_filter" name="division_filter" value="${division_filter}"/>
                                                            </td>
                                                            <th class="heading1">Zone</th>
                                                            <td>
                                                                <input class="input1" type="text" name="zone_filter" id="zone_filter" value="${zone_filter == '' ? '' : zone_filter}"  >
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th class="heading1">Feeder</th>
                                                            <td>
                                                                <input class="input1" type="text" name="feeder_filter" id="feeder_filter" value="${feeder_filter eq '' ? '' : feeder_filter}" size="30" >
                                                            </td>
                                                            <th class="heading1">Switching Point</th>
                                                            <td>
                                                                <input class="input1" type="text" name="switching_point_filter" id="switching_point_filter" value="${switching_point_filter eq '' ? '' : switching_point_filter}" size="30" >
                                                            </td>
                                                            <th class="heading1">Location</th>
                                                            <td>
                                                                <input  class="input1" type="text" name="location_filter" id="location_filter" value="${location_filter eq '' ? '' : location_filter}" size="30" >
                                                            </td>
                                                            <td align="center" colspan="2" nowrap>
                                                                <input class="button" type="submit" id="search_details" name="search_details" value="SEARCH">
                                                                <input class="button" type="button" id="clear_details" name="clear_details" value="NEW" onclick="clearText()">
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <DIV class="content_div">
                                                        <table id="table1" align="center"  class="content" width="920">
                                                            <tr>
                                                                <th class="heading" style="width: 20px">S.No.</th>
                                                                <th class="headingWrap">&nbsp;Switching Point No.&nbsp;</th>
                                                                <th class="heading">&nbsp;Switching Point&nbsp;</th>
                                                                <th class="heading">&nbsp;Auto Switching Point&nbsp;</th>
                                                                <th class="heading">&nbsp;Meter No.&nbsp;</th>
                                                                <th class="headingWrap">&nbsp;Sanctioned Load&nbsp;</th>
                                                                <th class="heading">&nbsp;Division&nbsp;</th>
                                                                <th class="heading">&nbsp;Zone&nbsp;</th>
                                                                <th class="heading">&nbsp;Feeder&nbsp;</th>
                                                                <th class="headingWrap">&nbsp;Switching Point Type&nbsp;</th>
                                                                <th class="heading">&nbsp;Address1&nbsp;</th>
                                                                <th class="heading">&nbsp;Address2&nbsp;</th>
                                                                <th class="heading">&nbsp;Address3&nbsp;</th>
                                                                <th class="heading">&nbsp;Description&nbsp;</th>
                                                                <th class="heading">&nbsp;Active&nbsp;</th>
                                                                <th  class="heading" style="display: none"></th>

                                                            </tr>

                                                            <c:forEach var="sw_point" items="${requestScope['sw_pointList']}" varStatus="loopCounter">
                                                                <tr class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}">
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.switching_point_code}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.switching_point}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.auto_switching_point}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.meter_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.sanctioned_load_kw}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.division_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.zone}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.feeder_name}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.switching_point_type}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.address1}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.address2}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.address3}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.description}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${sw_point.active}</td>
                                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" style="display: none">${sw_point.switching_point_id}</td>
                                                                </tr>
                                                            </c:forEach>
                                                            <tr>
                                                                <td align='center' colspan="8">
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
                                                                </td> </tr>
                                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                            <input type="hidden" name="switching_point_filter" id="switching_point_filter" value="${switching_point_filter}" >
                                                            <input type="hidden" name="location_filter" id="location_filter" value="${location_filter}" >
                                                            <input type="hidden" name="feeder_filter" id="feeder_filter" value="${feeder_filter}" >
                                                            <input type="hidden" name="zone_filter" id="zone_filter" value="${zone_filter}" >
                                                            <input type="hidden" name="switching_pt_no_filter" id="switching_pt_no_filter" value="${switching_pt_no_filter}" >
                                                            <input type="hidden" name="switching_point_type_filter" id="switching_point_type_filter" value="${switching_point_type_filter}" >
                                                        </table>
                                                    </DIV>
                                                </td>
                                            </tr>
                                        </table>


                                    </form>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form2" method="POST" action="switchingpontCont.do" onsubmit="return verify()">
                                        <table id="table2" class="reference" border="0" align="center" width="920">
                                            <tr><td colspan="6"></td> </tr>
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="6" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Feeder_Name</th>
                                                <td>
                                                    <input class="input" type="text" id="feeder_name" name="feeder_name" value="" disabled size="25">
                                                </td>
                                                <th class="heading1">Auto Switching Point</th>
                                                <td>
                                                    <input class="input" type="text" id="auto_switching_point_name" name="auto_switching_point_name" value="" readonly size="25">
                                                </td>
                                                <th class="heading1">Switching Point</th>
                                                <td>
                                                    <input class="input" type="text" id="switching_point_name" name="switching_point_name" value="" disabled size="25">
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Switching Point Type</th>
                                                <td>
                                                    <select class="dropdown" id="switching_point_type" name="switching_point_type">
                                                        <option value="0">Switching Point Type</option>
                                                        <c:forEach items="${switching_pt_type_map}" var="map">
                                                            <option value="${map.key}">${map.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                                <th class="heading1">Address 1</th>
                                                <td>
                                                    <input class="input"  type="text" id="address_line1" name="address_line1" value="" disabled size="25">
                                                </td>
                                                <th class="heading1">Address 2</th>
                                                <td>
                                                    <input class="input" type="text" id="address_line2" name="address_line2" value="" disabled size="25">
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading1">Address 3</th>
                                                <td>
                                                    <input class="input" type="text" id="address_line3" name="address_line3" value="" disabled size="25">
                                                </td>
                                                <th class="heading1">Description</th>
                                                <td>
                                                    <input class="input" type="text" id="description" name="description" value="" disabled size="25">
                                                </td>
                                                <th class="heading1"> Active</th>
                                                <td>
                                                    yes <input type="radio" id="yes" name="active" value="yes" checked>
                                                    &nbsp; &nbsp; &nbsp; &nbsp;
                                                    No  <input type="radio" id="no" value="no" name="active">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align='center' colspan="6">
                                                    <input class="input" type="text" id="switching_point_id" name="switching_point_id" value="" style="visibility:hidden">
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="delete" value="Delete" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>


                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden" name="switching_point_type_old" id="switching_point_type_old" value="" >
                                            <input type="hidden" name="switching_pt_code" id="switching_pt_code" value="" >
                                        </table>

                                    </form>
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


