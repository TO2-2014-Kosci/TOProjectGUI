package to2.dice.GUI.controllers;

import java.util.Random;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import to2.dice.GUI.animation.PutControl;
import to2.dice.GUI.animation.RollControl;
import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameAnimation;
import to2.dice.game.Dice;

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
						gameAnimation.getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.QUARTER_PI, new Vector3f(1,0,1)));
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

	public void destroy() {
		gameAnimation.stop();
		gameAnimation.destroy();
	}

	public void refresh() {
		if (model.isMyTurn()) {
			// TODO moja tura
		} else {
			Random r = new Random();
			Dice dice = model.getGameState().getCurrentPlayer().getDice();
			for (int a: dice.getDiceArray()) {
				System.out.println(a);
			}
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				RigidBodyControl diceControl = gameAnimation.getAnotherDice()[i].getControl(RigidBodyControl.class);
				switch (dice.getDiceArray()[i]) {
					case 1:
						diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(1,0,0)));
						break;
					case 2:
						diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(0,1,0)));
						break;
					case 3:
						diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.ZERO_TOLERANCE, new Vector3f(1,0,0)));
						break;
					case 4:
						diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.PI, new Vector3f(1,0,0)));
						break;
					case 5:
						diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(FastMath.HALF_PI, new Vector3f(0,1,0)));
						break;
					case 6:
						diceControl.setPhysicsRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, new Vector3f(1,0,0)));
						break;
				}
				diceControl.setEnabled(true);
				diceControl.setPhysicsLocation(new Vector3f(-16, -2 + i, 10));
				diceControl.setLinearVelocity(new Vector3f(10, 0, 0));
				diceControl.setAngularVelocity(new Vector3f(rndAngle(), rndAngle(), rndAngle()));
				diceControl.activate();
			}
		}
	}
	
	public void shakeAnotherDice(Dice dice) {
		synchronized (model) {
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				RollControl diceControl = gameAnimation.getAnotherDice()[i].getControl(RollControl.class);
				diceControl.setNumberToRoll(dice.getDiceArray()[i]);
			}	
		}
	}
	
	public void putAnotherDice(Dice dice) {
		synchronized (model) {
			for (int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
				PutControl diceControl = gameAnimation.getAnotherDice()[i].getControl(PutControl.class);
				diceControl.setNumberToPut(dice.getDiceArray()[i]);
			}
		}
	}
	
//	public void fireDice(Vector3f from, Vector3f force, Vector3f left, DiceExpression dice, DiceSetDesign setDesign) { 
//		
//		left = left.normalize();
//		from = from.clone();
//		force.normalize();
//		force.multLocal(10);
//		
//		
//		InitialDiceData idd = new InitialDiceData();
//		
//		idd.left = left;
//		idd.force = force;
//		idd.from = from;
//		idd.design = setDesign;
//		
//		int totalCount = dice.getTotalDice();
//		
//		idd.angular = new Vector3f[totalCount];
//		idd.diceTypes = new DiceType[totalCount];
//		idd.physicsRotation = new Matrix3f[totalCount];
//		
//		int counter =0;
//		for ( Map.Entry<DiceType,Integer> entry : dice.getDice().entrySet() ) {
//			DiceType t = entry.getKey();
//			int count = entry.getValue();
//			for ( int i =0; i < count ; i++ ) {
//				idd.diceTypes[counter] = t;
//				idd.angular[counter] = new Vector3f(rndAngle(), rndAngle(), rndAngle());
//				idd.physicsRotation[counter] = randomQuaternion().toRotationMatrix();
//				counter++;
//			}
//		}
//		idd.expression = dice;
//		
//		if (netManager != null ) {
//			netManager.sendRawUpdate(idd);
//		}
//		
//		fireDice(idd,true);
//		
//		
//	}
//
//	public void fireDice(InitialDiceData dd, boolean inControl) {
//
//		if (audioRenderer != null ) {
//			audioRenderer.playSource(audioNode);
//		}
//		
//		DiceThrowHandler handler = perPlayerDice.get(dd.player);
//		
//		if ( handler != null ) {
//			handler.clear();
//		} else {
//			handler = new DiceThrowHandler(this);
//			perPlayerDice.put(dd.player, handler);
//		}
//		
//		handler.init(dd, inControl,dd.expression);
//	}

	private float rndAngle() {
		return (float) (Math.random() * Math.PI * 2);
	}
	
	private static Quaternion randomQuaternion() {
		
		double u1 = Math.random();
		double u2 = Math.random();
		double u3 = Math.random();
		
		
		double u1sqrt = Math.sqrt(u1);
		double u1m1sqrt = Math.sqrt(1-u1);
		double x = u1m1sqrt *Math.sin(2*Math.PI*u2);
		double y = u1m1sqrt *Math.cos(2*Math.PI*u2);
		double z = u1sqrt *Math.sin(2*Math.PI*u3);
		double w = u1sqrt *Math.cos(2*Math.PI*u3);
		
		return new Quaternion((float)x,(float)y,(float)w,(float)z);
	}

//	public void updateFlyingDice(FlyingDiceData fdd) {
//		DiceThrowHandler handler = perPlayerDice.get(fdd.player);
//		if (handler == null ) {
//			log.warning("Got flying dice info for player " + fdd.player + " but without initial data");
//			return;
//		}
//		handler.updateFlyingDice(fdd);
//	}
}