package com.example.socks.impl;

import com.example.socks.exception.NoSuchItemInStockException;
import com.example.socks.exception.NotEnoughInStockException;
import com.example.socks.exception.UnknownOperationException;
import com.example.socks.model.Socks;
import com.example.socks.repository.SocksRepository;
import com.example.socks.service.SocksService;

import java.security.InvalidParameterException;

import static java.lang.Boolean.TRUE;

public class SocksServiceImpl implements SocksService {
    private final SocksRepository socksRepository;

    public SocksServiceImpl(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    @Override
    public Integer getSocksStock(String color, String operation, Integer cottonPart) {
        validateCottonPart(cottonPart);

        Integer socksCount = null;
        switch (operation) {
            case "moreThan":
                socksCount = socksRepository.getSocksCountByColorAndMoreThanCottonPart(color, cottonPart);
                break;
            case "lessThan":
                socksCount = socksRepository.getSocksCountByColorAndLessThanCottonPart(color, cottonPart);
                break;
            case "equal":
                socksCount = socksRepository.getSocksCountByColorAndEqualCottonPart(color, cottonPart);
                break;
            default:
                throw new UnknownOperationException();
        }

        if (socksCount == null) {
            return 0;
        } else {
            return socksCount;
        }
    }

    @Override
    public Boolean socksIncome(Socks socks) {
        validateCottonPart(socks.getCottonPart());
        validateQuantity(socks.getQuantity());

        Socks socksExist = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (socksExist == null) {
            socksRepository.save(socks);
        } else {
            socksExist.setQuantity(socksExist.getQuantity() + socks.getQuantity());
            socksRepository.save(socksExist);
        }

        return TRUE;
    }

    @Override
    public Boolean socksOutcome(Socks socks) {
        validateCottonPart(socks.getCottonPart());
        validateQuantity(socks.getQuantity());

        Socks socksExist = socksRepository.findByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if (socksExist == null) {
            throw new NoSuchItemInStockException("Товара нет на складе.");
        } else if (socksExist.getQuantity() < socks.getQuantity()) {
            throw new NotEnoughInStockException("Не достаточно товара на складе.");
        } else {
            socksExist.setQuantity(socksExist.getQuantity() - socks.getQuantity());
            socksRepository.save(socksExist);
        }

        return TRUE;
    }

    private void validateCottonPart(Integer cottonPart) {
        if (cottonPart < 0 || cottonPart > 100) {
            throw new InvalidParameterException("Процент содержания хлопка должен быть от 0 до 100.");
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity < 1) {
            throw new InvalidParameterException("Количество пар носков должно быть больше 0.");
        }
    }
}
