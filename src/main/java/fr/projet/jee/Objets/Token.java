package fr.projet.jee.Objets;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Token implements Serializable {
    private static final long serialVersionUID = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private LocalDateTime endValidity;

    @ManyToOne
    @JoinColumn(columnDefinition="integer", nullable = false, name="user_id")
    private User user;

    public Token() {
        value = getAlphaNumericString(15);
        endValidity = LocalDateTime.now().plusMinutes(15);
    }

    public String getValue() {
        return value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getEndValidity() {
        return endValidity;
    }

    public void setEndValidity(LocalDateTime endValidity) {
        this.endValidity = endValidity;
    }

    String getAlphaNumericString(int n)
    {
  
        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
  
        String randomString
            = new String(array, Charset.forName("UTF-8"));
  
        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();
  
        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {
  
            char ch = randomString.charAt(k);
  
            if (((ch >= 'a' && ch <= 'z')
                 || (ch >= 'A' && ch <= 'Z')
                 || (ch >= '0' && ch <= '9'))
                && (n > 0)) {
  
                r.append(ch);
                n--;
            }
        }
  
        // return the resultant string
        return r.toString();
    }
}