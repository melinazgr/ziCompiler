@echo off

set CUPJARRUNTIME=c:\CUP\lib\java-cup-11b-runtime.jar
set GRAPHIZ="C:\Program Files (x86)\Graphviz2.38\bin\dot.exe"

java -classpath  Zi.jar;%CUPJARRUNTIME%  Main.Main -i %1 -ir %1.ir -ast %1.gv
type %1.ir
 

%GRAPHIZ% -Tpng %1.gv -o %1.png

start %1.png

