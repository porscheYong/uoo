package xyz.wongs.workflow.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WorkflowService {


    public void init(){
        System.out.println(" WorkflowService was be init");
    }

    public List<String> user(){
        return Arrays.asList("Li Ke Qiang"," Wong Yong");
    }
}
