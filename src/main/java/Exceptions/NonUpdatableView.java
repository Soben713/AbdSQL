package Exceptions;

import utils.Log;

public class NonUpdatableView extends Exception {
	public NonUpdatableView(String viewName) {
		Log.print("VIEW " + viewName + " IS NOT UPDATABLE");
	}
}
