The configuration options can be edited in the file:

`app.properties`

Below is a list of options:

Option | Description | Default Value
--- | --- | ---
friendly.name | The name of the MediaPlayer as shown in the Control Point |
player | Which application should be used to play the files, can be either mpd or mplayer |
playlist.max | The maximum number of tracks in the playlist (maximum is 1000) | 1000
save.local.playlist | Is the playlist saved locally | true
enableAVTransport | If you are using only Kinsky as your Control Point you dont need the AVTransport, if you are using other CPs such as Asset Control you will need to enable this option (values are 'true' or 'false') |
enableReceiver | Determines if the SongCast Receiver Source is displayed in the Control Point. It looks good but at the moment it doesn't do anything !! |
mplayer.playlist | The type of streams supported by mplayer |
mplayer.path | The path to your mplayer app |
mplayer.cache | The size of the cache mplayer will try to maintain |
mplayer.cache_min | The minimum size in percent of cache that mplayer will fill before playing the stream |
mpd.host | The Hostname of the mpd player | localhost
mpd.port | the Port number of the mpd player | 6600
mpd.preload.timer | The time remaining on the current track when we add the next track to the MPD playlist |
log.file | The path the log file |
log.file.level | The log level, can be off,debug,info,warn,error,fatal | info
log.console.level | Used for debugging only, same options as above | off
openhome.debug.level | Set the log level of the OpenHome libraries, values can be None, Trace, Thread, Network, Timer, SsdpMulticast, SsdpUnicast, Http, Device, XmlFetch, Service, Event, Topology, DvInvocation, DvEvent, DvWebSocket, Bonjour, DvDevice, Error, All, Verbose | Error
openhome.port | The port used by the MediaPlayer, if left blank a random port will be used |

If you are using mpd to playback the tracks you don't need to worry about setting the mplayer options, and if you are using mplayer you don't need to worry about the mpd files.

When using mpd this will support gapless playback, but cannot resolve a lot of radio streams and so I have to resolve the streams such as .pls in the code. MPlayer will not do gapless playback but you can throw any stream\file type at it and it just works.. The choice is yours :-)
