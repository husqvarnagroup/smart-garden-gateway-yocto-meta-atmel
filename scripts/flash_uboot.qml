import QtQuick 2.3
import SAMBA 3.2
import SAMBA.Connection.Serial 3.2
import SAMBA.Device.SAM9xx5 3.2


SerialConnection {
    port: Script.arguments[0]

    device: SAM9xx5EK {
    }

    onConnectionOpened: {
        // initialize Low-Level applet
        initializeApplet("lowlevel")

        // initialize External RAM applet
        initializeApplet("extram")

        // initialize NAND flash applet
        initializeApplet("nandflash")

        // erase uboot partition
        applet.erase(0x0, 0x00180000)

        // write uboot
        applet.write(0x0, Script.arguments[1])
    }
}

