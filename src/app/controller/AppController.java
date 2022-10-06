package app.controller;

import app.model.CollabEmployees;
import app.model.Entry;
import app.service.EntryService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class AppController {
    public static final String FILE_PATH = ".\\src\\resources\\employeeEntries.txt";

    private List<DateTimeFormatter> formatters = Arrays.asList(DateTimeFormatter.ofPattern("d-MMM-yyyy", Locale.US),
                                                        DateTimeFormatter.ofPattern("d/MM/yyyy"),
                                                        DateTimeFormatter.ISO_LOCAL_DATE);

    private EntryService entryService;

    public AppController(EntryService entryService) {
        this.entryService = entryService;
    }

    public void run(){
        List<String> fileContent = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();

        File file = new File(FILE_PATH);

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while((line = reader.readLine()) != null){
                if(!line.trim().isEmpty()){
                    fileContent.add(line);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        for (String s : fileContent) {
            String[] lineTokens = s.split(", ");
            long employeeId = Long.parseLong(lineTokens[0]);
            long projectId = Long.parseLong(lineTokens[1]);
            LocalDate dateFrom = tryParse(lineTokens[2]);
            LocalDate dateTo;
            if(lineTokens[3] == null || "NULL".equals(lineTokens[3])){
                dateTo = LocalDate.now();
            }else{
                dateTo = tryParse(lineTokens[3]);
            }
            Entry entry = new Entry(employeeId, projectId, dateFrom, dateTo);
            entries.add(entry);
        }

        this.entryService.addEntries(entries);

        List<CollabEmployees> collaborationEmployees = entryService.getListOfAllCollabEmployees();

        if(!collaborationEmployees.isEmpty()){
            Comparator<CollabEmployees> comparator = Comparator
                    .comparing(CollabEmployees::getDays).reversed();

            List<CollabEmployees> sortedCollabEmployees = collaborationEmployees.stream()
                    .sorted(comparator)
                    .collect(Collectors.toList());

            CollabEmployees topPair = sortedCollabEmployees.get(0);

            System.out.println(topPair.getFirstEmployeeId() + ", " + topPair.getSecondEmployeeId() + ", " + topPair.getDays());
        }else{
            System.out.println("No employees worked together on common projects");
        }
    }

    public LocalDate tryParse(String s){
        for (DateTimeFormatter formatter : formatters) {
            try {
                LocalDate localDate = LocalDate.parse(s, formatter);
                return localDate;
            }catch(DateTimeParseException e){

            }
        }
        return null;
    }
}
