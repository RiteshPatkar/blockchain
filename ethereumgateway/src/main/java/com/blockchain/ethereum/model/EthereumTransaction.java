package com.blockchain.ethereum.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.ToString;

@JsonInclude(Include.NON_NULL)
@ToString(callSuper=true)
@Data
public class EthereumTransaction {

    private String id;
    private int fromId;
    private int toId;
    private long value;
    private boolean accepted;
}
