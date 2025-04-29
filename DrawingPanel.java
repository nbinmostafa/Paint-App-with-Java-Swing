import javax.swing.*;           
import java.awt.*;                 
import java.awt.event.*;          
import java.util.ArrayList;  




class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener  {
    
    private ArrayList<PenPoint> points = new ArrayList<>();
    private ArrayList<DrawnShape> shapes = new ArrayList<>();


    //General Properties 
    private int brushSize = 10; 
    private String currentTool = "Pen"; 
    private Color currentColor = Color.BLACK;
    private Color savedColor = Color.BLACK;
    private Point mousePos = new Point(0, 0);

    //Shape Properties
    private int shapeStartX, shapeStartY;
    private boolean isDrawingShape = false;
    private String currentShape = "";

    //Drawing Properties
    int x1, y1, x2, y2;
    String shapeType;
    int size;
    Color color;



    public DrawingPanel() {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    
    public DrawingPanel(int x1, int y1, int x2, int y2, String shapeType, int size, Color color) {
        setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.shapeType = shapeType;
        this.size = size;
        this.color = color;
    }
    
 /*
  * Setters
  */
    public void setTool(String tool) {
        currentTool = tool;
    }


    //Set Brush Size 
    public void setBrushSize(int size) {
        this.brushSize = size;
    }

    //Set Color
    public void setColor(Color color) {
        this.currentColor = color;
    
        // only update savedColor if not erasing
        if (!currentTool.equals("Eraser")) {
            savedColor = color;
        }
    }
    
    //Set Shape
    public void setShape(String shapeName) {
        this.currentTool = "Shape";        
        this.currentShape = shapeName;
        this.isDrawingShape = false;       // reset previous state
    }


 /*
  * Getters 
  */    
    //Get Saved Color
    public Color getSavedColor() {
        return savedColor;
    }
    
    



    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Render Stored Shapes
        for (DrawnShape s : shapes) {
            g.setColor(s.color);
            int x = Math.min(s.x1, s.x2);
            int y = Math.min(s.y1, s.y2);
            int w = Math.abs(s.x2 - s.x1);
            int h = Math.abs(s.y2 - s.y1);
        
            switch (s.shapeType) {
                case "Rectangle": g.fillRect(x, y, w, h); break;
                case "Circle":
                    int d = Math.max(w, h);
                    g.fillOval(x, y, d, d); break;
                case "Line":
                    ((Graphics2D) g).setStroke(new BasicStroke(s.size)); 
                    ((Graphics2D) g).drawLine(s.x1, s.y1, s.x2, s.y2);
                    ((Graphics2D) g).setStroke(new BasicStroke(1)); 
                    break;
            }
        }


        for (PenPoint p : points) {
            g.setColor(p.color); 
            g.fillOval(p.x - p.size / 2, p.y - p.size / 2, p.size, p.size);
        }
        
        

        //Preview Selected Shapes
        if (isDrawingShape && currentTool.equals("Shape")) {
            g.setColor(currentColor);
            int x = Math.min(shapeStartX, mousePos.x);
            int y = Math.min(shapeStartY, mousePos.y);
            int w = Math.abs(mousePos.x - shapeStartX);
            int h = Math.abs(mousePos.y - shapeStartY);
        
            switch (currentShape) {
                case "Rectangle": g.drawRect(x, y, w, h); break;
                case "Circle":
                    int d = Math.max(w, h);
                    g.drawOval(x, y, d, d); break;
                case "Line":
                    g.drawLine(shapeStartX, shapeStartY, mousePos.x, mousePos.y); break;
            }
        }


        // Draw gray preview circle
        g.setColor(Color.GRAY);
        g.drawOval(mousePos.x - brushSize / 2, mousePos.y - brushSize / 2, brushSize, brushSize);
    }

    //Erase Shapes and Pen Strokes
    private void eraseAt(int x, int y) {
        // Remove pen points within brush radius
        points.removeIf(p ->
            Math.abs(p.x - x) <= brushSize / 2 &&
            Math.abs(p.y - y) <= brushSize / 2
        );
    
        // Remove any shape the cursor is touching
        shapes.removeIf(s -> {
            int left = Math.min(s.x1, s.x2);
            int right = Math.max(s.x1, s.x2);
            int top = Math.min(s.y1, s.y2);
            int bottom = Math.max(s.y1, s.y2);
    
            return x >= left && x <= right && y >= top && y <= bottom;
        });
    }




    //Paints on click 
    @Override
    public void mousePressed(MouseEvent e) {
        mousePos = e.getPoint();

        if (currentTool.equals("Eraser")) {
            eraseAt(e.getX(), e.getY());
            repaint();
            return; 
        }

        if (currentTool.equals("Pen")) {
            points.add(new PenPoint(e.getX(), e.getY(), brushSize, currentColor));
            repaint();
        }

        if (currentTool.equals("Shape")) {
            shapeStartX = e.getX();
            shapeStartY = e.getY();
            isDrawingShape = true;
        }
    }      


    //Paints when Dragging mouse
    @Override
    public void mouseDragged(MouseEvent e) {
        mousePos = e.getPoint();

        if (currentTool.equals("Eraser")) {
            eraseAt(e.getX(), e.getY());
            repaint();
            return; 
        }

        if (currentTool.equals("Pen")) {
            points.add(new PenPoint(e.getX(), e.getY(), brushSize, currentColor));
        }

        repaint();
    }


    
    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = e.getPoint();
        repaint();
    }


    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentTool.equals("Shape") && isDrawingShape) {
            shapes.add(new DrawnShape(
                shapeStartX, shapeStartY,
                e.getX(), e.getY(),
                currentShape, brushSize, currentColor
            ));
            isDrawingShape = false;
            repaint();
        }
    }
    // Unused methods 
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    //Nested Class For Keeping Track of Different Points and sizes
    private static class PenPoint {
        int x, y, size;
        Color color;
    
        public PenPoint(int x, int y, int size, Color color) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.color = color;
        }
    }
    
    //Nested Class For drawing shapes
    private static class DrawnShape {
        int x1, y1, x2, y2;
        String shapeType;
        int size;
        Color color;
    
        public DrawnShape(int x1, int y1, int x2, int y2, String shapeType, int size, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.shapeType = shapeType;
            this.size = size;
            this.color = color;
        }
    }
    
}