package ru.vsu.cs.Shemenev;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.parse.Parser;
import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.*;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;
import ru.vsu.cs.Shemenev.imports.Graph;
import ru.vsu.cs.Shemenev.imports.GraphUtils;
import util.SwingUtils;

import java.awt.Graphics;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Scanner;

public class FrameMain extends JFrame {
    private JTabbedPane tabbedPaneMain;
    private JPanel panelMain;
    private JPanel panelGraphTab;

    private JTextArea textAreaSystemOut;
    private JPanel panelGraphPainterContainer;
    private JSplitPane splitPaneGraphTab1;
    private JSplitPane splitPaneGraphTab2;

    private JButton buttonSaveGraphSvgToFile;

    private JButton addNodeFrameButton;
    private JTextArea textAreaInputNumOfVertex;
    private JButton buttonExecute2;
    private JPanel graphMakerContainer;
    private JButton deleteNodesButton;
    private JButton deleteEdges;
    private JButton buttonSaveGraphToFile;
    private JButton buttonLoadGraphFromFile;
    private JButton buttonCreateGraphText;
    private JButton buttonExecute1;
    private JButton buttonCreateGraph2;

    private JFileChooser fileChooserTxtOpen;
    private JFileChooser fileChooserDotOpen;
    private JFileChooser fileChooserTxtSave;
    private JFileChooser fileChooserDotSave;
    private JFileChooser fileChooserImgSave;

    private SvgPanel panelGraphPainter;
    private JPanel graphMakerPanel;

    private JButton button;
    private Point mouseClickedCoordinates;
    private Point pointStart = null;
    private Point pointEnd = null;

    class Line {
        public final int x1;
        public final int x2;
        public final int y1;
        public final int y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    ArrayList<Line> myLineList = new ArrayList<>();

    private MySimpleGraph graph = new MySimpleGraph();
    private int nodesCount = 0;
    private int edgesCount = 0;
    private String node1;
    private String node2;

    private static class SvgPanel extends JPanel {
        private String svg = null;
        private GraphicsNode svgGraphicsNode = null;

        public void paint(String svg) throws IOException {
            String xmlParser = XMLResourceDescriptor.getXMLParserClassName();
            SAXSVGDocumentFactory df = new SAXSVGDocumentFactory(xmlParser);
            SVGDocument doc = df.createSVGDocument(null, new StringReader(svg));
            UserAgent userAgent = new UserAgentAdapter();
            DocumentLoader loader = new DocumentLoader(userAgent);
            BridgeContext ctx = new BridgeContext(userAgent, loader);
            ctx.setDynamicState(BridgeContext.DYNAMIC);
            GVTBuilder builder = new GVTBuilder();
            svgGraphicsNode = builder.build(ctx, doc);

            this.svg = svg;
            repaint();
        }

        @Override
        public void paintComponent(Graphics gr) {
            super.paintComponent(gr);

            if (svgGraphicsNode == null) {
                return;
            }

            double scaleX = this.getWidth() / svgGraphicsNode.getPrimitiveBounds().getWidth();
            double scaleY = this.getHeight() / svgGraphicsNode.getPrimitiveBounds().getHeight();
            double scale = Math.min(scaleX, scaleY);
            AffineTransform transform = new AffineTransform(scale, 0, 0, scale, 0, 0);
            svgGraphicsNode.setTransform(transform);
            Graphics2D g2d = (Graphics2D) gr;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            svgGraphicsNode.paint(g2d);
        }
    }

    public FrameMain() {
        this.setTitle("Графы");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        textAreaSystemOut.setEditable(false);

        splitPaneGraphTab1.setBorder(null);
        splitPaneGraphTab2.setBorder(null);

        fileChooserTxtOpen = new JFileChooser();
        fileChooserDotOpen = new JFileChooser();
        fileChooserTxtSave = new JFileChooser();
        fileChooserDotSave = new JFileChooser();
        fileChooserImgSave = new JFileChooser();
        fileChooserTxtOpen.setCurrentDirectory(new File("./files/input"));
        fileChooserDotOpen.setCurrentDirectory(new File("./files/input"));
        fileChooserTxtSave.setCurrentDirectory(new File("./files/input"));
        fileChooserDotSave.setCurrentDirectory(new File("./files/input"));
        fileChooserImgSave.setCurrentDirectory(new File("./files/output"));
        FileFilter txtFilter = new FileNameExtensionFilter("Text files (*.txt)", "txt");
        FileFilter dotFilter = new FileNameExtensionFilter("DOT files (*.dot)", "dot");
        FileFilter svgFilter = new FileNameExtensionFilter("SVG images (*.svg)", "svg");

        fileChooserTxtOpen.addChoosableFileFilter(txtFilter);
        fileChooserDotOpen.addChoosableFileFilter(dotFilter);
        fileChooserTxtSave.addChoosableFileFilter(txtFilter);
        fileChooserDotSave.addChoosableFileFilter(dotFilter);
        fileChooserImgSave.addChoosableFileFilter(svgFilter);

        fileChooserTxtSave.setAcceptAllFileFilterUsed(false);
        fileChooserTxtSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserTxtSave.setApproveButtonText("Save");
        fileChooserDotSave.setAcceptAllFileFilterUsed(false);
        fileChooserDotSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserDotSave.setApproveButtonText("Save");
        fileChooserImgSave.setAcceptAllFileFilterUsed(false);
        fileChooserImgSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserImgSave.setApproveButtonText("Save");

        panelGraphPainterContainer.setLayout(new BorderLayout());
        panelGraphPainter = new SvgPanel();
        panelGraphPainterContainer.add(new JScrollPane(panelGraphPainter));

        graphMakerContainer.setLayout(new BorderLayout());
        graphMakerPanel = new JPanel() {

            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.RED);
                if (pointStart != null) {
                    for (Line r : myLineList) {
                        g.drawLine(r.x1, r.y1, r.x2, r.y2);
                    }
                }
            }
        };
        graphMakerContainer.add(graphMakerPanel);

        class RoundButton extends JButton {
            public RoundButton(String label) {
                super(label);
                Dimension size = getPreferredSize();
                size.width = size.height = Math.max(size.width,
                        size.height);
                setPreferredSize(size);
                setContentAreaFilled(false);
            }

            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(Color.pink);
                } else {
                    g.setColor(getBackground());
                }
                g.fillOval(0, 0, getSize().width - 1,
                        getSize().height - 1);

                super.paintComponent(g);
            }

            protected void paintBorder(Graphics g) {
                g.setColor(getForeground());
                g.drawOval(0, 0, getSize().width - 1,
                        getSize().height - 1);
            }

            private Shape shape;

            public boolean contains(int x, int y) {
                if (shape == null ||
                        !shape.getBounds().equals(getBounds())) {
                    shape = new Ellipse2D.Float(0, 0,
                            getWidth(), getHeight());
                }
                return shape.contains(x, y);
            }
        }

        class CustomListener implements MouseListener, MouseMotionListener {

            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    button = (JButton) e.getSource();
                    mouseClickedCoordinates = SwingUtilities.convertPoint(button, e.getPoint(), button.getParent());
                } else if (e.getButton() == MouseEvent.BUTTON2) {
                    button = (JButton) e.getSource();
                    node1 = button.getText();
                    pointStart = SwingUtilities.convertPoint(button, e.getPoint(), button.getParent());

                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    if(pointStart==null){
                        SwingUtils.showInfoMessageBox("Выберите начальную вершину!");
                        return;
                    }
                    button = (JButton) e.getSource();
                    pointEnd = SwingUtilities.convertPoint(button, e.getPoint(), button.getParent());
                    myLineList.add(new Line(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y));
                    graphMakerPanel.repaint();

                    node2 = button.getText();
                    Node nodes1 = graph.nodes.get(Integer.parseInt(node1) - 1);
                    Node nodes2 = graph.nodes.get(Integer.parseInt(node2) - 1);
                    if (!graph.hasEdge(nodes1, nodes2) && !nodes1.equals(nodes2)) {
                        edgesCount++;
                        graph.addEdge(nodes1, nodes2);
                    }
                }
            }


            public void mouseDragged(MouseEvent e) {
                if(e.isControlDown()) {
                    button = (JButton) e.getSource();
                    Point drag = SwingUtilities.convertPoint(button, e.getPoint(), button.getParent());
                    if (button.getParent().getBounds().contains(drag)) {

                        Point newLocation = button.getLocation();
                        if(mouseClickedCoordinates==null){
                            SwingUtils.showInfoMessageBox("Выберите (кликните) вершину, которую хотитие передвинуть!");
                            return;
                        }
                        newLocation.translate(drag.x - mouseClickedCoordinates.x, drag.y - mouseClickedCoordinates.y);
                        newLocation.x = Math.max(newLocation.x, 0);
                        newLocation.y = Math.max(newLocation.y, 0);
                        newLocation.x = Math.min(newLocation.x, button.getParent().getWidth() - button.getWidth());
                        newLocation.y = Math.min(newLocation.y, button.getParent().getHeight() - button.getHeight());

                        button.setLocation(newLocation);
                        mouseClickedCoordinates = drag;
                    }
                }
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseMoved(MouseEvent e) {
            }
        }

        buttonLoadGraphFromFile.addActionListener(e -> {
            if (fileChooserTxtOpen.showOpenDialog(FrameMain.this) == JFileChooser.APPROVE_OPTION) {
                try (Scanner sc = new Scanner(fileChooserTxtOpen.getSelectedFile())) {
                    sc.useDelimiter("\\Z");
                    textAreaInputNumOfVertex.setText(sc.next());
                } catch (Exception exc) {
                    SwingUtils.showErrorMessageBox(exc);
                }
            }
        });

        buttonSaveGraphToFile.addActionListener(e -> {
            if (fileChooserTxtSave.showSaveDialog(FrameMain.this) == JFileChooser.APPROVE_OPTION) {
                String filename = fileChooserTxtSave.getSelectedFile().getPath();
                if (!filename.toLowerCase().endsWith(".txt")) {
                    filename += ".txt";
                }
                try (FileWriter wr = new FileWriter(filename)) {
                    wr.write(textAreaInputNumOfVertex.getText());
                } catch (Exception exc) {
                    SwingUtils.showErrorMessageBox(exc);
                }
            }
        });

        buttonCreateGraphText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelGraphPainter.removeAll();
                try {
                    Class clz = Class.forName("ru.vsu.cs.Shemenev.imports.AdjMatrixGraph");
                    Graph graph = GraphUtils.fromStr(textAreaInputNumOfVertex.getText(), clz);
                    panelGraphPainter.paint(dotToSvg(graph.toDot()));
                } catch (Exception exc) {
                    SwingUtils.showErrorMessageBox(exc);
                }

            }
        });

        addNodeFrameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(edgesCount!=0){
                    edgesCount = 0;
                    myLineList.clear();
                    for (Node node : graph.nodes) {
                        node.color="";
                        node.visited = false;
                        node.neighbors.clear();
                        node.visitNeighbors.clear();
                    }
                    graphMakerPanel.revalidate();
                    graphMakerPanel.repaint();
                }
                nodesCount++;
                button = new RoundButton(Integer.toString(nodesCount));
                button.setBounds(0, 0, 220, 30);
                button.setVisible(true);
                button.addMouseListener(new CustomListener());
                button.addMouseMotionListener(new CustomListener());

                graphMakerPanel.add(button);
                graphMakerPanel.revalidate();

                graph.addNode(nodesCount);
            }
        });

        deleteEdges.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                edgesCount = 0;
                myLineList.clear();
                for (Node node : graph.nodes) {
                    node.color="";
                    node.visited = false;
                    node.neighbors.clear();
                    node.visitNeighbors.clear();
                }
                graphMakerPanel.revalidate();
                graphMakerPanel.repaint();
            }
        });

        deleteNodesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nodesCount = 0;
                edgesCount = 0;
                myLineList.clear();
                graphMakerPanel.removeAll();
                graphMakerPanel.revalidate();
                graphMakerPanel.repaint();

                graph = new MySimpleGraph();
            }
        });

        buttonExecute2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(graph.nodes.size()==0){
                        SwingUtils.showErrorMessageBox(": Сделайте граф!", new Exception());
                        return;
                    }
                    panelGraphPainter.removeAll();
                    int res = Logic.checkPaintGraph(graph);
                    if (res == -1) {
                        textAreaSystemOut.setText("Граф не является двудольным!");

                        myLineList.clear();
                        graphMakerPanel.removeAll();
                        graphMakerPanel.revalidate();
                        graphMakerPanel.repaint();
                        graph = new MySimpleGraph();
                        return;
                    } else if (res == 0) {
                        textAreaSystemOut.setText("Граф не является полным двудольным!");

                        nodesCount = 0;
                        edgesCount = 0;
                        myLineList.clear();
                        graphMakerPanel.removeAll();
                        graphMakerPanel.revalidate();
                        graphMakerPanel.repaint();
                        graph = new MySimpleGraph();
                        return;
                    } else {
                        textAreaSystemOut.setText("Покраска прошла успешна! Граф является полным двудольным!");
                    }
                    String dotStr = GraphUtils.graphToDot(graph);
                    panelGraphPainter.paint(dotToSvg(dotStr));
                    panelGraphPainter.repaint();

                    nodesCount = 0;
                    edgesCount = 0;
                    myLineList.clear();
                    graphMakerPanel.removeAll();
                    graphMakerPanel.revalidate();
                    graphMakerPanel.repaint();
                    graph = new MySimpleGraph();
                } catch (Exception exc) {
                    SwingUtils.showErrorMessageBox(exc);
                }
            }
        });

        buttonExecute1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    panelGraphPainter.removeAll();
                    String[] stringInput = textAreaInputNumOfVertex.getText().split("\\W+");
                    int[] inputArray = new int[stringInput.length];
                    for (int i = 0; i < stringInput.length; i++) {
                        inputArray[i] = Integer.parseInt(stringInput[i]);
                    }
                    graph = GraphUtils.createGraph(inputArray);
                    int res = Logic.checkPaintGraph(graph);
                    if (res == -1) {
                        textAreaSystemOut.setText("Граф не является двудольным!");

                        nodesCount = 0;
                        edgesCount = 0;
                        myLineList.clear();
                        graphMakerPanel.removeAll();
                        graphMakerPanel.revalidate();
                        graphMakerPanel.repaint();
                        graph = new MySimpleGraph();
                        return;
                    } else if (res == 0) {
                        textAreaSystemOut.setText("Граф не является полным двудольным!");

                        nodesCount = 0;
                        edgesCount = 0;
                        myLineList.clear();
                        graphMakerPanel.removeAll();
                        graphMakerPanel.revalidate();
                        graphMakerPanel.repaint();
                        graph = new MySimpleGraph();
                        return;
                    } else {
                        textAreaSystemOut.setText("Покраска прошла успешна! Граф является полным двудольным!");
                    }
                    String dotStr = GraphUtils.graphToDot(graph);
                    panelGraphPainter.paint(dotToSvg(dotStr));
                    panelGraphPainter.repaint();

                    nodesCount = 0;
                    edgesCount = 0;
                    myLineList.clear();
                    graphMakerPanel.removeAll();
                    graphMakerPanel.revalidate();
                    graphMakerPanel.repaint();
                    graph = new MySimpleGraph();
                } catch (Exception exc) {
                    SwingUtils.showErrorMessageBox(exc);
                }
            }
        });

        buttonSaveGraphSvgToFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (panelGraphPainter.svg == null) {
                    return;
                }
                if (fileChooserImgSave.showSaveDialog(FrameMain.this) == JFileChooser.APPROVE_OPTION) {
                    String filename = fileChooserImgSave.getSelectedFile().getPath();
                    if (!filename.toLowerCase().endsWith(".svg")) {
                        filename += ".svg";
                    }
                    try (FileWriter wr = new FileWriter(filename)) {
                        wr.write(panelGraphPainter.svg);
                    } catch (Exception exc) {
                        SwingUtils.showErrorMessageBox(exc);
                    }
                }
            }
        });

        buttonCreateGraph2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelGraphPainter.removeAll();
                try {
                    String dotStr = GraphUtils.graphToDot(graph);
                    panelGraphPainter.paint(dotToSvg(dotStr));
                    panelGraphPainter.repaint();
                } catch (Exception exc){
                    SwingUtils.showErrorMessageBox(exc);
                }

            }
        });
    }

    /**
     * Преобразование dot-записи в svg-изображение (с помощью Graphviz)
     *
     * @param dotSrc dot-запись
     * @return svg
     * @throws IOException
     */
    private static String dotToSvg(String dotSrc) throws IOException {
        MutableGraph g = new Parser().read(dotSrc);
        return Graphviz.fromGraph(g).render(Format.SVG).toString();
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), 10, 10));
        panelMain.setInheritsPopupMenu(true);
        tabbedPaneMain = new JTabbedPane();
        tabbedPaneMain.setName("");
        panelMain.add(tabbedPaneMain, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        panelGraphTab = new JPanel();
        panelGraphTab.setLayout(new GridLayoutManager(1, 1, new Insets(10, 10, 10, 10), 10, 10));
        tabbedPaneMain.addTab("Граф", null, panelGraphTab, "Демонстрация работы с графами");
        splitPaneGraphTab1 = new JSplitPane();
        splitPaneGraphTab1.setOrientation(0);
        splitPaneGraphTab1.setResizeWeight(0.75);
        panelGraphTab.add(splitPaneGraphTab1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        splitPaneGraphTab2 = new JSplitPane();
        splitPaneGraphTab2.setResizeWeight(0.0);
        splitPaneGraphTab1.setLeftComponent(splitPaneGraphTab2);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneGraphTab2.setLeftComponent(panel1);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        addNodeFrameButton = new JButton();
        addNodeFrameButton.setText("Посчитать количество циклов в  полном графе");
        panel3.add(addNodeFrameButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.add(panel4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textAreaInputNumOfVertex = new JTextArea();
        panel4.add(textAreaInputNumOfVertex, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
        final JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel4.add(panel5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Введите количество вершин:");
        panel5.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel6 = new JPanel();
        panel6.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneGraphTab2.setRightComponent(panel6);
        buttonSaveGraphSvgToFile = new JButton();
        buttonSaveGraphSvgToFile.setText("Сохранить в файл");
        panel6.add(buttonSaveGraphSvgToFile, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panelGraphPainterContainer = new JPanel();
        panelGraphPainterContainer.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel6.add(panelGraphPainterContainer, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel6.add(spacer1, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        splitPaneGraphTab1.setRightComponent(panel7);
        final JLabel label2 = new JLabel();
        label2.setText("Количество циклов:");
        panel7.add(label2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textAreaSystemOut = new JTextArea();
        panel7.add(textAreaSystemOut, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

}
