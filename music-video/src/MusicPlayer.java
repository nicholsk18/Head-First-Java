import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;

public class MusicPlayer{
    static JFrame frame = new JFrame("My First Music Video");
    static MyDrawPanel ml;

    public static void main(String[] args) {
        MusicPlayer mini = new MusicPlayer();
        mini.play();
    }

    public void setUpGui () {
        ml = new MyDrawPanel();
        frame.setContentPane(ml);
        frame.setBounds(30, 30, 300, 300);
        frame.setVisible(true);
    }

    public void play() {
        setUpGui();

        try {
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.addControllerEventListener(ml, new int[] {127});
            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            int r = 0;
            for (int i = 0; i < 60; i+=4) {
                r = (int) ((Math.random() * 50) + 1);
                track.add(makeEvent(144, 1, r, 100, i));
                track.add(makeEvent(176, 1, 127, 0, i));
                track.add(makeEvent(128, 1, r, 100, i + 2));
            }

            sequencer.setSequence(seq);
            sequencer.start();
            sequencer.setTempoInBPM(120);

        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            event = new MidiEvent(a, tick);
        } catch (Exception e) {}
        return event;
    }

    class MyDrawPanel extends JPanel implements ControllerEventListener {
        boolean msg = false;

        // Needed to make sure repaint only happens on beat
        public void controlChange(ShortMessage event) {
            msg = true;
            repaint();
        }

        public void paintComponent(Graphics g) {
            if(msg) {
                // Graphics need to be cast in to g2 to display 3D
                Graphics2D g2 = (Graphics2D) g;

                int r = (int) (Math.random() * 250);
                int gr = (int) (Math.random() * 250);
                int b = (int) (Math.random() * 250);

                // set rec color
                g.setColor(new Color(r, gr, b));

                int ht = (int) ((Math.random() * 120) + 10);
                int width = (int) ((Math.random() * 120) + 10);

                int x = (int) ((Math.random() * 40) + 10);
                int y = (int) ((Math.random() * 40) + 10);

                g.fillRect(x, y, ht, width);
                msg = false;
            }
        }


    }

}
