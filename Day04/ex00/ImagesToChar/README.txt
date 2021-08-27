mkdir target
javac -d target src/java/edu.school21.printer/logic/*.java src/java/edu.school21.printer/app/*.java
java -classpath target edu.school21.printer.app.Program