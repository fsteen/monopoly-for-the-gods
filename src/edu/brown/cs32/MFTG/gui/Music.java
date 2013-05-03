//import javax.media.*;
//import java.net.URL;
//  
////need java media framework (don't know how to get it)http://www3.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
//public class Music extends Thread {
//   
//   private String filename;
//   Player player;
//   
//   public Music(String mp3Filename) {
//      this.filename = mp3Filename;
//   }
//   
//   public void run() {
//      try {
//         URL url = this.getClass().getClassLoader().getResource(filename);
//         MediaLocator locator = new MediaLocator(url);
//         player = Manager.createPlayer(locator);
//         player.addControllerListener(new ControllerListener() {
//            public void controllerUpdate(ControllerEvent event) {
//               if (event instanceof EndOfMediaEvent) {
//                  player.stop();
//                  player.close();
//               }
//            }
//         });
//         player.realize();
//         player.start();
//      } catch (Exception e) {
//         e.printStackTrace();
//      }
//   }
//   
//   public static void main(String[] args) {
//      new Mp3PlayerDemo("song.mp3").start();
//   }
//}