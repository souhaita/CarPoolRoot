package rode1lift.ashwin.uomtrust.mu.rod1lift.DTO;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ashwin on 05-Jun-17.
 */

public class RequestObjectList implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<RequestObject> requestObjects;

	public List<RequestObject> getRequestObjects() {
		return requestObjects;
	}

	public void setRequestObjects(List<RequestObject> requestObjects) {
		this.requestObjects = requestObjects;
	}
}
