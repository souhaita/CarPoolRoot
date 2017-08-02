package ashwin.uomtrust.ac.mu.service;

import java.util.List;

import ashwin.uomtrust.ac.mu.dto.MessageDTO;

public interface MessageService {
	
	public MessageDTO saveMessage(MessageDTO messageDTO);
	public List<MessageDTO> getMessages(MessageDTO messageDTO);
	
}
