package com.oyo.topscore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
public class PlayerScore {
    private Integer score;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public PlayerScore(Score score) {
        this.score = score.getScore();
        this.time = score.getTime();
    }
}