
import java.net.*;
import java.util.*;
public class ListName {
        public static void main(String[] args) {
            try {
                // Get all network interfaces on the system
                Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface networkInterface = networkInterfaces.nextElement();

                    // Skip virtual network interfaces
                    if (!networkInterface.isVirtual() && networkInterface.isUp()) {
                        String name = networkInterface.getName();

                        // Add "loopback" label if it's a loopback interface
                        if (networkInterface.isLoopback()) {
                            name += " (loopback)";
                        }

                        // Print the interface name
                        System.out.println("Interface: " + name);

                        // Print the MTU value
                        System.out.println("MTU: " + networkInterface.getMTU());

                        // Loop through the interface addresses and print relevant details
                        for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                            InetAddress ip = address.getAddress();
                            InetAddress broadcast = address.getBroadcast();
                            int prefixLength = address.getNetworkPrefixLength();

                            System.out.println("IP Address: " + ip.getHostAddress());
                            System.out.println("Subnet Mask Prefix Length: " + prefixLength);
                            System.out.println("Broadcast Address: " + (broadcast != null ? broadcast.getHostAddress() : "N/A"));
                        }
                        System.out.println("--------------------------------");
                    }
                }

            } catch (SocketException e) {
                System.out.println("SocketException occurred: " + e.getMessage());
            }
        }
    }
