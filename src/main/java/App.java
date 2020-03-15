import static entity.DrawAction.CHAIN;
import static entity.DrawAction.CHAIN_UPDATE;
import static entity.DrawAction.CIRCLE;
import static entity.DrawAction.ELLIPSE;
import static entity.DrawAction.LINE;
import static entity.DrawAction.MOVEMENT;
import static entity.DrawAction.POLYGON;
import static entity.DrawAction.POLYGON_UPDATE;
import static entity.DrawAction.RAY;
import static entity.DrawAction.RECTANGLE;
import static entity.DrawAction.RHOMBUS;
import static entity.DrawAction.SEGMENT;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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

import entity.Chain;
import entity.Circle;
import entity.DrawAction;
import entity.Ellipse;
import entity.Line;
import entity.Polygon;
import entity.Ray;
import entity.Rectangle;
import entity.Rhombus;
import entity.Segment;
import entity.Shape;

public class App extends JFrame {

    private JPanel rootPanel, drawPanel;
    private JButton segmentButton, rayButton, lineButton, chainButton, circleButton, ellipseButton, polygonButton,
            rhobmusButton, rectangleButton;
    private DrawAction drawAction = DrawAction.MOVEMENT;
    private DrawAction previousAction = MOVEMENT;
    private Map<DrawAction, Consumer<MouseEvent>> drawModeActionImpl;
    private Map<DrawAction, Consumer<MouseEvent>> shapeCreationActions;
    private Color borderColor = new Color(0, 0, 0);
    private Color fillColor = new Color(255, 120, 120);
    private List<Shape> shapes = new ArrayList<>();

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
        chainButton.addActionListener(e -> drawAction = CHAIN);
        circleButton.addChangeListener(e -> drawAction = CIRCLE);
        ellipseButton.addActionListener(e -> drawAction = ELLIPSE);
        polygonButton.addActionListener(e -> drawAction = POLYGON);
        rhobmusButton.addActionListener(e -> drawAction = RHOMBUS);
        rectangleButton.addActionListener(e -> drawAction = RECTANGLE);
    }

    private void initDrawModeActions() {

        drawModeActionImpl = new HashMap<>();
        drawModeActionImpl.put(LINE, e -> {
            Line line = (Line) App.this.getCurrentShape();
            line.setEnd(e.getPoint());
        });
        drawModeActionImpl.put(SEGMENT, e -> {

            Segment segment = (Segment) getCurrentShape();
            segment.setEnd(e.getPoint());
        });
        drawModeActionImpl.put(RAY, e -> {

            Ray ray = (Ray) getCurrentShape();
            ray.setEnd(e.getPoint());
        });
        drawModeActionImpl.put(CHAIN, e -> {

            Chain chain = (Chain) getCurrentShape();
            chain.getLastSegment().setEnd(e.getPoint());
            previousAction = CHAIN;
            drawAction = CHAIN_UPDATE;
        });
        drawModeActionImpl.put(CHAIN_UPDATE, e -> {

            Chain chain = (Chain) getCurrentShape();
            chain.getLastSegment().setEnd(e.getPoint());
        });
        drawModeActionImpl.put(CIRCLE, e -> {

            Circle circle = (Circle) getCurrentShape();
            circle.setCorner(e.getPoint());
        });
        drawModeActionImpl.put(ELLIPSE, e -> {

            Ellipse ellipse = (Ellipse) getCurrentShape();
            ellipse.setCorner(e.getPoint());
        });
        drawModeActionImpl.put(POLYGON, e -> {

            Polygon polygon = (Polygon) getCurrentShape();
            polygon.setLastPoint(e.getPoint());
            previousAction = POLYGON;
            drawAction = POLYGON_UPDATE;
        });
        drawModeActionImpl.put(POLYGON_UPDATE, e -> {

            Polygon polygon = (Polygon) getCurrentShape();
            polygon.setLastPoint(e.getPoint());
        });
        drawModeActionImpl.put(RHOMBUS, e -> {
            Rhombus rhombus = (Rhombus) getCurrentShape();
            rhombus.setCorner(e.getPoint());
        });
        drawModeActionImpl.put(RECTANGLE, e -> {

            Rectangle rectangle = (Rectangle) getCurrentShape();
            rectangle.setCorner(e.getPoint());
        });

        shapeCreationActions = new HashMap<>();
        shapeCreationActions.put(LINE, e -> shapes.add(new Line(borderColor, e.getPoint(), e.getPoint())));
        shapeCreationActions.put(SEGMENT, e -> shapes.add(new Segment(borderColor, e.getPoint(), e.getPoint())));
        shapeCreationActions.put(RAY, e -> shapes.add(new Ray(borderColor, e.getPoint(), e.getPoint())));
        shapeCreationActions.put(CHAIN, e -> {
            List<Segment> segments = new ArrayList<>();
            segments.add(new Segment(borderColor, e.getPoint(), e.getPoint()));
            shapes.add(new Chain(borderColor, e.getPoint(), segments));
        });
        shapeCreationActions.put(CHAIN_UPDATE, e -> {
            Chain chain = (Chain) getCurrentShape();
            chain.addSegment(new Segment(borderColor, chain.getLastSegment().getEnd(), e.getPoint()));
        });
        shapeCreationActions.put(CIRCLE,
                e -> shapes.add(new Circle(borderColor, e.getPoint(), fillColor, e.getPoint())));
        shapeCreationActions.put(ELLIPSE,
                e -> shapes.add(new Ellipse(borderColor, e.getPoint(), fillColor, e.getPoint())));
        shapeCreationActions.put(POLYGON, e -> {
            List<Point> points = new ArrayList<>();
            points.add(e.getPoint());
            points.add(e.getPoint());
            shapes.add(new Polygon(borderColor, e.getPoint(), fillColor, points));
        });
        shapeCreationActions.put(POLYGON_UPDATE, e -> {

            Polygon polygon = (Polygon) getCurrentShape();
            polygon.addPoint(e.getPoint());
        });
        shapeCreationActions.put(RHOMBUS,
                e -> shapes.add(new Rhombus(borderColor, e.getPoint(), e.getPoint(), fillColor)));
        shapeCreationActions.put(RECTANGLE,
                e -> shapes.add(new Rectangle(borderColor, e.getPoint(), e.getPoint(), fillColor)));
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

                if (SwingUtilities.isRightMouseButton(e)) {
                    drawAction = previousAction;
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    shapeCreationActions.get(drawAction).accept(e);
                    repaint();
                }
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
                shapes.forEach(s -> s.draw((Graphics2D) g));
            }
        };
    }

    private Shape getCurrentShape() {

        return shapes.get(shapes.size() - 1);
    }
}
