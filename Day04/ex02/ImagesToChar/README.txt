mkdir lib
mkdir -p target/resources

wget -P lib https://repo1.maven.org/maven2/com/beust/jcommander/1.81/jcommander-1.81.jar
wget -P lib https://repo1.maven.org/maven2/com/diogonunes/JColor/5.0.1/JColor-5.0.1.jar

javac -classpath lib/jcommander-1.81.jar:lib/JColor-5.0.1.jar -sourcepath src/java -d target src/java/edu/school21/printer/app/Program.java

cp src/resources/image.bmp target/resources/.
(cd target && exec jar xf ../lib/jcommander-1.81.jar com)
(cd target && exec jar xf ../lib/JColor-5.0.1.jar com)

jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target edu -C target resources -C target com

java -jar target/images-to-chars-printer.jar --white=RED --black=GREEN

Available colors: BLACK, BLUE, CYAN, GREEN, MAGENTA, RED, WHITE, YELLOW
