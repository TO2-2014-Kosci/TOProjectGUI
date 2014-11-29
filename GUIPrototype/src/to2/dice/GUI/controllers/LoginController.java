package to2.dice.GUI.controllers;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.LoginView;
import to2.dice.GUI.views.View;
import to2.dice.messaging.Response;

public class LoginController extends Controller {

	public LoginController(Model mode, View view) {
		super(mode, view);
	}
	//TODO
	public void clickedLoginButton(){
		String login = ((LoginView) super.getView()).getLogin();
		
		Response response = super.getModel().getConnectionProxy().login(login);
		
		
		GameListView newView = new GameListView(super.getModel());
		newView.setController(new GameListController(super.getModel(), newView));
	}
}
