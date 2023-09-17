package com.integrated.techhub.mail.infra.mail;

import com.integrated.techhub.mail.domain.ITemplateEngine;
import org.thymeleaf.context.IContext;


public class TestTemplateEngineWrapper implements ITemplateEngine {

    @Override
    public String process(String template, IContext context) {
        return "Test Template Engine";
    }

}
