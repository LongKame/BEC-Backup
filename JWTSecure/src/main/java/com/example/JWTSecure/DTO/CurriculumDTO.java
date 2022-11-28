package com.example.JWTSecure.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CurriculumDTO  implements Serializable {

    private Long id;
    private Long course_id;
    private String curriculum_name;
    private String link_url;
    private String description;
    private String created_at;
    private String updated_at;
    private Long level_id;
    private String course_name;
    private int page;
    private int pageSize;
    private String key_search;
}

