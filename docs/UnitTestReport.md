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

 ### **Class *class_name* - method *name***



**Criteria for method *name*:**
	

 - 
 - 





**Predicates for method *name*:**

| Criteria | Predicate |
| -------- | --------- |
|          |           |
|          |           |
|          |           |
|          |           |





**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|          |                 |
|          |                 |



**Combination of predicates**:


| Criteria 1 | Criteria 2 | ... | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|-------|
|||||||
|||||||
|||||||
|||||||
|||||||



# White Box Unit Tests

### Test cases definition
    
    <JUnit test classes must be in src/test/java/it/polito/ezgas>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|--|--|
|||
|||
||||

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|||||
|||||
||||||



