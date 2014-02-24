The MediaPlayer provides a list of Radio Stations, these Stations can be configured by editing the

`RadioList.xml`

The format of the xml file is:

```
	<Channel>
		<id>NAME</id>
		<url>URL</url>
		<image>IMAGE URL</image>
	</Channel>
```

Where:

 NAME      is the name displayed in the Control Point.

 URL       is the url of the radio feed.

 IMAGE URL is optional and is the url to an image file.



For Example:

```
	<Channel>
		<id>Absolute DAB</id>
		<url>http://mp3-ar-128.timlradio.co.uk</url>
		<image>http://onegoldensquare.com/images/uploads/2010/10/Absolute-radio-logo3.jpg</image>
	</Channel>
```

You can delete Channels or add your own Channels.

