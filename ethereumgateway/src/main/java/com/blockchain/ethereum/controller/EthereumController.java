package com.blockchain.ethereum.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.blockchain.ethereum.model.EthereumTransaction;
import com.blockchain.ethereum.service.EthereumService;

@RestController
public class EthereumController {

	@Autowired
    private EthereumService service;

    @PostMapping("/transaction")
    public EthereumTransaction execute(@RequestBody EthereumTransaction transaction) throws IOException {
        return service.process(transaction);
    }

}
