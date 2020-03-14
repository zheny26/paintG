import static entity.DrawAction.CHAIN;
import static entity.DrawAction.LINE;
import static entity.DrawAction.RAY;
import static entity.DrawAction.SEGMENT;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import entity.DrawAction;
import entity.Line;
import entity.Ray;
import entity.Segment;
import entity.Shape;

public class App extends JFrame {

    private JPanel rootPanel, drawPanel;
    private JButton segmentButton, rayButton, lineButton;
    private DrawAction drawAction = DrawAction.MOVEMENT;
    private Map<DrawAction, Consumer<MouseEvent>> drawModeActionImpl;
    private Map<DrawAction, Consumer<MouseEvent>> shapeCreationActions;
    private Color borderColor = new Color(0, 0, 0);
    private List<Shape> shapes = new ArrayList();

    private App() {

        initComponents();
        initDrawModeActions();
        setContentPane(rootPanel);
        setSize(1000, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(App::new);
    }

    private void initComponents() {

        drawPanel.addMouseListener(getMouseAdapter());
        drawPanel.addMouseMotionListener(getMouseMotionListener());

        segmentButton.addActionListener(e -> drawAction = DrawAction.SEGMENT);
        rayButton.addActionListener(e -> drawAction = DrawAction.RAY);
        lineButton.addActionListener(e -> drawAction = LINE);
    }

    private void initPanels() {


    }

    private void initDrawModeActions() {

        drawModeActionImpl = new HashMap<>();
        drawModeActionImpl.put(LINE, e -> {
            Line line = (Line) shapes.get(shapes.size() - 1);
            line.setEnd(e.getPoint());
        });
        drawModeActionImpl.put(SEGMENT, e -> {

            Segment segment = (Segment) shapes.get(shapes.size() - 1);
            segment.setEnd(e.getPoint());
        });
        drawModeActionImpl.put(RAY, e -> {

            Ray ray = (Ray) shapes.get(shapes.size() - 1);
            ray.setEnd(e.getPoint());
        });

        shapeCreationActions = new HashMap<>();
        shapeCreationActions.put(LINE, e -> shapes.add(new Line(borderColor, e.getPoint(), e.getPoint())));
        shapeCreationActions.put(SEGMENT, e -> shapes.add(new Segment(borderColor, e.getPoint(), e.getPoint())));
        shapeCreationActions.put(RAY, e -> shapes.add(new Ray(borderColor, e.getPoint(), e.getPoint())));
        shapeCreationActions.put(CHAIN,)
    }

    private MouseListener getMouseAdapter() {

        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {

                int cursorType = drawAction == DrawAction.MOVEMENT ? Cursor.HAND_CURSOR : Cursor.CROSSHAIR_CURSOR;
                setCursor(new Cursor(cursorType));
            }

            @Override
            public void mouseExited(MouseEvent e) {

                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mousePressed(MouseEvent e) {

                shapeCreationActions.get(drawAction).accept(e);
            }
        };
    }

    private MouseMotionListener getMouseMotionListener() {

        return new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (SwingUtilities.isLeftMouseButton(e)) {
                    drawModeActionImpl.get(drawAction).accept(e);
                    repaint();
                }
            }
        };
    }

    private void createUIComponents() {

        drawPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {

                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (Shape s : shapes) {
                    s.draw(g2d);
                }
            }
        };
    }
}
