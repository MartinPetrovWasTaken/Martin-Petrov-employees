package app.service;

import app.model.CollabEmployees;
import app.model.Entry;
import app.repository.EntryRepository;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class EntryService {
    private EntryRepository entryRepository;

    public EntryService(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    public void addEntries(List<Entry> entries){
        for (Entry entry : entries) {
            this.entryRepository.save(entry);
        }
    }

    public List<CollabEmployees> getListOfAllCollabEmployees(){
        List<CollabEmployees> collaborationEmployeesList = new ArrayList<>();
        List<Entry> entries = this.entryRepository.getEntries();

        for (int i = 0; i < entries.size() - 1; i++) {
            for (int j = i + 1; j < entries.size(); j++) {
                Entry firstEntry = entries.get(i);
                Entry secondEntry = entries.get(j);

                if(firstEntry.getProjectId() == secondEntry.getProjectId()
                        && (firstEntry.getDateFrom().isBefore(secondEntry.getDateTo())
                            || firstEntry.getDateFrom().isEqual(secondEntry.getDateTo()))
                            && (firstEntry.getDateTo().isAfter(secondEntry.getDateFrom())
                            ||  firstEntry.getDateTo().isEqual(secondEntry.getDateFrom()))){
                    LocalDate startDate;
                    LocalDate endDate;
                    if(firstEntry.getDateFrom().isBefore(secondEntry.getDateFrom())){
                         startDate = secondEntry.getDateFrom();
                    }else{
                         startDate = firstEntry.getDateFrom();
                    }

                    if(firstEntry.getDateTo().isBefore(secondEntry.getDateTo())){
                         endDate = firstEntry.getDateTo();
                    }else{
                         endDate = secondEntry.getDateTo();
                    }

                    long days = ChronoUnit.DAYS.between( startDate , endDate);
                    boolean isPresent = false;

                    if(days > 0){
                        for (CollabEmployees pair : collaborationEmployeesList) {
                            if(isPairPresent(pair,firstEntry, secondEntry)){
                                isPresent = true;
                                pair.addDays(days);
                            }
                        }
                        if(!isPresent){
                            CollabEmployees collabEmployees = new CollabEmployees(firstEntry.getEmployeeId(), secondEntry.getEmployeeId(), days);
                            collaborationEmployeesList.add(collabEmployees);
                        }
                    }
                }
            }
        }
        return collaborationEmployeesList;
    }

    public boolean isPairPresent(CollabEmployees pair, Entry firstEntry, Entry secondEntry){
        return (pair.getFirstEmployeeId() == firstEntry.getEmployeeId()
                && pair.getSecondEmployeeId() == secondEntry.getEmployeeId())
                || (pair.getFirstEmployeeId() == secondEntry.getEmployeeId()
                &&  pair.getSecondEmployeeId() == firstEntry.getEmployeeId());
    }
}
