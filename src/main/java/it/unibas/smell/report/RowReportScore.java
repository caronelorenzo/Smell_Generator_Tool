package it.unibas.smell.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RowReportScore extends RowReportSmell {

    @CsvBindByName(column = "#POSITIVE-COMMIT")
    @CsvBindByPosition(position = 15)
    private int numPositiveCommit = 0;

    @CsvBindByName(column = "#NEGATIVE-COMMIT")
    @CsvBindByPosition(position = 16)
    private int numNegativeCommit = 0;

    @CsvBindByName(column = "#NEUTRAL-COMMIT")
    @CsvBindByPosition(position = 17)
    private int numNeutralCommit = 0;

    @CsvBindByName(column = "#NON-NEUTRAL-COMMIT")
    @CsvBindByPosition(position = 18)
    private int numNonNeutralCommit = 0;

    @CsvBindByName(column = "SCORE")
    @CsvBindByPosition(position = 19)
    private float score = 0f;

    @CsvIgnore
    private List<CommitScore> commitScoreList = new ArrayList<>();

    public RowReportScore(RowReportSmell rowReportSmell) {
        super(rowReportSmell.getClassString(), rowReportSmell.getPackageString());
        super.setSmellCategories(rowReportSmell.getSmellCategories());
    }

    public void addCommitScore(CommitScore commitScore) {
        this.commitScoreList.add(commitScore);
        updateScore(commitScore);
    }

    private void updateScore(CommitScore commitScore) {
        if (commitScore.isNeutral()) {
            numNeutralCommit = numNeutralCommit + 1;
        }
        if (commitScore.isPositive()) {
            numPositiveCommit = numPositiveCommit + 1;
        }
        if (commitScore.isNegative()) {
            numNegativeCommit = numNegativeCommit + 1;
        }

        numNonNeutralCommit = commitScoreList.size() - numNeutralCommit;
        score = ((float) numNonNeutralCommit) / commitScoreList.size();
    }

    public int getNumPositiveCommit() {
        return numPositiveCommit;
    }

    public void setNumPositiveCommit(int numPositiveCommit) {
        this.numPositiveCommit = numPositiveCommit;
    }

    public int getNumNegativeCommit() {
        return numNegativeCommit;
    }

    public void setNumNegativeCommit(int numNegativeCommit) {
        this.numNegativeCommit = numNegativeCommit;
    }

    public int getNumNeutralCommit() {
        return numNeutralCommit;
    }

    public void setNumNeutralCommit(int numNeutralCommit) {
        this.numNeutralCommit = numNeutralCommit;
    }

    public int getNumNonNeutralCommit() {
        return numNonNeutralCommit;
    }

    public void setNumNonNeutralCommit(int numNonNeutralCommit) {
        this.numNonNeutralCommit = numNonNeutralCommit;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "RowScoreReportCompleto{" +
                "numPositiveCommit='" + numPositiveCommit + '\'' +
                ", numNegativeCommit='" + numNegativeCommit + '\'' +
                ", numNeutralCommit='" + numNeutralCommit + '\'' +
                ", numNonNeutralCommit='" + numNonNeutralCommit + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
