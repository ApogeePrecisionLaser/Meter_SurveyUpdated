<%--
    Document   : wardView
    Created on : Jul 11, 2014, 10:09:08 AM
    Author     : JPSS
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
        $("#searchTubeWellName").autocomplete("tubeWellBoreDataCont", {
            extraParams: {
                action1: function() { return "getTubeWellNameType"}
            }
        });
        $("#searchIvrsNo").autocomplete("tubeWellBoreDataCont", {
            extraParams: {
                action1: function() { return "getIvrsNo"}
            }
        });


//        $("#tubewell_name").autocomplete("tubeWellBoreDataCont", {
//            extraParams: {
//                action1: function() { return "getTubeWellNameType"}
//            }
//        });

        $("#ivrs_no").autocomplete("tubeWellBoreDataCont", {
            extraParams: {
                action1: function() { return "getIVRSNumber"}
            }
        });
        $("#used_for").autocomplete("tubeWellBoreDataCont", {
            extraParams: {
                action1: function() { return "getUsedForName"}
            }
        });
        $("#ward_no").autocomplete("tubeWellBoreDataCont", {
            extraParams: {
                action1: function() { return "getWardName"}
            }
        });
        $("#bore_casing_type").autocomplete("tubeWellBoreDataCont", {
            extraParams: {
                action1: function() { return "getBoreCasingTypeName"}
            }
        });
        $("#motor_type").autocomplete("tubeWellBoreDataCont", {
            extraParams: {
                action1: function() { return "getMotorTypeName"}
            }
        });
        $("#ivrs_no").result(function(event, data, formatted){
                var temp=$("#ivrs_no").val();
                    $.ajax({url: "tubeWellBoreDataCont?action1=getTubeWellName1&temp="+temp, success: function(response_data) {
                            $("#tubewell_name").val(response_data);
                        }
                    });
                });

    });

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {
        document.getElementById("ivrs_no").disabled = false;
        document.getElementById("ward_no").disabled = false;
        document.getElementById("bore_diameter").disabled = false;
        document.getElementById("bore_depth").disabled = false;
        document.getElementById("bore_casing_type").disabled = false;
        document.getElementById("motor_capacity").disabled = false;
        document.getElementById("tubewell_name").disabled = false;
        document.getElementById("motor_type").disabled = false;
        document.getElementById("suction_diameter").disabled = false;
        document.getElementById("delivery_diameter").disabled = false;
        document.getElementById("discharge_capacity").disabled = false;
        document.getElementById("date_of_installation").disabled = false;
        //        document.getElementById("contact_person").disabled = false;
        //        document.getElementById("contact_no").disabled = false;
        document.getElementById("near_by_contact_person_name").disabled = false;
        document.getElementById("near_by_contact_person_address").disabled = false;
        document.getElementById("near_by_contact_person_mobile_no").disabled = false;
        document.getElementById("operated_by").disabled = false;
        document.getElementById("used_for").disabled = false;
        document.getElementById("operator_name").disabled = false;
        document.getElementById("operator_mobile_no").disabled = false;
       
        document.getElementById("remark").disabled = false;
        document.getElementById("save").disabled = false;

        /* if(document.getElementById("active").value == "N" )
            {
                document.getElementById("bore_diameter").disabled = true;
                document.getElementById("bore_casing_type").disabled = true;
                document.getElementById("remark").disabled = true;
            }*/

        // document.getElementById("revise").disabled =false;
        document.getElementById("cancel").disabled =false;
        document.getElementById("save_As").disabled =false;
        document.getElementById("save").disabled = false;
        if(id == 'new') {
            //document.getElementById("size").disabled = true;
            // document.getElementById("active").value ='';
            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");

            //   document.getElementById("revise").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;




            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 3);
            document.getElementById("ivrs_no").focus();

        }
        if(id == 'edit'){
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
        }
        else document.getElementById("clickedButton").value = "Delete";
    }
    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise'||document.getElementById("clickedButton").value == 'Delete') {
            var bore_diameter = document.getElementById("bore_diameter").value;
            var a=document.getElementById("active").value;
            //    alert(a);
            if(myLeftTrim(bore_diameter).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>Ward No is required...</b></td>");
                document.getElementById("bore_diameter").focus();
                return false; // code to stop from submitting the form2.
            }
            var bore_casing_type = document.getElementById("bore_casing_type").value;

            if(myLeftTrim(bore_casing_type).length == 0) {

                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='5' bgcolor='coral'><b>City Name is required...</b></td>");
                document.getElementById("bore_casing_type").focus();
                return false; // code to stop from submitting the form2.
            }

            if(document.getElementById("active").value =='Revised' || document.getElementById("active").value =='Cancelled')
            {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>You can not perform any operation on revised and cancelled record...</b></td>");
                // document.getElementById("source_wattage"+i).focus();
                return false; // code to stop from submitting the form2.
            }
            if(result == false)    // if result has value false do nothing, so result will remain contain value false.
            {

            }
            else{ result = true;
            }

            if(document.getElementById("clickedButton").value == 'Save AS New'){
                result = confirm("Are you sure you want to save it as New record?")
                return result;
            }
        } else result = confirm("Are you sure you want to cancel this record?")
        return result;
    }
    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns =23;
        var columnId = id;                        <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>

        var lowerLimit, higherLimit;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)

            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;

        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.

        document.getElementById("tubewell_bore_data_id").value= document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("ivrs_no").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("ward_no").value = document.getElementById(t1id +(lowerLimit+3)).innerHTML;
        document.getElementById("tubewell_name").value = document.getElementById(t1id +(lowerLimit+4)).innerHTML;
        document.getElementById("bore_depth").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;
        document.getElementById("bore_diameter").value = document.getElementById(t1id +(lowerLimit+6)).innerHTML;
        document.getElementById("bore_casing_type").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        document.getElementById("motor_capacity").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;

        document.getElementById("motor_type").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        document.getElementById("suction_diameter").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML;
        document.getElementById("delivery_diameter").value = document.getElementById(t1id +(lowerLimit+11)).innerHTML;
        document.getElementById("discharge_capacity").value = document.getElementById(t1id +(lowerLimit+12)).innerHTML;
        document.getElementById("date_of_installation").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
        document.getElementById("near_by_contact_person_name").value = document.getElementById(t1id +(lowerLimit+14)).innerHTML;
        document.getElementById("near_by_contact_person_mobile_no").value = document.getElementById(t1id +(lowerLimit+15)).innerHTML;
        document.getElementById("near_by_contact_person_address").value = document.getElementById(t1id +(lowerLimit+16)).innerHTML;
        document.getElementById("operated_by").value = document.getElementById(t1id +(lowerLimit+17)).innerHTML;
        document.getElementById("used_for").value = document.getElementById(t1id +(lowerLimit+18)).innerHTML;
        document.getElementById("operator_name").value = document.getElementById(t1id +(lowerLimit+19)).innerHTML;
        document.getElementById("operator_mobile_no").value = document.getElementById(t1id +(lowerLimit+20)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+21)).innerHTML;


        //  document.getElementById("tubewell_name").value = document.getElementById(t1id +(lowerLimit+7)).innerHTML;
        //        document.getElementById("address").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        //        document.getElementById("date_of_installation").value = document.getElementById(t1id +(lowerLimit+9)).innerHTML;
        //        document.getElementById("size").value = document.getElementById(t1id +(lowerLimit+10)).innerHTML;
        //        document.getElementById("meter_no").value = document.getElementById(t1id +(lowerLimit+11)).innerHTML;
        //        document.getElementById("contact_person").value = document.getElementById(t1id +(lowerLimit+12)).innerHTML;
        //        document.getElementById("contact_no").value = document.getElementById(t1id +(lowerLimit+13)).innerHTML;
        //  document.getElementById("remark").value = document.getElementById(t1id +(lowerLimit+14)).innerHTML;
        //    document.getElementById("active").value = document.getElementById(t1id +(lowerLimit+14)).innerHTML;
        //       var b=  document.getElementById(t1id +(lowerLimit+8)).innerHTML;
        // alert(b);
        for(var i = 0; i < noOfColumns; i++) {
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
        }
        //  makeEditable('');

        document.getElementById("edit").disabled = false;
        if(!document.getElementById("save").disabled)   // if save button is already enabled, then make edit, and cancel button enabled too.
        {
            document.getElementById("save_As").disabled = true;
            document.getElementById("cancel").disabled = false;
        }
        //  document.getElementById("message").innerHTML = "";      // Remove message
        $("#message").html("");
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
    var popupwin = null;
    function displayImageList(id){

        var image = document.getElementById('image'+id).value;

        var image_id=document.getElementById('image_id'+id).value;
        var queryString = "task1=viewImage&motor_type="+image+"&general_image_details_id="+image_id ;
        var url = "tubeWellUserDataCont?"+queryString;
        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);

    }
    function displayMapList(){

        var queryString = "task=generateMapReport" ;
        var url = "tubeWellBoreDataCont?"+queryString;
        popupwin = openPopUp(url, "TUbeWell User Details", 500, 1000);

    }
    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, motor_capacity=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }

</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tubewell Bore Data Page</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body" class="maindiv" align="center" >
                    <table width="100%" align="center">
                        <tr><td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">TUBEWELL Bore DATA DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td></tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="tubeWellBoreDataCont">
                                        <table align="center" class="heading1" width="600">
                                            <tr>
                                                <td>TubeWell USer<input class="input" type="text" id="searchTubeWellName" name="searchTubeWellName" value="${searchTubeWellName}" size="20" ></td>
                                                <td>Ivrs number<input class="input" type="text" id="searchIvrsNo" name="searchIvrsNo" value="${searchIvrsNo}" size="20" ></td>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="tubeWellBoreDataCont">
                                    <DIV class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th class="heading">S.No.</th>
                                                <th class="heading">IVRS No.</th>
                                                <th class="heading">Ward No.</th>
                                                 <th class="heading">Tube Well Name</th>
                                                <th class="heading">Bore Depth</th>
                                                <th class="heading">Bore Diameter</th>
                                                <th class="heading">Bore Casing Type</th>
                                                <th class="heading">Motor Capacity</th>
                                                <th class="heading">Motor Type</th>
                                                <th class="heading">Suction Diameter</th>
                                                <th class="heading">Delivery Diameter</th>
                                                <th class="heading">Delivery Capacity</th>
                                                <th class="heading">Date Of Installation</th>
                                                <th class="heading">Contact Person Name</th>
                                                <th class="heading">Contact Person Mobile no.</th>
                                                <th class="heading">Contact Person Address</th>
                                                <th class="heading">Operated By</th>
                                                <th class="heading">Used For</th>
                                                <th class="heading">Operator Name</th>
                                                <th class="heading">Operator Mobile no.</th>

                                                <th class="heading">Remark</th>
                                                <th class="heading">Created Date</th>
                                             <!--   <th class="heading">View Image</th>-->
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="tubeWellDataTypeBean" items="${requestScope['tubeWellBoreDataList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >

                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${tubeWellDataTypeBean.tubewell_bore_data_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.ivrs_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.ward_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.tubewell_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.depth}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.bore_diameter}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.bore_casing_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.motore_capacity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.motor_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.suction_diameter}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.delivery_diameter}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.discharge_capacity}</td>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.date_of_installation}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.contact_person_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.contact_person_mobile_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.contact_person_address}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.operated_by}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.used_for}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.operator_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.operator_mobile_no}</td>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellDataTypeBean.created_date}</td>


                                                  <%--  <td>
                                                        <input type="button" class="button" id="${loopCounter.count}" name="view_image" value="View Image" onclick="displayImageList(id);" >
</td>--%>

                                                </tr>
                                               
                                            </c:forEach>
                                            <tr>
                                                <td align='center' colspan="22">
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
                                            <input  type="hidden" id="searchCityType" name="searchCityType" value="${searchCityType}" >
                                            <input  type="hidden" id="searchTubeWellName" name="searchTubeWellName" value="${searchTubeWellName}" >
                                        </table></DIV>
                                </form>
                            </td>
                        </tr>

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST"  action="tubeWellBoreDataCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="2" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            <tr>
                                                <th class="heading1">IVRS no.</th>
                                                <td>
                                                    <input class="input" type="text" id="ivrs_no" name="ivrs_no" value="" size="40" disabled>

                                                </td>
                                                <th class="heading1">Ward Name</th>
                                                <td>
                                                    <input class="input" type="text" id="ward_no" name="ward_no" value="" size="40" disabled>

                                                </td>
                                            </tr>

                                            <tr>
                                                <th class="heading1">TubeWell name</th>
                                                <td>
                                                    <input class="input" type="hidden" id="tubewell_bore_data_id" name="tubewell_bore_data_id" value="" >
                                                    <input class="input" type="text" id="tubewell_name" name="tubewell_name" value="" size="40" disabled readonly>

                                                </td>
                                                <th class="heading1">Bore Depth</th>
                                                <td><input class="input" type="text" id="bore_depth" name="bore_depth" value="" size="40" disabled></td>

                                            </tr>
                                            <tr>

                                            </tr>
                                            <tr>
                                                <th class="heading1">Bore Diameter</th>
                                                <td><input class="input" type="text" id="bore_diameter" name="bore_diameter" value="" size="40" disabled></td>
                                                <th class="heading1">Bore casing Type</th>
                                                <td><input class="input" type="text" id="bore_casing_type" name="bore_casing_type" value="" size="40" disabled></td>
                                            </tr>

                                            <tr >
                                                <th class="heading1">Motor Capacity</th>
                                                <td><input class="input" type="text" id="motor_capacity" name="motor_capacity" value="" size="40" disabled></td>
                                                <th class="heading1">Motor Type</th>
                                                <td>
                                                    <input class="input" type="text" id="motor_type" name="motor_type" value="" size="35" disabled>
                                                </td>


                                            </tr>
                                            <tr >
                                                <th class="heading1">Suction diameter</th>
                                                <td><input class="input" type="text" id="suction_diameter" name="suction_diameter" value="" size="40" disabled></td>
                                                <th class="heading1">Delivery Diameter</th>
                                                <td>
                                                    <input class="input"type="text" id="delivery_diameter" name="delivery_diameter" value="" size="35"  disabled>
                                                </td>
                                            </tr>
                                            <tr >
                                                <th class="heading1">Discharge Capacity</th>
                                                <td><input class="input" type="text" id="discharge_capacity" name="discharge_capacity" value="" size="40" disabled></td>
                                                <th class="heading1">Date of Installation</th>
                                                <td>
                                                    <input class="input" type="text" id="date_of_installation" name="date_of_installation" value="" size="35" disabled >
                                                </td>
                                            </tr>

                                            <tr >
                                                <th class="heading1">Near By Contact Person Name</th>
                                                <td><input class="input" type="text" id="near_by_contact_person_name" name="near_by_contact_person_name" value="" size="40" disabled></td>
                                                <th class="heading1">Near By Contact Person Address</th>
                                                <td>
                                                    <input class="input" type="text" id="near_by_contact_person_address" name="near_by_contact_person_address" value="" size="35" disabled >
                                                </td>
                                            </tr>

                                            <tr >
                                                <th class="heading1">Near By Contact Person Mobile No.</th>
                                                <td><input class="input" type="text" id="near_by_contact_person_mobile_no" name="near_by_contact_person_mobile_no" value="" size="40" disabled></td>
                                                <th class="heading1">Operated By</th>
                                                <td>
<!--                                                    <input class="input" type="text" id="operated_by" name="operated_by" value="" size="35"  >-->

                                                    <select class="input" type="text" id="operated_by" name="operated_by" value="" disabled>
                                                           <option value="A">A</option>
                                                           <option value="B">B</option>
                                                           <option value="C">C</option>
                                                           <option value="D">D</option>
                                                    </select>

                                                </td>
                                            </tr>

                                            <tr >
                                                <th class="heading1">Used For</th>
                                                <td><input class="input" type="text" id="used_for" name="used_for" value="" size="40" disabled></td>
                                                <th class="heading1">Operator Name</th>
                                                <td>
                                                    <input class="input" type="text" id="operator_name" name="operator_name" value="" size="35" disabled >
                                                </td>
                                            </tr>


                                            <tr>
                                                <th class="heading1">Operator Mobile No.</th>
                                                <td><input class="input" type="text" id="operator_mobile_no" name="operator_mobile_no" value="" size="40" disabled></td>

                                                <th class="heading1">Remark</th>
                                                <td>
                                                    <input class="input" type="text" id="remark" name="remark" value="" size="35"  disabled>
                                                </td>

                                            </tr>
                                            <%-- <tr style="display:none">
                                                 <th class="heading1">Active</th>
                                                 <td>
                                                     <select name="active" id="active" disabled>
                                                             <option>Y</option>
                                                             <option>N</option>
                                                     </select>
                                                 </td>

                                            </tr> --%>
                                            <tr>
                                                <td align='center' colspan="4">
                                                    <input class="button" type="button" name="edit" id="edit" value="Edit" onclick="makeEditable(id)" disabled>
                                                    <%--       <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled>  --%>
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled>
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)">
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>

                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="active" id="active" value="">
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchCityType" value="${searchCityType}" >
                                            <input type="hidden"  name="searchTubeWellName" value="${searchTubeWellName}" >
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
