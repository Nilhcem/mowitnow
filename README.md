# MowItNow
Technical Job Interview Project from Xebia

Description (french only)



requires jdk6+


## Compile
  $ mvn package
  $ mvn install

## Run the CLI
  $ mvn exec:java -pl mowitnow-cli -Dexec.args="mowitnow-cli/mower.instructions"
or
  $ cd mowitnow-cli
  $ mvn exec:java


## Projects descriptions:

mowitnow-cli:
Simple 1-class command line interface that will call the library and display the result

mowitnow-lib:
Library class that can be used with any GUI (web, swing, android etc.)

mowitnow-android:
Android project
deploy on your device using:
    mvn android:deploy


Import android project in Eclipse via
File > Import > Maven > Existing Maven Project
