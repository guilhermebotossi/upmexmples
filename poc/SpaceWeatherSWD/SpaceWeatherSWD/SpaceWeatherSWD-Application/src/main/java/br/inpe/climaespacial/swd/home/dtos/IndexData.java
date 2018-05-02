
package br.inpe.climaespacial.swd.home.dtos;

import java.util.List;
import javax.enterprise.context.Dependent;

@Dependent
public class IndexData {
    
    private List<IndexEntry> bEntries;
    private List<IndexEntry> cEntries;
    private List<IndexEntry> vEntries;
    private List<IndexEntry> zEntries;

    public List<IndexEntry> getbEntries() {
        return bEntries;
    }

    public void setbEntries(List<IndexEntry> bEntries) {
        this.bEntries = bEntries;
    }

    public List<IndexEntry> getcEntries() {
        return cEntries;
    }

    public void setcEntries(List<IndexEntry> cEntries) {
        this.cEntries = cEntries;
    }

    public List<IndexEntry> getvEntries() {
        return vEntries;
    }

    public void setvEntries(List<IndexEntry> vEntries) {
        this.vEntries = vEntries;
    }

    public List<IndexEntry> getzEntries() {
        return zEntries;
    }

    public void setzEntries(List<IndexEntry> zEntries) {
        this.zEntries = zEntries;
    }
    
}
