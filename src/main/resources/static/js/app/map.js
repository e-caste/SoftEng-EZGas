var map;
var markers = [];
var geolocation = {
	lat : null,
	lng : null
};
var options = {
	enableHighAccuracy : true,
	timeout : 5000,
	maximumAge : 0
};

function success(pos) {
	bias(pos.coords.latitude, pos.coords.longitude, 0)
	withLocation();
}

function withLocation() {
	addData('lat', geolocation.lat);
	addData('lng', geolocation.lng);
	addData('lat', geolocation.lat);
	addData('lng', geolocation.lng);
	
	setMap(geolocation.lat, geolocation.lng);
	
	document.getElementById('add1').value = ' ';
	document.getElementById('findST').click();
}

function resetMap(lat,lng) {
	if (window.map) {
		window.map.remove();
	}
	setMap(lat,lng);
}
function setMap(lat,lng) {
	window.map = L.map('map', {
		center : [lat, lng ],
		zoom : 15,
		scrollWheelZoom : false
	});
	L
			.tileLayer(
					'https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png',
					{
						attribution : '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
					}).addTo(map);
}

function error(err) {
	console.warn(`ERROR(${err.code}): ${err.message}`);
	resetMap(45.116177, 7.742615);
}

function centerMap() {
	var lat = document.getElementById('lat').value;
	var lng = document.getElementById('lng').value;
	resetMap(lat,lng);
}

function myFunction() {

	if (getCookie('mylat')) {
		window.coords = {};
		window.coords.latitude = getCookie('mylat');
		window.coords.longitude = getCookie('mylon');
		window.coords.accuracy = getCookie('myacc');
		
		bias(getCookie('mylat'), getCookie('mylon'), 0)

		setTimeout(function() {
			withLocation();
		}, 300);

	} else {
		navigator.geolocation.getCurrentPosition(success, error, options);
	}
}

function clearMap() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
	markers = [];
}

function initMap() {
	centerInEurope();
	myFunction();
}

function initOSMap(lat, lon) {
	myFunction();
}

function centerInEurope() {
	var europe = new google.maps.LatLng(48.499998, 23.3833318);
	map = new google.maps.Map(document.getElementById('map'), {
		zoom : 4,
		zoomControl : true,
		center : europe
	});
	map.setCenter(europe);
}

/*
 * addData('myLat',latitude); addData('myLon',longitude);
 * addData('myLat',latitude); addData('myLon',longitude);
 */
function addData(id, data) {
	var event = new Event('input', {
		bubbles : true,
		cancelable : true,
	});
	var element = document.getElementById(id);
	element.dispatchEvent(event);
	element.value = data;
}
function bias(lt, ln, acc) {
	geolocation = {
		lat : lt,
		lng : ln
	};
}

function geolocate() {
	if (getCookie('mylat')) {
		bias(parseFloat(getCookie('mylat')), parseFloat(getCookie('mylon')),
				parseFloat(getCookie('myacc')));
	} else if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(function(position) {
			setCurrentLocation(position.coords.latitude,
					position.coords.longitude, position.coords.accuracy);
			bias(position.coords.latitude, position.coords.longitude,
					position.coords.accuracy);
		});
	}
}

function addMarker(map, lat, lon, message) {
	L.marker([ lat, lon ]).addTo(map).bindPopup(
			'<center>' + message + '<\/center>');
}

function getAddressByLatLn(lat, lon) {
	$.get("https://photon.komoot.de/reverse?lat=" + lat + "&lon=" + lon
			+ "&limit=1&lang=en", function(data, status) {
		return getAddressFromData(data, 0);

	});
}

function reverseLocation(streetName, lat, lon) {
	
	$.get('https://photon.komoot.de/api/?q=' + streetName + '&lat=' + lat
			+ '&lon=' + lon + '&limit=2&lang=en', function(data, status) {
		 addData('lat', data['features'][0]['geometry']['coordinates'][1]);
		 addData('lng', data['features'][0]['geometry']['coordinates'][0]);
		 addData('lat', data['features'][0]['geometry']['coordinates'][1]);
		 addData('lng', data['features'][0]['geometry']['coordinates'][0]);
	});
}

function getAddressFromData(data, i) {
	var address;
	if (data['features'][i]['properties']['housenumber']) {
		address = data['features'][i]['properties']['street'] + ' ';
	} else {
		address = data['features'][i]['properties']['name'] + ' ';
	}
	if (data['features'][i]['properties']['housenumber']) {
		address += data['features'][i]['properties']['housenumber'] + ' ';
	}
	if (data['features'][i]['properties']['city']) {
		address += data['features'][i]['properties']['city'] + ' ';
	}
	address += data['features'][i]['properties']['state'] + ' ';
	address += data['features'][i]['properties']['country'] + ' ';

	return address;
}

$(function() {
	var getData = function(request, response) {
		$.getJSON("https://photon.komoot.de/api/?q=" + request.term + '&lat='
				+ geolocation.lat + '&lon=' + geolocation.lng
				+ '&limit=4&lang=en', function(data) {
			var i;
			var addrArray = "";
			for (i = 0; i < data['features'].length; i++) {
				if (i == 0) {
					addrArray += "[";
				}
				if (i != 0 && i != data['features'].length) {
					addrArray += " , ";
				}
				addrArray += "\"" + getAddressFromData(data, i) + "\"";
				if (i == data['features'].length - 1) {
					addrArray += "]";
				}

			}

			response($.parseJSON(addrArray));
		});
	};

	var selectItem = function(event, ui) {
		$("#autocomplete").val(ui.item.value);
		$("#autocomplete").val(ui.item.value);
		addData('add1', ui.item.value);
		addData('add1', ui.item.value);
		reverseLocation(ui.item.value, geolocation.lat, geolocation.lng);
		return true;
	}

	$("#autocomplete").autocomplete({
		source : getData,
		select : selectItem,
		minLength : 3,
	});
});
