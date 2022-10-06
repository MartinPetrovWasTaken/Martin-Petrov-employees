package app;

import app.controller.AppController;
import app.repository.EntryRepository;
import app.service.EntryService;

public class Main {
    public static void main(String[] args) {
        EntryRepository entryRepository = new EntryRepository();
        EntryService entryService = new EntryService(entryRepository);
        AppController appController = new AppController(entryService);
        appController.run();
    }
}
