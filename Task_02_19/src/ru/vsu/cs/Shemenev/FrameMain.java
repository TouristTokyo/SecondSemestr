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
    private JTable tableOutput;
    private JButton buttonLoadFromFile;
    private JButton buttonExecute;

    public FrameMain() {
        this.setTitle("*");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();

        JTableUtils.initJTableForArray(tableInput, 50, false, true, false, true);
        tableInput.setRowHeight(40);
        JTableUtils.initJTableForArray(tableOutput, 50, false, true, false, false);
        tableOutput.setRowHeight(40);


        buttonLoadFromFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                File file;
                int ret = fileChooser.showDialog(null, "Load from file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    file = fileChooser.getSelectedFile();
                } else {
                    return;
                }
                try {
                    JTableUtils.writeArrayToJTable(tableInput, TaskUtils.readFile(file));
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });

        buttonExecute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] array = JTableUtils.readStringArrayFromJTable(tableInput);
                if(TaskUtils.check(array)){
                    SwingUtils.showInfoMessageBox("Введите только числа!","Ошибка");
                    return;
                }
                String[] newArray = TaskUtils.newArray(Logic.newList(TaskUtils.list(array)));
                JTableUtils.writeArrayToJTable(tableOutput, newArray);
            }
        });
    }
}
