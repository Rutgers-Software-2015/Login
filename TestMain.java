package Login;

import Manager.ManagerRootWindow;

public class TestMain {

	public static void main(String[] args) {
		LoginAuthenticator auth = new LoginAuthenticator("sam","harsh");
		auth.getAuth();
		auth.getActorClass();
		//new ManagerRootWindow();

	}

}
