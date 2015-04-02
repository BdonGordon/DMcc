all:
	gcc -fPIC -o libJavaPMManager.so -lc -shared ParameterManager.c ParameterHelper.c  -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux -I.
	lex Lexx.l
	yacc -d -y Yacc.y
	gcc -g y.tab.c lex.yy.c -lfl -o scanner ParameterManager.c ParameterHelper.c  -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include -I/usr/lib/jvm/java-1.7.0-openjdk-amd64/include/linux 
	javac *.java
	
	export LIBRARY_PATH=./