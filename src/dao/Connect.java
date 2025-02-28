package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Esta classe é responsável por estabelecer uma conexão com o banco de dados SQLite e por criar a estrutura do banco de dados.
 */
public class Connect {
    static String url = "jdbc:sqlite:database.db";

    /**
     * Método estático para estabelecer uma conexão com o banco de dados SQLite.
     *
     * @return Uma conexão com o banco de dados ou nulo em caso de erro.
     */
    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Conexão ao SQLite realizada com sucesso!");
            return connection;
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            return null; // Retorna nulo em caso de erro
        }
    }

    /**
     * Método para criar a estrutura do banco de dados.
     * Cria as tabelas de usuários, categorias e gastos.
     */
    public void criar() {
        // Estabelece a conexão com try-catch
        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement()) {
            // Define um timeout de 30s para a query
            statement.setQueryTimeout(30);

            // Ativa suporte a chaves estrangeiras
            statement.execute("PRAGMA foreign_keys = ON");

            // Cria a tabela de usuários
            String createTableUsers = """
                    CREATE TABLE IF NOT EXISTS users (
                        ID_USER INTEGER PRIMARY KEY AUTOINCREMENT,
                        login VARCHAR(15) NOT NULL UNIQUE,
                        senha_hash VARCHAR(256) NOT NULL,
                        salt VARCHAR(64) NOT NULL
                    )""";

            statement.execute(createTableUsers);

            // Cria a tabela de categorias
            String createTableCategories = """
                    CREATE TABLE IF NOT EXISTS categorias (
                        ID_CATEGORIA INTEGER PRIMARY KEY AUTOINCREMENT,
                        nome_categoria VARCHAR(30) NOT NULL UNIQUE,
                        ID_USER INTEGER NOT NULL,
                        FOREIGN KEY (ID_USER) REFERENCES users(ID_USER)
                        ON DELETE CASCADE ON UPDATE CASCADE
                    )""";
            statement.execute(createTableCategories);

            // Cria a tabela de gastos
            String createTableGastos = """
                    CREATE TABLE IF NOT EXISTS gastos (
                        ID_GASTO INTEGER PRIMARY KEY AUTOINCREMENT,
                        data DATE NOT NULL,
                        valor REAL NOT NULL,
                        ID_CATEGORIA INTEGER,
                        ID_USER INTEGER,
                        EH_GASTO BOOLEAN,
                        descricao VARCHAR(200),
                        FOREIGN KEY (ID_CATEGORIA) REFERENCES categorias(ID_CATEGORIA)
                        ON DELETE CASCADE ON UPDATE CASCADE,
                        FOREIGN KEY (ID_USER) REFERENCES users(ID_USER)
                        ON DELETE CASCADE ON UPDATE CASCADE
                    )""";
            statement.execute(createTableGastos);

            System.out.println("Estrutura do banco de dados criada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar tabelas no banco de dados: " + e.getMessage());
        }
    }
}