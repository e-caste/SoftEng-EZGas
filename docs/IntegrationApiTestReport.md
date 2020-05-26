# Integration and API Test Documentation

Authors:Enrico Castelli s280124, Augusto Maria Guerriero s278018, Francesca Ponzetta s276535, Monica Rungi s276979

Date: 26/05/2020

Version: 1

# Contents

- [Dependency graph](#dependency graph)

- [Integration approach](#integration)

- [Tests](#tests)

- [Scenarios](#scenarios)

- [Coverage of scenarios and FR](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Dependency graph 

     ```plantuml
     @startuml
     skinparam backgroundcolor #FAEBDA
     skinparam package {
     BackgroundColor #FAEBDA/FFA563
     BorderColor #26424F
     }
     package "Backend"{
        package controller
        package Service.
        package repository
        package converter
        package Dto
        package entity
        
        controller -> Service.
        Service. -> repository
        Service. -> converter
        converter -> entity
        converter -> Dto
        repository -> entity
     }
     
     @enduml

     ```
     
# Integration approach

    <Write here the integration sequence you adopted, in general terms (top down, bottom up, mixed) and as sequence>
        <(ex: step1: class A, step 2: class A+B, step 3: class A+B+C, etc)> 
        <The last integration step corresponds to API testing at level of Service package>
        <Tests at level of Controller package will be done later>
        
    Bottom up approach.
    Step of testing:
    - Step 1: class Entity and Dto
    - Step 2: class Converter and Repository
    - Step 3: class ServiceImpl
    
    For implementation of ServiceImpl tests it is used a separate test Database.

#  Tests

   <define below a table for each integration step. For each integration step report the group of classes under test, and the names of
     JUnit test cases applied to them>

## Step 1
| Classes  | JUnit test cases |
|--------- | ---------------- |
| GasStation | Getters and Setters |
| User | Getters and Setters |
| PriceReport | Getters and Setters |
| GasStationDto | Getters and Setters |
| IdPw | Getters and Setters |
| LoginDto | Getters and Setters |
| PriceReportDto | Getters and Setters |
| UserDto | Getters and Setters |


## Step 2
| Classes  | JUnit test cases |
| -------- | ---------------- |
| GasStationRepository | test_findById |
|                      | test_findByCarSharing |
|                      | test_findAll |
|                      | test_save_existing |
|                      | test_save_notExisting |
|                      | test_delete_existing |
|                      | test_delete_notExisting |
|                      | test_findOne |
| UserRepository | selectAll |
|                | selectById |
|                | testFindById |
|                | testFindByEmail |
|                | testFindAll |
|                | testSave |
|                | testDelete |
| GasStationConverter | testConvertEntityToDto |
|                     | testConvertDtoToEntity |
| UserConverter | testConvertEntityToDto |
|               | testConvertDtoToEntity |
|               | testConvertEntityToLoginDto |


## Step 3 API Tests

   <The last integration step  should correspond to API testing, or tests applied to all classes implementing the APIs defined in the Service package>

| Classes  | JUnit test cases |
| -------- | ---------------- |
| GasStationServiceimpl| test_getGasStationById_existing |
|                      | test_getGasStationById_notExisting |
|                      | test_getAllGasStations |
|                      | test_deleteGasStation_existing |
|                      | test_deleteGasStation_notExisting |
|                      | test_getGasStationsByGasolineType_InvalidGasType |
|                      | test_getGasStationsByGasolineType_validGasType |
|                      | test_distance_nearest |
|                      | test_distance_near |
|                      | test_distance_far |
|                      | test_distance_furthest |
|                      | test_reportDependability_obsolescent |
|                      | test_reportDependability_notObsolescent |
|                      | test_reportDependability_sameDay_perfectUser |
|                      | test_reportDependability_sameDay_worstUser |
|                      | test_reportDependability_obsolescent_worstUser |
|                      | test_getGasStationsByProximity_invalidGPS |
|                      | test_getGasStationsWithCoordinates_invalidGPS |
|                      | test_getGasStationsWithCoordinates_invalidGasType |
|                      | test_getGasStationsWithCoordinates_existing |
|                      | test_getGasStationsWithCoordinates_notExisting |
|                      | test_getGasStationsWithoutCoordinates_invalidGasType |
|                      | test_getGasStationsWithoutCoordinates_existing |
|                      | test_saveGasStation_invalidGPS |
|                      | test_saveGasStation_nullPriceReportDtos |
|                      | test_saveGasStation_existing |
|                      | test_saveGasStation_notExisting |
|                      | test_setReport_invalidPrice |
|                      | test_setReport_invalidGasStation |
|                      | test_setReport_invalidUser |
|                      | test_setReport |
|                      | test_getGasStationByCarSharing_existing |
|                      | test_getGasStationByCarSharing_notExisting |
| UserServiceimpl | testGetUserById |
|                 | testSaveUser |
|                 | testGetAllUsers |
|                 | testDeleteUser |
|                 | testLogin |
|                 | testIncreaseUserReputation |
|                 | testDecreaseUserReputation |

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
|  ..         | FRx                             |             |             
|  ..         | FRy                             |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             



# Coverage of Non Functional Requirements


<Report in the following table the coverage of the Non Functional Requirements of the application - only those that can be tested with automated testing frameworks.>


### 

| Non Functional Requirement | Test name |
| -------------------------- | --------- |
|                            |           |


