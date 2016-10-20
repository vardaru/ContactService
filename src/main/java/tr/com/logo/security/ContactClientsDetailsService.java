package tr.com.logo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import tr.com.logo.model.Client;
import tr.com.logo.repositories.ClientRepository;

/**
 * Created by umitvardar on 16.08.2016.
 */
@Service
public class ContactClientsDetailsService implements ClientDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(ContactClientsDetailsService.class);

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ClientRegistrationException(" Hataaaaa."));
        return new ContactClientDetails(client);
    }
}
