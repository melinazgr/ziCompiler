rem @echo off

set JFLEX=C:\jflex\bin\jflex.bat
set CUPJAR=C:\CUP\lib\java-cup-11b.jar
set CUPJARRUNTIME=c:\CUP\lib\java-cup-11b-runtime.jar
set CUP=java -jar %CUPJAR%

echo .
echo generating flex file
call %JFLEX% --nobak calc.flex

echo .
echo generating cup file
%CUP% -expect 0 -progress -interface ycalc.cup

echo .
echo javac -cp %CUPJAR% Main.java sym.java Lexer.java parser.java
javac -d Classes -cp %CUPJAR% Main.java sym.java Lexer.java parser.java 


echo .
echo jar cmf manifest.mf Zi.jar  Main.class sym.class Lexer.class parser.class

cd classes
jar cmf ..\manifest.mf ..\Zi.jar *.class
cd ..

java -classpath  Zi.jar;%CUPJARRUNTIME%  Main test.zi

