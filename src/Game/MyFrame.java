package Game;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;

import gameClient.util.Range;
import gameClient.util.Range2D;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;


/**
 * This class represents a very simple GUI class to present a
 * game on a graph - you are welcome to use this class - yet keep in mind
 * that the code is not well written in order to force you improve the
 * code and not to take it "as is".
 */
public class MyFrame extends JFrame {
    private int _ind;
    private Arena _ar;
    private gameClient.util.Range2Range _w2f;
    private Image bufferImage;
    private Graphics bufferGraphics;

    MyFrame(String a) {
        super(a);
        int _ind = 0;
        this.setTitle("Ex2");
        this.setBackground(Color.white);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void update(Arena ar) {
        this._ar = ar;
        updateFrame();
    }

    private void updateFrame() {
        Range rx = new Range(20, this.getWidth() - 20);
        Range ry = new Range(this.getHeight() - 10, 150);
        Range2D frame = new Range2D(rx, ry);
        directed_weighted_graph g = _ar.getGraph();
        _w2f = Arena.w2f(g, frame);
    }

    public void paint(Graphics g) {
        int w = this.getWidth();
        int h = this.getHeight();
        updateFrame();
        bufferImage = createImage(w, h);
        bufferGraphics = bufferImage.getGraphics();
        paintComponent(bufferGraphics);
        g.drawImage(bufferImage, 0, 0, this);

    }

    public void paintComponent(Graphics bufferGraphics) {
        int w = this.getWidth();
        int h = this.getHeight();
        drawPokemons(bufferGraphics);
        drawGraph(bufferGraphics);
        drawAgants(bufferGraphics);
        drawInfo(bufferGraphics);
        bufferGraphics.setFont(new Font("name", Font.PLAIN,16));
        geo_location geo = _w2f.getFrame().fromPortion(new Point3D(0,0,0)) ;
        int pX = (int) geo.x() ;
        int pY = (int) geo.y() ;
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.drawString("Moves:" + _ar.getTotalMoves() + "  Score:" + _ar.getScore() + "  Level: "
                + _ar.getLevel()+"  Time to end: "+_ar.getTimer()/1000+" sec", pX, pY-4);

    }

    private void drawInfo(Graphics g) {
        List<String> str = _ar.get_info();
        String dt = "none";
        for (int i = 0; i < str.size(); i++) {
            g.drawString(str.get(i) + " dt: " + dt, 100, 60 + i * 20);
        }

    }

    private void drawGraph(Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        Iterator<node_data> iter = gg.getV().iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            g.setColor(Color.blue);
            drawNode(n, 5, g);
            Iterator<edge_data> itr = gg.getE(n.getKey()).iterator();
            while (itr.hasNext()) {
                edge_data e = itr.next();
                g.setColor(Color.gray);
                drawEdge(e, g);
            }
        }
    }

    private void drawPokemons(Graphics g) {
        for (Pokemon p : _ar.getPokemons()) {
            Point3D location = p.getLocation();
            int r = 10;
            g.setColor(Color.green);
            if (p.getType() < 0) {
                g.setColor(Color.orange);
            }
            geo_location converted_pos = _w2f.world2frame(location);
            int x = (int) converted_pos.x() - r;
            int y = (int) converted_pos.y() - r;
            g.fillOval(x, y, 2 * r, 2 * r);
        }
    }

    private void drawAgants(Graphics g) {
        List<Agent> rs = _ar.getAgents();
        g.setColor(Color.red);
        int i = 0;
        Graphics2D graphics2D = (Graphics2D) g;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image agent = toolkit.getImage("Pics\\ketch.png");
        while (rs != null && i < rs.size()) {
            geo_location c = rs.get(i).getAgentLocation();
            int r = 8;
            i++;
            if (c != null) {

                geo_location fp = this._w2f.world2frame(c);
                graphics2D.drawImage(agent,(int)fp.x()-r,(int)fp.y()-(r+15),4*r,4*r,this);
            }
        }
    }

    private void drawNode(node_data n, int r, Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image location = toolkit.getImage("Pics\\location.png");
        g.setFont(new Font("name", Font.BOLD,15));
        geo_location pos = n.getLocation();
        geo_location fp = this._w2f.world2frame(pos);
        graphics2D.drawImage(location,(int)fp.x()-r,(int)fp.y()-r-12,3*r,3*r,this);
        g.drawString("" + n.getKey(), (int) fp.x(), (int) fp.y() - 4 * r);
    }

    private void drawEdge(edge_data e, Graphics g) {
        directed_weighted_graph gg = _ar.getGraph();
        geo_location s = gg.getNode(e.getSrc()).getLocation();
        geo_location d = gg.getNode(e.getDest()).getLocation();
        geo_location s0 = this._w2f.world2frame(s);
        geo_location d0 = this._w2f.world2frame(d);
        g.drawLine((int) s0.x(), (int) s0.y(), (int) d0.x(), (int) d0.y());
    }
}

