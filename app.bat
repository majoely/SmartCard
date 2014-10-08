REM Bash script to start the app
CALL ant build-host
CALL ant build-applet
CALL ant deploy-applet
CALL ant run-script
CALL ant start-cad
