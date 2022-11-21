package com.example.springserver.dns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class DnsServerService {

    @Autowired
    DnsServer dnsServer;

    public String fetch(String key) {
        return dnsServer.getHashTable().get(key);
    }

    public void create(String domain, String ip) {
        dnsServer.getHashTable().put(domain, ip);
    }

    public void massCreate(int amount) throws FileNotFoundException {
        File myObj = new File("src/main/resources/static/names.txt");
        Scanner myReader = new Scanner(myObj);

        List<String> urls = new ArrayList<>();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            urls.add(data);
        }

        Random rand = new Random();
        rand.setSeed(new Date().getTime());

        for (int i = 0; i < amount; i++) {
            int a = rand.nextInt(250);
            int b = rand.nextInt(250);

            int x = rand.nextInt(100, 1000);
            int y = rand.nextInt(10);

            dnsServer.getHashTable().put("www." + urls.get(a) + urls.get(b) + ".com", x + ".0.0." + y);
        }

        myReader.close();
    }
}
