package uet.oop.bomberman.sound;
import sun.applet.Main;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    private static Clip myClip;

    public static void play(String sound) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    myClip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/sound/" + sound + ".wav"));
                    myClip.open(inputStream);
                    myClip.start();
                    if (sound.equals("ghost")) {
                        loopInf();
                    } else {
                        return;
                    }
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();

    }
    public static void stop() {
        myClip.stop();
    }

    public static void loopInf(){
        if (myClip.isActive()){
            return;
        }
        myClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
