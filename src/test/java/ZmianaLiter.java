import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteRunnable;

import javax.cache.Cache;
import java.util.Iterator;



public class ZmianaLiter {

    public static void main(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);
        client.compute().run(new InnerRunnable(atrakcjaCache));
    }


    public static class InnerRunnable implements IgniteRunnable{
        IgniteCache<Long, Atrakcja> atrakcjaCache;

        public InnerRunnable(IgniteCache<Long, Atrakcja> atrakcjaCache) {
            this.atrakcjaCache = atrakcjaCache;
        }

        @Override
        public void run() {
            Iterator<Cache.Entry<Long, Atrakcja>> itr = atrakcjaCache.iterator();

            while(itr.hasNext()) {
                Cache.Entry<Long, Atrakcja> entry = itr.next();

                Atrakcja atrakcja = entry.getValue();
                Long key = entry.getKey();
                String nazwa = atrakcja.getNazwa();

                System.out.println("Before Processing = " + atrakcja);
                if (nazwa.equals(nazwa.toLowerCase())) {
                    nazwa = nazwa.toUpperCase();
                    atrakcja.setNazwa(nazwa);
                } else{
                    nazwa = nazwa.toLowerCase();
                    atrakcja.setNazwa(nazwa);
                }
                System.out.println("After Processing = " + atrakcja);
                atrakcjaCache.replace(key, atrakcja);

            }
        }
    }
}

