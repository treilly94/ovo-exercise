# ovo-exercise

## Requirements
This project requires:
* Java 8
* Scala - version 2.12.6 was used in this project
* sbt

## Running the application
The application can be run in the sbt console. This can be accessed either in a terminal or in a appropriate IDE (I used Intellij with the scala plugin)  

To enter the sbt console from the terminal cd into the project root directory (where the build.sbt file is) and run the below command:  
$ sbt

https://alvinalexander.com/scala/sbt-how-to-compile-run-package-scala-project  
Once in the console you can compile and rin the application. Further details can be found at the above link but the basic steps are outlined below:
1. Complie the project using   
$ compile
2. Run the project by using "run" and passing your arguments like in the below example  
$ run cost 20 30
