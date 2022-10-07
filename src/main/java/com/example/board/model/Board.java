package com.example.board.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Board {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String author;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime createdTime;

    @Column
    private LocalDateTime updatedTime;

    @Builder
    public Board(long id, String author, String title, String content,
            LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
