package com.example.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public Board getBoardById(long id) {
        try {
            return boardRepository.getReferenceById(id);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    @Override
    public Page<Board> getBoardListToPage(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }

}
