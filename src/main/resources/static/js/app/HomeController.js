
var module = angular.module('home.controllers', []);
module.controller("HomeController", [ "$scope", "HomeService",
		function($scope, HomeService) {
	
			$scope.editinguser = false;
			$scope.editinggasstation = false;
			
			
			$scope.gasstationreportdisabled = true;
			$scope.dieselreportdisabled = true;
			$scope.superreportdisabled = true;
			$scope.superplusreportdisabled = true;
			$scope.gasreportdisabled = true;
			$scope.methanereportdisabled = true;

			$scope.tmppricereport = {
					userId : null,
					reportingStation : null,
					dieselPrice : "-1.0",
					superPrice : "-1.0",
					superPlusPrice : "-1.0",
					gasPrice: "-1.0",
					methanePrice: "-1.0"
			}

			$scope.idpw = {
				user: null,
				pw: null
			};
	
			$scope.userDto = {
				userId : null,
				userName : null,
				password: null,
				email: null,
				reputation: 0,
				admin: false
			};

			$scope.gasStationDto = {
				gasStationId : null,
				gasStationName : null,
				gasStationAddress : null,
				"hasDiesel": false,
				"hasSuper": false,
				"hasSuperPlus": false,
				"hasGas": false,
				"hasMethane": false,
				carSharing: null,
				dieselPrice: -1.0,
				superPrice: -1.0,
				superPlusPrice: -1.0,
				gasPrice: -1.0,
				methanePrice: -1.0,
				reportUser: -1,
				reportTimestamp: null
			};
							
			$scope.searchParameters = {
					myLat: null,
					myLon: null,
					gasolineType : null,
					carSharing : null
			}
			
			
			$scope.saveGasStation = function() {
				
				if ($scope.editinggasstation) {
					$scope.editinggasstation = false;
					HomeService.saveGasStation($scope.gasStationDto).then(function() {
						HomeService.getAllGasStations().then(function(value) {
							$scope.allGasStations= value.data;
						}, function(reason) {
							console.log("error occured");
						}, function(value) {
							console.log("no callback");
						});

				});
				}
				

				
				else {
					

					if (!$scope.gasStationDto.carSharing) {
						$scope.gasStationDto.carSharing = 'null';
					}
					
				HomeService.saveGasStation($scope.gasStationDto).then(function() {
					HomeService.getAllGasStations().then(function(value) {
						$scope.allGasStations= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					
					
						
					
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
				
				
				}
				
				$scope.gasStationDto = {
						gasStationId : null,
						gasStationName : null,
						gasStationAddress : null,
						"hasDiesel": false,
						"hasSuper": false,
						"hasSuperPlus": false,
						"hasGas": false,
						"methanePrice": false,
						carSharing: null,
						dieselPrice: -1.0,
						superPrice: -1.0,
						superPlusPrice: -1.0,
						gasPrice: -1.0,
						methanePrice: -1.0,
						reportUser: -1,
						reportTimestamp: null
					};
			}

			$scope.updateUser = function() {

				HomeService.saveUser($scope.userDto).then(function() {
					$scope.editinguser = userDto.userId;
					HomeService.getAllUsers().then(function(value) {
						$scope.allUsers= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					$scope.userDto = {
						userId : null,
						userName : null,
						password : null,
						email : null,
						reputation: 0,
						admin: false
					};
					if ($scope.editinguser) {
						$scope.editinguser = false;
					}
				});
				window.location.href = '/';
			}

			$scope.saveUser = function() {
				
				
				HomeService.saveUser($scope.userDto).then(function() {
					HomeService.getAllUsers().then(function(value) {
						$scope.allUsers= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					$scope.userDto = {
						userId : null,
						userName : null,
						password : null,
						email : null,
						reputation: 0
					};
					
					if ($scope.editinguser) {
						$scope.editinguser = false;
					}
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
			}
			
			
			$scope.resetGasStationForm = function() {
				
				if ( $scope.editinggasstation ) {
					$scope.editinggasstation = false;
				}

				
				$scope.gasStationDto = {
						gasStationId : null,
						gasStationName : null,
						gasStationAddress : null,
						"hasDiesel": false,
						"hasSuper": false,
						"hasSuperPlus": false,
						"hasGas": false,
						"methanePrice": false,
						dieselPrice: -1.0,
						superPrice: -1.0,
						superPlusPrice: -1.0,
						gasPrice: -1.0,
						methanePrice: -1.0,
						reportUser: -1,
						reportTimestamp: null
					};
					
				
			}
			
			$scope.resetGasStationSearchForm = function() {
				
				$scope.searchParameters = {
						myLat: null,
						myLon: null,
						gasolineType : null,
						carSharing : null,
				}
				
				$scope.formSearchGasStations.$setPristine(); //reset Form

			}
			
			
			$scope.resetAddPriceReportForm = function() {
				
				
				$scope.gasstationreportdisabled = true;
				$scope.dieselreportdisabled = true;
				$scope.superreportdisabled = true;
				$scope.superplusreportdisabled = true;
				$scope.gasreportdisabled = true;
				$scope.methanereportdisabled = true;

				$scope.tmppricereport = {
						userId : null,
						reportingStation : null,
						dieselPrice : "-1.0",
						superPrice : "-1.0",
						superPlusPrice : "-1.0",
						gasPrice: "-1.0",
						methanePrice: "-1.0"
				}
				
				
				$scope.formAddPriceReport.$setPristine();
			}
			
			
			$scope.reset = function(){
				
				if ($scope.editinguser) {
					$scope.editinguser = null;
				}
				
				$scope.userDto = {
						userId : null,
						userName : null,
						password : null,
						email : null,
						reputation: 0,
						admin: false
					};
					
				
				$scope.myForm.$setPristine(); //reset Form
				
				
		    }
			
			$scope.initGasStationList = function() {
				
				HomeService.getAllGasStations().then(function(value) {
					$scope.allGasStations= value.data;
				});
			}
			
			
			$scope.initUserList = function() {
				
				
				HomeService.getAllUsers().then(function(value) {
					$scope.allUsers= value.data;
				});
				
			}
			
			$scope.deleteUser = function(id) {
				
				HomeService.deleteUser(id).then(function() {
					HomeService.getAllUsers().then(function(value) {
						$scope.allUsers= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					$scope.userDto = {
						userId : null,
						userName : null,
						password : null,
						email : null,
						admin: false
					};
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
				
			}
			
			
			$scope.editUser = function(id) {
				
				$scope.editinguser= id;
				
				HomeService.getUserById(id).then(function(value) {
					$scope.userDto=value.data;
				});
				
				
				
				
			}
			
			$scope.deleteGasStation = function(id) {
				
				HomeService.deleteGasStation(id).then(function() {
					HomeService.getAllGasStations().then(function(value) {
						$scope.allGasStations= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					$scope.userDto = {
						userId : null,
						userName : null,
						password : null,
						email : null
					};
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
				
			}

			
			$scope.editGasStation = function(id) {
				
				

				$scope.editinggasstation = id;

				HomeService.getGasStationById(id).then(function(value) {
					$scope.gasStationDto=value.data;
				});
				
				
				
				
			}
			
			$scope.elementVisibility = function (id, visible) {
				var f1 = document.getElementById(id);
					if (f1) {
						if (visible) {
							f1.style.display = "block";
						} else {
							f1.style.display = "none";
						}
					}
			}

			$scope.selectGasStationForReport = function(id) {
				
				if (sessionStorage.getItem('username')) {
					var f2 = document.getElementById('reportAdded');
					if (f2) {
						f2.innerHTML = "";
					}
					$scope.elementVisibility('addReport', true);
					
					tmpGasStation = HomeService.getGasStationById(id).then(function(value) {
						
					
						$scope.tmppricereport.reportingStation = id;
						$scope.gasstationreportdisabled = false;

						if (sessionStorage.getItem('userid')) { 
							$scope.tmppricereport.userId = sessionStorage.getItem('userid');
						}
						if (value.data.hasDiesel) { 
							$scope.dieselreportdisabled = false; 
							$scope.tmppricereport.dieselPrice = "";
							$scope.elementVisibility('diesel', true); 
						} else {
							$scope.elementVisibility('diesel', false);
						}
						if (value.data.hasSuper) { 
							$scope.superreportdisabled = false; 
							$scope.tmppricereport.superPrice = "";
							$scope.elementVisibility('super', true); 
						} else {
							$scope.elementVisibility('super', false);
						}
						if (value.data.hasSuperPlus) { 
							$scope.superplusreportdisabled = false; 
							$scope.tmppricereport.superPlusPrice = ""; 
							$scope.elementVisibility('superPlus', true);
						} else {
							$scope.elementVisibility('superPlus', false);
						}
						if (value.data.hasGas) { 
							$scope.gasreportdisabled = false; 
							$scope.tmppricereport.gasPrice = ""; 
							$scope.elementVisibility('gas', true);
						} else {
							$scope.elementVisibility('gas', false);
						}
						if (value.data.hasMethane) { 
							$scope.methanereportdisabled = false; 
							$scope.tmppricereport.methanePrice = ""; 
							$scope.elementVisibility('methane', true);
						} else {
							$scope.elementVisibility('methane', false);
						}

						
					});
				} else {
					window.location.href = '/login';
				}
				
			}

			$scope.login = function() {
				HomeService.login($scope.idpw).then(function(value) {
							$scope.loginResult = value.data;
							if (window.writeLoginResult) {
								window.writeLoginResult($scope.loginResult);
							}
				});

			}

			$scope.searchGasStations = function() {
				
				if (window.clearMap) {
					window.clearMap();
				}
				
				
				if (!$scope.searchParameters.gasolineType) $scope.searchParameters.gasolineType = "null";
				if (!$scope.searchParameters.carSharing) $scope.searchParameters.carSharing = "null";

				
				

				
				if ($scope.searchParameters.myLat && $scope.searchParameters.myLon) {
						
						HomeService.getGasStationsWithCoordinates($scope.searchParameters.myLat, $scope.searchParameters.myLon, $scope.searchParameters.gasolineType, $scope.searchParameters.carSharing).then(function(value) {
							$scope.searchGasStationResults = value.data;
							
						});
					
				}
				
				else if ($scope.searchParameters.gasolineType) {
					
					
						HomeService.searchGasStationsWithoutCoordinates($scope.searchParameters.gasolineType).then(function(value) {
							$scope.searchGasStationResults = value.data;
						});
					
					
				}
				
				
			}
			
			
			$scope.addPriceReport = function() {
				
				
				
				//must find user
				HomeService.getUserById($scope.tmppricereport.userId).then(function(value) {
					
					
					$scope.gasStationDto = {
							gasStationId : null,
							gasStationName : null,
							gasStationAddress : null,
							"hasDiesel": false,
							"hasSuper": false,
							"hasSuperPlus": false,
							"hasGas": false,
							"hasMethane": false,
							carSharing: null,
							dieselPrice: -1.0,
							superPrice: -1.0,
							superPlusPrice: -1.0,
							gasPrice: -1.0,
							methanePrice: -1.0,
							reportUser: -1,
							reportTimestamp: null
						};
					
					
					HomeService.setReport($scope.tmppricereport.reportingStation, $scope.tmppricereport.dieselPrice, $scope.tmppricereport.superPrice, $scope.tmppricereport.superPlusPrice, $scope.tmppricereport.gasPrice, $scope.tmppricereport.methanePrice, $scope.tmppricereport.userId).then(function() {
						HomeService.getAllGasStations().then(function(value) {
							$scope.allGasStations= value.data;
							
							$scope.tmppricereport = {
									userId : null,
									reportingStation : null,
									dieselPrice : "-1.0",
									superPrice : "-1.0",
									superPlusPrice : "-1.0",
									gasPrice: "-1.0",
									methanePrice: "-1.0"
							}
						});
					});
				});

				var i;
				/*for (i = 0; i < $scope.searchGasStationResults.length; i++) {
  					if ($scope.searchGasStationResults[i]['gasStationId'] === $scope.tmppricereport.reportingStation) {
  						console.log($scope.searchGasStationResults[i]);
  					}
				}*/
				
				$scope.elementVisibility('addReport', false);

				var f2 = document.getElementById('reportAdded');
				if (f2) {
					f2.innerHTML = "Report added successfully";
				}
				
			}

			$scope.getUserById = function() {
				
				if (window.userid) {
					$scope.editinguser= window.userid;
				
					HomeService.getUserById(window.userid).then(function(value) {
						if (window.currentPage === 'map') {
							window.reputation = value.data.reputation;
							if (window.updateReputation) {
								updateReputation();
							}
						}
						if (window.updateData) {
							value.data.password = '';
							$scope.userDto=value.data;
							window.updateData(value.data);
						} 
					});
				}
			}

			$scope.addMarker = function(result) {
				var currentGasStation = {lat: result.lat, lng: result.lon};
				/*var infowindow = new google.maps.InfoWindow({
    				content: "<span> " + result.gasStationName + " </span>"
				});
				var marker = new google.maps.Marker({position: currentGasStation, map: map, title: result.gasStationName});
				google.maps.event.addListener(marker, 'click', function() {
  					infowindow.open(map,marker);
				});
				marker.setMap(window.map);
				window.markers.push(marker);
				*/
				window.addMarker(window.map, result.lat, result.lon, result.gasStationName)
				
				
				

			}
			
			$scope.signalRightPrice = function(gasStationId, pIndex, index) {
				
				
				
				HomeService.getGasStationById(gasStationId).then( function(value) {
					
					HomeService.getUserById(value.data.reportUser).then( function(value) {
						
						HomeService.increaseUserReputation(value.data.userId).then (function() {
							
							HomeService.getAllUsers().then(function(value) {
								$scope.allUsers = value.data;
							});
							
						});
						
					});
					
				});
				
				var elementId = "buttonthumbsup_" + index;
				
				angular.element(document.getElementById(elementId))[0].disabled = true;
				angular.element(document.getElementById(elementId))[0].style.opacity = "0.5";


			}
			
			$scope.signalWrongPrice = function(gasStationId, pIndex, index) {
				
				HomeService.getGasStationById(gasStationId).then( function(value) {
					
					HomeService.getUserById(value.data.reportUser).then( function(value) {
						
						HomeService.decreaseUserReputation(value.data.userId).then (function() {
							
							HomeService.getAllUsers().then(function(value) {
								$scope.allUsers = value.data;
							});
							
						});
						
					});
					
				});
				

				var elementId = "buttonthumbsdown_" + index;

				angular.element(document.getElementById(elementId))[0].disabled = true;
				angular.element(document.getElementById(elementId))[0].style.opacity = "0.5";

			}
			
			
			$scope.sortDiesel = function() {
				
				$scope.searchGasStationResults.sort((a, b) => (b.dieselPrice > a.dieselPrice) ? -1 : 0);
				while($scope.searchGasStationResults[0].dieselPrice < 0) {
					$scope.searchGasStationResults.push($scope.searchGasStationResults.splice(0, 1)[0]);
				}

			}
			
			$scope.sortSuper = function() {
				
				$scope.searchGasStationResults.sort((a, b) => (b.superPrice > a.superPrice) ? -1 : 0);
				while($scope.searchGasStationResults[0].superPrice < 0) {
					$scope.searchGasStationResults.push($scope.searchGasStationResults.splice(0, 1)[0]);
				}

			}

$scope.sortSuperPlus = function() {
	
	$scope.searchGasStationResults.sort((a, b) => (b.superPlusPrice > a.superPlusPrice) ? -1 : 0);
	while($scope.searchGasStationResults[0].superPlusPrice < 0) {
		$scope.searchGasStationResults.push($scope.searchGasStationResults.splice(0, 1)[0]);
	}

}

$scope.sortGas = function() {
	
	$scope.searchGasStationResults.sort((a, b) => (b.gasPrice > a.gasPrice) ? -1 : 0);
	while($scope.searchGasStationResults[0].gasPrice < 0) {
		$scope.searchGasStationResults.push($scope.searchGasStationResults.splice(0, 1)[0]);
	}

}

$scope.sortMethane = function() {
	
	$scope.searchGasStationResults.sort((a, b) => (b.methanePrice > a.methanePrice) ? -1 : 0);
	while($scope.searchGasStationResults[0].methanePrice < 0) {
		$scope.searchGasStationResults.push($scope.searchGasStationResults.splice(0, 1)[0]);
	}

}
			
			

			
		} ]);
