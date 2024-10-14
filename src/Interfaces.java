import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.net.*;
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
public class Interfaces {

    /**
     * @param args the command line arguments
     */
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
    }

}


public class ListInterfaces {
    public static void main(String[] args) {
        try { // Get all network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            // Iterate through each network interface
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                // Check if it's a virtual interface
                if (networkInterface.isVirtual()) {
                    continue;
                }
                //Print interface name
                String interfaceName =networkInterface.getName();
                if (networkInterface.isLoopback()) {
                    interfaceName +=
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
}
