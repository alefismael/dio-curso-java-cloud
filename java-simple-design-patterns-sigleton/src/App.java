public class App {
    public static void main(String[] args) throws Exception {

        Pessoa um = new Pessoa("Álef");
        Pessoa dois = new Pessoa("Manuele");

        var pCriadas = PessoasCriadas.getNewClass();
        pCriadas.setNumeroPessoasCriadas(um);
        pCriadas.setNumeroPessoasCriadas(dois);

        System.out.println(pCriadas.toString());
    }
}
