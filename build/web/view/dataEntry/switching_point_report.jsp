<%--
    Document   : fullPaymentDetail
    Created on : Jun 27, 2014, 2:30:38 PM
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Full Detail View</title>
        <link href="style/style.css" type="text/css" rel="stylesheet" media="Screen"/>
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" language="javascript">
       <%--     jQuery(function() {
                $(document).ready(function () {
                    if($("#checkBillMonth").val().length==0) {
                        $("#duration").show();
                        $("#singleMonth").hide();
                        $("#radioPeriod").attr("checked", true);
                    } else{
                        $("#singleMonth").show();
                        $("#duration").hide();
                        $("#radioSingle").attr("checked", true);
                    }
                });

            });

            function showBillMonth(){
                if($('input[name="monthType"]:checked').val()=='period'){
                    $("#duration").show();
                    $("#singleMonth").hide();
                    $("#radioPeriod").attr("checked", true);

                    $('#month_to option').each(function() {
                      if($(this).text()== 'Dec')
                          $(this).attr("selected", "selected");
                    });


                } else{
                    $("#singleMonth").show();
                    $("#duration").hide();
                    $("#radioSingle").attr("checked", true);
                }
            }

            function showView(){
                $("#form1").submit();
            }  --%>

            function showSwitchingPointDetail(zone) {
                //alert(div+" detail");
            /*    var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }

                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();  */
                var url="switchingPointReport.do?task=ShowZoneHtmlViewOfSwitching&zone="+zone;
                window.open(url);
            }
            function showPoleDetail(zone) {
               var url="switchingPointReport.do?task=ShowSwitchingPointsOfPolesInAZone&zone="+zone;
                window.open(url);
            }


  <%--          function showStDetail(div) {
                //alert(div+" st detail");
                var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }

                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division="+div+"&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=Street Light&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "",  700, 1100);
            }

            function showBlDetail(div) {
                //alert(div+" bl detail");
                 var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division="+div+"&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=Building&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "",700, 1100);
            }

            function showPhDetail(div) {
                //alert(div+" ph detail");
                 var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division="+div+"&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=Pump house&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "", 700, 1100);
            }

            function showDetail(div) {
                //alert(div+" detail");
                var month_from;
                var month;
                var year_from;
                var year;
                var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division="+div+"&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "", 700, 1100);
            }

            function showFullStDetail() {
                //alert(div+" st detail");
                var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }

                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division=&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=Street Light&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "",  700, 1100);
            }

            function showFullBlDetail() {
                //alert(div+" bl detail");
                 var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division=&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=Building&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "",700, 1100);
            }

            function showFullPhDetail() {
                //alert(div+" ph detail");
                 var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division=&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=Pump house&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "", 700, 1100);
            }

            function showFullDetail() {
                //alert(div+" detail");
                var month_from;
                var month;
                var year_from;
                var year;
                var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showMonthDetail&division=&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&connection=&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "", 700, 1100);
            }

            function showReport(){
                 var month_from;
                var month;
                var year_from;
                var year;
                 var month_type=$('input[name="monthType"]:checked').val();
                if(month_type=='period'){
                    month_from=$("#month_from").val();
                    year_from=$("#year_from").val();
                }else{
                    month=$("#month").val();
                    year=$("#year").val();
                }
                var month_to=$("#month_to").val();
                var year_to=$("#year_to").val();
                var column=$("#column").val();
                var table=$("#table").val();
                var url="fullPaymentDetailCont.do?task=showReport&month_from="+month_from+"&year_from="+year_from+"&month_to="
                    +month_to+"&year_to="+year_to+"&column="+column+"&table="+table+"&type=division&monthType="+month_type+"&month="+month+"&year="+year;
                popupwin = openPopUp(url, "", 600, 900);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url, window_name, window_features);
            }    --%>
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
                    <div class="maindiv" id="body" >
                        <table width="100%">
                            <tr>
                                <td>
                                    <div id="wrapper" align="center" style="margin-bottom: 0px;padding-bottom: 0px">
                                        <div class="block1" style="width: 450px">
                                            <div><h1>Full&nbsp;&nbsp;(Switching Point--Pole)&nbsp;&nbsp;Summery</h1></div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        <%--    <form action="switchingPointReport.do" id="form1" method="post" onsubmit="">
                                <tr>
                                    <td>
                                        <table  class="header_table" width="100%">
                                            <tr>
                                                <td>
                                                    <font style="background-color:thistle">Bill Month</font>
                                                    <input type="radio" name="monthType" id="radioSingle" value="single" onchange="showBillMonth()">single
                                                    <input type="radio" name="monthType" id="radioPeriod" value="period" onchange="showBillMonth()">Period
                                                </td>
                                                <td>
                                                    <font style="background-color:thistle">From</font>
                                                    <select class="dropdown3" id="table" name="table">
                                                        <option value="meter_bill" ${'meter_bill' eq search_table?'selected':''}>Assessed Bill</option>
                                                        <option value="mpeb_meter_bill" ${'mpeb_meter_bill' eq search_table?'selected':''}>MPEB Bill</option>
                                                    </select>
                                                </td>
                                                <td>
                                                    <font style="background-color:thistle">Column</font>
                                                    <select class="dropdown3" id="column" name="column">
                                                        <c:forEach var="col" items="${columnlist}">
                                                            <option value="${col.key}" ${col.key eq search_col?'selected':''}>${col.value}</option>
                                                        </c:forEach>
                                                    </select>&nbsp;&nbsp;
                                                </td>
                                                <td>
                                                    <input class="button" type="button" value="Search" onclick="showView()">
                                                    &nbsp;
                                                    <input class="pdf_button" type="button" value="" onclick="showReport()">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="4" align="left">
                                                    <div id="duration">
                                                        From:
                                                        <select class="dropdown3" id="month_from" name="month_from">
                                                            <option <c:if test="${month_from == 'Jan'}">selected</c:if>>Jan</option>
                                                            <option <c:if test="${month_from == 'Feb'}">selected</c:if>>Feb</option>
                                                            <option <c:if test="${month_from == 'Mar'}">selected</c:if>>Mar</option>
                                                            <option <c:if test="${month_from == 'Apr'}">selected</c:if>>Apr</option>
                                                            <option <c:if test="${month_from == 'May'}">selected</c:if>>May</option>
                                                            <option <c:if test="${month_from == 'Jun'}">selected</c:if>>Jun</option>
                                                            <option <c:if test="${month_from == 'Jul'}">selected</c:if>>Jul</option>
                                                            <option <c:if test="${month_from == 'Aug'}">selected</c:if>>Aug</option>
                                                            <option <c:if test="${month_from == 'Sep'}">selected</c:if>>Sep</option>
                                                            <option <c:if test="${month_from == 'Oct'}">selected</c:if>>Oct</option>
                                                            <option <c:if test="${month_from == 'Nov'}">selected</c:if>>Nov</option>
                                                            <option <c:if test="${month_from == 'Dec'}">selected</c:if>>Dec</option>
                                                        </select>
                                                        <select class="dropdown3" id="year_from" name="year_from">
                                                            <c:forEach var="year" items="${yearlist}">
                                                                <c:choose>
                                                                    <c:when test="${search_year_from eq year}">
                                                                        <option selected>${year}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option>${year}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                        &nbsp;
                                                        To:
                                                        <select class="dropdown3" id="month_to" name="month_to">
                                                            <option <c:if test="${month_to == 'Jan'}">selected</c:if>>Jan</option>
                                                            <option <c:if test="${month_to == 'Feb'}">selected</c:if>>Feb</option>
                                                            <option <c:if test="${month_to == 'Mar'}">selected</c:if>>Mar</option>
                                                            <option <c:if test="${month_to == 'Apr'}">selected</c:if>>Apr</option>
                                                            <option <c:if test="${month_to == 'May'}">selected</c:if>>May</option>
                                                            <option <c:if test="${month_to == 'Jun'}">selected</c:if>>Jun</option>
                                                            <option <c:if test="${month_to == 'Jul'}">selected</c:if>>Jul</option>
                                                            <option <c:if test="${month_to == 'Aug'}">selected</c:if>>Aug</option>
                                                            <option <c:if test="${month_to == 'Sep'}">selected</c:if>>Sep</option>
                                                            <option <c:if test="${month_to == 'Oct'}">selected</c:if>>Oct</option>
                                                            <option <c:if test="${month_to == 'Nov'}">selected</c:if>>Nov</option>
                                                            <option <c:if test="${month_to == 'Dec'}">selected</c:if>>Dec</option>
                                                        </select>
                                                        <select class="dropdown3" id="year_to" name="year_to">
                                                            <c:forEach var="year" items="${yearlist}">
                                                                <c:choose>
                                                                    <c:when test="${search_year_to eq year}">
                                                                        <option selected>${year}</option>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <option>${year}</option>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div id="singleMonth">
                                                        <select class="dropdown3" id="month" name="month">
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
                                                        <select class="dropdown3" id="year" name="year">
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
                                                    <input type="hidden" id="checkBillMonth" value="${month}">
                                            </tr>
                                            <input name="task" type="hidden" value="fullView">
                                        </table>
                                    </td>
                                </tr>
                                <tr></tr>
                                <tr></tr>
                                <tr></tr>
                            </form>   --%>
                            <tr>
                                <td>
                                    <div align="center">
                                        <table  width="80%" border="1" id="table1" align="center" class="content">
                                            <tr>
                                                <th class="heading" style="width: 20px">S.No.</th>
                                                <th class="heading">Zone</th>
                                                <th class="heading">Switching Point Survey</th>
                                                <th class="heading">Pole Survey</th>
                                            </tr>
                                          <c:forEach var="zone_list" items="${requestScope['zone_list']}" varStatus="loopCounter">
                                                <tr>
                                                    <td id="t1c${IDGenerator.uniqueID}" align="center">${loopCounter.count} </td>
                                                    <td id="t1c${IDGenerator.uniqueID}" >${zone_list.city}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" ><a href="#" onclick="showSwitchingPointDetail('${zone_list.city}');">${zone_list.no_of_switching_points}</a>&nbsp;&nbsp;(${zone_list.switching_no_of_poles})</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" ><a href="#" onclick="showPoleDetail('${zone_list.city}');">${zone_list.pole_no_of_poles}</a></td>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <th colspan="2" class="heading1" align="center" >Total :</th>

                                                <td><b>${sum_switching_point_nos}</b>&nbsp;&nbsp;(${sum_pole_nos})<input type="hidden" name="st_Total" id="colTotal" value="${sum_switching_point_nos}"></td>
                                                <td><b>${sumTotalPoleNosFromPole}</b><input type="hidden" name="bl_Total" id="bl_Total" value="${sumTotalPoleNosFromPole}"</td>

                                            </tr>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>

                                </td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>

             <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>

        </table>
    </body>
</html>
