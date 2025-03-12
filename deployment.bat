@echo off

SET "APP_NAME=Ticketing"

SET "TEMP_DIR=temp_%APP_NAME%"
SET "CLASSES_DIR=%TEMP_DIR%\WEB-INF\classes"
SET "VIEWS_DIR=%TEMP_DIR%\views"
SET "LIB=%TEMP_DIR%\WEB-INF\lib"
SET "WAR_NAME=%APP_NAME%.war"

mkdir %TEMP_DIR%

javac -parameters -d %CLASSES_DIR% -sourcepath src src\model\*.java src\controller\*.java -cp lib\*

if errorlevel 1 (
  echo Compilation failed.
    pause
    exit /b 1
)

xcopy /s /i web %VIEWS_DIR%

xcopy /s /i xml %TEMP_DIR%\WEB-INF

xcopy /s /i lib %LIB%

if exist src\application.properties (
    xcopy src\application.properties %CLASSES_DIR% /Y
) else (
    echo application.properties introuvable.
    pause
    exit /b 1
)


cd %TEMP_DIR%
jar -cvf %WAR_NAME% *

copy %WAR_NAME% "C:\Program Files\Apache Software Foundation\Tomcat 10.1\webapps\%APP_NAME%.war"

cd ..
rmdir /s /q %TEMP_DIR%