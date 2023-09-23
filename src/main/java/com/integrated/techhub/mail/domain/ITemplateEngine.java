package com.integrated.techhub.mail.domain;

import org.thymeleaf.context.IContext;

public interface ITemplateEngine {

    String process(String template, IContext context);

}
