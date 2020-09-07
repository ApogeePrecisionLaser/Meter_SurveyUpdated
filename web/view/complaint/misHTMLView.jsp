<%-- 
    Document   : misHTMLView
    Created on : Jul 1, 2015, 9:30:20 AM
    Author     : jpss
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Complaint Register</title>
        <link rel="shortcut icon" href="/imageslayout/ssadvt_logo.ico">
        <link href="style/style1.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/table.js"></script>
        <link rel="stylesheet" type="text/css" href="css/calendar.css" >
        <link href="style/Table_content.css" type="text/css" rel="stylesheet" media="Screen"/>
        <script language="javascript" type="text/javascript" src="JS/calendar.js"></script>
        <link rel="stylesheet" href="datePicker/jquery.ui.all.css">
        <script type="text/javascript"  src="datePicker/jquery.ui.core.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.widget.js"></script>
        <script type="text/javascript" src="datePicker/jquery.ui.datepicker.js"></script>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main" style="table-layout: auto">
            <tr>

                <td></td>

            </tr>
            <%--<tr>
                <td><%@include file="/view/layout/header_layout.jsp" %> </td>

            </tr>--%>
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %></td>
            </tr>
           <%-- change here  ---%>
           <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
           <%-- change here  ---%>
            <tr>
                <td>
                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">
                        <form name="form1" method="post" action="complaintregisterCont.do" onsubmit="return verifyform()">
                            <table width="100%">
                                <tr>
                                    <td>
                                        <table width="100%" border="4">
                                            <tr>
                                                <td align="center" class="header_table" width="90%">Complaint MIS HTML VIEW</td>
                                                <td><%@include file="/layout/complaint_menu.jsp" %> </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="center">

                                        <DIV STYLE="overflow: auto; width: 990px; max-height:350px;padding:0px; margin-bottom:10px">
                                            <table id="table1" border="1" align="center" width="100%" class="reference">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    <th class="heading1" >Complaint No.</th>
                                                    <th class="heading">Pole No</th>
                                                    <th class="heading">Status</th>
                                                    <th class="heading">Compl. Type</th>
                                                    <th class="heading">Compl. Sub Type</th>
                                                    <th class="heading">Complaint Date</th>
                                                    <th class="heading">Posted By</th>
                                                    <th class="heading">Assigned for</th>
                                                    <th class="heading">Assigned Date</th>
                                                    <th class="heading">Complition Date</th>
                                                    <th class="heading">Remark</th>
                                                </tr>
                                                <c:forEach var="asso" items="${ComplaintRegisterList}" varStatus="loopCounter">
                                                    <tr>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                            <input type="hidden" id="com_reg_id${loopCounter.count}" name="com_reg_id" value="${asso.complaint_reg_no}">
                                                            <%--<input type="checkbox"  id="checkPrint${loopCounter.count}" name="checkPrint" value="" onclick="singleCheckAndUncheck(${loopCounter.count})">--%>
                                                            <input type="hidden"  id="is_check${loopCounter.count}" name="is_check" value="">
                                                            <input type="hidden" id="compalint_rev_no${loopCounter.count}" name="compalint_rev_no${loopCounter.count}" value="${asso.compalint_rev_no}">
                                                            <input type="hidden" id="ad_sub_type1${loopCounter.count}" name="ad_sub_type1${loopCounter.count}" value="${asso.ad_sub_type1}">
                                                            <input type="hidden" id="ad_sub_type2${loopCounter.count}" name="ad_sub_type2${loopCounter.count}" value="${asso.ad_sub_type2}">
                                                            <input type="hidden" id="sub_site_name${loopCounter.count}" name="sub_site_name${loopCounter.count}" value="${asso.sub_site_name}">
                                                            <input type="hidden" id="pole_id{loopCounter.count}" name="pole_id${loopCounter.count}" value="${asso.pole_id}">
                                                            <input type="hidden" id="sp_id${loopCounter.count}" name="sp_id${loopCounter.count}" value="${asso.sp_id}">
                                                        </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${asso.complaint_reg_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.site_nameM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.statusM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_typeM} </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_sub_typeM} </td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complaint_dateM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.posted_byM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.assigned_forM}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.assign_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.complition_date}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)">${asso.remarkM}</td>
                                                    </tr>
                                                </c:forEach>                                                
                                                <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>
                                                <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                                <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                                <%--   <input type="hidden" id="search" name="task" value="Search"> --%>
                                                <input type="hidden" id="search_compalint_date_from"name="search_compalint_date_from" value="${search_compalint_date_from}" size="15">
                                                <input type="hidden" id="search_compalint_date_to"name="search_compalint_date_to" value="${search_compalint_date_to}" size="15">
                                            </table>
                                        </DIV>


                                    </td>
                                </tr>
                                <%--</c:if>--%>                                
                            </table>
                        </form>

                    </div>

                </td>

            </tr>
            <tr>
                <td>
                    <jsp:include page="calendar_view"/>

                </td>
            </tr>
            <tr>
                <td><%@include file="/layout/footer.jsp" %></td>
            </tr>
        </table>
    </body>
</html>
