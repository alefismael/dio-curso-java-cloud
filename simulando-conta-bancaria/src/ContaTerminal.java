import java.math.BigDecimal;
import java.util.Scanner;

public class ContaTerminal {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.printf("Informe o número de sua conta:");
        int numeroConta = Integer.parseInt(sc.nextLine());
        System.out.printf("Informe sua agência:");
        String agencia = sc.nextLine();
        System.out.printf("Informe o seu nome:");
        String nomeCliente = sc.nextLine();;
        System.out.printf("Informe o seu saldo:");
        BigDecimal saldo = new BigDecimal(sc.nextLine());
        System.out.println("Olá "+nomeCliente+", obrigado por criar uma conta em nosso banco, sua agência é "+agencia+", conta "+numeroConta+" e seu saldo "+saldo+" já está disponível para saque");
        sc.close();
    }
}
