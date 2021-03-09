package telran.logs.bugs.client;

import static telran.logs.bugs.api.EmailNotifierApi.ASSIGNER_CONTROLLER;
import static telran.logs.bugs.api.EmailNotifierApi.MAIL_CONTROLLER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class EmailProviderClient {
	static Logger LOG = LoggerFactory.getLogger(EmailProviderClient.class);
	RestTemplate restTemplate = new RestTemplate();
	@Value("${app-url-assigner-mail:xxxx}")
	String urlAssignerMail;

	@Value("${app-url-artifact-mail:xxxx}")
	String urlArtifactMail;

	public String getEmailByArtifact(String artifact) {
		String res;
		try {
			ResponseEntity<String> response = restTemplate.exchange(getUrlArtifact(artifact), HttpMethod.GET, null,
					String.class);
			res = response.getBody();
			LOG.debug("received emai {} by artifact:{} ", res, artifact);
		} catch (Exception e) {
			res = "";
		}

		return res;
	}

	public String getAssignerMail() {
		String res;
		try {
			ResponseEntity<String> response = restTemplate.exchange(getUrlAssigner(), HttpMethod.GET, null,
					String.class);
			res = response.getBody();
		} catch (RestClientException e) {
			res = "";
		}
		LOG.debug("assigner email is {}", res);
		return res;
	}

	private String getUrlAssigner() {
		String res = urlAssignerMail + MAIL_CONTROLLER + ASSIGNER_CONTROLLER;
		LOG.debug("URL for getting assigner mail is {}", res);
		return res;
	}

	private String getUrlArtifact(String artifact) {
		String res = urlArtifactMail + MAIL_CONTROLLER + artifact;
		LOG.debug("URL for getting artifact mail is {}", res);
		return res;
	}

}