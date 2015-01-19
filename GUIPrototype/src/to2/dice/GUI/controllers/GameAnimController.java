package to2.dice.GUI.controllers;

import to2.dice.GUI.animation.AnotherPutControl;
import to2.dice.GUI.animation.HideControl;
import to2.dice.GUI.animation.RollControl;
import to2.dice.GUI.animation.TextControl;
import to2.dice.GUI.animation.UserPutControl;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.game.Dice;

import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

public class GameAnimController extends AbstractControl implements ActionListener {
	private GameAnimation gameAnimation;
	private Model model;

	public GameAnimController(Model model) {
		this.model = model;
	}

	// TODO
	public boolean[] getSelectedDice() {
		return model.getSelectedDice();
	}

	public void setGameAnimation(GameAnimation gameAnimation) {
		this.gameAnimation = gameAnimation;
	}

	@Override
	public void onAction(String name, boolean keyPressed, float tpf) {

		if (name.equals("Select") && !keyPressed) {
			if (model.isMyTurn()) {
				// Reset results list.
				CollisionResults results = new CollisionResults();
				// Convert screen click to 3d position
				Vector2f click2d = gameAnimation.getInputManager().getCursorPosition();
				Vector3f click3d = gameAnimation.getCamera()
						.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
				Vector3f dir = gameAnimation.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f)
						.subtractLocal(click3d).normalizeLocal();
				// Aim the ray from the clicked spot forwards.
				Ray ray = new Ray(click3d, dir);
				gameAnimation.getRootNode().collideWith(ray, results);
				if (results.size() > 0) {

					Spatial target = results.getClosestCollision().getGeometry().getParent().getParent();
					if (target.getName().equals("dice")) {
						for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
							if (gameAnimation.getUserDice()[i].equals(target)) {
								if (model.getSelectedDice()[i]) {
									model.getSelectedDice()[i] = false;
									results.getClosestCollision().getGeometry().getMaterial()
											.setColor("Diffuse", ColorRGBA.White);
								} else {
									model.getSelectedDice()[i] = true;
									results.getClosestCollision().getGeometry().getMaterial()
											.setColor("Diffuse", ColorRGBA.Green);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	protected void controlRender(RenderManager rm, ViewPort vp) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void controlUpdate(float tpf) {
		setEnabled(false);
	}

	public void destroy() {
		gameAnimation.stop();
		gameAnimation.destroy();
	}

	public void shakeAnotherDice(Dice dice) {
		synchronized (model) {
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				RollControl diceControl = gameAnimation.getAnotherDice()[i].getControl(RollControl.class);
				diceControl.setNumberToRoll(dice.getDiceArray()[i]);
			}
		}
	}

	public void shakeUserDice(Dice dice) {
		synchronized (model) {
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				if (model.getSelectedDice()[i]) {
					gameAnimation.getUserDice()[i].getControl(RollControl.class)
							.setNumberToRoll(dice.getDiceArray()[i]);
				}
			}
		}
	}

	public void putAnotherDice(Dice dice) {
		synchronized (model) {
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				AnotherPutControl diceControl = gameAnimation.getAnotherDice()[i].getControl(AnotherPutControl.class);
				diceControl.setNumberToPut(dice.getDiceArray()[i]);
			}
		}
	}

	public void putUserDice(Dice dice) {
		synchronized (model) {
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				gameAnimation.getUserDice()[i].getControl(UserPutControl.class).setNumberToPut(dice.getDiceArray()[i]);
			}
		}
	}

	public void hideAnotherDice() {
		synchronized (model) {
			if (gameAnimation.getAnotherDice() == null) {
				try {
					model.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				gameAnimation.getAnotherDice()[i].getControl(HideControl.class).setHide(true);
			}
		}
	}

	public void showAnotherDice() {
		synchronized (model) {
			if (gameAnimation.getAnotherDice() == null) {
				try {
					model.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				gameAnimation.getAnotherDice()[i].getControl(HideControl.class).setHide(false);
			}
		}
	}

	public void showText(String text, int size) {
		synchronized (model) {
			gameAnimation.getBitmapText().getControl(TextControl.class).setText(text);
			gameAnimation.getBitmapText().setSize(size);
		}
	}

	public void hideBoxAndDice() {
		synchronized (model) {
			gameAnimation.getBox().getControl(HideControl.class).setHide(true);
			for (Spatial d : gameAnimation.getUserDice()) {
				d.getControl(HideControl.class).setHide(true);
			}
		}
	}
}