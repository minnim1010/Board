package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.board.model.Board;
import com.example.board.service.BoardService;
import com.example.board.util.EntityToJsonConverter;

@RestController
@RequestMapping("/api/v1/boards")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping("")
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        try {
            boardService.createBoard(board);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<Board> getAllBoards() {
        return boardService.getAllBoards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoard(@PathVariable long id) {
        EntityToJsonConverter<Board> entitytoJson = new EntityToJsonConverter<Board>();
        String result;

        try {
            Optional<Board> board = boardService.getBoardById(id);
            result = entitytoJson.convertEntityToJsonString(board.get());
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable long id, @RequestBody Board board) {
        try {
            boardService.updateBoard(id, board);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable long id) {
        try {
            boardService.deleteBoard(id);
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
