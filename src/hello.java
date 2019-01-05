import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;


public class hello {
public static void main (String[]args) throws AWTException{
	Robot r = new Robot();
	r.mouseMove(100, 51);
	int mask = InputEvent.BUTTON1_DOWN_MASK;
	r.mousePress(mask);
	r.mouseRelease(mask);
}
}
