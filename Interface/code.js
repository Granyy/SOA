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
        title: 'Gestion température'
      }]
    },{
      type: 'column',
      content: [{
        type: 'component',
        componentName: 'securityPanel',
        title: 'Gestion sécurité'
      }, {
        type: 'component',
        componentName: 'lightingPanel',
        title: 'Gestion lumières'
      }]
    }]
  }]
};

var layout = new GoldenLayout(config);

var host = "localhost:8080";
var dataPath = "/RestWS/insaRessources/data/";
var roomPath = "/RestWS/insaRessources/room/";

var roomOn = false;
var roomOnUpdate = true;
var regulTempOn = false;
var regulTempOnUpdate = true;
var heaterOn = false;
var heaterOnUpdate = true;
var regulLightingOn = false;
var regulLightingOnUpdate = true;
var lightsOn = false;
var lightsOnUpdate = true;
var securityOn = false;

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

var post = function(resource, callback) {
  sendRequest(resource, "POST", callback);
};

var get = function(resource, callback) {
  sendRequest(resource, "GET", callback);
};

var postData = function(resource, callback) {
  post(dataPath + roomSelector[0].value + "/" + resource, callback);
};

var getData = function(resource, callback) {
  get(dataPath + roomSelector[0].value + "/" + resource, callback);
};

var toggleRoom = function() {
  roomOn = !roomOn;
  roomOnUpdate = false;
  var op = roomOn ? "?op=true" : "?op=false";
  post(roomPath + roomSelector[0].value + op, function() {
    roomOnUpdate = true;
  });
};

var getRoom = function() {
  get(roomPath + roomSelector[0].value, function(response) {
    roomOn = (response == "true");
    roomToggleCheckbox.prop("checked", roomOn);
  });
}

var toggleRegulTemp = function() {
  regulTempOn = !regulTempOn;
  var op = regulTempOn ? "?op=true" : "?op=false";
  regulTempOnUpdate = false;
  postData("REGULTEMP/active" + op, function() {
    regulTempOnUpdate = true;
  });

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
  var op = heaterOn ? "?op=true" : "?op=false";
  var callback = undefined;
  if (regulTempOff) {
    heaterOnUpdate = false;
    callback = function() {
      heaterOnUpdate = true;
    }
  }
  postData("HEATER/active" + op, callback);
};

var getHeater = function() {
  getData("HEATER/active", function(response) {    
    heaterOn = (response == "true");
    heaterToggleCheckbox.prop("checked", heaterOn);
    if (heaterOn) {
      $('#ledheater').removeClass("led-gray");
      $('#ledheater').addClass("led-green");
    } else {
      $('#ledheater').removeClass("led-green");
      $('#ledheater').addClass("led-gray");
    }
  });
};

var getTemperature = function() {
  getData("TEMPERATURE/temperature", function(response) {
    $('#temp').val(response);
  });
};

var toggleRegulLighting = function() {
  regulLightingOn = !regulLightingOn;
  var op = regulLightingOn ? "?op=true" : "?op=false";
  regulLightingOnUpdate = false;
  postData("REGULLIGHT/active" + op, function() {
    regulLightingOnUpdate = true;
  });

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
  var op = lightsOn ? "?op=true" : "?op=false";
  var callback = undefined;
  if (regulLightsOff) {
    lightsOnUpdate = false;
    callback = function() {
      lightsOnUpdate = true;
    }
  }
  postData("LIGHT/active" + op, callback);
};

var getLights = function() {
  getData("LIGHT/active", function(response) {    
    lightsOn = (response == "true");
    lightsToggleCheckbox.prop("checked", lightsOn);
    if (lightsOn) {
      $('#ledlights').removeClass("led-gray");
      $('#ledlights').addClass("led-green");
    } else {
      $('#ledlights').removeClass("led-green");
      $('#ledlights').addClass("led-gray");
    }
  });
};

var getLuminosity = function() {
  getData("LUMINOSITY/luminosity", function(response) {
    $('#light').val(response);
  });
};

var getSecurity = function() {
  getData("SECURITY/active", function(response) {    
    securityOn = (response == "true");
    if (securityOn) {
      $('#ledsecurity').removeClass("led-gray");
      $('#ledsecurity').addClass("led-green");
    } else {
      $('#ledsecurity').removeClass("led-green");
      $('#ledsecurity').addClass("led-gray");
    }
  });
};

var sendSecurityTime = function() {
  postData("SECURITY/begin?value=" + $('#securityTime').val().toString());
};

var getSecurityTime = function() {
  getData("SECURITY/begin", function(response) {
    $('#securityTime').val(parseInt(response));
  });
};

var getMotion = function() {
  getData("MOTION/motion", function(response) {
    if (response == "true") {
      $('#ledmotion').removeClass("led-gray");
      $('#ledmotion').addClass("led-green");
    } else {
      $('#ledmotion').removeClass("led-green");
      $('#ledmotion').addClass("led-gray");
    }
  });
};

var getAlarm = function() {
  getData("ALARM/alarm", function(response) {
    if (response == "true") {
      $('#ledalarm').removeClass("led-gray");
      $('#ledalarm').addClass("led-alarm");
    } else {
      $('#ledalarm').removeClass("led-alarm");
      $('#ledalarm').addClass("led-gray");
    }
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

layout.registerComponent('securityPanel', function(container, state) {
  container.getElement().html('<h1>Gestion sécurité</h1>');

  container.getElement().append('<h2>Heure d\'activation</h2>');
  container.getElement().append('<input type="number" id="securityTime" min="0" max="23">');
  container.getElement().append('<button onclick="sendSecurityTime()">Envoyer</button>');

  container.getElement().append('<h2>Sécurité</h2>');
  container.getElement().append('<div id="ledsecurity" class="led-gray"></div>');

  container.getElement().append('<h2>Présence</h2>');
  container.getElement().append('<div id="ledmotion" class="led-gray"></div>');

  container.getElement().append('<h2>Alarme</h2>');
  container.getElement().append('<div id="ledalarm" class="led-gray"></div>');

  getSecurityTime();
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
  regulTempOnDiv.append('<h2>Chauffage</h2>');
  regulTempOnDiv.append('<div id="ledheater" class="led-gray"></div>');

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
  regulLightingOnDiv.append('<h2>Lumières</h2>');
  regulLightingOnDiv.append('<div id="ledlights" class="led-gray"></div>');

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

layout.init();

var update = function() {
  if (roomOnUpdate) {
    getRoom();
  }
  if (regulTempOnUpdate) {
    getRegulTemp();
  }
  if (heaterOnUpdate) {
    getHeater();
  }
  getTemperature();
  if (regulLightingOnUpdate) {
    getRegulLighting();
  }
  if (lightsOnUpdate) {
    getLights();
  }
  getLuminosity();
  getSecurity();
  getMotion();
  getAlarm();
};

update();
setInterval(update, 3000);
