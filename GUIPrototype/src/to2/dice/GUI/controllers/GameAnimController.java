package to2.dice.GUI.controllers;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameAnimation;

public class GameAnimController implements ActionListener {
	private GameAnimation gameAnimation;
	private Model model;
	
	public GameAnimController(Model model) {
		this.model = model ;
	}
	
	//TODO
	 public boolean[] getSelectedDice(){
		return null;
	 }
	 
	 public void setGameAnimation(GameAnimation gameAnimation) {
		 this.gameAnimation = gameAnimation;
	 }
	 
	 public void onAction(String name, boolean keyPressed, float tpf) {
			if (name.equals("Shake")) {
				for (int i = 0; i < 5; i++) {
					gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(-16, -2 + i, 10));
					gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setLinearVelocity(new Vector3f(14, 0, 0));
					gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).activate();
				}
			} else if (name.equals("Put")) {
				for (int i = 0; i < 5; i++) {
					gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(-7, -4 + 2 * i, -1.5f));
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
					results.getClosestCollision().getGeometry().getMaterial().setColor("GlowColor", ColorRGBA.Green);
					Spatial target = results.getClosestCollision().getGeometry().getParent().getParent();
					if (target.getName().equals("Model/Dice/dice.blend")) {
						for (int i = 0; i < model.gameSettings.getDiceNumber(); i++) {
							if (gameAnimation.getUserDice()[i].equals(target)) {
								gameAnimation.selectDice(i);
							}
						}
					}
				}
			}
		}
}