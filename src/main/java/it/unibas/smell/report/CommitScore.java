package it.unibas.smell.report;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class CommitScore {

    @CsvBindByName(column = "POSITIVITY")
    @CsvBindByPosition(position = 17)
    private int positivity;

    @CsvBindByName(column = "NEGATIVITY")
    @CsvBindByPosition(position = 18)
    private int negativity;

    public CommitScore() {
        this.positivity = 0;
        this.negativity = 0;
    }

    public CommitScore(int positivity, int negativity) {
        this.positivity = positivity;
        this.negativity = negativity;
    }

    public boolean isPositive() {
        return positivity + negativity > 0;
    }

    public boolean isNeutral() {
        return positivity == 1 && negativity == -1;
    }

    public int getPositivity() {
        return positivity;
    }

    public void setPositivity(int positivity) {
        this.positivity = positivity;
    }

    public int getNegativity() {
        return negativity;
    }

    public void setNegativity(int negativity) {
        this.negativity = negativity;
    }
}
