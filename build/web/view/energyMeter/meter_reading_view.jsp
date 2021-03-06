<%-- 
    Document   : meter_reading_view
    Created on : Mar 20, 2015, 1:15:59 PM
    Author     : Pooja
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Energy Meter Reading</title>
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
        <script>
            jQuery(function(){
                $("#searchJunction").autocomplete("meterReadingCont.do", {
                    extraParams: {
                        action1: function() { return "getJunctionName"}
                    }
                });

                $("#searchIvrs_no").autocomplete("meterReadingCont.do", {
                    extraParams: {
                        action1: function() { return "getIvrs_no"}
                    }
                });


                $("#searchReadingDateFrom").result(function(event, data, formatted){
                    $("#searchReadingDateTo").value = "";
                    $("#searchReadingDateTo").flushCache();
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
                        changeYear: true,
                        onselect:function (){debugger;
                            var date = $(this).datepicker('getDate');
                            alert($.datepicker.formatDate('DD', date));
                        }
                });

            });

           function displayPdf(){
                var searchJunction= document.getElementById("searchJunction").value;
                var searchReadingDateFrom= document.getElementById("searchReadingDateFrom").value;
                var searchReadingDateTo= document.getElementById("searchReadingDateTo").value;
                var searchVoltage= document.getElementById("searchVoltage").value;
                var searchVoltageValue= document.getElementById("searchVoltageValue").value;
                var searchCurrent= document.getElementById("searchCurrent").value;
                var searchCurrentValue= document.getElementById("searchCurrentValue").value;
                var searchConnectedLoad= document.getElementById("searchConnectedLoad").value;
                var searchTimeFromHour = $("#searchTimeFromHour").val();
                var searchTimeFromMin = $("#searchTimeFromMin").val();
                var searchTimeToHour =$("#searchTimeToHour").val();
                var searchTimeToMin = $("#searchTimeToMin").val();
                var queryString = "task=showPdf&searchJunction="+searchJunction+"&searchReadingDateFrom="+searchReadingDateFrom
                                   +"&searchReadingDateTo="+searchReadingDateTo+"&searchVoltage="+searchVoltage+"&searchVoltageValue="+searchVoltageValue
                                   +"&searchCurrent="+searchCurrent+"&searchCurrentValue="+searchCurrentValue+"&searchConnectedLoad="+searchConnectedLoad
                                   +"&searchTimeFromHour="+searchTimeFromHour+"&searchTimeFromMin"+searchTimeFromMin+"&searchTimeToHour="+searchTimeToHour
                                   +"&searchTimeToMin="+searchTimeToMin;
                var url = "meterReadingCont.do?"+queryString;
                popupwin = openPopUp(url, "Organisation", 600, 900);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }
        </script>
    </head>
    <body>
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
                                <td>
                                    <div id="wrapper" align="center" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <div class="header_table" style="width: 980px">
                                            <div><h1>Meter Readings</h1></div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                               <tr>
                                <td align="center">
                                    <div id="search_div" >
                                        <form name="form1" id="form1" name="form1" method="post" action="meterReadingCont.do">
                                            <table>
                                                <tr>
                                                    <th align="center">
                                                        Junction &nbsp;&nbsp;&nbsp;<input class="input1" type="text" name="searchJunction" id="searchJunction" value="${searchJunction == '' ? '' : searchJunction}">
                                                    </th>
                                                    <th align="center" >
                                                        Date Time From &nbsp;&nbsp;&nbsp;<input class="input1" type="text" name="searchReadingDateFrom" id="searchReadingDateFrom" value="${searchReadingDateFrom == '' ? '' : searchReadingDateFrom}" style="max-width: 25%" maxlength="10">
                                                        <b class="" style="">H.</b><input class="input" type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="searchTimeFromHour" name="searchTimeFromHour" value="${searchTimeFromHour}" style="" maxlength="2" size="2">
                                                        <b class="" style="">M.</b><input class="input" type="numeric" pattern="[0-5]{1}[0-9]{1}" id="searchTimeFromMin" name="searchTimeFromMin" value="${searchTimeFromMin}" style="" maxlength="2" size="2">
                                                    </th>
                                                    <th align="center" >
                                                        Date Time To &nbsp;&nbsp;&nbsp;<input class="input1" type="text" name="searchReadingDateTo" id="searchReadingDateTo" value="${searchReadingDateTo == '' ? '' : searchReadingDateTo}" style="max-width: 25%"  maxlength="10">
                                                        <b class="" style="">H.</b><input class="input" type="numeric" pattern="([0-1]{1}[0-9]{1}|20|21|22|23)" id="searchTimeToHour" name="searchTimeToHour" value="${searchTimeToHour}" style="" maxlength="2" size="2">
                                                        <b class="" style="">M.</b><input class="input" type="numeric" pattern="[0-5]{1}[0-9]{1}" id="searchTimeToMin" name="searchTimeToMin" value="${searchTimeToMin}" style="" maxlength="2" size="2">
                                                    </th>
                                                </tr>
                                                <tr>
                                                    <th align="center">
                                                        <SELECT name="searchVoltage" id="searchVoltage">
                                                            <option value="voltage1" ${searchVoltage == "voltage1"? 'selected' : ''}>Voltage 1</option>
                                                            <option value="voltage2" ${searchVoltage == "voltage2"? 'selected' : ''}>Voltage 2</option>
                                                            <option value="voltage3" ${searchVoltage == "voltage3"? 'selected' : ''}>Voltage 3</option>
                                                        </SELECT><b> > </b> &nbsp;&nbsp;&nbsp;
                                                        <input class="input1" type="text" name="searchVoltageValue" id="searchVoltageValue" value="${searchVoltageValue}" style="max-width: 25%">
                                                    </th>
                                                    <th align="center" >
                                                        <SELECT name="searchCurrent" id="searchCurrent">
                                                            <option value="current1" ${searchCurrent == "current1"? 'selected' : ''}>Current 1</option>
                                                            <option value="current2" ${searchCurrent == "current2"? 'selected' : ''}>Current 2</option>
                                                            <option value="current3" ${searchCurrent == "current3"? 'selected' : ''}>Current 3</option>
                                                        </SELECT><b> > </b> &nbsp;&nbsp;&nbsp;
                                                        <input class="input1" type="text" name="searchCurrentValue" id="searchCurrentValue" value="${searchCurrentValue}" style="max-width: 25%">
                                                    </th>
                                                    <th align="center" >
                                                        Connected Load > &nbsp;&nbsp;&nbsp;
                                                        <input class="input1" type="text" name="searchConnectedLoad" id="searchConnectedLoad" value="${searchConnectedLoad}" style="max-width: 25%">
                                                    </th>
                                                </tr>

                                                <tr>
                                                    <th align="center">
                                                        IVRS_NO. &nbsp;&nbsp;&nbsp;<input class="input1" type="text" name="searchIvrs_no" id="searchIvrs_no" value="${searchIvrs_no == '' ? '' : searchIvrs_no}">
                                                    </th>

                                                </tr>


                                                <tr>
                                                    <td colspan="3" align="center"><input type="submit" class="button" id="SEARCH" name="SEARCH" value="SEARCH">
                                                    <input type="submit" class="button" id="SHOW ALL" name="task" value="SHOW ALL">
                                                    <input type="button" class="pdf_button" id="pdf" name="task" value="" onclick="displayPdf()"></td>
                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <div STYLE="overflow: auto; width: 990px;margin: 20px 0px;">
                                        <form action="meterReadingCont.do" id="form2" name="form2" method="post">
                                            <table border="1"  id="table1" align="center" width="910" class="content" style="margin-top: 20px">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading">Junction Name</th>
                                                    <th class="heading">Ivrs_no</th>
                                                    <th class="heading">Voltage 1</th>
                                                    <th class="heading">Voltage 2</th>
                                                    <th class="heading">Voltage 3</th>
                                                    <th class="heading">Current 1</th>
                                                    <th class="heading">Current 2</th>
                                                    <th class="heading">Current 3</th>
                                                    <th class="heading">Power Factor</th>
                                                    <th class="heading">Consumed Unit</th>
                                                    <th class="heading">Connected Load</th>
                                                    <th class="heading">Reading Date</th>
                                                    <th class="heading">Reading Time</th>
                                                    <th class="heading">Remark</th>
                                                </tr>
                                                <c:forEach var="list" items="${requestScope['junction']}" varStatus="loopCounter">
                                                    <tr class="row" onMouseOver=this.style.backgroundColor='#E3ECF3' onmouseout=this.style.backgroundColor='white'>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                            ${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="junction_id${loopCounter.count}" value="${list.junction_id}">
                                                            <input type="hidden" id="program_version_no${loopCounter.count}" value="${list.program_version_no}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.junction_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.ivrs_no}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.voltage1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.voltage2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.voltage3}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.current1}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.current2}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.current3}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.power_factor}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.consumed_unit}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.connected_load}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.reading_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.reading_time}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="">${list.remark}</td>
                                                    </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="22">
                                                        <c:choose>
                                                            <c:when test="${showFirst eq 'false'}">
                                                                <input class="button" type='submit' name='buttonAction' value='First' disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input  class="button" type='submit' name='buttonAction' value='First'>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:choose>
                                                            <c:when test="${showPrevious == 'false'}">
                                                                <input  class="button" type='submit' name='buttonAction' value='Previous' disabled>
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
                                            </table>
                                            <input type="hidden" name="searchJunction" value="${searchJunction}">
                                            <input type="hidden" name="searchIvrs_no" value="${searchIvrs_no}">
                                            <input type="hidden" name="searchReadingDateFrom" value="${searchReadingDateFrom}">
                                            <input type="hidden" name="searchReadingDateTo" value="${searchReadingDateTo}">
                                            <input type="hidden" name="searchVoltage" value="${searchVoltage}">
                                            <input type="hidden" name="searchVoltageValue" value="${searchVoltageValue}">
                                            <input type="hidden" name="searchCurrent" value="${searchCurrent}">
                                            <input type="hidden" name="searchCurrentValue" value="${searchCurrentValue}">
                                            <input type="hidden" name="searchConnectedLoad" value="${searchConnectedLoad}">
                                            <input type="hidden" name="searchTimeFromHour" value="${searchTimeFromHour}">
                                            <input type="hidden" name="searchTimeFromMin" value="${searchTimeFromMin}">
                                            <input type="hidden" name="searchTimeToHour" value="${searchTimeToHour}">
                                            <input type="hidden" name="searchTimeToMin" value="${searchTimeToMin}">           
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                        </form>
                                    </div>
                                </td>
                            </tr>
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
