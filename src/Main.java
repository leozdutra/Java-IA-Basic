import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        // Carregar chave do OpenAI do arquivo config.properties
        Properties prop = new Properties();
        prop.load(new FileInputStream("config.properties"));
        String API_KEY = prop.getProperty("OPENAI_API_KEY");

        ChatGPT chatGPT = new ChatGPT(API_KEY);
        Scanner sc = new Scanner(System.in);

        System.out.println("🤖 Chat Java + IA iniciado! Digite 'sair' para encerrar.");

        while (true) {
            System.out.print("\nVocê: ");
            String pergunta = sc.nextLine();

            if (pergunta.equalsIgnoreCase("sair")) {
                System.out.println("🤖 IA: Até logo!");
                break;
            }

            try {
                String resposta = chatGPT.enviarParaIA(pergunta);
                System.out.println("\n🤖 IA: " + resposta);
            } catch (Exception e) {
                System.out.println("\n🤖 IA: Erro ao conectar com a API.");
            }
        }
    }
}

