package zerobase.demo.common.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailComponents {


    private final JavaMailSender javaMailSender;

    /**
     * 이메일, 제목, 내용을 넣으면 해당 이메일에 메일을 보냄
     * 내용은 html 문법으로 작성
     *
     * @param userId
     * @param subject
     * @param text
     */
    public void sendMail(String userId, String subject, String text) {

        MimeMessagePreparator msg = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(userId);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text, true);
        };
        javaMailSender.send(msg);
    }
}
