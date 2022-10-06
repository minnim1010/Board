package com.example.board.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.board.model.Board;

public interface BoardService {
    Board getBoardById(long id);

    Page<Board> getBoardListToPage(Pageable pageable);

}
