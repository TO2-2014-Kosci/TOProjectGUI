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
					JOptionPane.showMessageDialog(null,"Nick zajêty lub niepoprawny","B³¹d nicku",JOptionPane.ERROR_MESSAGE);
				}
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null,"Brak odpowiedzi serwera","B³¹d logowania",JOptionPane.ERROR_MESSAGE);
				//TODO Handling with connection error
			}
		}
		else{
			lv.eraseLogin();
			JOptionPane.showMessageDialog(null,"Nick niepoprawny","B³¹d nicku",JOptionPane.ERROR_MESSAGE);
			System.exit(0);
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
