package to2.dice.GUI.animation;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Matrix3f;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

public class UserRollControl extends AbstractControl {
	private int number;
	private boolean startRoll;
	private Quaternion targetRotate;
	private int diceName;
	private Vector3f[] controlPoints = { new Vector3f(0, 1, 0), // 1
			new Vector3f(1, 0, 0), // 2
			new Vector3f(0, 0, 1), // 3
			new Vector3f(0, 0, -1), // 4
			new Vector3f(-1, 0, 0), // 5
			new Vector3f(0, -1, 0) // 6
	};

	private int step;

	public UserRollControl(int diceName) {
		super();
		super.setEnabled(false);
		this.diceName = diceName;
	}

	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void controlUpdate(float arg0) {
		if (startRoll) {
			RigidBodyControl diceControl = this.spatial.getControl(RigidBodyControl.class);
			diceControl.setEnabled(true);
			switch (number) {
			case 1:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(1, 0, 0));
				break;
			case 2:
				targetRotate = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(0, 1, 0));
				break;
			case 3:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.ZERO_TOLERANCE, new Vector3f(1, 0, 0));
				break;
			case 4:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.PI, new Vector3f(1, 0, 0));
				break;
			case 5:
				targetRotate = new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(0, 1, 0));
				break;
			case 6:
				targetRotate = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(1, 0, 0));
				break;
			}
			diceControl.setPhysicsLocation(Util.randomLocation());
			diceControl.setPhysicsRotation(targetRotate);
			Node n = ((Node)spatial);
			((Geometry) n.getChild("Cube1")).getMaterial().setColor("Diffuse", ColorRGBA.White);
			startRoll = false;
			this.setEnabled(false);
		}
			
//		RigidBodyControl diceControl = this.spatial.getControl(RigidBodyControl.class);
//		if (startRoll) {
//			diceControl.setEnabled(true);
//			switch (number) {
//			case 1:
//				targetRotate = new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(1, 0, 0));
//				break;
//			case 2:
//				targetRotate = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(0, 1, 0));
//				break;
//			case 3:
//				targetRotate = new Quaternion().fromAngleAxis(FastMath.ZERO_TOLERANCE, new Vector3f(1, 0, 0));
//				break;
//			case 4:
//				targetRotate = new Quaternion().fromAngleAxis(FastMath.PI, new Vector3f(1, 0, 0));
//				break;
//			case 5:
//				targetRotate = new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(0, 1, 0));
//				break;
//			case 6:
//				targetRotate = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(1, 0, 0));
//				break;
//
//			}
//			diceControl.setPhysicsLocation(new Vector3f(-16, 2 * (-2 + diceName), 10));
//			diceControl.setLinearVelocity(new Vector3f(12, 3 * (2 - diceName), 0));
//			diceControl.setAngularVelocity(new Vector3f(random(), random(), random()));
//			Node n = ((Node)spatial);
//			((Geometry) n.getChild("Cube1")).getMaterial().setColor("Diffuse", ColorRGBA.White);
//			startRoll = false;
//		}
////		 System.out.print(spatial.getName() + " ");
//		// System.out.println(number);
//
//		// System.out.print(currentRotate);
//		// System.out.print(" ");
//		// System.out.println(targetRotate);
//		// if (targetRotate.add(negateCurrentRotate).toAngleAxis(null)) {
//
//		// }
//
//		Vector3f up = new Vector3f(0, 0, 1);
//		Matrix3f currentRotateMatrix = diceControl.getPhysicsRotation().toRotationMatrix();
//		currentRotateMatrix = currentRotateMatrix.invert();
//		up = currentRotateMatrix.mult(up).normalizeLocal();
//		int actualNumber = -1;
//		float minDistance = 10000;
//		for (int i = 0; i < controlPoints.length; i++) {
//			float distance = up.distance(controlPoints[i]);
//			if (distance < minDistance) {
//				minDistance = distance;
//				actualNumber = i;
//			}
//		}
//		actualNumber += 1;
//
//		if (step < 5 && step != 0) {
//			Quaternion currentRotate = diceControl.getPhysicsRotation();
//			currentRotate.slerp(targetRotate, step);
//			diceControl.setPhysicsRotation(currentRotate);
//		}
//		step--;
//		if (diceControl.getLinearVelocity().length() < 0.001f && diceControl.getAngularVelocity().length() < 0.001f) {
//			this.setEnabled(false);
//		}
//		if (step == -1
//				|| (diceControl.getLinearVelocity().length() < 0.1f || diceControl.getAngularVelocity().length() < 1f)) {
//			step = 0;
//			diceControl.setAngularVelocity(new Vector3f(0, 0, 0));
////		this.setEnabled(false);
//		}
	}

	public void setNumberToRoll(int number) {
		synchronized (this.spatial) {
			this.number = number;
			setEnabled(true);
			startRoll = true;
			step = 170;
		}
	}

}
