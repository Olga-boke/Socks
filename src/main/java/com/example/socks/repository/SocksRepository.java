package com.example.socks.repository;

import com.example.socks.model.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SocksRepository extends JpaRepository<Socks, Long> {
    Socks findByColorAndCottonPart(String color, Integer cottonPart);

    @Query(value = "select sum(quantity) from socks where color = :color and cotton_part >= :cottonPart", nativeQuery = true)
    Integer getSocksCountByColorAndMoreThanCottonPart(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query(value = "select sum(quantity) from socks where color = :color and cotton_part < :cottonPart", nativeQuery = true)
    Integer getSocksCountByColorAndLessThanCottonPart(@Param("color") String color, @Param("cottonPart") int cottonPart);

    @Query(value = "select quantity from socks where color = :color and cotton_part = :cottonPart", nativeQuery = true)
    Integer getSocksCountByColorAndEqualCottonPart(@Param("color") String color, @Param("cottonPart") int cottonPart);
}
