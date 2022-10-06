package com.example.board.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;

@RestController
@RequestMapping("api/v1/board/")
public class BoardController {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    @PostMapping("")
    public ResponseEntity<?> makeNewBoard(@RequestBody Board board) {
        board.setCreatedTime(LocalDateTime.now());
        board.setUpdatedTime(LocalDateTime.now());
        boardRepository.save(board);

        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Board> getBoard(@PathVariable long id) {
        return boardRepository.findById(id);
    }

    @PutMapping("")
    public ResponseEntity<?> updateBoard(@RequestBody Board board) {
        Board newBoard = boardService.getBoardById(board.getId());
        newBoard.setId(board.getId());
        newBoard.setTitle(board.getTitle());
        newBoard.setAuthor(board.getAuthor());
        newBoard.setContent(board.getContent());
        newBoard.setUpdatedTime(LocalDateTime.now());
        boardRepository.save(newBoard);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable long id) {
        boardRepository.deleteById(id);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
