package ashwin.uomtrust.ac.mu.entity;

public class Result {
	
	private String result;
	
	private String status;
	
	private Long id;
	
	private String role;

	public Result() {
	}
	
	public Result(String status) {
		this.status = status;
	}

	public Result(String status, String result) {
		this.status = status;
		this.result = result;
	}
	
	public Result(String status, String result, Long id) {
		this.result = result;
		this.status = status;
		this.id = id;
	}
	
	public Result(String status, String result, Long id, String role){
		this.result = result;
		this.status = status;
		this.id = id;
		this.setRole(role);
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
