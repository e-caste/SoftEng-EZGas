# Design Document 


Authors: 

Date:

Version:


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
package "Backend" {

}

package "Frontend" {

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
package "Frontend" {

    package "it.polito.ezgas.resources.views" {

    }


package "it.polito.ezgas.resources.controller" {

    }


package "it.polito.ezgas.resources.styles" {

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
package "Backend" {

package "it.polito.ezgas.service"  as ps {
   interface "GasStationService"
   interface "UserService"
} 


package "it.polito.ezgas.controller" {

}

package "it.polito.ezgas.converter" {

}

package "it.polito.ezgas.dto" {

}

package "it.polito.ezgas.entity" {

}

package "it.polito.ezgas.repository" {

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

    class BootEZGasApplication {
        + main()
        + setupWithData()
    }
    
    package "it.polito.ezgas.service" {
       interface "GasStationService" as GSS
       interface "UserService" as US

        package "it.polito.ezgas.service.impl" {
            class GasStationServiceImpl implements GSS {
                + getGasStationById()
                + saveGasStation()
                + getAllGasStations()
                + deleteGasStationById()
                + getGasStationsByGasolineType()
                + getGasStationsByProximity()
                + getGasStationsByCarSharing()
                + getGasStationsWithCoordinates()
                + getGasStationsWithoutCoordinates()
                + setReport()
            }
            class UserServiceImpl implements US {
                + getUserById()
                + saveUser()
                + getAllUsers()
                + deleteUserById()
                + login()
                + increaseUserReputation()
                + decreaseUserReputation()
            }
        }
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
            + admin()
            + index()
            + map()
            + login()
            + update()
            + signup()
        }

        class GasStationController {
         - gasStationService
            + getGasStationById()
            + saveGasStation()
            + getAllGasStations()
            + deleteGasStationById()
            + getGasStationsByGasolineType()
            + getGasStationsByProximity()
            + getGasStationsByCarSharing()
            + getGasStationsWithCoordinates()
            + getGasStationsWithoutCoordinates()
            + setReport()
        }
        
        class UserController {
         - userService
            + getUserById()
            + saveUser()
            + getAllUsers()
            + deleteUserById()
            + login()
            + increaseUserReputation()
            + decreaseUserReputation()
        }
    }
    
    package "it.polito.ezgas.converter" {
        class GasStationConverter {
            + convertEntityToDto()
            + convertDtoToEntity()
        }
        class PriceReportConverter {
            + convertEntityToDto()
            + convertDtoToEntity()
        }
        class AnonymousUserConverter {
            + convertEntityToDto()
            + convertDtoToEntity()
        }
        class UserConverter {
            + convertEntityToDto()
            + convertDtoToEntity()
        }
        class AdministratorConverter {
            + convertEntityToDto()
            + convertDtoToEntity()
        }
    }
    
    package "it.polito.ezgas.dto" {
        class UserDto {
         - userId
         - userName
         - password
         - email
         - reputation
         - isAdmin {y/n}
            + getUserId()
            + setUserId()
            + getUserName()
            + setUserName()
            + getPassword()
            + setPassword()
            + getEmail()
            + setEmail()
            + getReputation()
            + setReputation()
            + getIsAdmin()
            + setIsAdmin()
        }

        class GasStationDto {
         ~ gasStationId
         ~ gasStationName
         ~ gasStationAddress
         ~ geoPoint

         ~ priceReport

         - carSharing
         ~ user

         ~ hasDiesel
         ~ hasGasoline
         ~ hasPremiumDiesel
         ~ hasPremiumGasoline
         ~ hasLPG
         ~ hasMethane

         ~ dieselPrice
         ~ superPrice
         ~ superPlusPrice
         ~ gasPrice
         ~ methanePrice
         
            + getGasStationId()
            + setGasStationId()
            + getGasStationName()
            + setGasStationName()
            + getGasStationAddress()
            + setGasStationAddress()
            + getGeoPoint()
            + setGeoPoint()

            + getPriceReport()
            + setPriceReport()

            + getCarSharing()
            + setCarSharing()
            + getUser()
            + setUser()

            + getHasDiesel()
            + setHasDiesel()
            + getHasSuper()
            + setHasSuper()
            + getHasSuperPlus()
            + setHasSuperPlus()
            + getHasGas()
            + setHasGas()
            + getHasMethane()
            + setHasMethane()

            + getDieselPrice()
            + setDieselPrice()
            + getSuperPrice()
            + setSuperPrice()
            + getSuperPlusPrice()
            + setSuperPlusPrice()
            + getGasPrice()
            + setGasPrice()
            + getMethanePrice()
            + setMethanePrice()
        }
        
        class PriceReportDto {
         ~ priceReportId
         ~ user
         ~ priceReportDependability
         
         ~ dieselPrice
         ~ superPrice
         ~ superPlusPrice
         ~ gasPrice
         ~ methanePrice
            + getPriceReportId()
            + setPriceReportId()
            + getUser()
            + setUser()
            + getPriceReportDependability()
            + setPriceReportDependability()

            + getDieselPrice()
            + setDieselPrice()
            + getSuperPrice()
            + setSuperPrice()
            + getSuperPlusPrice()
            + setSuperPlusPrice()
            + getGasPrice()
            + setGasPrice()
            + getMethanePrice()
            + setMethanePrice()
        }

        class LoginDto {
         ~ userId
         ~ userName
         ~ token
         ~ email
         ~ reputation
         ~ isAdmin {y/n}
            + getUserId()
            + setUserId()
            + getUserName()
            + setUserName()
            + getToken()
            + setToken()
            + getEmail()
            + setEmail()
            + getReputation()
            + setReputation()
            + getIsAdmin()
            + setIsAdmin()
        }

        class IdPw {
         - user
         - pw
            + getUser()
            + setUser()
            + getPw()
            + setPw()
        }

        class AnonymousUserDto {
         - userId
         - geoPoint
            + getUserId()
            + setUserId()
            + getGeoPoint()
            + setGeoPoint()
        }
        
        class AdministratorDto {
            + addGasStation()
            + editGasStation()
            + removeGasStation()
            + addUser()
            + editUser()
            + removeUser(UserDto)
            + banUser()
        }
    }
    
    package "it.polito.ezgas.entity" {
        class AnonymousUser {
         - userId
         - geoPoint
            + getUserId()
            + setUserId()
            + getGeoPoint()
            + setGeoPoint()
        }
        
        class User {
         - userName
         - password
         - email
         - reputation
         - isAdmin {y/n}
            + getUserName()
            + setUserName()
            + getPassword()
            + setPassword()
            + getEmail()
            + setEmail()
            + getIsAdmin()
            + setIsAdmin()

            + removeUser()
        }
        
        class Administrator {
            + addGasStation()
            + editGasStation()
            + removeGasStation()
            + addUser()
            + editUser()
            + removeUser(UserDto)
            + banUser()
        }
        
        class GasStation {
         - gasStationId
         - gasStationName
         - gasStationAddress
         - geoPoint

         - priceReport

         - carSharing
         - user

         - hasDiesel
         - hasGasoline
         - hasPremiumDiesel
         - hasPremiumGasoline
         - hasLPG
         - hasMethane

         - dieselPrice
         - superPrice
         - superPlusPrice
         - gasPrice
         - methanePrice
         
            + getGasStationId()
            + setGasStationId()
            + getGasStationName()
            + setGasStationName()
            + getGasStationAddress()
            + setGasStationAddress()
            + getGeoPoint()
            + setGeoPoint()

            + getPriceReport()
            + setPriceReport()

            + getCarSharing()
            + setCarSharing()
            + getUser()
            + setUser()

            + getHasDiesel()
            + setHasDiesel()
            + getHasSuper()
            + setHasSuper()
            + getHasSuperPlus()
            + setHasSuperPlus()
            + getHasGas()
            + setHasGas()
            + getHasMethane()
            + setHasMethane()

            + getDieselPrice()
            + setDieselPrice()
            + getSuperPrice()
            + setSuperPrice()
            + getSuperPlusPrice()
            + setSuperPlusPrice()
            + getGasPrice()
            + setGasPrice()
            + getMethanePrice()
            + setMethanePrice()
        }
        
        class GeoPoint {
         + latitude
         + longitude
        }
        
        class PriceReport {
         - priceReportId
         - user
         - priceReportDependability
         
         - dieselPrice
         - superPrice
         - superPlusPrice
         - gasPrice
         - methanePrice
            + getPriceReportId()
            + setPriceReportId()
            + getUser()
            + setUser()
            + getPriceReportDependability()
            + setPriceReportDependability()

            + getDieselPrice()
            + setDieselPrice()
            + getSuperPrice()
            + setSuperPrice()
            + getSuperPlusPrice()
            + setSuperPlusPrice()
            + getGasPrice()
            + setGasPrice()
            + getMethanePrice()
            + setMethanePrice()
        }

        GasStation  -- "0..1" PriceReport
        User "*" -- GeoPoint
        GasStation "*" -- GeoPoint
        User "*" -- PriceReport

        User -|> AnonymousUser : extends
        Administrator -|> User : extends
    }
    
    package "it.polito.ezgas.repository" {
        class GasStationRepository {
        }
        class PriceReportRepository {
        }
        class AnonymousUserRepository {
        }
        class UserRepository {
        }
        class AdministratorRepository {
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

'database DB TODO: show DB with database icon

UserController <-- UserDto: UserDto
UserController -> UserService: saveUser(UserDto)
UserService -> UserRepository: findByEmail(String)
UserRepository --> UserService: User
UserService -> UserRepository: saveUser(UserDto)
UserRepository -> UserConverter: convertDtoToEntity(UserDto)
UserConverter --> UserRepository: User
UserRepository -> DB: save(User)

DB --> UserRepository: Boolean
UserRepository --> UserService: Boolean
UserService --> UserController: UserDto

@enduml
```

## UC2 - Modify User Account
```plantuml
@startuml

UserController <-- UserDto: UserDto
UserController -> UserService: updateUser(UserDto)
UserService -> UserRepository: findByEmail(String)
UserRepository --> UserService: User
UserService -> UserRepository: updateUser(UserDto)
UserRepository -> UserConverter: convertDtoToEntity(UserDto)
UserConverter --> UserRepository: User
UserRepository -> DB: update(User)

DB --> UserRepository: Boolean
UserRepository --> UserService: Boolean
UserService --> UserController: UserDto

@enduml
```

## UC6 - Delete Gas Station
```plantuml
@startuml

GasStationController -> GasStationService: deleteGasStation(Integer)
GasStationService -> GasStationRepository: deleteGasStation(Integer)
GasStationRepository -> DB: delete(Integer)

DB --> GasStationController

@enduml
```

## UC7 - Report fuel price for a gas station
```plantuml
@startuml

' TODO: PriceReportDto does not exist at this time, but is used to represent the list of parameters

GasStationController <-- PriceReportDto: PriceReportDto
GasStationController -> GasStationService: setReport(PriceReportDto)
GasStationService -> PriceReportRepository: setReport(PriceReportDto)
PriceReportRepository -> PriceReportConverter: convertDtoToEntity(PriceReportDto)
PriceReportConverter --> PriceReportRepository: PriceReport
PriceReportRepository -> DB: save(PriceReport)

DB --> GasStationController

@enduml
```

## UC8 - Obtain price of fuel for gas stations in a certain geographic area

- (get user coordinates) - no method
- get gas station dtos by proximity
- for each gas station dto:
        getPriceReportDtos

```plantuml
@startuml

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


