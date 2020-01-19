## Group

* Group ID: k
* Members:
	* Sagar Bajaj
	* Vato Maskhulia

## Run instructions

```bat
java -jar hw6.jar <csv-for-decision-tree> <csv-for-regression-tree>
```

```bat
java -jar hw6.jar diabetes.csv auto.csv
```

__Output:__ [decision-tree-out.txt](decision-tree-out.txt) [regression-tree-out.txt](regression-tree-out.txt)

## Decision Tree and Regression Tree implementation

Tbl [Tbl.java](HW6/src/Tbl.java).

Div2 [Div2.java](HW6/src/Div2.java).

Unit tests [TreeTest.java](HW6/src/TreeTest.java).

## Source code
Source code is located in directory [HW6/src](HW6/src).

Program runner [Main.java](HW6/src/Main.java).

CSV Parser [CSVParser.java](HW6/src/CSVParser.java) with unit tests [CSVParserTest.java](HW6/src/CSVParserTest.java).

Col parser [ColParser.java](HW6/src/ColParser.java) with unit tests [ColParserTest.java](HW6/src/ColParserTest.java).

Num and Sym [Num.java](HW6/src/Num.java) and [Sym.java](HW6/src/Sym.java).

