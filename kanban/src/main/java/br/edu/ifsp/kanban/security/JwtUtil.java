package br.edu.ifsp.kanban.security;
/*
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // chave fixa simples — funciona e pronto
    private static final String SECRET = "MINHA_CHAVE_SECRETA_BEM_SIMPLES";

    public String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String pegarEmail(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean tokenValido(String token) {
        try {
            pegarEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
*/