package src;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;


import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // JFrame object 
        JFrame frame = new JFrame();
        // Window Toolkit
        Container pane = frame.getContentPane();
        //
        pane.setLayout(new BorderLayout());

        // 
        JSlider horizontalSlider = new JSlider(-180, 180, 0);
        // 
        pane.add(horizontalSlider, BorderLayout.SOUTH);

        // 
        JSlider verticalSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        // Place
        pane.add(verticalSlider, BorderLayout.EAST);

        // DrawPanel to render
        JPanel renderPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                // Cast to 2d
                Graphics2D g2 = (Graphics2D)g;
                // 
                g2.setPaint(Color.BLACK);
                // 
                g2.fillRect(0, 0, this.getWidth(), this.getHeight());

                // wichtig 
                List<Triangle> tetrahedron = new ArrayList<>();
                tetrahedron.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100),
                    new Vertex(-100, 100, -100), Color.WHITE));
                tetrahedron.add(new Triangle(new Vertex(100, 100, 100), new Vertex(-100, -100, 100),
                    new Vertex(100, -100, -100), Color.RED));
                tetrahedron.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(100, -100, -100),
                    new Vertex(100, 100, 100), Color.GREEN));
                tetrahedron.add(new Triangle(new Vertex(-100, 100, -100), new Vertex(100, -100, -100),
                    new Vertex(-100, -100, 100), Color.BLUE));

                //wichtig 
                double horizontalRadius = Math.toRadians(horizontalSlider.getValue());
               
                Matrix3 XZTransform = new Matrix3(new double[] {
                    Math.cos(horizontalRadius), 0,
                    Math.sin(horizontalRadius), 0, 1, 0, -Math.sin(horizontalRadius),
                    0, Math.cos(horizontalRadius)});

                // 
                double verticalRadius = Math.toRadians(verticalSlider.getValue());
                
                Matrix3 YZTransform = new Matrix3(new double[] {
                    1, 0, 0, 0, Math.cos(verticalRadius), Math.sin(verticalRadius), 0, Math.sin(verticalRadius),
                    Math.cos(verticalRadius)});

                // 
                Matrix3 XYZTransform = XZTransform.multiply(YZTransform);

                g2.translate(getWidth() / 2, getHeight() / 2);
                g2.setColor(Color.WHITE);
                for (Triangle triangle : tetrahedron) {
            
                       //  Vertex v1 = XYZTransform.transform(triangle.v1);
                       //  Vertex v2 = XYZTransform.transform(triangle.v2);
                       //  Vertex v3 = XYZTransform.transform(triangle.v3);
                       //  Path2D path = new Path2D.Double();
// 
                       //  path.moveTo(v1.x, v1.y);
                       //  path.lineTo(v2.x, v2.y);
                       //  path.lineTo(v3.x, v3.y);
                       //  path.closePath();
                       //  g2.draw(path);

                        Path2D path = new Path2D.Double();
                        path.moveTo(triangle.v1.x, triangle.v1.y);
                        path.lineTo(triangle.v2.x, triangle.v2.y);
                        path.lineTo(triangle.v3.x, triangle.v3.y);
                        path.closePath();
                        g2.draw(path);
                }
            }
        };
        horizontalSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                renderPanel.repaint();
            }
        });

       verticalSlider.addChangeListener(new ChangeListener() {
           @Override
           public void stateChanged(ChangeEvent changeEvent) {
               renderPanel.repaint();
           }
       });

        pane.add(renderPanel, BorderLayout.CENTER);

        frame.setSize(600, 600);

        frame.setVisible(true);
    }
}  
                  

       

     
   
    