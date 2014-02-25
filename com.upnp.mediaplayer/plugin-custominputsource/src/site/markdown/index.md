This plugin if for the Raspi only, because it uses the GPIO.

This plugin allow you to configure Input Sources for the MediaPlayer, these are displayed in a suitable Control Point such as Kinsky and when the input source is selected a configurable GPIO pin is taken high. This can be used to drive relays which allow the source to be connected to the Amplifier.

The sources are configured in the InputSources.xml file which should be located in the mediaplayer directory.

The CustomInputSource.jar should be located in the mediaplayer/plugins directory.

The format of the xml is very easy:
'name' is the name you want displayed in the Control Point.
'type' can be either PlayList, Radio or Custom.
'GPIO_PIN' is the GPIO Pin which will be taken high when that input is selected.

```
<?xml version="1.0" encoding="UTF-8"?>
<Sources>
	<Source>
		<name>PlayList</name>
		<type>Playlist</type>
		<GPIO_PIN>9</GPIO_PIN>
	</Source>
	<Source>
		<name>Radio</name>
		<type>Radio</type>
		<GPIO_PIN>9</GPIO_PIN>
	</Source>


	<Source>
		<name>Laptop</name>
		<type>Custom</type>
		<GPIO_PIN>5</GPIO_PIN>
	</Source>

	<Source>
		<name>Home PC</name>
		<type>Custom</type>
		<GPIO_PIN>6</GPIO_PIN>
	</Source>

	<Source>
		<name>iPod</name>
		<type>Custom</type>
		<GPIO_PIN>7</GPIO_PIN>
	</Source>

	<Source>
		<name>DAB Radio</name>
		<type>Custom</type>
		<GPIO_PIN>8</GPIO_PIN>
	</Source>

</Sources>
```