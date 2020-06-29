# Unit Testing Documentation

Authors: Enrico Castelli s280124, Augusto Maria Guerriero s278018, Francesca Ponzetta s276535, Monica Rungi s276979

Date: 19/05/2020

Version: 1

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)
- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests

    <Define here criteria, predicates and the combination of predicates for each function of each class.
    Define test cases to cover all equivalence classes and boundary conditions.
    In the table, report the description of the black box test case and (traceability) the correspondence with the JUnit test case writing the 
    class and method name that contains the test case>
    <JUnit test classes must be in src/test/java/it/polito/ezgas   You find here, and you can use,  class EZGasApplicationTests.java that is executed before 
    the set up of all Spring components
    >

 ### **Class *GetterSetterTests* - *all methods***

**Criteria for *all methods*:**

 - set extracted value from possible values of correct type in setter
 - use corresponding getter to get the previously set value
 - check if values are equals with equals method

**Predicates for *all methods*:**

| Criteria | Predicate |
| -------- | --------- |
| value    | = extracted value of type String, Integer, Boolean, boolean, double |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|  value  | no boundaries, the only limit is the type of the value which corresponds to the type expected by the setter |

**Combination of predicates**:

Given the number of getter and setter methods to test, only one test case per couple of getter/setter was written. 
This can be easily increased by tweaking the Python script used to generate the tests in the src/test directory.

 ### **Class *GasStationServiceimplTest* - method *test_reportDependability_****



**Criteria for method *test_reportDependability_******:**


 - Number of days between new and last timestamps
 - Value of userTrustLevel





**Predicates for method *name*:**

| Criteria | Predicate |
| -------- | --------- |
|    Number of days between new and last timestamps      |     Number of days >= 7      |
|          |       0 <= Number of days < 7     |
|      Value of userTrustLevel    |   -5  <= userTrustLevel <= 5     |
|          |      userTrustLevel > 5     |
|          |     userTrustLevel < -5     |
|          |          |





**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|    Number of days between new and last timestamps      |         0, 1, ... , 7, 8, ... ∞    |
|    Value of userTrustLevel      |        -∞ ... -6, -5, ... , 0, ... , 5, 6, ... ∞          |



**Combination of predicates**:


| Number of days | userTrustLevel | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
|Number of days >= 7|-5  <= userTrustLevel <= 5|Valid|days = 8,  userTrustLevel = 2|test_reportDependability_obsolescent|
|||Valid|days = 8, userTrustLevel = -5|test_reportDependability_obsolescent_worstUser|
|| userTrustLevel > 5|Invalid?||
|| userTrustLevel < 5|Invalid?||
|0 <= Number of days < 7|-5  <= userTrustLevel <= 5|Valid|days = 1, userTrustLevel = 2|test_reportDependability_notObsolescent|
|||Valid|days = 0, userTrustLevel = -5|test_reportDependability_sameDay_worstUser|
|||Valid|days = 0, userTrustLevel = 5|test_reportDependability_sameDay_perfectUser|
|| userTrustLevel > 5|Invalid?||
|| userTrustLevel < 5|Invalid?||

### **Class *GasStationServiceimplTest* - method *test_distance_****



**Criteria for method *test_distance_******:**
	

 - Distance between two geopoints
 - lat
 - lon





**Predicates for method *name*:**

| Criteria | Predicate |
| -------- | --------- |
|      Distance between two geopoints    |       0 <= distance <= 1 km   |
|          |      distance > 1 km     |
|   at |  -95 <= lat <= 95 |
|       | lat > 95 |
|       |  lat < -95|
|   lon |   -180 <= lon <= 180 |
|       | lon > 180 |
|       | lon < 180 |

**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|      Distance between two geopoints    |     0 ...  ∞    |
|   lat |  -95 <= lat <= 95 |
|      lon    |        -180 <= lon <= 180         |



**Combination of predicates**:


| Distance between two geopoints | lat | lon | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
| 0 <= distance <= 1 km | -95 <= lat <= 95|-180 <= lon <= 180 |Valid|distance =~ 0.3km |test_distance_nearest|
|||lon > 180|||
|||lon < 180|||
||lat > 95||||
|||lon > 180|||
|||lon < 180|||
||lat < -95||||
|||lon > 180|||
|||lon < 180|||
| distance > 1 km  | -95 <= lat <= 95|-180 <= lon <= 180 |Valid|distance =~ 1.6km |test_distance_near|
|||||distance =~ 5.1|test_distance_far|
|||||distance =~ 7.1|test_distance_furthest|
|||lon > 180|||
|||lon < 180|||
||lat > 95||||
|||lon > 180|||
|||lon < 180|||
||lat < -95||||
|||lon > 180|||
|||lon < 180|||

# White Box Unit Tests

### Test cases definition
    
    <JUnit test classes must be in src/test/java/it/polito/ezgas>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name      | JUnit test case    |
| ---------------| ---------------    |
| User           | testUser           |
| GasStation     | testGasStation     |
| UserDto        | testUserDto        |
| LoginDto       | testLoginDto       |
| GasStationDto  | testGasStationDto  |
| IdPw           | testIdPw           |
| PriceReportDto | testPriceReportDto |


### Code coverage report

<img src="/images/Coverage/unknown.png">

### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name              | Loop rows | Number of iterations | JUnit test case |
|-----------------------|-----------|----------------------|-----------------|
|GasStationServiceimpl  | | | |
| getAllGasStations | lines 147-149 | multiple | test_getAllGasStations |
| getGasStationsByGasolineType | lines 172-200 | multiple | test_getGasStationsByGasolineType_validGasType|
| getGasStationsWithCoordinates | lines 253-262 | zero     | test_getGasStationsWithCoordinates_notExisting |
|                               |               | one      | test_getGasStationsWithCoordinates_existing |
| getGasStationsWithoutCoordinates | lines 276-282 | one   | test_getGasStationsWithoutCoordinates_existing |
| getGasStationByCarSharing | lines 341-343 | zero      | test_getGasStationByCarSharing_existing |
|                           |               | one       | test_getGasStationByCarSharing_notExisting |
|UserServiceimpl  | | | |
| getAllUsers | lines 66-68 | multiple     | testGetAllUsers |

