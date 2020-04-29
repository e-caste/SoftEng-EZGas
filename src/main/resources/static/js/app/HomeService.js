


angular.module('home.services', []).factory('HomeService',
		[ "$http", "CONSTANTS", function($http, CONSTANTS) {
			var service = {};
			service.getUserById = function(userId) {
				var url = CONSTANTS.getUserByIdUrl + userId;
				return $http.get(url);
			}
			service.getAllUsers = function() {
				return $http.get(CONSTANTS.getAllUsers);
			}
			service.saveUser = function(userDto) {
				return $http.post(CONSTANTS.saveUser, userDto);
			}
			service.getGasStationById = function(gasStationId) {
				var url = CONSTANTS.getGasStationByIdUrl + gasStationId;
				return $http.get(url);
			}
			service.getGasStationByProximity = function(myLat,myLon) {
				var url = CONSTANTS.getGasStationByProximityUrl + myLat + '/' + myLon + '/';
				return $http.get(url);
			}
			service.getAllGasStations = function() {
				return $http.get(CONSTANTS.getAllGasStations);
			}
			service.saveGasStation = function(gasStationDto) {
				return $http.post(CONSTANTS.saveGasStation, gasStationDto);
			}
			service.deleteUser = function(id) {
				var url = CONSTANTS.deleteUser + id;
				return $http.delete(url);
			}
			service.deleteGasStation = function(id) {
				var url = CONSTANTS.deleteGasStation + id;
				return $http.delete(url);
			}
			service.searchGasStationsByNeighborhood = function(neighborhood) {
				var url = CONSTANTS.searchGasStationsByNeighborhood + neighborhood;
				return $http.get(url);
			}
			service.searchGasStationsByGasolineType = function(gasolinetype) {
				var url = CONSTANTS.searchGasStationsByGasolineType + gasolinetype;
				return $http.get(url);
			}
			service.setReport = function(gasStationId, dieselPrice, superPrice, superPlusPrice, gasPrice, methanePrice, userId) {
				var url = CONSTANTS.setReport + gasStationId + '/' + dieselPrice + '/' + superPrice + '/' + superPlusPrice + '/' + gasPrice + '/' + methanePrice + '/' + userId + '/';
				return $http.post(url);
			}			
			service.increaseUserReputation = function(userId) {
				var url = CONSTANTS.increaseUserReputation + '/' + userId;
				return $http.post(url);
			}
			service.decreaseUserReputation = function(userId) {
				var url = CONSTANTS.decreaseUserReputation + '/' + userId;
				return $http.post(url);
			}
			service.login = function(idpw) {
				var url = CONSTANTS.login;
				return $http.post(url, idpw);
			}
			
			service.getGasStationsWithCoordinates = function(myLat, myLon, gasolineType, carSharing) {
				var url = CONSTANTS.getGasStationsWithCoordinates + myLat + "/" + myLon + "/" + gasolineType + "/" + carSharing + "/";
				return $http.get(url);		
			}
			
			service.getGasStationsWithoutCoordinates = function(gasolineType, carSharing) {
				var url = CONSTANTS.getGasStationsWithoutCoordinates + gasolineType + "/" + carSharing + "/";
				return $http.get(url);		
			}
			return service;
		} ]);