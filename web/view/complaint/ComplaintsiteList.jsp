<%--
    Document   : sitelist
    Created on : Jun 10, 2013, 5:59:59 PM
    Author     : Shruti
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Multiple Selections: List of ${param.adSubType2} sites for ${param.city_name} city.</title>
        <link rel="stylesheet" type="text/css" href="style/style1.css" /> <%-- It is used in conjuction with jquery.autocomplete.js --%>
        <link rel="stylesheet" type="text/css" href="style/Table_content.css" />
        <link rel="stylesheet" type="text/css" href="css/calendar.css" />
        <script type="text/javascript" src="JS/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="JS/jquery.autocomplete.js"></script>
        <script type="text/javascript" src="JS/calendar.js"></script>
        <script type="text/javascript" language="javascript">
            jQuery(function(){
                $("#organisation_name").autocomplete("orgNameListCont.do", {
                    extraParams: {
                        action: function() { return "orgNameList";},
                        org_type_name: function() { return "Associate";}
                    }
                });
                $("#structure_name").autocomplete("siteDetailCont.do", {
                    extraParams: {
                        action1: function() { return "getStructureNameList"},
                        action2: function() { return document.getElementById("ooh_ast2").value;}
                    }
                });
                $("#client").autocomplete("orgNameListCont.do", {
                    extraParams: {
                        action: function() { return "orgNameList";},
                        org_type_name: function() { return "Client";}
                    }
                });
                $("#client").result(function(event, data, formatted){
                    getQuotationlist();
                });
                $("#state_name").autocomplete("stateNameCont.do");
                $("#city_name").autocomplete("associatedealCont.do", {
                    extraParams: {
                        action: function() { return "City";}

                    }
                });
                $("#state_name").result(function(event, data, formatted){
                    document.getElementById("city_name").value = ""
                    $("#city_name").flushCache();
                    document.getElementById("city_name").focus();
                });

            });


            function getQuotationlist(){
                var organisation_name = $("#client").val();
                var queryString = "task=quotationList&organisation_name=" + organisation_name;
                $.ajax({url: "clientOrgCont.do", async: true, data: queryString, success: function(response_data) {
                        $("#org_qtn").html("<option selected>Select</option>");
                        var arr = response_data.split("&#;");
                        var i;
                        for(i = 0; i < arr.length - 1; i++) {
                            var opt        = document.createElement("option");
                            var val=  arr[i].split("rev_no");
                            opt.text = val[0];
                            opt.value =val[0];
                            document.getElementById("org_qtn").options.add(opt);
                        }
                        if(i == 1) {
                            document.getElementById("org_qtn").value = arr[0];
                            // getQuotationNoList();
                        } else {
                            document.getElementById("org_qtn").focus();
                        }
                        document.getElementById("org_qtn").disabled=false;
                        document.getElementById("rev_no").disabled=false;
                        if(arr.length>1){
                            document.getElementById("searchByClient").disabled= false;
                        }
                    }
                });
            }

            function getrevisionNum(){
                var qtn_no= $("#org_qtn").val();
                var queryString = "task=RevisionList&quotation_no=" + qtn_no;
                $.ajax({url: "clientOrgCont.do", async: true, data: queryString, success: function(response_data) {
                        $("#rev_no").html(" ");
                        var arr = response_data.split("&#;");
                        var i;
                        for(i = 0; i < arr.length - 1; i++) {
                            var opt        = document.createElement("option");
                            //  var val=  arr[i].split("rev_no");
                            opt.text =arr[i];
                            //opt.value =val[0]+"/"+val[1];
                            document.getElementById("rev_no").options.add(opt);
                        }
                        if(i == 1) {
                            document.getElementById("rev_no").value = arr[0];
                            // getQuotationNoList();
                        } else {
                            document.getElementById("rev_no").focus();
                        }
                        document.getElementById("city_name").value="";
                    }
                });

            }
            var popupwin = null;    // Holds id of "pop up window".
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
            function checkAllItems() {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                var count = 0;
                for(var i = 1; i <= noOfRowsTraversed; i++) {
                    if(!document.getElementById("cb_site_id" + i).disabled) {
                        document.getElementById("cb_site_id" + i).checked = true;
                        count++;
                    }
                }
                document.getElementById("noOfSelectedItems").value = count;
                if(count > 0) {
                    document.getElementById("submitSelectedSites").disabled = false;
                    document.getElementById("ooh_uncheckAll").disabled = false;
                }
                document.getElementById("ooh_checkAll").disabled = true;
            }
            function uncheckAllItems() {
                var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
                for(var i = 1; i <= noOfRowsTraversed; i++) {
                    document.getElementById("cb_site_id" + i).checked = false;
                }
                document.getElementById("noOfSelectedItems").value = 0;
                document.getElementById("submitSelectedSites").disabled = true;
                document.getElementById("ooh_uncheckAll").disabled = true;
                document.getElementById("ooh_checkAll").disabled = false;
            }
            function incrementByOne(id) {
                var count = parseInt(document.getElementById("noOfSelectedItems").value, 10);
                if(document.getElementById(id).checked == true) {
                    count++;
                } else {
                    count--;
                }
                document.getElementById("noOfSelectedItems").value = count;
                if(count == 0) {
                    document.getElementById("submitSelectedSites").disabled = true;
                    document.getElementById("ooh_uncheckAll").disabled = true;
                    document.getElementById("ooh_checkAll").disabled = false;
                } else {
                    if(count == document.getElementById("noOfRowsTraversed").value) {
                        document.getElementById("submitSelectedSites").disabled = false;
                        document.getElementById("ooh_uncheckAll").disabled = false;
                        document.getElementById("ooh_checkAll").disabled = true;
                    } else {
                        document.getElementById("submitSelectedSites").disabled = false;
                        document.getElementById("ooh_uncheckAll").disabled = false;
                        document.getElementById("ooh_checkAll").disabled = false;
                    }
                }
            }
            function showSiteImage(siteName) {
                var queryString = "task=ShowSiteImage&site_name=" + siteName;
                var url = "showImageCont.do?" + queryString;
                popupwin = openPopUp(url, "Hoarding Image", 570, 800);
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
            function setValueAndSubmit(id) {
                var searchBy;
                var city= $("#city_name").val();
                //                if(myLeftTrim(city)==0 || city=='No Such City Exists.'){
                //                    alert("First Select The city");
                //                    return;
                //                }
                if(id == 'searchBySiteNmae') {
                    searchBy = 'siteName';
                } else if(id=='searchByClient'){

                    var qtn= $("#org_qtn").val();
                    if(qtn=='Select'){
                        alert("First Select The Quotation No.");
                        return;
                    }
                    searchBy = 'clientOrg';
                }else if(id=='searchByAvailability'){
                    searchBy = 'Availability';
                }
                else if(id=='searchByStructure'){
                    searchBy = 'Structure';
                }
                else{
                    searchBy = 'locationAndPhasing';
                }
                var total_site=    document.getElementById("totalSites").value;
                var i;
                var site_ids ="";
                for(i=1; i<=total_site; i++){
                    if(document.getElementById("cb_site_id"+i).checked){

                        site_ids=  site_ids + document.getElementById("cb_site_id"+i).value+"#";
                    }
                }
                document.getElementById("pre_total_sites").value=site_ids;
                document.getElementById("searchBy").value = searchBy;

                document.searchByForm.submit();
            }

            $(document).ready(function (){
                var total_site=    document.getElementById("totalSites").value;
                var i;
                var count=0;
                for(i=1; i<=total_site; i++){
                    if(document.getElementById("cb_site_id"+i).checked){
                        count++;
                    }
                }
                if(count>0){
                    document.getElementById("noOfSelectedItems").value=count;
                    document.getElementById("submitSelectedSites").disabled = false;
                }
            });
            function enableDiv(id) {
                if(id == 'rb_siteName') {
                    document.getElementById("div_siteName").style.visibility = 'visible';
                    document.getElementById("div_locAndPhasing").style.visibility = 'hidden';
                    document.getElementById("div_client").style.visibility = 'hidden';
                    document.getElementById("div_availability").style.visibility = 'hidden';
                    document.getElementById("div_structure").style.visibility = 'hidden';
                    //document.getElementById("city_name").value="Jabalpur";
                    document.getElementById("siteName").focus();
                } else if(id=='rb_locAndPhasing') {
                    document.getElementById("div_locAndPhasing").style.visibility = 'visible';
                    document.getElementById("div_siteName").style.visibility = 'hidden';
                    document.getElementById("div_client").style.visibility = 'hidden';
                    document.getElementById("div_availability").style.visibility = 'hidden';
                    document.getElementById("div_structure").style.visibility = 'hidden';
                    // document.getElementById("city_name").value="Jabalpur";
                    document.getElementById("location").focus();
                }else if(id=='rb_availability') {
                    document.getElementById("div_availability").style.visibility = 'visible';
                    document.getElementById("div_locAndPhasing").style.visibility = 'hidden';
                    document.getElementById("div_siteName").style.visibility = 'hidden';
                    document.getElementById("div_client").style.visibility = 'hidden';
                    document.getElementById("div_structure").style.visibility = 'hidden';
                    // document.getElementById("city_name").value="Jabalpur";
                    document.getElementById("date_from").focus();
                }
                else if(id=='rb_structure') {
                    document.getElementById("div_structure").style.visibility = 'visible';
                    document.getElementById("div_availability").style.visibility = 'hidden';
                    document.getElementById("div_locAndPhasing").style.visibility = 'hidden';
                    document.getElementById("div_siteName").style.visibility = 'hidden';
                    document.getElementById("div_client").style.visibility = 'hidden';
                    // document.getElementById("city_name").value="Jabalpur";
                    document.getElementById("structure_name").focus();
                }
                else {
                    document.getElementById("div_locAndPhasing").style.visibility = 'hidden';
                    document.getElementById("div_siteName").style.visibility = 'hidden';
                    document.getElementById("div_client").style.visibility = 'visible';
                    document.getElementById("div_availability").style.visibility = 'hidden';
                    document.getElementById("div_structure").style.visibility = 'hidden';
                    document.getElementById("client").focus();

                }
            }
            function checkSearchBy() {
                var searchBy = document.getElementById("searchBy").value;
                if(searchBy == 'siteName' || searchBy == '') {
                    enableDiv('rb_siteName');
                    document.getElementById("rb_siteName").checked = true;
                    if(searchBy == '') {
                        document.getElementById("cb_siteName_ot").checked = true;
                        document.getElementById("cb_location_ot").checked = true;
                        document.getElementById("cb_phasing_ot").checked = true;
                    }
                } else if(searchBy=='clientOrg'){
                    enableDiv('rb_client');
                    document.getElementById("rb_client").checked = true;
                    getQuotationlist();
                }
                else if(searchBy=='Availability'){
                    enableDiv('rb_availability');
                    document.getElementById("rb_availability").checked = true;

                }
                else if(searchBy=='Structure'){
                    enableDiv('rb_structure');
                    document.getElementById("rb_structure").checked = true;

                }
                else{
                    enableDiv('rb_locAndPhasing');
                    document.getElementById("rb_locAndPhasing").checked = true;
                }
            }

            function checkOrgType() {
                if(document.getElementById("org_type").value == 'Associate') {
                    document.getElementById("organisation_name").disabled = false;
                    document.getElementById("organisation_name").style.backgroundColor = 'white';
                    document.getElementById("organisation_name").focus();
                } else {
                    document.getElementById("organisation_name").disabled = true;
                    document.getElementById("organisation_name").style.backgroundColor = 'lightgrey';
                }
            }
            function displayErrorMsg(message, msgBgColor) {
                var divContents = "<h1 align='center'>Error Occurred</h1>";
                divContents += "<table align='center'><tr><td>";
                divContents += "<label style='background-color: white; color: " + msgBgColor + "; font-size: large'>" + message + "</label>";
                divContents += "</td></tr><tr><td>&nbsp;</td></tr><tr><td align='center'>";
                divContents += "<input type='button' value='Close' onclick='self.close();'>";
                divContents += "</td></tr></table>";
                $('#div_multipleSelections').html(divContents);
            }
            function submitParent() {
                var element1 = opener.document.createElement("input");
                element1.type = "hidden";
                element1.name = "task";
                element1.value = "TraverseOnly";
                opener.document.forms[2].appendChild(element1);
                opener.document.forms[2].ho_lowerLimit.value = 0;   // Show records from starting.
                opener.document.forms[2].submit();
            }
            function submitChild() {
                document.getElementById("submitSelectedSites").disabled = true;
                var val="" ;
                $('input:checkbox[name=cb_site_id]:checked').each(function(i){
                    var site_name=   $(this).attr("id").replace("cb_site_id", "site_name");
                    var address=   $(this).attr("id").replace("cb_site_id", "location");
                    var site=   $("#"+site_name).val();
                    var location=   $.trim($("#"+address).html());
                    var site=site+' , '+location;
                    val += site+"#&;";
                });
                opener.document.getElementById("text").type="text";
                opener.document.getElementById("text").value = val;
                opener.document.getElementById("text").focus();
                self.close()
            }

            function enableMainDiv() {
                document.getElementById("div_animationLoading").style.display = 'none';
                document.getElementById("div_multipleSelections").style.display = '';
            }

            function setcolour(id){
                id= "tr_row"+id;
                var count_row=document.getElementById('multipleselection').getElementsByTagName('tr').length;
                var i=1;
                for(i=1;i<count_row; i++){
                    $("#tr_row"+i).css('background-color', '');
                }
                $("#"+id).css('background-color', 'yellowgreen');
            }
            function showHideNewQtDiv(){//                alert(id);
                var buttun_value = document.getElementById("quickSiteDivStatus").value;
                var height = 135;
                if(buttun_value == "Show Create New"){
                    document.getElementById("quickSiteDiv").style.visibility = 'visible';
                    document.getElementById("quickSiteDiv").style.height = height + 'px';
                    document.getElementById("quickSiteDivStatus").value = "Hide Create New";
                }else{
                    document.getElementById("quickSiteDiv").style.visibility = 'hidden';
                    document.getElementById("quickSiteDiv").style.height = '00px';
                    document.getElementById("quickSiteDivStatus").value = "Show Create New";
                }
            }
        </script>
    </head>
    <body onload="enableMainDiv();checkSearchBy();">
        <div id="div_animationLoading">
            <table align="center" style="vertical-align: middle;">
                <tr>
                    <td align="center">
                        <img src="images/Animation_Loading.gif" alt="images/Animation_Loading.gif">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label style="font-size: large; font-weight: bold;">Please wait loading data ...</label>
                    </td>
                </tr>
            </table>
        </div>
        <div id="div_multipleSelections" style="display: none;">
            <table id="table0"  align="center" width="100%" class="reference">
                <tr>
                    <td>
                        <table  class="header_table" width="100%">
                            <tr style="font-size:larger ;font-weight: 700;" align="center">
                                <td>
                                    List of ${param.adSubType2}  sites of ${param.organisation_name} for ${param.city_name} city.
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td>
                        <DIV STYLE=" height: 470px;padding:0px; margin: 0px">
                            <table id="table1" border="0" align="center"  width="100%">
                                <tr>
                                    <td colspan="6">
                                        <form name="searchByForm" action="SiteListCont" method="POST">
                                            <table style="background-color: lightsteelblue" width="100%">
                                                <tr>
                                                    <td>
                                                        <b>Search By</b>
                                                        <input type="radio" id="rb_siteName" name="rb_searchBy" value="siteName" onclick="enableDiv(id);"> Site Name & HIN
                                                        <input type="radio" id="rb_locAndPhasing" name="rb_searchBy" value="locAndPhasing" onclick="enableDiv(id);"> Location & Facing
                                                        <input type="radio" id="rb_client" name="rb_searchBy" value="clientOrg" onclick="enableDiv(id);"> Client
                                                        <input type="radio" id="rb_availability" name="rb_searchBy" value="availability" onclick="enableDiv(id);">Availability
                                                        <input type="radio" id="rb_structure" name="rb_searchBy" value="structure" onclick="enableDiv(id);">Structure
                                                        <%--&nbsp;&nbsp;<b>Org Type</b>--%>
                                                        <select id="org_type" name="org_type" onchange="checkOrgType();" style="display:${display}">
                                                            <option value="Home" ${param.org_type eq 'Home' ? 'SELECTED' : ''}>Home</option>
                                                            <option value="All" ${param.org_type eq 'All' ? 'SELECTED' : ''}>All</option>
                                                            <option value="Associate" ${param.org_type eq 'Associate' ? 'SELECTED' : ''}>Associate</option>
                                                        </select>
                                                        <%-- <b>Org Name</b--%>
                                                        <input type="text" id="organisation_name" name="organisation_name" value="${param.organisation_name}"
                                                               style="background-color:  ${param.organisation_name != null ? 'whilte' : 'lightgrey'};display:${display}"
                                                               ${param.organisation_name != null ? '' : 'disabled'} >

                                                        <input type="hidden" name="task" value="getAllSiteDetails">
                                                        <input type="hidden" name="selectionType" value="multipleSelection">
                                                        <input type="hidden" id="searchBy" name="searchBy" value="${param.searchBy}">
                                                        <input type="hidden" name="adSubType1" value="${param.adSubType1}">
                                                        <input type="hidden" name="adSubType2" value="${param.adSubType2}">
                                                        <input type="hidden" id="display" name="display" value="${display}">
                                                         <input type="hidden" id="display_type" name="display_type" value="${display_type}">
                                                        <input type="hidden" id="pre_total_sites" name="pre_total_sites">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div id="div_searchBy" style="width: auto; height: 30px;">
                                                            <div id="div_siteName" style="position: absolute; visibility: hidden;">
                                                                <table>
                                                                    <tr>
                                                                        <td>
                                                                            <b>Site Name</b>
                                                                            <input type="text" id="siteName" name="siteName" value="${param.siteName}">
                                                                            <select id="look_siteName" name="look_siteName" onfocus="setFieldBgColor('look_siteName', 'bisque')" onblur="setFieldBgColor('look_siteName', '');">
                                                                                <option ${param.look_siteName == "Anywhere" ? "SELECTED" : ""}>Anywhere</option>
                                                                                <option ${param.look_siteName == "Starts With" ? "SELECTED" : ""}>Starts With</option>
                                                                                <option ${param.look_siteName == "Ends With" ? "SELECTED" : ""}>Ends With</option>
                                                                            </select>
                                                                            <input type="checkbox" id="cb_siteName_ot" name="cb_siteName_ot" value="ASC" ${param.cb_siteName_ot == "ASC" ? "CHECKED" : ""}>ASC&nbsp;&nbsp;&nbsp;
                                                                            <b>HIN:</b> <input type="text" id="hin_num" name="hin_num" value="${param.hin_num}">
                                                                            <input class="button" type="button" id="searchBySiteNmae" name="searchBySiteNmae" value="Search" onclick="setValueAndSubmit(id);">
                                                                            <input class="button" type="button" id="quickSiteDivStatus" name="quickSiteDivStatus" value="Show Create New" onclick="showHideNewQtDiv();" >
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                            <div id="div_locAndPhasing" style="position: absolute; visibility: hidden;">
                                                                <table>
                                                                    <tr>
                                                                        <td>
                                                                            <b>Location</b>&nbsp;&nbsp
                                                                            <input type="text" id="location" name="location" value="${param.location}">
                                                                            <select id="look_location" name="look_location" onfocus="setFieldBgColor('look_location', 'bisque')" onblur="setFieldBgColor('look_location', '');">
                                                                                <option ${param.look_location == "Anywhere" ? "SELECTED" : ""}>Anywhere</option>
                                                                                <option ${param.look_location == "Starts With" ? "SELECTED" : ""}>Starts With</option>
                                                                                <option ${param.look_location == "Ends With" ? "SELECTED" : ""}>Ends With</option>
                                                                            </select>
                                                                            <input type="checkbox" id="cb_location_ot" name="cb_location_ot" value="ASC" ${param.cb_location_ot == "ASC" ? "CHECKED" : ""}>ASC&nbsp;&nbsp;&nbsp;
                                                                            <b>Facing</b>
                                                                            <input type="text" id="phasing" name="phasing" value="${param.phasing}">
                                                                            <select id="look_phasing" name="look_phasing" onfocus="setFieldBgColor('look_phasing', 'bisque')" onblur="setFieldBgColor('look_phasing', '');">
                                                                                <option ${param.look_phasing == "Anywhere" ? "SELECTED" : ""}>Anywhere</option>
                                                                                <option ${param.look_phasing == "Starts With" ? "SELECTED" : ""}>Starts With</option>
                                                                                <option ${param.look_phasing == "Ends With" ? "SELECTED" : ""}>Ends With</option>
                                                                            </select>
                                                                            <input type="checkbox" id="cb_phasing_ot" name="cb_phasing_ot" value="ASC" ${param.cb_phasing_ot == "ASC" ? "CHECKED" : ""}>ASC&nbsp;&nbsp;&nbsp;
                                                                            <input class="button" type="button" id="searchByLocAndPhasing" name="searchByLocAndPhasing" value="Search" onclick="setValueAndSubmit(id);">
                                                                            <input class="button" type="button" id="quickSiteDivStatus" name="quickSiteDivStatus" value="Show Create New" onclick="showHideNewQtDiv();" >
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                            <div id="div_client" style="position: absolute; visibility: hidden;">
                                                                <table>
                                                                    <tr>
                                                                        <td>
                                                                            <b>Client</b><input type="text" id="client" name="client" value="${client}" size="35" >
                                                                            <b>Quotation</b><select id="org_qtn" name="org_qtn" disabled onchange="getrevisionNum()">

                                                                            </select>
                                                                            <b>Rev No.</b><select id="rev_no" name="rev_no" disabled>
                                                                            </select>
                                                                            <input class="button" type="button" id="searchByClient" name="searchByClient" value="Search" onclick="setValueAndSubmit(id);" disabled>
                                                                            <input class="button" type="button" id="quickSiteDivStatus" name="quickSiteDivStatus" value="Show Create New" onclick="showHideNewQtDiv();" >
                                                                        </td>
                                                                    </tr>
                                                                </table>

                                                            </div>
                                                            <div id="div_availability" style="position: absolute; visibility: hidden;">
                                                                <table>
                                                                    <tr>
                                                                        <td>
                                                                            <b> Av. From</b>
                                                                            <input class="input" type="text" id="date_from" name="date_from" value="${date_from}" size="10">
                                                                            <a href="#" onclick="setYears(1947,2022);showCalender(this,'date_from')">
                                                                                <img alt=""  src="images/calender.png">
                                                                            </a>
                                                                            <b> Av. To</b>
                                                                            <input class="input" type="text" id="date_to" name="date_to" value="${date_to}" size="10">
                                                                            <a href="#" onclick="setYears(1947,2022);showCalender(this,'date_to')">
                                                                                <img alt=""  src="images/calender.png">
                                                                            </a>
                                                                            Status
                                                                            <select id="status" name="status">
                                                                                <option <c:if test="${status == 'All'}">selected</c:if>>All</option>
                                                                                <option <c:if test="${status == 'booked'}">selected</c:if>>Booked</option>
                                                                                <option <c:if test="${status == 'full_vacant'}">selected</c:if>>Full Vacant</option>
                                                                                <option <c:if test="${status == 'vacant'}">selected</c:if>>Vacant</option>
                                                                            </select>

                                                                            <b>Likely to Extended Site</b>
                                                                            <select id="like_extend" name="like_extend">
                                                                                <option ${like_extend=='All'?'selected':''}>All</option>
                                                                                <option ${like_extend=='Y'?'selected':''}>Yes</option>
                                                                                <option ${like_extend=='N'?'selected':''}>No</option>
                                                                            </select>
                                                                            <input class="button" type="button" id="searchByAvailability" name="searchByAvailability" value="Search" onclick="setValueAndSubmit(id);">
                                                                            <input class="button" type="button" id="quickSiteDivStatus" name="quickSiteDivStatus" value="Show Create New" onclick="showHideNewQtDiv();" >
                                                                        </td>
                                                                    </tr>
                                                                </table>

                                                            </div>
                                                            <div id="div_structure" style="position: absolute; visibility: hidden;">
                                                                <table>
                                                                    <tr>
                                                                        <td>
                                                                            <b>Structure Name</b>
                                                                            <input  type="text" id="structure_name" name="structure_name" value="${structure_name}">
                                                                            <input class="button" type="button" id="searchByStructure" name="searchByStructure" value="Search" onclick="setValueAndSubmit(id);">
                                                                            <input class="button" type="button" id="quickSiteDivStatus" name="quickSiteDivStatus" value="Show Create New" onclick="showHideNewQtDiv();" >
                                                                        </td>
                                                                    </tr>
                                                                </table>

                                                            </div>
                                                        </div>
                                                        <input type="checkbox" id="cb_showOnlyAvailable" name="cb_showOnlyAvailable" value="true" ${not empty param.cb_showOnlyAvailable ? 'CHECKED' : ''}>
                                                        <b>Only Avail.</b>
                                                        <b>State</b><input type="text" id="state_name" name="state_name" value="${param.state_name}" style="display:none">
                                                        <b>City</b><input type="text"  id="city_name" name="city_name" value="${param.city_name}">
                                                        &nbsp;&nbsp;<b>Total Sites</b>
                                                        <input type="text" id="totalSites" value="${noOfRowsTraversed}" style="background-color: gainsboro; text-align: center;" size="2" readonly>
                                                    </td>
                                                </tr>
                                            </table>
                                        </form>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left">
                                        <div align="left" id="quickSiteDiv" style="overflow: auto;visibility: visible; height: 0px">
                                            <jsp:include page="adQuickSitePopUpView"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <div style="overflow: auto; width: 1200px; height: 380px;">
                                            <form id="multipleSelectionsForm" name="multipleSelectionsForm" method="POST" action="#" onsubmit="">
                                                <table id="multipleselection" style=" border-collapse: collapse;" border="1" align="center"  width="100%">
                                                    <tr>
                                                        <th class="heading1">S.No.</th>
                                                        <th class="heading1">Accept</th>
                                                        <th class="heading1">View Image</th>
                                                        <th class="heading1">Site Name</th>
                                                        <th class="heading1" align="center">File No.</th>
                                                        <th class="heading1">
                                                            <table border="0" width="100%">
                                                                <tr>
                                                                    <th style="width: 60px">Structure Name</th>
                                                                    <th style="width: 30px">Str Area</th>
                                                                    <th style="width: 30px">Qty</th>
                                                                    <th style="width: 30px">SW</th>
                                                                    <th style="width: 40px">Status</th>
                                                                    <th style="width: 60px">Available From</th>
                                                                </tr>
                                                            </table>
                                                        </th>
                                                        <th class="heading1">Site Location</th>
                                                        <th class="heading1">Site Facing</th>


                                                    </tr>
                                                    <c:forEach var="site" items="${siteList}" varStatus="outerLoopCounter">
                                                        <tr id="tr_row${outerLoopCounter.count}"  class="${outerLoopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="" align="center">${outerLoopCounter.count}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="" align="center" >
                                                                <input type="checkbox" id="cb_site_id${outerLoopCounter.count}" name="cb_site_id" value="${site.site_id}"
                                                                       onclick="incrementByOne(id);" ${site.selected == true ? 'disabled': ''} ${site.isChecked}>
                                                                <input type="hidden" id="site_name${outerLoopCounter.count}" value="${site.site_name}">
                                                            </td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="setcolour('${outerLoopCounter.count}');" align="center"><a id="${site.site_name}" href="#" onclick="showSiteImage(id);">View Image</a></td>

                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="">${site.site_name}</td>

                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="">${site.file_no}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="">
                                                                <table border="0" style="border-collapse: collapse; border-left-style: hidden; border-right-style: hidden;" width="100%">
                                                                    <c:forEach var="siteStructure" items="${site.siteStrDetailsList}" varStatus="innerLoopCounter">
                                                                        <tr>
                                                                            <td style="width: 60px" align="left">${siteStructure.structure}</td>
                                                                            <td style="width: 30px" align="right"><fmt:formatNumber value="${siteStructure.total_area}" maxFractionDigits="2" minFractionDigits="2"/></td>
                                                                            <td style="width: 30px" align="center">${siteStructure.quantity}</td>
                                                                            <td style="width: 30px" align="center">${siteStructure.sell_whole}</td>
                                                                            <td style="width: 100px">
                                                                                <table border="0" style="border-collapse: collapse; border-left-style: hidden; border-right-style: hidden;" width="100%">
                                                                                    <c:forEach var="subSite" items="${siteStructure.subSiteList}">
                                                                                        <tr>
                                                                                            <td bgcolor="${subSite.color}">${subSite.ss_status}</td>
                                                                                            <td align="center">${subSite.available_from}</td>
                                                                                        </tr>
                                                                                    </c:forEach>
                                                                                </table>
                                                                            </td>
                                                                        </tr>
                                                                    </c:forEach>
                                                                </table>
                                                            </td>

                                                            <td id="location${outerLoopCounter.count}" onclick="">${site.site_location}</td>
                                                            <td id="t1c${IDGenerator.uniqueID}" onclick="">${site.site_phasing}</td>

                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                                <input type="hidden" name="advertising_type_name" value="OOH Display">
                                                <input type="hidden" name="ooh_ast1" value="${param.adSubType1}">
                                                <input type="hidden" id="ooh_ast2" name="ooh_ast1" value="${param.adSubType2}">
                                                <input type="hidden" name="task" value="jQueryRequest">
                                                <input type="hidden" name="action" value="SaveMultipleSelections">
                                                <input type="hidden" name="isdisplay" value="false">
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </DIV>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table border="1" style="background-color:white" id="table2" align="center" width="100%">
                            <tr>
                                <td align="center">
                                    <b>Previous Selected Sites</b>
                                    <input type="text" id="total_items" value="${ooh_total_sites}" style="background-color: gainsboro; text-align: center;" size="2" readonly>
                                    <b>No. Of Selected Sites</b>
                                    <input type="text" id="noOfSelectedItems" value="0" style="background-color: gainsboro; text-align: center;" size="2" readonly>
                                    &nbsp;&nbsp;<input class="button" type="button" id="submitSelectedSites" name="task" value="Submit Selected Sites" onclick="submitChild()" disabled>
                                    &nbsp;&nbsp;<input class="button" type="button" id="ooh_close" name="close" value="Close" onclick="self.close();">
                                    &nbsp;&nbsp;<input class="button" type="button" id="ooh_checkAll" value="Check All" onclick="checkAllItems();">
                                    &nbsp;&nbsp;<input class="button" type="button" id="ooh_uncheckAll" value="Uncheck All" onclick="uncheckAllItems();" disabled>
                                    <%-- Follwoing form elements are NOT send to the server, they are there just for Javascript. --%>
                                    <input type="hidden" id="ooh_autoClosePopUp" value="${ooh_autoClosePopUp}">
                                    <input type="hidden" id="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                    <input type="text" id="showOohBRDisplayTab" name="showOohBRDisplayTab" value="${showOohBRDisplayTab}">
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <jsp:include page="calendar_view"/>

                    </td>
                </tr>
            </table>
        </div>
        <div id="div_animationProcessing" style="height: 0px; display: none;">
            <table align="center" style="vertical-align: middle;">
                <tr>
                    <td align="center">
                        <img src="images/Animation_Processing.gif" alt="images/Animation_Processing.gif">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label style="font-size: large; font-weight: bold;">Please wait processing data ...</label>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
