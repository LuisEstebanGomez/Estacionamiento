package ar.edu.unlp.estacionamiento.security.dto;

public class ApiResponse {
	
	private String message;
	private boolean result;

	public ApiResponse(boolean b, String message) {
		this.result= b;
		this.message = message;
	}

	public String getMensaje() {
		return message;
	}

	public void setMensaje(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	
}


