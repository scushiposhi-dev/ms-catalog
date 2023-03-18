package com.example.mscatalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Catalog<T> {
    private String id;
    private Info<T> object;
    private Rating<T> rating;
}
