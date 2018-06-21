# ovo-exercise

## Requirements
This project requires:
* Java 8
* Scala
* sbt

## Running the application
The application can be run either in a terminal or in a appropriate IDE (I used Intellij with the scala plugin)  

### Terminal
https://alvinalexander.com/scala/sbt-how-to-compile-run-package-scala-project
Further deatils can be found at the above link but the basic steps are outlined below:
1. Complie the project using   
$ sbt compile
2. Run the project by using the sbt run and passing your arguments like in the below example  
$ sbt run cost 20 30

### IDE
If you are using Intellij with the scala plugin you can run the same commands in the sbt console without the leading "sbt"  
For example:  
$ run cost 20 30  
instead of  
$sbt run cost 20 30