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
|Requirement Engineering |11 |
|Design |1,25 |
|Coding |27,5 |
|Unit Testing |24 |
|Integration Testing |4 |
|Acceptance Testing |4 |
|Management |12,5 |
|Git Maven | |



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
