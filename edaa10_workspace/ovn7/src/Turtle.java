import se.lth.cs.window.SimpleWindow;

public class Turtle {
 
  /** skapar en sk�ldpadda som ritar i ritf�nstret w. 
      Fr�n b�rjan befinner sig sk�ldpaddan i punkten xHome,yHome med pennan 
      lyft och huvudet pekande rakt upp�t i f�nstret, dvs i negativ y-riktning  
  */
  Turtle(SimpleWindow w, int xHome, int yHome) {
  }

  /** s�nker pennan */
  void penDown() {
  }

  /** lyfter pennan */
  void penUp() {
  }

  /** g�r rakt fram�t n pixlar i huvudets riktning */
  void forward(int n) {
  }

  /** vrider huvudet beta grader �t v�nster */
  void left(int beta) {
  }

  /** vrider hvudet beta grader �t h�ger */
  void right(int beta) {
  }

  /** g�r till punkten newX,newY utan att rita. 
      Pennans l�ge och huvudets riktning p�verkas inte */
  void jumpTo(int newX, int newY) {
  }

  /** �terst�ller huvudets riktning till den ursprungliga */
  void turnNorth() {
  }

  /** tar reda p� sk�ldpaddans aktuella x-koordinat */
  int getX() {
  }

  /** tar reda p� sk�ldpaddans aktuella y-koordinat */
  int getY() {
  }
}
