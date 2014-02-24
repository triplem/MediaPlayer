The AlarmClock plugin allows you to start playing a Playlist or Radio at a certain time or stop playing at a certain time.

The AlarmClock.jar should be copied to a folder in mediaplayer/plugins directory for example /mediaplayer/plugins/AlarmClock.

In the same directory as the AlarmClock.jar you should add an AlarmClock.xml, this should be a in a format as below:


```
<?xml version="1.0" encoding="UTF-8"?>
<Alarms>
	<Alarm>
		<name>Absolute</name>
		<time>00 30 07 ? * 2-6</time>
		<volume>80</volume>
		<shuffle>false</shuffle>
		<type>Radio</type>
		<channel>Absolute DAB</channel>
	</Alarm>

	<Alarm>
		<name>News Beat</name>
		<time>00 45 12 ? * 2-6</time>
		<volume>100</volume>
		<shuffle>false</shuffle>
		<type>Radio</type>
		<channel>BBC Radio 1</channel>
	</Alarm>

	<Alarm>
		<name>After News Beat</name>
		<time>00 03 13 ? * 2-6</time>
		<volume>80</volume>
		<type>PlayList</type>
		<shuffle>true</shuffle>
	</Alarm>

	<Alarm>
		<name>Turn Off</name>
		<time>00 35 19 * * ?</time>
		<type>Off</type>
	</Alarm>
</Alarms>
```

Where:

'name' is a unique name for the alarm.
'time' is the time the action will occur, in the quartz cron format (http://www.cronmaker.com).
'volume' sets the volume of the MediaPlayer.
'type' can be either Radio, PlayList or Off.
'shuffle' will shuffle the PlayList.
