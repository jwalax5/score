package com.oyo.topscore.controller;

import com.oyo.topscore.model.PlayerScore;
import com.oyo.topscore.model.Score;
import com.oyo.topscore.service.ScoreService;
import com.oyo.topscore.utils.ApiException;
import com.oyo.topscore.utils.ApiResponseHandler;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ScoreController {

    @Autowired
    ScoreService scoreService;

    @GetMapping("")
    String home() {
        return "Hello, World!";
    }

    @GetMapping("/scores/{id}")
    public ResponseEntity<Score> getScore(@PathVariable long id) {
        ResponseEntity response;
        try {
            Score data = scoreService.getScoreById(id);
            response = ApiResponseHandler.generateSuccessResponse("Get score successfully", HttpStatus.OK, data);
        } catch (ApiException ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @GetMapping("/scores")
    public Page<Score> getScoreList(@RequestParam(required = false) String player,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date before,
                                    @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date after,
                                    @RequestParam(required = false, defaultValue = "0") int pageNo,
                                    @RequestParam(required = false, defaultValue = "10") int pageSize) {
        return scoreService.getScoreList(player, before, after, pageNo, pageSize);
    }

    @PutMapping("/scores")
    public ResponseEntity saveScore(@Valid @RequestBody Score score) {
        ResponseEntity response;
        try {
            scoreService.saveScore(score);
            response = ApiResponseHandler.generateSuccessResponse("Score has been created successfully", HttpStatus.OK, score.getId());
        } catch (Exception ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @DeleteMapping("/scores/{id}")
    public ResponseEntity deleteScore(@PathVariable long id) {
        ResponseEntity response;
        try {
            scoreService.deleteScoreById(id);
            response = ApiResponseHandler.generateSuccessResponse("Score has been deleted successfully", HttpStatus.OK, id);
        } catch (ApiException ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @GetMapping("/scores/{player}/list")
    public ResponseEntity<List<PlayerScore>> getPlayerScoreList(@PathVariable String player) {
        ResponseEntity response;
        try {
            List<PlayerScore> data = scoreService.getPlayerScoreList(player);
            response = ApiResponseHandler.generateSuccessResponse("Get player score list successfully", HttpStatus.OK, data);
        } catch (ApiException ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @GetMapping("/scores/{player}/top")
    public ResponseEntity<PlayerScore> getPlayerTopScore(@PathVariable String player) {
        ResponseEntity response;
        try {
            PlayerScore data = scoreService.getPlayerTopScore(player);
            response = ApiResponseHandler.generateSuccessResponse("Get player top score successfully", HttpStatus.OK, data);
        } catch (ApiException ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @GetMapping("/scores/{player}/low")
    public ResponseEntity<PlayerScore> getPlayerLowestScore(@PathVariable String player) {
        ResponseEntity response;
        try {
            PlayerScore data = scoreService.getPlayerLowestScore(player);
            response = ApiResponseHandler.generateSuccessResponse("Get player lowest score successfully", HttpStatus.OK, data);
        } catch (ApiException ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @GetMapping("/scores/{player}/avg")
    public ResponseEntity<Integer> getPlayerAverageScore(@PathVariable String player) {
        ResponseEntity response;
        try {
            Integer data = scoreService.getPlayerAverageScore(player);
            response = ApiResponseHandler.generateSuccessResponse("Get player average score successfully", HttpStatus.OK, data);
        } catch (ApiException ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            response = ApiResponseHandler.generateFailureResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }
}
