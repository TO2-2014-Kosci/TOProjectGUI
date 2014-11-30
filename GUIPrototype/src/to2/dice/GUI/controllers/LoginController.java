package to2.dice.GUI.controllers;

import javax.swing.JOptionPane;

import to2.dice.GUI.model.Model;
import to2.dice.GUI.views.GameListView;
import to2.dice.GUI.views.LoginView;
import to2.dice.messaging.Response;

public class LoginController extends Controller {

	public LoginController(Model model) {
		super(model);
	}
	//TODO
	public void clickedLoginButton(){
		LoginView lv = (LoginView) view;
		String login = lv.getLogin();
		if(isProper(login)){
			try{
				Response response = model.getConnectionProxy().login(login);
				if(response.isSuccess()){
					//TODO setter
					model.login = login;					
					GameListController newController = new GameListController(model);
					GameListView newView = new GameListView(model, newController);
					newController.setView(newView);
					model.diceApplication.setView(newView);
				}
				else{
					lv.eraseLogin();
					view.showErrorDialog("Nick zajêty lub niepoprawny", "B³¹d nicku", false);
				}
			}
			catch(Exception e){
				view.showErrorDialog("Utracono po³¹czenie z serwerem", "B³¹d po³¹czenia", true);
				//TODO Handling with connection error
			}
		}
		else{
			lv.eraseLogin();
			view.showErrorDialog("Nick zajêty lub niepoprawny", "B³¹d nicku", false);
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
