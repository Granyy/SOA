var config = {
  content: [{
    type: 'row',
    content: [{
      type: 'column',
      content: [{
        type: 'component',
        componentName: 'temperaturePanel',
        title: 'Gestion température',
        componentState: {
          text: 'Component 1'
        }
      },{
        type: 'component',
        componentName: 'lightingPanel',
        title:'Gestion lumières'
      }]
    },{
      type: 'column',
      content: [{
        type: 'component',
        componentName: 'securityPanel',
        title: 'Gestion sécurité'
      },{
        type: 'component',
        componentName: 'roomPanel',
        title: 'Gestion salle'
      }]
    }]
  }]
};

var myLayout = new GoldenLayout(config);

var host = "localhost";
var port = 8080;
var datapath = "/RestWS/insaRessources/data/"

var sendRequest = function(resource, method, callback = console.log) {
  var settings = {
    "async": true,
    "crossDomain": true,
    "url": "http://" + host + ":" + port.toString() + datapath + resource,
    "method": method,
    "headers": {
      "Cache-Control": "no-cache"
    }
  }

  $.ajax(settings).done(callback);
};

var sendPost = function(resource) {
  sendRequest(resource, "POST");
};

var sendGet = function(resource, callback) {
  sendRequest(resource, "GET", callback);
};

var toggleRegulTemp = function(checkbox) {
  regulTempOn = !regulTempOn;
  if(regulTempOn) {
    sendPost("salle_103/REGULTEMP/active?op=true");
  } else {
    sendPost("salle_103/REGULTEMP/active?op=false");
  }
};

var getRegulTemp = function() {
  sendGet("salle_103/REGULTEMP/active", function(response) {    
    regulTempOn = (response == "true");
    regulTempToggleButton.prop("checked", regulTempOn);
	console.log("getRegulTemp : " + response);
  });
};

var sendTempThreshold = function() {
  sendPost("salle_103/REGULTEMP/tempth?value=" + $('#tempth').val().toString());
};

var getTempThreshold = function() {
  sendGet("salle_103/REGULTEMP/tempth", function(response) {
    $('#tempth').val(parseInt(response));
	console.log("getTempThreshold : " + response);
  });
};

var getTemperature = function() {
  sendGet("salle_103/TEMPERATURE/temperature", function(response) {
    $('#temp').val(response);
	console.log("getTemperature : " + response);
  });
};

myLayout.registerComponent('temperaturePanel', function(container, state) {
  container.getElement().html('<h1>Gestion température</h1>');

  container.getElement().append('<h2>Régulation automatique</h2>');
  var labelToggleTemp = $('<label class="switch"></label>');
  regulTempToggleButton = $('<input type="checkbox">').click(toggleRegulTemp);
  labelToggleTemp.append(regulTempToggleButton);
  labelToggleTemp.append('<span class="slider round"></span>');
  container.getElement().append(labelToggleTemp);
  
  container.getElement().append('<h2>Température seuil</h2>');
  container.getElement().append('<input type="number" id="tempth" min="-10" max="50">');
  container.getElement().append('<button onclick="sendTempThreshold()">Envoyer</button>');

  container.getElement().append('<h2>Température actuelle</h2>');
  container.getElement().append('<input type="text" placeholder="?" id="temp" readonly/>');

  getRegulTemp();
  getTempThreshold();
});

myLayout.registerComponent('lightingPanel', function(container, state) {
  
});

myLayout.registerComponent('securityPanel', function(container, state) {
  
});

myLayout.registerComponent('roomPanel', function(container, state) {
  
});

myLayout.init();

setInterval(getTemperature, 1000);

