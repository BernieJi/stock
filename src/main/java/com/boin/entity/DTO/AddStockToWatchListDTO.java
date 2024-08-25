package com.boin.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStockToWatchListDTO {

    private String userId;

    private String watchlistName;

    private String code;
}
