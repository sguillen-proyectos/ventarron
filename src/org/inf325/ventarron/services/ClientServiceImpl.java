package org.inf325.ventarron.services;

import java.util.ArrayList;
import java.util.List;

import org.inf325.ventarron.dao.Client;
import org.inf325.ventarron.dao.ClientDa;
import org.inf325.ventarron.dao.DbHelper;

public class ClientServiceImpl implements ClientService {
	private ClientDa da;
	
	public static ClientServiceImpl createService(DbHelper helper) {
		return new ClientServiceImpl(new ClientDa(helper.getClientDao()));
	}
	
	private ClientServiceImpl(ClientDa da) {
		this.da = da;
	}
	
	@Override
	public List<Client> getClients() {
		List<Client> result = new ArrayList<Client>();
		Client clienta = new Client();
		clienta.setEmail("a");
		clienta.setName("a");
		clienta.setPhone("a");
		
		Client clientc = new Client();
		clientc.setEmail("c");
		clientc.setName("c");
		clientc.setPhone("c");
		
		Client clientd = new Client();
		clientd.setEmail("d");
		clientd.setName("d");
		clientd.setPhone("d");
		
		result.add(clienta);
		result.add(clientc);
		result.add(clientd);
		
//		return result;
		return da.getAll();
	}

	@Override
	public List<Client> filter(String keyword) {
		return da.filter(keyword);
	}

	@Override
	public Client getClient(int id) {
		return da.get(id);
	}

	@Override
	public void insert(Client client) {
		da.insert(client);
	}

	@Override
	public void update(Client client) {
		da.update(client);
	}

	@Override
	public void delete(Client client) {
		da.delete(client);
	}

}
