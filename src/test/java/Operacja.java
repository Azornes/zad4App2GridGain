import java.util.*;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.TextQuery;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.lang.IgniteClosure;

import javax.cache.Cache;

public class Operacja {

    public static void pobierzPoNazwie(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);

        //IgniteCache<Long, Atrakcja> atrakcjaCache = client.cache("atrakcje");

        Scanner in = new Scanner(System.in);
        System.out.println("Podaj nazwę atrakcji którą chcesz wyszukać");
        String wybor = "";
        wybor = in.nextLine();
        String finalWybor = wybor;

        IgniteBiPredicate<Long, Atrakcja> filter = (key, p) -> p.getNazwa().equals(finalWybor);

        try (QueryCursor<Cache.Entry<Long, Atrakcja>> qryCursor = atrakcjaCache.query(new ScanQuery<>(filter))) {
            qryCursor.forEach(
                    entry -> System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()));
        }
    }

    public static void pobierzPoDacie(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        personCfg.setName("Atrakcja");
        personCfg.setIndexedTypes(Long.class, Atrakcja.class);
        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);

        SqlFieldsQuery sql = new SqlFieldsQuery("select concat('Atrakcja ',nazwa, ' dlugosc wycieczki: ', dlugoscwycieczki) from Atrakcja WHERE (nazwa='Rzym' OR nazwa = 'Karaiby') AND (dlugoscwycieczki BETWEEN 3 AND 10)");

        try (QueryCursor<List<?>> cursor = atrakcjaCache.query(sql)) {
            for (List<?> row : cursor)
                System.out.println("Sql wynik: " + row.get(0));
        }
    }

    public static void pobierzWszystko(Ignite client) throws IgniteException {

        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        //.setBackups(1);
        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);
        //IgniteCache<Long, Atrakcja> atrakcjaCache = client.cache("atrakcje");

        Iterator<Cache.Entry<Long, Atrakcja>> itr = atrakcjaCache.iterator();
        while(itr.hasNext()) {
            Atrakcja object = itr.next().getValue();
            System.out.println(object);
        }
    }
/*
    public static void agregacja( HazelcastInstance client) throws UnknownHostException {
        //final HazelcastInstance client = HazelcastClient.newHazelcastClient();
        IMap<Long, Atrakcja> atrakcje = client.getMap("atrakcje");
        System.out.println(atrakcje.aggregate(Aggregators.integerMin("dlugoscwycieczki")));
    }

*/

    final private static Random r = new Random(System.currentTimeMillis());

    public static void zapiszDoMapy(Ignite client) throws IgniteException {

        //CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("atrakcje");
        //personCfg.setBackups(1);
        //IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);

        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        //personCfg.setName("Atrakcja");

        personCfg.setIndexedTypes(Long.class, Atrakcja.class);

        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);

        //IgniteCache<Long, Atrakcja> atrakcjaCache = client.cache("atrakcje");

        Long key1 = (long) Math.abs(r.nextInt());
        byte key11 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja1 = new Atrakcja("Rzym", key11 + 1);
        System.out.println("PUT " + key1 + " => " + atrakcja1);
        atrakcjaCache.put(key1, atrakcja1);

        Long key2 = (long) Math.abs(r.nextInt());
        byte key12 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja2 = new Atrakcja("Karaiby", key12+ 1);
        atrakcjaCache.put(key2, atrakcja2);
        System.out.println("PUT " + key2 + " => " + atrakcja2);
        Long key3 = (long) Math.abs(r.nextInt());
        byte key13 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja3 = new Atrakcja("Gdańsk", key13+ 1);
        atrakcjaCache.put(key3, atrakcja3);
        System.out.println("PUT " + key3 + " => " + atrakcja3);
        Long key4 = (long) Math.abs(r.nextInt());
        byte key14 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja4 = new Atrakcja("Kair", key14+ 1);
        atrakcjaCache.put(key4, atrakcja4);
        System.out.println("PUT " + key4 + " => " + atrakcja4);
        Long key5 = (long) Math.abs(r.nextInt());
        byte key15 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja5 = new Atrakcja("Rio de Janeiro", key15+ 1);
        atrakcjaCache.put(key5, atrakcja5);
        System.out.println("PUT " + key5 + " => " + atrakcja5);
        Long key6 = (long) Math.abs(r.nextInt());
        byte key16 = (byte) Math.abs(r.nextInt(30));
        Atrakcja atrakcja6 = new Atrakcja("Praga", key16+ 1);
        atrakcjaCache.put(key6, atrakcja6);
        System.out.println("PUT " + key6 + " => " + atrakcja6);

    }

    public static void usunWszystko(Ignite client ) throws IgniteException {
        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);
        //IgniteCache<Long, Atrakcja> atrakcjaCache = client.cache("atrakcje");
        atrakcjaCache.destroy();

    }

    public static void Przetworzenie(Ignite client ) throws IgniteException {

        CacheConfiguration<Long, Atrakcja> personCfg = new CacheConfiguration<Long, Atrakcja>("Atrakcja");
        IgniteCache<Long, Atrakcja> atrakcjaCache = client.getOrCreateCache(personCfg);

        Scanner in2 = new Scanner(System.in);
        System.out.println("Podaj nazwę atrakcji dla których chcesz powiększych długość wycieczki");
        String nazwa = "";
        nazwa = in2.nextLine();

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
