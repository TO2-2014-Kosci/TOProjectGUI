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

	public void clickedLoginButton(){
		LoginView lv = (LoginView) view;
		String login = lv.getLogin();
		if(isProper(login)){
			try{
				Response response = model.getConnectionProxy().login(login);
				if(response.isSuccess()){
					//TODO setter
					model.setLogin(login);					
					GameListController newController = new GameListController(model);
					GameListView newView = new GameListView(model, newController);
					newController.setView(newView);
					model.getDiceApplication().setView(newView);
					newController.refreshGameList();
				}
				else{
					lv.eraseLogin();
					view.showErrorDialog("Nick zaj�ty lub niepoprawny", "B��d nicku", false);
				}
			}
			catch(TimeoutException e){
				e.printStackTrace();
				view.showErrorDialog("Utracono po��czenie z serwerem", "B��d po��czenia", true);
			}
		}
		else{
			lv.eraseLogin();
			view.showErrorDialog("Nick zaj�ty lub niepoprawny", "B��d nicku", false);
		}
	}
	private boolean isProper(String login){
		if(login.equals("")){
			return false;
		}
		else {
			return true;
		}
	}
}
