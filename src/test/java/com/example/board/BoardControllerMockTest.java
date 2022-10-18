package com.example.board;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.board.controller.BoardController;
import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(BoardController.class)
public class BoardControllerMockTest {

    @MockBean
    private BoardService boardService;

    @MockBean
    private BoardRepository boardRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Long id = 1L;
    private static final String author = "John";
    private static final String title = "Test1";
    private static final String content = "Content1";

    private static final Board board = Board.builder()
            .id(id).title(title).author(author).content(content).build();

    @Test
    public void shouldCreateBoard() throws Exception {

        mockMvc.perform(post("/api/v1/boards").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(board)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    public void shouldGetBoardSuccess() throws Exception {

        when(boardService.getBoardById(id)).thenReturn(Optional.of(board));

        mockMvc.perform(get("/api/v1/boards/{id}", id)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.author").value(author))
                .andDo(print());
    }

    @Test
    public void shouldGetBoardFailure() throws Exception {

        when(boardService.getBoardById(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/boards/{id}", id))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldGetAllBoards() throws Exception {
        List<Board> boards = new ArrayList<Board>(
                Arrays.asList(
                        board,
                        Board.builder().id(id + 1).title(title).author(author).content(content).build(),
                        Board.builder().id(id + 2).title(title).author(author).content(content).build()));

        when(boardService.getAllBoards()).thenReturn(boards);

        mockMvc.perform(get("/api/v1/boards"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateBoardSuccess() throws Exception {
        Board updatedBoard = Board.builder().id(id)
                .title("updatedTitle").author("updatedAuthor")
                .content("updatedContent").build();

        when(boardService.getBoardById(id)).thenReturn(Optional.of(board));

        mockMvc.perform(put("/api/v1/boards/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBoard)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldUpdateBoardFailure() throws Exception {
        Board updatedBoard = Board.builder().id(id)
                .title("updatedTitle").author("updatedAuthor")
                .content("updatedContent").build();

        // doReturn(Optional.empty()).when(boardRepository).findById(id);
        when(boardRepository.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/boards/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBoard)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void shouldDeleteBoard() throws Exception {
        doNothing().when(boardService).deleteBoard(id);

        mockMvc.perform(delete("/api/v1/boards/{id}", id))
        .andExpect(status().isOk())
        .andDo(print());
    }
}
