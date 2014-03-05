root@raspberry:~# pacman -S xorg-server xorg-xinit xorg-utils xorg-server-utils
root@raspberry:~# pacman -S xf86-video-fbdev xterm
root@raspberry:~# pacman -S dbus xorg-xinit

export DISPLAY=:0.0
startx -- :0

pacman -S gtk2

root@raspberry:~# pacman -S jre7-openjdk (java is needed anyway, but for MediaPlayer itself you could use jre7-openjdk-headless)

java -cp Display-plugin-assembly-with-so.jar org.rpi.plugin.fullscreen.FullscreenDisplay

656x416 - Display http://www.amazon.de/gp/product/B0058S323Q/ref=oh_details_o02_s00_i01?ie=UTF8&psc=1

startx -- :0 &

see https://wiki.archlinux.org/index.php/Display_Power_Management_Signaling to disable screensaver:
xset s off (seems to disable the screensaver completely, which is what we want)
xset -dpms



