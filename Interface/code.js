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

var host = "192.168.1.16:8080";
var dataPath = "/RestWS/insaRessources/data/";
var roomPath = "/RestWS/insaRessources/room/";

var roomSelector = $('<select onchange="roomChanged"></select>');
var securityTimeUpdated = false;
var tempThresholdUpdated = false;
var lightThresholdUpdated = false;

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
var doorsOn = false;
var windowsOn = false;
var windowsOnUpdate = true;

var roomToggleCheckbox = $('<input type="checkbox">');
var regulTempToggleCheckbox = $('<input type="checkbox">');
var heaterToggleCheckbox = $('<input type="checkbox">');
var regulLightingToggleCheckbox = $('<input type="checkbox">');
var lightsToggleCheckbox = $('<input type="checkbox">');
var windowsToggleCheckbox = $('<input type="checkbox">');

var regulTempOnDiv = $('<div>');
var regulTempOffDiv = $('<div>');
var regulLightingOnDiv = $('<div>');
var regulLightingOffDiv = $('<div>');

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
  tempThresholdUpdated = false;
  getData("REGULTEMP/tempth", function(response) {
    $('#tempth').val(parseInt(response));
    tempThresholdUpdated = true;
  });
};

var toggleHeater = function() {
  heaterOn = !heaterOn;
  var op = heaterOn ? "?op=true" : "?op=false";
  var callback = undefined;
  if (!regulTempOn) {
    heaterOnUpdate = false;
    callback = function() {
      heaterOnUpdate = true;
    }
  }
  postData("HEATER/heating" + op, callback);
};

var getHeater = function() {
  getData("HEATER/heating", function(response) {    
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
  lightThresholdUpdated = false;
  getData("REGULLIGHT/lightth", function(response) {
    $('#lightth').val(parseInt(response));
    lightThresholdUpdated = true;
  });
};

var toggleLights = function() {
  lightsOn = !lightsOn;
  var op = lightsOn ? "?op=true" : "?op=false";
  var callback = undefined;
  if (!regulLightsOn) {
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

var sendSecurityTime = function() {
  postData("SECURITY/begin?value=" + $('#securityTime').val().toString());
};

var getSecurityTime = function() {
  securityTimeUpdated = false;
  getData("SECURITY/begin", function(response) {
    $('#securityTime').val(parseInt(response));
    securityTimeUpdated = true;
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

var getDoors = function() {
  getData("DOOR/open", function(response) {    
    doorsOn = (response == "true");
    if (doorsOn) {
      $('#leddoors').removeClass("led-gray");
      $('#leddoors').addClass("led-green");
    } else {
      $('#leddoors').removeClass("led-green");
      $('#leddoors').addClass("led-gray");
    }
  });
};

var toggleWindows = function() {
  windowsOn = !windowsOn;
  windowsOnUpdate = false;
  var op = windowsOn ? "?op=true" : "?op=false";
  postData("WINDOW/open" + op, function() {
    windowsOnUpdate = true;
  });
};

var getWindows = function() {
  getData("WINDOW/open", function(response) {    
    windowsOn = (response == "true");
    windowsToggleCheckbox.prop("checked", windowsOn);
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
  getData("ALARM/on", function(response) {
    if (response == "true") {
      $('#ledalarm').removeClass("led-gray");
      $('#ledalarm').addClass("led-alarm");
    } else {
      $('#ledalarm').removeClass("led-alarm");
      $('#ledalarm').addClass("led-gray");
    }
  });
};

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
  getDoors();
  if (windowsOnUpdate) {
    getWindows();
  }
  getMotion();
  getAlarm();
};

var resetRoom = function() {
  roomOn = false;
  roomOnUpdate = true;
  regulTempOn = false;
  regulTempOnUpdate = true;
  heaterOn = false;
  heaterOnUpdate = true;
  regulLightingOn = false;
  regulLightingOnUpdate = true;
  lightsOn = false;
  lightsOnUpdate = true;
  securityOn = false;
  doorsOn = false;
  windowsOn = false;
  windowsOnUpdate = true;

  roomToggleCheckbox.prop("checked", roomOn);
  regulTempToggleCheckbox.prop("checked", regulTempOn);
  heaterToggleCheckbox.prop("checked", heaterOn);
  regulLightingToggleCheckbox.prop("checked", regulLightingOn);
  lightsToggleCheckbox.prop("checked", lightsOn);
  windowsToggleCheckbox.prop("checked", windowsOn);

  updateRegulTempDivs();
  updateRegulLightingDivs();

  getSecurityTime();
  getTempThreshold();
  getLightThreshold();

  update();
};

var checkRoomUpdate = function() {
  if (securityTimeUpdated && tempThresholdUpdated && lightThresholdUpdated) {
    $('#roomLoader').addClass("invisible");
  }
};

var roomChanged = function() {
  $('#roomLoader').removeClass("invisible");
  resetRoom();
  setInterval(checkRoomUpdate, 3000);
};

layout.registerComponent('roomsPanel', function(container, state) {
  container.getElement().html('<h1>Vue d\'ensemble</h1>');

  roomSelector.change(roomChanged);
  roomSelector.append($('<option value="salle_102">Salle 102</option>'));
  roomSelector.append($('<option value="salle_103">Salle 103</option>'));
  container.getElement().append(roomSelector);
  container.getElement().append('<br>');

  container.getElement().append('<div id="roomLoader" class="loader invisible"></div>');

  container.getElement().append('<h2>Système de régulation</h2>');

  var roomToggleButton = $('<label class="switch"></label>');
  roomToggleCheckbox.click(toggleRoom);
  roomToggleButton.append(roomToggleCheckbox);
  roomToggleButton.append('<span class="slider round"></span>');

  container.getElement().append(roomToggleButton);
});

layout.registerComponent('securityPanel', function(container, state) {
  container.getElement().html('<h1>Gestion sécurité</h1>');

  container.getElement().append('<h2>Heure d\'activation</h2>');
  container.getElement().append('<input type="number" id="securityTime" min="0" max="23">');
  container.getElement().append('<button onclick="sendSecurityTime()">Envoyer</button>');
  container.getElement().append('<br>');

  container.getElement().append('<h2>Sécurité</h2>');
  container.getElement().append('<div id="ledsecurity" class="led-gray"></div>');
  container.getElement().append('<h2>Vérouillage portes</h2>');
  container.getElement().append('<div id="leddoors" class="led-gray"></div>');
  container.getElement().append('<br>');

  container.getElement().append('<h2>Présence</h2>');
  container.getElement().append('<div id="ledmotion" class="led-gray"></div>');
  container.getElement().append('<h2>Ouverture fenêtres</h2>');

  var windowsToggleButton = $('<label class="switch"></label>');
  windowsToggleCheckbox.click(toggleWindows);
  windowsToggleButton.append(windowsToggleCheckbox);
  windowsToggleButton.append('<span class="slider round"></span>');

  container.getElement().append(windowsToggleButton);
  container.getElement().append('<br>');

  container.getElement().append('<h2>Alarme</h2>');
  container.getElement().append('<div id="ledalarm" class="led-gray"></div>');
});

layout.registerComponent('temperaturePanel', function(container, state) {
  container.getElement().html('<h1>Gestion température</h1>');

  container.getElement().append('<h2>Régulation automatique</h2>');
  var regulTempToggleButton = $('<label class="switch"></label>');
  regulTempToggleCheckbox.click(toggleRegulTemp);
  regulTempToggleButton.append(regulTempToggleCheckbox);
  regulTempToggleButton.append('<span class="slider round"></span>');
  container.getElement().append(regulTempToggleButton);
  
  regulTempOnDiv.append('<h2>Chauffage</h2>');
  regulTempOnDiv.append('<div id="ledheater" class="led-gray"></div>');
  regulTempOnDiv.append('<br>');

  regulTempOnDiv.append('<h2>Température seuil</h2>');
  regulTempOnDiv.append('<input type="number" id="tempth" min="-10" max="50">');
  regulTempOnDiv.append('<button onclick="sendTempThreshold()">Envoyer</button>');
  container.getElement().append(regulTempOnDiv);

  regulTempOffDiv.append('<h2>Chauffage</h2>');

  var heaterToggleButton = $('<label class="switch"></label>');
  heaterToggleCheckbox.click(toggleHeater);
  heaterToggleButton.append(heaterToggleCheckbox);
  heaterToggleButton.append('<span class="slider round"></span>');
  regulTempOffDiv.append(heaterToggleButton);

  container.getElement().append(regulTempOffDiv);

  container.getElement().append('<h2>Température actuelle</h2>');
  container.getElement().append('<input type="text" placeholder="?" id="temp" readonly/>');
});

layout.registerComponent('lightingPanel', function(container, state) {
  container.getElement().html('<h1>Gestion lumières</h1>');

  container.getElement().append('<h2>Lumières automatiques</h2>');
  var regulLightingToggleButton = $('<label class="switch"></label>');
  regulLightingToggleCheckbox.click(toggleRegulLighting);
  regulLightingToggleButton.append(regulLightingToggleCheckbox);
  regulLightingToggleButton.append('<span class="slider round"></span>');
  container.getElement().append(regulLightingToggleButton);

  regulLightingOnDiv.append('<h2>Lumières</h2>');
  regulLightingOnDiv.append('<div id="ledlights" class="led-gray"></div>');
  regulLightingOnDiv.append('<br>');

  regulLightingOnDiv.append('<h2>Luminosité seuil</h2>');
  regulLightingOnDiv.append('<input type="number" id="lightth" min="-10" max="50">');
  regulLightingOnDiv.append('<button onclick="sendLightThreshold()">Envoyer</button>');
  container.getElement().append(regulLightingOnDiv);

  regulLightingOffDiv.append('<h2>Lumières</h2>');

  var lightsToggleButton = $('<label class="switch"></label>');
  lightsToggleCheckbox.click(toggleLights);
  lightsToggleButton.append(lightsToggleCheckbox);
  lightsToggleButton.append('<span class="slider round"></span>');
  regulLightingOffDiv.append(lightsToggleButton);

  container.getElement().append(regulLightingOffDiv);

  container.getElement().append('<h2>Luminosité actuelle</h2>');
  container.getElement().append('<input type="text" placeholder="?" id="light" readonly/>');
});

layout.init();

resetRoom();
setInterval(update, 3000);
