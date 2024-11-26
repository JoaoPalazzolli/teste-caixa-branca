package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Classe responsável por gerenciar a conexão ao banco de dados e a verificação de usuários.
 */
public class User {

    /**
     * Estabelece uma conexão com o banco de dados.
     *
     * @return Objeto Connection se a conexão for bem-sucedida, ou null caso contrário.
     */
    public Connection conectarBD() {
        Connection conn = null;
        try {
            // Carrega o driver JDBC do MySQL
            Class.forName("com.mysql.Driver").newInstance();

            // URL do banco de dados com credenciais
            String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";

            // Tenta estabelecer a conexão
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            // Exibe uma mensagem de erro em caso de falha na conexão
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Nome do usuário autenticado.
     * 
     * Será populado caso a validação de login seja bem-sucedida.
     */
    public String nome = "";

    /**
     * Indica se o login foi validado com sucesso.
     */
    public boolean result = false;

    /**
     * Verifica se as credenciais do usuário (login e senha) existem no banco de dados.
     *
     * @param login Login fornecido pelo usuário.
     * @param senha Senha fornecida pelo usuário.
     * @return Retorna true se o login for válido, ou false caso contrário.
     */
    public boolean verificarUsuario(String login, String senha) {
        String sql = ""; // Inicializa a consulta SQL
        Connection conn = conectarBD(); // Estabelece conexão com o banco

        // Monta a instrução SQL para verificar login e senha
        sql += "SELECT nome FROM usuarios ";
        sql += "WHERE login = '" + login + "' ";
        sql += "AND senha = '" + senha + "';";

        try {
            // Prepara e executa a consulta SQL
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            // Caso haja correspondência, atribui o nome do usuário e define o resultado como verdadeiro
            if (rs.next()) {
                result = true;
                nome = rs.getString("nome");
            }
        } catch (Exception e) {
            // Captura erros na execução da consulta
            System.err.println("Erro ao executar consulta: " + e.getMessage());
        }
        return result;
    }
}//fim da class