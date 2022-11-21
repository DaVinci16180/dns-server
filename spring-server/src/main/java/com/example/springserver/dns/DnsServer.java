package com.example.springserver.dns;

import com.example.springserver.hashtable.HashTable;
import org.springframework.stereotype.Component;

@Component
public class DnsServer {

    private final HashTable hashTable = new HashTable(97);

    public HashTable getHashTable() {
        return hashTable;
    }
}
