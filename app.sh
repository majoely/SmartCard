#!/bin/bash
# Bash script to start the app
ant build-host
ant build-applet
ant deploy-applet
ant start-cad
