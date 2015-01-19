package to2.dice.GUI.views;

import java.awt.Canvas;
import java.awt.Dimension;

import to2.dice.GUI.animation.AnotherPutControl;
import to2.dice.GUI.animation.HideControl;
import to2.dice.GUI.animation.RollControl;
import to2.dice.GUI.animation.TextControl;
import to2.dice.GUI.animation.UserPutControl;
import to2.dice.GUI.controllers.GameAnimController;
import to2.dice.GUI.model.Model;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.ClasspathLocator;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.controls.Trigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.BloomFilter;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.Spatial.CullHint;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

public class GameAnimation extends SimpleApplication {
	private GameAnimController gameAnimController;
	private Model model;
	private Spatial[] userDice;
	private Spatial[] anotherDice;
	private BulletAppState bulletAppState;
	private Spatial box;
	private boolean reload = false;
	private BitmapText bitmapText;
	private Canvas canvas;

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
		viewPort = new ViewPort("Cam", cam);
		viewPort.addProcessor(fpp);

		createCanvas();
		startCanvas(true);
		gameAnimController = animController;
		this.model = model;
		getCanvas();
	}

	public Canvas getCanvas() {
		canvas = ((JmeCanvasContext) this.getContext()).getCanvas();
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
		settings.setBitsPerPixel(32);
		flyCam.setEnabled(false);
		settings.setFrameRate(60);
		assetManager.registerLocator("assets", ClasspathLocator.class);
		cam.setLocation(new Vector3f(-8, -0.5f, 13.5f));
		cam.lookAt(new Vector3f(-3, -0.5f, 0), Vector3f.UNIT_Z);
		// bulletAppState.getPhysicsSpace().enableDebug(assetManager);
		bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, 0, -10));
		bulletAppState.getPhysicsSpace().setAccuracy(1 / 150f);
		Spatial table = assetManager.loadModel("Model/Table/table.j3o");
		table.setLocalTranslation(0, 0, 0);
		RigidBodyControl landscape = new RigidBodyControl(CollisionShapeFactory.createMeshShape(table), 0);
		table.addControl(landscape);
		bulletAppState.getPhysicsSpace().add(landscape);
		rootNode.attachChild(table);

		box = assetManager.loadModel("Model/Box/box.j3o");

		box.setLocalTranslation(new Vector3f(-7, 0, 0));
		RigidBodyControl boxShape = new RigidBodyControl(CollisionShapeFactory.createMeshShape(box), 0);
		box.addControl(boxShape);
		box.addControl(new HideControl());
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

		bitmapText = new BitmapText(guiFont);
		bitmapText.setSize(25);
		bitmapText.setText("");
		bitmapText.addControl(new TextControl(bitmapText));
		guiNode.attachChild(bitmapText);
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
						getUserDice()[i] = assetManager.loadModel("Model/Dice/dice.j3o");
						getUserDice()[i].setName("dice");
						CollisionShape diceShape = CollisionShapeFactory.createDynamicMeshShape(getUserDice()[i]);
						RigidBodyControl diceBody = new RigidBodyControl(diceShape, 10f);
						getUserDice()[i].addControl(diceBody);
						rootNode.attachChild(getUserDice()[i]);
						getUserDice()[i].addControl(new RollControl(i));
						getUserDice()[i].addControl(new UserPutControl(i, diceNumber));
						getUserDice()[i].addControl(new HideControl());
						bulletAppState.getPhysicsSpace().add(diceBody);

						getAnotherDice()[i] = assetManager.loadModel("Model/Dice/dice.j3o");
						getAnotherDice()[i].setName("dice");
						CollisionShape diceShapeA = CollisionShapeFactory.createDynamicMeshShape(getAnotherDice()[i]);
						RigidBodyControl diceBodyA = new RigidBodyControl(diceShapeA, 10f);
						getAnotherDice()[i].addControl(diceBodyA);
						getAnotherDice()[i].addControl(new RollControl(i));
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
					box.getControl(HideControl.class).setHide(true);
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
		bitmapText.setLocalTranslation((canvas.getWidth() - bitmapText.getLineWidth()) / 2, canvas.getHeight() - 25, 0);
	}

	@Override
	public void reshape(int w, int h) {
		super.reshape(w, h);
		bitmapText.setLocalTranslation((w - bitmapText.getLineWidth()) / 2, h - 25, 0);

	}

	public void setReload() {
		reload = true;
	}

	public Spatial[] getUserDice() {
		return userDice;
	}

	public void setUserDice(Spatial[] dice) {
		userDice = dice;
	}

	public Spatial[] getAnotherDice() {
		return anotherDice;
	}

	public void setAnotherDice(Spatial[] anotherDice) {
		this.anotherDice = anotherDice;
	}

	public BitmapText getBitmapText() {
		return bitmapText;
	}

	public Spatial getBox() {
		return box;
	}
}
