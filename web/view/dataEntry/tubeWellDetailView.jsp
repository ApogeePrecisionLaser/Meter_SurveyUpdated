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
    $(document).ready(function() {
        //alert("ready");
        //$("#pole_no_s").val()!==''&& alert($("#meter_no_s").val());
        if($("#meter_no_s").val()!=''){
            //alert("makeEditable start");
            makeEditable('');
            //alert("makeEditable end");

        }


    });
  

    jQuery(function(){ 
    //alert("vivek1");
//        if($('form[name="form1"] input[type=checkbox]:checked').size() == 0){
//            $("#updateIVRS").attr("disabled", "disabled");
//        }else{
//            $("#updateIVRS").removeAttr("disabled");
//        }
        //alert("1");
        $("#zone_search").autocomplete("tubeWellDetailCont", {

            extraParams: {
                action1: function() { return "getZoneSearch"}
            }
        });
//        $("#searchPoleNoSwitch").autocomplete("tubeWellDetailCont", {
//
//            extraParams: {
//                action1: function() { return "getPoleNoSwitch"}
//            }
//        });
        //alert("2");
        $("#service_conn_no_Search").autocomplete("tubeWellDetailCont", {

            extraParams: {
                action1: function() { return "getIvrsNoSearch"}
            }
        });
        $("#searchPoleNo").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getPoleNo"}
            }
        });
        //alert("3");
        $("#searchSwitchingPtName").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getSwitchingPtName"}
            }
        });
//        $("#searchPoleType").autocomplete("tubeWellDetailCont", {
//            extraParams: {
//                action1: function() { return "getPoleType"}
//            }
//        });
        //alert("4");
        $("#searchAreaName").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getAreaName"}
            }
        });
        $("#searchRoadName").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getRoadName"}
            }
        });
        //alert("5");

        //////////////////////////////////////////////////////////////////////////////////////////////////
        $("#zone").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getZoneName"}
            }
        });
        $("#zone").result(function(event, data, formatted){
            document.getElementById("ward_no").value = "";
            document.getElementById("area_name").value = "";
            $("#ward_no").flushCache();
            $("#area_name").flushCache();
        });

        $("#ward_no").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getWard_No"},
                action2: function() { return document.getElementById("zone").value;}
            }
        });
            $("#ward_no").result(function(event, data, formatted){
            document.getElementById("area_name").value = "";
            $("#area_name").flushCache();
        });
        //alert("6");

        $("#area_name").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getAreaName"},
                action2: function() { return document.getElementById("ward_no").value;}
            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        $("#division").result(function(event, data, formatted){
//            document.getElementById("zone").value = "";
//            document.getElementById("feeder").value = "";
//            $("#zone").flushCache();
//            $("#feeder").flushCache();
//        });
        //alert("7");
//        $("#zone").result(function(event, data, formatted){
//            document.getElementById("feeder").value = "";
//            $("#feeder").flushCache();
//        });
//        $("#city").result(function(event, data, formatted){
//            document.getElementById("ward_no").value = "";
//            document.getElementById("area_name").value = "";
//            $("#ward_no").flushCache();
//            $("#area_name").flushCache();
//        });
        //alert("8");
//        $("#ward_no").result(function(event, data, formatted){
//            document.getElementById("area_name").value = "";
//            $("#area_name").flushCache();
//        });
        $("#road_category").result(function(event, data, formatted){
            document.getElementById("road_name").value = "";
            document.getElementById("road_use").value = "";
            $("#road_name").flushCache();
            $("#road_use").flushCache();
        });
        //alert("9");
        $("#road_use").result(function(event, data, formatted){
            document.getElementById("road_name").value = "";
            $("#road_name").flushCache();
        });
        //alert("10");
    
    });
    jQuery(function(){
        //alert("11");
        for(var i=0; i<50; i++){
            $("#source_wattage"+i).autocomplete("tubeWellDetailCont", {
                extraParams: {
                    action1: function() { return "getLightType"}
                }
            });
        }
        //alert("12");
        $("#source_wattage").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getLightType"}
            }
        });
        $("#pole_no").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getPoleNo"}
            }
        });
        //alert("13");
        $("#division").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getDivision"}
            }
        });
        $("#road_use").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getRoadUse"},
                action2: function() { return document.getElementById("road_category").value;}
            }
        });
        //alert("14");
        $("#road_category").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getRoad_Category"}
            }
        });

        $("#road_name").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getRoadName"},
                action2: function() { return document.getElementById("road_category").value;},
                action3: function() { return document.getElementById("road_use").value;}
            }
        });

       // alert("15");
       $("#feeder_zone").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getFeederZoneName"}
                //action2: function() { return document.getElementById("ward_no").value;}
            }
        });
        $("#feeder_zone").result(function(event, data, formatted){
            document.getElementById("feeder").value = "";
            $("#feeder").flushCache();
        });
        $("#feeder").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getFeeder"},
                action2: function() { return document.getElementById("feeder_zone").value;}
            }
        });

        
        //alert("17");
        $("#traffic_type").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getTrafficType"}
            }
        });
        $("#control_mechanism_type").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getControlType"}
            }
        });
        //alert("18");
        $("#tube_well_name").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getSwitchType"}
            }
        });
        $("#fuse_type1").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
        //alert("19");
        $("#starter_make").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getStarterType"}
            }
        });
        $("#mccb_type1").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
        //alert("20");
        
        $("#fuse_type2").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
       
        $("#mccb_type2").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
        //alert("21");
       
        $("#fuse_type3").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getFuseType"}
            }
        });
       
        $("#mccb_type3").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getMccbType"}
            }
        });
        //alert("22");
      
//        $("#city").autocomplete("tubeWellDetailCont", {
//            extraParams: {
//                action1: function() { return "getCity"}
//            }
//        });
        $("#auto_switch_type").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getAutoSwitchType"}
            }
        });
        //alert("23");
        $("#main_switch_type").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getMainSwitchType"}
            }
        });
        $("#enclosure_type").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getEnclosureType"}
            }
        });
        //alert("24");
        $("#ivrs_no").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getIVRSNo"},
                action2: function() { return document.getElementById("pole_no_s").value;}
            }
        });
        $("#pole_no_s").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getPoleNoS"}
            }
        });
        //alert("25");
        $("#meter_no_s").autocomplete("tubeWellDetailCont", {
            extraParams: {
                action1: function() { return "getMeterNo"},
                action2: function() { return document.getElementById("pole_no_s").value;}
            }
        });

       //alert("26");
    });
    function openMap(longitude, latitude){

        var x = $.trim(document.getElementById(latitude).value);
        var y = $.trim(document.getElementById(longitude).value);
        var url="tubeWellDetailCont?task=showMapWindow&longitude="+y+"&latitude="+x;
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
       //alert("1");
        document.getElementById("pole_no_s").disabled = false;
        document.getElementById("tube_well_name").disabled = false;
        document.getElementById("gps_code_s").disabled = false;
        //  document.getElementById("survey_date").disabled = false;
        document.getElementById("meter_no_s").disabled = false; 
        document.getElementById("phase").disabled = false;
        document.getElementById("meter_status").disabled = false;
        //alert("2");
        //  document.getElementById("pole_no").disabled = false;
        document.getElementById("area_name").disabled = false;
        document.getElementById("road_name").disabled = false;
        document.getElementById("traffic_type").disabled = false;
        //   document.getElementById("fuse").disabled = false;
        document.getElementById("fuse_type1").disabled = false;
        document.getElementById("fuse_type2").disabled = false;
        //alert("3");
        document.getElementById("fuse_type3").disabled = false;
        document.getElementById("fuse_quantity").disabled = false;
        //  document.getElementById("Starter").disabled = false;
        document.getElementById("starter_type").disabled = false;
        document.getElementById("starter_capacity").disabled = false;
        document.getElementById("starter_make").disabled = false;
        //alert("4");
        document.getElementById("mccb_type1").disabled = false;
        document.getElementById("mccb_type2").disabled = false;
        document.getElementById("mccb_type3").disabled = false;
        document.getElementById("mccb_quantity").disabled = false;
        document.getElementById("control_mechanism_type").disabled = false;
        document.getElementById("is_working").disabled = false;
        //alert("5");
        document.getElementById("remark").disabled = false;
        // document.getElementById("active").disabled = false;
        //document.getElementById("survey_rev_no").disabled = false;
        // document.getElementById("source_wattage").disabled = false;
        //   document.getElementById("quantity").disabled = false;
        //  document.getElementById("working").disabled = false;
        //    document.getElementById("n_working").disabled = false;
        document.getElementById("latitude").disabled = false;
        document.getElementById("longitude").disabled = false;
        document.getElementById("road_use").disabled = false;
        document.getElementById("road_category").disabled = false;
       // alert("6");
        document.getElementById("feeder").disabled = false;
        document.getElementById("zone").disabled = false;
        document.getElementById("no_of_poles").disabled = false;
        document.getElementById("ivrs_no").disabled = false;
        document.getElementById("division").disabled = false;
       // alert("7");
        //  document.getElementById("zone").disabled = false;
        document.getElementById("feeder_zone").disabled = false;
        document.getElementById("ward_no").disabled = false;
        // document.getElementById("m_load").disabled = false;
        document.getElementById("service_conn_no").disabled = false;
        // document.getElementById("is_on_pole").disabled = false;
        document.getElementById("fuse_1").disabled = false;
        //alert("8");
        document.getElementById("fuse_2").disabled = false;
        document.getElementById("fuse_3").disabled = false;
        document.getElementById("mccb_1").disabled = false;
        document.getElementById("mccb_2").disabled = false;
        document.getElementById("mccb_3").disabled = false;
        //alert("9");
        document.getElementById("auto_switch_type").disabled = false;
        document.getElementById("main_switch_type").disabled = false;
        document.getElementById("main_switch_rating").disabled = false;
        document.getElementById("enclosure_type").disabled = false;
        document.getElementById("r_phase").disabled = false;
        document.getElementById("b_phase").disabled = false;
        document.getElementById("y_phase").disabled = false;

        document.getElementById("meter_address").disabled = false;
        
       // alert("10");
     

        /*document.getElementById("controller_model").disabled = false;
        document.getElementById("mobile_no").disabled = false;
        document.getElementById("sim_no").disabled = false;
        document.getElementById("imei_no").disabled = false;
        document.getElementById("panel_file_no").disabled = false;
         */
        document.getElementById("save").disabled = false;
        document.getElementById("save_As").disabled = false;
        document.getElementById("cancel").disabled = false;
        document.getElementById("revise").disabled = false;
        //document.getElementById("addMore").disabled = false;
        document.getElementById("update").disabled = false;
       // alert("11");

        if(id == 'new') {

            $("#message").html("");
            //      $('.source_wattage').attr('readonly', false);
            //      $('.source_wattage').prop('readonly', false);
            //       $('input[name="source_wattage"]').prop('readonly', false);
            //alert("12");
            document.getElementById("revise").disabled = true;
            document.getElementById("cancel").disabled = true;
            document.getElementById("update").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            //alert("13");
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 54);
            document.getElementById("pole_no_s").focus();
            document.getElementById("addMore").disabled = false;
            document.getElementById("addRowCount").value = "0";
            //alert("14");

            deleteRow();
            //alert("15");
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
            var tube_well_name = document.getElementById("tube_well_name").value;
            var service_conn_no = document.getElementById("service_conn_no").value;
            var ivrs_no = document.getElementById("ivrs_no").value;

            var no_of_poles = document.getElementById("no_of_poles").value;

            var feeder = document.getElementById("feeder").value;
            var area_name = document.getElementById("area_name").value;
            var road_name = document.getElementById("road_name").value;
            var traffic_type = document.getElementById("traffic_type").value;
            var fuse_type = document.getElementById("fuse_type").value;
            var mccb_type = document.getElementById("mccb_type").value;
            var starter_type = document.getElementById("starter_type").value;
            var timer_type = document.getElementById("timer_type").value;
            var control_mechanism_type = document.getElementById("control_mechanism_type").value;
            var m_load = document.getElementById("m_load").value;
            var pole_no = document.getElementById("pole_no").value;
         
            var source_wattage = document.getElementById("source_wattage").value;

            var quantity = document.getElementById("quantity").value;
               
            var isOnPole = $("#is_on_pole").is(":checked");

            if(myLeftTrim(tube_well_name).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Switching Point Name is Required...</b></td>");
                document.getElementById("tube_well_name").focus();
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
            if(myLeftTrim(starter_type).length == 0) {
                $("#message").html("<td colspan='6' bgcolor='coral'><b>Starter Type is Required...</b></td>");
                document.getElementById("starter_type").focus();
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
        var noOfColumns = 53;
        var columnId = id;
       
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
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


            //    alert(document.getElementById("tube_well_detail_id" + rowNo).value+","+document.getElementById("tube_well_rev_no" + rowNo).value);
            //    alert(document.getElementById("pole_id" + rowNo).value+","+document.getElementById("pole_rev_no" + rowNo).value);
            document.getElementById("tube_well_detail_id").value=document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
         
            document.getElementById("tube_well_rev_no").value= document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
            //    document.getElementById("pole_id").value = $.trim(document.getElementById("pole_id" + rowNo).value);
            //     document.getElementById("pole_rev_no").value = $.trim(document.getElementById("pole_rev_no" + rowNo).value);
            //document.getElementById("ward_name").value = document.getElementById(t1id +(lowerLimit+2)).innerHTML;
            document.getElementById("pole_no_s").value = $.trim(document.getElementById(t1id +(lowerLimit+3)).innerHTML);
            //  document.getElementById("pole_no").value = $.trim(document.getElementById(t1id +(lowerLimit+3)).innerHTML);
            //   document.getElementById("pole_no").value = $.trim(document.getElementById("pole_no_edit" + rowNo).value);
            document.getElementById("tube_well_name").value = $.trim(document.getElementById(t1id +(lowerLimit+4)).innerHTML);
            document.getElementById("gps_code_s").value = $.trim(document.getElementById(t1id +(lowerLimit+5)).innerHTML);
            document.getElementById("service_conn_no").value = $.trim(document.getElementById(t1id +(lowerLimit+6)).innerHTML);
            document.getElementById("ivrs_no").value = $.trim(document.getElementById(t1id +(lowerLimit+7)).innerHTML);
            document.getElementById("no_of_poles").value = $.trim(document.getElementById(t1id +(lowerLimit+8)).innerHTML);
            document.getElementById("meter_no_s").value = $.trim(document.getElementById(t1id +(lowerLimit+9)).innerHTML);
            document.getElementById("division").value = $.trim(document.getElementById(t1id +(lowerLimit+10)).innerHTML);
            document.getElementById("phase").value = $.trim(document.getElementById(t1id +(lowerLimit+11)).innerHTML);
//            document.getElementById("zone").value = $.trim(document.getElementById(t1id +(lowerLimit+12)).innerHTML);
            document.getElementById("feeder_zone").value = $.trim(document.getElementById(t1id +(lowerLimit+12)).innerHTML);
            document.getElementById("feeder").value = $.trim(document.getElementById(t1id +(lowerLimit+13)).innerHTML);

//            document.getElementById("feeder_zone").value = $.trim(document.getElementById(t1id +(lowerLimit+14)).innerHTML);
            document.getElementById("zone").value = $.trim(document.getElementById(t1id +(lowerLimit+14)).innerHTML);

            document.getElementById("ward_no").value = $.trim(document.getElementById(t1id +(lowerLimit+15)).innerHTML);
            document.getElementById("area_name").value = $.trim(document.getElementById(t1id +(lowerLimit+16)).innerHTML);
            document.getElementById("road_category").value = $.trim(document.getElementById(t1id +(lowerLimit+17)).innerHTML);
            document.getElementById("road_use").value = $.trim(document.getElementById(t1id +(lowerLimit+18)).innerHTML);
            document.getElementById("road_name").value = $.trim(document.getElementById(t1id +(lowerLimit+19)).innerHTML);
            document.getElementById("traffic_type").value = $.trim(document.getElementById(t1id +(lowerLimit+20)).innerHTML);
            document.getElementById("longitude").value = $.trim(document.getElementById(t1id +(lowerLimit+21)).innerHTML);
            document.getElementById("latitude").value = $.trim(document.getElementById(t1id +(lowerLimit+22)).innerHTML);
            // document.getElementById("fuse").value = $.trim(document.getElementById(t1id +(lowerLimit+23)).innerHTML);
            if(document.getElementById(t1id +(lowerLimit+23)).innerHTML=='Y'){
                document.getElementById('fuseYes').checked = true;
                document.getElementById('fuseNo').checked = false;
            }else{
                document.getElementById('fuseNo').checked = true;
                document.getElementById('fuseYes').checked = false;
            }
            document.getElementById("fuse_quantity").value = $.trim(document.getElementById(t1id +(lowerLimit+24)).innerHTML);
            document.getElementById("fuse_type1").value = $.trim(document.getElementById(t1id +(lowerLimit+25)).innerHTML);
            document.getElementById("fuse_type2").value = $.trim(document.getElementById(t1id +(lowerLimit+26)).innerHTML);
            document.getElementById("fuse_type3").value = $.trim(document.getElementById(t1id +(lowerLimit+27)).innerHTML);
            document.getElementById("fuse_1").value = $.trim(document.getElementById(t1id +(lowerLimit+28)).innerHTML);
            document.getElementById("fuse_2").value = $.trim(document.getElementById(t1id +(lowerLimit+29)).innerHTML);
            document.getElementById("fuse_3").value = $.trim(document.getElementById(t1id +(lowerLimit+30)).innerHTML);
            //  document.getElementById("mccb").value = $.trim(document.getElementById(t1id +(lowerLimit+31)).innerHTML);
            if(document.getElementById(t1id +(lowerLimit+31)).innerHTML=='Y'){
                document.getElementById('mccbYes').checked = true;
                document.getElementById('mccbNo').checked = false;
            }else{
                document.getElementById('mccbNo').checked = true;
                document.getElementById('mccbYes').checked = false;
            }
            document.getElementById("mccb_quantity").value = $.trim(document.getElementById(t1id +(lowerLimit+32)).innerHTML);
            document.getElementById("mccb_type1").value = $.trim(document.getElementById(t1id +(lowerLimit+33)).innerHTML);
            document.getElementById("mccb_type2").value = $.trim(document.getElementById(t1id +(lowerLimit+34)).innerHTML);
            document.getElementById("mccb_type3").value = $.trim(document.getElementById(t1id +(lowerLimit+35)).innerHTML);
            document.getElementById("mccb_1").value = $.trim(document.getElementById(t1id +(lowerLimit+36)).innerHTML);
            document.getElementById("mccb_2").value = $.trim(document.getElementById(t1id +(lowerLimit+37)).innerHTML);
            document.getElementById("mccb_3").value = $.trim(document.getElementById(t1id +(lowerLimit+38)).innerHTML);
            // document.getElementById("starter").value = $.trim(document.getElementById(t1id +(lowerLimit+39)).innerHTML);
            if(document.getElementById(t1id +(lowerLimit+39)).innerHTML=='Y'){
                document.getElementById('StarterYes').checked = true;
                document.getElementById('StarterNo').checked = false;
            }else{
                document.getElementById('StarterNo').checked = true;
                document.getElementById('StarterYes').checked = false;
            }
            document.getElementById("starter_type").value = $.trim(document.getElementById(t1id +(lowerLimit+40)).innerHTML);
            document.getElementById("starter_capacity").value = $.trim(document.getElementById(t1id +(lowerLimit+41)).innerHTML);
            document.getElementById("starter_make").value = $.trim(document.getElementById(t1id +(lowerLimit+42)).innerHTML);
            document.getElementById("control_mechanism_type").value = $.trim(document.getElementById(t1id +(lowerLimit+43)).innerHTML);
            document.getElementById("is_working").value = $.trim(document.getElementById(t1id +(lowerLimit+44)).innerHTML);
            document.getElementById("auto_switch_type").value = $.trim(document.getElementById(t1id +(lowerLimit+45)).innerHTML);
            document.getElementById("main_switch_type").value = $.trim(document.getElementById(t1id +(lowerLimit+46)).innerHTML);
            document.getElementById("main_switch_rating").value = $.trim(document.getElementById(t1id +(lowerLimit+47)).innerHTML);
            document.getElementById("enclosure_type").value = $.trim(document.getElementById(t1id +(lowerLimit+48)).innerHTML);
            document.getElementById("r_phase").value = $.trim(document.getElementById(t1id +(lowerLimit+49)).innerHTML);
            document.getElementById("b_phase").value = $.trim(document.getElementById(t1id +(lowerLimit+50)).innerHTML);
            document.getElementById("y_phase").value = $.trim(document.getElementById(t1id +(lowerLimit+51)).innerHTML);

            document.getElementById("meter_address").value = $.trim(document.getElementById(t1id +(lowerLimit+52)).innerHTML);
    


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
                 var url = "tubeWellDetailCont?"+queryString;
                 popupwin = openPopUp(url, "Switching Point Details", 500, 1000);

             }
             function openMapForCord() {
                              var url="tubeWellDetailCont?task=GetCordinates1";//"getCordinate";
                                  popupwin = openPopUp(url, "",  600, 630);
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

             window.onload = function () {
              jQuery(function(){
             ///alert("onload");
             var twd_pole_no=document.getElementById("twd_pole_no_s").value;
             //alert("twd_pole_no="+twd_pole_no);
             //alert(twd_pole_no.length);
             var tws_pole_no=document.getElementById("pole_no_s").value;
             //alert("tws_pole_no="+tws_pole_no);
              //alert(tws_pole_no.length);
             if(twd_pole_no.trim() == tws_pole_no.trim()){
                // alert("if");
                $("#pole_no_s").css('border', 'solid 2px green');
             }else{
                 //alert("else");
                  $("#pole_no_s").css('border', 'solid 2px red');
             }
             // alert("1");

             var twd_meter_no=document.getElementById("twd_meter_no_s").value;
             //alert("twd_meter_no="+twd_meter_no);
             var tws_meter_no=document.getElementById("meter_no_s").value;
             //alert("tws_meter_no="+tws_meter_no);
             if(twd_meter_no == tws_meter_no){
                   $("#meter_no_s").css('border', 'solid 2px green');
             }else{
                  $("#meter_no_s").css('border', 'solid 2px red');
             }
             //alert("2");

             var twd_b_phase=document.getElementById("twd_b_phase").value;
             var tws_b_phase=document.getElementById("b_phase").value;
             if(twd_b_phase == tws_b_phase){
                  $("#b_phase").css('border', 'solid 2px green');
             }else{
                  $("#b_phase").css('border', 'solid 2px red');
             }

             var twd_r_phase=document.getElementById("twd_r_phase").value;
             var tws_r_phase=document.getElementById("r_phase").value;
             if(twd_r_phase == tws_r_phase){
                  $("#r_phase").css('border', 'solid 2px green');
             }else{
                  $("#r_phase").css('border', 'solid 2px red');
             }
             //alert("3");

             var twd_y_phase=document.getElementById("twd_y_phase").value;
             var tws_y_phase=document.getElementById("y_phase").value;
             if(twd_y_phase == tws_y_phase){
                  $("#y_phase").css('border', 'solid 2px green');
             }else{
                  $("#y_phase").css('border', 'solid 2px red');
             }

             var twd_longitude=document.getElementById("twd_longitude").value;
             var tws_longitude=document.getElementById("longitude").value;
             if(twd_longitude == tws_longitude){
                  $("#longitude").css('border', 'solid 2px green');
             }else{
                  $("#longitude").css('border', 'solid 2px red');
             }
             //alert("4");

             var twd_latitude=document.getElementById("twd_latitude").value;
             var tws_latitude=document.getElementById("latitude").value;
             if(twd_latitude == tws_latitude){
                  $("#latitude").css('border', 'solid 2px green');
             }else{
                  $("#latitude").css('border', 'solid 2px red');
             }

             var twd_fuse_1=document.getElementById("twd_fuse_1").value;
             var tws_fuse_1=document.getElementById("fuse_1").value;
             if(twd_fuse_1 == tws_fuse_1){
                  $("#fuse_1").css('border', 'solid 2px green');
             }else{
                  $("#fuse_1").css('border', 'solid 2px red');
             }
             //alert("5");

             var twd_fuse_2=document.getElementById("twd_fuse_2").value;
             var tws_fuse_2=document.getElementById("fuse_2").value;
             if(twd_fuse_2 == tws_fuse_2){
                  $("#fuse_2").css('border', 'solid 2px green');
             }else{
                  $("#fuse_2").css('border', 'solid 2px red');
             }

             var twd_fuse_3=document.getElementById("twd_fuse_3").value;
             var tws_fuse_3=document.getElementById("fuse_3").value;
             if(twd_fuse_3 == tws_fuse_3){
                  $("#fuse_3").css('border', 'solid 2px green');
             }else{
                  $("#fuse_3").css('border', 'solid 2px red');
             }
             //alert("6");

             var twd_mccb_1=document.getElementById("twd_mccb_1").value;
             var tws_mccb_1=document.getElementById("mccb_1").value;
             if(twd_mccb_1 == tws_mccb_1){
                  $("#mccb_1").css('border', 'solid 2px green');
             }else{
                  $("#mccb_1").css('border', 'solid 2px red');
             }

             var twd_mccb_2=document.getElementById("twd_mccb_2").value;
             var tws_mccb_2=document.getElementById("mccb_2").value;
             if(twd_mccb_2 == tws_mccb_2){
                  $("#mccb_2").css('border', 'solid 2px green');
             }else{
                  $("#mccb_2").css('border', 'solid 2px red');
             }
             //alert("7");

             var twd_mccb_3=document.getElementById("twd_mccb_3").value;
             var tws_mccb_3=document.getElementById("mccb_3").value;
             if(twd_mccb_3 == tws_mccb_3){
                  $("#mccb_3").css('border', 'solid 2px green');
             }else{
                  $("#mccb_3").css('border', 'solid 2px red');
             }

             var twd_starter_capacity=document.getElementById("twd_starter_capacity").value;
             var tws_starter_capacity=document.getElementById("starter_capacity").value;
             if(twd_starter_capacity == tws_starter_capacity){
                  $("#starter_capacity").css('border', 'solid 2px green');
             }else{
                  $("#starter_capacity").css('border', 'solid 2px red');
             }
             //alert("8");

             var twd_starter_type=document.getElementById("twd_starter_type").value;
             var tws_starter_type=document.getElementById("starter_type").value;
             if(twd_starter_type == tws_starter_type){
                  $("#starter_type").css('border', 'solid 2px green');
             }else{
                  $("#starter_type").css('border', 'solid 2px red');
             }

             var twd_starter_make=document.getElementById("twd_starter_make").value;
             var tws_starter_make=document.getElementById("starter_make").value;
             if(twd_starter_make == tws_starter_make){
                  $("#starter_make").css('border', 'solid 2px green');
             }else{
                  $("#starter_make").css('border', 'solid 2px red');
             }
             //alert("9");

             var twd_auto_switch_type=document.getElementById("twd_auto_switch_type").value;
             var tws_auto_switch_type=document.getElementById("auto_switch_type").value;
             if(twd_auto_switch_type == tws_auto_switch_type){
                  $("#auto_switch_type").css('border', 'solid 2px green');
             }else{
                  $("#auto_switch_type").css('border', 'solid 2px red');
             }

             var twd_main_switch_type=document.getElementById("twd_main_switch_type").value;
             var tws_main_switch_type=document.getElementById("main_switch_type").value;
             if(twd_main_switch_type == tws_main_switch_type){
                  $("#main_switch_type").css('border', 'solid 2px green');
             }else{
                  $("#main_switch_type").css('border', 'solid 2px red');
             }
             //alert("10");

             var twd_main_switch_rating=document.getElementById("twd_main_switch_rating").value;
             var tws_main_switch_rating=document.getElementById("main_switch_rating").value;
             if(twd_main_switch_rating == tws_main_switch_rating){
                  $("#main_switch_rating").css('border', 'solid 2px green');
             }else{
                  $("#main_switch_rating").css('border', 'solid 2px red');
             }

             var twd_enclosure_type=document.getElementById("twd_enclosure_type").value;
             var tws_enclosure_type=document.getElementById("enclosure_type").value;
             if(twd_enclosure_type == tws_enclosure_type){
                  $("#enclosure_type").css('border', 'solid 2px green');
             }else{
                  $("#enclosure_type").css('border', 'solid 2px red');
             }
             //alert("11");

             var twd_phase=document.getElementById("twd_phase").value;
             var tws_phase=document.getElementById("phase").value;
             if(twd_phase == tws_phase){
                  $("#phase").css('border', 'solid 2px green');
             }else{
                  $("#phase").css('border', 'solid 2px red');
             }

             var twd_fuse_type1=document.getElementById("twd_fuse_type1").value;
             var tws_fuse_type1=document.getElementById("fuse_type1").value;
             if(twd_fuse_type1 == tws_fuse_type1){
                  $("#fuse_type1").css('border', 'solid 2px green');
             }else{
                  $("#fuse_type1").css('border', 'solid 2px red');
             }
             //alert("12");

             var twd_fuse_type2=document.getElementById("twd_fuse_type2").value;
             var tws_fuse_type2=document.getElementById("fuse_type2").value;
             if(twd_fuse_type2 == tws_fuse_type2){
                  $("#fuse_type2").css('border', 'solid 2px green');
             }else{
                  $("#fuse_type2").css('border', 'solid 2px red');
             }

             var twd_fuse_type3=document.getElementById("twd_fuse_type3").value;
             var tws_fuse_type3=document.getElementById("fuse_type3").value;
             if(twd_fuse_type3 == tws_fuse_type3){
                  $("#fuse_type3").css('border', 'solid 2px green');
             }else{
                  $("#fuse_type3").css('border', 'solid 2px red');
             }
             //alert("13");

             var twd_mccb_type1=document.getElementById("twd_mccb_type1").value;
             var tws_mccb_type1=document.getElementById("mccb_type1").value;
             if(twd_mccb_type1 == tws_mccb_type1){
                  $("#mccb_type1").css('border', 'solid 2px green');
             }else{
                  $("#mccb_type1").css('border', 'solid 2px red');
             }

             var twd_mccb_type2=document.getElementById("twd_mccb_type2").value;
             var tws_mccb_type2=document.getElementById("mccb_type2").value;
             if(twd_mccb_type2 == tws_mccb_type2){
                  $("#mccb_type2").css('border', 'solid 2px green');
             }else{
                  $("#mccb_type2").css('border', 'solid 2px red');
             }
             //alert("14");

             var twd_mccb_type3=document.getElementById("twd_mccb_type3").value;
             var tws_mccb_type3=document.getElementById("mccb_type3").value;
             if(twd_mccb_type3 == tws_mccb_type3){
                  $("#mccb_type3").css('border', 'solid 2px green');
             }else{
                  $("#mccb_type3").css('border', 'solid 2px red');
             }

                var twd_zone=document.getElementById("twd_zone").value;
               // alert(twd_zone);
             var tws_zone=document.getElementById("zone").value;
              //alert(tws_zone);
             if(twd_zone == tws_zone){
                  $("#zone").css('border', 'solid 2px green');
             }else{
                  $("#zone").css('border', 'solid 2px red');
             }
            // alert("15");

             var twd_ward_no=document.getElementById("twd_ward_no").value;
             var tws_ward_no=document.getElementById("ward_no").value;
             if(twd_ward_no == tws_ward_no){
                  $("#ward_no").css('border', 'solid 2px green');
             }else{
                  $("#ward_no").css('border', 'solid 2px red');
             }

             var twd_area_name=document.getElementById("twd_area_name").value;
             var tws_area_name=document.getElementById("area_name").value;
             if(twd_area_name == tws_area_name){
                  $("#area_name").css('border', 'solid 2px green');
             }else{
                  $("#area_name").css('border', 'solid 2px red');
             }

             var twd_meter_status=document.getElementById("twd_meter_status").value;
             var tws_meter_status=document.getElementById("meter_status").value;
             if(twd_meter_status == tws_meter_status){
                  $("#meter_status").css('border', 'solid 2px green');
             }else{
                  $("#meter_status").css('border', 'solid 2px red');
             }
             //alert("16");

             var twd_meter_address=document.getElementById("twd_meter_address").value;
             var tws_meter_address=document.getElementById("meter_address").value;
             if(twd_meter_address == tws_meter_address){
                  $("#meter_address").css('border', 'solid 2px green');
             }else{
                  $("#meter_address").css('border', 'solid 2px red');
             }




              });
             }



</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>TubeWell Detail Page</title>
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
                                        <td align="center" class="header_table" width="100%">TUBEWELL DETAIL</td>
                                        <td><%@include file="/layout/org_menu.jsp" %> </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td> <div align="center">
                                    <form name="form0" method="POST" action="tubeWellDetailCont">
                                        <table align="center" class="heading" width="590">
                                            <tr>
                                                <td align="center">Zone<input class="input" type="text" id="zone_search" name="zone_search" value="${zone_search}" size="20" ></td>
                                                <td align="center">Ivrs No.<input class="input" type="text" id="service_conn_no_Search" name="service_conn_no_Search" value="${service_conn_no_Search}" size="20" ></td>
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
                                <form name="form1" method="POST" action="tubeWellDetailCont">
                                    <DIV id="content_div" class="content_div">
                                        <table id="table1" width="600"  border="1"  align="center" class="content">
                                            <tr>
                                                <th style="white-space: normal" class="heading">S.No.</th>
                                                <th style="white-space: normal" class="heading">Pole No*</th>
                                                <th style="white-space: normal" class="heading">TubeWell Name*</th>
                                                <th style="white-space: normal" class="heading">Tube Well GPS code</th>
                                                <th style="white-space: normal" class="heading">Service Conn. No.</th>
                                                <th style="white-space: normal" class="heading">IVRS No. </th>
                                                <th style="white-space: normal" class="heading">No. of Poles*</th>

                                                <th style="white-space: normal" class="heading">Meter No</th>
                                                <th style="white-space: normal" class="heading">Division</th>
                                                <th style="white-space: normal" class="heading">Phase</th>
                                                <th style="white-space: normal" class="heading">Feeder_Zone</th>
                                                <th style="white-space: normal" class="heading">Feeder</th>
                                                <th style="white-space: normal" class="heading">Zone</th>
                                                <th style="white-space: normal" class="heading">Ward No.</th>
                                                <th style="white-space: normal" class="heading">Area Name</th>
                                                <th style="white-space: normal" class="heading">Road Catgry.</th>
                                                <th style="white-space: normal" class="heading">Road Use</th>
                                                <th style="white-space: normal" class="heading">Road Name</th>
                                                <th style="white-space: normal" class="heading">Traffic Type</th>
                                                <th style="white-space: normal" class="heading">Longitude</th>
                                                <th style="white-space: normal" class="heading">Latitude</th>


                                                <th style="white-space: normal" class="heading">Fuse</th>
                                                <th style="white-space: normal" class="heading">Fuse Quantity</th>
                                                <th style="white-space: normal" class="heading">Fuse Type1</th>
                                                <th style="white-space: normal" class="heading">Fuse Type2</th>
                                                <th style="white-space: normal" class="heading">Fuse Type3</th>
                                                <th style="white-space: normal" class="heading">Fuse1</th>
                                                <th style="white-space: normal" class="heading">Fuse2</th>
                                                <th style="white-space: normal" class="heading">Fuse3</th>

                                                <%--<th style="white-space: normal" class="heading">Starter Quantity</th>--%>
                                                <th style="white-space: normal" class="heading">Mccb</th>
                                                <th style="white-space: normal" class="heading">Mccb Quantity</th>
                                                <th style="white-space: normal" class="heading">Mccb Type1</th>
                                                <th style="white-space: normal" class="heading">Mccb Type2</th>
                                                <th style="white-space: normal" class="heading">Mccb Type3</th>
                                                <th style="white-space: normal" class="heading">Mccb1</th>
                                                <th style="white-space: normal" class="heading">Mccb2</th>
                                                <th style="white-space: normal" class="heading">Mccb3</th>
                                                <th style="white-space: normal" class="heading">Starter</th>
                                                <th style="white-space: normal" class="heading">Starter Type</th>
                                                <th style="white-space: normal" class="heading">Starter Capacity</th>

                                                <th style="white-space: normal" class="heading">Starter MAke</th>
                                                <%-- <th style="white-space: normal" class="heading">Timer</th>--%>
                                                <th style="white-space: normal" class="heading">Control Mechanism Type*</th>
                                                <th style="white-space: normal" class="heading">Is Working</th>
                                                <th style="white-space: normal" class="heading">Auto Switch Type</th>
                                                <th style="white-space: normal" class="heading">Main Switch Type</th>
                                                <th style="white-space: normal" class="heading">Main Switch Rating</th>
                                                <th style="white-space: normal" class="heading">Enclosure Type</th>
                                                <th style="white-space: normal" class="heading">R Phase</th>
                                                <th style="white-space: normal" class="heading">B Phase</th>
                                                <th style="white-space: normal" class="heading">Y Phase</th>
                                                <th style="white-space: normal" class="heading">Meter Address</th>
                                                <!--                                                <th style="white-space: normal; display:none" class="heading">Long-Lati</th>
                                                                                                <th style="white-space: normal" class="heading">View Map</th>
                                                                                                <th style="white-space: normal" class="heading">Measured Load KW</th>

                                                                                                <th style="white-space: normal" class="heading">Is On Pole</th>-->

                                                <%--<th style="white-space: normal" class="heading">Controller Model</th>
                                                <th style="white-space: normal" class="heading">Mobile No</th>
                                                <th style="white-space: normal" class="heading">Sim No</th>
                                                <th style="white-space: normal" class="heading">Imei No</th>
                                                <th style="white-space: normal" class="heading">Panel File No</th>
                                                <th style="white-space: normal" class="heading">Panel Transferred Status</th>--%>

                                                <!--         <th style="white-space: normal" class="heading">Survey Rev No</th>  X    -->
                                            </tr>
                                            <!---below is the code to show all values on jsp page fetched from trafficTypeList of TrafficController     --->
                                            <c:forEach var="tubeWellTypeBean" items="${requestScope['surveyTypeList']}"  varStatus="loopCounter">
                                                <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                    <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${tubeWellTypeBean.tube_well_detail_id}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                    <td style="display:none" id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.tube_well_rev_no}</td>
                                                    <%--  <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">
                                                          <input type="hidden" id="status_type_id${loopCounter.count}" value="${statusTypeBean.status_type_id}">${lowerLimit - noOfRowsTraversed + loopCounter.count}
                                                          <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                      </td> --%>
                                                    <%--   <td id="t1c${IDGenerator.uniqueID}"  style="display:none" onclick="fillColumns(id)">${tubeWellTypeBean.tube_well_detail_id}
                                                          <input type="text" name="tube_well_detail_id" id="tube_well_detail_id${loopCounter.count}" value=${tubeWellTypeBean.tube_well_detail_id} />
                                                          <input type="text" name="tube_well_rev_no" id="tube_well_rev_no${loopCounter.count}" value=${tubeWellTypeBean.survey_rev_no} />
                                                    <%--        <input type="text" name="pole_id" id="pole_id${loopCounter.count}" value=${tubeWellTypeBean.pole_id} />
                                                            <input type="text" name="pole_rev_no" id="pole_rev_no${loopCounter.count}" value=${tubeWellTypeBean.pole_rev_no} />
                                                    <input type="text" name="map_id" id="map_id${loopCounter.count}" value=${tubeWellTypeBean.map_id} />
                                                </td>
                                                <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.pole_no_s}</td>
                                                    <%--         <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.pole_no}</td>   --%>

                                                    <%--  <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.pole_no}

                                                        <input style="display:none" id="pole_no_ck_bx${loopCounter.count}" class="checkbox2" name="pole_no_ck_bx" type="checkbox" onclick="poleSingleCheckUncheck(${loopCounter.count})"/>

                                                        <input type="text"style="display:none" class="pole_no_edit" id="pole_no_edit${loopCounter.count}" name="pole_no_edit" value="${tubeWellTypeBean.pole_no}" readonly/>

                                                    </td> --%>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.pole_no_s}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.tube_well_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.gps_code_s}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.service_conn_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.ivrs_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.no_of_poles}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.meter_no_s}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.division}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.phase}</td>

                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.feeder_zone}</td>
                                                    
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.feeder}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.zone}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.ward_no}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.area_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.road_category}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.road_use}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.road_name}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.traffic_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.longitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.lattitude}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse_quantity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse_type1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse_type2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse_type3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.fuse3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb_quantity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb_type1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb_type2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb_type3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb1}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb2}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.mccb3}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.starter}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.starter_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.starter_capacity}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.starter_make}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.control_mechanism_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.is_working}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.auto_switch_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.main_switch_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.main_switch_reading}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.enclosure_type}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.r_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.b_phase}</td>
                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.y_phase}</td>

                                                    <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${tubeWellTypeBean.meter_address}</td>
                                                   

                                                   
                                                </tr>
                                            </c:forEach>
                                            <tr>
                                                <td align='right' colspan="8">
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

                        <tr>
                            <td align="center">
                                <div>
                                    <form name="form2" method="POST" action="tubeWellDetailCont" onsubmit="return verify()">
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="8" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>

                                            <tr>
                                                <th class="heading">Pole No.</th>
                                                <td>
                                                    <input class="input" type="hidden" id="tube_well_detail_id" name="tube_well_detail_id" value="${tubeWellTypeBean.tube_well_detail_id eq '' ? '' : tubeWellTypeBean.tube_well_detail_id}" >
                                                    <input class="input" type="hidden" id="tube_well_rev_no" name="tube_well_rev_no" value="${tubeWellTypeBean.tube_well_rev_no eq '' ? '' : tubeWellTypeBean.tube_well_rev_no}" >

                                                    <input class="input" type="hidden" id="twd_pole_no_s" name="twd_pole_no_s" value="${tubeWellTypeBean.pole_no_s eq '' ? '' : tubeWellTypeBean.pole_no_s}" size="20">
                                                    <input class="input" type="text" id="pole_no_s" name="pole_no_s" title="${tubeWellTypeBean.pole_no_s}" value="${tubeWellSurveyData.pole_no eq '' ? '' : tubeWellSurveyData.pole_no}" size="20" disabled>
                                                </td>
                                                <th class="heading">TubeWell Name</th>
                                                <td>
                                                    <input class="input" type="text" id="tube_well_name" name="tube_well_name" value="${tubeWellTypeBean.tube_well_name eq '' ? '' : tubeWellTypeBean.tube_well_name}" size="20" disabled>
                                                </td>
                                                <th class="heading">TubeWell GPS Code</th>
                                                <td>
<!--                                                    <input class="input" type="text" id="gps_code_s" name="gps_code_s" value="${tubeWellTypeBean.gps_code_s eq '' ? '' : tubeWellTypeBean.gps_code_s}" size="20" disabled>-->
                                                    <input class="input" type="text" id="gps_code_s" name="gps_code_s" value="" size="20" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading">Service Conn. No.</th>
                                                <td>
                                                    <input class="input" type="text" id="service_conn_no" name="service_conn_no" value="${tubeWellTypeBean.service_conn_no eq '' ? '' : tubeWellTypeBean.service_conn_no}" size="20" disabled>
                                                </td>
                                                <th class="heading">IVRS No.</th>
                                                <td>
                                                    <input class="input" type="text" id="ivrs_no" name="ivrs_no" value="${tubeWellTypeBean.ivrs_no eq '' ? '' : tubeWellTypeBean.ivrs_no}" size="20" disabled>
                                                </td>
                                                <th class="heading">No. of Users Connected</th>
                                                <td>
                                                    <input class="input" type="text" id="no_of_poles" name="no_of_poles" value="${tubeWellTypeBean.no_of_poles eq '' ? '' : tubeWellTypeBean.no_of_poles}" size="20" disabled>
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
                                                    <input class="input" type="hidden" id="twd_meter_no_s" name="twd_meter_no_s"  value="${tubeWellTypeBean.meter_no_s eq '' ? '' : tubeWellTypeBean.meter_no_s}" size="20">
                                                    <input class="input" type="text" id="meter_no_s" name="meter_no_s" title="${tubeWellTypeBean.meter_no_s}" value="${tubeWellSurveyData.meter_no eq '' ? '' : tubeWellSurveyData.meter_no}" size="20" disabled>
                                                </td>
                                                <th class="heading">Phase</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_phase" name="twd_phase" value="${tubeWellTypeBean.phase eq '' ? '' : tubeWellTypeBean.phase}" size="20" maxlength="1" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)" >
                                                    <input class="input" type="text" id="phase" name="phase" title="${tubeWellTypeBean.phase}" value="${tubeWellSurveyData.no_of_phase eq '' ? '' : tubeWellSurveyData.no_of_phase}" size="20" maxlength="1" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)" disabled>
                                                </td>
                                                <th class="heading">Meter Status</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_meter_status" name="twd_meter_status" value="${tubeWellTypeBean.meter_status eq '' ? '' : tubeWellTypeBean.meter_status}" size="20" maxlength="1" onchange="IsNumeric(id)" onkeyup="IsNumeric(id)" >
                                                    <input class="input" type="text" id="meter_status" name="meter_status" title="${tubeWellTypeBean.meter_status}" value="${tubeWellSurveyData.meter_status eq '' ? '' : tubeWellSurveyData.meter_status}" size="20" maxlength="1"  disabled>
                                                </td>

                                            </tr>
                                            <tr>
                                                <th class="heading">B phase</th>
                                                <td><input class="input" type="hidden" id="twd_b_phase" name="twd_b_phase" value="${tubeWellTypeBean.b_phase eq '' ? '' : tubeWellTypeBean.b_phase}" size="20">
                                                    <input class="input" type="text" id="b_phase" name="b_phase" title="${tubeWellTypeBean.b_phase}" value="${tubeWellSurveyData.b_phase eq '' ? '' : tubeWellSurveyData.b_phase}" size="20"onkeypress="return isNumberKey(event)" required disabled></td>

                                                <th class="heading">R Phase</th>
                                                <td><input class="input" type="hidden" id="twd_r_phase" name="twd_r_phase" value="${tubeWellTypeBean.r_phase eq '' ? '' : tubeWellTypeBean.r_phase}" size="20">
                                                    <input class="input" type="text" id="r_phase" name="r_phase" title="${tubeWellTypeBean.r_phase}" value="${tubeWellSurveyData.r_phase eq '' ? '' : tubeWellSurveyData.r_phase}" size="20" onkeypress="return isNumberKey(event)"disabled></td>

                                                <th class="heading">Y Phase</th>
                                                <td><input class="input" type="hidden" id="twd_y_phase" name="twd_y_phase" value="${tubeWellTypeBean.y_phase eq '' ? '' : tubeWellTypeBean.y_phase}" size="20">
                                                    <input class="input" type="text" id="y_phase" name="y_phase" title="${tubeWellTypeBean.y_phase}" value="${tubeWellSurveyData.y_phase eq '' ? '' : tubeWellSurveyData.y_phase}" size="20"onkeypress="return isNumberKey(event)" disabled></td>


                                            </tr>
                                            <tr>
                                                <th class="heading">Division</th>
                                                <td>
                                                    <input class="input" type="text" id="division" name="division" value="${tubeWellTypeBean.division eq '' ? '' : tubeWellTypeBean.division}" size="20" disabled>
                                                </td>
                                                <th class="heading">Feeder_Zone</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_feeder_zone" name="twd_feeder_zone" value="${tubeWellTypeBean.zone eq '' ? '' : tubeWellTypeBean.zone}" size="20" disabled>
                                                    <input class="input" type="text" id="feeder_zone" name="feeder_zone"  value="${tubeWellTypeBean.feeder_zone eq '' ? '' : tubeWellTypeBean.feeder_zone}" size="20" disabled>
                                                </td>
                                                <th class="heading">Feeder</th>
                                                <td>
                                                    <input class="input" type="text" id="feeder" name="feeder" value="${tubeWellTypeBean.feeder eq '' ? '' : tubeWellTypeBean.feeder}" size="20" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading">Zone</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_zone" name="twd_zone" value="${tubeWellTypeBean.zone eq '' ? '' : tubeWellTypeBean.zone}" size="20" disabled>
                                                    <input class="input" type="text" id="zone" name="zone" title="${tubeWellTypeBean.zone}" value="${tubeWellSurveyData.zone_m eq '' ? '' : tubeWellSurveyData.zone_m}" size="20" disabled>
                                                </td>
<!--                                                <th class="heading">City</th>
                                                <td><input class="input" type="text" id="city" name="city" value="${tubeWellTypeBean.city eq '' ? '' : tubeWellTypeBean.city}" size="20" disabled>
                                                </td>-->
                                                <th class="heading">Ward No</th>
                                                <td><input class="input" type="hidden" id="twd_ward_no" name="twd_ward_no" value="${tubeWellTypeBean.ward_no eq '' ? '' : tubeWellTypeBean.ward_no}" size="20" disabled>
                                                    <input class="input" type="text" id="ward_no" name="ward_no" title="${tubeWellTypeBean.ward_no}" value="${tubeWellSurveyData.ward_no_m eq '' ? '' : tubeWellSurveyData.ward_no_m}" size="20"onkeypress="return isNumberKey(event)" disabled>
                                                </td>
                                                <th class="heading">Area Name</th>
                                                <td><input class="input" type="hidden" id="twd_area_name" name="twd_area_name" value="${tubeWellTypeBean.area_name eq '' ? '' : tubeWellTypeBean.area_name}" size="20" disabled>
                                                    <input class="input" type="text" id="area_name" name="area_name" title="${tubeWellTypeBean.area_name}" value="${tubeWellSurveyData.area_name eq '' ? '' : tubeWellSurveyData.area_name}" size="20"onkeypress="return isNumberKey(event)" disabled>
                                                </td>
                                            </tr>
                                            <tr>
                                                <th class="heading">Road Category</th>
                                                <td>
                                                    <input class="input" type="text" id="road_category" name="road_category" value="${tubeWellTypeBean.road_category eq '' ? '' : tubeWellTypeBean.road_category}" size="20" disabled>
                                                </td>
                                                <th class="heading">Road Use</th>
                                                <td><input class="input" type="text" id="road_use" name="road_use" value="${tubeWellTypeBean.road_use eq '' ? '' : tubeWellTypeBean.road_use}" size="20" disabled>
                                                </td>
                                                <th class="heading">Road Name</th>
                                                <td><input class="input" type="text" id="road_name" name="road_name" value="${tubeWellTypeBean.road_name eq '' ? '' : tubeWellTypeBean.road_name}" size="20" disabled></td>
                                            </tr>
                                            <tr>
                                                <th class="heading">Traffic Type</th>
                                                <td>
                                                    <input class="input" type="text" id="traffic_type" name="traffic_type" value="${tubeWellTypeBean.traffic_type eq '' ? '' : tubeWellTypeBean.traffic_type}" size="20" disabled>
                                                </td>
                                                <th class="heading">Longitude</th>
                                                <td><input class="input" type="hidden" id="twd_longitude" name="twd_longitude" value="${tubeWellTypeBean.longitude eq '' ? '' : tubeWellTypeBean.longitude}" size="20">
                                                    <input class="input" type="text" id="longitude" name="longitude" title="${tubeWellTypeBean.longitude}" value="${tubeWellSurveyData.longitude eq '' ? '' : tubeWellSurveyData.longitude}" size="20"  required disabled></td>
                                                <th class="heading">Latitude</th>
                                                <td><input class="input" type="hidden" id="twd_latitude" name="twd_latitude" value="${tubeWellTypeBean.lattitude eq '' ? '' : tubeWellTypeBean.lattitude}" size="20" >
                                                    <input class="input" type="text" id="latitude" name="latitude" title="${tubeWellTypeBean.lattitude}" value="${tubeWellSurveyData.latitude eq '' ? '' : tubeWellSurveyData.latitude}" size="20" required disabled><input class="button" type="button" id="get_cordinate" value="Get Cordinate" onclick="openMapForCord()">
                                                </td>
                                            </tr>

                                            <tr>
                                                <th class="heading">Fuse</th>

                                                <td>
                                                    <input type="radio" id="fuseYes" name="fuseY" value="Y" onclick="uncheckAnother(id)" checked>Yes
                                                    <input type="radio" id="fuseNo" name="fuseN" value="N" onclick="uncheckAnother(id)" >No
                                                </td>
                                                <!--  <input class="input" type="text" id="fuse" name="fuse" value="" size="10" disabled></td>-->

                                                <th class="heading">Fuse Quantity</th>
                                                <td>
                                                    <input class="input" type="text" id="fuse_quantity" name="fuse_quantity" value="0" size="20" disabled>
                                                </td>

                                                <th class="heading">Meter Address</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_meter_address" name="twd_meter_address" value="${tubeWellTypeBean.meter_address eq '' ? '' : tubeWellTypeBean.meter_address}" size="20">
                                                    <input class="input" type="text" id="meter_address" name="meter_address" title="${tubeWellTypeBean.meter_address}" value="${tubeWellSurveyData.meter_address eq '' ? '' : tubeWellSurveyData.meter_address}" size="20" disabled>
                                                </td>

                                            </tr>
                                            <tr>
                                                <th class="heading">Fuse Type1</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_fuse_type1" name="twd_fuse_type1" value="${tubeWellTypeBean.fuse_type1 eq '' ? '' : tubeWellTypeBean.fuse_type1}" size="20">
                                                    <input class="input" type="text" id="fuse_type1" name="fuse_type1" title="${tubeWellTypeBean.fuse_type1}" value="${tubeWellSurveyData.fuse_type1 eq '' ? '' : tubeWellSurveyData.fuse_type1}" size="20" disabled>
                                                </td>
                                                <th class="heading">Fuse 1</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_fuse_1" name="twd_fuse_1" value="${tubeWellTypeBean.fuse1 eq '' ? '' : tubeWellTypeBean.fuse1}" size="20">
                                                    <input class="input" type="text" id="fuse_1" name="fuse_1" title="${tubeWellTypeBean.fuse1}" value="${tubeWellSurveyData.fuse1 eq '' ? '' : tubeWellSurveyData.fuse1}" size="20" disabled>
                                                </td>
                                                <th class="heading">Fuse Type2</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_fuse_type2" name="twd_fuse_type2" value="${tubeWellTypeBean.fuse_type2 eq '' ? '' : tubeWellTypeBean.fuse_type2}" size="20" >
                                                    <input class="input" type="text" id="fuse_type2" name="fuse_type2" title="${tubeWellTypeBean.fuse_type2}" value="${tubeWellSurveyData.fuse_type2 eq '' ? '' : tubeWellSurveyData.fuse_type2}" size="20" disabled>
                                                </td>
                                                <!--  <input class="input" type="text" id="fuse" name="fuse" value="" size="10" disabled></td>-->



                                            </tr>
                                            <tr>
                                                <th class="heading">Fuse 2</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_fuse_2" name="twd_fuse_2" value="${tubeWellTypeBean.fuse2 eq '' ? '' : tubeWellTypeBean.fuse2}" size="20">
                                                    <input class="input" type="text" id="fuse_2" name="fuse_2" title="${tubeWellTypeBean.fuse2}" value="${tubeWellSurveyBean.fuse2 eq '' ? '' : tubeWellSurveyBean.fuse2}" size="20" disabled>
                                                </td>
                                                <th class="heading">Fuse Type3</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_fuse_type3" name="twd_fuse_type3" value="${tubeWellTypeBean.fuse_type3 eq '' ? '' : tubeWellTypeBean.fuse_type3}" size="20" >
                                                    <input class="input" type="text" id="fuse_type3" name="fuse_type3" title="${tubeWellTypeBean.fuse_type3}" value="${tubeWellSurveyData.fuse_type3 eq '' ? '' : tubeWellSurveyData.fuse_type3}" size="20" disabled>
                                                </td>
                                                <th class="heading">Fuse 3</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_fuse_3" name="twd_fuse_3" value="${tubeWellTypeBean.fuse3 eq '' ? '' : tubeWellTypeBean.fuse3}" size="20">
                                                    <input class="input" type="text" id="fuse_3" name="fuse_3" title="${tubeWellTypeBean.fuse3}" value="${tubeWellSurveyData.fuse3 eq '' ? '' : tubeWellSurveyData.fuse3}" size="20" disabled>
                                                </td>

                                            </tr>


                                            <tr>
                                                <th class="heading">Mccb</th>
                                                <td>
                                                    <input type="radio" id="mccbYes" name="mccbY" value="Y" onclick="uncheckAnother(id)"checked>Yes
                                                    <input type="radio" id="mccbNo" name="mccbN" value="N" onclick="uncheckAnother(id)">No
                                                </td>

                                                <th class="heading">Mccb Quantity</th>
                                                <td>
                                                    <input class="input" type="text" id="mccb_quantity" name="mccb_quantity" value="0" size="20" disabled>
                                                </td>

                                            </tr>
                                            <tr>
                                                <th class="heading">Mccb Type1</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_mccb_type1" name="twd_mccb_type1" value="${tubeWellTypeBean.mccb_type1 eq '' ? '' : tubeWellTypeBean.mccb_type1}" size="20" >
                                                    <input class="input" type="text" id="mccb_type1" title="${tubeWellTypeBean.mccb_type1}" name="mccb_type1" value="${tubeWellSurveyData.mccb_type1 eq '' ? '' : tubeWellSurveyData.mccb_type1}" size="20" disabled>
                                                </td>
                                                <th class="heading">Mccb 1</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_mccb_1" name="twd_mccb_1" value="${tubeWellTypeBean.mccb1 eq '' ? '' : tubeWellTypeBean.mccb1}" size="20">
                                                    <input class="input" type="text" id="mccb_1" name="mccb_1" title="${tubeWellTypeBean.mccb1}" value="${tubeWellSurveyData.mccb1 eq '' ? '' : tubeWellSurveyData.mccb1}" size="20" disabled>
                                                </td>
                                                <th class="heading">Mccb Type2</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_mccb_type2" name="twd_mccb_type2" value="${tubeWellTypeBean.mccb_type2 eq '' ? '' : tubeWellTypeBean.mccb_type2}" size="20" >
                                                    <input class="input" type="text" id="mccb_type2" title="${tubeWellTypeBean.mccb_type2}" name="mccb_type2" value="${tubeWellSurveyData.mccb_type2 eq '' ? '' : tubeWellSurveyData.mccb_type2}" size="20" disabled>
                                                </td>
                                                <!--  <input class="input" type="text" id="fuse" name="fuse" value="" size="10" disabled></td>-->


                                            </tr>
                                            <tr>


                                                <th class="heading">Mccb 2</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_mccb_2" name="twd_mccb_2" value="${tubeWellTypeBean.mccb2 eq '' ? '' : tubeWellTypeBean.mccb2}" size="20" >
                                                    <input class="input" type="text" id="mccb_2" name="mccb_2" title="${tubeWellTypeBean.mccb2}" value="${tubeWellSurveyData.mccb2 eq '' ? '' : tubeWellSurveyData.mccb2}" size="20" disabled>
                                                </td>
                                                <th class="heading">Mccb Type3</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_mccb_type3" name="twd_mccb_type3" value="${tubeWellTypeBean.mccb_type3 eq '' ? '' : tubeWellTypeBean.mccb_type3}" size="20" >
                                                    <input class="input" type="text" id="mccb_type3" title="${tubeWellTypeBean.mccb_type3}" name="mccb_type3" value="${tubeWellSurveyData.mccb_type3 eq '' ? '' : tubeWellSurveyData.mccb_type3}" size="20" disabled>
                                                </td>
                                                <th class="heading">Mccb 3</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_mccb_3" name="twd_mccb_3" value="${tubeWellTypeBean.mccb3 eq '' ? '' : tubeWellTypeBean.mccb3}" size="20">
                                                    <input class="input" type="text" id="mccb_3" name="mccb_3" title="${tubeWellTypeBean.mccb3}" value="${tubeWellSurveyData.mccb3 eq '' ? '' : tubeWellSurveyData.mccb3}" size="20" disabled>
                                                </td>

                                            </tr>

                                            <tr>

                                                <th class="heading">Starter</th>
                                                <td>
                                                    <input type="radio" id="StarterYes" name="StarterY" value="Y" onclick="uncheckAnother(id)"checked>Yes
                                                    <input type="radio" id="StarterNo" name="StarterN" value="N" onclick="uncheckAnother(id)">No
                                                </td>


                                            </tr>
                                            <tr>
                                                <th class="heading">Starter Capacity</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_starter_capacity" name="twd_starter_capacity" value="${tubeWellTypeBean.starter_capacity eq '' ? '' : tubeWellTypeBean.starter_capacity}" size="20" >
                                                    <input class="input" type="text" id="starter_capacity" name="starter_capacity" title="${tubeWellTypeBean.starter_capacity}" value="${tubeWellSurveyData.starter_capacity eq '' ? '' : tubeWellSurveyData.starter_capacity}" size="20" disabled>
                                                </td>
                                                <th class="heading">Starter Type</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_starter_type" name="twd_starter_type" value="${tubeWellTypeBean.starter_type eq '' ? '' : tubeWellTypeBean.starter_type}" size="20" >
                                                    <input class="input" type="text" id="starter_type" name="starter_type" title="${tubeWellTypeBean.starter_type}" value="${tubeWellSurveyData.starter_type eq '' ? '' : tubeWellSurveyData.starter_type}" size="20" disabled>
                                                </td>
                                                <th class="heading">Starter Make</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_starter_make" name="twd_starter_make" value="${tubeWellTypeBean.starter_make eq '' ? '' : tubeWellTypeBean.starter_make}" size="20">
                                                    <input class="input" type="text" id="starter_make" name="starter_make" title="${tubeWellTypeBean.starter_make}" value="${tubeWellSurveyData.starter_make eq '' ? '' : tubeWellSurveyData.starter_make}" size="20" disabled>
                                                </td>

                                            </tr>

                                            <tr>
                                                <th class="heading">Control Mechanism</th>
                                                <td>
                                                    <input class="input" type="text" id="control_mechanism_type" name="control_mechanism_type" value="${tubeWellTypeBean.control_mechanism_type eq '' ? '' : tubeWellTypeBean.control_mechanism_type}" size="20" disabled>
                                                </td>
                                                <th class="heading">Is Working</th>
                                                <td><input class="input" type="text" id="is_working" name="is_working" value="${tubeWellTypeBean.is_working eq '' ? '' : tubeWellTypeBean.is_working}" size="20" disabled></td>

                                            </tr>
                                            <tr>
                                                <th class="heading">Auto Switch Type</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_auto_switch_type" name="twd_auto_switch_type" value="${tubeWellTypeBean.auto_switch_type eq '' ? '' : tubeWellTypeBean.auto_switch_type}" size="20">
                                                    <input class="input" type="text" id="auto_switch_type" name="auto_switch_type" title="${tubeWellTypeBean.auto_switch_type}" value="${tubeWellSurveyData.auto_switch_type eq '' ? '' : tubeWellSurveyData.auto_switch_type}" size="20" disabled>
                                                </td>
                                                <th class="heading">Main Switch Type</th>
                                                <td><input class="input" type="hidden" id="twd_main_switch_type" name="twd_main_switch_type" value="${tubeWellTypeBean.main_switch_type eq '' ? '' : tubeWellTypeBean.main_switch_type}" size="20" disabled>
                                                    <input class="input" type="text" id="main_switch_type" name="main_switch_type" title="${tubeWellTypeBean.main_switch_type}" value="${tubeWellSurveyData.main_switch_type eq '' ? '' : tubeWellSurveyData.main_switch_type}" size="20" disabled></td>
                                                <th class="heading">Main Switch Reading</th>
                                                <td>
                                                    <input class="input" type="hidden" id="twd_main_switch_rating" name="twd_main_switch_rating" value="${tubeWellTypeBean.main_switch_reading eq '' ? '' : tubeWellTypeBean.main_switch_reading}" size="20" >
                                                    <input class="input" type="text" id="main_switch_rating" name="main_switch_rating" title="${tubeWellTypeBean.main_switch_reading}" value="${tubeWellSurveyData.main_switch_rating eq '' ? '' : tubeWellSurveyData.main_switch_rating}" size="20" disabled>
                                                </td>

                                            </tr>
                                            <tr>
                                                <th class="heading">Enclosure Type</th>
                                                <td><input class="input" type="hidden" id="twd_enclosure_type" name="twd_enclosure_type" value="${tubeWellTypeBean.enclosure_type eq '' ? '' : tubeWellTypeBean.enclosure_type}" size="20">
                                                    <input class="input" type="text" id="enclosure_type" name="enclosure_type" title="${tubeWellTypeBean.enclosure_type}" value="${tubeWellSurveyData.enclosure_type eq '' ? '' : tubeWellSurveyData.enclosure_type}" size="20" disabled></td>

                                                <th class="heading">Remark</th><td><input class="input" type="text" id="remark" name="remark" value="${tubeWellTypeBean.remark eq '' ? '' : tubeWellTypeBean.remark}" size="20" disabled></td>


                                            </tr>

                                            <tr>


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
                                                    <input class="button" type="submit" name="task" id="revise" value="Revise" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save" value="Save" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="save_As" value="Save AS New" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="reset" name="new" id="new" value="New" onclick="makeEditable(id)"> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="cancel" value="Cancel" onclick="setStatus(id)" disabled> &nbsp;&nbsp;
                                                    <input class="button" type="submit" name="task" id="update" value="Update" onclick="setStatus(id)" disabled>
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input  type="hidden" id="searchPoleNoSwitch" name="searchPoleNoSwitch" value="${searchPoleNoSwitch}" >
                                            <input  type="hidden" id="searchPoleType" name="searchPoleType" value="${searchPoleType}" >
                                            <input  type="hidden" id="searchPoleNo" name="searchPoleNo" value="${searchPoleNo}" >
                                            <input  type="hidden" id="searchAreaName" name="searchAreaName" value="${searchAreaName}" >
                                            <input  type="hidden" id="searchRoadName" name="searchRoadName" value="${searchRoadName}" >
                                            <input  type="hidden" id="addRowCount" name="addRowCount" value="0" >
                                            <input  type="hidden" id="isCheckedTrue" name="isCheckedTrue" value="Y" >

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
