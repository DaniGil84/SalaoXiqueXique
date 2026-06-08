package org.example;

import java.sql.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/DER_ProjetoIntegrador_Manicure";
    private static final String DB_USUARIO = "root";
    private static final String DB_SENHA = "";



    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);


        //Exemplo de inicialização de lista
        //  List<String> listaPreenchida = List.of("Samuel|0634154654654|Ruyaasldjk","Item2","Item3");


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

            switch (opcao){
                case 1 :
                    cadastrarUsuario(scan);
                    break;
                case 2:
                    listarTodosUsuarios();
                    break;
                case 3:
                    //Consultar por nome
                    consultaUsuarioPorNome(scan);
                    break;
                case 4:
                    //Serviços prestados
                    servicosManicure(scan);
                    break;
//                case 8:
//                    capturaNotas(scan);
//                    break;
                case 10:
                    System.out.println("Encerrando o sistema .... Até logo!");
                    break;
                default:
                    System.out.println("Volte Sempre!");
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
        //System.out.println("8 - Calcular medias");
        //System.out.println("9 - Resultado final   ");
        System.out.println("10 - Sair");
        System.out.println("===========================");
    }

    public static void cadastrarUsuario(Scanner scan){


        System.out.println("---Cadastro de USUÁRIO---");

        System.out.println("Digite o nome: ");
        String nome = scan.nextLine();

        System.out.println("Digite o CPF: ");
        String CPF = scan.nextLine();

        System.out.println("Digite a cidade: ");
        String endereco = scan.nextLine();

        if(endereco.length() > 250){
            System.out.println("Cidade é invalida");
        }

        System.out.println("Digite o E-mail: ");
        String email = scan.nextLine();
        if(!email.contains("@")) {
            System.out.println("E-mail é invalido");
        }

        System.out.println("Digite o Telefone: ");
        String telefone = scan.nextLine();

        String sql ="INSERT INTO usuarios(nome,cpf,endereco,email,telefone) VALUES(?,?,?,?,?)";

        try(Connection conn = conectar(); PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,nome);
            stmt.setString(2,CPF);
            stmt.setString(3,endereco);
            stmt.setString(4,email);
            stmt.setString(5,telefone);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas>0){
                System.out.println("Usuário salvo com SUCESSO!");
            }
        } catch (Exception e) {
            System.out.println("FALHA ao Salvar!");
            throw new RuntimeException(e);
        }



    }

    public static void listarTodosUsuarios(){

        System.out.println("Lista de usuários");

        String sql ="SELECT * FROM usuarios";

        try(Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()){

            boolean encontrouDados = false;

            //para percorrer todas as linhas
            while (rs.next()){
                encontrouDados =true;
                System.out.println("Nome: "
                        + rs.getString("nome")
                        +" CPF: "
                        + rs.getString("cpf")
                        +" Email: "
                        + rs.getString("email")
                );


            }
            if(encontrouDados){
                System.out.println("Dados encontrados!");
            }else {
                System.out.println("Nenhum dado encontrado!");
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    public static void consultaUsuarioPorNome(Scanner scan){

        System.out.println("Digite o nome do usuário: ");
        String nome = scan.nextLine();



        String sql ="SELECT * FROM usuarios WHERE nome LIKE ?";

        try(Connection conn = conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1,"%"+ nome +"%");
            ResultSet rs = stmt.executeQuery();


            boolean encontrouDados = false;

            //para percorrer todas as linhas
            while (rs.next()){
                encontrouDados =true;
                System.out.println("Nome: "
                        + rs.getString("nome")
                        +" CPF: "
                        + rs.getString("cpf")
                        +" Email: "
                        + rs.getString("email")
                );


            }
            if(encontrouDados){
                System.out.println("Dados encontrados!");
            }else {
                System.out.println("Nenhum dado encontrado!");
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public static void servicosManicure (Scanner Scan){
        int serv = 0;

        while (serv != 10) {
            exibirMenu();

            System.out.println("Escolha uma opção: ");

            if (Scan.hasNextInt()) {
                serv = Scan.nextInt();
                Scan.nextLine();

            } else {
                System.out.println("Por favor digite um serviço válido!");
                Scan.nextLine();
                continue;
            }

            switch (serv){
                case 1 :
                    System.out.println ("Manicure R$ 25,00");
                    break;
                case 2:
                    System.out.println ("Pedicure R$ 30,00");
                    break;
                case 3:
                    System.out.println ("Manicure e Pedicure R$ 50,00");
                    break;
                case 4:
                    System.out.println ("Esmaltação em gel mão R$ 60,00");
                    break;
                case 8:
                    System.out.println ("Alongamento em gel R$ 190,00");
                    break;
                case 10:
                    System.out.println ("Alongamento em fibra R$ 250,00");
                    break;
                case 11:
                    System.out.println ("Esmaltação em gel pé R$ 60,00");
                    break;
                default:
                    System.out.println("Opção invalida!");
            }
        }
        Scan.close();
    }
    private static Connection conectar() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USUARIO,DB_SENHA);
    }
}


//    public  static void capturaNotas(Scanner scanner){
//        double notas = 0;
//        int quantidade = 0;
//        boolean capturar = true;
//
//        while (capturar){
//
//            double nota;
//            System.out.println("Digite a nota ou 9999 para resultado!");
//            nota = scanner.nextDouble();
//
//            if(nota <0 ){
//                System.out.println("Nota inválida!");
//                continue;
//            }
//
//            if (nota == 9999){
//                capturar = false;
//                continue;
//            }
//
//            notas += nota;
//            quantidade++;
//        }
//
//        double media = calcularMedia(notas, quantidade);
//
//
//
//        System.out.println("Media do aluno é igual a "+media);
//
//    }


//    public static double calcularMedia(double notas, int quatidade){
//        if (notas == 0){
//            return 0;
//        }
//
//        return notas/quatidade;
//    }
//
