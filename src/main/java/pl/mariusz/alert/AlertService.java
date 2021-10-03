package pl.mariusz.alert;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.mariusz.task.Task;

import javax.swing.*;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public void saveAlert(Alert alert){
        alertRepository.save(alert);

    }

    public Alert findById(Integer id){
        return alertRepository.findById(id).orElse(null);
    }

    public List<Alert> getAll(){
        return alertRepository.findAll();
    }
    public void deleteAlert(Integer id){
        Alert alert = findById(id);
        alertRepository.delete(alert);
    }

    public void updateAlert(Alert alert){
        alertRepository.save(alert);
    }

}
