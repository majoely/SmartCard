#!/bin/bash
# Bash script to start the app
ant clean
ant build-host
ant build-applet
ant deploy-applet
ant run-script
ant start-cad
