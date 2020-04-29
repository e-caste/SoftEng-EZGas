	'use strict'

var demoApp = angular.module('demo', [ 'ui.bootstrap', 
	'home.controllers', 'home.services' ]);
demoApp.constant("CONSTANTS", {
	getUserByIdUrl : "/user/getUser/",
	getAllUsers : "/user/getAllUsers",
	saveUser : "/user/saveUser",
	deleteUser : "/user/deleteUser/",
	increaseUserReputation: "/user/increaseUserReputation/",
	decreaseUserReputation: "/user/decreaseUserReputation/",
	getGasStationByIdUrl : "/gasstation/getGasStation/",
	getAllGasStations : "/gasstation/getAllGasStations",
	getGasStationByProximityUrl : "/gasstation/searchGasStationByProximity/",
	saveGasStation : "/gasstation/saveGasStation",
	deleteGasStation : "/gasstation/deleteGasStation/",
	searchGasStationsByNeighborhood : "/gasstation/searchGasStationByNeighborhood/",
	searchGasStationsByGasolineType : "/gasstation/searchGasStationByGasolineType/",
	getGasStationsWithCoordinates: "/gasstation/getGasStationsWithCoordinates/",
	getGasStationsWithoutCoordinates: "/gasstation/getGasStationsWithoutCoordinates/",
	setReport : "/gasstation/setGasStationReport/",
	login: "/user/login/"
});