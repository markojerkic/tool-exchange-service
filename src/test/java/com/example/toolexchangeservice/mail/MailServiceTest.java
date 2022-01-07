package com.example.toolexchangeservice.mail;

import com.example.toolexchangeservice.service.MailService;
import freemarker.template.TemplateException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testMailSending() throws MessagingException, TemplateException, IOException {
        this.mailService.sendOfferMail("markojerkic266@gmail.com", "Naslov", "marko",
                new Date(), "poruka");
        Assertions.assertThatNoException();
    }
}
