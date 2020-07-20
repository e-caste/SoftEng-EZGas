# Design Document 


Authors: Enrico Castelli s280124, Augusto Maria Guerriero s278018, Francesca Ponzetta s276535, Monica Rungi s276979

Date: 29/06/2020

Version: 3


# Contents

- [High level design](#package-diagram)
- [Low level design](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design must satisfy the Official Requirements document (see EZGas Official Requirements.md ). <br>
The design must comply with interfaces defined in package it.polito.ezgas.service (see folder ServicePackage ) <br>
UML diagrams **MUST** be written using plantuml notation.

# High level design 

The style selected is client - server. Clients can be smartphones, tablets, PCs.
The choice is to avoid any development client side. The clients will access the server using only a browser. 

The server has two components: the frontend, which is developed with web technologies (JavaScript, HTML, Css) and is in charge of collecting user inputs to send requests to the backend; the backend, which is developed using the Spring Framework and exposes API to the front-end.
Together, they implement a layered style: Presentation layer (front end), Application logic and data layer (back end). 
Together, they implement also an MVC pattern, with the V on the front end and the MC on the back end.



```plantuml
@startuml
skinparam backgroundcolor #FAEBDA
skinparam package {
BackgroundColor #FAEBDA/FFA563
BorderColor #26424F
}
package "Backend"{

}

package "Frontend"{

}


Frontend -> Backend
@enduml


```


## Front End

The Frontend component is made of: 

Views: the package contains the .html pages that are rendered on the browser and that provide the GUI to the user. 

Styles: the package contains .css style sheets that are used to render the GUI.

Controller: the package contains the JavaScript files that catch the user's inputs. Based on the user's inputs and on the status of the GUI widgets, the JavaScript controller creates REST API calls that are sent to the Java Controller implemented in the back-end.


```plantuml
@startuml
skinparam backgroundcolor #FAEBDA
skinparam package {
BackgroundColor #FAEBDA/FFA563
BorderColor #26424F
}

package "Frontend" #FAEBDA/FFA563{

    package "it.polito.ezgas.resources.views" #FAEBDA/FFA563{

    }


package "it.polito.ezgas.resources.controller" #FAEBDA/FFA563{

    }


package "it.polito.ezgas.resources.styles" #FAEBDA/FFA563{

    }



it.polito.ezgas.resources.styles -down-> it.polito.ezgas.resources.views

it.polito.ezgas.resources.views -right-> it.polito.ezgas.resources.controller


}
@enduml

```

## Back End

The backend  uses a MC style, combined with a layered style (application logic, data). 
The back end is implemented using the Spring framework for developing Java Entrerprise applications.

Spring was selected for its popularity and relative simplicity: persistency (M and data layer) and interactions are pre-implemented, the programmer needs only to add the specific parts.

See in the package diagram below the project structure of Spring.

For more information about the Spring design guidelines and naming conventions:  https://medium.com/the-resonant-web/spring-boot-2-0-project-structure-and-best-practices-part-2-7137bdcba7d3



```plantuml
@startuml
skinparam backgroundcolor #FAEBDA
skinparam package {
BackgroundColor #FAEBDA/FFA563
ArrowColor #26424F
BorderColor #26424F
}
skinparam class {
BackgroundColor #FAEBDA
ArrowColor #26424F
BorderColor #26424F
}
skinparam note {
BackgroundColor #FAEBDA
ArrowColor #26424F
BorderColor #26424F
}
package "Backend" #FAEBDA-FFA563{

package "it.polito.ezgas.service"  as ps{
   interface "GasStationService"
   interface "UserService"
} 


package "it.polito.ezgas.controller"{

}

package "it.polito.ezgas.converter"{

}

package "it.polito.ezgas.dto"{

}

package "it.polito.ezgas.entity"{

}

package "it.polito.ezgas.repository"{

}

    
}
note "see folder ServicePackage" as n
n -- ps
@enduml
```



The Spring framework implements the MC of the MVC pattern. The M is implemented in the packages Entity and Repository. The C is implemented in the packages Service, ServiceImpl and Controller. The packages DTO and Converter contain classes for translation services.



**Entity Package**

Each Model class should have a corresponding class in this package. Model classes contain the data that the application must handle.
The various models of the application are organised under the model package, their DTOs(data transfer objects) are present under the dto package.

In the Entity package all the Entities of the system are provided. Entities classes provide the model of the application, and represent all the data that the application must handle.




**Repository Package**

This package implements persistency for each Model class using an internal database. 

For each Entity class, a Repository class is created (in a 1:1 mapping) to allow the management of the database where the objects are stored. For Spring to be able to map the association at runtime, the Repository class associated to class "XClass" has to be exactly named "XClassRepository".

Extending class JpaRepository provides a lot of CRUD operations by inheritance. The programmer can also overload or modify them. 



**DTO package**

The DTO package contains all the DTO classes. DTO classes are used to transfer only the data that we need to share with the user interface and not the entire model object that we may have aggregated using several sub-objects and persisted in the database.

For each Entity class, a DTO class is created (in a 1:1 mapping).  For Spring the Dto class associated to class "XClass" must be called "XClassDto".  This allows Spring to find automatically the DTO class having the corresponding Entity class, and viceversa. 




**Converter Package**

The Converter Package contains all the Converter classes of the project.

For each Entity class, a Converter class is created (in a 1:1 mapping) to allow conversion from Entity class to DTO class and viceversa.

For Spring to be able to map the association at runtime, the Converter class associated to class "XClass" has to be exactly named "XClassConverter".




**Controller Package**

The controller package is in charge of handling the calls to the REST API that are generated by the user's interaction with the GUI. The Controller package contains methods in 1:1 correspondance to the REST API calls. Each Controller can be wired to a Service (related to a specific entity) and call its methods.
Services are in packages Service (interfaces of services) and ServiceImpl (classes that implement the interfaces)

The controller layer interacts with the service layer (packages Service and ServieImpl) 
 to get a job done whenever it receives a request from the view or api layer, when it does it should not have access to the model objects and should always exchange neutral DTOs.

The service layer never accepts a model as input and never ever returns one either. This is another best practice that Spring enforces to implement  a layered architecture.



**Service Package**


The service package provides interfaces, that collect the calls related to the management of a specific entity in the project.
The Java interfaces are already defined (see file ServicePackage.zip) and the low level design must comply with these interfaces.


**ServiceImpl Package**

Contains Service classes that implement the Service Interfaces in the Service package.


# Low level design

```plantuml
@startuml
skinparam backgroundcolor #FAEBDA
skinparam package {
BackgroundColor #FAEBDA-FFA563
ArrowColor #26424F
BorderColor #26424F
}
skinparam class {
BackgroundColor #FAEBDA/FFA563
ArrowColor #26424F
BorderColor #26424F
}
scale 1/3

    class BootEZGasApplication {
        + main(String): void
        + setupWithData(): void
    }

    package "it.polito.ezgas.entity" {
            class User {
             - userId: Integer
             - userName: String
             - password: String
             - email: String
             - reputation: Integer
             - admin {true/false}
            __
            ==
                + equals(User): Boolean
            == Getters and Setters ==
            }
            
            class GasStation {
            ..GS info..
             - gasStationId: Integer
             - gasStationName: String
             - gasStationAddress: String
             - lat: Double
             - lon: Double
            ..Report..
             - ReportUser: Integer
             - ReportTimestamp: String
             - ReportDependability: Double
            ..
             - carSharing: String
             - user: User
            ..Boolean info..
             - hasDiesel: Boolean
             - hasGasoline: Boolean
             - hasPremiumDiesel: Boolean
             - hasPremiumGasoline: Boolean
             - hasLPG: Boolean
             - hasMethane: Boolean
            ..Price info..
             - dieselPrice: Double
             - superPrice: Double
             - superPlusPrice: Double
             - gasPrice: Double
             - methanePrice: Double
            __
            == Getters and Setters ==
            ==
            + equals(GaStation): Boolean
            }
            
            class PriceReport {
             - priceReportId: Integer
             - user: User
            ..Type of Fuel..
             - dieselPrice: Double
             - superPrice: Double
             - superPlusPrice: Double
             - gasPrice: Double
             ~ methanePrice: Double
             
            __
            == Getters and Setters ==
            }
    
            GasStation  -- "0..1" PriceReport
            User "*" -- GeoPoint
            GasStation "*" -- GeoPoint
            User "*" -- PriceReport
           
        }
    
    package "it.polito.ezgas.service" {
       interface "GasStationService" as GSS {
       == Getters ==
           + getGasStationById(Integer): GasStationDto
           + getAllGasStations: List<GasStationDto>
           + getGasStationsByGasolineType(String): List<GasStationDto>
           + getGasStationsByProximity(double, double): List<GasStationDto> 
           + getGasStationsByProximity(double, double, int): List<GasStationDto>
           + getGasStationsWithCoordinates(double, double, int, String, String): List<GasStationDto> 
           + getGasStationsWithoutCoordinates(String, String): List<GasStationDto>
           + getGasStationsByCarSharing(String): List<GasStationDto> 
       == Setter ==
           + setReport(Integer, double, double, double, double, double, double, Integer): void
       == Save ==
           + saveGasStation(GasStationDto): GasStationDto
       == Delete ==
           + deleteGasStation(Integer): Boolean
       }
        
       interface "UserService" as US {
        == Getter ==
            + getUserById(Integer): UserDto
            + getAllUsers(): List<UserDto>
        == Save ==
            + saveUser(): UserDto
        == Delete ==
            + deleteUser(Integer): Boolean 
        ==
            + login(IdPw): LoginDto
        == Reputation ==
            + increaseUserReputation(Integer): Integer
            + decreaseUserReputation(Integer): Integer
        }
    }
    
    package "it.polito.ezgas.service.impl" {
        class GasStationServiceImpl implements GSS{
            + reportDependability(String, String, Integer): Double
            + distance(Double,Double,Double,Double): Double
        }
        class UserServiceImpl implements US
    }

    package "it.polito.ezgas.utils" {
        interface Constants {
         + GET_USER_BY_ID
         + GET_ALL_USERS
         + SAVE_USER
         + DELETE_USER
         + INCREASE_REPUTATION
         + DECREASE_REPUTATION
         + LOGIN
         + GET_GASSTATION_BY_ID
         + GET_ALL_GASSTATIONS
         + SAVE_GASSTATION
         + DELETE_GASSTATION
         + GET_GASSTATIONS_BY_NEIGHBORHOOD
         + GET_GASSTATIONS_BY_GASOLINETYPE
         + GET_GASSTATIONS_BY_PROXIMITY
         + GET_GASSTATIONS_WITH_COORDINATES
         + GET_GASSTATIONS_WITHOUT_COORDINATES
         + SET_GASSTATION_REPORT
        }
    }

    package "it.polito.ezgas.controller" {
        class HomeController {
            + admin(): String
            + index(): String
            + map(): String
            + login(): String
            + update(): String
            + signup(): String
        }

        class GasStationController {
         - gasStationService
        __
        == Getters ==
            + getGasStationById(Integer): GasStationDto 
            + getAllGasStations(): List<GasStationDto>
            + getGasStationsByGasolineType(String): List<GasStationDto>
            + getGasStationsByProximity(Double, Double, Integer): List<GasStationDto>
            + getGasStationsByCarSharing(): List<GasStationDto>
            + getGasStationsWithCoordinates(Double, Double, Integer, String, String): List<GasStationDto>
        == Setters ==
            + setGasStationReport(PriceReportDto): void
        == Save ==
            + saveGasStation(GasStationDto): void
        == Delete ==
            + deleteUser(Integer): void
            + deleteGasStation(Integer): Boolean
        }
        
        class UserController {
         - userService
        __
        == Getters ==
            + getUserById(Integer): UserDto
            + getAllUsers(): List<UserDto>
        == Save == 
            + saveUser(UserDto): UserDto
        == Delete ==
            + deleteUserById(Integer): Boolean
        == Login ==
            + login(IdPw): LoginDto
        == Reputation ==
            + increaseUserReputation(Integer): Integer
            + decreaseUserReputation(Integer): Integer
        }

        HomeController --"*" UserController
        HomeController --"*"GasStationController
    }
    
    package "it.polito.ezgas.converter" {
        class GasStationConverter {
            + convertEntityToDto(GasStation): GasStationDto
            + convertDtoToEntity(GasStationDto): GasStation
        }
        class PriceReportConverter {
            + convertEntityToDto(PriceReport): PriceReportDto
            + convertDtoToEntity(PriceReportDto): PriceReport
        }
        class UserConverter {
            + convertEntityToDto(User): UserDto
            + convertDtoToEntity(UserDto): User
            + convertEntityToLoginDto(User): LoginDto 
        }

        GasStationConverter --"0..1" PriceReportConverter
        UserConverter "*"-- PriceReportConverter

    }
    
    package "it.polito.ezgas.dto" {
        class UserDto {
         - userId: Integer
         - userName: String
         - password: String
         - email: String
         - reputation: Integer
         - admin {TRUE/FALSE}
        __
        == Getters and Setters ==
        ==
         + toString(): String
         + equals(UserDto): boolean
         + equalsIgnoreUserId(UserDto): boolean
        }

        class GasStationDto {
        ..GS info..
         ~ gasStationId: Integer
         ~ gasStationName: String
         ~ gasStationAddress: String
         ~ lat: Double
         ~ lon: Double
        ..
         ~ reportUser: Integer
         ~ reportTimestamp: String
         ~ reportDependability: Double
        ..
         - carSharing: String
         ~ userDto: UserDto
        ..boolean info..
         ~ hasDiesel: Boolean
         ~ hasGasoline: Boolean
         ~ hasPremiumDiesel: Boolean
         ~ hasPremiumGasoline: Boolean
         ~ hasLPG: Boolean
         ~ hasMethane: Boolean
        ..
         ~ dieselPrice: Double
         ~ premiumDieselPrice: Double
         ~ superPrice: Double
         ~ superPlusPrice: Double
         ~ gasPrice: Double
         ~ methanePrice: Double
        __
        == Getters and Setters ==
        ==
         +equals(GasStationDto): boolean
        }
        
        class PriceReportDto {
         ~ gasStationId: Integer
         ~ userId: Integer
         ..Types of Fuel..
         ~ dieselPrice: Double
         ~ superPrice: Double
         ~ superPlusPrice: Double
         ~ premiumDieselPrice: Double
         ~ gasPrice: Double
         ~ methanePrice: Double
        
        __
        == Getters and Setters ==
        }

        class LoginDto {
         ~ userId: Integer
         ~ userName: String
         ~ token: String
         ~ email: String
         ~ reputation: Integer
         ~ admin {TRUE/FALSE}
        __
        == Getters and Setters ==
        ==
         +equals(LoginDto): boolean
        }

        class IdPw {
         - user: String
         - pw: String
        __
        == Getters and Setters ==
        }

        GasStationDto -- "0..1" PriceReportDto
        UserDto "*"-- PriceReportDto
        UserDto "1"--"1" IdPw
        UserDto -- LoginDto
    }
    
    package "it.polito.ezgas.repository" {
        interface GasStationRepository {
            +findById(String): GasStation
            +findByCarSharing(String): GasStation
        }
        interface PriceReportRepository {
        }
        interface UserRepository {
            + findById(String): User
            + findByEmail(String): User
        }

          GasStationRepository --"0..1" PriceReportRepository
          UserRepository "*"-- PriceReportRepository
    }

        GasStation o-- GasStationRepository
        GasStationController o-- GasStationServiceImpl        
        GasStationServiceImpl o-- GasStationRepository
        GasStationServiceImpl o-- PriceReportRepository
        GasStationServiceImpl o-- GasStationDto
        GasStationServiceImpl o-- GasStation
        GasStationConverter o-- GasStationDto
        GasStationConverter o-- GasStation
        GasStationServiceImpl o-- GasStationDto
        GasStationRepository o-- GasStation 

        UserController o-- UserServiceImpl
        UserServiceImpl o-- UserRepository
        UserServiceImpl o-- PriceReportRepository
        UserServiceImpl o-- UserDto
        UserServiceImpl o-- LoginDto
        UserServiceImpl o-- User
        UserServiceImpl o-- IdPw
        UserRepository o-- User
        UserConverter o-- UserDto
        UserConverter o-- User

        PriceReportRepository o-- PriceReport
        PriceReportConverter o-- PriceReportDto
        PriceReportConverter o-- PriceReport

@enduml
```
# Test package
```plantuml
@startuml
skinparam backgroundcolor #FAEBDA
skinparam package {
BackgroundColor #FAEBDA-FFA563
ArrowColor #26424F
BorderColor #26424F
}
skinparam class {
BackgroundColor #FAEBDA/FFA563
ArrowColor #26424F
BorderColor #26424F
}
scale 1/3

package "it.polito.ezgas.test" {
        class EZGasApplicationTests {
            + contextLoads
        }
package service{
        class GasStationServiceimplTests {
            + setUpDatabase
            + tearDown
            + setUp
            + test_getGasStationById_existing
            + test_getGasStationById_notExisting
            + test_GetAllGasStations
            + test_DeleteGasStation_existing
            + test_DeleteGasStation_notExisting
            + test_getGasStationsByGasolineType_InvalidGasType
            + test_getGasStationsByGasolineType_validGasType
            + test_distance_nearest
            + test_distance_near
            + test_distance_far
            + test_distance_furthest
            + test_reportDependability_obsolescent
            + test_reportDependability_notObsolescent
            + test_reportDependability_sameDay_perfectUser
            + test_reportDependability_sameDay_worstUser
            + test_reportDependability_obsolescent_worstUser
            + test_getGasStationsByProximity_invalidGPS
            + test_getGasStationsWithCoordinates_invalidGasType
            + test_getGasStationsWithCoordinates_existing
            + test_getGasStationsWithCoordinates_notExisting
            + test_getGasStationsWithoutCoordinates_invalidGasType
            + test_getGasStationsWithoutCoordinates_existing
            + test_saveGasStation_invalidGPS
            + test_saveGasStation_negativePrices
            + test_saveGasStation_existing
            + test_saveGasStation_notExisting
            + test_setReport_invalidPrice
            + test_setReport_invalidGasStation
            + test_setReport_invalidUser
            + test_setReport
            + test_getGasStationByCarSharing_existing
            + test_getGasStationByCarSharing_notExisting
           
        }

        class UserServiceimplTests {
                    + setUpDatabase
                    + setUp
                    + tearDown
                    + testGetUserById
                    + testSaveUser
                    + testGetAllUsers
                    + testDeleteUser
                    + testLogin
                    + testIncreaseUserReputation
                    + testDecreaseUserReputation
                            
                }
}
package nodepencies{
        class GetterSetterTests {
            + testPriceReportDto
            + testIdPw
            + testLoginDto
            + testUserDto
            + testGasStationDto
            + testGasStation
            + testUser
            + testPriceReport
        }
}

package converter{
        class UserConverterTests {
           + setUp
           + testConvertEntityToDto
           + testConvertDtoToEntity
           + testConvertEntityToLoginDto
        }

        
        class GasStationConverterTests {
           + setUp
           + testConvertEntityToDto
           + testConvertDtoToEntity
        }
}

         class H2TestProfileJPAConfig {
           + dataSource(void): DataSource
        }

package controllertests{
        class TestController {
            + getResponseFromRequest
            + getJsonFromResponse
            + getMapper
            + test0GetUserById
            + test1GetAllUsers
            + test2SaveUser
            + test6DeleteUser
            + test3IncreaseUserReputation
            + test4DecreaseUserReputation
            + test5Login
            + test6GetGasStationById
            + test7GetAllGasStations
            + test8SaveGasStation
            + test9DeleteGasStation
            + test10GetGasStationsByGasolineType
            + test11GetGasStationsByProximity
            + test12GetGasStationsWithCoordinates
            + test13SetGasStationReport
        }
}
        package GUITests{
            class TestUseCase1
            class TestUseCase2
            class TestUseCase3
            class TestUseCase4
            class TestUseCase5
            class TestUseCase6
            class TestUseCase7
            class TestUseCase8
            class TestUseCase10
        }
        
        package Repository{
            class GasStationRepositoryTests{
                + setUpDatabase
                + tearDown
                + setUp
                + test_findByCarSharing
                + test_findAll
                + test_save_existing
                + test_save_notExisting
                + test_delete_existing
                + test_delete_notExisting
                + test_findById
            }
            class UserRepositoryTests{
                            + setUpDatabase
                            + tearDown
                            + setUp
                            + selectAll
                            + selectById
                            + testFindById
                            + testFindByEmail
                            + testFindAll
                            + testSave
                            + testDelete
                        }
        }
    package controller {
        class GasStationControllerTests{
            + setUpDatabase
            + tearDown
            + convertGasStationDtoToJSON
            + convertPriceReportDtoToJSON
            + separateTestsGraphically
            + test_getGasStationById
            + test_getAllGasStations
            + test_saveGasStation
            + test_deleteGasStation
            + test_getGasStationsByGasolineType
            + test_getGasStationsByProximity
            + test_getGasStationsWithCoordinates
            + test_setGasStationReport
        }
        class UserControllerTests{
            + setUp
            + setUpDatabase
            + tearDown
            + separateTestsGraphically
            + convertDtoToJSON
            + testGetUserById
            + testGetAllUsers
            + testSaveUser
            + testDeleteUser
            + testIncreaseUserReputation
            + testDecreaseUserReputation
            + testLogin
        }


}

@enduml
```




# Verification traceability matrix

| FR | EZGas | User  | Administrator | GeoPoint | GasStation | CarSharingCompany | PriceList | 
| :-------------: | :-------------: | :-----: | :-----: | :-----: | :-----: | :-----: | :-----: |
| FR1 |  | X | X |  |  |  |  | 
| FR1.1 |  | X | X |  |  |  |  |
| FR1.2 |  | X | X |  |  |  |  |
| FR1.3 |  |  | X |  |  |  |  |
| FR1.4 |  |  | X |  |  |  |  |
| FR2 | X |  |  |  |  |  |  |
| FR3 |  |  |  | X |  |  |  |
| FR3.1 |  |  | X |  |  |  |  |
| FR3.2 |  |  | X |  |  |  |  |
| FR3.3 |  |  | X |  |  |  |  |
| FR4 |  | X | X |  |  |  |  |
| FR4.1 | X |  |  |  |  |  |  |
| FR4.2 | X |  |  |  |  |  |  |
| FR4.3 | X |  |  |  |  |  |  |
| FR4.4 | X |  |  |  |  |  |  |
| FR4.5 | X |  |  |  |  |  |  |
| FR5 |  | X |  |  |  |  |  |
| FR5.1 |  | X |  |  |  |  |  |
| FR5.2 | X |  |  |  |  |  |  |
| FR5.3 |  | X |  |  |  |  |  |

# Verification sequence diagrams 

## UC1 - Create User Account
```plantuml
@startuml
skinparam backgroundcolor #FFFFEE
skinparam database {
BackgroundColor #FAEBDA-619196
ArrowColor #26424F
BorderColor #26424F
}
skinparam sequence{
    LifeLineBorderColor #619196
    ParticipantBorderColor #619196
    ParticipantBackgroundColor #B2D9EA-FFFFEE
    ArrowColor #619196
}
UserController <-- UserDto: UserDto
UserController -> UserService: saveUser(UserDto)
UserService -> UserRepository: findByEmail(String)
UserRepository --> UserService: User
UserService -> UserRepository: saveUser(UserDto)
UserRepository -> UserConverter: convertDtoToEntity(UserDto)
UserConverter --> UserRepository: User
database DB
UserRepository -> DB: save(User)

DB --> UserRepository: Boolean
UserRepository --> UserService: Boolean
UserService --> UserController: UserDto

@enduml
```

## UC2 - Modify User Account
```plantuml
@startuml
skinparam backgroundcolor #FFFFEE
skinparam database {
BackgroundColor #FAEBDA-619196
ArrowColor #26424F
BorderColor #26424F
}
skinparam sequence{
    LifeLineBorderColor #619196
    ParticipantBorderColor #619196
    ParticipantBackgroundColor #B2D9EA-FFFFEE
    ArrowColor #619196
}
UserController <-- UserDto: UserDto
UserController -> UserService: updateUser(UserDto)
UserService -> UserRepository: findByEmail(String)
UserRepository --> UserService: User
UserService -> UserRepository: updateUser(UserDto)
UserRepository -> UserConverter: convertDtoToEntity(UserDto)
UserConverter --> UserRepository: User
database DB
UserRepository -> DB: update(User)

DB --> UserRepository: Boolean
UserRepository --> UserService: Boolean
UserService --> UserController: UserDto

@enduml
```

## UC6 - Delete Gas Station
```plantuml
@startuml
skinparam backgroundcolor #FFFFEE
skinparam database {
BackgroundColor #FAEBDA-619196
ArrowColor #26424F
BorderColor #26424F
}
skinparam sequence{
    LifeLineBorderColor #619196
    ParticipantBorderColor #619196
    ParticipantBackgroundColor #B2D9EA-FFFFEE
    ArrowColor #619196
}
GasStationController -> GasStationService: deleteGasStation(Integer)
GasStationService -> GasStationRepository: deleteGasStation(Integer)
database DB
GasStationRepository -> DB: delete(Integer)

DB --> GasStationController

@enduml
```

## UC7 - Report fuel price for a gas station
```plantuml
@startuml
skinparam backgroundcolor #FFFFEE
skinparam database {
BackgroundColor #FAEBDA-619196
ArrowColor #26424F
BorderColor #26424F
}
skinparam sequence{
    LifeLineBorderColor #619196
    ParticipantBorderColor #619196
    ParticipantBackgroundColor #B2D9EA-FFFFEE
    ArrowColor #619196
}

GasStationController <-- PriceReportDto: PriceReportDto
GasStationController -> GasStationService: setReport(PriceReportDto)
GasStationService -> PriceReportRepository: setReport(PriceReportDto)
PriceReportRepository -> PriceReportConverter: convertDtoToEntity(PriceReportDto)
PriceReportConverter --> PriceReportRepository: PriceReport
database DB
PriceReportRepository -> DB: save(PriceReport)

DB --> GasStationController

@enduml
```

## UC8 - Obtain price of fuel for gas stations in a certain geographic area
```plantuml
@startuml
skinparam backgroundcolor #FFFFEE
skinparam database {
    BackgroundColor #FAEBDA-619196
    ArrowColor #26424F
    BorderColor #26424F
}
skinparam sequence{
    LifeLineBorderColor #619196
    ParticipantBorderColor #619196
    ParticipantBackgroundColor #B2D9EA-FFFFEE
    ArrowColor #619196
}
skinparam sequenceGroupBorderColor #26424F

' TODO: use GeoPoint instead of 2 doubles for coordinates

== Get Gas Stations from DB ==
GasStationController <-- GeoPointDto: GeoPointDto
GasStationController -> GasStationService: getGasStationsByProximity(GeoPointDto)
GasStationService -> GasStationRepository: getGasStationsByProximity(GeoPointDto)
GasStationRepository -> GeoPointConverter: convertDtoToEntity(GeoPointDto)
GeoPointConverter --> GasStationRepository: GeoPoint
database DB
GasStationRepository -> DB: getByProximity(GeoPoint)
DB --> GasStationRepository: List<GasStationDto>
GasStationRepository --> GasStationService: List<GasStationDto>
GasStationService --> GasStationController: List<GasStationDto>

' TODO: understand if we can stop here, or we need another specialized method to get the Map
== Get Price Reports ==
loop for each gas station dto
    GasStationService -> GasStationDto: getPriceReportDtos()
    GasStationDto --> GasStationService: List<PriceReportDto>
    GasStationService -> GasStationService: addLastPriceReportDtoToMap()
end
GasStationService --> GasStationController: Map<GasStationDto, PriceReportDto>

@enduml
```

## UC10 - Evaluate price
```plantuml
@startuml
skinparam backgroundcolor #FFFFEE
skinparam database {
    BackgroundColor #FAEBDA-619196
    ArrowColor #26424F
    BorderColor #26424F
}
skinparam sequence{
    LifeLineBorderColor #619196
    ParticipantBorderColor #619196
    ParticipantBackgroundColor #B2D9EA-FFFFEE
    ArrowColor #619196
}
skinparam sequenceGroupBorderColor #26424F

' TODO: use GeoPoint instead of 2 doubles for coordinates

== Get Gas Station from DB ==

GasStationController -> GasStationService: getGasStationById(gasStationId)
GasStationService -> GasStationRepository: getGasStationById(gasStationId)
database DB
GasStationRepository -> DB: getGasStationById(gasStationId)
GasStationRepository <-- DB: GasStationDto
GasStationService  <-- GasStationRepository: GasStationDto
GasStationController <-- GasStationService: GasStationDto
== Update User reputation ==
UserController --> UserService: increaseUserReputation(userId)
UserService --> UserRepository: increaseUserReputation(userId)
database DB
UserRepository -> DB: increaseUserReputation(userId)
UserRepository <-- DB: integer
UserService <-- UserRepository: integer
UserController <-- UserService: integer

@enduml
```


