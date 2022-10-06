package com.example.board.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
