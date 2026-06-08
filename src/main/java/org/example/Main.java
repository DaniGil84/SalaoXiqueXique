package org.example;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/DER_ProjetoIntegrador_Manicure";
    private static final String DB_USUARIO = "root";
    private static final String DB_SENHA = "";

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("============================");
        System.out.println("BEM VINDO AO SALÃO XIQUE XIQUE!");
        System.out.println("============================");

        int opcao = 0;

        while (opcao != 10) {
            exibirMenu();

            System.out.println("Escolha uma opção: ");

            if (scan.hasNextInt()) {
                opcao = scan.nextInt();
                scan.nextLine();
            } else {
                System.out.println("Por favor digite uma opção valida!");
                scan.nextLine();
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarUsuario(scan);
                    break;
                case 2:
                    listarTodosUsuarios();
                    break;
                case 3:
                    consultaUsuarioPorNome(scan);
                    break;
                case 4:
                    servicosManicure(scan);
                    break;
                case 10:
                    System.out.println("Encerrando o sistema .... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scan.close();
    }

    public static void exibirMenu() {
        System.out.println("============= Salão Xique Xique ===========");
        System.out.println("1 - Cadastro");
        System.out.println("2 - Listar todos");
        System.out.println("3 - Buscar por nome");
        System.out.println("4 - Serviços");
        System.out.println("10 - Sair");
        System.out.println("===========================");
    }

    public static void cadastrarUsuario(Scanner scan) {

        System.out.println("---Cadastro de USUÁRIO---");

        System.out.println("Digite o nome: ");
        String nome = scan.nextLine();

        System.out.println("Digite o CPF: ");
        String CPF = scan.nextLine();

        System.out.println("Digite a cidade: ");
        String endereco = scan.nextLine();

        if (endereco.length() > 250) {
            System.out.println("Cidade é invalida");
        }

        System.out.println("Digite o E-mail: ");
        String email = scan.nextLine();
        if (!email.contains("@")) {
            System.out.println("E-mail é invalido");
        }

        System.out.println("Digite o Telefone: ");
        String telefone = scan.nextLine();

        String sql = "INSERT INTO usuarios(nome,cpf,endereco,email,telefone) VALUES(?,?,?,?,?)";

        try (Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, CPF);
            stmt.setString(3, endereco);
            stmt.setString(4, email);
            stmt.setString(5, telefone);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Usuário salvo com SUCESSO!");
            }
        } catch (Exception e) {
            System.out.println("FALHA ao Salvar!");
            throw new RuntimeException(e);
        }
    }

    public static void listarTodosUsuarios() {

        System.out.println("Lista de usuários");

        String sql = "SELECT * FROM usuarios";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            boolean encontrouDados = false;

            while (rs.next()) {
                encontrouDados = true;
                System.out.println("Nome: "
                        + rs.getString("nome")
                        + " CPF: "
                        + rs.getString("cpf")
                        + " Email: "
                        + rs.getString("email")
                );
            }

            if (encontrouDados) {
                System.out.println("Dados encontrados!");
            } else {
                System.out.println("Nenhum dado encontrado!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void consultaUsuarioPorNome(Scanner scan) {

        System.out.println("Digite o nome do usuário: ");
        String nome = scan.nextLine();

        String sql = "SELECT * FROM usuarios WHERE nome LIKE ?";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();

            boolean encontrouDados = false;

            while (rs.next()) {
                encontrouDados = true;
                System.out.println("Nome: "
                        + rs.getString("nome")
                        + " CPF: "
                        + rs.getString("cpf")
                        + " Email: "
                        + rs.getString("email")
                );
            }

            if (encontrouDados) {
                System.out.println("Dados encontrados!");
            } else {
                System.out.println("Nenhum dado encontrado!");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void servicosManicure(Scanner scan) {
        int serv = 0;

        while (serv != 9) {
            System.out.println("===== Serviços =====");
            System.out.println("1 - Manicure R$ 25,00");
            System.out.println("2 - Pedicure R$ 30,00");
            System.out.println("3 - Manicure e Pedicure R$ 50,00");
            System.out.println("4 - Esmaltação em gel mão R$ 60,00");
            System.out.println("5 - Esmaltação em gel pé R$ 60,00");
            System.out.println("6 - Alongamento em gel R$ 190,00");
            System.out.println("7 - Alongamento em fibra R$ 250,00");
            System.out.println("9 - Voltar");
            System.out.println("====================");
            System.out.println("Escolha uma opção: ");

            if (scan.hasNextInt()) {
                serv = scan.nextInt();
                scan.nextLine();
            } else {
                System.out.println("Por favor digite um serviço válido!");
                scan.nextLine();
                continue;
            }

            switch (serv) {
                case 1: System.out.println("Manicure R$ 25,00"); break;
                case 2: System.out.println("Pedicure R$ 30,00"); break;
                case 3: System.out.println("Manicure e Pedicure R$ 50,00"); break;
                case 4: System.out.println("Esmaltação em gel mão R$ 60,00"); break;
                case 5: System.out.println("Esmaltação em gel pé R$ 60,00"); break;
                case 6: System.out.println("Alongamento em gel R$ 190,00"); break;
                case 7: System.out.println("Alongamento em fibra R$ 250,00"); break;
                case 9: System.out.println("Voltando ao menu principal..."); break;
                default: System.out.println("Opção inválida!");
            }
        }
    }

    private static Connection conectar() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USUARIO, DB_SENHA);
    }
}
