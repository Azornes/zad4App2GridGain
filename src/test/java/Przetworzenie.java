import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteRunnable;


import javax.cache.Cache;
import java.util.Iterator;
import java.util.Scanner;


public class Przetworzenie {

    public static void main(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);

        //IgniteConfiguration cfg = new IgniteConfiguration();
        //cfg.setExecutorConfiguration(new ExecutorConfiguration("myPool").setSize(16));

        Scanner in2 = new Scanner(System.in);
        System.out.println("Podaj nazwę atrakcji dla ktrócyh chcesz powiękdzyć długość wycieczki");
        String nazwa = "";
        nazwa = in2.nextLine();

        client.compute().run(new InnerRunnable(nazwa, atrakcjaCache));
    }


    public static class InnerRunnable implements IgniteRunnable{

        String nazwa = "";
        IgniteCache<Long, Atrakcja> atrakcjaCache;

        public InnerRunnable(String nazwa, IgniteCache<Long, Atrakcja> atrakcjaCache) {
            this.atrakcjaCache = atrakcjaCache;
            this.nazwa = nazwa;
        }

        @Override
        public void run() {
            Iterator<Cache.Entry<Long, Atrakcja>> itr = atrakcjaCache.iterator();

            while(itr.hasNext()) {
                Cache.Entry<Long, Atrakcja> entry = itr.next();

                Atrakcja atrakcja = entry.getValue();
                Long key = entry.getKey();

                int dlugoscwycieczki = atrakcja.getDlugoscwycieczki();
                String nazwa1 = atrakcja.getNazwa();

                if (nazwa.equals(nazwa1)) {
                    System.out.println("Before Processing = " + atrakcja);
                    atrakcja.setDlugoscwycieczki(dlugoscwycieczki + 1);
                    System.out.println("After Processing = " + atrakcja);
                    atrakcjaCache.replace(key, atrakcja);
                }
            }
        }
    }
}

