set HERE=%~dp0
cd %HERE%

set JAVA_HOME=C:\java\jdk1.6_64bit

rem ant でビルドするプロジェクト
rem set CLASSPATH=%USERPROFILE%\.m2\repository\junit\junit\3.8.1\junit-3.8.1.jar;%USERPROFILE%\.m2\repository\junit\junit\3.8.2\junit-3.8.2.jar
rem cd com\lib

rem call mvn install:install-file -DgroupId=jp.que.ti.wk.itext ^
rem                           -DartifactId=iText ^
rem                              -Dversion=2.1.7 ^
rem                            -Dpackaging=jar ^
rem                                 -Dfile=iText-2.1.7.jar
rem call mvn install:install-file -DgroupId=jp.que.ti.wk.itext ^
rem                           -DartifactId=iTextAsian ^
rem                              -Dversion=0.0.0 ^
rem                            -Dpackaging=jar ^
rem                                 -Dfile=iTextAsian.jar

cd %HERE%
set CLASSPATH=

rem maven2 でビルドするプロジェクト
call mvn clean install
cd %HERE%

pause
