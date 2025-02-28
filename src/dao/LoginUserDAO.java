package dao;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;

/**
 * Classe DAO para manipular autenticação de login de usuários, incluindo validação de credenciais.
 */
public class LoginUserDAO {

    /**
     * Gera um hash seguro da senha utilizando o algoritmo PBKDF2 com HMAC-SHA256.
     *
     * @param senha A senha a ser hashada.
     * @param salt O salt usado na geração do hash.
     * @return A senha hashada em formato Base64.
     * @throws Exception Caso ocorra algum erro na geração do hash.
     */
    private static String gerarHashSenha(String senha, byte[] salt) throws Exception {

        int iterations = 10000; // Número de iterações do algoritmo
        int keyLength = 256;    // Comprimento da chave gerada em bits

        // Configuração do algoritmo PBKDF2 com os parâmetros fornecidos
        PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        // Gera o hash da senha
        byte[] hash = factory.generateSecret(spec).getEncoded();

        // Codifica o hash em Base64 para armazenamento ou comparação
        return Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Valida o login do usuário verificando se as credenciais fornecidas estão corretas.
     *
     * @param login O login do usuário.
     * @param senha A senha fornecida pelo usuário.
     * @return true se as credenciais forem válidas; false caso contrário.
     */
    public static boolean validarLogin(String login, String senha) {
        // Consulta SQL para buscar as informações do usuário com base no login
        String query = "SELECT senha_hash, salt FROM users WHERE login = ?";

        try (Connection connection = Connect.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Define o parâmetro do login na consulta SQL
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Verifica se o usuário foi encontrado
            if (resultSet.next()) {
                // Recupera o hash da senha e o salt armazenados no banco de dados
                String senhaHashArmazenada = resultSet.getString("senha_hash");
                String saltBase64 = resultSet.getString("salt");

                // Decodifica o salt armazenado de Base64 para um array de bytes
                byte[] salt = Base64.getDecoder().decode(saltBase64);

                // Gera o hash da senha fornecida utilizando o salt armazenado
                String senhaHashGerada = gerarHashSenha(senha, salt);

                // Compara o hash gerado com o hash armazenado
                if (senhaHashArmazenada.equals(senhaHashGerada)) {
                    return true; // Login bem-sucedido
                } else {
                    return false; // Senha incorreta
                }

            } else {
                // Exibe uma mensagem informando que o usuário não foi encontrado
                JOptionPane.showMessageDialog(null, "USUÁRIO NÃO EXISTE, POR FAVOR, REGISTRE-SE", "ERRO", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            // Trata erros durante o processo de validação de login
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR LOGAR", "ERRO", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }
}
