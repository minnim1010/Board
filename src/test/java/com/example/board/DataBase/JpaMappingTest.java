package com.example.board.DataBase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JpaMappingTest {

    private final String title = "TestTitle";
    private final String content = "TestContent";

    @Autowired
    private BoardRepository boardRepository;

    @Before
    public void init() {
        boardRepository.save(Board.builder()
                .title(title)
                .content(content)
                .build());
    }

    @Test
    public void test() {
        Board board = boardRepository.getReferenceById((long) 1);
        assertThat(board.getTitle(), is(title));
        assertThat(board.getContent(), is(content));
    }

}
