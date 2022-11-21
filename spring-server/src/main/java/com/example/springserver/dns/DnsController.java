package com.example.springserver.dns;

import com.example.springserver.hashtable.HashTable;
import com.example.springserver.hashtable.NodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class DnsController {

    @Autowired
    DnsServer dnsServer;

    @Autowired
    DnsServerService dnsServerService;

    @GetMapping("/fetch/{domain}")
    public ResponseEntity<String> fetch(@PathVariable("domain") String domain) {
        String result = dnsServerService.fetch(domain);

        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/create")
    public void create(@RequestBody HashMap<String, String> params) {
        dnsServerService.create(params.get("domain"), params.get("ip"));
    }

    @GetMapping("/massCreate/{amount}")
    public void massCreate(@PathVariable("amount") int amount) throws FileNotFoundException {
        dnsServerService.massCreate(amount);
    }

    @GetMapping("/all")
    public List<NodeDto> getAll() {
        return dnsServer.getHashTable().getAll();
    }

    @DeleteMapping("/delete/{key}")
    public void delete(@PathVariable("key") String key) {
        dnsServer.getHashTable().remove(key);
    }
}
