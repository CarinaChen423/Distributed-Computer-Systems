import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

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
