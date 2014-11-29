package to2.dice.GUI.controllers;

import javax.swing.JOptionPane;

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
		LoginView lv = (LoginView) view;
		String login = lv.getLogin();
		if(isProper(login)){
			try{
				Response response = model.getConnectionProxy().login(login);
				if(response.isSuccess()){
					
					//TODO setter
					model.login = login;
					
					GameListView newView = new GameListView(model);
					newView.setController(new GameListController(model, newView));
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
