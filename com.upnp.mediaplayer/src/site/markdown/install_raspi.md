The MediaPlayer can be installed on either Raspian Wheezy HardFloat or Raspian Wheezy SoftFloat.

If you are not sure which version to use, choose the HardFloat version, which from version 2013-09-25-wheezy-raspbian has Java pre installed.

To check if you have Java installed enter

`java -version`

If you don't already have Java installed you will need to [Install Java](https://github.com/PeteManchester/MediaPlayer/wiki/Java-Install).

Once you have Java installed you can start the installation.

Install MPD

`sudo apt-get install mpd`

Install MPlayer

`sudo apt-get install mplayer`

Or if you already know if you are going to use you don't need to install both, just the one you want to use.

Copy the Release/mediaplayer folder to the /home/pi/ directory (this should include the mediaplayer_lib directory)

Change directory to /home/pi/mediaplayer

`cd /home/pi/mediaplayer`

Make the run.sh executable

`sudo chmod +x run.sh`

To test run the mediaplayer

`sudo /home/pi/mediaplayer/run.sh`

Some people have experienced issue with ^M characters being present in the run.sh after copying on the raspi (seems to be when copying from a Mac). You can check by using vi (sorry!!), if they are present you need to edit them out.

Or you can create your own run.sh by using nano, enter the text:

```
#!/bin/sh

DIRNAME="$( dirname "" )"
cd "${DIRNAME}"
java -jar /home/pi/mediaplayer/mediaplayer.jar &
_wlanexist=$(ifconfig | grep wlan) || true
if [ "$_wlanexist" ]; then
	iwconfig wlan0 power off
fi
exit 0
```

Ian has supplied some scripts that configure mediaplayer as a service:

Edit the scripts in the mediaplayer/scripts folder.

In the mediaplayer.sh script put the correct path to your mediaplayer directory.

In the mediaplayer.init script edit the USER and GROUP

 and then run the following commands:

```
sudo install -m 755 -T mediaplayer.init /etc/init.d/mediaplayer
sudo install -m 755 -T mediaplayer.sh /usr/local/bin/mediaplayer
sudo insserv mediaplayer
```

Once the service is installed mediplayer will be started when the raspberry pi boots up.
You can also run the following commands

`sudo service mediaplayer status/start/stop/restart`

For those who are using only a Wifi connection you might need to disable the Power Management of your Wifi Adapter.
If you run iwconfig

`iwconfig'

You can see if Power Management is enabled:

```
pi@rpistudy ~ $ iwconfig
wlan0     IEEE 802.11bgn  ESSID:"*******"
          Mode:Managed  Frequency:2.437 GHz  Access Point: 60:A4:4C:D2:00:C8
          Bit Rate=65 Mb/s   Tx-Power=20 dBm
          Retry  long limit:7   RTS thr:off   Fragment thr:off
          Power Management:on
          Link Quality=41/70  Signal level=-69 dBm
          Rx invalid nwid:0  Rx invalid crypt:0  Rx invalid frag:0
          Tx excessive retries:101  Invalid misc:965   Missed beacon:0

lo        no wireless extensions.

eth0      no wireless extensions.
```

If it is enabled you might need to disable it by editing the rc.local file:

`sudo nano /etc/rc.local`

And add the lines:

```
_wlanexist=$(ifconfig | grep wlan) || true
if [ "$_wlanexist" ]; then
	iwconfig wlan0 power off
fi

```

For example:

```
# Print the IP address
_IP=$(hostname -I) || true
if [ "$_IP" ]; then
  printf "My IP address is %s\n" "$_IP"
fi
_wlanexist=$(ifconfig | grep wlan) || true
if [ "$_wlanexist" ]; then
	iwconfig wlan0 power off
fi

exit 0
```

If you are using an ArchLinux Raspi, please note, that you need to have the package binutils (we need the binary readelf) installed, to automatically detect the ABI (HardFloat or SoftFloat). Furthermore (on all platforms) you can link the corresponding lib directory to the default directory, e.g.

```
ln -s mediaplayer_lib/ohNet/linux/armv6hf/ mediaplayer_lib/ohNet/default
```

Currently we do provide the ohNet Libs for Win x86, Win x64, Linux i386, Linux x64, Linux Arm v5sf, Linux Arm v6sf, Linux Arm v6hf and Linux Arm v7.


Some of the scripts now seem to have got ^M characters in them, to clean a file of ^M characters use the following command:

`sed -i 's/\r$//g' <filename>`