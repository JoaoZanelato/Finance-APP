package dao;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;

/**
 * Esta classe é responsável por criar um novo usuário no banco de dados.
 * Ela gera um hash seguro para a senha do usuário usando PBKDF2WithHmacSHA256.
 */
public class CriarUserDAO {
    /**
     * Método privado para gerar um salt e um hash para uma senha fornecida.
     *
     * @param password A senha a ser criptografada.
     * @return Um array de strings contendo o salt (base64) na primeira posição e o hash (base64) na segunda posição.
     * @throws Exception Se ocorrer um erro durante a geração do hash ou salt.
     */
    private String[] gerarSaltHash(String password) throws Exception {
        // Gerar um salt aleatório (16 bytes)
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        // Número de iterações (quanto maior, mais seguro)
        int iterations = 10000;
        // Tamanho do hash (256 bits, ou 32 bytes)
        int keyLength = 256;

        // Configurar PBKDF2
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        // Gerar o hash
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Codificar o salt e o hash para armazenamento em um formato legível (Base64)
        String saltBase64 = Base64.getEncoder().encodeToString(salt);
        String hashBase64 = Base64.getEncoder().encodeToString(hash);

        return new String[]{saltBase64, hashBase64};
    }

    /**
     * Método para criar um novo usuário no banco de dados.
     *
     * @param nome O nome de usuário do novo usuário.
     * @param password A senha do novo usuário.
     * @return True se o usuário foi criado com sucesso, false caso contrário.
     */
    public boolean CriarUser(String nome, String password) {
        try {
            // Gerar salt e hash
            String[] saltAndHash = gerarSaltHash(password);

            // Extrair salt e hash
            String saltBase64 = saltAndHash[0];
            String hashBase64 = saltAndHash[1];

            String sql = "INSERT INTO users(login, senha_hash, salt) VALUES (?, ?, ?)";

            // Inserir os dados do usuário no banco de dados SQLite
            try (Connection conn = Connect.getConnection()) {
                assert conn != null;
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setString(1, nome);       // login
                    pstmt.setString(2, hashBase64); // senha_hash
                    pstmt.setString(3, saltBase64); // salt

                    pstmt.executeUpdate(); // Executar a inserção
                    System.out.println("Usuário criado com sucesso!");
                    JOptionPane.showMessageDialog(null, "USUÁRIO CRIADO COM SUCESSO", "SUCESSO", JOptionPane.INFORMATION_MESSAGE);
                    return true;

                }
            } catch (Exception e) {
                System.err.println("Erro ao inserir usuário: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "USUÁRIO JÁ EXISTENTE", "ERRO", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Erro ao gerar hash e salt: " + e.getMessage());
            return false;
        }
    }
}