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
|  FR2.2    | Filter search based on lowest price first |
|  FR2.3    | Filter search based on type of fuel |
|  FR3      | Show recently visited gas stations |
|  FR4      | Record updated gas station price to database |
|  FR4.1    | Perform trust-based check before updating price |
|  FR4.2    | Add new gas station to database if not present |

## Non Functional Requirements

\<Describe constraints on functional requirements>

| ID        | Type (efficiency, reliability, ..)           | Description  | Refers to |
| ------------- |:-------------:| :-----:| -----:|
|  NFR1     |   |  | |
|  NFR2     | |  | |
|  NFR3     | | | |


# Use case diagram and use cases


## Use case diagram
\<define here UML Use case diagram UCD summarizing all use cases, and their relationships>


\<next describe here each use case in the UCD>
### Use case 1, UC1
| Actors Involved        |  |
| ------------- |:-------------:| 
|  Precondition     | \<Boolean expression, must evaluate to true before the UC can start> |  
|  Post condition     | \<Boolean expression, must evaluate to true after UC is finished> |
|  Nominal Scenario     | \<Textual description of actions executed by the UC> |
|  Variants     | \<other executions, ex in case of errors> |

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
