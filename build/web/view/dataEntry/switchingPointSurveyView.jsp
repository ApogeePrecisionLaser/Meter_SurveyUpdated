<%--
    Document   : switchingPointSurveyView
    Created on : Jul 15, 2014, 6:37:10 AM
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
    var map;
    var coords = new Object();
    var markersArray = [];
    coords.lat = 0;
    coords.lng = 0 ;

  /*$(document).ready(function() {
        //$("#pole_no_s").val()!==''&& alert($("#meter_no_s").val());
        if($("#searchPole").val()!=''){
            fillColumns('t1c1');
          makeEditable('new');
          $("searchPole").val("");
           $("searchMeter").val("");
       }
    });*/

    jQuery(function(){ 

        if($('form[name="form1"] input[type=checkbox]:checked').size() == 0){
            $("#updateIVRS").attr("disabled", "disabled");
        }else{
            $("#updateIVRS").removeAttr("disabled");
        }

        $("#zone_search").autocomplete("switchingPointSurvey", {

            extraParams: {
                action1: function() { return "getZoneSearch"}
            }
        });
        $("#searchPoleNoSwitch").autocomplete("switchingPointSurvey", {

            extraParams: {
                action1: function() { return "getPoleNoSwitch"}
            }
        });
        $("#service_conn_no_Search").autocomplete("switchingPointSurvey", {

            extraParams: {
                action1: function() { return "service_conn_no_Search"}
            }
        });
        $("#pole_no").autocomplete("switchingPointSurvey", {
            extraParams: {                action1: function() { return "getPoleNo"}
            }
        });
        $("#searchPoleNo").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "searchPoleNo"}
            }
        });
        $("#searchSwitchingPtName").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getSwitchingPtName"}
            }
        });
        $("#searchPoleType").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getPoleType"}
            }
        });
        $("#searchAreaName").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getAreaName"}
            }
        });
        $("#searchRoadName").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getRoadName"}
            }
        });


        $("#division").result(function(event, data, formatted){
            document.getElementById("zone").value = "";
            document.getElementById("feeder").value = "";
            $("#zone").flushCache();
            $("#feeder").flushCache();
        });
        $("#zone").result(function(event, data, formatted){
            document.getElementById("feeder").value = "";
            $("#feeder").flushCache();
        });
        $("#city").result(function(event, data, formatted){
            document.getElementById("ward_no").value = "";
            document.getElementById("area_name").value = "";
            $("#ward_no").flushCache();
            $("#area_name").flushCache();
        });
        $("#ward_no").result(function(event, data, formatted){
            document.getElementById("area_name").value = "";
            $("#area_name").flushCache();
        });
        $("#road_category").result(function(event, data, formatted){
            document.getElementById("road_name").value = "";
            $("#road_name").flushCache();
        });
        $("#road_use").result(function(event, data, formatted){
            document.getElementById("road_name").value = "";
            $("#road_name").flushCache();
        });
        /*  $("#searchSurveyDate").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });
                $("#survey_date").datepicker({

                    minDate: -100,
                    showOn: "both",
                    buttonImage: "images/calender.png",
                    dateFormat: 'dd-mm-yy',
                    buttonImageOnly: true,
                    changeMonth: true,
                    changeYear: true
                });*/
    });
    jQuery(function(){
        for(var i=0; i<50; i++){
            $("#source_wattage"+i).autocomplete("switchingPointSurvey", {
                extraParams: {
                    action1: function() { return "getLightType"}
                }
            });
        }
        $("#source_wattage").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getLightType"}
            }
        });
        $("#pole_no").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getPoleNo"}
            }
        });
        $("#division").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getDivision"}
            }
        });
        $("#road_use").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getRoadUse"}
            }
        });
        $("#road_category").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getRoad_Category"}
            }
        });
        $("#zone").autocomplete("switchingPointSurvey", {
            extraParams: {           
                action1: function() { return "getZone"},
                action2: function() { return document.getElementById("division").value;}
            }
        });
        $("#feeder").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getFeeder"},
                action2: function() { return document.getElementById("zone").value;}
            }
        });
        $("#ward_no").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getWard_No"},
                action2: function() { return document.getElementById("city").value;}
            }
        });

        $("#area_name").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getAreaName"},
                action2: function() { return document.getElementById("ward_no").value;}
            }
        });
        $("#road_name").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getRoadName"},
                action2: function() { return document.getElementById("road_category").value;},
                action3: function() { return document.getElementById("road_use").value;}
            }
        });
        $("#traffic_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getTrafficType"}
            }
        });
        $("#control_mechanism_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getControlType"}
            }
        });
        $("#switching_point_name").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getSwitchType"}
            }
        });
        $("#fuse_type1").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
        $("#contacter_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getContacterType"}
            }
        });
        $("#mccb_type1").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
        
          $("#fuse_type2").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
       
        $("#mccb_type2").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
       
          $("#fuse_type3").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
       
        $("#mccb_type3").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
      
        $("#city").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getCity"}
            }
        });
        $("#auto_switch_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getAutoSwitchType"}
            }
        });
        $("#main_switch_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getMainSwitchType"}
            }
        });
        $("#enclosure_type").autocomplete("switchingPointSurvey", {
            extraParams: {
                action1: function() { return "getEnclosureType"}
            }
        });
       
    });
    function openMap(longitude, lattitude){

        var x = $.trim(document.getElementById(lattitude).value);
        var y = $.trim(document.getElementById(longitude).value);
        var url="switchingPointSurvey?task=showMapWindow&logitude="+y+"&lattitude="+x;
        popupwin = openPopUp(url, "",  580, 620);

    }
    function plotPoint(srcLat,srcLon,title,popUpContent,markerIcon)
    {
        var myLatlng = new google.maps.LatLng(srcLat, srcLon);
        var marker = new google.maps.Marker({
            position: myLatlng,
            map: map,
            title:title,
            icon: markerIcon
        });
        markersArray.push(marker);
        var infowindow = new google.maps.InfoWindow({
            content: popUpContent
        });
        google.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map,marker);
        });
    }
    function initialize()
    {

        var latlng = new google.maps.LatLng(coords.lat, coords.lng);
        var myOptions = {
            zoom: 15,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("map_canvas"),  myOptions);
    }

    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for(var i = 0; i < noOfRowsTraversed; i++) {
            for(var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function verifyIVRSnoCheckStatus(){  
        var flag = 'false';
        $('.checkbox1').each(function(){
            if($('.checkbox1').is(':checked')){
        
            }else{
                flag = 'true';
            }
        });
        if(flag == 'true'){
            $("#checkinbulk").checked = false;
        }else{
            $("#checkinbulk").checked = true;
        }

    }
    function checkUncheckAll(){
        if($('#checkinbulk').is(':checked')){
            $('.checkbox1').each(function(){
                this.checked = true;
                $('.ivrs_no_id').val("1");
                document.getElementById("content_div").className ="ivrs_no_edit".removeAttribute("readonly");

                // $('.ivrs_no_edit').removeAttr('readonly');
                $("#update").removeAttr("disabled");
                //   document.getElementById("ivrs_no_edit").disabled = false;
            });
        }else{
            $('.checkbox1').each(function(){
                this.checked = false;
                $('.ivrs_no_id').val("0");
                document.getElementById("content_div").className ="ivrs_no_edit".setAttribute("readonly", "readonly");
                // $('.ivrs_no_edit').attr('readonly', true);
                $("#update").attr("disabled", "disabled");
            });
        }
    }
    
    function singleCheckUncheck(id){
        //  alert(document.getElementById('insertTable').rows.length);
        if ($('#ivrs_no'+id).is(':checked')) {
            $('#ivrs_no_id'+id).val("1");
            document.getElementById("ivrs_no_edit"+id).readOnly = false;
            // $('.ivrs_no_edit'+id).removeAttr('readonly');
            //document.getElementById("ivrs_no_edit"+id).disabled = false;
        }else{
            $('#ivrs_no_id'+id).val("0");
            document.getElementById("ivrs_no_edit"+id).readOnly = true;
            // $('.ivrs_no_edit'+id).attr('readonly', true);
            //document.getElementById("ivrs_no_edit"+id).disabled = true;
        }
        if($('form[name="form1"] input[type=checkbox]:checked').size() == 0){
            $("#updateIVRS").attr("disabled", "disabled");
        }else{
            $("#updateIVRS").removeAttr("disabled");
        }
        if($('form[name="form1"] input[type=checkbox]:checked').size() == 10){
            //   alert("ALL checked");
        }


    }

    function poleSingleCheckUncheck(id){
        //  alert(document.getElementById('insertTable').rows.length);
        if ($('#pole_no_ck_bx'+id).is(':checked')) {
            $('#ivrs_no_id'+id).val("1");
            document.getElementById("pole_no_edit"+id).readOnly = false;
            // $('.ivrs_no_edit'+id).removeAttr('readonly');
            //document.getElementById("ivrs_no_edit"+id).disabled = false;
        }else{
            $('#ivrs_no_id'+id).val("0");
            document.getElementById("pole_no_edit"+id).readOnly = true;
            // $('.ivrs_no_edit'+id).attr('readonly', true);
            //document.getElementById("ivrs_no_edit"+id).disabled = true;
        }
        if($('form[name="form1"] input[type=checkbox]:checked').size() == 0){
            $("#update").attr("disabled", "disabled");
        }else{
            $("#update").removeAttr("disabled");
        }
        if($('form[name="form1"] input[type=checkbox]:checked').size() == 10){
            //   alert("ALL checked");
        }


    }
    
    function makeEditable(id) {
        //$('input[name="source_wattage"]').prop('readonly', true);
        //       $('.source_wattage').attr('readonly', true);
       
        document.getElementById("pole_no_s").disabled = false;
        document.getElementById("switching_point_name").disabled = false;
        document.getElementById("gps_code_s").disabled = false;
        document.getElementById("survey_date").disabled = false;
        document.getElementById("meter_no_s").disabled = false; 
        document.getElementById("phase").disabled = false;
        document.getElementById("pole_no").disabled = false;
        document.getElementById("area_name").disabled = false;
        document.getElementById("road_name").disabled = false;
        document.getElementById("traffic_type").disabled = false;
        //   document.getElementById("fuse").disabled = false;
        document.getElementById("fuse_type1").disabled = false;
        document.getElementById("fuse_type2").disabled = false;
        document.getElementById("fuse_type3").disabled = false;
        document.getElementById("fuse_quantity").disabled = false;
        //  document.getElementById("contacter").disabled = false;
        document.getElementById("contacter_type").disabled = false;
        document.getElementById("contacter_capacity").disabled = false;
        document.getElementById("contacter_make").disabled = false;
        document.getElementById("mccb_type1").disabled = false;
        document.getElementById("mccb_type2").disabled = false;
        document.getElementById("mccb_type3").disabled = false;
        document.getElementById("mccb_quantity").disabled = false;
        document.getElementById("control_mechanism_type").disabled = false;
        document.getElementById("is_working").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("active").disabled = false;
        document.getElementById("survey_rev_no").disabled = false;
        document.getElementById("source_wattage").disabled = false;
        document.getElementById("quantity").disabled = false;
        document.getElementById("working").disabled = false;
        document.getElementById("n_working").disabled = false;
        document.getElementById("lattitude").disabled = false;
        document.getElementById("logitude").disabled = false;
        document.getElementById("road_use").disabled = false;
        document.getElementById("road_category").disabled = false;
        document.getElementById("feeder").disabled = false;
        document.getElementById("zone").disabled = false;
        document.getElementById("no_of_poles").disabled = false;
        document.getElementById("ivrs_no").disabled = false;
        document.getElementById("division").disabled = false;
        document.getElementById("zone").disabled = false;
        document.getElementById("city").disabled = false;
        document.getElementById("ward_no").disabled = false;
        document.getElementById("m_load").disabled = false;
        document.getElementById("service_conn_no").disabled = false;
        document.getElementById("is_on_pole").disabled = false;
        document.getElementById("fuse_1").disabled = false;
        document.getElementById("fuse_2").disabled = false;
        document.getElementById("fuse_3").disabled = false;
        document.getElementById("mccb_1").disabled = false;
        document.getElementById("mccb_2").disabled = false;
        document.getElementById("mccb_3").disabled = false;
        document.getElementById("auto_switch_type").disabled = false;
        document.getElementById("main_switch_type").disabled = false;
        document.getElementById("main_switch_reading").disabled = false;
           document.getElementById("enclosure_type").disabled = false;
     

        /*document.getElementById("controller_model").disabled = false;
        document.getElementById("mobile_no").disabled = false;
        document.getElementById("sim_no").disabled = false;
        document.getElementById("imei_no").disabled = false;
        document.getElementById("panel_file_no").disabled = false;
         */
        document.getElementById("save").disabled = true;
        document.getElementById("save_As").disabled = false;
        document.getElementById("cancel").disabled = false;
        document.getElementById("revise").disabled = false;
        document.getElementById("addMore").disabled = false;
        document.getElementById("update").disabled = false;

        if(id == 'new') {

            $("#message").html("");
            //      $('.source_wattage').attr('readonly', false);
            //      $('.source_wattage').prop('readonly', false);
            //       $('input[name="source_wattage"]').prop('readonly', false);
            document.getElementById("revise").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("update").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 54);
            document.getElementById("pole_no_s").focus();
            document.getElementById("addMore").disabled = false;
            document.getElementById("addRowCount").value = "0";

            deleteRow();
        } else if(id == 'edit'){
            document.getElementById("revise").disabled = false;
            document.getElementById("cancel").disabled = true;
            document.getElementById("update").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = true;
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 54);
            document.getElementById("pole_no_s").focus();
            document.getElementById("addMore").disabled = false;
            document.getElementById("addRowCount").value = "0";
        }
    }
    function addRow() {
        var count = document.getElementById("addRowCount").value;
        count++;

        $("#dynamic"+ count).css("display", "table-row");
        document.getElementById("addRowCount").value = count;
    }
    function setStatus(id) {
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        }
        else if(id == 'addMore'){
            document.getElementById("clickedButton").value = "Add More";
        }
        else if(id == 'save_As'){
            document.getElementById("clickedButton").value = "Save AS New";
        }
        else if(id == 'revise'){
            document.getElementById("clickedButton").value = "Revise";
        }
        else if(id == 'update'){
            document.getElementById("clickedButton").value = "Update";
        }
        else document.getElementById("clickedButton").value = "Cancel";
    }
    function verify() {
        var result;
        if(document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New' || document.getElementById("clickedButton").value == 'Revise' || document.getElementById("clickedButton").value == 'Update') {
            var switching_point_name = document.getElementById("switching_point_name").value;
            var service_conn_no = document.getElementById("service_conn_no").value;
            var ivrs_no = document.getElementById("ivrs_no").value;

            var no_of_poles = document.getElementById("no_of_poles").value;

            var feeder = document.getElementById("feeder").value;
            var area_name = document.getElementById("area_name").value;
            var road_name = document.getElementById("road_name").value;
            var traffic_type = document.getElementById("traffic_type").value;
            var fuse_type = document.getElementById("fuse_type").value;
            var mccb_type = document.getElementById("mccb_type").value;
            var contacter_type = document.getElementById("contacter_type").value;
            var timer_type = document.getElementById("timer_type").value;
            var control_mechanism_type = document.getElementById("control_mechanism_type").value;
            var m_load = document.getElementById("m_load").value;
            var pole_no = document.getElementById("pole_no").value;
         
            var source_wattage = document.getElementById("source_wattage").value;

            var quantity = document.getElementById("quantity").value;
               
            var isOnPole = $("#is_on_pole").is(":checked");

            if(myLeftTrim(switching_point_name).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Switching Point Name is Required...</b></td>");
                document.getElementById("switching_point_name").focus();
                return false; 
            }
            if(myLeftTrim(service_conn_no).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Service Connection No. is Required...</b></td>");
                document.getElementById("service_conn_no").focus();
                return false;
            }

            if(myLeftTrim(ivrs_no).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>IVRS No is Required...</b></td>");
                document.getElementById("ivrs_no").focus();
                return false;
            }


            if(myLeftTrim(no_of_poles).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>No. of Poles Connected is Required...</b></td>");
                document.getElementById("no_of_poles").focus();
                return false;
            }
            if(isOnPole){
                if(myLeftTrim(pole_no).length == 0) {
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Pole No. is Required...</b></td>");
                    document.getElementById("pole_no").focus();
                    return false;
                }
            }
            if(myLeftTrim(feeder).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Feeder is Required...</b></td>");
                document.getElementById("feeder").focus();
                return false;
            }
            if(myLeftTrim(area_name).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Area Name is Required...</b></td>");
                document.getElementById("area_name").focus();
                return false;
            }
            if(myLeftTrim(road_name).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Road Name is Required...</b></td>");
                document.getElementById("road_name").focus();
                return false;
            }
            if(myLeftTrim(traffic_type).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Traffic Type is Required...</b></td>");
                document.getElementById("traffic_type").focus();
                return false;
            }
            if(myLeftTrim(fuse_type).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Fuse Type is Required...</b></td>");
                document.getElementById("fuse_type").focus();
                return false;
            }
            if(myLeftTrim(contacter_type).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Contacter Type is Required...</b></td>");
                document.getElementById("contacter_type").focus();
                return false;
            }
            if(myLeftTrim(mccb_type).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>MCCB Type is Required...</b></td>");
                document.getElementById("mccb_type").focus();
                return false;
            }
            if(myLeftTrim(timer_type).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Timer Type is Required...</b></td>");
                document.getElementById("timer_type").focus();
                return false;
            }
            if(myLeftTrim(control_mechanism_type).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Control Mechanism Type is Required...</b></td>");
                document.getElementById("control_mechanism_type").focus();
                return false;
            }
            if(myLeftTrim(source_wattage).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Light Type is Required...</b></td>");
                document.getElementById("source_wattage").focus();
                return false;
            }

            if(myLeftTrim(source_wattage).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Light Type is Required...</b></td>");
                document.getElementById("source_wattage").focus();
                return false;
            } 


            if(myLeftTrim(quantity).length ==0) {
                // alert(quantity);
                // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Quantity is Required...</b></td>");
                document.getElementById("quantity").focus();
                return false;
            }

            if(myLeftTrim(m_load).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Measured Load is Required...</b></td>");
                document.getElementById("m_load").focus();
                return false;
            }
            
            for(var i=0; i<=50; i++){


                //    var source_wattage= $("#source_wattage"+i).val();
                // alert(source);
                //    var b=$("#quantity"+i).val()
                //alert(a);
                if(source_wattage== $("#source_wattage"+i).val()) {

                    // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                    $("#message").html("<td colspan='6' bgcolor='coral'><b>Light Type should be unique...</b></td>");
                    document.getElementById("source_wattage"+i).focus();
                    return false; // code to stop from submitting the form2.
                }

                /* var quantity= $("#quantity"+i).val();
           //    alert(quantity);
              if(myLeftTrim(source).length!=0 && myLeftTrim(quantity).length==0) {

                    // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                    $("#message").html("<td colspan='5' bgcolor='coral'><b>Quantity is required...</b></td>");
                    document.getElementById("quantity"+i).focus();
                    return false; // code to stop from submitting the form2.
                }  */
         
         

            }

        

            /* for(var i=0; i<50; i++){
          
                var source=$("#source_wattage"+i).val();
                var quantity= $("#quantity"+i).val();
               alert(quantity);
              if(myLeftTrim(source).length!=0 && myLeftTrim(quantity).length==0) {

                    // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                    $("#message").html("<td colspan='5' bgcolor='coral'><b>Quantity is required...</b></td>");
                    document.getElementById("quantity"+i).focus();
                    return false; // code to stop from submitting the form2.
                } 
            }  
             */
            if(document.getElementById("active").value =='N')
            {
                $("#message").html("<td colspan='5' bgcolor='coral'><b>You can not perform any operation on In-Active record...</b></td>");
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
            if(document.getElementById("clickedButton").value == 'Revise'){
                result = confirm("Are you sure you want to Revise this record?")
                return result;
            }
            if(document.getElementById("clickedButton").value == 'Update'){
                result = confirm("Are you sure you want to Update this record?")
                return result;
            }
        } else result = confirm("Are you sure you want to Cancel this record?")
        return result;
    }

    function fillColumns(id) {
       // deleteRow();
       
     
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        var noOfColumns = 63;
        var columnId = id;                              <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;

        for(var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;       // e.g. 11 = (1 * 10 + 1)
            higherLimit = (i + 1) * noOfColumns;    // e.g. 20 = ((1 + 1) * 10)
            rowNo++;
            if((columnId >= lowerLimit) && (columnId <= higherLimit)) break;
        }

        setDefaultColor(noOfRowsTraversed, noOfColumns);
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.



        //    alert(document.getElementById("switching_point_detail_id" + rowNo).value+","+document.getElementById("switching_rev_no" + rowNo).value);
        //    alert(document.getElementById("pole_id" + rowNo).value+","+document.getElementById("pole_rev_no" + rowNo).value);
        document.getElementById("switching_point_detail_id").value= $.trim(document.getElementById("switching_point_detail_id" + rowNo).value);
        document.getElementById("switching_rev_no").value= $.trim(document.getElementById("switching_rev_no" + rowNo).value);
        //    document.getElementById("pole_id").value = $.trim(document.getElementById("pole_id" + rowNo).value);
        //     document.getElementById("pole_rev_no").value = $.trim(document.getElementById("pole_rev_no" + rowNo).value);
        //document.getElementById("ward_name").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
        document.getElementById("pole_no_s").value = $.trim(document.getElementById(t1id +(lowerLimit+2)).innerHTML);
        //  document.getElementById("pole_no").value = $.trim(document.getElementById(t1id +(lowerLimit+3)).innerHTML);
        document.getElementById("pole_no").value = $.trim(document.getElementById("pole_no_edit" + rowNo).value);
        document.getElementById("switching_point_name").value = $.trim(document.getElementById(t1id +(lowerLimit+4)).innerHTML);
        document.getElementById("area_name").value = $.trim(document.getElementById(t1id +(lowerLimit+5)).innerHTML);
        document.getElementById("road_name").value = $.trim(document.getElementById(t1id +(lowerLimit+6)).innerHTML);
        document.getElementById("feeder").value = $.trim(document.getElementById(t1id +(lowerLimit+7)).innerHTML);
        document.getElementById("no_of_poles").value = $.trim(document.getElementById(t1id +(lowerLimit+8)).innerHTML);
    <%--    Switching_ Load ----11   --%>
            document.getElementById("meter_no_s").value = $.trim(document.getElementById(t1id +(lowerLimit+12)).innerHTML);
            document.getElementById("gps_code_s").value = $.trim(document.getElementById(t1id +(lowerLimit+13)).innerHTML);
            document.getElementById("phase").value = $.trim(document.getElementById(t1id +(lowerLimit+14)).innerHTML);
    <%--
   15 pole_type
   16 pole_span
   17 pole_height
   18 mounting_type
   19 mounting_height
   20 gps_code
   21 max_avg_lux_level
   22 min_avg_lux_level
   23 avg_lux_level
   24 standard_avg_lux_level   --%>
    <%--   document.getElementById("survey_date").value = document.getElementById(t1id +(lowerLimit+5)).innerHTML;

         document.getElementById("created_date").value = document.getElementById(t1id +(lowerLimit+8)).innerHTML;  --%>



                 document.getElementById("traffic_type").value = $.trim(document.getElementById(t1id +(lowerLimit+25)).innerHTML);

                 if(document.getElementById(t1id +(lowerLimit+26)).innerHTML=='Y'){
                     document.getElementById('fuseYes').checked = true;
                     document.getElementById('fuseNo').checked = false;
                 }else{
                     document.getElementById('fuseNo').checked = true;
                     document.getElementById('fuseYes').checked = false;
                 }
                 document.getElementById("fuse_1").value = $.trim(document.getElementById(t1id +(lowerLimit+27)).innerHTML)
                 document.getElementById("fuse_2").value = $.trim(document.getElementById(t1id +(lowerLimit+28)).innerHTML)
                 document.getElementById("fuse_3").value = $.trim(document.getElementById(t1id +(lowerLimit+29)).innerHTML)
                 document.getElementById("fuse_type1").value = $.trim(document.getElementById(t1id +(lowerLimit+30)).innerHTML);
                 document.getElementById("fuse_type2").value = $.trim(document.getElementById(t1id +(lowerLimit+31)).innerHTML);
                 document.getElementById("fuse_type3").value = $.trim(document.getElementById(t1id +(lowerLimit+32)).innerHTML);
                 document.getElementById("fuse_quantity").value = $.trim(document.getElementById(t1id +(lowerLimit+33)).innerHTML);

                 if(document.getElementById(t1id +(lowerLimit+34)).innerHTML=='Y'){
                     document.getElementById('contacterYes').checked = true;
                     document.getElementById('contacterNo').checked = false;
                 }else{
                     document.getElementById('contacterNo').checked = true;
                     document.getElementById('contacterYes').checked = false;
                 }
                 document.getElementById("contacter_type").value = $.trim(document.getElementById(t1id +(lowerLimit+35)).innerHTML);
                 document.getElementById("contacter_capacity").value = $.trim(document.getElementById(t1id +(lowerLimit+36)).innerHTML);

                 if(document.getElementById(t1id +(lowerLimit+37)).innerHTML=='Y'){
                     document.getElementById('mccbYes').checked = true;
                     document.getElementById('mccbNo').checked = false;
                 }else{
                     document.getElementById('mccbNo').checked = true;
                     document.getElementById('mccbYes').checked = false;
                 }
                 document.getElementById("mccb_1").value = $.trim(document.getElementById(t1id +(lowerLimit+38)).innerHTML);
                 document.getElementById("mccb_2").value = $.trim(document.getElementById(t1id +(lowerLimit+39)).innerHTML);
                 document.getElementById("mccb_3").value = $.trim(document.getElementById(t1id +(lowerLimit+40)).innerHTML);
                 document.getElementById("mccb_type1").value = $.trim(document.getElementById(t1id +(lowerLimit+41)).innerHTML);
                 document.getElementById("mccb_type2").value = $.trim(document.getElementById(t1id +(lowerLimit+42)).innerHTML);
                 document.getElementById("mccb_type3").value = $.trim(document.getElementById(t1id +(lowerLimit+43)).innerHTML);
                 document.getElementById("mccb_quantity").value = $.trim(document.getElementById(t1id +(lowerLimit+44)).innerHTML);

                 //if(document.getElementById(t1id +(lowerLimit+45)).innerHTML=='Y'){
                     //document.getElementById('timerYes').checked = true;
                     //document.getElementById('timerNo').checked = false;
                 //}else{
                     //document.getElementById('timerNo').checked = true;
                     //document.getElementById('timerYes').checked = false;
                 //}
                 //document.getElementById("timer_type").value = $.trim(document.getElementById(t1id +(lowerLimit+46)).innerHTML);
                 //document.getElementById("timer_quantity").value = $.trim(document.getElementById(t1id +(lowerLimit+47)).innerHTML);
                 document.getElementById("main_switch_reading").value = $.trim(document.getElementById(t1id +(lowerLimit+45)).innerHTML);
                 document.getElementById("main_switch_type").value = $.trim(document.getElementById(t1id +(lowerLimit+46)).innerHTML);
                 document.getElementById("auto_switch_type").value = $.trim(document.getElementById(t1id +(lowerLimit+47)).innerHTML);
                 document.getElementById("enclosure_type").value = $.trim(document.getElementById(t1id +(lowerLimit+48)).innerHTML);
                 //document.getElementById("control_mechanism_type").value = $.trim(document.getElementById(t1id +(lowerLimit+48)).innerHTML);
                 document.getElementById("is_working").value = $.trim(document.getElementById(t1id +(lowerLimit+49)).innerHTML);
                 document.getElementById("remark").value = $.trim(document.getElementById(t1id +(lowerLimit+50)).innerHTML);
                 document.getElementById("active").value = $.trim(document.getElementById(t1id +(lowerLimit+51)).innerHTML);
                 document.getElementById("road_category").value = $.trim(document.getElementById(t1id +(lowerLimit+52)).innerHTML);
                 document.getElementById("road_use").value = $.trim(document.getElementById(t1id +(lowerLimit+53)).innerHTML);
                 document.getElementById("city").value = $.trim(document.getElementById(t1id +(lowerLimit+55)).innerHTML);
                 document.getElementById("service_conn_no").value = $.trim(document.getElementById(t1id +(lowerLimit+54)).innerHTML);
                 document.getElementById("ivrs_no").value = $.trim(document.getElementById("ivrs_no_edit" + rowNo).value);
                 document.getElementById("lattitude").value = $.trim(document.getElementById("lattitude" + rowNo).value);
                 document.getElementById("logitude").value = $.trim(document.getElementById("longitude" + rowNo).value);
                 document.getElementById("m_load").value = $.trim(document.getElementById(t1id +(lowerLimit+58)).innerHTML);
                 document.getElementById("division").value = $.trim(document.getElementById(t1id +(lowerLimit+59)).innerHTML);
                 document.getElementById("zone").value = $.trim(document.getElementById(t1id +(lowerLimit+60)).innerHTML);
                 document.getElementById("ward_no").value = $.trim(document.getElementById(t1id +(lowerLimit+61)).innerHTML);
                 document.getElementById("isCheckedTrue").value = $.trim(document.getElementById(t1id +(lowerLimit+62)).innerHTML);


                 /*document.getElementById("controller_model").value = $.trim(document.getElementById(t1id +(lowerLimit+54)).innerHTML);
                 document.getElementById("mobile_no").value = $.trim(document.getElementById(t1id +(lowerLimit+55)).innerHTML);
                 document.getElementById("sim_no").value = $.trim(document.getElementById(t1id +(lowerLimit+56)).innerHTML);
                 document.getElementById("imei_no").value = $.trim(document.getElementById(t1id +(lowerLimit+57)).innerHTML);
                 document.getElementById("panel_file_no").value = $.trim(document.getElementById(t1id +(lowerLimit+58)).innerHTML);
                  */
                 var isCheckedTrue = document.getElementById("isCheckedTrue").value;
                 if(isCheckedTrue == 'Y'){
                     $('#is_on_pole').attr('checked', true);
                     $("#pole_no_box").css("display", "block");
                 }else{
                     $('#is_on_pole').attr('checked', false);
                     $("#pole_no_box").css("display", "none");
                 }


                 //    document.getElementById("survey_rev_no").value = document.getElementById(t1id +(lowerLimit+40)).innerHTML;
                 var xmap_id = $.trim(document.getElementById("map_id" + rowNo).value);
                 var xabc=xmap_id.split("__");
                 var xabcd=xabc[0];
                 var xmapp_id= xabcd.split("-")[0];
                 var xlight_type_id= xabcd.split("-")[1];
                 document.getElementById("mapp_id").value = xmapp_id;
                 document.getElementById("light_type_id").value = xlight_type_id;

                 var query=document.getElementById(t1id +(lowerLimit+9)).innerHTML;
                 var iswork=document.getElementById(t1id +(lowerLimit+10)).innerHTML;
                 
                 if(iswork.length>0){
                 var abc=query.split("__");
                 var abcd=abc[0];
                 var light= abcd.split(",")[0];
                 var quantity= abcd.split(",")[1];
                 document.getElementById("source_wattage").value = light;
                 document.getElementById("quantity").value = quantity;
                 var xyz  =  iswork.split("__");
                  
                 var rowCount=abc.length;
                 var rowCounts=xyz.length;
                 var xyzz = xyz[0];
                 //  alert(xyzz);
                 var xyzzz = xyzz.split(",")[1];
                
                 document.getElementById("working").value = xyzzz.split("/")[0];
                 document.getElementById("n_working").value = xyzzz.split("/")[1];
                 if(rowCount>1){
                     document.getElementById("addRowCount").value = rowCount-1;
                     for(ii=1; ii<=50; ii++){
                         document.getElementById("source_wattage"+ii).value = "";
                         document.getElementById("mapp_id"+ii).value = "0";
                         document.getElementById("light_type_id"+ii).value = "0";
                         document.getElementById("quantity"+ii).value = "";
                         document.getElementById("working"+ii).value = "";
                         document.getElementById("n_working"+ii).value = "";
                     }
                     for(i=1; i<rowCount; i++)
                     {
                         var xy_map_concat = xabc[i];
                         var xy_map_id = xy_map_concat.split("-")[0];
                         var xy_type_id = xy_map_concat.split("-")[1];

                         var query1 = abc[i];
                         var light_type = query1.split(",")[0];
                         var q = query1.split(",")[1];

                         $("#dynamic"+ i).css("display", "table-row");


                         // alert(q);

                         //        var table = document.getElementById('table2');
                         //         var row = table.rows.length;
                         //       idd = row - 9 ;

                         var query2 = xyz[i];
                         //alert(query2);
                         var type_wrkingNwrk = query2.split(",")[1];

                         var working = type_wrkingNwrk.split("/")[0];
                         var not_working = type_wrkingNwrk.split("/")[1];
                         document.getElementById("working"+i).value = working;
                         document.getElementById("n_working"+i).value = not_working;
                         document.getElementById("mapp_id"+i).value = xy_map_id;
                         document.getElementById("light_type_id"+i).value = xy_type_id;


                         document.getElementById("source_wattage"+i).value = light_type;
                         document.getElementById("quantity"+i).value = q;

                     }
                     var table = document.getElementById('table2');
                     var row = table.rows.length;
                     //   alert("total:"+row);

                 }
              }



                 for(var i = 0; i < noOfColumns; i++) {
                     document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";        // set the background color of clicked row to yellow.
                 }


                 $("#message").html(""); 
                 makeEditable('');
                
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
             function deleteRow() {
                 try {
                     var table = document.getElementById('table2');
                     var rowCount = table.rows.length-1;
                     //    alert("Before"+rowCount);
                     for(var i=50; i >0; i--) {
                         //alert(i);
                         //table.deleteRow(i);
                         //     $('#table2 tr:eq('+i+')').remove();
                         $("#dynamic"+i).css("display", "none");
                         //    alert(i);
                     }
                 }catch(e) {
                     alert(e);
                 }
             }
             function IsTimeNumeric(id) {
                 var strString = document.getElementById(id).value;
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
                         var  time = document.getElementById(id).value;
                         if(id=="submision_time_hour" || id=='receipt_time_hour'){
                             if(time>12){
                                 document.getElementById(id).value="";
                                 alert("Hour should Be less than 12");
                                 return;
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

             function IsNumeric(id) {
                 var strString = document.getElementById(id).value;
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
                         alert("Numeric value is expected !! ");
                         blnResult = false;
                     }
                 }
                 return blnResult;
             }

             function checkQuantity(){
                 var quantity = document.getElementById("quantity").value;

                 if(myLeftTrim(quantity).length == 0) {
                     // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Quantity is Required...</b></td>");
                     document.getElementById("quantity").focus();
                 }
             }
             function checkQuantityy(id){
                 columnId = id.substring(7, id.length);
                 var quantity = document.getElementById("quantity"+columnId).value;

                 if(myLeftTrim(quantity).length == 0) {
                     // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Quantity is Required...</b></td>");
                     document.getElementById("quantity"+columnId).focus();
                 }
             }
             function checkWorking(){
                 var working = document.getElementById("working").value;

                 if(myLeftTrim(working).length == 0) {
                     // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Working is Required...</b></td>");
                     document.getElementById("working").focus();
                 }
             }
             function checkWorkingg(id){
                 columnId = id.substring(9, id.length);
                 var working = document.getElementById("working"+columnId).value;

                 if(myLeftTrim(working).length == 0) {
                     // document.getElementById("message").innerHTML = "<td colspan='5' bgcolor='coral'><b>Organisation Type Name is required...</b></td>";
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Working is Required...</b></td>");
                     document.getElementById("working"+columnId).focus();
                 }
             }
             function setDefaultWorking(){
                 var quantity = document.getElementById("quantity").value;
                 if(myLeftTrim(quantity).length == 0){
                     document.getElementById("working").value = 0;
                     document.getElementById("n_working").value = 0;
                 }else{
                     document.getElementById("working").value = parseInt(quantity);
                     document.getElementById("n_working").value = 0;
                 }
             }
             function setDefaultWorkingg(id){
                 columnId = id.substring(8, id.length);
                 var quantity = document.getElementById("quantity"+columnId).value;
                 if(myLeftTrim(quantity).length == 0){
                     document.getElementById("working"+columnId).value = 0;
                     document.getElementById("n_working"+columnId).value = 0;
                 }else{
                     document.getElementById("working"+columnId).value = parseInt(quantity);
                     document.getElementById("n_working"+columnId).value = 0;
                 }
             }
             function setNworkingVal(){
                 var quantity = document.getElementById("quantity").value;
                 var working = document.getElementById("working").value;
                 if(myLeftTrim(working).length == 0){
                     document.getElementById("n_working").value = "";
                 }else{
                     document.getElementById("n_working").value = parseInt(quantity) - parseInt(working);
                 }
                 if(parseInt(working) > parseInt(quantity)){
                     document.getElementById("working").value = "";
                     document.getElementById("n_working").value = "";
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Value Cant Be Greater Than Quantity</b></td>");
                 }
             }
             function setNworkingVall(id){
                 columnId = id.substring(7, id.length);
                 var quantity = document.getElementById("quantity"+columnId).value;
                 var working = document.getElementById("working"+columnId).value;
                 if(myLeftTrim(working).length == 0){
                     document.getElementById("n_working"+columnId).value = "";
                 }else{
                     document.getElementById("n_working"+columnId).value = parseInt(quantity) - parseInt(working);
                 }
                 if(parseInt(working) > parseInt(quantity)){
                     document.getElementById("working"+columnId).value = "";
                     document.getElementById("n_working"+columnId).value = "";
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Value Cant Be Greater Than Quantity</b></td>");
                 }
             }
             function setWorkingVal(){
                 var quantity = document.getElementById("quantity").value;
                 var n_working =document.getElementById("n_working").value;
                 document.getElementById("working").value = parseInt(quantity) - parseInt(n_working);
                 if(parseInt(n_working) > parseInt(quantity)){
                     document.getElementById("n_working").value = "0";
                     document.getElementById("working").value = quantity;
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Value Cant Be Greater Than Quantity</b></td>");
                 }
             }

             function setWorkingVall(id){
                 columnId = id.substring(9, id.length);
                 var quantity = document.getElementById("quantity"+columnId).value;
                 var n_working = document.getElementById("n_working"+columnId).value;
                 document.getElementById("working"+columnId).value = parseInt(quantity) - parseInt(n_working);

                 if(parseInt(n_working) > parseInt(quantity)){
                     document.getElementById("n_working"+columnId).value = "0";
                     document.getElementById("working"+columnId).value = quantity;
                     $("#message").html("<td colspan='6' bgcolor='coral'><b>Value Cant Be Greater Than Quantity</b></td>");
                 }
             }
             var popupwin = null;
             function displayMapList(){
                 var zone_search = $('#zone_search').val();
                 var service_conn_no_Search = $('#service_conn_no_Search').val();
                 var searchSwitchingPtName = $('#searchSwitchingPtName').val();
                 var searchPoleNoSwitch = $('#searchPoleNoSwitch').val();
                 var queryString = "task=generateMapReport&zone_search="+zone_search+"&service_conn_no_Search="+service_conn_no_Search+"&searchSwitchingPtName="+searchSwitchingPtName+"&searchPoleNoSwitch="+searchPoleNoSwitch ;
                 var url = "switchingPointSurvey?"+queryString;
                 popupwin = openPopUp(url, "Switching Point Details", 500, 1000);

             }

             function openPopUp(url, window_name, popup_height, popup_width) {
                 var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
                 var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
                 var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

                 return window.open(url, window_name, window_features);
             }
             function setvalues(){
                 $("#zone_search").val("");
                 $("#service_conn_no_Search").val("");
                 $("#searchPoleNoSwitch").val("");
                 $("#searchPoleNo").val("");
                 $("#searchPoleType").val("");
             }
             function uncheckAnother(id){
                 var length = id.length;
                 var lastword = id.substring(length-1, length);
                 var a;
                 if(lastword == 's'){
                     a = id.split('Y')[0];
                     $("#"+a+"No").removeAttr('checked');
                 }else{
                     a = id.split('N')[0];
                     $("#"+a+"Yes").removeAttr('checked');
                 }
             }
             function poleNoBox_hide(){
                 if($('form[name="form2"] input[type=checkbox]:checked').size() == 0){
                     $("#isCheckedTrue").val("N");
                     $("#pole_no_box").css("display", "none");
                 }else{
                     $("#isCheckedTrue").val("Y");
                     $("#pole_no_box").css("display", "block");
                 }
             }


</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Switching Point Detail Page</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0" class="main">
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <td>
                <DIV id="body"  style="width:95%" class="maindiv" align="center" >
                    <table width="95%" align="center">
                        <tr>
                            <td>
                                <table align="center">
                                    <tr>
                                        <td align="center" class="header_table" width="100%">SWITCHING POINT DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="switchingPointSurvey">
                                        <table align="center" class="heading" width="590">
                                            <tr>
                                                <td align="center">Zone<input class="input" type="text" id="zone_search" name="zone_search" value="${zone_search}" size="20" ></td>
                                                <td align="center">Service Conn. No.<input class="input" type="text" id="service_conn_no_Search" name="service_conn_no_Search" value="${service_conn_no_Search}" size="20" ></td>
                                                <td align="center">Switching Pt. Name<input class="input" type="text" id="searchSwitchingPtName" name="searchSwitchingPtName" value="${searchSwitchingPtName}" size="20" ></td>
                                                <td style="display:none" align="center">Switching Pt. Pole No<input class="input" type="text" id="searchPoleNoSwitch" name="searchPoleNoSwitch" value="${searchPoleNoSwitch}" size="20" ></td>
                                                <td align="center">Pole No<input class="input" type="text" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" size="20" ></td>
                                                <td style="display:none"align="center">Pole Type<input class="input" type="text" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" size="20" ></td>
                                                    <%--     <td>Area Name<input class="input" type="text" id="searchAreaName" name="searchAreaName" value="${searchAreaName}" size="20" ></td>
                                                         <td>Road Name<input class="input" type="text" id="searchRoadName" name="searchRoadName" value="${searchRoadName}" size="20" ></td>   --%>
                                                <td><input class="button" type="submit" name="task" id="searchIn" value="Search"></td>
                                                <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records" onclick="setvalues()"></td>
                                                <td><input type="button" class="pdf_button" id="viewPdf" name="viewPdf" value="" onclick="displayMapList()"></td>
                                            </tr>
                                        </table>
                                    </form></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <form name="form1" method="POST" action="switchingPointSurvey">
                                    <DIV id="content_div" class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th style="white-space: normal" class="heading">S.No.</th>
                                                <th style="white-space: normal" class="heading">Switching Point Pole No</th>
                                                <th style="white-space: normal" class="heading">Pole No</th>
                                                <th style="white-space: normal" class="heading">Switching Point Name*</th>
                                                <th style="white-space: normal" class="heading">Area Name</th>
                                                <th style="white-space: normal" class="heading">Road Name</th>
                                                <th style="white-space: normal" class="heading">Feeder</th>
                                                <th style="white-space: normal" class="heading">No. of Poles*</th>
                                                <th style="white-space: normal" class="heading">Light Type</th>
                                                <th style="white-space: normal" class="heading">Light Working / Not Working</th>

                                                <th style="white-space: normal" class="heading">Switching Pt. Load (Watt)</th>
                                                <th style="white-space: normal" class="heading">Meter No</th>



                                                <th style="white-space: normal" class="heading">Switching Point GPS code</th>
                                                <!--            <th style="white-space: normal" class="heading">Survey Date</th>  X  -->

                                                <th style="white-space: normal" class="heading">Phase</th>

                                                <th style="white-space: normal" class="heading">Pole Type</th>
                                                <th style="white-space: normal" class="heading">Pole Span(M)</th>
                                                <th style="white-space: normal" class="heading">Pole Height(M)</th>
                                                <th style="white-space: normal" class="heading">Mounting Type</th>
                                                <th style="white-space: normal" class="heading">Mounting Height(M)</th>

                                                <th style="white-space: normal" class="heading">GPS code</th>
                                                <th style="white-space: normal" class="heading">Max Avg Lux Level</th>
                                                <th style="white-space: normal" class="heading">Min Avg Lux Level</th>
                                                <th style="white-space: normal" class="heading">Avg Lux Level</th>
                                                <th style="white-space: normal" class="heading">Standard Lux level</th>

                                                <th style="white-space: normal" class="heading">Traffic Type</th>
                                                <th style="white-space: normal" class="heading">Fuse</th>
                                                <th style="white-space: normal" class="heading">Fuse1</th>
                                                <th style="white-space: normal" class="heading">Fuse2</th>
                                                <th style="white-space: normal" class="heading">Fuse3</th>
                                                <th style="white-space: normal" class="heading">Fuse Type1</th>
                                                <th style="white-space: normal" class="heading">Fuse Type2</th>
                                                <th style="white-space: normal" class="heading">Fuse Type3</th>
                                                <th style="white-space: normal" class="heading">Fuse Quantity</th>
                                                <th style="white-space: normal" class="heading">Contacter</th>
                                                <th style="white-space: normal" class="heading">Contacter Type</th>
                                                <th style="white-space: normal" class="heading">Contacter Capacity</th>
                                                <th style="white-space: normal" class="heading">Mccb</th>
                                                <th style="white-space: normal" class="heading">Mccb1</th>
                                                <th style="white-space: normal" class="heading">Mccb2</th>
                                                <th style="white-space: normal" class="heading">Mccb3</th>
                                                <th style="white-space: normal" class="heading">Mccb Type1</th>
                                                <th style="white-space: normal" class="heading">Mccb Type2</th>
                                                <th style="white-space: normal" class="heading">Mccb Type3</th>
                                                <th style="white-space: normal" class="heading">Mccb Quantity</th>
                                                <th style="white-space: normal" class="heading">main Switch Rating</th>
                                                <th style="white-space: normal" class="heading">Main Switch Type</th>
                                                <th style="white-space: normal" class="heading">Auto Switch Type</th>
                                                <th style="white-space: normal" class="heading">Enclosure Type</th>
                                                <th style="white-space: normal" class="heading">Is Working</th>
                                                <th style="white-space: normal" class="heading">Remark</th>
                                                <th style="white-space: normal" class="heading">Active</th>
                                                <th style="white-space: normal" class="heading">Road Catgry.</th>
                                                
                                                <th style="white-space: normal" class="heading">Road Use</th>
                                                <th style="white-space: normal" class="heading">Service Conn. No.</th>
                                                <th style="white-space: normal" class="heading">City</th>
                                                
                                                <th style="white-space: normal" class="heading">
                                                    <%--  <input type="checkbox" name="checkinbulk" id="checkinbulk" onclick="checkUncheckAll()" />  --%>
                                                    IVRS No.
                                                </th>
                                                <th style="white-space: normal; display:none" class="heading">Long-Lati</th>
                                                
                                                <th style="white-space: normal" class="heading">Measured Load KW</th>
                                                <th style="white-space: normal" class="heading">Division</th>
                                                <th style="white-space: normal" class="heading">Zone</th>
                                                <th style="white-space: normal" class="heading">Ward No.</th>
                                                <th style="white-space: normal" class="heading">Is On Pole</th>
                                                


                                                <%--<th style="white-space: normal" class="heading">Controller Model</th>
                                                <th style="white-space: normal" class="heading">Mobile No</th>
                                                <th style="white-space: normal" class="heading">Sim No</th>
                                                <th style="white-space: normal" class="heading">Imei No</th>
                                                <th style="white-space: normal" class="heading">Panel File No</th>
                                                <th style="white-space: normal" class="heading">Panel Transferred Status</th>--%>

                                                <!--         <th style="white-space: normal" class="heading">Survey Rev No</th>  X    -->
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="poleTypeBean" items="${requestScope['surveyTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)">${poleTypeBean.switching_point_detail_id}
                                                        <input type="text" name="switching_point_detail_id" id="switching_point_detail_id${loopCounter.count}" value=${poleTypeBean.switching_point_detail_id} />
                                                        <input type="text" name="switching_rev_no" id="switching_rev_no${loopCounter.count}" value=${poleTypeBean.survey_rev_no} />
                                                        <%--        <input type="text" name="pole_id" id="pole_id${loopCounter.count}" value=${poleTypeBean.pole_id} />
                                                                <input type="text" name="pole_rev_no" id="pole_rev_no${loopCounter.count}" value=${poleTypeBean.pole_rev_no} />  --%>
                                                        <input type="text" name="map_id" id="map_id${loopCounter.count}" value=${poleTypeBean.map_id} />
                                                    </td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_no_s}</td>
                                                    <%--         <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_no}</td>   --%>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_no}

                                                        <input style="display:none" id="pole_no_ck_bx${loopCounter.count}" class="checkbox2" name="pole_no_ck_bx" type="checkbox" onclick="poleSingleCheckUncheck(${loopCounter.count})"/>

                                                        <input type="text"style="display:none" class="pole_no_edit" id="pole_no_edit${loopCounter.count}" name="pole_no_edit" value="${poleTypeBean.pole_no}" readonly/>

                                                    </td>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.switching_point_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.area_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.road_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.feeder}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.no_of_poles}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.source_wattage}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.isworking}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.switching_load}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.meter_no_s}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.gps_code_s}</td>
                                                    <%--           <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.survey_date}</td>  --%>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_span}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.pole_height}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mounting_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mounting_height}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.gps_code}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.max_avg_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.min_avg_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.avg_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.standard_avg_lux_level}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.traffic_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse_type1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse_type2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.fuse_quantity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.contacter}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.contacter_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.contacter_capacity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb_type}</td>
                                                     <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb_type1}</td>
                                                      <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb_type2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mccb_quantity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.main_switch_reading}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.main_switch_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.auto_switch_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.enclosure_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.is_working}</td>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.remark}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.active}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.road_category}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.road_use}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.service_conn_no}</td>
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.city}</td>
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >
                                                        <input id="ivrs_no${loopCounter.count}" class="checkbox1" name="ivrs_no" type="checkbox" onclick="singleCheckUncheck(${loopCounter.count})"/>
                                                        <input type="text" class="ivrs_no_id" id="ivrs_no_id${loopCounter.count}" name="ivrs_no_id" value="0" style="display:none"/>
                                                        <input type="text" class="ivrs_no_edit" id="ivrs_no_edit${loopCounter.count}" name="ivrs_no_edit" value="${poleTypeBean.ivrs_no}" readonly/>
                                                    </td>


                                                    <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)">
                                                        <input type="text" id="longitude${loopCounter.count}" name="longitude" value="${poleTypeBean.longitude}"/>
                                                        <input type="text"  id="lattitude${loopCounter.count}" name="lattitude" value="${poleTypeBean.lattitude}"/>
                                                    </td>
                                                   
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.measured_load}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.division}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.zone}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.ward_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.isCheckedTrue}</td>
                                                    

                                                   <%-- <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.contacter_make}</td>-->

                                                    <%--<td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.controller_model}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.mobile_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.sim_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.imei_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.panel_file_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.panel_transferred_status}</td>--%>

                                                    <%--     <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${poleTypeBean.survey_rev_no}</td>  --%>
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='left' colspan="45">
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
                                                <td><input class="button" type="submit" name="task" id="updateIVRS" value="updateIVRS" ></td>
                                            </tr>
                                            <tr id="messagee">
                                                <c:if test="${not empty messagee}">
                                                    <td colspan="8" bgcolor="${msgBgColorr}"><b>Result: ${messagee}</b></td>
                                                </c:if>
                                            </tr>
                                            <!--- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. -->
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" id="noOfRowsTraversed" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input  type="hidden" id="searchPoleNoSwitch" name="searchPoleNoSwitch" value="${searchPoleNoSwitch}" >
                                            <input  type="hidden" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" >
                                            <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                            
                                            <input  type="hidden" id="meter_name_auto" name="meter_name_auto" value="${searchMeterNameAuto}" >
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

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="switchingPointSurvey" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>

                                            <tr>
                                                <th class="heading">Switching Point Pole No.</th>
                                                <td>
                                                    <input class="input" type="hidden" id="switching_point_detail_id" name="switching_point_detail_id" value="" >
                                                    <input class="input" type="hidden" id="switching_rev_no" name="switching_rev_no" value="" >
                                                    <input class="input" type="text" id="pole_no_s" name="pole_no_s" value="${surveyTypeBean.pole_no_s eq '' ? '' : surveyTypeBean.pole_no_s}" size="20" disabled>
                                                </td>
                                                <th class="heading">Switching Point Name</th>
                                                <td>
                                                    <input class="input" type="text" id="switching_point_name" name="switching_point_name" value="" size="20" disabled required>
                                                </td>
                                                <th class="heading">Switching Point GPS Code</th>
                                                <td>
                                                    <input class="input" type="text" id="gps_code_s" name="gps_code_s" value="" size="20" disabled required>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading">Service Conn. No.</th>
                                                <td>
                                                    <input class="input" type="text" id="service_conn_no" name="service_conn_no" value="${surveyTypeBean.service_conn_no eq '' ? '' : surveyTypeBean.service_conn_no}" size="20" disabled required>
                                                </td>
                                                <th class="heading">IVRS No.</th>
                                                <td>
                                                    <input class="input" type="text" id="ivrs_no" name="ivrs_no" value="${surveyTypeBean.ivrs_no eq '' ? '' : surveyTypeBean.ivrs_no}" size="20" disabled required>
                                                </td>
                                                <th class="heading">No. of Poles Connected</th>
                                                <td>
                                                    <input class="input" type="text" id="no_of_poles" name="no_of_poles" value="" size="20" disabled>
                                                </td>
                                            </tr>

                                            <tr>

                                                <!--   <th class="heading">Survey Date</th>-->
                                                <td style="display:none">
                                                    <input type="text" id="survey_date" name="survey_date" value="" >
                                                    <%-- <a href="#" onclick="setYears(1947,2022);showCalender(this,'feedback_date')">
                                                         <img alt=""  src="images/calender.png">
                                                     </a>   --%>
                                                    <!--      <input  type="text" id="submission_time_hour" name="submission_time_hour"  value="" size="2"
                                                                  onchange="IsTimeNumeric(id)" Placeholder ="HH" onkeyup="IsTimeNumeric(id)">
                                                    <%-- <b>:</b>   --%>
                                                    <input  type="text" id="submission_time_min" name="submission_time_min" Placeholder ="MM"
                                                            value="" size="2" onchange="IsTimeNumeric(id)" onkeyup="IsTimeNumeric(id)">
                                                    <select id="submission_time" name="submission_time" class="dropdown" style="width: auto" >
                                                        <option>AM</option>
                                                        <option>PM</option>
                                                    </select-->
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading">Meter No</th>
                                                <td>
                                                    <input class="input" type="text" id="meter_no_s" name="meter_no_s" value="${surveyTypeBean.meter_no_s eq '' ? '' : surveyTypeBean.meter_no_s}" size="20" disabled>
                                                </td>
                                                <th class="heading">Phase</th>
                                                <td>
                                                    <input class="input" type="text" id="phase" name="phase" value="${surveyTypeBean.phase_no eq '' ? '' : surveyTypeBean.phase_no}" size="20" maxlength="1" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)" disabled required>
                                                </td>
                                                <th class="heading">Is On Pole
                                                    <input class="input" type="checkbox" id="is_on_pole" name="is_on_pole" size="20" onchange="poleNoBox_hide()"  disabled>
                                                </th>
                                                <td>
                                                    <span id ="pole_no_box">
                                                        <b class="heading">Pole No. </b>
                                                        <input class="input" type="text" id="pole_no" name="pole_no" value="${surveyTypeBean.pole_no eq '' ? '' : surveyTypeBean.pole_no}" size="11" disabled>
                                                    </span>
                                                </td>
                                                <%--    <th class="heading">Pole No</th>
                                                   <td>

                                                   </td>  --%>
                                            </tr>
                                            <tr>
                                                <th class="heading">Division</th>
                                                <td>
                                                    <input class="input" type="text" id="division" name="division" value="${surveyTypeBean.division eq '' ? '' : surveyTypeBean.division}" size="20" disabled>
                                                </td>
                                                <th class="heading">Zone</th>
                                                <td>
                                                    <input class="input" type="text" id="zone" name="zone" value="${surveyTypeBean.zone eq '' ? '' : surveyTypeBean.zone}" size="20" disabled>
                                                </td>
                                                <th class="heading">Feeder</th>
                                                <td>
                                                    <input class="input" type="text" id="feeder" name="feeder" value="${surveyTypeBean.feeder eq '' ? '' : surveyTypeBean.feeder}" size="20" disabled required>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading">City</th>
                                                <td><input class="input" type="text" id="city" name="city" value="${surveyTypeBean.city eq '' ? '' : surveyTypeBean.city}" size="20" disabled>
                                                </td>
                                                <th class="heading">Ward No</th>
                                                <td><input class="input" type="text" id="ward_no" name="ward_no" value="" size="20" disabled >
                                                </td>
                                                <th class="heading">Area Name</th>
                                                <td><input class="input" type="text" id="area_name" name="area_name" value="" size="20" disabled >
                                                </td>
                                            </tr>
                                            <tr>                                             
                                                <th class="heading">Road Category</th>
                                                <td>
                                                    <input class="input" type="text" id="road_category" name="road_category" value="" size="20" disabled required>
                                                </td>
                                                <th class="heading">Road Use</th>
                                                <td><input class="input" type="text" id="road_use" name="road_use" value="" size="20" disabled >
                                                </td>
                                                <th class="heading">Road Name</th>
                                                <td><input class="input" type="text" id="road_name" name="road_name" value="" size="20" disabled></td>
                                            </tr>
                                            <tr>                                                
                                                <th class="heading">Traffic Type</th>
                                                <td>
                                                    <input class="input" type="text" id="traffic_type" name="traffic_type" value="${surveyTypeBean.traffic_type eq '' ? '' : surveyTypeBean.traffic_type}" size="20" disabled>
                                                </td>
                                                <th class="heading">Longitude</th>
                                                <td><input class="input" type="text" id="logitude" name="logitude" value="${surveyTypeBean.longitude eq '' ? '' : surveyTypeBean.longitude}" size="20" disabled required></td>
                                                <th class="heading">Latitude</th>
                                                <td><input class="input" type="text" id="lattitude" name="lattitude" value="${surveyTypeBean.lattitude eq '' ? '' : surveyTypeBean.lattitude}" size="20" disabled required>
                                                </td>
                                            </tr>

                                            <tr>
                                                <th class="heading">Fuse</th>
                                                <td>
                                                    <input type="radio" id="fuseYes" name="fuseY" value="Y" onclick="uncheckAnother(id)" >Yes
                                                    <input type="radio" id="fuseNo" name="fuseN" value="N" onclick="uncheckAnother(id)" checked>No
                                                </td>
                                                <!--  <input class="input" type="text" id="fuse" name="fuse" value="" size="10" disabled></td>-->

                                                <th class="heading">Fuse Quantity</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_quantity" name="fuse_quantity" value="0" size="20" disabled>
                                                </td>

                                            </tr>
                                            <tr>
                                                <th class="heading">Fuse Type1</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_type1" name="fuse_type1" value="${surveyTypeBean.fuse_type eq '' ? '' : surveyTypeBean.fuse_type}" size="20" disabled >
                                                </td>
                                                <th class="heading">Fuse 1</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_1" name="fuse_1" value="${surveyTypeBean.fuse1 eq '' ? '' : surveyTypeBean.fuse1}" size="20" disabled>
                                                </td>
                                                <th class="heading">Fuse Type2</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_type2" name="fuse_type2" value="${surveyTypeBean.fuse_type1 eq '' ? '' : surveyTypeBean.fuse_type1}" size="20" disabled>
                                                </td>
                                                <!--  <input class="input" type="text" id="fuse" name="fuse" value="" size="10" disabled></td>-->



                                            </tr>
                                            <tr>
                                                <th class="heading">Fuse 2</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_2" name="fuse_2" value="${surveyTypeBean.fuse2 eq '' ? '' : surveyTypeBean.fuse2}" size="20" disabled>
                                                </td>
                                                <th class="heading">Fuse Type3</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_type3" name="fuse_type3" value="${surveyTypeBean.fuse_type2 eq '' ? '' : surveyTypeBean.fuse_type2}" size="20" disabled>
                                                </td>
                                                <th class="heading">Fuse 3</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_3" name="fuse_3" value="${surveyTypeBean.fuse3 eq '' ? '' : surveyTypeBean.fuse3}" size="20" disabled>
                                                </td>

                                            </tr>

                      
                                            <tr>
                                                <th class="heading">Mccb</th>
                                                <td>
                                                    <input type="radio" id="mccbYes" name="mccbY" value="Y" onclick="uncheckAnother(id)">Yes
                                                    <input type="radio" id="mccbNo" name="mccbN" value="N" onclick="uncheckAnother(id)" checked>No
                                                </td>

                                                <th class="heading">Mccb Quantity</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_quantity" name="mccb_quantity" value="0" size="20" disabled>
                                                </td>
                                              
                                            </tr>
                                            <tr>
                                                  <th class="heading">Mccb Type1</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_type1" name="mccb_type1" value="${surveyTypeBean.mccb_type eq '' ? '' : surveyTypeBean.mccb_type}" size="20" disabled>
                                                </td>
                                                <th class="heading">Mccb 1</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_1" name="mccb_1" value="${surveyTypeBean.mccb1 eq '' ? '' : surveyTypeBean.mccb1}" size="20" disabled>
                                                </td>
                                                  <th class="heading">Mccb Type2</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_type2" name="mccb_type2" value="${surveyTypeBean.mccb_type1 eq '' ? '' : surveyTypeBean.mccb_type1}" size="20" disabled>
                                                </td>
                                                <!--  <input class="input" type="text" id="fuse" name="fuse" value="" size="10" disabled></td>-->
                                               

                                            </tr>
                                            <tr>


                                                 <th class="heading">Mccb 2</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_2" name="mccb_2" value="${surveyTypeBean.mccb2 eq '' ? '' : surveyTypeBean.mccb2}" size="20" disabled>
                                                </td>
                                                <th class="heading">Mccb Type3</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_type3" name="mccb_type3" value="${surveyTypeBean.mccb_type2 eq '' ? '' : surveyTypeBean.mccb_type2}" size="20" disabled>
                                                </td>
                                                <th class="heading">Mccb 3</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_3" name="mccb_3" value="${surveyTypeBean.mccb3 eq '' ? '' : surveyTypeBean.mccb3}" size="20" disabled>
                                                </td>

                                            </tr>
                                            
                                            <tr>

                                                <th class="heading">Contacter</th>
                                                <td>
                                                    <input type="radio" id="contacterYes" name="contacterY" value="Y" onclick="uncheckAnother(id)">Yes
                                                    <input type="radio" id="contacterNo" name="contacterN" value="N" onclick="uncheckAnother(id)" checked>No
                                                </td>

                                               
                                            </tr>
                                            <tr>
                                                 <th class="heading">Contacter Capacity</th>
                                                <td>
                                                    <input class="input" type="text" id="contacter_capacity" name="contacter_capacity" value="${surveyTypeBean.contacter_capacity eq '' ? '' : surveyTypeBean.contacter_capacity}" size="20" disabled required>
                                                </td>
                                                <th class="heading">Contacter Type</th>
                                                <td>
                                                    <input class="input" type="text" id="contacter_type" name="contacter_type" value="${surveyTypeBean.contacter_type eq '' ? '' : surveyTypeBean.contacter_type}" size="20" disabled required>
                                                </td>
                                                  <th class="heading">Contacter Make</th>
                                                <td>
                                                    <input class="input" type="text" id="contacter_make" name="contacter_make" value="${surveyTypeBean.contacter_make eq '' ? '' : surveyTypeBean.contacter_make}" size="20" disabled required>
                                                </td>

                                            </tr>

                                            <tr>
                                                <th class="heading">Control Mechanism</th>
                                                <td>
                                                    <input class="input" type="text" id="control_mechanism_type" name="control_mechanism_type" value="" size="20" disabled required>
                                                </td>
                                                <th class="heading">Is Working</th>
                                                <td><input class="input" type="text" id="is_working" name="is_working" value="" size="20" disabled></td>
                                                
                                            </tr>
                                             <tr>
                                                <th class="heading">Auto Switch Type</th>
                                                <td>
                                                    <input class="input" type="text" id="auto_switch_type" name="auto_switch_type" value="${surveyTypeBean.auto_switch_type eq '' ? '' : surveyTypeBean.auto_switch_type}" size="20" disabled required>
                                                </td>
                                                <th class="heading">Main Switch Type</th>
                                                <td><input class="input" type="text" id="main_switch_type" name="main_switch_type" value="${surveyTypeBean.main_switch_type eq '' ? '' : surveyTypeBean.main_switch_type}" size="20" disabled required></td>
                                                <th class="heading">Main Switch Reading</th>
                                                <td><input class="input" type="text" id="main_switch_reading" name="main_switch_reading" value="${surveyTypeBean.main_switch_reading eq '' ? '' : surveyTypeBean.main_switch_reading}" size="20" disabled required></td>

                                            </tr>
                                            <tr>
                                                <th class="heading">Enclosure Type</th>
                                                <td><input class="input" type="text" id="enclosure_type" name="enclosure_type" value="${surveyTypeBean.enclosure_type eq '' ? '' : surveyTypeBean.enclosure_type}" size="20" disabled required></td>

                                                <th class="heading">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="" size="20" disabled></td>


                                            </tr>
                                   
                                            <tr>

                                                <!--  <th class="heading">Active</th>-->
                                                <td style="display:none">
                                                    <input class="input" type="text" id="active" name="active" value="${surveyTypeBean.active eq '' ? '' : surveyTypeBean.active}" size="20" disabled>

                                                </td>
                                                <td style="display:none">
                                                    <input class="input" type="text" id="survey_rev_no" name="survey_rev_no" value="" size="20" disabled>
                                                </td>
                                            </tr>
                                            <tr>

                                                <td><b class="heading">Light Type</b>
                                                    <input class="source_wattage" type="text" id="source_wattage" name="source_wattage" value="" size="10" disabled>
                                                    <input class="text" type="hidden" id="mapp_id" name="mapp_id" value="" size="10" >
                                                    <input class="text" type="hidden" id="light_type_id" name="light_type_id" value="" size="10">
                                                </td>

                                                <td><b class="heading">Quantity</b>
                                                    <input class="input" type="text" id="quantity" name="quantity" value="" size="10" onkeyup="setDefaultWorking();" onclick="setDefaultWorking();" disabled>
                                                </td>

                                                <td><b class="heading">Working</b>
                                                    <input class="input" type="text" id="working" name="working" value="" size="10" onclick="checkQuantity();" onkeyup="setNworkingVal();" disabled>
                                                </td>

                                                <td><b class="heading">Not Working</b>
                                                    <input class="input" type="text" id="n_working" name="n_working" value="" size="6" onclick="checkWorking();" onkeyup="setWorkingVal();" disabled>
                                                </td>
                                                <td><b class="heading">Measured Load</b>
                                                    <input class="input" type="text" id="m_load" name="m_load" value="" size="6" disabled>
                                                </td>
                                                <td align='center'colspan="2"><input class="button" type="button" name="addMore" id="addMore" value="Add More" onclick="addRow()" disabled></td>
                                            </tr>

                                            <c:forEach  begin="1" end="50" step="1" varStatus="loopCounter">
                                                <tr id="dynamic${loopCounter.count}" style="display:none">

                                                    <td><b class="heading" id="sw${loopCounter.count}" >Light Type</b>
                                                        <input class="source_wattage" type="text" id="source_wattage${loopCounter.count}" name="source_wattage" value="" size="10"  >
                                                        <input class="source_wattage" type="hidden" id="mapp_id${loopCounter.count}" name="mapp_id" value="0" size="10"  >
                                                        <input class="source_wattage" type="hidden" id="light_type_id${loopCounter.count}" name="light_type_id" value="0" size="10"  >
                                                    </td>

                                                    <td><b class="heading" id ="q${loopCounter.count}" >Quantity</b>
                                                        <input class="input" type="text" id="quantity${loopCounter.count}" name="quantity" value="" size="10"  onkeyup="setDefaultWorkingg(id);" onclick="setDefaultWorkingg(id);">
                                                    </td>

                                                    <td><b class="heading" id="w${loopCounter.count}" >Working</b>
                                                        <input class="input" type="text" id="working${loopCounter.count}" name="working" value="" size="10"  onclick="checkQuantityy(id);" onkeyup="setNworkingVall(id);" >
                                                    </td>

                                                    <td><b class="heading" id ="nw${loopCounter.count}" >Not Working</b>
                                                        <input class="input" type="text" id="n_working${loopCounter.count}" name="n_working" value="" size="6"  onclick="checkWorkingg(id);" onkeyup="setWorkingVall(id);" >
                                                    </td>

                                                </tr>                        
                                            </c:forEach>
                                        </table>
                                        <table>  <tr>
                                                <td align='center' colspan="6">
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" > &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" > &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" > &nbsp;&nbsp;
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="button" name="new" id="edit" value="Edit" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" > &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" >
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input  type="hidden" id="searchPoleNoSwitch" name="searchPoleNoSwitch" value="${searchPoleNoSwitch}" >
                                            <input  type="hidden" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" > 
                                            <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                            <input  type="hidden" id="meter_name_auto" name="meter_name_auto" value="${searchMeterNameAuto}" >
                                            <input  type="hidden" id="searchAreaName" name="searchAreaName" value="${searchAreaName}" >
                                            <input  type="hidden" id="searchRoadName" name="searchRoadName" value="${searchRoadName}" >
                                            <input  type="hidden" id="addRowCount" name="addRowCount" value="0" >
                                            <input  type="hidden" id="isCheckedTrue" name="isCheckedTrue" value="Y" >
                                            <%--    <input  type="hidden" id="pole_id" name="pole_id" value="" >
                                                 <input  type="hidden" id="pole_rev_no" name="pole_rev_no" value="" >   --%>
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
