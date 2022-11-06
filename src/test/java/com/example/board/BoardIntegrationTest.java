package com.example.board;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;

import lombok.extern.java.Log;

@Log
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BoardIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardRepository boardRepository;

    static final String author = "IntegrationAuthor";
    static final String title = "IntegrationTest";
    static final String content = "IntegrationContent";

    static final Board board = Board.builder()
            .title(title).author(author).content(content).build();

    @BeforeEach
    public void clear() {
        boardRepository.deleteAll();
        log.info("BeforeEach clear");
    }

    @Test
    public void When_CreateBoard_Expect_Success() {
        HttpEntity<Board> request = new HttpEntity<Board>(board);

        ResponseEntity<String> response = restTemplate.postForEntity(getUrl(0L), request, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void When_GetBoard_If_ExistBoard_Expect_Success() {
        boardService.createBoard(board);

        ResponseEntity<Board> response = restTemplate.getForEntity(getUrl(1L), Board.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Board responseBoard = response.getBody();
        Assertions.assertNotNull(responseBoard);

        Assertions.assertEquals(1L, responseBoard.getId());
        Assertions.assertEquals(title, responseBoard.getTitle());
        Assertions.assertEquals(author, responseBoard.getAuthor());
        Assertions.assertEquals(content, responseBoard.getContent());
    }

    @Test
    public void When_GetBoard_If_NonExistBoard_Expect_Fail() {
        ResponseEntity<Board> response = restTemplate.getForEntity(getUrl(2L), Board.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void When_GetAllBoards_Expect_Success() {
        boardService.createBoard(board);
        boardService.createBoard(Board.builder().title(title).author(author).content(content)
                .build());
        boardService.createBoard(Board.builder().title(title).author(author).content(content)
                .build());

        ResponseEntity<Board[]> response = restTemplate.getForEntity(getUrl(0L), Board[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Board[] responseBoard = response.getBody();
        Assertions.assertNotNull(responseBoard);

        Long bid = 1L;
        for (Board board : responseBoard) {
            Assertions.assertEquals(bid, board.getId());
            Assertions.assertEquals(title, board.getTitle());
            Assertions.assertEquals(author, board.getAuthor());
            Assertions.assertEquals(content, board.getContent());
            ++bid;
        }
    }

    @Test
    public void When_UpdateBoard_If_ExistBoard_Expect_Success() {
        boardService.createBoard(board);

        String updatedTitle = "UpdatedTitle";
        String updatedContent = "UpdatedContent";
        Board newBoard = Board.builder().title(updatedTitle)
                .content(updatedContent).build();
        HttpEntity<Board> request = new HttpEntity<Board>(newBoard);

        ResponseEntity<String> response = restTemplate.exchange(getUrl(1L), HttpMethod.PUT, request, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Board updatedBoard = boardService.getBoardById(1L).get();
        Assertions.assertEquals(updatedTitle, updatedBoard.getTitle());
        Assertions.assertEquals(updatedContent, updatedBoard.getContent());
    }

    @Test
    public void When_UpdateBoard_If_NonExistBoard_Expect_Fail() {
        String updatedTitle = "UpdatedTitle";
        String updatedContent = "UpdatedContent";
        Board newBoard = Board.builder().title(updatedTitle)
                .content(updatedContent).build();
        HttpEntity<Board> request = new HttpEntity<Board>(newBoard);

        ResponseEntity<String> response = restTemplate.exchange(getUrl(2L), HttpMethod.PUT, request, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void When_DeleteBoard_If_ExistBoard_Expect_Success() {
        boardService.createBoard(board);

        ResponseEntity<String> response = restTemplate.exchange(getUrl(1L), HttpMethod.DELETE, null, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void When_DeleteBoard_If_NonExistBoard_Expect_Fail() {
        ResponseEntity<String> response = restTemplate.exchange(getUrl(2L), HttpMethod.DELETE, null, String.class);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    public String getUrl(Long id) {
        if (id == 0)
            return "http://localhost:" + port + "/api/v1/boards";
        else
            return "http://localhost:" + port + "/api/v1/boards/" + id;
    }
}