## Group

* Group ID: k
* Members:
	* Sagar Bajaj
	* Vato Maskhulia

## Report

[report.txt](report.txt)

## Run instructions

```bat
java -jar hw9.jar xomo10000.csv all 0 20
```

```bat
java -jar hw9.jar pom310000.csv all 0 20
```

```bat
java -jar hw9.jar xomo10000.csv incremental 0.5 20
```

```bat
java -jar hw9.jar pom310000.csv incremental 0.5 20
```

__Output:__

[pom310000-all-[alpha=0.0].out.txt](pom310000-all-[alpha=0.0].out.txt)

[pom310000-incremental-[alpha=0.2].out.txt](pom310000-incremental-[alpha=0.2].out.txt)

[pom310000-incremental-[alpha=0.5].out.txt](pom310000-incremental-[alpha=0.5].out.txt)

[pom310000-incremental-[alpha=0.7].out.txt](pom310000-incremental-[alpha=0.7].out.txt)

[pom310000-incremental-[alpha=0.9].out.txt](pom310000-incremental-[alpha=0.9].out.txt)

[xomo10000-all-[alpha=0.0].out.txt](xomo10000-all-[alpha=0.0].out.txt)

[xomo10000-incremental-[alpha=0.2].out.txt](xomo10000-incremental-[alpha=0.2].out.txt)

[xomo10000-incremental-[alpha=0.5].out.txt](xomo10000-incremental-[alpha=0.5].out.txt)

[xomo10000-incremental-[alpha=0.7].out.txt](xomo10000-incremental-[alpha=0.7].out.txt)

## Solution

Report [Report.java](HW9/src/Report.java).

Tbl [Tbl.java](HW9/src/Tbl.java).

Div [Div.java](HW9/src/Div.java).

Unit tests [TreeTest.java](HW9/src/TreeTest.java).

## Source code
Source code is located in directory [HW9/src](HW9/src).

Program runner [Main.java](HW9/src/Main.java).

CSV Parser [CSVParser.java](HW9/src/CSVParser.java) with unit tests [CSVParserTest.java](HW9/src/CSVParserTest.java).

Col parser [ColParser.java](HW9/src/ColParser.java) with unit tests [ColParserTest.java](HW9/src/ColParserTest.java).

Num and Sym [Num.java](HW9/src/Num.java) and [Sym.java](HW9/src/Sym.java).

