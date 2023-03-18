package com.example.mscatalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rating <T>{
    private T object;
    private String name;
    //private Enum type;
    private int rate;
    private String description;
}
