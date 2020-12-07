package task4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;

import models.Parallelepiped;
import task4.draw.IDrawer;
import task4.draw.SimpleEdgeDrawer;
import task4.math.Vector3;
import task4.screen.ScreenConverter;
import task4.third.Camera;
import task4.third.Scene;

/**
 * @author Alexey
 */
public class DrawPanel extends JPanel
        implements CameraController.RepaintListener {
    private static Scene scene;
    private ScreenConverter sc;
    private Camera cam;
    private CameraController camController;
    private static Vector3 v1 = new Vector3(-0.4, -0.4, -0.4);
    private static Vector3 v2 = new Vector3(0.4, 0.4, 0.4);
    private Timer timer;

    public DrawPanel() {
        super();
        setFocusable(true);
        sc = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        cam = new Camera();
        camController = new CameraController(cam, sc);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();
        timer = new Timer(40, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scene.getRc().changeMatrix();
                repaint();
            }
        });
        timer.start();
        scene.getModelsList().add(new Parallelepiped(v1, v2));

        camController.addRepaintListener(this);
        addMouseListener(camController);
        addMouseMotionListener(camController);
        addMouseWheelListener(camController);
    }

    @Override
    public void paint(Graphics g) {
        sc.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D) bi.getGraphics();
        IDrawer dr = new SimpleEdgeDrawer(sc, graphics);
        scene.drawScene(dr, cam);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    public static void turn() {

    }

    @Override
    public void shouldRepaint() {
        repaint();
    }
}