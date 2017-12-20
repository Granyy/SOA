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

var toggleTemp = function(checkbox) {
  regulTempOn = !regulTempOn;
  if(regulTempOn) {
    sendPost("/REGULTEMP/active?op=true");
  } else {
    sendPost("/REGULTEMP/active?op=false");
  }
};

var sendTempThreshold = function() {
  sendPost("/REGULTEMP/tempth?value=" + $('#tempth').val().toString());
};

var getTempThreshold = function() {
  sendGet("/REGULTEMP/tempth", function(response) {
    $('#tempth').val(parseInt(response));
  });
};

var getTemperature = function() {
  sendGet("/TEMPERATURE/temperature", function(response) {
    $('#temp').val(response);
  });
};

myLayout.registerComponent('temperaturePanel', function(container, state) {
  container.getElement().html('<h1>Gestion température</h1>');

  container.getElement().append('<h2>Régulation automatique</h2>');
  var labelToggleTemp = $('<label class="switch"></label>');
  var toggleButton = $('<input type="checkbox">').click(toggleTemp);
  labelToggleTemp.append(toggleButton);
  labelToggleTemp.append('<span class="slider round"></span>');
  container.getElement().append(labelToggleTemp);
  regulTempOn = false;
  
  container.getElement().append('<h2>Température seuil</h2>');
  container.getElement().append('<input type="number" id="tempth" min="-10" max="50">');
  container.getElement().append('<button onclick="sendTempThreshold()">Envoyer</button>');

  container.getElement().append('<h2>Température actuelle</h2>');
  container.getElement().append('<input type="text" placeholder="?" id="temp" readonly/>');
});

myLayout.registerComponent('lightingPanel', function(container, state) {
  
});

myLayout.registerComponent('securityPanel', function(container, state) {
  
});

myLayout.registerComponent('roomPanel', function(container, state) {
  
});

myLayout.init();

setInterval(getTemperature, 1000);

