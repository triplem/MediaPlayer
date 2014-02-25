# LIRC Integration

LIRC is the Linux InfraRed Remote Control.

This page details how to install and configure LIRC on a Raspberry Pi.

This allows you to control an amplifiers volume and source selection.

To install lirc

`sudo apt-get install lirc`

Edit the /etc/modules file

`sudo nano /etc/modules`

Add the following two lines, choosing the GPIO pins that are not already being used

```
lirc_dev
lirc_rpi gpio_in_pin=23 gpio_out_pin=14
```

Edit your /etc/lirc/hardware.conf file

`sudo nano /etc/lirc/hardware.conf`

To be:

```
########################################################
    # /etc/lirc/hardware.conf
    #
    # Arguments which will be used when launching lircd
    LIRCD_ARGS="--uinput"

    # Don't start lircmd even if there seems to be a good config file
    # START_LIRCMD=false

    # Don't start irexec, even if a good config file seems to exist.
    # START_IREXEC=false

    # Try to load appropriate kernel modules
    LOAD_MODULES=true

    # Run "lircd --driver=help" for a list of supported drivers.
    DRIVER="default"
    # usually /dev/lirc0 is the correct setting for systems using udev
    DEVICE="/dev/lirc0"
    MODULES="lirc_rpi"

    # Default configuration files for your hardware if any
    LIRCD_CONF=""
    LIRCMD_CONF=""
    ########################################################
```

Restart lirc to pickup theses changes (sometimes a reboot is needed)

```
sudo /etc/init.d/lirc stop
sudo /etc/init.d/lirc start
```

To test if you are receiving IR signals

```
sudo /etc/init.d/lirc stop
mode2 -d /dev/lirc0
```

You should see something like this:

```
space 16777215
pulse 14131078
space 8687
pulse 4599
space 657
pulse 1656
space 648
pulse 505
space 600
pulse 1710
space 653
pulse 507
space 593
pulse 559
```

To List the IR commands you can program enter

`irrecord --list-namespace`

To create a config file, first stop lirc

`sudo /etc/init.d/lirc stop`

The put lirc into record mode

`irrecord -d /dev/lirc0 ~/lircd.conf`

And follow the instructions.

You should end up with a config file similar to:

```
#
# contributed by
#
# brand:                       /root/lircd.conf
# model no. of remote control:
# devices being controlled by this remote:
#

begin remote

  name  TEAC_H300
  bits           16
  flags SPACE_ENC
  eps            30
  aeps          100

  header       8682  4597
  one           646  1664
  zero          646   508
  ptrail        616
  repeat       9263  2290
  pre_data_bits   16
  pre_data       0xA156
  gap          40067
  repeat_gap   95502
  toggle_bit_mask 0x0

      begin codes
          KEY_POWER                0xE916
          KEY_POWER2               0xA156C936
          KEY_VOLUMEDOWN           0x7986
          KEY_VOLUMEUP             0xF906
          KEY_KP1                  0xA956
          KEY_KP2                  0x29D6
          KEY_KP3                  0x31CE
          KEY_KP4                  0x24DB
          KEY_KP5                  0xB14E
          KEY_KP6                  0x8976
      end codes

end remote
```

Copy this to /etc/lirc/lircd.conf

Install the MediaPlayer plugin in the mediaplayer/plugins directory.

In mediaplayer/plugins/LIRC directory should be two files

LIRCIntegration.jar
LIRCConfig.xml


The LIRCConfig.xml maps the mediaplayer events to the lirc commands.

At the moment there are two types of events:

```
Volume (Up/Down)
SourceChanged (Name of Source as configured in the InputSources.xml file)
StandbyChanged (true (in Standby) or false(not in Standby))
```

My LIRCConfig.xml looks like this:

```
<?xml version="1.0" encoding="UTF-8"?>
<Mappings>
	<Mapping>
		<Event>VolumeInc</Event>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_VOLUMEUP</Command>
		<!-- Command>notepad.exe UP</Command -->
	</Mapping>

	<Mapping>
		<Event>VolumeDec</Event>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_VOLUMEDOWN</Command>
		<!-- Command>notepad.exe DOWN</Command -->
	</Mapping>

	<Mapping>
		<Event>SourceChanged</Event>
		<Name>DAB Radio</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_KP1</Command>
	</Mapping>

	<Mapping>
		<Event>SourceChanged</Event>
		<Name>Home PC</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_KP1</Command>
	</Mapping>

	<Mapping>
		<Event>SourceChanged</Event>
		<Name>iPod</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_KP3</Command>
	</Mapping>

	<Mapping>
		<Event>SourceChanged</Event>
		<Name>Laptop</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_KP4</Command>
	</Mapping>

	<Mapping>
		<Event>SourceChanged</Event>
		<Name>PlayList</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_KP3</Command>
	</Mapping>

	<Mapping>
		<Event>SourceChanged</Event>
		<Name>Radio</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_KP3</Command>
	</Mapping>

	<Mapping>
		<Event>StandbyChanged</Event>
		<Name>true</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_POWER2</Command>
	</Mapping>

	<Mapping>
		<Event>StandbyChanged</Event>
		<Name>false</Name>
		<Command>irsend SEND_ONCE TEAC_H300 KEY_POWER</Command>
	</Mapping>

</Mappings>
```

The LIRCIntegration plugin will receive the event from MediaPlayer, it will then check the mapping and create the LIRC command, it will then put this command in a queue run in a separate thread to be processed.

At the moment the plugin just handles either VolumeInc and VolumeDec requests from the control points such as Kinsky, some control points such as BubbleDS send a SetVolume(xxx) request which at the moment is not handled.

LIRC seems to take a lot of CPU to execute a command and this can interfere with the audio playback.
