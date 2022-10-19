package com.example.board.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.el.lang.ELArithmetic.LongDelegate;
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
    public List<Board> getAllBoards() {
        return boardRepository.findAll();
    }

    @Override
    public Optional<Board> getBoardById(long id) throws IllegalArgumentException {
        return boardRepository.findById(id);
    }

    @Override
    public Page<Board> getBoardListToPage(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return boardRepository.findAll(pageable);
    }

    @Override
    public Long createBoard(Board board) throws IllegalArgumentException, OptimisticLockingFailureException {
        LocalDateTime localDateTime = LocalDateTime.now();

        board.setCreatedTime(localDateTime);
        board.setUpdatedTime(localDateTime);
        return boardRepository.save(board).getId();
    }

    @Override
    public void deleteBoard(long id) throws IllegalArgumentException {
        boardRepository.deleteById(id);
    }

    @Override
    public Long updateBoard(long id, Board board)
            throws EntityNotFoundException, IllegalArgumentException, OptimisticLockingFailureException {
        Optional<Board> updatedBoardWrapper = boardRepository.findById(id);
        if (updatedBoardWrapper.isEmpty())
            throw new EntityNotFoundException();

        Board updatedBoard = updatedBoardWrapper.get();

        updatedBoard.setTitle(board.getTitle());
        updatedBoard.setContent(board.getContent());
        updatedBoard.setUpdatedTime(LocalDateTime.now());
        return boardRepository.save(updatedBoard).getId();
    }

}
