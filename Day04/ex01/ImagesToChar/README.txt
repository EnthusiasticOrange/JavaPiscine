mkdir -p target/resources

javac -sourcepath src/java -d target src/java/edu/school21/printer/app/Program.java
cp src/resources/image.bmp target/resources/.

jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target edu -C target resources

java -jar target/images-to-chars-printer.jar