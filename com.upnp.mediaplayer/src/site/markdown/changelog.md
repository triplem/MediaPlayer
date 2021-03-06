Changelog
=========

16 Feb 2014
-----------

* Added TripleMs LastFM Scrobbler, with added blacklist to hide your guilty secrets..

24 January 2014
---------------

* Added the images directory back into the .jar file

23 January 2014
---------------

* OSManager changes, we now require readelf to be intalled on Linux OS, if this is not installed on ArchLinux install 'binutils'

5 January 2014
--------------

* Added 'icy_reverse' attribute to RadioList.xml, allows ICY title and artist to be reversed.

30 December 2013
----------------

* Fixed PlayList bug with BubbleUPnP, now on ReadList we only return the tracks requested by the CP, instead of all tracks.

24 December 2013
----------------

* Fixed but with LCDDisplay, I now set the standby status when creating the scroller

2 December 2013
---------------

* Changed the way the volume is handled for USB DAC that does not support hardware volume control, now we keep the -1 value internally and return zero to the control point, but if the volume is less than zero we do not set the volue or mute
* Added logic to ignore a play request if it is recieved within 1 second after a seekid or seekindex request, some control points just send a seekId or seekIndex request to play a track, others send a seekId or seekIndex immediately followed by a Play request

28 November 2013
----------------

* Fixed issue with the Volume value when a USB DAC does not support hardware volume
* Fixed issue when shutting down if Pi4J had not be initialized

12 November 2013
----------------

* Added InputSources.xml for configuring sources, added to main code and there is a plugin for controlling GPIO pins.
* Now added pi4j to main app, but only initiated if running on a Raspi, needs to be in the mediaplayer_lib, this allows mutliple plugins to share the same GPIO object

30th Sept 2013 v0.0.0.4
-----------------------

* Added support for the MPD player, which can provide gapless playback
* Added a check for 'AUDIO/X-MPEGURL' for unknown url types
* Added config options enableAVTransport and enableReceiver to add support for more Control Points

5th August 2013 v0.0.0.3
------------------------

* Changed Previous Track control to restart same track if the play time is greater than 3 seconds, if less than 3 seconds the previous track is started
* Added default path the mplayer on a raspi, so that mediaplayer should still work if the app.properties file is not present

23 April 2013
-------------

* Fixed bugs with volume and mute when starting playing tracks.
