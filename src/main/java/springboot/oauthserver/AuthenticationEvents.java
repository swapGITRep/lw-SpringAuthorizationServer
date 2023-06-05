package springboot.oauthserver;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class AuthenticationEvents {
	@EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
		// ...
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
      	log.info("**********AuthenticationEvents: AbstractAuthenticationFailureEvent ************************");
      	//failures.getException().printStackTrace();
    }
    
    @EventListener
    public void onFailure(AuthorizationDeniedEvent<?> failure) {
    	log.info("**********AuthenticationEvents: AuthorizationDeniedEvent ************************");
    	//log.info(failure.getAuthentication().get().getDetails());
    	
    	
    }
}