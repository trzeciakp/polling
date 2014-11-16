package pl.edu.agh.trzeciak.polling.web.rest.dto;

import pl.edu.agh.trzeciak.polling.domain.Poll;

public class PollDTO {

    private String name;
    private Integer maxScore;
    private String owner;
    private long id;
    private int emptyScores;
    private int allScores;

    public PollDTO() {
    }

    public PollDTO(Poll poll, int emptyScores, int allScores) {

        this.id = poll.getId();
        this.name = poll.getName();
        this.maxScore = poll.getMaxScore();
        this.owner = poll.getUser().getLogin();
        this.emptyScores = emptyScores;
        this.allScores = allScores;
    }

    @Override
    public String toString() {
        return "PollDTO{" +
                "name='" + name + '\'' +
                ", maxScore=" + maxScore +
                ", owner='" + owner + '\'' +
                ", id=" + id +
                ", emptyScores=" + emptyScores +
                ", allScores=" + allScores +
                '}';
    }

    public String getName() {
        return name;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public String getOwner() {
        return owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getEmptyScores() {
        return emptyScores;
    }

    public void setEmptyScores(int emptyScores) {
        this.emptyScores = emptyScores;
    }

    public int getAllScores() {
        return allScores;
    }

    public void setAllScores(int allScores) {
        this.allScores = allScores;
    }
}
