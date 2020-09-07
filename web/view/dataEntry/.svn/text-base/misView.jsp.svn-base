<%--
    Document   : reportMeter
    Created on : Aug 9, 2012, 11:20:24 AM
    Author     : JPSS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MIS</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript" src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
        <script type="text/javascript" language="javascript">
            $(document).ready(function() {
                
                //  if($('#consumption_faults').val()==''){alert("sgdj");
                //  $('#phase_table').show();
                //  $('#comsumption_table').hide();
                //   $('#faults_table').hide();
                //                $('#consumption_faults').click(function() {alert("djk");
                //                    $('#consumption_table').show();
                //                    $('#phase_table').hide();
                //                    $('#faults_table').hide();
                //                });
                //                $('#final_faults').click(function() {
                //                    $('#faults_table').show();
                //                    $('#comsumption_table').hide();
                //                    $('#phase_table').hide();
                //                });
                // }
              
            });

            function generateAlertSheet(){
                var requition_no= document.getElementById("requition_no").value;
                var division_filter= document.getElementById("division_filter").value;
                var zone_filter= document.getElementById("zone_filter").value;
                var feeder_filter1= document.getElementById("feeder_filter1").value;
                var parameter_value= document.getElementById("parameter_value").value;


                var premises_type = '';
                if($("option:selected", $("#premises_type")).val() != 'ALL'){
                    premises_type = $("option:selected", $("#premises_type")).val();
                }
                var alert_parameter = '';
                if($("option:selected", $("#alert_parameter")).val() != 0){
                    alert_parameter = $("option:selected", $("#alert_parameter")).val();
                }
                var arr = "";
                var value = "";
                var alter_value = $("#alter_value").is(":checked");
                var new_value = $("#new_value").is(":checked");
                if(alter_value == true){
                    value = "revision";
                }else if(new_value == true){
                    value = "new_value";
                }
                var id = $("input[type=radio]:checked").attr('id');
                //                alert(value);
                if((id = 'search_by_feeder') && ($("#"+id).is(":checked") == true)){
                    if(feeder_filter1 == ''){
                        arr = "";
                    }else{
                        arr = arr + "," + feeder_filter1;
                    }
                }

                var file_no = $("#file_no").val();
                // var month= $("option:selected", $("#month")).val();
                //var year = $("option:selected", $("#year")).val();

                var parameter_value_less_grater_default= $("input[name='parameter_value_less_grater_default']:checked").val();

                // var queryString = "requestprinrt=PRINT_ALERT_BY_PARAMETER&requition_no="+requition_no+"&division_filter="+division_filter+"&zone_filter="+zone_filter+"&feeder_filter="+arr
                //+"&month="+month+"&year=" +year+"&value=" +value+"&file_no="+file_no+"&alert_parameter="+alert_parameter+"&premises_type="+premises_type+"&parameter_value="+parameter_value+"&parameter_value_less_grater_default="+parameter_value_less_grater_default+"&energy_charges=false";
                //                alert(queryString);

                var month_from;
                var month;
                var year_from;
                var year;
                var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month1").val();
                    year=$("#year1").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var queryString = "requestprinrt=PRINT_ALERT_BY_PARAMETER&requition_no="+requition_no+"&division_filter="+division_filter+"&zone_filter="+zone_filter+"&feeder_filter="+arr
                    +"&month="+month+"&year="+year+"&month_from="+month_from+"&year_from="+year_from+"&month_to="+month_to+"&year_to="+year_to+"&monthType="+month_type+"&value="+value+"&file_no="+file_no+"&alert_parameter="+alert_parameter+"&premises_type="+premises_type+"&parameter_value="+parameter_value+"&parameter_value_less_grater_default="+parameter_value_less_grater_default+"&energy_charges=false";
                var url = "misCont.do?" + queryString;
                popupwin = openPopUp(url, "Alert Sheet", 600, 900);
            }
            function showContent(id){
                alert($("#"+id).val());
                if($("#"+id).val()=='Phase ImBalance'){
                    $("#phase_table").toggle();
                    $(this).toggleClass('content')
                    $("#comsumption_table").hide();
                    $(this).toggleClass('content')
                    $("#faults_table").hide();
                    $(this).toggleClass('content')

                }
                if($("#"+id).val()=='Consumption Faults'){
                    $("#comsumption_table").toggle();
                    $(this).toggleClass('content')
                    $("#phase_table").hide();
                    $(this).toggleClass('content')
                    $("#faults_table").hide();
                    $(this).toggleClass('content')
                }
                if($("#"+id).val()=='Faults'){
                    $("#faults_table").toggle();
                    $(this).toggleClass('content')
                    $("#comsumption_table").hide();
                    $(this).toggleClass('content')
                    $("#phase_table").hide();
                    $(this).toggleClass('content')
                }
            }
            function getShowData(id){
                var task_name=$("#"+id).val();
                var queryString = "task="+task_name;
                //alert(queryString);
                var url = "misCont.do?" + queryString;
                window.open(url);
            }
            var popupwin = null;
            function displayMapList(id){

                var queryString = "task=generateReport&report_name="+id;
                var url = "misCont.do?"+queryString;
                popupwin = openPopUp(url, "FAULTS Details", 500, 1000);

            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                return window.open(url, window_name, window_features);
            }
        </script>
    </head>
    <body onload="setVisibility();">
        <div id="div_animationLoading" style="display: none">
            <table align="center" style="vertical-align: middle;">
                <tr>
                    <td align="center">
                        <img src="images/Animation_Loading.gif" alt="images/Animation_Loading.gif">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label style="font-size: large; font-weight: bold;">Please wait refreshing mail ...</label>
                    </td>
                </tr>
            </table>
        </div>
        <table align="center"  class="main" cellpadding="0" cellspacing="0" width="100%">            <!--DWLayoutDefaultTable-->
            <tr>
                <td><%@include file="/layout/header.jsp" %></td>
            </tr>
            <tr>
                <%--   <td><%@include file="/layout/metermenu.jsp" %></td>  --%>
            </tr>
            <tr>
                <td align="center">
                    <div style="width: 100%" class="maindiv" id="body" align="center">
                        <table width="100%">
                            <tr class="header_table">
                                <td width="100%" align="center" height="60px">
                                    Management Information Systems
                                    <%--  <div align="right"><form action="readMailCont.do"><input type="submit" class="button" value="Refresh E-mail" onclick="enableMainDiv()"></form></div> --%>
                                </td>
                            </tr>

                        </table>

                        <table width="100%" >


                            <tr class="header_table">
                                <td width="100%" align="center" height="60px">
                                    <form name="form0"  method="POST" action="misCont.do">
                                        <input type="button" class="submit"  id="phase_imbalance" name="task" value="Phase ImBalance" onclick="getShowData(id);" >&nbsp;
                                        <input type="button" class="submit"  id="consumption_faults" name="task" value="Consumption Faults" onclick="getShowData(id);">&nbsp;
                                        <input type="button" class="submit"  id="final_faults" name="task" value="Faults" onclick="getShowData(id);">&nbsp;
                                    </form>
                                </td>
                            </tr>

                        </table>

                        <table>

                            <tr>
                                <td style="border: none;padding: 1px;align:left; background-color: mistyrose" colspan="9" nowrap>
                                    <div id="showBillmonth"> Bill Month

                                    </div></td>
                                <td>                 <div id="singleMonth">
                                        <select class="dropdown3" id="month1" name="month1">
                                            <option <c:if test="${month == 'Jan'}">selected</c:if>>Jan</option>
                                            <option <c:if test="${month == 'Feb'}">selected</c:if>>Feb</option>
                                            <option <c:if test="${month == 'Mar'}">selected</c:if>>Mar</option>
                                            <option <c:if test="${month == 'Apr'}">selected</c:if>>Apr</option>
                                            <option <c:if test="${month == 'May'}">selected</c:if>>May</option>
                                            <option <c:if test="${month == 'Jun'}">selected</c:if>>Jun</option>
                                            <option <c:if test="${month == 'Jul'}">selected</c:if>>Jul</option>
                                            <option <c:if test="${month == 'Aug'}">selected</c:if>>Aug</option>
                                            <option <c:if test="${month == 'Sep'}">selected</c:if>>Sep</option>
                                            <option <c:if test="${month == 'Oct'}">selected</c:if>>Oct</option>
                                            <option <c:if test="${month == 'Nov'}">selected</c:if>>Nov</option>
                                            <option <c:if test="${month == 'Dec'}">selected</c:if>>Dec</option>
                                        </select>
                                        <select class="dropdown3" id="year1" name="year1">
                                            <c:forEach var="year" items="${yearlist}">
                                                <c:choose>
                                                    <c:when test="${search_year eq year}">
                                                        <option selected>${year}</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option>${year}</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <input type="hidden" id="checkBillMonth" value="${month}"></td>
                                <td align="center" style="border: none;padding: 1px;background-color: mistyrose" colspan="9" nowrap>
                                    <%--    </td>
                                        <td align="left" style="border: none;padding: 1px;background-color: mistyrose" colspan="8" nowrap>   --%>
                                    <input type="button" class="button"  id="alert_sheet_by_param" name="alert_sheet_by_param" value="Sheet" onclick="displayMapList(id);">&nbsp;&nbsp;
                                    <input type="button" class="button"  class="button"  id="alert_sheet_excel_btn" name="alert_sheet_excel_btn" value="Excel" onclick="alertSheetExcel(id);">&nbsp;&nbsp;
                                    <input type="button" class="button"  class="button"  id="alert_sheet_btn" name="task" value="HTML" onclick="alertSheetHtmlView(id);" >
                                </td>
                            </tr>
                        </table>



                        <table id="phase_table" style="background-color: mistyrose">
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="misCont.do">
                                        <DIV id="content_div" class="content_div">
                                            <table id="table1" width="600"  border="1"  align="center" class="content">
                                                <tr>
                                                    <th style="white-space: normal" class="heading">S.No.</th>
                                                    <th style="white-space: normal" class="heading">Pole No*</th>
                                                    <th style="white-space: normal" class="heading">Service Conn </th>
                                                    <th style="white-space: normal" class="heading">Survey Date</th>
                                                    <th style="white-space: normal" class="heading">R phase</th>
                                                    <th style="white-space: normal" class="heading">B phase</th>
                                                    <th style="white-space: normal" class="heading">Y phase</th>

                                                </tr>
                                                <!---<th style="white-space: normal" class="heading">Phase</th>below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                                <c:forEach var="misTypeBean" items="${requestScope['misTypeList']}"  varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.pole_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.meter_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.survey_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.r_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.y_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.b_phase}</td>


                                                    </tr>
                                                    <th style="background-color:aquamarine">Message Factor</th>
                                                    <th style="background-color:aquamarine">Difference Percentage</th>

                                                    <th style="background-color:aquamarine">Alert Message</th>

                                                    <c:forEach var="list" items="${misTypeBean.messgae}"  varStatus="loopCounter">
                                                        <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                            <%--<td>${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>--%>
                                                            <td>${list.message_factor}</td>
                                                            <td>${list.difference_amt}</td>

                                                            <td>${list.alert_message}</td>
                                                            <%-- <td>
                                                                 <input type="button" class="button" id="${loopCounter.count}" name="view_image" value="View Image" onclick="displayMapList(id)">
                                                                    </td>--%>
                                                        </tr>
                                                    </c:forEach>
                                                </c:forEach>
                                                <%--<tr>
                                                    <td align='right' colspan="6">
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

                                    </tr>--%>
                                                <tr id="messagee">
                                                    <c:if test="${not empty messagee}">
                                                        <td colspan="8" bgcolor="${msgBgColorr}"><b>Result: ${messagee}</b></td>
                                                    </c:if>
                                                </tr>
                                                <!---  <td><input class="button" type="submit" name="task" id="updateIVRS" value="updateIVRS" ></td>These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input  type="hidden" id="searchPoleNoSwitch" name="searchPoleNoSwitch" value="${searchPoleNoSwitch}" >
                                                <input  type="hidden" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" >
                                                <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                                <input  type="hidden" id="searchAreaName" name="searchAreaName" value="${searchAreaName}" >
                                                <input  type="hidden" id="searchRoadName" name="searchRoadName" value="${searchRoadName}" >
                                                <input  type="hidden" id="searchSwitchingPtName" name="searchSwitchingPtName" value="${searchSwitchingPtName}" >
                                                <input  type="hidden" id="zone_search" name="zone_search" value="${zone_search}" >
                                            </table></DIV>
                                        <div id="map_container" title="Location Map">
                                            <div id="map_canvas" style="width:100%;height:100%;"></div>
                                        </div>
                                    </form>
                                </td>
                            </tr>
                        </table>
                        <%--     <table id="comsumption_table" style="display: none ;background-color: mistyrose">  <tr>
        <td align="center">
            <form name="form1" method="POST" action="misCont.do">
                <DIV id="content_div" class="content_div">
                    <table id="table2" width="600"  border="1"  align="center" class="content">
                        <tr>
                            <th style="white-space: normal" class="heading">S.No.</th>
                            <th style="white-space: normal" class="heading">Pole No*</th>
                            <th style="white-space: normal" class="heading">Service Conn </th>
                            <th style="white-space: normal" class="heading">Survey Date</th>
                            <th style="white-space: normal" class="heading">R phase</th>
                            <th style="white-space: normal" class="heading">B phase</th>
                            <th style="white-space: normal" class="heading">Y phase</th>
                            <th style="white-space: normal" class="heading">projected Consumption</th>
                            <th style="white-space: normal" class="heading">Bill Consumption</th>
                            <th style="white-space: normal" class="heading">Difference Amount</th>
                            <th style="white-space: normal" class="heading">Alert Message</th>

                                                </tr>
                                                <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                                <c:forEach var="misType" items="${requestScope['consumptionTypeList']}"  varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.pole_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.meter_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.survey_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.r_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.y_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.b_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.parameter1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.parameter2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.difference_amt}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misType.alert_message}</td>

                                                    </tr>
                                                </c:forEach>


                                                <!---  <td><input class="button" type="submit" name="task" id="updateIVRS" value="updateIVRS" ></td>These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input  type="hidden" id="searchPoleNoSwitch" name="searchPoleNoSwitch" value="${searchPoleNoSwitch}" >
                                                <input  type="hidden" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" >
                                                <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                                <input  type="hidden" id="searchAreaName" name="searchAreaName" value="${searchAreaName}" >
                                                <input  type="hidden" id="searchRoadName" name="searchRoadName" value="${searchRoadName}" >
                                                <input  type="hidden" id="searchSwitchingPtName" name="searchSwitchingPtName" value="${searchSwitchingPtName}" >
                                                <input  type="hidden" id="zone_search" name="zone_search" value="${zone_search}" >
                                            </table></DIV>
                                        <div id="map_container" title="Location Map">
                                            <div id="map_canvas" style="width:100%;height:100%;"></div>
                                        </div>
                                    </form>
                                </td>
                            </tr></table>
                        <table id="faults_table" style="display: none ; background-color: mistyrose ">  <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="misCont.do">
                                        <DIV id="content_div" class="content_div">
                                            <table id="table3" width="600"  border="1"  align="center" class="content">
                                                <tr>
                                                    <th style="white-space: normal" class="heading">S.No.</th>
                                                    <th style="white-space: normal" class="heading">Pole No*</th>
                                                    <th style="white-space: normal" class="heading">Service Conn </th>
                                                    <th style="white-space: normal" class="heading">Survey Date</th>
                                                    <th style="white-space: normal" class="heading">Previous Reading date</th>
                                                    <th style="white-space: normal" class="heading">Days Difference</th>
                                                    <th style="white-space: normal" class="heading">Current Reading</th>
                                                    <th style="white-space: normal" class="heading">Survey Reading</th>
                                                    <th style="white-space: normal" class="heading">Power Consumption</th>
                                                    <th style="white-space: normal" class="heading">Difference</th>
                                                    <th style="white-space: normal" class="heading">Alert Messages</th>






                                                </tr>
                                                <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                                <c:forEach var="misTypeBean" items="${requestScope['surveyConsumptionTypeList']}"  varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.pole_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.meter_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.survey_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.current_reading_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.days}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.r_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.y_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.b_phase}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.parameter1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.parameter2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.difference_amt}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${misTypeBean.alert_message}</td>

                                                    </tr>
                                                </c:forEach>


                                                <!---  <td><input class="button" type="submit" name="task" id="updateIVRS" value="updateIVRS" ></td>These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <input  type="hidden" id="searchPoleNoSwitch" name="searchPoleNoSwitch" value="${searchPoleNoSwitch}" >
                                                <input  type="hidden" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" >
                                                <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                                <input  type="hidden" id="searchAreaName" name="searchAreaName" value="${searchAreaName}" >
                                                <input  type="hidden" id="searchRoadName" name="searchRoadName" value="${searchRoadName}" >
                                                <input  type="hidden" id="searchSwitchingPtName" name="searchSwitchingPtName" value="${searchSwitchingPtName}" >
                                                <input  type="hidden" id="zone_search" name="zone_search" value="${zone_search}" >
                                            </table></DIV>
                                        <div id="map_container" title="Location Map">
                                            <div id="map_canvas" style="width:100%;height:100%;"></div>
                                        </div>
                                    </form>
                                </td>
                            </tr></table>--%>



                    </div>
                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
