import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.Collections;
import java.util.Scanner;

public class Serwer {
    public static void main(String[] args) throws IgniteException {

        IgniteConfiguration cfg = new IgniteConfiguration();
        Ignite serwer = Ignition.start(cfg);

        //-------------------------------
        Scanner in = new Scanner(System.in);
        String wybor = "";

        while (wybor != "0") {
            System.out.println("----------------------------------------------- \n" +
                    "Każda cyfra odpowiada następującej operacji: \n" +
                    "0 - wychodzi z programu \n" +
                    "1 - przetwórz (przedluza wycieczke o jeden dzien dla wybranego miasta)\n" +
                    "2 - test przetworzenie)\n" +
                    "----------------------------------------------- \n"
            );

            wybor = in.nextLine();

            switch (wybor) {
                case "1":
                    Przetworzenie.main(serwer);
                    break;
                case "2":
                    Operacja.Przetworzenie(serwer);
                    break;
                default:
                    System.out.println("\u001B[31mbledny znak \u001B[37m");
            }
        }
    }

}