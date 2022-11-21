package com.example.springserver.hashtable;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HashTable {

    static class Node {
        String key;
        String value;
        Node next;
        int frequency = 0;

        Node (String key, String value, Node next) {
            this.key = key;
            this.next = next;
            this.value = value;
        }

        boolean hasNext() {
            return next != null;
        }
    }

    final int size;
    Node[] table;

    public HashTable(int size) {
        this.size = size;
        table = new Node[size];
    }

    int hash(String key) {
        return key.chars().reduce(0, (sum, element) -> sum += element) % size;
    }

    public void put(String key, String value) {
        int hash = hash(key);
        Node node = table[hash];

        if (node == null) {
            node = new Node(key, value, null);
            table[hash] = node;
            return;
        }

        while (node != null) {
            if (node.key.equals(key)) {
                break;
            }

            node = node.next;
        }

        if (node == null) {
            node = new Node(key, value, table[hash]);
            table[hash] = node;
        } else {
            node.value = value;
        }

        print();
    }

    public String get(String key) {
        int hash = hash(key);
        Node node = table[hash];

        while (node != null) {
            if (node.key .equals(key)) {
                node.frequency++;

                reorderList(table[hash], hash);
                print();

                return node.value;
            }

            node = node.next;
        }

        return null;
    }

    private void reorderList(Node node, int hash) {
        while (node.hasNext()) {
            if (node.frequency < node.next.frequency) {
                Node nodeToMove = node.next;

                node.next = nodeToMove.next;

                Node iter = table[hash];

                if (iter.frequency < nodeToMove.frequency) {
                    table[hash] = nodeToMove;
                    nodeToMove.next = iter;
                    return;
                }

                while (iter.hasNext()) {
                    if (nodeToMove.frequency > iter.next.frequency) {
                        nodeToMove.next = iter.next;
                        iter.next = nodeToMove;
                        return;
                    }

                    iter = iter.next;
                }
            }

            node = node.next;
        }
    }

    private void print() {
        for (int i = 0; i < size; i++) {
            Node node = table[i];
            System.out.print(i);

            while (node != null) {
                System.out.print(" -> " + node.key + " (" + node.frequency + ")");
                node = node.next;
            }

            System.out.println();
        }
    }

    public List<NodeDto> getAll() {
        List<NodeDto> result = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Node node = table[i];

            while (node != null) {
                result.add(new NodeDto(node.key, node.value));

                node = node.next;
            }
        }

        return result;
    }

    public void remove(String key) {
        int hash = hash(key);
        Node node = table[hash];

        if (node != null && node.key.equals(key)) {
            table[hash] = node.next;
            node = null;

            print();
            return;
        }

        Node previous = null;
        while (node != null) {
            if (node.key.equals(key)) {
                previous.next = node.next;
                node = null;

                print();
                return;
            }

            previous = node;
            node = node.next;
        }
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }
}
