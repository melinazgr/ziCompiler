@echo off

set CUPJARRUNTIME=c:\CUP\lib\java-cup-11b-runtime.jar
set GRAPHIZ="C:\Program Files (x86)\Graphviz2.38\bin\dot.exe"
cd Tests

for %%i in (*.zi) do (
    echo %%i 
    java -classpath  ..\Zi.jar;%CUPJARRUNTIME%  Main.Main -i %%i -ir %%i.ir
    bash -c ^"mixasm %%i.mixal^"
    bash -c ^"mixvm --run %%i.mix^"
)
rem type %1.ir
rem -ast %1.gv

rem %GRAPHIZ% -Tpng %1.gv -o %1.png

rem start %1.png
cd ..

