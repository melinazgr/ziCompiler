@echo off

set CUPJARRUNTIME=c:\CUP\lib\java-cup-11b-runtime.jar
set GRAPHIZ="C:\Program Files (x86)\Graphviz2.38\bin\dot.exe"
cd Tests


java -classpath  ..\Zi.jar;%CUPJARRUNTIME%  Main.Main -i %1 -ir %1.ir 
rem ziCompiler.exe -i %1 -ir %1.ir
if %ERRORLEVEL% NEQ 0 goto exit
bash -c ^"mixasm %1.mixal^"
bash -c ^"mixvm --run %1.mix^"

:exit
cd ..