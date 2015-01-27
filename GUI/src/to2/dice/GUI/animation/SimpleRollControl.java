package to2.dice.GUI.animation;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class SimpleRollControl extends AbstractRollControl {

	private Quaternion targetRotate;
	private Spatial[] dices;
	
	public SimpleRollControl(int diceName, Spatial[] dices) {
		super();
		super.setEnabled(false);
		this.dices = dices;
	}

	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void controlUpdate(float arg0) {
		if (startRoll) {
			RigidBodyControl diceControl = spatial.getControl(RigidBodyControl.class);
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
			diceControl.setLinearVelocity(new Vector3f(0, 0, 0));
			diceControl.setPhysicsLocation(Utils.randomLocation());
			diceControl.setPhysicsRotation(targetRotate);
			Geometry d6 = (Geometry) spatial;
			d6.getMaterial().setColor("Diffuse", ColorRGBA.White);
//			CollisionResults col = new CollisionResults();
//			for (Spatial d: dices) {
//				if (!d.equals(spatial)) {
//					if (spatial.collideWith(d.getWorldBound(), col) > 0) {
//						
//					}
//					System.out.println(col);
//				}
//			}
			while (isCollision()) {
				diceControl.setPhysicsLocation(Utils.randomLocation());
			}
			startRoll = false;
			this.setEnabled(false);
		}
	}
	
	private boolean isCollision() {
		for (Spatial d: dices) {
			if (!d.equals(spatial)) {
				if (Utils.calcDistance(spatial, d) < 0.9) {
					return true;
				}
			}
		}
		return false;
	}
}
