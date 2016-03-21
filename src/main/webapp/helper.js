
//var host = "http://52.26.228.86:9763/cloudDrop_backend/services/";
var host = "http://52.27.145.23:9763/cloudDrop_backend/services/";
//var host = "http://localhost:9763/cloudDrop_backend/services/";
var selectedAddress = "default"

var deviceJson;

function createDeviceList(){

    var response = httpGet("device/list");
    deviceJson = JSON.parse(response);
    var newCount = 0;
    var blankCount = 0;
    var mappedCount = 0;
    var isImage = "";

    $("#new_deviceList").append('<li data-role="list-divider">New Devices<span id = "new_device_list_count" class="ui-li-count">0</span></li>');
    $("#blank_deviceList").append('<li data-role="list-divider">Blank Devices<span id = "blank_device_list_count" class="ui-li-count">0</span></li>');
    $("#deviceList").append('<li data-role="list-divider">Mapped Devices<span id = "device_list_count" class="ui-li-count">0</span></li>');


    for(i=0; i<deviceJson.device.length; i++){
        if(deviceJson.device[i].newDevice){
            newCount++;
            $("#new_device_list_count").text(newCount);
            $("#new_deviceList").append("<li data-name="+i+" data-icon='gear'><a href='#config_page'><img src='N"+getColour(deviceJson.device[i].address)+".png'><h2>"+deviceJson.device[i].title+"</h2><p>Click to configure.</p></a></li>");
        }else if(deviceJson.device[i].mappingType == 2){
            blankCount++;
            $("#blank_device_list_count").text(blankCount);
            $("#blank_deviceList").append("<li data-name="+i+" data-icon='plus'><a href='#add_mapping_page'><img src='B"+getColour(deviceJson.device[i].address)+".png'><h2>"+"#"+deviceJson.device[i].id+": " + deviceJson.device[i].title+"</h2><p>@"+deviceJson.device[i].location+"</p></a></li>");
        }else{
            mappedCount++;
            if(deviceJson.device[i].mappingType == 4){
                isImage = "_image";
            }else{
                isImage = "";
            }
            $("#device_list_count").text(mappedCount);
            $("#deviceList").append("<li data-name="+i+"><a href='#view_mapping_page"+isImage+"'><img src='"+deviceJson.device[i].mappingType+getColour(deviceJson.device[i].address)+".png'><h2>"+"#"+deviceJson.device[i].id+": "+deviceJson.device[i].title+"</h2><p>@"+deviceJson.device[i].location+"</p></a></li>");
        }

    }


}

function setSelectedAddress(address){
    selectedAddress = address;
    //$("#config_title_text").val(deviceJson.device[address].title);
    $("#config_title_text").val(deviceJson.device[address].title);
    var location = deviceJson.device[address].location;
    $("#config_location_text").val(location);
    var id = deviceJson.device[address].id;
    $("#config_id_text").text("postBit ID: #"+id);


    $("#view_mapping_title span").text(deviceJson.device[address].title);
    $("#view_content_text").text("");

    $("#image_preview").attr('src',"");
    $("#myFile").val("");

    if(deviceJson.device[address].mappingType == 4){
        $("#view_mapping_image_title span").text(deviceJson.device[address].title);
        var response = httpGet("data/image/"+deviceJson.device[address].address);
            if(response != null){
                document.getElementById("view_content_image").src = "data:image/png;base64," + response;
            }
    } else if(!(deviceJson.device[address].newDevice || deviceJson.device[address].mappingType == 2)){
        var response = httpGet("data/content/"+deviceJson.device[address].address);
        if(response != null){
            var textContent = "";
            var contentJson = JSON.parse(response);
            if(jQuery.isArray(contentJson.content.texts)){
                for(i=0; i<contentJson.content.texts.length; i++){
                    textContent += contentJson.content.texts[i]+"\n";
                }
            }else{
                textContent = contentJson.content.texts;
            }
            $("#view_content_text").text(textContent);
            var a;
        }

    }
}

function getSelectedAddress(){
    return selectedAddress;
}



function httpGet(url)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", host+url, false );
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function httpPost(url,body){

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", host+url, false );
    xmlHttp.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xmlHttp.send(body);
    return xmlHttp.responseText;
}

function httpPostData(url,body){

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", host+url, false );
    //xmlHttp.setRequestHeader('Content-Type', 'application/json; charset=UTF-8');
    xmlHttp.send(body);
    return xmlHttp.responseText;
}


function updateDevice(){
    var deviceAddress = deviceJson.device[getSelectedAddress()].address;
    var deviceID = getSelectedAddress();
    var deviceTitle = $("#config_title_text").val();
    var deviceLocation = $("#config_location_text").val();

    var json = '{"device":{' +
                   '"address":"'+deviceAddress+'",' +
                   '"id":"'+deviceID+'",'+
                   '"title":"'+deviceTitle+'",'+
                   '"location":"'+deviceLocation+'"}}';

    var r = httpPost("device/update",json)
    var a = 10;
    location.reload(true);

}

function sendText(){
    var deviceAddress = deviceJson.device[getSelectedAddress()].address;
    var textMessage = $("#add_text_text").val();
    var json = '{"message":{' +
                       '"address":"'+deviceAddress+'",' +
                       '"type":"text",' +
                       '"text":["'+textMessage+'"]'+

                       '}}';

    var r = httpPost("mapping/text",json)
    var a = 10;
    location.reload(true);
}

function sendList(){
    var deviceAddress = deviceJson.device[getSelectedAddress()].address;
    var textMessage = $("#add_list_text").val();
    var operation = "new";
    if($("#append_radio").is(":checked")){
        operation = "append"
    }
    var json = '{"message":{' +
                       '"address":"'+deviceAddress+'",' +
                       '"type":"list",' +
                       '"operation":"'+operation+'",' +
                       '"text":["'+textMessage+'"]'+

                       '}}';

    var r = httpPost("mapping/list",json)
    var a = 10;
    location.reload(true);
}


function sendTwitter(){
    var deviceAddress = deviceJson.device[getSelectedAddress()].address;
    var feed = $("#add_feed_text").val();


    var r = httpPost("mapping/twitter/"+deviceAddress+"/"+feed,null);
    var a = 10;
    location.reload(true);

}

function sendWeather(){
    var deviceAddress = deviceJson.device[getSelectedAddress()].address;
    var city = $("#add_weather_text").val();

    var url = "http://api.openweathermap.org/data/2.5/forecast?q="+city.replace(" ","_")+"&units=metric";
    var xmlHttp = new XMLHttpRequest();
        xmlHttp.open( "GET", url, false );
        xmlHttp.send( null );
    if(xmlHttp.responseText.indexOf("Error")>0){
        alert("City not found!\n"+xmlHttp.responseText);
    }else{
        var r = httpPost("mapping/weather/"+deviceAddress+"/"+city,null);
        var a = 10;
        location.reload(true);
    }

}

function sendNews(){
    var deviceAddress = deviceJson.device[getSelectedAddress()].address;
        var category = "world";
        var id = $("#add_news_select")[0].selectedIndex;
        switch (id){
            case 1: category = "allpolitics";break;
            case 2: category = "tech";break;
            case 3: category = "health";break;
            case 4: category = "travel";break;
            case 5: category = "showbiz";break;
        }

        var r = httpPost("mapping/news/"+deviceAddress+"/"+category,null);
        var a = 10;
        location.reload(true);

}

function sendFLL(){
    var deviceAddress = deviceJson.device[getSelectedAddress()].address;

        var r = httpPost("mapping/FLL/"+deviceAddress,null);
        var a = 10;
        location.reload(true);

}

function imageUpload(){
    var x = document.getElementById("myFile");
    var txt = "";
    if ('files' in x) {
        if (x.files.length > 0) {
            file = x.files[0];
            //$("#add_image_text").val(file.name);
            var deviceAddress = deviceJson.device[getSelectedAddress()].address;
            var r = httpPostData("mapping/image/"+deviceAddress+"/",file);
            $("#image_preview").attr('src',"");
            $("#myFile").val("");
                var a = 10;
                location.reload(true);

        }
    }
}

function handleFileSelect(evt) {
    var x = document.getElementById("myFile");
        var txt = "";
        if ('files' in x) {
            if (x.files.length > 0) {
                file = x.files[0];
                //$("#add_image_text").val(file.name);

                var reader  = new FileReader();
                if (file) {
                   reader.readAsDataURL(file);
                } else {
                   //  $("#image_preview").src = "";
                }
                reader.onloadend = function () {
                    $("#image_preview").attr('src',reader.result);
                }


            }
        }

  }

  function clearAll() {
      var passCode = $("#pass_code_text").val();
      httpGet("data/clear/"+passCode);
    }


  function getColour(address){

    address = address.toUpperCase();

    if(address == "0013A20040BE4796"){
        return "P";
    }else if(address == "0013A20040E44213"){
        return "P";
    }else if(address == "0013A20040E44279"){
             return "P";
    }else if(address == "0013A20040AD5783"){
             return "O";
    }else if(address == "0013A20040E441FE"){
             return "O";
    }
    else if(address == "0013A20040E4420A"){
             return "O";
    }else if(address == "0013A20040ADD42F"){
             return "Y";
    }else if(address == "0013A20040E441FF"){
             return "Y";
    }
    else if(address ==  "0013A20040E44220"){
             return "Y";
    }else if(address == "0013A20040BE45B1"){
             return "G";
    }else if(address == "0013A20040E44227"){
             return "G";
    }else if(address == "0013A20040E44227"){
             return "G";
    }else if(address == "0013A20040ADD0E5"){
             return "B";
    }else if(address == "0013A20040E4421B"){
             return "B";
    }else if(address == "0013A20040E4421B"){
             return "B";
    }else if(address == "0013A20040b3E2A4"){
                  return "P";
    }else{
        return "";
    }


  }

