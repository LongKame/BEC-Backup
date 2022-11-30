package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Week implements Serializable {
    private String start_date;
    private String end_date;

    public Week(String start_date, String end_date) {
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
