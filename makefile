compile: bin
	find src -name "*.java" > sources.txt
	javac -cp biuoop-1.4.jar -d bin @sources.txt

bin:
	mkdir bin

run:
	java -jar ass6game.jar

jar:
	jar cfm ass6game.jar MANIFEST.MF -C bin . -C resources .

check:
	java -jar checkstyle-5.7-all.jar -c biuoop.xml src/*.java src/animation/*.java src/collidablesdata/*.java src/gamelogic/*.java src/gameobjects/*.java src/indicators/*.java src/levelsdata/*.java src/listeners/*.java src/menu/*.java src/scoredata/*.java
