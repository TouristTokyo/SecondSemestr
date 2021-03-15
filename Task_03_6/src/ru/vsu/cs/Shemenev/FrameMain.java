package ru.vsu.cs.Shemenev;

import ru.vsu.cs.util.JTableUtils;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableInput;
    private JButton buttonInputFromFile;
    private JButton buttonExecute;
    private JTable tableOutput;

    public FrameMain(){
        this.setTitle("*");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput,40,false,true,false,true);
        tableInput.setRowHeight(40);
        JTableUtils.initJTableForArray(tableOutput,40,true,false,false,false);
        tableOutput.setRowHeight(40);

        buttonInputFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file;
                JFileChooser jFileChooser = new JFileChooser();
                int ret = jFileChooser.showDialog(null,"Load from file");
                if(ret==JFileChooser.APPROVE_OPTION){
                    file = jFileChooser.getSelectedFile();
                } else{
                    return;
                }
                try {
                    JTableUtils.writeArrayToJTable(tableInput, TaskUtils.readFile(file));
                } catch (FileNotFoundException fileNotFoundException){
                    SwingUtils.showErrorMessageBox(fileNotFoundException);
                }
            }
        });

        buttonExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] array = JTableUtils.readStringArrayFromJTable(tableInput);
                if(TaskUtils.check(array)){
                    SwingUtils.showErrorMessageBox("Введите только числа", new Exception("Error"));
                    return;
                }
                MySimpleDeque<String> deque = Logic.deque(array);
                JTableUtils.writeArrayToJTable(tableOutput,TaskUtils.arrayDeckOfCards(deque));
            }
        });
    }
}
