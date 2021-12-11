package com.oyo.topscore.service;

import com.oyo.topscore.model.PlayerScore;
import com.oyo.topscore.model.Score;
import com.oyo.topscore.repository.ScoreRepository;
import com.oyo.topscore.utils.ApiException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreService {
    @Autowired
    ScoreRepository scoreRepository;


    public Score getScoreById(long id) {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        return optionalScore.isPresent() ? optionalScore.get() : null;
    }

    public Page<Score> getScoreList(String player, Date before, Date after, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        if (player != null && !player.isEmpty()) {
            List<String> players = Arrays.asList(player.split(","));
            if (before != null && after != null) {
                return scoreRepository.findByPlayerIgnoreCaseInAndTimeGreaterThanAndTimeLessThan(players, after, before, pageable);
            } else if (before != null && after == null) {
                return scoreRepository.findByPlayerIgnoreCaseInAndTimeLessThan(players, before, pageable);
            } else if (before == null && after != null) {
                return scoreRepository.findByPlayerIgnoreCaseInAndTimeGreaterThan(players, after, pageable);
            } else {
                return scoreRepository.findByPlayerIgnoreCaseIn(players, pageable);
            }
        } else {
            if (before != null && after != null) {
                return scoreRepository.findByTimeBetween(before, after, pageable);
            } else if (before != null) {
                return scoreRepository.findByTimeLessThan(before, pageable);
            } else if (after != null) {
                return scoreRepository.findByTimeGreaterThan(after, pageable);
            } else {
                return scoreRepository.findAll(pageable);
            }
        }
    }

    public void saveScore(Score score) {
        scoreRepository.save(score);
    }

    public void deleteScoreById(long id) {
        Optional<Score> optionalScore = scoreRepository.findById(id);
        if (!optionalScore.isPresent()) throw new ApiException("Score id not found");
        scoreRepository.deleteById(id);
    }

    public List<PlayerScore> getPlayerScoreList(String player) {
        return scoreRepository.findByPlayerIgnoreCase(player)
                .stream()
                .map(PlayerScore::new)
                .collect(Collectors.toList());
    }

    public PlayerScore getPlayerTopScore(String player) throws ApiException {
        Optional<Score> optionalScore = scoreRepository.findByPlayerTopScore(player);
        if (!optionalScore.isPresent()) throw new ApiException("Player not found");
        return new PlayerScore(optionalScore.get());
    }

    public PlayerScore getPlayerLowestScore(String player) {
        Optional<Score> optionalScore = scoreRepository.findByPlayerLowestScore(player);
        if (!optionalScore.isPresent()) throw new ApiException("Player not found");
        return new PlayerScore(optionalScore.get());
    }

    public Integer getPlayerAverageScore(String player) {
        return scoreRepository.findByPlayerAverageScore(player);
    }
}
