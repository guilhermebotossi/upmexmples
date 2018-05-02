
package br.inpe.climaespacial.swd.calculation;

import br.inpe.climaespacial.swd.average.dtos.HourlyAverage;
import br.inpe.climaespacial.swd.indexes.v.dtos.VIndex;
import java.util.List;

public class VIndexTestData {
    
    private List<HourlyAverage> hourlyAverageList;
    
    private List<VIndex> vIndexList;
    
    private Double expectedVIndexPreValue;
    
    private Double expectedVIndexPostvalue;
    
    private boolean expectedVIndexIsCycleBegin;

    public Double getExpectedVIndexPostvalue() {
        return expectedVIndexPostvalue;
    }

    public void setExpectedVIndexPostvalue(Double expectedVIndexPostvalue) {
        this.expectedVIndexPostvalue = expectedVIndexPostvalue;
    }

    public List<HourlyAverage> getHourlyAverageList() {
        return hourlyAverageList;
    }

    public void setHourlyAverageList(List<HourlyAverage> hourlyAverageList) {
        this.hourlyAverageList = hourlyAverageList;
    }

    public List<VIndex> getvIndexList() {
        return vIndexList;
    }

    public void setvIndexList(List<VIndex> vIndexList) {
        this.vIndexList = vIndexList;
    }

    public Double getExpectedVIndexPreValue() {
        return expectedVIndexPreValue;
    }

    public void setExpectedVIndexPreValue(Double expectedVIndexPreValue) {
        this.expectedVIndexPreValue = expectedVIndexPreValue;
    }

    public boolean isExpectedVIndexIsCycleBegin() {
        return expectedVIndexIsCycleBegin;
    }

    public void setExpectedVIndexIsCycleBegin(boolean expectedVIndexIsCycleBegin) {
        this.expectedVIndexIsCycleBegin = expectedVIndexIsCycleBegin;
    }
    
}
