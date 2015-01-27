package to2.dice.GUI.controllers;

import java.util.concurrent.TimeoutException;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.LoginView;
import to2.dice.messaging.Response;

public class LoginController extends Controller {

	public LoginController(Model model) {
		super(model);
	}

	public void clickedLoginButton() {
		LoginView loginView = (LoginView) view;
		String login = loginView.getLogin();
		if (isProper(login)) {
			try {
				Response response = model.getConnectionProxy().login(login);
				if (response.isSuccess()) {
					model.setLogin(login);
					GameListController newController = new GameListController(model);
					GameListView newView = new GameListView(model, newController);
					newController.setView(newView);
					model.getDiceApplication().setView(newView);
					newController.refreshGameList();
				} else {
					loginView.eraseLogin();
					loginView.showErrorDialog(response.message, "B��d nicku", false);
				}
			} catch (TimeoutException e) {
				e.printStackTrace();
				loginView.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		} else {
			loginView.eraseLogin();
			loginView.showErrorDialog("Nick zaj�ty lub niepoprawny", "B��d nicku", false);
		}
	}

	private boolean isProper(String login) {
		if (login.equals("")) {
			return false;
		} else {
			return true;
		}
	}
}
