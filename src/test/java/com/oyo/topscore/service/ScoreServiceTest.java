package com.oyo.topscore.service;


import com.oyo.topscore.model.Score;
import com.oyo.topscore.repository.ScoreRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {

    @InjectMocks
    private ScoreService scoreService;

    @Mock
    ScoreRepository scoreRepositoryMock;

    private final static int PAGE_NO = 0;
    private final static int PAGE_SIZE = 10;

    @Test
    @DisplayName("id exist in db, when get score, then Score is return")
    void getScoreById_success() {
        Mockito.when(scoreRepositoryMock.findById(anyLong())).thenReturn(Optional.of(getScore()));
        Score result = scoreService.getScoreById(1);
        assertNotNull(result);
        assertEquals(result.getId(), 1);
        assertEquals(result.getPlayer(), "testPlayer");
        assertEquals(result.getScore(), 100);

    }

    @Test
    @DisplayName("id not exist in db, when get score, then null is return")
    void getScoreById_idNotFound(){
        Mockito.when(scoreRepositoryMock.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        Score result = scoreService.getScoreById(1);
        assertNull(result);
    }

    @Test
    @DisplayName("no query param pass, when get score list, then all result return")
    void getScoreList_success_no_param() {
        Pageable pageable = PageRequest.of(PAGE_NO, PAGE_SIZE);
        Mockito.when(scoreRepositoryMock.findAll(pageable)).thenReturn(getPaginationScore(pageable));
        Page<Score> result = scoreService.getScoreList(null, null, null, PAGE_NO, PAGE_SIZE);
        assertEquals(result.getTotalElements(), 3);
    }


//    @Test
//    void saveScore() {
//    }
//
//    @Test
//    void deleteScoreById() {
//    }
//
//    @Test
//    void getPlayerScoreList() {
//    }
//
//    @Test
//    void getPlayerTopScore() {
//    }
//
//    @Test
//    void getPlayerLowestScore() {
//    }
//
//    @Test
//    void getPlayerAverageScore() {
//    }


    /**
     * Create score object
     *
     * @return Score
     */
    private Score getScore() {
        return Score.builder()
                .id(1)
                .score(100)
                .player("testPlayer")
                .time(new Date())
                .build();
    }

    /**
     * create pagination score object
     *
     * @return Page<Score>
     */
    private Page<Score> getPaginationScore(Pageable pageable) {
        List<Score> list = getSinglePlayerScoreList();
        return new PageImpl(list, pageable, list.size());
    }

    /**
     * create score list
     *
     * @return List<Score>
     */
    private List<Score> getSinglePlayerScoreList() {
        Score score1 = Score.builder()
                .id(1)
                .score(100)
                .player("testPlayer")
                .time(new Date())
                .build();
        Score score2 = Score.builder()
                .id(2)
                .score(200)
                .player("testPlayer")
                .time(new Date())
                .build();
        Score score3 = Score.builder()
                .id(3)
                .score(300)
                .player("testPlayer")
                .time(new Date())
                .build();
        return Arrays.asList(score1, score2, score3);
    }
}