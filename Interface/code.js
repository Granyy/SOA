var config = {
  content: [{
    type: 'row',
    content: [{
      type: 'column',
      content: [{
        type: 'component',
        componentName: 'roomsPanel',
        title: 'Vue d\'ensemble'
      }, {
        type: 'component',
        componentName: 'temperaturePanel',
        title: 'Gestion température',
      }]
    },{
      type: 'column',
      content: [{
        type: 'component',
        componentName: 'lightingPanel',
        title: 'Gestion lumières'
      }, {
        type: 'component',
        componentName: 'securityPanel',
        title: 'Gestion sécurité'
      }]
    }]
  }]
};

var layout = new GoldenLayout(config);

var host = "localhost:8080";
var dataPath = "/RestWS/insaRessources/data/";
var roomPath = "/RestWS/insaRessources/room/";

var roomOn = true;
var regulTempOn = true;
var heaterOn = true;
var regulLightingOn = true;
var lightsOn = true;
var securityOn = true;

var sendRequest = function(resource, method, callback = console.log) {
  var settings = {
    "async": true,
    "crossDomain": true,
    "url": "http://" + host + resource,
    "method": method,
    "headers": {
      "Cache-Control": "no-cache"
    }
  }

  $.ajax(settings).done(callback);
};

var post = function(resource) {
  sendRequest(resource, "POST");
};

var get = function(resource, callback) {
  sendRequest(resource, "GET", callback);
};

var postData = function(resource) {
  post(dataPath + roomSelector[0].value + "/" + resource);
};

var getData = function(resource, callback) {
  get(dataPath + roomSelector[0].value + "/" + resource, callback);
};

var toggleRoom = function() {
  roomOn = !roomOn;
  if (roomOn) {
    post(roomPath + roomSelector[0].value + "?op=true");
  } else {
    post(roomPath + roomSelector[0].value + "?op=false");
  }
};

var toggleRegulTemp = function() {
  regulTempOn = !regulTempOn;
  if (regulTempOn) {
    postData("REGULTEMP/active?op=true");
  } else {
    postData("REGULTEMP/active?op=false");
  }

  updateRegulTempDivs();
};

var getRegulTemp = function() {
  getData("REGULTEMP/active", function(response) {    
    regulTempOn = (response == "true");
    regulTempToggleCheckbox.prop("checked", regulTempOn);
  });

  updateRegulTempDivs();
};

var updateRegulTempDivs = function() {
  if (regulTempOn) {
    regulTempOnDiv.removeClass('invisible');
    regulTempOffDiv.addClass('invisible');
  } else {
    regulTempOnDiv.addClass('invisible');
    regulTempOffDiv.removeClass('invisible');
  }
};

var sendTempThreshold = function() {
  postData("REGULTEMP/tempth?value=" + $('#tempth').val().toString());
};

var getTempThreshold = function() {
  getData("REGULTEMP/tempth", function(response) {
    $('#tempth').val(parseInt(response));
  });
};

var toggleHeater = function() {
  heaterOn = !heaterOn;
  if (heaterOn) {
    postData("HEATER/active?op=true");
  } else {
    postData("HEATER/active?op=false");
  }
};

var getHeater = function() {
  getData("HEATER/active", function(response) {    
    heaterOn = (response == "true");
    heaterToggleCheckbox.prop("checked", heaterOn);
  });
};

var getTemperature = function() {
  getData("TEMPERATURE/temperature", function(response) {
    $('#temp').val(response);
  });
};

var toggleRegulLighting = function() {
  regulLightingOn = !regulLightingOn;
  if (regulLightingOn) {
    postData("REGULLIGHT/active?op=true");
  } else {
    postData("REGULLIGHT/active?op=false");
  }

  updateRegulLightingDivs();
};

var getRegulLighting = function() {
  getData("REGULLIGHT/active", function(response) {    
    regulLightingOn = (response == "true");
    regulLightingToggleCheckbox.prop("checked", regulLightingOn);
  });

  updateRegulLightingDivs();
};

var updateRegulLightingDivs = function() {
  if (regulLightingOn) {
    regulLightingOnDiv.removeClass('invisible');
    regulLightingOffDiv.addClass('invisible');
  } else {
    regulLightingOnDiv.addClass('invisible');
    regulLightingOffDiv.removeClass('invisible');
  }
};

var sendLightThreshold = function() {
  postData("REGULLIGHT/lightth?value=" + $('#lightth').val().toString());
};

var getLightThreshold = function() {
  getData("REGULLIGHT/lightth", function(response) {
    $('#lightth').val(parseInt(response));
  });
};

var toggleLights = function() {
  lightsOn = !lightsOn;
  if (lightsOn) {
    postData("LIGHT/active?op=true");
  } else {
    postData("LIGHT/active?op=false");
  }
};

var getLights = function() {
  getData("LIGHT/active", function(response) {    
    lightsOn = (response == "true");
    lightsToggleCheckbox.prop("checked", lightsOn);
  });
};

var getLuminosity = function() {
  getData("LUMINOSITY/luminosity", function(response) {
    $('#light').val(response);
  });
};

var toggleSecurity = function() {
  securityOn = !securityOn;
  if (securityOn) {
    postData("SECURITY/active?op=true");
  } else {
    postData("SECURITY/active?op=false");
  }
};

var getSecurity = function() {
  getData("SECURITY/active", function(response) {    
    securityOn = (response == "true");
    securityToggleCheckbox.prop("checked", securityOn);
  });
};

var getMotion = function() {
  getData("MOTION/motion", function(response) {
    $('#motion').val(response);
  });
};

layout.registerComponent('roomsPanel', function(container, state) {
  container.getElement().html('<h1>Vue d\'ensemble</h1>');

  roomSelector = $('<select></select>');
  roomSelector.append($('<option value="salle_102">Salle 102</option>'));
  roomSelector.append($('<option value="salle_103">Salle 103</option>'));
  container.getElement().append(roomSelector);

  container.getElement().append('<h2>Système de régulation</h2>');

  var roomToggleButton = $('<label class="switch"></label>');
  roomToggleCheckbox = $('<input type="checkbox">').click(toggleRoom);
  roomToggleButton.append(roomToggleCheckbox);
  roomToggleButton.append('<span class="slider round"></span>');

  container.getElement().append(roomToggleButton);
});

layout.registerComponent('temperaturePanel', function(container, state) {
  container.getElement().html('<h1>Gestion température</h1>');

  container.getElement().append('<h2>Régulation automatique</h2>');
  var regulTempToggleButton = $('<label class="switch"></label>');
  regulTempToggleCheckbox = $('<input type="checkbox">').click(toggleRegulTemp);
  regulTempToggleButton.append(regulTempToggleCheckbox);
  regulTempToggleButton.append('<span class="slider round"></span>');
  container.getElement().append(regulTempToggleButton);
  
  regulTempOnDiv = $('<div>');
  regulTempOnDiv.append('<h2>Température seuil</h2>');
  regulTempOnDiv.append('<input type="number" id="tempth" min="-10" max="50">');
  regulTempOnDiv.append('<button onclick="sendTempThreshold()">Envoyer</button>');
  container.getElement().append(regulTempOnDiv);

  regulTempOffDiv = $('<div>');
  regulTempOffDiv.append('<h2>Chauffage</h2>');

  var heaterToggleButton = $('<label class="switch"></label>');
  heaterToggleCheckbox = $('<input type="checkbox">').click(toggleHeater);
  heaterToggleButton.append(heaterToggleCheckbox);
  heaterToggleButton.append('<span class="slider round"></span>');
  regulTempOffDiv.append(heaterToggleButton);

  regulTempOffDiv.append('<h2>Température cible</h2>');
  regulTempOffDiv.append('<input type="number" id="tempth" min="-10" max="50">');
  regulTempOffDiv.append('<button onclick="sendTempThreshold()">Envoyer</button>');
  container.getElement().append(regulTempOffDiv);

  container.getElement().append('<h2>Température actuelle</h2>');
  container.getElement().append('<input type="text" placeholder="?" id="temp" readonly/>');

  updateRegulTempDivs();
  getTempThreshold();
});

layout.registerComponent('lightingPanel', function(container, state) {
  container.getElement().html('<h1>Gestion lumières</h1>');

  container.getElement().append('<h2>Lumières automatiques</h2>');
  var regulLightingToggleButton = $('<label class="switch"></label>');
  regulLightingToggleCheckbox = $('<input type="checkbox">').click(toggleRegulLighting);
  regulLightingToggleButton.append(regulLightingToggleCheckbox);
  regulLightingToggleButton.append('<span class="slider round"></span>');
  container.getElement().append(regulLightingToggleButton);

  regulLightingOnDiv = $('<div>');
  regulLightingOnDiv.append('<h2>Luminosité seuil</h2>');
  regulLightingOnDiv.append('<input type="number" id="lightth" min="-10" max="50">');
  regulLightingOnDiv.append('<button onclick="sendLightThreshold()">Envoyer</button>');
  container.getElement().append(regulLightingOnDiv);

  regulLightingOffDiv = $('<div>');
  regulLightingOffDiv.append('<h2>Lumières</h2>');

  var lightsToggleButton = $('<label class="switch"></label>');
  lightsToggleCheckbox = $('<input type="checkbox">').click(toggleLights);
  lightsToggleButton.append(lightsToggleCheckbox);
  lightsToggleButton.append('<span class="slider round"></span>');
  regulLightingOffDiv.append(lightsToggleButton);

  container.getElement().append(regulLightingOffDiv);

  container.getElement().append('<h2>Luminosité actuelle</h2>');
  container.getElement().append('<input type="text" placeholder="?" id="light" readonly/>');

  updateRegulLightingDivs();
  getLightThreshold();
});

layout.registerComponent('securityPanel', function(container, state) {
  container.getElement().html('<h1>Gestion sécurité</h1>');

  container.getElement().append('<h2>Alarme automatique</h2>');
  var securityToggleButton = $('<label class="switch"></label>');
  securityToggleCheckbox = $('<input type="checkbox">').click(toggleSecurity);
  securityToggleButton.append(securityToggleCheckbox);
  securityToggleButton.append('<span class="slider round"></span>');
  container.getElement().append(securityToggleButton);

  container.getElement().append('<h2>Présence</h2>');
  container.getElement().append('<input type="text" placeholder="?" id="motion" readonly/>');
});

layout.init();

setInterval(function() {
  getRegulTemp();
  getHeater();
  getTemperature();
  getRegulLighting();
  getLights();
  getLuminosity();
  getSecurity();
  getMotion();
}, 1000);
