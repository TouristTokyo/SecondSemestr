package ru.vsu.cs.Shemenev;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ru.vsu.cs.util.SwingUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTextField paramA;
    private JButton buttonDraw;
    private JPanel panelDraw;
    private JTextField paramB;
    private ChartPanel chartPanel = null;

    private Pattern pattern = Pattern.compile("(-)?([0-9])+[\\.]?[0-9]*");

    public FrameMain() {
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setSize(Toolkit.getDefaultToolkit ().getScreenSize ());
        this.setTitle("Math-Draw");

        paramA.setText("1");
        paramB.setText("1");

        buttonDraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!pattern.matcher(paramA.getText()).matches()){
                    SwingUtils.showErrorMessageBox(new Exception("Введите корректные данные!"));
                    return;
                }
                double a = Double.parseDouble(paramA.getText());
                if(!pattern.matcher(paramB.getText()).matches()){
                    SwingUtils.showErrorMessageBox(new Exception("Введите корректные данные!"));
                    return;
                }
                double b = Double.parseDouble(paramB.getText());

                XYSeries series = new XYSeries("y = sqrt(x^3 + a*x + b)");
                XYSeries series2 = new XYSeries("y = -sqrt(x^3 + a*x + b)");
                for (double x = -10; x <= 10; x += 0.001) { // Построение графика
                    series.add(x, Math.sqrt(Math.pow(x,3)+a*x+b));
                }
                for (double x = -10; x <= 10; x += 0.001) { // Построение графика
                    series2.add(x, -Math.sqrt(Math.pow(x,3)+a*x+b));
                }

                XYSeriesCollection dataset = new XYSeriesCollection();
                dataset.addSeries(series);
                dataset.addSeries(series2);

                JFreeChart chart = ChartFactory.createXYLineChart("Элиптические кривые", "OX", "OY",
                        dataset, PlotOrientation.VERTICAL, true, true, true);
                if (chartPanel == null) {
                    chartPanel = new ChartPanel(chart);
                    panelDraw.add(chartPanel);
                    panelDraw.updateUI();
                } else {
                    chartPanel.setChart(chart);
                }
            }
        });
    }
}
