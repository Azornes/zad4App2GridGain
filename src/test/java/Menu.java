import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Scanner;



public class Menu {

    public static void main(String[] args) throws UnknownHostException {

        IgniteConfiguration cfg = new IgniteConfiguration();
        cfg.setClientMode(true);
        Ignite client = Ignition.start(cfg);


        Scanner in = new Scanner(System.in);
        String wybor = "";

        while (wybor != "0") {
            System.out.println("----------------------------------------------- \n" +
                    "Każda cyfra odpowiada następującej operacji: \n" +
                    "0 - wychodzi z programu \n" +
                    "1 - zapisz (dodaje dane)\n" +
                    "2 - aktualizuj (zmienia litery w nazwie ) \n" +
                    "3 - kasuj (usuwa wszystkie dane)\n" +
                    "4 - pobierz (wyswietla po nazwie) \n" +
                    "5 - pobierz (wyswietla przez sql Rzym lub Karaiby tylko te które trwają od 3 do 10 dni)\n" +
                    "6 - pobierz (wyswietla wszystko)\n" +
                    "7 - przetwórz po stronie serwera (dodatkowy dzień wycieczki dla wybranej atrakcji)\n" +
                    "8 - przetwórz po stronie klienta (dodatkowy dzień wycieczki dla wybranej atrakcji)\n" +
                    "----------------------------------------------- \n"
            );

            wybor = in.nextLine();

            switch (wybor) {
                case "1":
                    Operacja.zapiszDoMapy(client);
                    break;
                case "2":
                    ZmianaLiter.main(client);
                    break;
                case "3":
                    Operacja.usunWszystko(client);
                    break;
                case "4":
                    Operacja.pobierzPoNazwie(client);
                    break;
                case "5":
                    Operacja.pobierzPoDacie(client);
                    break;
                case "6":
                    Operacja.pobierzWszystko(client);
                    break;
                case "7":
                    Przetworzenie.main(client);
                    break;
                case "8":
                    Operacja.Przetworzenie(client);
                    break;
                case "9":
                    //Operacja.agregacja(client);
                    break;
                case "10":
                    //HExecutorService.main(client);
                    break;
                default:
                    System.out.println("\u001B[31mbledny znak \u001B[37m");
            }
        }
    }
}
