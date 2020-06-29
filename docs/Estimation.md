# Project Estimation  

Authors: Enrico Castelli s280124, Augusto Maria Guerriero s278018, Francesca Ponzetta s276535, Monica Rungi s276979

Date: 25/04/2020

Version: 1

# Contents



- [Estimate by product decomposition]
- [Estimate by activity decomposition ]



# Estimation approach

We have considered the classes from the glossary in our requirements document, excluding Price and Status, which can be represented by a double and a boolean value respectively.  
We have estimated the EZGas, GasStation, GuestUser and Update classes to be longer in terms of LOC, and the remaining classes more compact, with some classes (e.g. Location) containing only getters and setters methods.  

# Estimate by product decomposition



###

|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed   | 10 |             
|  A = Estimated average size per class, in LOC       | 400 |
| S = Estimated size of project, in LOC (= NC * A) | 4000 |
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  | 400 |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 12000€ |
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) | 3 |               


# Estimate by activity decomposition



###

|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- |
|Requirement Engineering |44 |
|Design |5 |
|Coding |110 |
|Unit Testing |96 |
|Integration Testing |16 |
|Acceptance Testing |16 |
|Management |50 |
|Git Maven | 10 |


###

Gantt chart
```plantuml
@startuml

Project starts the 13th of april 2020
sunday are closed

[Requirement Engineering] starts the 2020/04/13
[Requirement Engineering] ends the 2020/04/17
[Requirement Engineering] is colored in red

    [Requiremets Document] starts the 2020/04/13
    [Requiremets Document] ends the 2020/04/17

    [GUI] starts the 2020/04/15
    [GUI] ends the 2020/04/17

[Design] starts the 2020/04/25
[Design] lasts 5 days
[Design] is colored in red

    [Time Sheet] starts the 2020/04/25
    [Time Sheet] lasts 5 days
    [Estimation] starts the 2020/04/25
    [Estimation] lasts 1 day
    [Design Document] starts the 2020/04/27
    [Design Document] lasts 3 day

[Development] starts the 2020/05/05
[Development] ends the 2020/05/26
[Development] is colored in red

    [Coding] starts the 2020/05/05
    [Coding] ends the 2020/05/26


    [Unit Testing] starts the 2020/05/11
    [Unit Testing] ends the 2020/05/24
    [Unit Testing] is colored in blue
        [EZGas Test] starts the 2020/05/11
        [EZGas Test] ends the 2020/05/24
        [Gas Station Test]  starts the 2020/05/11
        [Gas Station Test] ends the 2020/05/24
        [User Test] starts the 2020/05/11
        [User Test] ends the 2020/05/24
        [Logged User Test] starts the 2020/05/11
        [Logged User Test] ends the 2020/05/16
        [Location Test] starts the 2020/05/11
        [Location Test] ends the 2020/05/14
        [Fuel Test] starts the 2020/05/15
        [Fuel Test] ends the 2020/05/16
        [Update Test] starts the 2020/05/13
        [Update Test]  ends the 2020/05/18
        [Price Update Test] starts the 2020/05/13
        [Price Update Test] ends the 2020/05/24
        [Status Update Test] starts the 2020/05/18
        [Status Update Test] ends the 2020/05/19
        [Preferences Test] starts the 2020/05/20
        [Preferences Test] ends the 2020/05/24

[Integration Testing] starts the 2020/06/01
[Integration Testing] ends the 2020/06/07
[Integration Testing] is colored in red

[Acceptance Testing] starts the 2020/06/15
[Acceptance Testing] ends the 2020/06/21
[Acceptance Testing] is colored in red

[Management] starts the 2020/06/24
[Management] ends the 2020/07/01
[Management] is colored in red


[Requirement Engineering] -> [Design]
[Design] -> [Development]
[Development] -> [Integration Testing]
[Integration Testing] -> [Acceptance Testing]
[Acceptance Testing] -> [Management]

@enduml
```

# Estimations based on the actual efforts computed

# Estimate by product decomposition



###

|             | Estimate                        |             
| ----------- | ------------------------------- |  
| NC =  Estimated number of classes to be developed   | 36 |             
|  A = Estimated average size per class, in LOC       |235 |
| S = Estimated size of project, in LOC (= NC * A) | 8444 |
| E = Estimated effort, in person hours (here use productivity 10 LOC per person hour)  | 258.5 |   
| C = Estimated cost, in euro (here use 1 person hour cost = 30 euro) | 7755€ |
| Estimated calendar time, in calendar weeks (Assume team of 4 people, 8 hours per day, 5 days per week ) | 1.6 |               

In order to get the LOC we have used the following commands:  
- backend+frontend: `git ls-files src | xargs wc -l` (total lines of code)  
- frontend: `git ls-files src/main/resources | xargs wc -l`  
- effective LOC = total LOC - frontend LOC

# Estimate by activity decomposition

###

|         Activity name    | Estimated effort (person hours)   |             
| ----------- | ------------------------------- |
|Requirement Engineering |36 |
|Design |44 |
|Coding |70 |
|Unit Testing |32|
|Integration Testing |45 |
|Acceptance Testing |7 |
|Management |13 |
|Git Maven | 11.5 |


