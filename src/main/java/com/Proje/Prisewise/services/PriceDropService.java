package com.Proje.Prisewise.services;

import com.Proje.Prisewise.entities.Favorite;
import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.entities.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PriceDropService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void notifyUsers(List<Map<String, Object>> priceChanges) {
        for (Map<String, Object> change : priceChanges) {
            String uniqueKey = (String) change.get("unique_key");
            Double oldPrice = (Double) change.get("old_price");
            Double newPrice = (Double) change.get("new_price");

            Product urun = entityManager.createQuery(
                            "SELECT u FROM Product u WHERE u.uniqueKey = :uniqueKey", Product.class)
                    .setParameter("uniqueKey", uniqueKey)
                    .getSingleResult();

            List<Favorite> favorites = entityManager.createQuery(
                            "SELECT f FROM Favorite f WHERE f.product = :product", Favorite.class)
                    .setParameter("product", urun)
                    .getResultList();

            String productUrl = urun.getUrl();
            String imageUrl = urun.getResim_url(); // Ürünün görseli

            for (Favorite favorite : favorites) {
                User user = favorite.getUser();
                sendPriceDropEmail(user.getEmail(), urun.getUrunAdi(), oldPrice, newPrice, productUrl, imageUrl);
            }
        }
    }
    private void sendPriceDropEmail(String to, String productName, Double oldPrice, Double newPrice, String productUrl, String imageUrl) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Favori Ürününüzde Fiyat Düşüşü!");
            helper.setFrom("your-email@gmail.com");

            //Logo url
            String logoImageUrl = "https://i.postimg.cc/nh3C7hqK/logo.png";

            // Mail için html içeriği
            String htmlContent = String.format(
                    "<div style='font-family: Arial, sans-serif; color: #333; max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px;'>"
                            + "<div style='text-align: center;'>"
                            + "    <img src='%s' alt='Marka Logosu' style='max-width: 150px;'>"
                            + "</div>"
                            + "<h2>Favori Ürününüzde Fiyat Düşüşü!</h2>"
                            + "<p>Merhaba,</p>"
                            + "<p><strong>%s</strong> adlı ürünün fiyatı <del>%.2f TL</del> → <strong>%.2f TL</strong> oldu!</p>"
                            + "<a href='%s' target='_blank'>"
                            + "<img src='%s' alt='%s' style='max-width: 300px; display: block; margin: 10px auto;'>"
                            + "</a>"
                            + "<p>Detayları görmek için aşağıdaki bağlantıya tıklayın:</p>"
                            + "<p><a href='%s' style='color: #007bff; text-decoration: none; font-weight: bold;'>Ürünü Gör</a></p>"
                            + "<p>İyi alışverişler!</p>"
                            + "</div>",
                    logoImageUrl, productName, oldPrice, newPrice, productUrl, imageUrl, productName, productUrl
            );

            helper.setText(htmlContent, true);

            mailSender.send(message);
            System.out.println("Mail gönderildi: " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
