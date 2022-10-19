package com.example.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.board.model.Board;

public interface BoardService {
    Long createBoard(Board board);

    Long updateBoard(long id, Board board);

    void deleteBoard(long id);

    List<Board> getAllBoards();

    Optional<Board> getBoardById(long id);

    Page<Board> getBoardListToPage(Pageable pageable);
}
