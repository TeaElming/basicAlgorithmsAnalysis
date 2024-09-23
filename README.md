# Assignment 1 - Algorithms and Data Structures

This Java application consists of various tasks related to performance timing, union-find algorithms, and 3-sum calculations. Each task can be executed individually using Gradle. In addition, there are two pdf-files located in the folders of Task4 and Task7 containing reports regarding testing executed.

## Prerequisites

Ensure you have the following installed:
- [Java JDK 8 or higher](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Gradle](https://gradle.org/install/)
- IDE (Optional, but recommended): [Visual Studio Code](https://code.visualstudio.com/)

## Project Structure
The project is structured so that Task1 contains the performance timer required to test task 2-3 as well as tasks 5-6, while task4 and task7 contain graphing functionality and pdfs with information about the tested tasks.

To gain information about the tasks, please see PDF-files
- [Task4](./app/src/main/java/assignment1/task4/Question4.pdf)
- [Task7](./app/src/main/java/assignment1/task7/Question7.pdf)

To gain better understanding of the implmentations, it is recommended to begin looking at the tester functions where they have been utlised.

The project contains the following key tasks to use as examples:
- **Timer Tester**: Runs performance timing examples.
- **QuickFind Tester**: Tests the QuickFind algorithm.
- **QuickUnion Tester**: Tests the QuickUnion algorithm.
- **WeightedUnion Tester**: Tests the Weighted QuickUnion algorithm.
- **3Sum Brute Force Tester**: Tests a brute-force 3Sum algorithm.
- **3Sum Improved Tester**: Tests an improved 3Sum algorithm.

## How to Compile

To compile the project, open a terminal in the project's root directory and run:
`
./gradlew build
`
This will download the necessary dependencies, compile the project, and run any existing tests.

## How to Run
Each example tester has been defined as a separate Gradle task.
For usage of specific classes, e.g. graphing classes, please open the desired file and run main manually. Alternatively, edit the main in App.java to include all desired classes.
To run any of the testers, use the following commands in the terminal from the project's root directory:

**Timer Tester**
To run the timer tester:
`
./gradlew timerTester
`

**QuickFind Tester**
To run the QuickFind algorithm tester:
`
./gradlew quickFindTester
`

**QuickUnion Tester**
To run the QuickUnion algorithm tester:
`
./gradlew quickUnionTester
`

**WeightedUnion Tester**
To run the Weighted QuickUnion algorithm tester:
`
./gradlew weightedUnionTester
`

**3Sum Brute Force Tester**
To run the 3Sum brute-force tester:
`
./gradlew brute3SumTester
`

**3Sum Improved Tester**
To run the improved 3Sum algorithm tester:
`
./gradlew improved3SumTester
`

## Testing
The project uses JUnit 5 for unit testing. You can run all tests by executing:
`
./gradlew test
`
## Dependencies

- *Guava:* A core library that provides additional functionality for collections and concurrency in Java.
- *JFreeChart:* Used for generating graphs (if applicable in your project).
- *JUnit 5:* For running unit tests.

## Author
Tea Elming

## License

This project is for educational purposes. Feel free to modify and use it as needed.