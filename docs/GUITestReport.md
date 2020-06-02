# GUI  Testing Documentation 

Authors:

Date:

Version:

# GUI testing

This part of the document reports about testing at the GUI level. Tests are end to end, so they should cover the Use Cases, and corresponding scenarios.

## Coverage of Scenarios and FR

```
<Complete this table (from IntegrationApiTestReport.md) with the column on the right. In the GUI Test column, report the name of the .py  file with the test case you created.>
```

### 

| Scenario ID | Functional Requirements covered | GUI Test(s) |
| ----------- | ------------------------------- | ----------- | 
| 1           | FR1, FR1.1                      |     TestUseCase1.py        |             
| 2           | FR1, FR1.1, FR1.3, FR1.4        |     TestUseCase2.py        |             
| 3           | FR1, FR1.2, FR1.3, FR1.4        |     TestUseCase3.py        |         
| 4           | FR3, FR3.1                      |     TestUseCase4.py        |             
| 5           | FR3, FR3.1                      |     TestUseCase5.py        |             
| 6           | FR3, FR3.2                      |     TestUseCase6.py        |             
| 7           | FR5, FR5.1                      |     TestUseCase7.py        |
| 8           | FR4, FR4.1, FR4.2, FR4.3, FR4.4, FR4.5|TestUseCase8.py       |
| 10, 10.1, 10.2| FR5, FR5.3                    |     TestUseCase10.py        |

# REST  API  Testing

This part of the document reports about testing the REST APIs of the back end. The REST APIs are implemented by classes in the Controller package of the back end. 
Tests should cover each function of classes in the Controller package

## Coverage of Controller methods

Report in this table the test cases defined to cover all methods in Controller classes 

We have used 3 classes for controller tests:
- it.polito.ezgas.controller.UserControllerTests
- it.polito.ezgas.controller.GasStationControllerTests
- it.polito.ezgas.controllertests.TestController  

where the first two use the MockMvc class to test the REST APIs against a controlled test database, 
and the third one uses the org.apache.http package and the Jackson library against the standard production database. 
For the sake of simplicity, the tests in the controller package are called by our general AllTestsSuite, 
while the TestController tests have to be run manually after renaming the /data/testController.mv.db database to /data/memo.mv.db 
and launching the server.

| class.method name | Functional Requirements covered |REST  API Test(s) | 
| ----------- | ------------------------------- | ----------- | 
|  TestController.test2SaveUser, UserControllerTests.testSaveUser | FR1.1 | /user/saveUser |     
|  TestController.test6DeleteUser, UserControllerTests.testDeleteUser | FR1.2 | /user/deleteUser/{userId} |
|  TestController.test1GetAllUsers, UserControllerTests.testGetAllUsers | FR1.3 | /user/getAllUsers |
|  TestController.test0GetUserById, UserControllerTests.testGetUserById | FR1.4 | /user/getUser/{userId} | 
|  TestController.test5Login, UserControllerTests.testLogin | FR2 | /user/login |         
|  TestController.test8SaveGasStation, GasStationControllerTests.test_saveGasStation | FR3.1 | /gasstation/saveGasStation |         
|  TestController.test9DeleteGasStation, GasStationControllerTests.test_deleteGasStation | FR3.2 | /gasstation/deleteGasStation/{gasStationId} |         
|  TestController.test7GetAllGasStations, GasStationControllerTests.test_getAllGasStations | FR3.3 | /gasstation/getAllGasStations |         
|  TestController.test6GetGasStationById, GasStationControllerTests.test_getGasStationById | FR4 | /gasstation/getGasStation/{gasStationId} |        
|  TestController.test11GetGasStationsByProximity, GasStationControllerTests.test_getGasStationsByProximity | FR4.1 | /gasstation/searchGasStationByProximity/{mLat}/{myLon} |    
|  TestController.test12GetGasStationsWithCoordinates, GasStationControllerTests.test_getGasStationsWithCoordinates | FR4.2 | /gasstation/getGasStationsWithCoordinates/{myLat}/{myLon}/{gasolineType}/{carSharing} |      
|  TestController.test10GetGasStationsByGasolineType, GasStationControllerTests.test_getGasStationsByGasolineType | FR4.5 | /gasstation//searchGasStationByGasolineType/{gasolinetype} |     
|  TestController.test13SetGasStationReport, GasStationControllerTests.test_setGasStationReport | FR5.1 | /gasstation/setGasStationReport/{gasStationId}/{dieselPrice}/{superPrice}/{superPlusPrice}/{gasPrice}/{methanePrice}/{userId} |
|  TestController.test3IncreaseUserReputation, TestController.test4DecreaseUserReputation, UserControllerTests.testIncreaseUserReputation, UserControllerTests.testDecreaseUserReputation,  | FR5.2 | /user/increaseUserReputation/{userId}, /user/decreaseUserReputation/{userId} |   
