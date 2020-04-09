# Requirements Document 

Authors: Enrico Castelli s280124, Augusto Maria Guerriero s278018, Francesca Ponzetta s276535, Monica Rungi s276979

Date: 09/04/2020

Version: 1

# Contents

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


# Stakeholders


| Stakeholder name  | Description | 
| ----- |:-----------:|
| Users | Use EZGas either actively, by updating gas prices, or passively, by checking gas prices other users have set up | 
| Developers | Add features to EZGas, fix bugs of EZGas, release the app, deploy and update any server-side software |
| Database | Contains all the prices that users upload to EZGas | 
| Maps | Provide an API to access gas station locations |

# Context Diagram and interfaces

## Context Diagram

```plantuml
actor User as U
actor Database as DB
actor Maps as M
U -- (EZGas)
DB -- (EZGas)
M -- (EZGas)
```

## Interfaces
| Actor | Logical Interface | Physical Interface  |
| ------------- |:-------------:| -----:|
| User  | GUI               | Touchscreen / screen, mouse and keyboard |
| Database | Internet Access | Server network cable |
| Maps | Internet Access | Server network cable |

# Stories and personas

Tom Honks is a truck driver always on the move. His company pays for gas, but he would like to start off on the right foot with the new boss. He is determined to find a way to reduce company costs to make a good impression and hopefully get a promotion to an office job, so he can stay more regularly at home with his family.
He finally finds the solution to his problem: EZGas, a crowd-sourced application that allows all the truck drivers in his company to determine at which gas station to stop.

Antonio is a man who has moved from Puglia to Piemonte for a job. Everytime he has to go on holiday to Puglia, he travels with his car. Moreover the car runs on natural gas, so it is really difficult to find a gas station suitable for his car. As the trip is really long, he would like to know where the gas stations are and where the prices are convenient, in order to save money. EZGas would be perfect for him, because, according to where he is, he could find the nearest gas station with the best prices.

Jared, 18 years old, just had his car bought as a present from his parents. He goes to high school, doesn't study a lot and his professors report that he doesn't behave well towards his classmates. Jared recently got to know about EZgas from one of his classmates and decided to download it to mess up the system by uploading wrong data. The developers of EZGas have thought about this chance, so Jared is left without the satisfaction of messing up the database.


# Functional and non functional requirements

## Functional Requirements

| ID        | Description  |
|:---------:|:------------:| 
|  FR1      | Show nearby world map |  
|  FR2      | Search for a place on the map |
|  FR2.1    | Filter search based on distance from user |
|  FR2.2    | Filter search based on lowest price |
|  FR2.3    | Filter search based on type of fuel |
|  FR3      | Show recently visited gas stations |
|  FR4      | Record updated gas station price to database |
|  FR4.1    | Perform trust-based check before updating price |
|  FR4.2    | Add new gas station to database if not present |
|  FR5      | Produce a graph of price fluctuations for a given station |
|  FR6      | Record gas station status |
|  FR7      | Create account |
|  FR7.1    | Login |
|  FR7.2    | Logout |

## Non Functional Requirements

| ID        | Type        | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     |  Portability | Application should run in a web browser, both mobile and desktop | All FR |
|  NFR2     | Performance | All functions should take less than 5 seconds when using 4G or 5G | All FR |
|  NFR3     | Performance | All functions should take less than 10 seconds when using 3G | All FR |
|  NFR4     | Performance | All functions should take less than 30 seconds when using 2G | All FR |
|  NFR5     | Localisation | Application should support EN and IT locales | All FR |
|  NFR6     | Usability | Application should be used with no previous training  | All FR |


# Use case diagram and use cases


## Use case diagram
```plantuml
left to right direction
actor User as U
actor Database as DB
actor Maps as M
rectangle EZGas {
    usecase "Show Map" as FR1
    usecase "Search place" as FR2
    usecase "Distance filter" as FR2.1
    usecase "Price filter" as FR2.2
    usecase "Fuel type filter" as FR2.3
    usecase "Show recently visited" as FR3
    usecase "Update price" as FR4
    usecase "Trust-based check" as FR4.1
    usecase "Add new gas station to DB" as FR4.2
    usecase "Show price graph" as FR5
    usecase "Update gas station status" as FR6
    usecase "Create account" as FR7
    usecase "Login" as FR7.1
    usecase "Logout" as FR7.2
}

U ----> FR1
U --> FR2
U --> FR3
U --> FR4
U --> FR5
U --> FR6
U --> FR7
U --> FR7.1
U --> FR7.2
FR1 ---> M
FR2 ----> M
FR2 <-. FR2.1 : extends
FR2 <-. FR2.2 : extends
FR2 <-. FR2.3 : extends
FR3 ---> DB
FR4 ---> DB
FR4 <-. FR4.1  : includes
FR4 <-. FR4.2 : includes
FR5 ----> DB
FR6 ---> DB
FR7 ---> DB
FR7.1 ---> DB
FR7.2 ---> DB
```
### Use case 1, UC1 - FR1 Show nearby world map
| Actors Involved        | User, Maps |
| ------------- |-------------| 
|  Precondition  | User U has installed EZGas app, Maps M are available on the Internet |  
|  Post condition | M displayed on U's screen |
|  Nominal Scenario | User opens application and sees Maps |
|  Variants | Maps not available, issue error |
| | Internet connection not available, issue warning |

### Use case 2, UC2 - FR2 Search for a place on the map
| Actors Involved        | User, Maps |
| ------------- |-------------| 
|  Precondition  | User U has installed EZGas app, Maps M are available on the Internet, Location L exists |  
|  Post condition | Location searched is displayed on U's screen |
|  Nominal Scenario | U searches for L and L is displayed on M |
|  Variants | Maps not available, issue error |
| | Internet connection not available, issue warning |
| | Location does not exist, issue warning |

### Use case 3, UC3 - FR2.1 Filter search based on distance from user
| Actors Involved        | User, Maps |
| ------------- |-------------| 
|  Precondition  | User U has installed EZGas app, Maps M are available on the Internet, User Location UL exists |  
|  Post condition | The nearest gas stations to UL are displayed on U's screen |
|  Nominal Scenario | U searches for nearest gas stations |
|  Variants | Maps not available, issue error |
| | Internet connection not available, issue warning |

### Use case 4, UC4 - FR2.2 Filter search based on lowest price
| Actors Involved        | User, Maps |
| ------------- |-------------| 
|  Precondition  | User U has installed EZGas app, Maps M are available on the Internet, User Location UL exists |  
|  Post condition | The gas stations with the lowest price in a default or user-selected range are displayed on U's screen |
|  Nominal Scenario | U searches for gas stations with lowest price |
|  Variants | Maps not available, issue error |
| | Internet connection not available, issue warning |

### Use case 5, UC5 - FR2.3 Filter search based on type of fuel
| Actors Involved        | User, Maps |
| ------------- |-------------| 
|  Precondition  | User U has installed EZGas app, Maps M are available on the Internet, User Location UL exists, Type of Fuel TF is an acceptable value |  
|  Post condition | The nearest gas stations to UL which offer TF are displayed on U's screen |
|  Nominal Scenario | U searches for nearest gas stations which offer TF |
|  Variants | Maps not available, issue error |
| | Internet connection not available, issue warning |

### Use case 6, UC3 - FR3 Show recently visited gas stations
| Actors Involved        | User, Database |
| ------------- |-------------| 
|  Precondition  | User U has installed EZGas app, Database DB is available on the Internet |  
|  Post condition | Database entries are pulled and displayed |
|  Nominal Scenario | U taps searchbar on screen, a brief list of the most recently visited gas stations appears |
|  Variants | Internet connection not available, issue warning |
| | Database not available, issue error |

### Use case 7, UC7 - FR4 Record updated gas station price to database
| Actors Involved        | User, Maps, Database |
| ------------- |-------------| 
|  Precondition  | User U has installed EZGas app, Maps M are available on the Internet, Database DB is available on the Internet, type of fuel TF is offered by gas station GS |  
|  Post condition | Database entry is updated |
|  Nominal Scenario | U taps GS on M and inputs new price NP and TF, uploaded to DB |
|  Variants | Maps not available, issue error |
| | Internet connection not available, issue warning |
| | Database not available, issue error |
| | Price validation error, issue error |

### Use case 8, UC8 - FR4.1 Perform trust-based check before updating price
| Actors Involved        | Database as DB |
| ------------- |-------------| 
|  Precondition  | New price NP is received at DB |  
|  Post condition | NP is saved to DB |
|  Nominal Scenario | The prices older than N days are removed. If NP is within a margin of error (e.g. 0.1€) from the last X user-submitted prices, then the price for that gas station and that type of fuel is updated to the average of the last X prices and is displayed in-app to the users; the last X prices are removed from the database. X is calculated based on how many NPs are received per day at each gas station, and is a gas station property. |
| | Else, save NP to DB. |

### Use case 9, UC9 - FR4.2 Add new gas station to database if not present
| Actors Involved        | Database as DB |
| ------------- |-------------| 
|  Precondition  | New price NP for gas station GS is received at DB |  
|  Post condition | GS is saved to DB |
|  Nominal Scenario | If GS is not already present in DB, it gets added, along with NP. |
| | Else, save NP to DB. |

### Use case 10, UC10 - FR5 Produce a graph of price fluctuations for a given station
| Actors Involved        | Database as DB |
| ------------- |-------------| 
|  Precondition  | Price history of gas station GS is pulled to application from DB |  
|  Post condition | Application shows the corresponding graph on the screen |
|  Nominal Scenario | Price history of GS is shown graphically in application |
|  Variants | GS is not in DB, issue error |
| | GS does not have a price history, issue warning |

### Use case 11, UC11 - FR6 Record gas station status
| Actors Involved        | User as U, Database as DB |
| ------------- |-------------| 
|  Precondition  | U is logged in, U sees in real life that gas station GS is either temporarily closed or under maintenance, or has re-opened after being temporarily closed or under maintenance |  
|  Post condition | The status of GS is updated in DB |
|  Nominal Scenario | U sees GS is closed, U logs into the app, U updates the status of GS, the status of GS is updated in DB |
|  Variants | U sees GS has re-opened, U logs into the app, U updates the status of GS, the status of GS is updated in DB |
| | The status of GS is the same as the new one set by U, issue warning |

### Use case 12, UC12 - FR7 Create account
| Actors Involved | User as U, Database as DB |
| ------------- |-------------| 
|  Precondition  | U is using EZGas, U sends e-mail address A and password P to DB |  
|  Post condition | A new locked user account is created in DB, a confirmation e-mail is sent to A |
|  Nominal Scenario | U registers on EZGas, U receives a confirmation e-mail at A |
|  Variants | P does not respect security standards, issue error |
| | A is not a valid e-mail address, issue error |

### Use case 13, UC13 - FR7.1 Login
| Actors Involved | User as U, Database as DB |
| ------------- |-------------| 
|  Precondition  | U is using EZGas, U sends e-mail address A and password P to DB |  
|  Post condition | U is logged in |
|  Nominal Scenario | U logs into EZGas with A, P |
| | A is not a valid e-mail address, issue error |
| | A is not in DB, issue error |
| | No A,P correspondance in DB, issue error |

### Use case 14, UC14 - FR7.2 Logout
| Actors Involved | User as U, Database as DB |
| ------------- |-------------| 
|  Precondition  | U is using EZGas, U is logged in |  
|  Post condition | U is logged out |
|  Nominal Scenario | U logs out of EZGas by pressing a logout button |
|  Variants | U is not logged in, issue warning |

##### Scenario 1.1 

\<describe here scenarios instances of UC1>

\<a scenario is a sequence of steps that corresponds to a particular execution of one use case>

\<a scenario is a more formal description of a story>

\<only relevant scenarios should be described>

| Scenario 1.1 | |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the scenario can start> |
|  Post condition     | \<Boolean expression, must evaluate to true after scenario is finished> |
| Step#        | Description  |
|  1     |  |  
|  2     |  |
|  ...     |  |

##### Scenario 1.2

### Use case 2, UC2
..

### Use case
..



# Glossary

\<use UML class diagram to define important concepts in the domain of the system, and their relationships> 

\<concepts are used consistently all over the document, ex in use cases, requirements etc>

# System Design
\<describe here system design>

\<must be consistent with Context diagram>

# Deployment Diagram 

\<describe here deployment diagram >
