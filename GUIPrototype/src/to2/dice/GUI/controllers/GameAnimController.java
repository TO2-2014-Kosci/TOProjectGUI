package to2.dice.GUI.controllers;

import java.util.Random;

import com.bulletphysics.linearmath.QuaternionUtil;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.game.GameState;

public class GameAnimController implements ActionListener {
	private GameAnimation gameAnimation;
	private Model model;
	private Random r = new Random();
	
	public GameAnimController(Model model) {
		this.model = model ;
	}
	
	//TODO
	 public boolean[] getSelectedDice(){
		return model.getSelectedDice();
	 }
	 
	 public void setGameAnimation(GameAnimation gameAnimation) {
		 this.gameAnimation = gameAnimation;
	 }
	 
	 public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("Shake")) {
				Random r = new Random();
				for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
//					if (model.getSelectedDice()[i]) {
//						gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.QUARTER_PI, new Vector3f(1,0,1)));
						gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(-16, -2 + i, 10));
						gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setLinearVelocity(new Vector3f(10, 0, 0));
						gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setAngularVelocity(new Vector3f(r.nextInt(10), r.nextInt(10), r.nextInt(10)));
						gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).activate();
						model.setTimer(model.getGameSettings().getTimeForMove());
						model.getSelectedDice()[i] = false;
//					}
				}
				
			} else if (name.equals("Put")) {
				for (int i = 0; i < 5; i++) {
					gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(-7, -4 + 2 * i, 0f));
				}
			} else if (name.equals("Select") && !keyPressed) {
				// Reset results list.
				CollisionResults results = new CollisionResults();
				// Convert screen click to 3d position
				Vector2f click2d = gameAnimation.getInputManager().getCursorPosition();
				Vector3f click3d = gameAnimation.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
				Vector3f dir = gameAnimation.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d).normalizeLocal();
				// Aim the ray from the clicked spot forwards.
				Ray ray = new Ray(click3d, dir);
				gameAnimation.getRootNode().collideWith(ray, results);
				if (results.size() > 0) {
					
					Spatial target = results.getClosestCollision().getGeometry().getParent().getParent();
					if (target.getName().equals("Model/Dice/dice.blend")) {
						for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
							if (gameAnimation.getUserDice()[i].equals(target)) {
								if (model.getSelectedDice()[i]) {
									model.getSelectedDice()[i] = false;
									//results.getClosestCollision().getGeometry().getMaterial().setColor("Diffuse", ColorRGBA.White);
								} else {
									model.getSelectedDice()[i] = true;
									//results.getClosestCollision().getGeometry().getMaterial().setColor("Diffuse", ColorRGBA.Green);
								}
								gameAnimation.selectDice(i);
							}
						}
					}
				}
			}
		}
}