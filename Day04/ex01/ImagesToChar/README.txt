mkdir target
javac -d target src/java/edu.school21.printer/logic/*.java src/java/edu.school21.printer/app/*.java
java -classpath target edu.school21.printer.app.Program
mkdir target/resources
cp src/resources/image.bmp target/resources/.
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .