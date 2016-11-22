package models;

import org.mongodb.morphia.annotations.Reference;

public class Grade {
    @Reference
    private GradeType gradeType;
    private int score;

    public GradeType getGradeType() {
        return gradeType;
    }

    public void setGradeType(GradeType gradeType) {
        this.gradeType = gradeType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
