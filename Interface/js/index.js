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

var sendRequest = function(url, method, callback = console.log) {
  var settings = {
    "async": true,
    "crossDomain": true,
    "url": url,
    "method": method,
    "headers": {
      "Cache-Control": "no-cache"
    }
  }

  $.ajax(settings).done(callback);
};

var sendPost = function(resource) {
  sendRequest("http://" + host + ":" + port.toString() + resource, "POST");
};

var sendGet = function(resource, callback) {
  sendRequest("http://" + host + ":" + port.toString() + resource, "GET", callback);
};

var toggleTemp = function(checkbox) {
  if(checkbox.checked) {
    sendPost("/RestWS/insaRessources/data/REGULTEMP/Active?op=true");
  } else {
    sendPost("/RestWS/insaRessources/data/REGULTEMP/Active?op=false");
  }
};

var sendTempThreshold = function() {
  sendPost("/RestWS/insaRessources/data/REGULTEMP?tempth=" + $('#tempth').val().toString());
};

var retrieveData = function() {
  sendGet("/RestWS/insaRessources/data/TEMPERATURE/Temperature", function(response) {
    $('#temp').val(response);
  });
};

myLayout.registerComponent('temperaturePanel', function(container, state) {
  container.getElement().html('<h1>Gestion température</h1>');

  container.getElement().append('<h2>Régulation automatique</h2>');
  container.getElement().append($('#onOffTemp').remove());
  
  container.getElement().append('<h2>Température seuil</h2>');
  container.getElement().append('<input type="number" id="tempth" value="20" min="-10" max="50">');
  container.getElement().append('<button onclick="sendTempThreshold()">Envoyer</button>');

  container.getElement().append('<h2>Température actuelle</h2>');
  container.getElement().append('<input type="text" placeholder="..." id="temp" readonly/>');
});

myLayout.registerComponent('lightingPanel', function(container, state) {
  
});

myLayout.registerComponent('securityPanel', function(container, state) {
  
});

myLayout.registerComponent('roomPanel', function(container, state) {
  
});

myLayout.init();

setInterval(retrieveData, 1000);

