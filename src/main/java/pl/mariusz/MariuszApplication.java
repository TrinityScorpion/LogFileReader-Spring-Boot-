package pl.mariusz;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.mariusz.alert.Alert;
import pl.mariusz.alert.AlertService;
import pl.mariusz.task.Task;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class MariuszApplication {

    private final AlertService alertService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MariuszApplication.class, args);

    }
}
