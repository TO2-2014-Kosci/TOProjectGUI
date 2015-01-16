package to2.dice.GUI.views;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPanel;

import org.lwjgl.opengl.Display;

import com.bulletphysics.collision.shapes.BoxShape;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.HullCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue.ShadowMode;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;
import com.jme3.system.JmeSystem;

import to2.dice.GUI.animation.AnotherPutControl;
import to2.dice.GUI.animation.AnotherRollControl;
import to2.dice.GUI.animation.HideControl;
import to2.dice.GUI.animation.TextControl;
import to2.dice.GUI.animation.UserPutDice;
import to2.dice.GUI.animation.UserRollDice;
import to2.dice.GUI.controllers.DiceControl;
import to2.dice.GUI.controllers.GameAnimController;
import to2.dice.GUI.model.Model;
import to2.dice.game.Player;

//TODO inheritance
public class GameAnimation extends SimpleApplication {
	private GameAnimController gameAnimController;
	private Model model;
	private Spatial[] userDice;
	private Spatial[] anotherDice;
	private BulletAppState bulletAppState;
	private Spatial box;
	private boolean reload = false;
	private BitmapText text;
	
	public GameAnimation(Model model, GameAnimController animController) {
		super();
		bulletAppState = new BulletAppState();
		stateManager.attach(bulletAppState);
		AppSettings settings = new AppSettings(true);
		settings.setAudioRenderer(null);
		this.setSettings(settings);
		FilterPostProcessor fpp = new FilterPostProcessor(assetManager);
		BloomFilter bloom = new BloomFilter(BloomFilter.GlowMode.Objects);
		fpp.addFilter(bloom);
		viewPort = new ViewPort("Cam", this.cam);
		viewPort.addProcessor(fpp);

		createCanvas();
		startCanvas(true);
		this.gameAnimController = animController;
		this.model = model;
	}

	public Canvas getCanvas() {
		Canvas canvas = ((JmeCanvasContext) this.getContext()).getCanvas();
		// HACK: Without this canvas doesn't shrink when window is reduced
		canvas.setMinimumSize(new Dimension(16, 16));
		return canvas;
	}

	@Override
	public void simpleInitApp() {
		setPauseOnLostFocus(false);
		JmeCanvasContext ctx = (JmeCanvasContext) getContext();
		ctx.setSystemListener(this);
		this.setDisplayStatView(false);
		this.setDisplayFps(false);
		this.settings.setBitsPerPixel(32);
		this.flyCam.setEnabled(false);
		this.settings.setFrameRate(60);
		this.assetManager.registerLocator("assets", ClasspathLocator.class);
		this.cam.setLocation(new Vector3f(-10, -1, 15));
		this.cam.lookAt(new Vector3f(-3, -1, 0), Vector3f.UNIT_Z);
		// bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, -10));
		bulletAppState.getPhysicsSpace().setAccuracy(1 / 150f);
		Spatial table = this.assetManager.loadModel("Model/Table/table.j3o");
		table.setLocalTranslation(0, 0, 0);
		RigidBodyControl landscape = new RigidBodyControl(CollisionShapeFactory.createMeshShape((Node) table), 0);
		table.addControl(landscape);
		bulletAppState.getPhysicsSpace().add(landscape);
		rootNode.attachChild(table);

		box = this.assetManager.loadModel("Model/Box/box.j3o");

		box.setLocalTranslation(new Vector3f(-7, 0, 0));
		RigidBodyControl boxShape = new RigidBodyControl(CollisionShapeFactory.createMeshShape((Node) box), 0);
		box.addControl(boxShape);
		bulletAppState.getPhysicsSpace().add(boxShape);
		rootNode.attachChild(box);
		AmbientLight ambient = new AmbientLight();
		ambient.setColor(ColorRGBA.White);
		DirectionalLight sun = new DirectionalLight();
		sun.setDirection(new Vector3f(6f, 0f, -7f).normalizeLocal());
		sun.setColor(ColorRGBA.White.mult(1.5f));

		PointLight point = new PointLight();
		point.setPosition(new Vector3f(0, 0, 15));
		point.setColor(ColorRGBA.White.mult(0.2f));
		PointLight pointBack = new PointLight();
		pointBack.setPosition(new Vector3f(15, 0, 15));
		pointBack.setColor(ColorRGBA.White.mult(0.2f));
		PointLight pointLeft = new PointLight();
		pointLeft.setPosition(new Vector3f(15, 10, 15));
		pointLeft.setColor(ColorRGBA.White.mult(0.2f));
		PointLight pointRight = new PointLight();
		pointRight.setPosition(new Vector3f(15, 10, 15));
		pointRight.setColor(ColorRGBA.White.mult(0.2f));
		rootNode.addLight(point);
		rootNode.addLight(pointBack);
		rootNode.addLight(pointRight);
		rootNode.addLight(pointLeft);
		rootNode.addLight(sun);
		rootNode.addLight(ambient);
		inputManager.addMapping("Shake", (Trigger) new KeyTrigger(KeyInput.KEY_S));
		inputManager.addMapping("Put", (Trigger) new KeyTrigger(KeyInput.KEY_P));
		inputManager.addMapping("Select", (Trigger) new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(gameAnimController, new String[] { "Shake", "Put", "Select" });
		gameAnimController.setEnabled(false);
		
		text = new BitmapText(guiFont);
//		text.setCullHint(CullHint.Always);
		text.setText("");
		text.addControl(new TextControl(text));
		guiNode.attachChild(text);
//		rootNode.addControl(gameAnimController);
	}

	@Override
	public void update() {
		super.update();
		if (reload) {
			synchronized (model) {
				reload = false;
				int diceNumber = model.getGameSettings().getDiceNumber();
				if (getUserDice() == null || getUserDice().length != diceNumber) {
					setUserDice(new Spatial[diceNumber]);
					setAnotherDice(new Spatial[diceNumber]);
					for (int i = 0; i < diceNumber; i++) {
						getUserDice()[i] = this.assetManager.loadModel("Model/Dice/dice.j3o");
						getUserDice()[i].setName("dice");
						CollisionShape diceShape = CollisionShapeFactory
								.createDynamicMeshShape((Node) getUserDice()[i]); // u¿ywamy
																					// Dynamic
																					// bo
																					// maj¹
																					// byæ
																					// kolizje
						RigidBodyControl diceBody = new RigidBodyControl(diceShape, 10f);
						getUserDice()[i].addControl(diceBody);
						rootNode.attachChild(getUserDice()[i]);
						getUserDice()[i].addControl(new UserRollDice(i));
						getUserDice()[i].addControl(new UserPutDice(i, diceNumber));
						getUserDice()[i].addControl(new HideControl());
						bulletAppState.getPhysicsSpace().add(diceBody);

						getAnotherDice()[i] = this.assetManager.loadModel("Model/Dice/dice.j3o");
						getAnotherDice()[i].setName("dice");
						CollisionShape diceShapeA = CollisionShapeFactory
								.createDynamicMeshShape((Node) getAnotherDice()[i]); // u¿ywamy
																						// Dynamic
																						// bo
																						// maj¹
																						// byæ
																						// kolizje
						RigidBodyControl diceBodyA = new RigidBodyControl(diceShapeA, 10f);
						getAnotherDice()[i].addControl(diceBodyA);
						getAnotherDice()[i].addControl(new AnotherRollControl(i));
						getAnotherDice()[i].addControl(new AnotherPutControl(i));
						getAnotherDice()[i].addControl(new HideControl());
						rootNode.attachChild(getAnotherDice()[i]);
						bulletAppState.getPhysicsSpace().add(diceBodyA);
					}
					box.setLocalScale(1, diceNumber, 1);
				}
				for (int i = 0; i < diceNumber; i++) {
					getUserDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(
							new Vector3f(-7, -diceNumber / 2 + i, 0.35f));
					getAnotherDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(
							new Vector3f(-i, -6, 0.35f));
				}
				if (!model.isSitting()) {
					box.setCullHint(CullHint.Always);
					for (Spatial dice : getUserDice()) {
						dice.getControl(HideControl.class).setHide(true);
					}
				} else {
					box.setCullHint(CullHint.Dynamic);
					for (Spatial dice : getUserDice()) {
						dice.getControl(HideControl.class).setHide(false);
					}
				}
				model.notifyAll();
			}
		}
	}
	
	@Override
	public void reshape(int w, int h) {
		super.reshape(w, h);
		text.setLocalTranslation((w - text.getLineWidth()) / 2, h - 25, 0);
	}
	

	public void setReload() {
		reload = true;
	}

	public Spatial[] getUserDice() {
		return userDice;
	}

	public void setUserDice(Spatial[] dice) {
		this.userDice = dice;
	}

	public Spatial[] getAnotherDice() {
		return anotherDice;
	}

	public void setAnotherDice(Spatial[] anotherDice) {
		this.anotherDice = anotherDice;
	}
	
	public Spatial getBitmapText() {
		return text;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		// Player currentPlayer = model.getGameState().getCurrentPlayer();
		// if (currentPlayer != null) {
		// if (currentPlayer.getName().equals(model.getLogin())) {
		// // u¿ytkownik
		// } else {
		// for(int i = 0; i < model.getGameSettings().getDiceNumber(); i++) {
		// getAnotherDice()[i].getControl(RigidBodyControl.class).setPhysicsLocation(new
		// Vector3f(i, -6, -1f));
		// }
		// // inny gracz
		// }
		// }
	}

//	public void selectDice(int i) {
//		if (model.getSelectedDice()[i]) {
//			getUserDice()[i].addLight(new PointLight());
//		} else {
//			getUserDice()[i].removeLight(new PointLight());
//		}
//		getUserDice()[i].getControl(RigidBodyControl.class).activate();
//	}
}
