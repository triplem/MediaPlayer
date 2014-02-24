MediaPlayer uses ANT to build a zip with the latest release.

If you would like to create a release, you need to call the following command line inside the MediaPlayer/com.upnp.mediaplayer directory to build it:

``
ant build_app
``

This will clean up the build directory (target: clean), create all necessary directories (target: prepare) and compile the application (target: compile). The classes are then compiled into the directory build/classes.

Then a JAR file for mediaplayer is created (target: build_jar) in the directory build/release/mediaplayer (its name is mediaplayer.jar).

All necessary dependencies are then copied into the directory build/release/mediaplayer (including the native dependencies from the folder lib/native and the scripts inside lib/application) and a mediaplayer.zip file is put into the directory build/release.

This ZIP-file can then be copied to your target System, unzipped in a directory of your choice and the run.sh inside the folder mediaplayer can then be executed, like described in [Install on Raspberry Pi](https://github.com/PeteManchester/MediaPlayer/wiki/Install-Raspberry-Pi).
