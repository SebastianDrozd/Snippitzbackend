package com.Snippitz.snipzapp.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
   // @NotBlank(message = "Please add department name")
    //@Length(max = 5, min= 1)
    //@Size(max = 10, min = 0)
    private String title;
    private String description;
    private String language;
    private long likes;
}
