package Lab2;

import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;

/*Network interfaces
        1. Compile and run the program delivered by the lecturer.
        2. Create the program that will show the details of the selected network interface (its name, MAC
        address and IP numbers attached to it). Use the getByName(), getHardwareAddress() and
        getInetAddresses() methods. The name of the qerried interface should be provided as the
        program’s parameter.
        3. Create the program that will list names (getName()) of all the non-virtual network interfaces
        (including the loopback address – in this case add the “loopback” string) with their MTU (use the
        getMTU() method), IP addresses, subnet masks, and broadcast addresses attached to them (use the
        methods of the InterfaceAddress type: getAddress(), getBroadcast().
        getNetworkPrefixLength()). */

/**
 *
 * @author Dell
 */


public class InterfaceDetails {
    public static void main(String[] args) {

        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface thisInterface;
            while (networkInterfaces.hasMoreElements())
            {
                thisInterface = networkInterfaces.nextElement();
                System.out.println(thisInterface.getDisplayName() + ": " + Arrays.toString(thisInterface.getHardwareAddress()));
            }

            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
            System.out.println("Interface name : address : broadcast");
            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                System.out.println(networkInterface.getName() + " : " +  address.getAddress() + " : " + address.getBroadcast());
            }
        }
        catch(SocketException | UnknownHostException e) {

        }

        if (args.length < 1) {
            System.out.println("Please provide the network interface name as a parameter.");
            return;
        }

        try {
            // Fetch the interface by its name provided as an argument
            NetworkInterface networkInterface = NetworkInterface.getByName(args[0]);

            if (networkInterface == null) {
                System.out.println("No such interface found.");
                return;
            }

            // Print the interface name
            System.out.println("Interface Name: " + networkInterface.getDisplayName());

            // Get the MAC address
            byte[] mac = networkInterface.getHardwareAddress();
            if (mac != null) {
                StringBuilder macStr = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    macStr.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                System.out.println("MAC Address: " + macStr.toString());
            } else {
                System.out.println("MAC Address not available.");
            }

            // Get and print the IP addresses attached to the interface
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                System.out.println("IP Address: " + inetAddress.getHostAddress());
            }

        } catch (SocketException e) {
            System.out.println("SocketException occurred: " + e.getMessage());
        }
    }
}
