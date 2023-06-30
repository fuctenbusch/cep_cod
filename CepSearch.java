import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CepSearch {

    private static final String API_URL = "http://viacep.com.br/ws/%s/json/";
    private static Map<String, String> cache = new HashMap<>();

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Escolha uma opção:");
            System.out.println("1. Consultar por CEP");
            System.out.println("2. Consultar por endereço");
            System.out.print("Opção: ");
            String option = reader.readLine();

            if (option.equals("1")) {
                System.out.print("Digite o CEP: ");
                String cep = reader.readLine();
                String addressData = getAddressDataByCep(cep);
                printAddressData(addressData);
            } else if (option.equals("2")) {
                System.out.print("Digite o endereço: ");
                String address = reader.readLine();
                String addressData = getAddressDataByAddress(address);
                printAddressData(addressData);
            } else {
                System.out.println("Opção inválida.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler a entrada do usuário: " + e.getMessage());
        }
    }

    private static String getAddressDataByCep(String cep) {
        if (cache.containsKey(cep)) {
            System.out.println("Recuperando do cache...");
            return cache.get(cep);
        }

        try {
            URL url = new URL(String.format(API_URL, cep));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String addressData = response.toString();
                cache.put(cep, addressData);
                return addressData;
            } else {
                System.out.println("Erro na consulta ao serviço: " + responseCode);
            }
        } catch (IOException e) {
            System.out.println("Erro na consulta ao serviço: " + e.getMessage());
        }

        return null;
    }

    private static String getAddressDataByAddress(String address) {
        // Implemente a lógica para consultar o serviço por endereço (não está incluída no exemplo)
        return null;
    }

    private static void printAddressData(String addressData) {
        // Implemente a lógica para imprimir os dados do endereço (não está incluída no exemplo)
        if (addressData != null) {
            System.out.println("Informações do endereço:");
            // Extrair e imprimir os dados do endereço
        } else {
            System.out.println("Endereço não encontrado.");
        }
    }
}
