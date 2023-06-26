package com.example.socks.service;

import com.example.socks.model.Socks;

public interface SocksService {
    Integer getSocksStock(String color, String operation, Integer cottonPart);
    Boolean socksIncome(Socks socks);
    Boolean socksOutcome(Socks socks);
}
