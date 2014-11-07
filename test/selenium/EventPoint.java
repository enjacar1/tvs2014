/**
 * JRobot.java 
 *
 * Created - 31/07/2013
 */
package selenium;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 * 
 *
 * @author Gustavo Bailon
 *
 */
public class EventPoint {

	private Robot robot;
	
	public EventPoint(){
		try {
			robot = new Robot();
		} catch (AWTException e) {

		}
	}
	
	public void click(final int valueX, final int valueY) {
		robot.setAutoDelay(1);
		robot.mouseMove(valueX, valueY);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public void sendKey(final int key) {
		robot.setAutoDelay(200);
		robot.keyPress(key);
		robot.keyRelease(key);
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(final Robot robot) {
		this.robot = robot;
	}
	
	
}
