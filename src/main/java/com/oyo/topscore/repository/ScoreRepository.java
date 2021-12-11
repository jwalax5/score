package com.oyo.topscore.repository;

import com.oyo.topscore.model.Score;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    Page<Score> findAll(Pageable pageable);

    Page<Score> findByPlayerIgnoreCaseIn(List<String> players, Pageable pageable);

    Page<Score> findByPlayerIgnoreCaseInAndTimeGreaterThanAndTimeLessThan(List<String> players, Date after, Date before, Pageable pageable);

    Page<Score> findByPlayerIgnoreCaseInAndTimeLessThan(List<String> players, Date before, Pageable pageable);

    Page<Score> findByPlayerIgnoreCaseInAndTimeGreaterThan(List<String> players, Date after, Pageable pageable);

    Page<Score> findByTimeLessThan(Date before, Pageable pageable);

    Page<Score> findByTimeGreaterThan(Date after, Pageable pageable);

    List<Score> findByPlayerIgnoreCase(String players);

    @Query(value = "SELECT * FROM Score WHERE time >= ?1 and time < ?2", nativeQuery = true)
    Page<Score> findByTimeBetween(Date before, Date after, Pageable pageable);

    @Query(value = "SELECT * FROM Score where player = ?1 ORDER BY score DESC LIMIT 1", nativeQuery = true)
    Optional<Score> findByPlayerTopScore(String player);

    @Query(value = "SELECT * FROM Score where player = ?1 ORDER BY score LIMIT 1", nativeQuery = true)
    Optional<Score> findByPlayerLowestScore(String player);

    @Query(value = "SELECT avg(score) FROM Score where player = ?1", nativeQuery = true)
    Integer findByPlayerAverageScore(String player);
}

