<%-- 
    Document   : primary_survey
    Created on : Sep 11, 2020, 4:33:53 PM
    Author     : user
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

    jQuery(function () {

        $("#meter_name_search1").autocomplete("PoleSurveyCont", {
            extraParams: {
                action1: function () {
                    return "getmeter_name_search"
                }
            }
        });
        $("#irvs_no_search1").autocomplete("PoleSurveyCont", {
            extraParams: {
                action1: function () {
                    return "getirvs_search"
                }
            }
        });

        $("#circuit_search1").autocomplete("PoleSurveyCont", {
            extraParams: {
                action1: function () {
                    return "getcircuit_search"
                }

            }
        });
    });

// jQuery(function(){
//        $("#city_name").autocomplete("areaTypeCont", {
//            extraParams: {
//                action1: function() { return "searchCity"}
//            }
//        });
//
//        $("#ward_no").autocomplete("areaTypeCont", {
//            extraParams: {
//                action1: function() { return "searchWard"},
//                action2: function() { return  $("#searchCity").val();}
//            }
//        });
//
//  });
    function setDefaultColor(noOfRowsTraversed, noOfColumns) {
        for (var i = 0; i < noOfRowsTraversed; i++) {
            for (var j = 1; j <= noOfColumns; j++) {
                document.getElementById("t1c" + (i * noOfColumns + j)).bgColor = "";     // set the default color.
            }
        }
    }
    function makeEditable(id) {
    document.getElementById("feeder_name").disabled = false;
        document.getElementById("switching_point_name").disabled = false;

        document.getElementById("pole_type").disabled = false;
        document.getElementById("mounting_type").disabled = false;
        document.getElementById("pole_span").disabled = false;
        document.getElementById("pole_height").disabled = false;
        document.getElementById("mounting_height").disabled = false;
        document.getElementById("created_by").disabled = true;
        document.getElementById("created_date").disabled = true;
        document.getElementById("pole_no").disabled = false;
        document.getElementById("pole_no_client").disabled = false;
        document.getElementById("gps_code").disabled = false;
        document.getElementById("max_avg_lux_level").disabled = false;
        document.getElementById("min_avg_lux_level").disabled = false;
        document.getElementById("avg_lux_level").disabled = false;
        document.getElementById("standard_lux_level").disabled = false;
        // document.getElementById("source_wattage").disabled = false;
        document.getElementById("quantity").disabled = false;
        document.getElementById("is_working").disabled = false;
        document.getElementById("remark").disabled = false;
        document.getElementById("active").disabled = false;
        document.getElementById("pole_rev_no").disabled = false;

        document.getElementById("city").disabled = false;
        document.getElementById("ward_no").disabled = false;
        document.getElementById("road_use").disabled = false;
        document.getElementById("road_category").disabled = false;
        document.getElementById("area_name").disabled = false;
        document.getElementById("road_name").disabled = false;
        document.getElementById("traffic_type").disabled = false;
        //    document.getElementById("source_wattage").readOnly =false;

        // document.getElementById("addMore").disabled =true;
        document.getElementById("revise").disabled = false;
        document.getElementById("update").disabled = false;
        document.getElementById("delete").disabled = false;
        document.getElementById("save_As").disabled =false;
        document.getElementById("save").disabled = true;
        //

        if(id == 'new') {
            //  alert("gffg");
            // document.getElementById("created_date").disabled =false;
            //document.getElementById("message").innerHTML = "";      // Remove message
            $("#message").html("");
            //document.getElementById("source_wattage").readOnly =false;

            //  document.getElementById("addMore").disabled =false;
            document.getElementById("update").disabled = true;
            document.getElementById("revise").disabled = true;
            document.getElementById("delete").disabled = true;
            document.getElementById("save_As").disabled = true;
            document.getElementById("save").disabled = false;
            document.getElementById("is_switch").disabled =false;
            deleteRow();
            setDefaultColor(document.getElementById("noOfRowsTraversed").value, 32);
            //document.getElementById("pole_type").focus();
            document.getElementById("feeder_name").focus();
            document.getElementById("pole_id").value='';
            // document.getElementById("addRowCount").value = "0";
            document.getElementById("newRowCount").value = "0";
        }
    }
    function fillColumns(id) {
        var noOfRowsTraversed = document.getElementById("noOfRowsTraversed").value;
        //alert(noOfRowsTraversed);
        var noOfColumns = 5;
        var columnId = id;
    <%-- holds the id of the column being clicked, excluding the prefix t1c e.g. t1c3 (column 3 of table 1). --%>
        columnId = columnId.substring(3, id.length);
    <%-- for e.g. suppose id is t1c3 we want characters after t1c i.e beginIndex = 3. --%>
        var lowerLimit, higherLimit, rowNo = 0;
        for (var i = 0; i < noOfRowsTraversed; i++) {
            lowerLimit = i * noOfColumns + 1;
            higherLimit = (i + 1) * noOfColumns;
            rowNo++;
            if ((columnId >= lowerLimit) && (columnId <= higherLimit))
                break;
        }
        //setDefaultColor(noOfRowsTraversed, noOfColumns);        // set default color of rows (i.e. of multiple coloumns).
        var t1id = "t1c";       // particular column id of table 1 e.g. t1c3.
        for (var i = 0; i < noOfColumns; i++) {
            // set the background color of clicked/selected row to yellow.
            document.getElementById(t1id + (lowerLimit + i)).bgColor = "#d0dafd";
        }
        // Now get clicked row data, and set these into the below edit table.
        //alert(document.getElementById("district_id"+rowNo).value);
        document.getElementById("area_id").value = document.getElementById(t1id + (lowerLimit + 0)).innerHTML;
        document.getElementById("area_name").value = document.getElementById(t1id + (lowerLimit + 2)).innerHTML;
//        document.getElementById("ward_name").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("ward_no").value = document.getElementById(t1id + (lowerLimit + 3)).innerHTML;
        document.getElementById("remark").value = document.getElementById(t1id + (lowerLimit + 4)).innerHTML;

        // Now enable/disable various buttons.
        document.getElementById("edit").disabled = false;
        if (!document.getElementById("save").disabled) {
            // if save button is already enabled, then make edit, and delete button enabled too.
            document.getElementById("delete").disabled = false;
            document.getElementById("save_As").disabled = true;
        }
        $("#message").html("");
    }
    function setStatus(id) {
       if(id == 'update'){
            document.getElementById("clickedButton").value = "Update";
        }
    }
    function myLeftTrim(str) {
        var beginIndex = 0;
        for (var i = 0; i < str.length; i++) {
            if (str.charAt(i) == ' ')
                beginIndex++;
            else
                break;
        }
        return str.substring(beginIndex, str.length);
    }
    function verify() {
        var result;
        if (document.getElementById("clickedButton").value == 'Save' || document.getElementById("clickedButton").value == 'Save AS New') {
            var ward_no = document.getElementById("ward_no").value;
            var city_name = document.getElementById("city_name").value;
            if (myLeftTrim(city_name).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>City  Name  is required...</b></td>");
                document.getElementById("city_name").focus();
                return false; // code to stop from submitting the form2.
            }

            if (myLeftTrim(ward_no).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Ward No is required...</b></td>");
                document.getElementById("ward_no").focus();
                return false; // code to stop from submitting the form2.
            }
            var area_name = document.getElementById("area_name").value;
            if (myLeftTrim(area_name).length == 0) {
                $("#message").html("<td colspan='2' bgcolor='coral'><b>Area Name is required...</b></td>");
                document.getElementById("area_name").focus();
                return false; // code to stop from submitting the form2.
            }
            if (result == false) {
                // if result has value false do nothing, so result will remain contain value false.
            } else {
                result = true;
            }

        } else {
            result = confirm("Are you sure you want to delete this record?");
        }
        return result;
    }

    var popupwin = null;
    function displayMapList() {

        var queryString = "task=areaDetailReport";
        var url = "areaTypeCont?" + queryString;
        popupwin = openPopUp(url, "Area Details", 500, 1000);

    }

    function openPopUp(url, window_name, popup_height, popup_width) {
        var popup_top_pos = (screen.availHeight / 2) - (popup_height / 2);
        var popup_left_pos = (screen.availWidth / 2) - (popup_width / 2);
        var window_features = "left=" + popup_left_pos + ", top=" + popup_top_pos + ", width=" + popup_width + ", height=" + popup_height + ", resizable=yes, scrollbars=yes, location=0, menubar=no, status=no, dependent=yes";

        return window.open(url, window_name, window_features);
    }

    function viewFirstPoleImage(circiut_id)
    {
        debugger;
        var queryString = "task=viewFirstPoleImage&circiut_id=" + circiut_id;

        var url = "PoleSurveyCont?" + queryString;


        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);
    }
    function viewLastPoleImage(circiut_id)
    {

        debugger;
        var queryString = "task=viewlastPoleImage&circiut_id=" + circiut_id;

        var url = "PoleSurveyCont?" + queryString;
        alert(url);

        popupwin = openPopUp(url, "Mounting Type Map Details", 500, 1000);
    }
    function update(id)
    {
     
        document.getElementById("updateform").style.display='block';
  var queryString = "task=updatedata&id=" + id;

        var url = "PoleSurveyCont?" + queryString;
   //     alert(url);
     //   window.open(url);
       
    }
       function displayImagePdfList(id)
                        {
                            var survey_id=id;
                          
                            var url = "PoleSurveyCont?task1=generatePDFReport&survey_id="+survey_id;
                            popupwin = openPopUp(url, "PoleSurveyCont", 500, 1000);

                        }
    function openMap(longitude, lattitude) {
                            var x = lattitude;//$.trim(doc.getElementById(lattitude).value);
                            var y = longitude;//$.trim(doc.getElementById(logitude).value);

                            var url="PoleSurveyCont?task1=showMapWindow&logitude="+y+"&lattitude="+x;
                            popupwin = openPopUp(url, "",  580, 620);
                        }
      function setStatus(id,id1) {
        if(id == 'save'){
            document.getElementById("clickedButton").value = "Save";
        } 
      else if(id == 'upd'){
          debugger;
        var a=""+id1;
            document.getElementById("clickedButton").value = "updatedata";
            document.getElementById("updateid").value = a;
            alert( document.getElementById("updateid").value = a);
        } 
      else if(id == 'update'){
         
            document.getElementById("clickedButton").value = "Save";
        }}
</script>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> Detail</title>
    </head>
    <body>
        <table align="center" cellpadding="0" cellspacing="0"  class="main">            <!--DWLayoutDefaultTable-->
            <tr><td><%@include file="/layout/header.jsp" %></td></tr>
            <tr>
                <td><%@include file="/layout/menu.jsp" %> </td>
            </tr>
            <tr>
                <td>
                    <DIV id="body" class="maindiv">
                        <table width="100%">
                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td align="center"  class="header_table"  width="100%">PoleSurveyDetail </td>
                                            <td>
                                                <div class="tab">
                                                    <a href="#"></a>

                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <div>
                                        <form name="form0" method="POST" action="PoleSurveyCont" >
                                            <table class="heading1" align="center" width="600">
                                                <tr>
                                                    <td>Pole No<input  type="text" name="meter_name_search1" id="meter_name_search1" value="${meter_name_search}" size="20"></td>
                                                    
                                                    <td>
                                                        <input class="button" type="submit" id="search" name="search" value="SEARCH">

                                                    </td>
                                                    <td><input class="button" type="submit" name="task" id="showAllRecords" value="Show All Records"></td>

                                                </tr>
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <form name="form1" method="POST" action="PoleSurveyCont">
                                        <DIV class="content_div" >
                                            <table  border="1" id="table1" align="center" width="600" class="content">
                                                <tr>
                                                    <th class="heading">S.No.</th>
                                                    
                                                    <th class="heading">pole_span</th>
                                                    <th class="heading">pole_height</th>
                                                    <th class="heading">mounting_type</th>
                                                    <th class="heading">mounting_height</th>
                                                    <th class="heading">pole_type</th>
                                                    <th class="heading">pole_no</th>
                                                    <th class="heading">created_date</th>
                                                    <th class="heading">max_avg_lux_level</th>
                                                    <th class="heading">min_avg_lux_level</th>
                                                    <th class="heading">avg_lux_level</th>
                                                    <th class="heading">standard_lux_level</th>
                                                    <th class="heading">road_name</th>
                                                    <th class="heading">traffic_type</th>

                                                    <th class="heading">lightname</th>
                                                    <th class="heading">latitude</th>

                                                    <th class="heading">longitude</th>

                                                    

                                                    <th class="heading">Image</th>

                                                </tr>
                                                <c:forEach var="area" items="${requestScope['dataTypeList']}" varStatus="loopCounter">
                                                    <tr  class="${loopCounter.index % 2 == 0 ? 'even': 'odd'}" >
                                                        <td id="t1c${IDGenerator.uniqueID}" style="display:none" onclick="fillColumns(id)">${area.pole_id}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}" onclick="fillColumns(id)" align="center">${lowerLimit - noOfRowsTraversed + loopCounter.count}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${area.pole_span}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${area.pole_height}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.mounting_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.mounting_height}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.pole_type}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.pole_no}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.created_date}</td>



                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)" >${area.max_avg_lux_level}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.min_avg_lux_level}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.avg_lux_level}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.standard_lux_level}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.road_name}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.traffic_type}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.lightname}</td>
                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.latitude}</td>

                                                        <td id="t1c${IDGenerator.uniqueID}"  onclick="fillColumns(id)">${area.longitude}</td>
                                                         <td>
                                                        <input type="button" class="btn"  value ="View Map" id="map_container${loopCounter.count}" onclick="openMap('${area.longitude}' , '${area.latitude}');"/>
                                                    </td>
                                                    
                                                        <td><center><input class="button" type="button" id="viewFirstPole1" value="viewImage" onclick="viewFirstPoleImage(${area.pole_id})"></center></td>
  <td>
                                                        <input type="button" class="pdf_button" id="${loopCounter.count}" name="viewPdfimage" value="" onclick="displayImagePdfList('${area.pole_id}')" >
                                                    </td>
                                                    <input type="hidden" name="updateid" id="updateid" value="${ids}">
                                                        <td>   <input class="button" type="submit" name="task" id="upd" value="Update" onclick="setStatus(id,'${area.pole_id}')" > </td>
                                                           </tr>
                                                </c:forEach>
                                                <tr>
                                                    <td align='center' colspan="12">
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
                                                    </td>  </tr>
                                                    <%-- These hidden fields "lowerLimit", and "noOfRowsTraversed" belong to form1 of table1. --%>

                                         
 

                                            
                                                               

                                                                </table>
                                                        </DIV>
                                                        </form>
                                                    </td>
                            </tr>
                            <tr id="updateform" style="display: block">

                                                    <td align="center">
                                                    
                                                             <form name="form2" method="POST" action="PoleSurveyCont"  >
                                                             
                                        <table id="table2"  class="content" border="0"  align="center" width="600">
                                            <tr id="message">
                                                <c:if test="${not empty message}">
                                                    <td colspan="7" bgcolor="${msgBgColor}"><b>Result: ${message}</b></td>
                                                </c:if>
                                            </tr>
                                            
                                            <tr>
                                                <th class="heading1">Pole Type</th>
                                                <td>
                                                    <input class="input" type="hidden" id="pole_id" name="pole_id" value="${poleid}" />
                                                    <input class="input" type="hidden" id="imgpath" name="imgpath" value="${imgpath}" />
                                                    <input class="input" type="text" id="pole_type" name="pole_type" value="${poletype}" size="20" />
                                                </td>

                                                <th class="heading1">Pole No</th>
                                                <td>

                                                    <input class="input" type="text" id="pole_no" name="pole_no" value="${poleno}" size="20" disbled/>
                                                </td>
                                                <th class="heading1">Light Type</th>
                                                <td>
                                                    <input class="input" type="text" id="lightname" name="lightname" value="${lightname}" size="20" >
                                                </td>
                                          </tr>
                                            <tr>
                                                <th class="heading1">Pole Span</th>
                                                <td><input class="input" type="text" id="pole_span" name="pole_span" value="${polespan}" size="20" ></td>
                                                <th class="heading1">Pole Height</th>
                                                <td><input class="input" type="text" id="pole_height" name="pole_height" value="${poleheight}" size="20" ></td>

                                                <th class="heading1">Mounting Type</th>
                                                <td><input class="input" type="text" id="mounting_type" name="mounting_type" value="${mountingtype}" size="20" ></td>


                                            </tr>
                                            <tr>
                                                <th class="heading1">Mounting Height</th>
                                                <td><input class="input" type="text" id="mounting_height" name="mounting_height" value="${mountingheight}" size="20" ></td>
                                                <th class="heading1">GPS Code</th>
                                                <td><input class="input" type="text" id="gps_code" name="gps_code" value="0" size="20" disabled></td>

                                                <th class="heading1">Max Avg Lux level</th>
                                                <td><input class="input" type="text" id="max_avg_lux_level" name="max_avg_lux_level" value="${max_lux_level}" size="20" ></td>


                                            </tr>
                                            <tr>
                                                <th class="heading1">Standard Lux level</th>
                                                <td><input class="input" type="text" id="standard_lux_level" name="standard_lux_level" value="${standard_lux_level}" size="20" ></td>
                                                <th class="heading1">Min Avg Lux level</th>
                                                <td><input class="input" type="text" id="min_avg_lux_level" name="min_avg_lux_level" value="${min_lux_level}" size="20" ></td>

                                                <th class="heading1">Avg Lux level</th>
                                                <td><input class="input" type="text" id="avg_lux_level" name="avg_lux_level" value="${avg_lux_level}" size="20" ></td>



                                            </tr>
                                            <tr>
                                                <th class="heading1">Is Working</th>
                                                <td><input class="input" type="text" id="is_working" name="is_working" value="Yes" size="20" ></td>
                                               <th class="heading1">Road Name</th>
                                                <td><input class="input" type="text" id="road_name" name="road_name" value="${roadname}" size="20" ></td>
                                                <th class="heading1">Traffic Type</th>
                                                <td>
                                                    <input class="input" type="text" id="traffic_type" name="traffic_type" value="${trfictype}" size="20" >
                                                </td>
                                                
                                            </tr>
                                            <tr>
                                                <th class="heading1">Area Name</th>
                                                <td><input class="input" type="text" id="area_name" name="area_name" value="${areaname}" size="20" >
                                                </td>
                                             
 
                                                <th class="heading1">Lattitude</th>
                                                <td><input class="input" type="text" id="lattitude" name="lattitude" value="${lattitude}" size="20" ></td>
                                                <th class="heading1">Longitude</th>
                                                <td>
                                                    <input class="input" type="text" id="longitude" name="longitude" value="${longitude}" size="20" >
                                                </td>
                                            </tr>
 
                                         
                                        </table>
                                        <table>  <tr>
                                                <td align='center' colspan="6">
                                                            <input class="button" type="submit" name="task" id="update" value="Save" onclick="setStatus(id)"  >
                                                </td>
                                            </tr>
                                            <%-- These hidden fields "lowerLimit", "noOfRowsTraversed", and "clickedButton" belong to form2 of table2. --%>
                                            <input type="hidden" name="lowerLimit" value="${lowerLimit}">
                                            <input type="hidden" name="noOfRowsTraversed" value="${noOfRowsTraversed}">
                                            <input type="hidden" id="clickedButton" value="">
                                            <input type="hidden"  name="searchPoleType" value="${searchPoleType}" >
                                            <input type="hidden"  name="searchMountingType" value="${searchMountingType}" >
                                            <input type="hidden"  name="id" value="${ids}" >
                                            <input  type="hidden" id="addRowCount" name="addRowCount" value="0" >
                                            <input  type="hidden" id="newRowCount" name="newRowCount" value="0" >
                                        </table>
                                        <div id="googleMap" style="width:600px;height:550px;"></div>
                                    </form>
                                    </DIV>
                                </td>
                            </tr>
                        </table>

                    </DIV>
                </td></tr>
            <tr><td><%@include file="/layout/footer.jsp" %></td> </tr>
        </table>
    </body>
</html>

