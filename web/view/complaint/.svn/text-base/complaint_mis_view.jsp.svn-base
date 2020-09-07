<%-- 
    Document   : complaint_mis_view
    Created on : Jun 30, 2015, 1:51:29 PM
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
        <script type="text/javascript" language="javascript">
            var popupwin;
            jQuery(function(){
                $("#pole_no").autocomplete("complaintregisterCont.do", {
            extraParams: {
                action: function() { return "getPole_No"},
                action1: function() { return $("#complaint_type option:selected").text();},
                action2: function() {return $("#sp_id").val();}
            }
        });

        $("#sp_name").autocomplete("complaintregisterCont.do", {
            extraParams: {
                action: function() { return "getSwitchingPoint"},
                action1: function() { return $("#feeder").val().split("#")[0];}
            }
        });
                $("#site_name").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                        action: function() { return "siteList";},
                        add_sub_type2: function() { return $("#ooh_ast2").val();}
                    }

                });

                $("#posted_by").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "PostedBy";}

                    }
                });

                $("#assigned_for").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "AssingedFor";}

                    }
                });
                $("#s_posted_by").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "PostedBy";}

                    }
                });
                $("#s_assigned_for").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "AssingedFor";}

                    }
                });
               $("#s_complaint_no").autocomplete("complaintregisterCont.do", {
                    extraParams: {
                         action: function() { return "ComplaintNo";}

                    }
                });

                $("#pole_no").result(function(event, data, formatted){
                    $.ajax({url: "complaintregisterCont.do?action=getPole_No_Id", data: "pole_no="+ data +"&q=", success: function(response_data) {
                        var id = response_data.split("_")[0];
                        var name = response_data.split("_")[1];
                        var i;
                        $("#sp_name").val(name);
                        $("#sp_id").val(id.split("#")[0]);
                        $("#switching_point").val(id);
                        selectDivisionZoneFeeder();


                    }
                });
                });

                $("#sp_name").result(function(event, data, formatted){
                    $.ajax({url: "complaintregisterCont.do?action=getSp_Id", data: "sp_name="+ data +"&q=", success: function(response_data) {
                        var id = response_data.split("_")[0];
                        //var name = response_data.split("#")[1];
                        var i;
                        //$("#sp_name").val(name);
                        $("#sp_id").val(id.split("#")[0]);
                        $("#switching_point").val(id);
                        selectDivisionZoneFeeder();


                    }
                });
                });
            });
            
            function selectZone(){
             var division = document.getElementById("division").value;
             if(division != 0){
             $(".zone"+division).show();}
             var x = document.getElementById("zone");
             //var txt;
             var i;
             var div_id;
             for (i = 0; i < x.length; i++) {
                 if(x.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 //txt = x.options[i].value;
                 div_id = (x.options[i].value).split("#")[1];
                 if(div_id != division){
                     $(".zone"+div_id).hide();
                 }
                 }
             }
             
         }

         function selectDivisionZone(){
             var feeder = document.getElementById("feeder").value;
             var z_id;
             if(feeder != "Select"){
                 z_id = feeder.split("#")[1];
                 //$("#zone").val(feeder.split("#")[1]);
             }
             var x = document.getElementById("zone");
             var i;
             var zone_id;
             var txt;
             for (i = 0; i < x.length; i++) {
                 if(x.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 txt = x.options[i].value;
                 zone_id = txt.split("#")[0];
                 if(zone_id == z_id){
                     $("#zone").val(txt);
                     $("#division").val(txt.split("#")[1]);
                     break;
                     //$(".zone"+zone_id).hide();
                 }
                 }
             }
         }

         function selectFeeder(){
             var zone = document.getElementById("zone").value;
             if(zone != "Select"){
                 $("#division").val(zone.split("#")[1]);
                 zone = zone.split("#")[0];
             $(".feeder"+zone).show();
             }
             var x = document.getElementById("feeder");
             //var txt;
             var i;
             var zone_id;
             for (i = 0; i < x.length; i++) {
                 if(x.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 //txt = x.options[i].value;
                 zone_id = (x.options[i].value).split("#")[1];
                 if(zone_id != zone){
                     $(".feeder"+zone_id).hide();
                 }
                 }
             }
         }

         function selectSwitchingPoint(){
             var feeder = document.getElementById("feeder").value;
             if(feeder != "Select"){
                 //$("#division").val(feeder.split("#")[1]);
                 feeder = feeder.split("#")[0];
             $(".sp"+feeder).show();
             }
             var x = document.getElementById("switching_point");
             //var txt;
             var i;
             var feeder_id;
             for (i = 0; i < x.length; i++) {
                 if(x.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 //txt = x.options[i].value;
                 feeder_id = (x.options[i].value).split("#")[1];
                 if(feeder_id != feeder){
                     $(".sp"+feeder_id).hide();
                 }
                 }
             }
         }

         function selectDivisionZoneFeeder(){
             var sp = document.getElementById("switching_point").value;
             var f_id;
             if(sp != "Select"){
                 f_id = sp.split("#")[1];
                 //$("#zone").val(feeder.split("#")[1]);
             }
             var x = document.getElementById("feeder");
             var i;
             var feeder_id;
             var txt;

             for (i = 0; i < x.length; i++) {
                 if(x.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 txt = x.options[i].value;
                 feeder_id = txt.split("#")[0];
                 if(feeder_id == f_id){
                     $("#feeder").val(txt);
                     selectDivisionZone();
                     //$("#division").val(txt.split("#")[1]);
                     break;
                     //$(".zone"+zone_id).hide();
                 }
                 }
             }

             /*for (i = 0; i < x.length; i++) {
                 if(x.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 txt = x.options[i].value;
                 zone_id = txt.split("#")[0];
                 if(zone_id == z_id){
                     $("#zone").val(txt);
                     $("#division").val(txt.split("#")[1]);
                     break;
                     //$(".zone"+zone_id).hide();
                 }
                 }
             }*/
         }

            function emptyPoleNo(){
             $("#pole_no").val("");
             $("#sp_name").val("");
             $("#pole_id").val("");
             $("#sp_id").val("");
             var complaint_type = $("#complaint_type option:selected").text();
             if(complaint_type == "Switching Point")
                 $("#sp_name").attr("readonly", true);
             else
                 $("#sp_name").attr("readonly", false);             
            }

            function ComplaintReportPdf() {
                var s_complaint_no = document.getElementById("s_complaint_no").value;

                var s_posted_by = document.getElementById("s_posted_by").value;
                var s_status = document.getElementById("s_status").value;
                var pole_no = document.getElementById("pole_no").value;
                var delayed_work;
                if(document.getElementById("delayed_comp").checked){
                    delayed_work="Y";
                }else{
                    delayed_work="N";
                }
                var sp_id = $("#sp_id").val();
                var s_assigned_for = document.getElementById("s_assigned_for").value;
                var queryString = "task=PRINTComplaintReport&s_posted_by="+s_posted_by+"&s_status="+s_status+"&s_assigned_for="+s_assigned_for+"&delayed_work="+delayed_work+"&s_complaint_no="+s_complaint_no+"&pole_no="+pole_no+"&sp_id="+sp_id;
                var url = "complaintMISCont.do?" + queryString;
                popupwin = openPopUp(url, "Complaint Report", 500, 750);
            }
            function ComplaintReportExcel() {
                var s_complaint_no = document.getElementById("s_complaint_no").value;
               //alert(s_complaint_no);
                var s_posted_by = document.getElementById("s_posted_by").value;
                var s_status = document.getElementById("s_status").value;
                var pole_no = document.getElementById("pole_no").value;
                var delayed_work;
                if(document.getElementById("delayed_comp").checked){
                    delayed_work="Y";
                }else{
                    delayed_work="N";
                }
                var sp_id = $("#sp_id").val();
                var s_assigned_for = document.getElementById("s_assigned_for").value;
                var queryString = "task=PRINTComplaintExcelReport&s_posted_by="+s_posted_by+"&s_status="+s_status+"&s_assigned_for="+s_assigned_for+"&delayed_work="+delayed_work+"&s_complaint_no="+s_complaint_no+"&pole_no="+pole_no+"&sp_id="+sp_id;
                var url = "complaintMISCont.do?" + queryString;
                popupwin = openPopUp(url, "Complaint Report", 500, 750);
            }

            function openPopUp(url, window_name, popup_height, popup_width) {
                var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=no, scrollbars=yes, status=no, dialog=yes, dependent=yes";
                return window.open(url ,window_name, window_features);
            }
            if (!document.all) {
                document.captureEvents (Event.CLICK);
            }
            document.onclick = function() {
                if (popupwin != null && !popupwin.closed) {
                    popupwin.focus();
                }
            }

            function htmlView(){
                //var serializedForm = $('#form2').serialize();                  //  alert(serializedForm);
                //var queryString = "requestprinrt=Alert Sheet HTML View&" + serializedForm;
                 var s_complaint_no = document.getElementById("s_complaint_no").value;
               //alert(s_complaint_no);
                var s_posted_by = document.getElementById("s_posted_by").value;
                var s_status = document.getElementById("s_status").value;
                var pole_no = document.getElementById("pole_no").value;
                var delayed_work;
                if(document.getElementById("delayed_comp").checked){
                    delayed_work="Y";
                }else{
                    delayed_work="N";
                }
                var s_assigned_for = document.getElementById("s_assigned_for").value;
                var sp_id = $("#sp_id").val();
                var queryString = "task=HTMLView&s_posted_by="+s_posted_by+"&s_status="+s_status+"&s_assigned_for="+s_assigned_for+"&delayed_work="+delayed_work+"&s_complaint_no="+s_complaint_no+"&pole_no="+pole_no+"&sp_id="+sp_id;
                var url = "complaintMISCont.do?" + queryString;
                window.open(url, '_blank');
            }

            $(document).ready(function() {
                //var x = document.getElementById("complaint_sub_type");
                var zone = document.getElementById("zone");
                var feeder = document.getElementById("feeder");
                var switching_point = document.getElementById("switching_point");
                var txt;
                var i;
                var id;
                /*for (i = 0; i < x.length; i++) {
                 if(x.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 //txt = x.options[i].value;
                 id = (x.options[i].value).split("#")[1];
                 x.options[i].className = id;
                 }
                }*/
                //var zone
                for (i = 0; i < zone.length; i++) {
                 if(zone.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 //txt = x.options[i].value;
                 id = (zone.options[i].value).split("#")[1];
                 zone.options[i].className = "zone" + id;
                 }
                }

                for (i = 0; i < feeder.length; i++) {
                 if(feeder.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 //txt = x.options[i].value;
                 id = (feeder.options[i].value).split("#")[1];
                 feeder.options[i].className = "feeder" + id;
                 }
                }

                for (i = 0; i < switching_point.length; i++) {
                 if(switching_point.options[i].value != "Select"){
                 //txt = txt + "\n" + x.options[i].value;
                 //txt = x.options[i].value;
                 id = (switching_point.options[i].value).split("#")[1];
                 switching_point.options[i].className = "sp" + id;
                 }
                }
            });
        </script>
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
           <%--<c:if test="${isSelectPriv eq 'Y'}">--%>
            <tr>
                <td>
                    <div id="main_div" class="maindiv" style="min-height: 600px ;max-height: auto">
                        <form name="form1" method="post" action="complaintMISCont.do" onsubmit="return verifyform()">
                            <table width="100%">
                                <tr>
                                    <td>
                                        <table width="100%" border="4">
                                            <tr>
                                                <td align="center" class="header_table" width="90%">Complain MIS</td>
                                                <td><%@include file="/layout/complaint_menu.jsp" %> </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <table width="100%" class="content">
                                            <tr class="heading">
                                                        <!--<th class="heading1">Switching Point Name</th>
                                                        <td>
                                                            <input type="text" name="switching_point" id="switching_point" disabled>
                                                        </td>
                                                        <th class="heading1">Pole No</th>
                                                        <td>
                                                        <input type="text" name="switching_point" id="switching_point" disabled>
                                                        </td>-->
                                                        <td style="text-align: center" colspan="3" class="heading">Complaint Type

                                                            <select id="complaint_type" name="complaint_type" onchange="emptyPoleNo();selectComplaint_sub_type();" >
                                                                <option value="0">All</option>
                                                                <c:forEach var="cmpl" items="${complaintTypeList}">
                                                                    <option value="${cmpl.key}">${cmpl.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                            Switching Point
                                                            <input class="input" type="text" id="sp_name" name="sp_name" value="" >
                                                            <select style="display: none" id="switching_point"
                                                                    name="switching_point"  title=""
                                                                    onchange="selectDivisionZoneFeeder()" >
                                                                <option value="Select">Select</option>
                                                                <c:forEach var="spList" items="${switchingPointList}">
                                                                    <option style="" class="" value="${spList.key}">${spList.value}</option>
                                                                </c:forEach>

                                                            </select>
                                                       Pole no.
                                                            <input class="input" type="text" id="pole_no" name="pole_no" value="" >
                                                        </td>
                                                    </tr>
                                                    <tr class="heading">
                                                        <td>
                                                            Division
                                                            <select id="division" name="division"
                                                                    onchange="selectZone();emptyPoleNo();"
                                                                    title="" >
                                                                <option value="0">Select</option>
                                                                <c:forEach var="dvsn" items="${divisionList}">
                                                                    <option  value="${dvsn.key}">${dvsn.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>                                                        
                                                        <td class="heading">
                                                            Zone
                                                            <select id="zone"
                                                                    name="zone"  title=""
                                                                    onchange="selectFeeder();emptyPoleNo();" >
                                                                <option value="Select">Select</option>
                                                                <c:forEach var="zon" items="${zoneList}">
                                                                    <option style="" class="" value="${zon.key}">${zon.value}</option>
                                                                </c:forEach>

                                                            </select>
                                                        </td>

                                                        <%--       <th class="heading1" >Site Name</th><td><input class="input" type="text" id="site_name" name="site_name" value="" onclick="fillAddSubSite()" > </td>   --%>

                                                        
                                                        <!--<td><input class="input" type="text" id="site_name" name="site_name" value="" disabled> </td>-->
                                                        <td class="heading">
                                                            Feeder
                                                            <select id="feeder"
                                                                    name="feeder"  title=""
                                                                    onchange="selectDivisionZone();selectSwitchingPoint();emptyPoleNo();" >
                                                                <option value="Select">Select</option>
                                                                <c:forEach var="feeder" items="${feederList}">
                                                                    <option class="" style="" value="${feeder.key}">${feeder.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                        <%--   <td><input class="input" type="text" id="ad_sub_site" name="ad_sub_site" value=""  size="10" disabled></td>    --%>

                                                        <%-- <th class="heading1" >Sub Site Name</th>  --%>
                                                       <td colspan="2" style="display: none">
                                                            <select style="display: none" id="ad_sub_site_name" name="ad_sub_site_name"  >

                                                            </select>
                                                        </td>

                                                    </tr>
                                            <tr>
                                                <td class="heading" colspan="">
                                                    Status <select id="s_status" name="s_status">
                                                        <option value="0"<c:if test="${s_status ==0}">selected</c:if>>Select</option>
                                                        <c:forEach var="status" items="${statuslist}">
                                                            <option  value="${status.key}" <c:if test="${s_status ==status.key}">selected</c:if>>${status.value}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td >
                                                <td class="heading" style="display: none">
                                                    posted By<input type="text" style="display: none" name="s_posted_by" id="s_posted_by" value="${s_posted_by}">
                                                </td>
                                                <td class="heading" colspan="" >
                                                    Assigned For<input type="text" name="s_assigned_for" id="s_assigned_for" value="${s_assigned_for}" size="10">
                                                </td>
                                                <td class="heading" colspan="">
                                                    delayed Complition<input type="checkbox" name="delayed_comp" id="delayed_comp">
                                                </td>

                                            </tr>
                                            <tr>
                                                <td class="heading" style="display: none">
                                                    Complaint Date From  <input type="text" style="display: none" id="search_compalint_date_from"name="search_compalint_date_from" value="${search_compalint_date_from}" size="15">
                                                    <%--    <a href="#" onclick="setYears(1947,2022);showCalender(this,'search_compalint_date_from')">
                                                            <img alt=""  src="images/calender.png">
                                                        </a>   --%>
                                                </td>
                                                <td class="heading" style="display: none">
                                                    Complaint Date To <input type="text" style="display: none" id="search_compalint_date_to"name="search_compalint_date_to" value="${search_compalint_date_to}" size="15">
                                                    <%--  <a href="#" onclick="setYears(1947,2022);showCalender(this,'search_compalint_date_to')">
                                                          <img alt=""  src="images/calender.png">
                                                      </a>   --%>
                                                </td>
                                                     <td class="heading" style="display: none">
                                                         Complaint No <input style="display: none" type="text" id="s_complaint_no" name="s_complaint_no" id="s_assigned_for" value="${s_complaint_no}" size="10">
                                                </td>
                                                <td class="heading" colspan="3">
                                                    <input type="submit" id="search" name="task" value="Search" style="display: none">
                                                    <input type="submit" id="clear" name="task" value="clear" onclick="setvalues()" style="display: none">
                                                    <input class="pdf_button" type="button" id="viewPdf" name="viewPdf" value="" onclick="ComplaintReportPdf()">
                                                    <input class="button" type="button" id="viewExcel" name="viewExcel" value="Excel" onclick="ComplaintReportExcel()">
                                                    <input class="button" type="button" id="viewHTML" name="viewHTML" value="HTML" onclick="htmlView()">
                                                    <input class="button" type="reset" id="reset" name="reset" value="Reset">
                                                </td>
                                            </tr>


                                        </table>
                                    </td>
                                </tr>


                                <tr>
                                    <td align="center">
                                        <DIV STYLE="overflow: auto; width: 990px; max-height:350px;padding:0px; margin-bottom:10px; display: none">
                                            <table id="table1" border="1" align="center" width="100%" class="reference">
                                            <c:forEach var="asso" items="${ComplaintHistoryReportList}" varStatus="loopCounter">
                                                 <tr>
                                                    <th class="heading1" >Complaint No.</th>
                                                    <td colspan="5">${asso.complaint_reg_no}</td>
                                                 </tr>
                                                 <tr>
                                                    <th class="heading">Complaint Type</th>
                                                    <th class="heading">Complaint sub Type</th>
                                                    <th class="heading">Status</th>

                                                    <th class="heading">Posted By</th>
                                                    <th class="heading">Assigned for</th>
                                                    <th class="heading">Complaint Date</th>
                                                    <th class="heading">Complition Date</th>
                                                  <%--  <th class="heading">Remark</th>   --%>
                                                </tr>
                                                    <tr>
                                                       <td >${asso.complaint_typeM} </td>
                                                       <td >${asso.complaint_sub_typeM} </td>
                                                       <td>${asso.statusM}</td>
                                                        <td>${asso.posted_byM}</td>
                                                        <td>${asso.assigned_forM}</td>
                                                       <td>${asso.complaint_dateM}</td>
                                                        <td>${asso.complition_date}</td>
                                                    </tr>
                                                <tr>
                                                     <th class="heading">S.No.</th>
                                                    <th class="heading">Complaint Feedback Type</th>
                                                    <th class="heading">Feedback Date</th>
                                                    <th class="heading" colspan="4">Feedback Time</th>
                                                </tr>

                                              <tr>
                                                     <c:forEach var="feedback" items="${asso.feebackList}" varStatus="loopCounter">

                                                      <td>${loopCounter.count}
                                                      </td>
                                                       <td>${feedback.feedback_type}</td> ${feebackList}
                                                       <td>${feedback.feedback_date}</td>
                                                       <td colspan="4" >${feedback.feedback_hr} ${feedback.feedback_min} ${feedback.feedback_time}</td>
                                                    </tr>
                                          </c:forEach>
                                              </c:forEach>

                                                <tr>
                                                    <td align='center' colspan="11">
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
                                <tr>
                                <input type="hidden" id="sp_id" name="sp_id" value="">
                                <input type="hidden" id="pole_id" name="pole_id" value="">
                                <tr>

                            </table>
                        </form>

                    </div>

                </td>

            </tr>
             <%--</c:if>--%>
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
