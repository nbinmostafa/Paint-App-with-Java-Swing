import javax.swing.*;             
import java.awt.*;                


public class PaintApp{
    
    private JFrame frame;
    
    public PaintApp(){

        frame = new JFrame("Mini-Paint App");

        //Create and add canvas
        DrawingPanel canvas = new DrawingPanel();
        canvas.setBounds(0, 60, 1300, 800);
        frame.add(canvas);

        frame.setSize(1300,850);
        frame.setLayout(null);
        

        //Pen
        JButton penBtn = new JButton();
        penBtn.setText("Pen");
        penBtn.setBounds(10,10,90,25);

        canvas.setTool("Pen"); 
        penBtn.addActionListener(e -> {
            canvas.setTool("Pen");
            canvas.setColor(canvas.getSavedColor()); 
        });

        //Eraser
        JButton eraser = new JButton();
        eraser.setText("Eraser");
        eraser.setBounds(100,10,90,25);
        eraser.addActionListener(e -> {
            canvas.setTool("Eraser");
            canvas.setColor(Color.WHITE); 
        });

        //Rectangle 
        JButton rectBtn = new JButton();
        rectBtn.setText("Rectangle");
        rectBtn.setBounds(190,10,100,25);
        rectBtn.addActionListener(e -> {
            canvas.setShape("Rectangle");
            canvas.setColor(canvas.getSavedColor());
        });

        //Circle
        JButton circBtn = new JButton();
        circBtn.setText("Circle");
        circBtn.setBounds(290,10,90,25);
        circBtn.addActionListener(e -> {
            canvas.setShape("Circle");
            canvas.setColor(canvas.getSavedColor());
            
        });

        //Line
        JButton lineBtn = new JButton();
        lineBtn.setText("Line");
        lineBtn.setBounds(380,10,90,25);
        lineBtn.addActionListener(e -> {
            canvas.setShape("Line");
            canvas.setColor(canvas.getSavedColor());
            
        });

        //Size Slider
        JSlider sizeSlider = new JSlider(0, 50, 10);
        sizeSlider.setMajorTickSpacing(10);
        sizeSlider.setMinorTickSpacing(5);
        sizeSlider.setPaintTicks(true);
        sizeSlider.setPaintLabels(true);
        sizeSlider.setBounds(480, 10, 200, 50);

        sizeSlider.addChangeListener(e -> {
            canvas.setBrushSize(sizeSlider.getValue());
        });

        //Color Picker
        JButton colorPickerBtn = new JButton("Pick Color");
        colorPickerBtn.setBounds(690, 10, 120, 25);
        colorPickerBtn.addActionListener(e -> {
        Color selectedColor = JColorChooser.showDialog(frame, "Choose a Color", Color.BLACK);
        if (selectedColor != null) {
            canvas.setColor(selectedColor);
        }
        });
        


        frame.add(eraser);
        frame.add(penBtn);
        frame.add(rectBtn);
        frame.add(circBtn);
        frame.add(lineBtn);
        frame.add(sizeSlider);
        frame.add(colorPickerBtn);
        frame.setVisible(true);

        }

}