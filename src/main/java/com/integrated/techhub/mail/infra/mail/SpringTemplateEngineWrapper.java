package com.integrated.techhub.mail.infra.mail;

import com.integrated.techhub.mail.domain.ITemplateEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.IContext;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Component
@RequiredArgsConstructor
public class SpringTemplateEngineWrapper implements ITemplateEngine {

    private final SpringTemplateEngine springTemplateEngine;

    @Override
    public String process(String template, IContext context) {
        return springTemplateEngine.process(template, context);
    }

}
