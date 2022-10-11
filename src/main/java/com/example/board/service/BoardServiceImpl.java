package com.example.board.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
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
    public Optional<Board> getBoardById(long id) throws EntityNotFoundException {
        return boardRepository.findById(id);
    }

    @Override
    public Page<Board> getBoardListToPage(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }

    @Override
    public void createBoard(Board board) throws IllegalArgumentException, OptimisticLockingFailureException {
        LocalDateTime localDateTime = LocalDateTime.now();

        board.setCreatedTime(localDateTime);
        board.setUpdatedTime(localDateTime);
        boardRepository.save(board);
    }

    @Override
    public void deleteBoard(long id) throws IllegalArgumentException {
        boardRepository.deleteById(id);
    }

    @Override
    public void updateBoard(long id, Board board)
            throws EntityNotFoundException, IllegalArgumentException, OptimisticLockingFailureException {
        Board newBoard = boardRepository.getReferenceById(id);

        newBoard.setTitle(board.getTitle());
        newBoard.setContent(board.getContent());
        newBoard.setUpdatedTime(LocalDateTime.now());
        boardRepository.save(newBoard);
    }

}
