rem @echo off

set JFLEX=C:\jflex\bin\jflex.bat
set CUPJAR=C:\CUP\lib\java-cup-11b.jar
set CUPJARRUNTIME=c:\CUP\lib\java-cup-11b-runtime.jar
set CUP=java -jar %CUPJAR%

echo .
echo %JFLEX% --nobak zi.flex
call %JFLEX% --nobak Main\zi.flex -d Main
if %ERRORLEVEL% NEQ 0 goto exit

echo .
echo %CUP% -expect 0 -progress -interface  zi.cup 
%CUP% -expect 2 -progress -interface -locations -destdir Main Main\zi.cup 
if %ERRORLEVEL% NEQ 0 goto exit

echo .
echo javac -cp %CUPJAR% Main.java sym.java Lexer.java parser.java
javac -d Classes -cp %CUPJAR% Main\*.java Nodes\*.java Model\*.java Error\*.java
if %ERRORLEVEL% NEQ 0 goto exit


echo .
echo creating jar file

cd classes
jar cmf ..\manifest.mf ..\Zi.jar Main\*.class Nodes\*.class Model\*.class Error\*.class
if %ERRORLEVEL% NEQ 0 goto exit
cd ..

echo java -classpath  Zi.jar;%CUPJARRUNTIME%  Main test.zi
java -classpath  Zi.jar;%CUPJARRUNTIME%  Main.Main -i test.zi -ast test.gv -v

:exit
