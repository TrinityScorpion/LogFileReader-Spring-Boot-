package pl.mariusz.alert;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.mariusz.task.Task;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
@RequiredArgsConstructor
public class AlertController {

    private final AlertService alertService;

    @RequestMapping("/")
    @ResponseBody
    public void createAlert(){
//        JFrame parentFrame = new JFrame();
        List<String> list = new ArrayList<String>();
        List<Task> taskList = new ArrayList<Task>();
        List<Alert> alertsList = new ArrayList<Alert>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to open");
//        int userSelection = fileChooser.showOpenDialog(new JPanel());
//        if (userSelection == JFileChooser.APPROVE_OPTION) {
        File file = fileChooser.getCurrentDirectory();
        try {
            Scanner scanner = new Scanner(new File("/home/mariusz/logfile.txt"));
            do {
                String line = scanner.nextLine();
                list.add(line.replaceAll("\"", "").replaceAll("\\{", "").replaceAll("\\}", ""));
            }
            while (scanner.hasNextLine());
            taskList = listToObjectList(list);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Save as file: " + file);
        alertsList = calculation(taskList);

        for (int i = 0; i < alertsList.size(); i++) {
            System.out.println(alertsList.get(i).toString());
            alertService.saveAlert(alertsList.get(i));
        }
        System.out.println("Done");

    }

    public static List<Alert> calculation(List<Task> list) {
        List<Task> startedList = new ArrayList<>();
        List<Task> finishedList = new ArrayList<>();
        List<Alert> alerts = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getState().equals("STARTED")) {
                startedList.add(list.get(i));
            } else if (list.get(i).getState().equals("FINISHED")) {
                finishedList.add(list.get(i));
            }
        }
        for (int i = 0; i < startedList.size(); i++) {
            for (int j = 0; j < finishedList.size(); j++) {
                if (startedList.get(i).getId().equals(finishedList.get(j).getId())) {
                    long time = finishedList.get(j).getTimestamp() - startedList.get(i).getTimestamp();
                    boolean alert;
                    if (time > 4)
                        alert = true;
                    else
                        alert = false;

                    alerts.add(new Alert(startedList.get(i).getId(),
                            time,
                            startedList.get(i).getType(),
                            startedList.get(i).getHost(),
                            alert));
                }
            }
        }

        return alerts;
    }

    public static List<Task> listToObjectList(List<String> list) {
        String id = "";
        String state = "";
        String type = "";
        String host = "";
        long timestamp = 0;
        ArrayList<Task> taskList = new ArrayList<Task>();

        for (int i = 0; i < list.size(); i++) {
            String[] array = list.get(i).split(", |\\:");
            if (array[0].equals("id")) {
                id = array[1];
            }
            if (array[2].equals("state")) {
                state = array[3];
            }

            if (array.length == 6) {
                if (array[4].equals("timestamp")) {
                    timestamp = Long.parseLong(array[5]);
                }

                taskList.add(new Task(id, state, "", "", timestamp));
            } else if (array.length == 10) {

                if (array[4].equals("type")) {
                    type = array[5];
                }
                if (array[6].equals("host")) {
                    host = array[7];
                }
                if (array[8].equals("timestamp")) {
                    timestamp = Long.parseLong(array[9]);
                }
                taskList.add(new Task(id, state, type, host, timestamp));
            }
        }
        return taskList;
    }

}
