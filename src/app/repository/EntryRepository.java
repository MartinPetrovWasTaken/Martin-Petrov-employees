package app.repository;

import app.model.Entry;

import java.util.ArrayList;
import java.util.List;

public class EntryRepository {
    private List<Entry> entries;

    public EntryRepository() {
        this.entries = new ArrayList<>();
    }

    public void save(Entry entry){
        this.entries.add(entry);
    }

    public List<Entry> getEntries(){
        return this.entries;
    }
}
