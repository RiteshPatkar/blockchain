package com.blockchain.ethereum.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import com.blockchain.ethereum.model.EthereumTransaction;

import java.io.IOException;
import java.math.BigInteger;

@Service
public class EthereumService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(EthereumService.class);

    @Autowired
    Web3j web3j;

    public EthereumTransaction process(EthereumTransaction trx) throws IOException {
        EthAccounts accounts = web3j.ethAccounts().send();
//        for(String account: accounts.getAccounts()) {
//        	web3j.personal.unlockAccount(web3j.personal.listAccounts[0],"<password>", 15000)
//        }
        EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(accounts.getAccounts().get(trx.getFromId()), DefaultBlockParameterName.LATEST).send();
        Transaction transaction = Transaction.createEtherTransaction(accounts.getAccounts().get(trx.getFromId()), transactionCount.getTransactionCount(), BigInteger.valueOf(trx.getValue()), BigInteger.valueOf(21_000), accounts.getAccounts().get(trx.getToId()),BigInteger.valueOf(trx.getValue()));
        EthSendTransaction response = web3j.ethSendTransaction(transaction).send();
        if (response.getError() != null) {
            trx.setAccepted(false);
            LOGGER.info("Tx rejected: {}", response.getError().getMessage());
            return trx;
        }
        trx.setAccepted(true);
        String txHash = response.getTransactionHash();
        LOGGER.info("Tx hash: {}", txHash);
        trx.setId(txHash);
        EthGetTransactionReceipt receipt = web3j.ethGetTransactionReceipt(txHash).send();
        if (receipt.getTransactionReceipt().isPresent()) {
            LOGGER.info("Tx receipt: {}", receipt.getTransactionReceipt().get().getCumulativeGasUsed().intValue());
        }
        return trx;
    }

}
