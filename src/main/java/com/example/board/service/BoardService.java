package com.example.board.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.board.model.Board;

public interface BoardService {
    void createBoard(Board board);

    void updateBoard(long id, Board board);

    void deleteBoard(long id);

    Optional<Board> getBoardById(long id);

    Page<Board> getBoardListToPage(Pageable pageable);
}
