package org.inf325.ventarron.services;

import java.util.List;

import org.inf325.ventarron.dao.Client;

public interface ClientService {
	public List<Client> getClients();
	public List<Client> filter(String keyword);
	public Client getClient(int id);
	public void insert(Client client);
	public void update(Client client);
	public void delete(Client client);
}
