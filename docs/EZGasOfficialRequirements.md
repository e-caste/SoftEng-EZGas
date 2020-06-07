# Requirements Document - EZ Gas 

Authors: Riccardo Coppola, Luca Ardito, Maurizio Morisio 

Date: 5 june 2020

Version: 2.0  
List of changes

| | |
|--|---|
|Issue12 | Added unit of measure for geo coordinates as NFR6| 
|Issue33 or CR4 | Modified UC7  |



# Contents

- [Abstract](#abstract)
- [Stakeholders](#stakeholders)
- [Context Diagram and interfaces](#context-diagram-and-interfaces)
	+ [Context Diagram](#context-diagram)
	+ [Interfaces](#interfaces) 
	
- [Stories and personas](#stories-and-personas)
- [Functional and non functional requirements](#functional-and-non-functional-requirements)
	+ [Functional Requirements](#functional-requirements)
	+ [Non functional requirements](#non-functional-requirements)
- [Use case diagram and use cases](#use-case-diagram-and-use-cases)
	+ [Use case diagram](#use-case-diagram)
	+ [Use cases](#use-cases)
    	+ [Relevant scenarios](#relevant-scenarios)
- [Glossary](#glossary)
- [System design](#system-design)
- [Deployment diagram](#deployment-diagram)


# Abstract
Each gas station in Italy is free to define the price of fuels (regular gasoline, premium gasoline, diesel, methane). Drivers are interested in finding as easily as possible the gas station in a specific area of the city, possibly with the cheapest price.

EZGas is a crowdsourcing service that allows users to
- collect prices of fuels in different gas stations
- locate gas stations in an area, along with the prices they practice.

EZGas is supported by a web application (accessible both via smartphone or PC)
# Stakeholders

| Stakeholder name  | Description | 
| ----------------- |:-----------|
|      Administrator             |   Manages the application, can insert or delete gas stations and users, can ban users       | 
| User | Uses the application to find information about prices of fuel, and contributes by signaling prices|
| Anonymous User | Uses the application to find information about prices of fuel|
| Owner | Funds the development and operation of the application, having in mind a two sided model. The application is meant to be used freely, with the goal of attracting the largest possible web traffic. The pay off is given by ads hosted on the web application using Ad Words and the Google Display Network|
| Open street maps | Provides maps and map services|

# Context Diagram and interfaces

## Context Diagram

```plantuml
top to bottom direction
actor Administrator as a
actor User as u
actor AnonymousUser as au
actor :Open Street Maps: as OSM
u -up-|> au
a -up-|> u
au -> (EZGas)
OSM -> (EZGas)
```


## Interfaces


| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------|:-----|
| Administrator | Web GUI | Screen keyboard on PC  |
| User, Anonymous User | Web GUI | Screen keyboard mouse on PC, touchscreen on smartphone|
|Open street maps| REST API v 0.6 as described in https://wiki.openstreetmap.org/wiki/API_v0.6| Internet link|

# Stories and personas

The following personas and stories are meant to cover different profiles of the User actor

Rebecca is 35, works as a sales person and for this reason she has to visit different customers every day. For work she drives the company car and gets the full reimbursement of petrol expenses. Rebecca has an inclination for punctuality and precision, and hates being late at her meetings. For this reason, when she needs to put gas in his car, her main interest is to find gas stations that are as close as possible to her position.

David is 45, works at the counter of a shop and has two children. He has to cover several kilometers each day to bring his children to school and take them back, and to go to the gym in the afternoon. Sometimes he has to cut on expenses, and hence his main interest is to find the cheapest gas stations where he can refuel his LPG city car.

Elena is 20, and is a student at the Polytechnic of Turin. She typically goes to the university or to hang out with her friends by bike, but sometimes - especially at night and in Winter - she can not help but rent a car sharing vehicle. In these cases, if possible, she takes the opportunity of refueling the vehicle in order to have bonus credit with the car sharing applications.

Emma is 35 and she has always had a lifelong passion for sport cars. She owns two personalized vintage cars that she personally takes care of, and with whom she goes for a ride every weekend. To keep her cars in good condition, she tries to use only high-performance petrol, and is annoyed that many gas stations often don't offer it.


# Functional and non functional requirements

## Functional Requirements


| ID        | Description  |
| ------------- |:-------------| 
|  FR1     |  Manage users|
| FR1.1    | Define a new user, or modify an existing user |
| FR1.2     | Delete a user |
|FR1.3     |    List all users   |
|FR1.4    |    Search a user   |
|  FR2     |  Manage rights. Authorize access to functions to specific actors according to access rights|
| FR3   | Manage gas stations |
| FR3.1    | Define a new gas station, or modify an existing gas station |
| FR3.2     | Delete a gas station |
|FR3.3     |    List all gas stations  |
| FR4    |  Search gas stations |
|FR4.1 | Retrieve gas stations within radius r of a given geo point |
|FR4.2 | Retrieve gas stations within radius r of a given address |
|FR4.3 | Show given set of gas stations, and their fuel prices on a given map |
|FR4.4 | Sort given set of gas stations according to fuel type price |
|FR4.5 | Filter out given set of gas stations according to fuel type and or car sharing option |
|FR5 | Manage fuel prices and trust |
|FR5.1 | Create a price list, attach it to user and gas station |
| FR5.2 | Update trust level of a price list for a gas station  |
| FR5.3 | Evaluate  price list for a gas station |


### Access right, actor vs function

| Function | Admin | User | Anonymous User |
| ------------- |:-------------|--|--|
| FR1.1| yes | only user X for user X| no|
| FR1.2| yes | only user X for user X| no |
| FR1.3| yes | no | no|
| FR2 | yes | no | no|
| FR3 | yes  | no|no|
|FR4  | yes  | yes | yes| 
|FR5.1 | yes | yes| no|
|FR5.2 |no  |no |no|
|FR5.3 |yes |yes|no|

## Non Functional Requirements
| ID        | Type        | Description  | Refers to |
| ------------- |:-------------:| :-----| -----:|
|  NFR1     | Usability | Application should be used with no specific training for the users | All FR |
|  NFR2     | Performance | All functions should complete in < 0.5 sec  | All FR |
|  NFR3     | Portability | The application should be accessed by Chrome (version 81 and more recent), and Safari (version 13 and more recent) (this covers around 80% of installed browsers); and from the operating systems where these browsers are available (Android, IoS, Windows, MacOS, Unix). As for devices, the application should be usable on smartphones (portrait) and PCs (landscape). | All FR |
|  NFR4     |                     Privacy                     | The data of one user should not be disclosed to other users. The identity of the user who signaled or assessed a price report should not be visible to other users. | All FR |
|  NFR5     | Localisation | Decimal numbers use . (dot) as decimal separator |All FR|
| NFR6      | Localisation | Unit of measure for geo coordinates is degrees, longitude range [-180 + 180[   latitude [-90 +90[ <br> Unit of measure for distance is km  | All FR, notably FR4|






# Use case diagram and use cases


## Use case diagram

```plantuml
top to bottom direction
actor Administrator as a
actor User as u
actor AnonymousUser as au
actor :Open Street Maps: as OSM
u -up-|> au
a -up-|> u
au --> (Obtain price of fuel)
(Obtain price of fuel) .> (Update trust level of price list) :include
OSM <-- (Obtain price of fuel)
u --> (Report  fuel price)
OSM <-- (Report  fuel price)
u --> (Evaluate price)
u --> (Manage user account)
a --> (Manage gas station)
```


```plantuml
(Manage user account) .> (Create user account) :include
(Manage user account) .> (Modify user account) :include
(Manage user account) .> (Delete user account) :include
(Manage gas station ) .> (Create gas station) :include
(Manage gas station) .> (Modify gas station) :include
(Manage gas station) .> (Delete gas station) :include
```
### Use case 1, UC1 - Create User Account

| Actors Involved        | User|
| ------------- |:-------------:|
|  Precondition     |           Account U does not exist                                                |
|  Post condition     |                Account U added in the system                 |
|  |                      U.trust_level = 0                       |
|  Nominal Scenario     |  New user creates a new account U and populates its fields.  |
|  Variants     | A user can create only one account, this is checked through the email (one email, one account at most). Administrator can create many |

### Use case 2, UC2 - Modify user account

| Actors Involved        |   User|
| ------------- |:-------------:|
|  Precondition     |                       Account U exists                       |
|  Post condition     |             -                                                 |
|  Nominal Scenario     | User U modifies one or more fields of account U |
|  Variants     | User U can modify only his / her account. Administrator can modify any account |

### Use case 3, UC3 - Delete user account

| Actors Involved        |  User |
| ------------- |:-------------:|
|  Precondition     |                       Account U exists                       |
|  Post condition     |              Account U deleted from the system               |
|  Nominal Scenario   |  User  selects an user account U to delete |
|  Variants     | User U can delete only his / her account. Administrator can delete any account |


### Use case 4, UC4 - Create Gas Station 

| Actors Involved        | Administrator |
| ------------- |:-------------:|
|  Precondition     |           Gas Station  G does not exist                                 |
|  Post condition     |                   Gas Station G is created                   |
|  Nominal Scenario     | The administrator of the system creates a new gas station G in the system; the administrator enters all fields of a gas station ; the application assigns a unique ID to the gas station |




### Use case 5, UC5 - Modify Gas Station information

| Actors Involved        | Administrator |
| ------------- |:-------------:|
|  Precondition     | Gas Station G exists |
|  Post condition     | - |
|  Nominal Scenario     | The administrator of the system selects a gas station G to modify; the administrator is prompted with the gas station properties and modifies them |
|  Variants     | - |



### Use case 6, UC6 - Delete Gas Station

| Actors Involved  |                        Administrator                         |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                     Gas Station G exists                     |
| Post condition   |           Gas Station G is deleted from the system           |
| Nominal Scenario | The administrator of the system selects a gas station G to delete; the administrator confirms the deletion decision once prompted by the system |
| Variants         |                              -                               |

### Use case 7, UC7 - Report fuel price for a gas station

| Actors Involved  |                             User                             |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |                     Gas station G exists                     |
|                  |              User U is registered in the system              |
|                  |              G has no attached price list                  |
| Post condition   |                  Price list P is created                   |
|                  |   P.time_tag is set to the current timestamp of the system   |
|                  |                     P is attached to G                  |
|                  |                     U is attached to P (needed later to update trust level of U)                 |
| Nominal Scenario | The user U selects a gas station G for which he/she wants to insert a price report; no price list is attached to G; the system prompts the user with the list of possible fuels provided by the gas station;      the user inserts the prices for the fuels; the price list is attached to G |
| Variants         |         a price list is already  attached to G, previously inserted by U2, <br>  U.trust_level >= U2.trust_level, the previous price list is overwritten                  |
| |   a price list is already  attached to G, previously inserted by U2, <br> U.trust_level < U2.trust_level, the previous price list is overwritten only if (today - P.time_tag ) > 4 days      |



### Use case 8, UC8 - Obtain price of fuel for gas stations in a certain geographic area

| Actors Involved  |                     Anonymous User                             |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |              -              |
| Post condition   |                              -                               |
| Nominal Scenario | The anonymous user AU selects a geo point GP and a radius r (default radius is 5km); the system prompts all gas stations within r from GP, with their prices for all available fuels. If a price for a fuel is missing "NA" is reported. The system shows also, for each gas station, the trust level of the prices, computed as in UC 9 |
| Variants         | restrict to a certain fuel type. The system shows only prices for a certain fuel type|
| Variants         | restrict to car sharing. The system shows only gas stations having a deal with a certain car sharing company |
| Variants         | sort by price, for a fuel type. The system shows only gas stations for a certain fuel type, sorted by price, in ascending order |
| Variants         | sort by distance, for a fuel type. The system shows only gas stations for a certain fuel type, sorted by distance, in ascending order |



### Use case 9, UC9 - Update trust level of price list
| Actors Involved  |                     Anonymous User                             |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |     price list P is attached to user U         -              |
| Post condition   |      P.trust_level = 50 * (U.trust_level +5)/10 + 50 * obsolescence  |
| |  obsolescence = 0 if (today - P.time_tag) > 7 days |
| |     otherwise obsolescence = 1 - (today - P.time_tag)/7 |
| Nominal Scenario |  compute and update  trust level of price list P|


### Use case 10, UC10 - Evaluate price

| Actors Involved  |                             User                             |
| ---------------- | :----------------------------------------------------------: |
| Precondition     |              User U exists  and has valid account            |
|                  |          Gas Station G exists and has price list inserted by U2         |
| Post condition   |   U2.trust_level is modified                               |
| Nominal Scenario | The user U selects a gas station and the attached price list ; the user indicates that the price is correct; the system identifies the user U2 that provided the corresponding price report and increases his/her trust level |
| Variants         |   The user U selects a gas station and the attached price list ; the user indicates that the price is wrong; the system identifies the user U2 that provided the corresponding price report and decreases his/her trust level                     |

##### Scenario 10.1 

| Scenario |  price is correct |
| ------------- |:-------------:| 
|  Precondition     | User U exists and has valid account |
| | Gas Station G exists and has price list inserted by U2 |
|  Post condition     | U2.trust_level++  |
| Step#        | Description  |
|  1     |  U selects gas station G|  
|  2     |  U signals price for G is correct|
|  3    |  System searches the user U2 who did signal the prices for G|
|  4    |  System increases by 1 the trust level of U2 |

##### Scenario 10.2 
| Scenario |  price is wrong |
| ------------- |:-------------:| 
|  Precondition     | User U exists and has valid account |
| | Gas Station G exists and has price list inserted by U2 |
|  Post condition     | U2.trust_level--  |
| Step#        | Description  |
|  1     |  U selects gas station G|  
|  2     |  U signals price for G is wrong |
|  3    |  System searches the user U2 who did signal the prices for G|
|  4    |  System decreases  by 1 the trust level of U2 |


# Glossary
```plantuml
class EZGas
class User {
 account_name
 account_pwd
 email
 trust_level
}
class Administrator
note "trust_level ranges from -5  to 5" as N1
N1 .. User
class GasStation {
 ID
 name
 address
 brand
 hasDiesel
 hasGasoline
 hasPremiumDiesel
 hasPremiumGasoline
 hasLPG
 hasMethane
}
class GeoPoint {
 latitude
 longitude
}
class CarSharingCompany {
 name
}
class PriceList {
 time_tag
 dieselPrice
 gasolinePrice
 premiumDieselPrice
 premiumGasolinePrice
 LPGPrice
 methanePrice
 trust_level
}

note "trust_level ranges from 0 to 100 \ntime_tag is the time when the price list is created" as N2
N2 .. PriceList
Administrator -up-|> User
EZGas -- "*" User
EZGas -- "*" GasStation
GasStation "*" -- "0..1" CarSharingCompany
GasStation  -- "0..1" PriceList
User -- "*" PriceList
User "*" -- GeoPoint
GeoPoint -- GasStation
```

# System Design
Not really meaningful in this case.  Only software components are needed.


# Deployment Diagram
Client server model. The application runs on a server or virtual machine, any client accesses it through PC or smart phones.

```plantuml
artifact "EZGas Application" as ezgas
node "server" as s
node "PC client" as pc
node "Smartphone client" as phone
s -- ezgas
s -- "*" pc
s -- "*" phone
```