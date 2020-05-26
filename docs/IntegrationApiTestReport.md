# Integration and API Test Documentation

Authors:

Date:

Version:

# Contents

- [Dependency graph](#dependency graph)

- [Integration approach](#integration)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph 

     <report the here the dependency graph of the classes in it/polito/Ezgas, using plantuml>
     
# Integration approach

    <Write here the integration sequence you adopted, in general terms (top down, bottom up, mixed) and as sequence
    (ex: step1: class A, step 2: class A+B, step 3: class A+B+C, etc)> 
    <The last integration step corresponds to API testing at level of Service package>
    <Tests at level of Controller package will be done later>



#  Tests

   <define below a table for each integration step. For each integration step report the group of classes under test, and the names of
     JUnit test cases applied to them>

## Step 1
| Classes  | JUnit test cases |
|--|--|
|||


## Step 2
| Classes  | JUnit test cases |
|--|--|
|||


## Step n API Tests

   <The last integration step  should correspond to API testing, or tests applied to all classes implementing the APIs defined in the Service package>

| Classes  | JUnit test cases |
|--|--|
|||




# Scenarios


<If needed, define here additional scenarios for the application. Scenarios should be named
 referring the UC they detail>

## Scenario UCx.y

| Scenario |  name |
| ------------- |:-------------:| 
|  Precondition     |  |
|  Post condition     |   |
| Step#        | Description  |
|  1     |  ... |  
|  2     |  ... |



# Coverage of Scenarios and FR


<Report in the following table the coverage of  scenarios (from official requirements and from above) vs FR. 
Report also for each of the scenarios the (one or more) API JUnit tests that cover it. >

| Scenario ID | Functional Requirements covered | JUnit  Test(s) | 
| ----------- | ------------------------------- | ----------- | 
|  UC1, UC2, UC3 | FR1                             |  testUser(), testUserDto()  |
|  UC1, UC2   | FR1.1                           |      testSaveUser(), testFindById() , testSave(), testConvertDtoToEntity(), testConvertEntityToDto()      |             
|  UC3         | FR1.2                           |      testDeleteUser(), testFindById(), testDelete()      |             
| ...         | FR1.3                           |     testGetAllUsers(),  testFindAll(), testConvertEntityToDto()       |             
| ...         | FR1.4                           |  testGetUserById(), testFindById(), testConvertEntityToDto()         |             
| ...         | FR2                             |   testLoginDto(), testIdPw(), testFindByEmail(), testConvertEntityToLoginDto()  |             
| UC4, UC5, UC6  | FR3             |       testGasStation(), testGasStationDto()|             
|UC4, UC5|FR3.1|test_saveGasStation_existing(), test_saveGasStation_invalidGPS(), test_saveGasStation_notExisting(), test_saveGasStation_nullPriceReportDtos()test_findById(), testConvertDtoToEntity(), testConvertEntityToDto(), test_save_existing(), test_save_notExisting() |
|UC6|FR3.2|test_deleteGasStation_existing(), test_deleteGasStation_notExisting(), test_findById(), test_delete_existing(), test_delete_notExisting()|
||FR3.3|test_getAllGasStations(), test_findAll(), testConvertEntityToDto()|
|  UC8         | FR4             |       testGasStation(), testGasStationDto()|           
|UC8|FR4.1, FR4.2|test_getGasStationsByProximity_invalidGPS(), test_findAll(), test_distance_far(), test_distance_furthest(), test_distance_near(), test_distance_nearest(), testConvertEntityToDto()|
|UC8|FR4.5|test_getGasStationsWithCoordinates_existing(), test_getGasStationsWithCoordinates_invalidGasType(), test_getGasStationsWithCoordinates_invalidGPS(), test_getGasStationsWithCoordinates_notExisting(), test_getGasStationsWithoutCoordinates_existing(), test_getGasStationsWithoutCoordinates_invalidGasType(), test_getGasStationByCarSharing_existing(), test_getGasStationByCarSharing_notExisting(), test_getGasStationsByGasolineType_InvalidGasType(), test_getGasStationsByGasolineType_validGasType()|
|UC7, UC9, UC10|FR5|testPriceReport(), testPriceReportDto()|
|UC7|FR5.1|test_findOne(), test_findById(), test_setReport(), test_setReport_invalidGasStation(), test_setReport_invalidPrice(), test_setReport_invalidUser()|
||FR5.2|test_reportDependability_notObsolescent(), test_reportDependability_obsolescent(), test_reportDependability_obsolescent_worstUser(), test_reportDependability_sameDay_perfectUser(), test_reportDependability_sameDay_worstUser()|
|UC10|FR5.3|testIncreaseUserReputation(), testDecreaseUserReputation(), testUser()|



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|          NFR5              |      test_distance_*(), testPriceReport(), testPriceReportDto(), test_getGasStationsWithCoordinates_*(), test_setReport*()|


