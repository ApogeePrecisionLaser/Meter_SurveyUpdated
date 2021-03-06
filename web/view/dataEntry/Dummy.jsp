

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Google Map in jQuery dialog box</title>

<script src="http://maps.googleapis.com/maps/api/js?sensor=false" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"  type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/jquery-ui.min.js"  type="text/javascript"></script>

<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.14/themes/ui-darkness/jquery-ui.css" />
<style>
    .gBubble
    {
        color:black;
        font-family:Tahoma, Geneva, sans-serif;
        font-size:12px;
    }
</style>
<script>
    var map;
    var coords = new Object();
    var markersArray = [];
    coords.lat = 23.1667;
    coords.lng = 79.9333 ;

    $(document).ready(function()
    {
         $( "#map_container" ).dialog({
            autoOpen:false,
            width: 600,
            height: 700,
            resizeStop: function(event, ui) {google.maps.event.trigger(map, 'resize')  },
            open: function(event, ui) {google.maps.event.trigger(map, 'resize'); }
        });  
       

        $( "#showMap" ).click(function() {
            $( "#map_container" ).dialog( "open" );
            map.setCenter(new google.maps.LatLng(coords.lat, coords.lng), 10);
            return false;
        });
        
       initialize();
        plotPoint(coords.lat,coords.lng,'Mall of America','<span class="gBubble"><b>Mall of America</b><br>60 East Brodway<br>Bloomington, MN 55425</span>'); 
    });

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
          zoom: 5,
          center: latlng,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
       map = new google.maps.Map(document.getElementById("map_canvas"),  myOptions);
    }  
</script>
</head>

<body>
    <div id="map_container" title="Location Map">
        <div id="map_canvas" style="width:100%;height:100%;"></div>
    </div>

    <div id="controls">
        <input type="button" name="showMap" value="Show Map" id="showMap" />
    </div>
</body>
</html>

