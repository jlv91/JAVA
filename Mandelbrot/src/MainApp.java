
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class MainApp  {

    // to generate a name when saving image
    private int imageIndex = 0;

    public MainApp(ImagePanel imagePanel) {
        buildFrame(imagePanel);
    }

    private JFrame buildFrame(final ImagePanel imagePanel) {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.add(imagePanel, BorderLayout.CENTER);
        frame.pack();
        // Mouse wheel controls zoom
        frame.addMouseWheelListener(new MouseWheelListener() {
            public void mouseWheelMoved(MouseWheelEvent e) {
                // zoom in
                if (e.getWheelRotation() < 0) {
                    imagePanel.zoomIn();
                } else {
                    imagePanel.zoomOut();
                }
                imagePanel.repaint();
            }
        });

        // Click and move to move image
        frame.addMouseListener(new MouseAdapter() {
            private Point initialPoint;
            public void mousePressed(MouseEvent e) {
                initialPoint = e.getPoint();
            }
            public void mouseReleased(MouseEvent e) {
                Point finalPoint = e.getPoint();
                imagePanel.addTranslation(finalPoint, initialPoint);
                imagePanel.repaint();
            }
        });

        // s to save the frame
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent ke) {
                switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_S) {
                            BufferedImage image = new BufferedImage(imagePanel.getWidth(), imagePanel.getHeight(), BufferedImage.TYPE_INT_RGB);
                            Graphics g = image.createGraphics();
                            imagePanel.print(g);
                            g.dispose();
                            File outputfile = new File("Image" + imageIndex + ".jpg");
                            imageIndex++;
                            try {
                                ImageIO.write(image, "jpg", outputfile);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                }
                return false;
            }
        });

        return frame;
    }
}
