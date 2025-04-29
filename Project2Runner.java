

public class Project2Runner {
    
    /*
     * Name: Nafi Bin Mostafa
     * Student ID: 501304144
     * 
     ******** Project Description ********
     * This project is a custom-built paint application developed using Java Swing and AWT. It allows users
     * to draw freehand using a pen tool, erase parts of their drawing, and create shapes such as rectangles,
     * circles, and lines. Users can also change the brush size and color using a slider and a color picker 
     * interface. A live preview shows the brush size and location to assist with precision. The app maintains 
     * color state even after using the eraser and includes real-time mouse tracking for accurate rendering.
     * 
     * 
     *
     * 
     ******** Swing Requirement ********
     * 
     * JButton is used for tools like Pen, Eraser, Rectangle, Circle, Line (defined in paintApp.java, line 23).
     *  JSlider is used for adjusting brush size (paintApp.java, line 72).
     *  JColorChooser is used for selecting colors (paintApp.java, line 87).
     * 
     * 
     * 
     ******** 2D Graphics Requirement ********
     *
     *The program uses a custom `JPanel` subclass called `DrawingPanel` to handle all 2D drawing (DrawingPanel.java, line 10).
     * This panel overrides `paintComponent(Graphics g)` to render pen strokes, shapes, and previews using Java’s 2D graphics API.
     * 
     * 
     * 
     * 
     ******** Event Listener Requirement ********
     *
     * The program uses multiple listeners to handle user interaction:
     * ActionListener is used in paintApp.java (line 28) to respond to button clicks like Pen, Eraser, Shape tools, etc.
     * MouseListener and MouseMotionListener are implemented in DrawingPanel.java (line 37,38) to track drawing, erasing,
     * and shape creation through user input to provides a responsive drawing experience.
     */

    public static void main(String[] args) {
        new PaintApp();
    }
}
